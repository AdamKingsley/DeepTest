package cn.edu.nju.software.data.mutation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by mengf on 2018/6/4 0004.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeuronData {
    private Integer layer;
    @Field("neuron_index")
    private Integer neuronIndex;
}
