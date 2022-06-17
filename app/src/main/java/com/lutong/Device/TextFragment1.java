package com.lutong.Device;

import static com.lutong.Device.TextFragment3.ridRepeat4;


import android.annotation.SuppressLint;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.lutong.R;
import com.lutong.Utils.DeviceUtils;
import com.lutong.Utils.MainUtils;
import com.lutong.base.BaseFragment;
import com.lutong.Constant.Constant;


import java.util.ArrayList;
import java.util.List;

public class TextFragment1 extends BaseFragment {
    RecyclerView recyclerView;
    RyImsiAdapter ryImsiAdapter;
    View view;
    TextView tv_plmn, tv_up, tv_down, tv_band, tv_dk, tv_tac, tv_pci, tv_cid, tv_max, tv_type;//小区信息
    TextView tv_ms, tv_zq, tv_ueimsi, tv_sbzq, tv_glkg, tvmax;//定位模式
    TextView tv_rrcnum, tv_rrcsucess, tv_rrcsucessnum, tv_imsiNum, tv_imsiNumsucess;
    TextView tv_zy, tv_sj;
    TextView tv_tbtype, tv_tbzt;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100001) {//无线正确

            }
        }
    };

//
//    public View initView() {
////        view = inflater.inflate(R.layout.deviceinfragment0, container, false);
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.deviceinfragment1, null);
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
    view = inflater.inflate(R.layout.deviceinfragment1, null);
    initView(view);
    initData();
    return view;
}
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法
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

    @Override
    public void initData() {
        findViews();
        getData();
    }

    private void findViews() {
//        TextView tv_plmn, tv_up, tv_down, tv_band, tv_dk, tv_tac, tv_pci, tv_cid, tv_max, tv_type;//小区信息
//        TextView tv_ms, tv_zq, tv_ueimsi, tv_sbzq, tv_glkg, tvmax;//定位模式
//        TextView tv_zy, tv_sj;
        tv_plmn = view.findViewById(R.id.tv_plmn);
        tv_up = view.findViewById(R.id.tv_up);
        tv_down = view.findViewById(R.id.tv_down);
        tv_band = view.findViewById(R.id.tv_band);
        tv_dk = view.findViewById(R.id.tv_dk);
        tv_tac = view.findViewById(R.id.tv_tac);
        tv_pci = view.findViewById(R.id.tv_pci);
        tv_cid = view.findViewById(R.id.tv_cid);
        tv_max = view.findViewById(R.id.tv_max);
        tv_type = view.findViewById(R.id.tv_type);

        recyclerView = view.findViewById(R.id.ryimsi);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        //定位模式
        tv_ms = view.findViewById(R.id.tv_ms);
        tv_zq = view.findViewById(R.id.tv_zq);
        tv_ueimsi = view.findViewById(R.id.tv_ueimsi);
        tv_sbzq = view.findViewById(R.id.tv_sbzq);
        tv_glkg = view.findViewById(R.id.tv_glkg);
        tvmax = view.findViewById(R.id.tvmax);
        //增益
        tv_zy = view.findViewById(R.id.tv_zy);
        tv_sj = view.findViewById(R.id.tv_sj);


//        tv_rrcnum,tv_rrcsucess,tv_rrcsucessnum,tv_imsiNum,tv_imsiNumsucess
        tv_rrcnum = view.findViewById(R.id.tv_rrcnum);
        tv_rrcsucess = view.findViewById(R.id.tv_rrcsucess);
        tv_rrcsucessnum = view.findViewById(R.id.tv_rrcsucessnum);
        tv_imsiNum = view.findViewById(R.id.tv_imsiNum);
        tv_imsiNumsucess = view.findViewById(R.id.tv_imsiNumsucess);

        tv_tbtype = view.findViewById(R.id.tv_tbtype);
        tv_tbzt = view.findViewById(R.id.tv_tbzt);
        /////////////
        tv_plmn.setText(Constant.PLMN1);
        tv_up.setText(Constant.UP1);
        tv_down.setText(Constant.DWON1);
        tv_band.setText(Constant.BAND1);
        if (!TextUtils.isEmpty(Constant.DK1)) {
            int i = Integer.parseInt(Constant.DK1);
            tv_dk.setText(i / 5 + "");
        }
        tv_tac.setText(Constant.TAC1);
        tv_pci.setText(Constant.PCI1);
        tv_cid.setText(Constant.CELLID1);
        tv_max.setText(Constant.DBM1);
        tv_type.setText(Constant.TYPE1);
        //
        tv_ms.setText(Constant.GZMS1);
        tv_zq.setText(Constant.ZHZQ1);
        tv_ueimsi.setText(Constant.UEIMSI);
        tv_sbzq.setText(Constant.SBZQ1);
        tv_glkg.setText(Constant.UEMAXOF1);
        tvmax.setText(Constant.UEMAX1);

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

    public void clear() {
        tv_plmn.setText("");
        tv_up.setText("");
        tv_down.setText("");
        tv_band.setText("");
//        if (!TextUtils.isEmpty(DK1)) {
//            int i = Integer.parseInt(DK1);
        tv_dk.setText("");
//        }
        tv_tac.setText("");
        tv_pci.setText("");
        tv_cid.setText("");
        tv_max.setText("");
        tv_type.setText("");
        //
        tv_ms.setText("");
        tv_zq.setText("");
        tv_ueimsi.setText("");
        tv_sbzq.setText("");
        tv_glkg.setText("");
        tvmax.setText("");

        tv_zy.setText("");
        tv_sj.setText("");
        tv_rrcnum.setText("");
        tv_rrcsucess.setText("");
        tv_rrcsucessnum.setText("");
        tv_imsiNum.setText("");
        tv_imsiNumsucess.setText("");
        tv_tbzt.setText("");
        tv_tbtype.setText("");
//        tv_rrcnum,tv_rrcsucess,tv_rrcsucessnum,tv_imsiNum,tv_imsiNumsucess
        Constant.BLACKLISTSB1.clear();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < Constant.BLACKLISTSB1.size(); i++) {
            list.add(i);

        }
        //定位模式黑名单
        ryImsiAdapter = new RyImsiAdapter(mContext, list, Constant.BLACKLISTSB1);
        recyclerView.setAdapter(ryImsiAdapter);

    }

    private void getData() {
        DeviceUtils.SelectQury(1, 1);
        DeviceUtils.SelectQury(2, 1);
        DeviceUtils.SelectQury(3, 1);
        DeviceUtils.SelectQury(4, 1);
        DeviceUtils.SelectQury(5, 1);
        DeviceUtils.SelectQury(6, 1);
        MainUtils.getType(Constant.IP1);//查询状态
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                tv_devicenumber.setText(DEVICENUMBER1);
//                tv_hardware.setText(HARDWAREVERSION1);
//                tv_software.setText(SOFTWAREVERSION1);
//                tv_sn.setText(SNNUMBER1);
//                tv_mac.setText(MACADDRESS1);
//                tvuboot.setText(UBOOTVERSION1);
//                tvboard.setText(BOARDTEMPERATURE1);
//定位模式
                tv_ms = view.findViewById(R.id.tv_ms);
                tv_zq = view.findViewById(R.id.tv_zq);
                tv_ueimsi = view.findViewById(R.id.tv_ueimsi);
                tv_sbzq = view.findViewById(R.id.tv_sbzq);
                tv_glkg = view.findViewById(R.id.tv_glkg);
                tvmax = view.findViewById(R.id.tvmax);
                //增益
                tv_zy = view.findViewById(R.id.tv_zy);
                tv_sj = view.findViewById(R.id.tv_sj);

                /////////////
                tv_plmn.setText(Constant.PLMN1);
                tv_up.setText(Constant.UP1);
                tv_down.setText(Constant.DWON1);
                tv_band.setText(Constant.BAND1);
                if (!TextUtils.isEmpty(Constant.DK1)) {
                    int i = Integer.parseInt(Constant.DK1);
                    tv_dk.setText(i / 5 + "");
                }
                tv_tac.setText(Constant.TAC1);
                tv_pci.setText(Constant.PCI1);
                tv_cid.setText(Constant.CELLID1);
                tv_max.setText(Constant.DBM1);
                tv_type.setText(Constant.TYPE1);

                if (Constant.BLACKLISTSB1.size() > 0) {
                    List<String> list1 = ridRepeat4(Constant.BLACKLISTSB1);
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0; i < list1.size(); i++) {
                        list.add(i);
                    }
                    //定位模式黑名单
                    Log.d("zlist1", "run: " + list1.toString());
                    ryImsiAdapter = new RyImsiAdapter(mContext, list, list1);
                    recyclerView.setAdapter(ryImsiAdapter);
                }
                //
                tv_ms.setText(Constant.GZMS1);
                tv_zq.setText(Constant.ZHZQ1);
                tv_ueimsi.setText(Constant.UEIMSI);
                tv_sbzq.setText(Constant.SBZQ1);
                tv_glkg.setText(Constant.UEMAXOF1);
                tvmax.setText(Constant.UEMAX1);

                tv_zy.setText(Constant.ZENGYI1);
                tv_sj.setText(Constant.SHUAIJIAN1);
                //        tv_rrcnum,tv_rrcsucess,tv_rrcsucessnum,tv_imsiNum,tv_imsiNumsucess
                tv_rrcnum.setText(Constant.REQUEST1);
                tv_rrcsucess.setText(Constant.ENDNUM1);
                if (!TextUtils.isEmpty(Constant.REQUEST1) && !TextUtils.isEmpty(Constant.ENDNUM1) && !Constant.REQUEST1.equals("0") && !Constant.ENDNUM1.equals("0")) {
                    Double i = Double.parseDouble(Constant.REQUEST1);
                    Double i2 = Double.parseDouble(Constant.ENDNUM1);
                    Double i3 = i2 / i*100 ;
                    @SuppressLint({"NewApi", "LocalSuppress"}) DecimalFormat df2=new DecimalFormat("#.#");
                    Log.d("REQUEST1ENDNUM1", "runi: "+i+"   -i2="+i2+"--i3="+i3);

                    tv_rrcsucessnum.setText(df2.format(i3) + "%");
//                    tv_rrcsucessnum.setText("444");
                } else {
                    tv_rrcsucessnum.setText("0%");
                }
                tv_imsiNum.setText(Constant.IMSINUM1);
                if (!TextUtils.isEmpty(Constant.IMSINUM1) && !TextUtils.isEmpty(Constant.ENDNUM1) && !Constant.REQUEST1.equals("0") && !Constant.IMSINUM1.equals("0")) {
                    int i = Integer.parseInt(Constant.IMSINUM1);
                    int i2 = Integer.parseInt(Constant.ENDNUM1);
                    int i3 = i2 / i * 100;
                    @SuppressLint({"NewApi", "LocalSuppress"}) DecimalFormat df2=new DecimalFormat("#.#");

                    tv_imsiNumsucess.setText(i3+ "%");

                } else {
                    tv_imsiNumsucess.setText("0%");
                }
                if (!TextUtils.isEmpty(Constant.TBTYPE1)){
                    if (Constant.TBTYPE1.equals("0")){
                        tv_tbtype.setText("空口同步");
                    }else {
                        tv_tbtype.setText("GPS同步");
                    }
                }
                if (!TextUtils.isEmpty(Constant.TBZT1)){
                    if (Constant.TBZT1.equals("0")){
                        tv_tbzt.setText("GPS同步成功");
                    }
                    if (Constant.TBZT1.equals("1")){
                        tv_tbzt.setText("空口同步成功");
                    }
                    if (Constant.TBZT1.equals("2")){
                        tv_tbzt.setText("未同步");
                    }
                    if (Constant.TBZT1.equals("3")){
                        tv_tbzt.setText("GPS失步");
                    }
                    if (Constant.TBZT1.equals("4")){
                        tv_tbzt.setText("空口失步");
                    }
                }

            }
        }, 5000);
    }

}
