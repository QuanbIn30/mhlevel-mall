package com.mhlevel.enums;

/**
 * @author quanbin
 * @date 2021-03-29
 */
public enum PayMethod {
    WEIXIN(1, "微信支付"),
    ALIPAY(2,"支付宝支付")
    ;

    private Integer type;

    private String Msg;

    PayMethod(Integer type, String msg) {
        this.type = type;
        Msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return Msg;
    }
}
