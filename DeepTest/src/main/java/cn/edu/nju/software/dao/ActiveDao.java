package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ActiveData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mengf on 2018/6/1 0001.
 */
@Repository
public class ActiveDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String BASIC_COLLECTION_NAME = "del_neuron_%d_%d";

    public void save(ActiveData data, int layer, int neuron_index) {
        mongoTemplate.insert(data, String.format(BASIC_COLLECTION_NAME, layer, neuron_index));
    }

    public void saveBatch(Iterable<ActiveData> datas, int layer, int neuron_index) {
        mongoTemplate.insert(datas, String.format(BASIC_COLLECTION_NAME, layer, neuron_index));
    }

    public List<ActiveData> findAll(int layer, int neuron_index) {
        List<ActiveData> datas = mongoTemplate.findAll(ActiveData.class, String.format(BASIC_COLLECTION_NAME, layer, neuron_index));
        return datas;
    }


    public ActiveData findDataInMutation(Long imageId, String dataCollection, Boolean output) {
        Query query = new Query(Criteria.where("_id").is(imageId));
        ActiveData data = mongoTemplate.findOne(query, ActiveData.class, dataCollection);
        return data;
    }

    public ActiveData findDataInStandard(Long imageId, Boolean output) {
        return findDataInMutation(imageId, "standard_model", output);
    }
}
