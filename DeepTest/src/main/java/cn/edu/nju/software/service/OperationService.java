package cn.edu.nju.software.service;

import cn.edu.nju.software.dao.OperationDao;
import cn.edu.nju.software.data.OperationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Service
public class OperationService {
    @Autowired
    private OperationDao operationDao;
    @Autowired
    private CommonService commonService;

    public void saveOperation(Long imageId, Long modelId, String userId) {
        OperationData data = new OperationData();
        data.setImageId(imageId);
        data.setModelId(modelId);
        data.setUserId(commonService.getUserId());
        data.setOperationTime(new Date());
        operationDao.save(data);
    }
}
