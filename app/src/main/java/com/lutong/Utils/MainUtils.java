package com.lutong.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.lutong.Constant.Constant;
import com.lutong.OrmSqlLite.Bean.Conmmunit01Bean;
import com.lutong.OrmSqlLite.Bean.SpBean;
import com.lutong.OrmSqlLite.DBManager01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.lutong.Utils.MainUtils2.toIMSI;

public class MainUtils {


    // 指定格式化格式
    public static SimpleDateFormat ReceiveMainDateFormat1 = new SimpleDateFormat("" + "yyyy-MM-dd HH:mm:ss ");
    public static SimpleDateFormat ReceiveMainDateFormat2 = new SimpleDateFormat("" + "HH:mm:ss ");
    public static String MyDown1 = "";
    public static String MyDown2 = "";
    public static int i;
    public static String str3 = "";
    public static String str4 = "";
    public static long mPressedTime = 0;
    public static long mPressedTime1 = 0;
    public static long mPressedTime2 = 0;
    private static String TAG = "MainUtils";
    private static boolean ThreadFlag = true;
    private static byte[] buf1;
    static String str = "";
    private static int timerDate = 5000;
    //    private static String WIFINAME = "SMLOCATIONAP";
    private static String WIFINAME = "\"" + "SMLOCATIONAP" + "\"";
    private static String WIFINAME2 = "SMLOCATIONAP";
    //    private    static   DatagramPacket dp;
    //    private Context context;
//    private Handler handler;
//
//    public MainUtils(Context context, Handler handler) {
//        this.context = context;
//        this.handler = handler;
//
//    }

    private static class SingletonClassInstance {
        private static final MainUtils instance = new MainUtils();
    }

    private MainUtils() {
    }

    public static MainUtils getInstance() {
        return SingletonClassInstance.instance;
    }


    private static boolean wifiFlag = false;
    private static boolean timer1Flag, timer2Flag = false;
    private static long ZTLONG = 8000; //连接中
    private static long ZTLONGS = 180000;//连接失败

    public static void
    offOn(final int switchs) {//风扇开关 ，1开启，2，关闭
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                byte[] outputData = null;
                if (switchs == 1) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.CLOSEGF);
                }
                if (switchs == 2) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.OPENGF);

                }
                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(Constant.IP1);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * wifi状态的监听
     *
     * @param handler
     * @param messages
     * @param bundle
     * @param timer1
     * @param timer2
     * @param dp
     * @param buf
     * @param context
     */
    public static void WifiMain(final Handler handler, Message messages, final Bundle bundle, final Timer timer1, final Timer timer2, final DatagramPacket dp, final byte[] buf, final Context context) {
        //wifi状态的判断 设备1wifi状态判断
        Timer timerWIFI = new Timer();
        timerWIFI.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String wifiName = MyUtils.getWifiName((Activity) context);
                Log.d(TAG, "wifiNamerun: " + wifiName);
                String wifi = "";
                if (WIFINAME.equals(wifiName)||WIFINAME2.contains(wifi)) {
                    wifiFlag = true;
                    wifi = "正常";
//                        Log.d(TAG, "run:走了");
                    bundle.putString("msgWifi", "true");
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                    message.what = 100001;
                } else {
//                        ToastUtils.showToast("连接wifi错误");
                    wifi = "错误";
                    wifiFlag = false;
                    Log.d(TAG, "run:走了");
                    bundle.putString("msgWifi", "false");
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                    message.what = 100001;

                }
            }
        }, 0, 2000);
    }

    /**
     * 温度查询
     *
     * @param handler
     */
    public static void ReceiveMainWD(final Handler handler, Message message, final Bundle bundle, final Timer timer1_wendu) {


        timer1_wendu.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DeviceUtils.Select(6, 1);//板卡温度
                bundle.putString("msgWifi", "true");
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
                message.what = 8153;
            }
        }, 0, 5000);
    }

    /**
     * 温度查询
     *
     * @param handler
     */
    public static void ReceiveMainWD0(final Handler handler, Message message, final Bundle bundle, final Timer timer1_wendu) {


        timer1_wendu.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DeviceUtils.Select(6, 1);//板卡温度
                bundle.putString("msgWifi", "true");
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
                message.what = 8153;
            }
        }, 0, 5000);
    }

    /**
     * 温度查询
     *
     * @param handler
     */
    public static void ReceiveMainWD1(final Handler handler, Message message, final Bundle bundle, final Timer timer1_wendu) {


        timer1_wendu.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DeviceUtils.Select(6, 1);//板卡温度
                bundle.putString("msgWifi", "true");
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
                message.what = 8153;
            }
        }, 0, 5000);
    }

    /**
     * 温度查询
     *
     * @param handler
     */
    public static void ReceiveMainWD2(final Handler handler, Message message, final Bundle bundle, final Timer timer1_wendu) {


        timer1_wendu.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DeviceUtils.Select(6, 1);//板卡温度
                bundle.putString("msgWifi", "true");
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
                message.what = 8153;
            }
        }, 0, 5000);
    }
   public static DatagramSocket DS = null;
    public static void ReceiveMain(final Handler handler, Message messages, final Bundle bundle, final Timer timer1, final Timer timer2, final DatagramPacket dp, final byte[] buf, final Context context, final boolean runThread) {


        mPressedTime1 = System.currentTimeMillis();
        mPressedTime2 = System.currentTimeMillis();
        Timer timers = new Timer();
        timers.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long mNowTime = System.currentTimeMillis();//获取第一次按键时间
                if ((mNowTime - mPressedTime1) > ZTLONG) {//超过八秒就是未启动
//                    ToastUtils.showToast("再按一次退出程序");
//                        mPressedTime = mNowTime;


                    if ((mNowTime - mPressedTime1) > ZTLONGS) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("zt1", "1");
                        message.setData(bundle);
                        handler.sendMessage(message);
                        message.what = 100120;
                    } else {
//                            Log.d(TAG, "mPressedTimerun1: ");
                        //设备1状态设定
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("zt1", "0");
                        message.setData(bundle);
                        handler.sendMessage(message);
                        message.what = 100120;
                    }
                } else {//代表启动
//                        Log.d(TAG, "mPressedTimerun2: ");
//                        System.exit(0);
                }
                if ((mNowTime - mPressedTime2) > ZTLONG) {//超过八秒就是未启动
//                    ToastUtils.showToast("再按一次退出程序");
//                        mPressedTime = mNowTime;
//                        Log.d(TAG, "mPressedTimerun1: ");

//                        //设备2状态设定
//                        Message message = new Message();
//                        bundle.putString("zt2", "0");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 200120;
                    if ((mNowTime - mPressedTime2) > ZTLONGS) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("zt2", "1");
                        message.setData(bundle);
                        handler.sendMessage(message);
                        message.what = 200120;
                    } else {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("zt2", "0");
                        message.setData(bundle);
                        handler.sendMessage(message);
                        message.what = 200120;
                    }

                } else {//代表启动
//                        Log.d(TAG, "mPressedTimerun2: ");
//                        System.exit(0);
                }
            }
        }, 0, 5000);
