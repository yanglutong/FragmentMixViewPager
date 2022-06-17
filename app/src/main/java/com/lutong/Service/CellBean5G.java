package com.lutong.Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CellBean5G {

    @SerializedName("header")
    private HeaderBean header;
    @SerializedName("body")
    private BodyBean body;

    @Override
    public String toString() {
        return "CellBean5G{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class HeaderBean {
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

    public static class BodyBean {
        @SerializedName("band")
        private String band;

        @Override
        public String toString() {
            return "BodyBean{" +
                    "band='" + band + '\'' +
                    ", dlnarfcn=" + dlnarfcn +
                    ", ssbgscn=" + ssbgscn +
                    ", pci=" + pci +
                    ", subfrmassign=" + subfrmassign +
                    ", specialsubfrm=" + specialsubfrm +
                    ", tac=" + tac +
                    ", power='" + power + '\'' +
                    ", bandwidth='" + bandwidth + '\'' +
                    ", plmnList=" + plmnList +
                    '}';
        }

        @SerializedName("dlnarfcn")
        private Integer dlnarfcn;
        @SerializedName("ssbgscn")
        private Integer ssbgscn;
        @SerializedName("pci")
        private Integer pci;
        @SerializedName("subfrmassign")
        private Integer subfrmassign;
        @SerializedName("specialsubfrm")
        private Integer specialsubfrm;
        @SerializedName("tac")
        private Integer tac;
        @SerializedName("power")
        private String power;
        @SerializedName("bandwidth")
        private String bandwidth;
        @SerializedName("plmn_list")
        private List<PlmnListBean> plmnList;

        public String getBand() {
            return band;
        }

        public void setBand(String band) {
            this.band = band;
        }

        public Integer getDlnarfcn() {
            return dlnarfcn;
        }

        public void setDlnarfcn(Integer dlnarfcn) {
            this.dlnarfcn = dlnarfcn;
        }

        public Integer getSsbgscn() {
            return ssbgscn;
        }

        public void setSsbgscn(Integer ssbgscn) {
            this.ssbgscn = ssbgscn;
        }

        public Integer getPci() {
            return pci;
        }

        public void setPci(Integer pci) {
            this.pci = pci;
        }

        public Integer getSubfrmassign() {
            return subfrmassign;
        }

        public void setSubfrmassign(Integer subfrmassign) {
            this.subfrmassign = subfrmassign;
        }

        public Integer getSpecialsubfrm() {
            return specialsubfrm;
        }

        public void setSpecialsubfrm(Integer specialsubfrm) {
            this.specialsubfrm = specialsubfrm;
        }

        public Integer getTac() {
            return tac;
        }

        public void setTac(Integer tac) {
            this.tac = tac;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getBandwidth() {
            return bandwidth;
        }

        public void setBandwidth(String bandwidth) {
            this.bandwidth = bandwidth;
        }

        public List<PlmnListBean> getPlmnList() {
            return plmnList;
        }

        public void setPlmnList(List<PlmnListBean> plmnList) {
            this.plmnList = plmnList;
        }

        public static class PlmnListBean {
            @SerializedName("plmn")
            private String plmn;

            public String getPlmn() {
                return plmn;
            }

            public void setPlmn(String plmn) {
                this.plmn = plmn;
            }
        }
    }
}
