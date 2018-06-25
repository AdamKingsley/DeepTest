package cn.edu.nju.software.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "submit_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintSubmitData {
    @Id
    private ObjectId id;

    @Field("exam_id")
    @JSONField(name = "exam_id")
    private Long examId;
    @Field("user_id")
    @JSONField(name = "user_id")
    private String userId;

    @Field("image_id")
    @JSONField(name = "image_id")
    private Long imageId;

    @Field("model_id")
    @JSONField(name = "model_id")
    private Long modelId;

    @Field("adversial_path")
    @JSONField(name = "adversial_path")
    private String adversialPath;

    @Field("compose_path")
    @JSONField(name = "compose_path")
    private String composePath;

    @Field("standard_activation_data")
    @JSONField(name = "standard_activation_data")
    private Double[][] standardActivationData;

    @Field("mutation_activation_data")
    @JSONField(name = "mutation_activation_data")
    private Double[][] mutationActivationData;

    @Field("isKilled")
    private Boolean isKilled;

    @Field("score")
    private Double score;

    @Field("mse")
    private Double mse;

    @Field("submit_time")
    @JSONField(name = "submit_time", format = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
}
