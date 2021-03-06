package cn.edu.nju.software.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2018/6/7 0007.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamCommand {
    private Long id;
    private String taksId;
    // 0 是找图片 1是图片上加噪音
    private Integer type;
    private List<Long> imageIds;
    private List<Long> modelIds;
    private Date createTime;
    private Date modifyTime;
}
