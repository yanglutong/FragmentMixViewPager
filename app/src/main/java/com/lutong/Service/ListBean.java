package com.lutong.Service;

import java.util.List;

public class ListBean {

    private HeaderDTO header;
    private Body body;

    public HeaderDTO getHeader() {
        return header;
    }

    public void setHeader(HeaderDTO header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class HeaderDTO {
        private int cell_id;
        private String session_id;
        private String msg_id;

        public int getCell_id() {
            return cell_id;
        }

        public void setCell_id(int cell_id) {
            this.cell_id = cell_id;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;
        }
    }

    public static class Body {
        private List<UeListDTO> ue_list;

        public List<UeListDTO> getUe_list() {
            return ue_list;
        }

        public void setUe_list(List<UeListDTO> ue_list) {
            this.ue_list = ue_list;
        }

        public static class UeListDTO {
            private String imsi;

            public String getImsi() {
                return imsi;
            }

            public void setImsi(String imsi) {
                this.imsi = imsi;
            }
        }
    }
}
