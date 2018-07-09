package cn.edu.nju.software.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaintCommand {
    private Long examId;
    private String caseId;
    //单个模型
    // 标准模型不需要进行传入究竟是哪个模型
    //private List<Long> models;
    //在哪个图像的基础上修改的
    private Long imageId;
    private String userId;
    //扰动后的图片整体的base64编码
    private String composeImageStr;
}
