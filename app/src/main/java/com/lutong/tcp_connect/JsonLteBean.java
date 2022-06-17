package com.lutong.tcp_connect;

import java.util.List;

/**工模 数据类
 * @author: 小杨同志
 * @date: 2022/3/17
 */
public class JsonLteBean  {

    /**
     * ALTITUDE :
     * BAND : 3
     * CID : 46026774
     * DUPLEX : FDD
     * EARFCN : 1650
     * LATITUDE :
     * LONGITUDE :
     * MAXMOD : 1
     * MODULE : 0
     * NETMODE : LTE
     * PCI : 239
     * PLMN : ["46001"]
     * RSRP : -81.3125
     * SIB3 : {"q_Hyst":4,"q_RxlevMin":-64,"reselPriority":5,"s_IntraSearch":29,"s_NonIntraSearch":24,"threshServing_Low":4}
     * SIB5 : [{"Freq":100,"Priority":6,"ReducedMeasPerformance":1,"RxLevMin":-64,"ThreshX_H":11,"ThreshX_L":11},{"Freq":3740,"Priority":5,"ReducedMeasPerformance":1,"RxLevMin":-64,"ThreshX_H":11,"ThreshX_L":11}]
     * SN : 0
     * SRXLEV : -62
     * TAC : 58182
     * TIME :
     */

    private String ALTITUDE;
    private int BAND;
    private int CID;
    private String DUPLEX;
    private int EARFCN;
    private String LATITUDE;
    private String LONGITUDE;
    private int MAXMOD;
    private int MODULE;
    private String NETMODE;
    private int PCI;
    private double RSRP;
    private SIB3Bean SIB3;
    private int SN;
    private int SRXLEV;
    private int TAC;
    private String TIME;
    private List<String> PLMN;
    private List<SIB5Bean> SIB5;

    @Override
    public String toString() {
        return "JsonLteBean{" +
                "ALTITUDE='" + ALTITUDE + '\'' +
                ", BAND=" + BAND +
                ", CID=" + CID +
                ", DUPLEX='" + DUPLEX + '\'' +
                ", EARFCN=" + EARFCN +
                ", LATITUDE='" + LATITUDE + '\'' +
                ", LONGITUDE='" + LONGITUDE + '\'' +
                ", MAXMOD=" + MAXMOD +
                ", MODULE=" + MODULE +
                ", NETMODE='" + NETMODE + '\'' +
                ", PCI=" + PCI +
                ", RSRP=" + RSRP +
                ", SIB3=" + SIB3 +
                ", SN=" + SN +
                ", SRXLEV=" + SRXLEV +
                ", TAC=" + TAC +
                ", TIME='" + TIME + '\'' +
                ", PLMN=" + PLMN +
                ", SIB5=" + SIB5 +
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

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getDUPLEX() {
        return DUPLEX;
    }

    public void setDUPLEX(String DUPLEX) {
        this.DUPLEX = DUPLEX;
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

    public double getRSRP() {
        return RSRP;
    }

    public void setRSRP(double RSRP) {
        this.RSRP = RSRP;
    }

    public SIB3Bean getSIB3() {
        return SIB3;
    }

    public void setSIB3(SIB3Bean SIB3) {
        this.SIB3 = SIB3;
    }

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public int getSRXLEV() {
        return SRXLEV;
    }

    public void setSRXLEV(int SRXLEV) {
        this.SRXLEV = SRXLEV;
    }

    public int getTAC() {
        return TAC;
    }

    public void setTAC(int TAC) {
        this.TAC = TAC;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public List<String> getPLMN() {
        return PLMN;
    }

    public void setPLMN(List<String> PLMN) {
        this.PLMN = PLMN;
    }

    public List<SIB5Bean> getSIB5() {
        return SIB5;
    }

    public void setSIB5(List<SIB5Bean> SIB5) {
        this.SIB5 = SIB5;
    }



    public static class SIB3Bean {
        @Override
        public String toString() {
            return "SIB3Bean{" +
                    "q_Hyst=" + q_Hyst +
                    ", q_RxlevMin=" + q_RxlevMin +
                    ", reselPriority=" + reselPriority +
                    ", s_IntraSearch=" + s_IntraSearch +
                    ", s_NonIntraSearch=" + s_NonIntraSearch +
                    ", threshServing_Low=" + threshServing_Low +
                    '}';
        }

        /**
         * q_Hyst : 4
         * q_RxlevMin : -64
         * reselPriority : 5
         * s_IntraSearch : 29
         * s_NonIntraSearch : 24
         * threshServing_Low : 4
         */

        private int q_Hyst;
        private int q_RxlevMin;
        private int reselPriority;
        private int s_IntraSearch;
        private int s_NonIntraSearch;
        private int threshServing_Low;

        public int getQ_Hyst() {
            return q_Hyst;
        }

        public void setQ_Hyst(int q_Hyst) {
            this.q_Hyst = q_Hyst;
        }

        public int getQ_RxlevMin() {
            return q_RxlevMin;
        }

        public void setQ_RxlevMin(int q_RxlevMin) {
            this.q_RxlevMin = q_RxlevMin;
        }

        public int getReselPriority() {
            return reselPriority;
        }

        public void setReselPriority(int reselPriority) {
            this.reselPriority = reselPriority;
        }

        public int getS_IntraSearch() {
            return s_IntraSearch;
        }

        public void setS_IntraSearch(int s_IntraSearch) {
            this.s_IntraSearch = s_IntraSearch;
        }

        public int getS_NonIntraSearch() {
            return s_NonIntraSearch;
        }

        public void setS_NonIntraSearch(int s_NonIntraSearch) {
            this.s_NonIntraSearch = s_NonIntraSearch;
        }

        public int getThreshServing_Low() {
            return threshServing_Low;
        }

        public void setThreshServing_Low(int threshServing_Low) {
            this.threshServing_Low = threshServing_Low;
        }
    }

    public static class SIB5Bean {
        @Override
        public String toString() {
            return "SIB5Bean{" +
                    "Freq=" + Freq +
                    ", Priority=" + Priority +
                    ", ReducedMeasPerformance=" + ReducedMeasPerformance +
                    ", RxLevMin=" + RxLevMin +
                    ", ThreshX_H=" + ThreshX_H +
                    ", ThreshX_L=" + ThreshX_L +
                    '}';
        }

        /**
         * Freq : 100
         * Priority : 6
         * ReducedMeasPerformance : 1
         * RxLevMin : -64
         * ThreshX_H : 11
         * ThreshX_L : 11
         */

        private int Freq;
        private int Priority;
        private int ReducedMeasPerformance;
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

        public int getReducedMeasPerformance() {
            return ReducedMeasPerformance;
        }

        public void setReducedMeasPerformance(int ReducedMeasPerformance) {
            this.ReducedMeasPerformance = ReducedMeasPerformance;
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
