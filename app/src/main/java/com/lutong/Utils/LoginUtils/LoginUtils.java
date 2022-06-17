package com.lutong.Utils.LoginUtils;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;


import com.lutong.Utils.ReceiveTask;
import com.lutong.Constant.Constant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginUtils {

    public static void ReceiveLogin(final Handler handler, final Boolean LoginTAG) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket ds = null;
                byte[] buf = null;
                buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds = new DatagramSocket(3345);
                    if (ds == null) {
                        ds = new DatagramSocket(null);
                        ds.setReuseAddress(true);
                        ds.bind(new InetSocketAddress(3345));
                    }
                } catch (Exception e) {

                }
                while (LoginTAG) {

                    try {
                        ds.receive(dp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] buf1 = dp.getData();
                    //btye[]字节数组转换成16进制的字符串
                    String str = ReceiveTask.toHexString1(buf1);
                    if (Constant.IP1.equals(dp.getAddress().getHostAddress())) {
                        System.out.println("123456");
                        System.out.println("收到" + dp.getAddress().getHostAddress() + "发送数据：" + str);

                        //时间
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        System.out.println("时间：" + sdf.format(d));

                        if ("10f0".equals(str.substring(8, 12))) {
                            //心跳解析
                            //查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
                            Message message = new Message();
                            Bundle bundle = new Bundle();
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
//                                Integer.parseInt(StringPin(str.substring(28, 32)), 16);
//                                System.out.println("Band号：" + Integer.parseInt(StringPin(str.substring(28, 32)), 16));
//                                //上行频点
//                                Integer.parseInt(StringPin(str.substring(32, 40)), 16);
//                                System.out.println("上行频点：" + Integer.parseInt(StringPin(str.substring(32, 40)), 16));
//                                //下行频点
//                                Integer.parseInt(StringPin(str.substring(40, 48)), 16);
//                                System.out.println("下行频点：" + Integer.parseInt(StringPin(str.substring(40, 48)), 16));
//                                DOWNPIN1 = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
//                                message = new Message();
//                                bundle.putString("down", Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");//查询增益
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100151;
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

//                                //PCI
//                                Integer.parseInt(StringPin(str.substring(64, 68)), 16);
//                                System.out.println("PCI:" + Integer.parseInt(StringPin(str.substring(64, 68)), 16));
//                                //TAC
//                                Integer.parseInt(StringPin(str.substring(68, 72)), 16);
//                                System.out.println("TAC:" + Integer.parseInt(StringPin(str.substring(68, 72)), 16));

                            } else if ("0400".equals(str.substring(24, 28))) {
                                System.out.println("4：小区去激活中");
                            }

                        }
                    }
                }
            }
        }).start();


    }

    //日志插入的日志Id
    public static int getId(Context context) {
        SharedPreferences userSettings = context.getSharedPreferences("setting", 0);

        return Integer.parseInt(userSettings.getString("id", ""));
    }

    public static String setBase64(String string) {

        String encodedString = Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
        return encodedString;
    }
    public static String getBase64(String string) {

        String decodedString =new String(Base64.decode(string,Base64.DEFAULT));

        return decodedString;
    }
}
