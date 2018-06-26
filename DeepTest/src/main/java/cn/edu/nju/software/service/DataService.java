package cn.edu.nju.software.service;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.command.python.ImageCommand;
import cn.edu.nju.software.command.python.ModelCommand;
import cn.edu.nju.software.command.python.PaintSubmitCommand;
import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.dao.*;
import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.data.ExamData;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.data.SubmitData;
import cn.edu.nju.software.data.mutation.DelModelData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.ActiveDto;
import cn.edu.nju.software.dto.ImageDto;
import cn.edu.nju.software.dto.PaintSubmitDto;
import cn.edu.nju.software.dto.SubmitDto;
import cn.edu.nju.software.service.feign.PythonFeign;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import javax.swing.*;
import java.rmi.ServerError;
import java.util.Date;
import java.util.List;

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
    private PythonFeign pythonFeign;
    @Autowired
    private CommonService commonService;

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
        checkTime(data.getStartTime(),data.getEndTime());
        List<MutationData> models = examDao.findModels(command.getExamId());
        //List<String> collections = Lists.newArrayList();
        //models.forEach(model -> collections.add(model.getDataCollection()));
        List<Long> killedIds = modelDao.getKilledIds(command.getImageIds(), models);
        dto.setModelIds(killedIds);
        dto.setKilledNums(killedIds.size());

        SubmitData submitData = new SubmitData();
        BeanUtils.copyProperties(command, submitData);
        submitData.setKillModelId(killedIds);
        submitData.setSubmitTime(new Date());
        submitDao.insert(submitData);
        //count ++
        return dto;
    }

    private void checkTime(Date startTime,Date endTime){
        //如果不在考试时间范围内，不允许提交
        long millis = new Date().getTime();
        if(startTime.getTime()>millis) {
            throw new ServiceException("考试尚未开始，耐心等待开始再提交！");
        }
        if(endTime.getTime()<millis){
            throw new ServiceException("考试已经结束，无法提交您的数据！");
        }
    }


    public List<PaintSubmitDto> submit(PaintCommand paintCommand) {
        // 时间检查
        ExamData data = examDao.getSimpleExamData(paintCommand.getExamId());
        // 如果不在考试时间范围内的话，不允许提交
        checkTime(data.getStartTime(),data.getEndTime());
        List<MutationData> models = modelDao.getModelByIds(paintCommand.getModels());
        ImageData imageData = imageDao.findById(paintCommand.getImageId());

        //整理models为modelCommand
        List<ModelCommand> model_commands = Lists.newArrayList();
        models.forEach(model-> {
            ModelCommand modelCommand = new ModelCommand();
            BeanUtils.copyProperties(model,modelCommand);
            model_commands.add(modelCommand);
        });
        //整理image为imageCommand
        ImageCommand imageCommand = new ImageCommand();
        BeanUtils.copyProperties(imageData,imageCommand);

        PaintSubmitCommand command = new PaintSubmitCommand();
        command.setAdversialStr(paintCommand.getAdversial());
        command.setUserId(paintCommand.getUserId()==null?commonService.getUserId():paintCommand.getUserId());
        command.setExamId(paintCommand.getExamId());
        command.setMutaionModels(model_commands);
        command.setImage(imageCommand);
        command.setStandardModelPath("standard_model.hdf5");
        //TODO 前后端联调测试该接口
        //调用python处理的api的 传入考试id 用户id  原图样本数据id+path 以及需要处理的变异模型的id+path 以及噪音前景图 adversial
        List<PaintSubmitDto> dtos = pythonFeign.paintSubmit(command);
        //count++
        return dtos;
    }

    public List<ImageDto> filter(FilterCommand filterCommand) {
        List<Long> imageIds = examDao.getImageIds(filterCommand.getExamId());
        List<ImageDto> imageDtos = imageDao.filter(imageIds, filterCommand.getTags(), filterCommand.getActiveLocations());
        return imageDtos;
    }

}

