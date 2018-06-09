package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.mutation.MutationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by mengf on 2018/6/8 0008.
 */
@Repository
public class ModelDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据ID获取对应的变异模型
     * @param id
     * @return
     */
    public MutationData getById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MutationData.class);
    }
}
