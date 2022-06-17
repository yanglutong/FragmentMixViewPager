package com.lutong.OrmSqlLite.Bean;

public class DataAliBean {
    @Override
    public String toString() {
        return "DataAliBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * status : 0
     * msg : ok
     * result : {"lat":"39.65280914","lng":"115.93230438","addr":"北京市房山区周口店地区城一锅(房宜路),新街路与房易路路口东南409.97米","accuracy":500}
     */

    private int status;
    private String msg;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", addr='" + addr + '\'' +
                    ", accuracy=" + accuracy +
                    '}';
        }

        /**
         * lat : 39.65280914
         * lng : 115.93230438
         * addr : 北京市房山区周口店地区城一锅(房宜路),新街路与房易路路口东南409.97米
         * accuracy : 500
         */

        private String lat;
        private String lng;
        private String addr;
        private int accuracy;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public int getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(int accuracy) {
            this.accuracy = accuracy;
        }
    }
}
