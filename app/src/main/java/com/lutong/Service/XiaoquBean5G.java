package com.lutong.Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class XiaoquBean5G {

    @SerializedName("band")
    private String band;

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public Integer getNref() {
        return nref;
    }

    public void setNref(Integer nref) {
        this.nref = nref;
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

    @Override
    public String toString() {
        return "XiaoquBean5G{" +
                "band='" + band + '\'' +
                ", nref=" + nref +
                ", pci=" + pci +
                ", subfrmassign=" + subfrmassign +
                ", specialsubfrm=" + specialsubfrm +
                ", tac=" + tac +
                ", power='" + power + '\'' +
                ", bandwidth='" + bandwidth + '\'' +
                ", plmnList=" + plmnList +
                '}';
    }

    public List<PlmnListDTO> getPlmnList() {
        return plmnList;
    }

    public void setPlmnList(List<PlmnListDTO> plmnList) {
        this.plmnList = plmnList;
    }

    @SerializedName("nref")
    private Integer nref;
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
    private List<PlmnListDTO> plmnList;

    public static class PlmnListDTO {
        @SerializedName("plmn")
        private String plmn;

        public String getPlmn() {
            return plmn;
        }

        public void setPlmn(String plmn) {
            this.plmn = plmn;
        }

        @Override
        public String toString() {
            return "PlmnListDTO{" +
                    "plmn='" + plmn + '\'' +
                    '}';
        }
    }
}
