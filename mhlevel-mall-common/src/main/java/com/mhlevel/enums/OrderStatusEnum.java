package com.mhlevel.enums;

/**
 * 订单状态
 * @author quanbin
 * @date 2021-03-29
 */
public enum OrderStatusEnum {
    WAIT_PAY(10, "待付款"),
    WAIT_DELIVER(20, "已付款，待发货"),
    WAIT_RECEIVE(30, "已发货，待收货"),
    SUCCESS(40, "交易成功"),
    CLOSE(50, "交易关闭")
    ;

    private Integer type;

    private String msg;

    OrderStatusEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
