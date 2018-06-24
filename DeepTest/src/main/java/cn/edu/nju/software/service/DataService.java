package cn.edu.nju.software.service;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.dao.*;
import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.data.SubmitData;
import cn.edu.nju.software.data.mutation.DelModelData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.ActiveDto;
import cn.edu.nju.software.dto.ImageDto;
import cn.edu.nju.software.dto.SubmitDto;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import javax.swing.*;
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
        List<MutationData> models = examDao.findModels(command.getExamId());
        //List<String> collections = Lists.newArrayList();
        //models.forEach(model -> collections.add(model.getDataCollection()));
        List<Long> killedIds = modelDao.getKilledIds(command.getImageIds(), models);
        dto.setModelIds(killedIds);
        dto.setKilledNums(killedIds.size());

        SubmitData submitData = new SubmitData();
        BeanUtils.copyProperties(command, submitData);
        submitData.setKillModelId(killedIds);
        submitData.setOperationTime(new Date());
        submitDao.insert(submitData);

        return dto;
    }

    public ActiveDto submit(PaintCommand paintCommand) {
        ActiveDto dto = new ActiveDto();
        List<MutationData> models = modelDao.getModelByIds(paintCommand.getModels());
        ImageData imageData = imageDao.findById(paintCommand.getImageId());
        //TODO
        //调用python处理的api的 传入考试id 用户id  原图样本数据id+path 以及需要处理的变异模型的id+path 以及噪音前景图 adversial
        return dto;
    }

    public List<ImageDto> filter(FilterCommand filterCommand) {
        List<Long> imageIds = examDao.getImageIds(filterCommand.getExamId());
        List<ImageDto> imageDtos = imageDao.filter(imageIds, filterCommand.getTags(), filterCommand.getActiveLocations());
        return imageDtos;
    }

}

