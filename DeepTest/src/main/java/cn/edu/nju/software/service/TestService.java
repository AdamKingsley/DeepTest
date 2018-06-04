package cn.edu.nju.software.service;

import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.dto.ActiveDto;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by mengf on 2018/6/3 0003.
 */
@Service
public class TestService {
    @Autowired
    private MongoTemplate template;


    public List<ActiveDto> getSamples() {
        long image_id = 0;
        List<ActiveDto> datas = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            ActiveDto dto = new ActiveDto();
            image_id = i * 20;
            System.out.println("the "+i + " round");
            Query query = new Query(Criteria.where("_id").is(image_id));
            ActiveData standard = template.findOne(query, ActiveData.class,"standard_model");
            ActiveData mutation = template.findOne(query,ActiveData.class,"del_neuron_1_0_model");
            dto.setImageId(image_id);
            dto.setStandard(standard);
            dto.setMutation(mutation);
            datas.add(dto);
        }
        File file = new File("compare.json");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(JSON.toJSONString(datas));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datas;
    }
}
