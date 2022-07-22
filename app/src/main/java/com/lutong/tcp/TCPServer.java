package com.lutong.tcp;

import static com.lutong.Constants.beat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.lutong.App.MessageEvent;
import com.lutong.AppContext;
import com.lutong.Utils.JK;
import com.lutong.server.SocketServerClientHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class TCPServer {
    Socket socket;
    ServerSocket serverSocket;
    InputStream inputStream;
    OutputStream outputStream;
    Message message;
    Handler handler;

    public TCPServer(Handler handler,Integer port) {
        try {
            serverSocket = new ServerSocket(port);
            this.handler = handler;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ylt", "TCPServer: "+e.getMessage() );
        }
    }



    ArrayList<SocketServerClientHandler> list = new ArrayList<>();
    public void connect() throws Exception {
        list.clear();
        System.out.println("?????????????");
        while (true) {
            Socket socket = serverSocket.accept();
            Log.e("ylt", "connect: " + socket);
//            SocketServerClientHandler client1 = new SocketServerClientHandler(socket, this);
//            list.add(client1);
//            client1.start();
            if (socket != null) {
                System.out.println("连接成功");
                message = new Message();
                message.obj = "连接成功";
                message.what = 3333;//连接成功
                handler.sendMessage(message);
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
            Log.e("ylt", "accept: " + fromHexString);
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
                Log.e("ylt", "accept: " + json);
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

    public void startSchedule(Timer timerStart, int time) {
        timerStart.schedule(new MyTasks(), 0, time);
    }

    private void sendHandlerMessage(int i, String msg) {
        Message message = Message.obtain();
        message.what = i;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void sendHandlerMessage(int i, boolean msg) {
        Message message = Message.obtain();
        message.what = i;
        message.obj = msg;
        handler.sendMessage(message);
    }

    public void startSocketConnect(ExecutorService executorService) {
        //创建可缓存线程池
        executorService.submit(new TimerTask() {
            @Override
            public void run() {
                try {
                    eliminate();
                    connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class MyTasks extends TimerTask {
        @Override
        public void run() {
            sendMessage(beat);//心跳指令
        }
    }

    /**
     * 判断 sim 卡是否准备好
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = (TelephonyManager) AppContext.context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null && tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }


    /**
     * 通过tcp发送信息
     */
    public void sendMessage(final String s) {
        new Thread(() -> {
            try {
                for (SocketServerClientHandler client : list) {
//                    if (client.getClientConnectSocket().getPort() != client.getPorts())//wifi的不走
                    Log.e("ylt", "sendMessage: "+s);
                    client.getClientConnectSocket().getOutputStream().flush();
                    client.getClientConnectSocket().getOutputStream().write(JK.hexStringToByteArray(s));
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendHandlerMessage(8888, e.getMessage());
            }
        }).start();
    }
}
