package com.lutong.OrmSqlLite.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2018/3/22 0022.
 */
@DatabaseTable(tableName = "student")
public class Student {
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", className='" + className + '\'' +
                ", sex='" + sex + '\'' +
                ", userId='" + userId + '\'' +
                ", jcjlid='" + jcjlid + '\'' +
                ", bdzid='" + bdzid + '\'' +
                ", lineid='" + lineid + '\'' +
                ", fzlinename='" + fzlinename + '\'' +
                ", equiptypecode='" + equiptypecode + '\'' +
                ", jlgh='" + jlgh + '\'' +
                ", jlweather='" + jlweather + '\'' +
                ", jlwd='" + jlwd + '\'' +
                ", jlsd='" + jlsd + '\'' +
                ", jlfl='" + jlfl + '\'' +
                ", jldb='" + jldb + '\'' +
                ", jljl='" + jljl + '\'' +
                ", jldytz='" + jldytz + '\'' +
                ", qxcode='" + qxcode + '\'' +
                ", qxlevel='" + qxlevel + '\'' +
                ", jluser='" + jluser + '\'' +
                ", ivbianhao=" + ivbianhao +
                ", ivquanjing=" + ivquanjing +
                ", ivquyu=" + ivquyu +
                ", ivjubu=" + ivjubu +
                ", jldate='" + jldate + '\'' +
                ", qxdesc='" + qxdesc + '\'' +
                ", jljiel='" + jljiel + '\'' +
                ", jljy='" + jljy + '\'' +
                ", jingdu='" + jingdu + '\'' +
                ", weidu='" + weidu + '\'' +
                ", orgid='" + orgid + '\'' +
                '}';
    }

    @DatabaseField //只有添加这个注释，才能把此属性添加到表中的字段
    private String name;

    @DatabaseField(generatedId = true) //generatedId = true 表示自增长的主键
    private int id;

    @DatabaseField
    private String className;
    @DatabaseField
    private String sex;
    /**
     * save
     */
    @DatabaseField
    private String userId;//用户id
    @DatabaseField
    private String jcjlid;//检测记录id（0：新增；非0：修改）
    @DatabaseField
    private String bdzid;//变电站id
    @DatabaseField
    private String lineid;//线路id
    @DatabaseField
    private String fzlinename;//分支名称
    @DatabaseField
    private String equiptypecode;//设备种类id
    @DatabaseField
    private String jlgh;// 杆号

    @DatabaseField
    private String jlweather;//检测记录天气
    @DatabaseField
    private String jlwd;//温度

    @DatabaseField
    private String jlsd;//湿度

    @DatabaseField
    private String jlfl;//风力

    @DatabaseField
    private String jldb;// db值
    @DatabaseField
    private String jljl;//检测距离
    @DatabaseField
    private String jldytz;//地域特征
    @DatabaseField
    private String qxcode;//缺陷种类
    @DatabaseField
    private String qxlevel;//缺陷类别
    @DatabaseField

    private String jluser;//检测人员

    public String getIvbianhao() {
        return ivbianhao;
    }

    public void setIvbianhao(String ivbianhao) {
        this.ivbianhao = ivbianhao;
    }

    public String getIvquanjing() {
        return ivquanjing;
    }

    public void setIvquanjing(String ivquanjing) {
        this.ivquanjing = ivquanjing;
    }

    public String getIvquyu() {
        return ivquyu;
    }

    public void setIvquyu(String ivquyu) {
        this.ivquyu = ivquyu;
    }

    public String getIvjubu() {
        return ivjubu;
    }

    public void setIvjubu(String ivjubu) {
        this.ivjubu = ivjubu;
    }

    @DatabaseField
    private String ivboxing;

    public String getIvboxing() {
        return ivboxing;
    }

    public void setIvboxing(String ivboxing) {
        this.ivboxing = ivboxing;
    }

    @DatabaseField
    private String ivbianhao;//杆塔编号
    @DatabaseField

    private String ivquanjing;//杆塔全景
    @DatabaseField
    private String ivquyu;//杆塔区域
    @DatabaseField
    private String ivjubu;//杆塔局部


    @DatabaseField
    private String jldate;//检测日期
    @DatabaseField
    private String qxdesc;//缺陷描述
    @DatabaseField
    private String jljiel;//检测结论
    @DatabaseField
    private String jljy;//检测建议
    @DatabaseField
    private String jingdu;//经度
    @DatabaseField
    private String weidu;//纬度

    public String getJingdu() {
        return jingdu;
    }

    public void setJingdu(String jingdu) {
        this.jingdu = jingdu;
    }

    public String getWeidu() {
        return weidu;
    }

    public void setWeidu(String weidu) {
        this.weidu = weidu;
    }

    @DatabaseField
    private String orgid;//单位id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJcjlid() {
        return jcjlid;
    }

    public void setJcjlid(String jcjlid) {
        this.jcjlid = jcjlid;
    }

    public String getBdzid() {
        return bdzid;
    }

    public void setBdzid(String bdzid) {
        this.bdzid = bdzid;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public String getFzlinename() {
        return fzlinename;
    }

    public void setFzlinename(String fzlinename) {
        this.fzlinename = fzlinename;
    }

    public String getEquiptypecode() {
        return equiptypecode;
    }

    public void setEquiptypecode(String equiptypecode) {
        this.equiptypecode = equiptypecode;
    }

    public String getJlgh() {
        return jlgh;
    }

    public void setJlgh(String jlgh) {
        this.jlgh = jlgh;
    }

    public String getJlweather() {
        return jlweather;
    }

    public void setJlweather(String jlweather) {
        this.jlweather = jlweather;
    }

    public String getJlwd() {
        return jlwd;
    }

    public void setJlwd(String jlwd) {
        this.jlwd = jlwd;
    }

    public String getJlsd() {
        return jlsd;
    }

    public void setJlsd(String jlsd) {
        this.jlsd = jlsd;
    }

    public String getJlfl() {
        return jlfl;
    }

    public void setJlfl(String jlfl) {
        this.jlfl = jlfl;
    }

    public String getJldb() {
        return jldb;
    }

    public void setJldb(String jldb) {
        this.jldb = jldb;
    }

    public String getJljl() {
        return jljl;
    }

    public void setJljl(String jljl) {
        this.jljl = jljl;
    }

    public String getJldytz() {
        return jldytz;
    }

    public void setJldytz(String jldytz) {
        this.jldytz = jldytz;
    }

    public String getQxcode() {
        return qxcode;
    }

    public void setQxcode(String qxcode) {
        this.qxcode = qxcode;
    }

    public String getQxlevel() {
        return qxlevel;
    }

    public void setQxlevel(String qxlevel) {
        this.qxlevel = qxlevel;
    }

    public String getJluser() {
        return jluser;
    }

    public void setJluser(String jluser) {
        this.jluser = jluser;
    }

    public String getJldate() {
        return jldate;
    }

    public void setJldate(String jldate) {
        this.jldate = jldate;
    }

    public String getQxdesc() {
        return qxdesc;
    }

    public void setQxdesc(String qxdesc) {
        this.qxdesc = qxdesc;
    }

    public String getJljiel() {
        return jljiel;
    }

    public void setJljiel(String jljiel) {
        this.jljiel = jljiel;
    }

    public String getJljy() {
        return jljy;
    }

    public void setJljy(String jljy) {
        this.jljy = jljy;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }
}
