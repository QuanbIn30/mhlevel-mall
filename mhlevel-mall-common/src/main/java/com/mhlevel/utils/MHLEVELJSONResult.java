package com.mhlevel.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应数据结构
 *  200：成功
 *  500：错误
 *  501：bean验证错误，不管多少个错误都以map形式返回
 *  502：拦截器拦截到用户token出错
 *  555：异常抛出信息
 *  556: 用户qq校验异常
 * @author quanbin
 * @date 2021-03-20
 */
public class MHLEVELJSONResult {

    //定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //响应业务状态
    private Integer status;

    //响应消息
    private String msg;

    //响应中的数据
    private Object data;

    @JsonIgnore
    private String ok;

    public MHLEVELJSONResult(){}

    public MHLEVELJSONResult(Integer status, String msg, Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public MHLEVELJSONResult(Integer status, String msg, Object data, String ok){
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public MHLEVELJSONResult(Object data){
        this.status = 200;
        this.msg = "ok";
        this.data = data;
    }

    public Boolean isOK(){
        return this.status == 200;
    }

    public static MHLEVELJSONResult build(Integer status, String msg, Object data){
        return new MHLEVELJSONResult(status, msg, data);
    }

    public static MHLEVELJSONResult build(Integer status, String msg, Object data, String ok){
        return new MHLEVELJSONResult(status, msg, data, ok);
    }

    public static MHLEVELJSONResult ok(Object data){
        return new MHLEVELJSONResult(data);
    }

    public static MHLEVELJSONResult ok(){
        return new MHLEVELJSONResult(null);
    }

    public static MHLEVELJSONResult errorMsg(String msg){
        return new MHLEVELJSONResult(500,msg, null);
    }

    public static MHLEVELJSONResult errorMap(Object data){
        return new MHLEVELJSONResult(501, "error", data);
    }


    public static MHLEVELJSONResult errorTokenMsg(String msg) {
        return new MHLEVELJSONResult(502, msg, null);
    }

    public static MHLEVELJSONResult errorException(String msg) {
        return new MHLEVELJSONResult(555, msg, null);
    }

    public static MHLEVELJSONResult errorUserQQ(String msg) {
        return new MHLEVELJSONResult(556, msg, null);
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
