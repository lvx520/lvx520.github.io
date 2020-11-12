package com.ssm.lv.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lv
 * @date 2020/10/12 - 21:19
 */
@Component
public class MvcConfing implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/img/**").addResourceLocations("file:D:/temp-rainy/");
     registry.addResourceHandler("/img/**").addResourceLocations("file:D:/IDEAJava/IdeaProject/springboot-learn-network/src/main/resources/static/image/imgs/");

    }
}
