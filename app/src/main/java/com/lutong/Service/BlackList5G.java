package com.lutong.Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BlackList5G {
    @Override
    public String toString() {
        return "BlackList5G{" +
                "ueList=" + ueList +
                '}';
    }

    @SerializedName("ue_list")
    private List<UeListDTO> ueList;

    public List<UeListDTO> getUeList() {
        return ueList;
    }

    public void setUeList(List<UeListDTO> ueList) {
        this.ueList = ueList;
    }

    public static class UeListDTO {
        public String getImsi() {
            return imsi;
        }

        @Override
        public String toString() {
            return "UeListDTO{" +
                    "imsi='" + imsi + '\'' +
                    ", power=" + power +
                    ", msisdn='" + msisdn + '\'' +
                    '}';
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public Integer getPower() {
            return power;
        }

        public void setPower(Integer power) {
            this.power = power;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public void setMsisdn(String msisdn) {
            this.msisdn = msisdn;
        }

        @SerializedName("imsi")
        private String imsi;
        @SerializedName("power")
        private Integer power;
        @SerializedName("msisdn")
        private String msisdn;
    }
}
