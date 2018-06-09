package cn.edu.nju.software.dto;

import cn.edu.nju.software.data.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by mengf on 2018/6/9 0009.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    @Id
    private Long id;

    private String name;

    private List<Tag> tags;

    private String path;

    @Field("thumbnail_path")
    private String thumbnailPath;

    @Field("submit_time")
    private String submitTime;
}
