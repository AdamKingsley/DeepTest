package cn.edu.nju.software.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitCommand {
    //提交的样本Id集合
    private List<Long> imageIds;
    //考试ID
    private Long examId;
    //用户ID
    private Long userId;
}
