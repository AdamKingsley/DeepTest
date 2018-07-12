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


    public void create(ExamCommand command) {
        ExamData data = new ExamData();
        BeanUtils.copyProperties(command, data);
        data.setCreateTime(new Date());
        data.setModifyTime(data.getCreateTime());
        examDao.insert(data);
    }



    public ExamDto findByTaskId(String taskId) {
        ExamData examData = examDao.getExamIdByTaskId(taskId);
        ExamDto dto = new ExamDto();
        dto.setId(examData.getId());
        dto.setType(examData.getType());
        return dto;
    }

}
