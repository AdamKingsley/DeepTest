package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Document(collection = "submit_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitData {
    @Id
    private ObjectId id;

    @Field("exam_id")
    private Long examId;
    @Field("user_id")
    private String userId;

    @Field("image_ids")
    private List<Long> imageIds;

    @Field("kill_model_ids")
    private List<Long> killModelId;

    @Field("submit_time")
    private Date submitTime;

}
