package cn.edu.nju.software.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDataDto {
    //通过数据扩增方式 => 变胖或变瘦等操作后的图片的数据
    private String image;
}
