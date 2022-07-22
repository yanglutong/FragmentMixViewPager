package com.lutong.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lutong.App.MessageEvent;
import com.lutong.Constant.Constant;
import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.Bean.Conmmunit01Bean;
import com.lutong.OrmSqlLite.Bean.PinConfigBean5G;
import com.lutong.OrmSqlLite.Bean.SaopinBean;
import com.lutong.OrmSqlLite.Bean.SpBean;
import com.lutong.OrmSqlLite.DBManager01;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.OrmSqlLite.DBManagerPinConfig5G;
import com.lutong.OrmSqlLite.DBManagerZY;
import com.lutong.OrmSqlLite.DBManagersaopin;
import com.lutong.R;
import com.lutong.Service.Bean;
import com.lutong.Service.BeanBlackSet;
import com.lutong.Service.BeanDw5G;
import com.lutong.Service.CellBean5G;
import com.lutong.Utils.ACacheUtil;
import com.lutong.Utils.DbUtils;
import com.lutong.Utils.MainUtils;
import com.lutong.Utils.MainUtilsThread;
import com.lutong.Utils.MyUtils;
import com.lutong.Utils.States;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.LineChartView;
import com.lutong.Views.LineChartView2;
import com.lutong.base.BaseFragment;
import com.lutong.fragment.adapter.RyImsiAdapter;
import com.google.gson.Gson;
import com.lutong.AppContext;
import com.lutong.Service.MyService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


import static com.lutong.Constant.Constant.IP1;
import static com.lutong.Constant.Constant.IP2;
import static com.lutong.Utils.MainUtils.location;
import static com.lutong.Utils.MyUtils.removeDuplicate;
import static com.lutong.Utils.SendUtils.setzy;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChildFragment3 extends BaseFragment implements NewViewDW.View {
    LineChartView lineChartViewdw;
    LineChartView2 lineChartViewdw2;
    TextView tv1type, tvtypetext, tvtypetext2, tv2type, tv_imsi1_dw, tv_imsi2_dw;
    public static String TAG = "ChildFragment3";
    RecyclerView ryIMSI;//IMSI 列表
    CheckBox cbzt1_g, cbzt1_z, cbzt1_d, cbzt2_g, cbzt2_z, cbzt2_d;
    Button bts_start1_dw, bts_stop_dw;
    TextView tv_sb1_jl_dw, tv_sb2_jl_dw;
    ProgressBar pb2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmernt3, null);
        initView(view);
        return view;
    }

    int sux1 = 0;//  定位进度1
    int sux2 = 0;//  定位进度1

    TextView tv;
    private String type1 = "";
    private String type2 = "";
    Button bttest2;
    Button bttest3;
    Button bttest4;
    Button bttest5;
    Button bttest6;
    Button bttest7;
    Button bttest8;

    public void initView(View view) {
        buttontest = view.findViewById(R.id.bttest1);
        bttest2 = view.findViewById(R.id.bttest2);
        bttest3 = view.findViewById(R.id.bttest3);
        bttest4 = view.findViewById(R.id.bttest4);
        bttest5 = view.findViewById(R.id.bttest5);
        bttest6 = view.findViewById(R.id.bttest6);
        bttest7 = view.findViewById(R.id.bttest7);
        bttest8 = view.findViewById(R.id.bttest8);
        buttontest.setOnClickListener(new View.OnClickListener() {//设置小区参数
            @Override
            public void onClick(View view) {
                setCell5G();
            }
        });

        bttest2.setOnClickListener(new View.OnClickListener() {//查询小区参数
            @Override
            public void onClick(View view) {
                queryCell();
            }
        });

        bttest3.setOnClickListener(new View.OnClickListener() {//开始定位
            @Override
            public void onClick(View view) {
                Start5Gdingwei();
            }
        });
        bttest4.setOnClickListener(new View.OnClickListener() {//停止定位
            @Override
            public void onClick(View view) {
                Stop5Gdingwei();




            }
        });
        bttest5.setOnClickListener(new View.OnClickListener() {//查询黑名单
            @Override
            public void onClick(View view) {
                QueryBlack();
            }
        });
        bttest6.setOnClickListener(new View.OnClickListener() {//删除黑名单
            @Override
            public void onClick(View view) {
                set5GblackListDelete("460005192822841");
            }
        });
        bttest7.setOnClickListener(new View.OnClickListener() {//添加黑名单
            @Override
            public void onClick(View view) {
                set5GblackList();
            }
        });
        bttest8.setOnClickListener(new View.OnClickListener() {//设定定位模式
            @Override
            public void onClick(View view) {

            }
        });
        EventBus.getDefault().register(this);//注册
//        tv = (TextView) view.findViewById(R.id.tv_fragment_name);
//        tv.setText("Child_Fragment3");
        bts_start1_dw = view.findViewById(R.id.bts_start1_dw);
        bts_start1_dw.setOnClickListener(this);
        bts_stop_dw = view.findViewById(R.id.bts_stop_dw);
        bts_stop_dw.setOnClickListener(this);
        bts_stop_dw.setOnClickListener(this);
        ryIMSI = view.findViewById(R.id.ryIMSI_dw);
        ryIMSI.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);//recyview显示最底部一条
        tv_imsi1_dw = view.findViewById(R.id.tv_imsi1_dw);
        tv_imsi2_dw = view.findViewById(R.id.tv_imsi2_dw);
        tv_sb1_jl_dw = view.findViewById(R.id.tv_sb1_jl_dw);
        tv_sb2_jl_dw = view.findViewById(R.id.tv_sb2_jl_dw);
        laba2dw = view.findViewById(R.id.laba2dw);
        laba1dw = view.findViewById(R.id.laba1dw);
        laba2dw.setOnClickListener(this);
        laba1dw.setOnClickListener(this);
        lineChartViewdw = (LineChartView) view.findViewById(R.id.line_chart_viewdw);
        lineChartViewdw2 = (LineChartView2) view.findViewById(R.id.line_chart_viewdw2);
        list1quxian = new ArrayList<>();
        list2quxian = new ArrayList<>();
//        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);
        list1quxian.add(0);

//        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        list2quxian.add(0);
        setqxData(list1quxian, list2quxian);
        setqxData2(list1quxian, list2quxian);
        tv1type = view.findViewById(R.id.tv1type);
        tvtypetext = view.findViewById(R.id.tvtypetext);
        tvtypetext2 = view.findViewById(R.id.tvtypetext2);
        tv2type = view.findViewById(R.id.tv2type);
        //设备2增益
        cbzt1_g = view.findViewById(R.id.cbzt1_g);
        cbzt1_z = view.findViewById(R.id.cbzt1_z);
        cbzt1_d = view.findViewById(R.id.cbzt1_d);
        //设备2增益
        cbzt2_g = view.findViewById(R.id.cbzt2_g);
        cbzt2_z = view.findViewById(R.id.cbzt2_z);
        cbzt2_d = view.findViewById(R.id.cbzt2_d);
        pb = view.findViewById(R.id.pb);
        pb2 = view.findViewById(R.id.pb2);
        ImslList();//初始化IMSI定位列表
        CheckBoxOnclickSet();//增益点击事件
        initTTS();
        if (timerLocation == null) {
            timerLocation = new Timer();
            //schedule方法是执行时间定时任务的方法
            timerLocation.schedule(new TimerTask() {

                //run方法就是具体需要定时执行的任务
                @Override
                public void run() {
                    Message message = new Message();
                    mHandler.sendMessage(message);
                    message.what = 300002;
                    Log.d(TAG, "IMSI 能量值 定时8秒更新handlerrun300002: " + 1);
                }
//            }, 0, 10000);
            }, 0, 11000);
        }
        new NewPersentDW(this);
    }

    //删除黑名单
