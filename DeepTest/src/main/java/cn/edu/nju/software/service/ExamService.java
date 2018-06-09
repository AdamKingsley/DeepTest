package cn.edu.nju.software.service;

import cn.edu.nju.software.command.ExamCommand;
import cn.edu.nju.software.dao.ExamDao;
import cn.edu.nju.software.data.ExamData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
