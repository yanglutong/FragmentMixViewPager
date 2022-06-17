package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "guijiviewben")
public class GuijiViewBean {

    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    @DatabaseField
    private double lat;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GuijiViewBean{" +
                "id=" + id +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @DatabaseField

    private double lon;
}
