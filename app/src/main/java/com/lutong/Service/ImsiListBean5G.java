package com.lutong.Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class ImsiListBean5G {

    public List<ImsiListDTO> getImsiList() {
        return imsiList;
    }

    public void setImsiList(List<ImsiListDTO> imsiList) {
        this.imsiList = imsiList;
    }

    @Override
    public String toString() {
        return "ImsiListBean5G{" +
                "imsiList=" + imsiList +
                '}';
    }

    @SerializedName("imsi_list")
    private List<ImsiListDTO> imsiList;

    public static class ImsiListDTO {
        @SerializedName("imsi")
        private String imsi;

        @Override
        public String toString() {
            return "ImsiListDTO{" +
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
