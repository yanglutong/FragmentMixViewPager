package com.lutong.tcp;

/**工模 数据类
 * @author: 小杨同志
 * @date: 2022/3/16
 */
public class RecJsonBean {
    public String tv_arfcn;
    public String tv_cj_time;
    public String tv_plmn;
    public String tv_rsrp;
    public String tv_tac;
    public String tv_tdd;
    public int tv_yxj;
    public String tv_NetWorkType;
    public String tv_pci;
    public String tv_cid;
    public String tv_band;
    public int index;
    public String NrCenterArfcn;
    public boolean jzState;
    public boolean jzBjState;

    public RecJsonBean(String tv_arfcn, String tv_cj_time, String tv_plmn, String tv_rsrp, String tv_tac, String tv_tdd, int tv_yxj, String tv_NetWorkType, String tv_pci, String tv_cid, String tv_band) {
        this.tv_arfcn = tv_arfcn;
        this.tv_cj_time = tv_cj_time;
        this.tv_plmn = tv_plmn;
        this.tv_rsrp = tv_rsrp;
        this.tv_tac = tv_tac;
        this.tv_tdd = tv_tdd;
        this.tv_yxj = tv_yxj;
        this.tv_NetWorkType = tv_NetWorkType;
        this.tv_pci = tv_pci;
        this.tv_cid = tv_cid;
        this.tv_band = tv_band;
    }

    public RecJsonBean(String tv_arfcn, String tv_cj_time, String tv_plmn, String tv_rsrp, String tv_tac, String tv_tdd, int tv_yxj, String tv_NetWorkType, String tv_pci, String tv_cid, String tv_band, String nrCenterArfcn) {
        this.tv_arfcn = tv_arfcn;
        this.tv_cj_time = tv_cj_time;
        this.tv_plmn = tv_plmn;
        this.tv_rsrp = tv_rsrp;
        this.tv_tac = tv_tac;
        this.tv_tdd = tv_tdd;
        this.tv_yxj = tv_yxj;
        this.tv_NetWorkType = tv_NetWorkType;
        this.tv_pci = tv_pci;
        this.tv_cid = tv_cid;
        this.tv_band = tv_band;
        this.NrCenterArfcn = nrCenterArfcn;
    }

    public String getNrCenterArfcn() {
        return NrCenterArfcn;
    }

    public void setNrCenterArfcn(String nrCenterArfcn) {
        NrCenterArfcn = nrCenterArfcn;
    }

    public boolean isJzBjState() {
        return jzBjState;
    }

    public void setJzBjState(boolean jzBjState) {
        this.jzBjState = jzBjState;
    }


    public boolean isJzState() {
        return jzState;
    }

    public void setJzState(boolean jzState) {
        this.jzState = jzState;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTv_arfcn() {
        return tv_arfcn;
    }

    public void setTv_arfcn(String tv_arfcn) {
        this.tv_arfcn = tv_arfcn;
    }

    public String getTv_cj_time() {
        return tv_cj_time;
    }

    public void setTv_cj_time(String tv_cj_time) {
        this.tv_cj_time = tv_cj_time;
    }

    public String getTv_plmn() {
        return tv_plmn;
    }

    public void setTv_plmn(String tv_plmn) {
        this.tv_plmn = tv_plmn;
    }

    public String getTv_rsrp() {
        return tv_rsrp;
    }

    public void setTv_rsrp(String tv_rsrp) {
        this.tv_rsrp = tv_rsrp;
    }

    public String getTv_tac() {
        return tv_tac;
    }

    public void setTv_tac(String tv_tac) {
        this.tv_tac = tv_tac;
    }

    public String getTv_tdd() {
        return tv_tdd;
    }

    public void setTv_tdd(String tv_tdd) {
        this.tv_tdd = tv_tdd;
    }

    public int getTv_yxj() {
        return tv_yxj;
    }

    public void setTv_yxj(int tv_yxj) {
        this.tv_yxj = tv_yxj;
    }

    public String getTv_NetWorkType() {
        return tv_NetWorkType;
    }

    public void setTv_NetWorkType(String tv_NetWorkType) {
        this.tv_NetWorkType = tv_NetWorkType;
    }

    public String getTv_pci() {
        return tv_pci;
    }

    public void setTv_pci(String tv_pci) {
        this.tv_pci = tv_pci;
    }

    public String getTv_cid() {
        return tv_cid;
    }

    public void setTv_cid(String tv_cid) {
        this.tv_cid = tv_cid;
    }

    public String getTv_band() {
        return tv_band;
    }

    public void setTv_band(String tv_band) {
        this.tv_band = tv_band;
    }

    @Override
    public String toString() {
        return "RecJsonBean{" +
                "tv_arfcn='" + tv_arfcn + '\'' +
                ", tv_cj_time='" + tv_cj_time + '\'' +
                ", tv_plmn='" + tv_plmn + '\'' +
                ", tv_rsrp='" + tv_rsrp + '\'' +
                ", tv_tac='" + tv_tac + '\'' +
                ", tv_tdd='" + tv_tdd + '\'' +
                ", tv_yxj=" + tv_yxj +
                ", tv_NetWorkType='" + tv_NetWorkType + '\'' +
                ", tv_pci='" + tv_pci + '\'' +
                ", tv_cid='" + tv_cid + '\'' +
                ", tv_band='" + tv_band + '\'' +
                ", index=" + index +
                ", jzBjState=" + jzBjState +
                '}';
    }
}
