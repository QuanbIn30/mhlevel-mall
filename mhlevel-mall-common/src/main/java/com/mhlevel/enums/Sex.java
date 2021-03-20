package com.mhlevel.enums;

/**
 * @author quanbin
 * @date 2021-03-20
 */
public enum Sex {
    MAN(1,"男"),
    WOMAN(0,"女"),
    SECRET(3,"保密")
    ;

    private final Integer type;

    private final String msg;

    Sex(Integer type, String msg) {
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
