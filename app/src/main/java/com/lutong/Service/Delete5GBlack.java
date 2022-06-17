package com.lutong.Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Delete5GBlack {
    @Override
    public String toString() {
        return "Delete5GBlack{" +
                "body=" + body +
                ", header=" + header +
                '}';
    }

    @SerializedName("body")
    private BodyBean body;
    @SerializedName("header")
    private HeaderBean header;

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public static class BodyBean {
        @Override
        public String toString() {
            return "BodyBean{" +
                    "imsiList=" + imsiList +
                    '}';
        }

        @SerializedName("imsi_list")
        private List<ImsiListBean> imsiList;

        public List<ImsiListBean> getImsiList() {
            return imsiList;
        }

        public void setImsiList(List<ImsiListBean> imsiList) {
            this.imsiList = imsiList;
        }

        public static class ImsiListBean {
            @SerializedName("imsi")
            private String imsi;

            @Override
            public String toString() {
                return "ImsiListBean{" +
                        "imsi='" + imsi + '\'' +
                        '}';
            }

            public String getImsi() {
                return imsi;
            }

            public void setImsi(String imsi) {
                this.imsi = imsi;
            }
        }
    }

    public static class HeaderBean {
        @SerializedName("cell_id")
        private Integer cellId;
        @SerializedName("msg_id")
        private String msgId;
        @SerializedName("session_id")
        private String sessionId;

        public Integer getCellId() {
            return cellId;
        }

        public void setCellId(Integer cellId) {
            this.cellId = cellId;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }
}
