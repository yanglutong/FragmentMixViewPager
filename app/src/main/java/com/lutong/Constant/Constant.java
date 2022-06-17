package com.lutong.Constant;



import com.lutong.OrmSqlLite.Bean.SpBean;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static boolean isCell=true;//是邻小区或历史记录
    public static int TIMEOUT=10;
    public static boolean MAXTA = true;//最大ta圈
    public static boolean MINTA = true;//最小ta圈
    public static boolean UNIFORMTA = true;//平均圈
    //IP 1
    public static String IP1 = "192.168.2.53";
    //IP 2
    public static String IP2 = "192.168.2.54";
    //清空指令
    public static String CLEAR = "aa aa 55 55 53 f0 64 01 00 00 00 ff 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00";
    //打开重定向指令
    public static String OPENCHONGDINGXIANG = "aa aa 55 55 17 f0 18 00 00 00 00 ff 00 00 00 00 d9 29 00 00 00 00 00 00";
    //   （设置为重定向模式）
    public static String CHONGDINGXIANGSET = "aa aa 55 55 06 f0 28 00 00 00 00 ff 04 02 01 00 30 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 17 00 00 01 00 00 00 00 00";
    //重启指令
    public static String Restart = "aaaa55550bf01000000000ff01000000";
    //Fdd
    public static String FDD = "aa aa 55 55 01 f0 10 00 00 00 00 ff 01 00 00 00";
    //TDD
    public static String TDD = "aa aa 55 55 01 f0 10 00 00 00 00 ff 00 00 00 00";
    //重启激活小区
    public static String REBOOT = "aa aa 55 55 0b f0 10 00 00 00 00 ff 00 00 00 00";
    //打开公放// 打开风扇
    public static String  CLOSEGF= "aa aa 55 55 9f f0 10 00 00 00 00 ff 01 00 00 00";
    //关闭公放  //关闭风扇
    public static String  OPENGF= "aa aa 55 55 9f f0 10 00 00 00 00 ff 00 00 00 00";
    //小区去激活(停止定位)
    public static String STOPLOCATION = "aa aa 55 55 0d f0 10 00 00 00 00 ff 00 00 00 00";
    //不同步激活小区
    public static String TFFBUTONGBU = "aa aa 55 55 0d f0 10 00 00 00 00 ff 01 00 00 00";
    //查询增益值
    public static String ZYCHAXYN = "aa aa 55 55 31 f0 0c 00 00 00 00 ff";
    //设置SNF
    public static String SNFFDD = "aa aa 55 55 7d f0 10 00 00 00 00 ff 01 00 00 00";
    public static String SNFTDD = "aa aa 55 55 7d f0 10 00 00 00 00 ff 00 00 00 00";

    //基带板IP、端口号查询指令
    public static String IPstr = "aa aa 55 55 33 f0 0c 00 00 00 00 ff";

    //    定位模式黑名单  黑名单查询指令
    public static String BLACKLIST = "aa aa 55 55 55 f0 0c 00 00 00 00 ff";
    //   随机接入
    public static String SUIJI = "aa aa 55 55 65 f0 0c 00 00 00 00 ff";
    //小区状态信息查询指令
//    public static  String STADR="aa aa 55 55 2f f0 0c 00 00 00 00 ff";
//                                aa aa 55 55 27 f0 0c 00 00 00 00 ff
    public static String STADR = "aa aa 55 55 27 f0 0c 00 00 00 00 ff";