//    private void Delete5gGlack(String imsi) {
//        if (!TextUtils.isEmpty(imsi)) {
////            new Thread(new Runnable() {
////                @Override
////                public void run() {
////                    BeanBlackSet bean = new BeanBlackSet();
////                    BeanBlackSet.BodyDTO dto = new BeanBlackSet.BodyDTO();
//////                                        dto.setSwitchs("open");
////                    BeanBlackSet.HeaderDTO headerDTO = new BeanBlackSet.HeaderDTO();
////
////                    headerDTO.setSession_id("51119");
////                    headerDTO.setMsg_id("DEL_BLACKLIST");
////                    headerDTO.setCell_id(1);
////                    bean.setHeader(headerDTO);
////                    List<BeanBlackSet.Imsi> list = new ArrayList<>();
////                    BeanBlackSet.Imsi Imsi = new BeanBlackSet.Imsi();
//////                Imsi.setImsi("460026321932409");
//////                list.add(Imsi);
////                    AddPararBean addPararBean = new AddPararBean();
////                    try {
////                        DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
////                        List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
////                        for (int i = 0; i < dataAll.size(); i++) {
////                            if (dataAll.get(i).isCheckbox()) {
////                                addPararBean = dataAll.get(0);
////
////                                break;
////                            }
////
////                        }
////
////
////                    } catch (SQLException throwables) {
////                        throwables.printStackTrace();
////                    }
////                    Imsi = new BeanBlackSet.Imsi();
////                    Imsi.setImsi(imsi);
////                    list.add(Imsi);
////                    dto.setImsi_list(list);
////
////                    bean.setBody(dto);
////                    Log.d("TAGbeanADD_BLACKLIST", "run: " + bean.toString());
////
////                    Gson gson = new Gson();
////                    String s2 = gson.toJson(bean);
////                    Log.d("TAGs2s", "run: " + s2);
////                    byte[] srtbyte = s2.getBytes();
////                    try {
////                        ADDRESS2 = InetAddress.getByName(IP3);
////                    } catch (UnknownHostException e) {
////                        e.printStackTrace();
////                    }
////                    DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, ADDRESS2, 32001);
////                    try {
////                        DS2.sendMessage(outputPacket);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }).start();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Delete5GBlack bean = new Delete5GBlack();
//                    Delete5GBlack.BodyBean bodyBean = new Delete5GBlack.BodyBean();
//
//                    Delete5GBlack.HeaderBean headerBean = new Delete5GBlack.HeaderBean();
//
//                    headerBean.setSessionId("51119");
//                    headerBean.setMsgId("DEL_BLACKLIST");
//                    headerBean.setCellId(1);
//                    bean.setHeader(headerBean);
//                    List<Delete5GBlack.BodyBean.ImsiListBean> list = new ArrayList<>();
//                    Delete5GBlack.BodyBean.ImsiListBean Imsi = new Delete5GBlack.BodyBean.ImsiListBean();
////
//                    Imsi = new Delete5GBlack.BodyBean.ImsiListBean();
//                    Imsi.setImsi(imsi);
//                    list.add(Imsi);
//                    bodyBean.setImsiList(list);
////
//                    bean.setBody(bodyBean);
//                    Log.d("TAGbeandelete", "run: " + bean.toString());
//
//                    Gson gson = new Gson();
//                    String s2 = gson.toJson(bean);
//                    Log.d("TAGs2s", "run: " + s2);
//
////                    s2=s2.replace("\"","");
//                    byte[] srtbyte = s2.getBytes();
//                    try {
//                        ADDRESS2 = InetAddress.getByName(IP3);
//                    } catch (UnknownHostException e) {
//                        e.printStackTrace();
//                    }
//                    DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, ADDRESS2, 32001);
//                    try {
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        DS2.sendMessage(outputPacket);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } else {
//
//            ToastUtils.showToast("黑名单为空");
//        }
//
//
//    }

    //**查询黑名单列表
    private void QueryBlack() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                Bean.BodyDTO dto = new Bean.BodyDTO();
//                dto.setSwitchs("");
//                dto.setImsi("460005192822841");
                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                headerDTO.setSession_id("51118");
                headerDTO.setMsg_id("QUERY_BLACKLIST_REQ");
                headerDTO.setCell_id(1);
                bean.setHeader(headerDTO);
                bean.setBody(dto);
                Log.d("TAGbean", "run: " + bean.toString());

                Gson gson = new Gson();
                String s2 = gson.toJson(bean);
                Log.d("QUERY_BLACKLIST_REQ", "run: " + s2);
                byte[] srtbyte = s2.getBytes();
                try {
                    MyService.ADDRESS2 = InetAddress.getByName(MyService.IP3);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, MyService.ADDRESS2, 32001);
                try {
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    MyService.DS2.send(outputPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //**停止5G 定位号码
    private void Stop5Gdingwei() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                Bean.BodyDTO dto = new Bean.BodyDTO();
//                dto.setSwitchs("");
                String Imsi = "";
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox()) {
                            Imsi = dataAll.get(i).getImsi();
                            break;
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                dto.setImsi(Imsi);
                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                headerDTO.setSession_id("51117");
                headerDTO.setMsg_id("STOP_POSITION");
                headerDTO.setCell_id(1);
                bean.setHeader(headerDTO);
                bean.setBody(dto);
                Log.d("TAGbean", "run: " + bean.toString());

                Gson gson = new Gson();
                String s2 = gson.toJson(bean);
                Log.d("stop_POSITION", "run: " + s2);
                byte[] srtbyte = s2.getBytes();
                try {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    //开始5G定位
    private void Start5Gdingwei() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                Bean.BodyDTO dto = new Bean.BodyDTO();
//                dto.setSwitchs("");
                String Imsi = "";
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox()) {
                            Imsi = dataAll.get(i).getImsi();
                            break;
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
//
//                dto.setImsi("460005192822841");
                dto.setImsi(Imsi);

                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                headerDTO.setSession_id("51116");
                headerDTO.setMsg_id("START_POSITION");
                headerDTO.setCell_id(1);
                bean.setHeader(headerDTO);
                bean.setBody(dto);
                Log.d("TAGbean", "run: " + bean.toString());

                Gson gson = new Gson();
                String s2 = gson.toJson(bean);
                Log.d("START_POSITION", "run: " + s2);
                byte[] srtbyte = s2.getBytes();
                try {
                    InetAddress ADDRESS2 = InetAddress.getByName(MyService.IP3);
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

    //查询小区参数
    private void queryCell() {
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
    }

    List<AddPararBean> dataAll = null;//首页IMSI列表的数据
    List<AddPararBean> listImsiListTrue = null;//装载已经被选中的imsi
    List<AddPararBean> pararBeansList1 = new ArrayList<>();
    RyImsiAdapter ryImsiAdapter;

    //imsi列表
    private void ImslList() {
        try {
            DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(getContext());
            dataAll = dbManagerAddParam.getDataAll();
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
                    pararBeansList1 = listImsiListTrue;

                    ryImsiAdapter = new RyImsiAdapter(getContext(), listImsiListTrue, list1size, config, tv_imsi1_dw, tv_imsi2_dw);//list是imsi列表选中的数据
                    ryIMSI.setAdapter(ryImsiAdapter);
                }

            }
            Log.d("addPararBeans", "init: " + dataAll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Dialog dialog;
    View inflate;
    RyImsiAdapter.IDialogPinConfig config = new RyImsiAdapter.IDialogPinConfig() {
        @Override
        public void call(final String imsi, String sb) {
            if (sb.equals("1")) {
                dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定要定位" + imsi + "吗?");
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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

//                                        Log.d("nzqsendimsi", "run: " + sendListBlack.get(0).getImsi());
//                                        String location = location(sendListBlack.get(0).getImsi(), "04", sa);
//                                        Log.d(TAG, "run: " + location);
                                byte[] outputData = MainUtilsThread.hexStringToByteArray(location(imsi, "04", sa, getContext(), 1));
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
                                Log.e("nzqsendstart1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                                try {
//                                    sb1locationgFlag = true;
                                    socket.send(outputPacket);
//
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                LocationFlag1 = true;
                                long l = System.currentTimeMillis();
//
                            }
                        }).start();
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

            } else if (sb.equals("2")) {

                dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定要定位" + imsi + "吗?");
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String sa = "";
                                if (tf2.equals("TDD")) {
                                    sa = "01";
                                }
                                if (tf2.equals("FDD")) {
                                    sa = "00";
                                }
                                DatagramSocket socket = null;
                                InetAddress address = null;//你本机的ip地址
//                                        Log.e("nzq", "run: nzqsend");
//                                        Log.d("nzqsendimsi", "run: " + sendListBlack.get(0).getImsi());
//                                        String location = location(sendListBlack.get(0).getImsi(), "04", sa);
//                                        Log.d(TAG, "run: " + location);
                                byte[] outputData = MainUtilsThread.hexStringToByteArray(location(imsi, "04", sa, getContext(), 2));
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
                                Log.e("nzqsendstart1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                                try {
//                                    sb2locationgFlag = true;
                                    socket.send(outputPacket);
//
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                LocationFlag2 = true;
                                long l = System.currentTimeMillis();
//
                            }
                        }).start();
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


            } else {
                ToastUtils.showToast("未中标");

            }
        }
    };

    // 是否返回fragment
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (hidden) {
//            String spinner1 = ACacheUtil.getSpinner1();
//            Log.d(TAG, "onHiddenChanged1: " + spinner1+hidden);
//            ImslList();
//        } else {
//            String spinner2 = ACacheUtil.getSpinner2();
//            Log.d(TAG, "onHiddenChanged2: " + spinner2+hidden);
//        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bts_start1_dw:
                if (TextUtils.isEmpty(ACacheUtil.getZD())) {
                    ToastUtils.showToast("请选择建站方式");
                    break;
                }
                if (BtfLAG == true) {
                    ToastUtils.showToast("请先停止定位");
                    break;
                }
                if (type1.equals("定位中")) {
                    ToastUtils.showToast("请先停止定位");
                    return;
                }
                if (ACacheUtil.getZD().equals("0")) {//手动建站
                    Boolean b1 = DbUtils.GetCellDatacheck(mContext, 1);//  4G设备是否选中
                    Boolean b2 = DbUtils.GetCellDatacheck(mContext, 2);//5G设备是否选中

                    if (b1 == true && b2 == true) {//同时选中
                        ToastUtils.showToast("同时选中");


                        sb1FirstFlag = true;
                        sb2FirstFlag = true;
//                        presenter.startsd(1, tf1, mContext, ACacheUtil.getSpinner1(), type1);
                        if (!type1.equals("就绪")) {
                            ToastUtils.showToast("4G设备不在就绪状态");
                            return;
                        }
//                        if (!type2.equals("就绪")) {
//                            ToastUtils.showToast("设备2不在就绪状态");
//                            return;
//                        }
                        if (TextUtils.isEmpty(ACacheUtil.getSpinner1())) {
                            ToastUtils.showToast("4G设备下行频点不能为空");
                            return;
                        }

                        if (!TextUtils.isEmpty(ACacheUtil.getSpinner1())) {
                            List<String> listlt = new ArrayList<>();
                            List<String> listdx = new ArrayList<>();
                            List<String> listyd = new ArrayList<>();
                            try {
                                DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                                List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                                for (int i = 0; i < dataAll.size(); i++) {
                                    if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {//  IMSI选中    并且判断  号码 运营商
                                        listyd.add(dataAll.get(i).getImsi());
                                    }
                                    if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("联通")) {
                                        listlt.add(dataAll.get(i).getImsi());
                                    }
                                    if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("电信")) {
                                        listdx.add(dataAll.get(i).getImsi());
                                    }

                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                            if (listdx.size() > 0 && listlt.size() > 0) {//   有联通 有电信
                                ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                                return;
                            }
                            if (listyd.size() > 0 && listlt.size() > 0) {//   有联通 有电信
                                ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                                return;
                            }
                            if (listyd.size() > 0 && listdx.size() > 0) {//   有联通 有电信
                                ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                                return;
                            }
                            if (listlt.size() > 1) {//
                                ToastUtils.showToast("目标IMSI不能超过10个");
                                return;
                            }
                            if (listdx.size() > 1) {//
                                ToastUtils.showToast("目标IMSI不能超过10个");
                                return;
                            }
                            if (listyd.size() > 1) {//
                                ToastUtils.showToast("目标IMSI不能超过10个");
                                return;
                            }
                            if (DbUtils.blackListIsyidong(mContext) == 1) {
//                                    ToastUtils.showToast("5G黑名单符合条件1个");
                                if (TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                                    ToastUtils.showToast("下行频点不能为空");
                                    return;
                                }

//                                QueryBlack();//查询黑名单
//                                dialog.dismiss();
//                                dialog.cancel();
//                                    return;
                            } else if (DbUtils.blackListIsyidong(mContext) == 0) {
                                ToastUtils.showToast("没有选中的5G黑名单");
                                return;
                            } else if (DbUtils.blackListIsyidong(mContext) > 1) {
                                ToastUtils.showToast("5G黑名单列表不能大于1个");
                                return;
                            }
                            presenter.startsd(true, 1, tf1, mContext, ACacheUtil.getSpinner1(), type1);
                            sb1FirstFlag = true;

                            //启动5G
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Bean bean = new Bean();
//                                    Bean.BodyDTO dto = new Bean.BodyDTO();
//                                    dto.setSwitchs("open");
//                                    Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();
//
//                                    headerDTO.setSession_id(session_id);
//                                    headerDTO.setMsg_id("SET_CELL_SWITCH");
//                                    headerDTO.setCell_id(1);
//                                    bean.setHeader(headerDTO);
//                                    bean.setBody(dto);
//                                    Log.d("TAGbean", "run: " + bean.toString());//
//                                    Gson gson = new Gson();
//                                    String s2 = gson.toJson(bean);
//                                    Log.d("TAGs2s", "run: " + s2);
//                                    byte[] srtbyte = s2.getBytes();
//                                    try {
//                                        ADDRESS2 = InetAddress.getByName(IP3);
//                                    } catch (UnknownHostException e) {
//                                        e.printStackTrace();
//                                    }
//                                    DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, ADDRESS2, 32001);
//                                    try {
//                                        DS2.sendMessage(outputPacket);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }).start();


//                            if (DbUtils.blackListIsyidong(mContext) == 1) {
////                                    ToastUtils.showToast("5G黑名单符合条件1个");
//                                if (TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
//                                    ToastUtils.showToast("下行频点不能为空");
//                                    return;
//                                }
//
//                                QueryBlack();//查询黑名单
////                                dialog.dismiss();
////                                dialog.cancel();
////                                    return;
//                            } else if (DbUtils.blackListIsyidong(mContext) == 0) {
//                                ToastUtils.showToast("没有选中的5G黑名单");
//                                return;
//                            } else if (DbUtils.blackListIsyidong(mContext) > 1) {
//                                ToastUtils.showToast("5G黑名单列表不能大于1个");
//                                return;
//                            }
                        } else {
                            ToastUtils.showToast("4G设备下行频点不能为空");
                        }
                    } else if (b1 == true && b2 == false) {//选中了4G设备
//                        ToastUtils.showToast("选中了");
                        if (!TextUtils.isEmpty(ACacheUtil.getSpinner1())) {
                            List<String> listyd = new ArrayList<>();
                            List<String> listlt = new ArrayList<>();
                            List<String> listdx = new ArrayList<>();
                            try {
                                DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                                List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                                for (int i = 0; i < dataAll.size(); i++) {
                                    if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {//  IMSI选中    并且判断  号码 运营商
                                        listyd.add(dataAll.get(i).getImsi());
                                    }
                                    if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("联通")) {
                                        listlt.add(dataAll.get(i).getImsi());
                                    }
                                    if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("电信")) {
                                        listdx.add(dataAll.get(i).getImsi());
                                    }

                                }
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }

                            if (listdx.size() > 0 && listlt.size() > 0) {//   有联通 有电信
                                ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                                return;
                            }
                            if (listyd.size() > 0 && listlt.size() > 0) {//   有联通 有电信
                                ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                                return;
                            }
                            if (listyd.size() > 0 && listdx.size() > 0) {//   有联通 有电信
                                ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                                return;
                            }
                            if (listlt.size() > 1) {//
                                ToastUtils.showToast("目标IMSI不能超过10个");
                                return;
                            }
                            if (listdx.size() > 1) {//
                                ToastUtils.showToast("目标IMSI不能超过10个");
                                return;
                            }
                            if (listyd.size() > 1) {//
                                ToastUtils.showToast("目标IMSI不能超过10个");
                                return;
                            }

                            presenter.startsd(true, 1, tf1, mContext, ACacheUtil.getSpinner1(), type1);
                            sb1FirstFlag = true;

                        } else {
                            ToastUtils.showToast("4G设备下行频点不能为空");
                        }
                    } else if (b1 == false && b2 == true) { //选中了设备2  只启动5G 定位、
//                        ToastUtils.showToast("选中了设备2");
                        //  a
                        if (TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                            ToastUtils.showToast("5G下行频点不能为空");
                            return;
                        }
                        dialog = new Dialog(mContext, R.style.menuDialogStyleDialogStyle);
                        inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_item_title, null);
                        TextView tv_title = inflate.findViewById(R.id.tv_title);


                        tv_title.setText("确定要启动定位吗?");


                        Button bt_confirm = inflate.findViewById(R.id.bt_confirm);

                        bt_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                start5G();    //2022年前
                                //年后定位流程。
                                //  第一步 判断5G设备 是否选中一人
                                Log.d("blackListIsyidong", "onClick: " + DbUtils.blackListIsyidong(mContext));
                                if (DbUtils.blackListIsyidong(mContext) == 1) {
//                                    ToastUtils.showToast("5G黑名单符合条件1个");
                                    if (TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                                        ToastUtils.showToast("下行频点不能为空");
                                        return;
                                    }

                                    QueryBlack();//查询黑名单
                                    dialog.dismiss();
                                    dialog.cancel();
//                                    return;
                                } else if (DbUtils.blackListIsyidong(mContext) == 0) {
                                    ToastUtils.showToast("没有选中的5G黑名单");
                                    return;
                                } else if (DbUtils.blackListIsyidong(mContext) > 1) {
                                    ToastUtils.showToast("5G黑名单列表不能大于1个");
                                    return;
                                }


//                                set5GblackList();//设置5G黑名单
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

                    } else if (b1 == false && b2 == false) {//同时未选中
                        ToastUtils.showToast("请至少选中一个设备");
                    }

                } else {//自动建站
//
                    if (!type1.equals("就绪")) {
                        ToastUtils.showToast("4G设备不在就绪状态");
                        return;
                    }
                    //弹出窗选择  当前运营商    4+5G  只能选择一个运营商
//                    DialogUtils.SaoPinDialog(1, mContext, inflate, saoPinCallback, tf1, true);
                    // 档期 模式 默认一键 扫频 ， 无弹出窗

                    List<String> listyd = new ArrayList<>();
                    List<String> listlt = new ArrayList<>();
                    List<String> listdx = new ArrayList<>();
                    try {
                        DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                        List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                        for (int i = 0; i < dataAll.size(); i++) {
                            if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {//  IMSI选中    并且判断  号码 运营商
                                listyd.add(dataAll.get(i).getImsi());
                            }
                            if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("联通")) {
                                listlt.add(dataAll.get(i).getImsi());
                            }
                            if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("电信")) {
                                listdx.add(dataAll.get(i).getImsi());
                            }

                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if (listyd.size() == 0 && listlt.size() == 0 && listdx.size() == 0) {
                        ToastUtils.showToast("没有选中的IMSI");
                        return;
                    }
                    if (listyd.size() > 0 && listlt.size() > 0) {
                        ToastUtils.showToast("目标IMSI所属运营商不能超过1个");//  移动  联通
                        return;
                    }
                    if (listyd.size() > 0 && listdx.size() > 0) {
                        ToastUtils.showToast("目标IMSI所属运营商不能超过1个");//移动 电信
                        Log.d("taglistdxyd", "run: ");
                        return;
                    }
                    if (listlt.size() > 0 && listdx.size() > 0) {
                        ToastUtils.showToast("目标IMSI所属运营商不能超过1个");//联通 电信
                        return;
                    }
                    if (listyd.size() > 0 && listlt.size() == 0 && listdx.size() == 0) {//  只有移动
                        if (listyd.size() > 10) {
                            ToastUtils.showToast("同一运营商移动IMSI不能大于10个");
                        }
                        if (tf1.equals("TDD")) {//如果当前是TDD
                            yidongsaopinFlag = 1;
                            //第一步
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {

//                                saopinSend1(saopinBeanList, tf1, SAOPIN);

                            DBManagersaopin dbManagersaopin = null;
                            try {
                                dbManagersaopin = new DBManagersaopin(mContext);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                final List<SaopinBean> saopinBeanList = dbManagersaopin.getStudentquery("移动TDD", "TDD");

                                if (saopinBeanList.size() <= 10) {
                                    dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                                    inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                                    tv_title.setText("确定要启动扫频定位吗");
                                    Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new Thread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    yidong1sucess1 = true;
                                                    yidong1sucess2 = true;
                                                    yidongFlag = true;
                                                    saopinSend1(saopinBeanList, tf1, 1, mContext);

                                                }
                                            }).start();
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

                                } else {
                                    ToastUtils.showToast("移动频点大于10个或等于0个");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        } else {//如果当前是FDD

                            DBManagersaopin dbManagersaopin = null;
                            try {
                                dbManagersaopin = new DBManagersaopin(mContext);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                final List<SaopinBean> saopinBeanList = dbManagersaopin.getStudentquery("移动FDD", "FDD");

                                if (saopinBeanList.size() <= 10) {
//                                    Set1StatusBar("功放开启成功");

                                    dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                                    inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                                    tv_title.setText("确定要启动扫频定位吗");
                                    Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new Thread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    yidongsaopinFlag = 2;
                                                    yidong1sucess1 = true;
                                                    yidong1sucess2 = true;
                                                    yidongFlag = true;
                                                    saopinSend1(saopinBeanList, tf1, 2, mContext);
//
                                                }
                                            }).start();
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
                                } else {
                                    ToastUtils.showToast("移动频点大于10个或等于0个");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (listyd.size() == 0 && listlt.size() > 0 && listdx.size() == 0) {//  只有联通
                        if (listlt.size() > 10) {
                            ToastUtils.showToast("同一运营商联通IMSI不能大于10个");
                            return;
                        }
                        if (tf1.equals("TDD")) {

                            dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
                            tv_title.setText("确定要启动扫频定位吗");
                            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                            bt_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            yidongsaopinFlag = 0;
                                            yidongFlag = false;
                                            zhishiqiehuan(1, "TDD");
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    litongFlag = false;
                                                }
                                            }, 15000);//230秒 移动FDD去扫频
//
                                        }
                                    }).start();
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
                        if (tf1.equals("FDD")) {
                            DBManagersaopin dbManagersaopin = null;
                            try {
                                dbManagersaopin = new DBManagersaopin(mContext);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {
                                final List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("联通", "FDD");
                                if (saopinBeanList2.size() <= 10) {
                                    dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                                    inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                                    tv_title.setText("确定要启动扫频定位吗");
                                    Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new Thread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    saopinSend1(saopinBeanList2, tf1, 3, AppContext.context);

//
                                                }
                                            }).start();
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
                                } else {
                                    ToastUtils.showToast("联通频点大于10个或等于0个");
                                    return;
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (listyd.size() == 0 && listlt.size() == 0 && listdx.size() > 0) {//  只有电信
                        if (listdx.size() > 10) {
                            ToastUtils.showToast("同一运营商IMSI电信不能大于10个");
                            return;
                        }
                        if (tf1.equals("TDD")) {
                            dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                            inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                            TextView tv_title = inflate.findViewById(R.id.tv_title);
                            tv_title.setText("确定要启动扫频定位吗");
                            Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                            bt_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            yidongsaopinFlag = 0;
                                            yidongFlag = false;
                                            zhishiqiehuan(1, "TDD");
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dxFlag = false;
                                                }
                                            }, 8000);//230秒 移动FDD去扫频
