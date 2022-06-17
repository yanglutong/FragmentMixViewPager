package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "guijiviewbens")
public class GuijiViewBeanjizhan {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private String mcc;

    @DatabaseField
    private String mnc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @DatabaseField
    private String lac;

    @DatabaseField
    private String ci;

    @DatabaseField
    private String lat;

    @DatabaseField
    private String lon;

    @DatabaseField
    private String radius;

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    @DatabaseField
    private String address;

    @DatabaseField
    private int type;
    @DatabaseField
    private String resources;

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    @Override
    public String toString() {
        return "GuijiViewBeanjizhan{" +
                "id=" + id +
                ", mcc='" + mcc + '\'' +
                ", mnc='" + mnc + '\'' +
                ", lac='" + lac + '\'' +
                ", ci='" + ci + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", radius='" + radius + '\'' +
                ", address='" + address + '\'' +
                ", type=" + type +
                ", resources='" + resources + '\'' +
                ", ta='" + ta + '\'' +
                ", sid='" + sid + '\'' +
                ", band='" + band + '\'' +
                ", types='" + types + '\'' +
                ", pci='" + pci + '\'' +
                ", down='" + down + '\'' +
                '}';
    }

    @DatabaseField
    private String ta;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @DatabaseField
    private String sid;

    @DatabaseField
    private String band;
    @DatabaseField
    private String types;
    @DatabaseField
    private String pci;

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    @DatabaseField
    private String down;
}
