package com.lutong.OrmSqlLite.Bean;

public class LoginBena {

    @Override
    public String toString() {
        return "LoginBena{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 6
     * msg : 登录成功！
     * data : {"userId":1,"userName":"admin","passWord":"21232f297a57a5a743894a0e4a801fc3","token":"32asd","nickName":"smtx","age":18,"type":1,"companyId":1,"times":100}
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
         * userId : 1
         * userName : admin
         * passWord : 21232f297a57a5a743894a0e4a801fc3
         * "editTime": "2021-04-26 09:11:36",
         * token : 32asd
         * nickName : smtx
         * age : 18
         * type : 1
         * companyId : 1
         * times : 100
         */

        private int userId;
        private String userName;
        private String passWord;
        private String token;
        private String nickName;
        private int age;
        private int type;
        private int companyId;
        private int times;
        private String editTime;
        private String total;
        private String deadline;

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", passWord='" + passWord + '\'' +
                    ", token='" + token + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", age=" + age +
                    ", type=" + type +
                    ", companyId=" + companyId +
                    ", times=" + times +
                    ", total='" + total + '\'' +
                    ", remainder='" + remainder + '\'' +
                    '}';
        }

        public String getEditTime() {
            return editTime;
        }

        public void setEditTime(String editTime) {
            this.editTime = editTime;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getRemainder() {
            return remainder;
        }

        public void setRemainder(String remainder) {
            this.remainder = remainder;
        }

        private String remainder;
        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }
}
