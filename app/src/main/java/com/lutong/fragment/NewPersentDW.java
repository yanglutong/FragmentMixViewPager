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
                tv_title.setText("?????????????????????????");
                ip = IP1;
            }
            if (i == 2) {
                tv_title.setText("?????????????????????????");
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

                    string = "????????????";

//                //????????????
//                try {
//                    dbManagerLog = new DBManagerLog(context);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                LogBean logBean = new LogBean();
//                logBean.setAssociated(LoginUtils.getId(context) + "");//??????ID
//                logBean.setEvent(LoginUtils.setBase64(string));//????????????
//                logBean.setSb(LoginUtils.setBase64("1"));
//                String format = sdf.format(new Date());//????????????
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
            //????????????Activity???????????????
            Window dialogWindow = dialog.getWindow();
            //??????Dialog?????????????????????
            dialogWindow.setGravity(Gravity.CENTER);
            //?????????????????????
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
            dialogWindow.setAttributes(lp);
            dialog.show();//???????????????
        }else {

            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            String ip = IP1;
            if (i == 1) {
                tv_title.setText("???????????????????????????????");
                ip = IP1;
            }
            if (i == 2) {
                tv_title.setText("?????????????????????????");
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
//                    string = "????????????";
                       viewS.bt_startFalg(false);
//                //????????????
//                try {
//                    dbManagerLog = new DBManagerLog(context);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                LogBean logBean = new LogBean();
//                logBean.setAssociated(LoginUtils.getId(context) + "");//??????ID
//                logBean.setEvent(LoginUtils.setBase64(string));//????????????
//                logBean.setSb(LoginUtils.setBase64("1"));
//                String format = sdf.format(new Date());//????????????
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
            //????????????Activity???????????????
            Window dialogWindow = dialog.getWindow();
            //??????Dialog?????????????????????
            dialogWindow.setGravity(Gravity.CENTER);
            //?????????????????????
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
            dialogWindow.setAttributes(lp);
            dialog.show();//???????????????



        }

    }

    @Override
    public void setGKtart(final Context context, final String tf1, final String tf2, final String sb1, final String sb2, int GuankongType) {
        if (GuankongType == 1) {//?????????1
            if (TextUtils.isEmpty(tf1)) {
                ToastUtils.showToast("??????1?????????");
                return;
            }
            if (TextUtils.isEmpty(tf2)) {
                ToastUtils.showToast("??????2?????????");
                return;
            }

            if (!sb1.equals("??????")) {
                ToastUtils.showToast("??????1??????????????????");
                return;
            }
            if (!sb2.equals("??????")) {
                ToastUtils.showToast("??????2??????????????????");
                return;
            }
            Log.d("setGKtart", "setGKtart: ");
            //---------------------------------???????????????????????????
            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            tv_title.setText("??????????????????????");
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("setGKtart", "setGKtart:?????????????????????? ");
                    try {
                        DBManagerAddParam dbManagerAddParamA = new DBManagerAddParam(context);
                        List<AddPararBean> dataAlla = dbManagerAddParamA.getDataAll();
                        List<AddPararBean> listImsiListTrue = new ArrayList<>();
                        Log.d("setGKtartaddPararBeans", "???????????????--init: " + dataAlla.toString());

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
                                    if (listImsiListTrue.get(j).getYy().equals("??????")) {
                                        sendwhitelist.add(listImsiListTrue.get(j));
                                    }
                                }
                                Log.d("+sendwhitelist", "onClick: " + sendwhitelist);
                                if (sendwhitelist.size() > 0) {
                                    if (!tf1.equals("TDD")) {
                                        ToastUtils.showToast("??????1???????????????FDD");
                                        viewS.gkqiehuan(tf1, 1);
//                return;
                                    }
                                    if (!tf2.equals("FDD")) {
                                        ToastUtils.showToast("??????2???????????????TDD");
                                        viewS.gkqiehuan(tf2, 2);
//                return;
                                    }
//                                    if (!sb1.equals("??????")) {
////                ToastUtils.showToast("??????1??????????????????");
//                                        return;
//                                    }
//                                    if (!sb2.equals("??????")) {
////                ToastUtils.showToast("??????2??????????????????");
//                                        return;
//                                    }
                                    sendwhiteListRun(sendwhitelist, tf1, tf2, context, listImsiListTrue);//???????????????  2???

                                } else {
                                    if (!tf1.equals("TDD")) {
                                        ToastUtils.showToast("??????1???????????????FDD");
                                        viewS.gkqiehuan(tf1, 1);
//                return;
                                    }
                                    if (!tf2.equals("FDD")) {
                                        ToastUtils.showToast("??????2???????????????TDD");
                                        viewS.gkqiehuan(tf2, 2);
//                return;
                                    }
//                                    if (!sb1.equals("??????")) {
////                ToastUtils.showToast("??????1??????????????????");
//                                        return;
//                                    }
//                                    if (!sb2.equals("??????")) {
////                ToastUtils.showToast("??????2??????????????????");
//                                        return;
//                                    }
                                    sendwhiteListRunDELE(sendwhitelist, tf1, tf2, context, listImsiListTrue);//???????????????  2???

                                }


                            } else {
//                                ToastUtils.showToast("?????????????????????IMSI");

                                List<AddPararBean> sendwhitelist = new ArrayList<>();
                                sendwhiteListRunDELE(sendwhitelist, tf1, tf2, context, listImsiListTrue);//???????????????  2???

                            }
                        } else {//???????????????
                            if (tf1.equals("TDD") && tf2.equals("FDD")) {
                                List<AddPararBean> sendwhitelist = new ArrayList<>();
                                sendwhiteListRunDELE(sendwhitelist, tf1, tf2, context, listImsiListTrue);//???????????????  2???
                            }
                            if (!tf1.equals("TDD")) {
                                ToastUtils.showToast("??????1???????????????FDD");
                                viewS.gkqiehuan(tf1, 1);
//                return;
                            } else {

                            }
                            if (!tf2.equals("FDD")) {
                                ToastUtils.showToast("??????2???????????????TDD");
                                viewS.gkqiehuan(tf2, 2);
//                return;
                            } else {

                            }
                        }
                        Log.d("setGKtartaddPararBeans", "?????????init: " + listImsiListTrue.toString());
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
            //????????????Activity???????????????
            Window dialogWindow = dialog.getWindow();
            //??????Dialog?????????????????????
            dialogWindow.setGravity(Gravity.CENTER);
            //?????????????????????
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
            dialogWindow.setAttributes(lp);
            dialog.show();//???????????????

        }
        if (GuankongType == 2) {//?????????1
            if (TextUtils.isEmpty(tf1)) {
                ToastUtils.showToast("??????1?????????");
                return;
            }
            if (TextUtils.isEmpty(tf2)) {
                ToastUtils.showToast("??????2?????????");
                return;
            }

            if (!sb1.equals("??????")) {
                ToastUtils.showToast("??????1??????????????????");
                return;
            }
            if (!sb2.equals("??????")) {
                ToastUtils.showToast("??????2??????????????????");
                return;
            }
            Log.d("setGKtart", "setGKtart: ");
            //---------------------------------???????????????????????????
            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            tv_title.setText("??????????????????????");
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("setGKtart", "setGKtart:????????????????????? ");
                    try {
                        DBManagerAddParam dbManagerAddParamA = new DBManagerAddParam(context);
                        List<AddPararBean> dataAlla = dbManagerAddParamA.getDataAll();
                        List<AddPararBean> listImsiListTrue = new ArrayList<>();
                        Log.d("setGKtartaddPararBeans", "???????????????--init: " + dataAlla.toString());

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
                                    if (listImsiListTrue.get(j).getYy().equals("??????")) {
                                        sendwhitelistLT.add(listImsiListTrue.get(j));
                                    }
                                }
                                List<AddPararBean> sendwhitelistDX = new ArrayList<>();
                                for (int j = 0; j < listImsiListTrue.size(); j++) {
                                    if (listImsiListTrue.get(j).getYy().equals("??????")) {
                                        sendwhitelistDX.add(listImsiListTrue.get(j));
                                    }
                                }
                                if (!tf1.equals("FDD")) {
                                    ToastUtils.showToast("??????1???????????????TDD");
                                    viewS.gkqiehuan(tf1, 1);
//                                    return;
                                }
                                if (!tf2.equals("FDD")) {
                                    ToastUtils.showToast("??????2???????????????TDD");
                                    viewS.gkqiehuan(tf2, 2);
//                                    return;
                                }
                                if (sendwhitelistLT.size() > 0) {
                                    sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//???????????????  2???
                                } else {
                                    sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//???????????????  2???
                                }
                                if (sendwhitelistDX.size() > 0) {
                                    sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//???????????????  2???
                                } else {
                                    sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//???????????????  2???

                                }


                            } else {
//                                ToastUtils.showToast("?????????????????????IMSI");
                                List<AddPararBean> sendwhitelistLT = new ArrayList<>();
                                List<AddPararBean> sendwhitelistDX = new ArrayList<>();
//                                ToastUtils.showToast("111");

                                sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//???????????????  2???


                                sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//???????????????  2???


                            }
                        } else {
                            List<AddPararBean> sendwhitelistLT = new ArrayList<>();
                            List<AddPararBean> sendwhitelistDX = new ArrayList<>();
                            if (!tf1.equals("FDD")) {
                                ToastUtils.showToast("??????1???????????????TDD");
                                viewS.gkqiehuan(tf1, 1);
//                                    return;
                            } else {
                                sendwhiteListRunLT(sendwhitelistLT, tf1, tf2, context, listImsiListTrue);//???????????????  2???
                            }
                            if (!tf2.equals("FDD")) {
                                ToastUtils.showToast("??????2???????????????TDD");
                                viewS.gkqiehuan(tf2, 2);
//                                    return;
                            } else {
                                sendwhiteListRunDX(sendwhitelistDX, tf1, tf2, context, listImsiListTrue);//???????????????  2???
                            }
                        }
                        Log.d("setGKtartaddPararBeans", "?????????init: " + listImsiListTrue.toString());
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
            //????????????Activity???????????????
            Window dialogWindow = dialog.getWindow();
            //??????Dialog?????????????????????
            dialogWindow.setGravity(Gravity.CENTER);
            //?????????????????????
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
            dialogWindow.setAttributes(lp);
            dialog.show();//???????????????

        }
    }

    //???????????????
    private void sendwhiteListRunDELE(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {

        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
//        for (int i = 0; i < sendListBlack.size(); i++) {
//            list.add(sendListBlack.get(i).getImsi());
//        }
        Log.d("aaaaalist", "sendwhiteListRun: " + list);
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //?????????
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff00");

//        //???????????????
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

        ///????????????15??? 1

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
            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????
            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????

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
        //?????????
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff01");
        //???????????????
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
//            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????
            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????

        }
    }

    //???????????????
    private void sendwhiteListRun(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {

        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        Log.d("aaaaalist", "sendwhiteListRun: " + list);
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //?????????
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff01");

        //???????????????
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
            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????
            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????

        }
    }

    private void sendrunwhite2(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//????????????ip??????
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
                Log.e("nzqsendstart1Black", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());
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
        //?????????
        StringBuffer str = new StringBuffer("aaaa555539f0b900000000ff01");
        //???????????????
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
            sendrunwhite(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????
//            sendrunwhite2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????

        }
    }

    //??????1???????????????
    private void sendrunwhite(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//????????????ip??????
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
                Log.e("nzqsendstart1Black", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());
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
        // ????????????
        textToSpeech.setSpeechRate(8.01f);
        textToSpeech.speak(data,//??????????????????????????????????????????????????????
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
                        ToastUtils.showToast("??????1??????????????????");
                        return;
                    }
                    try {
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1)); //?????????????????????

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
                        forid = dbManager01.forid(1);  //????????????1

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (pinConfigBeans == null) {
//                    ToastUtils.showToast("??????????????????");
//                    Set1StatusBar("??????????????????");
//                        viewS.buildSdError("??????????????????", 1);
                    }
                    if (forid == null) {
//                    ToastUtils.showToast("??????1????????????");
//                    Set1StatusBar("??????1????????????");
//                        viewS.buildSdError("??????1????????????", 1);
                        return;
                    }
                    DatagramSocket socket = null;
                    InetAddress address = null;//????????????ip??????
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
                                if (listImsiListTrue.get(i).getYy().equals("??????")) {
                                    listyd.add(listImsiListTrue.get(i).getImsi());
                                }
                            }
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals("??????")) {
                                    listlt.add(listImsiListTrue.get(i).getImsi());
                                }
                            }
                            for (int i = 0; i < listImsiListTrue.size(); i++) {
                                if (listImsiListTrue.get(i).getYy().equals("??????")) {
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
                                Log.e("startLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

                                try {
                                    if (sb1.equals("?????????")) {

                                    } else {
                                        socket.send(outputPacket);
                                        Log.e("socketstartLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

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
                                Log.e("startLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

                                try {
                                    if (sb1.equals("?????????")) {

                                    } else {
                                        socket.send(outputPacket);
                                        Log.e("socketstartLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

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
                                Log.e("startLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

                                try {
                                    if (sb1.equals("?????????")) {

                                    } else {
                                        socket.send(outputPacket);
                                        Log.e("socketstartLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

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
                            Log.e("startLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

                            try {
                                if (sb1.equals("?????????")) {

                                } else {
                                    socket.send(outputPacket);
                                    Log.e("socketstartLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

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
                        ToastUtils.showToast("??????2??????????????????");
                        return;
                    }
                    try {
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1)); //?????????????????????

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
                        forid = dbManager01.forid(2);  //????????????1

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (pinConfigBeans == null) {
//                    ToastUtils.showToast("??????????????????");
//                    Set1StatusBar("??????????????????");
//                        viewS.buildSdError("??????????????????", 1);
                    }
                    if (forid == null) {
//                    ToastUtils.showToast("??????1????????????");
//                    Set1StatusBar("??????1????????????");
//                        viewS.buildSdError("??????1????????????", 1);
                        return;
                    }
                    DatagramSocket socket = null;
                    InetAddress address = null;//????????????ip??????
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
                    Log.e("startLocation1s????????????1", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

                    try {
                        if (sb1.equals("?????????")) {

                        } else {
                            socket.send(outputPacket);
                            Log.e("socketstartLocation1s????????????2", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());

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
     * ??????????????????
     *
     * @param context
     * @param device
     * @param tf
     * @param down
     */
    @Override
    public void saopinjianlixiaoqu(Context context, int device, String tf, String down) {
        if (device == 1) {
            if (tf.equals("?????????")) {

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
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(down)); //?????????????????????
                yy = pinConfigBeans.get(0).getYy();
                Log.d("qwqea", "saopinjianlixiaoqu: " + yy);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Log.d(TAG, "sendBlackList:?????? ");
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
//                    if (listImsiListTrue.get(i).getYy().equals(yy)) { //??? ?????????????????????
//                        sendListBlack.add(listImsiListTrue.get(i));
//                    }
                    if (listImsiListTrue.get(i).isCheckbox()) { //??? ????????? IMSI??????
                        sendListBlack.add(listImsiListTrue.get(i));
                    }
                }
            }

            if (sendListBlack.size() > 0 && sendListBlack != null) {
//                Log.d(TAG, "sendBlackList: " + sendListBlack);
                if (sendListBlack.size() > 20) {
                    ToastUtils.showToast("????????????????????????????????????20");
                } else {
                    sendBlackListRun(sendListBlack, tf, down, context, listImsiListTrue);
                }
//
            } else {
                viewS.MesageV(1);
                ToastUtils.showToast("?????????IMSI???????????????");
            }
        }

        //??????2
        if (device == 2) {
            if (tf.equals("?????????")) {

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
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(down)); //?????????????????????
                yy = pinConfigBeans.get(0).getYy();
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Log.d(TAG, "sendBlackList:?????? ");
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
                    ToastUtils.showToast("????????????????????????????????????20");
                } else {
                    sendBlackListRun2(sendListBlack, tf, down, context, listImsiListTrue);
                }
//
            } else {
                viewS.MesageV(2);
                ToastUtils.showToast("?????????IMSI???????????????");
            }
        }
    }


    public void startsd(boolean b, int device, final String tf, final Context context, final String spinnerDown, String sbzhuangtai) {
        Log.d(TAG, "startSD: " + device + "tf==" + tf + "---" + spinnerDown);
        if (device == 1) {
            if (b) {
                if (TextUtils.isEmpty(tf)) {
                    ToastUtils.showToast("??????1?????????");
                    return;
                }
                if (TextUtils.isEmpty(sbzhuangtai)) {
                    ToastUtils.showToast("??????1??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(spinnerDown)) {
                    ToastUtils.showToast("??????1????????????????????????");
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
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //?????????????????????
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//??????IMSI???????????????
                List<AddPararBean> listImsiListTrue = null;//????????????????????????imsi
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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                                ToastUtils.showToast("????????????????????????????????????20");
                            } else {

                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("?????????????????????1????");
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
                                //????????????Activity???????????????
                                Window dialogWindow = dialog.getWindow();
                                //??????Dialog?????????????????????
                                dialogWindow.setGravity(Gravity.CENTER);
                                //?????????????????????
                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                                dialogWindow.setAttributes(lp);
                                dialog.show();//???????????????
                            }
//
                        } else {

                            ToastUtils.showToast("?????????IMSI???????????????");
                        }

                    } catch (Exception e) {
                    }
                } else {//???????????????
//                                ToastUtils.showToast("??????1???????????????");


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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                                ToastUtils.showToast("????????????????????????????????????20");
                            } else {
                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("??????1????????????????????????????");
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
                                //????????????Activity???????????????
                                Window dialogWindow = dialog.getWindow();
                                //??????Dialog?????????????????????
                                dialogWindow.setGravity(Gravity.CENTER);
                                //?????????????????????
                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                                dialogWindow.setAttributes(lp);
                                dialog.show();//???????????????

                            }
//
                        } else {

                            ToastUtils.showToast("?????????IMSI???????????????");
                            return;
                        }

                    } catch (Exception e) {
                    }


                    return;
                }
            } else {   //?????????

//                if (TextUtils.isEmpty(tf)) {
//                    ToastUtils.showToast("??????1?????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(sbzhuangtai)) {
//                    ToastUtils.showToast("??????1??????????????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(spinnerDown)) {
//                    ToastUtils.showToast("??????1????????????????????????");
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
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //?????????????????????
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//??????IMSI???????????????
                List<AddPararBean> listImsiListTrue = null;//????????????????????????imsi
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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                                ToastUtils.showToast("????????????????????????????????????20");
                            } else {
                                final List<AddPararBean> finalSendListBlack = sendListBlack;
                                final List<AddPararBean> finalListImsiListTrue = listImsiListTrue;
                                sendBlackListRun(finalSendListBlack, tf, spinnerDown, context, finalListImsiListTrue);
                            }
//
                        } else {

                            ToastUtils.showToast("?????????IMSI???????????????");
                        }

                    } catch (Exception e) {
                    }
                }

            }


        }
        //??????2
        if (device == 2) {
            if (b) {
                if (TextUtils.isEmpty(tf)) {
                    ToastUtils.showToast("??????2?????????");
                    return;
                }
                if (TextUtils.isEmpty(sbzhuangtai)) {
                    ToastUtils.showToast("??????2??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(spinnerDown)) {
                    ToastUtils.showToast("??????2????????????????????????");
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
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //?????????????????????
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//??????IMSI???????????????
                List<AddPararBean> listImsiListTrue = null;//????????????????????????imsi
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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                                ToastUtils.showToast("????????????????????????????????????20");
                            } else {

                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("?????????????????????2????");
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
                                //????????????Activity???????????????
                                Window dialogWindow = dialog.getWindow();
                                //??????Dialog?????????????????????
                                dialogWindow.setGravity(Gravity.CENTER);
                                //?????????????????????
                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                                dialogWindow.setAttributes(lp);
                                dialog.show();//???????????????
                            }
//
                        } else {

                            ToastUtils.showToast("?????????IMSI???????????????");
                        }

                    } catch (Exception e) {
                    }
                } else {//???????????????
//                                ToastUtils.showToast("??????1???????????????");


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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                                ToastUtils.showToast("????????????????????????????????????20");
                            } else {
                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                                TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                                tv_title.setText("??????2????????????????????????????");
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
                                //????????????Activity???????????????
                                Window dialogWindow = dialog.getWindow();
                                //??????Dialog?????????????????????
                                dialogWindow.setGravity(Gravity.CENTER);
                                //?????????????????????
                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                                dialogWindow.setAttributes(lp);
                                dialog.show();//???????????????

                            }
//
                        } else {

                            ToastUtils.showToast("?????????IMSI???????????????");
                            return;
                        }

                    } catch (Exception e) {
                    }


                    return;
                }
            } else {//??????2?????????

//                if (TextUtils.isEmpty(tf)) {
//                    ToastUtils.showToast("??????2?????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(sbzhuangtai)) {
//                    ToastUtils.showToast("??????2??????????????????");
//                    return;
//                }
//                if (TextUtils.isEmpty(spinnerDown)) {
//                    ToastUtils.showToast("??????2????????????????????????");
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
                    pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown)); //?????????????????????
                    yy = pinConfigBeans.get(0).getYy();
                    sb1zhishi = pinConfigBeans.get(0).getTf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                DBManagerAddParam dbManagerAddParam = null;
                List<AddPararBean> dataAll = null;//??????IMSI???????????????
                List<AddPararBean> listImsiListTrue = null;//????????????????????????imsi
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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                                ToastUtils.showToast("????????????????????????????????????20");
                            } else {

//                                dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
//                                inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
//                                TextView tv_title = inflate.findViewById(R.id.tv_title);
////            String ip=IP1;
//
//                                tv_title.setText("?????????????????????2????");
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
//                                //????????????Activity???????????????
//                                Window dialogWindow = dialog.getWindow();
//                                //??????Dialog?????????????????????
//                                dialogWindow.setGravity(Gravity.CENTER);
//                                //?????????????????????
//                                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
////                           lp.y = 20;//??????Dialog?????????????????????
////                          ????????????????????????
//                                dialogWindow.setAttributes(lp);
//                                dialog.show();//???????????????
                            }
//
                        } else {

                            ToastUtils.showToast("?????????IMSI???????????????");
                        }

                    } catch (Exception e) {
                    }
                }


            }

        }

    }

    public void startsdquanbu(int device, final String tf1, final String tf2, final Context context, final String spinnerDown1, String spinnerDown2, String sbzhuangtai1, String sbzhuangtai2) {
        Log.d(TAG, "startSD: " + device + "tf==" + tf1 + "---" + spinnerDown1);
        //??????1
        if (device == 3) {
            if (TextUtils.isEmpty(tf1)) {
                ToastUtils.showToast("??????1?????????");
                return;
            }
            if (TextUtils.isEmpty(sbzhuangtai1)) {
                ToastUtils.showToast("??????1??????????????????");
                return;
            }
            if (TextUtils.isEmpty(spinnerDown1)) {
                ToastUtils.showToast("??????1????????????????????????");
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
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown1)); //?????????????????????
                yy = pinConfigBeans.get(0).getYy();
                sb1zhishi = pinConfigBeans.get(0).getTf();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManagerAddParam dbManagerAddParam = null;
            List<AddPararBean> dataAll = null;//??????IMSI???????????????
            List<AddPararBean> listImsiListTrue = null;//????????????????????????imsi
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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                            ToastUtils.showToast("????????????????????????????????????20");
                        } else {

                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                            tv_title.setText("?????????????????????????");
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
                            //????????????Activity???????????????
                            Window dialogWindow = dialog.getWindow();
                            //??????Dialog?????????????????????
                            dialogWindow.setGravity(Gravity.CENTER);
                            //?????????????????????
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                            dialogWindow.setAttributes(lp);
                            dialog.show();//???????????????
                        }
