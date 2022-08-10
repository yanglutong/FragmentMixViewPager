package com.lutong.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import android.os.Build;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;


import com.blankj.utilcode.util.DeviceUtils;
import com.lutong.OrmSqlLite.Bean.NumberBean;
import com.lutong.OrmSqlLite.Bean.SpBean;
import com.lutong.R;
import com.lutong.Retrofit.RetrofitFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static android.content.Context.WIFI_SERVICE;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyUtils {
    //查询次数
    public static void getNumber(String appId) {
        RetrofitFactory.getInstence().API().NUMBER(appId).enqueue(new Callback<NumberBean>() {
            @Override
            public void onResponse(Call<NumberBean> call, Response<NumberBean> response) {
                if (response.body().getData() != null) {
                    ACacheUtil.putNumberMax(response.body().getData().getTotal() + "");//一共查询次数
                    ACacheUtil.putNumberremainder(response.body().getData().getRemainder() + "");
                    Log.d(TAG, "getNumberonResponse: " + "最多" + ACacheUtil.getNumberMax() + "剩余:" + ACacheUtil.getNumberremainder());
                }
            }

            @Override
            public void onFailure(Call<NumberBean> call, Throwable t) {

            }
        });
    }

    private static Context context;
    private static boolean typeAppup = false;//是否强制更新

    //集合转String
    public static String listToString(List<Double> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Double string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static void getPermissions(Activity mainActivity) {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mainActivity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(mainActivity, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    //权限
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    public static List<String> mPermissionList = new ArrayList<>();
    public static String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
    };//申请的权限

    public static List<Double> StringTolist(String str) {
        List<Double> integerList = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            integerList.add(0.0);
            return integerList;
        }
        if (!str.contains(",")) {
            integerList.add(0.0);
            return integerList;
        }
        List list = Arrays.asList(str.split(","));
        for (int i = 0; i < list.size(); i++) {
//            Integer.parseInt((String) list.get(i));
//            integerList.add(Integer.parseInt((String) list.get(i)));
            integerList.add(Double.parseDouble((String) list.get(i)));
        }
        return integerList;
    }

    public static List<Integer> StringToListInteger(String str) {
        List<Integer> integerList = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            integerList.add(0);
            return integerList;
        }
        integerList.add(Integer.parseInt(str));
        return integerList;
    }

    public static String getWifiName(Activity activity) {
        WifiManager my_wifiManager = ((WifiManager) activity.getApplicationContext().getSystemService(WIFI_SERVICE));
        assert my_wifiManager != null;
        WifiInfo wifiInfo = my_wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        int networkId = wifiInfo.getNetworkId();
        @SuppressLint("MissingPermission") List<WifiConfiguration> configuredNetworks = my_wifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.networkId == networkId) {
                ssid = wifiConfiguration.SSID.replace("\"", "");
                Log.d("pppppa", "init: " + ssid);
                return ssid;

            }
        }
        return ssid;
    }

