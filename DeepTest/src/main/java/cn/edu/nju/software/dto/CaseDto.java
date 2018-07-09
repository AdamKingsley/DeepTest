package cn.edu.nju.software.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseDto {
    private String taskId;
    //根据task_id 映射到的case_id
    private Long examId;
    //根据case_id 映射到的image_id
    private String caseId;
    private Long imageId;
    private String path;
    private String compose_path;
}
