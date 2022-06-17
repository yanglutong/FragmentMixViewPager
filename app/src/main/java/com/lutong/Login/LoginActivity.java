package com.lutong.Login;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.AppUtils;
import com.lutong.OrmSqlLite.Bean.AdminBean;
import com.lutong.OrmSqlLite.Bean.LogBean;
import com.lutong.OrmSqlLite.Bean.ZcBean;
import com.lutong.OrmSqlLite.DBManagerAdmin;
import com.lutong.OrmSqlLite.DBManagerLog;
import com.lutong.OrmSqlLite.DBManagerZX;
import com.lutong.R;
import com.lutong.Utils.GPS.GpsUtil;
import com.lutong.Utils.GPSLocationUtil;
import com.lutong.Utils.LoginUtils.LoginUtils;
import com.lutong.Utils.ToastUtils;
import com.lutong.activity.ConfigsActivity;
import com.lutong.base.BaseActivity;
import com.lutong.ui.MainActivity2;
import com.lutong.AppContext;
import com.pedaily.yc.ycdialoglib.dialog.loading.ViewLoading;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_user, et_pwd;
    private ImageView iv_show;
    private Button bt_login;
    private boolean showaBoolean = false;
    private TextView tv_version;
    private String sb1 = "";
    boolean dateFlag = false;
    GPSLocationUtil gUtil;
    LocationManager locationManager;
    Location location;
    private TextView tv_oldDate;
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 100120: //设备1当前状态的判断
//                    String zt1 = msg.getData().getString("zt1");
//                    if (!TextUtils.isEmpty(zt1)) {
//                        if (zt1.equals("0")) {
////                        tv1_type.setText("当前状态: " + "连接中...");
//
//                            sb1 = "连接中...";
//                            break;
//                        }
//                        if (zt1.equals("1")) {
//
//                            sb1 = "连接失败";
//
//                            break;
//                        }
//                        if (zt1.equals("2")) {
//                            //
////                            设备1状态
//                            sb1 = "就绪";
////
//                            System.out.println("就绪");
//                            if (dateFlag == false) {
//                                getDate(IP1);
//                                System.out.println("发送了");
//                                dateFlag = true;
//                            }
//                            break;
//                        }
//                        if (zt1.equals("3")) {
////                            tv1_type.setText("当前状态: " + "扫频同步进行中");//闲置状态
//                            sb1 = "扫频同步进行中";
//                            break;
//                        }
//                        if (zt1.equals("4")) {
////                            tv1_type.setText("当前状态: " + "小区激活中");//闲置状态
//                            sb1 = "小区激活中";
//                            break;
//                        }
//                        if (zt1.equals("5")) {
//                            sb1 = "管控中";
//                            break;
//                        }
//                    }
//            }
//        }
//    };

    @SuppressLint("NewApi")
    @Override
    protected void initQx() {
        getPermissions();

    }

    @Override
    protected void init() {
//        MyUtils.getPermissions(this);
//        getPermissions();
//        setUser_pwd();
        gUtil = GPSLocationUtil.getInstance(LoginActivity.this);
////        ifWifi();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        updateView(location);
//        String time = getTime(location);
        try {
            DBManagerZX dbManagerZX = new DBManagerZX(LoginActivity.this);
            ZcBean forid = dbManagerZX.forid(1);
            if (!TextUtils.isEmpty(forid.getDate())) {
                tv_version.setText("有效期至:" + forid.getDate());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String lastdate = forid.getDate();//到期时间
                ZcBean forid2 = dbManagerZX.forid(2);
                String nowDate = forid2.getDate();

                if (TextUtils.isEmpty(nowDate)) {
                    ToastUtils.showToast("请先注册激活");
                    return;
                }
                String date1 = forid.getDate();
//                String date1="2021-11-28";

                Date d1 = sdf.parse(date1);//到期的时间
                String format = sdf.format(new Date());//当前的时间
                Date d2 = sdf.parse(format);

                long daysBetween = (d1.getTime() - d2.getTime() + 1000000) / (60 * 60 * 24 * 1000);

                System.out.println("计算" + date1 + "---" + format + "--    " + daysBetween + " 天");
                if (daysBetween > 1) {
                    tv_oldDate.setText("剩余天数:" + daysBetween + "天");
                } else {
                    tv_oldDate.setText("已过期:" + daysBetween + "天");
                }


//                String nowDateS = simpleDateFormat.format(new Date());//系统当前时间
//
//                       变化吧好不好

            } else {
                tv_version.setText("有效期至:");
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateView(location);
//                String time = getTime(location);
//                Log.d("nzqonLocationChanged", "onLocationChanged: " + time);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });


//        JSONObject createJSONObject = null;
//        try {
//            createJSONObject = createJSONObject();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println("测试json"+createJSONObject);
//        System.out.println("测试jsonTostring"+createJSONObject.toString());

    }

    private void openGPS() {
        new AlertDialog.Builder(LoginActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("请开启GPS")
                .setMessage("去开启")
                .setNegativeButton("取消", null)
                .setPositiveButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 887);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 887:
                //开启GPS，重新添加地理监听
//                startLocation();
                String time = getTime(location);
                ToastUtils.showToast(time);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateView(Location location) {
        if (location != null) {
            Log.d("TAGlocation", "updateView: " + "非空");
            StringBuffer stringBuffer = new StringBuffer();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                stringBuffer.append(simpleDateFormat.format(new Date(location.getTime())));
                stringBuffer.append("\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("TAGlocation", "updateView: " + "非空" + stringBuffer.toString());
//            tv_version.setText(stringBuffer.toString());
        } else {
            Log.d("TAGlocation", "updateView: " + "空");
        }
    }

    //获取定位时间
    private String getTime(Location location) {
        if (location != null) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                stringBuffer.append(simpleDateFormat.format(new Date(location.getTime())));
//                stringBuffer.append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuffer.toString();
        } else {
            return "";
        }
//        return "";
    }


    @Override
    protected void findViews() {
        et_user = findViewById(R.id.et_user);
        et_pwd = findViewById(R.id.et_pwd);
        iv_show = findViewById(R.id.iv_show);
        iv_show.setOnClickListener(this);
        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        tv_version = findViewById(R.id.tv_version);
        tv_oldDate = findViewById(R.id.tv_oldDate);
//        et_user.setText("smsbgly");
//        et_pwd.setText("smsbgly");
//                et_user.setText("useradmin");
//        et_pwd.setText("useradmin");
//        et_user.setText("smsbgly");
//        et_pwd.setText("smsbgly");
    }

    @Override
    protected int getView() {
        return R.layout.activity_login;
    }

    private void setUser_pwd() {
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String namesp = userSettings.getString("name", "");
        String pswsp = userSettings.getString("pwd", "");
//        et_user.setText(namesp);
//        et_pwd.setText(pswsp);
        String appVersionName = AppUtils.getAppVersionName();
//        tv_version.setText("测试版版本:" + appVersionName + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
//                Toast.makeText(LoginActivity.this, "你点击了展示密码功能", Toast.LENGTH_LONG).show();
                if (showaBoolean == false) {
                    showaBoolean = true;
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Log.e("showaBoolean", "onClick: " + showaBoolean);
                } else if (showaBoolean == true) {
                    showaBoolean = false;
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Log.e("showaBoolean", "onClick: " + showaBoolean);
                }

                break;
            case R.id.bt_login:
//
                Logins();
//
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void Logins() {
//        KeyboardUtils.hideSoftInput(LoginActivity.this);
        final String number = et_user.getText().toString();
        final String pwd = et_pwd.getText().toString();
//        et_user.setText("useradmin");
//        et_pwd.setText("useradmin");
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd)) {
            if (number.equals("smsbgly") && pwd.equals("smsbgly")) {//权限管理
                startActivity(new Intent(LoginActivity.this, ConfigsActivity.class));
//                finish();
            } else if (number.equals("useradmin") && pwd.equals("useradmin")) {//账号管理
//                LoadingTrue("正在登陆");

                //输入注册码第一次
                final SharedPreferences preferences = AppContext.context.getSharedPreferences(
                        "SHARE_APP_TAGZC", 0);
                Boolean isFirst = preferences.getBoolean("FIRSTStartzc", true);
                if (isFirst) {//第一次
                    final Dialog dialog = new Dialog(LoginActivity.this, R.style.menuDialogStyleDialogStyle);
                    View inflate = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_item_title_zc, null);
                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                    tv_title.setText("验证注册码");
                    final EditText et_jh = inflate.findViewById(R.id.et_jh);

                    Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String zhucema = "";
                            try {
                                DBManagerZX dbManagerZX = new DBManagerZX(LoginActivity.this);
                                ZcBean forid = dbManagerZX.forid(1);
                                zhucema = forid.getZhucema();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            if (et_jh.getText().toString().equals(zhucema)) {
                                preferences.edit().putBoolean("FIRSTStartzc", false).commit();

                                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                                SharedPreferences.Editor editor = userSettings.edit();
                                editor.putString("name", number);
                                editor.putString("pwd", pwd);
                                editor.commit();
                                startActivity(new Intent(LoginActivity.this, Adminctivity.class));
                                LoadingFalse();

                                dialog.dismiss();
                                dialog.cancel();

                            } else {
                                ToastUtils.showToast("注册码错误");
                            }

                        }
                    });
                    Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
                    bt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            dialog.cancel();
                        }
                    });

                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentView(inflate);
                    //获取当前Activity所在的窗体
                    Window dialogWindow = dialog.getWindow();
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity(Gravity.CENTER);
                    //获得窗体的属性
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//设置Dialog距离底部的距离
//                          将属性设置给窗体
                    dialogWindow.setAttributes(lp);
                    dialog.show();//显示对话框
                } else {
                    SharedPreferences userSettings = getSharedPreferences("setting", 0);
                    SharedPreferences.Editor editor = userSettings.edit();
                    editor.putString("name", number);
                    editor.putString("pwd", pwd);
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, Adminctivity.class));
                    LoadingFalse();
                }


