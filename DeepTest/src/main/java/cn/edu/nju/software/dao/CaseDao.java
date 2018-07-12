package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.CaseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CaseDao {
    @Autowired
    private MongoTemplate template;

    /**
     * 查询某道题目的详情
     *
     * @param examId
     * @param caseId
     * @return
     */
    public CaseData getCaseData(Long examId, String caseId) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("case_id").is(caseId));
        CaseData caseData = template.findOne(query, CaseData.class);
        return caseData;
    }

    /**
     * 查询某次考试的所有题目
     *
     * @param examId
     * @return
     */
    public List<CaseData> getCaseDatas(Long examId) {
        Query query = new Query(Criteria.where("exam_id").is(examId));
        List<CaseData> caseDatas = template.find(query, CaseData.class);
        return caseDatas;
    }

    /**
     * 更新某道题目的最后修改，
     * 以后进入的时候方便用户直接看到上次修改
     *
     * @param examId
     * @param caseId
     * @param composePath
     */
    public void updateLastComposePath(Long examId, String caseId, String composePath) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("case_id").is(caseId));
        Update update = Update.update("compose_path", composePath);
        template.updateFirst(query, update, CaseData.class);
    }

    public void updateCaseData(Long examId, String caseId, String composePath, String max_composePath, Double score, Boolean isKilled) {
//        @Field("compose_path")
//        private String composePath;
//        //得分最高的合成图的path
//        @Field("max_compose_path")
//        private String maxComposePath;
//        private Double score;
//        //是否杀死变异模型
//        @Field("is_killed")
//        private Boolean isKilled;
        Query query = new Query(Criteria.where("exam_id").is(examId).and("case_id").is(caseId));
        Update update = new Update();
        update.set("compose_path", composePath).set("score", score).set("is_killed", isKilled).set("max_compose_path", max_composePath);
        template.updateFirst(query, update, CaseData.class);

    }


    public void insertMany(List<CaseData> datas) {
        template.insert(datas, CaseData.class);
    }


}
