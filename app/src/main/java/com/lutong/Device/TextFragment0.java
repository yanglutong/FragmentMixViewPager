package com.lutong.Device;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lutong.R;
import com.lutong.Utils.DeviceUtils;
import com.lutong.base.BaseFragment;
import com.lutong.Constant.Constant;

import java.text.DecimalFormat;

public class TextFragment0 extends BaseFragment {
    private static String TAG = "TextFragment0";
    DecimalFormat df2;
    View view;
    TextView tv_devicenumber, tv_hardware, tv_software, tv_sn, tv_mac, tvuboot, tvboard;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100001) {//无线正确

            }
        }
    };


//    public View initView() {
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.deviceinfragment0, null);
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
        view = inflater.inflate(R.layout.deviceinfragment0, null);
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
    public void onStart() {
        super.onStart();
        clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResumeA: " + "执行了4");
        clear();
        getData();
    }

    private void findViews() {
        tv_devicenumber = view.findViewById(R.id.tv_devicenumber);
        tv_hardware = view.findViewById(R.id.tv_hardware);
        tv_software = view.findViewById(R.id.tv_software);
        tv_sn = view.findViewById(R.id.tv_sn);
        tv_mac = view.findViewById(R.id.tv_mac);
        tvuboot = view.findViewById(R.id.tvuboot);
        tvboard = view.findViewById(R.id.tvboard);

        tv_devicenumber.setText(Constant.DEVICENUMBER1);
        tv_hardware.setText(Constant.HARDWAREVERSION1);
        tv_software.setText(Constant.SOFTWAREVERSION1);
        tv_sn.setText(Constant.SNNUMBER1);
        tv_mac.setText(Constant.MACADDRESS1);
        tvuboot.setText(Constant.UBOOTVERSION1);
        tvboard.setText(Constant.BOARDTEMPERATURE1);
        if (!TextUtils.isEmpty(Constant.BOARDTEMPERATURE1)) {
            Double i = Double.parseDouble(Constant.BOARDTEMPERATURE1);

            tvboard.setText(df2.format(i));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法
            Log.d(TAG, "onResumeA: " + "执行了1");
//            tv_devicenumber.setText("");
//            tv_hardware.setText("");
//            tv_software.setText("");
//            tv_sn.setText("");
//            tv_mac.setText("");
//            tvuboot.setText("");
//            initData();
//            clear();
//            initView();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    clear();
                    getData();
                }
            }, 100);
        } else {
//            clear();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    clear();
                    getData();
                }
            }, 100);
//            getData();
            Log.d(TAG, "onResumeA: " + "执行了2");
        }
    }

    @Override
    public boolean getUserVisibleHint() {
        boolean userVisibleHint = super.getUserVisibleHint();
        Log.d(TAG, "onResumeA: " + "执行了" + userVisibleHint);
        return super.getUserVisibleHint();


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onResumeA: " + "执行了3");
        clear();
    }

    private void clear() {
        tv_devicenumber.setText("");
        tv_hardware.setText("");
        tv_software.setText("");
        tv_sn.setText("");
        tv_mac.setText("");
        tvuboot.setText("");
        tvboard.setText("");
    }

    private void getData() {
        Log.d(TAG, "TextFragment0onResume: " + "执行了2");
        // 相当于onpause()方法
        DeviceUtils.Select(0, 1);//设备号
        DeviceUtils.Select(1, 1);//硬件版本
        DeviceUtils.Select(2, 1);//软件版本
        DeviceUtils.Select(3, 1);//SN号
        DeviceUtils.Select(4, 1);//MAC地址
        DeviceUtils.Select(5, 1);//uboot版本号
        DeviceUtils.Select(6, 1);//板卡温度


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_devicenumber.setText("");
                tv_hardware.setText("");
                tv_software.setText("");
                tv_sn.setText("");
                tv_mac.setText("");
                tvuboot.setText("");

                tv_devicenumber.setText(Constant.DEVICENUMBER1);
                tv_hardware.setText(Constant.HARDWAREVERSION1);
                tv_software.setText(Constant.SOFTWAREVERSION1);
                tv_sn.setText(Constant.SNNUMBER1);
                tv_mac.setText(Constant.MACADDRESS1);
                if (!TextUtils.isEmpty(Constant.BOARDTEMPERATURE1)) {
                    Double i = Double.parseDouble(Constant.BOARDTEMPERATURE1);

                    tvboard.setText(df2.format(i));
                }
                tvuboot.setText(Constant.UBOOTVERSION1);
//                tvboard.setText(BOARDTEMPERATURE1);





            }
        }, 5000);
    }
}