//
//        mPressedTime1 = System.currentTimeMillis();
//        mPressedTime2 = System.currentTimeMillis();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    long mNowTime = System.currentTimeMillis();//获取第一次按键时间
//                    if ((mNowTime - mPressedTime1) > ZTLONG) {//超过八秒就是未启动
////                    ToastUtils.showToast("再按一次退出程序");
////                        mPressedTime = mNowTime;
//
//
//                        if ((mNowTime - mPressedTime1) > ZTLONGS) {
//                            Message message = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("zt1", "1");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
//                        } else {
////                            Log.d(TAG, "mPressedTimerun1: ");
//                            //设备1状态设定
//                            Message message = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("zt1", "0");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
//                        }
//
//                    } else {//代表启动
////                        Log.d(TAG, "mPressedTimerun2: ");
////                        System.exit(0);
//                    }
//                    if ((mNowTime - mPressedTime2) > ZTLONG) {//超过八秒就是未启动
////                    ToastUtils.showToast("再按一次退出程序");
////                        mPressedTime = mNowTime;
////                        Log.d(TAG, "mPressedTimerun1: ");
//
////                        //设备2状态设定
////                        Message message = new Message();
////                        bundle.putString("zt2", "0");
////                        message.setData(bundle);
////                        handler.sendMessage(message);
////                        message.what = 200120;
//                        if ((mNowTime - mPressedTime2) > ZTLONGS) {
//                            Message message = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("zt2", "1");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 200120;
//                        } else {
//                            Message message = new Message();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("zt2", "0");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 200120;
//                        }
//
//                    } else {//代表启动
////                        Log.d(TAG, "mPressedTimerun2: ");
////                        System.exit(0);
//                    }
//                }
//
//            }
//        });
        new Thread(new Runnable() {

            @Override
            public void run() {

                int opionts1 = 0;
                int opionts2 = 0;

                int opionts11 = 0;
                int opionts22 = 0;
                ThreadFlag = true;
                Log.d("wppn", "whiletry ");
                bundle.putString("msgWifi", 222 + "");
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
                message.what = 100;
                try {
                    DS = new DatagramSocket(3345);

                    if (DS == null) {
                        DS = new DatagramSocket(null);
                        DS.setReuseAddress(true);
                        DS.bind(new InetSocketAddress(3345));
                    }
                    //设备1状态设定
                    message = new Message();
                    bundle.putString("zt1", "0");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    message.what = 100120;
                    //设备2状态设定
                    message = new Message();
                    bundle.putString("zt2", "0");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    message.what = 200120;
                    byte[] buf = new byte[1024];
//                     dp = new DatagramPacket(buf, buf.length);
//                    mPressedTime1 = System.currentTimeMillis();
//                    mPressedTime2 = System.currentTimeMillis();

                } catch (Exception e) {

                }
                while (true) {
//                        bundle.putString("msgWifi", 222 + "");
//                        message = new Message();
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100;
                    //通过udp的socket服务将数据包接收到，通过receive方法
//                        ds.setSoTimeout((int) timerDate);//延时操作
//                        if (wifiFlag==false){
//                            return;
//                        }
                    try {
                        DS.receive(dp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] buf1 = dp.getData();
                    //btye[]字节数组转换成16进制的字符串
                    String str = ReceiveTask.toHexString1(buf1);
                    //String str = new String(dp.getData(),0,dp.getLength());
                    String ID1TF = "";
                    String ID2TF = "";
                    if (Constant.IP1.equals(dp.getAddress().getHostAddress())) {
                        mPressedTime1 = System.currentTimeMillis();
                        System.out.println("123456");
                        System.out.println("收到" + dp.getAddress().getHostAddress() + "发送数据：" + str);

                        //时间
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        System.out.println("时间：" + sdf.format(d));
                        //小区基本信息状态查询响应
                        if ("2cf0".equals(str.substring(8, 12))) {

                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设备型号：" + hexStringToString(str.substring(32, 46)));
                                Constant.DEVICENUMBER1 = hexStringToString(str.substring(32, 46));
                            } else if ("01000000".equals(str.substring(24, 32))) {
                                System.out.println("硬件版本：" + hexStringToString(str.substring(32, 34)));
                                Constant.HARDWAREVERSION1 = hexStringToString(str.substring(32, 34));
                            } else if ("02000000".equals(str.substring(24, 32))) {
                                System.out.println("软件版本：" + hexStringToString(str.substring(32, 106)));
                                Constant.SOFTWAREVERSION1 = hexStringToString(str.substring(32, 106));
                            } else if ("03000000".equals(str.substring(24, 32))) {
                                System.out.println("序列号SN:" + hexStringToString(str.substring(32, 70)));
                                Constant.SNNUMBER1 = hexStringToString(str.substring(32, 70));
                            } else if ("04000000".equals(str.substring(24, 32))) {
                                System.out.println("MAC地址：" + hexStringToString(str.substring(32, 66)));
                                Constant.MACADDRESS1 = hexStringToString(str.substring(32, 66));
                            } else if ("05000000".equals(str.substring(24, 32))) {
                                Constant.UBOOTVERSION1 = hexStringToString(str.substring(32, 47));
                                System.out.println("uboot版本号：" + hexStringToString(str.substring(32, 47)));
                            } else if ("06000000".equals(str.substring(24, 32))) {
                                System.out.println("板卡温度：" + hexStringToString(str.substring(32, 50)));
                                Constant.BOARDTEMPERATURE1 = hexStringToString(str.substring(32, 50));
                            }
                        }

                        if ("66f0".equals(str.substring(8, 12))) {
                            Constant.REQUEST1 = Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "";
                            Constant.ENDNUM1 = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            Constant.IMSINUM1 = Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "";
//                            Log.d("Asubstring66f0", "--run: " + Integer.parseInt(str.substring(24, 26), 16)+"/n"+ENDNUM1+"/n"+IMSINUM1);
                            String substring = str.substring(24, 32);
                            String substring1 = str.substring(32, 40);
                            String substring2 = str.substring(56, 64);
//                            Log.d("Asubstring66f0REQUEST1", "run: "+substring.toString());
//                            Log.d("Asubstring66f0ENDNUM1", "run: "+substring1.toString());
//                            Log.d("Asubstring66f0IMSINUM1", "run: "+substring2.toString());
//                            Log.d("Asubstring66f0", "run: "+str.toString());

                            Log.d("Asubstring66f0REQUEST1", "run: " + Constant.REQUEST1);
                            Log.d("Asubstring66f0ENDNUM1", "run: " + Constant.ENDNUM1);
                            Log.d("Asubstring66f0IMSINUM1", "run: " + Constant.IMSINUM1);
                            Log.d("Asubstring66f0", "run: " + str.toString());
                        }
                        if ("2ef0".equals(str.substring(8, 12))) {
                            Log.d("nzq2df0", str.toString());
                            System.out.println("同步类型" + str.toString());
                            String substring = str.substring(25, 26);
                            String substring2 = str.substring(29, 30);
                            Constant.TBTYPE1 = substring.toString();
                            Constant.TBZT1 = str.substring(29, 30);
                            Log.d("Asubstringzt1", "run: " + substring2.toString());
                            Log.d("Asubstringzt1", "run: " + str.toString());
                        }
//                        if (str.contains("2ef0")) {
//                            System.out.println("同步类型--" + str.toString());
//                        }
//                        if ("f02d".equals(str.substring(13, 14))) {
//                            Log.d("nzq2df0",str.toString());
//                            System.out.println("同步类型"+str.toString());
//                        }
                        //当前小区信息状态响应
                        if ("28f0".equals(str.substring(8, 12))) {
                            //PLMN
                            System.out.println(StringTOIMEI(str.substring(40, 50)));
                            Constant.PLMN1 = StringTOIMEI(str.substring(40, 50));

                            //上行频点
                            System.out.println("我上行上行频点" + Integer.parseInt(StringPin(str.substring(24, 32)), 16));
                            Constant.UP1 = Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "";
                            //下行频点
                            System.out.println(Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                            Constant.DWON1 = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            //Band
                            System.out.println(Integer.parseInt(StringPin(str.substring(56, 64)), 16));
                            Constant.BAND1 = Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "";
                            //带宽
                            System.out.println(Integer.parseInt(str.substring(54, 56), 16));
                            Constant.DK1 = Integer.parseInt(str.substring(54, 56), 16) + "";
                            //TAC
                            System.out.println(Integer.parseInt(StringPin(str.substring(68, 72)), 16));
                            Constant.TAC1 = Integer.parseInt(StringPin(str.substring(68, 72)), 16) + "";
                            //PCI
                            System.out.println(Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                            Constant.PCI1 = Integer.parseInt(StringPin(str.substring(64, 68)), 16) + "";
                            //Cellld
                            System.out.println(Integer.parseInt(StringPin(str.substring(72, 80)), 16));
                            Constant.CELLID1 = Integer.parseInt(StringPin(str.substring(72, 80)), 16) + "";
                            //UePMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(80, 84)), 16));
                            Constant.DBM1 = Integer.parseInt(StringPin(str.substring(80, 84)), 16) + "";
                            //EnodeBPMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(84, 88)), 16));
//                            TYPE1 = Integer.parseInt(StringPin(str.substring(84, 88)), 16) + "";

                        }
                        //小区状态信息查询指令的响应
                        if ("30f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("小区空闲态");
                                Constant.TYPE1 = "小区空闲态";
                            } else if ("01000000".equals(str.substring(24, 32))) {
                                System.out.println("同步或扫频中");
                                Constant.TYPE1 = "同步或扫频中";
                            } else if ("02000000".equals(str.substring(24, 32))) {
                                System.out.println("小区激活中");
                                Constant.TYPE1 = "小区激活中";
                            } else if ("03000000".equals(str.substring(24, 32))) {
                                System.out.println("小区已激活");
                                Constant.TYPE1 = "小区已激活";
                            } else if ("04000000".equals(str.substring(24, 32))) {
                                System.out.println("小区去激活中");
                                Constant.TYPE1 = "小区去激活中";
                            }
                        }

                        //UE测量配置查询响应信息
                        if ("3ef0".equals(str.substring(8, 12))) {
                            //工作模式
                            if ("00".equals(str.substring(24, 26))) {
                                System.out.println("持续侦码模式");
                                Constant.GZMS1 = "持续侦码模式";
                            } else if ("01".equals(str.substring(24, 26))) {
                                System.out.println("周期侦码模式");
                                //周期模式参数，指示针对同一个IMSI 的抓号周期
                                System.out.println(Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                Constant.ZHZQ1 = "" + Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                Constant.GZMS1 = "周期侦码模式";
                            } else if ("02".equals(str.substring(24, 26))) {
                                System.out.println("定位模式");
                                Constant.GZMS1 = "定位模式";
                                //定位模式，定位的终端 IMSI
                                Constant.UEIMSI = StringTOIMEI(str.substring(32, 62));
                                System.out.println("imsi:" + StringTOIMEI(str.substring(32, 62)));
                                //定位模式，终端测量值的上报周期,建议设置为 1024ms, 0：120ms,1：240ms,2：480ms,3：640ms,4：1024ms,5：2048ms

                                if ("00".equals(str.substring(66, 68))) {
                                    System.out.println("120ms");
                                    Constant.SBZQ1 = "120ms";
                                } else if ("01".equals(str.substring(66, 68))) {
                                    System.out.println("240ms");
                                    Constant.SBZQ1 = "240ms";
                                } else if ("02".equals(str.substring(66, 68))) {
                                    System.out.println("480ms");
                                    Constant.SBZQ1 = "480ms";
                                } else if ("03".equals(str.substring(66, 68))) {
                                    System.out.println("640ms");
                                    Constant.SBZQ1 = "640ms";
                                } else if ("04".equals(str.substring(66, 68))) {
                                    System.out.println("1024ms");
                                    Constant.SBZQ1 = "1024ms";
                                } else if ("05".equals(str.substring(66, 68))) {
                                    System.out.println("2048ms");
                                    Constant.SBZQ1 = "2048ms";
                                }
                                //定位模式，调度定位终端最大功率发射开关，需要设置为 0,0：enable,1：disable
                                if ("00".equals(str.substring(68, 70))) {
                                    System.out.println("enable");
                                    Constant.UEMAXOF1 = "开";
                                } else if ("01".equals(str.substring(68, 70))) {
                                    System.out.println("disable");
                                    Constant.UEMAXOF1 = "关";
                                }
                                //定位模式，UE 最大发射功率，最大值 不 超 过wrFLLmtToEnbSysArfcnCfg 配置的UePMax，建议设置为 23dBm
                                Constant.UEMAX1 = Integer.parseInt(str.substring(70, 72), 16) + "";
                                System.out.println(Integer.parseInt(str.substring(70, 72), 16));

                                //定位模式，由于目前都采用 SRS 方案配合单兵，因此此值需要设置为disable，否则大用户量时有定位终端掉线概率。0: disable,1: enable
                                if ("00".equals(str.substring(72, 74))) {
                                    System.out.println("disable");
                                } else if ("01".equals(str.substring(72, 74))) {
                                    System.out.println("enable");
                                }
                                //定位模式，是否把非定位终端踢回公网的配置，建议设置为 0。设置为 1 的方式是为了发布版本时保留用户反复接入基站测试使用。1：非定位终端继续被本小区吸附, 0：把非定位终端踢回公网
                                if ("00".equals(str.substring(76, 78))) {
                                    System.out.println("把非定位终端踢回公网");
                                } else if ("01".equals(str.substring(76, 78))) {
                                    System.out.println("非定位终端继续被本小区吸附");
                                }
                                //定位模式，是否对定位终端建立SRS 配置。
                                if ("00".equals(str.substring(78, 80))) {
                                    System.out.println("disable");
                                } else if ("01".equals(str.substring(78, 80))) {
                                    System.out.println("enable");
                                }
                            } else if ("03".equals(str.substring(24, 26))) {
                                System.out.println("管控模式");
                                Constant.GZMS1 = "管控模式";
                                //管控模式的子模式0：黑名单子模式；1：白名单子模式
                                if ("00".equals(str.substring(80, 82))) {
                                    System.out.println("黑名单子模式");
                                    Constant.GZMS1 = "黑名单子模式";
                                } else if ("01".equals(str.substring(80, 82))) {
                                    System.out.println("白名单子模式");
                                    Constant.GZMS1 = "白名单子模式";
                                }
                            } else if ("04".equals(str.substring(24, 26))) {
                                System.out.println("重定向模式");
                                Constant.GZMS1 = "重定向模式";
                            /*0: 名单中的用户执行重定向；名单外的全部踢回公网
                            1: 名单中的用户踢回公网；名单外的全部重定向
							2: 名单中的用户执行重定向；名单外的全部吸附在本站
							3: 名单中的用户吸附在本站;名单外的全部重定向
							4: 所有目标重定向*/
                                System.out.println(Integer.parseInt(str.substring(26, 28), 16));

                            }

                        }
//定位模式黑名单查询响应
                        if ("56f0".equals(str.substring(8, 12))) {

                            //黑名单数量
                            Integer blacklistNum = Integer.parseInt(StringPin(str.substring(24, 28)), 16);
                            int begin = 28;
                            int end = 58;
                            for (int i = 0; i < blacklistNum; i++) {
                                System.out.println("黑名单打印" + StringTOIMEI(str.substring(begin, end)));
                                Constant.BLACKLISTSB1.add(StringTOIMEI(str.substring(begin, end)));
                                begin = begin + 34;
                                end = end + 34;
                            }

                        }
                        //接受增益和发射功率查询的响应
                        if ("32f0".equals(str.substring(8, 12))) {
                            //增益
                            //寄存器中的值，实际生效的值（FDD 模式下仅在建立完小区查询，该值有效）
                            System.out.println("我增益1" + Integer.parseInt(str.substring(24, 26), 16));
                            Constant.ZENGYI1 = Integer.parseInt(str.substring(24, 26), 16) + "";
                            //发射功率  衰减
                            //寄存器中的值，实际生效的值（FDD 模式下仅在建立完小区查询，该值有效）
                            System.out.println(Integer.parseInt(str.substring(28, 30), 16));
                            Constant.SHUAIJIAN1 = Integer.parseInt(str.substring(28, 30), 16) + "";
                            System.out.println("我衰减1" + Integer.parseInt(str.substring(24, 26), 16));

//                            //数据库中的保存值，重启保留生效的值,
//                            System.out.println(Integer.parseInt(str.substring(26,28),16));
//
//                            //数据库中的保存值，重启保留生效的值
//                            System.out.println(Integer.parseInt(str.substring(30,32),16));
//                            //FDD AGC 开关
//                            if("00".equals(str.substring(30,32))){
//                                System.out.println("FDD AGC 开关关闭");
//                            }else if("01".equals(str.substring(30,32))){
//                                System.out.println("FDD AGC 开关打开");
//                            }

                            //增益
                            //只在FDD模式下有效，寄存器中的值，实际生效的值,该值只有在扫频完成后，建立小区前查询有效
//                            System.out.println(Integer.parseInt(str.substring(34,36),16));
//                            //eeprom 中的保存值，重启保留生效的值
//                            System.out.println(Integer.parseInt(str.substring(36,38),16));
                        }
                        //设置黑名单响应 清空响应
                        if ("3af0".equals(str.substring(8, 12))) {
                            if ("00".equals(str.substring(24, 26))) {
                                opionts1++;
                                System.out.println("设置基站白名单成功" + opionts1);
                                Log.d("setwhtite", "run: " + str);
                                message = new Message();
                                bundle.putString("100191", "1");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100191;
                            } else {
                                System.out.println("设置基站白名单失败");
                                Log.d("setwhtite", "run: " + str);
                                message = new Message();
                                bundle.putString("100191", "2");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100191;
                            }
                        }
                        //设置黑名单响应 清空响应
                        if ("54f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                opionts1++;
                                System.out.println("设置基站黑名单成功" + opionts1);
                                if (opionts1 % 2 == 0) {
                                    Log.d("jsgs", "run:偶数");
                                    message = new Message();
                                    bundle.putString("100130", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 100131;
                                } else {
                                    Log.d("jsgs", "run:基数");
                                    message = new Message();
                                    bundle.putString("100131", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 100130;
                                }
                            } else {
                                System.out.println("设置基站黑名单失败");

                                if (opionts2 % 2 == 0) {
                                    Log.d("jsgs", "run:偶数");
                                    message = new Message();
                                    bundle.putString("100130", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 100135;
                                } else {
                                    Log.d("jsgs", "run:基数");
                                    message = new Message();
                                    bundle.putString("100131", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 100134;
                                }


                            }
                        }
                        //黑名单中标
                        if ("05f0".equals(str.substring(8, 12))) {
                            String down = "";
                            System.out.println("1黑名单中标IMSI号：" + hexStringToString(str.substring(32, 62)));
                            //当前小区信息状态响应
//                            if ("28f0".equals(str.substring(8, 12))) {
//                                down = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
//                            }
//                            down=Integer.parseInt(StringPin(str.substring(40, 48)), 16)+"";
                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(32, 62));
                            Log.d("jsgs", "run:基数");
                            message = new Message();
                            bundle.putString("imsi", imsi);
                            bundle.putString("sb", "1");
                            bundle.putString("zb", "");
                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
//                            bundle.putString("zmdown", "" + down);
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 30000;
                            Log.d("Aaimsi", "imsirun: " + imsi);
                            System.out.println("Imsi黑名单中标" + imsi + "down" + down);

//
//                            bundle.putString("imsi", imsi);
//                            bundle.putString("sb", "1");
//                            bundle.putString("zb", "");
//                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));

//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 3000000;

                        }

                        //设置定位模式的应答信息
                        if ("07f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设置基站ue定位成功");

                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100136;
                                Log.d("str10010001", "run: " + str.toString());
                            } else {
                                System.out.println("设置基站ue定位失败");
                                Log.d("str10010002", "run: " + str.toString());
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100137;
                            }
                        }
                        //重启是否成功
                        if ("0cf0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("重启设置成功！");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100138;
                            } else {
                                System.err.println("重启失败！");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100139;
                            }
                        }
                        //制式切换是否成功
                        if ("02f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("制式切换成功！");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100140;
                            } else {
                                System.err.println("制式切换失败");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100141;
                            }
                        }
                        //模式：FDD TDD
                        if ("00ff".equals(str.substring(16, 20))) {
                            //设置模式FDD
                            ID1TF = "FDD";
                            System.err.println("FDD");
                            message = new Message();
                            bundle.putString("tf1", "FDD");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 100110;
                        }
                        if ("ff00".equals(str.substring(16, 20))) {
                            //设置模式TDD
                            ID1TF = "TDD";
                            System.out.println("TDD");
                            message = new Message();
                            bundle.putString("tf1", "TDD");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 100110;
                        }
                        //建立小区是发送否成功1
                        if ("04f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            Log.d("wtto", "04f0run:0 ");
                            if (row == 0) {
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100145;
                                System.out.println("设置成功！开始建立小区！");
                                Log.d("wtto", "04f0run:1 ");
                            } else {
                                System.err.println("设置失败！");
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100146;
                                Log.d("wtto", "04f0run:2 ");
                            }
                        }
                        //公放设置
//                        if ("a0f0".equals(str.substring(8, 12))) {
//                            //成功0；不成功>0（16进制字符串转换成十进制）
//                            int row = Integer.parseInt(str.substring(24, 32), 16);
//                            if (row == 0) {
//                                System.out.println("公放设置成功！");
//                                message = new Message();
//                                bundle.putString("test", "0");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100142;
//                            } else {
//                                System.err.println("设置失败！");
//                                message = new Message();
//                                bundle.putString("test", "0");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100143;
//                            }
//                        }
                        //原来的功放设置响应,现改为风扇开关
                        if ("a0f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("风扇开启成功");
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
//                                message.what = 100142;
                            } else {
                                System.err.println("风扇开启失败");
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
//                                message.what = 100143;
                            }
                        }
                        //去激活小区是否成功
                        if ("0ef0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("设置成功！去激活小区成功，停止定位！");
                            } else {
                                System.err.println("设置失败！");
                            }
                        }
                        //基站执行状态
                        if ("19f0".equals(str.substring(8, 12))) {
                            String state = str.substring(24, 32);
                            System.out.println("state" + state);
                            Log.d("wtto", "staterun: " + state);
                            if ("00000000".equals(state)) {
                                System.err.println("空口同步成功");
                                Log.d("wtto", "qqqrun:空口同步成功");

                                message = new Message();
                                bundle.putString("test", "1");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("01000000".equals(state)) {
                                System.err.println("空口同步失败");
                                Log.d("wtto", "qqqrun:空口同步失败");
                                message = new Message();
                                bundle.putString("test", "2");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("02000000".equals(state)) {
                                System.err.println("GPS同步成功");
                                Log.d("wtto", "qqqrun:GPS同步成功");
                                message = new Message();
                                bundle.putString("test", "3");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("03000000".equals(state)) {
                                System.err.println("GPS同步失败");
                                Log.d("wtto", "qqqrun:GPS同步失败");
                                message = new Message();
                                bundle.putString("test", "4");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("04000000".equals(state)) {
                                System.err.println("扫频成功");
                                Log.d("wtto", "qqqrun:扫频成功");
                                message = new Message();
                                bundle.putString("test", "5");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("05000000".equals(state)) {
                                System.err.println("扫频失败");
                                Log.d("wtto", "qqqrun:扫频失败");
                                message = new Message();
                                bundle.putString("test", "6");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("06000000".equals(state)) {
                                System.err.println("小区激活成功");
                                Log.d("wtto", "qqqrun:小区激活成功");
                                message = new Message();
                                bundle.putString("test", "7");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("07000000".equals(state)) {
                                System.err.println("小区激活失败");
                                Log.d("wtto", "qqqrun:小区激活失败");
                                message = new Message();
                                bundle.putString("test", "8");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("08000000".equals(state)) {
                                System.err.println("小区去激活");
                                Log.d("wtto", "qqqrun:小区去激活");
                                message = new Message();
                                bundle.putString("test", "9");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("09000000".equals(state)) {
                                System.err.println("空口同步中");
                                Log.d("wtto", "qqqrun:空口同步中");
                                message = new Message();
                                bundle.putString("test", "10");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("0a000000".equals(state)) {
                                System.err.println("GPS同步中");
                                Log.d("wtto", "qqqrun:GPS同步中");
                                message = new Message();
                                bundle.putString("test", "11");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("0b000000".equals(state)) {
                                System.err.println("扫频中");
                                Log.d("wtto", "qqqrun:扫频中");
                                message = new Message();
                                bundle.putString("test", "12");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("0c000000".equals(state)) {
                                System.err.println("小区激活中");
                                Log.d("wtto", "qqqrun:小区激活中");
                                message = new Message();
                                bundle.putString("test", "13");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            } else if ("0d000000".equals(state)) {
                                System.err.println("小区去激活中");
                                Log.d("wtto", "qqqrun:小区去激活中");
                                message = new Message();
                                bundle.putString("test", "14");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 1001200;
                            }

                        }
                        if ("08f0".equals(str.substring(8, 12))) {

                            //目标距离（16进制字符串转换成十进制）
                            Integer.parseInt(str.substring(24, 26), 16);
                            System.out.println("距离：" + Integer.parseInt(str.substring(24, 26), 16));
                            message = new Message();
                            bundle.putString("sb1jl", Integer.parseInt(str.substring(24, 26), 16) + "");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 100147;

                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(26, 56));
                            message = new Message();
                            bundle.putString("imsi", imsi);
                            bundle.putString("sb", "1");
                            bundle.putString("zb", "1");
                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 30000;
                            //IMSI号
                            StringTOIMEI(str.substring(26, 56));
                            System.out.println("IMSI号：" + hexStringToString(str.substring(26, 56)));


                            message = new Message();
                            bundle.putString("imsi1", imsi);
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 100148;
                            Log.d(TAG, "loggnzqrun: " + hexStringToString(str.substring(26, 56)));
                        }

                        if ("10f0".equals(str.substring(8, 12))) {
                            //心跳解析
                            //查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
                            message = new Message();
                            bundle.putString("zt1", "2");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 100120;
                            if ("0000".equals(str.substring(24, 28))) {
                                System.out.println("0：小区 IDLE态");
                                message = new Message();
                                bundle.putString("zt1", "2");//IDLE态
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100120;
                            } else if ("0100".equals(str.substring(24, 28))) {
                                System.out.println("1：扫频/同步进行中");
                                message = new Message();
                                bundle.putString("zt1", "3");//扫频同步进行中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100120;
                            } else if ("0200".equals(str.substring(24, 28))) {
                                System.out.println("2：小区激活中");
                                message = new Message();
                                bundle.putString("zt1", "4");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100120;
                            } else if ("0300".equals(str.substring(24, 28))) {
                                System.out.println("3：定位中");
                                message = new Message();
                                bundle.putString("zt1", "5");//小区激活态
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100120;
                                //Band号
                                Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                System.out.println("Band号：" + Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                //上行频点
                                Integer.parseInt(StringPin(str.substring(32, 40)), 16);
                                System.out.println("上行频点：" + Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                                //下行频点
                                Integer.parseInt(StringPin(str.substring(40, 48)), 16);
                                System.out.println("下行频点：" + Integer.parseInt(StringPin(str.substring(40, 48)), 16));
                                Constant.DOWNPIN1 = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
                                message = new Message();
                                bundle.putString("down", Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");//查询增益
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100151;
                                System.out.println("100151");
                                //移动联通电信
                                if ("3436303030".equals(str.substring(48, 58))) {
                                    //设置中国移动
                                }
                                if ("3436303031".equals(str.substring(48, 58))) {
                                    //设置中国联通
                                }
                                if ("3436303033".equals(str.substring(48, 58)) || "3436303131".equals(str.substring(48, 58))) {
                                    //设置中国电信
                                }

                                //PCI
                                Integer.parseInt(StringPin(str.substring(64, 68)), 16);
                                System.out.println("PCI:" + Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                                //TAC
                                Integer.parseInt(StringPin(str.substring(68, 72)), 16);
                                System.out.println("TAC:" + Integer.parseInt(StringPin(str.substring(68, 72)), 16));

                            } else if ("0400".equals(str.substring(24, 28))) {
                                System.out.println("4：小区去激活中");
                            }

                        }
                        //温度告警
                        if ("5bf0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(32, 40))) {
                                System.out.println("基带板温度超过70度");
                            }
                            if ("01000000".equals(str.substring(32, 40))) {
                                System.out.println("基带板温度降低到70度以下了");
                            }

                        }
                        //
                        if ("32f0".equals(str.substring(8, 12))) {
                            message = new Message();
                            bundle.putString("zy1", Integer.parseInt(str.substring(24, 26), 16) + "");//查询增益
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 100149;
                            System.out.println("100149");
                        }

                        //设置增益是否成功
                        if ("14f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("增益值设置成功!");
                                message = new Message();
                                bundle.putString("zyset1", "增益值设置成功");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100150;
                                System.out.println("100150");

                            } else {
                                System.err.println("增益值设置失败!");
                                message = new Message();
                                bundle.putString("zyset1", "增益值设置失败");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100150;
                                System.out.println("100150");
                            }
                        }

//                            aaaa
                        //扫频频点设置响应
                        if ("04f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("设置成功！");
                            } else {
                                System.err.println("设置失败！");
                            }
                        }
                        //     扫频频点设置响应
                        if ("0af0".equals(str.substring(8, 12))) {
                            //采集的小区数目
                            int row;
                            if ("ffff".equals(str.substring(24, 28))) {
                                row = 0;
                            } else {
                                row = Integer.parseInt(StringPin(str.substring(24, 28)), 16);
                                System.out.println("小区个数：" + row);
                            }
                        }
                        if ("7ef0".equals(str.substring(8, 12))) {
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("SNF端口扫频设置成功！");

                                message = new Message();
                                bundle.putString("snf", "SNF端口扫频设置成功");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100154;
                                System.out.println("100154");

                            } else {
                                System.err.println("SNF端口扫频设置失败！");
                                message = new Message();
                                bundle.putString("snf", "SNF端口扫频设置失败");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100154;
                                System.out.println("100154");
                            }
                        }
//                            F
                        //判断f019是否 包含 0af
                        boolean Flag19F0=false;
                        if ("19f0".equals(str.substring(8, 12))) {
                            if ("0af0".equals(str.substring(40, 44))) {
                                Flag19F0=true;
                                String sa=str;
                                str = sa.substring(32);
                                List<SpBean> spBeanList = new ArrayList<>();
                                //消息头长度
                                String length = str.substring(12, 16);
                                String len = StringPin(length);
                                Integer strlen = Integer.parseInt(len, 16);
                                //是否发送完
                                Integer.parseInt(StringPin(str.substring(20, 24)), 16);
                                String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));

                                code = StringAd(code);
                                System.out.println("南志强2F--" + str3);
                                System.err.println("code:" + code);
                                String s = str.substring(24, strlen * 2);
                                System.out.println("ss___" + s);
                                str3 = str3 + s;
                                System.err.println("s3___" + str3);
                                if ("0".equals(code.substring(0, 1))) {

                                    str3 = "aaaa55550af0240000ff0000" + str3;
                                    System.err.println("str3:+++++++++++++++" + str3);
                                    //采集的小区数目
                                    int row;
                                    if ("ffff".equals(str3.substring(24, 28))) {
                                        row = 0;
                                    } else {
                                        row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
                                        System.out.println("小区个数：" + row);
                                        int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                        System.out.println("小区个数下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                    }
                                    System.out.println("南志强F--" + str3);
                                    int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                    int pciBegin = 40, pciEnd = 44;
                                    int tacBegin = 44, tacEnd = 48;
                                    int plmnBegin = 48, plmnEnd = 52;
                                    int celldBegin = 56, celldEnd = 64;
                                    int priorityBegin = 64, priorityEnd = 72;
                                    int rsrpBegin = 72, rsrpEnd = 74;
                                    int rsrqBegin = 74, rsrqEnd = 76;
                                    int bandWidthBegin = 76, bandWidthEnd = 78;
                                    int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
                                    int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
                                    int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
                                    for (int i = 0; i < row; i++) {
                                        //下行频点
                                        SpBean spBean = new SpBean();
                                        try {
                                            System.out.println(str3.substring(dlEarfcnBegin, dlEarfcnEnd));
                                            if ("ffffffff".equals(str3.substring(dlEarfcnBegin, dlEarfcnEnd))) {
                                                System.out.println("null");
//                                            continue;
                                            } else {
                                                try {
                                                    System.out.println("下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                                    spBean.setDown(Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                                } catch (NumberFormatException e) {
                                                    spBean.setDown(null);
                                                }

                                            }
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setDown(null);
                                        }

                                        if (ID1TF.equals("TDD")) {
                                            spBean.setUp(255 + "");
                                        } else {
                                            if (!TextUtils.isEmpty(spBean.getDown())) {
                                                int i1 = Integer.parseInt(spBean.getDown()) + 18000;
                                                spBean.setUp(i1 + "");
                                            }
                                        }
                                        //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(0);
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);


                                        try {
                                            str3.substring(pciBegin, pciEnd);
                                            spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setPci(0);
                                        }
                                        //TAC
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                    spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));

                                        try {
                                            str3.substring(tacBegin, tacEnd);
                                            spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setTac(0);
                                        }


//                                    Log.d(TAG, "spBeanTAcrun: " + spBean.getTac());
//                                    spBean.setTac(0);
                                        //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
                                        try {
                                            if ("ffff".equals(str3.substring(plmnBegin, plmnEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPlmn(0 + "");
                                            } else {
                                                spBean.setPlmn(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (StringIndexOutOfBoundsException E) {
                                            spBean.setPlmn(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区PLMN error");
                                        }


//                                    spBean.setPlmn("0");
                                        //CellId

                                        try {
                                            System.out.println("ffffffff".equals(str3.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "------CellId：");
                                            if ("ffffffff".equals(str3.substring(celldBegin, celldEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setCid(0 + "");
                                            } else {
                                                spBean.setCid(Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setCid(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区CellId error");
                                        }

                                        //Priority 本小区频点优先级
                                        try {
                                            System.out.println(str3.substring(64, 72));
//                                        System.out.println("ffffffff".equals(str3.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
                                            if ("ffffffff".equals(str3.substring(priorityBegin, priorityEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPriority(0);
                                            } else {
                                                spBean.setPriority(Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setPriority(0);
                                            Log.d("nzqExceloing", "run:1 小区Priority error");
                                        }

                                        //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrpBegin, rsrpEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrp(0);
                                            } else {
                                                spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setRsrp(0);
                                            Log.d("nzqExceloing", "run:1 小区RSRPerror");
                                        }

//                                    spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));

                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrqBegin, rsrqEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrq(0);
                                            } else {
                                                spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区RSQPerror");
                                        }

                                        //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                    spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                        try {
                                            if ("ffffffff".equals(str3.substring(bandWidthBegin, bandWidthEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setBand("");
                                            } else {
                                                spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区带宽error");
                                        }

                                        try {
                                            if (spBean.getDown().equals("0")) {

                                            } else {
                                                spBeanList.add(spBean);//测试代码add
                                            }
                                        } catch (Exception e) {

                                        }

                                        //TddSpecialSf Patterns TDD特殊子帧配置
//                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str3.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
                                        //异频小区个数
                                        int InterFreqLstNum;
                                        try {
                                            if ("ffffffff".equals(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
                                                InterFreqLstNum = 0;
                                            } else {
                                                try {
                                                    InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                                } catch (Exception e) {
                                                    InterFreqLstNum = 0;
                                                    Log.d("nzqexce1", "run: " + e.getMessage());
                                                }
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                            }
                                        } catch (Exception e) {
                                            InterFreqLstNum = 0;
                                            Log.d("nzqexce3", "run: " + e.getMessage());
                                        }


                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
                                        System.out.println("异频小区个数：------" + InterFreqLstNum);

                                        dlEarfcnBegin = dlEarfcnBegin + 64;
                                        dlEarfcnEnd = dlEarfcnEnd + 64;
                                        pciBegin = pciBegin + 64;
                                        pciEnd = pciEnd + 64;
                                        tacBegin = tacBegin + 64;
                                        tacEnd = tacEnd + 64;
                                        plmnBegin = plmnBegin + 64;
                                        plmnEnd = plmnEnd + 64;
                                        celldBegin = celldBegin + 64;
                                        celldEnd = celldEnd + 64;
                                        priorityBegin = priorityBegin + 64;
                                        priorityEnd = priorityEnd + 64;
                                        rsrpBegin = rsrpBegin + 64;
                                        rsrpEnd = rsrpEnd + 64;
                                        rsrqBegin = rsrqBegin + 64;
                                        rsrqEnd = rsrqEnd + 64;
                                        bandWidthBegin = bandWidthBegin + 64;
                                        bandWidthEnd = bandWidthEnd + 64;
                                        tddSpecialSfBegin = tddSpecialSfBegin + 64;
                                        tddSpecialSfEnd = tddSpecialSfEnd + 64;
                                        //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;

                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                        for (int r = 0; r < InterFreqLstNum; r++) {
                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                            interFreqNghNumBegin = interFreqLstNumBegin + 24;
                                            interFreqNghNumEnd = interFreqLstNumEnd + 24;
                                            //异频小区的领区数目
//                                            System.out.println(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                            int interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                            System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                            int interFreqNghNum;
//                                        try {
//                                        if (str4.length() < interFreqNghNumEnd) {
//                                            continue;
//                                        }
                                            try {
                                                if (!TextUtils.isEmpty(str3)) {

                                                } else {
                                                    continue;
                                                }
                                                if ("ffffffff".equals(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {

                                                    interFreqNghNum = 0;
                                                } else {
                                                    interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                                }
                                            } catch (Exception e) {
                                                interFreqNghNum = 0;
//                                            Log.d("nzqexce2", "run: " + e.getMessage());
                                            }

//                                        } catch (Exception e) {
//                                            continue;
//                                        }

                                            for (int n = 0; n < interFreqNghNum; n++) {
                                                dlEarfcnBegin = dlEarfcnBegin + 8;
                                                dlEarfcnEnd = dlEarfcnEnd + 8;
                                                pciBegin = pciBegin + 8;
                                                pciEnd = pciEnd + 8;
                                                tacBegin = tacBegin + 8;
                                                tacEnd = tacEnd + 8;
                                                plmnBegin = plmnBegin + 8;
                                                plmnEnd = plmnEnd + 8;
                                                celldBegin = celldBegin + 8;
                                                celldEnd = celldEnd + 8;
                                                priorityBegin = priorityBegin + 8;
                                                priorityEnd = priorityEnd + 8;
                                                rsrpBegin = rsrpBegin + 8;
                                                rsrpEnd = rsrpEnd + 8;
                                                rsrqBegin = rsrqBegin + 8;
                                                rsrqEnd = rsrqEnd + 8;
                                                bandWidthBegin = bandWidthBegin + 8;
                                                bandWidthEnd = bandWidthEnd + 8;
                                                tddSpecialSfBegin = tddSpecialSfBegin + 8;
                                                tddSpecialSfEnd = tddSpecialSfEnd + 8;
                                                interFreqLstNumBegin = interFreqLstNumBegin + 8;
                                                interFreqLstNumEnd = interFreqLstNumEnd + 8;
                                                interFreqNghNumBegin = interFreqNghNumBegin + 8;
                                                interFreqNghNumEnd = interFreqNghNumEnd + 8;
                                            }

									/*int number = InterFreqLstNum-r;
                                    if(number!=1){
										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
									}*/
                                            dlEarfcnBegin = dlEarfcnBegin + 24;
                                            dlEarfcnEnd = dlEarfcnEnd + 24;
                                            pciBegin = pciBegin + 24;
                                            pciEnd = pciEnd + 24;
                                            tacBegin = tacBegin + 24;
                                            tacEnd = tacEnd + 24;
                                            plmnBegin = plmnBegin + 24;
                                            plmnEnd = plmnEnd + 24;
                                            celldBegin = celldBegin + 24;
                                            celldEnd = celldEnd + 24;
                                            priorityBegin = priorityBegin + 24;
                                            priorityEnd = priorityEnd + 24;
                                            rsrpBegin = rsrpBegin + 24;
                                            rsrpEnd = rsrpEnd + 24;
                                            rsrqBegin = rsrqBegin + 24;
                                            rsrqEnd = rsrqEnd + 24;
                                            bandWidthBegin = bandWidthBegin + 24;
                                            bandWidthEnd = bandWidthEnd + 24;
                                            tddSpecialSfBegin = tddSpecialSfBegin + 24;
                                            tddSpecialSfEnd = tddSpecialSfEnd + 24;
                                            interFreqLstNumBegin = interFreqLstNumBegin + 24;
                                            interFreqLstNumEnd = interFreqLstNumEnd + 24;

                                        }
                                        interFreqLstNumBegin = interFreqLstNumBegin + 64;
                                        interFreqLstNumEnd = interFreqLstNumEnd + 64;
                                        interFreqNghNumBegin = interFreqNghNumBegin + 64;
                                        interFreqNghNumEnd = interFreqNghNumEnd + 64;
                                    }
                                    str3 = "";
                                    Log.d("nzqrun77spBeanList", "nzqrun: " + "执行" + spBeanList);
                                    Constant.SPBEANLIST1Fragment = spBeanList;
                                    if (spBeanList.size() == 0) {
                                        Log.d(TAG, "nzqrunrun: " + "等于0");
                                    } else {
                                        Log.d(TAG, "nzqrunrun: " + "大于0");
                                        Constant.SPBEANLIST1 = spBeanList;
                                        Log.d("nzqspBeanList1", "" + spBeanList);
//                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
                                        //先根据优先级,如果优先级一样根据RSRP
                                        List<Integer> list = new ArrayList();
                                        String down1 = "";
                                        SpBean spBean1 = new SpBean();
                                        SpBean spBean2 = new SpBean();
                                        if (spBeanList.size() >= 2) {
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                list.add(spBeanList.get(i).getPriority());
                                            }
                                            Integer max = Collections.max(list);
                                            Log.d("Anzqmax", "大于2条run: " + max);
                                            list.remove(max);//最大的优先

                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max.equals(spBeanList.get(i).getPriority())) {
                                                    down1 = spBeanList.get(i).getDown();
                                                    spBean1 = spBeanList.get(i);
                                                }
                                            }
                                            //第二个优先
                                            String down2 = "";
                                            Integer max2 = Collections.max(list);
//                                    Log.d("Anzqmax2", "run: " + max);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max2.equals(spBeanList.get(i).getPriority())) {

                                                    down2 = spBeanList.get(i).getDown();
                                                    spBean2 = spBeanList.get(i);
                                                }
                                            }
                                            Log.d("down2a", "run: " + down2);
                                            if (max != max2) {

                                                if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    bundle.putString("sp1MAX2value", down2);
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "A大于2条run且优先级有区别但是频点一致: " + max);
                                                } else {
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }
                                                    }


                                                    bundle.putString("sp1MAX2value", down2);
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
                                                }


                                            } else {//根据优先级比较一致  ,通过rsrp比较

                                                int rssp1;
                                                int rssp2;
                                                List<Integer> list1rsp = new ArrayList<>();
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    list1rsp.add(spBeanList.get(i).getRsrp());
                                                }
                                                //最大的rsrp
                                                rssp1 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp1 == spBeanList.get(i).getRsrp()) {
                                                        down1 = spBeanList.get(i).getDown();
                                                        spBean1 = spBeanList.get(i);
                                                    }
                                                }
                                                for (int i = 0; i < list1rsp.size(); i++) {
                                                    if (list1rsp.get(i).equals(rssp1)) {
                                                        list1rsp.remove(i);
                                                    }
                                                }
                                                //求第二个rsrp
                                                rssp2 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp2 == spBeanList.get(i).getRsrp()) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                    }
                                                }
                                                if (down1.equals(down2)) {
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }

                                                    }
                                                    bundle.putString("sp1MAX2value", "");
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
                                                } else {
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    bundle.putString("sp1MAX2value", down2);
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
                                                }


//                                            ToastUtils.showToast("当前条数为多条");
//                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
                                            }

                                        } else {
                                            //

                                            if (spBeanList.size() > 0 && spBeanList.size() == 1) {
                                                down1 = spBeanList.get(0).getDown();
//                                            spBean2 = spBeanList.get(0);
                                                message = new Message();
                                                bundle.putString("sp1MAX1value", down1);
                                                bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
                                                bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
                                                bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
                                                bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
                                                bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");

                                                bundle.putString("sp1MAX2value", "");
                                                bundle.putString("sp2up", "");
                                                bundle.putString("sp2pci", "");
                                                bundle.putString("sp2plmn", "");
                                                bundle.putString("sp2band", "");
                                                bundle.putString("sp2tac", "");


                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 100152;
//                                            ToastUtils.showToast("当前条数为1");
                                                Log.d("Anzqmax", "当前条数为1: ");
                                            } else {
                                                message = new Message();
                                                bundle.putString("sp1MAX1value", "");
                                                bundle.putString("sp1MAX2value", "");
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 100152;
//                                            ToastUtils.showToast("当前条数为0");
                                                Log.d("Anzqmax", "当前条数为0: ");
                                            }
                                        }
                                    }


                                }

                            }


                        }

                        if ("0af0".equals(str.substring(8, 12))) {
                            if (!Flag19F0){//判断是否 f019和 0af合并



                                List<SpBean> spBeanList = new ArrayList<>();
                                //消息头长度
                                String length = str.substring(12, 16);
                                String len = StringPin(length);
                                Integer strlen = Integer.parseInt(len, 16);
                                //是否发送完
                                Integer.parseInt(StringPin(str.substring(20, 24)), 16);
                                String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));

                                code = StringAd(code);
                                System.out.println("南志强2F--" + str3);
                                System.err.println("code:" + code);
                                String s = str.substring(24, strlen * 2);
                                System.out.println("ss___" + s);
                                str3 = str3 + s;
                                System.err.println("s3___" + str3);
                                if ("0".equals(code.substring(0, 1))) {

                                    str3 = "aaaa55550af0240000ff0000" + str3;
                                    System.err.println("str3:+++++++++++++++" + str3);
                                    //采集的小区数目
                                    int row;
                                    if ("ffff".equals(str3.substring(24, 28))) {
                                        row = 0;
                                    } else {
                                        row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
                                        System.out.println("小区个数：" + row);
                                        int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                        System.out.println("小区个数下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                    }
                                    System.out.println("南志强F--" + str3);
                                    int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                    int pciBegin = 40, pciEnd = 44;
                                    int tacBegin = 44, tacEnd = 48;
                                    int plmnBegin = 48, plmnEnd = 52;
                                    int celldBegin = 56, celldEnd = 64;
                                    int priorityBegin = 64, priorityEnd = 72;
                                    int rsrpBegin = 72, rsrpEnd = 74;
                                    int rsrqBegin = 74, rsrqEnd = 76;
                                    int bandWidthBegin = 76, bandWidthEnd = 78;
                                    int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
                                    int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
                                    int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
                                    for (int i = 0; i < row; i++) {
                                        //下行频点
                                        SpBean spBean = new SpBean();
                                        try {
                                            System.out.println(str3.substring(dlEarfcnBegin, dlEarfcnEnd));
                                            if ("ffffffff".equals(str3.substring(dlEarfcnBegin, dlEarfcnEnd))) {
                                                System.out.println("null");
//                                            continue;
                                            } else {
                                                try {
                                                    System.out.println("下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                                    spBean.setDown(Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                                } catch (NumberFormatException e) {
                                                    spBean.setDown(null);
                                                }

                                            }
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setDown(null);
                                        }

                                        if (ID1TF.equals("TDD")) {
                                            spBean.setUp(255 + "");
                                        } else {
                                            if (!TextUtils.isEmpty(spBean.getDown())) {
                                                int i1 = Integer.parseInt(spBean.getDown()) + 18000;
                                                spBean.setUp(i1 + "");
                                            }
                                        }
                                        //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(0);
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);


                                        try {
                                            str3.substring(pciBegin, pciEnd);
                                            spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setPci(0);
                                        }
                                        //TAC
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                    spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));

                                        try {
                                            str3.substring(tacBegin, tacEnd);
                                            spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setTac(0);
                                        }


//                                    Log.d(TAG, "spBeanTAcrun: " + spBean.getTac());
//                                    spBean.setTac(0);
                                        //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
                                        try {
                                            if ("ffff".equals(str3.substring(plmnBegin, plmnEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPlmn(0 + "");
                                            } else {
                                                spBean.setPlmn(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (StringIndexOutOfBoundsException E) {
                                            spBean.setPlmn(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区PLMN error");
                                        }


//                                    spBean.setPlmn("0");
                                        //CellId

                                        try {
                                            System.out.println("ffffffff".equals(str3.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "------CellId：");
                                            if ("ffffffff".equals(str3.substring(celldBegin, celldEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setCid(0 + "");
                                            } else {
                                                spBean.setCid(Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setCid(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区CellId error");
                                        }

                                        //Priority 本小区频点优先级
                                        try {
                                            System.out.println(str3.substring(64, 72));
//                                        System.out.println("ffffffff".equals(str3.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
                                            if ("ffffffff".equals(str3.substring(priorityBegin, priorityEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPriority(0);
                                            } else {
                                                spBean.setPriority(Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setPriority(0);
                                            Log.d("nzqExceloing", "run:1 小区Priority error");
                                        }

                                        //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrpBegin, rsrpEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrp(0);
                                            } else {
                                                spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setRsrp(0);
                                            Log.d("nzqExceloing", "run:1 小区RSRPerror");
                                        }

//                                    spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));

                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrqBegin, rsrqEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrq(0);
                                            } else {
                                                spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区RSQPerror");
                                        }

                                        //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                    spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                        try {
                                            if ("ffffffff".equals(str3.substring(bandWidthBegin, bandWidthEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setBand("");
                                            } else {
                                                spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区带宽error");
                                        }

                                        try {
                                            if (spBean.getDown().equals("0")) {

                                            } else {
                                                spBeanList.add(spBean);//测试代码add
                                            }
                                        } catch (Exception e) {

                                        }

                                        //TddSpecialSf Patterns TDD特殊子帧配置
//                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str3.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
                                        //异频小区个数
                                        int InterFreqLstNum;
                                        try {
                                            if ("ffffffff".equals(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
                                                InterFreqLstNum = 0;
                                            } else {
                                                try {
                                                    InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                                } catch (Exception e) {
                                                    InterFreqLstNum = 0;
                                                    Log.d("nzqexce1", "run: " + e.getMessage());
                                                }
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                            }
                                        } catch (Exception e) {
                                            InterFreqLstNum = 0;
                                            Log.d("nzqexce3", "run: " + e.getMessage());
                                        }


                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
                                        System.out.println("异频小区个数：------" + InterFreqLstNum);

                                        dlEarfcnBegin = dlEarfcnBegin + 64;
                                        dlEarfcnEnd = dlEarfcnEnd + 64;
                                        pciBegin = pciBegin + 64;
                                        pciEnd = pciEnd + 64;
                                        tacBegin = tacBegin + 64;
                                        tacEnd = tacEnd + 64;
                                        plmnBegin = plmnBegin + 64;
                                        plmnEnd = plmnEnd + 64;
                                        celldBegin = celldBegin + 64;
                                        celldEnd = celldEnd + 64;
                                        priorityBegin = priorityBegin + 64;
                                        priorityEnd = priorityEnd + 64;
                                        rsrpBegin = rsrpBegin + 64;
                                        rsrpEnd = rsrpEnd + 64;
                                        rsrqBegin = rsrqBegin + 64;
                                        rsrqEnd = rsrqEnd + 64;
                                        bandWidthBegin = bandWidthBegin + 64;
                                        bandWidthEnd = bandWidthEnd + 64;
                                        tddSpecialSfBegin = tddSpecialSfBegin + 64;
                                        tddSpecialSfEnd = tddSpecialSfEnd + 64;
                                        //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;

                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                        for (int r = 0; r < InterFreqLstNum; r++) {
                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                            interFreqNghNumBegin = interFreqLstNumBegin + 24;
                                            interFreqNghNumEnd = interFreqLstNumEnd + 24;
                                            //异频小区的领区数目
//                                            System.out.println(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                            int interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                            System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                            int interFreqNghNum;
//                                        try {
//                                        if (str4.length() < interFreqNghNumEnd) {
//                                            continue;
//                                        }
                                            try {
                                                if (!TextUtils.isEmpty(str3)) {

                                                } else {
                                                    continue;
                                                }
                                                if ("ffffffff".equals(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {

                                                    interFreqNghNum = 0;
                                                } else {
                                                    interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                                }
                                            } catch (Exception e) {
                                                interFreqNghNum = 0;
//                                            Log.d("nzqexce2", "run: " + e.getMessage());
                                            }

//                                        } catch (Exception e) {
//                                            continue;
//                                        }

                                            for (int n = 0; n < interFreqNghNum; n++) {
                                                dlEarfcnBegin = dlEarfcnBegin + 8;
                                                dlEarfcnEnd = dlEarfcnEnd + 8;
                                                pciBegin = pciBegin + 8;
                                                pciEnd = pciEnd + 8;
                                                tacBegin = tacBegin + 8;
                                                tacEnd = tacEnd + 8;
                                                plmnBegin = plmnBegin + 8;
                                                plmnEnd = plmnEnd + 8;
                                                celldBegin = celldBegin + 8;
                                                celldEnd = celldEnd + 8;
                                                priorityBegin = priorityBegin + 8;
                                                priorityEnd = priorityEnd + 8;
                                                rsrpBegin = rsrpBegin + 8;
                                                rsrpEnd = rsrpEnd + 8;
                                                rsrqBegin = rsrqBegin + 8;
                                                rsrqEnd = rsrqEnd + 8;
                                                bandWidthBegin = bandWidthBegin + 8;
                                                bandWidthEnd = bandWidthEnd + 8;
                                                tddSpecialSfBegin = tddSpecialSfBegin + 8;
                                                tddSpecialSfEnd = tddSpecialSfEnd + 8;
                                                interFreqLstNumBegin = interFreqLstNumBegin + 8;
                                                interFreqLstNumEnd = interFreqLstNumEnd + 8;
                                                interFreqNghNumBegin = interFreqNghNumBegin + 8;
                                                interFreqNghNumEnd = interFreqNghNumEnd + 8;
                                            }

									/*int number = InterFreqLstNum-r;
                                    if(number!=1){
										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
									}*/
                                            dlEarfcnBegin = dlEarfcnBegin + 24;
                                            dlEarfcnEnd = dlEarfcnEnd + 24;
                                            pciBegin = pciBegin + 24;
                                            pciEnd = pciEnd + 24;
                                            tacBegin = tacBegin + 24;
                                            tacEnd = tacEnd + 24;
                                            plmnBegin = plmnBegin + 24;
                                            plmnEnd = plmnEnd + 24;
                                            celldBegin = celldBegin + 24;
                                            celldEnd = celldEnd + 24;
                                            priorityBegin = priorityBegin + 24;
                                            priorityEnd = priorityEnd + 24;
                                            rsrpBegin = rsrpBegin + 24;
                                            rsrpEnd = rsrpEnd + 24;
                                            rsrqBegin = rsrqBegin + 24;
                                            rsrqEnd = rsrqEnd + 24;
                                            bandWidthBegin = bandWidthBegin + 24;
                                            bandWidthEnd = bandWidthEnd + 24;
                                            tddSpecialSfBegin = tddSpecialSfBegin + 24;
                                            tddSpecialSfEnd = tddSpecialSfEnd + 24;
                                            interFreqLstNumBegin = interFreqLstNumBegin + 24;
                                            interFreqLstNumEnd = interFreqLstNumEnd + 24;

                                        }
                                        interFreqLstNumBegin = interFreqLstNumBegin + 64;
                                        interFreqLstNumEnd = interFreqLstNumEnd + 64;
                                        interFreqNghNumBegin = interFreqNghNumBegin + 64;
                                        interFreqNghNumEnd = interFreqNghNumEnd + 64;
                                    }
                                    str3 = "";
                                    Log.d("nzqrun77spBeanList", "nzqrun: " + "执行" + spBeanList);
                                    Constant.SPBEANLIST1Fragment = spBeanList;
                                    if (spBeanList.size() == 0) {
                                        Log.d(TAG, "nzqrunrun: " + "等于0");
                                    } else {
                                        Log.d(TAG, "nzqrunrun: " + "大于0");
                                        Constant.SPBEANLIST1 = spBeanList;
                                        Log.d("nzqspBeanList1", "" + spBeanList);
//                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
                                        //先根据优先级,如果优先级一样根据RSRP
                                        List<Integer> list = new ArrayList();
                                        String down1 = "";
                                        SpBean spBean1 = new SpBean();
                                        SpBean spBean2 = new SpBean();
                                        if (spBeanList.size() >= 2) {
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                list.add(spBeanList.get(i).getPriority());
                                            }
                                            Integer max = Collections.max(list);
                                            Log.d("Anzqmax", "大于2条run: " + max);
                                            list.remove(max);//最大的优先

                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max.equals(spBeanList.get(i).getPriority())) {
                                                    down1 = spBeanList.get(i).getDown();
                                                    spBean1 = spBeanList.get(i);
                                                }
                                            }
                                            //第二个优先
                                            String down2 = "";
                                            Integer max2 = Collections.max(list);
//                                    Log.d("Anzqmax2", "run: " + max);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max2.equals(spBeanList.get(i).getPriority())) {

                                                    down2 = spBeanList.get(i).getDown();
                                                    spBean2 = spBeanList.get(i);
                                                }
                                            }
                                            Log.d("down2a", "run: " + down2);
                                            if (max != max2) {

                                                if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    bundle.putString("sp1MAX2value", down2);
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "A大于2条run且优先级有区别但是频点一致: " + max);
                                                } else {
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }
                                                    }


                                                    bundle.putString("sp1MAX2value", down2);
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
                                                }


                                            } else {//根据优先级比较一致  ,通过rsrp比较

                                                int rssp1;
                                                int rssp2;
                                                List<Integer> list1rsp = new ArrayList<>();
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    list1rsp.add(spBeanList.get(i).getRsrp());
                                                }
                                                //最大的rsrp
                                                rssp1 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp1 == spBeanList.get(i).getRsrp()) {
                                                        down1 = spBeanList.get(i).getDown();
                                                        spBean1 = spBeanList.get(i);
                                                    }
                                                }
                                                for (int i = 0; i < list1rsp.size(); i++) {
                                                    if (list1rsp.get(i).equals(rssp1)) {
                                                        list1rsp.remove(i);
                                                    }
                                                }
                                                //求第二个rsrp
                                                rssp2 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp2 == spBeanList.get(i).getRsrp()) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                    }
                                                }
                                                if (down1.equals(down2)) {
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }

                                                    }
                                                    bundle.putString("sp1MAX2value", "");
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
                                                } else {
                                                    message = new Message();
                                                    bundle.putString("sp1MAX1value", down1);//下行
                                                    bundle.putString("sp1up", spBean1.getUp() + "");
                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
                                                    bundle.putString("sp1band", spBean1.getBand() + "");
                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    bundle.putString("sp1MAX2value", down2);
                                                    bundle.putString("sp2up", spBean2.getUp() + "");
                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
                                                    bundle.putString("sp2band", spBean2.getBand() + "");
                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
                                                    message.setData(bundle);
                                                    handler.sendMessage(message);
                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
                                                }


//                                            ToastUtils.showToast("当前条数为多条");
//                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
                                            }

                                        } else {
                                            //

                                            if (spBeanList.size() > 0 && spBeanList.size() == 1) {
                                                down1 = spBeanList.get(0).getDown();
//                                            spBean2 = spBeanList.get(0);
                                                message = new Message();
                                                bundle.putString("sp1MAX1value", down1);
                                                bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
                                                bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
                                                bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
                                                bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
                                                bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");

                                                bundle.putString("sp1MAX2value", "");
                                                bundle.putString("sp2up", "");
                                                bundle.putString("sp2pci", "");
                                                bundle.putString("sp2plmn", "");
                                                bundle.putString("sp2band", "");
                                                bundle.putString("sp2tac", "");


                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 100152;
//                                            ToastUtils.showToast("当前条数为1");
                                                Log.d("Anzqmax", "当前条数为1: ");
                                            } else {
                                                message = new Message();
                                                bundle.putString("sp1MAX1value", "");
                                                bundle.putString("sp1MAX2value", "");
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 100152;
//                                            ToastUtils.showToast("当前条数为0");
                                                Log.d("Anzqmax", "当前条数为0: ");
                                            }
                                        }
                                    }


                                }

                            }}
//                        if ("0af0".equals(str.substring(8, 12))) {
//                            List<SpBean> spBeanList = new ArrayList<>();
//                            //消息头长度
//                            String length = str.substring(12, 16);
//                            String len = StringPin(length);
//                            Integer strlen = Integer.parseInt(len, 16);
//                            //是否发送完
//                            Integer.parseInt(StringPin(str.substring(20, 24)), 16);
//                            String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));
//
//                            code = StringAd(code);
//                            System.out.println("南志强2F--" + str3);
//                            System.err.println("code:" + code);
//                            String s = str.substring(24, strlen * 2);
//                            System.out.println("ss___" + s);
//                            str3 = str3 + s;
//                            System.err.println("s3___" + str3);
//                            if ("0".equals(code.substring(0, 1))) {
//
//                                str3 = "aaaa55550af0240000ff0000" + str3;
//                                System.err.println("str3:+++++++++++++++" + str3);
//                                //采集的小区数目
//                                int row;
//                                if ("ffff".equals(str3.substring(24, 28))) {
//                                    row = 0;
//                                } else {
//                                    row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
//                                    System.out.println("小区个数：" + row);
//                                }
//                                System.out.println("南志强F--" + str3);
//                                int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
//                                int pciBegin = 40, pciEnd = 44;
//                                int tacBegin = 44, tacEnd = 48;
//                                int plmnBegin = 48, plmnEnd = 52;
//                                int celldBegin = 56, celldEnd = 64;
//                                int priorityBegin = 64, priorityEnd = 72;
//                                int rsrpBegin = 72, rsrpEnd = 74;
//                                int rsrqBegin = 74, rsrqEnd = 76;
//                                int bandWidthBegin = 76, bandWidthEnd = 78;
//                                int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
//                                int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
//                                int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
//                                for (int i = 0; i < row; i++) {
//                                    //下行频点
//                                    SpBean spBean = new SpBean();
//                                    try {
//                                        System.out.println(str3.substring(dlEarfcnBegin, dlEarfcnEnd));
//                                        if ("ffffffff".equals(str3.substring(dlEarfcnBegin, dlEarfcnEnd))) {
//                                            System.out.println("null");
////                                            continue;
//                                        } else {
//                                            try {
//                                                System.out.println("下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                                spBean.setDown(Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
//                                            } catch (NumberFormatException e) {
//                                                spBean.setDown(null);
//                                            }
//
//                                        }
//                                    } catch (StringIndexOutOfBoundsException e) {
//                                        spBean.setDown(null);
//                                    }
//
//                                    if (ID1TF.equals("TDD")) {
//                                        spBean.setUp(255 + "");
//                                    } else {
//                                        if (!TextUtils.isEmpty(spBean.getDown())) {
//                                            int i1 = Integer.parseInt(spBean.getDown()) + 18000;
//                                            spBean.setUp(i1 + "");
//                                        }
//                                    }
//                                    //PCI
////                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
////                                    spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
////                                    spBean.setPci(0);
////                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);
//
//
//                                    try {
//                                        str3.substring(pciBegin, pciEnd);
//                                        spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    } catch (StringIndexOutOfBoundsException e) {
//                                        spBean.setPci(0);
//                                    }
//                                    //TAC
////                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
////                                    spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//
//                                    try {
//                                        str3.substring(tacBegin, tacEnd);
//                                        spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                    } catch (StringIndexOutOfBoundsException e) {
//                                        spBean.setTac(0);
//                                    }
//
//
////                                    Log.d(TAG, "spBeanTAcrun: " + spBean.getTac());
////                                    spBean.setTac(0);
//                                    //PLMN
////                                    System.out.println(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
//                                    try {
//                                        if ("ffff".equals(str3.substring(plmnBegin, plmnEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setPlmn(0 + "");
//                                        } else {
//                                            spBean.setPlmn(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "");
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                    } catch (StringIndexOutOfBoundsException E) {
//                                        spBean.setPlmn(0 + "");
//                                        Log.d("nzqExceloing", "run:1 小区PLMN error");
//                                    }
//
//
////                                    spBean.setPlmn("0");
//                                    //CellId
//
//                                    try {
//                                        System.out.println("ffffffff".equals(str3.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "------CellId：");
//                                        if ("ffffffff".equals(str3.substring(celldBegin, celldEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setCid(0 + "");
//                                        } else {
//                                            spBean.setCid(Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "");
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                    } catch (Exception e) {
//                                        spBean.setCid(0 + "");
//                                        Log.d("nzqExceloing", "run:1 小区CellId error");
//                                    }
//
//                                    //Priority 本小区频点优先级
//                                    try {
//                                        System.out.println(str3.substring(64, 72));
////                                        System.out.println("ffffffff".equals(str3.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
//                                        if ("ffffffff".equals(str3.substring(priorityBegin, priorityEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setPriority(0);
//                                        } else {
//                                            spBean.setPriority(Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16));
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                    } catch (Exception e) {
//                                        spBean.setPriority(0);
//                                        Log.d("nzqExceloing", "run:1 小区Priority error");
//                                    }
//
//                                    //RSRP
////                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
//                                    try {
//                                        if ("ffffffff".equals(str3.substring(rsrpBegin, rsrpEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setRsrp(0);
//                                        } else {
//                                            spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                    } catch (Exception e) {
//                                        spBean.setRsrp(0);
//                                        Log.d("nzqExceloing", "run:1 小区RSRPerror");
//                                    }
//
////                                    spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
//                                    //RSRQ
////                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
////                                    spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//
//                                    try {
//                                        if ("ffffffff".equals(str3.substring(rsrqBegin, rsrqEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setRsrq(0);
//                                        } else {
//                                            spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                    } catch (Exception e) {
//                                        Log.d("nzqExceloing", "run:1 小区RSQPerror");
//                                    }
//
//                                    //Bandwidth小区工作带宽
////                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16));
////                                    spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
//                                    try {
//                                        if ("ffffffff".equals(str3.substring(bandWidthBegin, bandWidthEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setBand("");
//                                        } else {
//                                            spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                    } catch (Exception e) {
//                                        Log.d("nzqExceloing", "run:1 小区带宽error");
//                                    }
//
//                                    try {
//                                        if (spBean.getDown().equals("0")) {
//
//                                        } else {
//                                            spBeanList.add(spBean);//测试代码add
//                                        }
//                                    } catch (Exception e) {
//
//                                    }
//
//                                    //TddSpecialSf Patterns TDD特殊子帧配置
////                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str3.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
//                                    //异频小区个数
//                                    int InterFreqLstNum;
//                                    try {
//                                        if ("ffffffff".equals(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
//                                            InterFreqLstNum = 0;
//                                        } else {
//                                            try {
//                                                InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                            } catch (Exception e) {
//                                                InterFreqLstNum = 0;
//                                                Log.d("nzqexce1", "run: " + e.getMessage());
//                                            }
////                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                        }
//                                    } catch (Exception e) {
//                                        InterFreqLstNum = 0;
//                                        Log.d("nzqexce3", "run: " + e.getMessage());
//                                    }
//
//
//                                    System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
//                                    System.out.println("异频小区个数：------" + InterFreqLstNum);
//
//                                    dlEarfcnBegin = dlEarfcnBegin + 64;
//                                    dlEarfcnEnd = dlEarfcnEnd + 64;
//                                    pciBegin = pciBegin + 64;
//                                    pciEnd = pciEnd + 64;
//                                    tacBegin = tacBegin + 64;
//                                    tacEnd = tacEnd + 64;
//                                    plmnBegin = plmnBegin + 64;
//                                    plmnEnd = plmnEnd + 64;
//                                    celldBegin = celldBegin + 64;
//                                    celldEnd = celldEnd + 64;
//                                    priorityBegin = priorityBegin + 64;
//                                    priorityEnd = priorityEnd + 64;
//                                    rsrpBegin = rsrpBegin + 64;
//                                    rsrpEnd = rsrpEnd + 64;
//                                    rsrqBegin = rsrqBegin + 64;
//                                    rsrqEnd = rsrqEnd + 64;
//                                    bandWidthBegin = bandWidthBegin + 64;
//                                    bandWidthEnd = bandWidthEnd + 64;
//                                    tddSpecialSfBegin = tddSpecialSfBegin + 64;
//                                    tddSpecialSfEnd = tddSpecialSfEnd + 64;
//                                    //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;
//
//                                    System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
//                                    for (int r = 0; r < InterFreqLstNum; r++) {
//                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
//                                        interFreqNghNumBegin = interFreqLstNumBegin + 24;
//                                        interFreqNghNumEnd = interFreqLstNumEnd + 24;
//                                        //异频小区的领区数目
////                                            System.out.println(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd));
////                                            System.out.println("pin:" + StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
////                                            System.out.println(Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
////                                            int interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
////                                            System.out.println("异频小区的邻区个数：" + interFreqNghNum);
//                                        int interFreqNghNum;
////                                        try {
////                                        if (str4.length() < interFreqNghNumEnd) {
////                                            continue;
////                                        }
//                                        try {
//                                            if (!TextUtils.isEmpty(str3)) {
//
//                                            } else {
//                                                continue;
//                                            }
//                                            if ("ffffffff".equals(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {
//
//                                                interFreqNghNum = 0;
//                                            } else {
//                                                interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
////                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
//                                            }
//                                        } catch (Exception e) {
//                                            interFreqNghNum = 0;
////                                            Log.d("nzqexce2", "run: " + e.getMessage());
//                                        }
//
////                                        } catch (Exception e) {
////                                            continue;
////                                        }
//
//                                        for (int n = 0; n < interFreqNghNum; n++) {
//                                            dlEarfcnBegin = dlEarfcnBegin + 8;
//                                            dlEarfcnEnd = dlEarfcnEnd + 8;
//                                            pciBegin = pciBegin + 8;
//                                            pciEnd = pciEnd + 8;
//                                            tacBegin = tacBegin + 8;
//                                            tacEnd = tacEnd + 8;
//                                            plmnBegin = plmnBegin + 8;
//                                            plmnEnd = plmnEnd + 8;
//                                            celldBegin = celldBegin + 8;
//                                            celldEnd = celldEnd + 8;
//                                            priorityBegin = priorityBegin + 8;
//                                            priorityEnd = priorityEnd + 8;
//                                            rsrpBegin = rsrpBegin + 8;
//                                            rsrpEnd = rsrpEnd + 8;
//                                            rsrqBegin = rsrqBegin + 8;
//                                            rsrqEnd = rsrqEnd + 8;
//                                            bandWidthBegin = bandWidthBegin + 8;
//                                            bandWidthEnd = bandWidthEnd + 8;
//                                            tddSpecialSfBegin = tddSpecialSfBegin + 8;
//                                            tddSpecialSfEnd = tddSpecialSfEnd + 8;
//                                            interFreqLstNumBegin = interFreqLstNumBegin + 8;
//                                            interFreqLstNumEnd = interFreqLstNumEnd + 8;
//                                            interFreqNghNumBegin = interFreqNghNumBegin + 8;
//                                            interFreqNghNumEnd = interFreqNghNumEnd + 8;
//                                        }
//
//									/*int number = InterFreqLstNum-r;
//                                    if(number!=1){
//										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
//									}*/
//                                        dlEarfcnBegin = dlEarfcnBegin + 24;
//                                        dlEarfcnEnd = dlEarfcnEnd + 24;
//                                        pciBegin = pciBegin + 24;
//                                        pciEnd = pciEnd + 24;
//                                        tacBegin = tacBegin + 24;
//                                        tacEnd = tacEnd + 24;
//                                        plmnBegin = plmnBegin + 24;
//                                        plmnEnd = plmnEnd + 24;
//                                        celldBegin = celldBegin + 24;
//                                        celldEnd = celldEnd + 24;
//                                        priorityBegin = priorityBegin + 24;
//                                        priorityEnd = priorityEnd + 24;
//                                        rsrpBegin = rsrpBegin + 24;
//                                        rsrpEnd = rsrpEnd + 24;
//                                        rsrqBegin = rsrqBegin + 24;
//                                        rsrqEnd = rsrqEnd + 24;
//                                        bandWidthBegin = bandWidthBegin + 24;
//                                        bandWidthEnd = bandWidthEnd + 24;
//                                        tddSpecialSfBegin = tddSpecialSfBegin + 24;
//                                        tddSpecialSfEnd = tddSpecialSfEnd + 24;
//                                        interFreqLstNumBegin = interFreqLstNumBegin + 24;
//                                        interFreqLstNumEnd = interFreqLstNumEnd + 24;
//
//                                    }
//                                    interFreqLstNumBegin = interFreqLstNumBegin + 64;
//                                    interFreqLstNumEnd = interFreqLstNumEnd + 64;
//                                    interFreqNghNumBegin = interFreqNghNumBegin + 64;
//                                    interFreqNghNumEnd = interFreqNghNumEnd + 64;
//                                }
//                                str3 = "";
//                                Log.d("nzqrun77spBeanList", "nzqrun: " + "执行" + spBeanList);
//                                SPBEANLIST1Fragment = spBeanList;
//                                if (spBeanList.size() == 0) {
//                                    Log.d(TAG, "nzqrunrun: " + "等于0");
//                                } else {
//                                    Log.d(TAG, "nzqrunrun: " + "大于0");
//                                    SPBEANLIST1 = spBeanList;
//                                    Log.d("nzqspBeanList1", "" + spBeanList);
////                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
//                                    //先根据优先级,如果优先级一样根据RSRP
//                                    List<Integer> list = new ArrayList();
//                                    String down1 = "";
//                                    SpBean spBean1 = new SpBean();
//                                    SpBean spBean2 = new SpBean();
//                                    if (spBeanList.size() >= 2) {
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            list.add(spBeanList.get(i).getPriority());
//                                        }
//                                        Integer max = Collections.max(list);
//                                        Log.d("Anzqmax", "大于2条run: " + max);
//                                        list.remove(max);//最大的优先
//
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            if (max.equals(spBeanList.get(i).getPriority())) {
//                                                down1 = spBeanList.get(i).getDown();
//                                                spBean1 = spBeanList.get(i);
//                                            }
//                                        }
//                                        //第二个优先
//                                        String down2 = "";
//                                        Integer max2 = Collections.max(list);
////                                    Log.d("Anzqmax2", "run: " + max);
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            if (max2.equals(spBeanList.get(i).getPriority())) {
//
//                                                down2 = spBeanList.get(i).getDown();
//                                                spBean2 = spBeanList.get(i);
//                                            }
//                                        }
//                                        Log.d("down2a", "run: " + down2);
//                                        if (max != max2) {
//
//                                            if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", down2);
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "A大于2条run且优先级有区别但是频点一致: " + max);
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                        break;
//                                                    }
//                                                }
//
//
//                                                bundle.putString("sp1MAX2value", down2);
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
//                                            }
//
//
//                                        } else {//根据优先级比较一致  ,通过rsrp比较
//
//                                            int rssp1;
//                                            int rssp2;
//                                            List<Integer> list1rsp = new ArrayList<>();
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                list1rsp.add(spBeanList.get(i).getRsrp());
//                                            }
//                                            //最大的rsrp
//                                            rssp1 = Collections.max(list1rsp);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (rssp1 == spBeanList.get(i).getRsrp()) {
//                                                    down1 = spBeanList.get(i).getDown();
//                                                    spBean1 = spBeanList.get(i);
//                                                }
//                                            }
//                                            for (int i = 0; i < list1rsp.size(); i++) {
//                                                if (list1rsp.get(i).equals(rssp1)) {
//                                                    list1rsp.remove(i);
//                                                }
//                                            }
//                                            //求第二个rsrp
//                                            rssp2 = Collections.max(list1rsp);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (rssp2 == spBeanList.get(i).getRsrp()) {
//                                                    down2 = spBeanList.get(i).getDown();
//                                                    spBean2 = spBeanList.get(i);
//                                                }
//                                            }
//                                            if (down1.equals(down2)) {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                        break;
//                                                    }
//
//                                                }
//                                                bundle.putString("sp1MAX2value", "");
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", down2);
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
//                                            }
//
//
////                                            ToastUtils.showToast("当前条数为多条");
////                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
//                                        }
//
//                                    } else {
//                                        //
//
//                                        if (spBeanList.size() > 0 && spBeanList.size() == 1) {
//                                            down1 = spBeanList.get(0).getDown();
////                                            spBean2 = spBeanList.get(0);
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value", down1);
//                                            bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                            bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                            bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                            bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                            bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                            bundle.putString("sp1MAX2value", "");
//                                            bundle.putString("sp2up", "");
//                                            bundle.putString("sp2pci", "");
//                                            bundle.putString("sp2plmn", "");
//                                            bundle.putString("sp2band", "");
//                                            bundle.putString("sp2tac", "");
//
//
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 100152;
////                                            ToastUtils.showToast("当前条数为1");
//                                            Log.d("Anzqmax", "当前条数为1: ");
//                                        } else {
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value", "");
//                                            bundle.putString("sp1MAX2value", "");
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 100152;
////                                            ToastUtils.showToast("当前条数为0");
//                                            Log.d("Anzqmax", "当前条数为0: ");
//                                        }
//                                    }
//                                }
//
//
//                            }
//
//                        }


//                            //新
//                            if ("0af0".equals(str.substring(8, 12))) {
//                                i++;
//                                System.out.println("南志强加加"+str);
////                                List<SpBean> spBeanList = new ArrayList<>();
//                                //消息头长度
//                                String length = str.substring(12, 16);
//                                String len = StringPin(length);
//                                Integer strlen = Integer.parseInt(len, 16);
//                                //是否发送完
//                                Integer.parseInt(StringPin(str.substring(20, 24)), 16);
//                                String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));
//
//                                code = StringAd(code);
//
////                                System.err.println("code:" + code);
//                                String s = str.substring(24, strlen * 2);
////                                System.out.println("ss___" + s);
//                                str3 = str3 + s;
////                                System.err.println("s3___" + str3);
//
//                                if ("0".equals(code.substring(0, 1))) {
//                                    i++;
//
//                                    List<SpBean> spBeanList = new ArrayList<>();
//                                    str3 = "aaaa55550af0240000ff0000" + str3;
////                                    System.err.println("str3:+++++++++++++++" + str3);
//                                    //采集的小区数目
//                                   System.out.println("南志强F--"+str3);
//                                    int row;
//                                    if ("ffff".equals(str3.substring(24, 28))) {
//                                        row = 0;
//                                    } else {
//                                        row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
//                                        System.out.println("小区个数：" + row);
//                                    }
//
//                                    int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
//                                    int pciBegin = 40, pciEnd = 44;
//                                    int tacBegin = 44, tacEnd = 48;
//                                    int plmnBegin = 48, plmnEnd = 52;
//                                    int celldBegin = 56, celldEnd = 64;
//                                    int priorityBegin = 64, priorityEnd = 72;
//                                    int rsrpBegin = 72, rsrpEnd = 74;
//                                    int rsrqBegin = 74, rsrqEnd = 76;
//                                    int bandWidthBegin = 76, bandWidthEnd = 78;
//                                    int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
//                                    int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
//                                    int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
//                                    for (int i = 0; i < row; i++) {
//                                        SpBean spBean = new SpBean();
//                                        //下行频点
//                                        System.out.println(str3.substring(dlEarfcnBegin, dlEarfcnEnd));
//                                        if ("ffffffff".equals(str3.substring(dlEarfcnBegin, dlEarfcnEnd))) {
//                                            System.out.println("null");
//                                        } else {
//                                            System.out.println("下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                            spBean.setDown(Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
//                                        }
//                                        if (ID1TF.equals("TDD")) {
//                                            spBean.setUp(255 + "");
//                                        } else {
//                                            if (!TextUtils.isEmpty(spBean.getDown())) {
//                                                int i1 = Integer.parseInt(spBean.getDown() + 18000);
//                                                spBean.setUp(i1 + "");
//                                            }
//                                        }
//                                        //PCI
//                                        System.out.println("PCI：------" + Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                        spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                        System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);
//
//                                        //TAC
//                                        System.out.println("TAC：------" + Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                        spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                        //PLMN
//                                        System.out.println(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
//                                        spBean.setPlmn(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "");
//                                        //CellId
//                                        System.out.println("ffffffff".equals(str3.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "------CellId：");
//                                        //Priority 本小区频点优先级
//                                        System.out.println(str3.substring(64, 72));
//                                        System.out.println("ffffffff".equals(str3.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
//                                        if ("ffffffff".equals(str3.substring(priorityBegin, priorityEnd))) {
//                                            Log.d("1nzqffffffff", "run:1 ");
//                                            spBean.setPriority(0);
//                                        } else {
//                                            spBean.setPriority(Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16));
//                                            Log.d("2nzqffffffff", "run:1 ");
//                                        }
//                                        //RSRP
//                                        System.out.println("RSRP：------" + Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
//                                        spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//
//                                        //RSRQ
//                                        System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//                                        spBean.setRsrp(Integer.parseInt(StringPin(str.substring(rsrpBegin, rsrpEnd)), 16));
//                                        //Bandwidth小区工作带宽
//                                        System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                        spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
//                                        spBeanList.add(spBean);
//                                        //TddSpecialSf Patterns TDD特殊子帧配置
//                                        System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str3.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
//                                        //异频小区个数
//                                        int InterFreqLstNum;
//                                        if ("ffffffff".equals(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
//                                            InterFreqLstNum = 0;
//                                        } else {
//                                            InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                        }
//                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
//                                        System.out.println("异频小区个数：------" + InterFreqLstNum);
//
//                                        dlEarfcnBegin = dlEarfcnBegin + 64;
//                                        dlEarfcnEnd = dlEarfcnEnd + 64;
//                                        pciBegin = pciBegin + 64;
//                                        pciEnd = pciEnd + 64;
//                                        tacBegin = tacBegin + 64;
//                                        tacEnd = tacEnd + 64;
//                                        plmnBegin = plmnBegin + 64;
//                                        plmnEnd = plmnEnd + 64;
//                                        celldBegin = celldBegin + 64;
//                                        celldEnd = celldEnd + 64;
//                                        priorityBegin = priorityBegin + 64;
//                                        priorityEnd = priorityEnd + 64;
//                                        rsrpBegin = rsrpBegin + 64;
//                                        rsrpEnd = rsrpEnd + 64;
//                                        rsrqBegin = rsrqBegin + 64;
//                                        rsrqEnd = rsrqEnd + 64;
//                                        bandWidthBegin = bandWidthBegin + 64;
//                                        bandWidthEnd = bandWidthEnd + 64;
//                                        tddSpecialSfBegin = tddSpecialSfBegin + 64;
//                                        tddSpecialSfEnd = tddSpecialSfEnd + 64;
//                                        //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;
//
//                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
//                                        for (int r = 0; r < InterFreqLstNum; r++) {
//                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
//                                            interFreqNghNumBegin = interFreqLstNumBegin + 24;
//                                            interFreqNghNumEnd = interFreqLstNumEnd + 24;
//                                            //异频小区的领区数目
//                                            System.out.println(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                            int interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                            System.out.println("异频小区的邻区个数：" + interFreqNghNum);
//
//                                            for (int n = 0; n < interFreqNghNum; n++) {
//                                                dlEarfcnBegin = dlEarfcnBegin + 8;
//                                                dlEarfcnEnd = dlEarfcnEnd + 8;
//                                                pciBegin = pciBegin + 8;
//                                                pciEnd = pciEnd + 8;
//                                                tacBegin = tacBegin + 8;
//                                                tacEnd = tacEnd + 8;
//                                                plmnBegin = plmnBegin + 8;
//                                                plmnEnd = plmnEnd + 8;
//                                                celldBegin = celldBegin + 8;
//                                                celldEnd = celldEnd + 8;
//                                                priorityBegin = priorityBegin + 8;
//                                                priorityEnd = priorityEnd + 8;
//                                                rsrpBegin = rsrpBegin + 8;
//                                                rsrpEnd = rsrpEnd + 8;
//                                                rsrqBegin = rsrqBegin + 8;
//                                                rsrqEnd = rsrqEnd + 8;
//                                                bandWidthBegin = bandWidthBegin + 8;
//                                                bandWidthEnd = bandWidthEnd + 8;
//                                                tddSpecialSfBegin = tddSpecialSfBegin + 8;
//                                                tddSpecialSfEnd = tddSpecialSfEnd + 8;
//                                                interFreqLstNumBegin = interFreqLstNumBegin + 8;
//                                                interFreqLstNumEnd = interFreqLstNumEnd + 8;
//                                                interFreqNghNumBegin = interFreqNghNumBegin + 8;
//                                                interFreqNghNumEnd = interFreqNghNumEnd + 8;
//                                            }
//
//									/*int number = InterFreqLstNum-r;
//									if(number!=1){
//										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
//									}*/
//                                            dlEarfcnBegin = dlEarfcnBegin + 24;
//                                            dlEarfcnEnd = dlEarfcnEnd + 24;
//                                            pciBegin = pciBegin + 24;
//                                            pciEnd = pciEnd + 24;
//                                            tacBegin = tacBegin + 24;
//                                            tacEnd = tacEnd + 24;
//                                            plmnBegin = plmnBegin + 24;
//                                            plmnEnd = plmnEnd + 24;
//                                            celldBegin = celldBegin + 24;
//                                            celldEnd = celldEnd + 24;
//                                            priorityBegin = priorityBegin + 24;
//                                            priorityEnd = priorityEnd + 24;
//                                            rsrpBegin = rsrpBegin + 24;
//                                            rsrpEnd = rsrpEnd + 24;
//                                            rsrqBegin = rsrqBegin + 24;
//                                            rsrqEnd = rsrqEnd + 24;
//                                            bandWidthBegin = bandWidthBegin + 24;
//                                            bandWidthEnd = bandWidthEnd + 24;
//                                            tddSpecialSfBegin = tddSpecialSfBegin + 24;
//                                            tddSpecialSfEnd = tddSpecialSfEnd + 24;
//                                            interFreqLstNumBegin = interFreqLstNumBegin + 24;
//                                            interFreqLstNumEnd = interFreqLstNumEnd + 24;
//
//                                        }
//                                        interFreqLstNumBegin = interFreqLstNumBegin + 64;
//                                        interFreqLstNumEnd = interFreqLstNumEnd + 64;
//                                        interFreqNghNumBegin = interFreqNghNumBegin + 64;
//                                        interFreqNghNumEnd = interFreqNghNumEnd + 64;
//                                    }
//                                    str3 = "";
//                                    if (spBeanList.size() == 0) {
//
//                                    } else {
//                                        Log.d("nzqspBeanList1", "" + spBeanList);
////                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
//                                        //先根据优先级,如果优先级一样根据RSRP
//                                        List<Integer> list = new ArrayList();
//                                        String down1 = "";
//                                        SpBean spBean1 = new SpBean();
//                                        SpBean spBean2 = new SpBean();
//                                        if (spBeanList.size() >= 2) {
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                list.add(spBeanList.get(i).getPriority());
//                                            }
//                                            Integer max = Collections.max(list);
//                                            Log.d("Anzqmax", "大于2条run: " + max);
//                                            list.remove(max);//最大的优先
//
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (max.equals(spBeanList.get(i).getPriority())) {
//                                                    down1 = spBeanList.get(i).getDown();
//                                                    spBean1 = spBeanList.get(i);
//                                                }
//                                            }
//                                            //第二个优先
//                                            String down2 = "";
//                                            Integer max2 = Collections.max(list);
////                                    Log.d("Anzqmax2", "run: " + max);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (max2.equals(spBeanList.get(i).getPriority())) {
//
//                                                    down2 = spBeanList.get(i).getDown();
//                                                    spBean2 = spBeanList.get(i);
//                                                }
//                                            }
//                                            Log.d("down2a", "run: " + down2);
//                                            if (max != max2) {
//
//                                                if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
//                                                    Log.d("Anzqmax", "大于2条run且优先级有区别但是频点一致: " + max);
//                                                } else {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    for (int i = 0; i < spBeanList.size(); i++) {
//                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                            down2 = spBeanList.get(i).getDown();
//                                                            spBean2 = spBeanList.get(i);
//                                                            break;
//                                                        }
//                                                    }
//
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
//                                                    Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
//                                                }
//
//
//                                            } else {//根据优先级比较一致  ,通过rsrp比较
//
//                                                int rssp1;
//                                                int rssp2;
//                                                List<Integer> list1rsp = new ArrayList<>();
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    list1rsp.add(spBeanList.get(i).getRsrp());
//                                                }
//                                                //最大的rsrp
//                                                rssp1 = Collections.max(list1rsp);
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (rssp1 == spBeanList.get(i).getRsrp()) {
//                                                        down1 = spBeanList.get(i).getDown();
//                                                        spBean1 = spBeanList.get(i);
//                                                    }
//                                                }
//                                                for (int i = 0; i < list1rsp.size(); i++) {
//                                                    if (list1rsp.get(i).equals(rssp1)) {
//                                                        list1rsp.remove(i);
//                                                    }
//                                                }
//                                                //求第二个rsrp
//                                                rssp2 = Collections.max(list1rsp);
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (rssp2 == spBeanList.get(i).getRsrp()) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                    }
//                                                }
//                                                if (down1.equals(down2)) {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//                                                    for (int i = 0; i < spBeanList.size(); i++) {
//                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                            down2 = spBeanList.get(i).getDown();
//                                                            spBean2 = spBeanList.get(i);
//                                                            break;
//                                                        }
//
//                                                    }
//                                                    bundle.putString("sp1MAX2value", "");
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
//                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
//                                                } else {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
//                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
//                                                }
//
//
//                                                ToastUtils.showToast("当前条数为多条");
////                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
//                                            }
//
//                                        } else {
//                                            //
//
//                                            if (spBeanList.size() > 0 && spBeanList.size() == 1) {
//                                                down1 = spBeanList.get(0).getDown();
////                                            spBean2 = spBeanList.get(0);
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);
//                                                bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                                bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                                bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                                bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                                bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", "");
//                                                bundle.putString("sp2up", "");
//                                                bundle.putString("sp2pci", "");
//                                                bundle.putString("sp2plmn", "");
//                                                bundle.putString("sp2band", "");
//                                                bundle.putString("sp2tac", "");
//
//
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                ToastUtils.showToast("当前条数为1");
//                                                Log.d("Anzqmax", "当前条数为1: ");
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", "");
//                                                bundle.putString("sp1MAX2value", "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                ToastUtils.showToast("当前条数为0");
//                                                Log.d("Anzqmax", "当前条数为0: ");
//                                            }
//                                        }
//                                    }
//
//                                }
//
//                            }


//                            //扫频频点设置响应  旧
//                            if ("0af0".equals(str.substring(8, 12))) {
//                                //采集的小区数目
//                                int row;
//                                if ("ffff".equals(str.substring(24, 28))) {
//                                    row = 0;
//                                } else {
//                                    row = Integer.parseInt(StringPin(str.substring(24, 28)), 16);
//                                    System.out.println("小区个数：" + row);
//                                }
//                                List<SpBean> spBeanList = new ArrayList<>();
//                                int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
//                                int pciBegin = 40, pciEnd = 44;
//                                int tacBegin = 44, tacEnd = 48;
//                                int plmnBegin = 48, plmnEnd = 52;
//                                int celldBegin = 56, celldEnd = 64;
//                                int priorityBegin = 64, priorityEnd = 72;
//                                int rsrpBegin = 72, rsrpEnd = 74;
//                                int rsrqBegin = 74, rsrqEnd = 76;
//                                int bandWidthBegin = 76, bandWidthEnd = 78;
//                                int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
//                                int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
//                                int interFreqNghNumBegin = 112, interFreqNghNumEnd = 120;
//
//                                for (int i = 0; i < row; i++) {
//                                    SpBean spBean = new SpBean();
//                                    //下行频点
//                                    System.out.println(str.substring(dlEarfcnBegin, dlEarfcnEnd));
//
//                                    if ("ffffffff".equals(str.substring(dlEarfcnBegin, dlEarfcnEnd))) {
//                                        System.out.println("null");
//                                        spBean.setDown("null");
//                                    } else {
//                                        System.out.println("下行频点：------" + Integer.parseInt(StringPin(str.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                        spBean.setDown(Integer.parseInt(StringPin(str.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
//                                    }
//                                    if (ID1TF.equals("TDD")) {
//                                        spBean.setUp(255 + "");
//                                    } else {
//                                        if (!TextUtils.isEmpty(spBean.getDown())) {
//                                            int i1 = Integer.parseInt(spBean.getDown() + 18000);
//                                            spBean.setUp(i1 + "");
//                                        }
//                                    }
//                                    Log.d("spBeanup", "run: " + spBean.getUp());
//                                    //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(Integer.parseInt(StringPin(str.substring(pciBegin, pciEnd)), 16));
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);
//
//                                    //TAC
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str.substring(tacBegin, tacEnd)), 16));
//                                    spBean.setTac(Integer.parseInt(StringPin(str.substring(tacBegin, tacEnd)), 16));
//                                    //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
//
//                                    spBean.setPlmn(Integer.parseInt(StringPin(str.substring(plmnBegin, plmnEnd)), 16) + "");
//                                    //CellId
//                                    System.out.println("ffffffff".equals(str.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str.substring(celldBegin, celldEnd)), 16) + "------CellId：");
//                                    //Priority 本小区频点优先级
//                                    System.out.println("优先级--" + str.substring(64, 72));
//                                    System.out.println("ffffffff".equals(str.substring(priorityBegin, priorityEnd)) ? "null" : Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
//                                    Log.d("nzqffffffff", "ffffffff".equals(str.substring(priorityBegin, priorityEnd)) ? "0" : Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16) + "");
//                                    if ("ffffffff".equals(str.substring(priorityBegin, priorityEnd))) {
//                                        Log.d("1nzqffffffff", "run:1 ");
//                                        spBean.setPriority(0);
//                                    } else {
//                                        spBean.setPriority(Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16));
//                                        Log.d("2nzqffffffff", "run:1 ");
//                                    }
////
////
////                                    spBean.setPriority(Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16)+"");
//                                    //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str.substring(rsrpBegin, rsrpEnd)), 16));
//                                    //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrp(Integer.parseInt(StringPin(str.substring(rsrqBegin, rsrqEnd)), 16));
//
//                                    if (spBean.getDown().equals("0")) {
//
//                                    } else {
//                                        spBeanList.add(spBean);//测试代码add
//                                    }
//
//                                    //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                    spBean.setBand(Integer.parseInt(StringPin(str.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
//                                    //TddSpecialSf Patterns TDD特殊子帧配置
//                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
//                                    //异频小区个数
//                                    int InterFreqLstNum;
//                                    if ("ffffffff".equals(str.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
//                                        InterFreqLstNum = 0;
//                                    } else {
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                    }
//                                    System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
//                                    System.out.println("异频小区个数：------" + InterFreqLstNum);
//
//                                    dlEarfcnBegin = dlEarfcnBegin + 64;
//                                    dlEarfcnEnd = dlEarfcnEnd + 64;
//                                    pciBegin = pciBegin + 64;
//                                    pciEnd = pciEnd + 64;
//                                    tacBegin = tacBegin + 64;
//                                    tacEnd = tacEnd + 64;
//                                    plmnBegin = plmnBegin + 64;
//                                    plmnEnd = plmnEnd + 64;
//                                    celldBegin = celldBegin + 64;
//                                    celldEnd = celldEnd + 64;
//                                    priorityBegin = priorityBegin + 64;
//                                    priorityEnd = priorityEnd + 64;
//                                    rsrpBegin = rsrpBegin + 64;
//                                    rsrpEnd = rsrpEnd + 64;
//                                    rsrqBegin = rsrqBegin + 64;
//                                    rsrqEnd = rsrqEnd + 64;
//                                    bandWidthBegin = bandWidthBegin + 64;
//                                    bandWidthEnd = bandWidthEnd + 64;
//                                    tddSpecialSfBegin = tddSpecialSfBegin + 64;
//                                    tddSpecialSfEnd = tddSpecialSfEnd + 64;
//                                    interFreqLstNumBegin = interFreqLstNumBegin + 64;
//                                    interFreqLstNumEnd = interFreqLstNumEnd + 64;
//
//                                    System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
////                                    for (int r = 0; r < InterFreqLstNum; r++) {
////                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
////                                        //异频小区的领区数目
////                                        System.out.println(str.substring(interFreqNghNumBegin, interFreqNghNumEnd));
////                                        System.out.println("pin:" + StringPin(str.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
////                                        System.out.println(Integer.parseInt(StringPin(str.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
////                                        int interFreqNghNum = Integer.parseInt(StringPin(str.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
////                                        System.out.println("异频小区的邻区个数：" + interFreqNghNum);
////
////                                        for (int n = 0; n < interFreqNghNum; n++) {
////                                            dlEarfcnBegin = dlEarfcnBegin + 8;
////                                            dlEarfcnEnd = dlEarfcnEnd + 8;
////                                            pciBegin = pciBegin + 8;
////                                            pciEnd = pciEnd + 8;
////                                            tacBegin = tacBegin + 8;
////                                            tacEnd = tacEnd + 8;
////                                            plmnBegin = plmnBegin + 8;
////                                            plmnEnd = plmnEnd + 8;
////                                            celldBegin = celldBegin + 8;
////                                            celldEnd = celldEnd + 8;
////                                            priorityBegin = priorityBegin + 8;
////                                            priorityEnd = priorityEnd + 8;
////                                            rsrpBegin = rsrpBegin + 8;
////                                            rsrpEnd = rsrpEnd + 8;
////                                            rsrqBegin = rsrqBegin + 8;
////                                            rsrqEnd = rsrqEnd + 8;
////                                            bandWidthBegin = bandWidthBegin + 8;
////                                            bandWidthEnd = bandWidthEnd + 8;
////                                            tddSpecialSfBegin = tddSpecialSfBegin + 8;
////                                            tddSpecialSfEnd = tddSpecialSfEnd + 8;
////                                            interFreqLstNumBegin = interFreqLstNumBegin + 8;
////                                            interFreqLstNumEnd = interFreqLstNumEnd + 8;
////                                            interFreqNghNumBegin = interFreqNghNumBegin + 8;
////                                            interFreqNghNumEnd = interFreqNghNumEnd + 8;
////                                        }
////
////                                        int number = InterFreqLstNum - r;
////                                        if (number != 1) {
////                                            interFreqNghNumBegin = interFreqNghNumBegin + 24;
////                                            interFreqNghNumEnd = interFreqNghNumEnd + 24;
////                                        }
////                                        dlEarfcnBegin = dlEarfcnBegin + 24;
////                                        dlEarfcnEnd = dlEarfcnEnd + 24;
////                                        pciBegin = pciBegin + 24;
////                                        pciEnd = pciEnd + 24;
////                                        tacBegin = tacBegin + 24;
////                                        tacEnd = tacEnd + 24;
////                                        plmnBegin = plmnBegin + 24;
////                                        plmnEnd = plmnEnd + 24;
////                                        celldBegin = celldBegin + 24;
////                                        celldEnd = celldEnd + 24;
////                                        priorityBegin = priorityBegin + 24;
////                                        priorityEnd = priorityEnd + 24;
////                                        rsrpBegin = rsrpBegin + 24;
////                                        rsrpEnd = rsrpEnd + 24;
////                                        rsrqBegin = rsrqBegin + 24;
////                                        rsrqEnd = rsrqEnd + 24;
////                                        bandWidthBegin = bandWidthBegin + 24;
////                                        bandWidthEnd = bandWidthEnd + 24;
////                                        tddSpecialSfBegin = tddSpecialSfBegin + 24;
////                                        tddSpecialSfEnd = tddSpecialSfEnd + 24;
////                                        interFreqLstNumBegin = interFreqLstNumBegin + 24;
////                                        interFreqLstNumEnd = interFreqLstNumEnd + 24;
////
////                                    }
//                                    interFreqNghNumBegin = interFreqNghNumBegin + 64;
//                                    interFreqNghNumEnd = interFreqNghNumEnd + 64;
//                                }
//                                //测试代码37900
////                                SpBean spBean = new SpBean();
////                                spBean.setRsrp(0);
////                                spBean.setPriority(20);
////                                spBean.setDown("37900");
////                                spBean.setPci(1234);
////                                spBean.setTac(987654);
////                                spBean.setPlmn("46000");
////                                spBean.setBand(38+"");
////                                spBeanList.add(spBean);
////                                spBeanList.add(spBean);
////
////                                SpBean spBean22 = new SpBean();
////                                spBean22.setRsrp(0);
////                                spBean22.setPriority(0);
////                                spBean22.setDown("37200");
////                                spBeanList.add(spBean22);
//                                if (spBeanList.size() == 0) {
//
//                                } else {
//                                    Log.d("nzqspBeanList1", "" + spBeanList);
////                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
//                                    //先根据优先级,如果优先级一样根据RSRP
//                                    List<Integer> list = new ArrayList();
//                                    String down1 = "";
//                                    SpBean spBean1 = new SpBean();
//                                    SpBean spBean2 = new SpBean();
//                                    if (spBeanList.size() >= 2) {
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            list.add(spBeanList.get(i).getPriority());
//                                        }
//                                        Integer max = Collections.max(list);
//                                        Log.d("Anzqmax", "大于2条run: " + max);
//                                        list.remove(max);//最大的优先
//
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            if (max.equals(spBeanList.get(i).getPriority())) {
//                                                down1 = spBeanList.get(i).getDown();
//                                                spBean1 = spBeanList.get(i);
//                                            }
//                                        }
//                                        //第二个优先
//                                        String down2 = "";
//                                        Integer max2 = Collections.max(list);
////                                    Log.d("Anzqmax2", "run: " + max);
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            if (max2.equals(spBeanList.get(i).getPriority())) {
//
//                                                down2 = spBeanList.get(i).getDown();
//                                                spBean2 = spBeanList.get(i);
//                                            }
//                                        }
//                                        Log.d("down2a", "run: " + down2);
//                                        if (max != max2) {
//
//                                            if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", down2);
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点一致: " + max);
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                        break;
//                                                    }
//                                                }
//
//
//                                                bundle.putString("sp1MAX2value", down2);
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
//                                            }
//
//
//                                        } else {//根据优先级比较一致  ,通过rsrp比较
//
//                                            int rssp1;
//                                            int rssp2;
//                                            List<Integer> list1rsp = new ArrayList<>();
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                list1rsp.add(spBeanList.get(i).getRsrp());
//                                            }
//                                            //最大的rsrp
//                                            rssp1 = Collections.max(list1rsp);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (rssp1 == spBeanList.get(i).getRsrp()) {
//                                                    down1 = spBeanList.get(i).getDown();
//                                                    spBean1 = spBeanList.get(i);
//                                                }
//                                            }
//                                            for (int i = 0; i < list1rsp.size(); i++) {
//                                                if (list1rsp.get(i).equals(rssp1)) {
//                                                    list1rsp.remove(i);
//                                                }
//                                            }
//                                            //求第二个rsrp
//                                            rssp2 = Collections.max(list1rsp);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (rssp2 == spBeanList.get(i).getRsrp()) {
//                                                    down2 = spBeanList.get(i).getDown();
//                                                    spBean2 = spBeanList.get(i);
//                                                }
//                                            }
//                                            if (down1.equals(down2)) {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                        break;
//                                                    }
//
//                                                }
//                                                bundle.putString("sp1MAX2value", "");
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);//下行
//                                                bundle.putString("sp1up", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", down2);
//                                                bundle.putString("sp2up", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
//                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
//                                            }
//
//
//                                            ToastUtils.showToast("当前条数为多条");
////                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
//                                        }
//
//                                    } else {
//                                        //
//
//                                        if (spBeanList.size() > 0 && spBeanList.size() == 1) {
//                                            down1 = spBeanList.get(0).getDown();
////                                            spBean2 = spBeanList.get(0);
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value", down1);
//                                            bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                            bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                            bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                            bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                            bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                            bundle.putString("sp1MAX2value", "");
//                                            bundle.putString("sp2up", "");
//                                            bundle.putString("sp2pci", "");
//                                            bundle.putString("sp2plmn", "");
//                                            bundle.putString("sp2band", "");
//                                            bundle.putString("sp2tac", "");
//
//
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 100152;
//                                            ToastUtils.showToast("当前条数为1");
//                                            Log.d("Anzqmax", "当前条数为1: ");
//                                        } else {
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value", "");
//                                            bundle.putString("sp1MAX2value", "");
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 100152;
//                                            ToastUtils.showToast("当前条数为0");
//                                            Log.d("Anzqmax", "当前条数为0: ");
//                                        }
//                                    }
//                                }
//
////                                } else if (spBeanList.size() > 0 && spBeanList.size() == 1) {//如果只有一条
////                                    if (!spBeanList.get(0).getDown().equals("0")) {
////                                        down1 = spBeanList.get(0).getDown();
////                                        spBean2 = spBeanList.get(0);
////                                        message = new Message();
////                                        bundle.putString("sp1MAX1value", down1);
////                                        bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
////                                        bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
////                                        bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
////                                        bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
////                                        bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
////
////                                        bundle.putString("sp1MAX2value", "");
////                                        message.setData(bundle);
////                                        handler.sendMessage(message);
////                                        message.what = 100152;
////                                        ToastUtils.showToast("当前条数为1");
////                                        Log.d("Anzqmax", "当前条数为1: ");
////                                    }
////                                } else { //当没有大于1 或者大于2
////                                    message = new Message();
////                                    bundle.putString("sp1MAX1value", "");
////                                    bundle.putString("sp1MAX2value", "");
////                                    message.setData(bundle);
////                                    handler.sendMessage(message);
////                                    message.what = 100152;
////                                    ToastUtils.showToast("当前条数为0");
////                                    Log.d("Anzqmax", "当前条数为0: ");
////                                }
//                            }

                    }

                    //设备2
                    if (Constant.IP2.equals(dp.getAddress().getHostAddress())) {
                        mPressedTime2 = System.currentTimeMillis();
                        System.out.println("123456");
                        System.out.println("收到2A" + dp.getAddress().getHostAddress() + "发送数据：" + str);
                        //时间
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        System.out.println("时间：" + sdf.format(d));

                        if ("2cf0".equals(str.substring(8, 12))) {

                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设备型号：" + hexStringToString(str.substring(32, 46)));
                                Constant.DEVICENUMBER2 = hexStringToString(str.substring(32, 46));
                            } else if ("01000000".equals(str.substring(24, 32))) {
                                System.out.println("硬件版本：" + hexStringToString(str.substring(32, 34)));
                                Constant.HARDWAREVERSION2 = hexStringToString(str.substring(32, 34));
                            } else if ("02000000".equals(str.substring(24, 32))) {
                                System.out.println("软件版本：" + hexStringToString(str.substring(32, 106)));
                                Constant.SOFTWAREVERSION2 = hexStringToString(str.substring(32, 106));
                            } else if ("03000000".equals(str.substring(24, 32))) {
                                System.out.println("序列号SN:" + hexStringToString(str.substring(32, 70)));
                                Constant.SNNUMBER2 = hexStringToString(str.substring(32, 70));
                            } else if ("04000000".equals(str.substring(24, 32))) {
                                System.out.println("MAC地址：" + hexStringToString(str.substring(32, 66)));
                                Constant.MACADDRESS2 = hexStringToString(str.substring(32, 66));
                            } else if ("05000000".equals(str.substring(24, 32))) {
                                Constant.UBOOTVERSION2 = hexStringToString(str.substring(32, 47));
                                System.out.println("uboot版本号：" + hexStringToString(str.substring(32, 47)));
                            } else if ("06000000".equals(str.substring(24, 32))) {
                                System.out.println("板卡温度：" + hexStringToString(str.substring(32, 50)));
                                Constant.BOARDTEMPERATURE2 = hexStringToString(str.substring(32, 50));


                            }
                        }
                        if ("2ef0".equals(str.substring(8, 12))) {
                            Log.d("nzq2df0", str.toString());
                            System.out.println("同步类型" + str.toString());
                            String substring = str.substring(25, 26);
                            Constant.TBTYPE2 = substring.toString();
                            Constant.TBZT2 = str.substring(29, 30);
                            Log.d("Asubstring", "run: " + substring.toString());
                        }
                        if ("66f0".equals(str.substring(8, 12))) {
                            Constant.REQUEST2 = Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "";
                            Constant.ENDNUM2 = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            Constant.IMSINUM2 = Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "";
//                            Log.d("Asubstring66f0", "--run: " + Integer.parseInt(str.substring(24, 26), 16)+"/n"+ENDNUM1+"/n"+IMSINUM1);
                            String substring = str.substring(24, 32);
                            String substring1 = str.substring(32, 40);
                            String substring2 = str.substring(56, 64);
//                            Log.d("Asubstring66f0REQUEST1", "run: "+substring.toString());
//                            Log.d("Asubstring66f0ENDNUM1", "run: "+substring1.toString());
//                            Log.d("Asubstring66f0IMSINUM1", "run: "+substring2.toString());
//                            Log.d("Asubstring66f0", "run: "+str.toString());
//                            REQUEST2=      Integer.parseInt(StringPin(str.substring(24, 32)), 16)+"";
                            Log.d("2Asubstring66f0REQUEST1", "run: " + Constant.REQUEST2);
                            Log.d("2Asubstring66f0REQUEST1", "run: " + Constant.ENDNUM2);
                            Log.d("2Asubstring66f0REQUEST1", "run: " + Constant.IMSINUM2);
                            Log.d("2Asubstring66f0REQUEST1", "run: " + str.toString());
                        }
//                        if ("66f0".equals(str.substring(8, 12))) {
//                            REQUEST2 = Integer.parseInt(str.substring(24, 32), 16) + "";
//                            ENDNUM2 = Integer.parseInt(str.substring(32, 40), 16) + "";
//                            IMSINUM2 = Integer.parseInt(str.substring(48, 56), 16) + "";
//                            Log.d("Asubstring66f0", "--run: " + Integer.parseInt(str.substring(24, 26), 16)+"/n"+ENDNUM2+"/n"+IMSINUM2);
//                        }

                        //当前小区信息状态响应
                        if ("28f0".equals(str.substring(8, 12))) {
                            //PLMN
                            System.out.println(StringTOIMEI(str.substring(40, 50)));
                            Constant.PLMN2 = StringTOIMEI(str.substring(40, 50));
                            //上行频点
                            System.out.println(Integer.parseInt(StringPin(str.substring(24, 32)), 16));
                            Constant.UP2 = Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "";
                            //下行频点
                            System.out.println(Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                            Constant.DWON2 = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            //Band
                            System.out.println(Integer.parseInt(StringPin(str.substring(56, 64)), 16));
                            Constant.BAND2 = Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "";
                            //带宽
                            System.out.println(Integer.parseInt(str.substring(54, 56), 16));
                            Constant.DK2 = Integer.parseInt(str.substring(54, 56), 16) + "";
                            //TAC
                            System.out.println(Integer.parseInt(StringPin(str.substring(68, 72)), 16));
                            Constant.TAC2 = Integer.parseInt(StringPin(str.substring(68, 72)), 16) + "";
                            //PCI
                            System.out.println(Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                            Constant.PCI2 = Integer.parseInt(StringPin(str.substring(64, 68)), 16) + "";
                            //Cellld
                            System.out.println(Integer.parseInt(StringPin(str.substring(72, 80)), 16));
                            Constant.CELLID2 = Integer.parseInt(StringPin(str.substring(72, 80)), 16) + "";
                            //UePMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(80, 84)), 16));
                            Constant.DBM2 = Integer.parseInt(StringPin(str.substring(80, 84)), 16) + "";
                            //EnodeBPMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(84, 88)), 16));
//                            TYPE2 = Integer.parseInt(StringPin(str.substring(84, 88)), 16) + "";

                        }
                        //小区状态信息查询指令的响应
                        if ("30f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("小区空闲态");
                                Constant.TYPE2 = "小区空闲态";
                            } else if ("01000000".equals(str.substring(24, 32))) {
                                System.out.println("同步或扫频中");
                                Constant.TYPE2 = "同步或扫频中";
                            } else if ("02000000".equals(str.substring(24, 32))) {
                                System.out.println("小区激活中");
                                Constant.TYPE2 = "小区激活中";
                            } else if ("03000000".equals(str.substring(24, 32))) {
                                System.out.println("小区已激活");
                                Constant.TYPE2 = "小区已激活";
                            } else if ("04000000".equals(str.substring(24, 32))) {
                                System.out.println("小区去激活中");
                                Constant.TYPE2 = "小区去激活中";
                            }
                        }
                        //UE测量配置查询响应信息
                        if ("3ef0".equals(str.substring(8, 12))) {
                            //工作模式
                            if ("00".equals(str.substring(24, 26))) {
                                System.out.println("持续侦码模式");
                                Constant.GZMS2 = "持续侦码模式";
                            } else if ("01".equals(str.substring(24, 26))) {
                                System.out.println("周期侦码模式");
                                //周期模式参数，指示针对同一个IMSI 的抓号周期
                                System.out.println(Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                Constant.ZHZQ2 = "" + Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                Constant.GZMS2 = "周期侦码模式";
                            } else if ("02".equals(str.substring(24, 26))) {
                                System.out.println("定位模式");
                                Constant.GZMS2 = "定位模式";
                                //定位模式，定位的终端 IMSI
                                Constant.UEIMS2 = StringTOIMEI(str.substring(32, 62));
                                System.out.println("imsi:" + StringTOIMEI(str.substring(32, 62)));
                                //定位模式，终端测量值的上报周期,建议设置为 1024ms, 0：120ms,1：240ms,2：480ms,3：640ms,4：1024ms,5：2048ms

                                if ("00".equals(str.substring(66, 68))) {
                                    System.out.println("120ms");
                                    Constant.SBZQ2 = "120ms";
                                } else if ("01".equals(str.substring(66, 68))) {
                                    System.out.println("240ms");
                                    Constant.SBZQ2 = "240ms";
                                } else if ("02".equals(str.substring(66, 68))) {
                                    System.out.println("480ms");
                                    Constant.SBZQ2 = "480ms";
                                } else if ("03".equals(str.substring(66, 68))) {
                                    System.out.println("640ms");
                                    Constant.SBZQ2 = "640ms";
                                } else if ("04".equals(str.substring(66, 68))) {
                                    System.out.println("1024ms");
                                    Constant.SBZQ2 = "1024ms";
                                } else if ("05".equals(str.substring(66, 68))) {
                                    System.out.println("2048ms");
                                    Constant.SBZQ2 = "2048ms";
                                }
                                //定位模式，调度定位终端最大功率发射开关，需要设置为 0,0：enable,1：disable
                                if ("00".equals(str.substring(68, 70))) {
                                    System.out.println("enable");
                                    Constant.UEMAXOF2 = "开";
                                } else if ("01".equals(str.substring(68, 70))) {
                                    System.out.println("disable");
                                    Constant.UEMAXOF2 = "关";
                                }
                                //定位模式，UE 最大发射功率，最大值 不 超 过wrFLLmtToEnbSysArfcnCfg 配置的UePMax，建议设置为 23dBm
                                Constant.UEMAX2 = Integer.parseInt(str.substring(70, 72), 16) + "";
                                System.out.println(Integer.parseInt(str.substring(70, 72), 16));

                                //定位模式，由于目前都采用 SRS 方案配合单兵，因此此值需要设置为disable，否则大用户量时有定位终端掉线概率。0: disable,1: enable
                                if ("00".equals(str.substring(72, 74))) {
                                    System.out.println("disable");
                                } else if ("01".equals(str.substring(72, 74))) {
                                    System.out.println("enable");
                                }
                                //定位模式，是否把非定位终端踢回公网的配置，建议设置为 0。设置为 1 的方式是为了发布版本时保留用户反复接入基站测试使用。1：非定位终端继续被本小区吸附, 0：把非定位终端踢回公网
                                if ("00".equals(str.substring(76, 78))) {
                                    System.out.println("把非定位终端踢回公网");
                                } else if ("01".equals(str.substring(76, 78))) {
                                    System.out.println("非定位终端继续被本小区吸附");
                                }
                                //定位模式，是否对定位终端建立SRS 配置。
                                if ("00".equals(str.substring(78, 80))) {
                                    System.out.println("disable");
                                } else if ("01".equals(str.substring(78, 80))) {
                                    System.out.println("enable");
                                }
                            } else if ("03".equals(str.substring(24, 26))) {
                                System.out.println("管控模式");
                                Constant.GZMS2 = "管控模式";
                                //管控模式的子模式0：黑名单子模式；1：白名单子模式
                                if ("00".equals(str.substring(80, 82))) {
                                    System.out.println("黑名单子模式");
                                    Constant.GZMS2 = "黑名单子模式";
                                } else if ("01".equals(str.substring(80, 82))) {
                                    System.out.println("白名单子模式");
                                    Constant.GZMS2 = "白名单子模式";
                                }
                            } else if ("04".equals(str.substring(24, 26))) {
                                System.out.println("重定向模式");
                                Constant.GZMS2 = "重定向模式";
                            /*0: 名单中的用户执行重定向；名单外的全部踢回公网
                            1: 名单中的用户踢回公网；名单外的全部重定向
							2: 名单中的用户执行重定向；名单外的全部吸附在本站
							3: 名单中的用户吸附在本站;名单外的全部重定向
							4: 所有目标重定向*/
                                System.out.println(Integer.parseInt(str.substring(26, 28), 16));

                            }

                        }

                        if ("56f0".equals(str.substring(8, 12))) {

                            //黑名单数量
                            Integer blacklistNum = Integer.parseInt(StringPin(str.substring(24, 28)), 16);
                            int begin = 28;
                            int end = 58;
                            for (int i = 0; i < blacklistNum; i++) {
                                System.out.println("黑名单打印2" + StringTOIMEI(str.substring(begin, end)));
//                                for (int j = 0; j < BLACKLISTSB2.size(); j++) {
//                                    if (BLACKLISTSB2.get(i).equals(StringTOIMEI(str.substring(begin, end)))) {
//
//                                    }
//                                }
                                Constant.BLACKLISTSB2.add(StringTOIMEI(str.substring(begin, end)));
                                begin = begin + 34;
                                end = end + 34;
                            }

                        }
                        //接受增益和发射功率查询的响应
                        if ("32f0".equals(str.substring(8, 12))) {
                            //增益
                            //寄存器中的值，实际生效的值（FDD 模式下仅在建立完小区查询，该值有效）
                            System.out.println(Integer.parseInt(str.substring(24, 26), 16));
                            Constant.ZENGYI2 = Integer.parseInt(str.substring(24, 26), 16) + "";
                            //发射功率  衰减
                            //寄存器中的值，实际生效的值（FDD 模式下仅在建立完小区查询，该值有效）
                            System.out.println(Integer.parseInt(str.substring(28, 30), 16));
                            Constant.SHUAIJIAN2 = Integer.parseInt(str.substring(28, 30), 16) + "";

//                            //数据库中的保存值，重启保留生效的值,
//                            System.out.println(Integer.parseInt(str.substring(26,28),16));
//
//                            //数据库中的保存值，重启保留生效的值
//                            System.out.println(Integer.parseInt(str.substring(30,32),16));
//                            //FDD AGC 开关
//                            if("00".equals(str.substring(30,32))){
//                                System.out.println("FDD AGC 开关关闭");
//                            }else if("01".equals(str.substring(30,32))){
//                                System.out.println("FDD AGC 开关打开");
//                            }

                            //增益
                            //只在FDD模式下有效，寄存器中的值，实际生效的值,该值只有在扫频完成后，建立小区前查询有效
//                            System.out.println(Integer.parseInt(str.substring(34,36),16));
//                            //eeprom 中的保存值，重启保留生效的值
//                            System.out.println(Integer.parseInt(str.substring(36,38),16));
                        }


                        //设置白名单响应 清空响应
                        if ("3af0".equals(str.substring(8, 12))) {
                            if ("00".equals(str.substring(24, 26))) {
                                opionts1++;
                                System.out.println("设置基站白名单成功" + opionts1);
                                Log.d("setwhtite", "run: " + str);
                                message = new Message();
                                bundle.putString("200191", "1");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200191;
                            } else {
                                System.out.println("设置基站白名单失败");
                                Log.d("setwhtite", "run: " + str);
                                message = new Message();
                                bundle.putString("200191", "2");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200191;
                            }
                        }
                        //设置黑名单响应 清空响应
                        if ("54f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                opionts11++;
                                System.out.println("设置基站黑名单成功" + opionts1);
                                if (opionts11 % 2 == 0) {
                                    Log.d("jsgs", "run:偶数");
                                    message = new Message();
                                    bundle.putString("200130", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 200131;
                                } else {
                                    Log.d("jsgs", "run:基数");
                                    message = new Message();
                                    bundle.putString("200131", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 200130;
                                }
                            } else {
                                System.out.println("设置基站黑名单失败");
                                if (opionts22 % 2 == 0) {
                                    Log.d("jsgs", "run:偶数");
                                    message = new Message();
                                    bundle.putString("200130", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 200135;
                                } else {
                                    Log.d("jsgs", "run:基数");
                                    message = new Message();
                                    bundle.putString("200131", "FDD");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 200134;
                                }
                            }
                        }

                        //黑名单中标
                        if ("05f0".equals(str.substring(8, 12))) {
                            System.out.println("2黑名单中标IMSI号：" + hexStringToString(str.substring(32, 62)));
                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(32, 62));
                            Log.d("jsgs", "run:基数");
                            message = new Message();
                            bundle.putString("imsi", imsi);
                            bundle.putString("sb", "2");
                            bundle.putString("zb", "");
                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 30000;
                        }

                        //设置定位模式的应答信息
                        if ("07f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设置基站ue定位成功");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200136;
                            } else {
                                System.out.println("设置基站ue定位失败");


                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200137;
                            }
                        }
                        //重启是否成功
                        if ("0cf0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("重启设置成功！");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200138;
                            } else {
                                System.err.println("重启失败！");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200139;
                            }
                        }
                        //制式切换是否成功
                        if ("02f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("制式切换成功！");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200140;
                            } else {
                                System.err.println("制式切换失败");
                                message = new Message();
                                bundle.putString("test", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200141;
                            }
                        }
                        //模式：FDD TDD
                        if ("00ff".equals(str.substring(16, 20))) {
                            //设置模式FDD
                            ID2TF = "FDD";
                            System.err.println("FDD");
                            message = new Message();
                            bundle.putString("tf2", "FDD");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 200110;
                        }
                        if ("ff00".equals(str.substring(16, 20))) {
                            //设置模式TDD
                            ID2TF = "TDD";
                            System.out.println("TDD");
                            message = new Message();
                            bundle.putString("tf2", "TDD");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 200110;
                        }
                        //建立小区是否成功
                        if ("04f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200145;
                                System.out.println("设置成功！开始建立小区！");
                            } else {
                                System.err.println("设置失败！");
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200146;
                            }
                        }
                        //公放设置
                        if ("a0f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("公放设置成功！");
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
//                                message.what = 200142;
                            } else {
                                System.err.println("设置失败！");
                                message = new Message();
                                bundle.putString("test", "0");
                                message.setData(bundle);
                                handler.sendMessage(message);
//                                message.what = 200143;
                            }
                        }
                        //去激活小区是否成功
                        if ("0ef0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("设置成功！去激活小区成功，停止定位！");
                            } else {
                                System.err.println("设置失败！");
                            }
                        }
                        //基站执行状态
                        //基站执行状态
                        if ("19f0".equals(str.substring(8, 12))) {
                            String state = str.substring(24, 32);
                            System.out.println("state" + state);
                            Log.d("wtto", "staterun: " + state);
                            if ("00000000".equals(state)) {
                                System.err.println("空口同步成功");
                                Log.d("wtto", "qqqrun:空口同步成功");
                                message = new Message();
                                bundle.putString("test", "1");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("01000000".equals(state)) {
                                System.err.println("空口同步失败");
                                Log.d("wtto", "qqqrun:空口同步失败");
                                message = new Message();
                                bundle.putString("test", "2");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("02000000".equals(state)) {
                                System.err.println("GPS同步成功");
                                Log.d("wtto", "qqqrun:GPS同步成功");
                                message = new Message();
                                bundle.putString("test", "3");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("03000000".equals(state)) {
                                System.err.println("GPS同步失败");
                                Log.d("wtto", "qqqrun:GPS同步失败");
                                message = new Message();
                                bundle.putString("test", "4");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("04000000".equals(state)) {
                                System.err.println("扫频成功");
                                Log.d("wtto", "qqqrun:扫频成功");
                                message = new Message();
                                bundle.putString("test", "5");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("05000000".equals(state)) {
                                System.err.println("扫频失败");
                                Log.d("wtto", "qqqrun:扫频失败");
                                message = new Message();
                                bundle.putString("test", "6");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("06000000".equals(state)) {
                                System.err.println("小区激活成功");
                                Log.d("wtto", "qqqrun:小区激活成功");
                                message = new Message();
                                bundle.putString("test", "7");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("07000000".equals(state)) {
                                System.err.println("小区激活失败");
                                Log.d("wtto", "qqqrun:小区激活失败");
                                message = new Message();
                                bundle.putString("test", "8");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("08000000".equals(state)) {
                                System.err.println("小区去激活");
                                Log.d("wtto", "qqqrun:小区去激活");
                                message = new Message();
                                bundle.putString("test", "9");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("09000000".equals(state)) {
                                System.err.println("空口同步中");
                                Log.d("wtto", "qqqrun:空口同步中");
                                message = new Message();
                                bundle.putString("test", "10");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("0a000000".equals(state)) {
                                System.err.println("GPS同步中");
                                Log.d("wtto", "qqqrun:GPS同步中");
                                message = new Message();
                                bundle.putString("test", "11");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("0b000000".equals(state)) {
                                System.err.println("扫频中");
                                Log.d("wtto", "qqqrun:扫频中");
                                message = new Message();
                                bundle.putString("test", "12");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("0c000000".equals(state)) {
                                System.err.println("小区激活中");
                                Log.d("wtto", "qqqrun:小区激活中");
                                message = new Message();
                                bundle.putString("test", "13");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            } else if ("0d000000".equals(state)) {
                                System.err.println("小区去激活中");
                                Log.d("wtto", "qqqrun:小区去激活中");
                                message = new Message();
                                bundle.putString("test", "14");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 2001200;
                            }
                        }
                        if ("08f0".equals(str.substring(8, 12))) {

                            //目标距离（16进制字符串转换成十进制）
                            Integer.parseInt(str.substring(24, 26), 16);
                            System.out.println("距离：" + Integer.parseInt(str.substring(24, 26), 16));
                            message = new Message();
                            bundle.putString("sb1j2", Integer.parseInt(str.substring(24, 26), 16) + "");
                            bundle.putString("imsi2", hexStringToString(str.substring(26, 56)) + "");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 200147;
                            //IMSI号
                            StringTOIMEI(str.substring(26, 56));
                            System.out.println("IMSI号zb：" + hexStringToString(str.substring(26, 56)));
                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(26, 56));
                            message = new Message();
                            bundle.putString("imsi", imsi);
                            bundle.putString("sb", "2");
                            bundle.putString("zb", "2");
                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 30000;


                            message = new Message();
                            bundle.putString("imsi2", imsi);
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 200148;
                        }

                        if ("10f0".equals(str.substring(8, 12))) {
                            //心跳解析
                            //查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
                            message = new Message();
                            bundle.putString("zt1", "2");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 200120;
                            if ("0000".equals(str.substring(24, 28))) {
                                System.out.println("0：小区 IDLE态");
                                message = new Message();
                                bundle.putString("zt2", "2");//IDLE态
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200120;
                            } else if ("0100".equals(str.substring(24, 28))) {
                                System.out.println("1：扫频/同步进行中");

                                message = new Message();
                                bundle.putString("zt2", "3");//扫频同步进行中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200120;
                            } else if ("0200".equals(str.substring(24, 28))) {
                                System.out.println("2：小区激活中");
                                message = new Message();
                                bundle.putString("zt2", "4");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 100120;
                            } else if ("0300".equals(str.substring(24, 28))) {
                                System.out.println("3：定位中");
                                message = new Message();
                                bundle.putString("zt2", "5");//小区激活态
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200120;
                                //Band号
                                Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                System.out.println("Band号：" + Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                //上行频点
                                Integer.parseInt(StringPin(str.substring(32, 40)), 16);
                                System.out.println("上行频点：" + Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                                //下行频点
                                Integer.parseInt(StringPin(str.substring(40, 48)), 16);
                                Constant.DOWNPIN2 = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
                                System.out.println("下行频点：" + Integer.parseInt(StringPin(str.substring(40, 48)), 16));
                                message = new Message();
                                bundle.putString("down", Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");//查询增益
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200151;
                                System.out.println("200151");
                                //移动联通电信
                                if ("3436303030".equals(str.substring(48, 58))) {
                                    //设置中国移动
                                }
                                if ("3436303031".equals(str.substring(48, 58))) {
                                    //设置中国联通
                                }
                                if ("3436303033".equals(str.substring(48, 58)) || "3436303131".equals(str.substring(48, 58))) {
                                    //设置中国电信
                                }

                                //PCI
                                Integer.parseInt(StringPin(str.substring(64, 68)), 16);
                                System.out.println("PCI:" + Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                                //TAC
                                Integer.parseInt(StringPin(str.substring(68, 72)), 16);
                                System.out.println("TAC:" + Integer.parseInt(StringPin(str.substring(68, 72)), 16));

                            } else if ("0400".equals(str.substring(24, 28))) {
                                System.out.println("4：小区去激活中");
                            }

                        }
                        //温度告警
                        if ("5bf0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(32, 40))) {
                                System.out.println("基带板温度超过70度");
                            }
                            if ("01000000".equals(str.substring(32, 40))) {
                                System.out.println("基带板温度降低到70度以下了");
                            }

                        }
                        if ("32f0".equals(str.substring(8, 12))) {
                            message = new Message();
                            int i = Integer.parseInt(str.substring(24, 26), 16);

                            Log.d(TAG, "run: " + i);
                            bundle.putString("zy2", Integer.parseInt(str.substring(24, 26), 16) + "");//查询增益
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 200149;
                            System.out.println("200149");
                        }

                        //设置增益是否成功
                        if ("14f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("增益值设置成功!");
                                message = new Message();
                                bundle.putString("zyset2", "增益值设置成功");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200150;
                                System.out.println("200150");

                            } else {
                                System.err.println("增益值设置失败!");
                                message = new Message();
                                bundle.putString("zyset2", "增益值设置失败");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200150;
                                System.out.println("200150");
                            }
                        }
                        if ("7ef0".equals(str.substring(8, 12))) {
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("SNF端口扫频设置成功！");

                                message = new Message();
                                bundle.putString("snf", "SNF端口扫频设置成功");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200154;
                                System.out.println("100154");

                            } else {
                                System.err.println("SNF端口扫频设置失败！");
                                message = new Message();
                                bundle.putString("snf", "SNF端口扫频设置失败");//小区激活中
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200154;
                                System.out.println("100154");
                            }
                        }
                        //新 54

                        if ("0af0".equals(str.substring(8, 12))) {
                            //消息头长度
                            List<SpBean> spBeanList = new ArrayList<>();
                            String length = str.substring(12, 16);
                            String len = StringPin(length);
                            Integer strlen = Integer.parseInt(len, 16);
                            //是否发送完
                            Integer.parseInt(StringPin(str.substring(20, 24)), 16);
                            String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));

                            code = StringAd(code);

                            System.err.println("code:" + code);
                            String s = str.substring(24, strlen * 2);
                            System.out.println("ss___" + s);
                            str4 = str4 + s;
                            System.err.println("s3___" + str4);
                            if ("0".equals(code.substring(0, 1))) {

                                str4 = "aaaa55550af0240000ff0000" + str4;
                                System.err.println("str3:+++++++++++++++" + str4);
                                //采集的小区数目
                                int row;
                                if ("ffff".equals(str4.substring(24, 28))) {
                                    row = 0;
                                } else {
                                    row = Integer.parseInt(StringPin(str4.substring(24, 28)), 16);
                                    System.out.println("小区个数：" + row);
                                }

                                int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                int pciBegin = 40, pciEnd = 44;
                                int tacBegin = 44, tacEnd = 48;
                                int plmnBegin = 48, plmnEnd = 52;
                                int celldBegin = 56, celldEnd = 64;
                                int priorityBegin = 64, priorityEnd = 72;
                                int rsrpBegin = 72, rsrpEnd = 74;
                                int rsrqBegin = 74, rsrqEnd = 76;
                                int bandWidthBegin = 76, bandWidthEnd = 78;
                                int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
                                int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
                                int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
                                for (int i = 0; i < row; i++) {
                                    //下行频点
                                    //下行频点
                                    SpBean spBean = new SpBean();
                                    System.out.println(str4.substring(dlEarfcnBegin, dlEarfcnEnd));
                                    if ("ffffffff".equals(str4.substring(dlEarfcnBegin, dlEarfcnEnd))) {
                                        System.out.println("null");

                                    } else {
                                        try {
//                                            System.out.println("下行频点：------" + Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                            spBean.setDown(Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                            try {
                                                System.out.println("下行频点：------" + Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                                spBean.setDown(Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                            } catch (NumberFormatException e) {
                                                spBean.setDown(null);
                                            }
                                        } catch (NumberFormatException e) {
                                            spBean.setDown(null);
                                        }
//                                        System.out.println("下行频点：------" + Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                        spBean.setDown(Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                    }
                                    if (ID2TF.equals("TDD")) {
                                        spBean.setUp(255 + "");
                                    } else {
                                        if (!TextUtils.isEmpty(spBean.getDown())) {
                                            int i1 = Integer.parseInt(spBean.getDown()) + 18000;
                                            spBean.setUp(i1 + "");
                                        }
                                    }
                                    //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str4.substring(pciBegin, pciEnd)), 16));
                                    if ("ffff".equals(str4.substring(pciBegin, pciEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setPci(0);
                                    } else {
                                        spBean.setPci(Integer.parseInt(StringPin(str4.substring(pciBegin, pciEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }

//                                    spBean.setPci(Integer.parseInt(StringPin(str4.substring(pciBegin, pciEnd)), 16));
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);
//                                    spBean.setPci(0);
//                                    TAC
                                    try {
                                        str4.substring(tacBegin, tacEnd);
                                        spBean.setTac(Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));
                                    } catch (StringIndexOutOfBoundsException e) {
                                        spBean.setTac(0);
                                    }
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));

//                                    spBean.setTac(Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));
//                                    if (str4.length() > tacEnd) {
//                                        if ("ffffffff".equals(str4.substring(tacBegin, tacEnd))) {
////
//                                            spBean.setTac(0);
//                                        } else {
//                                            spBean.setTac(Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));
////
//                                        }
//                                    } else {
//                                        spBean.setTac(0);
//                                    }
//                                    spBean.setTac(0);
                                    //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str4.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
//                                    spBean.setPlmn(Integer.parseInt(StringPin(str4.substring(plmnBegin, plmnEnd)), 16) + "");
//                                    spBean.setPlmn("");

                                    try {
                                        if ("ffff".equals(str4.substring(plmnBegin, plmnEnd))) {
                                            Log.d("1nzqffffffff", "run:1 ");
                                            spBean.setPlmn(0 + "");
                                        } else {
                                            spBean.setPlmn(Integer.parseInt(StringPin(str4.substring(plmnBegin, plmnEnd)), 16) + "");
                                            Log.d("2nzqffffffff", "run:1 ");
                                        }
                                    } catch (StringIndexOutOfBoundsException e) {
                                        spBean.setPlmn(0 + "");
                                    }


                                    //CellId
//                                    System.out.println("ffffffff".equals(str4.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str4.substring(celldBegin, celldEnd)), 16) + "------CellId：");
                                    try {
                                        if ("ffffffff".equals(str4.substring(celldBegin, celldEnd))) {
                                            Log.d("1nzqffffffff", "run:1 ");
                                            spBean.setCid(0 + "");
                                        } else {
                                            spBean.setCid(Integer.parseInt(StringPin(str4.substring(celldBegin, celldEnd)), 16) + "");
                                            Log.d("2nzqffffffff", "run:1 ");
                                        }
                                    } catch (NumberFormatException e) {
                                        spBean.setCid(0 + "");
                                    }

                                    //Priority 本小区频点优先级
//                                    System.out.println(str4.substring(64, 72));
                                    System.out.println("ffffffff".equals(str4.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str4.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
                                    if ("ffffffff".equals(str4.substring(priorityBegin, priorityEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setPriority(0);
                                    } else {
                                        spBean.setPriority(Integer.parseInt(StringPin(str4.substring(priorityBegin, priorityEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }
                                    //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str4.substring(rsrpBegin, rsrpEnd)), 16));
//                                    spBean.setRsrp(Integer.parseInt(StringPin(str4.substring(rsrpBegin, rsrpEnd)), 16));

                                    if ("ffffffff".equals(str4.substring(rsrpBegin, rsrpEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setRsrp(0);
                                    } else {
                                        spBean.setRsrp(Integer.parseInt(StringPin(str4.substring(rsrpBegin, rsrpEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }


                                    //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str4.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrq(Integer.parseInt(StringPin(str4.substring(rsrqBegin, rsrqEnd)), 16));

                                    if ("ffffffff".equals(str4.substring(rsrqBegin, rsrqEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setRsrq(0);
                                    } else {
                                        spBean.setRsrq(Integer.parseInt(StringPin(str4.substring(rsrqBegin, rsrqEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }
                                    //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str4.substring(bandWidthBegin, bandWidthEnd)), 16));

                                    if ("ffffffff".equals(str4.substring(bandWidthBegin, bandWidthEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setBand("");
                                    } else {
                                        spBean.setBand(Integer.parseInt(StringPin(str4.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }
//                                    spBean.setBand(Integer.parseInt(StringPin(str4.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                    try {
                                        if (spBean.getDown().equals("0")) {

                                        } else {
                                            spBeanList.add(spBean);//测试代码add
                                        }
                                    } catch (Exception e) {
                                        
                                    }

                                    //TddSpecialSf Patterns TDD特殊子帧配置
//                                        System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str4.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
                                    //异频小区个数
//                                    int InterFreqLstNum;
//                                    if ("ffffffff".equals(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
//                                        InterFreqLstNum = 0;
//                                    } else {
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                    }
                                    int InterFreqLstNum;
                                    try {
                                        if ("ffffffff".equals(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
                                            InterFreqLstNum = 0;
                                        } else {
                                            try {
                                                InterFreqLstNum = Integer.parseInt(StringPin(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                            } catch (Exception e) {
                                                InterFreqLstNum = 0;
                                                Log.d("nzqexce1", "run: " + e.getMessage());
                                            }
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                        }
                                    } catch (Exception e) {
                                        InterFreqLstNum = 0;
                                        Log.d("nzqexce3", "run: " + e.getMessage());
                                    }
//                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
//                                        System.out.println("异频小区个数：------" + InterFreqLstNum);

                                    dlEarfcnBegin = dlEarfcnBegin + 64;
                                    dlEarfcnEnd = dlEarfcnEnd + 64;
                                    pciBegin = pciBegin + 64;
                                    pciEnd = pciEnd + 64;
                                    tacBegin = tacBegin + 64;
                                    tacEnd = tacEnd + 64;
                                    plmnBegin = plmnBegin + 64;
                                    plmnEnd = plmnEnd + 64;
                                    celldBegin = celldBegin + 64;
                                    celldEnd = celldEnd + 64;
                                    priorityBegin = priorityBegin + 64;
                                    priorityEnd = priorityEnd + 64;
                                    rsrpBegin = rsrpBegin + 64;
                                    rsrpEnd = rsrpEnd + 64;
                                    rsrqBegin = rsrqBegin + 64;
                                    rsrqEnd = rsrqEnd + 64;
                                    bandWidthBegin = bandWidthBegin + 64;
                                    bandWidthEnd = bandWidthEnd + 64;
                                    tddSpecialSfBegin = tddSpecialSfBegin + 64;
                                    tddSpecialSfEnd = tddSpecialSfEnd + 64;
                                    //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;

                                    System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                    for (int r = 0; r < InterFreqLstNum; r++) {
//                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                        interFreqNghNumBegin = interFreqLstNumBegin + 24;
                                        interFreqNghNumEnd = interFreqLstNumEnd + 24;
                                        //异频小区的领区数目
//                                            System.out.println(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                        int interFreqNghNum;
//                                        if ("ffffffff".equals(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {
//
//                                            continue;
//                                        } else {
//                                            interFreqNghNum = Integer.parseInt(StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
////                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
//                                        }
                                        int interFreqNghNum;
//                                        try {
//                                        if (str4.length() < interFreqNghNumEnd) {
//                                            continue;
//                                        }
                                        try {
                                            if (!TextUtils.isEmpty(str4)) {

                                            } else {
                                                continue;
                                            }
                                            if ("ffffffff".equals(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {

                                                interFreqNghNum = 0;
                                            } else {
                                                interFreqNghNum = Integer.parseInt(StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                            }
                                        } catch (Exception e) {
                                            interFreqNghNum = 0;
//                                            Log.d("nzqexce2", "run: " + e.getMessage());
                                        }

                                        for (int n = 0; n < interFreqNghNum; n++) {
                                            dlEarfcnBegin = dlEarfcnBegin + 8;
                                            dlEarfcnEnd = dlEarfcnEnd + 8;
                                            pciBegin = pciBegin + 8;
                                            pciEnd = pciEnd + 8;
                                            tacBegin = tacBegin + 8;
                                            tacEnd = tacEnd + 8;
                                            plmnBegin = plmnBegin + 8;
                                            plmnEnd = plmnEnd + 8;
                                            celldBegin = celldBegin + 8;
                                            celldEnd = celldEnd + 8;
                                            priorityBegin = priorityBegin + 8;
                                            priorityEnd = priorityEnd + 8;
                                            rsrpBegin = rsrpBegin + 8;
                                            rsrpEnd = rsrpEnd + 8;
                                            rsrqBegin = rsrqBegin + 8;
                                            rsrqEnd = rsrqEnd + 8;
                                            bandWidthBegin = bandWidthBegin + 8;
                                            bandWidthEnd = bandWidthEnd + 8;
                                            tddSpecialSfBegin = tddSpecialSfBegin + 8;
                                            tddSpecialSfEnd = tddSpecialSfEnd + 8;
                                            interFreqLstNumBegin = interFreqLstNumBegin + 8;
                                            interFreqLstNumEnd = interFreqLstNumEnd + 8;
                                            interFreqNghNumBegin = interFreqNghNumBegin + 8;
                                            interFreqNghNumEnd = interFreqNghNumEnd + 8;
                                        }

									/*int number = InterFreqLstNum-r;
                                    if(number!=1){
										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
									}*/
                                        dlEarfcnBegin = dlEarfcnBegin + 24;
                                        dlEarfcnEnd = dlEarfcnEnd + 24;
                                        pciBegin = pciBegin + 24;
                                        pciEnd = pciEnd + 24;
                                        tacBegin = tacBegin + 24;
                                        tacEnd =
                                                +24;
                                        plmnBegin = plmnBegin + 24;
                                        plmnEnd = plmnEnd + 24;
                                        celldBegin = celldBegin + 24;
                                        celldEnd = celldEnd + 24;
                                        priorityBegin = priorityBegin + 24;
                                        priorityEnd = priorityEnd + 24;
                                        rsrpBegin = rsrpBegin + 24;
                                        rsrpEnd = rsrpEnd + 24;
                                        rsrqBegin = rsrqBegin + 24;
                                        rsrqEnd = rsrqEnd + 24;
                                        bandWidthBegin = bandWidthBegin + 24;
                                        bandWidthEnd = bandWidthEnd + 24;
                                        tddSpecialSfBegin = tddSpecialSfBegin + 24;
                                        tddSpecialSfEnd = tddSpecialSfEnd + 24;
                                        interFreqLstNumBegin = interFreqLstNumBegin + 24;
                                        interFreqLstNumEnd = interFreqLstNumEnd + 24;
                                    }
                                    interFreqLstNumBegin = interFreqLstNumBegin + 64;
                                    interFreqLstNumEnd = interFreqLstNumEnd + 64;
                                    interFreqNghNumBegin = interFreqNghNumBegin + 64;
                                    interFreqNghNumEnd = interFreqNghNumEnd + 64;
                                }
                                str4 = "";

                                Constant.SPBEANLIST2Fragment = spBeanList;
                                if (spBeanList.size() == 0) {
//
                                } else {
//                                    Log.d("nzqspBeanList2", "" + spBeanList);
//                                    bundle.putParcelableArrayList("List,", (ArrayList<? extends Parcelable>) spBeanList);
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 200152;

                                    Constant.SPBEANLIST2 = spBeanList;
//                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
                                    //先根据优先级,如果优先级一样根据RSRP
                                    List<Integer> list = new ArrayList();
                                    String down1 = "";
                                    SpBean spBean1 = new SpBean();
                                    SpBean spBean2 = new SpBean();
                                    if (spBeanList.size() >= 2) {
                                        for (int i = 0; i < spBeanList.size(); i++) {
                                            list.add(spBeanList.get(i).getPriority());
                                        }
                                        Integer max = Collections.max(list);
                                        Log.d("Anzqmax", "大于2条run: " + max);
                                        list.remove(max);//最大的优先

                                        for (int i = 0; i < spBeanList.size(); i++) {
                                            if (max.equals(spBeanList.get(i).getPriority())) {
                                                down1 = spBeanList.get(i).getDown();
                                                spBean1 = spBeanList.get(i);
                                                break;
                                            }
                                        }

                                        //第二个优先
                                        String down2 = "";
                                        Integer max2 = Collections.max(list);
//                                    Log.d("Anzqmax2", "run: " + max);
                                        for (int i = 0; i < spBeanList.size(); i++) {
                                            if (max2.equals(spBeanList.get(i).getPriority())) {

                                                down2 = spBeanList.get(i).getDown();
                                                spBean2 = spBeanList.get(i);
                                            }
                                        }
                                        Log.d("down2a", "run: " + down2);
                                        if (max != max2) {

                                            if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
                                                message = new Message();

                                                bundle.putString("sp1MAX1value54", down1);//下行
                                                bundle.putString("sp1up54", spBean1.getUp() + "");
                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
                                                bundle.putString("sp1band54", spBean1.getBand() + "");
                                                bundle.putString("sp1tac54", spBean1.getTac() + "");

                                                bundle.putString("sp1MAX2value54", down2);
                                                bundle.putString("sp2up54", spBean2.getUp() + "");
                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
                                                bundle.putString("sp2band54", spBean2.getBand() + "");
                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 200152;
                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点一致: " + max);
                                            } else {
                                                message = new Message();
                                                bundle.putString("sp1MAX1value54", down1);//下行
                                                bundle.putString("sp1up54", spBean1.getUp() + "");
                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
                                                bundle.putString("sp1band54", spBean1.getBand() + "");
                                                bundle.putString("sp1tac54", spBean1.getTac() + "");

                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                        break;
                                                    }
                                                }


                                                bundle.putString("sp1MAX2value54", down2);
                                                bundle.putString("sp2up54", spBean2.getUp() + "");
                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
                                                bundle.putString("sp2band54", spBean2.getBand() + "");
                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 200152;
                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
                                            }


                                        } else {//根据优先级比较一致  ,通过rsrp比较

                                            int rssp1;
                                            int rssp2;
                                            List<Integer> list1rsp = new ArrayList<>();
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                list1rsp.add(spBeanList.get(i).getRsrp());
                                            }
                                            //最大的rsrp
                                            rssp1 = Collections.max(list1rsp);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (rssp1 == spBeanList.get(i).getRsrp()) {
                                                    down1 = spBeanList.get(i).getDown();
                                                    spBean1 = spBeanList.get(i);
                                                }
                                            }
                                            for (int i = 0; i < list1rsp.size(); i++) {
                                                if (list1rsp.get(i).equals(rssp1)) {
                                                    list1rsp.remove(i);
                                                }
                                            }
                                            //求第二个rsrp
                                            rssp2 = Collections.max(list1rsp);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (rssp2 == spBeanList.get(i).getRsrp()) {
                                                    down2 = spBeanList.get(i).getDown();
                                                    spBean2 = spBeanList.get(i);
                                                }
                                            }
                                            if (down1.equals(down2)) {
                                                message = new Message();
                                                bundle.putString("sp1MAX1value54", down1);//下行
                                                bundle.putString("sp1up54", spBean1.getUp() + "");
                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
                                                bundle.putString("sp1band54", spBean1.getBand() + "");
                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                        break;
                                                    }

                                                }
                                                bundle.putString("sp1MAX2value54", "");
                                                bundle.putString("sp2up54", spBean2.getUp() + "");
                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
                                                bundle.putString("sp2band54", spBean2.getBand() + "");
                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 200152;
                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
                                            } else {
                                                message = new Message();
                                                bundle.putString("sp1MAX1value54", down1);//下行
                                                bundle.putString("sp1up54", spBean1.getUp() + "");
                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
                                                bundle.putString("sp1band54", spBean1.getBand() + "");
                                                bundle.putString("sp1tac54", spBean1.getTac() + "");

                                                bundle.putString("sp1MAX2value54", down2);
                                                bundle.putString("sp2up54", spBean2.getUp() + "");
                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
                                                bundle.putString("sp2band54", spBean2.getBand() + "");
                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
                                                message.setData(bundle);
                                                handler.sendMessage(message);
                                                message.what = 200152;
                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
                                            }


                                            ToastUtils.showToast("当前条数为多条");
//                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
                                        }

                                    } else {

                                        if (spBeanList.size() > 0 && spBeanList.size() == 1) {
                                            down1 = spBeanList.get(0).getDown();
//                                            spBean2 = spBeanList.get(0);
                                            message = new Message();
                                            bundle.putString("sp1MAX1value54", down1);
                                            bundle.putString("sp1up54", spBeanList.get(0).getUp() + "");
                                            bundle.putString("sp1pci54", spBeanList.get(0).getPci() + "");
                                            bundle.putString("sp1plmn54", spBeanList.get(0).getPlmn() + "");
                                            bundle.putString("sp1band54", spBeanList.get(0).getBand() + "");
                                            bundle.putString("sp1tac54", spBeanList.get(0).getTac() + "");

                                            bundle.putString("sp1MAX2value54", "");
                                            bundle.putString("sp2up54", spBean2.getUp() + "");
                                            bundle.putString("sp2pci54", spBean2.getPci() + "");
                                            bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
                                            bundle.putString("sp2band54", spBean2.getBand() + "");
                                            bundle.putString("sp2tac54", spBean2.getTac() + "");
                                            message.setData(bundle);
                                            handler.sendMessage(message);
                                            message.what = 200152;
                                            ToastUtils.showToast("当前条数为1");
                                            Log.d("Anzqmax", "当前条数为1: ");
                                        } else {
                                            message = new Message();
                                            bundle.putString("sp1MAX1value54", "");
                                            bundle.putString("sp1MAX2value54", "");
                                            message.setData(bundle);
                                            handler.sendMessage(message);
                                            message.what = 200152;
                                            ToastUtils.showToast("当前条数为0");
                                            Log.d("Anzqmax", "当前条数为0: ");
                                        }
                                    }
                                }

                            } else if (spBeanList.size() > 0 && spBeanList.size() == 1) {//如果只有一条
                                if (!spBeanList.get(0).getDown().equals("0")) {
                                    String down1 = spBeanList.get(0).getDown();
                                    SpBean spBean2 = spBeanList.get(0);
                                    message = new Message();
                                    bundle.putString("sp1MAX1value", down1);
                                    bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
                                    bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
                                    bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
                                    bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
                                    bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");

                                    bundle.putString("sp1MAX2value", "");
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                    message.what = 200152;
                                    ToastUtils.showToast("当前条数为1");
                                    Log.d("Anzqmax", "当前条数为1: ");
                                }
                            } else { //当没有大于1 或者大于2
                                message = new Message();
                                bundle.putString("sp1MAX1value", "");
                                bundle.putString("sp1MAX2value", "");
                                message.setData(bundle);
                                handler.sendMessage(message);
                                message.what = 200152;
                                ToastUtils.showToast("当前条数为0");
                                Log.d("Anzqmax", "当前条数为0: ");
                            }
                        }

                    }

                    //扫频频点设置响应
//                            if ("0af0".equals(str.substring(8, 12))) {
//                                //采集的小区数目
//                                int row;
//                                if ("ffff".equals(str.substring(24, 28))) {
//                                    row = 0;
//                                } else {
//                                    row = Integer.parseInt(StringPin(str.substring(24, 28)), 16);
//                                    System.out.println("小区个数：" + row);
//                                }
//                                List<SpBean> spBeanList = new ArrayList<>();
//                                int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
//                                int pciBegin = 40, pciEnd = 44;
//                                int tacBegin = 44, tacEnd = 48;
//                                int plmnBegin = 48, plmnEnd = 52;
//                                int celldBegin = 56, celldEnd = 64;
//                                int priorityBegin = 64, priorityEnd = 72;
//                                int rsrpBegin = 72, rsrpEnd = 74;
//                                int rsrqBegin = 74, rsrqEnd = 76;
//                                int bandWidthBegin = 76, bandWidthEnd = 78;
//                                int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
//                                int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
//                                int interFreqNghNumBegin = 112, interFreqNghNumEnd = 120;
//
//                                for (int i = 0; i < row; i++) {
//                                    SpBean spBean = new SpBean();
//                                    //下行频点
//                                    System.out.println(str.substring(dlEarfcnBegin, dlEarfcnEnd));
//
//                                    if ("ffffffff".equals(str.substring(dlEarfcnBegin, dlEarfcnEnd))) {
//                                        System.out.println("null");
//                                        spBean.setDown("0");
//                                    } else {
//                                        System.out.println("下行频点：------" + Integer.parseInt(StringPin(str.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                        spBean.setDown(Integer.parseInt(StringPin(str.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
//                                    }
//                                    if (ID2TF.equals("TDD")) {
//                                        spBean.setUp(255 + "");
//                                    } else {
//                                        if (!TextUtils.isEmpty(spBean.getDown())) {
//                                            int i2 = Integer.parseInt(spBean.getDown());
//                                            int i1 = i2 + 18000;
//                                            spBean.setUp(i1 + "");
//
//                                        }
//                                    }
//                                    Log.d("spBeanup", "run: " + spBean.getUp());
//                                    //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(Integer.parseInt(StringPin(str.substring(pciBegin, pciEnd)), 16));
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);
//
//                                    //TAC
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str.substring(tacBegin, tacEnd)), 16));
//                                    spBean.setTac(Integer.parseInt(StringPin(str.substring(tacBegin, tacEnd)), 16));
//                                    //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
//
//                                    spBean.setPlmn(Integer.parseInt(StringPin(str.substring(plmnBegin, plmnEnd)), 16) + "");
//                                    //CellId
//                                    System.out.println("ffffffff".equals(str.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str.substring(celldBegin, celldEnd)), 16) + "------CellId：");
//                                    //Priority 本小区频点优先级
//                                    System.out.println("优先级--" + str.substring(64, 72));
//                                    System.out.println("ffffffff".equals(str.substring(priorityBegin, priorityEnd)) ? "null" : Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
//                                    Log.d("nzqffffffff", "ffffffff".equals(str.substring(priorityBegin, priorityEnd)) ? "0" : Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16) + "");
//                                    if ("ffffffff".equals(str.substring(priorityBegin, priorityEnd))) {
//                                        Log.d("1nzqffffffff", "run:1 ");
//                                        spBean.setPriority(0);
//                                    } else {
//                                        spBean.setPriority(Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16));
//                                        Log.d("2nzqffffffff", "run:1 ");
//                                    }
////
////
////                                    spBean.setPriority(Integer.parseInt(StringPin(str.substring(priorityBegin, priorityEnd)), 16)+"");
//                                    //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str.substring(rsrpBegin, rsrpEnd)), 16));
//                                    //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrp(Integer.parseInt(StringPin(str.substring(rsrpBegin, rsrpEnd)), 16));
//                                    spBeanList.add(spBean);//测试代码add
//                                    //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                    spBean.setBand(Integer.parseInt(StringPin(str.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
//                                    //TddSpecialSf Patterns TDD特殊子帧配置
//                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
//                                    //异频小区个数
//                                    int InterFreqLstNum;
//                                    if ("ffffffff".equals(str.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
//                                        InterFreqLstNum = 0;
//                                    } else {
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                    }
//                                    System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
//                                    System.out.println("异频小区个数：------" + InterFreqLstNum);
//
////                                    dlEarfcnBegin = dlEarfcnBegin + 64;
////                                    dlEarfcnEnd = dlEarfcnEnd + 64;
////                                    pciBegin = pciBegin + 64;
////                                    pciEnd = pciEnd + 64;
////                                    tacBegin = tacBegin + 64;
////                                    tacEnd = tacEnd + 64;
////                                    plmnBegin = plmnBegin + 64;
////                                    plmnEnd = plmnEnd + 64;
////                                    celldBegin = celldBegin + 64;
////                                    celldEnd = celldEnd + 64;
////                                    priorityBegin = priorityBegin + 64;
////                                    priorityEnd = priorityEnd + 64;
////                                    rsrpBegin = rsrpBegin + 64;
////                                    rsrpEnd = rsrpEnd + 64;
////                                    rsrqBegin = rsrqBegin + 64;
////                                    rsrqEnd = rsrqEnd + 64;
////                                    bandWidthBegin = bandWidthBegin + 64;
////                                    bandWidthEnd = bandWidthEnd + 64;
////                                    tddSpecialSfBegin = tddSpecialSfBegin + 64;
////                                    tddSpecialSfEnd = tddSpecialSfEnd + 64;
////                                    interFreqLstNumBegin = interFreqLstNumBegin + 64;
////                                    interFreqLstNumEnd = interFreqLstNumEnd + 64;
////
////                                    System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
////                                    for (int r = 0; r < InterFreqLstNum; r++) {
////                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
////                                        //异频小区的领区数目
////                                        System.out.println(str.substring(interFreqNghNumBegin, interFreqNghNumEnd));
////                                        System.out.println("pin:" + StringPin(str.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
////                                        System.out.println(Integer.parseInt(StringPin(str.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
////                                        int interFreqNghNum = Integer.parseInt(StringPin(str.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
////                                        System.out.println("异频小区的邻区个数：" + interFreqNghNum);
////
////                                        for (int n = 0; n < interFreqNghNum; n++) {
////                                            dlEarfcnBegin = dlEarfcnBegin + 8;
////                                            dlEarfcnEnd = dlEarfcnEnd + 8;
////                                            pciBegin = pciBegin + 8;
////                                            pciEnd = pciEnd + 8;
////                                            tacBegin = tacBegin + 8;
////                                            tacEnd = tacEnd + 8;
////                                            plmnBegin = plmnBegin + 8;
////                                            plmnEnd = plmnEnd + 8;
////                                            celldBegin = celldBegin + 8;
////                                            celldEnd = celldEnd + 8;
////                                            priorityBegin = priorityBegin + 8;
////                                            priorityEnd = priorityEnd + 8;
////                                            rsrpBegin = rsrpBegin + 8;
////                                            rsrpEnd = rsrpEnd + 8;
////                                            rsrqBegin = rsrqBegin + 8;
////                                            rsrqEnd = rsrqEnd + 8;
////                                            bandWidthBegin = bandWidthBegin + 8;
////                                            bandWidthEnd = bandWidthEnd + 8;
////                                            tddSpecialSfBegin = tddSpecialSfBegin + 8;
////                                            tddSpecialSfEnd = tddSpecialSfEnd + 8;
////                                            interFreqLstNumBegin = interFreqLstNumBegin + 8;
////                                            interFreqLstNumEnd = interFreqLstNumEnd + 8;
////                                            interFreqNghNumBegin = interFreqNghNumBegin + 8;
////                                            interFreqNghNumEnd = interFreqNghNumEnd + 8;
////                                        }
////
////                                        int number = InterFreqLstNum - r;
////                                        if (number != 1) {
////                                            interFreqNghNumBegin = interFreqNghNumBegin + 24;
////                                            interFreqNghNumEnd = interFreqNghNumEnd + 24;
////                                        }
////                                        dlEarfcnBegin = dlEarfcnBegin + 24;
////                                        dlEarfcnEnd = dlEarfcnEnd + 24;
////                                        pciBegin = pciBegin + 24;
////                                        pciEnd = pciEnd + 24;
////                                        tacBegin = tacBegin + 24;
////                                        tacEnd = tacEnd + 24;
////                                        plmnBegin = plmnBegin + 24;
////                                        plmnEnd = plmnEnd + 24;
////                                        celldBegin = celldBegin + 24;
////                                        celldEnd = celldEnd + 24;
////                                        priorityBegin = priorityBegin + 24;
////                                        priorityEnd = priorityEnd + 24;
////                                        rsrpBegin = rsrpBegin + 24;
////                                        rsrpEnd = rsrpEnd + 24;
////                                        rsrqBegin = rsrqBegin + 24;
////                                        rsrqEnd = rsrqEnd + 24;
////                                        bandWidthBegin = bandWidthBegin + 24;
////                                        bandWidthEnd = bandWidthEnd + 24;
////                                        tddSpecialSfBegin = tddSpecialSfBegin + 24;
////                                        tddSpecialSfEnd = tddSpecialSfEnd + 24;
////                                        interFreqLstNumBegin = interFreqLstNumBegin + 24;
////                                        interFreqLstNumEnd = interFreqLstNumEnd + 24;
////
////                                    }
//                                    interFreqNghNumBegin = interFreqNghNumBegin + 64;
//                                    interFreqNghNumEnd = interFreqNghNumEnd + 64;
//                                }
//                                //测试代码37900
////                                SpBean spBean = new SpBean();
////                                spBean.setRsrp(0);
////                                spBean.setPriority(20);
////                                spBean.setDown("37900");
////                                spBean.setPci(1234);
////                                spBean.setTac(987654);
////                                spBean.setPlmn("46000");
////                                spBean.setBand(38+"");
////                                spBeanList.add(spBean);
////                                spBeanList.add(spBean);
////
////                                SpBean spBean22 = new SpBean();
////                                spBean22.setRsrp(0);
////                                spBean22.setPriority(0);
////                                spBean22.setDown("37200");
////                                spBeanList.add(spBean22);
//                                if (spBeanList.size() == 0) {
//
//                                } else {
//                                    Log.d("nzqspBeanList", "" + spBeanList);
////                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
//                                    //先根据优先级,如果优先级一样根据RSRP
//                                    List<Integer> list = new ArrayList();
//                                    String down1 = "";
//                                    SpBean spBean1 = new SpBean();
//                                    SpBean spBean2 = new SpBean();
//                                    if (spBeanList.size() >= 2) {
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            list.add(spBeanList.get(i).getPriority());
//                                        }
//                                        Integer max = Collections.max(list);
//                                        Log.d("Anzqmax", "大于2条run: " + max);
//                                        list.remove(max);//最大的优先
//
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            if (max.equals(spBeanList.get(i).getPriority())) {
//                                                down1 = spBeanList.get(i).getDown();
//                                                spBean1 = spBeanList.get(i);
//                                                break;
//                                            }
//                                        }
//
//                                        //第二个优先
//                                        String down2 = "";
//                                        Integer max2 = Collections.max(list);
////                                    Log.d("Anzqmax2", "run: " + max);
//                                        for (int i = 0; i < spBeanList.size(); i++) {
//                                            if (max2.equals(spBeanList.get(i).getPriority())) {
//
//                                                down2 = spBeanList.get(i).getDown();
//                                                spBean2 = spBeanList.get(i);
//                                            }
//                                        }
//                                        Log.d("down2a", "run: " + down2);
//                                        if (max != max2) {
//
//                                            if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value54", down2);
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
//                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点一致: " + max);
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
//
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                        break;
//                                                    }
//                                                }
//
//
//                                                bundle.putString("sp1MAX2value54", down2);
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
//                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
//                                            }
//
//
//                                        } else {//根据优先级比较一致  ,通过rsrp比较
//
//                                            int rssp1;
//                                            int rssp2;
//                                            List<Integer> list1rsp = new ArrayList<>();
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                list1rsp.add(spBeanList.get(i).getRsrp());
//                                            }
//                                            //最大的rsrp
//                                            rssp1 = Collections.max(list1rsp);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (rssp1 == spBeanList.get(i).getRsrp()) {
//                                                    down1 = spBeanList.get(i).getDown();
//                                                    spBean1 = spBeanList.get(i);
//                                                }
//                                            }
//                                            for (int i = 0; i < list1rsp.size(); i++) {
//                                                if (list1rsp.get(i).equals(rssp1)) {
//                                                    list1rsp.remove(i);
//                                                }
//                                            }
//                                            //求第二个rsrp
//                                            rssp2 = Collections.max(list1rsp);
//                                            for (int i = 0; i < spBeanList.size(); i++) {
//                                                if (rssp2 == spBeanList.get(i).getRsrp()) {
//                                                    down2 = spBeanList.get(i).getDown();
//                                                    spBean2 = spBeanList.get(i);
//                                                }
//                                            }
//                                            if (down1.equals(down2)) {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
//                                                for (int i = 0; i < spBeanList.size(); i++) {
//                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                        down2 = spBeanList.get(i).getDown();
//                                                        spBean2 = spBeanList.get(i);
//                                                        break;
//                                                    }
//
//                                                }
//                                                bundle.putString("sp1MAX2value54", "");
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
//                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
//                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value54", down2);
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
//                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
//                                            }
//
//
//                                            ToastUtils.showToast("当前条数为多条");
////                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
//                                        }
//
//                                    } else {
//
//                                        if (spBeanList.size() > 0 && spBeanList.size() == 1) {
//                                            down1 = spBeanList.get(0).getDown();
////                                            spBean2 = spBeanList.get(0);
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value54", down1);
//                                            bundle.putString("sp1up54", spBeanList.get(0).getUp() + "");
//                                            bundle.putString("sp1pci54", spBeanList.get(0).getPci() + "");
//                                            bundle.putString("sp1plmn54", spBeanList.get(0).getPlmn() + "");
//                                            bundle.putString("sp1band54", spBeanList.get(0).getBand() + "");
//                                            bundle.putString("sp1tac54", spBeanList.get(0).getTac() + "");
//
//                                            bundle.putString("sp1MAX2value54", "");
//                                            bundle.putString("sp2up54", spBean2.getUp() + "");
//                                            bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                            bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                            bundle.putString("sp2band54", spBean2.getBand() + "");
//                                            bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 200152;
//                                            ToastUtils.showToast("当前条数为1");
//                                            Log.d("Anzqmax", "当前条数为1: ");
//                                        } else {
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value54", "");
//                                            bundle.putString("sp1MAX2value54", "");
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 200152;
//                                            ToastUtils.showToast("当前条数为0");
//                                            Log.d("Anzqmax", "当前条数为0: ");
//                                        }
//                                    }
//                                }

//                                } else if (spBeanList.size() > 0 && spBeanList.size() == 1) {//如果只有一条
//                                    if (!spBeanList.get(0).getDown().equals("0")) {
//                                        down1 = spBeanList.get(0).getDown();
//                                        spBean2 = spBeanList.get(0);
//                                        message = new Message();
//                                        bundle.putString("sp1MAX1value", down1);
//                                        bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                        bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                        bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                        bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                        bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                        bundle.putString("sp1MAX2value", "");
//                                        message.setData(bundle);
//                                        handler.sendMessage(message);
//                                        message.what = 100152;
//                                        ToastUtils.showToast("当前条数为1");
//                                        Log.d("Anzqmax", "当前条数为1: ");
//                                    }
//                                } else { //当没有大于1 或者大于2
//                                    message = new Message();
//                                    bundle.putString("sp1MAX1value", "");
//                                    bundle.putString("sp1MAX2value", "");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100152;
//                                    ToastUtils.showToast("当前条数为0");
//                                    Log.d("Anzqmax", "当前条数为0: ");
//                                }
                }

//                }

//                        if ("192.168.2.54".equals(dp.getAddress().getHostAddress())) {
////                            mPressedTime2 = System.currentTimeMillis();
////                            System.out.println("ABCD");
////                            System.out.println("收到" + dp.getAddress().getHostAddress() + "发送数据：" + str);
////
////                            //时间
////                            Date d = new Date();
////                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                            System.out.println("时间：" + sdf.format(d));
////                            //设置模式：FDD TDD
////                            if ("00ff".equals(str.substring(16, 20))) {
////                                //设置模式FDD
////                                System.err.println("FDD");
////                                System.err.println("FDD");
////                                message = new Message();
////                                bundle.putString("tf2", "FDD");
////                                message.setData(bundle);
////                                handler.sendMessage(message);
////                                message.what = 200110;
////                            }
////                            if ("ff00".equals(str.substring(16, 20))) {
////                                //设置模式TDD
////                                System.out.println("TDD");
////                                message = new Message();
////                                bundle.putString("tf2", "TDD");
////                                message.setData(bundle);
////                                handler.sendMessage(message);
////                                message.what = 200110;
////                            }
////
////                            if ("08f0".equals(str.substring(8, 12))) {
////
////                                //目标距离（16进制字符串转换成十进制）
////                                Integer.parseInt(str.substring(24, 26), 16);
////                                System.out.println("距离：" + Integer.parseInt(str.substring(24, 26), 16));
////                                //IMSI号
////                                StringTOIMEI(str.substring(26, 56));
////                                System.out.println("IMSI号：" + hexStringToString(str.substring(26, 56)));
////
////                            }
////                            if ("10f0".equals(str.substring(8, 12))) {
////                                message = new Message();
////                                bundle.putString("zt2", "2");
////                                message.setData(bundle);
////                                handler.sendMessage(message);
////                                message.what = 200120;
////                                //心跳解析
////                                //查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
////                                message = new Message();
////                                bundle.putString("zt2", "0");
////                                message.setData(bundle);
////                                handler.sendMessage(message);
////                                message.what = 200120;
////                                if ("0000".equals(str.substring(24, 28))) {
////                                    System.out.println("0：小区 IDLE态");
////                                    message = new Message();
////                                    bundle.putString("zt2", "2");
////                                    message.setData(bundle);
////                                    handler.sendMessage(message);
////                                    message.what = 200120;
////                                } else if ("0100".equals(str.substring(24, 28))) {
////                                    System.out.println("1：扫频/同步进行中");
////                                    message = new Message();
////                                    bundle.putString("zt2", "3");
////                                    message.setData(bundle);
////                                    handler.sendMessage(message);
////                                    message.what = 200120;
////                                } else if ("0200".equals(str.substring(24, 28))) {
////                                    System.out.println("2：小区激活中");
////                                    message = new Message();
////                                    bundle.putString("zt2", "4");
////                                    message.setData(bundle);
////                                    handler.sendMessage(message);
////                                    message.what = 200120;
////                                } else if ("0300".equals(str.substring(24, 28))) {
////                                    System.out.println("3：小区激活态");
////                                    message = new Message();
////                                    bundle.putString("zt2", "5");
////                                    message.setData(bundle);
////                                    handler.sendMessage(message);
////                                    message.what = 200120;
////                                    //Band号
////                                    Integer.parseInt(StringPin(str.substring(28, 32)), 16);
////                                    System.out.println("Band号：" + Integer.parseInt(StringPin(str.substring(28, 32)), 16));
////                                    //上行频点
////                                    Integer.parseInt(StringPin(str.substring(32, 40)), 16);
////                                    System.out.println("上行频点：" + Integer.parseInt(StringPin(str.substring(32, 40)), 16));
////                                    //下行频点
////                                    Integer.parseInt(StringPin(str.substring(40, 48)), 16);
////                                    System.out.println("下行频点：" + Integer.parseInt(StringPin(str.substring(40, 48)), 16));
////                                    //移动联通电信
////                                    if ("3436303030".equals(str.substring(48, 58))) {
////                                        //设置中国移动
////                                    }
////                                    if ("3436303031".equals(str.substring(48, 58))) {
////                                        //设置中国联通
////                                    }
////                                    if ("3436303033".equals(str.substring(48, 58)) || "3436303131".equals(str.substring(48, 58))) {
////                                        //设置中国电信
////                                    }
////
////                                    //PCI
////                                    Integer.parseInt(StringPin(str.substring(64, 68)), 16);
////                                    System.out.println("PCI:" + Integer.parseInt(StringPin(str.substring(64, 68)), 16));
////                                    //TAC
////                                    Integer.parseInt(StringPin(str.substring(68, 72)), 16);
////                                    System.out.println("TAC:" + Integer.parseInt(StringPin(str.substring(68, 72)), 16));
////
////                                } else if ("0400".equals(str.substring(24, 28))) {
////                                    System.out.println("4：小区去激活中");
////                                }
////
////                            }
////
////                        } else {
//////                            message = new Message();
//////                            bundle.putString("zt2", "0");
//////                            message.setData(bundle);
//////                            handler.sendMessage(message);
//////                            message.what = 200120;
////                        }
                /*System.out.println("截取字符串"+str.substring(8, 12));
                if(str.substring(8, 12).equals("08f0")){

					//目标距离（16进制字符串转换成十进制）
					Integer.parseInt(str.substring(24, 26),16);
					System.out.println("距离："+Integer.parseInt(str.substring(24, 26),16));
					//IMSI号
					StringTOIMEI(str.substring(26, 56));
					System.out.println("IMSI号："+hexStringToString(str.substring(26, 56)));

				}*//*if("10f0".equals(str.substring(8, 12))){
                    //心跳解析
					//查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
					if("0000".equals(str.substring(24, 28))){
						System.out.println("0：小区 IDLE态");
					}if("0100".equals(str.substring(24, 28))){
						System.out.println("1：扫频/同步进行中");
					}if("0200".equals(str.substring(24, 28))){
						System.out.println("2：小区激活中");
					}if("0300".equals(str.substring(24, 28))){
						System.out.println("3：小区激活态");
					}if("0400".equals(str.substring(24, 28))){
						System.out.println("4：小区去激活中");
					}
					//设置模式：FDD TDD
					if("00ff".equals(str.substring(16, 20))){
						//设置模式FDD
						System.err.println("FDD");
					}if("ff00".equals(str.substring(16, 20))){
						//设置模式TDD
						System.out.println("TDD");
					}
					//时间
					Date d = new Date();
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        System.out.println("时间："+sdf.format(d));
					//Band号
					Integer.parseInt(StringPin(str.substring(28,32)),16);
					System.out.println("Band号："+Integer.parseInt(StringPin(str.substring(28,32)),16));
					//上行频点
					Integer.parseInt(StringPin(str.substring(32,40)),16);
					System.out.println("上行频点："+Integer.parseInt(StringPin(str.substring(32,40)),16));
					//下行频点
					Integer.parseInt(StringPin(str.substring(40,48)),16);
					System.out.println("下行频点："+Integer.parseInt(StringPin(str.substring(40,48)),16));
					//移动联通电信
					if("3436303030".equals(str.substring(48, 58))){
						//设置中国移动
					}if("3436303031".equals(str.substring(48, 58))){
						//设置中国联通
					}if("3436303033".equals(str.substring(48, 58))||"3436303131".equals(str.substring(48, 58))){
						//设置中国电信
					}

					//PCI
					Integer.parseInt(StringPin(str.substring(64,68)),16);
					System.out.println("PCI:"+Integer.parseInt(StringPin(str.substring(64,68)),16));
					//TAC
					Integer.parseInt(StringPin(str.substring(68,72)),16);
					System.out.println("TAC:"+Integer.parseInt(StringPin(str.substring(68,72)),16));

				}*/
//                    }
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//                    String s = e.toString();
//                    Log.d("TAG120111", "run: e.printStackTrace()" + "执行了" + e.getMessage() + "\n" + e.getLocalizedMessage() + "\n" + s);
////                    //发送设备连接异常
//                    bundle.putString("runThread", "true");
//                    message = new Message();
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                    message.what = 120;
//////
////                    //设备1状态 未启动
////                    message = new Message();
////                    bundle.putString("zt1", "0");
////                    message.setData(bundle);
////                    handler.sendMessage(message);
////                    message.what = 100120;
////                    //设备2状态 未启动
////                    message = new Message();
////                    bundle.putString("zt2", "0");
////                    message.setData(bundle);
////                    handler.sendMessage(message);
////                    message.what = 200120;
//////
//
////                    interrupted();//中断线程
////                    ThreadFlag = false;
//////                    System.exit(0);
//
////
///
/// .scheduleAtFixedRate(new TimerTask() {
////            @Override
////            public void run() {
////
////            }
////        }, 0, 1000);
//
//                }
                /// 外面

            }
        }).

                start();

    }


    DatagramSocket ds = null; // 接收数据 Socket

    public void closeSocket() {

        if (ds != null && !ds.isClosed()) {
            ds.close();
            ds.disconnect();
            ds = null;
        }
    }


    /*
     * btye[]字节数组转换成16进制的字符串
     */
    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }

    /**
     * 16进制转换成为string类型字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    //取字符串偶数位字符拼接到一起
    public static String StringTOIMEI(String str) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i <= str.length(); i += 2) {
            if (i % 2 != 0) {
                buffer.append(str.charAt(i));
            }
        }
        return buffer.toString();
    }

    //字符串分割成两个字符一组，倒序拼接到一起
    public static String StringPin(String str) {

        String[] bands = new String[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            bands[i / 2] = str.substring(i, i + 2);
        }
        String str1 = new String();
        for (int i = bands.length - 1; i >= 0; i--) {
            str1 += bands[i];
        }

        return str1;
    }

    public static void TypeTimer(final Handler handler, final Bundle bundle,
                                 final Timer timer1, final Timer timer2) {

//        int PERIOD = 1000 * 60 * 4;
//        timer1.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("Timer is running");
//                Message message = new Message();
//                bundle.putString("wifiFlag", String.valueOf(wifiFlag));
//                bundle.putString("str", str);
//                message.setData(bundle);
//                handler.sendMessage(message);
//                message.what = 10010;
//                Log.d(TAG, "handlerrun: " + 1);
//            }
//        }, 0, 1000);
    }

    //重启指令
    public static void Restart() {
        final StringBuffer str = new StringBuffer(Constant.Restart);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = MainUtilsThread.hexStringToByteArray(str.toString());
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName("192.168.2.53");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    //重启指令
    public static void Restart2() {
        final StringBuffer str = new StringBuffer(Constant.Restart);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = MainUtilsThread.hexStringToByteArray(str.toString());
                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName("192.168.2.54");
//                    address = InetAddress.getByName(IP2);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart2Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //查询同步类型指令
    public static void getType(final String ip) {
        final StringBuffer str = new StringBuffer(Constant.SYNCHRONOUS);
        new Thread(new Runnable() {
            @Override
            public void run() {

                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("SYNCHRONOUSnzq", "run: nzqsend" + str);
                byte[] outputData = MainUtilsThread.hexStringToByteArray(str.toString());
                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(ip);
//                    address = InetAddress.getByName(IP2);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("Anzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //设置同步类型指令
    public static void SetType(final String ip, final int TYPE) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = "";
                if (TYPE == 0) {//空口同步
                    str = Constant.KONGKOUTB;
                } else {//GPS同步
                    str = Constant.GPSTB;
                }
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("SYNCHRONOUSnzq", "run: nzqsend" + str);
                byte[] outputData = MainUtilsThread.hexStringToByteArray(str.toString());
                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(ip);
//                    address = InetAddress.getByName(IP2);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("Anzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //查询日期
    public static void getDate(final String ip) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = "";

                str = Constant.Date1;

                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("SYNCHRONOUSnzq", "run: nzqsend" + str);
                byte[] outputData = MainUtilsThread.hexStringToByteArray(str.toString());
                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(ip);
//                    address = InetAddress.getByName(IP2);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("Anzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    public static void GlSend(final String ip, final String bytes) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = "";
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("SYNCHRONOUSnzq", "run: nzqsend" + str);
                StringBuffer stringBuffer = new StringBuffer("aa aa 55 55 15 f0 14 00 00 00 00 ff");

                stringBuffer.append(bytes + "00 00 00 00 00 00 00");
                byte[] outputData = MainUtilsThread.hexStringToByteArray(stringBuffer.toString());
                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(ip);
//                    address = InetAddress.getByName(IP2);
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("Anzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    /**
     * 发送扫频
     *
     * @param list
     * @param ip
     */
    public static void sendspSocket(final List<Integer> list, final String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzqsendspSocket", "run: nzqsend" + list.toString());
                int wholeBandRem = 1;
                int size = list.size();
                String setpd = setpd(wholeBandRem, size, list);
                Log.d(TAG, "setpdrun: " + setpd);
//                String Mystr = "aa aa 55 55 09 f0 3c 00 00 00 00 ff 01 00 00 00 02 00 00 00 26 98 00 00 0c 94 00 00 00 96 00 00 90 96 00 00 27 98 00 00 ec 98 00 00 7c 99 00 00 2b 98 00 00 c2 4c 00 00 71 4d 00 00";
                byte[] outputData = MainUtilsThread.hexStringToByteArray(setpd(wholeBandRem, size, list));
//                byte[] outputData = MainUtilsThread.hexStringToByteArray(Mystr);
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(ip);
////                    try {
//                        Thread.sleep(2000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendspSocket", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
                    Log.d("sendsaopin1", "run: ");
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    /**
     * 公放设置1
     *
     * @param sb      设备序号
     * @param switchs 开关  1代表开 2代表关闭
     */
    public static void OpenGF1(final int sb, final int switchs, final Handler handler) {
//        final StringBuffer str = new StringBuffer(Constant.Restart);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;
                if (switchs == 1) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.OPENGF);
                }
                if (switchs == 2) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.CLOSEGF);

                }
                try {
                    socket = new DatagramSocket();
                    if (sb == 1) {
                        address = InetAddress.getByName(Constant.IP1);
                    }
                    if (sb == 2) {
                        address = InetAddress.getByName(Constant.IP2);
                    }
//                    try {
////                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
//                    socket.sendMessage(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                boolean GFFLAG1 = true;
                boolean GFFLAGs1 = true;
                long l = System.currentTimeMillis();
                while (GFFLAG1) {
                    long la = System.currentTimeMillis();
                    if (la - l > 5000) {
                        GFFLAG1 = false;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        message.setData(bundle);
                        handler.sendMessage(message);
//                        message.what = 100143;

                    } else if (GFFLAGs1 == false) {
                        GFFLAG1 = false;
                    }
                }
            }
        }).start();

    }

    /**
     * 公放设置2
     *
     * @param sb      设备序号
     * @param switchs 开关  1代表开 2代表关闭
     */
    public static void OpenGF2(final int sb, final int switchs, final Handler handler) {
//        final StringBuffer str = new StringBuffer(Constant.Restart);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;
                if (switchs == 1) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.OPENGF);
                }
                if (switchs == 2) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.CLOSEGF);

                }
                try {
                    socket = new DatagramSocket();
                    if (sb == 1) {
                        address = InetAddress.getByName(Constant.IP1);
                    }
                    if (sb == 2) {
                        address = InetAddress.getByName(Constant.IP2);
                    }
//                    try {
////                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
//                    socket.sendMessage(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();

    }

    //小区去激活指令
    public static void StopLocation(final String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;
//
                outputData = MainUtilsThread.hexStringToByteArray(Constant.STOPLOCATION);

                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(ip);
//                    try {
////                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    /**
     * 发送SNF
     *
     * @param ip
     */
    public static void start1SNF(final String ip, final String snf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;
//
                outputData = MainUtilsThread.hexStringToByteArray(snf);

                try {
                    socket = new DatagramSocket();

                    address = InetAddress.getByName(ip);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //切换制式
    public static void Qiehuanzs(final String zs, final String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("awwjjnzq", "run: " + zs);
                String Sa = "";
                byte[] outputData = null;
//                outputData = MainUtilsThread.hexStringToByteArray(location("460010619201205", "04", Sa,));
                if (zs.equals("FDD")) {
                    Sa = "00";
                }
                if (zs.equals("TDD")) {
                    Sa = "01";
                }

                if (zs.equals("FDD")) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.FDD);//切换FDD
                }
                if (zs.equals("TDD")) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.TDD);//切换TDD
                }

                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                Log.d("awwjjnzq", "run: " + Sa);
//                    byte[] outputData = MainUtilsThread.hexStringToByteArray(location("460010619201205", "04", Sa));
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 定位模式
     *
     * @param
     * @param imsi  imsi号
     * @param sbzq  上报周期时间   00：120ms 01：240ms 02：480ms 3：640ms 04：1024ms 05：2048ms
     * @param power 功率  00：enable-FDD(最大功率) 01：disable-TDD(最小功率)
     * @return
     */
//    public static String location(String imsi, String sbzq, String power) {
//        StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff02040100");//定位模式
////        StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff01040100");//侦码模式
////        buffer.append("30000000000000000000000000000000000000170000000000000000");
//        /**
//         * 4-14
//         */
//        StringBuffer buffer1 = buffer.append(toIMSI(imsi)).append("0000").append(sbzq).append("00").append("170100000000000000");//不启用srs配置 启动prb配置
//
//        /**
//         * 2020 -3-19 改
//         */
////        StringBuffer buffer1 = buffer.append(toIMSI(imsi)).append("0000").append(sbzq).append("00").append("170000000000000000");//不启用srs配置
////        StringBuffer buffer1 = buffer.append(toIMSI(imsi)).append("0000").append(sbzq).append("00").append("170000010000000000");//启用srs配置
//        //  动态 抓号周期
//        return buffer1.toString();
//    }


    /**
     * 定位模式
     *
     * @param sb    设备编号
     * @param imsi  imsi号
     * @param sbzq  上报周期时间   00：120ms 01：240ms 02：480ms 3：640ms 04：1024ms 05：2048ms
     * @param power 功率  00：enable-FDD(最大功率) 01：disable-TDD(最小功率)
     * @return
     */
    public static String location(String imsi, String sbzq, String power, Context context, int sb) {

        StringBuffer buffer1 = new StringBuffer();
        DBManager01 dbManager01 = null;
        Conmmunit01Bean forid = null;
        String cycle = "";
        if (sb == 1) {
            try {
                dbManager01 = new DBManager01(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                forid = dbManager01.forid(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cycle = forid.getCycle();
            if (TextUtils.isEmpty(cycle)) {
                cycle = "0";
            }

            int a = Integer.parseInt(cycle);
            byte[] bytes = StringConvertUtils.toHH(a);
            String str = ReceiveTask.toHexString1(bytes);
            String substring = str.substring(4);
            String substring1 = substring.substring(0, 1);
            String substring2 = substring.substring(1, 2);
            String substring3 = substring.substring(2, 3);
            String substring4 = substring.substring(3, 4);
            String strs = substring3 + substring4 + substring1 + substring2;
//            StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff02040100");//定位模式

            StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff0204");//定位模式
            buffer.append(strs);

            buffer1 = buffer.append(toIMSI(imsi)).append("0000").append(sbzq).append("00").append("170000010000000000");//启用srs配置 bu 启动prb配置
            return buffer1.toString();
        } else if (sb == 2) {
            try {
                dbManager01 = new DBManager01(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                forid = dbManager01.forid(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cycle = forid.getCycle();
            if (TextUtils.isEmpty(cycle)) {
                cycle = "0";

            }
            int a = Integer.parseInt(cycle);
            byte[] bytes = StringConvertUtils.toHH(a);
            String str = ReceiveTask.toHexString1(bytes);
            String substring = str.substring(4);
            String substring1 = substring.substring(0, 1);
            String substring2 = substring.substring(1, 2);
            String substring3 = substring.substring(2, 3);
            String substring4 = substring.substring(3, 4);
            String strs = substring3 + substring4 + substring1 + substring2;
//            StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff02040100");//定位模式
            StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff0204");//定位模式
            buffer.append(strs);

            buffer1 = buffer.append(toIMSI(imsi)).append("0000").append(sbzq).append("00").append("170000010000000000");//启用srs配置 bu启动prb配置
            return buffer1.toString();
        }
        return buffer1.toString();
    }

    public static String locationzm(Context context, int sb) {
//        StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff01040100");//侦码模式
        StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff0104");//侦码模式

//        buffer.append("30000000000000000000000000000000000000170000000000000000");
//        buffer.append("30000000000000000000000000000000000000170100000000000000");// 4-20


        DBManager01 dbManager01 = null;
        Conmmunit01Bean forid = null;
        String cycle = "";
        if (sb == 1) {
            try {
                dbManager01 = new DBManager01(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                forid = dbManager01.forid(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cycle = forid.getCycle();
            if (TextUtils.isEmpty(cycle)) {
                cycle = "0";
            }
            int a = Integer.parseInt(cycle);
            byte[] bytes = StringConvertUtils.toHH(a);
            String str = ReceiveTask.toHexString1(bytes);
            String substring = str.substring(4);
            String substring1 = substring.substring(0, 1);
            String substring2 = substring.substring(1, 2);
            String substring3 = substring.substring(2, 3);
            String substring4 = substring.substring(3, 4);
            String strs = substring3 + substring4 + substring1 + substring2;
//            StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff02040100");//定位模式

            buffer = new StringBuffer("aaaa555506f02800000000ff0104");//帧码模式
            buffer.append(strs);

            buffer.append("30000000000000000000000000000000000000170000010000000000");

        } else if (sb == 2) {
            try {
                dbManager01 = new DBManager01(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                forid = dbManager01.forid(2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cycle = forid.getCycle();
            if (TextUtils.isEmpty(cycle)) {
                cycle = "0";

            }
            int a = Integer.parseInt(cycle);
            byte[] bytes = StringConvertUtils.toHH(a);
            String str = ReceiveTask.toHexString1(bytes);
            String substring = str.substring(4);
            String substring1 = substring.substring(0, 1);
            String substring2 = substring.substring(1, 2);
            String substring3 = substring.substring(2, 3);
            String substring4 = substring.substring(3, 4);
            String strs = substring3 + substring4 + substring1 + substring2;
//            StringBuffer buffer = new StringBuffer("aaaa555506f02800000000ff02040100");//定位模式
            buffer = new StringBuffer("aaaa555506f02800000000ff0104");//帧码模式
            buffer.append(strs);

            buffer.append("30000000000000000000000000000000000000170000010000000000");
//            return buffer1.toString();
        }
        return buffer.toString();
    }

    /*
     * 判断数组中是否有重复的值
     */
    public static boolean cheakIsRepeat(List<Integer> array) {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (int i = 0; i < array.size(); i++) {
            hashSet.add(array.get(i));
        }
        if (hashSet.size() == array.size()) {
            return true;
        } else {
            return false;
        }
    }

    //字符串前添加0，添加到字符串为8位为止
    public static StringBuffer StringAdd(String str) {
        StringBuffer buffer = new StringBuffer(str);
        for (int i = buffer.length(); i < 8; i++) {
            buffer.insert(0, "0");
        }
        return buffer;
    }

    //字符串分割成两个字符一组，倒序拼接到一起
    public static StringBuffer StringPin(StringBuffer str) {

        String[] bands = new String[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            bands[i / 2] = str.substring(i, i + 2);
        }

        StringBuffer str1 = new StringBuffer();
        for (int i = bands.length - 1; i >= 0; i--) {
            str1.append(bands[i]);
        }

        return str1;
    }

    /**
     * @param wholeBandRem 是否开启全频段扫频  取值范围为：0：不开启；1：开启。
     * @param sysEarfcnNum 扫频频点数目。取值范围为：1~10。
     * @return
     */
    public static String setpd(int wholeBandRem, int sysEarfcnNum, List<Integer> sysEarfcn) {

        StringBuffer buffer3 = new StringBuffer();
        if (sysEarfcn.size() != 10) {

            System.out.println("参数数量不对");
        }
        if (cheakIsRepeat(sysEarfcn)) {
            for (int i = 0; i < sysEarfcn.size(); i++) {
                buffer3.append(StringPin(StringAdd(Integer.toString(sysEarfcn.get(i), 16))));
            }
        } else {
            System.out.println("有重复值！");
        }
        //消息头
        StringBuffer buffer = new StringBuffer("aaaa555509f03c00000000ff");
        //是否开启全频段扫频  取值范围为：0：不开启；1：开启。
        StringBuffer buffer1 = StringPin(StringAdd(Integer.toString(wholeBandRem, 16)));
        //扫频频点数目。取值范围为：1~10
        StringBuffer buffer2 = StringPin(StringAdd(Integer.toString(sysEarfcnNum, 16)));

        String str = buffer.append(buffer1).append(buffer2).append(buffer3).toString();

        return str;

    }

    public static String getBand(int down) {
        String BAND = "1";

        if (down >= 0 && down <= 599) {
            BAND = "1";
            return BAND;
        }

        if (down >= 1200 && down <= 1949) {
            BAND = "3";
            return BAND;
        }

        if (down >= 2400 && down <= 2649) {
            BAND = "5";
            return BAND;
        }


        if (down >= 3450 && down <= 3799) {
            BAND = "8";
            return BAND;
            //FDD
        }

        if (down >= 36200 && down <= 36349) {
            BAND = "34";
            return BAND;
        }
        if (down >= 37750 && down <= 38249) {
            BAND = "38";
            return BAND;
        }
        if (down >= 38250 && down <= 38649) {
            BAND = "39";
            return BAND;
        }
        if (down >= 38650 && down <= 39649) {
            BAND = "40";
            return BAND;
        }
        if (down >= 39650 && down <= 41589) {
            BAND = "41";
            return BAND;
        }
        return BAND;

    }

    public static String getTF(int down) {
        String BAND = "FDD";

        if (down >= 0 && down <= 599) {
            BAND = "1";
            return "FDD";
        }

        if (down >= 1200 && down <= 1949) {
            BAND = "3";
            return "FDD";
        }

        if (down >= 2400 && down <= 2649) {
            BAND = "5";
            return "FDD";
        }


        if (down >= 3450 && down <= 3799) {
            BAND = "8";
            return "FDD";
            //FDD
        }

        if (down >= 36200 && down <= 36349) {
            BAND = "34";
            return "FDD";
        }
        if (down >= 37750 && down <= 38249) {
            BAND = "38";
            return "FDD";
        }
        if (down >= 38250 && down <= 38649) {
            BAND = "39";
            return "FDD";
        }
        if (down >= 38650 && down <= 39649) {
            BAND = "40";
            return "FDD";
        }
        if (down >= 39650 && down <= 41589) {
            BAND = "41";
            return "FDD";
        }
        return BAND;

    }

    //字符串前添加0，添加到字符串为16位为止
    public static String StringAd(String str) {
        StringBuffer buffer = new StringBuffer(str);
        for (int i = buffer.length(); i < 16; i++) {
            buffer.insert(0, "0");
        }
        return buffer.toString();
    }

    public static void TYPES(final Handler handler) {//用于监听设备响应状态  黑名单清空状态,黑名单设置状态,设置定位模式状态  建立小区状态 ,
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {//是否开启
                    if (Constant.CALLBLACKFLAG1 == true) {//清空黑名单设备1
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKTIME1 > 15000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKFLAG1", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8121;
//                            System.out.println("执行了8121");

                        } else {
//                            System.out.println("执行了8121SSS");
                        }
                    }
                    if (Constant.CALLBLACKFLAG2 == true) {//清空黑名单设备2
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKTIME2 > 15000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKFLAG2", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8122;
                            System.out.println("执行了8121");

                        } else {
//                            System.out.println("执行了8121SSS");
                        }
                    }

                    if (Constant.CALLBLACKFLAGSET1 == true) {//设置黑名单列表设备1
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKTIMESET1 > 15000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKFLAGSET1", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8131;
//                            System.out.println("执行了8131");

                        } else {
//                            System.out.println("执行了8131SSS");
                        }
                    }
                    if (Constant.CALLBLACKFLAGSET2 == true) {//设置黑名单列表设备2
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKTIMESET2 > 15000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKFLAGSET2", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8132;
                            System.out.println("执行了8132");

                        } else {
                            System.out.println("执行了8132SSS");
                        }
                    }

                    if (Constant.CALLBLACKOPEN1 == true) {//打开公放1
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKOPENSET1 > 15000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKOPEN1", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8141;
                            System.out.println("执行了818141");

                        } else {
                            System.out.println("执行了8141SSS");
                        }
                    }

                    if (Constant.CALLBLACKOPEN2 == true) {//打开公放1
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKOPENSET2 > 15000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKOPEN2", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8142;
                            System.out.println("执行了8142");

                        } else {
                            System.out.println("执行了8142SSS");
                        }
                    }
                    if (Constant.CALLBLACKLOCATION1 == true) {//设置定位模式1
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKLOCATION1 > 10000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKLOCATION1", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8151;
//                            System.out.println("执行了8151");

                        } else {
//                            System.out.println("执行了8151SSS");
                        }
                    }

                    if (Constant.CALLBLACKLOCATION2 == true) {//设置定位模式2
                        long blackTime11s = System.currentTimeMillis();
                        if (blackTime11s - Constant.BLACKLOCATION2 > 10000) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("CALLBLACKLOCATION2", "1");
                            message.setData(bundle);
                            handler.sendMessage(message);
                            message.what = 8152;
                            System.out.println("执行了8152");

                        } else {
                            System.out.println("执行了8152SSS");
                        }
                    }
                }
            }
        }).start();

    }

    /**
     * 判断当前设备状态的定位模式 ：true 为定位模式，false侦码模式
     *
     * @param sbID        设备 ：1,2
     * @param context：上下文
     * @return
     */
    public static boolean dblocation(int sbID, Context context) {
        DBManager01 dbManager01 = null;

        try {
            dbManager01 = new DBManager01(context);

        } catch (Exception e) {

        }
        Conmmunit01Bean forid1 = null;
        try {
            forid1 = dbManager01.forid(sbID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String type1 = forid1.getType();
        if (type1.equals("0")) {  // 定位模式
            return true;

        } else {//侦码模式
            return false;

        }
    }

    public static void sbzmLocation(final String ip, final Context context) {//侦码模式
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;

                if (ip.equals(Constant.IP1)) {
                    outputData = MainUtilsThread.hexStringToByteArray(locationzm(context, 1));
                }
                if (ip.equals(Constant.IP2)) {
                    outputData = MainUtilsThread.hexStringToByteArray(locationzm(context, 2));
                }

                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(ip);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//              DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsbzmLocation", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
//                    interrupted();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
