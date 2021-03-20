package com.mhlevel.controller;

import com.mhlevel.service.UserService;
import com.mhlevel.utils.MHLEVELJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quanbin
 * @date 2021-03-19
 */
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public MHLEVELJSONResult usernameIsExist(@RequestParam String userName){
        //1.判断用户名是否为空
        if(StringUtils.isBlank(userName)){
            return MHLEVELJSONResult.errorMsg("用户名不能为空");
        }
        //2.查找注册的用户名是否存在
        boolean result = userService.queryUsernameIsExist(userName);
        if(result){
            return MHLEVELJSONResult.errorMsg("用户名已经存在");
        }
        //3.请求成功，没有重复的用户名
        return MHLEVELJSONResult.ok();
    }
}
