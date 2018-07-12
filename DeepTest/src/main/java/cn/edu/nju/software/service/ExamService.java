package cn.edu.nju.software.service;

import cn.edu.nju.software.command.ExamCommand;
import cn.edu.nju.software.dao.*;
import cn.edu.nju.software.data.ExamData;
import cn.edu.nju.software.data.ExamScoreData;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.*;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Service
public class ExamService {
    @Autowired
    private ExamDao examDao;

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private SubmitDao submitDao;
    @Autowired
    private SubmitCountDao submitCountDao;

    @Autowired
    private ExamScoreDao examScoreDao;

    @Autowired
    private CommonService commonService;


    public void create(ExamCommand command) {
        ExamData data = new ExamData();
        BeanUtils.copyProperties(command, data);
        data.setCreateTime(new Date());
        data.setModifyTime(data.getCreateTime());
        examDao.insert(data);
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
        //之前是通过count进行的统计
        //return submitDao.getSubmitTimes(id, commonService.getUserId());
        //如今将count放在了examCount表中
        return submitCountDao.getCount(id, commonService.getUserId());
    }

    public List<Long> getKilledIds(Long id) {
        //直接是通过submit_data表进行的统计
        //List<Long> killIds = submitDao.getKilledModelIds(id, commonService.getUserId());
        //return killIds;
        //如今将数据放在了exam_score表中
        return examScoreDao.getKilledModelIds(id, commonService.getUserId());
    }

    public ExamDto findByTaskId(String taskId) {
        ExamData examData = examDao.getExamIdByTaskId(taskId);
        ExamDto dto = new ExamDto();
        dto.setId(examData.getId());
        dto.setType(examData.getType());
        return dto;
    }

    public ExamScoreDto getScore(Long examId, String userId) {
        ExamScoreData scoreData = examScoreDao.getExamScore(examId, userId);
        ExamScoreDto dto = new ExamScoreDto();
        BeanUtils.copyProperties(scoreData, dto);
        return dto;
    }
}
