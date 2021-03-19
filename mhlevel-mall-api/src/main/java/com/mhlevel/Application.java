package com.mhlevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author quanbin
 * @date 2021-03-18
 */
@SpringBootApplication
@MapperScan(basePackages = "com.mhlevel.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
