package cn.edu.nju.software.service.score;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 选择样本提交杀死变异的考试方案的计分规则
 */
@Component("calScoreForSelectSample")
public class CalScoreForSelectSample implements CalScoreStrategy {
    @Override
    public Double calScore(int killNum, int toltalNum, int count, List<Double> mesScore) {
        // killNum/totalNum + 1/count
        // 可以通过count不一样 区分出相通killNum的问题
        if (toltalNum == 0) {
            return 0.0;
        }
        Double score = killNum * 1.0 / toltalNum * 100;
        if (count != 0) {
            score += (1.0 / count);
        }
        return score;
    }
}
