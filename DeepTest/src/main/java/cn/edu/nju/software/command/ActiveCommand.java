package cn.edu.nju.software.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by mengf on 2018/6/9 0009.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveCommand {
    //第几层 从1开始 ！！注意哦~ 统一一下
    private Integer layer;
    //第几个神经元 从0开始
    private Integer neuronIndex;
    //是否被激活
    private Boolean isActive;
    //模型所在集合的名称
    private String collection;
}
