package com.lutong.OrmSqlLite.Bean;

import java.io.Serializable;
import java.util.List;

public class JzGetData implements Serializable {

    /**
     * code : 1
     * msg : 成功！
     * data : [{"mobileId":27,"type":"室外","companyStationNumber":"SJCHA0131养蛇厂-HLHF","areaName":"SJCHA0131养蛇厂-HLHF_0","eNodeBid":210417,"localAreaMark":0,"areaMark":0,"tac":12498,"band":39,"downFrequencyPoint":38400,"pci":342,"longitude":114.58777,"latitude":38.09162},{"mobileId":28,"type":"室外","companyStationNumber":"SJCHA0131养蛇厂-HLHF","areaName":"SJCHA0131养蛇厂-HLHF_1","eNodeBid":210417,"localAreaMark":1,"areaMark":1,"tac":12498,"band":39,"downFrequencyPoint":38400,"pci":343,"longitude":114.58777,"latitude":38.09162},{"mobileId":29,"type":"室外","companyStationNumber":"SJCHA0131养蛇厂-HLHF","areaName":"SJCHA0131养蛇厂-HLHF_2","eNodeBid":210417,"localAreaMark":2,"areaMark":2,"tac":12498,"band":39,"downFrequencyPoint":38400,"pci":344,"longitude":114.58777,"latitude":38.09162},{"mobileId":558,"type":"室外","companyStationNumber":"SJCHA2630西兆通2-HLHF","areaName":"SJCHA2630西兆通2-HLHF_0","eNodeBid":210241,"localAreaMark":0,"areaMark":0,"tac":12498,"band":39,"downFrequencyPoint":38400,"pci":111,"longitude":114.59327,"latitude":38.06657},{"mobileId":559,"type":"室外","companyStationNumber":"SJCHA2630西兆通2-HLHF","areaName":"SJCHA2630西兆通2-HLHF_1","eNodeBid":210241,"localAreaMark":1,"areaMark":1,"tac":12498,"band":39,"downFrequencyPoint":38350,"pci":112,"longitude":114.59327,"latitude":38.06657},{"mobileId":560,"type":"室外","companyStationNumber":"SJCHA2630西兆通2-HLHF","areaName":"SJCHA2630西兆通2-HLHF_2","eNodeBid":210241,"localAreaMark":2,"areaMark":2,"tac":12498,"band":39,"downFrequencyPoint":38400,"pci":113,"longitude":114.59327,"latitude":38.06657},{"mobileId":19972,"type":"室外","companyStationNumber":"SJCHA2630西兆通2-HLHF","areaName":"SJCHA2630西兆通2-HLHD_3","eNodeBid":210241,"localAreaMark":3,"areaMark":3,"tac":12498,"band":38,"downFrequencyPoint":37900,"pci":81,"longitude":114.59327,"latitude":38.06657},{"mobileId":19973,"type":"室外","companyStationNumber":"SJCHA2630西兆通2-HLHF","areaName":"SJCHA2630西兆通2-HLHD_4","eNodeBid":210241,"localAreaMark":4,"areaMark":4,"tac":12498,"band":38,"downFrequencyPoint":37900,"pci":82,"longitude":114.59327,"latitude":38.06657},{"mobileId":19974,"type":"室外","companyStationNumber":"SJCHA2630西兆通2-HLHF","areaName":"SJCHA2630西兆通2-HLHD_5","eNodeBid":210241,"localAreaMark":5,"areaMark":5,"tac":12498,"band":38,"downFrequencyPoint":37900,"pci":83,"longitude":114.59327,"latitude":38.06657},{"mobileId":36081,"type":"室外","companyStationNumber":"SJCHA9320西兆通2-HNH","areaName":"SJCHA9320西兆通2-HZH-0","eNodeBid":516606,"localAreaMark":3,"areaMark":77,"tac":12498,"band":8,"downFrequencyPoint":3683,"pci":174,"longitude":114.59327,"latitude":38.06657},{"mobileId":36082,"type":"室外","companyStationNumber":"SJCHA9320西兆通2-HNH","areaName":"SJCHA9320西兆通2-HZH-1","eNodeBid":516606,"localAreaMark":4,"areaMark":78,"tac":12498,"band":8,"downFrequencyPoint":3683,"pci":175,"longitude":114.59327,"latitude":38.06657},{"mobileId":36083,"type":"室外","companyStationNumber":"SJCHA9320西兆通2-HNH","areaName":"SJCHA9320西兆通2-HZH-2","eNodeBid":516606,"localAreaMark":5,"areaMark":79,"tac":12498,"band":8,"downFrequencyPoint":3683,"pci":176,"longitude":114.59327,"latitude":38.06657},{"mobileId":39822,"type":"室外","companyStationNumber":"SJLUQ9503凌透-HZH","areaName":"SJLUQ9503凌透-HZH-0","eNodeBid":345267,"localAreaMark":0,"areaMark":64,"tac":12498,"band":8,"downFrequencyPoint":3683,"pci":318,"longitude":114.59343,"latitude":38.07572},{"mobileId":39823,"type":"室外","companyStationNumber":"SJLUQ9503凌透-HZH","areaName":"SJLUQ9503凌透-HZH-1","eNodeBid":345267,"localAreaMark":1,"areaMark":65,"tac":12498,"band":8,"downFrequencyPoint":3683,"pci":319,"longitude":114.59343,"latitude":38.07572},{"mobileId":39824,"type":"室外","companyStationNumber":"SJLUQ9503凌透-HZH","areaName":"SJLUQ9503凌透-HZH-2","eNodeBid":345267,"localAreaMark":2,"areaMark":66,"tac":12498,"band":8,"downFrequencyPoint":3683,"pci":320,"longitude":114.59343,"latitude":38.07572},{"mobileId":40790,"type":"室分","companyStationNumber":"SJZHD9654国展中心-HLS","areaName":"SJZHD9654国展中心-HLSE1-0","eNodeBid":729459,"localAreaMark":0,"areaMark":64,"tac":12446,"band":40,"downFrequencyPoint":38950,"pci":350,"longitude":114.5972,"latitude":38.119608}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JzGetData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * mobileId : 27
         * type : 室外
         * companyStationNumber : SJCHA0131养蛇厂-HLHF
         * areaName : SJCHA0131养蛇厂-HLHF_0
         * eNodeBid : 210417
         * localAreaMark : 0
         * areaMark : 0
         * tac : 12498
         * band : 39
         * downFrequencyPoint : 38400
         * pci : 342
         * longitude : 114.58777
         * latitude : 38.09162
         */

