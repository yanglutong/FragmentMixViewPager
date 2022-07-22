package com.lutong.fragment;

import static com.lutong.Constant.Constant.IP1;
import static com.lutong.Constant.Constant.IP2;
import static com.lutong.Utils.MainUtils.location;
import static com.lutong.Utils.MainUtils2.toIMSI;
import static com.lutong.fragment.ChildFragment3.TAG;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.Bean.Conmmunit01Bean;
import com.lutong.OrmSqlLite.Bean.PararBean;
import com.lutong.OrmSqlLite.Bean.PinConfigBean;
import com.lutong.OrmSqlLite.DBManager01;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.OrmSqlLite.DBManagerPinConfig;
import com.lutong.R;
import com.lutong.Utils.MainUtils;
import com.lutong.Utils.MainUtils2;
import com.lutong.Utils.MainUtilsThread;
import com.lutong.Utils.ToastUtils;
import com.lutong.Utils.setxq;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.lutong.Constant.Constant.DOWNPIN1;
import static com.lutong.Constant.Constant.DOWNPIN2;

import androidx.annotation.NonNull;

public class NewPersentDW implements NewViewDW.MainPresenter {
    public NewViewDW.View viewS;

    public NewPersentDW(@NonNull NewViewDW.View view) {
        this.viewS = view;
        view.setPresenter(this);
    }

