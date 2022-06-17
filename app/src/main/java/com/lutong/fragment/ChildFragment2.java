package com.lutong.fragment;

import static com.lutong.Constant.Constant.DOWNPIN1;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lutong.App.MessageEvent;
import com.lutong.R;
import com.lutong.Service.MyService;
import com.lutong.Utils.ACacheUtil;
import com.lutong.Utils.DbUtils;
import com.lutong.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ChildFragment2 extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnFocusChangeListener {
    private MyService.Binder binder = null;
    CheckBox cb_jl_sd, cb_jl_zd;//建立方式
    CheckBox cb_cell1, cb_cell2;//小区基带板选择
    EditText et_tac1, et_pci1, et_eci1;//小区1参数
    EditText et_tac2, et_pci2, et_eci2;//小区2参数
    Spinner spinner1, spinner2;
    TextView tvtype1, tvtype2, tf1, tf2;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.child_fragment, null);
        initView(view);

        return view;
    }

    TextView tv; //测试id
    TextView tv_down1;

    public void initView(View view) {
        EventBus.getDefault().register(this);
        tv = (TextView) view.findViewById(R.id.tv_childfragment_name);
        tv.setText("Child_Fragment2");
        tv_down1 = view.findViewById(R.id.tv_down1);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        tvtype1 = view.findViewById(R.id.tv1_titletype);
        tvtype2 = view.findViewById(R.id.tv2_titletype);
        tf1 = view.findViewById(R.id.tf1);
        tf2 = view.findViewById(R.id.tf2);
        cb_jl_sd = view.findViewById(R.id.cb_jl_sd);
        cb_jl_zd = view.findViewById(R.id.cb_jl_zd);
        cb_cell1 = view.findViewById(R.id.cb_cell1);
        cb_cell2 = view.findViewById(R.id.cb_cell2);
        cb_cell1.setOnCheckedChangeListener(this);
        cb_cell2.setOnCheckedChangeListener(this);
        cb_jl_zd.setOnCheckedChangeListener(this);
        cb_jl_sd.setOnCheckedChangeListener(this);
        //
        //小区1参数
        et_tac1 = view.findViewById(R.id.et_tac1);
        et_pci1 = view.findViewById(R.id.et_pci1);
        et_eci1 = view.findViewById(R.id.et_eci1);
        et_tac1.setOnFocusChangeListener(this);
        et_pci1.setOnFocusChangeListener(this);
        et_eci1.setOnFocusChangeListener(this);
        //小区2参数
        et_tac2 = view.findViewById(R.id.et_tac2);
        et_pci2 = view.findViewById(R.id.et_pci2);
        et_eci2 = view.findViewById(R.id.et_eci2);
        et_tac2.setOnFocusChangeListener(this);
        et_pci2.setOnFocusChangeListener(this);
        et_eci2.setOnFocusChangeListener(this);
//        et_tac1.set
//        Intent intent = new Intent(getActivity(), MyService.class);
//        getActivity().bindService(intent, servic, Context.BIND_AUTO_CREATE);
        getdataset_Ed();//小区设置
        //判断手动自动建立小区
        if (!TextUtils.isEmpty(ACacheUtil.getZD())) {
            if (ACacheUtil.getZD().equals("1")) {
                cb_jl_zd.setChecked(true);
                cb_jl_sd.setChecked(false);
            } else {
                cb_jl_zd.setChecked(false);
                cb_jl_sd.setChecked(true);
            }
        } else {
            ACacheUtil.putZD("0");
            cb_jl_sd.setChecked(true);
            cb_jl_zd.setChecked(false);
        }
        /**
         * 下拉框4G设备数据
         */

        /**
         * 下拉框设备5G设备数据
         */

        setSpinnerData();
    }

    private void setSpinnerData() {


        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item
                , DbUtils.getSpinnerData(getContext()));
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_item
                , DbUtils.getSpinnerData5G(getContext()));
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ACacheUtil.putSpinner1(adapter1.getItem(i));
                Log.d("spinner1", "onItemSelected: " + ACacheUtil.getSpinner1());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                spinnerYY = adapter1.getItem(i);//下拉框1选择的数据
                Log.d("spinner2", "onItemSelected: " + adapter2.getItem(i));
                ACacheUtil.putSpinner2(adapter2.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getdataset_Ed() {
        DbUtils.setCellData(getContext(), et_tac1, et_pci1, et_eci1, et_tac2, et_pci2, et_eci2, cb_cell1, cb_cell2);
    }


    ServiceConnection servic = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.Binder) service;
            binder.getService().setCallback(new MyService.CallBack() {
                @Override
                public void onDataChanged(final String data) {//因为在Service里面赋值data是在Thread中进行的，所以我们不能直接在这里将返回的值展示在TextView上。
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("lcm", data);
                            tv.setText("data : " + data);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onStop() {
        super.onStop();
//        getActivity().unbindService(servic);
        Log.e("lcm", "onStop");
    }


    @Override
    public void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("lcm", "onDestroy");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        String data = (String) event.getData();
//        tv.setText(data);
        switch (event.getCode()) {
            case 100110:
                tf1.setText("双工:" + event.getData());
                break;
            case 100120:
                tvtype1.setText("状态:" + event.getData());
                if (event.getData().equals("定位中")) {

                    spinner1.setVisibility(View.GONE);
                    tv_down1.setVisibility(View.VISIBLE);
                    tv_down1.setText("" + DOWNPIN1);
                }
                if (event.getData().equals("就绪")) {

                    spinner1.setVisibility(View.VISIBLE);
                    tv_down1.setVisibility(View.GONE);
                    tv_down1.setText("");
                }
                break;
            case 200110:
                tf2.setText("双工:" + event.getData());
                break;
            case 200120:
//                tvtype2.setText("状态:" + event.getData());
                break;
            case 5101:
                tvtype2.setText("状态:" + event.getData());

                break;

        }
    }

    /**
     * 所有复选框点击事件
     *
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_cell1:
                DbUtils.xiaoquCB(getContext(), 1, isChecked);//设置小区1选中
                break;
            case R.id.cb_cell2:
                DbUtils.xiaoquCB(getContext(), 2, isChecked);//设置小区2选中
                break;
            case R.id.cb_jl_sd://手动建立
                if (isChecked) {
                    ACacheUtil.putZD("0");
                    cb_jl_zd.setChecked(false);
                    cb_cell1.setChecked(false);//选择手动建立 两个小区都不选中
                    cb_cell2.setChecked(false);
                    DbUtils.xiaoquCB(getContext(), 1, false);//设置小区1选中
                    DbUtils.xiaoquCB(getContext(), 2, false);//设置小区2选中
                    cb_cell1.setClickable(true);//可选中
                    cb_cell2.setClickable(true);
                    spinner1.setEnabled(true);//下拉框可选
                    spinner2.setEnabled(true);
                    setSpinnerData();
                }
                if (!isChecked) {
                    cb_jl_zd.setChecked(true);
                    ACacheUtil.putZD("1");
                    cb_cell1.setChecked(true);//选择自动建立 两个小区都选中
                    cb_cell2.setChecked(true);
                    DbUtils.xiaoquCB(getContext(), 1, true);//设置小区1选中
                    DbUtils.xiaoquCB(getContext(), 2, true);//设置小区2选中
                    cb_cell1.setClickable(false);//不可选中
                    cb_cell2.setClickable(false);
                    spinner1.setEnabled(false);//下拉框不可选
                    spinner2.setEnabled(false);
                    setSpinnerData();
                }
                break;
            case R.id.cb_jl_zd://自动建立
                if (isChecked) {
                    ACacheUtil.putZD("1");
                    cb_jl_sd.setChecked(false);
                }
                if (!isChecked) {
                    ACacheUtil.putZD("0");
                    cb_jl_sd.setChecked(true);
                }
                break;
        }
    }

    /**
     * 失去焦点的事件
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_tac1:
                DbUtils.setTac(getContext(), 1, et_tac1);
                break;
            case R.id.et_pci1:
                DbUtils.setPci(getContext(), 1, et_pci1);
                break;
            case R.id.et_eci1:
                DbUtils.setCid(getContext(), 1, et_eci1);
                break;
            case R.id.et_tac2:
                DbUtils.setTac(getContext(), 2, et_tac2);
                break;
            case R.id.et_pci2:
                DbUtils.setPci(getContext(), 2, et_pci2);
                break;
            case R.id.et_eci2:
                DbUtils.setCid(getContext(), 2, et_eci2);
                break;
        }
    }

    ;
}
