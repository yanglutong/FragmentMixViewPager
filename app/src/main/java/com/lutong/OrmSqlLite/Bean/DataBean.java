package com.lutong.OrmSqlLite.Bean;

public class DataBean {


    @Override
    public String toString() {
        return "DataBean{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }

    /**
     * reason : 查询成功
     * result : {"mcc":"460","mnc":"1","lac":"12573","ci":"34464818","lat":"37.978332524689","lon":"114.467399601897","radius":"674","address":"河北省石家庄市桥西区振头街道平和路"}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * mcc : 460
         * mnc : 1
         * lac : 12573
         * ci : 34464818
         * lat : 37.978332524689
         * lon : 114.467399601897
         * radius : 674
         * address : 河北省石家庄市桥西区振头街道平和路
         */

        private String mcc;
        private String mnc;
        private String lac;
        private String ci;
        private String lat;
        private String lon;
        private String radius;
        private String address;

        public String getMcc() {
            return mcc;
        }

        public void setMcc(String mcc) {
            this.mcc = mcc;
        }

        public String getMnc() {
            return mnc;
        }

        public void setMnc(String mnc) {
            this.mnc = mnc;
        }
        public String getLac() {
            return lac;
        }
        public void setLac(String lac) {
            this.lac = lac;
        }

        public String getCi() {
            return ci;
        }

        public void setCi(String ci) {
            this.ci = ci;
        }
        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
