package com.mhlevel.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author quanbin
 * @date 2021-03-30
 */
public class WebMvcConfig implements WebMvcConfigurer {

    //实现静态资源的映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources") // 映射swagger2
                .addResourceLocations("file:/Users/Shared/Relocated Items/Security/MyDocument/网盘/mk/Architecture/WorkSpace/"); // 映射本地静态资源
    }
}
