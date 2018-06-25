package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 用户提交记录的数据
 * 用于进行用户限制的一些操作
 * 如限时查看等功能
 *
 * 现在无用
 */
@Document(collection = "operation_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationData {
    @Id
    private ObjectId id;

    @Field("user_id")
    private String userId;

    @Field("exam_id")
    private Long examId;

    @Field("image_id")
    private Long imageId;

    @Field("model_id")
    private Long modelId;

    @Field("operation_time")
    private Date operationTime;
}
