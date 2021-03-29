package com.mhlevel.service;

import com.mhlevel.pojo.bo.SubmitOrderBO;

/**
 * 订单Service
 * @author quanbin
 * @date 2021-03-29
 */
public interface OrderService {

    /**
     * 创建订单相关信息
     * @param submitOrderBO
     */
    String createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
