package com.lutong.OrmSqlLite.Bean;

public class JzDataQury {

    /**
     * code : 1
     * msg : 成功！
     * data : {"mobileId":7,"type":"室外","companyStationNumber":"SJCHA0089燕赵房地产-HLHD","areaName":"SJCHA0089燕赵房地产-HLHD_0","eNodeBid":197877,"localAreaMark":0,"areaMark":0,"tac":13001,"band":38,"downFrequencyPoint":37900,"pci":372,"longitude":114.50899,"latitude":38.07806,"mnc":0}
     */

    private int code;
    private String msg;

    @Override
    public String toString() {
        return "JzDataQury{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * mobileId : 7
         * type : 室外
         * companyStationNumber : SJCHA0089燕赵房地产-HLHD
         * areaName : SJCHA0089燕赵房地产-HLHD_0
         * eNodeBid : 197877
         * localAreaMark : 0
         * areaMark : 0
         * tac : 13001
         * band : 38
         * downFrequencyPoint : 37900
         * pci : 372
         * longitude : 114.50899
         * latitude : 38.07806
         * mnc : 0
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
        private int mnc;

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

        public int getMnc() {
            return mnc;
        }

        public void setMnc(int mnc) {
            this.mnc = mnc;
        }
    }
}
