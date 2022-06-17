package com.lutong.OrmSqlLite.Bean;


public class SpBean2 {
    private String Down;
    private String Priority;
    private String Rsrp;
    private String Plmn;
    private String Band;
    private String Rsrq;
    private boolean zw=false;

    @Override
    public String toString() {
        return "SpBean{" +
                "Down='" + Down + '\'' +
                ", Priority=" + Priority +
                ", Rsrp=" + Rsrp +
                ", Plmn='" + Plmn + '\'' +
                ", Band='" + Band + '\'' +
                ", Rsrq=" + Rsrq +
                ", zw=" + zw +
                ", cid='" + cid + '\'' +
                ", Pci=" + Pci +
                ", Tac=" + Tac +
                ", Up='" + Up + '\'' +
                '}';
    }

    public boolean isZw() {
        return zw;
    }

    public void setZw(boolean zw) {
        this.zw = zw;
    }

    public String getRsrq() {
        return Rsrq;
    }

    public void setRsrq(String rsrq) {
        Rsrq = rsrq;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    public String getCid() {
        return cid;
    }

    private String cid;

    private String Pci;
    private String Tac;

    public String getPlmn() {
        return Plmn;
    }

    public void setPlmn(String plmn) {
        Plmn = plmn;
    }

    public String getBand() {
        return Band;
    }

    public void setBand(String band) {
        Band = band;
    }

    public String getPci() {
        return Pci;
    }

    public void setPci(String pci) {
        Pci = pci;
    }

    public String getTac() {
        return Tac;
    }

    public void setTac(String tac) {
        Tac = tac;
    }

    public String getUp() {
        return Up;
    }

    public void setUp(String up) {
        Up = up;
    }

    private String Up;
    public String getDown() {
        return Down;
    }

    public void setDown(String down) {
        Down = down;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getRsrp() {
        return Rsrp;
    }

    public void setRsrp(String rsrp) {
        Rsrp = rsrp;
    }



}
