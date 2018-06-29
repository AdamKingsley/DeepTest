package cn.edu.nju.software.service;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.command.python.ImageCommand;
import cn.edu.nju.software.command.python.ImageDataCommand;
import cn.edu.nju.software.command.python.ModelCommand;
import cn.edu.nju.software.command.python.PaintSubmitCommand;
import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.dao.*;
import cn.edu.nju.software.data.*;
import cn.edu.nju.software.data.mutation.DelModelData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.*;
import cn.edu.nju.software.service.feign.PythonFeign;
import cn.edu.nju.software.service.score.ScoreStrategyContext;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Service
public class DataService {

    @Autowired
    private ActiveDao activeDao;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private SubmitDao submitDao;
    @Autowired
    private SubmitCountDao submitCountDao;
    @Autowired
    private ExamScoreDao examScoreDao;
    @Autowired
    private PythonFeign pythonFeign;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ScoreStrategyContext strategy;

    public ActiveDto getActivaData(Long imageId, Long modelId, Boolean output) {
        //TODO output 字段的筛选还没实现
        ActiveDto dto = new ActiveDto();
        dto.setImageId(imageId);
        MutationData mutation = modelDao.getById(modelId);
        if (mutation instanceof DelModelData) {
            ActiveData data = activeDao.findDataInMutation(imageId, mutation.getDataCollection(), output);
            dto.setMutation(data);
            dto.setMutationCollection(mutation.getDataCollection());
        }
        ActiveData data = activeDao.findDataInStandard(imageId, output);
        dto.setStandard(data);
        return dto;
    }

    public SubmitDto submit(SubmitCommand command) {
        SubmitDto dto = new SubmitDto();
        ExamData data = examDao.getSimpleExamData(command.getExamId());
        //如果不在考试时间范围内的话，不允许提交
        checkTime(data.getStartTime(), data.getEndTime());
        List<MutationData> models = examDao.findModels(command.getExamId());
        //本次杀死的killedIds
        List<Long> killedIds = modelDao.getKilledIds(command.getImageIds(), models);
        dto.setModelIds(killedIds);
        dto.setKilledNums(killedIds.size());
        //保存本次杀死的信息
        SubmitData submitData = new SubmitData();
        BeanUtils.copyProperties(command, submitData);
        submitData.setKillModelId(killedIds);
        submitData.setSubmitTime(new Date());
        submitDao.insert(submitData);
        //count ++
        submitCountDao.updateCount(submitData.getExamId(), submitData.getUserId(), submitData.getSubmitTime());
        //更新成绩表
        //获取杀死变异体的并集
        killedIds = getTotalKilledModels(command.getExamId(), command.getUserId(), killedIds);
        //传入考试id 用户id 和总杀死量 计算成绩
        Double score = calScoreForSelectExam(submitData.getExamId(), submitData.getUserId(), killedIds);
        //保存该考生本次提交后的综合成绩
        examScoreDao.updateScore(submitData.getExamId(), submitData.getUserId(), submitData.getKillModelId(), score);
        return dto;
    }

    private Double calScoreForSelectExam(Long examId, String userId, List<Long> killModelIds) {
        //考试中总共的model的个数
        List<Long> modelIds = examDao.getModelIds(examId);
        Long totalNum = (long) modelIds.size();
        //提交的次数
        Long count = submitCountDao.getCount(examId, userId);
        //计算第一种选择图片的考试的成绩
        Double score = strategy.calculateScore("calScoreForSelectSample", (long) killModelIds.size(), totalNum, count, Lists.newArrayList());
        return score;
    }

    private List<Long> getTotalKilledModels(Long examId, String userId, List<Long> killModelIds) {
        //防止空指针异常
        if (killModelIds == null) {
            killModelIds = Lists.newArrayList();
        }
        ExamScoreData data = examScoreDao.getExamScore(examId, userId);
        if (data == null || data.getKilledModelIds() == null || data.getKilledModelIds().size() == 0) {
            data.setKilledModelIds(killModelIds);
        } else {
            List<Long> killedModelIds = data.getKilledModelIds();
            for (Long modelId : killedModelIds) {
                //取并集
                if (!killModelIds.contains(modelId))
                    killModelIds.add(modelId);
            }
        }
        return killModelIds;
    }


    private Double calScoreForPaintExam(Long examId, String userId, List<MseScoreData> killedDetails) {
        //数据准备
        //考试中总共的model的个数
        List<Long> modelIds = examDao.getModelIds(examId);
        Long totalNum = (long) modelIds.size();
        List<Long> killedModelIds = Lists.newArrayList();
        List<Double> killedMseScore = Lists.newArrayList();
        killedDetails.forEach(detail -> {
            killedModelIds.add(detail.getModelId());
            killedMseScore.add(detail.getScore());
        });
        //提交的次数
        Long count = submitCountDao.getCount(examId, userId);

        //计算第二种考试的成绩
        Double score = strategy.calculateScore("calScoreForPaintSample", (long) killedModelIds.size(), totalNum, count, killedMseScore);
        return score;
    }


    private void checkTime(Date startTime, Date endTime) {
        //如果不在考试时间范围内，不允许提交
        long millis = new Date().getTime();
        if (startTime.getTime() > millis) {
            throw new ServiceException("考试尚未开始，耐心等待开始再提交！");
        }
        if (endTime.getTime() < millis) {
            throw new ServiceException("考试已经结束，无法提交您的数据！");
        }
    }

