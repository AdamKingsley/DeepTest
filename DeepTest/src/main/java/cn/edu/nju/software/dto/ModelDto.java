package cn.edu.nju.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mengf on 2018/6/9 0009.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDto {
    private Long id;
    private String name;
    //变异模型的激活数据存储的collection的名称
    private String dataCollection;
    //变异类型 目前就0 表示神经元删除
    private Integer type;
}
