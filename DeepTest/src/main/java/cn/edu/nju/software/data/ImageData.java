package cn.edu.nju.software.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mongodb.morphia.annotations.Embedded;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mengf on 2018/5/29 0029.
 */
@Document(collection = "image_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {
    @Id
    private Long id;

    @Embedded
    private List<Tag> tags;

    private String path;

    @Field("thumbnail_path")
    private String thumbnailPath;
}
