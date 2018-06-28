package cn.edu.nju.software.command.python;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDataCommand {
    //BASE_64的图片数据
    private String image;
}
