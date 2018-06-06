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
    private Long id;
    private String name;
    //变异模型的激活数据存储的collection的名称
    @Field("data_collection")
    private String dataCollection;
    //变异类型 目前就0 表示神经元删除
    private Integer type;

}
