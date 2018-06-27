package cn.edu.nju.software.service.score;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
public class ScoreStrategyContext {
    /**
     * 使用线程安全的ConcurrentHashMap存储所有实现Strategy接口的Bean
     * key:beanName
     * value：实现Strategy接口Bean
     */
    private final Map<String, CalScoreStrategy> strategyMap = Maps.newConcurrentMap();

    /**
     * 注入所有实现了Strategy接口的Bean
     *
     * @param strategyMap
     */
    @Autowired
    public ScoreStrategyContext(Map<String, CalScoreStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach((k, v) -> this.strategyMap.put(k, v));
    }

    /**
     * 计算价格
     *
     * @param scoreType 算分策略
     * @return 分数
     */
    public Double calculateScore(String scoreType, Long killNum, Long totalNum, Long count, List<Double> mse) {
        if (!StringUtils.isEmpty(scoreType)) {
            return strategyMap.get(scoreType).calScore(killNum, totalNum, count, mse);
        }
        return 0.0;
    }
}
