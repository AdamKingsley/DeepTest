package cn.edu.nju.software.controller;

import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mengf on 2018/5/29 0029.
 */

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TestService service;

    @GetMapping("hello")
    public Result hello() {
        return Result.success().message("hello world!");
    }

    @GetMapping("world")
    public Result another() {
        return Result.success().message("another hello world!");
    }

    @GetMapping("what")
    public Result what() {
        return Result.success().message("what are you talking about!");
    }

    @GetMapping("getData")
    public Result getData() {
        Query query = new Query(Criteria.where("_id").is(1));
        List<ActiveData> list = mongoTemplate.find(query, ActiveData.class, "del_neuron_1_0_model");
        return Result.success().withData(list);
    }

    @GetMapping("getSamples")
    public Result getSamples() {
        return Result.success().withData(service.getSamples()).message("获取数据成功");
    }


    @GetMapping("insertImages")
    public Result insertImages() {
        service.loadImageDataFromJson();
        return Result.success().message("预准备插入图像相关数据成功！");
    }

    @GetMapping("insertModels")
    public Result insertModels() {
        service.insertModelData();
        return Result.success().message("预准备插入变异相关数据成功！");
    }

    @GetMapping("resave_active_data")
    public Result resave() {
        service.resave_avtive_data();
        return Result.success().message("将int32转为64成功~");
    }

}