//
                                        }
                                    }).start();
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
                        if (tf1.equals("FDD")) {
                            DBManagersaopin dbManagersaopin = null;
                            try {
                                dbManagersaopin = new DBManagersaopin(mContext);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            try {

                                final List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("电信", "FDD");
                                if (saopinBeanList2.size() <= 10) {
                                    dialog = new Dialog(getContext(), R.style.menuDialogStyleDialogStyle);
                                    inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item_title, null);
                                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                                    tv_title.setText("确定要启动扫频定位吗");
                                    Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            new Thread(new Runnable() {

                                                @Override
                                                public void run() {

                                                    saopinSend1(saopinBeanList2, tf1, 4, AppContext.context);
                                                }
                                            }).start();
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


                                } else {
                                    ToastUtils.showToast("电信频点大于10个或等于0个");
                                    return;
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
//

                }
                break;
            case R.id.bts_stop_dw:

//                presenter.stopdw(1, mContext, BtfLAG, bts_start1_dw);
                if (!BtfLAG) {
                    dialog = new Dialog(mContext, R.style.menuDialogStyleDialogStyle);
                    inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_item_title, null);
                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                    String ip = IP1;
//                    if (i == 1) {
                    tv_title.setText("确定要停止定位吗?");
                    ip = IP1;
//                    }
//                    if (i == 2) {
//                        tv_title.setText("确定要停止定位吗?");
//                        ip = IP2;
//                    }
                    Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                    final String finalIp = ip;
                    bt_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            dialog.cancel();
                            MainUtils.StopLocation(IP1);
                            MainUtils.StopLocation(IP2);
//                GFFLAG1 = 2;
//                MainUtils.OpenGF1(1, 2, handler);
                            stopdwup(1);
                            stopdwup(1);
                            Stop5Gdingwei();

                            stop5G();

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
                } else {

                    dialog = new Dialog(mContext, R.style.menuDialogStyleDialogStyle);
                    inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_item_title, null);
                    TextView tv_title = inflate.findViewById(R.id.tv_title);
                    String ip = IP1;
//                    if (i == 1) {
                    tv_title.setText("确定要终止定位环节吗?");
                    ip = IP1;
//                    }
//                    if (i == 2) {
//                        tv_title.setText("确定要停止定位吗?");
//                        ip = IP2;
//                    }
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
                            bt_startFalg(false);


                            TIMERRESTARTFLAG1 = true;

                            yidong1sucess1 = true;

                            yidong1sucess3 = true;

                            yidong1sucess2 = true;

                            litongFlag = true;


                            dxFlag = true;
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
                break;

            case R.id.laba1dw://喇叭1开关
                presenter.setlaba(mContext, laba1dw, 1, laba1Flag);
//                presenter.startsd(false,2, tf2, mContext, ACacheUtil.getSpinner2(), type2);
                break;
            case R.id.laba2dw://喇叭1开关

                presenter.setlaba(mContext, laba2dw, 2, laba2Flag);
                break;
        }
    }

    /**
     * 设置5G黑名单
     */
    private void set5GblackList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BeanBlackSet bean = new BeanBlackSet();
                BeanBlackSet.BodyDTO dto = new BeanBlackSet.BodyDTO();
//                                        dto.setSwitchs("open");
                BeanBlackSet.HeaderDTO headerDTO = new BeanBlackSet.HeaderDTO();

                headerDTO.setSession_id("51111");
                headerDTO.setMsg_id("ADD_BLACKLIST");
                headerDTO.setCell_id(1);
                bean.setHeader(headerDTO);
                List<BeanBlackSet.Imsi> list = new ArrayList<>();
                BeanBlackSet.Imsi Imsi = new BeanBlackSet.Imsi();
//                Imsi.setImsi("460026321932409");
//                list.add(Imsi);
                AddPararBean addPararBean = new AddPararBean();
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox()) {
                            addPararBean = dataAll.get(i);
                            break;
                        }

                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

