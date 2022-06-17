package com.lutong.Service;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.stmt.query.In;

public class BeanDw5G {
    private Header header;
    private Body body;

    @Override
    public String toString() {
        return "BeanDw5G{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Header {
        @Override
        public String toString() {
            return "HeaderBean{" +
                    "cellId=" + cellId +
                    ", sessionId='" + sessionId + '\'' +
                    ", msgId='" + msgId + '\'' +
                    '}';
        }

        @SerializedName("cell_id")
        private Integer cellId;
        @SerializedName("session_id")
        private String sessionId;
        @SerializedName("msg_id")
        private String msgId;

        public Integer getCellId() {
            return cellId;
        }

        public void setCellId(Integer cellId) {
            this.cellId = cellId;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }
    }

    public static class Body {
        public String getImsi() {
            return imsi;
        }

        @Override
        public String toString() {
            return "body{" +
                    "imsi=" + imsi +
                    '}';
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        @SerializedName("imsi")
        private String imsi;

    }
}

