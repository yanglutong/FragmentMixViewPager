package com.lutong.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
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
import com.lutong.ormlite.JzbJBean;
import com.lutong.tcp_connect.JsonLteBean;
import com.lutong.tcp_connect.JsonNrBean;
import com.lutong.tcp_connect.RecJsonBean;
import com.lutong.ui.MainActivity2;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static com.lutong.Constants.isDx;
import static com.lutong.Constants.isJzBj;
import static com.lutong.Constants.isLt;
import static com.lutong.Constants.isYd;
import static com.lutong.Constants.jzMessage;


/**
 * 工模界面-子Fragment 4G 5G
 */
public class GijChildFragment4 extends Fragment {
    private ArrayList<RecJsonBean> list;
    private ArrayList<RecJsonBean> listAdd;
    private RecyclerView recycler;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<JzbJBean> listManager = new ArrayList<>();

    public GijChildFragment4() {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11114) {
                JsonLteBean jsonLteBean = (JsonLteBean) msg.obj;
                listAdd.clear();
                addLte(jsonLteBean);//添加数据源
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
                            if (jzbJBean.getTac().equals(jsonLteBean.getTAC() + "") && jzbJBean.getCid().equals(jsonLteBean.getCID() + "")) {
                                tac = jsonLteBean.getTAC() + "";
                                cid = jsonLteBean.getCID() + "";
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

    private void findView(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    private void initData(View view) {
        //注册Event
        EventBus.getDefault().register(this);
        list = new ArrayList<>();
        listAdd = new ArrayList<>();

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(getContext(), listAdd, "LTE");
        recycler.setAdapter(recyclerAdapter);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onEvent(MessageEvent event) {
        if (event.getCode() == 11114) {//接收的是TCPServer数据
            Message message = new Message();
            message.what = 11114;
            message.obj = event.getData();
            handler.sendMessage(message);
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
            EventBus.getDefault().postSticky(new MessageEvent(2022, Constants.sendLte));
        }
    }

    private void addLte(JsonLteBean jsonLteBean) {
        if (jsonLteBean.getNETMODE().equals("LTE")) {
            int earfcn = jsonLteBean.getEARFCN();
            String time = jsonLteBean.getTIME();
            List<String> plmn = jsonLteBean.getPLMN();
            double rsrp = jsonLteBean.getRSRP();
            int tac = jsonLteBean.getTAC();
            String duplex = jsonLteBean.getDUPLEX();
            String netmode = jsonLteBean.getNETMODE();
            int pci = jsonLteBean.getPCI();
            int cid = jsonLteBean.getCID();
            int band = jsonLteBean.getBAND();


            ArrayList<RecJsonBean> arrayList = new ArrayList<>();
            ArrayList<RecJsonBean> beans = new ArrayList<>();
            int index = 0;
            int index2 = 0;
            if (plmn.size() > 1) {//双条基站
                if (jsonLteBean.getSIB3() != null) {
                    arrayList.add(new RecJsonBean(earfcn + "", time, plmn.get(0), rsrp + "", tac + "", duplex, jsonLteBean.getSIB3().getReselPriority(), netmode, pci + "", cid + "", band + ""));
                    arrayList.add(new RecJsonBean(earfcn + "", time, plmn.get(1), rsrp + "", tac + "", duplex, jsonLteBean.getSIB3().getReselPriority(), netmode, pci + "", cid + "", band + ""));
                } else {
                    arrayList.add(new RecJsonBean(earfcn + "", time, plmn.get(0), rsrp + "", tac + "", duplex, 0, netmode, pci + "", cid + "", band + ""));
                    arrayList.add(new RecJsonBean(earfcn + "", time, plmn.get(1), rsrp + "", tac + "", duplex, 0, netmode, pci + "", cid + "", band + ""));
                }
                //集合是否已经存在当前条目
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        RecJsonBean jsonBean = list.get(i);
                        if (jsonBean.getTv_plmn().equals(arrayList.get(0).getTv_plmn()) && jsonBean.getTv_tac().equals(arrayList.get(0).getTv_tac()) && jsonBean.getTv_cid().equals(arrayList.get(0).getTv_cid()) && jsonBean.getTv_pci().equals(arrayList.get(0).getTv_pci())) {
                            //替换老条目
                            index = i;
                            beans.add(jsonBean);
                            //集合里有此条目时看过来的数据有无优先级 这次优先级为0就保留上次的
                            if (arrayList.get(0).getTv_yxj() == 0) {
                                if (list.get(i).getTv_yxj() != 0) {
                                    arrayList.get(0).setTv_yxj(list.get(i).getTv_yxj());
                                }
                            }
                        }
                        if (jsonBean.getTv_plmn().equals(arrayList.get(1).getTv_plmn()) && jsonBean.getTv_tac().equals(arrayList.get(1).getTv_tac()) && jsonBean.getTv_cid().equals(arrayList.get(1).getTv_cid()) && jsonBean.getTv_pci().equals(arrayList.get(1).getTv_pci())) {
                            //替换老条目
                            index2 = i;
                            beans.add(jsonBean);
                            //集合里有此条目时看过来的数据有无优先级 如果没有 使用集合里的此条目优先级 有的话就直接添加
                            if (arrayList.get(1).getTv_yxj() == 0) {
                                if (list.get(i).getTv_yxj() != 0) {//有优先级的话就替换过来这条优先级
                                    arrayList.get(1).setTv_yxj(list.get(i).getTv_yxj());
                                }
                            }
                        }
                    }

                    if (beans.size() > 1) {//代表这两条都是listLte集合已经存在的数据 直接替换掉老数据
                        list.set(index, arrayList.get(0));
                        list.set(index2, arrayList.get(1));
                    } else {
                        list.add(index, arrayList.get(0));
                        list.add(index2, arrayList.get(1));
                    }
                } else {
                    list.add(arrayList.get(0));
                    list.add(arrayList.get(1));
                }
            } else if (plmn.size() == 1) {//单条基站
                if (jsonLteBean.getSIB3() != null) {
                    arrayList.add(new RecJsonBean(earfcn + "", time, plmn.get(0), rsrp + "", tac + "", duplex, jsonLteBean.getSIB3().getReselPriority(), netmode, pci + "", cid + "", band + ""));
                } else {
                    arrayList.add(new RecJsonBean(earfcn + "", time, plmn.get(0), rsrp + "", tac + "", duplex, 0, netmode, pci + "", cid + "", band + ""));
                }
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        RecJsonBean lteBean = list.get(i);
                        if (lteBean.getTv_tac().equals(tac + "") && lteBean.getTv_pci().equals(pci + "") && lteBean.getTv_cid().equals(cid + "")) {
                            beans.add(lteBean);
                            index = i;

                            //集合里有此条目时看过来的数据有无优先级 如果没有 使用集合里的此条目优先级 有的话就直接添加
                            if (arrayList.get(0).getTv_yxj() == 0) {
                                if (list.get(i).getTv_yxj() != 0) {//有优先级的话就替换过来这条优先级
                                    arrayList.get(0).setTv_yxj(list.get(i).getTv_yxj());
                                }
                            }
                        }
                    }
                    if (beans.size() > 0) {//过来的是集合里有过得数据
                        //如果是集合里有过的数据就将新数据替换老条目
                        list.set(index, arrayList.get(0));
                    } else {
                        list.add(arrayList.get(0));
                    }
                } else {
                    list.add(arrayList.get(0));
                }
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