package com.lutong.OrmSqlLite.Bean;

import android.widget.CheckBox;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cumm01bean")
public class Conmmunit01Bean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private String tac;
    @DatabaseField
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @DatabaseField
    private String pci;
    @DatabaseField
    private String type;//设备的模式      0：默认的定位模式。  1：侦码模式

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getPci() {
        return pci;
    }

    public void setPci(String pci) {
        this.pci = pci;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getUepmax() {
        return uepmax;
    }

    public void setUepmax(String uepmax) {
        this.uepmax = uepmax;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getEnodebpmax() {
        return enodebpmax;
    }

    public void setEnodebpmax(String enodebpmax) {
        this.enodebpmax = enodebpmax;
    }

    @DatabaseField
    private String cid;
    @DatabaseField
    private String uepmax  ;

    @Override
    public String toString() {
        return "Conmmunit01Bean{" +
                "id=" + id +
                ", tac='" + tac + '\'' +
                ", time='" + time + '\'' +
                ", pci='" + pci + '\'' +
                ", type='" + type + '\'' +
                ", cid='" + cid + '\'' +
                ", uepmax='" + uepmax + '\'' +
                ", enodebpmax='" + enodebpmax + '\'' +
                ", cycle='" + cycle + '\'' +
                ", cb=" + cb +
                '}';
    }

    @DatabaseField
    private String enodebpmax;

    public boolean getCb() {
        return cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }

    @DatabaseField
    private String cycle;//抓号周期
    @DatabaseField
    private boolean cb;//抓号周期
}