//                finish();
            } else {//子账号登陆
                LoadingFalse();
                if (GpsUtil.isOPen(LoginActivity.this)) {//GPS是否打开
                    try {
                        DBManagerAdmin dbManagerAdmin = new DBManagerAdmin(LoginActivity.this);
                        List<AdminBean> adminBeans = dbManagerAdmin.foridName(number);
                        if (adminBeans.size() > 0) {
                            AdminBean adminBean = adminBeans.get(0);
                            Log.d("nzqTAG", "Logins: " + adminBean);
                            if (adminBean.getPwd().equals(pwd)) {//用户 密码一致
                                if (adminBean.getType() == 1) {
//                                LoadingTrue("正在登陆");
//
                                    //到期时间

                                    DBManagerZX zx = new DBManagerZX(LoginActivity.this);
                                    ZcBean forid = zx.forid(1);
                                    if (!TextUtils.isEmpty(forid.getDate())) {
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        String lastdate = forid.getDate();//到期时间
                                        ZcBean forid2 = zx.forid(2);
                                        String nowDate = forid2.getDate();
                                        if (TextUtils.isEmpty(nowDate)) {
                                            ToastUtils.showToast("请先注册激活");
                                            return;
                                        }
                                        String nowDateS = simpleDateFormat.format(new Date());//系统当前时间
                                        Date date1 = null;
                                        Date date2 = null;
                                        Date date3 = null;
                                        try {
                                            date1 = simpleDateFormat.parse(lastdate);   // 到期的时间
                                            date2 = simpleDateFormat.parse(nowDate);    //设定的日期
                                            date3 = simpleDateFormat.parse(nowDateS);
//                                            int days = (int) ((date3.getTime() - date2.getTime()) / (1000*3600*24));
//                                            Log.d("TAG相差天数1", "Logins: "+days);
                                        } catch (Exception e) {

                                        }
                                        if (date1.getTime() >= date2.getTime() && date1.getTime() >= date3.getTime()) {
                                            int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
                                            Log.d("TAG相差天数2", "Logins: " + days);
                                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                            if (!TextUtils.isEmpty(getTime(location))) {

                                                String time = getTime(location);
                                                try {
                                                    date2 = simpleDateFormat.parse(time);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                if (date1.getTime() >= date2.getTime()) {
                                                    ZcBean zcBean = new ZcBean();
                                                    zcBean.setId(2);
                                                    zcBean.setDate(time);
                                                    zx.updateConmmunit01Bean(zcBean);

//                                                    ToastUtils.showToast("GPS有效日期");
                                                    SharedPreferences userSettings = getSharedPreferences("setting", 0);
                                                    SharedPreferences.Editor editor = userSettings.edit();
                                                    editor.putString("name", number);
                                                    editor.putString("pwd", pwd);
                                                    int id = adminBean.getId();
                                                    editor.putString("id", id + "");
                                                    Log.e("myid", "Logins: " + adminBean.getId());
                                                    editor.commit();

                                                    //登录日志
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                    DBManagerLog dbManagerLog = new DBManagerLog(LoginActivity.this);
                                                    LogBean logBean = new LogBean();
                                                    logBean.setAssociated(id + "");//关联ID
                                                    logBean.setEvent(LoginUtils.setBase64("登录"));//登录事件
                                                    String format = sdf.format(new Date());//登录时间
                                                    logBean.setDatetime(LoginUtils.setBase64(format));
                                                    dbManagerLog.insertConmmunit01Bean(logBean);
                                                    //   默认 4G+5G 定位
//                                                    startActivity(new Intent(LoginActivity.this, MainActivity2.class));
//                                                    //救援
////                                                    startActivity(new Intent(LoginActivity.this, SOSActivity.class));
//
//                                                    finish();
//                                                    ToastUtils.showToast("11");
                                                    ViewLoading.show(LoginActivity.this);
                                                    TimerTask task = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            /**
                                                             *要执行的操作
                                                             */
//                                                            ViewLoading.dismiss(LoginActivity.this);
                                                            startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                                                            finish();
                                                        }
                                                    };
                                                    Timer timer = new Timer();
                                                    timer.schedule(task, 3000);//3秒后执行TimeTask的run方法
                                                } else {
                                                    ToastUtils.showToast("已过有效期");
                                                }
                                            } else {
                                                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                                                SharedPreferences.Editor editor = userSettings.edit();
                                                editor.putString("name", number);
                                                editor.putString("pwd", pwd);
                                                int id = adminBean.getId();
                                                editor.putString("id", id + "");
                                                editor.commit();
                                                //登录日志
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                DBManagerLog dbManagerLog = new DBManagerLog(LoginActivity.this);
                                                LogBean logBean = new LogBean();

                                                logBean.setAssociated(id + "");//关联ID
                                                logBean.setEvent(LoginUtils.setBase64("登录"));//登录事件
                                                String format = sdf.format(new Date());//登录时间
                                                logBean.setDatetime(LoginUtils.setBase64(format));
                                                dbManagerLog.insertConmmunit01Bean(logBean);
                                                //   默认 4G+5G 定位
//                                                startActivity(new Intent(LoginActivity.this, MainActivity2.class));
////                                                救援
////                                                startActivity(new Intent(LoginActivity.this, SOSActivity.class));
//
//                                                finish();
                                                ViewLoading.show(LoginActivity.this);
                                                TimerTask task = new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        /**
                                                         *要执行的操作
                                                         */
                                                        startActivity(new Intent(LoginActivity.this, MainActivity2.class));
                                                        finish();
                                                    }
                                                };
                                                Timer timer = new Timer();
                                                timer.schedule(task, 3000);//3秒后执行TimeTask的run方法
                                            }


//                                            ToastUtils.showToast("有效日期");
                                        } else {
                                            ToastUtils.showToast("已过有效期");
                                        }

                                    } else {

                                        ToastUtils.showToast("请先注册激活");
                                    }

                                } else {
                                    ToastUtils.showToast("账户未启用");
//                                LoadingTrue("正在登陆");
                                    SharedPreferences userSettings = getSharedPreferences("setting", 0);
                                    SharedPreferences.Editor editor = userSettings.edit();
                                    editor.putString("name", number);
                                    editor.putString("pwd", pwd);
                                    editor.commit();
                                }
                            } else {
                                ToastUtils.showToast("用户名或密码错误");
//                            LoadingTrue("正在登陆");
                                SharedPreferences userSettings = getSharedPreferences("setting", 0);
                                SharedPreferences.Editor editor = userSettings.edit();
                                editor.putString("name", number);
                                editor.putString("pwd", pwd);
                                editor.commit();
                            }
                        } else {
                            ToastUtils.showToast("用户不存在");
//                        LoadingTrue("正在登陆");
                            SharedPreferences userSettings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = userSettings.edit();
                            editor.putString("name", number);
                            editor.putString("pwd", pwd);
                            editor.commit();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("请先开启GPS");
                }
            }
//
        } else {//账号名密码不能为空

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}
