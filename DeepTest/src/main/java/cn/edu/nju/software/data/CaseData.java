package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "case_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseData {
    @Id
    private ObjectId id;
    @Field("exam_id")
    private Long examId;
    @Field("case_id")
    private String caseId;

    @Field("image_id")
    private Long imageId;

    @Field("path")
    private String path;

    @Field("compose_path")
    private String composePath;

    //得分最高的合成图的path
    @Field("max_compose_path")
    private String maxComposePath;

    private Double score;

    //是否杀死变异模型
    @Field("is_killed")
    private Boolean isKilled;
}
