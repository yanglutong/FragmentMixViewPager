package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author: 小杨同志
 * @date: 2021/8/26
 */

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@DatabaseTable(tableName = "cellbeannr")
public class CellBeanNr {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    @DatabaseField
    private String  SsSinr;//dbm
    @DatabaseField
    private String  Rsrq;
    @DatabaseField
    private String  Rsrp;
    @DatabaseField
    private String  cid;
    @DatabaseField
    private String  Pci;
    @DatabaseField
    private String  Tac;
    @DatabaseField
    private String  Arfcn;
    @DatabaseField
    private String  MncString;
    @DatabaseField
    private String band;
    @DatabaseField
    private String addArfcn;//添加频点
    @DatabaseField
    private int  i;//添加频点
    @DatabaseField
    private boolean isCellShow;//小区查看还是历史记录

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
    @DatabaseField //显示的最大的长度
    private int lengthMax;

    public int getLengthMax() {
        return lengthMax;
    }

    public void setLengthMax(int lengthMax) {
        this.lengthMax = lengthMax;
    }
    public CellBeanNr() {
        this.Tac="TAC";
        this.cid="ECI";
        this.Pci="PCI";
        this.Arfcn="NR ARFCN";
        this.band="BAND";
        this.Rsrp="RSRP";
        this.Rsrq="RSRQ";
        this.SsSinr="SINR";
        this.addArfcn="添加频点";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSsSinr() {
        return SsSinr;
    }

    public void setSsSinr(String ssSinr) {
        SsSinr = ssSinr;
    }

    public String getRsrq() {
        return Rsrq;
    }

    public void setRsrq(String rsrq) {
        Rsrq = rsrq;
    }

    public String getRsrp() {
        return Rsrp;
    }

    public void setRsrp(String rsrp) {
        Rsrp = rsrp;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    public String getArfcn() {
        return Arfcn;
    }

    public void setArfcn(String arfcn) {
        Arfcn = arfcn;
    }

    public String getMncString() {
        return MncString;
    }

    public void setMncString(String mncString) {
        MncString = mncString;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getAddArfcn() {
        return addArfcn;
    }

    public void setAddArfcn(String addArfcn) {
        this.addArfcn = addArfcn;
    }

    public boolean isCellShow() {
        return isCellShow;
    }

    public void setCellShow(boolean cellShow) {
        isCellShow = cellShow;
    }

    @Override
    public String toString() {
        return "CellBeanNr{" +
                "id=" + id +
                ", SsSinr='" + SsSinr + '\'' +
                ", Rsrq='" + Rsrq + '\'' +
                ", Rsrp='" + Rsrp + '\'' +
                ", cid='" + cid + '\'' +
                ", Pci='" + Pci + '\'' +
                ", Tac='" + Tac + '\'' +
                ", Arfcn='" + Arfcn + '\'' +
                ", MncString='" + MncString + '\'' +
                ", band='" + band + '\'' +
                ", addArfcn='" + addArfcn + '\'' +
                ", i=" + i +
                ", isCellShow=" + isCellShow +
                ", lengthMax=" + lengthMax +
                '}';
    }
}
