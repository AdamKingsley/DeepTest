package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.SubmitCountData;
import cn.edu.nju.software.data.SubmitData;
import cn.edu.nju.software.util.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class SubmitCountDao {
    @Autowired
    private MongoTemplate mongoTemplate;


    public void updateCount(Long examId, String userId, Date submitTime) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("user_id").is(userId));
        SubmitCountData data = mongoTemplate.findOne(query, SubmitCountData.class);
        // 有 upsert 方法，但是感觉总要取出来，因为count需要++ 如果不存在需要设置为1
        if (data == null) {
            data = new SubmitCountData();
            data.setCount(1L);
            data.setExamId(examId);
            data.setUserId(userId);
            data.setFirstTime(submitTime.getTime());
            data.setTotalTime(0L);
            mongoTemplate.insert(data);
        } else {
            //更新 count++ 时间last_submit - first_submit
            Update update = new Update().set("count", data.getCount() + 1).set("total_time", submitTime.getTime() - data.getFirstTime());
            mongoTemplate.updateFirst(query, update, SubmitCountData.class);
        }
    }

    public Long getCount(Long examId, String userId) {
        Query query = QueryUtil.queryByField("count");
        query.addCriteria(Criteria.where("exam_id").is(examId).and("user_id").is(userId));
        SubmitCountData data = mongoTemplate.findOne(query, SubmitCountData.class);
        if (data == null)
            return 0L;
        return data.getCount();
    }
}
