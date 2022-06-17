package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "adminbean")
public class AdminBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField

    private boolean checkbox;//选中
    @DatabaseField

    private int type;//选中1未选中0

    @DatabaseField
    private String name;//账号

    @Override
    public String toString() {
        return "AdminBean{" +
                "id=" + id +
                ", checkbox=" + checkbox +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @DatabaseField
    private String pwd;//密码
    @DatabaseField
    private String note;//备注

}
