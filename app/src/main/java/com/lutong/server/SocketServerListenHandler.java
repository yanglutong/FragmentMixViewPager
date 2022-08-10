package com.lutong.server;

import static com.lutong.Constants.beat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.lutong.ConnectivityManager.NetUtil;
import com.lutong.Constants;
import com.lutong.Utils.JK;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: com.io.socket.server
 * @description: 服务端监听类
 * 监听客户端的请求
 * @author: liujinghui
 * @create: 2019-02-16 10:39
 **/
public class SocketServerListenHandler {
    Handler handler;
    int port;
    Context context;
    //A server socket waits for requests to come in over the network.
    //这是一个不断等待获取网络传入请求的服务端Socket
    private ServerSocket serverSocket;

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param handler handler
     * @param port    端口
     */
    public SocketServerListenHandler(Context context, Handler handler, int port) {
        try {
            this.context = context;
            this.handler = handler;
            this.port = port;
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 无限循环的监听客户端的连接
     * Listens for a connection to be made to this socket and accepts it.
     * 当有一个连接产生，将结束accept()方法的阻塞
     * The method blocks until a connection is made.
     */
    ArrayList<SocketServerClientHandler> list = new ArrayList<>();

    public void listenClientConnect() {
        eliminate();//主动关闭socket
        //再次进行连接
        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(Constants.port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread(() -> {
            while (true) {
                try {
                    if (list.size() <= 1) {
                        Log.e("ylt", "listenClientConnect: ");
                        System.out.println("服务端监听开始。。。");
                        Socket clientConnectSocket = serverSocket.accept();
                        if (clientConnectSocket != null) {
                            sendHandlerMessage(3333, "连接成功");
                            accept(clientConnectSocket);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("服务端监听连接程序异常" + e.getMessage());
                }
            }
        }).start();
    }

    private void accept(Socket clientConnectSocket) {
        SocketServerClientHandler client1 = new SocketServerClientHandler(clientConnectSocket);
        list.add(client1);
        client1.start();
        Log.e("ylt", "listenClientConnect: 监听到客户端连接" + clientConnectSocket + "---list" + list.size());
    }

    public void sendMessage(final String s) {
        new Thread(() -> {
            atomicBoolean(s);
//            try {
//                if (this.list.size() > 0) {//连接状态
//                    for (SocketServerClientHandler socket : this.list) {
//                        socket.getOutputStream().write(JK.hexStringToByteArray(s));
//                    }
//                } else {//未连接分两种情况 地址被占用就不进行操作，地址可用未连接才进行连接
//                    sendHandlerMessage(8888, "未连接");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                sendHandlerMessage(8888, e.getMessage());
//            }
        }).start();
        Log.e("ylt", "sendMessage: 发送消息" + s);
    }

    ArrayList<Boolean> listNet = new ArrayList<>();
    private void setTitleState(boolean atomicBoolean) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator(); //sim提供者
        listNet.clear();

        if(NetUtil.getNetWorkState(context) == 1){//wifi可用 卡可用  卡不可用
            //连接着，并且网络不可用，扫网可用，true
            if(TextUtils.isEmpty(operator)){
                //wifi能用 并且卡不可用
                listNet.add(false);//添加移动数据不可用标识，基站查询
            }else{
                //wifi能用 卡可用
                listNet.add(true);//添加移动数据不可用标识，基站查询
            }
            listNet.add(atomicBoolean);//连接着，并且网络不可用，扫网可用，true
        }else{
            if (NetUtil.getNetWorkState(context) == -1) {//网络不可用
                //移动数据不可用
                listNet.add(false);//添加移动数据不可用标识，基站查询
                listNet.add(atomicBoolean);//连接着，并且网络不可用，扫网可用，true
            } else if(NetUtil.getNetWorkState(context) == 0){//移动数据可用
                listNet.add(true);
                listNet.add(false);//扫网不可用，false
            }
        }
        sendHandlerMessage(3030, listNet);//标题栏状态
    }


    AtomicBoolean isConnect = new AtomicBoolean(false);
    /**
     * 通过tcp发送信息
     *
     * @param s
     */
    public boolean atomicBoolean(String s) {
        try {
            if (this.list.size() > 0) {//连接状态
                for (SocketServerClientHandler socket : this.list) {
                    socket.getOutputStream().write(JK.hexStringToByteArray(s));
                    isConnect.set(true);
                    Log.e("TAG", "atomicBoolean1: " + s);
                }
            } else {//未连接分两种情况 地址被占用就不进行操作，地址可用未连接才进行连接
                isConnect.set(false);
                sendHandlerMessage(8888, "未连接");
                Log.e("TAG", "atomicBoolean2: " + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
            isConnect.set(false);
            sendHandlerMessage(8888, "未连接");
            Log.e("TAG", "atomicBoolean3: " + s);
        }
        return isConnect.get();
    }


    public void startSchedule(Timer timerStart, int time) {
        timerStart.schedule(new MyTasks(), 0, time);
    }
    public void startScheduleTitle(Timer timerStart, int time) {
        timerStart.schedule(new MyTasksTitle(), 0, time);
    }

    private void sendHandlerMessage(int i, String msg) {
        Message message = Message.obtain();
        message.what = i;
        message.obj = msg;
        handler.sendMessage(message);
    }

    private void sendHandlerMessage(int i, List<Boolean> list) {
        if (handler == null) return;
        Message message = Message.obtain();
        message.what = i;
        message.obj = list;
        handler.sendMessage(message);
    }

    class MyTasks extends TimerTask {
        @Override
        public void run() {
            Log.e("TAG", " MyTasks" );
            sendMessage(beat);//心跳指令
        }
    }

    class MyTasksTitle extends TimerTask {
        @Override
        public void run() {
            Log.e("TAG", " MyTasksTitle" +isConnect.get());
            setTitleState(isConnect.get());
        }
    }

    /**
     * 清除
     */
    public void eliminate() {
        try {
            for (SocketServerClientHandler socket : list) {
                if (socket.getOutputStream() != null) {
                    socket.getOutputStream().close();
                }
                if (socket.getInputStream() != null) {
                    socket.getInputStream().close();
                }
            }
            for (SocketServerClientHandler socket : list) {
                if (socket.getClientConnectSocket() != null) {
                    socket.getClientConnectSocket().close();
                }
            }
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "eliminate: " + e.getMessage());
        }
    }
}