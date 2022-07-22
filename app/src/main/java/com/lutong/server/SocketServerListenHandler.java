package com.lutong.server;

import static com.lutong.Constants.beat;
import static com.lutong.Constants.port;
import static com.lutong.Constants.ports;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lutong.Constant.Constant;
import com.lutong.Constants;
import com.lutong.Utils.JK;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputValidation;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

    //A server socket waits for requests to come in over the network.
    //这是一个不断等待获取网络传入请求的服务端Socket
    private ServerSocket serverSocket;

    /**
     * 构造方法
     *
     * @param handler handler
     * @param port    端口
     */
    public SocketServerListenHandler(Handler handler, int port) {
        try {
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

    /**
     * 通过tcp发送信息
     */
    public void sendMessage(final String s) {
        Log.e("ylt", "sendMessage: 发送消息"+s);
        new Thread(() -> {
            try {
                if (list.size() > 0) {//连接状态
                    for (SocketServerClientHandler socket : list) {
                        socket.getOutputStream().write(JK.hexStringToByteArray(s));
                    }
                } else {//未连接分两种情况 地址被占用就不进行操作，地址可用未连接才进行连接
                    sendHandlerMessage(8888, "未连接");
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendHandlerMessage(8888, e.getMessage());
            }
        }).start();
    }


    public void startSchedule(Timer timerStart, int time) {
        timerStart.schedule(new MyTasks(), 1000, time);
    }

    private void sendHandlerMessage(int i, String msg) {
        Message message = Message.obtain();
        message.what = i;
        message.obj = msg;
        handler.sendMessage(message);
    }

    class MyTasks extends TimerTask {
        @Override
        public void run() {
            sendMessage(beat);//心跳指令
        }
    }

    /**
     * 清除
     */
    public void eliminate() {
        try {
            for (SocketServerClientHandler socket : list) {
                socket.getOutputStream().close();
                socket.getInputStream().close();
                socket.setOutputStream(null);
                socket.setInputStream(null);
            }
            for (SocketServerClientHandler socket : list) {
                socket.getClientConnectSocket().close();
                socket.setClientConnectSocket(null);
            }
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "eliminate: " + e.getMessage());
        }
    }
}