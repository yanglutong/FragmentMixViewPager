package com.lutong.Utils;

import android.util.Log;

public class MainUtils2 {
    public static String YY(String imsi) {

        String s = "";
        if (imsi.startsWith("46000")) {//因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
            //中国移动
            Log.d("aimsistartsWith", "init: 中国移动");
            s = "移动";
            return s;
        } else if (imsi.startsWith("46001")) {
            //中国联通
            Log.d("aimsistartsWith", "init: 中国联通");
            s = "联通";

        } else if (imsi.startsWith("46003") || (imsi.startsWith("46011"))) {
            //中国电信
            Log.d("aimsistartsWith", "init: 中国电信");
            s = "电信";
        }
        return s;
    }

    public static StringBuffer blacklistCount(String str){

        StringBuffer buffer = new StringBuffer(str);
        for(int i = buffer.length(); i<4; i++){
            buffer.insert(0,"0");
        }
        return buffer;
    }
    //字符串分割成两个字符一组，倒序拼接到一起
    public static StringBuffer StringPin(StringBuffer str){

        String [] bands = new String[str.length()/2];
        for(int i=0;i<str.length();i+=2){
            bands[i/2] = str.substring(i,i+2);
        }

        StringBuffer str1 = new StringBuffer();
        for(int i=bands.length-1;i>=0;i--){
            str1.append(bands[i]);
        }
        return str1;
    }

    //手机上的IMSI号转换成指令中的IMSI号
    public static StringBuffer toIMSI(String str){

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuffer sb = new StringBuffer("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);

        }
        return sb;
    }

}
