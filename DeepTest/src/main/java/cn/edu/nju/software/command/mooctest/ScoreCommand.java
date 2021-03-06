package cn.edu.nju.software.command.mooctest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreCommand {
    @JSONField(name = "openId")
    private String openId;
    @JSONField(name = "score")
    private Double score;
    @JSONField(name = "detail")
    private List<ScoreDetailCommand> details;
}
