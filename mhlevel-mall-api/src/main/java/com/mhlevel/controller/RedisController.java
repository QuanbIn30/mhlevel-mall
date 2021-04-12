package com.mhlevel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Redis测试
 * @author quanbin
 * @date 2021-04-06
 */
@ApiIgnore
@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/insert")
    public String insert(String key, String value){
        redisTemplate.opsForValue().set(key, value);
        return "ok";
    }

    @GetMapping("/get")
    public String get(String key){
        String value = (String)redisTemplate.opsForValue().get(key);
        return value;
    }

    @GetMapping("/delete")
    public String delete (String key){
        redisTemplate.delete(key);
        return "Ok";
    }
}
