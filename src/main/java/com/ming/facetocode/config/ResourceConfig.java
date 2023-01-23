package com.ming.facetocode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 小明
 * @date 2022/4/23
 * @description
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Pictures/**")
                .addResourceLocations("file:/root/TorchProject/face-detect/Pictures/")
                .addResourceLocations("file:/root/titlePic/");
    }

}
