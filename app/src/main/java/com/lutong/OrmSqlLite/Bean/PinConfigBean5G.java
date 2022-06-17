package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pinbean5g")
public class PinConfigBean5G {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private int up;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public String getTf() {
        return tf;
    }

    public void setTf(String tf) {
        this.tf = tf;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public String getPlmn() {
        return plmn;
    }

    public void setPlmn(String plmn) {
        this.plmn = plmn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    @DatabaseField
    private int down;

    public int getGscn() {
        return gscn;
    }

    public void setGscn(int gscn) {
        this.gscn = gscn;
    }

    @DatabaseField
    private String tf;
    @DatabaseField
    private int band;
    @DatabaseField
    private String plmn;
    @DatabaseField
    private int type;

    @Override
    public String toString() {
        return "PinConfigBean5G{" +
                "id=" + id +
                ", up=" + up +
                ", down=" + down +
                ", tf='" + tf + '\'' +
                ", band=" + band +
                ", plmn='" + plmn + '\'' +
                ", type=" + type +
                ", gscn=" + gscn +
                ", yy='" + yy + '\'' +
                ", zhongxinpindian='" + zhongxinpindian + '\'' +
                ", ssb='" + ssb + '\'' +
                '}';
    }

    @DatabaseField
    private int gscn;

    public String getZhongxinpindian() {
        return zhongxinpindian;
    }

    public void setZhongxinpindian(String zhongxinpindian) {
        this.zhongxinpindian = zhongxinpindian;
    }

    public String getSsb() {
        return ssb;
    }

    public void setSsb(String ssb) {
        this.ssb = ssb;
    }

    @DatabaseField
    private  String yy;
    @DatabaseField
    private  String  zhongxinpindian;
    @DatabaseField
    private  String ssb;
}
