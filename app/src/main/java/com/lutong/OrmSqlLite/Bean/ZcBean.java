package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "zcbean")
public class ZcBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private String date;
    @DatabaseField
    private String zhucema;

    public String getZhucema() {
        return zhucema;
    }

    @Override
    public String toString() {
        return "ZcBean{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", zhucema='" + zhucema + '\'' +
                ", check='" + check + '\'' +
                '}';
    }

    public void setZhucema(String zhucema) {
        this.zhucema = zhucema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    @DatabaseField
    private String check;
}
