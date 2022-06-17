package com.lutong.Utils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 进制转换工具
 * @author: 小杨同志
 * @date: 2021/6/1
 */
public class JK {
    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        int len = hexString.length();
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            bytes[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return bytes;
    }
    /**
     * 16进制直接转换成为字符串 包含\r\n \t

     * @explain

//     * @param hexStr 16进制字符串

     * @return String (字符集：UTF-8)

     */

    public static String fromHexString(String hexString) throws Exception {
// 用于接收转换结果

        String result = "";

// 转大写

        hexString = hexString.toUpperCase();

// 16进制字符

        String hexDigital = "0123456789ABCDEF";

// 将16进制字符串转换成char数组

        char[] hexs = hexString.toCharArray();

// 能被16整除，肯定可以被2整除

        byte[] bytes = new byte[hexString.length() / 2];

        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = hexDigital.indexOf(hexs[2 * i]) * 16 + hexDigital.indexOf(hexs[2 * i + 1]);

            bytes[i] = (byte) (n & 0xff);

        }

// byte[]-->String

        result = new String(bytes, "UTF-8");

        return result;

    }

    /**
     包含\r\n \t
     * 字符串转换成为16进制字符串(大写)

     * @explain 因为java转义字符串在java中有着特殊的意义，

     * 所以当字符串中包含转义字符串，并将其转换成16进制后，16进制再转成String时，会出问题：

     * java会将其当做转义字符串所代表的含义解析出来

     * @param str 字符串(去除java转义字符)

     * @return 16进制字符串

     * @throws Exception

     */

    public static String toHexString(String str) {
// 用于接收转换结果

        String hexString = "";

// 1.校验是否包含特殊字符内容

// java特殊转义符

// String[] escapeArray = {"\b","\t","\n","\f","\r","\'","\"","\\"};

        String[] escapeArray = {"\b","\t","\n","\f","\r"};

// 用于校验参数是否包含特殊转义符
//        boolean flag = false;----1

// 迭代

        for (String esacapeStr : escapeArray) {
// 一真则真

            if (str.contains(esacapeStr)) {
                //如果有包含的就转成自己想要的
                //\t  09  \r\n 0d 0a



//                flag = true;----2
//                break;// 终止循环----3
            }

        }

// 包含特殊字符

//        if (flag) throw new Exception("参数字符串不能包含转义字符！"); ----4

// 16进制字符

        char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        StringBuilder sb = new StringBuilder();

// String-->byte[]

        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;

            sb.append(hexArray[bit]);

            bit = bs[i] & 0x0f;

            sb.append(hexArray[bit]);

        }

        hexString = sb.toString();

        return hexString;

    }


    /**
     * 将十进制转换成16进制
     *
     * @param
     * @return
     */
    public static String hex10To16(int valueTen){
        return String.format("%08X",valueTen);
    }

//    /**
//     * 将16进制转换成十进制
//     *
//     * @param
//     * @return
//     */
//    public static int hex16To10(String strHex){
//        BigInteger integer = new BigInteger(strHex,16);
//        return integer.intValue();
//    }
    /**
     * 将16进制转换成十进制
     *
     * @param
     * @return
     */
    public static String hex16To10(String strHex){
        BigInteger integer = new BigInteger(strHex,16);
        return integer.intValue()+"";
    }
    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     **/
    public static String conver2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2) + ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }

    /**
     * 二进制字符串转换为byte数组,每个字节以","隔开
     **/
    public static byte[] conver2HexToByte(String hex2Str) {
        String[] temp = hex2Str.split(",");
        byte[] b = new byte[temp.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp[i], 2).byteValue();
        }
        return b;
    }

    /**
     * 十六进制转换字符串
     //     * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr)
    {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++)
        {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * byte数组转换为十六进制的字符串
     **/
    public static String conver16HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((b[i] & 0xff) < 0x10)
                result.append("0");
            result.append(Long.toString(b[i] & 0xff, 16));
        }
        return result.toString().toUpperCase();
    }

    /**
     * 十六进制的字符串转换为byte数组
     **/
    public static byte[] conver16HexToByte(String hex16Str) {
        char[] c = hex16Str.toCharArray();
        byte[] b = new byte[c.length / 2];
        for (int i = 0; i < b.length; i++) {
            int pos = i * 2;
            b[i] = (byte) ("0123456789ABCDEF".indexOf(c[pos]) << 4 | "0123456789ABCDEF".indexOf(c[pos + 1]));
        }
        return b;
    }
    /**
     * 获取时间 小时:分;秒 HH:mm:ss
     *
     * @return
     */
    public static String getTimeShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
