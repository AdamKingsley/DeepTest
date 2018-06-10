package cn.edu.nju.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by mengf on 2018/6/9 0009.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamImageDto {
    private List<Long> selectedImageIds;
    private List<ImageDto> allImages;
}
