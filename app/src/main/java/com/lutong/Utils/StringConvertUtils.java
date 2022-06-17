package com.lutong.Utils;

/**
 * 字符串转码工具类
 *
 * @author Logan
 * @createDate 2019-04-01
 * @version 1.0.0
 *
 */
public class StringConvertUtils {

    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 转换字符串为16进制字符串
     *
     * @param text 源字符串
     * @return 转义后的16进制字符串
     */
    public static String encode(String text) {
        if (null == text) {
            return null;
        }

        StringBuffer buf = new StringBuffer();
        byte[] bytes = text.getBytes();
        for (byte b : bytes) {
            buf.append(String.format("%02x", b));
        }
        return buf.toString();
    }

    /**
     * 解码转义的16进制字符串
     *
     * @param encodeStr 转义的16进制字符串
     * @return 源字符串
     */
    public static String decode(String encodeStr) {
        if (null == encodeStr) {
            return null;
        }

        // 一个字节会转为两个16进制字符
        if (encodeStr.length() % 2 != 0) {
            throw new IllegalArgumentException("错误的编码字符串");
        }

        byte[] bytes = new byte[encodeStr.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(encodeStr.substring(i * 2, i * 2 + 2), 16);
        }

        return new String(bytes);
    }

    public static void main(String[] args) {
        String s = "A⊙♂()[] {}=BBbb=&&||";
        String encodeStr = encode(s);
        System.out.println(encodeStr);
        String decodeStr = decode(encodeStr);
        System.out.println(decodeStr);
    }

}