    public void setlaba(Context context, ImageView imageView, int device, boolean laba) {
        if (device == 1) {
            if (laba) {
                laba = false;
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.laba_gray));
                viewS.labaup(device, laba);
            } else {
                laba = true;
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.laba_green));
                viewS.labaup(device, laba);
            }
        }
        if (device == 2) {
            if (laba) {
                laba = false;
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.laba_gray));
                viewS.labaup(device, laba);
            } else {
                laba = true;
                imageView.setBackground(context.getResources().getDrawable(R.mipmap.laba_red));
                viewS.labaup(device, laba);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void setIMSInengliangzhi(String sb1jl, Context context, int device, String tf, DecimalFormat dfBaoshu, TextView sb1_jl, boolean laba, TextToSpeech textToSpeech) {
        if (device == 1) {

//        Log.d(TAG, "sb1jlhandleMessage: " + sb1jl + "---");
            if (!TextUtils.isEmpty(sb1jl)) {
                String format = "";
                if (TextUtils.isEmpty(tf)) {
                    return;
                }
                if (tf.equals("TDD")) {
                    double v = Double.parseDouble(sb1jl);
                    double s = v / 110 * 1000;
                    format = dfBaoshu.format(s);
//                Log.d(TAG, "dfBaoshuhandleMessage: " + format);
                } else {
                    double v = Double.parseDouble(sb1jl);
                    double s = v / 164 * 1000;
                    format = dfBaoshu.format(s);
//                Log.d(TAG, "dfBaoshuhandleMessage: " + format);
                }


                sb1_jl.setText(format);
                if (laba == true) {
                    startAuto(format, true, textToSpeech);
                }
                viewS.quxian(format, 1);

            }
        }
        if (device == 2) {

//        Log.d(TAG, "sb1jlhandleMessage: " + sb1jl + "---");
            if (!TextUtils.isEmpty(sb1jl)) {
                String format = "";
                if (TextUtils.isEmpty(tf)) {
                    return;
                }
                if (tf.equals("TDD")) {
//                    double v = Double.parseDouble(sb1jl);
//                    double s = v / 110 * 1000;
//                    format = dfBaoshu.format(s);
//                Log.d(TAG, "dfBaoshuhandleMessage: " + format);
                } else {
//                    double v = Double.parseDouble(sb1jl);
//                    double s = v / 164 * 1000;
//                    format = dfBaoshu.format(s);
//                Log.d(TAG, "dfBaoshuhandleMessage: " + format);
                }


                sb1_jl.setText("1000");
//                if (laba == true) {
                    startAuto("1000", true, textToSpeech);
//                }
                viewS.quxian("1000", 2);

            }
        }
    }

    @Override
    public void stopdw(final int i, Context context, boolean b, Button btstart) {
        if (!b){
            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            String ip = IP1;
            if (i == 1) {
                tv_title.setText("确定要停止定位吗?");
                ip = IP1;
            }
            if (i == 2) {
                tv_title.setText("确定要停止定位吗?");
                ip = IP2;
            }
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            final String finalIp = ip;
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainUtils.StopLocation(IP1);
                    MainUtils.StopLocation(IP2);
//                GFFLAG1 = 2;
//                MainUtils.OpenGF1(1, 2, handler);
                    viewS.stopdwup(i);
                    viewS.stopdwup(i);
                    dialog.dismiss();
                    dialog.cancel();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                DBManagerLog dbManagerLog = null;
                    String string = "";

                    string = "停止定位";

//                //退出日志
//                try {
//                    dbManagerLog = new DBManagerLog(context);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                LogBean logBean = new LogBean();
//                logBean.setAssociated(LoginUtils.getId(context) + "");//关联ID
//                logBean.setEvent(LoginUtils.setBase64(string));//登录事件
//                logBean.setSb(LoginUtils.setBase64("1"));
//                String format = sdf.format(new Date());//登录时间
//                logBean.setDatetime(LoginUtils.setBase64(format));
//                try {
//                    dbManagerLog.insertConmmunit01Bean(logBean);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
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
        }else {

            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            String ip = IP1;
            if (i == 1) {
                tv_title.setText("确定要终止定位环节吗?");
                ip = IP1;
            }
            if (i == 2) {
                tv_title.setText("确定要停止定位吗?");
                ip = IP2;
            }
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            final String finalIp = ip;
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    MainUtils.StopLocation(IP1);
//                    MainUtils.StopLocation(IP2);
////                GFFLAG1 = 2;
////                MainUtils.OpenGF1(1, 2, handler);
//                    viewS.stopdwup(i);
//                    viewS.stopdwup(i);
                    dialog.dismiss();
                    dialog.cancel();
//
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                DBManagerLog dbManagerLog = null;
//                    String string = "";
//
//                    string = "停止定位";
                       viewS.bt_startFalg(false);
//                //退出日志
//                try {
//                    dbManagerLog = new DBManagerLog(context);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                LogBean logBean = new LogBean();
//                logBean.setAssociated(LoginUtils.getId(context) + "");//关联ID
//                logBean.setEvent(LoginUtils.setBase64(string));//登录事件
//                logBean.setSb(LoginUtils.setBase64("1"));
//                String format = sdf.format(new Date());//登录时间
//                logBean.setDatetime(LoginUtils.setBase64(format));
//                try {
//                    dbManagerLog.insertConmmunit01Bean(logBean);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
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



        }

    }

    @Override
    public void setGKtart(final Context context, final String tf1, final String tf2, final String sb1, final String sb2, int GuankongType) {
        if (GuankongType == 1) {//运营商1
            if (TextUtils.isEmpty(tf1)) {
                ToastUtils.showToast("设备1未连接");
                return;
            }
            if (TextUtils.isEmpty(tf2)) {
                ToastUtils.showToast("设备2未连接");
                return;
            }

            if (!sb1.equals("就绪")) {
                ToastUtils.showToast("设备1不在就绪状态");
                return;
            }
            if (!sb2.equals("就绪")) {
                ToastUtils.showToast("设备2不在就绪状态");
                return;
            }
            Log.d("setGKtart", "setGKtart: ");
            //---------------------------------确定要启动侦码轮循
            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            tv_title.setText("启动通讯管控吗?");
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("setGKtart", "setGKtart:启动通讯管控吗? ");
                    try {
                        DBManagerAddParam dbManagerAddParamA = new DBManagerAddParam(context);
                        List<AddPararBean> dataAlla = dbManagerAddParamA.getDataAll();
                        List<AddPararBean> listImsiListTrue = new ArrayList<>();
                        Log.d("setGKtartaddPararBeans", "白名单所有--init: " + dataAlla.toString());

                        if (dataAlla.size() > 0) {
                            for (int i = 0; i < dataAlla.size(); i++) {
                                if (dataAlla.get(i).isCheckbox() == true) {
                                    dataAlla.get(i).setSb("");
                                    listImsiListTrue.add(dataAlla.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {
                                    list1size.add(i);
                                }

                                List<AddPararBean> sendwhitelist = new ArrayList<>();
                                for (int j = 0; j < listImsiListTrue.size(); j++) {
                                    if (listImsiListTrue.get(j).getYy().equals("移动")) {
                                        sendwhitelist.add(listImsiListTrue.get(j));
                                    }
                                }
                                Log.d("+sendwhitelist", "onClick: " + sendwhitelist);
                                if (sendwhitelist.size() > 0) {
                                    if (!tf1.equals("TDD")) {
                                        ToastUtils.showToast("设备1当前制式为FDD");
                                        viewS.gkqiehuan(tf1, 1);
//                return;
                                    }
                                    if (!tf2.equals("FDD")) {
                                        ToastUtils.showToast("设备2当前制式为TDD");
                                        viewS.gkqiehuan(tf2, 2);
//                return;
                                    }
//                                    if (!sb1.equals("就绪")) {
////                ToastUtils.showToast("设备1不在就绪状态");
//                                        return;
//                                    }
//                                    if (!sb2.equals("就绪")) {
////                ToastUtils.showToast("设备2不在就绪状态");
//                                        return;
//                                    }
                                    sendwhiteListRun(sendwhitelist, tf1, tf2, context, listImsiListTrue);//发送白名单  2个

                                } else {
                                    if (!tf1.equals("TDD")) {
                                        ToastUtils.showToast("设备1当前制式为FDD");
                                        viewS.gkqiehuan(tf1, 1);
//                return;
                                    }
                                    if (!tf2.equals("FDD")) {
                                        ToastUtils.showToast("设备2当前制式为TDD");
                                        viewS.gkqiehuan(tf2, 2);
//                return;
                                    }
//                                    if (!sb1.equals("就绪")) {
////                ToastUtils.showToast("设备1不在就绪状态");
//                                        return;
//                                    }
//                                    if (!sb2.equals("就绪")) {
////                ToastUtils.showToast("设备2不在就绪状态");
//                                        return;
//                                    }
                                    sendwhiteListRunDELE(sendwhitelist, tf1, tf2, context, listImsiListTrue);//发送白名单  2个

                                }


                            } else {
//                                ToastUtils.showToast("没有选中的非控IMSI");

                                List<AddPararBean> sendwhitelist = new ArrayList<>();
                                sendwhiteListRunDELE(sendwhitelist, tf1, tf2, context, listImsiListTrue);//发送白名单  2个

                            }
                        } else {//白名单为空
                            if (tf1.equals("TDD") && tf2.equals("FDD")) {
                                List<AddPararBean> sendwhitelist = new ArrayList<>();
                                sendwhiteListRunDELE(sendwhitelist, tf1, tf2, context, listImsiListTrue);//发送白名单  2个
                            }
                            if (!tf1.equals("TDD")) {
                                ToastUtils.showToast("设备1当前制式为FDD");
                                viewS.gkqiehuan(tf1, 1);
//                return;
                            } else {

                            }
                            if (!tf2.equals("FDD")) {
                                ToastUtils.showToast("设备2当前制式为TDD");
                                viewS.gkqiehuan(tf2, 2);
//                return;
                            } else {

                            }
                        }
                        Log.d("setGKtartaddPararBeans", "白名单init: " + listImsiListTrue.toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    dialog.cancel();
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

        }
        if (GuankongType == 2) {//运营商1
            if (TextUtils.isEmpty(tf1)) {
                ToastUtils.showToast("设备1未连接");
                return;
            }
            if (TextUtils.isEmpty(tf2)) {
                ToastUtils.showToast("设备2未连接");
                return;
            }

            if (!sb1.equals("就绪")) {
                ToastUtils.showToast("设备1不在就绪状态");
                return;
            }
            if (!sb2.equals("就绪")) {
                ToastUtils.showToast("设备2不在就绪状态");
                return;
            }
            Log.d("setGKtart", "setGKtart: ");
            //---------------------------------确定要启动侦码轮循
            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            tv_title.setText("启动通讯管控吗?");
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("setGKtart", "setGKtart:启动移动管控吗 ");
                    try {
                        DBManagerAddParam dbManagerAddParamA = new DBManagerAddParam(context);
                        List<AddPararBean> dataAlla = dbManagerAddParamA.getDataAll();
                        List<AddPararBean> listImsiListTrue = new ArrayList<>();
                        Log.d("setGKtartaddPararBeans", "白名单所有--init: " + dataAlla.toString());

                        if (dataAlla.size() > 0) {
                            for (int i = 0; i < dataAlla.size(); i++) {
                                if (dataAlla.get(i).isCheckbox() == true) {
                                    dataAlla.get(i).setSb("");
                                    listImsiListTrue.add(dataAlla.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {
                                    list1size.add(i);
                                }

                                List<AddPararBean> sendwhitelistLT = new ArrayList<>();
                                for (int j = 0; j < listImsiListTrue.size(); j++) {
                                    if (listImsiListTrue.get(j).getYy().equals("联通")) {
                                        sendwhitelistLT.add(listImsiListTrue.get(j));
                                    }
                                }
                                List<AddPararBean> sendwhitelistDX = new ArrayList<>();
                                for (int j = 0; j < listImsiListTrue.size(); j++) {
                                    if (listImsiListTrue.get(j).getYy().equals("电信")) {
                                        sendwhitelistDX.add(listImsiListTrue.get(j));
                                    }
                                }
                                if (!tf1.equals("FDD")) {
                                    ToastUtils.showToast("设备1当前制式为TDD");
                                    viewS.gkqiehuan(tf1, 1);
//                                    return;
                                }
                                if (!tf2.equals("FDD")) {
                                    ToastUtils.showToast("设备2当前制式为TDD");
                                    viewS.gkqiehuan(tf2, 2);
//                                    return;
                                }
                                if (sendwhitelistLT.size() > 0) {
                                    sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//发送白名单  2个
                                } else {
                                    sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//发送白名单  2个
                                }
                                if (sendwhitelistDX.size() > 0) {
                                    sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//发送白名单  2个
                                } else {
                                    sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//发送白名单  2个

                                }


                            } else {
//                                ToastUtils.showToast("没有选中的非控IMSI");
                                List<AddPararBean> sendwhitelistLT = new ArrayList<>();
                                List<AddPararBean> sendwhitelistDX = new ArrayList<>();
//                                ToastUtils.showToast("111");

                                sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//发送白名单  2个


                                sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//发送白名单  2个


                            }
                        } else {
                            List<AddPararBean> sendwhitelistLT = new ArrayList<>();
                            List<AddPararBean> sendwhitelistDX = new ArrayList<>();
                            if (!tf1.equals("FDD")) {
                                ToastUtils.showToast("设备1当前制式为TDD");
                                viewS.gkqiehuan(tf1, 1);
//                                    return;
                            } else {
                                sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//发送白名单  2个
                            }
                            if (!tf2.equals("FDD")) {
                                ToastUtils.showToast("设备2当前制式为TDD");
                                viewS.gkqiehuan(tf2, 2);
//                                    return;
                            } else {
                                sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//发送白名单  2个
                            }
                        }
                        Log.d("setGKtartaddPararBeans", "白名单init: " + listImsiListTrue.toString());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    dialog.dismiss();
                    dialog.cancel();
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

        }
    }

    //发送黑名单
    private void sendwhiteListRunDELE(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {

        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
//        for (int i = 0; i < sendListBlack.size(); i++) {
//            list.add(sendListBlack.get(i).getImsi());
//        }
        Log.d("aaaaalist", "sendwhiteListRun: " + list);
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //消息头
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff00");

//        //黑名单数量
//        String s = "";
//        if (totalMy == 10) {
//            s = "10";
//        } else {
//            s = "0" + String.valueOf(totalMy);
//        }
        str.append("01");
        str.append("00");
//        str.append(MainUtils2.StringPin(MainUtils2.blacklistCount(Integer.toString(totalMy, 16))));
        Log.d("aaTAG", "sendwhiteListRun: " + str.toString());
//        str.append("00");

        ///第一个写15个 1

        str.append("3131313131313131313131313131310000");
//        List<String> list2 = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            if (!list.get(i).equals("") && list.get(i) != null) {
//                StringBuffer imsi = toIMSI(list.get(i));
//                str.append(imsi).append("0000");
//                list2.add(list.get(i));
//            }
//        }
//        for (int i = 0; i < list2.size(); i++) {
//            PararBean pararBean = new PararBean();
//            pararBean.setImsi(list2.get(i));
//        }
        for (int i = 0; i < 10 - 1; i++) {
            str.append("0000000000000000000000000000000000");
        }
        Log.d("uuew", "sendwhiteListRun: " + str.toString());
        str.append("03ff00");
        StringBuffer str2 = new StringBuffer("aa aa 55 55 39 f0 b9 00 00 00 00 ff 00 01 00 " +
                "31 31 31 31 31 31 31 31 31 31 31 31 31 31 31 00 00" +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 " +
                "03 ff 00");
        if (!TextUtils.isEmpty(str)) {
            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送
            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送

        }
    }

    private void sendwhiteListRunDX(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {

        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        Log.d("aaaaalist", "sendwhiteListRun: " + list);
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //消息头
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff01");
        //黑名单数量
        String s = "";
        if (totalMy == 10) {
            s = "10";
        } else {
            s = "0" + String.valueOf(totalMy);
        }
        str.append(s);
//        str.append(MainUtils2.StringPin(MainUtils2.blacklistCount(Integer.toString(totalMy, 16))));
        Log.d("aaTAG", "sendwhiteListRun: " + str.toString());
        str.append("00");
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("") && list.get(i) != null) {
                StringBuffer imsi = toIMSI(list.get(i));
                str.append(imsi).append("0000");
                list2.add(list.get(i));
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            PararBean pararBean = new PararBean();
            pararBean.setImsi(list2.get(i));
        }
        for (int i = 0; i < 10 - list2.size(); i++) {
            str.append("0000000000000000000000000000000000");
        }
        Log.d("uuew", "sendwhiteListRun: " + str.toString());
        str.append("03ff00");
        if (!TextUtils.isEmpty(str)) {
//            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送
            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送

        }
    }

    //发送黑名单
    private void sendwhiteListRun(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {

        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        Log.d("aaaaalist", "sendwhiteListRun: " + list);
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //消息头
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff01");

        //黑名单数量
        String s = "";
        if (totalMy == 10) {
            s = "10";
        } else {
            s = "0" + String.valueOf(totalMy);
        }
        str.append(s);
//        str.append(MainUtils2.StringPin(MainUtils2.blacklistCount(Integer.toString(totalMy, 16))));
        Log.d("aaTAG", "sendwhiteListRun: " + str.toString());
        str.append("00");
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("") && list.get(i) != null) {
                StringBuffer imsi = toIMSI(list.get(i));
                str.append(imsi).append("0000");
                list2.add(list.get(i));
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            PararBean pararBean = new PararBean();
            pararBean.setImsi(list2.get(i));
        }
        for (int i = 0; i < 10 - list2.size(); i++) {
            str.append("0000000000000000000000000000000000");
        }
        Log.d("uuew", "sendwhiteListRun: " + str.toString());
        str.append("03ff00");
        if (!TextUtils.isEmpty(str)) {
            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送
            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送

        }
    }

    private void sendrunwhite2(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("strDatanzq", "run: nzqsend" + strData);
                byte[] outputData = MainUtilsThread.hexStringToByteArray(strData.toString());
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(IP2);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
//                    interrupted();
//                    try {
////                        Thread.sleep(2000);
////                        sb1Locations(sendListBlack, tf1, spinnerS1, context, listImsiListTrue);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    private void sendwhiteListRunLT(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {

        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        Log.d("aaaaalist", "sendwhiteListRun: " + list);
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //消息头
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff01");
        //黑名单数量
        String s = "";
        if (totalMy == 10) {
            s = "10";
        } else {
            s = "0" + String.valueOf(totalMy);
        }
        str.append(s);
//        str.append(MainUtils2.StringPin(MainUtils2.blacklistCount(Integer.toString(totalMy, 16))));
        Log.d("aaTAG", "sendwhiteListRun: " + str.toString());
        str.append("00");
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("") && list.get(i) != null) {
                StringBuffer imsi = toIMSI(list.get(i));
                str.append(imsi).append("0000");
                list2.add(list.get(i));
            }
        }
        for (int i = 0; i < list2.size(); i++) {
            PararBean pararBean = new PararBean();
            pararBean.setImsi(list2.get(i));
        }
        for (int i = 0; i < 10 - list2.size(); i++) {
            str.append("0000000000000000000000000000000000");
        }
        Log.d("uuew", "sendwhiteListRun: " + str.toString());
        str.append("03ff00");
        if (!TextUtils.isEmpty(str)) {
            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送
//            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送

        }
    }

    //设备1已开始发送
    private void sendrunwhite(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
//                String aa="";
//                aa ="aaaa555539f0b900000000ff010301" +
//                        "3436303030323336303839333634380000" +
//                        "3131313131313131313131313131310000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "0000000000000000000000000000000000" +
//                        "03ff00";
                Log.e("delestrDatanzq", "run: nzqsend" + strData);
//                Log.e("strDatanaazq", "run: nzqsend" + aa.toString());
                byte[] outputData = MainUtilsThread.hexStringToByteArray(strData.toString());
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(IP1);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
//                    interrupted();
//                    try {
////                        Thread.sleep(2000);
////                        sb1Locations(sendListBlack, tf1, spinnerS1, context, listImsiListTrue);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    private void startAuto(String data, boolean b, TextToSpeech textToSpeech) {
        textToSpeech.setPitch(1.f);
        Log.d("wpnqq", "startAuto: " + b);
        // 设置语速
        textToSpeech.setSpeechRate(8.01f);
        textToSpeech.speak(data,//输入中文，若不支持的设备则不会读出来
                TextToSpeech.QUEUE_FLUSH, null);
    }

    Dialog dialog;
    View inflate;

    @Override
    public void buildSD(final String spinnerS1, final int i, final String sb1, final Context context) {
        if (i == 1) {
            new Thread(new Runnable() {
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    List<PinConfigBean> pinConfigBeans = null;
                    DBManagerPinConfig dbManagerPinConfig = null;
                    try {
                        dbManagerPinConfig = new DBManagerPinConfig(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (TextUtils.isEmpty(spinnerS1)) {
                        ToastUtils.showToast("设备1频点不能为空");
                        return;
                    }
                    try {
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1)); //查询对应的频点

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Conmmunit01Bean forid = null;
                    DBManager01 dbManager01 = null;
                    try {
                        dbManager01 = new DBManager01(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        forid = dbManager01.forid(1);  //查询小区1

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (pinConfigBeans == null) {
//                    ToastUtils.showToast("频点配置错误");
//                    Set1StatusBar("频点配置错误");
//                        viewS.buildSdError("频点配置错误", 1);
                    }
                    if (forid == null) {
//                    ToastUtils.showToast("小区1配置错误");
//                    Set1StatusBar("小区1配置错误");
//                        viewS.buildSdError("小区1配置错误", 1);
                        return;
                    }
                    DatagramSocket socket = null;
                    InetAddress address = null;//你本机的ip地址
                    Log.e("znzq", "run: nzqsend");

//        int ulEarfcn,int dlEarfcn,String PLMN, int band,int PCI,int TAC
                    String s = setxq.setXq(
                            pinConfigBeans.get(0).getUp(),
                            pinConfigBeans.get(0).getDown(),
                            pinConfigBeans.get(0).getPlmn(),
                            pinConfigBeans.get(0).getBand(),
                            Integer.parseInt(forid.getPci()),
                            Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid()));
//                Log.d(TAG, "run: " + s);
//                byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
//                        pinConfigBeans.get(0).getUp(),
//                        pinConfigBeans.get(0).getDown(),
//                        pinConfigBeans.get(0).getPlmn(),
//                        pinConfigBeans.get(0).getBand(),
//                        Integer.parseInt(forid.getPci()),
//                        Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));

                    String plmn = "46000";
                    try {
                        DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(context);
                        List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                        List<AddPararBean> listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                        }
                        List<String> listyd = new ArrayList<>();
                        List<String> listlt = new ArrayList<>();
                        List<String> listdx = new ArrayList<>();
                        if (listImsiListTrue.size() > 0) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals("移动")) {
                                    listyd.add(listImsiListTrue.get(i).getImsi());
                                }
                            }
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals("联通")) {
                                    listlt.add(listImsiListTrue.get(i).getImsi());
                                }
                            }
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals("电信")) {
                                    listdx.add(listImsiListTrue.get(i).getImsi());
                                }
                            }
                            if (listyd.size()==0&&listlt.size()==0&&listdx.size()==0){

                                return;
                            }
                            if (listyd.size()>0&&listlt.size()==0&&listdx.size()==0){
                                plmn="46000";
                                byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
                                        pinConfigBeans.get(0).getUp(),
                                        pinConfigBeans.get(0).getDown(),
                                        plmn,
                                        pinConfigBeans.get(0).getBand(),
                                        Integer.parseInt(forid.getPci()),
                                        Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));//4294967295   //222222222l   //  268435455
                                DOWNPIN1 = pinConfigBeans.get(0).getDown() + "";
                                try {
                                    socket = new DatagramSocket();
                                    address = InetAddress.getByName(IP1);
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                } catch (SocketException e) {
                                    e.printStackTrace();
                                }
                                ;
                                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
                                Log.e("startLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                try {
                                    if (sb1.equals("定位中")) {

                                    } else {
                                        socket.send(outputPacket);
                                        Log.e("socketstartLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                    }

//                    interrupted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            if (listlt.size()>0&&listyd.size()==0&&listdx.size()==0){
                                plmn="46001";
                                byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
                                        pinConfigBeans.get(0).getUp(),
                                        pinConfigBeans.get(0).getDown(),
                                        plmn,
                                        pinConfigBeans.get(0).getBand(),
                                        Integer.parseInt(forid.getPci()),
                                        Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));//4294967295   //222222222l   //  268435455
                                DOWNPIN1 = pinConfigBeans.get(0).getDown() + "";
                                try {
                                    socket = new DatagramSocket();
                                    address = InetAddress.getByName(IP1);
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                } catch (SocketException e) {
                                    e.printStackTrace();
                                }
                                ;
                                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
                                Log.e("startLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                try {
                                    if (sb1.equals("定位中")) {

                                    } else {
                                        socket.send(outputPacket);
                                        Log.e("socketstartLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                    }

//                    interrupted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;

                            }
                            if (listdx.size()>0&&listlt.size()==0&&listyd.size()==0){
                                plmn="46011";
                                byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
                                        pinConfigBeans.get(0).getUp(),
                                        pinConfigBeans.get(0).getDown(),
                                        plmn,
                                        pinConfigBeans.get(0).getBand(),
                                        Integer.parseInt(forid.getPci()),
                                        Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));//4294967295   //222222222l   //  268435455
                                DOWNPIN1 = pinConfigBeans.get(0).getDown() + "";
                                try {
                                    socket = new DatagramSocket();
                                    address = InetAddress.getByName(IP1);
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                } catch (SocketException e) {
                                    e.printStackTrace();
                                }
                                ;
                                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
                                Log.e("startLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                try {
                                    if (sb1.equals("定位中")) {

                                    } else {
                                        socket.send(outputPacket);
                                        Log.e("socketstartLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                    }

//                    interrupted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                        }else {
                            byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
                                    pinConfigBeans.get(0).getUp(),
                                    pinConfigBeans.get(0).getDown(),
                                    pinConfigBeans.get(0).getPlmn(),
                                    pinConfigBeans.get(0).getBand(),
                                    Integer.parseInt(forid.getPci()),
                                    Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));//4294967295   //222222222l   //  268435455
                            DOWNPIN1 = pinConfigBeans.get(0).getDown() + "";
                            try {
                                socket = new DatagramSocket();
                                address = InetAddress.getByName(IP1);
                            } catch (UnknownHostException e) {
                                e.printStackTrace();
                            } catch (SocketException e) {
                                e.printStackTrace();
                            }
                            ;
                            DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
                            Log.e("startLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                            try {
                                if (sb1.equals("定位中")) {

                                } else {
                                    socket.send(outputPacket);
                                    Log.e("socketstartLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                                }

//                    interrupted();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("addPararBeans", "init: " + dataAll);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }





                }
            }).start();
        }
        if (i == 2) {
            new Thread(new Runnable() {
                @SuppressLint("LongLogTag")
                @Override
                public void run() {
                    List<PinConfigBean> pinConfigBeans = null;
                    DBManagerPinConfig dbManagerPinConfig = null;
                    try {
                        dbManagerPinConfig = new DBManagerPinConfig(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (TextUtils.isEmpty(spinnerS1)) {
                        ToastUtils.showToast("设备2频点不能为空");
                        return;
                    }
                    try {
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1)); //查询对应的频点

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Conmmunit01Bean forid = null;
                    DBManager01 dbManager01 = null;
                    try {
                        dbManager01 = new DBManager01(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        forid = dbManager01.forid(2);  //查询小区1

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (pinConfigBeans == null) {
//                    ToastUtils.showToast("频点配置错误");
//                    Set1StatusBar("频点配置错误");
//                        viewS.buildSdError("频点配置错误", 1);
                    }
                    if (forid == null) {
//                    ToastUtils.showToast("小区1配置错误");
//                    Set1StatusBar("小区1配置错误");
//                        viewS.buildSdError("小区1配置错误", 1);
                        return;
                    }
                    DatagramSocket socket = null;
                    InetAddress address = null;//你本机的ip地址
                    Log.e("znzq", "run: nzqsend");

//        int ulEarfcn,int dlEarfcn,String PLMN, int band,int PCI,int TAC
                    String s = setxq.setXq(
                            pinConfigBeans.get(0).getUp(),
                            pinConfigBeans.get(0).getDown(),
                            pinConfigBeans.get(0).getPlmn(),
                            pinConfigBeans.get(0).getBand(),
                            Integer.parseInt(forid.getPci()),
                            Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid()));
//                Log.d(TAG, "run: " + s);
//                byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
//                        pinConfigBeans.get(0).getUp(),
//                        pinConfigBeans.get(0).getDown(),
//                        pinConfigBeans.get(0).getPlmn(),
//                        pinConfigBeans.get(0).getBand(),
//                        Integer.parseInt(forid.getPci()),
//                        Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));

                    byte[] outputData = MainUtilsThread.hexStringToByteArray(setxq.setXq(
                            pinConfigBeans.get(0).getUp(),
                            pinConfigBeans.get(0).getDown(),
                            pinConfigBeans.get(0).getPlmn(),
                            pinConfigBeans.get(0).getBand(),
                            Integer.parseInt(forid.getPci()),
                            Integer.parseInt(forid.getTac()), Integer.parseInt(forid.getCid())));//4294967295   //222222222l   //  268435455
                    DOWNPIN2 = pinConfigBeans.get(0).getDown() + "";
                    try {
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(IP2);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    ;
                    DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
                    Log.e("startLocation1s建立小区1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                    try {
                        if (sb1.equals("定位中")) {

                        } else {
                            socket.send(outputPacket);
                            Log.e("socketstartLocation1s建立小区2", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());

                        }

//                    interrupted();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

    }

    /**
     * 扫频建立小区
     *
     * @param context
     * @param device
     * @param tf
     * @param down
     */
    @Override
    public void saopinjianlixiaoqu(Context context, int device, String tf, String down) {
        if (device == 1) {
            if (tf.equals("定位中")) {

                return;
            }
            String yy = "";
            List<AddPararBean> sendListBlack = null;
            List<PinConfigBean> pinConfigBeans = null;
            sendListBlack = new ArrayList<>();
            DBManagerPinConfig dbManagerPinConfig = null;
            try {
                dbManagerPinConfig = new DBManagerPinConfig(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(down)); //查询对应的频点
                yy = pinConfigBeans.get(0).getYy();
                Log.d("qwqea", "saopinjianlixiaoqu: " + yy);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Log.d(TAG, "sendBlackList:移动 ");
            List<AddPararBean> dataAll = null;
            try {
                DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(context);
                dataAll = dbManagerAddParam.getDataAll();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<AddPararBean> listImsiListTrue = new ArrayList<>();
            if (dataAll.size() > 0) {
                for (int i = 0; i < dataAll.size(); i++) {
                    if (dataAll.get(i).isCheckbox() == true) {
                        listImsiListTrue.add(dataAll.get(i));
                    }

                }
            }

            if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                for (int i = 0; i < listImsiListTrue.size(); i++) {
//                    if (listImsiListTrue.get(i).getYy().equals(yy)) { //以 运营商频点按断
//                        sendListBlack.add(listImsiListTrue.get(i));
//                    }
                    if (listImsiListTrue.get(i).isCheckbox()) { //以 选中的 IMSI判断
                        sendListBlack.add(listImsiListTrue.get(i));
                    }
                }
            }

            if (sendListBlack.size() > 0 && sendListBlack != null) {
//                Log.d(TAG, "sendBlackList: " + sendListBlack);
                if (sendListBlack.size() > 20) {
                    ToastUtils.showToast("符合条件的黑名单列表大于20");
                } else {
                    sendBlackListRun(sendListBlack, tf, down, context, listImsiListTrue);
                }
//
            } else {
                viewS.MesageV(1);
                ToastUtils.showToast("频点与IMSI运营商不符");
            }
        }

        //设备2
        if (device == 2) {
            if (tf.equals("定位中")) {

                return;
            }
            String yy = "";
            List<AddPararBean> sendListBlack = null;
            List<PinConfigBean> pinConfigBeans = null;
            sendListBlack = new ArrayList<>();
            DBManagerPinConfig dbManagerPinConfig = null;
            try {
                dbManagerPinConfig = new DBManagerPinConfig(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(down)); //查询对应的频点
                yy = pinConfigBeans.get(0).getYy();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Log.d(TAG, "sendBlackList:移动 ");
            List<AddPararBean> dataAll = null;
            try {
                DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(context);
                dataAll = dbManagerAddParam.getDataAll();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<AddPararBean> listImsiListTrue = new ArrayList<>();
            if (dataAll.size() > 0) {
                for (int i = 0; i < dataAll.size(); i++) {
                    if (dataAll.get(i).isCheckbox() == true) {
                        listImsiListTrue.add(dataAll.get(i));
                    }

                }
            }
            if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                for (int i = 0; i < listImsiListTrue.size(); i++) {
                    if (listImsiListTrue.get(i).getYy().equals(yy)) {
                        sendListBlack.add(listImsiListTrue.get(i));
                    }
                }
            }

            if (sendListBlack.size() > 0 && sendListBlack != null) {
//                Log.d(TAG, "sendBlackList: " + sendListBlack);
                if (sendListBlack.size() > 20) {
                    ToastUtils.showToast("符合条件的黑名单列表大于20");
                } else {
                    sendBlackListRun2(sendListBlack, tf, down, context, listImsiListTrue);
                }
//
            } else {
                viewS.MesageV(2);
                ToastUtils.showToast("频点与IMSI运营商不符");
            }
        }
    }


    public void startsd(boolean b, int device, final String tf, final Context context, final String spinnerDown, String sbzhuangtai) {
        Log.d(TAG, "startSD: " + device + "tf==" + tf + "---" + spinnerDown);
        if (device == 1) {
            if (b) {
                if (TextUtils.isEmpty(tf)) {
                    ToastUtils.showToast("设备1未连接");
                    return;
                }
                if (TextUtils.isEmpty(sbzhuangtai)) {
                    ToastUtils.showToast("设备1不在就绪状态");
                    return;
                }
                if (TextUtils.isEmpty(spinnerDown)) {
                    ToastUtils.showToast("设备1下行频点不能为空");
                    return;
                }
                String yy = "";
                String sb1zhishi = "";
                List<PinConfigBean> pinConfigBeans = null;
                DBManagerPinConfig dbManagerPinConfig = null;
                try {
                    dbManagerPinConfig = new DBManagerPinConfig(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //查询对应的频点
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//首页IMSI列表的数据
                List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
                if (sb1zhishi.equals(tf)) {


                    try {
                        try {
                            dbManagerAddParam = new DBManagerAddParam(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            dataAll = dbManagerAddParam.getDataAll();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                    list1size.add(i);
                                }
//                            viewS.setpararBeansList1(listImsiListTrue);
                            }
                        }
                        List<AddPararBean> sendListBlack = null;
                        sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                        if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                    sendListBlack.add(listImsiListTrue.get(i));
                                }
                            }
                        }

                        if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                            if (sendListBlack.size() > 20) {
                                ToastUtils.showToast("符合条件的黑名单列表大于20");
                            } else {

                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("确定要启动设备1吗?");
//                ip=IP1;

                                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                                final List<AddPararBean> finalSendListBlack = sendListBlack;
                                final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
                                bt_confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        sendBlackListRun(finalSendListBlack, tf, spinnerDown, context, finalListImsiListTrue);

                                        viewS.bt_startFalg(true);
                                        dialog.dismiss();
                                        dialog.cancel();

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
                            }
//
                        } else {

                            ToastUtils.showToast("频点与IMSI运营商不符");
                        }

                    } catch (Exception e) {
                    }
                } else {//制式不一致
//                                ToastUtils.showToast("设备1制式不一致");


                    try {
                        try {
                            dbManagerAddParam = new DBManagerAddParam(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            dataAll = dbManagerAddParam.getDataAll();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                    list1size.add(i);
                                }
//                            viewS.setpararBeansList1(listImsiListTrue);
                            }
                        }
                        List<AddPararBean> sendListBlack = null;
                        sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                        if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                    sendListBlack.add(listImsiListTrue.get(i));
                                }
                            }
                        }

                        if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                            if (sendListBlack.size() > 20) {
                                ToastUtils.showToast("符合条件的黑名单列表大于20");
                            } else {
                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("设备1需要切换制式并重启?");
//                ip=IP1;

                                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                                bt_confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        viewS.zhishiqiehuan(1, tf);
                                        dialog.dismiss();
                                        dialog.cancel();

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

                            }
//
                        } else {

                            ToastUtils.showToast("频点与IMSI运营商不符");
                            return;
                        }

                    } catch (Exception e) {
                    }


                    return;
                }
            } else {   //无弹窗

//                if (TextUtils.isEmpty(tf)) {
//                    ToastUtils.showToast("设备1未连接");
//                    return;
//                }
//                if (TextUtils.isEmpty(sbzhuangtai)) {
//                    ToastUtils.showToast("设备1不在就绪状态");
//                    return;
//                }
//                if (TextUtils.isEmpty(spinnerDown)) {
//                    ToastUtils.showToast("设备1下行频点不能为空");
//                    return;
//                }
                String yy = "";
                String sb1zhishi = "";
                List<PinConfigBean> pinConfigBeans = null;
                DBManagerPinConfig dbManagerPinConfig = null;
                try {
                    dbManagerPinConfig = new DBManagerPinConfig(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //查询对应的频点
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//首页IMSI列表的数据
                List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
                if (sb1zhishi.equals(tf)) {


                    try {
                        try {
                            dbManagerAddParam = new DBManagerAddParam(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            dataAll = dbManagerAddParam.getDataAll();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                    list1size.add(i);
                                }
//                            viewS.setpararBeansList1(listImsiListTrue);
                            }
                        }
                        List<AddPararBean> sendListBlack = null;
                        sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                        if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                    sendListBlack.add(listImsiListTrue.get(i));
                                }
                            }
                        }

                        if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                            if (sendListBlack.size() > 20) {
                                ToastUtils.showToast("符合条件的黑名单列表大于20");
                            } else {
                                final List<AddPararBean> finalSendListBlack = sendListBlack;
                                final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
                                sendBlackListRun(finalSendListBlack, tf, spinnerDown, context, finalListImsiListTrue);
                            }
//
                        } else {

                            ToastUtils.showToast("频点与IMSI运营商不符");
                        }

                    } catch (Exception e) {
                    }
                }

            }


        }
        //设备2
        if (device == 2) {
            if (b) {
                if (TextUtils.isEmpty(tf)) {
                    ToastUtils.showToast("设备2未连接");
                    return;
                }
                if (TextUtils.isEmpty(sbzhuangtai)) {
                    ToastUtils.showToast("设备2不在就绪状态");
                    return;
                }
                if (TextUtils.isEmpty(spinnerDown)) {
                    ToastUtils.showToast("设备2下行频点不能为空");
                    return;
                }
                String yy = "";
                String sb1zhishi = "";
                List<PinConfigBean> pinConfigBeans = null;
                DBManagerPinConfig dbManagerPinConfig = null;
                try {
                    dbManagerPinConfig = new DBManagerPinConfig(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //查询对应的频点
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//首页IMSI列表的数据
                List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
                if (sb1zhishi.equals(tf)) {


                    try {
                        try {
                            dbManagerAddParam = new DBManagerAddParam(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            dataAll = dbManagerAddParam.getDataAll();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                    list1size.add(i);
                                }
//                            viewS.setpararBeansList1(listImsiListTrue);
                            }
                        }
                        List<AddPararBean> sendListBlack = null;
                        sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                        if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                    sendListBlack.add(listImsiListTrue.get(i));
                                }
                            }
                        }

                        if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                            if (sendListBlack.size() > 20) {
                                ToastUtils.showToast("符合条件的黑名单列表大于20");
                            } else {

                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("确定要启动设备2吗?");
//                ip=IP1;

                                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                                final List<AddPararBean> finalSendListBlack = sendListBlack;
                                final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
                                bt_confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        sendBlackListRun2(finalSendListBlack, tf, spinnerDown, context, finalListImsiListTrue);


                                        dialog.dismiss();
                                        dialog.cancel();

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
                            }
//
                        } else {

                            ToastUtils.showToast("频点与IMSI运营商不符");
                        }

                    } catch (Exception e) {
                    }
                } else {//制式不一致
//                                ToastUtils.showToast("设备1制式不一致");


                    try {
                        try {
                            dbManagerAddParam = new DBManagerAddParam(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            dataAll = dbManagerAddParam.getDataAll();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                    list1size.add(i);
                                }
//                            viewS.setpararBeansList1(listImsiListTrue);
                            }
                        }
                        List<AddPararBean> sendListBlack = null;
                        sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                        if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                    sendListBlack.add(listImsiListTrue.get(i));
                                }
                            }
                        }

                        if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                            if (sendListBlack.size() > 20) {
                                ToastUtils.showToast("符合条件的黑名单列表大于20");
                            } else {
                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("设备2需要切换制式并重启?");
//                ip=IP1;

                                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                                bt_confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        viewS.zhishiqiehuan(2, tf);

                                        dialog.dismiss();
                                        dialog.cancel();

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

                            }
//
                        } else {

                            ToastUtils.showToast("频点与IMSI运营商不符");
                            return;
                        }

                    } catch (Exception e) {
                    }


                    return;
                }
            } else {//设备2无弹窗

//                if (TextUtils.isEmpty(tf)) {
//                    ToastUtils.showToast("设备2未连接");
//                    return;
//                }
//                if (TextUtils.isEmpty(sbzhuangtai)) {
//                    ToastUtils.showToast("设备2不在就绪状态");
//                    return;
//                }
//                if (TextUtils.isEmpty(spinnerDown)) {
//                    ToastUtils.showToast("设备2下行频点不能为空");
//                    return;
//                }
                String yy = "";
                String sb1zhishi = "";
                List<PinConfigBean> pinConfigBeans = null;
                DBManagerPinConfig dbManagerPinConfig = null;
                try {
                    dbManagerPinConfig = new DBManagerPinConfig(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //查询对应的频点
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//首页IMSI列表的数据
                List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
                if (sb1zhishi.equals(tf)) {


                    try {
                        try {
                            dbManagerAddParam = new DBManagerAddParam(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            dataAll = dbManagerAddParam.getDataAll();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImsiListTrue = new ArrayList<>();
                        if (dataAll.size() > 0) {
                            for (int i = 0; i < dataAll.size(); i++) {
                                if (dataAll.get(i).isCheckbox() == true) {
                                    dataAll.get(i).setSb("");
                                    listImsiListTrue.add(dataAll.get(i));
                                }
                            }
                            List<Integer> list1size = new ArrayList<>();
                            if (listImsiListTrue.size() > 0) {
                                for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                    list1size.add(i);
                                }
//                            viewS.setpararBeansList1(listImsiListTrue);
                            }
                        }
                        List<AddPararBean> sendListBlack = null;
                        sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                        if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                    sendListBlack.add(listImsiListTrue.get(i));
                                }
                            }
                        }

                        if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                            if (sendListBlack.size() > 20) {
                                ToastUtils.showToast("符合条件的黑名单列表大于20");
                            } else {

//                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
//                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
//                                TextView tv_title = inflate.findViewById(R.id.tv_title);
////            String ip=IP1;
//
//                                tv_title.setText("确定要启动设备2吗?");
////                ip=IP1;
//
//                                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                                final List<AddPararBean> finalSendListBlack = sendListBlack;
                                final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
//                                bt_confirm.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
                                sendBlackListRun2(finalSendListBlack, tf, spinnerDown, context, finalListImsiListTrue);


//                                        dialog.dismiss();
//                                        dialog.cancel();
//
//                                    }
//                                });
//                                Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
//                                bt_cancel.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        dialog.dismiss();
//                                        dialog.cancel();
//                                    }
//                                });
//                                dialog.setCanceledOnTouchOutside(false);
//                                dialog.setContentView(inflate);
//                                //获取当前Activity所在的窗体
//                                Window dialogWindow = dialog.getWindow();
//                                //设置Dialog从窗体底部弹出
//                                dialogWindow.setGravity(Gravity.CENTER);
//                                //获得窗体的属性
//                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
////                           lp.y = 20;//设置Dialog距离底部的距离
////                          将属性设置给窗体
//                                dialogWindow.setAttributes(lp);
//                                dialog.show();//显示对话框
                            }
//
                        } else {

                            ToastUtils.showToast("频点与IMSI运营商不符");
                        }

                    } catch (Exception e) {
                    }
                }


            }

        }

    }

    public void startsdquanbu(int device, final String tf1, final String tf2, final Context context, final String spinnerDown1, String spinnerDown2, String sbzhuangtai1, String sbzhuangtai2) {
        Log.d(TAG, "startSD: " + device + "tf==" + tf1 + "---" + spinnerDown1);
        //设备1
        if (device == 3) {
            if (TextUtils.isEmpty(tf1)) {
                ToastUtils.showToast("设备1未连接");
                return;
            }
            if (TextUtils.isEmpty(sbzhuangtai1)) {
                ToastUtils.showToast("设备1不在就绪状态");
                return;
            }
            if (TextUtils.isEmpty(spinnerDown1)) {
                ToastUtils.showToast("设备1下行频点不能为空");
                return;
            }
            String yy = "";
            String sb1zhishi = "";
            List<PinConfigBean> pinConfigBeans = null;
            DBManagerPinConfig dbManagerPinConfig = null;
            try {
                dbManagerPinConfig = new DBManagerPinConfig(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown1)); //查询对应的频点
                yy = pinConfigBeans.get(0).getYy();
                sb1zhishi = pinConfigBeans.get(0).getTf();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManagerAddParam dbManagerAddParam = null;
            List<AddPararBean> dataAll = null;//首页IMSI列表的数据
            List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
            if (sb1zhishi.equals(tf1)) {


                try {
                    try {
                        dbManagerAddParam = new DBManagerAddParam(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataAll = dbManagerAddParam.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    listImsiListTrue = new ArrayList<>();
                    if (dataAll.size() > 0) {
                        for (int i = 0; i < dataAll.size(); i++) {
                            if (dataAll.get(i).isCheckbox() == true) {
                                dataAll.get(i).setSb("");
                                listImsiListTrue.add(dataAll.get(i));
                            }
                        }
                        List<Integer> list1size = new ArrayList<>();
                        if (listImsiListTrue.size() > 0) {
                            for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                list1size.add(i);
                            }
//                            viewS.setpararBeansList1(listImsiListTrue);
                        }
                    }
                    List<AddPararBean> sendListBlack = null;
                    sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                    if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                        for (int i = 0; i < listImsiListTrue.size(); i++) {
                            if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                sendListBlack.add(listImsiListTrue.get(i));
                            }
                        }
                    }

                    if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                        if (sendListBlack.size() > 20) {
                            ToastUtils.showToast("符合条件的黑名单列表大于20");
                        } else {

                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                            tv_title.setText("确定要启动设备吗?");
//                ip=IP1;

                            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                            final List<AddPararBean> finalSendListBlack = sendListBlack;
                            final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
                            bt_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sendBlackListRun(finalSendListBlack, tf1, spinnerDown1, context, finalListImsiListTrue);


                                    dialog.dismiss();
                                    dialog.cancel();

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
                        }
//
                    } else {

                        ToastUtils.showToast("频点与IMSI运营商不符");
                    }

                } catch (Exception e) {
                }
            } else {//制式不一致
//                                ToastUtils.showToast("设备1制式不一致");


                try {
                    try {
                        dbManagerAddParam = new DBManagerAddParam(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataAll = dbManagerAddParam.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    listImsiListTrue = new ArrayList<>();
                    if (dataAll.size() > 0) {
                        for (int i = 0; i < dataAll.size(); i++) {
                            if (dataAll.get(i).isCheckbox() == true) {
                                dataAll.get(i).setSb("");
                                listImsiListTrue.add(dataAll.get(i));
                            }
                        }
                        List<Integer> list1size = new ArrayList<>();
                        if (listImsiListTrue.size() > 0) {
                            for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                list1size.add(i);
                            }
//                            viewS.setpararBeansList1(listImsiListTrue);
                        }
                    }
                    List<AddPararBean> sendListBlack = null;
                    sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                    if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                        for (int i = 0; i < listImsiListTrue.size(); i++) {
                            if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                sendListBlack.add(listImsiListTrue.get(i));
                            }
                        }
                    }

                    if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                        if (sendListBlack.size() > 20) {
                            ToastUtils.showToast("符合条件的黑名单列表大于20");
                        } else {
                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                            tv_title.setText("设备1需要切换制式并重启?");


                            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                            bt_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewS.zhishiqiehuan(1, tf1);
//
                                    dialog.dismiss();
                                    dialog.cancel();

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

                        }
//
                    } else {

                        ToastUtils.showToast("频点与IMSI运营商不符");
                        return;
                    }

                } catch (Exception e) {
                }


                return;
            }
        }
        //设备2
        if (device == 3) {
            if (TextUtils.isEmpty(tf2)) {
                ToastUtils.showToast("设备2未连接");
                return;
            }
            if (TextUtils.isEmpty(sbzhuangtai2)) {
                ToastUtils.showToast("设备2不在就绪状态");
                return;
            }
            if (TextUtils.isEmpty(spinnerDown2)) {
                ToastUtils.showToast("设备2下行频点不能为空");
                return;
            }
            String yy = "";
            String sb1zhishi = "";
            List<PinConfigBean> pinConfigBeans = null;
            DBManagerPinConfig dbManagerPinConfig = null;
            try {
                dbManagerPinConfig = new DBManagerPinConfig(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown2)); //查询对应的频点
                yy = pinConfigBeans.get(0).getYy();
                sb1zhishi = pinConfigBeans.get(0).getTf();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManagerAddParam dbManagerAddParam = null;
            List<AddPararBean> dataAll = null;//首页IMSI列表的数据
            List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
            if (sb1zhishi.equals(tf2)) {


                try {
                    try {
                        dbManagerAddParam = new DBManagerAddParam(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataAll = dbManagerAddParam.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    listImsiListTrue = new ArrayList<>();
                    if (dataAll.size() > 0) {
                        for (int i = 0; i < dataAll.size(); i++) {
                            if (dataAll.get(i).isCheckbox() == true) {
                                dataAll.get(i).setSb("");
                                listImsiListTrue.add(dataAll.get(i));
                            }
                        }
                        List<Integer> list1size = new ArrayList<>();
                        if (listImsiListTrue.size() > 0) {
                            for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                list1size.add(i);
                            }
//                            viewS.setpararBeansList1(listImsiListTrue);
                        }
                    }
                    List<AddPararBean> sendListBlack = null;
                    sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                    if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                        for (int i = 0; i < listImsiListTrue.size(); i++) {
                            if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                sendListBlack.add(listImsiListTrue.get(i));
                            }
                        }
                    }

                    if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                        if (sendListBlack.size() > 20) {
                            ToastUtils.showToast("设备2符合条件的黑名单列表大于20");
                        } else {

//                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
//                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
//                            TextView tv_title = inflate.findViewById(R.id.tv_title);
////            String ip=IP1;
//
//                            tv_title.setText("确定要启动设备2吗?");
////                ip=IP1;
//
//                            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
//
                            final List<AddPararBean> finalSendListBlack = sendListBlack;
                            final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
//                            bt_confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
                            sendBlackListRun2(finalSendListBlack, tf2, spinnerDown2, context, finalListImsiListTrue);


//                                    dialog.dismiss();
//                                    dialog.cancel();
//
//                                }
//                            });
//                            Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
//                            bt_cancel.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    dialog.dismiss();
//                                    dialog.cancel();
//                                }
//                            });
//                            dialog.setCanceledOnTouchOutside(false);
//                            dialog.setContentView(inflate);
//                            //获取当前Activity所在的窗体
//                            Window dialogWindow = dialog.getWindow();
//                            //设置Dialog从窗体底部弹出
//                            dialogWindow.setGravity(Gravity.CENTER);
//                            //获得窗体的属性
//                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
////                           lp.y = 20;//设置Dialog距离底部的距离
////                          将属性设置给窗体
//                            dialogWindow.setAttributes(lp);
//                            dialog.show();//显示对话框
                        }
//
                    } else {

                        ToastUtils.showToast("设备2频点与IMSI运营商不符");
                    }

                } catch (Exception e) {
                }
            } else {//制式不一致
//                                ToastUtils.showToast("设备1制式不一致");


                try {
                    try {
                        dbManagerAddParam = new DBManagerAddParam(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        dataAll = dbManagerAddParam.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    listImsiListTrue = new ArrayList<>();
                    if (dataAll.size() > 0) {
                        for (int i = 0; i < dataAll.size(); i++) {
                            if (dataAll.get(i).isCheckbox() == true) {
                                dataAll.get(i).setSb("");
                                listImsiListTrue.add(dataAll.get(i));
                            }
                        }
                        List<Integer> list1size = new ArrayList<>();
                        if (listImsiListTrue.size() > 0) {
                            for (int i = 1; i < listImsiListTrue.size() + 1; i++) {

                                list1size.add(i);
                            }
//                            viewS.setpararBeansList1(listImsiListTrue);
                        }
                    }
                    List<AddPararBean> sendListBlack = null;
                    sendListBlack = new ArrayList<>();
//                        Log.d(TAG, "sendBlackList:移动 ");
                    if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                        for (int i = 0; i < listImsiListTrue.size(); i++) {
                            if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                sendListBlack.add(listImsiListTrue.get(i));
                            }
                        }
                    }

                    if (sendListBlack.size() > 0 && sendListBlack != null) {
//                                Log.d(TAG, "sendBlackList: " + sendListBlack);
                        if (sendListBlack.size() > 20) {
                            ToastUtils.showToast("符合条件的黑名单列表大于20");
                        } else {
                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                            tv_title.setText("设备2需要切换制式并重启?");
//                ip=IP1;

                            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                            bt_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewS.zhishiqiehuan(2, tf2);

                                    dialog.dismiss();
                                    dialog.cancel();

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

                        }
//
                    } else {

                        ToastUtils.showToast("设备2频点与IMSI运营商不符");
                        return;
                    }

                } catch (Exception e) {
                }


                return;
            }
        }

    }


    //发送黑名单
    private void sendBlackListRun(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {
        if (sendListBlack.size() == 0) {
            ToastUtils.showToast("频点与IMSI运营商不符");
            return;
        }
        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //消息头
        StringBuffer str = new StringBuffer("aaaa555553f06401000000ff");
        //黑名单数量
        str.append(MainUtils2.StringPin(MainUtils2.blacklistCount(Integer.toString(totalMy, 16))));
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("") && list.get(i) != null) {
                StringBuffer imsi = toIMSI(list.get(i));
                str.append(imsi).append("0000");
                list2.add(list.get(i));

            }
        }
        for (int i = 0; i < list2.size(); i++) {
            PararBean pararBean = new PararBean();
            pararBean.setImsi(list2.get(i));


        }
        for (int i = 0; i < 20 - list2.size(); i++) {
            str.append("0000000000000000000000000000000000");
        }
        str.append("010000");
        if (!TextUtils.isEmpty(str)) {
            sendrun(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送

        }
    }

    //发送黑名单2
    private void sendBlackListRun2(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {
        if (sendListBlack.size() == 0) {
            ToastUtils.showToast("频点与IMSI运营商不符");
            return;
        }
        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //消息头
        StringBuffer str = new StringBuffer("aaaa555553f06401000000ff");
        //黑名单数量
        str.append(MainUtils2.StringPin(MainUtils2.blacklistCount(Integer.toString(totalMy, 16))));
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("") && list.get(i) != null) {
                StringBuffer imsi = toIMSI(list.get(i));
                str.append(imsi).append("0000");
                list2.add(list.get(i));

            }
        }
        for (int i = 0; i < list2.size(); i++) {
            PararBean pararBean = new PararBean();
            pararBean.setImsi(list2.get(i));


        }
        for (int i = 0; i < 20 - list2.size(); i++) {
            str.append("0000000000000000000000000000000000");
        }
        str.append("010000");
        if (!TextUtils.isEmpty(str)) {
            sendrun2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//开始发送

        }
    }

    //设备2已开始发送
    private void sendrun2(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("strDatanzq", "run: nzqsend" + strData);
                byte[] outputData = MainUtilsThread.hexStringToByteArray(strData.toString());
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(IP2);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstart2Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
//                    interrupted();
                    try {
                        Thread.sleep(2000);
                        sb2Locations(sendListBlack, tf1, spinnerS1, context, listImsiListTrue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    //设备2定位模式 手动
    private void sb2Locations(final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String sa = "";
                if (tf1.equals("TDD")) {
                    sa = "01";
                }
                if (tf1.equals("FDD")) {
                    sa = "00";
                }
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");

                if (!TextUtils.isEmpty(spinnerS1)) {
                    String yy = "";
                    DBManagerPinConfig dbManagerPinConfig = null;
                    List<PinConfigBean> pinConfigBeans;
                    try {
                        dbManagerPinConfig = new DBManagerPinConfig(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1));//查询对应的频点
                        yy = pinConfigBeans.get(0).getYy();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                        Log.d(TAG, "sendBlackList:移动 ");
                    if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                        for (int i = 0; i < listImsiListTrue.size(); i++) {
                            if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                sendListBlack.add(listImsiListTrue.get(i));
                            }
                        }
                    }

                    if (sendListBlack.size() > 0 && sendListBlack != null) {
//                            Log.d(TAG, "sendBlackList: " + sendListBlack);
                        if (sendListBlack.size() > 20) {
                            ToastUtils.showToast("符合条件的黑名单列表大于20");
                        } else {
//                                sendBlackListRun(sendListBlack);
                        }
//
                    } else {
                        ToastUtils.showToast("频点与IMSI运营商不符");
                    }

                } else {
                    ToastUtils.showToast("请先设置下行频点");
                }
                if (sendListBlack.size() == 0) {
//                        Set1StatusBar("没有符合下行频点的IMSI");
                    ToastUtils.showToast("频点与IMSI运营商不符");
                } else {
                    byte[] outputData = MainUtilsThread.hexStringToByteArray(location(sendListBlack.get(0).getImsi(), "04", sa, context, 1));
                    try {
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(IP2);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    ;
                    DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                    Log.e("nzqsendstart1设置定位模式", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                    try {
                        socket.send(outputPacket);
//
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();

    }

    //设备1已开始发送
    private void sendrun(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("strDatanzq", "run: nzqsend" + strData);
                byte[] outputData = MainUtilsThread.hexStringToByteArray(strData.toString());
                try {
                    socket = new DatagramSocket();
                    address = InetAddress.getByName(IP1);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                ;
                DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                Log.e("nzqsendstaart1Black", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                try {
                    socket.send(outputPacket);
//                    interrupted();
                    try {
                        Thread.sleep(2000);
                        sb1Locations(sendListBlack, tf1, spinnerS1, context, listImsiListTrue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sb1Locations(final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String sa = "";
                if (tf1.equals("TDD")) {
                    sa = "01";
                }
                if (tf1.equals("FDD")) {
                    sa = "00";
                }
                DatagramSocket socket = null;
                InetAddress address = null;//你本机的ip地址
                Log.e("nzq", "run: nzqsend");

                if (!TextUtils.isEmpty(spinnerS1)) {
                    String yy = "";
                    DBManagerPinConfig dbManagerPinConfig = null;
                    List<PinConfigBean> pinConfigBeans;
                    try {
                        dbManagerPinConfig = new DBManagerPinConfig(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1));//查询对应的频点
                        yy = pinConfigBeans.get(0).getYy();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                        Log.d(TAG, "sendBlackList:移动 ");
                    if (listImsiListTrue.size() > 0 && listImsiListTrue != null) {
                        for (int i = 0; i < listImsiListTrue.size(); i++) {
                            if (listImsiListTrue.get(i).getYy().equals(yy)) {
                                sendListBlack.add(listImsiListTrue.get(i));
                            }
                        }
                    }

                    if (sendListBlack.size() > 0 && sendListBlack != null) {
//                            Log.d(TAG, "sendBlackList: " + sendListBlack);
                        if (sendListBlack.size() > 20) {
                            ToastUtils.showToast("符合条件的黑名单列表大于20");
                        } else {
//                                sendBlackListRun(sendListBlack);
                        }
//
                    } else {
                        ToastUtils.showToast("频点与IMSI运营商不符");
                    }

                } else {
                    ToastUtils.showToast("请先设置下行频点");
                }
                if (sendListBlack.size() == 0) {
//                        Set1StatusBar("没有符合下行频点的IMSI");
                    ToastUtils.showToast("频点与IMSI运营商不符");
                } else {
                    byte[] outputData = MainUtilsThread.hexStringToByteArray(location(sendListBlack.get(0).getImsi(), "04", sa, context, 1));
                    try {
                        socket = new DatagramSocket();
                        address = InetAddress.getByName(IP1);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    ;
                    DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                    Log.e("nzqsendstart1设置定位模式", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                    try {
                        socket.send(outputPacket);
//
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();

    }

}
