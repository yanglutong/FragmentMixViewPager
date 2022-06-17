package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author: 小杨同志
 * @date: 2021/9/1
 */
@DatabaseTable(tableName = "devicepd")
public class DevicePdBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String plmn;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String tac;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String eci;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String pci;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String earfcn;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String band;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String rsrp;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String rsrq;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String position;

    public DevicePdBean() {
    }

    public DevicePdBean(String plmn, String tac, String eci, String pci, String earfcn, String band, String rsrp, String rsrq, String position) {
        this.plmn = plmn;
        this.tac = tac;
        this.eci = eci;
        this.pci = pci;
        this.earfcn = earfcn;
        this.band = band;
        this.rsrp = rsrp;
        this.rsrq = rsrq;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlmn() {
        return plmn;
    }

    public void setPlmn(String plmn) {
        this.plmn = plmn;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getEci() {
        return eci;
    }

    public void setEci(String eci) {
        this.eci = eci;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getEarfcn() {
        return earfcn;
    }

    public void setEarfcn(String earfcn) {
        this.earfcn = earfcn;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "DevicePdBean{" +
                "id=" + id +
                ", plmn='" + plmn + '\'' +
                ", tac='" + tac + '\'' +
                ", eci='" + eci + '\'' +
                ", pci='" + pci + '\'' +
                ", earfcn='" + earfcn + '\'' +
                ", band='" + band + '\'' +
                ", rsrp='" + rsrp + '\'' +
                ", rsrq='" + rsrq + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
