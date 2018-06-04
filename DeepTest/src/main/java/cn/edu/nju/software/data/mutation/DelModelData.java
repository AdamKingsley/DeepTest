package cn.edu.nju.software.data.mutation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by mengf on 2018/6/1 0001.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelModelData extends MutationData {
    @Field("delete_neurons")
    private List<NeuronData> deleteNuerons;
}