//                             aa aa 55 55 27 f0 0c 00 00 00 00 ff

    //UE测量配置查询指令
    public static String UESTR = "aa aa 55 55 3d f0 0c 00 00 00 00 ff";  // 定位模式

    //定位模式黑名单查询
    public static String BLACKLISTSTR = "aa aa 55 55 55 f0 0c 00 00 00 00 ff";
    //小区状态信息查询指令
    public static String STRTYPESXQ = "aa aa 55 55 2f f0 0c 00 00 00 00 ff";
    //接受增益和发射功率查询
    public static String STR1AS = "aa aa 55 55 31 f0 0c 00 00 00 00 ff";
    //查询同步方式
    public static String SYNCHRONOUS = "aa aa 55 55 2d f0 0c 00 00 00 00 ff";
    //空口同步类型
    public static String KONGKOUTB = "aa aa 55 55 23 f0 10 00 00 00 00 ff 00 00 00 00";
    //GPS同步类型
    public static String GPSTB = "aa aa 55 55 23 f0 10 00 00 00 00 ff 01 00 00 00";
    //日期
    public static String Date1 = "aa aa 55 55 7b f0 0c 00 00 00 00 ff";
    //公放1
    public static int GFFLAG1 = 1;
    ////公放2
    public static int GFFLAG2 = 1;


    //黑名单清空响应设备1
    public static boolean CALLBLACKFLAG1 = false;       //false 完成  true 执行
    public static long BLACKTIME1 = 0;
    //设置黑名单1
    public static boolean CALLBLACKFLAGSET1 = false;       //false 完成  true 执行
    public static long BLACKTIMESET1 = 0;
    //打开公放1
    public static boolean CALLBLACKOPEN1 = false;       //false 完成  true 执行
    public static long BLACKOPENSET1 = 0;

    //设置定位模式1
    public static boolean CALLBLACKLOCATION1 = false;       //false 完成  true 执行
    public static long BLACKLOCATION1 = 0;

    //黑名单清空响应设备2
    public static boolean CALLBLACKFLAG2 = false;       //false 完成  true 执行
    public static long BLACKTIME2 = 0;
    //设置黑名单2
    public static boolean CALLBLACKFLAGSET2 = false;       //false 完成  true 执行
    public static long BLACKTIMESET2 = 0;
    //打开公放2
    public static boolean CALLBLACKOPEN2 = false;       //false 完成  true 执行
    public static long BLACKOPENSET2 = 0;

    //设置定位模式2
    public static boolean CALLBLACKLOCATION2 = false;       //false 完成  true 执行
    public static long BLACKLOCATION2 = 0;


    //设备信息的变量------
    //设备号
    public static String DEVICENUMBER1 = "";
    public static String DEVICENUMBER2 = "";
    //硬件版本号
    public static String HARDWAREVERSION1 = "";
    public static String HARDWAREVERSION2 = "";
    //软件版本
    public static String SOFTWAREVERSION1 = "";
    public static String SOFTWAREVERSION2 = "";
    //SN号
    public static String SNNUMBER1 = "";
    public static String SNNUMBER2 = "";
    //MAC地址
    public static String MACADDRESS1 = "";
    public static String MACADDRESS2 = "";
    //UBOOT 版本号
    public static String UBOOTVERSION1 = "";
    public static String UBOOTVERSION2 = "";
    //板卡温度
    public static String BOARDTEMPERATURE1 = "";
    public static String BOARDTEMPERATURE2 = "";
    //当前小区信息状态响应
    public static String PLMN1 = "";
    public static String PLMN2 = "";
    public static String UP1 = "";
    public static String UP2 = "";
    public static String DWON1 = "";
    public static String DWON2 = "";
    public static String BAND1 = "";
    public static String BAND2 = "";
    public static String DK1 = "";
    public static String DK2 = "";
    public static String TAC1 = "";
    public static String TAC2 = "";
    public static String PCI1 = "";
    public static String PCI2 = "";
    public static String CELLID1 = "";
    public static String CELLID2 = "";
    public static String DBM1 = "";
    public static String DBM2 = "";
    public static String TYPE1 = "";
    public static String TYPE2 = "";
    //定位模式
    public static String GZMS1 = "";//工作模式
    public static String ZHZQ1 = "";//抓号周期
    public static String UEIMSI = "";//UEIMSO
    public static String SBZQ1 = "";//测量值上报周期
    public static String UEMAXOF1 = "";//ue最大功率发设开关
    public static String UEMAX1 = "";//ue最大发设功率

    public static String GZMS2 = "";//工作模式
    public static String ZHZQ2 = "";//抓号周期
    public static String UEIMS2 = "";//UEIMSO
    public static String SBZQ2 = "";//测量值上报周期
    public static String UEMAXOF2 = "";//ue最大功率发设开关
    public static String UEMAX2 = "";//ue最大发设功率

    //接受增益和衰减
    public static String ZENGYI1 = "";
    public static String SHUAIJIAN1 = "";
    public static String ZENGYI2 = "";
    public static String SHUAIJIAN2 = "";


    public static String TITLEZD = "自动";//扫频定位
    public static String TITLESD = "手动";

    public static String TITLEZDZM = "自动侦码";//
    public static String TITLESDZM = "手动侦码";//
    public static List<String> BLACKLISTSB1 = new ArrayList<>();
    public static List<String> BLACKLISTSB2 = new ArrayList<>();
    //主界面用的下行频点变量 两个

    public static String DOWNPIN1 = "";
    public static String DOWNPIN2 = "";

    //同步类型
    public static String TBTYPE1 = "";
    public static String TBTYPE2 = "";
    //同步状态
    public static String TBZT1 = "";
    public static String TBZT2 = "";
    //请求数
    public static String REQUEST1 = "0";
    public static String REQUEST2 = "0";
    //完成次数
    public static String ENDNUM1 = "0";
    public static String ENDNUM2 = "0";
    //获取imsi次数
    public static String IMSINUM1 = "0";
    public static String IMSINUM2 = "0";

    public static List<SpBean> SPBEANLIST1Fragment = new ArrayList<>();
    public static List<SpBean> SPBEANLIST2Fragment = new ArrayList<>();
    public static List<SpBean> SPBEANLIST1 = new ArrayList<>();
    public static List<SpBean> SPBEANLIST2 = new ArrayList<>();


}
