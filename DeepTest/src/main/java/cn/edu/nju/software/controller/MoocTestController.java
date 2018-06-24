package cn.edu.nju.software.controller;

import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dto.UserDto;
import cn.edu.nju.software.service.MoocTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("mooctest")
public class MoocTestController {

    @Autowired
    private MoocTestService moocTestService;

    @GetMapping("/enter/exam")
    public void redirectToExam(@RequestParam("task_id") String taskId, @RequestParam(value = "case_id", required = false) String caseId,
                               @RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO  同前端商议url
        // 实现跳转到第三方的链接
        // 主要是获取到taskId 根据数据库获取到exam的信息 (mongodb)
        // 获取到authrization_code 可以通过这个数据获取session_ticket
        // 从而获取到用户的数据
        HttpSession session = request.getSession();
        session.setAttribute("authrization_code", code);
        response.sendRedirect("做题的url");
    }

    @GetMapping("/user")
    public Result getUserDto() {
        UserDto dto = moocTestService.getUser();
        return Result.success().withData(dto).message("获取用户信息成功");
    }

    @PostMapping("/assignTask")
    public Result assignTask() {
        moocTestService.assignTask();
        return Result.success().message("提交考试成绩成功!");
    }
}
