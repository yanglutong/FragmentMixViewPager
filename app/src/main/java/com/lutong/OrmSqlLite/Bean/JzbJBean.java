package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author: 小杨同志
 * @date: 2021/8/3
 */
@DatabaseTable(tableName = "JzbJBean")
public class JzbJBean {
    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String tac;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String cid;
    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private int  count;

    public JzbJBean() {
    }

    public JzbJBean(String tac, String cid, int count) {
        this.tac = tac;
        this.cid = cid;
        this.count = count;
    }

    @Override
    public String toString() {
        return "JzbJBean{" +
                "id=" + id +
                ", tac='" + tac + '\'' +
                ", cid='" + cid + '\'' +
                ", count=" + count +
                '}';
    }

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
