package com.lutong.OrmSqlLite.Bean;


public class SpBean {
    private String Down;
    private int Priority;
    private int Rsrp;
    private String Plmn;
    private String Band;
    private int Rsrq;
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

    public int getRsrq() {
        return Rsrq;
    }

    public void setRsrq(int rsrq) {
        Rsrq = rsrq;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }


    public String getCid() {
        return cid;
    }

    private String cid;

    private int Pci;
    private int Tac;

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

    public int getPci() {
        return Pci;
    }

    public void setPci(int pci) {
        Pci = pci;
    }

    public int getTac() {
        return Tac;
    }

    public void setTac(int tac) {
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

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getRsrp() {
        return Rsrp;
    }

    public void setRsrp(int rsrp) {
        Rsrp = rsrp;
    }



}
