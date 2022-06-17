package com.lutong.tcp_connect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lutong.App.MessageEvent;
import com.lutong.AppContext;
import com.lutong.ConnectivityManager.NetUtil;
import com.lutong.Constants;
import com.lutong.Utils.JK;
import com.lutong.Utils.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TCPServer {
    Socket socket;
    ServerSocket serverSocket;
    InputStream inputStream;
    OutputStream outputStream;
    Message message;
    Handler handler;

    public TCPServer(Handler handler) {
        this.handler = handler;
    }

    public void connect(Integer port) throws Exception {
        if (serverSocket == null) {
            serverSocket = new ServerSocket(port);
        }
        System.out.println("?????????????");
        while (true) {
            if (socket == null) {
                socket = serverSocket.accept();
            }
            if (socket != null) {
                System.out.println("连接成功");
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                message = new Message();
                message.obj = "连接成功";
                message.what = 3333;//连接成功
                handler.sendMessage(message);
                accept();
            }
        }
    }

    private void accept() throws Exception {
        while (true) {
            int count = 0;
            while (count == 0) {
                count = inputStream.available();//获取字节流的长度
            }
            byte[] b = new byte[count];
            inputStream.read(b);
            String fromHexString = getString(b);

            Log.e("ylt", "accept: "+fromHexString );
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
                Log.e("ylt", "accept: "+json );
                EventBus.getDefault().postSticky(new MessageEvent(11114, json));
            }
        }
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

    /**
     * 通过tcp发送信息
     */
    public void sendMessage(final String s) {
        new Thread(() -> {
            try {
                if (outputStream == null) {
                    return;
                }
                outputStream.flush();
                outputStream.write(JK.hexStringToByteArray(s));
                message = new Message();
                message.what = 2222;//发送
                message.obj = s;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ylt", "sendMessage3: "+e.getMessage() );
            }
        }).start();
    }

    /**
     * 清除
     */
    public void eliminate() {
        try {
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ylt", "eliminate: "+e.getMessage() );
//            sendHandlerMessage(e.getMessage());
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

    public void startTimerSchedule(Timer timerStart,  TCPServer tcpServer) {
        timerStart.schedule(new MyTask(tcpServer), 0, 5000);
    }

    public void startTimerScheduleNet(Timer timerStart,Context context) {
        timerStart.schedule(new MyTasks(context), 0, 2000);
    }

    private void sendHandlerMessage(String msg) {
        Message message = new Message();
        message.what = 4444;
        message.obj = msg;
        handler.sendMessage(message);
    }
    class MyTask extends TimerTask {
        private TCPServer tcpServer;

        public MyTask(TCPServer tcpServer) {
            this.tcpServer = tcpServer;
        }
        @Override
        public void run() {
            Log.e("tcp", "run: ");
            try {
                tcpServer.eliminate();
                tcpServer.connect(Constants.port);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("tcp", "run: "+e.getMessage() );
            }
        }
    }

    class MyTasks extends TimerTask {
        private Context context;
        public MyTasks(Context context) {
            this.context = context;
        }
        @Override
        public void run() {
            EventBus.getDefault().postSticky(new MessageEvent(3030, NetUtil.getNetWorkState(context)));
        }
    }
}
