package com.lutong.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.ActivityUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity {
    public static Dialog dialog;
    public static String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION

    };//申请的权限
    public static List<String> mPermissionList = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;
    public Context Basecontext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        initQx();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,//防止锁屏,只有当前为Activity时生效
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getView());//设置布局
        setStatBar();//根据版本设置沉浸式状态栏
        findViews();//初始化组件
        try {
            init();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Basecontext=this;
        // 初始化netEvent
//        netEvent = (NetBroadcastReceiver.NetChangeListener) this;
    }

    protected abstract void initQx();

    protected abstract void init() throws UnsupportedEncodingException;//初始化的方法

    protected abstract void findViews();//初始化组件

    protected abstract int getView();//布局

    @SuppressLint("NewApi")
    public void setStatBar() {//根据版本设置沉浸式状态栏
        View decorView = getWindow().getDecorView();
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT
        );
    }
//    private boolean ifWifi() {
//        String wifiName = MyUtils.getWifiName(this);
//        if (wifiName.equals("SMLOCATIONAP")) {
//            return true;
//        } else {
//            ToastUtils.showToast("连接wifi错误");
//            return false;
//        }
//    }
    public void LoadingTrue(String text) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.item_dibushow, null);
        TextView tv=inflate.findViewById(R.id.tv);
        tv.setText(""+text);
        //将布局设置给Dialog
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.setCanceledOnTouchOutside(false);//点击屏幕外不消失
        dialog.show();//显示对话框
    }

    public void LoadingFalse() {
        if (dialog!=null){
            dialog.dismiss();
            dialog.cancel();
        }

    }

    private long mPressedTime = 0;

    @Override
    public void onBackPressed() {//重写返回键方法
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        Log.d("nzq", "onBackPressed: "+mNowTime);
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            ToastUtils.showToast("再按一次退出程序");
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        if (dialog!=null){
            dialog.cancel();
        }
        super.onDestroy();
    }
    /**
     * 申请权限
     */
    public void getPermissions() {//获取手机权限
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    protected boolean isIgnoringBatteryOptimizations() {
//        boolean isIgnoring = false;
//        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        if (powerManager != null) {
//            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
//        }
//        return isIgnoring;
//    }
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void requestIgnoreBatteryOptimizations() {
//        try {
//            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    protected void onResume() {
        super.onResume();
        if (dialog!=null){

        }
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
