package cn.edu.nju.software.dto;

import cn.edu.nju.software.data.MseScoreData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamScoreDto {
    private Long ExamId;
    private String userId;
    private List<Long> killedModelIds;
    private Double score;
    private List<MseScoreData> killedDetail;
}
