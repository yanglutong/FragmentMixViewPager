package com.lutong.OrmSqlLite.Bean;

public class DownBean {
    @Override
    public String toString() {
        return "DownBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 1
     * msg : 成功！
     * data : {"appId":1,"appName":"smmap","appPath":"http://39.107.141.215:81/uploads/com.sm.smmap.0.1.2.apk","appType":1,"versionCode":"2","versionName":"1.2","createTime":"2019-07-12 10:27:53"}
     */

    private int code;
    private String msg;
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
         * appId : 1
         * appName : smmap
         * appPath : http://39.107.141.215:81/uploads/com.sm.smmap.0.1.2.apk
         * appType : 1
         * versionCode : 2
         * versionName : 1.2
         * createTime : 2019-07-12 10:27:53
         */

        private int appId;
        private String appName;

        @Override
        public String toString() {
            return "DataBean{" +
                    "appId=" + appId +
                    ", appName='" + appName + '\'' +
                    ", appPath='" + appPath + '\'' +
                    ", appType=" + appType +
                    ", versionCode='" + versionCode + '\'' +
                    ", versionName='" + versionName + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", newFunction='" + newFunction + '\'' +
                    '}';
        }

        private String appPath;
        private int appType;
        private String versionCode;
        private String versionName;
        private String createTime;
        private String newFunction;
        public int getAppId() {
            return appId;
        }

        public String getNewFunction() {
            return newFunction;
        }

        public void setNewFunction(String newFunction) {
            this.newFunction = newFunction;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppPath() {
            return appPath;
        }

        public void setAppPath(String appPath) {
            this.appPath = appPath;
        }

        public int getAppType() {
            return appType;
        }

        public void setAppType(int appType) {
            this.appType = appType;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
