package cn.edu.nju.software.command.mooctest;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignTaskCommand {
    @JSONField(name = "task_id")
    private String taskId;
    @JSONField(name = "task_case_list")
    private Map<String, String> caseList;
    //@JSONField(name = "score")
    //private Double score;
    @JSONField(name = "scoreDetails")
    private List<ScoreCommand> scoreDetails;
}
