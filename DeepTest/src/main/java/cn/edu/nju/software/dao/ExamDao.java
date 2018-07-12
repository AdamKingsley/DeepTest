package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ExamData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.util.QueryUtil;
import com.google.common.collect.Lists;
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

    public void insert(ExamData examData) {
        mongoTemplate.insert(examData);
    }


    public ExamData getExamIdByTaskId(String taskId) {
        Query query = QueryUtil.queryByField("_id", "type");
        query.addCriteria(Criteria.where("task_id").is(taskId));
        ExamData examData = mongoTemplate.findOne(query, ExamData.class);
        return examData;
    }

    public ExamData getSimpleExamData(Long id) {
        Query query = QueryUtil.queryExceptField("image_ids", "model_ids");
        query.addCriteria(Criteria.where("_id").is(id));
        ExamData data = mongoTemplate.findOne(query, ExamData.class);
        return data;
    }

}
