package cn.edu.nju.software.command.python;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCommand {
    private Long id;
    private String path;
    private Object tag;
}
