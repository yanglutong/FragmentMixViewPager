package com.lutong.fragment;

import static com.lutong.Constants.isDx;
import static com.lutong.Constants.isJzBj;
import static com.lutong.Constants.isLt;
import static com.lutong.Constants.isYd;
import static com.lutong.Constants.jzMessage;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.App.MessageEvent;
import com.lutong.Constants;
import com.lutong.R;
import com.lutong.Utils.MyUtils;
import com.lutong.adapter.RecyclerAdapter;
import com.lutong.ormlite.DBManagerBj;
import com.lutong.ormlite.JzbJBean;
import com.lutong.tcp_connect.JsonLteBean;
import com.lutong.tcp_connect.JsonNrBean;
import com.lutong.tcp_connect.RecJsonBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 工模界面-子Fragment 4G 5G
 */
public class GijChildFragment5 extends Fragment {
    public GijChildFragment5() {
    }

    private ArrayList<RecJsonBean> list;
    private ArrayList<RecJsonBean> listAdd;
    private RecyclerView recycler;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<JzbJBean> listManager = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11115) {
                JsonNrBean jsonNrBean = (JsonNrBean) msg.obj;
                listAdd.clear();
                setNr(jsonNrBean);//添加数据源
                //移动联通电信运营商区分
                if (list.size() > 0) {
                    for (RecJsonBean recJsonBean : list) {
                        if (isYd) {
                            if (recJsonBean.getTv_plmn().equals("46002") || recJsonBean.getTv_plmn().equals("46000")) {
                                listAdd.add(recJsonBean);
                            }
                        }
                        if (isLt) {
                            if (recJsonBean.getTv_plmn().equals("46001")) {
                                listAdd.add(recJsonBean);
                            }
                        }
                        if (isDx) {
                            if (recJsonBean.getTv_plmn().equals("46011")) {
                                listAdd.add(recJsonBean);
                            }
                        }
                    }
                }

                //基站老化
                Iterator<RecJsonBean> iterator = list.iterator();
                while (iterator.hasNext()) {
                    RecJsonBean recJsonBean = iterator.next();
                    boolean remove = MyUtils.isRemove(MyUtils.timeM(MyUtils.getTimeShort()), MyUtils.timeM(recJsonBean.getTv_cj_time()), jzMessage);
                    if (remove) {
                        iterator.remove();
                    }
                }
                //按照优先级排序
                Collections.sort(listAdd, new Comparator<RecJsonBean>() {
                    @Override
                    public int compare(RecJsonBean o1, RecJsonBean o2) {
                        return o2.getTv_yxj() - o1.getTv_yxj();
                    }
                });
                //基站报警
                if (listManager.size() > 1) {
                    //基站报警
                    ArrayList<RecJsonBean> beans = new ArrayList<>();
                    for (int i = 1; i < listManager.size(); i++) {
                        JzbJBean jzbJBean = listManager.get(i);
                        for (int j = 0; j < listAdd.size(); j++) {
                            RecJsonBean jsonBean = listAdd.get(j);
                            if (jzbJBean.getCid().equals(jsonBean.getTv_cid() + "") && jzbJBean.getTac().equals(jsonBean.getTv_tac() + "")) {
                                beans.add(jsonBean);
                            }
                        }
                    }
                    for (int i = 0; i < beans.size(); i++) {
                        RecJsonBean jzbJBean = beans.get(i);
                        jzbJBean.setJzBjState(true);
//                        list.set(jzbJBean.getIndex() - 1, jzbJBean);
                    }

                    //是选中状态，并且显示在界面上的才会响铃
                    if (isJzBj) {//报警声音
                        String tac = null;
                        String cid = null;
                        for (int i = 1; i < listManager.size(); i++) {
                            JzbJBean jzbJBean = listManager.get(i);
                            if (jzbJBean.getTac().equals(jsonNrBean.getPLMNLIST().get(0).getTAC() + "") && jzbJBean.getCid().equals(jsonNrBean.getPLMNLIST().get(0).getCID() + "")) {
                                tac = jsonNrBean.getPLMNLIST().get(0).getTAC() + "";
                                cid = jsonNrBean.getPLMNLIST().get(0).getCID() + "";
                            }
                        }
                        if (tac != null && cid != null) {
                            //有报警的基站并且为显示状态才报警
                            boolean isBaoJ = false;
                            for (RecJsonBean jsonBean : listAdd) {
                                if (jsonBean.getTv_tac().equals(tac) && jsonBean.getTv_cid().equals(cid)) {
                                    isBaoJ = true;
                                }
                            }

                            if (isBaoJ) {
                                startVoice();
                            } else {
                                length = 5;
                                stopVoice();
                            }
                        }
                    } else {
                        length = 5;
                        stopVoice();
                    }
                } else {
                    for (int i = 0; i < listAdd.size(); i++) {
                        RecJsonBean jsonBean = listAdd.get(i);
                        jsonBean.setJzBjState(false);
                    }
                }
                //显示条目下标
                for (int i = 0; i < listAdd.size(); i++) {
                    RecJsonBean jsonBean = listAdd.get(i);
                    jsonBean.setIndex(i + 1);
                }
                recyclerAdapter.notifyDataSetChanged();
            }
        }
    };
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gijs, container, false);
        findView(view);
        initData(view);
        return view;
    }

    private void initData(View view) {
        //注册Event
        EventBus.getDefault().register(this);

        list = new ArrayList<>();
        listAdd = new ArrayList<>();

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(getContext(), listAdd, "NR");
        recycler.setAdapter(recyclerAdapter);


        //获取数据库是否有基站报警的条目
        try {
            DBManagerBj managerBj = new DBManagerBj(getContext());
//            Log.e("ylt", "initGmConfigState4: " + managerBj.getdemoBeanList());
            listManager = (ArrayList<JzbJBean>) managerBj.getdemoBeanList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findView(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(MessageEvent event) {
        if (event.getCode() == 11115) {//接收的是tcpserver数据
            Message message = new Message();
            message.what = 11115;
            message.obj = event.getData();
            handler.sendMessage(message);
            Log.e("ylt", "onEventGijFragment5: " + event.getData());
        }
        if (event.getCode() == 13145) {//获取activity的数据库listManager
            listManager = (ArrayList<JzbJBean>) event.getData();
            Log.e("ylt", "GijChildFragment4: " + listManager.size() + "===" + listManager.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        stopVoice();//只要有切换界面的操作 
        if (isVisibleToUser) {//显示的时候
            //切换成4G
            EventBus.getDefault().postSticky(new MessageEvent(2022, Constants.sendNr));
        }
    }

    private void setNr(JsonNrBean jsonNrBean) {
        List<JsonNrBean.PLMNLISTBean> plmnlist = jsonNrBean.getPLMNLIST();
        int earfcn = jsonNrBean.getEARFCN();
        String time = jsonNrBean.getTIME();
        double rsrp = jsonNrBean.getRSRP();
        String mode = jsonNrBean.getMODE();
        int priority = jsonNrBean.getPRIORITY();
        String netmode = jsonNrBean.getNETMODE();
        int pci = jsonNrBean.getPCI();
        int band = jsonNrBean.getBAND();
        int carriercenterarfcn = jsonNrBean.getCARRIERCENTERARFCN();
        if (plmnlist.size() == 1) {//5G数据plmn条目有一条的话
            ArrayList<RecJsonBean> beans = new ArrayList<>();
            //1.添加到集合
            beans.add(new RecJsonBean(earfcn + "", time, plmnlist.get(0).getPLMN(), rsrp + "", plmnlist.get(0).getTAC() + "", mode, priority, netmode, pci + "", plmnlist.get(0).getCID() + "", band + "", carriercenterarfcn + ""));
            //2.看5G集合是否有数据
            if (list.size() > 0) {
                ArrayList<RecJsonBean> jsonBeans = new ArrayList<>();
                int index = 0;
                for (int i = 0; i < list.size(); i++) {
                    RecJsonBean jsonBean = list.get(i);
                    if (jsonBean.getTv_tac().equals(plmnlist.get(0).getTAC() + "") && jsonBean.getTv_pci().equals(pci + "") && jsonBean.getTv_arfcn().equals(earfcn + "") && jsonBean.getTv_plmn().equals(plmnlist.get(0).getPLMN())) {
                        jsonBeans.add(jsonBean);
                        index = i;

                        //集合里有此条目时看过来的数据有无优先级 这次优先级为0就保留上次的
                        if (beans.get(0).getTv_yxj() == 0) {
                            if (list.get(i).getTv_yxj() != 0) {
                                beans.get(0).setTv_yxj(list.get(i).getTv_yxj());
                            }
                        }
                    }
                }
                if (jsonBeans.size() > 0) {//此集合里有一致的数据
                    //将新的条目数据更新到原有条目
                    if (plmnlist.get(0).getCID() != 0) {
                        list.set(index, beans.get(0));
                    }
                } else {
                    //没有的话就直接添加
                    list.add(beans.get(0));
                }
            } else {
                //没有的话就直接添加
                list.add(beans.get(0));
            }
        } else if (plmnlist.size() > 1) {
            ArrayList<RecJsonBean> beans = new ArrayList<>();
            //1.先处理这条数据 将一条分为两条数据
            beans.add(new RecJsonBean(earfcn + "", time, plmnlist.get(0).getPLMN(), rsrp + "", plmnlist.get(0).getTAC() + "", mode, priority, netmode, pci + "", plmnlist.get(0).getCID() + "", band + "", carriercenterarfcn + ""));
            //1.先处理这条数据 将一条分为两条数据
            beans.add(new RecJsonBean(earfcn + "", time, plmnlist.get(1).getPLMN(), rsrp + "", plmnlist.get(1).getTAC() + "", mode, priority, netmode, pci + "", plmnlist.get(1).getCID() + "", band + "", carriercenterarfcn + ""));
            //2.看5G集合是否有数据
            if (list.size() > 0) {
                ArrayList<RecJsonBean> jsonBeans = new ArrayList<>();
                int index = 0;
                int index2 = 0;
                for (int i = 0; i < list.size(); i++) {
                    RecJsonBean jsonBean = list.get(i);
                    if (jsonBean.getTv_tac().equals(beans.get(0).getTv_tac() + "") && jsonBean.getTv_pci().equals(beans.get(0).getTv_pci() + "") && jsonBean.getTv_arfcn().equals(beans.get(0).getTv_arfcn() + "") && jsonBean.getTv_plmn().equals(beans.get(0).getTv_plmn() + "")) {
                        jsonBeans.add(jsonBean);
                        index = i;


                        //集合里有此条目时看过来的数据有无优先级 这次优先级为0就保留上次的
                        if (beans.get(0).getTv_yxj() == 0) {
                            if (list.get(i).getTv_yxj() != 0) {
                                beans.get(0).setTv_yxj(list.get(i).getTv_yxj());
                            }
                        }
                    }
                }

                for (int i = 0; i < list.size(); i++) {
                    RecJsonBean jsonBean = list.get(i);
                    if (jsonBean.getTv_tac().equals(beans.get(1).getTv_tac() + "") && jsonBean.getTv_pci().equals(beans.get(1).getTv_pci() + "") && jsonBean.getTv_arfcn().equals(beans.get(1).getTv_arfcn() + "") && jsonBean.getTv_plmn().equals(beans.get(1).getTv_plmn())) {
                        jsonBeans.add(jsonBean);
                        index2 = i;

                        //集合里有此条目时看过来的数据有无优先级 这次优先级为0就保留上次的
                        if (beans.get(1).getTv_yxj() == 0) {
                            if (list.get(i).getTv_yxj() != 0) {
                                beans.get(1).setTv_yxj(list.get(i).getTv_yxj());
                            }
                        }
                    }
                }

                if (jsonBeans.size() > 1) {//此集合里有一致的数据
                    //将新的条目数据更新到原有条目
                    if (plmnlist.get(0).getCID() != 0) {
                        list.set(index, beans.get(0));
                        list.set(index2, beans.get(1));
                    }
                } else {//此集合里有不一致的数据
                    list.add(beans.get(0));
                    list.add(beans.get(1));
                }
            } else {
                //没有的话就直接添加
                list.add(beans.get(0));
                list.add(beans.get(1));
            }
        }
    }

    //停止播放音频
    public void stopVoice() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release(); //切记一定要release
            mediaPlayer = null;
        }
    }

    private int length = 5;
    private MediaPlayer mediaPlayer;

    //循环播放音频文件
    public void startVoice() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return;
        }
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.jk);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> {
            if (mediaPlayer == null) {
                return;
            }
            if (length != 0) {
                mediaPlayer.start();
                length--;
            } else {
                length = 5;
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release(); //切记一定要release
                    mediaPlayer = null;
                }
            }
//                mediaPlayer.setLooping(false);
        });
    }
}