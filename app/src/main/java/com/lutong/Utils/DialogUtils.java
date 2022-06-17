package com.lutong.Utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lutong.OrmSqlLite.Bean.SpBean;
import com.lutong.OrmSqlLite.Bean.ZmBean;
import com.lutong.OrmSqlLite.DBManagerZM;
import com.lutong.R;
import com.lutong.fragment.IT.ZmDataCallBack;
import com.lutong.fragment.SaoPinCallback;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class DialogUtils {

    private ArrayList<ArrayList<String>> recordList;
    private static SpBean spBean1;
    private static SpBean spBean2;
    
//    /**
//     * @param sb             设备编号
//     * @param context        上下文
//     * @param inflate        布局
//     * @param saoPinCallback 回调函数
//     */
//    @SuppressLint("NewApi")
    public static void SaoPinDialog(final int sb, final Context context, View inflate, final SaoPinCallback saoPinCallback, String tf, boolean phonesp) {

        final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
        inflate = LayoutInflater.from(context).inflate(R.layout.saopin_list_item, null);
        spBean1 = new SpBean();//运营商1
        spBean2 = new SpBean();//运营商2

        final CheckBox cb_phone = inflate.findViewById(R.id.cb_phone);//扫描 -手机
        final CheckBox cb_device = inflate.findViewById(R.id.cb_device);//扫描 -设备
        //布局隐藏
        final LinearLayout ll_phone = inflate.findViewById(R.id.ll_phone);//手机扫描布局
        final LinearLayout ll_device = inflate.findViewById(R.id.ll_device);//设备扫描布局
        //布局隐藏的运营商1 2
        final CheckBox cb_yy1 = inflate.findViewById(R.id.cb_yy1);//扫描 -手机
        final CheckBox cb_yy2 = inflate.findViewById(R.id.cb_yy2);//扫描 -手机

        //手机布局里的ID
        //卡槽1
        final TextView tv1_yy = inflate.findViewById(R.id.tv1_yy);//设备扫描布局
        final TextView tv1_zhishi = inflate.findViewById(R.id.tv1_zhishi);//设备扫描布局
        final TextView tv1_EAR = inflate.findViewById(R.id.tv1_EAR);//设备扫描布局
        final TextView tv1_BAND = inflate.findViewById(R.id.tv1_BAND);//设备扫描布局
        final TextView tv1_TAC = inflate.findViewById(R.id.tv1_TAC);//设备扫描布局
        final TextView tv1_PCI = inflate.findViewById(R.id.tv1_PCI);//设备扫描布局
        final TextView tv1_CID = inflate.findViewById(R.id.tv1_CID);//设备扫描布局
        final TextView tv1_rsrp = inflate.findViewById(R.id.tv1_rsrp);//设备扫描布局
        final TextView tv1_rsrq = inflate.findViewById(R.id.tv1_rsrq);//设备扫描布局
        final TextView tv1_plmn = inflate.findViewById(R.id.tv1_plmn);//设备扫描布局


        //卡槽2
        final TextView tv2_yy = inflate.findViewById(R.id.tv2_yy);//设备扫描布局
        final TextView tv2_zhishi = inflate.findViewById(R.id.tv2_zhishi);//设备扫描布局
        final TextView tv2_EAR = inflate.findViewById(R.id.tv2_EAR);//设备扫描布局
        final TextView tv2_BAND = inflate.findViewById(R.id.tv2_BAND);//设备扫描布局
        final TextView tv2_TAC = inflate.findViewById(R.id.tv2_TAC);//设备扫描布局
        final TextView tv2_PCI = inflate.findViewById(R.id.tv2_PCI);//设备扫描布局
        final TextView tv2_CID = inflate.findViewById(R.id.tv2_CID);//设备扫描布局
        final TextView tv2_RSRP = inflate.findViewById(R.id.tv2_rsrp);//设备扫描布局

        final TextView tv2_rsrq = inflate.findViewById(R.id.tv2_rsrq);//设备扫描布局
        final TextView tv2_plmn = inflate.findViewById(R.id.tv2_plmn);//设备扫描布局
        ll_phone.setVisibility(View.GONE);
        ll_device.setVisibility(View.VISIBLE);
        Log.d("cb_device", "SaoPinDialog: " + sb);
        if (sb == 1) {
            cb_device.setText("设备1扫描");
        }
        if (sb == 2) {
            cb_device.setText("设备2扫描");
        }
        final List<SpBean> list1 = new ArrayList<>();
        final List<SpBean> list2 = new ArrayList<>();
        cb_phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_phone.setVisibility(View.VISIBLE);
                    ll_device.setVisibility(View.GONE);
                    cb_device.setChecked(false);
                    List<SpBean> gsmInfoList = null;
                    gsmInfoList = getGsmInfoList(context);
                    Log.d("nzqgsmInfoList", "SaoPinDialog: " + gsmInfoList);


                    if (gsmInfoList.size() > 0 && gsmInfoList != null) {//有数据
                        if (gsmInfoList.size() == 1) {//只有一条
                            String plmn = gsmInfoList.get(0).getPlmn();
                            String yy = MainUtils2.YY(plmn);
                            tv1_yy.setText(yy);
                            spBean1 = gsmInfoList.get(0);
                            spBean2 = null;


                            tv1_zhishi.setText("小区类型: LTE");
                            tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1.getPlmn()));
                            tv1_EAR.setText("EARFCN: " + spBean1.getDown());
                            tv1_TAC.setText("TAC: " + spBean1.getTac());
                            tv1_CID.setText("CID: " + spBean1.getCid());
                            String zs = "";
                            String band = MainUtils.getBand(Integer.parseInt(spBean1.getDown()));
                            if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
                                zs = "FDD";
                            } else {
                                zs = "TDD";
                            }
                            tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1.getDown())) + "(" + zs + ")");
                            tv1_PCI.setText("PCI:" + spBean1.getPci());
                            tv1_rsrp.setText("RSRP:" + spBean1.getRsrp());
                            tv1_rsrq.setText("RSRQ:" + spBean1.getRsrq());
                            tv1_plmn.setText("PLMN: " + spBean1.getPlmn());
                        } else {//多条
                            String plmn = gsmInfoList.get(0).getPlmn();
//                    String yy = MainUtils2.YY(plmn);
//                    tv1_yy.setText(yy);
                            String TAG = "数据判断";
                            for (int i = 0; i < gsmInfoList.size(); i++) {
                                if (gsmInfoList.get(i).getPlmn().equals(plmn)) {
                                    list1.add(gsmInfoList.get(i));
                                } else {
                                    list2.add(gsmInfoList.get(i));
                                }

                            }
                            if (list1.size() > 0 && list2.size() == 0) {//只有一个运营商1
                                spBean1 = list1.get(0);
                                spBean2 = null;
                                Log.d(TAG, "SaoPinDialog:只有一个运营商1 ");
                            } else if (list2.size() > 0 && list1.size() == 0) {////只有一个运营商1
                                spBean1 = list2.get(0);
                                spBean2 = null;
                                Log.d(TAG, "SaoPinDialog:只有一个运营商2 ");
                            } else if (list1.size() > 0 && list2.size() > 0) {//两个运营商都有
                                spBean1 = list1.get(0);
                                spBean2 = list2.get(0);
                                Log.d(TAG, "SaoPinDialog:两个运营商都有 ");
                            } else {//一个都没有
                                spBean1 = null;
                                spBean2 = null;
                                Log.d(TAG, "SaoPinDialog:一个都没有 ");
                            }
                            if (spBean1 != null) {
                                tv1_zhishi.setText("小区类型: LTE");
                                tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1.getPlmn()));
                                tv1_EAR.setText("EARFCN: " + spBean1.getDown());
                                tv1_TAC.setText("TAC: " + spBean1.getTac());
                                tv1_CID.setText("CID: " + spBean1.getCid());
                                String zs = "";
                                String band = MainUtils.getBand(Integer.parseInt(spBean1.getDown()));
                                if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
                                    zs = "FDD";
                                } else {
                                    zs = "TDD";
                                }
                                tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1.getDown())) + "(" + zs + ")");
                                tv1_PCI.setText("PCI:" + spBean1.getPci());
                                tv1_rsrp.setText("RSRP:" + spBean1.getRsrp());
                                tv1_rsrq.setText("RSRQ:" + spBean1.getRsrq());
                                tv1_plmn.setText("PLMN: " + spBean1.getPlmn());

                            }
                            if (spBean2 != null) {
                                tv2_zhishi.setText("小区类型: LTE");
                                tv2_yy.setText("运营商:" + MainUtils2.YY(spBean2.getPlmn()));
                                tv2_EAR.setText("EARFCN: " + spBean2.getDown());
                                tv2_TAC.setText("TAC: " + spBean2.getTac());
                                tv2_CID.setText("CID: " + spBean2.getCid());
                                String zs = "";
                                String band = MainUtils.getBand(Integer.parseInt(spBean2.getDown()));
                                if (band.equals("1") && band.equals("3") && band.equals("5") && band.equals("8")) {
                                    zs = "FDD";
                                } else {
                                    zs = "TDD";
                                }
                                tv2_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean2.getDown())) + "(" + zs + ")");
                                tv2_PCI.setText("PCI: " + spBean2.getPci());
                                tv2_RSRP.setText("RSRP: " + spBean2.getRsrp());
                                tv2_rsrq.setText("RSRQ: " + spBean2.getRsrq());
                                tv2_plmn.setText("PLMN:" + spBean2.getPlmn());
                            }


                        }

                    } else {//无数据
                        spBean1 = null;
                        spBean2 = null;
                    }
                } else {
                    cb_device.setChecked(true);
                    ll_phone.setVisibility(View.GONE);
                    ll_device.setVisibility(View.VISIBLE);
                }
            }
        });
        cb_device.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_phone.setVisibility(View.GONE);
                    ll_device.setVisibility(View.VISIBLE);
                    cb_phone.setChecked(false);
                } else {
                    cb_phone.setChecked(true);
                    ll_phone.setVisibility(View.VISIBLE);
                    ll_device.setVisibility(View.GONE);
                }
            }
        });
        //本手机扫描的checkbox 监控
        cb_yy1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_yy2.setChecked(false);
                } else {
                    cb_yy2.setChecked(true);
                }
            }
        });
        cb_yy2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_yy1.setChecked(false);
                } else {
                    cb_yy1.setChecked(true);
                }
            }
        });

        List<SpBean> list3 = new ArrayList<>();
