package com.lutong.tcp;

import java.util.List;

/**工模 数据类
 * @author: 小杨同志
 * @date: 2022/2/15
 */
public class JsonNrBean {

    /**
     * ALTITUDE :
     * BAND : 78
     * BANDWIDTH : 100
     * CARRIERCENTERARFCN : 630000
     * EARFCN : 627264
     * LATITUDE :
     * LONGITUDE :
     * MAXMOD : 1
     * MODE : TDD
     * MODULE : 0
     * NETMODE : NR
     * PCI : 248
     * PLMNLIST : [{"CID":9127043074,"PLMN":"46001","TAC":2228225},{"CID":9127043074,"PLMN":"46011","TAC":2228225}]
     * POINTA : 626724
     * PRIORITY : 7
     * RSRP : -113.064003
     * SIB4 : [{"Freq":633984,"Priority":7,"RxLevMin":-64,"ThreshX_H":11,"ThreshX_L":11}]
     * SN : 0
     * TIME :
     */

    private String ALTITUDE;
    private int BAND;
    private int BANDWIDTH;
    private int CARRIERCENTERARFCN;
    private int EARFCN;
    private String LATITUDE;
    private String LONGITUDE;
    private int MAXMOD;
    private String MODE;
    private int MODULE;
    private String NETMODE;
    private int PCI;
    private int POINTA;
    private int PRIORITY;
    private double RSRP;
    private int SN;
    private String TIME;
    private List<PLMNLISTBean> PLMNLIST;
    private List<SIB4Bean> SIB4;

    @Override
    public String toString() {
        return "JsonNrBean{" +
                "ALTITUDE='" + ALTITUDE + '\'' +
                ", BAND=" + BAND +
                ", BANDWIDTH=" + BANDWIDTH +
                ", CARRIERCENTERARFCN=" + CARRIERCENTERARFCN +
                ", EARFCN=" + EARFCN +
                ", LATITUDE='" + LATITUDE + '\'' +
                ", LONGITUDE='" + LONGITUDE + '\'' +
                ", MAXMOD=" + MAXMOD +
                ", MODE='" + MODE + '\'' +
                ", MODULE=" + MODULE +
                ", NETMODE='" + NETMODE + '\'' +
                ", PCI=" + PCI +
                ", POINTA=" + POINTA +
                ", PRIORITY=" + PRIORITY +
                ", RSRP=" + RSRP +
                ", SN=" + SN +
                ", TIME='" + TIME + '\'' +
                ", PLMNLIST=" + PLMNLIST +
                ", SIB4=" + SIB4 +
                '}';
    }

    public String getALTITUDE() {
        return ALTITUDE;
    }

    public void setALTITUDE(String ALTITUDE) {
        this.ALTITUDE = ALTITUDE;
    }

    public int getBAND() {
        return BAND;
    }

    public void setBAND(int BAND) {
        this.BAND = BAND;
    }

    public int getBANDWIDTH() {
        return BANDWIDTH;
    }

    public void setBANDWIDTH(int BANDWIDTH) {
        this.BANDWIDTH = BANDWIDTH;
    }

    public int getCARRIERCENTERARFCN() {
        return CARRIERCENTERARFCN;
    }

    public void setCARRIERCENTERARFCN(int CARRIERCENTERARFCN) {
        this.CARRIERCENTERARFCN = CARRIERCENTERARFCN;
    }

    public int getEARFCN() {
        return EARFCN;
    }

    public void setEARFCN(int EARFCN) {
        this.EARFCN = EARFCN;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public int getMAXMOD() {
        return MAXMOD;
    }

    public void setMAXMOD(int MAXMOD) {
        this.MAXMOD = MAXMOD;
    }

    public String getMODE() {
        return MODE;
    }

    public void setMODE(String MODE) {
        this.MODE = MODE;
    }

    public int getMODULE() {
        return MODULE;
    }

    public void setMODULE(int MODULE) {
        this.MODULE = MODULE;
    }

    public String getNETMODE() {
        return NETMODE;
    }

    public void setNETMODE(String NETMODE) {
        this.NETMODE = NETMODE;
    }

    public int getPCI() {
        return PCI;
    }

    public void setPCI(int PCI) {
        this.PCI = PCI;
    }

    public int getPOINTA() {
        return POINTA;
    }

    public void setPOINTA(int POINTA) {
        this.POINTA = POINTA;
    }

    public int getPRIORITY() {
        return PRIORITY;
    }

    public void setPRIORITY(int PRIORITY) {
        this.PRIORITY = PRIORITY;
    }

    public double getRSRP() {
        return RSRP;
    }

    public void setRSRP(double RSRP) {
        this.RSRP = RSRP;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public List<PLMNLISTBean> getPLMNLIST() {
        return PLMNLIST;
    }

    public void setPLMNLIST(List<PLMNLISTBean> PLMNLIST) {
        this.PLMNLIST = PLMNLIST;
    }

    public List<SIB4Bean> getSIB4() {
        return SIB4;
    }

    public void setSIB4(List<SIB4Bean> SIB4) {
        this.SIB4 = SIB4;
    }

    public static class PLMNLISTBean {
        @Override
        public String toString() {
            return "PLMNLISTBean{" +
                    "CID=" + CID +
                    ", PLMN='" + PLMN + '\'' +
                    ", TAC=" + TAC +
                    '}';
        }

        /**
         * CID : 9127043074
         * PLMN : 46001
         * TAC : 2228225
         */

        private long CID;
        private String PLMN;
        private int TAC;

        public long getCID() {
            return CID;
        }

        public void setCID(long CID) {
            this.CID = CID;
        }

        public String getPLMN() {
            return PLMN;
        }

        public void setPLMN(String PLMN) {
            this.PLMN = PLMN;
        }

        public int getTAC() {
            return TAC;
        }

        public void setTAC(int TAC) {
            this.TAC = TAC;
        }
    }

    public static class SIB4Bean {
        @Override
        public String toString() {
            return "SIB4Bean{" +
                    "Freq=" + Freq +
                    ", Priority=" + Priority +
                    ", RxLevMin=" + RxLevMin +
                    ", ThreshX_H=" + ThreshX_H +
                    ", ThreshX_L=" + ThreshX_L +
                    '}';
        }

        /**
         * Freq : 633984
         * Priority : 7
         * RxLevMin : -64
         * ThreshX_H : 11
         * ThreshX_L : 11
         */

        private int Freq;
        private int Priority;
        private int RxLevMin;
        private int ThreshX_H;
        private int ThreshX_L;

        public int getFreq() {
            return Freq;
        }

        public void setFreq(int Freq) {
            this.Freq = Freq;
        }

        public int getPriority() {
            return Priority;
        }

        public void setPriority(int Priority) {
            this.Priority = Priority;
        }

        public int getRxLevMin() {
            return RxLevMin;
        }

        public void setRxLevMin(int RxLevMin) {
            this.RxLevMin = RxLevMin;
        }

        public int getThreshX_H() {
            return ThreshX_H;
        }

        public void setThreshX_H(int ThreshX_H) {
            this.ThreshX_H = ThreshX_H;
        }

        public int getThreshX_L() {
            return ThreshX_L;
        }

        public void setThreshX_L(int ThreshX_L) {
            this.ThreshX_L = ThreshX_L;
        }
    }
}
