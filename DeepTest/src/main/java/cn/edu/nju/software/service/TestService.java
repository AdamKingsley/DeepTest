package cn.edu.nju.software.service;

import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.data.Tag;
import cn.edu.nju.software.data.mutation.DelModelData;
import cn.edu.nju.software.data.mutation.MutationData;
import cn.edu.nju.software.data.mutation.NeuronData;
import cn.edu.nju.software.dto.ActiveDto;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.thoughtworks.proxy.toys.delegate.Delegating;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.*;

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
            System.out.println("the " + i + " round");
            Query query = new Query(Criteria.where("_id").is(image_id));
            ActiveData standard = template.findOne(query, ActiveData.class, "standard_model");
            ActiveData mutation = template.findOne(query, ActiveData.class, "del_neuron_1_0_model");
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

    // 预存储image数据
    public void loadImageDataFromJson() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("D:/imagedata.json"));
            String text = IOUtils.toString(inputStream, "utf8");
            JSONArray array = JSON.parseArray(text);
            List<ImageData> datas = Lists.newArrayList();
            for (int i = 0; i < array.size(); i++) {
                System.out.println(i);
                Long id = array.getJSONObject(i).getLong("_id");
                List<Integer> tags = array.getJSONObject(i).getObject("tag", new TypeReference<List<Integer>>() {
                });
                List<Tag> tagList = Lists.newArrayList();
                tags.forEach(num -> tagList.add(new Tag("number", num)));
                ImageData data = array.getObject(i, ImageData.class);
                data.setId(id);
                data.setTags(tagList);
                data.setName(data.getTags().get(0).getValue() + "_" + data.getId());
                datas.add(data);
            }
            //打印样本图片总张数并批量插入
            System.out.println(datas.size());
            template.insertAll(datas);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 预存储 model数据
    public void insertModelData() {
        List<MutationData> datas = Lists.newArrayList();
        long count = 1;
        for (int i = 0; i < 128; i++) {
            List<NeuronData> neuronDatas = Lists.newArrayList();
            neuronDatas.add(new NeuronData(1, i));
            DelModelData data = new DelModelData();
            data.setId(count);
            data.setName("del_neuron_1_" + i);
            data.setDataCollection(data.getName() + "_model");
            data.setType(0);
            data.setDeleteNuerons(neuronDatas);
            data.setPath("del_neuron_model_1_" + i + ".hdf5");
            datas.add(data);
            count++;
        }
        for (int i = 0; i < 64; i++) {
            List<NeuronData> neuronDatas = Lists.newArrayList();
            neuronDatas.add(new NeuronData(2, i));
            DelModelData data = new DelModelData();
            data.setId(count);
            data.setName("del_neuron_2_" + i);
            data.setDataCollection(data.getName() + "_model");
            data.setType(0);
            data.setDeleteNuerons(neuronDatas);
            data.setPath("del_neuron_model_2_" + i + ".hdf5");
            datas.add(data);
            count++;
        }
        System.out.println("共有" + datas.size() + "个变异模型！");
        template.insertAll(datas);
    }

    public void resave_avtive_data() {
        for (int i = 0; i < 128; i++) {
            List<ActiveData> datas = template.findAll(ActiveData.class, "del_neuron_1_" + i + "_model");
        }
        for (int i = 0; i < 64; i++) {

        }
    }
    // 创建考试数据

}
