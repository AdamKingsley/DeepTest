package cn.edu.nju.software.service.score;

import java.util.List;

public interface CalScoreStrategy {

    /**
     * 计算成绩
     *
     * @param killNum   杀死的模型的数量
     * @param toltalNum 总的模型的数量
     * @param count     提交的次数
     * @param mesScore  第二种扰动的考试方案中的计算分数的mse值
     * @return 成绩
     */
    Double calScore(int killNum, int toltalNum, int count, List<Double> mesScore);
}
