package com.lutong;

/**
 * @author: 小杨同志
 * @date: 2022/3/17
 */
public class Constants {
    public static String sendLte = "0addbeef00000000000000537b22574f524b223a312c224d4f44554c45223a5b7b224d4f445f494458223a302c224e45544d4f4445223a224c5445222c2242414e44223a5b312c332c352c382c33342c33382c33392c34302c34315d7d5d7d";
//    public static String sendNr = "0addbeef00000000000000447b22574f524b223a312c224d4f44554c45223a5b7b224d4f445f494458223a302c224e45544d4f4445223a224e52222c2242414e44223a5b34312c37382c37395d7d5d7d";
    public static String sendNr = "0addbeef000000000000004B7b22574f524b223a312c224d4f44554c45223a5b7b224d4f445f494458223a302c224e45544d4f4445223a224e52222c2242414e44223a5b312c332c32382c34312c37372c37385d7d5d7d";
//    public static String sendNr = "7b22574f524b223a312c224d4f44554c45223a5b7b224d4f445f494458223a302c224e45544d4f4445223a224e52222c2242414e44223a5b312c332c32382c34312c37372c37385d7d5d7d";
    public static String sendStop = "0addbeef00000000000000447b22574f524b223a302c224d4f44554c45223a5b7b224d4f445f494458223a302c224e45544d4f4445223a224c5445222c2242414e44223a5b312c332c352c385d7d5d7d";
    public static Integer port = 56969;//端口号

    public static boolean isYd = true;
    public static boolean isLt = true;
    public static boolean isDx = true;
    public static boolean isJzBj = true;
    public static int jzMessage = 300;//基站信息显示时间
    public static int page = 0;//进入后台返回界面时的标记


    public final static String getBaseUrl = "http://www.cellmap.cn/api/cell2gps.aspx";//基站查询
    public final static String getBaseUrl3 = "http://www.cellmap.cn/api/cdma2gps_api.aspx";//基站查询3G
    public final static String appKey = "09f9433abbe7406aa5da1d3290d36c1d";//基站查询key
}
