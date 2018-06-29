package cn.edu.nju.software.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "base_path")
@Getter
@Setter
public class FilePathConfig {
    private String image;
    private String model;
    private String composeImage;
}