//    public static List<ZmBeanlinshi> removeD(List<ZmBeanlinshi> list) {
//// 从list中索引为0开始往后遍历
//        for (int i = 0; i < list.size() - 1; i++) {
//            // 从list中索引为 list.size()-1 开始往前遍历
//            for (int j = list.size() - 1; j > i; j--) {
//                // 进行比较
//                if (list.get(j).getImsi().equals(list.get(i).getImsi())) {
//                    // 去重
//                    list.remove(j);
//                }
//            }
//        }
//        return list;
//    }

    /**
     * 去重
     *
     * @param list
     * @return
     */
    public static List<States> removeDuplicate(List<States> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getImsi().equals(list.get(i).getImsi())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    /**
     * 去重
     *
     * @param list
     * @return
     */
    public static List<String> removeDuplicateSpBean(List<SpBean> list) {
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getDown().equals(list.get(i).getDown())) {
                    list.remove(j);
                }
            }
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list1.add(list.get(i).getDown());
            }
        }
        return list1;
    }

    public static List<States> removeDuplicatelinshi(List<States> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getImsi().equals(list.get(i).getImsi())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

//    //停止定位
//    public static void stopLocations(Context context, int ip) {
//        if (ip == 1) {
//            new CircleDialog.Builder((FragmentActivity) context)
//                    .setTitle("")
//                    .setText("确定要停止定位1吗?")
//                    .setTitleColor(Color.parseColor("#00acff"))
//                    .setNegative("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            MainUtils.StopLocation(IP1);
//                        }
//                    })
//                    .setPositive("取消", null)
//                    .show();
//        }
//        if (ip == 2) {
//            new CircleDialog.Builder((FragmentActivity) context)
//                    .setTitle("")
//                    .setText("确定要停止定位2吗?")
//                    .setTitleColor(Color.parseColor("#00acff"))
//                    .setNegative("确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            MainUtils.StopLocation(IP2);
//                        }
//                    })
//                    .setPositive("取消", null)
//                    .show();
//        }
//
//    }

    public static String YYname(int i) {
        String name = "移动TDD";
        if (i == 1) {
            name = "移动TDD";
            return name;
        }
        if (i == 2) {
            name = "移动FDD";
            return name;
        }
        if (i == 3) {
            name = "联通";
            return name;
        }
        if (i == 4) {
            name = "电信";
            return name;
        }
        return name;
    }

    public static String PLMN(int i) {
        String plmn = "移动TDD";
        if (i == 1) {
            plmn = "46000";
            return plmn;
        }
        if (i == 2) {
            plmn = "46000";
            return plmn;
        }
        if (i == 3) {
            plmn = "46001";
            return plmn;
        }
        if (i == 4) {
            plmn = "46003";
            return plmn;
        }
        return plmn;
    }

    public static String getZC() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            String uniqueID = UUID.randomUUID().toString();
            String replace = uniqueID.replace(":", "");
            String replace2 = uniqueID.replace("-", "");
            String substring1 = replace2.substring(0, 2);
            String substring2 = replace2.substring(2, 4);
            String substring3 = replace2.substring(4, 6);
            String substring4 = replace2.substring(6, 8);
            String substring5 = replace2.substring(8, 10);
            String substring6 = replace2.substring(10, 12);
            BigInteger bigint1 = new BigInteger(substring1, 16);
            int numb1 = bigint1.intValue();
            BigInteger bigint2 = new BigInteger(substring2, 16);
            int numb2 = bigint2.intValue();
            BigInteger bigint3 = new BigInteger(substring3, 16);
            int numb3 = bigint3.intValue();
            BigInteger bigint4 = new BigInteger(substring4, 16);
            int numb4 = bigint4.intValue();
            BigInteger bigint5 = new BigInteger(substring5, 16);
            int numb5 = bigint5.intValue();
            BigInteger bigint6 = new BigInteger(substring6, 16);
            int numb6 = bigint6.intValue();
            Log.d("10进制", "setUser_pwd: " + numb1 + "--" + numb2 + "--" + numb3 + "--" + numb4 + "--" + numb5 + "--" + numb6);
            int el = numb1 + numb2 + numb3 + numb4 + numb5;//和
            int d1 = (el - numb1) % 10;
            int d2 = (el - numb2) % 10;
            int d3 = (el - numb3) % 10;
            int d4 = (el - numb4) % 10;
            int d5 = (el - numb5) % 10;
            int d6 = (el - numb6) % 10;
            Log.d("10进制加前五位", "setUser_pwd: " + d1 + "" + d2 + "" + d3 + "" + d4 + "" + d5 + "" + d6);
            return String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
        } else {

            @SuppressLint("MissingPermission") String macAddress = DeviceUtils.getMacAddress();//获取mac
            String replace = macAddress.replace(":", "");
            String substring1 = replace.substring(0, 2);
            String substring2 = replace.substring(2, 4);
            String substring3 = replace.substring(4, 6);
            String substring4 = replace.substring(6, 8);
            String substring5 = replace.substring(8, 10);
            String substring6 = replace.substring(10, 12);
            BigInteger bigint1 = new BigInteger(substring1, 16);
            int numb1 = bigint1.intValue();
            BigInteger bigint2 = new BigInteger(substring2, 16);
            int numb2 = bigint2.intValue();
            BigInteger bigint3 = new BigInteger(substring3, 16);
            int numb3 = bigint3.intValue();
            BigInteger bigint4 = new BigInteger(substring4, 16);
            int numb4 = bigint4.intValue();
            BigInteger bigint5 = new BigInteger(substring5, 16);
            int numb5 = bigint5.intValue();
            BigInteger bigint6 = new BigInteger(substring6, 16);
            int numb6 = bigint6.intValue();
            Log.d("10进制", "setUser_pwd: " + numb1 + "--" + numb2 + "--" + numb3 + "--" + numb4 + "--" + numb5 + "--" + numb6);
            int el = numb1 + numb2 + numb3 + numb4 + numb5;//和
            int d1 = (el - numb1) % 10;
            int d2 = (el - numb2) % 10;
            int d3 = (el - numb3) % 10;
            int d4 = (el - numb4) % 10;
            int d5 = (el - numb5) % 10;
            int d6 = (el - numb6) % 10;
            Log.d("10进制加前五位", "setUser_pwd: " + d1 + "" + d2 + "" + d3 + "" + d4 + "" + d5 + "" + d6);
            return String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
        }


