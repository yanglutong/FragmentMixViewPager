package com.lutong.fragment;

import android.app.Dialog;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lutong.App.MessageEvent;
import com.lutong.OrmSqlLite.Bean.ZmBean;
import com.lutong.OrmSqlLite.DBManagerZM;
import com.lutong.R;
import com.lutong.Utils.DialogUtils;
import com.lutong.Utils.ToastUtils;
import com.lutong.base.BaseFragment;
import com.lutong.fragment.IT.ZmDataCallBack;
import com.lutong.fragment.adapter.RyZmAdapter;
import com.lutong.AppContext;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ChildFragment4 extends BaseFragment {
    RecyclerView ry_zhenma_zm;//侦码列表
    EditText et_zhenmasearch_zm;
    TextView tv_searchNum_zm;
    ImageButton ib_qc_zm, ib_dc_zm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment4, null);
        initView(view);
        return view;
    }

    TextView tv;
    RyZmAdapter ryZmAdapter;
    View inflate;
    public void initView(View view) {
        EventBus.getDefault().register(this);
//        tv = (TextView) view.findViewById(R.id.tv_childfragment_name);
//        tv.setText("Child_Fragment3");
        ry_zhenma_zm = view.findViewById(R.id.ry_zhenma_zm);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//
        ry_zhenma_zm.setLayoutManager(linearLayoutManager);
        et_zhenmasearch_zm = view.findViewById(R.id.et_zhenmasearch_zm);
        tv_searchNum_zm = view.findViewById(R.id.tv_searchNum_zm);
        setEt_searchZM();
        ib_qc_zm = view.findViewById(R.id.ib_qc_zm);//侦码清空
        ib_dc_zm = view.findViewById(R.id.ib_dc_zm);//导出
        ib_dc_zm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.DataExportzm(mContext, inflate, zmDataCallBack, 2);
                //停止侦码

//                try {
//                    dbManagerLog = new DBManagerLog(context);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                logBean = new LogBean();
//                logBean.setAssociated(LoginUtils.getId(context) + "");//关联ID
//                logBean.setEvent(LoginUtils.setBase64("侦码数据导出"));//登录事件
//                logBean.setSb(LoginUtils.setBase64("1"));
//                format = sdf.format(new Date());//登录时间
//                logBean.setDatetime(LoginUtils.setBase64(format));
//                try {
//                    dbManagerLog.insertConmmunit01Bean(logBean);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
            }
        });
        ib_qc_zm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext, R.style.menuDialogStyleDialogStyle);
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                tv_title.setText("确定要清除侦码记录吗?");
                bt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBManagerZM dbManagerZM = null;
                        try {
                            dbManagerZM = new DBManagerZM(AppContext.context);
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
//                            try {
//                                dbManagerZM = new DBManagerZM(context);
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                List<ZmBean> dataAll1 = dbManagerZM.getDataAll();
//                                List<ZmBean> dataAll12 = new ArrayList<>();
//                                for (int i = 0; i < dataAll1.size(); i++) {
//                                    if (dataAll1.get(i).getMaintype().equals("1")) {
//                                        dbManagerZM.deleteAddPararBean(dataAll1.get(i));
//                                    }
//
//                                }
//                                dialog.dismiss();
//                                dialog.cancel();
//
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
                            try {
                                dbManagerZM = new DBManagerZM(getContext());
                                dbManagerZM.deleteall();
                                //此处显示侦码的imsi列表
                                List<ZmBean> zmBeanss = dbManagerZM.getDataAll();

                                List<ZmBean> zmBeans = new ArrayList<>();
                                for (int i = 0; i < zmBeanss.size(); i++) {
//                                    if (zmBeanss.get(i).getMaintype().equals("1")) {
                                    zmBeans.add(zmBeanss.get(i));
//                                    }
                                }
                                List<Integer> listsize = new ArrayList<>();
                                for (int i = 0; i < zmBeans.size(); i++) {
                                    listsize.add(i + 1);

                                }
//                    Log.d("dbManagerZM", "handleMessage: " + i);
//                    Log.d("dbManagerZMbb", "handleMessage:size " + zmBeans.toString());
                                ryZmAdapter = new RyZmAdapter(getContext(), zmBeans, listsize);//list是imsi列表选中的数据
                                if (et_zhenmasearch_zm.getText().length() == 0) {


//                        ry_zhenma.setAdapter(ryZmAdapter);
                                    if (zmBeans.size() > 12) {
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                                        linearLayoutManager.setStackFromEnd(true);//recyview显示最底部一条
                                        ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                                        tv_searchNum_zm.setText("(" + zmBeans.size() + ")");
                                    } else {
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                                        linearLayoutManager.setStackFromEnd(false);//recyview显示最底部一条
                                        ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                                        tv_searchNum_zm.setText("(" + zmBeans.size() + ")");
                                    }
                                    ryZmAdapter = new RyZmAdapter(getContext(), zmBeans, listsize);//list是imsi列表选中的数据
                                    ry_zhenma_zm.setAdapter(ryZmAdapter);
                                }

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
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
        });
    }
    ZmDataCallBack zmDataCallBack = new ZmDataCallBack() {
        @Override
        public void sucess(String dir, String datetime) {

        }

        @Override
        public void deleall() {
            DBManagerZM dbManagerZM = null;
            try {
                dbManagerZM = new DBManagerZM(AppContext.context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //此处显示侦码的imsi列表
            List<ZmBean> zmBeanss = new ArrayList<>();
            List<ZmBean> zmBeans = new ArrayList<>();
            try {
                zmBeanss = dbManagerZM.getDataAll();
                for (int i = 0; i < zmBeanss.size(); i++) {
                    if (zmBeanss.get(i).getMaintype().equals("1")) {
                        zmBeans.add(zmBeanss.get(i));
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ToastUtils.showToast("侦码数据清除成功");
//            Log.d("dbManagerZM", "handleMessage: " + i);
            Log.d("dbManagerZM", "handleMessage:size " + zmBeans.toString());
            List<Integer> listsize = new ArrayList<>();
            for (int i = 0; i < zmBeans.size(); i++) {
                listsize.add(i + 1);
            }
            if (zmBeans == null) {
                zmBeans = new ArrayList<>();
                ryZmAdapter = new RyZmAdapter(mContext, zmBeans, listsize);//list是imsi列表选中的数据
                ry_zhenma_zm.setAdapter(ryZmAdapter);
                tv_searchNum_zm.setText("(" + "0" + ")");
            } else {
                ryZmAdapter = new RyZmAdapter(mContext, zmBeans, listsize);//list是imsi列表选中的数据
                ry_zhenma_zm.setAdapter(ryZmAdapter);
                tv_searchNum_zm.setText("(" + listsize.size() + ")");
            }
        }
    };
    private void setEt_searchZM() {//侦码界面的
        et_zhenmasearch_zm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("et_zhenmasearch", "beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("et_zhenmasearch", "onTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    Log.d("et_zhenmasearch_zm", "afterTextChanged:1 ");
                    DBManagerZM dbManagerZM = null;
                    try {
                        dbManagerZM = new DBManagerZM(AppContext.context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //此处显示侦码的imsi列表
                    List<ZmBean> zmBeans = null;
                    try {
                        zmBeans = dbManagerZM.getDataAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    List<ZmBean> zmBeanscontains = new ArrayList<>();
                    if (zmBeans != null) {
                        for (int i = 0; i < zmBeans.size(); i++) {
                            if (zmBeans.get(i).getImsi().contains(str)) {
                                zmBeanscontains.add(zmBeans.get(i));
                            }

                        }
                    } else {
                        return;
                    }
                    List<Integer> listsize = new ArrayList<>();
                    for (int i = 0; i < zmBeanscontains.size(); i++) {
                        listsize.add(i + 1);
                    }
                    if (zmBeans == null) {
                        tv_searchNum_zm.setText("(" + "0" + ")");
                    } else {
                        if (zmBeanscontains.size() > 34) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                            linearLayoutManager.setStackFromEnd(true);//recyview显示最底部一条
                            ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                            tv_searchNum_zm.setText("(" + listsize.size() + ")");
                        } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                            linearLayoutManager.setStackFromEnd(false);//recyview显示最底部一条
                            ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                            tv_searchNum_zm.setText("(" + listsize.size() + ")");
                        }
                        ryZmAdapter = new RyZmAdapter(mContext, zmBeanscontains, listsize);//list是imsi列表选中的数据
                        ry_zhenma_zm.setAdapter(ryZmAdapter);
                    }
                } else {
                    Log.d("et_zhenmasearch_zm", "afterTextChanged:2 ");
                    DBManagerZM dbManagerZM = null;
                    try {
                        dbManagerZM = new DBManagerZM(AppContext.context);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //此处显示侦码的imsi列表
                    List<ZmBean> zmBeans = new ArrayList<>();
                    List<ZmBean> zmBeanss = new ArrayList<>();
                    try {
                        zmBeanss = dbManagerZM.getDataAll();
                        for (int i = 0; i < zmBeanss.size(); i++) {
//                            if (zmBeanss.get(i).getMaintype().equals("1")) {
                            zmBeans.add(zmBeanss.get(i));
//                            }

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    List<Integer> listsize = new ArrayList<>();
                    for (int i = 0; i < zmBeans.size(); i++) {
                        listsize.add(i + 1);

                    }
//                    Log.d("dbManagerZM", "handleMessage: " + i);
                    Log.d("dbManagerZMaa", "handleMessage:size " + zmBeans.toString());
                    ryZmAdapter = new RyZmAdapter(mContext, zmBeans, listsize);//list是imsi列表选中的数据
                    if (et_zhenmasearch_zm.getText().length() == 0) {


//                        ry_zhenma.setAdapter(ryZmAdapter);
                        if (zmBeans.size() > 34) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                            linearLayoutManager.setStackFromEnd(true);//recyview显示最底部一条
                            ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                            tv_searchNum_zm.setText("(" + zmBeans.size() + ")");
                        } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                            linearLayoutManager.setStackFromEnd(false);//recyview显示最底部一条
                            ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                            tv_searchNum_zm.setText("(" + zmBeans.size() + ")");
                        }
                        //此处显示侦码的imsi列表
                        zmBeans = new ArrayList<>();
                        zmBeanss = new ArrayList<>();
                        try {
                            zmBeanss = dbManagerZM.getDataAll();
                            for (int i = 0; i < zmBeanss.size(); i++) {
//                                if (zmBeanss.get(i).getMaintype().equals("1")) {
                                zmBeans.add(zmBeanss.get(i));
//                                }

                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listsize = new ArrayList<>();
                        for (int i = 0; i < zmBeans.size(); i++) {
                            listsize.add(i + 1);

                        }
                        ryZmAdapter = new RyZmAdapter(mContext, zmBeans, listsize);//list是imsi列表选中的数据
                        ry_zhenma_zm.setAdapter(ryZmAdapter);
                    }
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        String data = (String) event.getData();
//        tv.setText(data);
        switch (event.getCode()) {
            case 30000:
                DBManagerZM dbManagerZM = null;
//                Map<String, String> map = (Map<String, String>) event.getData();
//                String imsi = map.get("imsi");
//                Log.d("MainType==1", "handleMessage: " + imsi);

                try {
                    dbManagerZM = new DBManagerZM(getContext());
//                    ZmBean zmBean = new ZmBean();
//                    zmBean.setImsi(imsi);
//                    if (map.get("sb").equals("1")) {
//                        if (!TextUtils.isEmpty(DOWNPIN1)) {
//                            zmBean.setDown(DOWNPIN1);
//                            zmBean.setMaintype("1");
//
//                            zmBean.setSb(map.get("sb"));
//                            zmBean.setTime(map.get("time"));
//                            zmBean.setDatatime(map.get("datetime"));
//                            int i = dbManagerZM.insertAddZmBean(zmBean);
//                            //以上是侦码数据列表插入
////                            ZmBeanlinshi zmBeanlinshi = new ZmBeanlinshi();
////                            DBManagerZMlinshi dbManagerZMlinshi = new DBManagerZMlinshi(context);
////                            zmBeanlinshi.setImsi(imsi);
////                            zmBeanlinshi.setDown(DOWNPIN1);
////                            zmBeanlinshi.setTime(msg.getData().getString("time"));
////                            zmBeanlinshi.setSb(msg.getData().getString("sb"));
////                            zmBeanlinshi.setCheck("0");
////                            if (!TextUtils.isEmpty(startdatazm1)) {
////                                zmBeanlinshi.setStartdatatime(startdatazm1);
////                            } else {
////                                startdatazm1 = aDateFormat.format(new Date());
////                                zmBeanlinshi.setStartdatatime(startdatazm1);
////                            }
////
////                            zmBeanlinshi.setDatatime(msg.getData().getString("datetime"));
////                            int i2 = dbManagerZMlinshi.insertAddZmBeanlinshi(zmBeanlinshi);
//                        } else {
//
//                        }
//
//                    }
//                    if (map.get("sb").equals("2")) {
//
//                        if (!TextUtils.isEmpty(DOWNPIN2)) {
//                            zmBean.setDown(DOWNPIN2);
//                            zmBean.setMaintype("1");
//                            zmBean.setSb(map.get("sb"));
//                            zmBean.setTime(map.get("time"));
//                            zmBean.setDatatime(map.get("datetime"));
//                            int i = dbManagerZM.insertAddZmBean(zmBean);
//                            //以上是侦码数据列表插入
////                            ZmBeanlinshi zmBeanlinshi = new ZmBeanlinshi();
////                            DBManagerZMlinshi dbManagerZMlinshi = new DBManagerZMlinshi(context);
////                            zmBeanlinshi.setImsi(imsi);
////                            zmBeanlinshi.setDown(DOWNPIN2);
////                            zmBeanlinshi.setCheck("0");
////                            zmBeanlinshi.setTime(msg.getData().getString("time"));
////                            zmBeanlinshi.setSb(msg.getData().getString("sb"));
////                            if (!TextUtils.isEmpty(startdatazm1)) {
////                                zmBeanlinshi.setStartdatatime(startdatazm1);
////                            } else {
////                                startdatazm2 = aDateFormat.format(new Date());
////                                zmBeanlinshi.setStartdatatime(startdatazm1);
////                            }
////
////                            zmBeanlinshi.setDatatime(msg.getData().getString("datetime"));
////                            int i2 = dbManagerZMlinshi.insertAddZmBeanlinshi(zmBeanlinshi);
//                        }
//
//                    }
                    //此处显示侦码的imsi列表
                    List<ZmBean> zmBeanss = dbManagerZM.getDataAll();
                    Log.d("TAGzmBeanss", "onMessageEvent: " + zmBeanss.toString());
                    List<ZmBean> zmBeans = new ArrayList<>();
                    for (int i = 0; i < zmBeanss.size(); i++) {
//                        if (zmBeanss.get(i).getMaintype().equals("1")) {
                        zmBeans.add(zmBeanss.get(i));
//                        }
                    }
                    List<Integer> listsize = new ArrayList<>();
                    for (int i = 0; i < zmBeans.size(); i++) {
                        listsize.add(i + 1);

                    }
//                    Log.d("dbManagerZM", "handleMessage: " + i);
//                    Log.d("dbManagerZMbb", "handleMessage:size " + zmBeans.toString());
                    ryZmAdapter = new RyZmAdapter(getContext(), zmBeans, listsize);//list是imsi列表选中的数据
                    if (et_zhenmasearch_zm.getText().length() == 0) {


//                        ry_zhenma.setAdapter(ryZmAdapter);
                        if (zmBeans.size() > 34) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                            linearLayoutManager.setStackFromEnd(true);//recyview显示最底部一条
                            ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                            tv_searchNum_zm.setText("(" + zmBeans.size() + ")");
                        } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.context);
                            linearLayoutManager.setStackFromEnd(false);//recyview显示最底部一条
                            ry_zhenma_zm.setLayoutManager(linearLayoutManager);
                            tv_searchNum_zm.setText("(" + zmBeans.size() + ")");
                        }
                        ryZmAdapter = new RyZmAdapter(getContext(), zmBeans, listsize);//list是imsi列表选中的数据
                        ry_zhenma_zm.setAdapter(ryZmAdapter);
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.d("300001SQLException", "handleMessage: ." + e.getMessage());
                }
                break;
        }
    }

    ;

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("lcm", "onDestroy");

    }


}
