package com.lutong.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import android.telephony.CellIdentityLte;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lutong.OrmSqlLite.Bean.SpBean2;
import com.lutong.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by admin on 2020/4/15.
 */

public class DtUtils {
    public static String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
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
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.RECEIVE_BOOT_COMPLETED
    };
//    Manifest.permission.DEVICE_POWER
    //申请的权限
    public static List<String> mPermissionList = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;

    @SuppressLint({"NewApi", "LongLogTag"})
    public static List<SpBean2> getGsmInfoList(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
       @SuppressLint("MissingPermission") List<CellInfo> cellInfoList = manager.getAllCellInfo();
        Log.d("cellInfoListnzq", "getGsmInfoList: " + cellInfoList);
        List<SpBean2> gsmInfoList = new ArrayList<>();
        if (cellInfoList != null) {
            Log.e("cellInfoList.size=" + cellInfoList.size(), "");
//            GsmInfo gsmInfo;
            SpBean2 bean;
            for (CellInfo info : cellInfoList) {
                Log.e("nzqinfos" + info.toString(), "");
                if (info.toString().contains("CellInfoLte")) {
                    @SuppressLint({"NewApi", "LocalSuppress"}) CellInfoLte cellInfoLte = (CellInfoLte) info;
                    @SuppressLint({"NewApi", "LocalSuppress"}) CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
//                    NeighboringCellInfo neighboringCellInfo=new   NeighboringCellInfo ();
//                    neighboringCellInfo
                    bean = new SpBean2();
//                    cellIdentityLte.
                    String mobileNetworkOperator = cellIdentityLte.getMobileNetworkOperator();
                    Log.d("nzqmobileNetworkOperator", "getGsmInfoList: " + mobileNetworkOperator);
//                    bean.setMcc(cellIdentityLte.getMcc());
                    //上行频点
                    String mobileNetworkOperator1 = cellIdentityLte.getMobileNetworkOperator();
                    String yy = YY(mobileNetworkOperator1);
                    if (yy.equals("移动")) {
                        bean.setUp("255");
                    }
                    if (yy.equals("联通")) {
                        int i = cellIdentityLte.getEarfcn();
                        int i1 = i + 18000;
                        bean.setUp(i1 + "");
                    }
                    if (yy.equals("电信")) {
                        int i = cellIdentityLte.getEarfcn();
                        int i1 = i + 18000;
                        bean.setUp(i1 + "");

                    }
//                    String.valueOf()
                    bean.setTac(cellIdentityLte.getTac()+"");
                    bean.setCid(cellIdentityLte.getCi()+"");
//                    bean.setRssi(cellInfoLte.getCellSignalStrength().getRssi());
                    bean.setRsrp(cellInfoLte.getCellSignalStrength().getRsrp() + "");
                    bean.setRsrq(cellInfoLte.getCellSignalStrength().getRsrq() + "");
                    bean.setDown(cellIdentityLte.getEarfcn() + "");
                    bean.setPci(cellIdentityLte.getPci() + "");
                    bean.setPlmn(cellIdentityLte.getMobileNetworkOperator());
                    bean.setBand(getBand(cellIdentityLte.getEarfcn()));
                    bean.setZw(true);
                    Log.d("beanaa", "getGsmInfoList: ." + bean);
                    gsmInfoList.add(bean);
                } else if (info.toString().contains("CellInfoGsm")) {
//                    CellInfoGsm cellInfoGsm = (CellInfoGsm) info;
//                    CellIdentityGsm cellIdentityGsm = cellInfoGsm.getCellIdentity();
//
//                    gsmInfo = new GsmInfo();
//                    gsmInfo.setMcc(cellIdentityGsm.getMcc());
//                    gsmInfo.setMnc(cellIdentityGsm.getMnc());
//                    gsmInfo.setLac(cellIdentityGsm.getLac());
//                    gsmInfo.setCid(cellIdentityGsm.getCid());
//                    gsmInfo.setEar(cellIdentityGsm.getArfcn());
//                    gsmInfo.setRssi(cellInfoGsm.getCellSignalStrength().getDbm());

//                    gsmInfoList.add(gsmInfo);
                } else if (info.toString().contains("CellInfoCdma")) {
//                    CellInfoCdma cellInfoCdma = (CellInfoCdma) info;
//                    CellIdentityCdma cellIdentityCdma = cellInfoCdma.getCellIdentity();
//
//                    gsmInfo = new GsmInfo();
//                    gsmInfo.setMcc(460);
//                    gsmInfo.setMnc(0);
//                    gsmInfo.setLac(0);
//                    gsmInfo.setCid(cellIdentityCdma.getBasestationId());
//                    gsmInfo.setRssi(cellInfoCdma.getCellSignalStrength().getDbm());
//                    gsmInfo.setRssi(cellInfoCdma.getCellSignalStrength().getCdmaEcio());
//                    gsmInfoList.add(gsmInfo);
                } else if (info.toString().contains("CellInfoNr")) {

                }
            }
        } else {
            Log.e("cellInfoList == null", "");
        }

        return gsmInfoList;
    }

    /**
     * 申请权限
     */
    public static void getPermissions(Context context) {//获取手机权限
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    /**
     * 获取手机IMSI
     */
    public static String getIMSI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            @SuppressLint({"HardwareIds", "MissingPermission"}) String imsi = telephonyManager.getSimSerialNumber();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取设备唯一标识符
     *
     * @return 唯一标识符
     */
//    public static String getDeviceId() {
//        // 通过 SharedPreferences 获取 GUID
//        String guid = SPUtils.getInstance().getString(AppConfig.SP_GUID);
//        if (!TextUtils.isEmpty(guid)) {
//            return guid;
//        }
//
//        // 获取 ANDROID_ID
//        String android_id = Settings.System.getString(
//                App.getApp().getContentResolver(), Settings.Secure.ANDROID_ID);
//        if (!TextUtils.isEmpty(android_id)) {
//            // 通过 ANDROID_ID 生成 guid（唯一标识符）
//            guid = EncryptUtils.encryptMD5ToString(android_id);
//        } else {
//            // 通过 UUID 生成 guid（唯一标识符）
//            guid = EncryptUtils.encryptMD5ToString(UUID.randomUUID().toString());
//        }
//        // 保存 guid 到 SharedPreferences
//        SPUtils.getInstance().put(AppConfig.SP_GUID, guid);
//        return guid;
//    }
    public static String YY(String imsi) {

        String s = "";
        if (!TextUtils.isEmpty(imsi)){

            if (imsi.startsWith("46000")) {//因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                //中国移动
                Log.d("aimsistartsWith", "init: 中国移动");
                s = "移动";
                return s;
            } else if (imsi.startsWith("46001")) {
                //中国联通
                Log.d("aimsistartsWith", "init: 中国联通");
                s = "联通";

            } else if (imsi.startsWith("46003") || (imsi.startsWith("46011"))) {
                //中国电信
                Log.d("aimsistartsWith", "init: 中国电信");
                s = "电信";
            }

        }

        return s;
    }
    public static int YY2(String imsi) {

        int s = 0;
        if (imsi.startsWith("46000")) {//因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
            //中国移动
            Log.d("aimsistartsWith", "init: 中国移动");
            s = 0;
            return s;
        } else if (imsi.startsWith("46001")) {
            //中国联通
            Log.d("aimsistartsWith", "init: 中国联通");
            s = 1;
            return s;
        } else if (imsi.startsWith("46003") || (imsi.startsWith("46011"))) {
            //中国电信
            Log.d("aimsistartsWith", "init: 中国电信");
            s = 11;
            return s;
        }
        return s;
    }


    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacFromFile();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    private static String getMacDefault(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     *
     * @return
     */
    private static String getMacFromFile() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WifiAddress;
    }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     *
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }
    public static String getGsmBand(int Arfcn){
    String string="";
            if(Arfcn>=1&&Arfcn<=94){
        string="GSM 900";
    }
            if(Arfcn>=975&&Arfcn<=1023){
        string="EGSM";
    }
            if(Arfcn>=512&&Arfcn<=561||Arfcn>=587&&Arfcn<=636){
        string="DCS 1800";
    }
            return string;
}
    public static String getBand(int down) {
        String BAND = "1";

        if (down >= 0 && down <= 599) {
            BAND = "1";
            return BAND;
        }

        if (down >= 1200 && down <= 1949) {
            BAND = "3";
            return BAND;
        }

        if (down >= 2400 && down <= 2649) {
            BAND = "5";
            return BAND;
        }


        if (down >= 3450 && down <= 3799) {
            BAND = "8";
            return BAND;
            //FDD
        }

        if (down >= 36200 && down <= 36349) {
            BAND = "34";
            return BAND;
        }
        if (down >= 37750 && down <= 38249) {
            BAND = "38";
            return BAND;
        }
        if (down >= 38250 && down <= 38649) {
            BAND = "39";
            return BAND;
        }
        if (down >= 38650 && down <= 39649) {
            BAND = "40";
            return BAND;
        }
        if (down >= 39650 && down <= 41589) {
            BAND = "41";
            return BAND;
        }
        return BAND;

    }
    public static String getBands(int down) {
        String BAND = "0";
        if (down==1) {
            BAND = "1(FDD)";
            return BAND;
        }

        if (down ==3) {
            BAND = "3(FDD)";
            return BAND;
        }

        if (down ==5) {
            BAND = "5(FDD)";
            return BAND;
        }


        if (down ==8) {
            BAND = "8(FDD)";
            return BAND;
            //FDD
        }

        if (down ==34) {
            BAND = "34(TDD)";
            return BAND;
        }
        if (down ==38) {
            BAND = "38(TDD)";
            return BAND;
        }
        if (down ==39) {
            BAND = "39(TDD)";
            return BAND;
        }
        if (down ==40) {
            BAND = "40(TDD)";
            return BAND;
        }
        if (down ==41) {
            BAND = "41(TDD)";
            return BAND;
        }
        return BAND;
    }
    public static String getECI(String down) {
        String i=down;
        //将数值转为16进制
        String string = JK.hex10To16(Integer.parseInt(down));
        Log.e("yleeet", "将数值转为16进制: "+string);
        if(down.length()>4){
            //将数值 后尾两位拆分

            String substring1 = string.substring(string.length() - 2, string.length());
            Log.e("yleeet", "后尾两位拆分: "+substring1);


            String substring2 = string.substring(0, string.length() - 2);
            Log.e("yleeet", "前段: "+JK.hex16To10(substring2));

            i =down+"("+JK.hex16To10(substring2)+"-"+JK.hex16To10(substring1)+")";
        }
        return i;
    }

        public interface ShowDialog{
            void showDialog(View view, String type);
        }
    public static ShowDialog showDialog(Context context, String text, final ShowDialog showDialog){

        final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
        TextView tv_title = inflate.findViewById(R.id.tv_title);//标题
        tv_title.setText(text);//设置弹出的标题
        Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
        Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
        bt_confirm.setOnClickListener(new View.OnClickListener() {//确定按钮
            @Override
            public void onClick(View view) {
                showDialog.showDialog(view,"确定");
                dialog.dismiss();
                dialog.cancel();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {//确定按钮
            @Override
            public void onClick(View view) {
                showDialog.showDialog(view,"取消");
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

        return showDialog;
    }
    //      用Math进行比较0~10 5是否在此区间

    public static boolean rangeInDefined(int current, int min, int max)
    {
        return Math.max(min, current) == Math.min(current, max);
    }
//    public static int  rangeInDefinedS(int  nrarfcn){
//        int band=0;
//        //判断是什么频点
//        if (rangeInDefined(nrarfcn, 151600, 160600)) {
//            band = 28;
//        }
//        if (rangeInDefined(nrarfcn, 499200, 537999)) {
//            band = 41;
//        }
//        if (rangeInDefined(nrarfcn, 620000, 653333)) {
//            band = 78;
//        }
//        if (rangeInDefined(nrarfcn, 693333, 733333)) {
//            band = 79;
//        }
//        return band;
//    }
public static String rangeInDefinedS(int  nrarfcn){
    String band="";
    //判断是什么频点
    if (rangeInDefined(nrarfcn, 151600, 160600)) {
        band = "28(FDD)";
    }
    if (rangeInDefined(nrarfcn, 499200, 537999)) {
        band = "41(TDD)";
    }
    if (rangeInDefined(nrarfcn, 620000, 653333)) {
        band = "78(TDD)";
    }
    if (rangeInDefined(nrarfcn, 693333, 733333)) {
        band = "79(TDD)";
    }
    return band;
}
}
