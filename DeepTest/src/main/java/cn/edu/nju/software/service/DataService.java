package cn.edu.nju.software.service;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.command.mooctest.AssignTaskCommand;
import cn.edu.nju.software.command.mooctest.ScoreCommand;
import cn.edu.nju.software.command.mooctest.ScoreDetailCommand;
import cn.edu.nju.software.command.python.ImageCommand;
import cn.edu.nju.software.command.python.ImageDataCommand;
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
        CaseData caseData = caseDao.getCaseData(paintCommand.getExamId(), paintCommand.getCaseId());
        //获取last_submit_time
        Date submitTime = submit_datas.get(0).getSubmitTime();
        //count++
        submitCountDao.updateCount(paintCommand.getExamId(), paintCommand.getUserId(), submitTime);
        //TODO ！！！！
        //成绩回传到mooctest 若失败抛出异常 捕获后返回给前端
        //size == 0
        for (PaintSubmitData submitData : submit_datas) {
            updateCaseInfo(submitData, caseData, data.getTaskId());
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

    private void updateCaseInfo(PaintSubmitData submitData, CaseData caseData, String taskId) {
        //回传成功的话更新考试成绩表score 并且根据成绩是否提升更新case表的数据
        if (submitData.getIsKilled()) {
            //如果caseData杀死了
            if (caseData.getIsKilled()) {
                //如果最新的case的分数比原来高
                if (caseData.getScore() < submitData.getScore()) {
                    caseDao.updateCaseData(submitData.getExamId(), submitData.getCaseId(),
                            submitData.getComposePath(), submitData.getComposePath(),
                            submitData.getScore(), submitData.getIsKilled());
                    //发送回mooctest成绩
                    assignScore(submitData, taskId);
                } else {
                    //如果不比原来高的话就直接存储的是最新的绘图地址
                    caseDao.updateLastComposePath(submitData.getExamId(), submitData.getCaseId(), submitData.getComposePath());
                }
            } else {
                //如果第一次杀死就直接更新数据
                caseDao.updateCaseData(submitData.getExamId(), submitData.getCaseId(),
                        submitData.getComposePath(), submitData.getComposePath(),
                        submitData.getScore(), submitData.getIsKilled());
                //发送回mooctest成绩
                assignScore(submitData, taskId);
            }
        } else {
            //没杀死只是更新最后的绘制的图片数据
            caseDao.updateLastComposePath(submitData.getExamId(), submitData.getCaseId(), submitData.getComposePath());
        }
    }

    //TODO 提交成绩的接口调用 问一下黄老师和梅杰学长
    private void assignScore(PaintSubmitData submitData, String taskId) {
//        AssignTaskCommand command = new AssignTaskCommand();
//        command.setTaskId(taskId);
//        command.setScore(submitData.getScore());
//        ScoreCommand scoreCommand = new ScoreCommand();
//        scoreCommand.setScore(submitData.getScore());
//        scoreCommand.setOpenId(commonService.getUserId());
//        List<ScoreDetailCommand> details = Lists.newArrayList();
//        ScoreDetailCommand scoreDetailCommand = new ScoreDetailCommand();
//        scoreDetailCommand.setCaseId(submitData.getCaseId());
//        scoreDetailCommand.setScore(submitData.getScore());
//        details.add(scoreDetailCommand);
//        scoreCommand.setDetails(details);
//        List<ScoreCommand> scoreDetails = Lists.newArrayList();
//        scoreDetails.add(scoreCommand);
//        command.setScoreDetails(scoreDetails);
//        //提交成绩
//        moocTestService.assignTask(command);
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

    /**
     * 图片变瘦
     * @param command
     * @return
     */
    public ImageDataDto processThin(ImageDataCommand command) {
        return pythonFeign.processThin(command);
    }

    /**
     * 图片变胖
     * @param command
     * @return
     */
    public ImageDataDto processFat(ImageDataCommand command) {
        return pythonFeign.processFat(command);
    }
}

