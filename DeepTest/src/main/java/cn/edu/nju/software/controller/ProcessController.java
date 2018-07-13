package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.PaintCommand;
import cn.edu.nju.software.command.python.ImageDataCommand;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dto.*;
import cn.edu.nju.software.service.CommonService;
import cn.edu.nju.software.service.DataService;
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
    private CommonService commonService;

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

}
