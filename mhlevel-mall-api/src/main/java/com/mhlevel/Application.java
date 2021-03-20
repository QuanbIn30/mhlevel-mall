package com.mhlevel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author quanbin
 * @date 2021-03-18
 */
@SpringBootApplication
//扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.mhlevel.mapper")
//扫描所有包已经相关组建所在的包
@ComponentScan(basePackages = {"com.mhlevel", "org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
