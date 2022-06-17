package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author: 小杨同志
 * @date: 2021/10/22
 */
@DatabaseTable(tableName = "celllishibean")
public class LiShiBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    private String band;
    private String earfcn;
    private String pci;
    private String rssi;
    private String rsrp;
    private String rsrq;
    private String addPd;
    private boolean isType;
    private int flay;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "LiShiBean{" +
                "id=" + id +
                ", band='" + band + '\'' +
                ", earfcn='" + earfcn + '\'' +
                ", pci='" + pci + '\'' +
                ", rssi='" + rssi + '\'' +
                ", rsrp='" + rsrp + '\'' +
                ", rsrq='" + rsrq + '\'' +
                ", addPd='" + addPd + '\'' +
                ", isType=" + isType +
                ", flay=" + flay +
                '}';
    }

    public LiShiBean() {
    }

    public LiShiBean(int id, String band, String earfcn, String pci, String rssi, String rsrp, String rsrq, String addPd, boolean isType, int flay) {
        this.id = id;
        this.band = band;
        this.earfcn = earfcn;
        this.pci = pci;
        this.rssi = rssi;
        this.rsrp = rsrp;
        this.rsrq = rsrq;
        this.addPd = addPd;
        this.isType = isType;
        this.flay = flay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getRsrp() {
        return rsrp;
    }

    public void setRsrp(String rsrp) {
        this.rsrp = rsrp;
    }

    public String getRsrq() {
        return rsrq;
    }

    public void setRsrq(String rsrq) {
        this.rsrq = rsrq;
    }

    public String getAddPd() {
        return addPd;
    }

    public void setAddPd(String addPd) {
        this.addPd = addPd;
    }

    public boolean isType() {
        return isType;
    }

    public void setType(boolean type) {
        isType = type;
    }

    public int getFlay() {
        return flay;
    }

    public void setFlay(int flay) {
        this.flay = flay;
    }
}
