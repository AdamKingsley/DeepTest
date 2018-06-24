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
    private List<Long> models;
    private Long imageId;
    private String userId;
    //base64 的前景图数据
    private String adversial;
}
