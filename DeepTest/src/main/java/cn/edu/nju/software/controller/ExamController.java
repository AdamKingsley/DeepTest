package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.ExamCommand;
import cn.edu.nju.software.common.result.PageResult;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@RestController
@RequestMapping("exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    //TODO URL 风格需选定 目前是我自己理解的RESTFUL风格，如有不对欢迎指正，我立马修改
    //@PostMapping("create")
    @PostMapping
    public Result create(@RequestBody ExamCommand command, HttpServletRequest request) {
        examService.create(command);
        return Result.success().message("创建考试成功!");
    }

    /**
     * 包含题库信息|图片信息
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/detail")
    public Result queryExamDetail(@PathVariable Long id, HttpServletRequest request) {
        return Result.success().message("获取对应id的考试成功~");
    }

    /**
     * 只是简单将ExamData对应的数据提取出来
     * 可能不会被用到
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/simple")
    public Result queryExam(@PathVariable Long id, HttpServletRequest request) {
        return Result.success().message("获取对应id的考试成功~");
    }

    /**
     * 获取对应考试id下面对应的models
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/models")
    public Result queryExamModels(@PathVariable Long id, HttpServletRequest request) {
        return Result.success().message("获取对应id的考试成功~");
    }
    /**
     * 获取对应考试id下面对应的samples(目前是图片)
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/samples")
    public Result queryExamSamples(@PathVariable Long id, HttpServletRequest request) {
        return Result.success().message("获取对应id的考试成功~");
    }
    /**
     * //TODO
     * 根据筛选条件进行筛选考试
     * 分页显示
     * 目前没有需求，可之后再添加
     * @param request
     * @return
     */
    @GetMapping
    public PageResult queryBySample(HttpServletRequest request) {
        return PageResult.error("该接口还没实现好，请通知程序猿同志继续努力~");
    }

    /**
     * 修改考试信息
     * @param command
     * @param request
     * @return
     */
    @PutMapping
    public Result modifyExam(@RequestBody ExamCommand command, HttpServletRequest request) {
        return Result.success().message("程序猿小哥哥要加油哦~");
    }
}
