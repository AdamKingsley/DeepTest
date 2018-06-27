package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MseScoreData {
    //合成的能够杀死该modelId的变异体的模型图片的位置
    @Field("compose_path")
    private String composePath;
    //在哪张图片的基础上进行的修改
    @Field("image_id")
    private Long imageId;
    //杀死的变异体模型id
    @Field("model_id")
    private Long modelId;
    //得分 每次更新都会选取更高的得分
    private Double score;
}
