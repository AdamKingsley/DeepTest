package cn.edu.nju.software.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //addResourceHandler是指你想在url请求的路径
        //addResourceLocations是图片存放的真实路径
        log.info(filePathConfig.getImage());
        log.info(filePathConfig.getModel());
        log.info(filePathConfig.getComposeImage());

        registry.addResourceHandler("/image/**").addResourceLocations("file:" + new File(filePathConfig.getImage()).getAbsolutePath() + File.separator);
        registry.addResourceHandler("/compose/image/**").addResourceLocations("file:" + new File(filePathConfig.getComposeImage()).getAbsolutePath() + File.separator);
        super.addResourceHandlers(registry);
    }
}