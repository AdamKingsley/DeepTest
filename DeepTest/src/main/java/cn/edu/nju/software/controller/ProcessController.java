package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.FilterCommand;
import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.SubmitCommand;
import cn.edu.nju.software.command.python.ImageDataCommand;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.data.ImageData;
import cn.edu.nju.software.dto.*;
import cn.edu.nju.software.service.CommonService;
import cn.edu.nju.software.service.DataService;
import cn.edu.nju.software.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

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
    private CommonService commonService;
    @Autowired
    private OperationService operationService;

    //TODO 这个controller的URL 我写的自己混乱了…… 跟老司机商量一下
    //USER id 前端传过来？还是后端直接取到？

    /**
     * 选择图片类型的考试的提交接口
     * 用户提交信息数据
     *
     * @param command
     * @param request
     * @return
     */
    @PostMapping("submit")
    public Result submit(@RequestBody SubmitCommand command, HttpServletRequest request) {
        //提交需要反馈用户这是第几次提交，提交次数可能会作为最后评价的一个标准
        if (command.getUserId() == null) {
            command.setUserId(commonService.getUserId());
        }
        SubmitDto dto = dataService.submit(command);
        //注意，仅返回本次提交的样本杀死的变异体的数量和Ids 前端拿到数据后自己并集添加对应的杀死的id
        // 后端只是第一次获取exam的时候将当下所有变异体杀死情况+已经进行了多少次提交反馈给前端
        // 后面的提交工作只是提交一个反馈一个
        return Result.success().message("提交数据成功").withData(dto);
    }


    /**
     * 绘制图片adversial的考试类型的提交接口
     *
     * @param paintCommand
     * @param request
     * @return
     */
    @PostMapping("/paint/submit")
    public Result submitPaintData(@RequestBody PaintCommand paintCommand, HttpServletRequest request) {
        if (paintCommand.getUserId() == null) {
            paintCommand.setUserId(commonService.getUserId());
        }
        List<PaintSubmitDto> dtos = dataService.submit(paintCommand);
        return Result.success().message("提交数据成功").withData(dtos);
    }

    /**
     * 图片变瘦
     *
     * @param command
     * @return
     */
    @PostMapping("/image/thin")
    public Result pocessThin(@RequestBody ImageDataCommand command) {
        ImageDataDto dto = dataService.processThin(command);
        return Result.success().message("处理图像变瘦成功!").withData(dto);
    }

    /**
     * 图片变胖
     *
     * @param command
     * @return
     */
    @PostMapping("/image/fat")
    public Result processFat(@RequestBody ImageDataCommand command) {
        ImageDataDto dto = dataService.processFat(command);
        return Result.success().message("处理图像变胖成功!").withData(dto);
    }

    /**
     * 筛选图片数据【图片标签，在某模型中激活神经元】
     *
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
        operationService.saveOperation(imageId, modelId, commonService.getUserId());
        return Result.success().message("获取激活信息成功").withData(dto);
    }

}
