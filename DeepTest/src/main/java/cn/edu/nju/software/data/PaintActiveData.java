package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintActiveData {
    @Id
    private Long id;

    private Integer predict;

    @Field("standard_predict")
    private Integer standardPredict;

    @Field("model_id")
    private Long modelId;

    @Field("mutation_activation_data")
    //@JSONField(name = "activation_data")
    private Double[][] mutationActivationData;

    @Field("standard_activation_data")
    //@JSONField(name = "activation_data")
    private Double[][] standardActivationData;
}
