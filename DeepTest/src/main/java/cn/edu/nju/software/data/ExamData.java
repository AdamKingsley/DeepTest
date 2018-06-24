package cn.edu.nju.software.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.omg.CORBA.PERSIST_STORE;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2018/6/1 0001.
 */
@Document(collection = "exam_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamData {
    @Id
    private Long id;
    //考试类型
    private Integer type;
    @Field("taskId")
    private String taskId;
    @Field("image_ids")
    private List<Long> imageIds;
    @Field("model_ids")
    private List<Long> modelIds;
    private Date createTime;
    private Date modifyTime;
}
