package com.mhlevel.controller;

import com.mhlevel.pojo.Users;
import com.mhlevel.pojo.bo.UserBO;
import com.mhlevel.service.UserService;
import com.mhlevel.utils.CookieUtils;
import com.mhlevel.utils.JsonUtils;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author quanbin
 * @date 2021-03-19
 */
@Api(value = "注册登入", tags = "用于注册登入相关接口")
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
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

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public MHLEVELJSONResult regist(@RequestBody UserBO userBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response){
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

        user = setNullProperty(user);
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(user),true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据


        return MHLEVELJSONResult.ok();
    }

    /**
     * 用户登录
     * @param userBO
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public MHLEVELJSONResult login(@RequestBody UserBO userBO,
                                   HttpServletRequest request,
                                   HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return MHLEVELJSONResult.errorMsg("用户名或密码不能为空");
        }

        Users users = userService.queryUserForLogin(username, password);
        if (users == null){
            return MHLEVELJSONResult.errorMsg("用户名和密码不一致");
        }
        users = setNullProperty(users);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(users), true);

        return MHLEVELJSONResult.ok(users);
    }

    @ApiOperation(value = "用户推出登录", notes = "用户推出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public MHLEVELJSONResult logout(@RequestParam String userId,
                                    HttpServletRequest request,
                                    HttpServletResponse response){
        //清除用户相关的信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return MHLEVELJSONResult.ok();
    }

    /**
     * 设置返回对象的隐私字段为空
     * @param userResult
     * @return
     */
    private Users setNullProperty(Users userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
