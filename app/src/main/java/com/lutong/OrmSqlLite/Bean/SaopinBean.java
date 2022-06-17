package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "saopinbean")
public class SaopinBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    public int getCheck() {
        return check;
    }

    @Override
    public String toString() {
        return "SaopinBean{" +
                "id=" + id +
                ", up=" + up +
                ", check=" + check +
                ", down=" + down +
                ", tf='" + tf + '\'' +
                ", band=" + band +
                ", plmn='" + plmn + '\'' +
                ", type=" + type +
                ", yy='" + yy + '\'' +
                '}';
    }

    public void setCheck(int check) {
        this.check = check;
    }

    @DatabaseField
    private int up;
    @DatabaseField
    private int check;
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

    @DatabaseField
    private String tf;
    @DatabaseField
    private int band;
    @DatabaseField
    private String plmn;
    @DatabaseField
    private int type;

    @DatabaseField
    private  String yy;
}
