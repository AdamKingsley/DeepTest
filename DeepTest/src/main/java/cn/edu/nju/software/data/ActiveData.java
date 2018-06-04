package cn.edu.nju.software.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by mengf on 2018/5/29 0029.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveData {
    @Id
    private Long id;

    private Integer predict;

    private Integer label;

    @Field("activation_data")
    @JSONField(name = "activation_data")
    private Double[][] activationData;
}
