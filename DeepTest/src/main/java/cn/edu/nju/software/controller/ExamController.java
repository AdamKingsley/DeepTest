package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.ExamCommand;
import cn.edu.nju.software.common.result.PageResult;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dao.ExamDao;
import cn.edu.nju.software.dto.ExamDto;
import cn.edu.nju.software.dto.ExamImageDto;
import cn.edu.nju.software.dto.ImageDto;
import cn.edu.nju.software.dto.ModelDto;
import cn.edu.nju.software.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    //TODO 下面查询考试具体详情的信息 是否需要传入User_Id  这样可以找到用户选择了的图片集
    //TODO 即用户是否登录的请求是后端还是前端在维持？ 后端需要知道user是否登录？

    /**
     * 包含题库信息|图片信息
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}")
    public Result queryExamDetail(@PathVariable Long id, HttpServletRequest request) {
        ExamDto dto = examService.getExamDetail(id);
        return Result.success().message("获取对应考试详细信息成功！").withData(dto);
    }

    /**
     * 获取题库对应的变异模型信息
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/models")
    public Result queryExamModels(@PathVariable Long id, HttpServletRequest request) {
        List<ModelDto> dtos = examService.getExamModels(id);
        return Result.success().message("获取对应考试的变异模型体信息成功！").withData(dtos);
    }

    /**
     * 获取题库对应的样本集信息
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/samples")
    public Result queryExamImage(@PathVariable Long id, HttpServletRequest request) {
        ExamImageDto dtos = examService.getExamImages(id);
        return Result.success().message("获取对应考试的样本集合信息成功！").withData(dtos);
    }

    /**
     * //TODO 需要可添加
     * 只是简单将ExamData对应的数据提取出来
     * 可能不会被用到
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("{id}/simple")
    public Result queryExam(@PathVariable Long id, HttpServletRequest request) {
        return Result.success().message("获取对应id的考试成功~");
    }

    @GetMapping("{id}/submit/count")
    public Result querySubmitCount(@PathVariable Long id, HttpServletRequest request) {
        Long times = examService.getSubmitCount(id);
        return Result.success().message("获取考试提交次数成功！").withData(times);
    }

    @GetMapping("{id}/killedIds")
    public Result queryKilledIds(@PathVariable Long id, HttpServletRequest request) {
        List<Long> killedIds = examService.getKilledIds(id);
        return Result.success().message("获取考试杀死变异体id集合成功！");
    }

    /**
     * //TODO
     * 根据筛选条件进行筛选考试
     * 分页显示
     * 目前没有需求，可之后再添加
     *
     * @param request
     * @return
     */
    @GetMapping
    public PageResult queryBySample(HttpServletRequest request) {
        return PageResult.error("该接口还没实现好，请通知程序猿同志继续努力~");
    }

    /**
     * //TODO 待实现 目前还没有提供考试创建的界面
     * 修改考试信息
     *
     * @param command
     * @param request
     * @return
     */
    @PutMapping
    public Result modifyExam(@RequestBody ExamCommand command, HttpServletRequest request) {
        return Result.success().message("程序猿小哥哥要加油哦~");
    }
}
