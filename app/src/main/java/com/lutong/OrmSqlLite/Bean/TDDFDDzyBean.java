package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by admin on 2020/4/8.
 */
@DatabaseTable(tableName = "tddfddzybean")
public class TDDFDDzyBean {
    @Override
    public String toString() {
        return "TDDFDDzyBean{" +
                "TDDzyd='" + TDDzyd + '\'' +
                ", TDDzyz='" + TDDzyz + '\'' +
                ", TDDzyg='" + TDDzyg + '\'' +
                ", FDDzyd='" + FDDzyd + '\'' +
                ", FDDzyz='" + FDDzyz + '\'' +
                ", FDDzyg='" + FDDzyg + '\'' +
                ", id=" + id +
                '}';
    }

    public String getTDDzyd() {
        return TDDzyd;
    }

    public void setTDDzyd(String TDDzyd) {
        this.TDDzyd = TDDzyd;
    }

    public String getTDDzyz() {
        return TDDzyz;
    }

    public void setTDDzyz(String TDDzyz) {
        this.TDDzyz = TDDzyz;
    }

    public String getTDDzyg() {
        return TDDzyg;
    }

    public void setTDDzyg(String TDDzyg) {
        this.TDDzyg = TDDzyg;
    }

    public String getFDDzyd() {
        return FDDzyd;
    }

    public void setFDDzyd(String FDDzyd) {
        this.FDDzyd = FDDzyd;
    }

    public String getFDDzyz() {
        return FDDzyz;
    }

    public void setFDDzyz(String FDDzyz) {
        this.FDDzyz = FDDzyz;
    }

    public String getFDDzyg() {
        return FDDzyg;
    }

    public void setFDDzyg(String FDDzyg) {
        this.FDDzyg = FDDzyg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DatabaseField
    private String TDDzyd;

    //增益值  低中高
    @DatabaseField
    private String TDDzyz;
    @DatabaseField
    private String TDDzyg;


    @DatabaseField
    private String FDDzyd;

    //增益值  低中高
    @DatabaseField
    private String FDDzyz;
    @DatabaseField
    private String FDDzyg;
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
}
