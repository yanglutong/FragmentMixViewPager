package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "logbean")
public class LogBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    @DatabaseField
    private String associated;//关联ID

    @Override
    public String toString() {
        return "LogBean{" +
                "id=" + id +
                ", associated='" + associated + '\'' +
                ", datetime='" + datetime + '\'' +
                ", person='" + person + '\'' +
                ", pd='" + pd + '\'' +
                ", sb='" + sb + '\'' +
                ", event='" + event + '\'' +
                ", sucessIMSI='" + sucessIMSI + '\'' +
                '}';
    }

    @DatabaseField
    private String datetime;//日期时间

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @DatabaseField
    private String person;//人员

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssociated() {
        return associated;
    }

    public void setAssociated(String associated) {
        this.associated = associated;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPd() {
        return pd;
    }

    public void setPd(String pd) {
        this.pd = pd;
    }

    public String getSb() {
        return sb;
    }

    public void setSb(String sb) {
        this.sb = sb;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSucessIMSI() {
        return sucessIMSI;
    }

    public void setSucessIMSI(String sucessIMSI) {
        this.sucessIMSI = sucessIMSI;
    }

    @DatabaseField
    private String pd;//频点
    @DatabaseField
    private String sb;//设备
    @DatabaseField
    private String event;//事件
    @DatabaseField
    private String sucessIMSI;//事件
}