//        gsmInfoList = getGsmInfoList(context);
//        Log.d("nzqgsmInfoList", "SaoPinDialog: " + gsmInfoList);
        boolean checked = cb_phone.isChecked();
//        if (checked) {
//            gsmInfoList = getGsmInfoList(context);
//            Log.d("nzqgsmInfoList", "SaoPinDialog: " + gsmInfoList);
//
//
//            if (gsmInfoList.size() > 0 && gsmInfoList != null) {//有数据
//                if (gsmInfoList.size() == 1) {//只有一条
//                    String plmn = gsmInfoList.get(0).getPlmn();
//                    String yy = MainUtils2.YY(plmn);
//                    tv1_yy.setText(yy);
//                    spBean1[0] = gsmInfoList.get(0);
//                    spBean2[0] = null;
//
//
//                    tv1_zhishi.setText("小区类型: LTE");
//                    tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1[0].getPlmn()));
//                    tv1_EAR.setText("EARFCN: " + spBean1[0].getDown());
//                    tv1_TAC.setText("TAC: " + spBean1[0].getTac());
//                    tv1_CID.setText("CID: " + spBean1[0].getCid());
//                    String zs = "";
//                    String band = MainUtils.getBand(Integer.parseInt(spBean1[0].getDown()));
//                    if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                        zs = "FDD";
//                    } else {
//                        zs = "TDD";
//                    }
//                    tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1[0].getDown())) + "(" + zs + ")");
//                    tv1_PCI.setText("PCI:" + spBean1[0].getPci());
//                    tv1_rsrp.setText("RSRP:" + spBean1[0].getRsrp());
//                    tv1_rsrq.setText("RSRQ:" + spBean1[0].getRsrq());
//                    tv1_plmn.setText("PLMN: " + spBean1[0].getPlmn());
//                } else {//多条
//                    String plmn = gsmInfoList.get(0).getPlmn();
////                    String yy = MainUtils2.YY(plmn);
////                    tv1_yy.setText(yy);
//                    String TAG = "数据判断";
//                    for (int i = 0; i < gsmInfoList.size(); i++) {
//                        if (gsmInfoList.get(i).getPlmn().equals(plmn)) {
//                            list1.add(gsmInfoList.get(i));
//                        } else {
//                            list2.add(gsmInfoList.get(i));
//                        }
//
//                    }
//                    if (list1.size() > 0 && list2.size() == 0) {//只有一个运营商1
//                        spBean1[0] = list1.get(0);
//                        spBean2[0] = null;
//                        Log.d(TAG, "SaoPinDialog:只有一个运营商1 ");
//                    } else if (list2.size() > 0 && list1.size() == 0) {////只有一个运营商1
//                        spBean1[0] = list2.get(0);
//                        spBean2[0] = null;
//                        Log.d(TAG, "SaoPinDialog:只有一个运营商2 ");
//                    } else if (list1.size() > 0 && list2.size() > 0) {//两个运营商都有
//                        spBean1[0] = list1.get(0);
//                        spBean2[0] = list2.get(0);
//                        Log.d(TAG, "SaoPinDialog:两个运营商都有 ");
//                    } else {//一个都没有
//                        spBean1[0] = null;
//                        spBean2[0] = null;
//                        Log.d(TAG, "SaoPinDialog:一个都没有 ");
//                    }
//                    if (spBean1[0] != null) {
//                        tv1_zhishi.setText("小区类型: LTE");
//                        tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1[0].getPlmn()));
//                        tv1_EAR.setText("EARFCN: " + spBean1[0].getDown());
//                        tv1_TAC.setText("TAC: " + spBean1[0].getTac());
//                        tv1_CID.setText("CID: " + spBean1[0].getCid());
//                        String zs = "";
//                        String band = MainUtils.getBand(Integer.parseInt(spBean1[0].getDown()));
//                        if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                            zs = "FDD";
//                        } else {
//                            zs = "TDD";
//                        }
//                        tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1[0].getDown())) + "(" + zs + ")");
//                        tv1_PCI.setText("PCI:" + spBean1[0].getPci());
//                        tv1_rsrp.setText("RSRP:" + spBean1[0].getRsrp());
//                        tv1_rsrq.setText("RSRQ:" + spBean1[0].getRsrq());
//                        tv1_plmn.setText("PLMN: " + spBean1[0].getPlmn());
//
//                    }
//                    if (spBean2[0] != null) {
//                        tv2_zhishi.setText("小区类型: LTE");
//                        tv2_yy.setText("运营商:" + MainUtils2.YY(spBean2[0].getPlmn()));
//                        tv2_EAR.setText("EARFCN: " + spBean2[0].getDown());
//                        tv2_TAC.setText("TAC: " + spBean2[0].getTac());
//                        tv2_CID.setText("CID: " + spBean2[0].getCid());
//                        String zs = "";
//                        String band = MainUtils.getBand(Integer.parseInt(spBean2[0].getDown()));
//                        if (band.equals("1") && band.equals("3") && band.equals("5") && band.equals("8")) {
//                            zs = "FDD";
//                        } else {
//                            zs = "TDD";
//                        }
//                        tv2_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean2[0].getDown())) + "(" + zs + ")");
//                        tv2_PCI.setText("PCI: " + spBean2[0].getPci());
//                        tv2_RSRP.setText("RSRP: " + spBean2[0].getRsrp());
//                        tv2_rsrq.setText("RSRQ: " + spBean2[0].getRsrq());
//                        tv2_plmn.setText("PLMN:" + spBean2[0].getPlmn());
//                    }
//
//
//                }
//
//            } else {//无数据
//                spBean1[0] = null;
//                spBean2[0] = null;
//            }
//        }
        //运营商选择
        final CheckBox cb_tdd = inflate.findViewById(R.id.cb_tdd);//移动TDD
        final CheckBox cb_fdd = inflate.findViewById(R.id.cb_fdd);//移动FDD
        final CheckBox cb_lt = inflate.findViewById(R.id.cb_lt);//联通 全部为FDD
        final CheckBox cb_ds = inflate.findViewById(R.id.cb_ds);//电信全部为TDD
        final ImageView iv_tdd = inflate.findViewById(R.id.iv_tdd);//
        final ImageView iv_fdd = inflate.findViewById(R.id.iv_fdd);//
        final ImageView iv_lt = inflate.findViewById(R.id.iv_lt);//
        final ImageView iv_ds = inflate.findViewById(R.id.iv_ds);//

        final TextView tv_tdd = inflate.findViewById(R.id.tv_tdd);//
        final TextView tv_fdd = inflate.findViewById(R.id.tv_fdd);//
        final TextView tv_lt = inflate.findViewById(R.id.tv_lt);//
        final TextView tv_ds = inflate.findViewById(R.id.tv_ds);//

        if (tf.equals("TDD")) {
            cb_tdd.setText("移动");
            iv_tdd.setBackground(context.getResources().getDrawable(R.mipmap.norest));
            tv_tdd.setText("");

            cb_fdd.setText("移动");
            iv_fdd.setBackground(context.getResources().getDrawable(R.mipmap.rest));
            tv_fdd.setText("");

            cb_lt.setText("联通 ");
            iv_lt.setBackground(context.getResources().getDrawable(R.mipmap.rest));
            tv_lt.setText("");

            cb_ds.setText("电信 ");
            iv_ds.setBackground(context.getResources().getDrawable(R.mipmap.rest));
            tv_ds.setText("");
        }
        if (tf.equals("FDD")) {
            cb_tdd.setText("移动");
            iv_tdd.setBackground(context.getResources().getDrawable(R.mipmap.rest));
            tv_tdd.setText("");

            cb_fdd.setText("移动");
            iv_fdd.setBackground(context.getResources().getDrawable(R.mipmap.norest));
            tv_fdd.setText("");

            cb_lt.setText("联通 ");
            iv_lt.setBackground(context.getResources().getDrawable(R.mipmap.norest));
            tv_lt.setText("");
            cb_ds.setText("电信 ");
            iv_ds.setBackground(context.getResources().getDrawable(R.mipmap.norest));
            tv_ds.setText("");
        }
        cb_tdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_fdd.setChecked(false);
                    cb_lt.setChecked(false);
                    cb_ds.setChecked(false);
                }
            }
        });
        cb_fdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_tdd.setChecked(false);
                    cb_lt.setChecked(false);
                    cb_ds.setChecked(false);
                }
            }
        });
        cb_lt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_tdd.setChecked(false);
                    cb_fdd.setChecked(false);
                    cb_ds.setChecked(false);
                }
            }
        });
        cb_ds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cb_tdd.setChecked(false);
                    cb_fdd.setChecked(false);
                    cb_lt.setChecked(false);
                }
            }
        });
        Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
