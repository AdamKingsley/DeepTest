package cn.edu.nju.software.dto;

import cn.edu.nju.software.data.ActiveData;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by mengf on 2018/6/3 0003.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveDto {
    private Long imageId;
    private ActiveData standard;
    private ActiveData mutation;
    private String standardCollection = "standard_model";
    private String mutationCollection;
}
