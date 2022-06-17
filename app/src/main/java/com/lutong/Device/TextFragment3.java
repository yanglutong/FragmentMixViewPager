package com.lutong.Device;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.R;
import com.lutong.Service.Bean;
import com.lutong.base.BaseFragment;
import com.google.gson.Gson;
import com.lutong.Constant.Constant;
import com.lutong.Service.MyService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TextFragment3 extends BaseFragment {

    View view;
    RecyclerView recyclerView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100001) {//无线正确

            }
        }
    };


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
        view = inflater.inflate(R.layout.deviceinfragment5gzt, null);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法
            Log.d("nzq", "AsetUserVisibleHint: 1");
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
            Log.d("nzq", "AsetUserVisibleHint: 2");
        }
    }

    @Override
    public void initData() {
        findViews();
        getData();
    }

    TextView tvband, tv_xiaxing, tvpci, tvsub, tvtac, tvsup, tvpower, tvdaikuan, tvplmnlist, tv_tbtype, tv_tbzt;

    private void findViews() {

        recyclerView = view.findViewById(R.id.ryimsi);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        tvband = view.findViewById(R.id.tvband);
        tv_xiaxing = view.findViewById(R.id.tv_xiaxing);
        tvpci = view.findViewById(R.id.tvpci);
        tvsub = view.findViewById(R.id.tvsub);
        tvtac = view.findViewById(R.id.tvtac);
        tvsup = view.findViewById(R.id.tvsup);
        tvpower = view.findViewById(R.id.tvpower);
        tvdaikuan = view.findViewById(R.id.tvdaikuan);
        tvplmnlist = view.findViewById(R.id.tvplmnlist);

        tv_tbtype = view.findViewById(R.id.tv_tbtype);
        tv_tbzt = view.findViewById(R.id.tv_tbzt);
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
        MyService.BAND5G = "";
        MyService.NREF5G = "";
        MyService.PCI5G = "";
        MyService.SUBFRAMSSIGN5G = "";
        MyService.TAC5G = "";
        MyService.SPECIALSUBFRM5G = "";
        MyService.POWER5G = "";
        MyService.BANDWIDTH5G = "";
        MyService.PLMN_LIST.clear();
        MyService.CELL_STATUS = "";
        MyService.SYNC_STATUS = "";
        List<Integer> listtest = new ArrayList<>();
//        for (int i = 0; i < BLACKLISTSB1.size(); i++) {
//            list.add(i);
//
//        }
        //定位模式黑名单
        MyService.IMSI_LIST.clear();
        ryImsiAdapter = new RyImsiAdapter(mContext, listtest, MyService.IMSI_LIST);


        tvband.setText("");
        tv_xiaxing.setText("");
        tvpci.setText("");
        tvsub.setText("");
        tvtac.setText("");
        tvsup.setText("");
        tvpower.setText("");
        tvdaikuan.setText("");
        tvplmnlist.setText("");
        tv_tbzt.setText("");
        tv_tbtype.setText("");
        MyService.IMSI_LIST.clear();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < MyService.IMSI_LIST.size(); i++) {
            list.add(i);

        }

        //定位模式黑名单
        ryImsiAdapter = new RyImsiAdapter(mContext, list, MyService.IMSI_LIST);
        recyclerView.setAdapter(ryImsiAdapter);

    }

    RyImsiAdapter ryImsiAdapter;

    private void getData() {

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
//                tvband, tv_xiaxing, tvpci,tvsub,tvtac,tvsup,tvpower,tvdaikuan,tvplmnlist
                tvband.setText(MyService.BAND5G);
                tv_xiaxing.setText(MyService.NREF5G);
                tvpci.setText(MyService.PCI5G);
                tvsub.setText(MyService.SUBFRAMSSIGN5G);
                tvtac.setText(MyService.TAC5G);
                tvsup.setText(MyService.SPECIALSUBFRM5G);
                tvpower.setText(MyService.POWER5G);
                tvdaikuan.setText(MyService.BANDWIDTH5G);
                tvplmnlist.setText(MyService.PLMN_LIST.toString());
                tv_tbtype.setText(MyService.CELL_STATUS);
                tv_tbzt.setText(MyService.SYNC_STATUS);
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < Constant.BLACKLISTSB1.size(); i++) {
                    list.add(i);

                }
                //定位模式黑名单
                ryImsiAdapter = new RyImsiAdapter(mContext, list, MyService.IMSI_LIST);
                recyclerView.setAdapter(ryImsiAdapter);

//查询小区列表
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bean bean = new Bean();
                        Bean.BodyDTO dto = new Bean.BodyDTO();
                        dto.setSwitchs("");
                        Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                        headerDTO.setSession_id("51114");
                        headerDTO.setMsg_id("QUERY_CELL_PARAM_REQ");
                        headerDTO.setCell_id(1);
                        bean.setHeader(headerDTO);
                        bean.setBody(dto);
                        Log.d("TAGbean", "run: " + bean.toString());

                        Gson gson = new Gson();
                        String s2 = gson.toJson(bean);
                        Log.d("TAGs2s", "run: " + s2);
                        byte[] srtbyte = s2.getBytes();
                        try {
                            MyService.ADDRESS2 = InetAddress.getByName(MyService.IP3);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, MyService.ADDRESS2, 32001);
                        try {
                            MyService.DS2.send(outputPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //查询黑名单列表

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bean bean = new Bean();
                        Bean.BodyDTO dto = new Bean.BodyDTO();
                        dto.setSwitchs("open");
                        Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                        headerDTO.setSession_id(MyService.session_id);
                        headerDTO.setMsg_id("QUERY_BLACKLIST_REQ");
                        headerDTO.setCell_id(1);
                        bean.setHeader(headerDTO);
                        bean.setBody(dto);
                        Log.d("TAGbean", "run: " + bean.toString());

                        Gson gson = new Gson();
                        String s2 = gson.toJson(bean);
                        Log.d("TAGs2s", "run: " + s2);
                        byte[] srtbyte = s2.getBytes();
                        try {
                            MyService.ADDRESS2 = InetAddress.getByName(MyService.IP3);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, MyService.ADDRESS2, 32001);
                        try {
                            MyService.DS2.send(outputPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                //查询小区状态。 同步状态
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bean bean = new Bean();
                        Bean.BodyDTO dto = new Bean.BodyDTO();
                        dto.setSwitchs("open");
                        Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                        headerDTO.setSession_id(MyService.session_id);
                        headerDTO.setMsg_id("QUERY_CELL_STATUS_REQ");
                        headerDTO.setCell_id(1);
                        bean.setHeader(headerDTO);
                        bean.setBody(dto);
                        Log.d("TAGbean", "run: " + bean.toString());

                        Gson gson = new Gson();
                        String s2 = gson.toJson(bean);
                        Log.d("TAGs2s", "run: " + s2);
                        byte[] srtbyte = s2.getBytes();
                        try {
                            MyService.ADDRESS2 = InetAddress.getByName(MyService.IP3);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, MyService.ADDRESS2, 32001);
                        try {
                            MyService.DS2.send(outputPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        }, 5000);
    }

    // Set通过HashSet去重（将ridRepeat3方法缩减为一行） 无序
    public static List<String> ridRepeat4(List<String> list) {
        System.out.println("list = [" + list + "]");
        List<String> listNew = new ArrayList<String>(new HashSet(list));
        System.out.println("listNew = [" + listNew + "]");
        return listNew;
    }
}