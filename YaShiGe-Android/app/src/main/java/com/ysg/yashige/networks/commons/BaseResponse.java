package com.ysg.yashige.networks.commons;

/**
 * Created by weitong on 16/7/4.
 */
public class BaseResponse {

    private String code;
    private String msg;
    private String data;

    public BaseResponse() {
    }

    public BaseResponse(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return NetConfig.code_success.equals(code);
    }

}
