package cn.edu.nju.software.controller;

import cn.edu.nju.software.command.ExamCommand;
import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dto.ExamImageDto;
import cn.edu.nju.software.dto.ExamScoreDto;
import cn.edu.nju.software.service.CommonService;
import cn.edu.nju.software.service.ExamService;
import org.apache.commons.lang.StringUtils;
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
    @Autowired
    private CommonService commonService;

    @PostMapping
    public Result create(@RequestBody ExamCommand command, HttpServletRequest request) {
        examService.create(command);
        return Result.success().message("创建考试成功!");
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
     * 获取考试成绩
     *
     * @param examId
     * @param userId
     * @return
     */
    @GetMapping("/score")
    public Result getScore(@RequestParam("examId") Long examId, @RequestParam(value = "userId", required = false) String userId) {
        userId = StringUtils.isEmpty(userId) ? commonService.getUserId() : userId;
        ExamScoreDto examScoreDto = examService.getScore(examId, userId);
        return Result.success().message("获取考试成绩成功!").withData(examScoreDto);
    }

}
