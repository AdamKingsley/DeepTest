package cn.edu.nju.software.service;

import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.mooctest.AssignTaskCommand;
import cn.edu.nju.software.command.mooctest.ScoreCommand;
import cn.edu.nju.software.command.mooctest.ScoreDetailCommand;
import cn.edu.nju.software.command.python.ImageCommand;
import cn.edu.nju.software.command.python.ImageDataCommand;
import cn.edu.nju.software.command.python.PaintSubmitCommand;
import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.dao.*;
import cn.edu.nju.software.data.*;
import cn.edu.nju.software.dto.ImageDataDto;
import cn.edu.nju.software.dto.PaintSubmitDto;
import cn.edu.nju.software.service.feign.PythonFeign;
import cn.edu.nju.software.service.score.ScoreStrategyContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
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
@Slf4j
public class DataService {

    @Autowired
    private ExamDao examDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private SubmitCountDao submitCountDao;
    @Autowired
    private PythonFeign pythonFeign;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MoocTestService moocTestService;
    @Autowired
    private CaseDao caseDao;
    @Autowired
    private ScoreStrategyContext strategy;

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
        //打印data
        log.info(JSON.toJSONString(data));
        if (data == null) {
            throw new ServiceException("考试信息获取失败！");
        }
        // 如果不在考试时间范围内的话，不允许提交
        checkTime(data.getStartTime(), data.getEndTime());
        //List<MutationData> models = modelDao.getModelByIds(paintCommand.getModels());
        ImageData imageData = imageDao.findById(paintCommand.getImageId());
        //调用python接口跑模型 获取模型运行的结果
        List<PaintSubmitData> submit_datas = getPaintSubmitDatas(paintCommand, imageData);
        //获取case当前的基础信息
        UserCaseData caseData = caseDao.getUserCaseData(paintCommand.getExamId(), paintCommand.getUserId(), paintCommand.getCaseId());
        //获取last_submit_time
        Date submitTime = submit_datas.get(0).getSubmitTime();
        //count++
        submitCountDao.updateCount(paintCommand.getExamId(), paintCommand.getUserId(), submitTime);
        //更新case的基本信息情况
        boolean update_result = false;
        for (PaintSubmitData submitData : submit_datas) {
            update_result = updateCaseInfo(submitData, caseData, data.getTaskId());
        }
        //成绩回传到mooctest 若失败抛出异常 捕获后返回给前端
        //size == 0
        if (update_result) {
            //成绩需要更新，需要重新向慕测提交成绩信息
            assignScore(paintCommand.getExamId(), paintCommand.getUserId(), data.getTaskId());
        }
        //返回给前端运行数据的结果信息
        List<PaintSubmitDto> results = Lists.newArrayList();
        submit_datas.forEach(submit_data -> {
            PaintSubmitDto dto = new PaintSubmitDto();
            BeanUtils.copyProperties(submit_data, dto);
            results.add(dto);
        });
        return results;
    }

    private boolean updateCaseInfo(PaintSubmitData submitData, UserCaseData caseData, String taskId) {
        boolean result = false;
        //回传成功的话更新考试成绩表score 并且根据成绩是否提升更新case表的数据
        if (submitData.getIsKilled()) {
            //如果caseData杀死了
            if (caseData.getIsKilled()) {
                //如果最新的case的分数比原来高
                if (caseData.getScore() < submitData.getScore()) {
                    caseDao.updateCaseData(submitData.getExamId(), submitData.getUserId(), submitData.getCaseId(),
                            submitData.getComposePath(), submitData.getComposePath(),
                            submitData.getScore(), submitData.getIsKilled());
                    //表示得分高 分数需要重新计算
                    result = true;
                } else {
                    //如果不比原来高的话就直接存储的是最新的绘图地址
                    caseDao.updateLastComposePath(submitData.getExamId(), submitData.getUserId(), submitData.getCaseId(), submitData.getComposePath());
                }
            } else {
                //如果第一次杀死就直接更新数据
                //表示得分
                caseDao.updateCaseData(submitData.getExamId(), submitData.getUserId(), submitData.getCaseId(),
                        submitData.getComposePath(), submitData.getComposePath(),
                        submitData.getScore(), submitData.getIsKilled());
                result = true;
            }
        } else {
            //没杀死只是更新最后的绘制的图片数据
            caseDao.updateLastComposePath(submitData.getExamId(), submitData.getUserId(), submitData.getCaseId(), submitData.getComposePath());
        }
        return result;
    }

    //TODO 提交成绩的接口调用
    private void assignScore(Long examId, String userId, String taskId) {
        List<UserCaseData> caseDataList = caseDao.getUserCaseDatas(examId, userId);
        //拿到所有的成绩信息数据开始算分
        List<Double> scores = Lists.newArrayList();
        int killedNum = 0;
        //算完分后开始进行数据的组装提交
        AssignTaskCommand command = new AssignTaskCommand();
        command.setTaskId(taskId);
        Map<String, String> task_case_list = Maps.newConcurrentMap();
        List<ScoreCommand> scoreDetails = Lists.newArrayList();
        ScoreCommand scoreCommand = new ScoreCommand();
        List<ScoreDetailCommand> details = Lists.newArrayList();
        for (UserCaseData userCaseData : caseDataList) {
            ScoreDetailCommand detail = new ScoreDetailCommand();
            detail.setCaseId(userCaseData.getCaseId());
            task_case_list.put(userCaseData.getCaseId(), userCaseData.getCaseId());
            if (userCaseData.getIsKilled() == null || !userCaseData.getIsKilled()) {
                detail.setScore(0.0);
                scores.add(detail.getScore());
            } else {
                //杀死
                detail.setScore(userCaseData.getScore());
                scores.add(detail.getScore());
                killedNum += 1;
            }
            details.add(detail);
        }
        Double score = strategy.calculateScore("calScoreForPaintSample", killedNum, caseDataList.size(), 1, scores);
        scoreCommand.setScore(score);
        scoreCommand.setOpenId(userId);
        scoreCommand.setDetails(details);
        scoreDetails.add(scoreCommand);
        command.setCaseList(task_case_list);
        command.setScoreDetails(scoreDetails);
        //pring JSONObject String of the command
        log.info(JSON.toJSONString(command));
        //TODO 提交成绩
        moocTestService.assignTask(command);
    }

    private List<PaintSubmitData> getPaintSubmitDatas(PaintCommand paintCommand, ImageData imageData) {
        //整理image为imageCommand
        ImageCommand imageCommand = new ImageCommand();
        BeanUtils.copyProperties(imageData, imageCommand);
        //设置image的tag数据
        imageCommand.setTag(imageData.getTags().get(0).getValue());

        PaintSubmitCommand command = new PaintSubmitCommand();
        command.setComposeImageStr(paintCommand.getComposeImageStr());
        command.setUserId(paintCommand.getUserId() == null ? commonService.getUserId() : paintCommand.getUserId());
        command.setExamId(paintCommand.getExamId());
        command.setCaseId(paintCommand.getCaseId());
        //command.setMutaionModels(model_commands);
        command.setImage(imageCommand);
        command.setStandardModelPath("standard_model.hdf5");
        //TODO 前后端联调测试该接口
        //调用python处理的api的 传入考试id 用户id  原图样本数据id+path 以及需要处理的变异模型的id+path 以及噪音前景图 adversial
        String datas = pythonFeign.paintSubmit(command);
        List<PaintSubmitData> paintSubmitDatas = JSONArray.parseArray(datas, PaintSubmitData.class);
        return paintSubmitDatas;
    }


    public ImageDataDto processThin(ImageDataCommand command) {
        return pythonFeign.processThin(command);
    }

    public ImageDataDto processFat(ImageDataCommand command) {
        return pythonFeign.processFat(command);
    }
}

