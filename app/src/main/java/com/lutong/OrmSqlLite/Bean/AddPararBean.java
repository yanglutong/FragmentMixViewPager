package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "addpararbean")
public class AddPararBean {
    private Date date;
    private String zb;//中标

    @Override
    public String toString() {
        return "AddPararBean{" +
                "date=" + date +
                ", zb='" + zb + '\'' +
                ", type='" + type + '\'' +
                ", sb='" + sb + '\'' +
                ", location='" + location + '\'' +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", imsi='" + imsi + '\'' +
                ", yy='" + yy + '\'' +
                ", checkbox=" + checkbox +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getZb() {
        return zb;
    }

    public void setZb(String zb) {
        this.zb = zb;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String sb;
    private String location;

    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    @DatabaseField
    private String imsi;

    @DatabaseField
    private String yy;

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    @DatabaseField

    private boolean checkbox;
}
