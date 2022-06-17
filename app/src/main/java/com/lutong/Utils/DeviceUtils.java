package com.lutong.Utils;

import android.util.Log;


import com.lutong.Constant.Constant;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * Created by admin on 2019/12/6.
 */

public class DeviceUtils {
    /**
     * 查询基站基本信息查询指令
     *
     * @param baseInfoType 0：设备号，1：硬件版本，2：软件版本，3：SN号；4：MAC地址；5:uboot版本号；5：板卡温度
     * @return
     */
    public static String baseStationInfo(int baseInfoType) {
        StringBuffer buffer = new StringBuffer("aaaa55552bf01000000000ff");
        StringBuffer buffer1 = StringPin(StringAdd(Integer.toString(baseInfoType, 16)));
        String str = buffer.append(buffer1).toString();
        return str;

    }

    /**
     * 小区信息查询指令
     *
     * @param str
     * @return
     */

    String ss = "aa aa 55 55 27 f0 0c 00 00 00 00 ff";

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

    //字符串前添加0，添加到字符串为8位为止
    public static StringBuffer StringAdd(String str) {
        StringBuffer buffer = new StringBuffer(str);
        for (int i = buffer.length(); i < 8; i++) {
            buffer.insert(0, "0");
        }
        return buffer;
    }

    /**
     * 查询基站基本信息查询指令
     *
     * @param type：设备号，1：硬件版本，2：软件版本，3：SN号；4：MAC地址；5:uboot版本号；5：板卡温度
     * @param sb                                                     设备编号 1 代表IP1 2 代表 ip2
     * @return
     */
    public static void Select(final int type, final int sb) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;

                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;
                outputData = MainUtilsThread.hexStringToByteArray(baseStationInfo(type));
                try {
                    socket = new DatagramSocket();
                    if (sb == 1) {
                        address = InetAddress.getByName(Constant.IP1);
                    }
                    if (sb == 2) {
                        address = InetAddress.getByName(Constant.IP2);
                    }

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                };

                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);

//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black温度", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
//                InetAddress inetAddress = socket.getInetAddress();
//                String s = inetAddress.toString();
//                Log.d("nzqsendstart1Black温度S", "run: "+s);
                try {

                    socket.send(outputPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 查询基站基本信息查询指令
     *
     * @param type：设备号，1：硬件版本，2：软件版本，3：SN号；4：MAC地址；5:uboot版本号；5：板卡温度
     * @param sb                                                     设备编号 1 代表IP1 2 代表 ip2
     * @return
     */
    public static void SelectQury(final int type, final int sb) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");
                byte[] outputData = null;
                if (type == 1) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.STADR);//小区状态信息查询指令
                    Log.d("STADRaaaa", "run: ");
                }
                if (type == 2) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.UESTR);//定位模式
                }
                if (type == 3) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.STR1AS);// //接受增益和发射功率查询
                }
                if (type == 4) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.STRTYPESXQ);// //小区状态信息查询指令
                }
                if (type == 5) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.BLACKLIST);// //黑名单查询指令
                }
                if (type == 6) {
                    outputData = MainUtilsThread.hexStringToByteArray(Constant.SUIJI);// //随机接入
                }
                try {
                    socket = new DatagramSocket();
                    if (sb == 1) {
                        address = InetAddress.getByName(Constant.IP1);
                    }
                    if (sb == 2) {
                        address = InetAddress.getByName(Constant.IP2);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
