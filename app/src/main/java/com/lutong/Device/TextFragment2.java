package com.lutong.Device;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lutong.R;
import com.lutong.Utils.DeviceUtils;
import com.lutong.base.BaseFragment;

import java.text.DecimalFormat;

public class TextFragment2 extends BaseFragment {
    private static String TAG = "TextFragment0";
    DecimalFormat df2;
    //            NumberFormat.getInstance(Locale.GERMAN);
    View view;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100001) {//无线正确

            }
        }
    };


//    public View initView() {
////        view = inflater.inflate(R.layout.deviceinfragment0, container, false);
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.deviceinfragment2, null);
//            initData();
//        }
////        view = inflater.inflate(R.layout.deviceinfragment0, container, false);
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeView(view);
//            initData();
//        }
//        return view;
//    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.deviceinfragment5gcanshu, null);
        initView(view);
        initData();
        return view;
    }
    @Override
    public void initData() {
        df2 = new DecimalFormat("####.00");
        findViews();
        clear();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        clear();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        clear();
    }

    private void findViews() {

    }

    @Override
    public void onStart() {
        super.onStart();
        clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        clear();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法
            Log.d(TAG, "onResume: " + "执行了1");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    clear();
                    getData();
                }
            }, 100);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    clear();
                    getData();
                }
            }, 100);

        }
    }

    private void clear() {

    }

    private void getData() {
        Log.d(TAG, "onResume: " + "执行了2");
        // 相当于onpause()方法
        DeviceUtils.Select(0, 2);//设备号
        DeviceUtils.Select(1, 2);//硬件版本
        DeviceUtils.Select(2, 2);//软件版本
        DeviceUtils.Select(3, 2);//SN号
        DeviceUtils.Select(4, 2);//MAC地址
        DeviceUtils.Select(5, 2);//uboot版本号
        DeviceUtils.Select(6, 2);//板卡温度


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 5000);
    }
}
