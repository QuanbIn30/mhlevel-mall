package com.mhlevel.controller.center;

import com.mhlevel.pojo.Users;
import com.mhlevel.service.center.CenterUserService;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author quanbin
 * @date 2021-03-29
 */
@Api(value = "center - 用户中心", tags = {"用户中心展示的相关接口"})
@RestController("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("/userInfo")
    public MHLEVELJSONResult userInfo(
            @ApiParam(name="userId", value="用户id", required = true)
            @RequestParam String userId){
        Users users = centerUserService.queryUserInfo(userId);
        return MHLEVELJSONResult.ok(users);
    }
}
