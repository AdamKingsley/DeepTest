package cn.edu.nju.software.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintSubmitDto {
    //TODO 字段修整
    private Long examId;
    private String userId;
    private Long imageId;
    private Long modelId;
    private String adversialPath;
    private String composePath;
    private Double[][] standardActivationData;
    private Double[][] mutationActivationData;
    private Boolean isKilled;
    private Double score;
    private Double mse;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
}
