package com.lutong.fragment;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.speech.tts.TextToSpeech;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lutong.base.BasePresenter;
import com.lutong.base.BaseView;

public class NewViewDW {
    interface View extends BaseView<MainPresenter> {

        //        void Up(String str, String strs);//回调更新
//
//        void FsUp(boolean b);
//
//        void buildSdError(String str, int id);
//
        void zhishiqiehuan(int device, String tf);

        //
//        void zidongsaopinjianlixiaoqu(int device);//自动扫频建立小区
//
//        void setpararBeansList1(List<AddPararBean> pararBeansList1);
//
//        void labaup(int device, boolean bs);
//
        void quxian(String data, int device);

        //
//        void zhishiqiehuanZm(int device);
//
        void MesageV(int i);

        /**
         * 侦码轮循的两个集合
         * <p>
         * //         * @param list1
         * //         * @param list2
         */
//        void zmlunxunlist(List<Integer> list1, List<Integer> list2);
//
//        void zmstop(int i, int i2);
//
//        void gkstop();
//
        void gkqiehuan(String tf, int device);

        //
//        void stopZmV();
        void stopdwup(int i);

        void labaup(int device, boolean bs);

        void bt_startFalg(boolean b);
    }

    interface MainPresenter extends BasePresenter {//使用

        //        void set(String str);
//
//        void setFS(boolean b, ImageView imageView, Context context);
//
//        void setStart(int device, boolean b, int maintype, String sb1, String sb2, String sp1, String sp2, Context context, String tf1, String tf2, int restart, boolean phoneFalg);
//
        void buildSD(String spinner, int i, String sb, Context context);//手动定位

        //
//        void stopdw(int i, Context context);//停止设备定位
//
//        void stopZM(int i, Context context, Timer timerSB1, Timer timerSB2, CountDownTimer countDownTimer1, CountDownTimer countDownTimer2, TextView tv_datashengyu1, TextView tv_datashengyu2, TextView tv_xunhuanNum1_zm, TextView tv_xunhuanNum2_zm, int lunxunNum1, int lunxunNum2);//停止设备定位
//
//        void stop(int i, Context context);//停止设备定位
//
//        void stopGK(Context context);
//        void spbuils(Context context, int device, int yy, String tf1, String tf2);//扫频  弹出窗
//        void spbuilsshow(Context context, int device, int yy, String tf1, String tf2);//扫频  查看小区
//        void spbuilsGK(Context context, int device, int yy, String tf1, String tf2);//扫频  弹出窗
//
        void saopinjianlixiaoqu(Context context, int device, String tf, String down);
//
//        void sendsaopin(Context context, int device, int yy);

//        void setRyimsidw(Context context, RecyclerView recyclerView, Message msg, RyZmAdapterdw ryZmAdapter, EditText et_zhenmasearchdw, TextView tv_searchNumdw);

//        void setryGK(Context context, RecyclerView recyclerView, String down1, String down2, RyZmAdapterGk ryZmAdapterdw, Message msg);

//        void setRyimsizm(Context context, RecyclerView recyclerView, Message msg, RyZmAdapter ryZmAdapter, EditText et_zhenmasearchdw, TextView tv_searchNumdw, String DOWN1, String DOWN2, String startdatazm1, String startdatazm2, String stopdata);

//        void setIMSInengliangzhi(Context context, int device, String tf, DecimalFormat dfBaoshu, TextView sb1_jl, boolean laba, Message message, TextToSpeech textToSpeech);
//
//        void setIMSIshow(Message message, TextView tv_imsi1_dw);
//
//        void setIMSIshow2(Message message, TextView tv_imsi1_dw);
//
//        void setlaba(Context context, ImageView imageView, int device, boolean laba);
//
//        void setnengliangAndzhenma(Context context, ImageButton qx, ImageButton zm, int type, boolean nengliangquxiantu, boolean zhenmajilu);

//        /**
//         * @param context
//         * @param tf1
//         * @param tf2
//         * @param sb1
//         * @param sb2
//         * @param type    1 有弹窗 2 没弹窗
//         */
//        void setZMStart(Context context, String tf1, String tf2, String sb1, String sb2, int type);
//
//        void setGKtart(Context context, String tf1, String tf2, String sb1, String sb2, int type);
//
//        void setLunxunJianLixiaoqu(final Context context, final int device, final String down, final String type);
//
//        void chongdingxiang1(String down);
//
//        void chongdingxiang2(String down);
//
//        void chongdingxiangSET1();
//
//        void chongdingxiangSET2();
        void startsd(boolean b, int device, String tf, Context context, String spinnerDown, String sbzhuangtai);

        void startsdquanbu(int device, String tf1, String tf2, Context context, String spinnerDown1, String spinnerDown2, String sbzhuangtai1, String sbzhuangtai2);

        //        buildSD
        void setlaba(Context context, ImageView imageView, int device, boolean laba);

        void setIMSInengliangzhi(String juli, Context context, int device, String tf, DecimalFormat dfBaoshu, TextView sb1_jl, boolean laba, TextToSpeech textToSpeech);

        void stopdw(int i, Context context, boolean b, Button btstart);//停止设备定位

        void setGKtart(final Context context, final String tf1, final String tf2, final String sb1, final String sb2, int GuankongType);

    }
}
