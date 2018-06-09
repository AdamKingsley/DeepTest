package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ExamData;
import cn.edu.nju.software.data.mutation.MutationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by mengf on 2018/6/8 0008.
 */
@Repository
public class ExamDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MutationData> findModels(Long examId) {
        //TODO 可否有连表查询提高效率的方法？
        Query query = Query.query(Criteria.where("_id").is(examId));
        ExamData data = mongoTemplate.findOne(query, ExamData.class);
        List<Long> modelIds = data.getModelIds();
        query = new Query(Criteria.where("_id").in(modelIds));
        List<MutationData> mutationDatas = mongoTemplate.find(query, MutationData.class);
        return mutationDatas;
    }

    public List<MutationData> findByModelIds(Collection<Long> ids) {
        //TODO 可否有连表查询提高效率的方法？
        Query query = Query.query(Criteria.where("_id").in(ids));
        query = new Query(Criteria.where("_id").in(ids));
        List<MutationData> mutationDatas = mongoTemplate.find(query, MutationData.class);
        return mutationDatas;
    }

    public void insert(ExamData examData) {
        mongoTemplate.insert(examData);
    }
}
