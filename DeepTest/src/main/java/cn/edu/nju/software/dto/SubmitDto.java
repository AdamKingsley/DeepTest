package cn.edu.nju.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by mengf on 2018/6/8 0008.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDto {
    //杀死变异体的数量
    private Integer killedNums;
    //杀死变异体的ID
    private List<Long> modelIds;
    //第几次提交
    private Integer times;
}
