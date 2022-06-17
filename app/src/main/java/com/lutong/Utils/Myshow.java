package com.lutong.Utils;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.UiSettings;
import com.lutong.Constant.Constant;
import com.lutong.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.lutong.OrmSqlLite.Bean.JzGetData;
import com.lutong.OrmSqlLite.DBManagerJZ;
import com.lutong.R;
import com.lutong.activity.Adapter.SerrnAdapter;
import com.lutong.fragment.adapter.AddCallBack;
import com.lutong.fragment.adapter.DemoAdapteradd;
import com.lutong.fragment.adapter.Mycallback;
import com.lutong.fragment.adapter.ScreenCallBack;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.SwitchView;


public class Myshow {
    private static SerrnAdapter serrnAdapter;
    public static boolean xunFlags = false;
    private static int business;
    public static int types = 0;

    public static int show(final Context context, final Bundle extraInfo, final DBManagerJZ dbManagerJZ, final Mycallback mycallback, AddCallBack addcallBack, final List<Double> listMarker, final DemoAdapteradd demoAdapter) {
        //底部弹出窗

        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow2, null);
        //初始化控件
        final EditText editText = inflate.findViewById(R.id.et_ta);
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        final RecyclerView recyclerView = inflate.findViewById(R.id.recylerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        demoAdapter = new DemoAdapteradd(listMarker, addcallBack);
        recyclerView.setAdapter(demoAdapter);
        Button btadd = inflate.findViewById(R.id.btadd);
        final DemoAdapteradd finalDemoAdapter = demoAdapter;
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    MyToast.showToast("输入不能为空");
//                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
//                if (et_ta.getText().length() > 3) {
//                    Toast.makeText(MainActivity.this, "输入格式不正确", Toast.LENGTH_LONG).show();
//                    return;
//                }
                listMarker.add(Double.parseDouble(editText.getText().toString()));
                Log.d(TAG, "onClick: listMarker" + listMarker.size());
//                mAdapter.notifyDataSetChanged();
                finalDemoAdapter.notifyDataSetChanged();

//                aaa
            }
        });

        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    if (listMarker.size() > 0) {
                        GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
                        d.setBand(extraInfo.get("band") + "");
                        d.setPci(extraInfo.get("pci") + "");
                        d.setDown(extraInfo.get("down") + "");
                        d.setTypes(extraInfo.get("types") + "");
                        d.setId(1);
                        d.setAddress(extraInfo.getString("address"));
                        d.setCi(extraInfo.getString("ci"));
                        d.setLac(extraInfo.getString("lac"));
                        d.setMcc(extraInfo.getString("mcc"));
                        d.setMnc(extraInfo.getString("mnc"));
                        d.setRadius(extraInfo.getString("Radius"));
                        d.setTa(MyUtils.listToString(listMarker));
                        d.setType(0);
                        d.setResources(extraInfo.getString("resources"));
                        d.setLat(String.valueOf(extraInfo.getString("lat")));
                        d.setLon(String.valueOf(extraInfo.getString("lon")));
                        try {
                            int i = dbManagerJZ.insertStudent(d);
                            Log.d(TAG, "insertonClick: " + i);
                            dialog2.dismiss();
//                            Toast.makeText(AppLication.getInstance().getApplicationContext(), "增加成功", Toast.LENGTH_LONG).show();
                            MyToast.showToast("增加成功");
                            mycallback.call();
//            initdatas();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Log.d(TAG, "insertonClickerro: " + e.getMessage());
                        }
                    } else {
                        MyToast.showToast("请添加至少一个TA值");
//                        Toast.makeText(context, "请添加ta值", Toast.LENGTH_LONG).show();
                    }


                } else {
                    MyToast.showToast("TA值不能为空");
//                    Toast.makeText(AppLication.getInstance().getApplicationContext(), "TA值不能为空",
//                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //将布局设置给Dialog
        dialog2.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog2.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog2.show();//显示对话框

        return 1;
    }

    public static void jizhanShow(final Context context, final Mycallback mycallback, boolean b, int BUSINESS, final LinearLayout ll_screen, final BaiduMap mBaiduMap, final UiSettings uiSettings) {
        //底部弹出窗
        final ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();
        list.add("移动");
        list.add("联通");

//        list.add("O型");
//        list.add("AB型");
//        list.add("其他");
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow3, null);
        final SwitchView switchView = inflate.findViewById(R.id.switchviews);
        switchView.setOpened(b);//巡视开关
        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOpened = switchView.isOpened();
                Log.d(TAG, "switchViewonClick: " + isOpened);
                if (isOpened == true) {
                    ll_screen.setVisibility(View.VISIBLE);
                    mBaiduMap.setMaxAndMinZoomLevel(21, 19);
//
                    //直接缩放至缩放级别16
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(19));
                } else {
                    ll_screen.setVisibility(View.GONE);
//
                    mBaiduMap.setMaxAndMinZoomLevel(21, 5);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));//设置固定的比例尺
                    mycallback.DataShow(true);
                }
                xunFlags = isOpened;
            }
        });
        RadioGroup rg_main = inflate.findViewById(R.id.rg_main);
        final RadioButton b1 = inflate.findViewById(R.id.rb_yidong);
        final RadioButton b2 = inflate.findViewById(R.id.rb_liantong);
        if (BUSINESS == 0) {
            b1.setChecked(true);
        }
        if (BUSINESS == 1) {
            b2.setChecked(true);
        }
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d(TAG, "onCheckedChanged: " + i);
                if (i == b1.getId()) {
                    Log.d(TAG, "onCheckedChangedb1: " + i);
                    types = 0;
                    mycallback.isCheck(xunFlags, types);
                }
                if (i == b2.getId()) {
                    Log.d(TAG, "onCheckedChangedb2: " + i);
                    types = 1;
                    mycallback.isCheck(xunFlags, types);
                }
            }
        });
        final EditText editText = inflate.findViewById(R.id.et_ta);
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });


        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mycallback.isCheck(xunFlags, types);
                dialog2.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog2.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog2.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog2.show();//显示对话框

    }

    public static int taShow(final Context context, final Mycallback mycallback, boolean b, int bs) {
        //底部弹出窗

        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow3, null);
        //初始化控件

        final EditText editText = inflate.findViewById(R.id.et_ta);
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        final SwitchView switchView = inflate.findViewById(R.id.switchviews);
        switchView.setOpened(b);
        switchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isOpened = switchView.isOpened();
                Log.d(TAG, "switchViewonClick: " + isOpened);
                xunFlags = isOpened;
            }
        });

        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mycallback.isCheck(xunFlags, 1);
                dialog2.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog2.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog2.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog2.show();//显示对话框
        return business;
    }

    //TA圈设置
    public static int ShowTa(final Context context, boolean maxta, boolean minta, boolean uniformta, final Callshow callshow, boolean fugaiFlag) {
        //底部弹出窗
        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow7, null);
        //初始化控件

        final EditText editText = inflate.findViewById(R.id.et_ta);
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        //最大
        final SwitchView max_switchviews = inflate.findViewById(R.id.max_switchviews);
        max_switchviews.setOpened(maxta);
        max_switchviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.MAXTA = max_switchviews.isOpened();
                callshow.call();

            }
        });
        //最小
        final SwitchView min_switchviews = inflate.findViewById(R.id.min_switchviews);
        min_switchviews.setOpened(minta);
        min_switchviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.MINTA = min_switchviews.isOpened();
                callshow.call();
            }
        });
        //平均
        final SwitchView uniform_switchviews = inflate.findViewById(R.id.uniform_switchviews);
        uniform_switchviews.setOpened(uniformta);
        uniform_switchviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.UNIFORMTA = uniform_switchviews.isOpened();
                callshow.call();
            }
        });

        SwitchView fugai_switchviews = inflate.findViewById(R.id.fugai_switchviews);
        fugai_switchviews.setOpened(fugaiFlag);
        fugai_switchviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callshow.callfg();
            }
        });
        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog2.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog2.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog2.show();//显示对话框
        return business;
    }

    //滑动屏幕弹出窗 从内部服务器获取的数据
    public static void Showscreen(final Context context, JzGetData jzGetData, ScreenCallBack screenCallBack) {
        //底部弹出窗

        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow8, null);
        //初始化控件
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        Button bt_adddilao = new Button(context);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        RecyclerView recyclerView = inflate.findViewById(R.id.ry);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Integer> listnum = new ArrayList<>();
        for (int i = 1; i < jzGetData.getData().size() + 1; i++) {
            listnum.add(i);
        }
        serrnAdapter = new SerrnAdapter(context, jzGetData, listnum, dialog2, screenCallBack);
        recyclerView.setAdapter(serrnAdapter);
        Button button = inflate.findViewById(R.id.bt_adddilao);
        button.setText("关闭");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        //将布局设置给Dialog
        dialog2.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog2.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog2.show();//显示对话框

    }


    public static int showSerrnTa(final Context context, final Bundle extraInfo, final DBManagerJZ dbManagerJZ, final Mycallback mycallback, AddCallBack addcallBack, final List<Double> listMarker, final DemoAdapteradd demoAdapter) {
        //底部弹出窗

        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow2, null);
        //初始化控件
        final EditText editText = inflate.findViewById(R.id.et_ta);
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        final RecyclerView recyclerView = inflate.findViewById(R.id.recylerview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        demoAdapter = new DemoAdapteradd(listMarker, addcallBack);
        recyclerView.setAdapter(demoAdapter);
        Button btadd = inflate.findViewById(R.id.btadd);
        final DemoAdapteradd finalDemoAdapter = demoAdapter;
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    MyToast.showToast("输入不能为空");
//                    Toast.makeText(context, "输入不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
//                if (et_ta.getText().length() > 3) {
//                    Toast.makeText(MainActivity.this, "输入格式不正确", Toast.LENGTH_LONG).show();
//                    return;
//                }
                listMarker.add(Double.parseDouble(editText.getText().toString()));
                Log.d(TAG, "onClick: listMarker" + listMarker.size());
//                mAdapter.notifyDataSetChanged();
                finalDemoAdapter.notifyDataSetChanged();

//                aaa
            }
        });

        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    if (listMarker.size() > 0) {
                        GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
                        d.setId(1);
                        d.setAddress(extraInfo.getString("address"));
                        d.setCi(extraInfo.getString("ci"));
                        d.setLac(extraInfo.getString("lac"));
                        d.setMcc(extraInfo.getString("mcc"));
                        d.setMnc(extraInfo.getString("mnc"));
                        d.setRadius(extraInfo.getString("Radius"));
                        d.setTa(MyUtils.listToString(listMarker));
                        d.setType(0);
                        d.setLat(String.valueOf(extraInfo.getString("lat")));
                        d.setLon(String.valueOf(extraInfo.getString("lon")));
                        try {
                            int i = dbManagerJZ.insertStudent(d);
                            Log.d(TAG, "insertonClick: " + i);
                            dialog2.dismiss();
                            MyToast.showToast("增加成功");
//                            Toast.makeText(AppLication.getInstance().getApplicationContext(), "增加成功", Toast.LENGTH_LONG).show();
                            mycallback.call();
//            initdatas();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Log.d(TAG, "insertonClickerro: " + e.getMessage());
                        }
                    } else {
                        MyToast.showToast("请添加至少一个TA值");
//                        Toast.makeText(context, "请添加ta值", Toast.LENGTH_LONG).show();
                    }


                } else {
                    MyToast.showToast("TA值不能为空");
//                    Toast.makeText(AppLication.getInstance().getApplicationContext(), "TA值不能为空",
//                            Toast.LENGTH_LONG).show();
                }
            }
        });

        //将布局设置给Dialog
        dialog2.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog2.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog2.show();//显示对话框

        return 1;
    }

    public void is() {
        String ipint = "1,2,3";
        String[] split = ipint.split(",");
//

    }

    public interface Callshow {
        void call();

        void callfg();
    }
}
