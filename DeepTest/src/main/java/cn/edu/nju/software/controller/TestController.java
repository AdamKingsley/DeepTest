package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.python.ImageCommand;
import cn.edu.nju.software.command.python.ModelCommand;
import cn.edu.nju.software.command.python.PaintSubmitCommand;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.data.ActiveData;
import cn.edu.nju.software.dto.PaintSubmitDto;
import cn.edu.nju.software.service.MoocTestService;
import cn.edu.nju.software.service.TestService;
import cn.edu.nju.software.service.feign.PythonFeign;
import com.google.common.collect.Lists;
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
//@EnableConfigurationProperties({MoocTestConfig.class})
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private TestService service;
    @Autowired
    private MoocTestService moocTestService;
    @Autowired
    private PythonFeign pythonFeign;
//    @Autowired
//    private MoocTestConfig moocTestConfig;

//    @GetMapping("secretKey")
//    public String get(){
//        return moocTestConfig.getSecretKey();
//    }

    @GetMapping("python")
    public Result testPythonFeign() {
        PaintSubmitCommand command = new PaintSubmitCommand();
        ImageCommand imageCommand = new ImageCommand();
        List<ModelCommand> modelCommands = Lists.newArrayList();
        ModelCommand modelCommand = new ModelCommand();
        modelCommand.setId(0L);
        modelCommand.setPath("del_neuron_model_1_0.hdf5");
        modelCommands.add(modelCommand);
        imageCommand.setId(525L);
        imageCommand.setPath("mnist_test/0/mnist_test_525.png");
        command.setExamId(3L);
        command.setUserId("13232323");
        command.setAdversialStr("data:image/png;base64,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");
        command.setStandardModelPath("standard_model.hdf5");
        command.setImage(imageCommand);
        command.setMutaionModels(modelCommands);
        List<PaintSubmitDto> dtos = pythonFeign.paintSubmit(command);
        return Result.success().message("调用成功!").withData(dtos);
    }

    @GetMapping("secretKey")
    public String get() {
        return moocTestService.accessToken();
    }


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
