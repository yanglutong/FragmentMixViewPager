package com.lutong.Utils;

public class States {
    String imsi;
    String sb;
    String zb;
    String datatime;
    String time;
    String down;

    @Override
    public String toString() {
        return "States{" +
                "imsi='" + imsi + '\'' +
                ", sb='" + sb + '\'' +
                ", zb='" + zb + '\'' +
                ", datatime='" + datatime + '\'' +
                ", time='" + time + '\'' +
                ", down='" + down + '\'' +
                '}';
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getImsi() {
        return imsi;
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

    public String getZb() {
        return zb;
    }

    public void setZb(String zb) {
        this.zb = zb;
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
}
