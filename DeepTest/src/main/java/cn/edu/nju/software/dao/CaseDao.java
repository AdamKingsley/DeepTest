package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.CaseData;
import cn.edu.nju.software.data.UserCaseData;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    public UserCaseData getUserCaseData(Long examId, String userId, String caseId) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("case_id").is(caseId).and("user_id").is(userId));
        UserCaseData userCaseData = template.findOne(query, UserCaseData.class);
        if (userCaseData == null) {
            //插入所有数据
            insertAllCases(examId, userId);
            userCaseData = template.findOne(query, UserCaseData.class);
        }
        return userCaseData;
    }

    /**
     * 查询某次考试的所有题目
     *
     * @param examId
     * @return
     */
    public List<UserCaseData> getUserCaseDatas(Long examId, String userId) {
        Query query = new Query(Criteria.where("exam_id").is(examId));
        List<UserCaseData> userCaseDatas = template.find(query, UserCaseData.class);
        if (userCaseDatas == null || userCaseDatas.size() == 0) {
            //若该用户的cases信息没有需要插入数据
            insertAllCases(examId, userId);
            userCaseDatas = template.find(query, UserCaseData.class);
        }
        return userCaseDatas;
    }

    private List<CaseData> getCaseDatas(Long examId) {
        Query query = new Query(Criteria.where("exam_id").is(examId));
        List<CaseData> caseDatas = template.find(query, CaseData.class);
        return caseDatas;
    }

    private void insertAllCases(Long examId, String userId) {
        List<CaseData> caseDatas = getCaseDatas(examId);
        List<UserCaseData> userCaseDatas = Lists.newArrayList();
        caseDatas.forEach(caseData -> {
            UserCaseData userCaseData = new UserCaseData();
            BeanUtils.copyProperties(caseData, userCaseData);
            userCaseData.setUserId(userId);
            userCaseDatas.add(userCaseData);
        });
        template.insert(userCaseDatas, UserCaseData.class);
    }

    /**
     * 更新某道题目的最后修改，
     * 以后进入的时候方便用户直接看到上次修改
     *
     * @param examId
     * @param caseId
     * @param composePath
     */
    public void updateLastComposePath(Long examId, String userId, String caseId, String composePath) {
        Query query = new Query(Criteria.where("exam_id").is(examId).and("case_id").is(caseId).and("user_id").is(userId));
        Update update = Update.update("compose_path", composePath);
        template.updateFirst(query, update, UserCaseData.class);
    }

    public void updateCaseData(Long examId, String userId, String caseId, String composePath, String max_composePath, Double score, Boolean isKilled) {
//        @Field("compose_path")
//        private String composePath;
//        //得分最高的合成图的path
//        @Field("max_compose_path")
//        private String maxComposePath;
//        private Double score;
//        //是否杀死变异模型
//        @Field("is_killed")
//        private Boolean isKilled;
        Query query = new Query(Criteria.where("exam_id").is(examId).and("case_id").is(caseId).and("user_id").is(userId));
        Update update = new Update();
        update.set("compose_path", composePath).set("score", score).set("is_killed", isKilled).set("max_compose_path", max_composePath);
        template.updateFirst(query, update, UserCaseData.class);

    }


    public void insertMany(List<CaseData> datas) {
        template.insert(datas, CaseData.class);
    }


}