//                dto.setImsi(addPararBean.getImsi());
                Imsi = new BeanBlackSet.Imsi();
                Imsi.setImsi(addPararBean.getImsi());
                list.add(Imsi);
                dto.setImsi_list(list);

                bean.setBody(dto);
                Log.d("TAGbeanADD_BLACKLIST", "run: " + bean.toString());

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
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MyService.DS2.send(outputPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    /**
     * 删除黑名单
     */
    private void set5GblackListDelete(final String MyIMSI) {
        if (!TextUtils.isEmpty(MyIMSI)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BeanBlackSet bean = new BeanBlackSet();
                    BeanBlackSet.BodyDTO dto = new BeanBlackSet.BodyDTO();
//                                        dto.setSwitchs("open");
                    BeanBlackSet.HeaderDTO headerDTO = new BeanBlackSet.HeaderDTO();

                    headerDTO.setSession_id("51119");
                    headerDTO.setMsg_id("DEL_BLACKLIST");
                    headerDTO.setCell_id(1);
                    bean.setHeader(headerDTO);
                    List<BeanBlackSet.Imsi> list = new ArrayList<>();
                    BeanBlackSet.Imsi Imsi = new BeanBlackSet.Imsi();
//                Imsi.setImsi("460026321932409");
//                list.add(Imsi);
                    AddPararBean addPararBean = new AddPararBean();
                    try {
                        DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                        List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                        for (int i = 0; i < dataAll.size(); i++) {
                            if (dataAll.get(i).isCheckbox()) {
                                addPararBean = dataAll.get(0);

                                break;
                            }

                        }


                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    Imsi = new BeanBlackSet.Imsi();
//                String s = listImsiBlack5G.get(0);
                    Imsi.setImsi(MyIMSI);
                    list.add(Imsi);
                    dto.setImsi_list(list);
                    bean.setBody(dto);
                    Log.d("TAGDEL_BLACKLIST", "run: " + listImsiBlack5G);

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
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        MyService.DS2.send(outputPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ToastUtils.showToast("查询到的黑名单为空");
        }

    }


    private void setdingwBlack() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BeanDw5G bean = new BeanDw5G();
                BeanDw5G.Body dto = new BeanDw5G.Body();
//                                        dto.setSwitchs("open");
                BeanDw5G.Header headerDTO = new BeanDw5G.Header();

                headerDTO.setSessionId("51120");
                headerDTO.setMsgId("START_POSITION");
                headerDTO.setCellId(1);
                bean.setHeader(headerDTO);
                List<BeanBlackSet.Imsi> list = new ArrayList<>();
                BeanBlackSet.Imsi Imsi = new BeanBlackSet.Imsi();
//                Imsi.setImsi("460026321932409");
//                list.add(Imsi);

                AddPararBean addPararBean = new AddPararBean();
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox()) {
                            addPararBean = dataAll.get(i);
                            break;
                        }

                    }


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                dto.setImsi(addPararBean.getImsi());
                bean.setBody(dto);
                Log.d("START_POSITION", "run: " + bean.toString());

                Gson gson = new Gson();
                String s2 = gson.toJson(bean);
                Log.d("TAGs2sSTART_POSITION", "START_POSITIONrun: " + s2);
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

    /**
     * 设置5G定位模式
     */
    private void set5GDW_Mode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BeanBlackSet bean = new BeanBlackSet();
                BeanBlackSet.BodyDTO dto = new BeanBlackSet.BodyDTO();
//                                        dto.setSwitchs("open");
                BeanBlackSet.HeaderDTO headerDTO = new BeanBlackSet.HeaderDTO();

                headerDTO.setSession_id("51112");
                headerDTO.setMsg_id("SET_WORK_MODE");
                headerDTO.setCell_id(1);
                bean.setHeader(headerDTO);
                dto.setWork_mode("position");

                bean.setBody(dto);
                Log.d("TAGbeanADD_BLACKLIST", "run: " + bean.toString());

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

    //启动5G
    private void start5G() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                Bean.BodyDTO dto = new Bean.BodyDTO();
                dto.setSwitchs("open");
                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                headerDTO.setSession_id("51113");
                headerDTO.setMsg_id("SET_CELL_SWITCH");
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
    //停止5G
    private void stop5G() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                Bean.BodyDTO dto = new Bean.BodyDTO();
                dto.setSwitchs("close");
                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                headerDTO.setSession_id("52113");
                headerDTO.setMsg_id("SET_CELL_SWITCH");
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
    Button buttontest;
    String tac5 = "";
    String pci5 = "";
    String plmn5 = "";
    String band5 = "";

    //设置小区 参数
    public void setCell5G() {

        int zhongxinpindian = 0;
        int gscn = 0;
        String spinner2 = ACacheUtil.getSpinner2();
        try {
            DBManagerPinConfig5G config5G = new DBManagerPinConfig5G(mContext);
            List<PinConfigBean5G> student = config5G.getStudent();

            for (int i = 0; i < student.size(); i++) {
                if (student.get(i).getSsb().equals(spinner2)) ;

                zhongxinpindian = Integer.parseInt(student.get(i).getZhongxinpindian());
                gscn = student.get(i).getGscn();
                plmn5 = student.get(i).getPlmn();
                band5 = student.get(i).getBand() + "";
                break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }


        final int finalZhongxinpindian = zhongxinpindian;
        final int finalGscn = gscn;

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    DBManager01 dbManager01 = new DBManager01(mContext);
                    Conmmunit01Bean forid = dbManager01.forid(2);
                    tac5 = forid.getTac();
                    pci5 = forid.getPci();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                CellBean5G cellBean5G = new CellBean5G();
                CellBean5G.BodyBean bodyBean = new CellBean5G.BodyBean();
                CellBean5G.HeaderBean headerBean = new CellBean5G.HeaderBean();
                headerBean.setCellId(1);
                headerBean.setMsgId("SET_CELL_PARAM");
                headerBean.setSessionId("51115");
                cellBean5G.setHeader(headerBean);
                bodyBean.setBand("n" + String.valueOf(band5));//设置band
                bodyBean.setDlnarfcn(finalZhongxinpindian);
//                bodyBean.setDlnarfcn(504990);
                bodyBean.setSsbgscn(finalGscn);
                bodyBean.setPci(Integer.parseInt(pci5));
                bodyBean.setSubfrmassign(2);
                bodyBean.setSpecialsubfrm(44);
                bodyBean.setTac(Integer.parseInt(tac5));
                bodyBean.setPower("max");
                bodyBean.setBandwidth("100M");
                List<CellBean5G.BodyBean.PlmnListBean> list = new ArrayList<>();
                CellBean5G.BodyBean.PlmnListBean plmnListBean = new CellBean5G.BodyBean.PlmnListBean();
                plmnListBean.setPlmn(plmn5);
                list.add(plmnListBean);
//                plmnListBean = new CellBean5G.BodyBean.PlmnListBean();
//                plmnListBean.setPlmn("46001");
//                list.add(plmnListBean);
//
//                plmnListBean = new CellBean5G.BodyBean.PlmnListBean();
//                plmnListBean.setPlmn("46011");
//
//                list.add(plmnListBean);
                bodyBean.setPlmnList(list);

                cellBean5G.setBody(bodyBean);
//
//
//                Bean bean = new Bean();
//                Bean.BodyDTO dto = new Bean.BodyDTO();
//                dto.setSwitchs("open");
//                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();
//
//                headerDTO.setSession_id("51113");
//                headerDTO.setMsg_id("SET_CELL_SWITCH");
//                headerDTO.setCell_id(1);
//                bean.setHeader(headerDTO);
//                bean.setBody(dto);
                Log.d("TAGbean", "run: " + cellBean5G.toString());

                Gson gson = new Gson();
                String s2 = gson.toJson(cellBean5G);
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
                ToastUtils.showToast("111");
            }
        }).start();
    }

    private int yidongsaopinFlag = 1;
    private boolean yidong1sucess1 = true;    //  false    没有重启过     true重启过
    private boolean yidong1sucess2 = true;     //  false    没有重启过     true重启过
    private boolean yidong1sucess3 = true;     //  false    没有重启过     true重启过

    List<SpBean> listyidongTDD = new ArrayList<>();
    List<SpBean> listyidongFDD = new ArrayList<>();
    boolean yidongFlag = false;
    boolean litongFlag = true;
    boolean dxFlag = true;
    SaoPinCallback saoPinCallback = new SaoPinCallback() {
        @Override
        public void sucess(int sb, int sum) {
            Log.d("TAGSaoPinCallback", "sucesssb =: " + sb + "运营商=" + sum);
            if (sum == 1) {//当前选的的是 移动运营商    1 和2  皆为 移动
                List<String> listyd = new ArrayList<>();
                List<String> listlt = new ArrayList<>();
                List<String> listdx = new ArrayList<>();
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    Log.d("dbManagerAddParam", dataAll.toString());
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {//  IMSI选中    并且判断  号码 运营商
                            listyd.add(dataAll.get(i).getImsi());
                        }
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("联通")) {
                            listlt.add(dataAll.get(i).getImsi());
                        }
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("电信")) {
                            listdx.add(dataAll.get(i).getImsi());
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                //////////////////////////////////////////////
                Log.d("TAGlist", "onClick: 移动：" + listyd + "/n" + "   联通：" + listlt + "/n" + "   电信：" + listdx);

//                    判断运营商个数
                if (listyd.size() > 0 && listlt.size() == 0 && listdx.size() == 0) {//只有一个移动
                    if (listyd.size() > 10) {
                        ToastUtils.showToast("移动目标IMSI不能超过10个");
                    } else {//正常范围
                        if (tf1.equals("TDD")) {//如果当前是TDD
                            yidongsaopinFlag = 1;
                            //第一步
//                            mHandler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {

//                                saopinSend1(saopinBeanList, tf1, SAOPIN);
                            yidong1sucess1 = true;
                            yidong1sucess2 = true;
                            yidongFlag = true;
                            DBManagersaopin dbManagersaopin = null;
                            try {
                                dbManagersaopin = new DBManagersaopin(mContext);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                List<SaopinBean> saopinBeanList = dbManagersaopin.getStudentquery("移动TDD", "TDD");

                                if (saopinBeanList.size() <= 10) {
//                                    Set1StatusBar("功放开启成功");
                                    saopinSend1(saopinBeanList, tf1, 1, mContext);
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            zhishiqiehuan(1, "TDD");
//                                            Log.d(TAG, "run: 30秒 切换FDD ");
//                                        }
//                                    }, 30000);//30秒后切换制式
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Log.d(TAG, "run: 230秒 移动FDD去扫频 ");
//                                            DBManagersaopin dbManagersaopin = null;
//                                            try {
//                                                dbManagersaopin = new DBManagersaopin(mContext);
//                                            } catch (SQLException e) {
//                                                e.printStackTrace();
//                                            }
//                                            try {
//
//                                                List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("移动FDD", "FDD");
//                                                if (saopinBeanList2.size() <= 10) {
//                                                    saopinSend1(saopinBeanList2, tf1, 1, context);
//
//                                                } else {
//                                                    ToastUtils.showToast("移动频点大于10个或等于0个");
//                                                }
//
//                                            } catch (SQLException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }, 231000);//230秒 移动FDD去扫频
//
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            List<SpBean> list = new ArrayList<>();
//                                            for (int i = 0; i < listyidongTDD.size(); i++) {
//                                                list.add(listyidongTDD.get(i));
//
//                                            }
//                                            for (int i = 0; i < listyidongFDD.size(); i++) {
//                                                list.add(listyidongFDD.get(i));
//                                            }
//                                            List<Integer> listMax = new ArrayList<>();
//                                            String down = "";
//                                            for (int i = 0; i < list.size(); i++) {
//                                                listMax.add(list.get(i).getPriority());
//                                            }
//                                            Integer max = Collections.max(listMax);
//                                            String band = "";
//                                            for (int i = 0; i < list.size(); i++) {
//                                                if (max.equals(list.get(i).getPriority())) {
//
//                                                    down = list.get(i).getDown();
//                                                    band = list.get(i).getBand();
//                                                }
//                                            }
//                                            String zs = "";
//                                            if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                                                zs = "FDD";
//                                            } else {
//                                                zs = "TDD";
//                                            }
//                                            if (tf1.equals(zs)) {//当前制式 是否 和扫频结果比较的 一直  一直直接建立小区
//                                                presenter.saopinjianlixiaoqu(context, 1, tf1, down);
//                                            }
//                                            if (!tf1.equals(zs)) {//当前制式  和扫频结果比较的 不一致  切换制式  建立小区
//                                                zhishiqiehuan(1, tf1);
//
//                                                String finalDown = down;
//                                                mHandler.postDelayed(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        presenter.saopinjianlixiaoqu(context, 1, tf1, finalDown);
//                                                    }
//                                                }, 230000);//230秒 移动FDD去扫频
//
//                                            }
//                                        }
//                                    }, 251000);//250秒 移动TDD 和FDD 比较  然后去建立小区
                                } else {
                                    ToastUtils.showToast("移动频点大于10个或等于0个");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
//                                }
//                            }, 20000);

                        } else {//如果当前是FDD
                            yidongsaopinFlag = 2;
////


                            yidong1sucess1 = true;
                            yidong1sucess2 = true;
                            yidongFlag = true;
                            DBManagersaopin dbManagersaopin = null;
                            try {
                                dbManagersaopin = new DBManagersaopin(mContext);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            try {
                                List<SaopinBean> saopinBeanList = dbManagersaopin.getStudentquery("移动FDD", "FDD");

                                if (saopinBeanList.size() <= 10) {
//                                    Set1StatusBar("功放开启成功");
                                    saopinSend1(saopinBeanList, tf1, 2, mContext);
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            zhishiqiehuan(1, "TDD");
//                                            Log.d(TAG, "run: 30秒 切换FDD ");
//                                        }
//                                    }, 30000);//30秒后切换制式
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Log.d(TAG, "run: 230秒 移动FDD去扫频 ");
//                                            DBManagersaopin dbManagersaopin = null;
//                                            try {
//                                                dbManagersaopin = new DBManagersaopin(mContext);
//                                            } catch (SQLException e) {
//                                                e.printStackTrace();
//                                            }
//                                            try {
//
//                                                List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("移动FDD", "FDD");
//                                                if (saopinBeanList2.size() <= 10) {
//                                                    saopinSend1(saopinBeanList2, tf1, 1, context);
//
//                                                } else {
//                                                    ToastUtils.showToast("移动频点大于10个或等于0个");
//                                                }
//
//                                            } catch (SQLException e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    }, 231000);//230秒 移动FDD去扫频
//
//                                    mHandler.postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            List<SpBean> list = new ArrayList<>();
//                                            for (int i = 0; i < listyidongTDD.size(); i++) {
//                                                list.add(listyidongTDD.get(i));
//
//                                            }
//                                            for (int i = 0; i < listyidongFDD.size(); i++) {
//                                                list.add(listyidongFDD.get(i));
//                                            }
//                                            List<Integer> listMax = new ArrayList<>();
//                                            String down = "";
//                                            for (int i = 0; i < list.size(); i++) {
//                                                listMax.add(list.get(i).getPriority());
//                                            }
//                                            Integer max = Collections.max(listMax);
//                                            String band = "";
//                                            for (int i = 0; i < list.size(); i++) {
//                                                if (max.equals(list.get(i).getPriority())) {
//
//                                                    down = list.get(i).getDown();
//                                                    band = list.get(i).getBand();
//                                                }
//                                            }
//                                            String zs = "";
//                                            if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
//                                                zs = "FDD";
//                                            } else {
//                                                zs = "TDD";
//                                            }
//                                            if (tf1.equals(zs)) {//当前制式 是否 和扫频结果比较的 一直  一直直接建立小区
//                                                presenter.saopinjianlixiaoqu(context, 1, tf1, down);
//                                            }
//                                            if (!tf1.equals(zs)) {//当前制式  和扫频结果比较的 不一致  切换制式  建立小区
//                                                zhishiqiehuan(1, tf1);
//
//                                                String finalDown = down;
//                                                mHandler.postDelayed(new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        presenter.saopinjianlixiaoqu(context, 1, tf1, finalDown);
//                                                    }
//                                                }, 230000);//230秒 移动FDD去扫频
//
//                                            }
//                                        }
//                                    }, 251000);//250秒 移动TDD 和FDD 比较  然后去建立小区
                                } else {
                                    ToastUtils.showToast("移动频点大于10个或等于0个");
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }


//                        MainUtils.start1SNF(IP1, Constant.SNFTDD);
//                        DBManagersaopin dbManagersaopin = null;
//                        try {
//                            dbManagersaopin = new DBManagersaopin(mContext);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//
//                        try {
//                            List<SaopinBean> saopinBeanList = dbManagersaopin.getStudentquery("移动TDD", "TDD");
//                            List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("移动FDD", "FDD");
//                            if (saopinBeanList2.size() <= 10 && saopinBeanList.size() < 10) {
////                                    Set1StatusBar("功放开启成功");
//                                yidongFlag = true;
//                                // 这里判断 当前制式   去扫频
//                                if (tf1.equals("TDD")) {
//                                    saopinSend1(saopinBeanList, tf1, 1, context);
//                                    listyidongTDD.clear();
//                                    listyidongFDD.clear();
//                                    listTDDFlag = false;
//                                    listFDDFlag = false;
//                                    Log.d("yyds移动TDD先去扫", "sucess: ");
//                                }
//                                if (tf1.equals("FDD")) {
//                                    saopinSend1(saopinBeanList2, tf1, 1, context);
//                                    Log.d("yyds移动FDD先去扫", "sucess: ");
//                                    listyidongTDD.clear();
//                                    listyidongFDD.clear();
//                                    listTDDFlag = false;
//                                    listFDDFlag = false;
//                                }
//                            } else {
//                                ToastUtils.showToast("移动频点大于10个或等于0个");
//                            }
//
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
                if (listyd.size() == 0 && listlt.size() > 0 && listdx.size() == 0) {//只有一个联通
//                    if (listlt.size() > 10) {
//                        ToastUtils.showToast("联通目标IMSI不能超过10个");
//                    } else {//正常范围
//                        MainUtils.start1SNF(IP2, Constant.SNFFDD);
//                        DBManagersaopin dbManagersaopin = null;
//                        try {
//                            dbManagersaopin = new DBManagersaopin(mContext);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC
//                        try {
//                            List<SaopinBean> saopinBeanList = dbManagersaopin.getStudentquery("联通", "FDD");
//                            if (saopinBeanList != null && saopinBeanList.size() > 0) {
////                                    Set1StatusBar("功放开启成功");
//                                if (saopinBeanList.size() > 10) {
//                                    ToastUtils.showToast("扫频不能大于10个");
//                                } else {
//                                    saopinSend2(saopinBeanList, tf2, 3, context);
//                                }
//
//                            } else {
//                                ToastUtils.showToast("当前没有频点");
//                            }
//
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    ToastUtils.showToast("频点与IMSI运营商不符");
                }
//                if (listyd.size() > 0 && listlt.size() == 0 && listdx.size() == 0) {//只有一个电信
////                    if (listdx.size() > 10) {
////                        ToastUtils.showToast("电信目标IMSI不能超过10个");
////                    } else {//正常范围
////
////                    }
//                    ToastUtils.showToast("频点与IMSI运营商不符");
//                }
                if (listyd.size() > 0 && listlt.size() > 0 && listdx.size() == 0) {//有移动 联通 无电信
//                    if (listyd.size() > 10) {
//                        ToastUtils.showToast("移动目标IMSI不能超过10个");
//                        return;
//                    }
//                    if (listlt.size() > 10) {
//                        ToastUtils.showToast("联通目标IMSI不能超过10个");
//                        return;
//                    }
//                    if (listlt.size() > 10) {
//                        ToastUtils.showToast("联通目标IMSI不能超过10个");
//                        return;
//                    }
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
                if (listyd.size() == 0 && listlt.size() > 0 && listdx.size() > 0) {// 联通 电信  无移动
//                    if (listlt.size() > 10) {
//                        ToastUtils.showToast("联通目标IMSI不能超过10个");
//                        return;
//                    }
//                    if (listdx.size() > 10) {
//                        ToastUtils.showToast("电信目标IMSI不能超过10个");
//                        return;
//                    }
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
//
                if (listyd.size() > 0 && listlt.size() > 0 && listdx.size() > 0) {//有移动   有联通 有电信
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
                Log.d("TAGlist", "onClick: 移动：" + listyd + "/n" + "   联通：" + listlt + "/n" + "   电信：" + listdx);
//                    presenter.setGKtart(mContext, tf1, tf2, type1, type2, GuankongType);

            }
            if (sum == 3) {//当前选的的是 联通运营商

                yidongFlag = false;
                List<String> listyd = new ArrayList<>();
                List<String> listlt = new ArrayList<>();
                List<String> listdx = new ArrayList<>();
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {//  IMSI选中    并且判断  号码 运营商
                            listyd.add(dataAll.get(i).getImsi());
                        }
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("联通")) {
                            listlt.add(dataAll.get(i).getImsi());
                        }
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("电信")) {
                            listdx.add(dataAll.get(i).getImsi());
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                Log.d("TAGlist", "onClick: 移动：" + listyd + "/n" + "   联通：" + listlt + "/n" + "   电信：" + listdx);

                if (listyd.size() > 0 && listlt.size() > 0) {//有移动   有联通 有电信
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
                if (listdx.size() > 0 && listlt.size() > 0) {//有移动   有联通 有电信
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
                if (listlt.size() > 10) {//
                    ToastUtils.showToast("目标IMSI不能超过10个");
                    return;
                }
                if (listlt.size() == 0) {//
                    ToastUtils.showToast("频点与IMSI运营商不符");
                    return;
                }

                if (tf1.equals("TDD")) {
                    yidongFlag = false;
                    zhishiqiehuan(1, "TDD");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            DBManagersaopin dbManagersaopin = null;
//                            try {
//                                dbManagersaopin = new DBManagersaopin(mContext);
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//
//                                List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("联通", "FDD");
//                                if (saopinBeanList2.size() <= 10) {
//                                    saopinSend1(saopinBeanList2, tf1, 3, context);
//
//                                } else {
//                                    ToastUtils.showToast("联通频点大于10个或等于0个");
//                                }
//
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
                            litongFlag = false;
                        }
                    }, 30000);//230秒 移动FDD去扫频
                }
                if (tf1.equals("FDD")) {
                    DBManagersaopin dbManagersaopin = null;
                    try {
                        dbManagersaopin = new DBManagersaopin(mContext);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {

                        List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("联通", "FDD");
                        if (saopinBeanList2.size() <= 10) {
                            saopinSend1(saopinBeanList2, tf1, 3, AppContext.context);

                        } else {
                            ToastUtils.showToast("联通频点大于10个或等于0个");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (sum == 4) {//当前选的的是 电信运营商
                yidongFlag = false;
                List<String> listyd = new ArrayList<>();
                List<String> listlt = new ArrayList<>();
                List<String> listdx = new ArrayList<>();
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                    for (int i = 0; i < dataAll.size(); i++) {
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {//  IMSI选中    并且判断  号码 运营商
                            listyd.add(dataAll.get(i).getImsi());
                        }
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("联通")) {
                            listlt.add(dataAll.get(i).getImsi());
                        }
                        if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("电信")) {
                            listdx.add(dataAll.get(i).getImsi());
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (listyd.size() > 0 && listlt.size() > 0) {//有移动   有联通 有电信
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
                if (listdx.size() > 0 && listlt.size() > 0) {//有移动   有联通 有电信
                    ToastUtils.showToast("目标IMSI所属运营商不能超过1个");
                    return;
                }
                if (listdx.size() > 10) {//
                    ToastUtils.showToast("目标IMSI不能超过10个");
                    return;
                }
                if (listdx.size() == 10) {//
                    ToastUtils.showToast("频点与IMSI运营商不符");
                    return;
                }
                if (tf1.equals("TDD")) {
                    yidongFlag = false;
                    zhishiqiehuan(1, "TDD");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dxFlag = false;
                        }
                    }, 230000);//230秒 移动FDD去扫频
                }
                if (tf1.equals("FDD")) {
                    DBManagersaopin dbManagersaopin = null;
                    try {
                        dbManagersaopin = new DBManagersaopin(mContext);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {

                        List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("电信", "FDD");
                        if (saopinBeanList2.size() <= 10) {
                            saopinSend1(saopinBeanList2, tf1, 4, AppContext.context);

                        } else {
                            ToastUtils.showToast("电信频点大于10个或等于0个");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };


    //扫频1
    private void saopinSend1(List<SaopinBean> saopinBeanList, String tf1, int yy, Context context) {
//        Log.d(TAG, "listImsiListTruesad1: " + listImsiListTrue);
        List<Integer> list = new ArrayList<>();
        Log.d(TAG, "AsaopinSend1: " + saopinBeanList + "制式" + yy);
        DBManagersaopin dbManagersaopin = null;
        try {
            dbManagersaopin = new DBManagersaopin(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            saopinBeanList = dbManagersaopin.getStudent();
//            Log.d(TAG, "AsaopinSend1: " + saopinBeanList + "制式" + zs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (saopinBeanList.size() > 0) {

            for (int i = 0; i < saopinBeanList.size(); i++) {
                if (saopinBeanList.get(i).getYy().equals(MyUtils.YYname(yy)) && saopinBeanList.get(i).getType() == 1) {
                    list.add(saopinBeanList.get(i).getDown());
                }
            }

            if (list.size() > 0) {
                if (list.size() > 10) {
                    ToastUtils.showToast("扫频列表大于10条");
                } else {
                    MainUtils.sendspSocket(list, IP1);

                    BtfLAG = true;
//                    bts_start1_dw.setEnabled(false);
                    bts_start1_dw.setBackground(context.getResources().getDrawable(R.color.hui));
//                    Log.d(TAG, "saopinSend1: " + list);
                }

            } else {
//                ToastUtils.showToast("当前没有" + zs + "的制式");

            }

        } else {
            ToastUtils.showToast("当前没有频点");
        }
    }

    //扫频2
    private void saopinSend2(List<SaopinBean> saopinBeanList, String tf1, int yy, Context context) {
//        Log.d(TAG, "listImsiListTruesad1: " + listImsiListTrue);
        List<Integer> list = new ArrayList<>();
        Log.d(TAG, "AsaopinSend1: " + saopinBeanList + "制式" + yy);
        DBManagersaopin dbManagersaopin = null;
        try {
            dbManagersaopin = new DBManagersaopin(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            saopinBeanList = dbManagersaopin.getStudent();
//            Log.d(TAG, "AsaopinSend1: " + saopinBeanList + "制式" + zs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (saopinBeanList.size() > 0) {

            for (int i = 0; i < saopinBeanList.size(); i++) {
                if (saopinBeanList.get(i).getYy().equals(MyUtils.YYname(yy)) && saopinBeanList.get(i).getType() == 1) {
                    list.add(saopinBeanList.get(i).getDown());
                }
            }

            if (list.size() > 0) {
                if (list.size() > 10) {
                    ToastUtils.showToast("扫频列表大于10条");
                } else {
                    MainUtils.sendspSocket(list, IP2);
//                    Log.d(TAG, "saopinSend1: " + list);
                }

            } else {
//                ToastUtils.showToast("当前没有" + zs + "的制式");

            }

        } else {
            ToastUtils.showToast("当前没有频点");
        }
    }

    //增益  4G设备
    private void CheckBoxOnclickSet() {

        //4G设备
        cbzt1_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbzt1_z.setChecked(false);
                    cbzt1_g.setChecked(false);
                    try {
                        DBManagerZY dbManagerZY = new DBManagerZY(getContext());
                        if (tf1.equals("TDD")) {
                            int data = dbManagerZY.foriddata(1, 1, 1);
                            setzy(data, 1);
                        }
                        if (tf1.equals("FDD")) {
                            int data = dbManagerZY.foriddata(1, 2, 1);
                            setzy(data, 1);
                            Log.d("zydata", "onCheckedChanged: " + data);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//
                } else {
//                    cbzt1_d.setChecked(true);
//                    cbzt1_z.setChecked(false);
//                    cbzt1_g.setChecked(false);
                    if (!cbzt1_z.isChecked() && !cbzt1_g.isChecked()) {
                        cbzt1_d.setChecked(true);
                        Log.d("cbzt1_da", "1onCheckedChanged: ");
                    } else {
                        Log.d("cbzt1_da", "2onCheckedChanged: ");
                    }
                }

            }
        });
        cbzt1_z.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbzt1_d.setChecked(false);
                    cbzt1_g.setChecked(false);
                    try {
                        DBManagerZY dbManagerZY = new DBManagerZY(getContext());
                        if (tf1.equals("TDD")) {
                            int data = dbManagerZY.foriddata(1, 1, 2);
                            setzy(data, 1);
                        }
                        if (tf1.equals("FDD")) {
                            int data = dbManagerZY.foriddata(1, 2, 2);
                            setzy(data, 1);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!cbzt1_d.isChecked() && !cbzt1_g.isChecked()) {
                        cbzt1_z.setChecked(true);
                        Log.d("cbzt1_da", "1onCheckedChanged: ");
                    } else {
                        Log.d("cbzt1_da", "2onCheckedChanged: ");
                    }
                }
            }
        });
        cbzt1_g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbzt1_z.setChecked(false);
                    cbzt1_d.setChecked(false);
                    try {
                        DBManagerZY dbManagerZY = new DBManagerZY(getContext());
                        if (tf1.equals("TDD")) {
                            int data = dbManagerZY.foriddata(1, 1, 3);
                            setzy(data, 1);
                        }
                        if (tf1.equals("FDD")) {
                            int data = dbManagerZY.foriddata(1, 2, 3);
                            setzy(data, 1);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!cbzt1_z.isChecked() && !cbzt1_d.isChecked()) {
                        cbzt1_g.setChecked(true);
                        Log.d("cbzt1_da", "1onCheckedChanged: ");
                    } else {
                        Log.d("cbzt1_da", "2onCheckedChanged: ");
                    }
                }
            }
        });

        //设备2
        cbzt2_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbzt2_z.setChecked(false);
                    cbzt2_g.setChecked(false);
                    try {
                        DBManagerZY dbManagerZY = new DBManagerZY(getContext());
                        if (tf2.equals("TDD")) {
                            int data = dbManagerZY.foriddata(2, 1, 1);
                            setzy(data, 2);
                        }
                        if (tf2.equals("FDD")) {
                            int data = dbManagerZY.foriddata(2, 2, 1);
                            setzy(data, 2);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!cbzt2_z.isChecked() && !cbzt2_g.isChecked()) {
                        cbzt2_d.setChecked(true);
                        Log.d("cbzt1_da", "1onCheckedChanged: ");
                    } else {
                        Log.d("cbzt1_da", "2onCheckedChanged: ");
                    }
                }
            }
        });
        cbzt2_z.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbzt2_d.setChecked(false);
                    cbzt2_g.setChecked(false);
                    try {
                        DBManagerZY dbManagerZY = new DBManagerZY(getContext());
                        if (tf2.equals("TDD")) {
                            int data = dbManagerZY.foriddata(2, 1, 2);
                            setzy(data, 2);
                        }
                        if (tf2.equals("FDD")) {
                            int data = dbManagerZY.foriddata(2, 2, 2);
                            setzy(data, 2);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!cbzt2_d.isChecked() && !cbzt2_g.isChecked()) {
                        cbzt2_z.setChecked(true);
                        Log.d("cbzt1_da", "1onCheckedChanged: ");
                    } else {
                        Log.d("cbzt1_da", "2onCheckedChanged: ");
                    }
                }
            }
        });
        cbzt2_g.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbzt2_z.setChecked(false);
                    cbzt2_d.setChecked(false);
                    try {
                        DBManagerZY dbManagerZY = new DBManagerZY(getContext());
                        if (tf2.equals("TDD")) {
                            int data = dbManagerZY.foriddata(2, 1, 3);
                            setzy(data, 2);
                        }
                        if (tf2.equals("FDD")) {
                            int data = dbManagerZY.foriddata(2, 2, 3);
                            setzy(data, 2);
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (!cbzt2_z.isChecked() && !cbzt2_d.isChecked()) {
                        cbzt2_g.setChecked(true);
                        Log.d("cbzt1_da", "1onCheckedChanged: ");
                    } else {
                        Log.d("cbzt1_da", "2onCheckedChanged: ");
                    }
                }
            }
        });
    }

    //初始化数据方法
    @Override
    public void initData() {

    }

    //销毁时方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("lcm", "onDestroy");
    }

    private String tf1 = "";
    private String tf2 = "";
    ImageButton laba2dw, laba1dw;
    private boolean laba1Flag = true;//默认声音开启
    private boolean laba2Flag = true;
    ProgressBar pb;
    Timer timerLocation;
    //主线程执行的任务
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {


                case 300002:
//                    if (type1.equals("就绪")) {
                    tv_imsi1_dw.setText("");
                    tv_sb1_jl_dw.setText("");
//                    }
//                    if (type2.equals("就绪")) {
                        tv_imsi2_dw.setText("");
                        tv_sb2_jl_dw.setText("");
//                    }

                    break;

                case 300001:
                    //去重
                    List<States> listremoveDuplicate = removeDuplicate(listStates);
                    Log.d(TAG, "handleMessagesslistStates: " + listremoveDuplicate.size() + listremoveDuplicate.toString());
                    for (int i = 0; i < pararBeansList1.size(); i++) {
                        pararBeansList1.get(i).setSb("");
//                    pararBeansList1.get(i).setZb("");
                    }
                    for (int i = 0; i < pararBeansList1.size(); i++) {
                        for (int j = 0; j < listremoveDuplicate.size(); j++) {
                            if (listremoveDuplicate.get(j).getImsi().equals(pararBeansList1.get(i).getImsi())) {
                                pararBeansList1.get(i).setSb(listremoveDuplicate.get(j).getSb());
                                pararBeansList1.get(i).setZb(listremoveDuplicate.get(j).getZb());
                            }
                        }
                    }
                    List<Integer> size = new ArrayList<>();
                    for (int i = 1; i < pararBeansList1.size() + 1; i++) {
                        size.add(i);
                    }
                    final List<String> listFirstIMSI1 = new ArrayList<>();
                    final List<String> listFirstIMSI2 = new ArrayList<>();
                    if (pararBeansList1.size() > 0) {
                        for (int i = 0; i < pararBeansList1.size(); i++) {
                            if (pararBeansList1.get(i).getSb().equals("4G")) {
                                listFirstIMSI1.add(pararBeansList1.get(i).getImsi());
                            }
                            if (pararBeansList1.get(i).getSb().equals("5G")) {
                                listFirstIMSI2.add(pararBeansList1.get(i).getImsi());
                            }
                        }
                    }
                    if (listFirstIMSI1.size() > 0) {
                        if (sb1FirstFlag == true) {
                            if (type1.equals("定位中")) {
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
//                                        Log.e("nzq", "run: nzqsend");
//                                        Log.d("nzqsendimsi", "run: " + sendListBlack.get(0).getImsi());
//                                        String location = location(sendListBlack.get(0).getImsi(), "04", sa);
//                                        Log.d(TAG, "run: " + location);
                                        byte[] outputData = MainUtilsThread.hexStringToByteArray(location(listFirstIMSI1.get(0), "04", sa, mContext, 1));
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
                                        Log.e("nzqsendstart1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                                        try {

                                            socket.send(outputPacket);
//
                                            sb1FirstFlag = false;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        listFirstIMSI1.clear();


                                    }
                                }).start();
                            }

                        }
                    }
                    if (listFirstIMSI2.size() > 0) {
                        if (sb2FirstFlag == true) {
                            if (tv2type.equals("定位中")) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String sa = "";
                                        if (tf2.equals("TDD")) {
                                            sa = "01";
                                        }
                                        if (tf2.equals("FDD")) {
                                            sa = "00";
                                        }
                                        DatagramSocket socket = null;
                                        InetAddress address = null;//你本机的ip地址
//                                        Log.e("nzq", "run: nzqsend");
//                                        Log.d("nzqsendimsi", "run: " + sendListBlack.get(0).getImsi());
//                                        String location = location(sendListBlack.get(0).getImsi(), "04", sa);
//                                        Log.d(TAG, "run: " + location);
                                        byte[] outputData = MainUtilsThread.hexStringToByteArray(location(listFirstIMSI2.get(0), "04", sa, mContext, 2));
                                        try {
                                            socket = new DatagramSocket();
                                            address = InetAddress.getByName(IP2);
                                        } catch (UnknownHostException e) {
                                            e.printStackTrace();
                                        } catch (SocketException e) {
                                            e.printStackTrace();
                                        }
                                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, address, 3345);
//                        DatagramPacket outputPacket = new DatagramPacket(outputData, outputData.length, reIP, Integer.parseInt(et_port.getText().toString()));
                                        Log.e("nzqsendstart1", "run: sendsocket端口号" + outputPacket.getPort() + "Ip地址:" + outputPacket.getAddress().toString() + "数据:" + outputPacket.getData());
                                        try {

                                            socket.send(outputPacket);
                                            sb2FirstFlag = false;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        listFirstIMSI2.clear();

                                    }
                                }).start();
                            }
                        }
                    }
                    List<AddPararBean> pararBeansListA = new ArrayList<>();
                    Log.d(TAG, "handleMessage: " + pararBeansList1);

                    ryImsiAdapter = new RyImsiAdapter(mContext, pararBeansList1, size, config, tv_imsi1_dw, tv_imsi2_dw);//list是imsi列表选中的数据
                    ryIMSI.setAdapter(ryImsiAdapter);
                    listStates.clear();
                    break;
            }
        }


    };
    private boolean sb1FirstFlag = false;  //是否第一次定位
    private boolean sb2FirstFlag = false;
    private boolean TIMERRESTARTFLAG1 = true;  //是否重启完成1  若重启完 true
    private boolean TIMERRESTARTFLAG2 = true;  //是否重启完成1  若重启完 true

    private boolean GBLACKFLAG5G = false;
    List<String> listImsiBlack5G = new ArrayList<>();
    private boolean DINGWFLAG5G = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        String data = (String) event.getData();
