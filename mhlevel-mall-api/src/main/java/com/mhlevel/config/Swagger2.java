package com.mhlevel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 的配置类
 * @author quanbin
 * @date 2021-03-20
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    //访问路径: http://localhost:8088/doc.html

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mhlevel.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Mhlevel-mall 电商平台 api")
                .contact(new Contact("imooc",
                        "https://mhlevel-mall.com",
                        "abc@mhlevel.com"))
                .description("专为mhlevel-mall提供的api文档")
                .version("1.0.0")
                .termsOfServiceUrl("https://mhlevel-mall.com")
                .build();
    }
}
