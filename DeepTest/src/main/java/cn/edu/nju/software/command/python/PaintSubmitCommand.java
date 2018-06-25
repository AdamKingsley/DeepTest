package cn.edu.nju.software.command.python;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintSubmitCommand {
    @JSONField(name = "exam_id")
    private Long examId;
    @JSONField(name = "user_id")
    private String userId;
    private ImageCommand image;
    @JSONField(name = "mutation_models")
    private List<ModelCommand> mutaionModels;
    @JSONField(name = "standard_model_path")
    private String standardModelPath;
    @JSONField(name = "adversial_str")
    private String adversialStr;
}
