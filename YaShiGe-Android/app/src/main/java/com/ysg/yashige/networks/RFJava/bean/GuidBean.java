package com.ysg.yashige.networks.RFJava.bean;

/**
 * Created by apple on 2017/6/28.
 */

public class GuidBean {

    /**
     * code : 0
     * msg : success
     * data : {"list":{"pic":"http://storage.easyiservice.com/iservicev2/img/201705/74f70496af641ab805f5de5ad60e4946.jpg!width_750","link":"http://www.baidu.com"}}
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
         * list : {"pic":"http://storage.easyiservice.com/iservicev2/img/201705/74f70496af641ab805f5de5ad60e4946.jpg!width_750","link":"http://www.baidu.com"}
         */

        private ListBean list;

        public ListBean getList() {
            return list;
        }

        public void setList(ListBean list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * pic : http://storage.easyiservice.com/iservicev2/img/201705/74f70496af641ab805f5de5ad60e4946.jpg!width_750
             * link : http://www.baidu.com
             */

            private String pic;
            private String link;

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }

    public static class DebugInfoBean {
    }
}
