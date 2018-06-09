package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.OperationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by mengf on 2018/6/8 0008.
 */
@Repository
public class OperationDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(OperationData data) {
        mongoTemplate.save(data);
    }
}
