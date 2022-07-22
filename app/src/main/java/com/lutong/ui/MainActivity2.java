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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
 * 主界面
 */
public class MainActivity2 extends FragmentActivity implements
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
    };//申请的权限
    public static List<String> mPermissionList = new ArrayList<>();
    public MyFragmentTabHost mTabHost;
    private MyService.Binder binder = null;
    private TextView title, titles_r; //标题栏
    private ImageView ivadd;//add IMSI
    private ImageView iv_menu;// setting
    private DLPopupWindow popupWindow;
    private List<DLPopItem> mList = new ArrayList<>();
    private NetChangeReceiver myBroadcastReceiver;//监听是否有手机卡网络连接
    private Timer timerStart = new Timer();
    private int pageMode;//区分界面标识
    private LinearLayout liner_jz;
    private LinearLayout liner_location;
    private Context context;
    private SocketServerListenHandler socketServerListenHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        LocationClient.setAgreePrivacy(true);
        SDKInitializer.initialize(getApplicationContext());
        BitmapUtil.init();//创建轨迹图片
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);//注册
        setCallReceiver();//注册广播
        initView();
        //这步是关键，因为Fragment实现了该接口，所以可以直接把Fragment实例赋值给接口引用
        findViews();
        byte[] buf = new byte[1024];
        //创建窗口清空上次app侦码记录
        try {
            DBManagerZM dbManagerZM = new DBManagerZM(this);
            dbManagerZM.deleteall();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //初始化服务
        socketServerListenHandler = new SocketServerListenHandler(handler, port);
        socketServerListenHandler.startSchedule(timerStart, 10000);
        //初始化工模配置选项状态
        initGmConfigState();
    }

    private void setCallReceiver() {
        //创建IntenFilter的实例
        IntentFilter intentFilter = new IntentFilter();
        myBroadcastReceiver = new NetChangeReceiver();
        myBroadcastReceiver.setNetChangeListener(listener);
        //网络发生变化，系统会发出android.net.conn.CONNECTIVITY_CHANGE这样的广播
        //在action添加广播内容，就能接收到相应的广播内容
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myBroadcastReceiver, intentFilter);//
    }

    private void initGmConfigState() {
        //初始化成员变量
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        isJzBj = name.getBoolean("checkbox_jzBaoJ", true);
        jzMessage = name.getInt("ed_jzTime", 300);
        isYd = name.getBoolean("checkbox4_yd", true);
        isLt = name.getBoolean("checkbox4_lt", true);
        isDx = name.getBoolean("checkbox4_dx", true);

        //初始化数据库
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
                case 3333: {//连接成功
                    MyToast.showToast("连接成功");
                    //发送停止
                    socketServerListenHandler.sendMessage(sendMsg);//发送停止命令
                    break;
                }
                case 8888: {
                    Log.e("ylt", "handleMessage 8888: " + msg.obj);
                    socketServerListenHandler.listenClientConnect();//重新连接
                    break;
                }
//                case 3030: {//标题栏状态
////                    ArrayList<Boolean> booleans = (ArrayList<Boolean>) msg.obj;
////                    Boolean page0 = booleans.get(0);
////                    Boolean page1 = booleans.get(1);
//////                    boolean state = (boolean) msg.obj;//true 移动数据可用 false不可用
////                    String title = "";
////                    if (pageMode == 0) {
////                        title = "(移动数据切换中...)";
////                        if (page0) {//移动数据可用
////                            title = "(移动数据切换成功)";
////                        }
////                    }
//////                    else if (pageMode == 1) {
//////                        title = "(扫网设置切换中...)";
//////                        if (page1) {
//////                            title = "(扫网设置切换成功)";
//////                        }
//////                    }
////                    titles_r.setText(title);
////                    Log.e("TAG", "handleMessage:3030 "+booleans.toString());
//                    break;
//                }
            }
        }
    };

    //移动数据切换中...  移动数据切换成功
    private void findViews() {
        title = findViewById(R.id.titles);//标题
        titles_r = findViewById(R.id.titles_r);//标题小标题
        //基站标题
        liner_jz = findViewById(R.id.liner_jz);
        //定位标题
        liner_location = findViewById(R.id.Liner_location);
        title.setVisibility(View.VISIBLE);//默认标题栏展示
        title.setText(getText(R.string.title2));//默认标题栏文字
        ivadd = findViewById(R.id.iv_add);//imsi添加按钮
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
                //返回主界面
                finish();
            }
        });
    }

    ServiceConnection servic = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.Binder) service;
            binder.getService().setCallback(new MyService.CallBack() {
                @Override
                public void onDataChanged(final String data) {//因为在Service里面赋值data是在Thread中进行的，所以我们不能直接在这里将返回的值展示在TextView上。
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //初始化
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
        AddITemMenujz();//定位
        getPermissions();
    }

    private void AddITemMenu() {//添加菜单的按钮
        popupWindow = new DLPopupWindow(this, mList, DLPopupWindow.STYLE_WEIXIN);
        mList.clear();
        AddMenuUtils.addmenu(this, popupWindow, mList);
        popupWindow.setOnItemClickListener(new DLPopupWindow.OnItemClickListener() {
            @Override
            public void OnClick(int position) {
                if (mList.get(position).getText().equals("4G扫频")) {
                    startActivity(new Intent(context, SaoPinSetingActivity.class));
                }
                if (mList.get(position).getText().equals("4G频点")) {
                    startActivity(new Intent(context, PinConfigViewPagerActivity.class));
                }
                if (mList.get(position).getText().equals("5G频点")) {
                    startActivity(new Intent(context, PinConfigViewPagerActivity5G.class));
                }
                if (mList.get(position).getText().equals("5G扫频")) {
                    ToastUtils.showToast("开发中暂未开放");
                }
                if (mList.get(position).getText().equals("设备信息")) {
                    startActivity(new Intent(context, DeviceInfoActivity.class));
                }
                if (mList.get(position).getText().equals("数据管理")) {
                }
            }
        });
    }

    private void AddITemMenujz() {//添加菜单的按钮
        popupWindow = new DLPopupWindow(this, mList, DLPopupWindow.STYLE_DEF);
        mList.clear();
        AddMenuUtils.addmenuJz(this, popupWindow, mList);
        popupWindow.setOnItemClickListener(new DLPopupWindow.OnItemClickListener() {
            @Override
            public void OnClick(int position) {
                if (mList.get(position).getText().equals("基站列表")) {
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
        MainTab[] tabs = MainTab.values();//tab数据
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
     * 底部导航栏监听
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

                EventBus.getDefault().postSticky(new MessageEvent<>(3033,""));

                setCurrentTab((String) getText(R.string.title2));
                break;
            case 1:
                pageMode = 1;

                typePage = 1;
                //获取工模界面的4 5G选中状态
                setCurrentTab((String) getText(R.string.title3));
                break;
            case 2:
                pageMode = 2;

                typePage = 2;

                EventBus.getDefault().postSticky(new MessageEvent<>(3033,""));

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
        socketServerListenHandler.sendMessage(sendMsg);//基站工模定位切换下发指令
    }

    //工模右上角设置菜单按钮
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
                popupWindow.setFocusable(true);//解决点击view重新打开popwindow的问题
                popupWindow.setOutsideTouchable(true);//点击外部popupwindow消失
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景不为空但是完全透明。
                if (mList.get(position).getText().equals("配置选项")) {
                    //基站设置
                    setGmConfigs();
                }
                if (mList.get(position).getText().equals("基站报警")) {
//                    setGmBjConfig(popupWindow);
                    //基站报警
                    setGmBjConfigs();
                }
            }
        });
    }

    private void setGmBjConfigs() {
        LDialog dialog = LDialog.newInstance(this, R.layout.pin_config_lte_type);
        dialog
                .setMaskValue(0.5f) //遮罩--透明度(0-1)
                //1.设置宽
                //精确宽度
//                .setWidth(800) //单位:dp
//                .setWidthPX(800) //单位:px
                .setWidthRatio(0.8) //占屏幕宽比例
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
//                .setHeight(600) //单位:dp
//                .setHeightPX(600) //单位:px
                .setHeightRatio(0.53) //占屏幕高比例
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
                .setBgRadius(5) //圆角, 单位：dp
                .setBgRadius(5, 5, 0, 0) //圆角, 单位：dp
                .setBgRadiusPX(10) //圆角, 单位：px
                .setBgRadiusPX(10, 10, 10, 10) //圆角, 单位：px

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
        //初始化
        initGmBjConfigs(dialog);
    }

    private void initGmBjConfigs(LDialog dialog) {
        //控件查找
        et_cid = dialog.getView(R.id.et_Cid);
        et_tac = dialog.getView(R.id.et_tac);
        Button bt_Ok = dialog.getView(R.id.bt_Ok);
        Button bt_Cancel = dialog.getView(R.id.bt_Cancel);
        final RecyclerView recyclerView = dialog.getView(R.id.recycler_bao_j);
        final LinearLayout liner_jzBj = dialog.getView(R.id.liner_jzBj);
        jBeans = new ArrayList<>();

        //初始化recyclerView
        //条目初始化
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapterJz = new RecyclerAdapter_Jz(context, jBeans);
        recyclerView.setAdapter(adapterJz);

        try {
            //获取数据库的集合
            final DBManagerBj dbManager = new DBManagerBj(context);
            //先插入默认数据
            if (dbManager.getdemoBeanList().size() == 0) {
                dbManager.insertdemoBean(new JzbJBean());
            }

            Log.e("ylt", "initGmBjConfigs: " + dbManager.getdemoBeanList().toString());

            jBeans.clear();
            jBeans.addAll(dbManager.getdemoBeanList());
            if (jBeans.size() > 1) {//显示界面，将数据库数据显示出来
                liner_jzBj.setVisibility(View.VISIBLE);
                dialog.setHeightRatio(0.53);
            } else {//数据库没有数据，隐藏界面
                liner_jzBj.setVisibility(View.GONE);
                dialog.setHeightRatio(0.3);
            }
            adapterJz.setOnItemClick(new RecyclerAdapter_Jz.onItemClick() {
                @Override
                public void onClick(int i) {
                    if (jBeans.size() > 1) {
                        if (jBeans.size() == 2) {
                            //删除的为最后一条数据就隐藏item界面
                            JzbJBean jBean = jBeans.get(i);
                            try {
                                dbManager.deletedemoBean(jBean);
                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());

                                //                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //将保存的数据库条目发送给fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            liner_jzBj.setVisibility(View.GONE);
                            dialog.setHeightRatio(0.3);

                        } else {
                            //删除的不为最后一条
                            try {
                                dbManager.deletedemoBean(jBeans.get(i));

                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());

                                //                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //将保存的数据库条目发送给fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            //将序号排序
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
                //获取tac 和cid
                String cid = et_cid.getText().toString();
                String tac = et_tac.getText().toString();

                if (TextUtils.isEmpty(cid) || TextUtils.isEmpty(tac)) {
                    Toast.makeText(context, "请输入TAC或CID", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        DBManagerBj dbManager = new DBManagerBj(context);
                        List<JzbJBean> beans = dbManager.getdemoBeanList();

                        if (beans.size() > 1) {//已有数据 就正常添加数据
                            boolean isExist = false;
                            for (int i = 1; i < beans.size(); i++) {
                                JzbJBean bean = beans.get(i);
                                if (bean.getTac().equals(tac) && bean.getCid().equals(cid)) {//重复的数据
                                    isExist = true;
                                }
                            }
                            if (isExist) {
                                Toast.makeText(context, "请勿重复添加", Toast.LENGTH_SHORT).show();
                            } else {
                                //插入数据
                                if (dbManager.getdemoBeanList().size() == 2) {//处理只有一条数据时
                                    dbManager.updateName(dbManager.getdemoBeanList().get(1), 1);
                                }

                                dbManager.insertdemoBean(new JzbJBean(tac, cid, dbManager.getdemoBeanList().size()));
                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());

//                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //将保存的数据库条目发送给fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));

                                //查看是否插入成功
                                if (jBeans.get(jBeans.size() - 1).getTac().equals(tac) && jBeans.get(jBeans.size() - 1).getCid().equals(cid)) {//最后一条是最新插入的
                                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                                    adapterJz.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {//数据库里首次添加数据 就显示界面
                            //插入数据
                            dbManager.insertdemoBean(new JzbJBean(tac, cid, 1));
                            beans = dbManager.getdemoBeanList();
                            //查看是否插入成功
                            if (beans.get(beans.size() - 1).getTac().equals(tac) && beans.get(beans.size() - 1).getCid().equals(cid)) {//最后一条是最新插入的
                                liner_jzBj.setVisibility(View.VISIBLE);
                                dialog.setHeightRatio(0.53);
                                jBeans.clear();
                                jBeans.addAll(dbManager.getdemoBeanList());


                                //                                listManager = (ArrayList<JzbJBean>) dbManager.getdemoBeanList();
//                                Log.e("ylt", "Main2ListManager: "+listManager.toString());
                                //将保存的数据库条目发送给fragment
                                EventBus.getDefault().postSticky(new MessageEvent(13145, jBeans));
                                adapterJz.notifyDataSetChanged();
                                Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
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

    private EditText et_cid;//基站报警 cid
    private EditText et_tac;//基站报警 tac
    private ArrayList<JzbJBean> jBeans;
    private RecyclerAdapter_Jz adapterJz;

    private void setGmConfigs() {
        LDialog dialog = LDialog.newInstance(this, R.layout.pin_config_lte);
        dialog
                .setMaskValue(0.5f)
                .setWidthRatio(0.8)
                .setHeightRatio(0.33)
                .setBgColor(Color.WHITE)
                .setBgRadius(5) //圆角, 单位：dp
                .setBgRadius(5, 5, 0, 0) //圆角, 单位：dp
                .setBgRadiusPX(10) //圆角, 单位：px
                .setBgRadiusPX(10, 10, 10, 10) //圆角, 单位：px

                //4.设置弹框位置
//                .setGravity(Gravity.LEFT | Gravity.BOTTOM) //弹框位置
//                .setGravity(Gravity.LEFT, 0, 0) //弹框位置(偏移量)
                .setGravity(Gravity.CENTER, 0, 0)
                .setAnimations(LAnimationsType.SCALE)
                //5.2 自定义动画
                .setAnimationsStyle(R.style.li_dialog_default)
                .setOnTouchOutside(true)
                .show();
        CheckBox checkbox_yd = dialog.getView(R.id.checkbox_yd);
        CheckBox checkbox_lt = dialog.getView(R.id.checkbox_lt);
        CheckBox checkbox_dx = dialog.getView(R.id.checkbox_dx);
        CheckBox checkbox_jzBaoJ = dialog.getView(R.id.checkbox_jzBaoJ);//基站报警声音选中状态
        EditText ed_jzTime = dialog.getView(R.id.ed_jzTime);//基站信息显示持续时间
        //保存
        SharedPreferences name = getSharedPreferences("name", Context.MODE_PRIVATE);
        isJzBj = name.getBoolean("checkbox_jzBaoJ", true);
        jzMessage = name.getInt("ed_jzTime", 300);
        isYd = name.getBoolean("checkbox4_yd", true);
        isLt = name.getBoolean("checkbox4_lt", true);
        isDx = name.getBoolean("checkbox4_dx", true);
        //监听
        checkbox_yd.setOnCheckedChangeListener(this);
        checkbox_lt.setOnCheckedChangeListener(this);
        checkbox_dx.setOnCheckedChangeListener(this);
        checkbox_jzBaoJ.setOnCheckedChangeListener(this);
        //进入popw时的上次记录
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
        //dialog隐藏
        dialog.getView(R.id.bt_Ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //步骤1：创建一个SharedPreferences对象
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //步骤2： 实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();


                jzMessage = Integer.parseInt(ed_jzTime.getText().toString());

                isJzBj = checkbox_jzBaoJ.isChecked();

                //3：将获取过来的值放入文件
                editor.putBoolean("checkbox_jzBaoJ", isJzBj);
                editor.putInt("ed_jzTime", jzMessage);
                editor.putBoolean("checkbox4_yd", isYd);
                editor.putBoolean("checkbox4_lt", isLt);
                editor.putBoolean("checkbox4_dx", isDx);
                //4：提交
                editor.commit();
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //步骤1：创建一个SharedPreferences对象
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //步骤2： 实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();


                jzMessage = Integer.parseInt(ed_jzTime.getText().toString());

                isJzBj = checkbox_jzBaoJ.isChecked();

                //3：将获取过来的值放入文件
                editor.putBoolean("checkbox_jzBaoJ", isJzBj);
                editor.putInt("ed_jzTime", jzMessage);
                editor.putBoolean("checkbox4_yd", isYd);
                editor.putBoolean("checkbox4_lt", isLt);
                editor.putBoolean("checkbox4_dx", isDx);
                //4：提交
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
        binder.getService().setCallback(data -> {//因为在Service里面赋值data是在Thread中进行的，所以我们不能直接在这里将返回的值展示在TextView上。
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
        if(servic!=null){
            unbindService(servic);
        }
    }

    /**
     * 设置背景颜色
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
     * 申请权限
     */
    public void getPermissions() {//获取手机权限
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    long mPressedTime = 0;

    @Override
    public void onBackPressed() {//重写返回键方法
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        Log.d("nzq", "onBackPressed: " + mNowTime);
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            ToastUtils.showToast("再按一次退出程序");
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }

    public String sendMsg = stop;//切换工模界面默认发送的命令
    //订阅Event
    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onEvent(MessageEvent event) {
        if (event.getCode() == 3030) {//监听网络状态
            boolean data = (Boolean) event.getData();
            Message message = Message.obtain();
            message.obj = data;
            message.what = 3030;
            handler.sendMessage(message);
        }
        if (event.getCode() == 2022) {//工模4、5G页面切换状态
            sendMsg = event.getData().toString();
            socketServerListenHandler.sendMessage(sendMsg);//工模界面下发指令
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.checkbox_yd: {
                //步骤1：创建一个SharedPreferences对象
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //步骤2： 实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox4_yd", isChecked);
                editor.commit();
                isYd = isChecked;
//                //将最新状态返回给fragment
//                setGmConfigSettings.setGmConfig(isYd, isLt, isDx, isJzBj, jzMessage);
                break;
            }
            case R.id.checkbox_lt: {
                //步骤1：创建一个SharedPreferences对象
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //步骤2： 实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox4_lt", isChecked);
                editor.commit();
                isLt = isChecked;
//                //将最新状态返回给fragment
//                setGmConfigSettings.setGmConfig(isYd, isLt, isDx, isJzBj, jzMessage);
                break;
            }
            case R.id.checkbox_dx: {
                //步骤1：创建一个SharedPreferences对象
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                //步骤2： 实例化SharedPreferences.Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("checkbox4_dx", isChecked);
                editor.commit();
                isDx = isChecked;
//                //将最新状态返回给fragment
//                setGmConfigSettings.setGmConfig(isYd, isLt, isDx, isJzBj, jzMessage);
                break;
            }
            case R.id.checkbox_jzBaoJ: {
                if(!isChecked){//未选中 发送停止报警声音
                    EventBus.getDefault().postSticky(new MessageEvent<>(3033,""));
                }
                isJzBj = isChecked;
            }
        }
    }

    NetChangeReceiver.NetChangeListener listener = status -> {//发送网络监听广播
        EventBus.getDefault().postSticky(new MessageEvent(5500, status));
    };
}