//        final SpBean finalSpBean1 = spBean1[0];
//        final SpBean finalSpBean2 = spBean2[0];
//        final List<SpBean> finalGsmInfoList = gsmInfoList;
        bt_confirm.setOnClickListener(new View.OnClickListener() {// 确定按钮的监听
            @Override
            public void onClick(View view) {
                if (sb == 1) {
                    if (cb_phone.isChecked()) {//如果手机扫描
                        if (cb_yy1.isChecked()) {//如过选择运营商1
                            if (spBean1 != null) {
//                                Log.d(TAG, "onClick: ");
//                                ToastUtils.showToast("运营商1有数据");
//                                saoPinCallback.sucessphone(1, spBean1.getDown(), spBean1, true);
//                                Log.d("000qqa", "onClick: "+ spBean1.getDown()+spBean1.toString()+"");
                                dialog.dismiss();
                                dialog.cancel();
//                                SPBEANLIST1 = finalGsmInfoList;
                            } else {
                                ToastUtils.showToast("运营商1无数据");
                            }
                        } else {//如过选择运营商2
                            if (spBean2 != null) {
//                                ToastUtils.showToast("运营商2有数据");
//                                saoPinCallback.sucessphone(1, spBean2.getDown(), spBean2, true);
                                dialog.dismiss();
                                dialog.cancel();
//                                SPBEANLIST1 = finalGsmInfoList;

                            } else {
                                ToastUtils.showToast("运营商2无数据");
                            }
                        }
                    } else {//如果是设备扫描
                        int i = 0;
                        if (cb_tdd.isChecked()) {
                            i = 1;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else if (cb_fdd.isChecked()) {
                            i = 2;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else if (cb_lt.isChecked()) {
                            i = 3;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else if (cb_ds.isChecked()) {
                            i = 4;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else {
                            i = 0;
//                            saoPinCallback.error();
                        }
                    }
                } else {//设备2
                    if (cb_phone.isChecked()) {//如果手机扫描
                        if (cb_yy1.isChecked()) {//如过选择运营商1
                            if (spBean1 != null) {
//                                ToastUtils.showToast("运营商1有数据");
//                                saoPinCallback.sucessphone(2, spBean1.getDown(), spBean1, true);
                                dialog.dismiss();
                                dialog.cancel();
//                                SPBEANLIST2 = finalGsmInfoList;
                            } else {
                                ToastUtils.showToast("运营商1无数据");
                            }
                        } else {//如过选择运营商2
                            if (spBean2 != null) {
//                                ToastUtils.showToast("运营商2有数据");
//                                saoPinCallback.sucessphone(2, spBean2.getDown(), spBean2, true);
                                dialog.dismiss();
                                dialog.cancel();
//                                SPBEANLIST2 = finalGsmInfoList;
                            } else {
                                ToastUtils.showToast("运营商2无数据");
                            }
                        }
                    } else {//如果是设备扫描
                        int i = 0;
                        if (cb_tdd.isChecked()) {
                            i = 1;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else if (cb_fdd.isChecked()) {
                            i = 2;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else if (cb_lt.isChecked()) {
                            i = 3;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else if (cb_ds.isChecked()) {
                            i = 4;
                            saoPinCallback.sucess(sb, i);
                            dialog.dismiss();
                            dialog.cancel();
                        } else {
                            i = 0;
//                            saoPinCallback.error();
                        }
                    }

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

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

    /**
     * @param sb             设备编号
     * @param context        上下文
     * @param inflate        布局
     * @param saoPinCallback 回调函数
     */
    //    public static void SaoPinDialog2(final int sb, final Context context, View inflate, final SaoPinCallback saoPinCallback, String tf, boolean phonesp, String dw) {
//        final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
//        inflate = LayoutInflater.from(context).inflate(R.layout.saopin_list_item, null);
//        spBean1 = new SpBean();//运营商1
//        spBean2 = new SpBean();//运营商2
//        List<SpBean> gsmInfoList = null;
//        final CheckBox cb_phone = inflate.findViewById(R.id.cb_phone);//扫描 -手机
//        final CheckBox cb_device = inflate.findViewById(R.id.cb_device);//扫描 -设备
//        //布局隐藏
//        final LinearLayout ll_phone = inflate.findViewById(R.id.ll_phone);//手机扫描布局
//        final LinearLayout ll_device = inflate.findViewById(R.id.ll_device);//设备扫描布局
//        //布局隐藏的运营商1 2
//        final CheckBox cb_yy1 = inflate.findViewById(R.id.cb_yy1);//扫描 -手机
//        final CheckBox cb_yy2 = inflate.findViewById(R.id.cb_yy2);//扫描 -手机
//
//        //手机布局里的ID
//        //卡槽1
//        final TextView tv1_yy = inflate.findViewById(R.id.tv1_yy);//设备扫描布局
//        final TextView tv1_zhishi = inflate.findViewById(R.id.tv1_zhishi);//设备扫描布局
//        final TextView tv1_EAR = inflate.findViewById(R.id.tv1_EAR);//设备扫描布局
//        final TextView tv1_BAND = inflate.findViewById(R.id.tv1_BAND);//设备扫描布局
//        final TextView tv1_TAC = inflate.findViewById(R.id.tv1_TAC);//设备扫描布局
//        final TextView tv1_PCI = inflate.findViewById(R.id.tv1_PCI);//设备扫描布局
//        final TextView tv1_CID = inflate.findViewById(R.id.tv1_CID);//设备扫描布局
//        final TextView tv1_rsrp = inflate.findViewById(R.id.tv1_rsrp);//设备扫描布局
//        final TextView tv1_rsrq = inflate.findViewById(R.id.tv1_rsrq);//设备扫描布局
//        final TextView tv1_plmn = inflate.findViewById(R.id.tv1_plmn);//设备扫描布局
//
//
//        //卡槽2
//        final TextView tv2_yy = inflate.findViewById(R.id.tv2_yy);//设备扫描布局
//        final TextView tv2_zhishi = inflate.findViewById(R.id.tv2_zhishi);//设备扫描布局
//        final TextView tv2_EAR = inflate.findViewById(R.id.tv2_EAR);//设备扫描布局
//        final TextView tv2_BAND = inflate.findViewById(R.id.tv2_BAND);//设备扫描布局
//        final TextView tv2_TAC = inflate.findViewById(R.id.tv2_TAC);//设备扫描布局
//        final TextView tv2_PCI = inflate.findViewById(R.id.tv2_PCI);//设备扫描布局
//        final TextView tv2_CID = inflate.findViewById(R.id.tv2_CID);//设备扫描布局
//        final TextView tv2_RSRP = inflate.findViewById(R.id.tv2_rsrp);//设备扫描布局
//
//        final TextView tv2_rsrq = inflate.findViewById(R.id.tv2_rsrq);//设备扫描布局
//        final TextView tv2_plmn = inflate.findViewById(R.id.tv2_plmn);//设备扫描布局
//        ll_phone.setVisibility(View.GONE);
//        ll_device.setVisibility(View.VISIBLE);
//
//        final Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
//        final Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
////        bt_confirm.setEnabled(false);
//        Log.d("cb_device", "SaoPinDialog: " + sb);
//        if (sb == 1) {
//            cb_device.setText("设备1扫描");
//        }
//        if (sb == 2) {
//            cb_device.setText("设备2扫描");
//        }
//        final List<SpBean> list1 = new ArrayList<>();
//        final List<SpBean> list2 = new ArrayList<>();
//        cb_phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                if (b) {
////                    ll_phone.setVisibility(View.VISIBLE);
////                    ll_device.setVisibility(View.GONE);
////                    cb_device.setChecked(false);
//////                    bt_confirm.setVisibility(View.GONE);
//////                    bt_confirm.setEnabled(false);
////
////
////
////
////
////
////
////                } else {
////                    cb_device.setChecked(true);
////                    ll_phone.setVisibility(View.GONE);
////                    ll_device.setVisibility(View.VISIBLE);
//////                    bt_confirm.setEnabled(true);
////                }
//                if (b) {
//                    ll_phone.setVisibility(View.VISIBLE);
//                    ll_device.setVisibility(View.GONE);
//                    cb_device.setChecked(false);
//                    List<SpBean> gsmInfoList = null;
//                    gsmInfoList = getGsmInfoList(context);
//                    Log.d("nzqgsmInfoList", "SaoPinDialog: " + gsmInfoList);
//
//
//                    if (gsmInfoList.size() > 0 && gsmInfoList != null) {//有数据
//                        if (gsmInfoList.size() == 1) {//只有一条
//                            String plmn = gsmInfoList.get(0).getPlmn();
//                            String yy = MainUtils2.YY(plmn);
//                            tv1_yy.setText(yy);
//                            spBean1 = gsmInfoList.get(0);
//                            spBean2 = null;
//                            tv1_zhishi.setText("小区类型: LTE");
//                            tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1.getPlmn()));
//                            tv1_EAR.setText("EARFCN: " + spBean1.getDown());
//                            tv1_TAC.setText("TAC: " + spBean1.getTac());
//                            tv1_CID.setText("CID: " + spBean1.getCid());
//                            String zs = "";
//                            String band = MainUtils.getBand(Integer.parseInt(spBean1.getDown()));
//                            if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                                zs = "FDD";
//                            } else {
//                                zs = "TDD";
//                            }
//                            tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1.getDown())) + "(" + zs + ")");
//                            tv1_PCI.setText("PCI:" + spBean1.getPci());
//                            tv1_rsrp.setText("RSRP:" + spBean1.getRsrp());
//                            tv1_rsrq.setText("RSRQ:" + spBean1.getRsrq());
//                            tv1_plmn.setText("PLMN: " + spBean1.getPlmn());
//                        } else {//多条
//                            String plmn = gsmInfoList.get(0).getPlmn();
////                    String yy = MainUtils2.YY(plmn);
////                    tv1_yy.setText(yy);
//                            String TAG = "数据判断";
//                            for (int i = 0; i < gsmInfoList.size(); i++) {
//                                if (gsmInfoList.get(i).getPlmn().equals(plmn)) {
//                                    list1.add(gsmInfoList.get(i));
//                                } else {
//                                    list2.add(gsmInfoList.get(i));
//                                }
//
//                            }
//                            if (list1.size() > 0 && list2.size() == 0) {//只有一个运营商1
//                                spBean1 = list1.get(0);
//                                spBean2 = null;
//                                Log.d(TAG, "SaoPinDialog:只有一个运营商1 ");
//                            } else if (list2.size() > 0 && list1.size() == 0) {////只有一个运营商1
//                                spBean1 = list2.get(0);
//                                spBean2 = null;
//                                Log.d(TAG, "SaoPinDialog:只有一个运营商2 ");
//                            } else if (list1.size() > 0 && list2.size() > 0) {//两个运营商都有
//                                spBean1 = list1.get(0);
//                                spBean2 = list2.get(0);
//                                Log.d(TAG, "SaoPinDialog:两个运营商都有 ");
//                            } else {//一个都没有
//                                spBean1 = null;
//                                spBean2 = null;
//                                Log.d(TAG, "SaoPinDialog:一个都没有 ");
//                            }
//                            if (spBean1 != null) {
//                                tv1_zhishi.setText("小区类型: LTE");
//                                tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1.getPlmn()));
//                                tv1_EAR.setText("EARFCN: " + spBean1.getDown());
//                                tv1_TAC.setText("TAC: " + spBean1.getTac());
//                                tv1_CID.setText("CID: " + spBean1.getCid());
//                                String zs = "";
//                                String band = MainUtils.getBand(Integer.parseInt(spBean1.getDown()));
//                                if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                                    zs = "FDD";
//                                } else {
//                                    zs = "TDD";
//                                }
//                                tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1.getDown())) + "(" + zs + ")");
//                                tv1_PCI.setText("PCI:" + spBean1.getPci());
//                                tv1_rsrp.setText("RSRP:" + spBean1.getRsrp());
//                                tv1_rsrq.setText("RSRQ:" + spBean1.getRsrq());
//                                tv1_plmn.setText("PLMN: " + spBean1.getPlmn());
//
//                            }
//                            if (spBean2 != null) {
//                                tv2_zhishi.setText("小区类型: LTE");
//                                tv2_yy.setText("运营商:" + MainUtils2.YY(spBean2.getPlmn()));
//                                tv2_EAR.setText("EARFCN: " + spBean2.getDown());
//                                tv2_TAC.setText("TAC: " + spBean2.getTac());
//                                tv2_CID.setText("CID: " + spBean2.getCid());
//                                String zs = "";
//                                String band = MainUtils.getBand(Integer.parseInt(spBean2.getDown()));
//                                if (band.equals("1") && band.equals("3") && band.equals("5") && band.equals("8")) {
//                                    zs = "FDD";
//                                } else {
//                                    zs = "TDD";
//                                }
//                                tv2_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean2.getDown())) + "(" + zs + ")");
//                                tv2_PCI.setText("PCI: " + spBean2.getPci());
//                                tv2_RSRP.setText("RSRP: " + spBean2.getRsrp());
//                                tv2_rsrq.setText("RSRQ: " + spBean2.getRsrq());
//                                tv2_plmn.setText("PLMN:" + spBean2.getPlmn());
//                            }
//
//
//                        }
//
//                    } else {//无数据
//                        spBean1 = null;
//                        spBean2 = null;
//                    }
//                } else {
//                    cb_device.setChecked(true);
//                    ll_phone.setVisibility(View.GONE);
//                    ll_device.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        if (dw.equals("定位中")) {
//            cb_device.setEnabled(false);
//        }
//        cb_device.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    ll_phone.setVisibility(View.GONE);
//                    ll_device.setVisibility(View.VISIBLE);
//                    cb_phone.setChecked(false);
//                } else {
//                    cb_phone.setChecked(true);
//                    ll_phone.setVisibility(View.VISIBLE);
//                    ll_device.setVisibility(View.GONE);
//                }
//            }
//        });
//        //本手机扫描的checkbox 监控
//        cb_yy1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    cb_yy2.setChecked(false);
//                } else {
//                    cb_yy2.setChecked(true);
//                }
//            }
//        });
//        cb_yy2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    cb_yy1.setChecked(false);
//                } else {
//                    cb_yy1.setChecked(true);
//                }
//            }
//        });
//        final List<SpBean> list11 = new ArrayList<>();
//        final List<SpBean> list22 = new ArrayList<>();
////        List<SpBean> list3 = new ArrayList<>();
//
//        if (cb_phone.isChecked()) {
//            gsmInfoList = getGsmInfoList(context);
//            Log.d("nzqgsmInfoList", "SaoPinDialog: " + gsmInfoList);
//
//
//            if (gsmInfoList.size() > 0 && gsmInfoList != null) {//有数据
//                if (gsmInfoList.size() == 1) {//只有一条
//                    String plmn = gsmInfoList.get(0).getPlmn();
//                    String yy = MainUtils2.YY(plmn);
//                    tv1_yy.setText(yy);
//                    spBean1 = gsmInfoList.get(0);
//                    spBean2 = null;
//
//                    SPBEANLIST1 = gsmInfoList;
//
//                    tv1_zhishi.setText("小区类型: LTE");
//                    tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1.getPlmn()));
//                    tv1_EAR.setText("EARFCN: " + spBean1.getDown());
//                    tv1_TAC.setText("TAC: " + spBean1.getTac());
//                    tv1_CID.setText("CID: " + spBean1.getCid());
//                    String zs = "";
//                    String band = MainUtils.getBand(Integer.parseInt(spBean1.getDown()));
//                    if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                        zs = "FDD";
//                    } else {
//                        zs = "TDD";
//                    }
//                    tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1.getDown())) + "(" + zs + ")");
//                    tv1_PCI.setText("PCI:" + spBean1.getPci());
//                    tv1_rsrp.setText("RSRP:" + spBean1.getRsrp());
//                    tv1_rsrq.setText("RSRQ:" + spBean1.getRsrq());
//                    tv1_plmn.setText("PLMN: " + spBean1.getPlmn());
//                } else {//多条
//                    String plmn = gsmInfoList.get(0).getPlmn();
////                    String yy = MainUtils2.YY(plmn);
////                    tv1_yy.setText(yy);
//                    String TAG = "数据判断";
//                    for (int i = 0; i < gsmInfoList.size(); i++) {
//                        if (gsmInfoList.get(i).getPlmn().equals(plmn)) {
//                            list1.add(gsmInfoList.get(i));
//                        } else {
//                            list2.add(gsmInfoList.get(i));
//                        }
//
//                    }
//                    if (list1.size() > 0 && list2.size() == 0) {//只有一个运营商1
//                        spBean1 = list1.get(0);
//                        spBean2 = null;
//                        Log.d(TAG, "SaoPinDialog:只有一个运营商1 ");
//                    } else if (list2.size() > 0 && list1.size() == 0) {////只有一个运营商1
//                        spBean1 = list2.get(0);
//                        spBean2 = null;
//                        Log.d(TAG, "SaoPinDialog:只有一个运营商2 ");
//                    } else if (list1.size() > 0 && list2.size() > 0) {//两个运营商都有
//                        spBean1 = list1.get(0);
//                        spBean2 = list2.get(0);
//                        Log.d(TAG, "SaoPinDialog:两个运营商都有 ");
//                    } else {//一个都没有
//                        spBean1 = null;
//                        spBean2 = null;
//                        Log.d(TAG, "SaoPinDialog:一个都没有 ");
//                    }
//                    if (spBean1 != null) {
//                        tv1_zhishi.setText("小区类型: LTE");
//                        tv1_yy.setText("运营商:" + MainUtils2.YY(spBean1.getPlmn()));
//                        tv1_EAR.setText("EARFCN: " + spBean1.getDown());
//                        tv1_TAC.setText("TAC: " + spBean1.getTac());
//                        tv1_CID.setText("CID: " + spBean1.getCid());
//                        String zs = "";
//                        String band = MainUtils.getBand(Integer.parseInt(spBean1.getDown()));
//                        if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                            zs = "FDD";
//                        } else {
//                            zs = "TDD";
//                        }
//
//                        tv1_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean1.getDown())) + "(" + zs + ")");
//                        tv1_PCI.setText("PCI:" + spBean1.getPci());
//                        tv1_rsrp.setText("RSRP:" + spBean1.getRsrp());
//                        tv1_rsrq.setText("RSRQ:" + spBean1.getRsrq());
//                        tv1_plmn.setText("PLMN: " + spBean1.getPlmn());
//
//                    }
//                    if (spBean2 != null) {
//                        tv2_zhishi.setText("小区类型: LTE");
//                        tv2_yy.setText("运营商:" + MainUtils2.YY(spBean2.getPlmn()));
//                        tv2_EAR.setText("EARFCN: " + spBean2.getDown());
//                        tv2_TAC.setText("TAC: " + spBean2.getTac());
//                        tv2_CID.setText("CID: " + spBean2.getCid());
//                        String zs = "";
//                        String band = MainUtils.getBand(Integer.parseInt(spBean2.getDown()));
//                        if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                            zs = "FDD";
//                        } else {
//                            zs = "TDD";
//                        }
//                        tv2_BAND.setText("BAND: " + MainUtils.getBand(Integer.parseInt(spBean2.getDown())) + "(" + zs + ")");
//                        tv2_PCI.setText("PCI: " + spBean2.getPci());
//                        tv2_RSRP.setText("RSRP: " + spBean2.getRsrp());
//                        tv2_rsrq.setText("RSRQ: " + spBean2.getRsrq());
//                        tv2_plmn.setText("PLMN:" + spBean2.getPlmn());
//                    }
//
//                }
//
//            } else {//无数据
//                spBean1 = null;
//                spBean2 = null;
//            }
//        }
//        //运营商选择
//        final CheckBox cb_tdd = inflate.findViewById(R.id.cb_tdd);//移动TDD
//        final CheckBox cb_fdd = inflate.findViewById(R.id.cb_fdd);//移动FDD
//        final CheckBox cb_lt = inflate.findViewById(R.id.cb_lt);//联通 全部为FDD
//        final CheckBox cb_ds = inflate.findViewById(R.id.cb_ds);//电信全部为TDD
//        final ImageView iv_tdd = inflate.findViewById(R.id.iv_tdd);//
//        final ImageView iv_fdd = inflate.findViewById(R.id.iv_fdd);//
//        final ImageView iv_lt = inflate.findViewById(R.id.iv_lt);//
//        final ImageView iv_ds = inflate.findViewById(R.id.iv_ds);//
//
//        final TextView tv_tdd = inflate.findViewById(R.id.tv_tdd);//
//        final TextView tv_fdd = inflate.findViewById(R.id.tv_fdd);//
//        final TextView tv_lt = inflate.findViewById(R.id.tv_lt);//
//        final TextView tv_ds = inflate.findViewById(R.id.tv_ds);//
//
//        if (tf.equals("TDD")) {
//            cb_tdd.setText("移动TDD");
//            iv_tdd.setBackground(context.getResources().getDrawable(R.mipmap.norest));
//            tv_tdd.setText("不重启");
//
//            cb_fdd.setText("移动FDD");
//            iv_fdd.setBackground(context.getResources().getDrawable(R.mipmap.rest));
//            tv_fdd.setText("需重启");
//
//            cb_lt.setText("联     通 ");
//            iv_lt.setBackground(context.getResources().getDrawable(R.mipmap.rest));
//            tv_lt.setText("需重启");
//
//            cb_ds.setText("电     信 ");
//            iv_ds.setBackground(context.getResources().getDrawable(R.mipmap.rest));
//            tv_ds.setText("需重启");
//        }
//        if (tf.equals("FDD")) {
//            cb_tdd.setText("移动TDD");
//            iv_tdd.setBackground(context.getResources().getDrawable(R.mipmap.rest));
//            tv_tdd.setText("需重启");
//
//            cb_fdd.setText("移动FDD");
//            iv_fdd.setBackground(context.getResources().getDrawable(R.mipmap.norest));
//            tv_fdd.setText("不重启");
//
//            cb_lt.setText("联     通 ");
//            iv_lt.setBackground(context.getResources().getDrawable(R.mipmap.norest));
//            tv_lt.setText("不重启");
//            cb_ds.setText("电     信 ");
//            iv_ds.setBackground(context.getResources().getDrawable(R.mipmap.norest));
//            tv_ds.setText("不重启");
//        }
//        cb_tdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    cb_fdd.setChecked(false);
//                    cb_lt.setChecked(false);
//                    cb_ds.setChecked(false);
//                }
//            }
//        });
//        cb_fdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    cb_tdd.setChecked(false);
//                    cb_lt.setChecked(false);
//                    cb_ds.setChecked(false);
//                }
//            }
//        });
//        cb_lt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    cb_tdd.setChecked(false);
//                    cb_fdd.setChecked(false);
//                    cb_ds.setChecked(false);
//                }
//            }
//        });
//        cb_ds.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    cb_tdd.setChecked(false);
//                    cb_fdd.setChecked(false);
//                    cb_lt.setChecked(false);
//                }
//            }
//        });
//
////        final SpBean finalSpBean1 = spBean1;
////        final SpBean finalSpBean2 = spBean2;
//        gsmInfoList = getGsmInfoList(context);
//        final List<SpBean> finalGsmInfoList = gsmInfoList;
//        Log.d("finalGsmInfoLista", "SaoPinDialog2: " + finalGsmInfoList.toString());
//        bt_confirm.setOnClickListener(new View.OnClickListener() {// 确定按钮的监听
//            @Override
//            public void onClick(View view) {
//                if (sb == 1) {
//                    if (cb_phone.isChecked()) {//如果手机扫描
//                        if (cb_yy1.isChecked()) {//如过选择运营商1
//                            Log.d("cb_yy1true", "onClick: " + spBean1.toString());
//                            if (spBean1 != null) {
////                                ToastUtils.showToast("运营商1有数据");
//                                if (finalGsmInfoList == null) {
//                                    ToastUtils.showToast("运营商1无数据");
//                                    return;
//                                }
//                                if (finalGsmInfoList.size() == 1) {
//                                    SPBEANLIST1 = finalGsmInfoList;
//                                } else {
//                                    SPBEANLIST1 = finalGsmInfoList;
//                                }
//                                saoPinCallback.sucessphoneShow(1, spBean1.getDown(), spBean1, false, true);
//                                dialog.dismiss();
//                                dialog.cancel();
////                                Log.d("0LAAD",finalGsmInfoList.toString()+"");
//
//
//                                Log.d("Dialog2 SPBEANLIST1", "onClick: " + SPBEANLIST1.toString());
//                            } else {
//                                ToastUtils.showToast("运营商1无数据");
//                            }
//                        } else {//如过选择运营商2
//
//                            if (finalGsmInfoList == null) {
//                                ToastUtils.showToast("运营商2无数据");
//                                return;
//                            }
//                            if (spBean2 != null) {
////                                ToastUtils.showToast("运营商2有数据");
//                                saoPinCallback.sucessphoneShow(1, spBean2.getDown(), spBean2, false, true);
//                                dialog.dismiss();
//                                dialog.cancel();
//                                SPBEANLIST1 = list2;
//
//                            } else {
//                                ToastUtils.showToast("运营商2无数据");
//                            }
//                        }
//                    } else {//如果是设备扫描
//                        int i = 0;
//                        if (cb_tdd.isChecked()) {
//                            i = 1;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else if (cb_fdd.isChecked()) {
//                            i = 2;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else if (cb_lt.isChecked()) {
//                            i = 3;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else if (cb_ds.isChecked()) {
//                            i = 4;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else {
//                            i = 0;
//                            saoPinCallback.error();
//                        }
//                    }
//                } else {//设备2
//                    if (cb_phone.isChecked()) {//如果手机扫描
//                        if (cb_yy1.isChecked()) {//如过选择运营商1
//                            if (spBean1 != null) {
////                                ToastUtils.showToast("运营商1有数据");
//                                if (finalGsmInfoList.size() == 1) {
//                                    SPBEANLIST2 = finalGsmInfoList;
//                                } else {
//                                    SPBEANLIST2 = list1;
//                                }
//                                saoPinCallback.sucessphoneShow(2, spBean1.getDown(), spBean1, false, true);
//                                dialog.dismiss();
//                                dialog.cancel();
//                                Log.d("SPBEANLIST2", "onClick: " + SPBEANLIST2 + "/n" + list1);
////                                SPBEANLIST2 = list1;
//
//                            } else {
//                                ToastUtils.showToast("运营商1无数据");
//                            }
//                        } else {//如过选择运营商2
//                            if (spBean2 != null) {
////                                ToastUtils.showToast("运营商2有数据");
//                                saoPinCallback.sucessphoneShow(2, spBean2.getDown(), spBean2, false, true);
//                                dialog.dismiss();
//                                dialog.cancel();
//                                SPBEANLIST2 = list2;
//                            } else {
//                                ToastUtils.showToast("运营商2无数据");
//                            }
//                        }
//                    } else {//如果是设备扫描
//                        int i = 0;
//                        if (cb_tdd.isChecked()) {
//                            i = 1;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else if (cb_fdd.isChecked()) {
//                            i = 2;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else if (cb_lt.isChecked()) {
//                            i = 3;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else if (cb_ds.isChecked()) {
//                            i = 4;
//                            saoPinCallback.sucess(sb, i);
//                            dialog.dismiss();
//                            dialog.cancel();
//                        } else {
//                            i = 0;
//                            saoPinCallback.error();
//                        }
//                    }
//
//                }
//
//
//            }
//        });
//
//        bt_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                dialog.cancel();
//            }
//        });
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setContentView(inflate);
//        //获取当前Activity所在的窗体
//        Window dialogWindow = dialog.getWindow();
//        //设置Dialog从窗体底部弹出
//        dialogWindow.setGravity(Gravity.CENTER);
//
//        //获得窗体的属性
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//
////                    lp.y = 20;//设置Dialog距离底部的距离
////        lp.height=1200;
////       将属性设置给窗体
//        dialogWindow.setAttributes(lp);
//        dialog.show();//显示对话框
//    }


    /**
     * @param context 上下文
     * @param inflate 布局
     * @param
     */
    @SuppressLint({"NewApi", "NewApi"})
    public static void DataExport(final Context context, View inflate, final ZmDataCallBack zmDataCallBack) {
        final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
        inflate = LayoutInflater.from(context).inflate(R.layout.dataexport_item, null);
//        bt_export
//        bt_clear
//        tv_location
        final Button bt_export = inflate.findViewById(R.id.bt_export);//数据导出按钮
        final Button bt_clear = inflate.findViewById(R.id.bt_clear);//清除数据按钮
        final Button bt_cancel = inflate.findViewById(R.id.bt_cancel);//关闭按钮
        final TextView tv_location = inflate.findViewById(R.id.tv_location);

        bt_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManagerZM dbManagerZM = null;
                List<ZmBean> ListdataAll = null;
                try {
                    dbManagerZM = new DBManagerZM(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    ListdataAll = dbManagerZM.getDataAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (ListdataAll.size() == 0) {
                    ToastUtils.showToast("无侦码记录");
                } else {//有侦码记录
                    Log.d("ListdataAllListdataAll", "onClick: " + ListdataAll.size());
                    List<ZmBean> zmBeans;
                    ArrayList<ArrayList<String>> recordList;
                    String[] title = {"编号", "时间", "IMSI", "下行频点", "设备"};
//                    private static String[] title = { "编号","姓名","性别","年龄","班级","数学","英语","语文" };

                    recordList = new ArrayList<>();
                    for (int i = 0; i < ListdataAll.size(); i++) {
                        ZmBean zmBean = ListdataAll.get(i);
                        ArrayList<String> beanList = new ArrayList<String>();
                        beanList.add(i + 1 + "");
                        beanList.add(zmBean.getDatatime());
                        beanList.add(zmBean.getImsi());
                        beanList.add(zmBean.getDown());
                        beanList.add(zmBean.getSb());
//                        beanList.add(zmBean.getSb());
                        recordList.add(beanList);
                    }
                    File file = new File(getSDPath() + "/locationzm");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("" + "yyyy-MM-dd HH:mm:ss ");
                    Date date = new Date();
                    String format = simpleDateFormat.format(date);
                    makeDir(file);
                    ExcelUtils.initExcel(file.toString() + "/" + format + ".xls", title);

                    String fileName = getSDPath() + "/locationzm/" + format + ".xls";
                    ExcelUtils.writeObjListToExcel(recordList, fileName, context);
                    tv_location.setText("" + fileName);
                }
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastUtils.showToast("11111");
                DBManagerZM dbManagerZM = null;
                try {
                    dbManagerZM = new DBManagerZM(context);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                List<ZmBean> dataAll = null;
                try {
                    dataAll = dbManagerZM.getDataAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (dataAll.size() == 0) {
                    ToastUtils.showToast("无侦码记录");
                } else {//有侦码记录

                    final Dialog dialoga = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                    View inflatea = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                    dialoga.setCanceledOnTouchOutside(false);
                    dialoga.setContentView(inflatea);
                    //获取当前Activity所在的窗体
                    Window dialogWindow = dialoga.getWindow();
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity(Gravity.CENTER);

                    //获得窗体的属性
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//        lp.width=400;
//       将属性设置给窗体
                    dialogWindow.setAttributes(lp);
                    dialoga.show();//显示对话框
                    TextView tv_title = inflatea.findViewById(R.id.tv_title);
                    tv_title.setText("确定要清除侦码数据吗?");
                    Button bt_confirm = inflatea.findViewById(R.id.bt_confirm);
                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DBManagerZM dbManagerZM = null;
                            try {
                                dbManagerZM = new DBManagerZM(context);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                dbManagerZM.deleteall();
                                dialoga.dismiss();
                                dialoga.cancel();
                                zmDataCallBack.deleall();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Button bt_cancel = inflatea.findViewById(R.id.bt_cancel);
                    bt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialoga.dismiss();
                            dialoga.cancel();
                        }
                    });
                }
            }
        });
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

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//        lp.width=400;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    /**
     * @param context 上下文
     * @param inflate 布局
     * @param type    1 删除  2 导出
     */
    @SuppressLint("NewApi")
    public static void DataExportzm(final Context context, View inflate, final ZmDataCallBack zmDataCallBack, int type) {
        if (type == 1) {

            final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            tv_title.setText("确定要清除侦码记录吗?");
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBManagerZM dbManagerZM = null;
                    try {
                        dbManagerZM = new DBManagerZM(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    List<ZmBean> dataAll = null;
                    try {
                        dataAll = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dataAll.size() == 0) {
                        ToastUtils.showToast("无侦码记录");
                    } else {//有侦码记录


//                                DBManagerZM dbManagerZM = null;
                        try {
                            dbManagerZM = new DBManagerZM(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            List<ZmBean> dataAll1 = dbManagerZM.getDataAll();
                            List<ZmBean> dataAll12 = new ArrayList<>();
                            for (int i = 0; i < dataAll1.size(); i++) {
                                if (dataAll1.get(i).getMaintype().equals("1")) {
                                    dbManagerZM.deleteAddPararBean(dataAll1.get(i));
                                }

                            }
                            dialog.dismiss();
                            dialog.cancel();
                            zmDataCallBack.deleall();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

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
        if (type == 2) {
            final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dataexport_item, null);
//        bt_export
//        bt_clear
//        tv_location
            final Button bt_export = inflate.findViewById(R.id.bt_export);//数据导出按钮
            final Button bt_clear = inflate.findViewById(R.id.bt_clear);//清除数据按钮
            final Button bt_cancel = inflate.findViewById(R.id.bt_cancel);//关闭按钮
            final TextView tv_location = inflate.findViewById(R.id.tv_location);
            bt_clear.setVisibility(View.GONE);
            bt_export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBManagerZM dbManagerZM = null;
                    List<ZmBean> ListdataAll = null;
                    try {
                        dbManagerZM = new DBManagerZM(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        ListdataAll = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (ListdataAll.size() == 0) {
                        ToastUtils.showToast("无侦码记录");
                    } else {//有侦码记录
                        Log.d("ListdataAllListdataAll", "onClick: " + ListdataAll.size());
                        List<ZmBean> zmBeans;
                        ArrayList<ArrayList<String>> recordList;
                        String[] title = {"编号", "时间", "IMSI", "下行频点", "设备"};
//                    private static String[] title = { "编号","姓名","性别","年龄","班级","数学","英语","语文" };

                        recordList = new ArrayList<>();
                        for (int i = 0; i < ListdataAll.size(); i++) {
                            ZmBean zmBean = ListdataAll.get(i);
                            ArrayList<String> beanList = new ArrayList<String>();
                            beanList.add(i + 1 + "");
                            beanList.add(zmBean.getDatatime());
                            beanList.add(zmBean.getImsi());
                            beanList.add(zmBean.getDown());
                            beanList.add(zmBean.getSb());
//                        beanList.add(zmBean.getSb());
                            recordList.add(beanList);
                        }
                        File file = new File(getSDPath() + "/locationzm");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("" + "yyyy-MM-dd HH:mm:ss ");
                        Date date = new Date();
                        String format = simpleDateFormat.format(date);
                        makeDir(file);
                        ExcelUtils.initExcel(file.toString() + "/" + format + ".xls", title);

                        String fileName = getSDPath() + "/locationzm/" + format + ".xls";
                        ExcelUtils.writeObjListToExcel(recordList, fileName, context);
                        tv_location.setText("" + fileName);
                    }
                }
            });

            bt_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                ToastUtils.showToast("11111");
                    DBManagerZM dbManagerZM = null;
                    try {
                        dbManagerZM = new DBManagerZM(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    List<ZmBean> dataAll = null;
                    try {
                        dataAll = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dataAll.size() == 0) {
                        ToastUtils.showToast("无侦码记录");
                    } else {//有侦码记录

                        final Dialog dialoga = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                        View inflatea = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                        dialoga.setCanceledOnTouchOutside(false);
                        dialoga.setContentView(inflatea);
                        //获取当前Activity所在的窗体
                        Window dialogWindow = dialoga.getWindow();
                        //设置Dialog从窗体底部弹出
                        dialogWindow.setGravity(Gravity.CENTER);

                        //获得窗体的属性
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//        lp.width=400;
//       将属性设置给窗体
                        dialogWindow.setAttributes(lp);
                        dialoga.show();//显示对话框
                        TextView tv_title = inflatea.findViewById(R.id.tv_title);
                        tv_title.setText("确定要清除侦码数据吗?");
                        Button bt_confirm = inflatea.findViewById(R.id.bt_confirm);
                        bt_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DBManagerZM dbManagerZM = null;
                                try {
                                    dbManagerZM = new DBManagerZM(context);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    dbManagerZM.deleteall();
                                    dialoga.dismiss();
                                    dialoga.cancel();
                                    zmDataCallBack.deleall();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Button bt_cancel = inflatea.findViewById(R.id.bt_cancel);
                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialoga.dismiss();
                                dialoga.cancel();
                            }
                        });
                    }
                }
            });
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

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//        lp.width=400;
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        }
    }

    /**
     * @param context 上下文
     * @param inflate 布局
     * @param type    1 删除  2 导出
     */
    @SuppressLint("NewApi")
    public static void DataExportzmGK(final Context context, View inflate, final ZmDataCallBack zmDataCallBack, int type) {
        if (type == 1) {

            final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
            TextView tv_title = inflate.findViewById(R.id.tv_title);
            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
            tv_title.setText("确定要清除侦码记录吗?");
            bt_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBManagerZM dbManagerZM = null;
                    try {
                        dbManagerZM = new DBManagerZM(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    List<ZmBean> dataAll = null;
                    try {
                        dataAll = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dataAll.size() == 0) {
                        ToastUtils.showToast("无侦码记录");
                    } else {//有侦码记录


//                                DBManagerZM dbManagerZM = null;
                        try {
                            dbManagerZM = new DBManagerZM(context);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            List<ZmBean> dataAll1 = dbManagerZM.getDataAll();
                            List<ZmBean> dataAll12 = new ArrayList<>();
                            for (int i = 0; i < dataAll1.size(); i++) {
                                if (dataAll1.get(i).getMaintype().equals("1")) {
                                    dbManagerZM.deleteAddPararBean(dataAll1.get(i));
                                }

                            }
                            dialog.dismiss();
                            dialog.cancel();
                            zmDataCallBack.deleall();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

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
        if (type == 2) {
            final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
            inflate = LayoutInflater.from(context).inflate(R.layout.dataexport_item, null);
//        bt_export
//        bt_clear
//        tv_location
            final Button bt_export = inflate.findViewById(R.id.bt_export);//数据导出按钮
            final Button bt_clear = inflate.findViewById(R.id.bt_clear);//清除数据按钮
            final Button bt_cancel = inflate.findViewById(R.id.bt_cancel);//关闭按钮
            final TextView tv_location = inflate.findViewById(R.id.tv_location);
            bt_clear.setVisibility(View.GONE);
            bt_export.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBManagerZM dbManagerZM = null;
                    List<ZmBean> ListdataAll = null;
                    try {
                        dbManagerZM = new DBManagerZM(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        ListdataAll = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (ListdataAll.size() == 0) {
                        ToastUtils.showToast("无侦码记录");
                    } else {//有侦码记录
                        Log.d("ListdataAllListdataAll", "onClick: " + ListdataAll.size());
                        List<ZmBean> zmBeans;
                        ArrayList<ArrayList<String>> recordList;
                        String[] title = {"编号", "时间", "IMSI", "下行频点", "设备"};
//                    private static String[] title = { "编号","姓名","性别","年龄","班级","数学","英语","语文" };

                        recordList = new ArrayList<>();
                        for (int i = 0; i < ListdataAll.size(); i++) {
                            ZmBean zmBean = ListdataAll.get(i);
                            ArrayList<String> beanList = new ArrayList<String>();
                            beanList.add(i + 1 + "");
                            beanList.add(zmBean.getDatatime());
                            beanList.add(zmBean.getImsi());
                            beanList.add(zmBean.getDown());
                            beanList.add(zmBean.getSb());
//                        beanList.add(zmBean.getSb());
                            recordList.add(beanList);
                        }
                        File file = new File(getSDPath() + "/locationzm");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("" + "yyyy-MM-dd HH:mm:ss ");
                        Date date = new Date();
                        String format = simpleDateFormat.format(date);
                        makeDir(file);
                        ExcelUtils.initExcel(file.toString() + "/" + format + ".xls", title);

                        String fileName = getSDPath() + "/locationzm/" + format + ".xls";
                        ExcelUtils.writeObjListToExcel(recordList, fileName, context);
                        tv_location.setText("" + fileName);
                    }
                }
            });

            bt_clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                ToastUtils.showToast("11111");
                    DBManagerZM dbManagerZM = null;
                    try {
                        dbManagerZM = new DBManagerZM(context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    List<ZmBean> dataAll = null;
                    try {
                        dataAll = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (dataAll.size() == 0) {
                        ToastUtils.showToast("无侦码记录");
                    } else {//有侦码记录

                        final Dialog dialoga = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                        View inflatea = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                        dialoga.setCanceledOnTouchOutside(false);
                        dialoga.setContentView(inflatea);
                        //获取当前Activity所在的窗体
                        Window dialogWindow = dialoga.getWindow();
                        //设置Dialog从窗体底部弹出
                        dialogWindow.setGravity(Gravity.CENTER);

                        //获得窗体的属性
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//        lp.width=400;
//       将属性设置给窗体
                        dialogWindow.setAttributes(lp);
                        dialoga.show();//显示对话框
                        TextView tv_title = inflatea.findViewById(R.id.tv_title);
                        tv_title.setText("确定要清除侦码数据吗?");
                        Button bt_confirm = inflatea.findViewById(R.id.bt_confirm);
                        bt_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DBManagerZM dbManagerZM = null;
                                try {
                                    dbManagerZM = new DBManagerZM(context);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    dbManagerZM.deleteall();
                                    dialoga.dismiss();
                                    dialoga.cancel();
                                    zmDataCallBack.deleall();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Button bt_cancel = inflatea.findViewById(R.id.bt_cancel);
                        bt_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialoga.dismiss();
                                dialoga.cancel();
                            }
                        });
                    }
                }
            });
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

//                    lp.y = 20;//设置Dialog距离底部的距离
//        lp.height=1200;
//        lp.width=400;
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        }
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    //    private  ArrayList<ArrayList<String>> getRecordData() {
//        recordList = new ArrayList<>();
//        for (int i = 0; i <students.size(); i++) {
//            ZmBean student = students.get(i);
//            ArrayList<String> beanList = new ArrayList<String>();
//            beanList.add(student.id);
//            beanList.add(student.name);
//            beanList.add(student.sex);
//            beanList.add(student.age);
//            beanList.add(student.classNo);
//            beanList.add(student.math);
//            beanList.add(student.english);
//            beanList.add(student.chinese);
//            recordList.add(beanList);
//        }
//        return recordList;
//    }
    @SuppressLint({"NewApi", "LongLogTag"})
    public static List<SpBean> getGsmInfoList(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"NewApi", "MissingPermission", "LocalSuppress"}) List<CellInfo> cellInfoList = manager.getAllCellInfo();
        Log.d("cellInfoListnzq", "getGsmInfoList: " + cellInfoList);
        List<SpBean> gsmInfoList = new ArrayList<>();
        if (cellInfoList != null) {
            Log.e("cellInfoList.size=" + cellInfoList.size(), "");
//            GsmInfo gsmInfo;
            SpBean bean;
          int  version = android.os.Build.VERSION.SDK_INT;
          if (version>28){
              for (CellInfo info : cellInfoList) {
                  Log.e("nzqinfos" + info.toString(), "");
                  if (info.toString().contains("CellInfoLte")) {
                      CellInfoLte cellInfoLte = (CellInfoLte) info;
                      CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();
//                    NeighboringCellInfo neighboringCellInfo=new   NeighboringCellInfo ();
//                    neighboringCellInfo
                      bean = new SpBean();
//                    cellIdentityLte.
                      try {

//                        String mobileNetworkOperator1 = cellIdentityLte.getMobileNetworkOperator();

                          if (!TextUtils.isEmpty(cellIdentityLte.getMobileNetworkOperator())){
                              String yy = MainUtils2.YY(cellIdentityLte.getMobileNetworkOperator());
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

                              bean.setTac(cellIdentityLte.getTac());
                              bean.setCid(cellIdentityLte.getCi() + "");
//                    bean.setRssi(cellInfoLte.getCellSignalStrength().getRssi());
                              bean.setRsrp(cellInfoLte.getCellSignalStrength().getRsrp());
                              bean.setRsrq(cellInfoLte.getCellSignalStrength().getRsrq());
                              bean.setDown(cellIdentityLte.getEarfcn() + "");
                              bean.setPci(cellIdentityLte.getPci());
                              bean.setPlmn(cellIdentityLte.getMobileNetworkOperator());
                              bean.setBand(MainUtils.getBand(cellIdentityLte.getEarfcn()));
                              bean.setZw(true);
                              Log.d("beanaa", "getGsmInfoList: ." + bean);
                              gsmInfoList.add(bean);
                          }
                      }catch (Exception e){

                      }
//                    String mobileNetworkOperator = cellIdentityLte.getMobileNetworkOperator();
//                    Log.d("nzqmobileNetworkOperator", "getGsmInfoList: " + mobileNetworkOperator);
//                    bean.setMcc(cellIdentityLte.getMcc());
                      //上行频点



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
          }else {

          }

        } else {
            Log.e("cellInfoList == null", "");
        }

        return gsmInfoList;
    }

}
