package cn.edu.nju.software.controller;

import cn.edu.nju.software.common.result.Result;
import cn.edu.nju.software.dto.CaseDto;
import cn.edu.nju.software.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("case")
public class CaseController {
    @Autowired
    private CaseService caseService;

    /**
     * 根据examId和caseId
     * 获取本道题目的详细情况
     *
     * @param examId
     * @param caseId
     * @param request
     * @return
     */
    @GetMapping
    public Result getCaseDto(@RequestParam("examId") Long examId, @RequestParam("caseId") String caseId, HttpServletRequest request) {
        CaseDto caseDto = caseService.getCaseDto(examId, caseId);
        return Result.success().withData(caseDto).message("获取case详情成功！");
    }

    @GetMapping("/list")
    public Result getExamCases(@RequestParam("examId") Long examId, HttpServletRequest request) {
        List<CaseDto> caseDtos = caseService.getCaseDtos(examId);
        return Result.success().withData(caseDtos).message("获取考试的cases成功!");
    }

}
