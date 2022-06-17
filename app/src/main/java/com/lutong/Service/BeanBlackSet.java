package com.lutong.Service;

import java.util.List;

public class BeanBlackSet {

    private HeaderDTO header;
    private BodyDTO body;

    public HeaderDTO getHeader() {
        return header;
    }

    public void setHeader(HeaderDTO header) {
        this.header = header;
    }

    public BodyDTO getBody() {
        return body;
    }

    public void setBody(BodyDTO body) {
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

        @Override
        public String toString() {
            return "HeaderDTO{" +
                    "cell_id=" + cell_id +
                    ", session_id='" + session_id + '\'' +
                    ", msg_id='" + msg_id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Bean{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public static class BodyDTO {
        private List<CellListDTO> cell_list;
        private List<ListBean.Body.UeListDTO> ue_list;
        private String work_mode;//工作模式


        private List<Imsi> imsi_list;

        public List<Imsi> getImsi_list() {
            return imsi_list;
        }

        public void setImsi_list(List<Imsi> imsi_list) {
            this.imsi_list = imsi_list;
        }

        public String getWork_mode() {
            return work_mode;
        }

        public void setWork_mode(String work_mode) {
            this.work_mode = work_mode;
        }

        public List<ListBean.Body.UeListDTO> getUe_list() {
            return ue_list;
        }

        public void setUe_list(List<ListBean.Body.UeListDTO> ue_list) {
            this.ue_list = ue_list;
        }

//        public String getSWITCH() {
//            return SWITCH;
//        }

//        public void setSWITCH(String SWITCH) {
//            this.SWITCH = SWITCH;
//        }

//        private String result;
//        private String SWITCH;

//        public String getSwitchs() {
//            return SWITCH;
//        }

//        public void setSwitchs(String switchs) {
//            this.SWITCH = switchs;
//        }

//        public String getResult() {
//            return result;
//        }

//        public void setResult(String result) {
//            this.result = result;
//        }

        public List<CellListDTO> getCell_list() {
            return cell_list;
        }

        public void setCell_list(List<CellListDTO> cell_list) {
            this.cell_list = cell_list;
        }

        public static class CellListDTO {
            private int cell_id;

            public int getCell_id() {
                return cell_id;
            }

            public void setCell_id(int cell_id) {
                this.cell_id = cell_id;
            }
        }

    }

 public static class Imsi {
        public String imsi;

     @Override
     public String toString() {
         return "Imsi{" +
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
