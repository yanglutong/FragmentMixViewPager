package com.lutong.ui;

import static com.lutong.Constants.isDx;
import static com.lutong.Constants.isJzBj;
import static com.lutong.Constants.isLt;
import static com.lutong.Constants.isYd;
import static com.lutong.Constants.jzMessage;
import static com.lutong.Constants.port;
import static com.lutong.Constants.sendLte;
import static com.lutong.Constants.sendNr;
import static com.lutong.Constants.stop;
import static com.lutong.Constants.typeJzMode;
import static com.lutong.Constants.typePage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.navisdk.util.common.LogUtil;
import com.liys.dialoglib.LAnimationsType;
import com.liys.dialoglib.LDialog;
import com.lutong.App.MessageEvent;
import com.lutong.ConnectivityManager.NetChangeReceiver;
import com.lutong.Constants;
import com.lutong.Device.DeviceInfoActivity;
import com.lutong.OrmSqlLite.DBManagerZM;
import com.lutong.PinConfig.PinConfigViewPagerActivity;
import com.lutong.PinConfig.PinConfigViewPagerActivity5G;
import com.lutong.R;
import com.lutong.SaoPin.SaopinList.SaoPinSetingActivity;
import com.lutong.Service.MyService;
import com.lutong.Utils.AddMenuUtils;
import com.lutong.Utils.BitmapUtil;
import com.lutong.Utils.MyToast;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.pop.DLPopItem;
import com.lutong.Views.pop.DLPopupWindow;
import com.lutong.activity.AddParamActivity;
import com.lutong.activity.JzListActivity;
import com.lutong.adapter.RecyclerAdapter_Jz;
import com.lutong.base.OnTabReselectListener;
import com.lutong.ormlite.DBManagerBj;
import com.lutong.ormlite.JzbJBean;
import com.lutong.server.SocketServerListenHandler;
import com.lutong.tcp.RecJsonBean;
import com.lutong.widget.MyFragmentTabHost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ?????????
 */
