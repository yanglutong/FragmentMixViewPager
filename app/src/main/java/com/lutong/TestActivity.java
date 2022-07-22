package com.lutong;

import static com.lutong.Constants.beat;
import static com.lutong.Constants.sendLte;
import static com.lutong.Constants.sendStop;

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
        setContentView(R.layout.activity_test2);
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
}