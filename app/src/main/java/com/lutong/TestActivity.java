package com.lutong;

import static com.lutong.Constants.beat;
import static com.lutong.Constants.sendLte;
import static com.lutong.Constants.sendStop;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

//import com.lutong.server.SocketServerListenHandler;

public class TestActivity extends AppCompatActivity {
//
//    private SocketServerListenHandler socketServerListenHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setStatBar(0);
//        socketServerListenHandler = new SocketServerListenHandler(null,56969);
//        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        socketServerListenHandler.sendMsg(sendLte);
//                    }
//                }).start();
//            }
//        });
//        findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        socketServerListenHandler.sendMsg(sendStop);
//                    }
//                }).start();
//            }
//        });
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                socketServerListenHandler.listenClientConnect();
//            }
//        }).start();
    }
    @SuppressLint("NewApi")
    public void setStatBar(int type) {//根据版本设置沉浸式状态栏
        View decorView = getWindow().getDecorView();
        int option;
        if (type == 1) {
            option = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.parseColor("#00564B"));
        } else {
            option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}