package com.lutong.PinConfig.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.lutong.OrmSqlLite.Bean.PinConfigBean;
import com.lutong.OrmSqlLite.DBManagerPinConfig;
import com.lutong.PinConfig.Adapter.PinConfigViewPagerAdapter;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.base.BaseFragmentOld;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lutong.AppContext.context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by admin on 2019/12/31.
 */
@SuppressLint("ValidFragment")
public class PinConfigFragment1 extends BaseFragmentOld implements View.OnClickListener{
    private String name;
    View view;
    PinConfigViewPagerAdapter pinConfigAdapter;
    RecyclerView ry;
    /**
     * 数据库操作
     */
    //用于底部弹出窗
    private Dialog dialog;
    private View inflate;
    private Button bt_adddilao, bt_canel;
    private ImageView iv_show_finish;
    private EditText et_up_ping, et_down_ping, et_plmn, et_band;
    private String spinnerYY = "";
    private String spinnerTF = "";
    private Spinner sp_yy, sp_tf;
    private TextView titles;
    int sizeinit;//用來判斷是否添加成功的
    private DBManagerPinConfig dbManager;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100001) {//无线正确
                    setAdapter();
            }
        }
    };
    PinConfigViewPagerAdapter.IDialogPinConfig dialogPinConfig = new PinConfigViewPagerAdapter.IDialogPinConfig() {
        @Override
        public void getPosition(String position, int index, String linename) {

        }

        @Override
        public void deleID(int deleid) {
            setAdapter();
        }


        public void up(PinConfigBean pinConfigBean) {

        }

        @Override
        public void upData() {

        }


        public void upbianji(PinConfigBean pinConfigBean) {
            showDialog(true, pinConfigBean);
        }
    };

    @SuppressLint("ValidFragment")
    public PinConfigFragment1(String name) {
        this.name = name;

    }


    public View initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.activity_pin_config_viewpager_fragment, null);
        return view;
    }

    @Override
    public void initData() {
        findviews();
        setAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        if (isVisibleToUser) {
            // 相当于onResume()方法
            Log.d("nzq", "onResume: " + "执行了1");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("test", "0");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    message.what = 100001;
                }
            }, 100);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("test", "0");
                    message.setData(bundle);
                    handler.sendMessage(message);
                    message.what = 100001;
                }
            }, 100);

        }
    }
    private void setAdapter() {
        try {
            dbManager = new DBManagerPinConfig(mContext);
            List<PinConfigBean> student = dbManager.getStudent();
            List<PinConfigBean> pinConfigBeanList = new ArrayList<>();
            if (student.size() > 0 && student != null) {
                for (int i = 0; i < student.size(); i++) {
                    if (student.get(i).getTf().equals("FDD") && student.get(i).getYy().equals("移动")) {
                        pinConfigBeanList.add(student.get(i));
                    }
                }
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < pinConfigBeanList.size(); i++) {
                    list.add(i);
                }
                pinConfigAdapter = new PinConfigViewPagerAdapter(mContext, pinConfigBeanList, dialogPinConfig,list);
                ry.setAdapter(pinConfigAdapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findviews() {
        ry = view.findViewById(R.id.ry);
        ry.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }
    /**
     * 底部弹出窗
     */
    private void showDialog(boolean b, PinConfigBean pinConfigBean) {
        if (b) {
            dialog = new Dialog(mContext, R.style.ActionSheetDialogStyleDialog);
//            //填充对话框的布局
            inflate = LayoutInflater.from(mContext).inflate(R.layout.configlist_dibushow, null);
//            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            titles = inflate.findViewById(R.id.titles);
            titles.setText("配置频点");
            bt_adddilao.setText("确认");
            bt_adddilao.setOnClickListener(new MySetUpdate(pinConfigBean, pinConfigBean.getId(), pinConfigBean.getType()));
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_up_ping.setText(pinConfigBean.getUp() + "");
            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            et_down_ping.setText(pinConfigBean.getDown() + "");
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
            et_plmn.setText(pinConfigBean.getPlmn());
            et_band = inflate.findViewById(R.id.et_band);//波段
            et_band.setText(pinConfigBean.getBand() + "");
            sp_yy = inflate.findViewById(R.id.sp_yy);
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(this);
            String yy = pinConfigBean.getYy();
            Log.d("pinConfigBeanaa", "showDialog: " + yy);
            List<String> list1 = new ArrayList<>();
            list1.add("移动");
            list1.add("联通");
            list1.add("电信");
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext, R.layout.spinner_item
                    , list1);


            sp_yy.setAdapter(adapter1);
//            sp_yy.setSelection(2);
            if (!TextUtils.isEmpty(yy)) {
                if (yy.equals("移动")) {
                    sp_yy.setSelection(0);
                }
                if (yy.equals("联通")) {
                    sp_yy.setSelection(1);
                }
                if (yy.equals("电信")) {
                    sp_yy.setSelection(2);
                }
            }
            sp_yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerYY = adapter1.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            String tf = pinConfigBean.getTf();
            Log.d("pinConfigBeanaa", "showDialog: " + tf);
            List<String> list2 = new ArrayList<>();
            list2.add("TDD");
            list2.add("FDD");
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext, R.layout.spinner_item
                    , list2);


            sp_tf.setAdapter(adapter2);
//            sp_yy.setSelection(2);
            if (!TextUtils.isEmpty(tf)) {
                if (yy.equals("TDD")) {
                    sp_tf.setSelection(0);
                }
                if (tf.equals("FDD")) {
                    sp_tf.setSelection(1);
                }
            }
            sp_tf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerTF = adapter2.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            dialog.setCanceledOnTouchOutside(false);
////            et_yy.setText(pinConfigBean.getYy());
//            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        } else {
            dialog = new Dialog(mContext, R.style.ActionSheetDialogStyleDialog);
            //填充对话框的布局
            inflate = LayoutInflater.from(mContext).inflate(R.layout.configlist_dibushow, null);
            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            bt_adddilao.setOnClickListener(this);
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD   下拉框
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
            et_band = inflate.findViewById(R.id.et_band);//波段
            sp_yy = inflate.findViewById(R.id.sp_yy);//运营商   下拉框
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(this);
            List<String> list1 = new ArrayList<>();
            list1.add("移动");
            list1.add("联通");
            list1.add("电信");
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list1);
            sp_yy.setAdapter(adapter1);
            sp_yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerYY = adapter1.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            List<String> list2 = new ArrayList<>();
            list2.add("TDD");
            list2.add("FDD");
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list2);
            sp_tf.setAdapter(adapter2);
            sp_tf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerTF = adapter2.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            dialog.setCanceledOnTouchOutside(false);
            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_canel:

                dialog.cancel();
                break;
        }

    }

    class MySetUpdate implements View.OnClickListener {
        private int type;
        private int id;
        PinConfigBean pinConfigBean;

        public MySetUpdate(PinConfigBean pinConfigBean, int id, int type) {
            this.pinConfigBean = pinConfigBean;
            this.id = id;
            this.type = type;
        }

        @Override
        public void onClick(View view) {
            if (isnull()) return;
            PinConfigBean pinConfigBeanss = new PinConfigBean();
            pinConfigBeanss.setUp(Integer.parseInt(et_up_ping.getText().toString()));
            pinConfigBeanss.setDown(Integer.parseInt(et_down_ping.getText().toString()));
            pinConfigBeanss.setTf(spinnerTF);
            pinConfigBeanss.setPlmn(et_plmn.getText().toString());
            pinConfigBeanss.setBand(Integer.parseInt(et_band.getText().toString()));
            pinConfigBeanss.setId(id);
            pinConfigBeanss.setType(type);
            pinConfigBeanss.setYy(spinnerYY);
            if (isError()) return;
            try {
                int i = dbManager.updateData(pinConfigBeanss);
                Log.d("nzq", "updateDataonClick: " + i);
//                queryStudent();
                setAdapter();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }
    /**
     * 判断频点输出
     * @return
     */
    private boolean isError() {
        //判断运营商
        if (spinnerYY.equals("移动") && !et_plmn.getText().toString().equals("46000")) {

            ToastUtils.showToast("移动运营商PLMN应为46000");
            return true;
        }
        if (spinnerYY.equals("联通") && !et_plmn.getText().toString().equals("46001")) {

            ToastUtils.showToast("联通运营商PLMN应为46001");
            return true;
        }
        if (spinnerYY.equals("电信") && !et_plmn.getText().toString().equals("46011")) {

            ToastUtils.showToast("电信运营商PLMN应为46011");
            return true;
        }
        //波段1
        if (et_band.getText().toString().equals("1")) {
            if (!spinnerTF.equals("FDD")) {
                ToastUtils.showToast("BAND1应为FDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) < 18000 || Integer.parseInt(et_up_ping.getText().toString()) > 18599) {
                ToastUtils.showToast("BAND1上行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 0 || Integer.parseInt(et_down_ping.getText().toString()) > 599) {
                ToastUtils.showToast("BAND1下行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString())-Integer.parseInt(et_down_ping.getText().toString())!=18000) {
                ToastUtils.showToast("上、下行频点间隔不为18000");
                return true;
            }
        }
        //波段3
        if (et_band.getText().toString().equals("3")) {
            if (!spinnerTF.equals("FDD")) {
                ToastUtils.showToast("BAND3应为FDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) < 19200 || Integer.parseInt(et_up_ping.getText().toString()) > 19949) {
                ToastUtils.showToast("BAND3上行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 1200 || Integer.parseInt(et_down_ping.getText().toString()) > 1949) {
                ToastUtils.showToast("BAND3下行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString())-Integer.parseInt(et_down_ping.getText().toString())!=18000) {
                ToastUtils.showToast("上、下行频点间隔不为18000");
                return true;
            }
        }
        //波段5
        if (et_band.getText().toString().equals("5")) {
            if (!spinnerTF.equals("FDD")) {
                ToastUtils.showToast("BAND5应为FDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) < 20400 || Integer.parseInt(et_up_ping.getText().toString()) > 20649) {
                ToastUtils.showToast("BAND5上行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 2400 || Integer.parseInt(et_down_ping.getText().toString()) > 2649) {
                ToastUtils.showToast("BAND5下行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString())-Integer.parseInt(et_down_ping.getText().toString())!=18000) {
                ToastUtils.showToast("上、下行频点间隔不为18000");
                return true;
            }
        }
        //波段8
        if (et_band.getText().toString().equals("8")) {
            if (!spinnerTF.equals("FDD")) {
                ToastUtils.showToast("BAND8应为FDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) < 21450 || Integer.parseInt(et_up_ping.getText().toString()) > 21799) {
                ToastUtils.showToast("BAND8上行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 3450 || Integer.parseInt(et_down_ping.getText().toString()) > 3799) {
                ToastUtils.showToast("BAND8下行频点设置错误");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString())-Integer.parseInt(et_down_ping.getText().toString())!=18000) {
                ToastUtils.showToast("上、下行频点间隔不为18000");
                return true;
            }
        }
        //波段34
        if (et_band.getText().toString().equals("24")) {
            if (!spinnerTF.equals("TDD")) {
                ToastUtils.showToast("BAND34应为TDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) !=255) {
                ToastUtils.showToast("上行频点应为255");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 36200 || Integer.parseInt(et_down_ping.getText().toString()) > 36349) {
                ToastUtils.showToast("BAND34下行频点设置错误");
                return true;
            }

        }
        //波段38
        if (et_band.getText().toString().equals("38")) {
            if (!spinnerTF.equals("TDD")) {
                ToastUtils.showToast("BAND38应为TDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) !=255) {
                ToastUtils.showToast("上行频点应为255");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 37750 || Integer.parseInt(et_down_ping.getText().toString()) > 38249) {
                ToastUtils.showToast("BAND38下行频点设置错误");
                return true;
            }

        }
        //波段39
        if (et_band.getText().toString().equals("39")) {
            if (!spinnerTF.equals("TDD")) {
                ToastUtils.showToast("BAND39应为TDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) !=255) {
                ToastUtils.showToast("上行频点应为255");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 38250 || Integer.parseInt(et_down_ping.getText().toString()) > 38649) {
                ToastUtils.showToast("BAND39下行频点设置错误");
                return true;
            }

        }
        //波段40
        if (et_band.getText().toString().equals("40")) {
            if (!spinnerTF.equals("TDD")) {
                ToastUtils.showToast("BAND40应为TDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) !=255) {
                ToastUtils.showToast("上行频点应为255");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 38650 || Integer.parseInt(et_down_ping.getText().toString()) > 39649) {
                ToastUtils.showToast("BAND40下行频点设置错误");
                return true;
            }

        }
        //波段41
        if (et_band.getText().toString().equals("41")) {
            if (!spinnerTF.equals("TDD")) {
                ToastUtils.showToast("BAND41应为TDD");
                return true;
            }
            if (Integer.parseInt(et_up_ping.getText().toString()) !=255) {
                ToastUtils.showToast("上行频点应为255");
                return true;
            }
            if (Integer.parseInt(et_down_ping.getText().toString()) < 39650 || Integer.parseInt(et_down_ping.getText().toString()) > 41589) {
                ToastUtils.showToast("BAND41下行频点设置错误");
                return true;
            }

        }
        return false;
    }
    private boolean isnull() {
        if (TextUtils.isEmpty(et_up_ping.getText().toString())) {
            Toast.makeText(mContext, "上行频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(et_down_ping.getText().toString())) {
            Toast.makeText(mContext, "下行频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

//        if (TextUtils.isEmpty(et_tf.getText().toString())) {
//            Toast.makeText(this, "TDD/FDD不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if (TextUtils.isEmpty(et_band.getText().toString())) {
            Toast.makeText(mContext, "频段不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(et_plmn.getText().toString())) {
            Toast.makeText(mContext, "PLMN不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}