package cn.edu.nju.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created by mengf on 2018/6/9 0009.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto {
    private Long id;
    private List<ModelDto> models;
    private List<Long> selectedImageIds;
    private List<ImageDto> allImages;
    private List<Long> killedModelIds;
    private Date createTime;
    private Date modifyTime;
    private Date startTime;
    private Date endTime;
    private Integer type;
    //提交的次数
    private Long times;
}
