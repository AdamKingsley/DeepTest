package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "submit_count")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCountData {
    @Id
    private ObjectId id;
    @Field("exam_id")
    private Long examId;
    @Field("user_id")
    private String userId;
    private Long count;
    //第一次提交的时间
    @Field("first_time")
    private Long firstTime;
    //最后一次提交的时间-第一次提交的时间 = 总时间
    @Field("total_time")
    private Long totalTime;
}
