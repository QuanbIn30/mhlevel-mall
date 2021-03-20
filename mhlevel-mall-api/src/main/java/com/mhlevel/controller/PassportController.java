package com.mhlevel.controller;

import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.UserBO;
import com.mhlevel.service.UserService;
import com.mhlevel.utils.MHLEVELJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/regist")
    public MHLEVELJSONResult regist(@RequestBody UserBO userBO){
        String userName = userBO.getUsername();
        String passWord = userBO.getPassword();
        String confirmPassword = userBO.getConfirmedPassword();
        //1.判断用户名和密码不能为空
        if(StringUtils.isBlank(userName)
                || StringUtils.isBlank(passWord)
                || StringUtils.isBlank(confirmPassword)){
            return MHLEVELJSONResult.errorMsg("用户名或密码不能为空");
        }
        //2.查询用户名是否存在
        boolean result = userService.queryUsernameIsExist(userName);
        if (result){
            return MHLEVELJSONResult.errorMsg("用户名已存在");
        }
        //3.密码长度不能小于6位
        if (passWord.length() < 6){
            return MHLEVELJSONResult.errorMsg("密码的长度不能小于6位");
        }
        //4.判断两次输入的密码是否一致
        if (!passWord.equals(confirmPassword)){
            return MHLEVELJSONResult.errorMsg("两次输入的密码不一致");
        }
        //5.完成注册
        Users user = userService.createUser(userBO);
        return MHLEVELJSONResult.ok();
    }
}
