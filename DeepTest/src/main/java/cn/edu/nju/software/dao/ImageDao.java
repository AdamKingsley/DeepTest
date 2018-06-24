package cn.edu.nju.software.dao;

import cn.edu.nju.software.command.ActiveCommand;
import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.dto.ImageDto;
import cn.edu.nju.software.util.QueryUtil;
import com.google.common.collect.Lists;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

/**
 * Created by mengf on 2018/6/1 0001.
 */
@Repository
public class ImageDao {
    @Autowired
    private MongoTemplate mongoTemplate;

//    public List<ImageData> findByActiveData(Long examId, int layer, int neuron_index, int model_layer, int model_neuron_index) {
//        // 通过考试 找出考试出题的图片id
//        // 根据选择的神经元信息来查询 所选图图片中激活了 对应的位置在对应模型中激活了的信息
//        // 根据图片id们 返回对应的imageData列表
//        return Lists.newArrayList();
//    }

    public List<ImageData> findByIds(List<Long> ids) {
        Query query = new Query(Criteria.where("_id").in(ids));
        return mongoTemplate.find(query, ImageData.class);
    }

    //TODO 格外及其需要重构
    public List<ImageDto> filter(List<Long> imageIds, List<Object> tags, List<ActiveCommand> active_locations) {
        //返回的数据
        List<ImageDto> images = Lists.newArrayList();

        Criteria criteria = Criteria.where("_id").in(imageIds);
        //只获取image_id
        Query query = QueryUtil.queryOnlyId();
        //根据标签筛选图片
        if (tags != null && tags.size() != 0) {
            for (Object tag : tags) {
                criteria.and("tags.value").is(tag);
            }
            query.addCriteria(criteria);
            imageIds = getImageIds(mongoTemplate.find(query, ImageData.class));
        }
        //根据激活数据进行筛选图片
        if (active_locations != null && active_locations.size() != 0) {
            for (ActiveCommand command : active_locations) {
                //都已经没有图片了就不要浪费时间筛选了
                if (imageIds.size() == 0) {
                    break;
                }
                query = QueryUtil.queryOnlyId();
                criteria = Criteria.where("_id").in(imageIds)
                        .and("activation_data." + (command.getLayer() - 1) + "." + command.getNeuronIndex());
                criteria = command.getIsActive() ? criteria.gt(0) : criteria.is(0);
                query.addCriteria(criteria);
                imageIds = getImageIdsFromActiveData(mongoTemplate.find(query, ActiveData.class, command.getCollection()));
            }
        }
        //将对应的图片数据获取出来
        query = new Query(Criteria.where("_id").in(imageIds));
        List<ImageData> datas = mongoTemplate.find(query, ImageData.class);
        datas.forEach(image -> {
            ImageDto dto = new ImageDto();
            BeanUtils.copyProperties(image, dto);
            images.add(dto);
        });
        return images;
    }

    //提取所有的id形成集合
    private List<Long> getImageIds(List<ImageData> imageDatas) {
        List<Long> ids = Lists.newArrayList();
        if (imageDatas != null)
            imageDatas.forEach(imageData -> ids.add(imageData.getId()));
        return ids;
    }

    //从激活数据中,将对应的id即图片id进行数据的整合
    private List<Long> getImageIdsFromActiveData(List<ActiveData> activeDatas) {
        List<Long> ids = Lists.newArrayList();
        if (activeDatas != null)
            activeDatas.forEach(activeData -> ids.add(activeData.getId()));
        return ids;
    }

    public ImageData findById(Long id) {
        Query query = new Query(Criteria.where("_id").in(id));
        return mongoTemplate.findOne(query, ImageData.class);
    }
}
