package com.mhlevel.controller;

import com.mhlevel.enums.PayMethod;
import com.mhlevel.pojo.bo.SubmitOrderBO;
import com.mhlevel.service.OrderService;
import com.mhlevel.utils.CookieUtils;
import com.mhlevel.utils.MHLEVELJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author quanbin
 * @date 2021-03-29
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RestController
@RequestMapping("orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public MHLEVELJSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                                    HttpServletRequest request,
                                    HttpServletResponse response){

        if(submitOrderBO.getPayMethod() != PayMethod.ALIPAY.getType()
            || submitOrderBO.getPayMethod() != PayMethod.WEIXIN.getType()){
            return MHLEVELJSONResult.errorMsg("支付方式不支持");
        }

        System.out.println(submitOrderBO.toString());

        //1. 创建订单
        String orderId = orderService.createOrder(submitOrderBO);

        //2. 创建订单以后，从购物车中移除已结算（已提交）的商品
        //TODO 整合redis后，完善购物车中的已结算商品清除，并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);


        //3. 向支付中心发送当前订单，用户保存支付中心的订单数据

        return MHLEVELJSONResult.ok(orderId);
    }



}
