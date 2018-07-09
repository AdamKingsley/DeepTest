package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.CaseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

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
}
