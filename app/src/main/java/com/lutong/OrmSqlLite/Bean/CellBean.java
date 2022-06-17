package com.lutong.OrmSqlLite.Bean;

/**存放当前小区和邻小区
 * @author: 小杨同志
 * @date: 2021/8/11
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@DatabaseTable(tableName = "demobean")
public class CellBean {

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
    private String rssi;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String rsrp;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String rsrq;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String sinr;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String addEarfcn;//添加频点
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private int  i;//添加频点
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private boolean isCellLiShi;
    @DatabaseField //显示的最大的长度
    private int lengthMax;

    public int getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public CellBean() {
        this.plmn="PLMN";
        this.tac="TAC";
        this.eci="ECI";
        this.pci="PCI";
        this.earfcn="EARFCN";
        this.band="BAND";
        this.rssi="RSSI";
        this.rsrp="RSRP";
        this.rsrq="RSRQ";
        this.sinr="SINR";
        this.addEarfcn="添加频点";
    }

    public CellBean(String plmn, String tac, String eci, String pci, String earfcn, String band, String rssi, String rsrp, String rsrq, String addEarfcn, boolean isCellLiShi) {
        this.plmn = plmn;
        this.tac = tac;
        this.eci = eci;
        this.pci = pci;
        this.earfcn = earfcn;
        this.band = band;
        this.rssi = rssi;
        this.rsrp = rsrp;
        this.rsrq = rsrq;
        this.addEarfcn=addEarfcn;
        this.isCellLiShi=isCellLiShi;
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

    public String getSinr() {
        return sinr;
    }

    public void setSinr(String sinr) {
        this.sinr = sinr;
    }

    public boolean isCellLiShi() {
        return isCellLiShi;
    }

    public void setCellLiShi(boolean cellLiShi) {
        isCellLiShi = cellLiShi;
    }

    public String getAddEarfcn() {
        return addEarfcn;
    }

    public void setAddEarfcn(String addEarfcn) {
        this.addEarfcn = addEarfcn;
    }

    @Override
    public String toString() {
        return "CellBean{" +
                "id=" + id +
                ", plmn='" + plmn + '\'' +
                ", tac='" + tac + '\'' +
                ", eci='" + eci + '\'' +
                ", pci='" + pci + '\'' +
                ", earfcn='" + earfcn + '\'' +
                ", band='" + band + '\'' +
                ", rssi='" + rssi + '\'' +
                ", rsrp='" + rsrp + '\'' +
                ", rsrq='" + rsrq + '\'' +
                ", sinr='" + sinr + '\'' +
                ", addEarfcn='" + addEarfcn + '\'' +
                ", i=" + i +
                ", isCellLiShi=" + isCellLiShi +
                ", lengthMax=" + lengthMax +
                '}';
    }
}
