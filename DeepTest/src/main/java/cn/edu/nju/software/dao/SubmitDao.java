package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.SubmitData;
import cn.edu.nju.software.util.QueryUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by mengf on 2018/6/8 0008.
 */
@Repository
public class SubmitDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public Long getSubmitTimes(Long examId, Long userId) {
        SubmitData example = new SubmitData();
        example.setExamId(examId);
        example.setUserId(userId);
        Query query = new Query(Criteria.byExample(example));
        return mongoTemplate.count(query, SubmitData.class);
    }

    public List<Long> getSubmitImageIds(Long examId, Long userId) {
        Query query = QueryUtil.queryByField("image_ids");
        SubmitData example = new SubmitData();
        example.setExamId(examId);
        example.setUserId(userId);
        query.addCriteria(Criteria.byExample(example));
        //按照提交时间升序排序
        query.with(new Sort(Sort.Direction.ASC, "submit_time"));
        List<SubmitData> datas = mongoTemplate.find(query, SubmitData.class);
        List<Long> ids = Lists.newArrayList();
        //合并去重
        for (SubmitData data : datas) {
            if (data.getImageIds() != null && data.getImageIds().size() > 0) {
                //ids.addAll(data.getImageIds());
                for (Long id : data.getImageIds()) {
                    if (!ids.contains(id)) {
                        ids.add(id);
                    }
                }
            }
        }
        return ids;
    }

    public List<Long> getKilledModelIds(Long examId, Long userId) {
        Query query = QueryUtil.queryByField("kill_model_ids");
        SubmitData example = new SubmitData();
        example.setExamId(examId);
        example.setUserId(userId);
        query.addCriteria(Criteria.byExample(example));
        List<SubmitData> datas = mongoTemplate.find(query, SubmitData.class);
        //合并去重
        Set<Long> ids = Sets.newHashSet();
        for (SubmitData data : datas) {
            if (data.getKillModelId() != null && data.getImageIds().size() > 0) {
                ids.addAll(data.getKillModelId());
            }
        }
        List<Long> result = Lists.newArrayList();
        result.addAll(ids);
        return result;
    }

    public void insert(SubmitData submitData) {
        mongoTemplate.insert(submitData);
    }


    public void save(SubmitData submitData) {
        mongoTemplate.save(submitData);
    }
}
