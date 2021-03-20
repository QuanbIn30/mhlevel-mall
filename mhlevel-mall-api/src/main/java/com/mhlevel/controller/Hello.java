package com.mhlevel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author quanbin
 * @date 2021-03-18
 */
@ApiIgnore
@RestController
public class Hello {

    final static Logger logger = LoggerFactory.getLogger(Hello.class);

    @GetMapping("/hello")
    public Object hello(){
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
//        logger.error("error");
        return "hello";
    }
}