public class MainActivity extends FragmentActivity implements
        OnTabChangeListener, OnTouchListener, ServiceConnection, CompoundButton.OnCheckedChangeListener {
    public static String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };//???????????????
    public static List<String> mPermissionList = new ArrayList<>();
    public MyFragmentTabHost mTabHost;
    private MyService.Binder binder = null;
    private TextView title, titles_r; //?????????
    private ImageView ivadd;//add IMSI
    private ImageView iv_menu;// setting
    private DLPopupWindow popupWindow;
    private List<DLPopItem> mList = new ArrayList<>();
    private NetChangeReceiver myBroadcastReceiver;//????????????????????????????????????
    private Timer timerStart = new Timer();
    private int pageMode;//??????????????????
    private LinearLayout liner_jz;
    private LinearLayout liner_location;
    private Context context;
    private SocketServerListenHandler socketServerListenHandler;
    private RelativeLayout liner_home;
    private LinearLayout liner_main;
    private TextView liner_jz_home, liner_gm, liner_dw;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        LocationClient.setAgreePrivacy(true);
        SDKInitializer.initialize(getApplicationContext());
        BitmapUtil.init();//??????????????????
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);//??????
        setCallReceiver();//????????????
        initView();
        //????????????????????????Fragment??????????????????????????????????????????Fragment???????????????????????????
        findViews();
        byte[] buf = new byte[1024];
        //????????????????????????app????????????
        try {
            DBManagerZM dbManagerZM = new DBManagerZM(this);
            dbManagerZM.deleteall();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //?????????????????????????????????
        initGmConfigState();

        //???????????????
        socketServerListenHandler = new SocketServerListenHandler(handler, port);
        socketServerListenHandler.startSchedule(timerStart, 10000);

        setHome();
    }

    private void setHome() {
        liner_home.setVisibility(View.VISIBLE);
        liner_main.setVisibility(View.GONE);
        setStatBar(0);//??????????????????
    }

    private void setCallReceiver() {
        //??????IntenFilter?????????
        IntentFilter intentFilter = new IntentFilter();
        myBroadcastReceiver = new NetChangeReceiver();
        myBroadcastReceiver.setNetChangeListener(listener);
        //????????????????????????????????????android.net.conn.CONNECTIVITY_CHANGE???????????????
        //???action?????????????????????????????????????????????????????????
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myBroadcastReceiver, intentFilter);//
    }

    private void initGmConfigState() {
        //?????????????????????
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        isJzBj = name.getBoolean("checkbox_jzBaoJ", true);
        jzMessage = name.getInt("ed_jzTime", 300);
        isYd = name.getBoolean("checkbox4_yd", true);
        isLt = name.getBoolean("checkbox4_lt", true);
        isDx = name.getBoolean("checkbox4_dx", true);

        //??????????????????
        try {
            DBManagerBj managerBj = new DBManagerBj(context);
            Log.e("ylt", "initGmConfigState: " + managerBj.getdemoBeanList());
            EventBus.getDefault().postSticky(new MessageEvent(13145, managerBj.getdemoBeanList()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 3333: {//????????????
                    MyToast.showToast("????????????");
                    //????????????
                    socketServerListenHandler.sendMessage(sendMsg);//??????????????????
                    break;
                }
                case 8888: {
                    Log.e("ylt", "handleMessage 8888: " + msg.obj);
                    socketServerListenHandler.listenClientConnect();//????????????
                    break;
                }
                case 7777: {
                    RecJsonBean obj = (RecJsonBean) msg.obj;
                    Log.e("ylt", "handleMessage: 7777" + obj);
                    mTabHost.setCurrentTab(0);
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            EventBus.getDefault().postSticky(new MessageEvent(9999, obj));
                        }
                    }, 3000);
                    break;
                }
//                case 3030: {//???????????????
////                    ArrayList<Boolean> booleans = (ArrayList<Boolean>) msg.obj;
////                    Boolean page0 = booleans.get(0);
////                    Boolean page1 = booleans.get(1);
//////                    boolean state = (boolean) msg.obj;//true ?????????????????? false?????????
////                    String title = "";
////                    if (pageMode == 0) {
////                        title = "(?????????????????????...)";
////                        if (page0) {//??????????????????
////                            title = "(????????????????????????)";
////                        }
////                    }
//////                    else if (pageMode == 1) {
//////                        title = "(?????????????????????...)";
//////                        if (page1) {
//////                            title = "(????????????????????????)";
//////                        }
//////                    }
////                    titles_r.setText(title);
////                    Log.e("TAG", "handleMessage:3030 "+booleans.toString());
//                    break;
//                }
            }
        }
    };

    //?????????????????????...  ????????????????????????
    private void findViews() {
        title = findViewById(R.id.titles);//??????
        titles_r = findViewById(R.id.titles_r);//???????????????
        //????????????
        liner_jz = findViewById(R.id.liner_jz);
        //????????????
        liner_location = findViewById(R.id.Liner_location);
        title.setVisibility(View.VISIBLE);//?????????????????????
        title.setText(getText(R.string.title2));//?????????????????????
        ivadd = findViewById(R.id.iv_add);//imsi????????????
        ivadd.setVisibility(View.GONE);
        ivadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AddParamActivity.class));
            }
        });
        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v, 0, 0);
            }
        });

        ImageView iv_home = findViewById(R.id.iv_home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????
                liner_home.setVisibility(View.VISIBLE);
                liner_main.setVisibility(View.GONE);
                mTabHost.clearAllTabs();
                initTabs();
                setStatBar(0);
            }
        });
        ImageView iv_homes = findViewById(R.id.iv_homes);
        iv_homes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????
                liner_home.setVisibility(View.VISIBLE);
                liner_main.setVisibility(View.GONE);
                mTabHost.clearAllTabs();
                initTabs();
                setStatBar(0);
            }
        });

        liner_home = findViewById(R.id.liner_home);
        liner_main = findViewById(R.id.liner_main);
        liner_jz_home = findViewById(R.id.liner_jz_home);
        liner_gm = findViewById(R.id.liner_gm);
        liner_dw = findViewById(R.id.liner_dw);

        liner_jz_home.setOnClickListener(v -> {
            //???????????????
            liner_home.setVisibility(View.GONE);
            liner_main.setVisibility(View.VISIBLE);
            setStatBar(1);
            mTabHost.setCurrentTab(0);
        });
        liner_gm.setOnClickListener(v -> {
            //???????????????
            liner_home.setVisibility(View.GONE);
            liner_main.setVisibility(View.VISIBLE);
            setStatBar(1);
            mTabHost.setCurrentTab(1);
        });
        liner_dw.setOnClickListener(v -> {
            //???????????????
            liner_home.setVisibility(View.GONE);
            liner_main.setVisibility(View.VISIBLE);
            setStatBar(1);
            mTabHost.setCurrentTab(2);
        });
    }

    ServiceConnection servic = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.Binder) service;
            binder.getService().setCallback(new MyService.CallBack() {
                @Override
                public void onDataChanged(final String data) {//?????????Service????????????data??????Thread????????????????????????????????????????????????????????????????????????TextView??????
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //?????????
    private void initView() {
        Intent intent = new Intent(this, MyService.class);
        this.bindService(intent, servic, Context.BIND_AUTO_CREATE);
        // TODO Auto-generated method stub
        mTabHost = (MyFragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        initTabs();
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
//		mTabHost.setVisibility(View.GONE);
        AddITemMenujz();//??????
        getPermissions();
    }

    private void AddITemMenu() {//?????????????????????
        popupWindow = new DLPopupWindow(this, mList, DLPopupWindow.STYLE_WEIXIN);
        mList.clear();
        AddMenuUtils.addmenu(this, popupWindow, mList);
        popupWindow.setOnItemClickListener(new DLPopupWindow.OnItemClickListener() {
            @Override
            public void OnClick(int position) {
                if (mList.get(position).getText().equals("4G??????")) {
                    startActivity(new Intent(context, SaoPinSetingActivity.class));
                }
                if (mList.get(position).getText().equals("4G??????")) {
                    startActivity(new Intent(context, PinConfigViewPagerActivity.class));
                }
                if (mList.get(position).getText().equals("5G??????")) {
                    startActivity(new Intent(context, PinConfigViewPagerActivity5G.class));
                }
                if (mList.get(position).getText().equals("5G??????")) {
                    ToastUtils.showToast("?????????????????????");
                }
                if (mList.get(position).getText().equals("????????????")) {
                    startActivity(new Intent(context, DeviceInfoActivity.class));
                }
                if (mList.get(position).getText().equals("????????????")) {
                }
            }
        });
    }

    private void AddITemMenujz() {//?????????????????????
        popupWindow = new DLPopupWindow(this, mList, DLPopupWindow.STYLE_DEF);
        mList.clear();
        AddMenuUtils.addmenuJz(this, popupWindow, mList);
        popupWindow.setOnItemClickListener(new DLPopupWindow.OnItemClickListener() {
            @Override
            public void OnClick(int position) {
                if (mList.get(position).getText().equals("????????????")) {
                    startActivity(new Intent(context, JzListActivity.class));
                }
                if (mList.get(position).getText().equals("dk")) {
                    Toast.makeText(context, "dk", Toast.LENGTH_SHORT).show();
                    socketServerListenHandler.eliminate();
                }
            }
        });
    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();//tab??????
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(tag -> new View(context));
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);

        }
    }

    /**
     * ?????????????????????
     *
     * @param tabId
     */
    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        Log.d("onTabChanged", "onTabChanged: " + mTabHost.getCurrentTab());
        ivadd.setVisibility(View.GONE);
        switch (mTabHost.getCurrentTab()) {
            case 0:
                pageMode = 0;

                typePage = 0;

                EventBus.getDefault().postSticky(new MessageEvent<>(3033, ""));

                setCurrentTab((String) getText(R.string.title2));
                break;
            case 1:
                pageMode = 1;

                typePage = 1;
                //?????????????????????4 5G????????????
                setCurrentTab((String) getText(R.string.title3));
                break;
            case 2:
                pageMode = 2;

                typePage = 2;

                EventBus.getDefault().postSticky(new MessageEvent<>(3033, ""));

                setCurrentTab((String) getText(R.string.title1));
                break;
        }
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }

    private void setCurrentTab(String title) {
        if (pageMode == 0) {
            AddITemMenujz();
            iv_menu.setVisibility(View.VISIBLE);
            liner_jz.setVisibility(View.VISIBLE);
            liner_location.setVisibility(View.GONE);
            sendMsg = stop;
        } else if (pageMode == 1) {
            AddITemMenuGm();
            iv_menu.setVisibility(View.VISIBLE);
            liner_jz.setVisibility(View.VISIBLE);
            liner_location.setVisibility(View.GONE);
            if (typeJzMode == 5) {
                sendMsg = sendNr;
            } else {
                sendMsg = sendLte;
            }
        } else {
            AddITemMenu();
            iv_menu.setVisibility(View.VISIBLE);
            liner_jz.setVisibility(View.GONE);
            liner_location.setVisibility(View.VISIBLE);
            sendMsg = stop;
        }
        this.title.setText(title);
        socketServerListenHandler.sendMessage(sendMsg);//????????????????????????????????????
    }

    //?????????????????????????????????
    private void AddITemMenuGm() {
        popupWindow = new DLPopupWindow(this, mList, DLPopupWindow.STYLE_WEIXIN);
        mList.clear();
        AddMenuUtils.addmenuGm(this, popupWindow, mList);
        popupWindow.setOnItemClickListener(new DLPopupWindow.OnItemClickListener() {
            @Override
            public void OnClick(int position) {
                final PopupWindow popupWindow = new PopupWindow(context);
                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                                popupWindow .setBackgroundDrawable(null);
                popupWindow.setFocusable(true);//????????????view????????????popwindow?????????
                popupWindow.setOutsideTouchable(true);//????????????popupwindow??????
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//??????????????????????????????????????????
                if (mList.get(position).getText().equals("????????????")) {
                    //????????????
                    setGmConfigs();
                }
                if (mList.get(position).getText().equals("????????????")) {
//                    setGmBjConfig(popupWindow);
                    //????????????
                    setGmBjConfigs();
                }
            }
        });
    }

    private void setGmBjConfigs() {
        LDialog dialog = LDialog.newInstance(this, R.layout.pin_config_lte_type);
        dialog
                .setMaskValue(0.5f) //??????--?????????(0-1)
                //1.?????????
                //????????????
//                .setWidth(800) //??????:dp
//                .setWidthPX(800) //??????:px
                .setWidthRatio(0.8) //??????????????????
//                //????????????
//                .setMinWidth(300) //??????:dp
//                .setMinWidth(100) //??????:px
//                .setMinWidthRatio(0.5) //??????????????????
//                //????????????
//                .setMaxWidth(300) //??????:dp
//                .setMaxWidthPX(100) //??????:px
//                .setMaxWidthRatio(0.8) //??????????????????

                //2.?????????
                //????????????
//                .setHeight(600) //??????:dp
//                .setHeightPX(600) //??????:px
                .setHeightRatio(0.53) //??????????????????
//                //????????????
//                .setMinHeight(100) //??????:dp
//                .setMinHeightPX(100) //??????:px
//                .setMinHeightRatio(0.3) //??????????????????
//                //????????????
//                .setMaxHeight(100) //??????:dp
//                .setMaxHeightPX(100) //??????:px
//                .setMaxHeightRatio(0.3) //??????????????????

                //3.????????????
                //??????
                .setBgColor(Color.WHITE) //????????????
//                .setBgColor("#FFFFFF") //????????????
//                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, Color.BLUE, Color.YELLOW) //????????????(????????????) ??????1??????????????????
//                .setBgColor(GradientDrawable.Orientation.BOTTOM_TOP, "#00FEE9", "#008EB4") //????????????(????????????)
//                .setBgColorRes(R.color.white) //????????????(res??????)
//                .setBgColorRes(GradientDrawable.Orientation.BOTTOM_TOP, R.color.colorAccent, R.color.colorPrimary) //????????????(????????????)
                //??????
                .setBgRadius(5) //??????, ?????????dp
                .setBgRadius(5, 5, 0, 0) //??????, ?????????dp
                .setBgRadiusPX(10) //??????, ?????????px
                .setBgRadiusPX(10, 10, 10, 10) //??????, ?????????px

                //4.??????????????????
//                .setGravity(Gravity.LEFT | Gravity.BOTTOM) //????????????
//                .setGravity(Gravity.LEFT, 0, 0) //????????????(?????????)
                .setGravity(Gravity.CENTER, 0, 0)


                //5.????????????
                //5.1 ????????????(??????????????????????????????)
                // ???????????????DEFAULT(??????) (LEFT TOP RIGHT BOTTOM ??????)  SCALE(??????)
                .setAnimations(LAnimationsType.SCALE)
                //5.2 ???????????????
                .setAnimationsStyle(R.style.li_dialog_default) //????????????

                //6.??????????????????
                //6.1 ????????????View??????
//                .setText(R.id.tv_title, "??????")
//                .setTextColor()
//                .setTextSize()
//                .setTextSizePX()
//                .setBackgroundColor()
//                .setBackgroundRes()
//                .setImageBitmap()
//                .setVisible()
//                .setGone()
                //6.2 ????????????
                .setOnTouchOutside(true) //??????????????????
//                .setCancelBtn(R.id.tv_cancel, R.id.tv_confirm) //?????????????????????????????????(???????????????)
//                .setOnClickListener(new LDialog.DialogOnClickListener() { //??????????????????
//                    @Override
//                    public void onClick(View v, LDialog customDialog) {
//                        customDialog.dismiss();
//                    }
//                }, R.id.tv_confirm, R.id.tv_cancel)  //???????????????
                .show();
        //?????????
        initGmBjConfigs(dialog);
    }

    private void initGmBjConfigs(LDialog dialog) {
        //????????????
        et_cid = dialog.getView(R.id.et_Cid);
        et_tac = dialog.getView(R.id.et_tac);
        Button bt_Ok = dialog.getView(R.id.bt_Ok);
        Button bt_Cancel = dialog.getView(R.id.bt_Cancel);
        final RecyclerView recyclerView = dialog.getView(R.id.recycler_bao_j);
        final LinearLayout liner_jzBj = dialog.getView(R.id.liner_jzBj);
        jBeans = new ArrayList<>();

        //?????????recyclerView
        //???????????????
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapterJz = new RecyclerAdapter_Jz(context, jBeans);
        recyclerView.setAdapter(adapterJz);

        try {
            //????????????????????????
            final DBManagerBj dbManager = new DBManagerBj(context);
            //?????????????????????
            if (dbManager.getdemoBeanList().size() == 0) {
                dbManager.insertdemoBean(new JzbJBean());
            }

            Log.e("ylt", "initGmBjConfigs: " + dbManager.getdemoBeanList().toString());

            jBeans.clear();
            jBeans.addAll(dbManager.getdemoBeanList());
            if (jBeans.size() > 1) {//?????????????????????????????????????????????
                liner_jzBj.setVisibility(View.VISIBLE);
                dialog.setHeightRatio(0.53);
            } else {//????????????????????????????????????
                liner_jzBj.setVisibility(View.GONE);
                dialog.setHeightRatio(0.3);
            }
            adapterJz.setOnItemClick(new RecyclerAdapter_Jz.onItemClick() {
                @Override
                public void onClick(int i) {
                    if (jBeans.size() > 1) {
                        if (jBeans.size() == 2) {
                            //???????????????????????????????????????item??????
                            JzbJBean jBean = jBeans.get(i);
                            try {
                                dbManager.deletedemoBean(jBean);
                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());

                                //                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //????????????????????????????????????fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            liner_jzBj.setVisibility(View.GONE);
                            dialog.setHeightRatio(0.3);

                        } else {
                            //???????????????????????????
                            try {
                                dbManager.deletedemoBean(jBeans.get(i));

                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());

                                //                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //????????????????????????????????????fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            //???????????????
                            if (jBeans.size() > 1) {
                                for (int i1 = 1; i1 < jBeans.size(); i1++) {
                                    JzbJBean jBean = jBeans.get(i1);
                                    jBean.setCount(i1);
                                }
                            }
                            try {
                                List<JzbJBean> beans = dbManager.getdemoBeanList();
                                if (beans.size() > 1) {
                                    for (int i1 = 1; i1 < beans.size(); i1++) {
                                        JzbJBean bean = beans.get(i1);
                                        dbManager.updateName(bean, i1);
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                        adapterJz.notifyDataSetChanged();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bt_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????tac ???cid
                String cid = et_cid.getText().toString();
                String tac = et_tac.getText().toString();

                if (TextUtils.isEmpty(cid) || TextUtils.isEmpty(tac)) {
                    Toast.makeText(context, "?????????TAC???CID", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        DBManagerBj dbManager = new DBManagerBj(context);
                        List<JzbJBean> beans = dbManager.getdemoBeanList();

                        if (beans.size() > 1) {//???????????? ?????????????????????
                            boolean isExist = false;
                            for (int i = 1; i < beans.size(); i++) {
                                JzbJBean bean = beans.get(i);
                                if (bean.getTac().equals(tac) && bean.getCid().equals(cid)) {//???????????????
                                    isExist = true;
                                }
                            }
                            if (isExist) {
                                Toast.makeText(context, "??????????????????", Toast.LENGTH_SHORT).show();
                            } else {
                                //????????????
                                if (dbManager.getdemoBeanList().size() == 2) {//???????????????????????????
                                    dbManager.updateName(dbManager.getdemoBeanList().get(1), 1);
                                }

                                dbManager.insertdemoBean(new JzbJBean(tac, cid, dbManager.getdemoBeanList().size()));
                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());

//                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //????????????????????????????????????fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));

                                //????????????????????????
                                if (jBeans.get(jBeans.size() - 1).getTac().equals(tac) && jBeans.get(jBeans.size() - 1).getCid().equals(cid)) {//??????????????????????????????
                                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
                                    adapterJz.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {//?????????????????????????????? ???????????????
                            //????????????
                            dbManager.insertdemoBean(new JzbJBean(tac, cid, 1));
                            beans = dbManager.getdemoBeanList();
                            //????????????????????????
                            if (beans.get(beans.size() - 1).getTac().equals(tac) && beans.get(beans.size() - 1).getCid().equals(cid)) {//??????????????????????????????
                                liner_jzBj.setVisibility(View.VISIBLE);
                                dialog.setHeightRatio(0.53);
                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());


                                //                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //????????????????????????????????????fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));
                                adapterJz.notifyDataSetChanged();
                                Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "????????????", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bt_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private EditText et_cid;//???????????? cid
    private EditText et_tac;//???????????? tac
    private ArrayList<JzbJBean> jBeans;
    private RecyclerAdapter_Jz adapterJz;

    private void setGmConfigs() {
        LDialog dialog = LDialog.newInstance(this, R.layout.pin_config_lte);
        dialog
                .setMaskValue(0.5f)
                .setWidthRatio(0.8)
                .setHeightRatio(0.33)
                .setBgColor(Color.WHITE)
                .setBgRadius(5) //??????, ?????????dp
                .setBgRadius(5, 5, 0, 0) //??????, ?????????dp
                .setBgRadiusPX(10) //??????, ?????????px
                .setBgRadiusPX(10, 10, 10, 10) //??????, ?????????px

                //4.??????????????????
//                .setGravity(Gravity.LEFT | Gravity.BOTTOM) //????????????
//                .setGravity(Gravity.LEFT, 0, 0) //????????????(?????????)
                .setGravity(Gravity.CENTER, 0, 0)
                .setAnimations(LAnimationsType.SCALE)
                //5.2 ???????????????
                .setAnimationsStyle(R.style.li_dialog_default)
                .setOnTouchOutside(true)
                .show();
        CheckBox checkbox_yd = dialog.getView(R.id.checkbox_yd);
        CheckBox checkbox_lt = dialog.getView(R.id.checkbox_lt);
        CheckBox checkbox_dx = dialog.getView(R.id.checkbox_dx);
        CheckBox checkbox_jzBaoJ = dialog.getView(R.id.checkbox_jzBaoJ);//??????????????????????????????
        EditText ed_jzTime = dialog.getView(R.id.ed_jzTime);//??????????????????????????????
        //??????
        SharedPreferences name = getSharedPreferences("name", Context.MODE_PRIVATE);
        isJzBj = name.getBoolean("checkbox_jzBaoJ", true);
        jzMessage = name.getInt("ed_jzTime", 300);
        isYd = name.getBoolean("checkbox4_yd", true);
        isLt = name.getBoolean("checkbox4_lt", true);
        isDx = name.getBoolean("checkbox4_dx", true);
        //??????
        checkbox_yd.setOnCheckedChangeListener(this);
        checkbox_lt.setOnCheckedChangeListener(this);
        checkbox_dx.setOnCheckedChangeListener(this);
        checkbox_jzBaoJ.setOnCheckedChangeListener(this);
        //??????popw??????????????????
        checkbox_yd.setChecked(isYd);
        checkbox_lt.setChecked(isLt);
        checkbox_dx.setChecked(isDx);
        checkbox_jzBaoJ.setChecked(isJzBj);
        ed_jzTime.setText(jzMessage + "");
        dialog.getView(R.id.bt_Cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //dialog??????
        dialog.getView(R.id.bt_Ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //??????1???????????????SharedPreferences??????
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //??????2??? ?????????SharedPreferences.Editor??????
                SharedPreferences.Editor editor = sharedPreferences.edit();


                jzMessage = Integer.parseInt(ed_jzTime.getText().toString());

                isJzBj = checkbox_jzBaoJ.isChecked();

                //3????????????????????????????????????
                editor.putBoolean("checkbox_jzBaoJ", isJzBj);
                editor.putInt("ed_jzTime", jzMessage);
                editor.putBoolean("checkbox4_yd", isYd);
                editor.putBoolean("checkbox4_lt", isLt);
                editor.putBoolean("checkbox4_dx", isDx);
                //4?????????
                editor.commit();
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //??????1???????????????SharedPreferences??????
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //??????2??? ?????????SharedPreferences.Editor??????
                SharedPreferences.Editor editor = sharedPreferences.edit();


                jzMessage = Integer.parseInt(ed_jzTime.getText().toString());

                isJzBj = checkbox_jzBaoJ.isChecked();

                //3????????????????????????????????????
                editor.putBoolean("checkbox_jzBaoJ", isJzBj);
                editor.putInt("ed_jzTime", jzMessage);
                editor.putBoolean("checkbox4_yd", isYd);
                editor.putBoolean("checkbox4_lt", isLt);
                editor.putBoolean("checkbox4_dx", isDx);
                //4?????????
                editor.commit();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (MyService.Binder) service;
        binder.getService().setCallback(data -> {//?????????Service????????????data??????Thread????????????????????????????????????????????????????????????????????????TextView??????
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            msg.setData(bundle);
            handler.sendMessage(msg);
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        destory();
    }

    private void destory() {
        EventBus.getDefault().unregister(this);
        if (timerStart != null) {
            timerStart.cancel();
            timerStart = null;
        }
        if (socketServerListenHandler != null) {
            socketServerListenHandler = null;
        }
        if (myBroadcastReceiver != null) {
            unregisterReceiver(myBroadcastReceiver);
        }
        if (servic != null) {
            unbindService(servic);
        }
    }

    /**
     * ??????????????????
     *
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(float bgAlpha, Context mContext) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;

    /**
     * ????????????
     */
    public void getPermissions() {//??????????????????
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//?????????????????????????????????????????????
//            Toast.makeText(LoginActivity.this,"????????????",Toast.LENGTH_LONG).show();
        } else {//??????????????????
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//???List????????????
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    long mPressedTime = 0;

    @Override
    public void onBackPressed() {//?????????????????????
        long mNowTime = System.currentTimeMillis();//???????????????????????????
        Log.d("nzq", "onBackPressed: " + mNowTime);
        if ((mNowTime - mPressedTime) > 2000) {//???????????????????????????
            ToastUtils.showToast("????????????????????????");
            mPressedTime = mNowTime;
        } else {//????????????
            this.finish();
            System.exit(0);
        }
    }

    public String sendMsg = stop;//???????????????????????????????????????

    //??????Event
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onEvent(MessageEvent event) {
        if (event.getCode() == 3030) {//??????????????????
            boolean data = (Boolean) event.getData();
            Message message = Message.obtain();
            message.obj = data;
            message.what = 3030;
            handler.sendMessage(message);
        }
        if (event.getCode() == 2022) {//??????4???5G??????????????????
            sendMsg = event.getData().toString();
            socketServerListenHandler.sendMessage(sendMsg);//????????????????????????
        }
        if (event.getCode() == 7777) {
            RecJsonBean recJsonBean = (RecJsonBean) event.getData();
            Message message = Message.obtain();
            message.obj = recJsonBean;
            message.what = 7777;
            handler.sendMessage(message);
//            mTabHost.setCurrentTab(0);
            Log.e("ylt", "onEvent: 7777" + recJsonBean.toString());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox_yd: {
                //??????1???????????????SharedPreferences??????
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //??????2??? ?????????SharedPreferences.Editor??????
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox4_yd", isChecked);
                editor.commit();
                isYd = isChecked;
//                //????????????????????????fragment
//                setGmConfigSettings.setGmConfig(isYd, isLt, isDx, isJzBj, jzMessage);
                break;
            }
            case R.id.checkbox_lt: {
                //??????1???????????????SharedPreferences??????
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //??????2??? ?????????SharedPreferences.Editor??????
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox4_lt", isChecked);
                editor.commit();
                isLt = isChecked;
//                //????????????????????????fragment
//                setGmConfigSettings.setGmConfig(isYd, isLt, isDx, isJzBj, jzMessage);
                break;
            }
            case R.id.checkbox_dx: {
                //??????1???????????????SharedPreferences??????
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //??????2??? ?????????SharedPreferences.Editor??????
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox4_dx", isChecked);
                editor.commit();
                isDx = isChecked;
//                //????????????????????????fragment
//                setGmConfigSettings.setGmConfig(isYd, isLt, isDx, isJzBj, jzMessage);
                break;
            }
            case R.id.checkbox_jzBaoJ: {
                if (!isChecked) {//????????? ????????????????????????
                    EventBus.getDefault().postSticky(new MessageEvent<>(3033, ""));
                }
                isJzBj = isChecked;
            }
        }
    }

    NetChangeReceiver.NetChangeListener listener = status -> {//????????????????????????
        EventBus.getDefault().postSticky(new MessageEvent(5500, status));
    };

    @SuppressLint("NewApi")
    public void setStatBar(int type) {//????????????????????????????????????
        View decorView = getWindow().getDecorView();
        int option;
        if (type == 1) {
            option = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.parseColor("#00564B"));
        } else {
            option =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


    }

    /**
     * ????????????activity??????????????????????????????????????????
     *
     * @param activity ???????????????
     */
    public void setScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
