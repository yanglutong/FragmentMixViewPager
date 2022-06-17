package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by admin on 2020/4/8.
 */
@DatabaseTable(tableName="zmbean")
public class ZmBean {
    @DatabaseField
    private String imsi;

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getZb() {
        return zb;
    }

    public void setZb(String zb) {
        this.zb = zb;
    }

    public String getDatatime() {
        return datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ZmBean{" +
                "imsi='" + imsi + '\'' +
                ", sb='" + sb + '\'' +
                ", zb='" + zb + '\'' +
                ", datatime='" + datatime + '\'' +
                ", time='" + time + '\'' +
                ", down='" + down + '\'' +
                ", maintype='" + maintype + '\'' +
                ", id=" + id +
                '}';
    }

    @DatabaseField
    private String sb;

    public String getMaintype() {
        return maintype;
    }

    public void setMaintype(String maintype) {
        this.maintype = maintype;
    }

    @DatabaseField
    private String zb;
    @DatabaseField
    private String datatime;
    @DatabaseField
    private String time;
    @DatabaseField
    private String down;
    @DatabaseField
    private String maintype;
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
}
