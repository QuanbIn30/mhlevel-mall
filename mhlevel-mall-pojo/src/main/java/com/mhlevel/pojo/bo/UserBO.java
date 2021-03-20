package com.mhlevel.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author quanbin
 * @date 2021-03-20
 */
@ApiModel(value = "用户对象BO", description = "从客户端，由用户传入的数据封装在此entity中")
public class UserBO {

    @ApiModelProperty(value = "用户名",name = "suername",example = "harden",required = true)
    private String username;

    @ApiModelProperty(value = "密码",name = "password", example = "134413", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码",name = "confirmPassword", example = "134413", required = true)
    private String confirmedPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}
