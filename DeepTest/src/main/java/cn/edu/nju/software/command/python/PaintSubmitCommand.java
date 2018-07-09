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
    @JSONField(name = "case_id")
    private String caseId;
    private ImageCommand image;
    @JSONField(name = "mutation_models")
    private List<ModelCommand> mutaionModels;
    //想了想前端不要传这个参数，后台自己设定
    @JSONField(name = "standard_model_path")
    private String standardModelPath;
    @JSONField(name = "compose_image_str")
    private String composeImageStr;
}