//        ed_zc.setText(bjzcm);
    }

    /**
     * 获取当前卡槽数量
     *
     * @param
     * @return
     * @description
     * @author lutong
     * @time 2021/9/26 16:14
     */

    public static int readSimState(Context context) {
        int s = 0;
        SubscriptionManager mSubscriptionManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mSubscriptionManager = SubscriptionManager.from(context);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            } else {
                s = mSubscriptionManager.getActiveSubscriptionInfoCount();//获取当前sim卡数量
//                Log.i("ylt", "onCreate: "+s);
            }

        }
//        Log.e("YangLuTong", "readSimState: "+s);
        return s;
    }

    /**
     * @param
     * @return
     * @description 获取时分秒的总数
     * @author lutong
     * @time 2022/4/13 17:02
     */

    public static int timeM(String date) {
        int ss = 0;
        String[] split = date.split(":");
        if (split.length >= 3) {
            ss += Integer.parseInt(split[0]) * 60 * 60;
            ss += Integer.parseInt(split[1]) * 60;
            ss += Integer.parseInt(split[2]);
        }
        return ss;
    }

    /**
     * @param start   用户现在的时间
     * @param end     首次添加的时间
     * @param setting 用户自定义时间
     * @return
     * @description
     * @author lutong
     * @time 2022/4/13 17:11
     */

    public static boolean isRemove(int start, int end, int setting) {//老化基站就删除
        boolean is = false;
        int cha = start - end;
        if (setting < cha) {
            is = true;//代表基站保留已过时
        }
        return is;
    }

    //单个播放音频文件raw
    public static MediaPlayer initBeepSound(Context context) {
//        if ( mediaPlayer == null) {
        // The volume on STREAM_SYSTEM is not adjustable, and users found it
        // too loud,
        // so we now play on the music stream.

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
// TODO Auto-generated method stub
                mp.seekTo(0);
                mp.start();
            }
        });

        AssetFileDescriptor file = context.getResources().openRawResourceFd(R.raw.pl);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(9.10f, 9.10f);
            mediaPlayer.prepare();
        } catch (IOException e) {
            mediaPlayer = null;
        }
        return mediaPlayer;
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


    public static String getInterListElement(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer integer : list) {
            stringBuilder.append(integer);
        }
        return stringBuilder.toString();
    }
}