        private int mobileId;
        private String type;
        private String companyStationNumber;
        private String areaName;
        private int eNodeBid;
        private int localAreaMark;
        private int areaMark;
        private int tac;
        private int band;
        private int downFrequencyPoint;
        private int pci;
        private double longitude;
        private double latitude;

        @Override
        public String toString() {
            return "DataBean{" +
                    "mobileId=" + mobileId +
                    ", type='" + type + '\'' +
                    ", companyStationNumber='" + companyStationNumber + '\'' +
                    ", areaName='" + areaName + '\'' +
                    ", eNodeBid=" + eNodeBid +
                    ", localAreaMark=" + localAreaMark +
                    ", areaMark=" + areaMark +
                    ", tac=" + tac +
                    ", band=" + band +
                    ", downFrequencyPoint=" + downFrequencyPoint +
                    ", pci=" + pci +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    ", mnc=" + mnc +
                    '}';
        }

        private int mnc;

        public int getMnc() {
            return mnc;
        }

        public void setMnc(int mnc) {
            this.mnc = mnc;
        }

        public int getMobileId() {
            return mobileId;
        }

        public void setMobileId(int mobileId) {
            this.mobileId = mobileId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCompanyStationNumber() {
            return companyStationNumber;
        }

        public void setCompanyStationNumber(String companyStationNumber) {
            this.companyStationNumber = companyStationNumber;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getENodeBid() {
            return eNodeBid;
        }

        public void setENodeBid(int eNodeBid) {
            this.eNodeBid = eNodeBid;
        }

        public int getLocalAreaMark() {
            return localAreaMark;
        }

        public void setLocalAreaMark(int localAreaMark) {
            this.localAreaMark = localAreaMark;
        }

        public int getAreaMark() {
            return areaMark;
        }

        public void setAreaMark(int areaMark) {
            this.areaMark = areaMark;
        }

        public int getTac() {
            return tac;
        }

        public void setTac(int tac) {
            this.tac = tac;
        }

        public int getBand() {
            return band;
        }

        public void setBand(int band) {
            this.band = band;
        }

        public int getDownFrequencyPoint() {
            return downFrequencyPoint;
        }

        public void setDownFrequencyPoint(int downFrequencyPoint) {
            this.downFrequencyPoint = downFrequencyPoint;
        }

        public int getPci() {
            return pci;
        }

        public void setPci(int pci) {
            this.pci = pci;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }
}