//        tv.setText(data);
        String spinner1 = ACacheUtil.getSpinner1();
        Log.d(TAG, "onHiddenChanged: " + spinner1);
        String spinner2 = ACacheUtil.getSpinner2();
        Log.d(TAG, "onHiddenChanged: " + spinner2);
//        ImslList();
        switch (event.getCode()) {
            //5G
            case 5100:
                Log.d(TAG, "handleMessage: " + event.getData());
                ToastUtils.showToast("" + event.getData());
                tv2type.setText("设备2:" + "定位中");
                break;

            case 5101://5G设备状态
                if (MyService.SETUP_LINK_RSP_FLAG == true) {
                    tv2type.setText("5G设备:" + event.getData());
                    if (event.getData().equals("连接中")) {
                        tvtypetext2.setVisibility(View.VISIBLE);
                    }
                    if (event.getData().equals("就绪")) {
                        if (DINGWFLAG5G) {
                            tv2type.setText("5G设备:" + "定位中");
                        } else {
                            tvtypetext2.setVisibility(View.GONE);
                            tv2type.setText("5G设备:" + "就绪");
                            tv_sb2_jl_dw.setText("");
                        }

                    }
                    if (event.getData().equals("连接失败")) {
                        tvtypetext2.setVisibility(View.VISIBLE);
                    }
                } else {
                    tv2type.setText("5G设备:" + "连接中");
                    tvtypetext2.setVisibility(View.VISIBLE);
                }

                break;

            case 51111://5G黑名单设置

//                ToastUtils.showToast("5G黑名单设置" + event.getData());
                GBLACKFLAG5G = false;
//                if (!GBLACKFLAG5G){
//                set5GDW_Mode();
                setCell5G();//设置小区参数
                Log.d("GBLACKFLAG5G", "onMessageEvent: ");
//                    GBLACKFLAG5G=true;
//                }
                tv2type.setText("5G设备:小区激活中");
                tvtypetext2.setVisibility(View.VISIBLE);
                break;

            case 51112://5G定位模式 scout用于侦码，position用于定位。

//                ToastUtils.showToast("5G模式设置:" + event.getData());
                if (event.getData().equals("成功")) {
                    start5G();

                    tv2type.setText("5G设备:小区激活中");
                    tvtypetext2.setVisibility(View.VISIBLE);
                } else {

                }
                break;
            case 51113:

//                ToastUtils.showToast("5G激活小区:" + event.getData());
                if (event.getData().equals("成功")) {
                    setdingwBlack();//设置定位IMSI
                    DINGWFLAG5G = true;
                    tv2type.setText("5G设备:定位中");
                    tvtypetext2.setVisibility(View.GONE);
                }
                if (event.getData().equals("失败")) {

                }
                break;
            case 51114://查询小区参数
//                ToastUtils.showToast("5G查询小区参数:" + event.getData());
                break;
            case 51115:
//                ToastUtils.showToast("5G小区参数:" + event.getData());
                if (event.getData().equals("成功")) {
//                    start5G();
                    set5GDW_Mode();//设置定位模式
                }
                break;
            case 51116:


//                ToastUtils.showToast("5G开始定位:" + event.getData());
            case 51117:
//                ToastUtils.showToast("5G停止定位:" + event.getData());
                DINGWFLAG5G = false;
                tv2type.setText("5G设备:就绪");
                tvtypetext2.setVisibility(View.GONE);
                break;
            case 51118:

                listImsiBlack5G = (List<String>) event.getData();
//                ToastUtils.showToast("5G黑名单列表:" + listImsiBlack5G);
                if (listImsiBlack5G.size() == 0) {//如果没有存储过的黑名单 就先设置
                    set5GblackList();
                } else if (listImsiBlack5G.size() == 1) {//如果有存储过的黑名单 为1 先清除
                    String imis = listImsiBlack5G.get(0);
                    Log.d("TAG51118", "onMessageEvent: " + imis);
                    set5GblackListDelete(imis);

                } else if (listImsiBlack5G.size() > 1) {//如果有存储过的黑名单 大于1个 循环清除然后设置黑名单
                    for (int i = 0; i < listImsiBlack5G.size(); i++) {
                        set5GblackListDelete(listImsiBlack5G.get(i));
                    }
                }
                break;

            case 51119:


//                ToastUtils.showToast("5G删除黑名单:" + event.getData());
                if (event.getData().equals("成功")) {
                    set5GblackList();
                    tv2type.setText("5G设备:小区激活中");
                    tvtypetext2.setVisibility(View.VISIBLE);
                }
//                if (event.getData().equals("失败")){
//                    set5GblackList();
//                }
                break;
            case 51120:
//                ToastUtils.showToast("5G定位黑名单一个:" + event.getData());
                break;

            case 52113:
//                ToastUtils.showToast("5G停止小区:" + event.getData());
                if (event.getData().equals("成功")) {
                    DINGWFLAG5G = false;

                }
                if (event.getData().equals("失败")) {

                }
                break;
            case 30000:
                Map map = (Map) event.getData();
                sbImsiType(map);
                Log.d("ChidFragment3 30000", "onMessageEvent: " + map.toString());
                break;


            case 100110:
                tf1 = event.getData() + "";//当前4G设备制式
                Log.d("100110", "onMessageEvent: " + 100110 + tf1);
                break;
            case 111111:
                ImslList();
                break;
            case 100121://4G扫频
                Log.d("TAGevent.getData()", "onMessageEvent: " + event.getData() + "  yidongsaopinFlag=" + yidongsaopinFlag + " yidong1sucess1 " + yidong1sucess1 + "   yidong1sucess2" + yidong1sucess2);//扫频失败、
                if (yidongFlag) {
                    if (yidongsaopinFlag == 1) {
                        if (yidong1sucess1 == true && yidong1sucess2 == true) {
                            zhishiqiehuan(1, "TDD");       //移动模式1 TDD 完成  切换 制式
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    yidong1sucess1 = false;
                                    Log.d("nzqasa", "onMessageEvent: 1");
                                }
                            }, 30000);//
                        }
                        if (yidong1sucess1 == true && yidong1sucess2 == false) {
//                        yidong1sucess2 = false;
                            Log.d("1nzqasa", "onMessageEvent: 2");
                            List<SpBean> list = new ArrayList<>();
                            for (int i = 0; i < listyidongTDD.size(); i++) {
                                list.add(listyidongTDD.get(i));

                            }
                            for (int i = 0; i < listyidongFDD.size(); i++) {
                                list.add(listyidongFDD.get(i));
                            }
                            List<Integer> listMax = new ArrayList<>();
                            String down = "";
                            for (int i = 0; i < list.size(); i++) {
                                listMax.add(list.get(i).getPriority());
                            }
                            Log.d("TAGaweq", "onMessageEventlistyidongTDD: " + listyidongTDD.toString());
                            Log.d("TAGaweq", "onMessageEventlistlistyidongFDD: " + listyidongFDD.toString());
                            Log.d("TAGaweq", "onMessageEventlistylistMax: " + listMax.toString());
                            Integer max = Collections.max(listMax);
                            String band = "";
                            for (int i = 0; i < list.size(); i++) {
                                if (max.equals(list.get(i).getPriority())) {

                                    down = list.get(i).getDown();
                                    band = list.get(i).getBand();
                                }
                            }
                            String zs = "";
                            if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
                                zs = "FDD";
                            } else {
                                zs = "TDD";
                            }
                            if (tf1.equals(zs)) {//当前制式 是否 和扫频结果比较的 一致  一致直接建立小区
                                presenter.saopinjianlixiaoqu(AppContext.context, 1, tf1, down);
                                Log.d("1nzqasa", "onMessageEvent: 3");
                                ACacheUtil.putSpinner1(down);
                            } else {
                                if (!tf1.equals(zs)) {//当前制式  和扫频结果比较的 不一致  切换制式  建立小区
                                    zhishiqiehuan(1, tf1);
                                    finalDown = down;
                                    ACacheUtil.putSpinner1(finalDown);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
//                                        presenter.saopinjianlixiaoqu(context, 1, tf1, finalDown);
                                            Log.d("1nzqasa", "onMessageEvent: 4");
                                            yidong1sucess3 = false;
                                        }
                                    }, 30000);//230秒 移动FDD去扫频

                                }
                            }
                        }

                    } else {
                        if (yidong1sucess1 == true && yidong1sucess2 == true) {
                            zhishiqiehuan(1, "FDD");       //移动模式1 TDD 完成  切换 制式
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    yidong1sucess2 = false;
                                    Log.d("2nzqasa2", "onMessageEvent: 1");
                                }
                            }, 30000);//
                        }
                        if (yidong1sucess1 == false && yidong1sucess2 == true) {
//                        yidong1sucess2 = false;
                            Log.d("2nzqasa2", "onMessageEvent: 2");
                            List<SpBean> list = new ArrayList<>();
                            for (int i = 0; i < listyidongTDD.size(); i++) {
                                list.add(listyidongTDD.get(i));

                            }
                            for (int i = 0; i < listyidongFDD.size(); i++) {
                                list.add(listyidongFDD.get(i));
                            }
                            List<Integer> listMax = new ArrayList<>();
                            String down = "";
                            for (int i = 0; i < list.size(); i++) {
                                listMax.add(list.get(i).getPriority());
                            }
                            Log.d("TAGaweq", "onMessageEventlistyidongTDD: " + listyidongTDD.toString());
                            Log.d("TAGaweq", "onMessageEventlistlistyidongFDD: " + listyidongFDD.toString());
                            Log.d("TAGaweq", "onMessageEventlistylistMax: " + listMax.toString());
                            Integer max = Collections.max(listMax);
                            String band = "";
                            for (int i = 0; i < list.size(); i++) {
                                if (max.equals(list.get(i).getPriority())) {

                                    down = list.get(i).getDown();
                                    band = list.get(i).getBand();
                                }
                            }
                            String zs = "";
                            if (band.equals("1") || band.equals("3") || band.equals("5") || band.equals("8")) {
                                zs = "FDD";
                            } else {
                                zs = "TDD";
                            }
                            if (tf1.equals(zs)) {//当前制式 是否 和扫频结果比较的 一致  一致直接建立小区
                                presenter.saopinjianlixiaoqu(AppContext.context, 1, tf1, down);
                                Log.d("2nzqasa2", "onMessageEvent: 3");
                                ACacheUtil.putSpinner1(down);
                            } else {
                                if (!tf1.equals(zs)) {//当前制式  和扫频结果比较的 不一致  切换制式  建立小区
                                    zhishiqiehuan(1, tf1);
                                    finalDown = down;
                                    ACacheUtil.putSpinner1(down);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
//                                        presenter.saopinjianlixiaoqu(context, 1, tf1, finalDown);
                                            Log.d("2nzqasa2", "onMessageEvent: 4");
                                            yidong1sucess3 = false;
                                        }
                                    }, 30000);//230秒 移动FDD去扫频

                                }
                            }
                        }
                    }

                }

