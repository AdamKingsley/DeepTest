package cn.edu.nju.software.data.mutation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by mengf on 2018/6/4 0004.
 */
@Document(collection = "mutation_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MutationData {
    @Id
    private ObjectId id;
    //变异模型的激活数据存储的collection
    @Field("data_collection")
    private String dataCollection;
    //变异类型
    private Integer type;

}
