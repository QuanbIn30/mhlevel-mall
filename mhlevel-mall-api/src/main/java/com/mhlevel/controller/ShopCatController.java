package com.mhlevel.controller;

import com.mhlevel.pojo.bo.ShopcartBO;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author quanbin
 * @date 2021-03-23
 */
@Api(value = "购物车接口Controller", tags = {"购物车接口相关api"})
@RequestMapping("shopcart")
@RestController
public class ShopCatController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public MHLEVELJSONResult add(@RequestParam String userId,
                                 @RequestBody ShopcartBO shopcartBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        if (userId == null){
            return MHLEVELJSONResult.errorMsg("");
        }

        System.out.println(shopcartBO);
        // TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        return MHLEVELJSONResult.ok(shopcartBO);
    }

    @ApiOperation(value = "删除购物车中的商品", notes = "删除购物车中的商品", httpMethod = "POST")
    @PostMapping("/del")
    public MHLEVELJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return MHLEVELJSONResult.ok("参数不能为空");
        }
        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return MHLEVELJSONResult.ok();
    }
}
