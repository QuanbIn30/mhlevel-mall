package com.mhlevel.enums;

/**
 * @author quanbin
 * @date 2021-03-20
 */
public enum YesOrNo {
    YES(1,"Yes"),
    NO(0,"NO")
    ;

    private Integer type;

    private String mes;

    YesOrNo(Integer type, String mes) {
        this.type = type;
        this.mes = mes;
    }

    public Integer getType() {
        return type;
    }

    public String getMes() {
        return mes;
    }
}
