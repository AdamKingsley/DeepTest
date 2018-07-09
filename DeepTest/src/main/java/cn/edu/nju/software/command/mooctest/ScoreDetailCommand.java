package cn.edu.nju.software.command.mooctest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDetailCommand {
    @JSONField(name = "case_id")
    private String caseId;
    private Double score;
}
