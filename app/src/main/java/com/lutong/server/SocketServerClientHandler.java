package com.lutong.server;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lutong.App.MessageEvent;
import com.lutong.Utils.JK;
import com.lutong.tcp.JsonLteBean;
import com.lutong.tcp.JsonNrBean;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: com.io.socket.server
 * @description: 服务端客户消息处理线程类
 * @author: liujinghui
 * @create: 2019-02-16 11:02
 **/
public class SocketServerClientHandler extends Thread {
    //每个消息通过Socket进行传输
    private Socket clientConnectSocket;

    private InputStream inputStream;
    private OutputStream outputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public SocketServerClientHandler(Socket clientConnectSocket) {
        this.clientConnectSocket = clientConnectSocket;
    }

    public Socket getClientConnectSocket() {
        return clientConnectSocket;
    }

    public void setClientConnectSocket(Socket clientConnectSocket) {
        this.clientConnectSocket = clientConnectSocket;
    }

    @Override
    public void run() {
        try {
            inputStream = clientConnectSocket.getInputStream();
            outputStream = clientConnectSocket.getOutputStream();
            while (true) {
                int count = 0;
                while (count == 0) {
                    count = inputStream.available();//获取字节流的长度
                }
                byte[] b = new byte[count];
                inputStream.read(b);
                String fromHexString = getString(b);
                System.out.println("客户端" + clientConnectSocket.getPort() + "传来消息: " + fromHexString);

                if (fromHexString.contains("{\"NETMODE\":\"WIFI\"") || fromHexString.contains("{\"NETMODE\":\"WIFI\"")) {//wifi发来的消息
                    //将端口告诉后台

                }

                if (fromHexString.contains("\"NETMODE\":\"NR\"")) {
                    JsonNrBean json = new Gson().fromJson(fromHexString, JsonNrBean.class);
                    if (TextUtils.isEmpty(json.getTIME())) {//手动添加时间戳
                        json.setTIME(getTimeShort());
                    }
                    EventBus.getDefault().postSticky(new MessageEvent(11115, json));
                } else if (fromHexString.contains("\"NETMODE\":\"LTE\"")) {
                    JsonLteBean json = new Gson().fromJson(fromHexString, JsonLteBean.class);
                    if (TextUtils.isEmpty(json.getTIME())) {//手动添加时间戳
                        json.setTIME(getTimeShort());
                    }
                    EventBus.getDefault().postSticky(new MessageEvent(11114, json));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @NonNull
    private String getString(byte[] b) throws Exception {
        String hexStr = JK.conver16HexStr(b);//将字节转为16进制字符串
        String body_length = hexStr.substring(16, 24);//消息内容的长度 16进制
        String hex16To10 = JK.hex16To10(body_length);//将消息内容16转10输出
        String body = hexStr.substring(24, 24 + Integer.parseInt(hex16To10) * 2);//内容
        String fromHexString = JK.fromHexString(body);
        return fromHexString;
    }
}