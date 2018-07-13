package cn.edu.nju.software.service.score;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 选择样本进行绘制以及数据扩增方案的考试的计分规则
 */
@Component("calScoreForPaintSample")
public class CalScoreForPaintSample implements CalScoreStrategy {

    //该评分方式 杀死变异体数量的评分比例只占20%
    //对应图片的杀死变异的扰动情况 占80%
    private static final Double KILL_PERCENT = 0.2;
    private static final Double NOISE_PERCENT = 0.8;

    @Override
    public Double calScore(int killNum, int toltalNum, int count, List<Double> mseScores) {
        // killNum/totalNum + 1/count
        // 可以通过count不一样 区分出相通killNum的问题
        if (toltalNum == 0 || mseScores.size() == 0 || mseScores == null) {
            return 0.0;
        }
        Double score = killNum * 1.0 / toltalNum * KILL_PERCENT * 100;

        Double temp = 0.0;
        for (Double mseScore : mseScores)
            temp += mseScore;
        //加上扰动程度的分数
        score += (killNum * 1.0 / toltalNum) * (temp * 1.0 / mseScores.size()) * NOISE_PERCENT;
        if (count != 0) {
            score += (1.0 / count);
        }
        return score;
    }
}
