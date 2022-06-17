package com.lutong.OrmSqlLite.Bean;


public class PararBean {
    private String type;
    private String sb;
    private String location;

    @Override
    public String toString() {
        return "PararBean{" +
                "type='" + type + '\'' +
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

    private int id;

    private String phone;

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


    private String imsi;


    private String yy;

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }



    private boolean checkbox;
}
