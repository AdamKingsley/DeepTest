package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "exam_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamScoreData {
    @Id
    private ObjectId id;
    @Field("exam_id")
    private Long ExamId;
    @Field("user_id")
    private String userId;
    @Field("killed_model_ids")
    private List<Long> killedModelIds;
    private Double score;
    @Field("killed_detail")
    private List<MseScoreData> killedDetail;
}
