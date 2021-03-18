package com.mhlevel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quanbin
 * @date 2021-03-18
 */
@RestController
public class Hello {

    @GetMapping("/hello")
    public Object hello(){
        return "Hello MHLEVEL";
    }
}
