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

    public List<Long> getImageIds(Long id) {
        Query query = QueryUtil.queryByField("image_ids");
        ExamData example = new ExamData();
        example.setId(id);
        query.addCriteria(Criteria.byExample(example));
        ExamData data = mongoTemplate.findOne(query, ExamData.class);
        return data.getImageIds();
    }

    public List<Long> getModelIds(Long id) {
        Query query = QueryUtil.queryByField("model_ids");
        ExamData example = new ExamData();
        example.setId(id);
        query.addCriteria(Criteria.byExample(example));
        ExamData data = mongoTemplate.findOne(query, example.getClass());
        return data.getModelIds();
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

    public ExamData getExamData(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        ExamData data = mongoTemplate.findOne(query, ExamData.class);
        return data;
    }
}
