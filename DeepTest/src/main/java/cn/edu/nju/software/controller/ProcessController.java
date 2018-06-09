package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dto.ActiveDto;
import cn.edu.nju.software.dto.ImageDto;
import cn.edu.nju.software.dto.SubmitDto;
import cn.edu.nju.software.service.DataService;
import cn.edu.nju.software.service.OperationService;
import cn.edu.nju.software.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@RestController
@RequestMapping("process")
public class ProcessController {
    @Autowired
    private DataService dataService;

    @Autowired
    private OperationService operationService;

    //TODO 这个controller的URL 我写的自己混乱了…… 跟老司机商量一下
    //USER id 前端传过来？还是后端直接取到？

    /**
     * 用户提交信息数据
     *
     * @param command
     * @param request
     * @return
     */
    @PostMapping("submit")
    public Result submit(@RequestBody SubmitCommand command, HttpServletRequest request) {
        //提交需要反馈用户这是第几次提交，提交次数可能会作为最后评价的一个标准
        SubmitDto dto = dataService.submit(command);
        return Result.success().message("提交数据成功").withData(dto);
    }

    /**
     * 筛选图片数据【图片标签，在某模型中激活神经元】
     * @param filterCommand
     * @param request
     * @return
     */
    @PostMapping("filter")
    public Result filter(@RequestBody FilterCommand filterCommand, HttpServletRequest request) {
        List<ImageDto> dtos = dataService.filter(filterCommand);
        return Result.success().message("筛选图片成功！").withData(dtos);
    }

    /**
     * output表示是否显示对应数据的输出层
     * TODO 暂时output无用处 没能找到合适的删除最后一层数据的方案
     *
     * @param output
     * @param request
     * @return
     */
    @GetMapping("active-data")
    public Result getActiveData(@RequestParam("imageId") Long imageId, @RequestParam("modelId") Long modelId,
                                @RequestParam(value = "output", required = false) Boolean output, HttpServletRequest request) {
        output = output == null ? false : output;
        ActiveDto dto = dataService.getActivaData(imageId, modelId, output);
        //存储操作记录
        operationService.saveOperation(imageId, modelId, UserUtil.getUserId());
        return Result.success().message("获取激活信息成功").withData(dto);
    }


}
