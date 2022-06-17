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
@DatabaseTable(tableName = "cellbeangsm")
public class CellBeanGSM {
    public CellBeanGSM() {
        this.Cid="CI";
        this.dbmRXL="RXLEV";
        this.Arfcn="ARFCN";
        this.Lac="LAC";
        this.Bsic="BSIC";
        this.addArfcn="添加频点";
    }
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
     private int id;
    @DatabaseField
     private String  dbmRXL;//dbm
    @DatabaseField
     private String  Lac;
    @DatabaseField
     private String  Cid;
    @DatabaseField
     private String  Bsic;
    @DatabaseField
     private String  Arfcn;
    @DatabaseField
     private String  MncString;
    @DatabaseField
     private String band;
    @DatabaseField
     private String addArfcn;//添加频点
    @DatabaseField
     private String GSM;//区分网络类型
    @DatabaseField
     private boolean isCellShow;//小区查看还是历史记录
    @DatabaseField
     private int  i;//小区查看还是历史记录
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDbmRXL() {
        return dbmRXL;
    }

    public void setDbmRXL(String dbmRXL) {
        this.dbmRXL = dbmRXL;
    }

    public String getLac() {
        return Lac;
    }

    public void setLac(String lac) {
        Lac = lac;
    }

    @Override
    public String toString() {
        return "CellBeanGSM{" +
                "id=" + id +
                ", dbmRXL='" + dbmRXL + '\'' +
                ", Lac='" + Lac + '\'' +
                ", Cid='" + Cid + '\'' +
                ", Bsic='" + Bsic + '\'' +
                ", Arfcn='" + Arfcn + '\'' +
                ", MncString='" + MncString + '\'' +
                ", band='" + band + '\'' +
                ", addArfcn='" + addArfcn + '\'' +
                ", GSM='" + GSM + '\'' +
                ", isCellShow=" + isCellShow +
                ", i=" + i +
                ", lengthMax=" + lengthMax +
                '}';
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getBsic() {
        return Bsic;
    }

    public void setBsic(String bsic) {
        Bsic = bsic;
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

    public String getGSM() {
        return GSM;
    }

    public void setGSM(String GSM) {
        this.GSM = GSM;
    }

    public boolean isCellShow() {
        return isCellShow;
    }

    public void setCellShow(boolean cellShow) {
        isCellShow = cellShow;
    }
}
