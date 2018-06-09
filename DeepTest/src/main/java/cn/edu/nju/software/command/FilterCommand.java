package cn.edu.nju.software.command;

import cn.edu.nju.software.data.mutation.NeuronData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by mengf on 2018/6/9 0009.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCommand {
    private List<Object> tags;
    private List<ActiveCommand> active_locations;
    private List<Long> imageIds;
}
