package cn.edu.nju.software.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by mengf on 2018/6/10 0010.
 */
@Repository
public class BasicDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Object obj) {
        mongoTemplate.save(obj);
    }

    public void insert(Object obj) {
        mongoTemplate.insert(obj);
    }
}
