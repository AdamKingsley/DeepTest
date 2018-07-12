package cn.edu.nju.software.controller;

import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dto.ExamDto;
import cn.edu.nju.software.dto.UserDto;
import cn.edu.nju.software.service.ExamService;
import cn.edu.nju.software.service.MoocTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("mooctest")
@Slf4j
public class MoocTestController {

    @Autowired
    private MoocTestService moocTestService;
    @Autowired
    private ExamService examService;

    @GetMapping("/exam")
    public Result redirectToExam(@RequestParam("task_id") String taskId, @RequestParam(value = "case_id", required = false) String caseId,
                                 @RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
        //前端接收url请求后，将task_id和code [一般没有case id]发送给后端查询考试id
        //修改为需要添加考试的case_id，考试仅返回考试
        HttpSession session = request.getSession();
        session.setAttribute("authrization_code", code);
        log.info("the authrization code is {}", code);
        ExamDto examDto = examService.findByTaskId(taskId);
        return Result.success().message("根据mooctest task id 获取考试id成功!").withData(examDto);
    }

    @GetMapping("/user")
    public Result getUserDto() {
        UserDto dto = moocTestService.getUser();
        return Result.success().withData(dto).message("获取用户信息成功");
    }

    @PostMapping("/assignTask")
    public Result assignTask() {
        //TODO delete this method, cause assignTask was in the service method
        //moocTestService.assignTask();
        return Result.success().message("提交考试成绩成功!");
    }
}
