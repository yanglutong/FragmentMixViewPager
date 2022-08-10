package com.lutong.fragment;

import static com.lutong.Constants.isDx;
import static com.lutong.Constants.isGd;
import static com.lutong.Constants.isJzBj;
import static com.lutong.Constants.isLt;
import static com.lutong.Constants.isYd;
import static com.lutong.Constants.jzMessage;
import static com.lutong.Constants.typeJzMode;
import static com.lutong.Constants.typePage;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liys.dialoglib.LAnimationsType;
import com.liys.dialoglib.LDialog;
import com.lutong.App.MessageEvent;
import com.lutong.R;
import com.lutong.Utils.MyUtils;
import com.lutong.adapter.RecyclerAdapter;
import com.lutong.ormlite.DBManagerBj;
import com.lutong.ormlite.JzbJBean;
import com.lutong.tcp.JsonLteBean;
import com.lutong.tcp.RecJsonBean;

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
public class GijChildFragment4 extends Fragment implements RecyclerAdapter.OnLongClick {
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
                        if (isGd) {
                            if (recJsonBean.getTv_plmn().equals("46015")) {
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

                Log.e("TAG", "handleMessage: " + listManager.toString());
                //基站报警
                if (typePage == 1 && typeJzMode == 4) {
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
                                    if (isPlay) {
                                        startVoice();
                                    }
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
                } else {
                    stopVoice();
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
        initData();
        return view;
    }

    private void findView(View view) {
        recycler = view.findViewById(R.id.recycler);
    }

    private void initData() {
        //注册Event
        EventBus.getDefault().register(this);
        list = new ArrayList<>();
        listAdd = new ArrayList<>();

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerAdapter = new RecyclerAdapter(getContext(), listAdd, "LTE");
        recycler.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnLongClick(this);

        //获取数据库是否有基站报警的条目
        try {
            DBManagerBj managerBj = new DBManagerBj(getContext());
            Log.e("ylt", "initGmConfigState4: " + managerBj.getdemoBeanList());
            listManager = (ArrayList<JzbJBean>) managerBj.getdemoBeanList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        if (event.getCode() == 3033) {//界面为基站查询或定位时，停止播放报警声音
            stopVoice();
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
            typeJzMode = 4;
//            EventBus.getDefault().postSticky(new MessageEvent(2022, Constants.sendLte));
        } else {
            stopVoice();
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
        if (getContext() == null) {
            return;
        }
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


    private boolean isPlay = true;

    @Override
    public void onPause() {
        super.onPause();
        Log.e("CijChild4", "onPause: ");
        stopVoice();
        isPlay = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isPlay = true;
    }

    private void showDialog(Context context, RecJsonBean recJsonBean) {
        LDialog dialog = LDialog.newInstance(context, R.layout.gm_pop);
        Button bt_adddilao = dialog.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过EventBus发送消息切换到基站查询界面
                EventBus.getDefault().postSticky(new MessageEvent(7777, recJsonBean));
                dialog.dismiss();
                //切换到基站查询界面
            }
        });
        Button bt_cancel = dialog.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog
                .setMaskValue(0.5f) //遮罩--透明度(0-1)
                //1.设置宽
                //精确宽度
                .setWidth(750) //单位:dp
                .setWidthPX(750) //单位:px
//                .setWidthRatio(0.5) //占屏幕宽比例
//                //最小宽度
//                .setMinWidth(300) //单位:dp
//                .setMinWidth(100) //单位:px
//                .setMinWidthRatio(0.5) //占屏幕宽比例
//                //最大宽度
//                .setMaxWidth(300) //单位:dp
//                .setMaxWidthPX(100) //单位:px
//                .setMaxWidthRatio(0.8) //占屏幕宽比例

                //2.设置高
                //精确高度
                .setHeight(400) //单位:dp
                .setHeightPX(400) //单位:px
//                .setHeightRatio(0.4) //占屏幕高比例
//                //最小高度
//                .setMinHeight(100) //单位:dp
//                .setMinHeightPX(100) //单位:px
//                .setMinHeightRatio(0.3) //占屏幕高比例
//                //最大高度
//                .setMaxHeight(100) //单位:dp
//                .setMaxHeightPX(100) //单位:px
//                .setMaxHeightRatio(0.3) //占屏幕高比例

                //3.设置背景
                //颜色
                .setBgColor(Color.WHITE) //一种颜色
//                .setBgColor("#FFFFFF") //一种颜色
//                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, Color.BLUE, Color.YELLOW) //颜色渐变(可传多个) 参数1：渐变的方向
//                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, "#00FEE9", "#008EB4") //颜色渐变(可传多个)
//                .setBgColorRes(R.color.white) //一种颜色(res资源)
//                .setBgColorRes(GradientDrawable.Orientation.BOTTOM_TOP, R.color.colorAccent, R.color.colorPrimary) //颜色渐变(可传多个)
                //圆角
                .setBgRadius(20) //圆角, 单位：dp
                .setBgRadius(20, 20, 20, 20) //圆角, 单位：dp
                .setBgRadiusPX(20) //圆角, 单位：px
                .setBgRadiusPX(20, 20, 20, 20) //圆角, 单位：px

                //4.设置弹框位置
//                .setGravity(Gravity.LEFT | Gravity.BOTTOM) //弹框位置
//                .setGravity(Gravity.LEFT, 0, 0) //弹框位置(偏移量)
                .setGravity(Gravity.CENTER, 0, 0)


                //5.设置动画
                //5.1 内置动画(平移，从各个方向弹出)
                // 对应的值：DEFAULT(渐变) (LEFT TOP RIGHT BOTTOM 平移)  SCALE(缩放)
                .setAnimations(LAnimationsType.SCALE)
                //5.2 自定义动画
                .setAnimationsStyle(R.style.li_dialog_default) //设置动画

                //6.设置具体布局
                //6.1 常见系统View属性
//                .setText(R.id.tv_title, "确定")
//                .setTextColor()
//                .setTextSize()
//                .setTextSizePX()
//                .setBackgroundColor()
//                .setBackgroundRes()
//                .setImageBitmap()
//                .setVisible()
//                .setGone()
                //6.2 其它属性
                .setOnTouchOutside(true) //点击空白消失
//                .setCancelBtn(R.id.tv_cancel, R.id.tv_confirm) //设置按钮，点击弹框消失(可以传多个)
//                .setOnClickListener(new LDialog.DialogOnClickListener() { //设置按钮监听
//                    @Override
//                    public void onClick(View v, LDialog customDialog) {
//                        customDialog.dismiss();
//                    }
//                }, R.id.tv_confirm, R.id.tv_cancel)  //可以传多个
                .show();
    }

    @Override
    public void setOnLongClick(int i) {
        //适配器长按监听
        RecJsonBean recJsonBean = listAdd.get(i);
        showDialog(getContext(), recJsonBean);
        Log.e("ylt", "setOnLongClick: " + recJsonBean.toString());
    }
}