//
                    } else {

                        ToastUtils.showToast("?????????IMSI???????????????");
                    }

                } catch (Exception e) {
                }
            } else {//???????????????
//                                ToastUtils.showToast("??????1???????????????");


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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                            ToastUtils.showToast("????????????????????????????????????20");
                        } else {
                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                            tv_title.setText("??????1????????????????????????????");


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
                            //????????????Activity???????????????
                            Window dialogWindow = dialog.getWindow();
                            //??????Dialog?????????????????????
                            dialogWindow.setGravity(Gravity.CENTER);
                            //?????????????????????
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                            dialogWindow.setAttributes(lp);
                            dialog.show();//???????????????

                        }
//
                    } else {

                        ToastUtils.showToast("?????????IMSI???????????????");
                        return;
                    }

                } catch (Exception e) {
                }


                return;
            }
        }
        //??????2
        if (device == 3) {
            if (TextUtils.isEmpty(tf2)) {
                ToastUtils.showToast("??????2?????????");
                return;
            }
            if (TextUtils.isEmpty(sbzhuangtai2)) {
                ToastUtils.showToast("??????2??????????????????");
                return;
            }
            if (TextUtils.isEmpty(spinnerDown2)) {
                ToastUtils.showToast("??????2????????????????????????");
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
                pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerDown2)); //?????????????????????
                yy = pinConfigBeans.get(0).getYy();
                sb1zhishi = pinConfigBeans.get(0).getTf();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManagerAddParam dbManagerAddParam = null;
            List<AddPararBean> dataAll = null;//??????IMSI???????????????
            List<AddPararBean> listImsiListTrue = null;//????????????????????????imsi
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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                            ToastUtils.showToast("??????2????????????????????????????????????20");
                        } else {

//                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
//                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
//                            TextView tv_title = inflate.findViewById(R.id.tv_title);
////            String ip=IP1;
//
//                            tv_title.setText("?????????????????????2????");
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
//                            //????????????Activity???????????????
//                            Window dialogWindow = dialog.getWindow();
//                            //??????Dialog?????????????????????
//                            dialogWindow.setGravity(Gravity.CENTER);
//                            //?????????????????????
//                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
////                           lp.y = 20;//??????Dialog?????????????????????
////                          ????????????????????????
//                            dialogWindow.setAttributes(lp);
//                            dialog.show();//???????????????
                        }
