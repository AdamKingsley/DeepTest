package cn.edu.nju.software.dao;

import cn.edu.nju.software.data.ImageData;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mengf on 2018/6/1 0001.
 */
@Repository
public class ImageDataDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ImageData> findByActiveData(Long examId, int layer, int neuron_index, int model_layer, int model_neuron_index) {
        // 通过考试 找出考试出题的图片id
        // 根据选择的神经元信息来查询 所选图图片中激活了 对应的位置在对应模型中激活了的信息
        // 根据图片id们 返回对应的imageData列表
        return Lists.newArrayList();
    }
}