    public List<PaintSubmitDto> submit(PaintCommand paintCommand) {
        // 时间检查
        ExamData data = examDao.getSimpleExamData(paintCommand.getExamId());
        // 如果不在考试时间范围内的话，不允许提交
        checkTime(data.getStartTime(), data.getEndTime());
        List<MutationData> models = modelDao.getModelByIds(paintCommand.getModels());
        ImageData imageData = imageDao.findById(paintCommand.getImageId());
        //调用python接口跑模型 获取模型运行的结果
        List<PaintSubmitData> submit_datas = getPaintSubmitDatas(paintCommand, models, imageData);
        //获取last_submit_time
        Date submitTime = submit_datas.get(0).getSubmitTime();
        //count++
        submitCountDao.updateCount(paintCommand.getExamId(), paintCommand.getUserId(), submitTime);
        ExamScoreData scoreData = examScoreDao.getExamScore(paintCommand.getExamId(), paintCommand.getUserId());
        //获取之前保存的考试成绩的data 注意空指针异常
        //List<Long> killedModelIds = scoreData == null || scoreData.getKilledModelIds() == null ?
        //        Lists.newArrayList() : scoreData.getKilledModelIds();
        List<MseScoreData> mseDatas = scoreData == null || scoreData.getKilledDetail() == null ?
                Lists.newArrayList() : scoreData.getKilledDetail();
        //将之前已经杀死的数据构建出一个map
        Map<Long, MseScoreData> killedMap = Maps.newConcurrentMap();
        mseDatas.forEach(mse -> killedMap.put(mse.getModelId(), mse));
        mseDatas = getKilledDetails(submit_datas, killedMap);
        //更新成绩表
        Double score = calScoreForPaintExam(paintCommand.getExamId(), paintCommand.getUserId(), mseDatas);
        examScoreDao.updateScore(paintCommand.getExamId(), paintCommand.getUserId(), score, mseDatas);
        List<PaintSubmitDto> dtos = Lists.newArrayList();
        submit_datas.forEach(submitData -> {
            PaintSubmitDto dto = new PaintSubmitDto();
            BeanUtils.copyProperties(submitData, dto);
            dtos.add(dto);
        });
        return dtos;
    }

    private List<MseScoreData> getKilledDetails(List<PaintSubmitData> submit_datas, Map<Long, MseScoreData> killedMap) {
        //整合数据返回
        submit_datas.forEach(submitData -> {
            //只有本次提交杀死了模型才能计入成绩
            if (submitData.getIsKilled()) {
                MseScoreData killedDetail = killedMap.get(submitData.getModelId());
                //如果之前已经杀死了就比较mse成绩哪家强  强者留下来
                if (killedDetail != null) {
                    if (killedDetail.getScore() < submitData.getScore()) {
                        MseScoreData mseScoreData = new MseScoreData();
                        BeanUtils.copyProperties(submitData, mseScoreData);
                        killedMap.put(submitData.getModelId(), mseScoreData);
                    }
                } else {
                    //如果之前没有杀死的话，保留信息
                    //killedModelIds.add(submitData.getModelId());
                    MseScoreData mseScoreData = new MseScoreData();
                    BeanUtils.copyProperties(submitData, mseScoreData);
                    //mseDatas.add(mseScoreData);
                    killedMap.put(mseScoreData.getModelId(), mseScoreData);
                }
            }
        });
        List<MseScoreData> mseScoreDataList = Lists.newArrayList();
        killedMap.values().forEach(mseScoreData -> mseScoreDataList.add(mseScoreData));
        return mseScoreDataList;
    }

    public List<ImageDto> filter(FilterCommand filterCommand) {
        List<Long> imageIds = examDao.getImageIds(filterCommand.getExamId());
        List<ImageDto> imageDtos = imageDao.filter(imageIds, filterCommand.getTags(), filterCommand.getActiveLocations());
        return imageDtos;
    }

    private List<PaintSubmitData> getPaintSubmitDatas(PaintCommand paintCommand, List<MutationData> models, ImageData imageData) {
        //整理models为modelCommand
        List<ModelCommand> model_commands = Lists.newArrayList();
        models.forEach(model -> {
            ModelCommand modelCommand = new ModelCommand();
            BeanUtils.copyProperties(model, modelCommand);
            model_commands.add(modelCommand);
        });
        //整理image为imageCommand
        ImageCommand imageCommand = new ImageCommand();
        BeanUtils.copyProperties(imageData, imageCommand);

        PaintSubmitCommand command = new PaintSubmitCommand();
        command.setComposeImageStr(paintCommand.getComposeImageStr());
        command.setUserId(paintCommand.getUserId() == null ? commonService.getUserId() : paintCommand.getUserId());
        command.setExamId(paintCommand.getExamId());
        command.setMutaionModels(model_commands);
        command.setImage(imageCommand);
        command.setStandardModelPath("standard_model.hdf5");
        //TODO 前后端联调测试该接口
        //调用python处理的api的 传入考试id 用户id  原图样本数据id+path 以及需要处理的变异模型的id+path 以及噪音前景图 adversial
        String datas = pythonFeign.paintSubmit(command);
        List<PaintSubmitData> submitDatas = JSONArray.parseArray(datas, PaintSubmitData.class);
        return submitDatas;
    }

    public ImageDataDto processThin(ImageDataCommand command) {
        return pythonFeign.processThin(command);
    }

    public ImageDataDto processFat(ImageDataCommand command) {
        return pythonFeign.processFat(command);
    }
}