//
                    } else {

                        ToastUtils.showToast("??????2?????????IMSI???????????????");
                    }

                } catch (Exception e) {
                }
            } else {//???????????????
//                                ToastUtils.showToast("??????1???????????????");


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
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                            ToastUtils.showToast("????????????????????????????????????20");
                        } else {
                            dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
//            String ip=IP1;

                            tv_title.setText("??????2????????????????????????????");
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
                            //????????????Activity???????????????
                            Window dialogWindow = dialog.getWindow();
                            //??????Dialog?????????????????????
                            dialogWindow.setGravity(Gravity.CENTER);
                            //?????????????????????
                            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//??????Dialog?????????????????????
//                          ????????????????????????
                            dialogWindow.setAttributes(lp);
                            dialog.show();//???????????????

                        }
//
                    } else {

                        ToastUtils.showToast("??????2?????????IMSI???????????????");
                        return;
                    }

                } catch (Exception e) {
                }


                return;
            }
        }

    }


    //???????????????
    private void sendBlackListRun(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {
        if (sendListBlack.size() == 0) {
            ToastUtils.showToast("?????????IMSI???????????????");
            return;
        }
        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //?????????
        StringBuffer str = new StringBuffer("aaaa555553f06401000000ff");
        //???????????????
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
            sendrun(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????

        }
    }

    //???????????????2
    private void sendBlackListRun2(List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, List<AddPararBean> listImsiListTrue) {
        if (sendListBlack.size() == 0) {
            ToastUtils.showToast("?????????IMSI???????????????");
            return;
        }
        List<String> list = new ArrayList<>();
//        Log.d(TAG, "sendBlacListRun:list" + list);
        for (int i = 0; i < sendListBlack.size(); i++) {
            list.add(sendListBlack.get(i).getImsi());
        }
        int totalMy = list.size();
//        Log.d(TAG, "sendBlackListRun:totalMy" + totalMy);
        //?????????
        StringBuffer str = new StringBuffer("aaaa555553f06401000000ff");
        //???????????????
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
            sendrun2(str, sendListBlack, tf1, spinnerS1, context, listImsiListTrue);//????????????

        }
    }

    //??????2???????????????
    private void sendrun2(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//????????????ip??????
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
                Log.e("nzqsendstart2Black", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());
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

    //??????2???????????? ??????
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
                InetAddress address = null;//????????????ip??????
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
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1));//?????????????????????
                        yy = pinConfigBeans.get(0).getYy();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                            ToastUtils.showToast("????????????????????????????????????20");
                        } else {
//                                sendBlackListRun(sendListBlack);
                        }
