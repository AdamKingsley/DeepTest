package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ImageData;
import com.google.common.collect.Lists;
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
public class ImageDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ImageData> findByIds(List<Long> ids) {
        Query query = new Query(Criteria.where("_id").in(ids));
        return mongoTemplate.find(query, ImageData.class);
    }

    //提取所有的id形成集合
    private List<Long> getImageIds(List<ImageData> imageDatas) {
        List<Long> ids = Lists.newArrayList();
        if (imageDatas != null)
            imageDatas.forEach(imageData -> ids.add(imageData.getId()));
        return ids;
    }


    public ImageData findById(Long id) {
        Query query = new Query(Criteria.where("_id").in(id));
        return mongoTemplate.findOne(query, ImageData.class);
    }
}
