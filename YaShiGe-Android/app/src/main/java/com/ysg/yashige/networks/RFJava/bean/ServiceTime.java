package com.ysg.yashige.networks.RFJava.bean;

/**
 * Created by apple on 2017/6/28.
 */

public class ServiceTime {

    /**
     * code : 0
     * msg : success
     * data : {"time":"1498639023"}
     * debugInfo : {}
     */

    private int code;
    private String msg;
    private DataBean data;
    private DebugInfoBean debugInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DebugInfoBean getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(DebugInfoBean debugInfo) {
        this.debugInfo = debugInfo;
    }

    public static class DataBean {
        /**
         * time : 1498639023
         */

        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class DebugInfoBean {
    }
}