//
                    } else {
                        ToastUtils.showToast("?????????IMSI???????????????");
                    }

                } else {
                    ToastUtils.showToast("????????????????????????");
                }
                if (sendListBlack.size() == 0) {
//                        Set1StatusBar("???????????????????????????IMSI");
                    ToastUtils.showToast("?????????IMSI???????????????");
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
                    Log.e("nzqsendstart1??????????????????", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());
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

    //??????1???????????????
    private void sendrun(final StringBuffer strData, final List<AddPararBean> sendListBlack, final String tf1, final String spinnerS1, final Context context, final List<AddPararBean> listImsiListTrue) {
//
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                InetAddress address = null;//????????????ip??????
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
                Log.e("nzqsendstaart1Black", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());
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
                InetAddress address = null;//????????????ip??????
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
                        pinConfigBeans = dbManagerPinConfig.queryData(Integer.parseInt(spinnerS1));//?????????????????????
                        yy = pinConfigBeans.get(0).getYy();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                        Log.d(TAG, "sendBlackList:?????? ");
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
                            ToastUtils.showToast("????????????????????????????????????20");
                        } else {
//                                sendBlackListRun(sendListBlack);
                        }
//
                    } else {
                        ToastUtils.showToast("?????????IMSI???????????????");
                    }

                } else {
                    ToastUtils.showToast("????????????????????????");
                }
                if (sendListBlack.size() == 0) {
//                        Set1StatusBar("???????????????????????????IMSI");
                    ToastUtils.showToast("?????????IMSI???????????????");
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
                    Log.e("nzqsendstart1??????????????????", "run: sendsocket?????????" + outputPacket.getPort() + "Ip??????:" + outputPacket.getAddress().toString() + "??????:" + outputPacket.getData());
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