//
                break;
            case 1001200:
                Log.d("TAGevent.getData()", "onMessageEvent: " + event.getData());
                if (event.getData().equals("扫频同步进行中")) {
                    tvtypetext.setVisibility(View.VISIBLE);
                    bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                }
                if (event.getData().equals("小区去激活")) {
                    tvtypetext.setVisibility(View.VISIBLE);
                }

                break;
            case 100120://设备状态  就绪/   定位
                tv1type.setText("4G设备:" + event.getData());
                type1 = event.getData() + "";
                Log.d("100120", "onMessageEvent: 状态:1" + type1 + " -- 设备2状态:" + type2);
                if (type1.equals("定位中")) {
                    pb.setProgress(8);
                    tvtypetext.setVisibility(View.GONE);
                    BtfLAG = false;

                    bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                }
                if (type1.equals("小区去激活")) {
                    tvtypetext.setVisibility(View.VISIBLE);
                }
                if (type1.equals("扫频同步进行中")) {
                    tvtypetext.setVisibility(View.VISIBLE);
                    bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                }
                if (ACacheUtil.getZD().equals("0") && DbUtils.GetCellDatacheck(mContext, 1)) {
                    if (type1.equals("就绪")) {
                        pb.setProgress(0);
                        tvtypetext.setVisibility(View.GONE);
//                        if (!TextUtils.isEmpty(tf1)){
                        if (TIMERRESTARTFLAG1 == false) {
                            Log.d("TIMERRESTARTFLAG1100120", "onMessageEvent: " + tf1 + "---" + ACacheUtil.getSpinner1() + "==" + type1);
                            presenter.startsd(false, 1, tf1, mContext, ACacheUtil.getSpinner1(), type1);
                            TIMERRESTARTFLAG1 = true;
                        }
//                        }

                    }
                } else {//自动
                    if (type1.equals("就绪")) {
                        pb.setProgress(0);
                        tvtypetext.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(tf1)) {
                            if (yidongsaopinFlag == 1) {
                                if (yidong1sucess1 == false) {
                                    yidong1sucess1 = true;
                                    yidong1sucess2 = false;
                                    DBManagersaopin dbManagersaopin = null;
                                    try {
                                        dbManagersaopin = new DBManagersaopin(mContext);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    try {

                                        List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("移动FDD", "FDD");
                                        if (saopinBeanList2.size() <= 10) {
                                            saopinSend1(saopinBeanList2, tf1, 2, AppContext.context);

                                        } else {
                                            ToastUtils.showToast("移动频点大于10个或等于0个");
                                        }

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (yidong1sucess3 == false) {
                                    yidong1sucess3 = true;
                                    presenter.saopinjianlixiaoqu(AppContext.context, 1, tf1, finalDown);
                                }


                            } else {   //yidongsaopinFlag =2 的时候
                                if (yidong1sucess2 == false) {
                                    yidong1sucess1 = false;
                                    yidong1sucess2 = true;
                                    DBManagersaopin dbManagersaopin = null;
                                    try {
                                        dbManagersaopin = new DBManagersaopin(mContext);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    try {

                                        List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("移动TDD", "TDD");
                                        if (saopinBeanList2.size() <= 10) {
                                            saopinSend1(saopinBeanList2, tf1, 1, AppContext.context);

                                        } else {
                                            ToastUtils.showToast("移动频点大于10个或等于0个");
                                        }

                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (yidong1sucess3 == false) {
                                    yidong1sucess3 = true;
                                    presenter.saopinjianlixiaoqu(AppContext.context, 1, tf1, finalDown);
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(tf1)) {
                            if (litongFlag == false) {
                                litongFlag = true;
                                DBManagersaopin dbManagersaopin = null;
                                try {
                                    dbManagersaopin = new DBManagersaopin(mContext);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {

                                    List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("联通", "FDD");
                                    if (saopinBeanList2.size() <= 10) {
                                        saopinSend1(saopinBeanList2, tf1, 3, AppContext.context);
                                    } else {
                                        ToastUtils.showToast("联通频点大于10个或等于0个");
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (dxFlag == false) {
                                dxFlag = true;
                                DBManagersaopin dbManagersaopin = null;
                                try {
                                    dbManagersaopin = new DBManagersaopin(mContext);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try {

                                    List<SaopinBean> saopinBeanList2 = dbManagersaopin.getStudentquery("电信", "FDD");
                                    if (saopinBeanList2.size() <= 10) {
                                        saopinSend1(saopinBeanList2, tf1, 4, AppContext.context);

                                    } else {
                                        ToastUtils.showToast("联通频点大于10个或等于0个");
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }


                    }

                }

                if (type1.equals("就绪")) {
                    pb.setProgress(0);
                    tvtypetext.setVisibility(View.GONE);
                    if (BtfLAG) {
                        bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                    } else {
                        bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.blue));
                    }
//

                }
                Log.d("ProgressBarType", "onMessageEvent: " + FragmentUtils.ProgressBarType(mContext, ACacheUtil.getSpinner1(), ACacheUtil.getSpinner2()));
//                if (1 == FragmentUtils.ProgressBarType(mContext, ACacheUtil.getSpinner1(), ACacheUtil.getSpinner2())) {//只有一个设备选中1
                if (type1.equals("连接中")) {
                    sux1 = 0;
                    pb.setProgress(sux1);
                    tvtypetext.setVisibility(View.VISIBLE);
                }

                if (type1.equals("空口同步中")) {
                    sux1 = 2;
                    pb.setProgress(sux1);
                    tvtypetext.setVisibility(View.VISIBLE);
                    bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                }
                if (type1.equals("小区激活中")) {
                    sux1 = 6;
                    pb.setProgress(sux1);
                    tvtypetext.setVisibility(View.VISIBLE);
                    bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                }
                if (type1.equals("定位中")) {
                    pb.setProgress(8);
                    tvtypetext.setVisibility(View.GONE);
                    bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
//                        BtfLAG=true;
                }

                break;
            case 100136:
                Log.d(TAG, "onMessageEvent: 设置定位模式成功" + ACacheUtil.getSpinner1() + "自动？" + ACacheUtil.getZD() + "状态" + type1);
                if (ACacheUtil.getZD().equals("0")) {
                    presenter.buildSD(ACacheUtil.getSpinner1(), 1, type1, mContext);
                } else {
                    presenter.buildSD(ACacheUtil.getSpinner1(), 1, type1, mContext);
                }
                break;
            case 100137:
                Log.d(TAG, "onMessageEvent: 设置定位模式失败");
                break;
            case 100138:
                Log.d(TAG, "onMessageEvent: " + "4G设备重启设置成功");
                BtfLAG = true;

                bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("重启完成执行", "run:100138");
//                                saopinSend1(saopinBeanList, tf1, SAOPIN);
                        TIMERRESTARTFLAG1 = false;
//                        tf1 = "";
                        tv1type.setText("4G设备:重启中");

                        tvtypetext.setVisibility(View.VISIBLE);
                        if (tf1.equals("TDD")) {
                            tf1 = "FDD";
                        } else if (tf1.equals("FDD")) {
                            tf1 = "TDD";
                        }
                    }
                }, 12000);
                break;
            case 100139:
                Log.d(TAG, "onMessageEvent: " + "4G设备重启设置失败");
                break;
            case 100140:
                if (event.getData().equals("1")) {//切换制式成功
                    MainUtils.Restart();
                }
                break;
            case 100141:

                if (event.getData().equals("0")) {//切换制式失败

                }
                break;
            case 100147:
                Log.d("100147", "能量值onMessageEvent: " + event.getData());
                tv_sb1_jl_dw.setText(event.getData() + "");
                presenter.setIMSInengliangzhi(event.getData().toString(), mContext, 1, tf1, dfBaoshu, tv_sb1_jl_dw, laba1Flag, textToSpeech);
                break;
            case 100148:
                Log.d("100148", "定位IMSIonMessageEvent: " + event.getData());
                tv_imsi1_dw.setText(event.getData() + "");
                break;
            case 100152:
                //  定时 扫频
                Log.d(TAG, "100152onMessageEvent: " + event.getData() + "----" + ACacheUtil.getSpinner1());
                String data = (String) event.getData();
                if (yidongFlag) {//当前是移动扫频

                    if (type1.equals("空口同步中") || type1.equals("小区激活中") || type1.equals("小区激活成功") || type1.equals("定位中") || type1.equals("空口同步成功")) {
                        break;
                    }
                    if (!TextUtils.isEmpty(data)) {
                        if (tf1.equals("TDD")) {
                            if (listyidongTDD.size() == 0) {
                                listyidongTDD = MyService.SPBEANLIST1;
                                Log.d("listyidongTDD", "onMessageEvent: listyidongTDD" + listyidongTDD);
                            }

                        }
                        if (tf1.equals("FDD")) {
                            if (listyidongFDD.size() == 0) {
                                listyidongFDD = MyService.SPBEANLIST1;
                                Log.d("listyidongFDD", "onMessageEvent: listyidongTDD" + listyidongFDD);
                            }
                        }

                    } else {
                        ToastUtils.showToast("没有可用频点");
                    }
                } else {//联通 或者电信
                    if (!TextUtils.isEmpty(data)) {
                        Log.d(TAG, "100152onMessageEvent:1 " + event.getData() + "----" + ACacheUtil.getSpinner1());
                        if (!ACacheUtil.getZD().equals("0")) {
                            Log.d(TAG, "100152onMessageEvent:2 " + event.getData() + "----" + ACacheUtil.getSpinner1());
//                        if (type1.equals("就绪")){
                            Log.d(TAG, "100152onMessageEvent:3 " + event.getData() + "----" + ACacheUtil.getSpinner1());
                            if (type1.equals("空口同步中") || type1.equals("小区激活中") || type1.equals("小区激活成功") || type1.equals("定位中") || type1.equals("空口同步成功")) {

                            } else {
                                linshiDown = data;
                                ACacheUtil.putSpinner1(data);
                                presenter.saopinjianlixiaoqu(AppContext.context, 1, tf1, data);
                            }


                            Log.d(TAG, "100152onMessageEvent:4 " + event.getData() + "----" + ACacheUtil.getSpinner1());

                        } else {
                            Log.d(TAG, "100152onMessageEvent:5 " + event.getData() + "----" + ACacheUtil.getSpinner1());
                        }

                    } else {
                        ToastUtils.showToast("没有可用频点");
                    }
                }

                break;

            //设备2
            case 200110:
                tf2 = event.getData() + "";//当前5G设备制式
                break;
            case 200120://设备状态  就绪/   定位
                tv2type.setText("设备2:" + event.getData());
                type2 = event.getData() + "";
                Log.d("TAG200120", "onMessageEvent:状态 " + type2 + TIMERRESTARTFLAG2);
                if (ACacheUtil.getZD().equals("0") && DbUtils.GetCellDatacheck(mContext, 2)) {
                    if (type2.equals("就绪")) {
                        if (TIMERRESTARTFLAG2 == false) {
                            presenter.startsd(false, 2, tf2, mContext, ACacheUtil.getSpinner2(), type2);
                            TIMERRESTARTFLAG2 = true;
                        }
                    }
                }
                if (2 == FragmentUtils.ProgressBarType(mContext, ACacheUtil.getSpinner1(), ACacheUtil.getSpinner2())) {//只有一个设备选中1

                }

                break;
            case 200136:
                Log.d(TAG, "onMessageEvent: 设置定位模式成功");
                presenter.buildSD(ACacheUtil.getSpinner2(), 2, type2, mContext);
                break;
            case 200137:
                Log.d(TAG, "onMessageEvent: 设置定位模式失败");
                break;
            case 200138:
                Log.d(TAG, "onMessageEvent: " + "5G设备重启设置成功");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("重启完成执行", "run:788888888888");
//                                saopinSend1(saopinBeanList, tf1, SAOPIN);
                        TIMERRESTARTFLAG2 = false;
                    }
                }, 200000);
                break;
            case 200139:
                Log.d(TAG, "onMessageEvent: " + "5G设备重启设置失败");
            case 200140:
                if (event.getData().equals("1")) {//切换制式成功
                    MainUtils.Restart2();
                }
                break;
            case 200141:

//                if (event.getData().equals("0")) {//切换制式失败
//
//                }
                break;
            case 200147:
                Log.d("200147", "能量值onMessageEvent: " + event.getData());
                tv_sb2_jl_dw.setText("1000");
                presenter.setIMSInengliangzhi(event.getData().toString(), mContext, 2, "TDD", dfBaoshu, tv_sb2_jl_dw, laba2Flag, textToSpeech);
                tv2type.setText("5G设备:定位中");
                tvtypetext2.setVisibility(View.GONE);
//                DINGWFLAG5G = true;
                break;
            case 200148:
                Log.d("200148", "定位IMSIonMessageEvent: " + event.getData());
                tv_imsi2_dw.setText(event.getData() + "");
                break;
            case 200152:
                Log.d(TAG, "200152onMessageEvent: " + event.getData());
                break;
        }
    }

    String finalDown = "";
    String linshiDown = "";
    List<States> listStates = new ArrayList<>();// 设备黑名单中标情况
    private Timer timer = null;//五秒一次imsi列表更新

    /**
     * 设备定位imsi的数据
     *
     * @param map
     */
    private void sbImsiType(Map<String, String> map) {
        String imsi = map.get("imsi");
        String sb = map.get("sb");
        String zb = map.get("zb");
        String datetime = map.get("datetime");
        String time = map.get("time");
        Log.d(TAG, "handl黑名单: " + imsi + "---" + sb + "zb--" + "datatime--" + datetime + "time--" + time);
        States states = new States();
        states.setImsi(imsi);
        states.setSb(sb);
        states.setZb(zb + "");
        states.setDatatime(datetime);
        states.setTime(time);
        listStates.add(states);

        Log.d("statessbImsiType", "statessbImsiType: " + states.toString());
        //创建一个定时器
        if (timer == null) {
            timer = new Timer();
            //schedule方法是执行时间定时任务的方法
            timer.schedule(new TimerTask() {

                //run方法就是具体需要定时执行的任务
                @Override
                public void run() {

                    Message message = new Message();
                    mHandler.sendMessage(message);
                    message.what = 300001;
                    Log.d(TAG, "handlerrun: " + 1);
                    Log.d(TAG, "handlerrun: " + 1);
                }
            }, 0, 11000);//IMSI
        } else {
            Log.d(TAG, "ahandlerrun: " + 1);

        }
    }

    @SuppressLint("NewApi")
    DecimalFormat dfBaoshu = new DecimalFormat("###");
    List<Integer> list1quxian = null;//4G设备曲线图数据
    List<Integer> list2quxian = null;//5G设备曲线图数据
    private TextToSpeech textToSpeech = null;//创建自带语音对象
    ;

    //初始化语音系统
    private void initTTS() {
        //实例化自带语音对象
        textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == textToSpeech.SUCCESS) {
                    // Toast.makeText(MainActivity.this,"成功输出语音",
                    // Toast.LENGTH_SHORT).show();
                    // Locale loc1=new Locale("us");
                    // Locale loc2=new Locale("china");

                    textToSpeech.setPitch(0.5f);//方法用来控制音调
                    textToSpeech.setSpeechRate(0.01f);//用来控制语速

                    //判断是否支持下面两种语言
                    int result1 = textToSpeech.setLanguage(Locale.US);
                    int result2 = textToSpeech.setLanguage(Locale.
                            SIMPLIFIED_CHINESE);
                    boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                    boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

//                    Log.i("zhh_tts", "US支持否？--》" + a +
//                            "\nzh-CN支持否》--》" + b);
                } else {
//                    MyToast.showToast("数据丢失或不支持");
//                    Toast.makeText(MainActivity.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 设置曲线图数据
     *
     * @param list1quxian
     * @param list2quxian
     */
    private void setqxData(List<Integer> list1quxian, List<Integer> list2quxian) {
        lineChartViewdw.setShowTable(true);
        List<LineChartView.Data> datas = new ArrayList<>();
        for (int value : list1quxian) {
            LineChartView.Data data = new LineChartView.Data(value);
            datas.add(data);
        }

        List<LineChartView.Data> datas2 = new ArrayList<>();
        for (int value2 : list2quxian) {
            LineChartView.Data data2 = new LineChartView.Data(value2);
            datas2.add(data2);
        }
        lineChartViewdw.setData(datas, datas2);
        lineChartViewdw.setBezierLine(true);
        lineChartViewdw.setRulerYSpace(200);
        lineChartViewdw.setStepSpace(17);
//        scrollViewdw.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
//        scrollViewdw.post(new Runnable() {
//            @Override
//            public void run() {
//                //滚动到底部
//                scrollViewdw.fullScroll(ScrollView.FOCUS_DOWN);
//                //滚动到顶部
//                //scrollview.fullScroll(ScrollView.FOCUS_UP);
//            }
//        });
    }

    /**
     * 设置曲线图数据
     *
     * @param list1quxian
     * @param list2quxian
     */
    private void setqxData2(List<Integer> list1quxian, List<Integer> list2quxian) {
        lineChartViewdw2.setShowTable(true);
        List<LineChartView2.Data> datas = new ArrayList<>();
        for (int value : list1quxian) {
            LineChartView2.Data data = new LineChartView2.Data(value);
            datas.add(data);
        }

        List<LineChartView2.Data> datas2 = new ArrayList<>();
        for (int value2 : list2quxian) {
            LineChartView2.Data data2 = new LineChartView2.Data(value2);
            datas2.add(data2);
        }
        lineChartViewdw2.setData(datas, datas2);
        lineChartViewdw2.setBezierLine(true);
        lineChartViewdw2.setRulerYSpace(200);
        lineChartViewdw2.setStepSpace(17);
//        scrollViewdw.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
//        scrollViewdw.post(new Runnable() {
//            @Override
//            public void run() {
//                //滚动到底部
//                scrollViewdw.fullScroll(ScrollView.FOCUS_DOWN);
//                //滚动到顶部
//                //scrollview.fullScroll(ScrollView.FOCUS_UP);
//            }
//        });
    }

    NewViewDW.MainPresenter presenter;

    @Override
    public void setPresenter(NewViewDW.MainPresenter presenter) {
        this.presenter = presenter;
    }

    //要重启的设备     4G设备 5G设备
    @Override
    public void zhishiqiehuan(int device, String tf) {
        if (device == 1) {
            String titles = "";
            if (tf1.equals("TDD")) {
                titles = "FDD";
                MainUtils.start1SNF(IP1, Constant.SNFFDD);
            }
            if (tf1.equals("FDD")) {
                titles = "TDD";
                MainUtils.start1SNF(IP1, Constant.SNFTDD);
            }
            MainUtils.Qiehuanzs(titles, IP1);
        }
        if (device == 2) {
            String titles = "";
            if (tf2.equals("TDD")) {
                titles = "FDD";
                MainUtils.start1SNF(IP2, Constant.SNFFDD);
            }
            if (tf2.equals("FDD")) {
                titles = "TDD";
                MainUtils.start1SNF(IP2, Constant.SNFTDD);
            }
            MainUtils.Qiehuanzs(titles, IP2);
        }
    }

    //设备曲线图数据
    @Override
    public void quxian(String data, int device) {
        if (device == 1) {
            if (list1quxian.size() > 0) {
                list1quxian.remove(0);
                list1quxian.add(Integer.parseInt(data));
                double total = 0;
                for (int i = 0; i < list1quxian.size(); i++) {
                    total += list1quxian.get(i);
                }
                double a = total / list1quxian.size();

                setqxData(list1quxian, list2quxian);
            }
        }
        if (device == 2) {
            if (list2quxian.size() > 0) {
                list2quxian.remove(0);
                list2quxian.add(Integer.parseInt(data));
                double total = 0;
                for (int i = 0; i < list2quxian.size(); i++) {
                    total += list2quxian.get(i);
                }
                double a = total / list2quxian.size();

                setqxData2(list1quxian, list2quxian);
            }
        }
    }

    @Override
    public void MesageV(int i) {

    }

    @Override
    public void gkqiehuan(String tf, int device) {

    }

    @Override
    public void stopdwup(int i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                Bean.BodyDTO dto = new Bean.BodyDTO();
                dto.setSwitchs("close");
                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                headerDTO.setSession_id(MyService.session_id);
                headerDTO.setMsg_id("SET_CELL_SWITCH");
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

    //喇叭回调 喇叭开关
    @Override
    public void labaup(int device, boolean bs) {
        if (device == 1) {
            laba1Flag = bs;
        }
        if (device == 2) {
            laba2Flag = bs;
        }
    }

    private boolean BtfLAG = false;//  判断是否进行中  .  false未进行

    @Override
    public void bt_startFalg(boolean b) {
        if (b) {
            BtfLAG = true;
//            bts_start1_dw.setEnabled(false);
            bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.hui));
            QueryBlack();//启动5G
        } else {
            BtfLAG = false;
//            bts_start1_dw.setEnabled(true);
            bts_start1_dw.setBackground(AppContext.context.getResources().getDrawable(R.color.blue));
            QueryBlack();//启动5G
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        ImslList();//刷新IMSI列表

        Log.d(TAG, "onResume: " + "4545644564");
    }
    //

}
