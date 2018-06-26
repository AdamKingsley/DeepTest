package cn.edu.nju.software.service;

import cn.edu.nju.software.command.ExamCommand;
import cn.edu.nju.software.common.exception.ServiceException;
import cn.edu.nju.software.dao.ExamDao;
import cn.edu.nju.software.dao.ImageDao;
import cn.edu.nju.software.dao.ModelDao;
import cn.edu.nju.software.dao.SubmitDao;
import cn.edu.nju.software.data.ExamData;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.data.SubmitData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.*;
import cn.edu.nju.software.util.UserUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Service
public class ExamService {
    @Autowired
    private ExamDao examDao;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private SubmitDao submitDao;
    @Autowired
    private CommonService commonService;


    public void create(ExamCommand command) {
        ExamData data = new ExamData();
        BeanUtils.copyProperties(command, data);
        data.setCreateTime(new Date());
        data.setModifyTime(data.getCreateTime());
        examDao.insert(data);
    }

    public ExamDto getExamDetail(Long id) {
        ExamDto dto = new ExamDto();
        dto.setModels(getExamModels(id));
        ExamImageDto examImageDto = getExamImages(id);
        dto.setAllImages(examImageDto.getAllImages());
        dto.setSelectedImageIds(examImageDto.getSelectedImageIds());
        List<Long> killIds = submitDao.getKilledModelIds(id, commonService.getUserId());
        dto.setKilledModelIds(killIds);
        dto.setTimes(submitDao.getSubmitTimes(id, commonService.getUserId()));
        return dto;
    }

    public List<ModelDto> getExamModels(Long id) {
        List<ModelDto> modelDtos = Lists.newArrayList();
        List<Long> modelIds = examDao.getModelIds(id);
        List<MutationData> datas = modelDao.getModelByIds(modelIds);
        datas.forEach(data -> {
            ModelDto dto = new ModelDto();
            BeanUtils.copyProperties(data, dto);
            modelDtos.add(dto);
        });
        return modelDtos;
    }

    public ExamImageDto getExamImages(Long id) {
        List<ImageDto> allImageDtos = Lists.newArrayList();
        List<ImageDto> selectImageDtos = Lists.newArrayList();

        //获取考试所有的样本图片的数据，然后从image_data表中获取图片的具体信息
        List<Long> allImageIds = examDao.getImageIds(id);
        List<ImageData> allImages = imageDao.findByIds(allImageIds);
        allImages.forEach(image -> {
            ImageDto dto = new ImageDto();
            BeanUtils.copyProperties(image, dto);
            allImageDtos.add(dto);
        });

        List<Long> selectedImageIds = submitDao.getSubmitImageIds(id, commonService.getUserId());

        //组装数据返回
        ExamImageDto dto = new ExamImageDto();
        dto.setAllImages(allImageDtos);
        dto.setSelectedImageIds(selectedImageIds);
        return dto;
    }

    public Long getSubmitCount(Long id) {
        return submitDao.getSubmitTimes(id, commonService.getUserId());
    }

    public List<Long> getKilledIds(Long id) {
        List<Long> killIds = submitDao.getKilledModelIds(id, commonService.getUserId());
        return killIds;
    }

    public ExamDto findByTaskId(String taskId) {
        ExamData examData = examDao.getExamIdByTaskId(taskId);
        ExamDto dto = new ExamDto();
        dto.setId(examData.getId());
        dto.setType(examData.getType());
        return dto;
    }
}
