package cn.edu.nju.software.service;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.dao.ActiveDao;
import cn.edu.nju.software.dao.ExamDao;
import cn.edu.nju.software.dao.ImageDao;
import cn.edu.nju.software.dao.ModelDao;
import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.data.mutation.DelModelData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.ActiveDto;
import cn.edu.nju.software.dto.ImageDto;
import cn.edu.nju.software.dto.SubmitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

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
        List<MutationData> models = examDao.findModels(command.getExamId());

        return null;
    }

    public List<ImageDto> filter(FilterCommand filterCommand) {
        List<ImageDto> imageDtos = imageDao.filter(filterCommand.getImageIds(), filterCommand.getTags(), filterCommand.getActive_locations());
        return imageDtos;
    }
}

