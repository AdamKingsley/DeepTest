package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ExamData;
import cn.edu.nju.software.data.ExamScoreData;
import cn.edu.nju.software.data.MseScoreData;
import cn.edu.nju.software.service.score.ScoreStrategyContext;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import sun.nio.cs.US_ASCII;

import java.util.List;

@Repository
public class ExamScoreDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ExamDao examDao;
    @Autowired
    private SubmitCountDao submitCountDao;


    //获取某用户某考试中杀死的变异体数量
    public List<Long> getKilledModelIds(Long examId, String userId) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("user_id").is(userId));
        ExamScoreData data = mongoTemplate.findOne(query, ExamScoreData.class);

        List<Long> killedModelIds = Lists.newArrayList();
        if (data == null || data.getKilledModelIds() == null) {
            killedModelIds = Lists.newArrayList();
        } else {
            killedModelIds = data.getKilledModelIds();
        }
        return killedModelIds;
    }

    public ExamScoreData getExamScore(Long examId, String userId) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("user_id").is(userId));
        ExamScoreData data = mongoTemplate.findOne(query, ExamScoreData.class);
        return data;
    }

    // 更新用户成绩信息
    public Double updateScore(ExamScoreData data) {
        Query query = new Query(Criteria.where("exam_id").is(data.getExamId()).and("user_id").is(data.getUserId()));
        //ExamScoreData data = mongoTemplate.findOne(query, ExamScoreData.class);
        Update update = new Update();
        update.set("exam_id", data.getExamId());
        update.set("user_id", data.getUserId());
        update.set("killed_model_ids", data.getKilledModelIds());
        update.set("score", data.getScore());
        mongoTemplate.upsert(query, update, ExamScoreData.class);
        return data.getScore();
    }

    public Double updateScore(Long examId, String userId, List<Long> killModelId, Double score) {
        ExamScoreData data = new ExamScoreData();
        data.setKilledModelIds(killModelId);
        data.setUserId(userId);
        data.setExamId(examId);
        data.setScore(score);
        return this.updateScore(data);
    }

    public Double updateScore(Long examId, String userId, List<Long> killModelId, Double score, List<MseScoreData> mseScoreData) {
        ExamScoreData data = new ExamScoreData();
        data.setKilledModelIds(killModelId);
        data.setUserId(userId);
        data.setExamId(examId);
        data.setScore(score);
        data.setKilledDetail(mseScoreData);
        return this.updateScore(data);
    }

    public Double updateScore(Long examId, String userId, Double score, List<MseScoreData> mseDatas) {
        List<Long> killedModelIds = Lists.newArrayList();
        mseDatas.forEach(mseScoreData -> killedModelIds.add(mseScoreData.getModelId()));
        return this.updateScore(examId, userId, killedModelIds, score, mseDatas);
    }
}
