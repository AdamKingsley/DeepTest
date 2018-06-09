package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.dto.ModelDto;
import cn.edu.nju.software.util.QueryUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mengf on 2018/6/8 0008.
 */
@Repository
@Slf4j
public class ModelDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据ID获取对应的变异模型
     *
     * @param id
     * @return
     */
    public MutationData getById(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, MutationData.class);
    }

    public List<MutationData> getModelByIds(List<Long> ids) {
        Query query = new Query(Criteria.where("_id").in(ids));
        return mongoTemplate.find(query, MutationData.class);
    }

    public List<Long> getKilledIds(List<Long> imageIds, List<MutationData> collections) {
        List<Long> killedIds = Lists.newArrayList();
        Query query = QueryUtil.queryByField("predict");
        query.addCriteria(Criteria.where("_id").in(imageIds));
        query.with(new Sort(Sort.Direction.ASC, "_id"));
        List<ActiveData> standard = mongoTemplate.find(query, ActiveData.class, "standard_model");
        for (MutationData mutation : collections) {
            List<ActiveData> mutationData = mongoTemplate.find(query, ActiveData.class, mutation.getDataCollection());
            log.info(mutation.getDataCollection());
            Boolean isKilled = false;
            if (standard.size() == mutationData.size()) {
                for (int i = 0; i < standard.size(); i++) {
                    //标准模型和原始模型不一样的话就说明杀掉了
                    if (!standard.get(i).getPredict().equals(mutationData.get(i).getPredict())) {
                        isKilled = true;
                        break;
                    }
                }
            }
            if (isKilled) {
                killedIds.add(mutation.getId());
            }
        }
        return killedIds;
    }
}
