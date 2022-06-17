package com.lutong.PinConfig;

import static com.lutong.AppContext.context;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.SQLException;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.lutong.OrmSqlLite.Bean.PinConfigBean;
import com.lutong.OrmSqlLite.Bean.PinConfigBean5G;
import com.lutong.OrmSqlLite.DBManagerPinConfig5G;
import com.lutong.PinConfig.fragment.PinConfigFragment05G;
import com.lutong.PinConfig.fragment.PinConfigFragment15G;
import com.lutong.PinConfig.fragment.PinConfigFragment25G;
import com.lutong.PinConfig.fragment.PinConfigFragment35G;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;
import com.lutong.base.BaseFragmentOld;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class PinConfigViewPagerActivity5G extends BaseActivity implements View.OnClickListener {
    LinearLayout layout;
    TextView title;
    ImageView iv_finish, iv_add, imageView;
    ViewPager viewpage;
    Button button0, button1, button2, button3;
    List<Button> buttonList = new ArrayList<>();
    private int type = 0;
    //用于底部弹出窗
    private Dialog dialog;
    private View inflate;
    private Button bt_adddilao, bt_canel;
    private ImageView iv_show_finish;
    private EditText et_up_ping, et_down_ping, et_plmn, et_band;
    private String spinnerYY = "";
    private String spinnerTF = "";

    Spinner sp_band;
    private String spinnerBand = "";
    EditText et_zhongxinpindian;
    EditText et_ssb;
    private Spinner sp_yy, sp_tf;
    private TextView titles;
    int sizeinit;//用來判斷是否添加成功的
    private DBManagerPinConfig5G dbManager;

    @Override
    protected void initQx() {

    }

    @Override
    protected void init() throws UnsupportedEncodingException {
//        setStatBar();//去导航栏
        UtilsView.setViewVisibility(this, layout, title, imageView, getResources().getString(R.string.pin_activity5g), false, iv_finish, true);
        setViewpagerData();
        //初始化DBManager
        try {
            dbManager = new DBManagerPinConfig5G(this);
//            dbManager.deletequery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    private void setViewpagerData() {
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.getItem(0);
        viewpage.setAdapter(myFragmentPagerAdapter);
        viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("viewpagercount", "findViews: " + i);
                if (i == 0) {
                    setBackground(buttonList, 0);
                    type = 0;
                    return;
                }
                if (i == 1) {
                    setBackground(buttonList, 1);
                    type = 1;
                    return;

                }
                if (i == 2) {
                    setBackground(buttonList, 2);
                    type = 2;
                    return;

                }
                if (i == 3) {
                    setBackground(buttonList, 3);
                    type = 3;
                    return;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    public void setBackground(List<Button> buttonList, int i) {
        for (int j = 0; j < buttonList.size(); j++) {
            if (i == 0) {
                button0.setBackground(getResources().getDrawable(R.mipmap.duan));
                button0.setTextColor(getResources().getColor(R.color.white));
            } else {
                buttonList.get(j).setBackground(null);
                buttonList.get(j).setTextColor(getResources().getColor(R.color.black));
            }
            if (i == 1) {
                button1.setBackground(getResources().getDrawable(R.mipmap.duan));
                button1.setTextColor(getResources().getColor(R.color.white));
            } else {
                buttonList.get(j).setBackground(null);
                buttonList.get(j).setTextColor(getResources().getColor(R.color.black));
            }
            if (i == 2) {
                button2.setBackground(getResources().getDrawable(R.mipmap.duan));
                button2.setTextColor(getResources().getColor(R.color.white));
            } else {
                buttonList.get(j).setBackground(null);
                buttonList.get(j).setTextColor(getResources().getColor(R.color.black));
            }
            if (i == 3) {
                button3.setBackground(getResources().getDrawable(R.mipmap.duan));
                button3.setTextColor(getResources().getColor(R.color.white));
            } else {
                buttonList.get(j).setBackground(null);
                buttonList.get(j).setTextColor(getResources().getColor(R.color.black));
            }
        }

    }

    @Override
    protected void findViews() {
        iv_add = findViewById(R.id.iv_add);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(this);
        title = findViewById(R.id.titles);
        imageView = findViewById(R.id.iv_menu);
        iv_finish = findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        layout = findViewById(R.id.ll_tab);
        viewpage = findViewById(R.id.viewpager);
        button0 = findViewById(R.id.bt_0);
        button1 = findViewById(R.id.bt_1);
        button2 = findViewById(R.id.bt_2);
        button3 = findViewById(R.id.bt_3);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        buttonList.add(button0);
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);

    }

    @Override
    protected int getView() {
        return R.layout.activity_pin_config_viewpager;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 轮播图适配器
     */
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragmentOld> fragments = new ArrayList<>();
        private String[] titles = {//
                "第一页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
                "第二页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
                "第三页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度", //
                "第四页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度"};

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new PinConfigFragment05G("移动TDD"));
            fragments.add(new PinConfigFragment15G("移动FDD"));
            fragments.add(new PinConfigFragment25G("联通"));
            fragments.add(new PinConfigFragment35G("电信"));
        }


        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                finish();
                break;

            case R.id.iv_add:
//                ToastUtils.showToast("111");
                PinConfigBean pinConfigBean = new PinConfigBean();
                showDialog2(false, pinConfigBean);
                break;

            case R.id.bt_adddilao:

                ViewisEmpty();
                break;
            case R.id.bt_canel:
                dialog.dismiss();
                break;

            case R.id.bt_0:
                viewpage.setCurrentItem(0);
                break;
            case R.id.bt_1:
                viewpage.setCurrentItem(1);
                break;
            case R.id.bt_2:
                viewpage.setCurrentItem(2);
                break;
            case R.id.bt_3:
                viewpage.setCurrentItem(3);
                break;

        }
    }

    /**
     * 底部弹出窗
     */
    private void showDialog(boolean b, PinConfigBean pinConfigBean) {
        if (b) {
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
//            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow, null);
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
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item
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
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item
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
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow, null);
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

    /**
     * 底部弹出窗
     */
    @SuppressLint("SetTextI18n")
    private void showDialog2(boolean b, PinConfigBean pinConfigBean) {
        if (b) {//编辑频点
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
//            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow5g, null);
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
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item
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
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item
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
        } else {//添加新频点
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow5g, null);
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
            et_zhongxinpindian = inflate.findViewById(R.id.et_zhongxinpindian);
            et_ssb = inflate.findViewById(R.id.et_ssb);
            sp_yy = inflate.findViewById(R.id.sp_yy);//运营商   下拉框
            sp_band = inflate.findViewById(R.id.sp_band);
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(this);
            List<String> list3 = new ArrayList<>();
            list3.add("41");
            list3.add("77");
            list3.add("78");
            list3.add("79");
            list3.add("1");
            list3.add("28");
            final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list3);
            sp_band.setAdapter(adapter3);
            sp_band.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerBand = adapter3.getItem(i);//下拉框1选择的数据
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
                    Log.d("spinnerYY", "onItemSelected: " + spinnerYY);
                    if (spinnerYY.equals("移动")) {
                        et_plmn.setText("46000");
                    }
                    if (spinnerYY.equals("联通")) {
                        et_plmn.setText("46001");
                    }
                    if (spinnerYY.equals("电信")) {
                        et_plmn.setText("46011");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
//            if (TextUtils.isEmpty(et_plmn.getText().toString())) {
//                ToastUtils.showToast("PLMN不能为空");
//                return;
//            }
//            if (TextUtils.isEmpty(et_zhongxinpindian.getText().toString())) {
//                ToastUtils.showToast("中心频点不能为空");
//                return;
//            }
//            if (TextUtils.isEmpty(et_ssb.getText().toString())) {
//                ToastUtils.showToast("SSB频点不能为空");
//                return;
//            }
//            et_ssb.setText("504990");
//            long ssbarfcn = Integer.parseInt(et_ssb.getText().toString());
////            sp_band
//            int sum = 504990;
//            Log.d("TAGsum", "showDialog2: " + et_ssb.getText().toString());
//            List<Integer> mt = new ArrayList<>();
//            mt.add(1);
//            mt.add(3);
//            mt.add(5);
//            int GSCN = -1;
//
//            if (spinnerBand.equals("41")) {
//                Log.d("TAGssbarfcn", "showDialog2: " + ssbarfcn);
//
//                for (int i : mt) {
//                    int M = 0;
//                    long N = 0;
//
//                    long yushu = (ssbarfcn * 5 - i * 50) % 1200;
//                    if (yushu == 0) {
//                        M = i;
//                        N = (ssbarfcn * 5 - i * 50) / 1200;
//                        GSCN = (int) (3 * N + (M - 3) / 2);
//                        break;
//                    }
//
//                }
//            } else if (spinnerBand.equals("77") || spinnerBand.equals("78") || spinnerBand.equals("79")) {
////                Log.d("627264--::", (7499 + (ssbarfcn - 600000) * 15.0 / 1000 / 1.44) + "");
//                GSCN = (int) (7499 + (ssbarfcn - 600000) * 15.0 / 1000 / 1.44);
//            } else {
//                System.out.print("不支持频段");
//            }
//            System.out.print("算出的" + GSCN);

//            et_down_ping.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    //text  输入框中改变前的字符串信息
//                    //start 输入框中改变前的字符串的起始位置
//                    //count 输入框中改变前后的字符串改变数量一般为0
//                    //after 输入框中改变后的字符串与起始位置的偏移量
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    //text  输入框中改变后的字符串信息
//                    //start 输入框中改变后的字符串的起始位置
//                    //before 输入框中改变前的字符串的位置 默认为0
//                    //count 输入框中改变后的一共输入字符串的数量
//                }
//
//                //edit  输入结束呈现在输入框中的信息
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (!TextUtils.isEmpty(s.toString())) {
//                        String str = s.toString();
//                        Log.d("afterTextChanged", "afterTextChanged: /" + str + spinnerYY);
//                        if (spinnerYY.equals("移动")) {
//                            et_up_ping.setText("255");//移动上
//                            if (Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 599) {
//                                et_band.setText("1");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 1200 && Integer.parseInt(str) <= 1949) {
//                                et_band.setText("3");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 2400 && Integer.parseInt(str) <= 2649) {
//                                et_band.setText("5");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 3450 && Integer.parseInt(str) <= 3799) {
//                                et_band.setText("8");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 36200 && Integer.parseInt(str) <= 36349) {
//                                et_band.setText("34");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 37750 && Integer.parseInt(str) <= 38249) {
//                                et_band.setText("38");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 38250 && Integer.parseInt(str) <= 38649) {
//                                et_band.setText("39");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 38650 && Integer.parseInt(str) <= 39649) {
//                                et_band.setText("40");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 39650 && Integer.parseInt(str) <= 41589) {
//                                et_band.setText("41");
//                                sp_tf.setSelection(0);
//                            }
//                        }
//                        if (spinnerYY.equals("联通")) {
//                            et_up_ping.setText(Integer.parseInt(str) + 18000 + "");
//                            if (Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 599) {
//                                et_band.setText("1");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 1200 && Integer.parseInt(str) <= 1949) {
//                                et_band.setText("3");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 2400 && Integer.parseInt(str) <= 2649) {
//                                et_band.setText("5");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 3450 && Integer.parseInt(str) <= 3799) {
//                                et_band.setText("8");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 36200 && Integer.parseInt(str) <= 36349) {
//                                et_band.setText("34");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 37750 && Integer.parseInt(str) <= 38249) {
//                                et_band.setText("38");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 38250 && Integer.parseInt(str) <= 38649) {
//                                et_band.setText("39");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 38650 && Integer.parseInt(str) <= 39649) {
//                                et_band.setText("40");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 39650 && Integer.parseInt(str) <= 41589) {
//                                et_band.setText("41");
//                                sp_tf.setSelection(0);
//                            }
//                        }
//                        if (spinnerYY.equals("电信")) {
//                            et_up_ping.setText(Integer.parseInt(str) + 18000 + "");
//                            if (Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 599) {
//                                et_band.setText("1");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 1200 && Integer.parseInt(str) <= 1949) {
//                                et_band.setText("3");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 2400 && Integer.parseInt(str) <= 2649) {
//                                et_band.setText("5");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 3450 && Integer.parseInt(str) <= 3799) {
//                                et_band.setText("8");
//                                sp_tf.setSelection(1);
//                            }
//                            if (Integer.parseInt(str) >= 36200 && Integer.parseInt(str) <= 36349) {
//                                et_band.setText("34");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 37750 && Integer.parseInt(str) <= 38249) {
//                                et_band.setText("38");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 38250 && Integer.parseInt(str) <= 38649) {
//                                et_band.setText("39");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 38650 && Integer.parseInt(str) <= 39649) {
//                                et_band.setText("40");
//                                sp_tf.setSelection(0);
//                            }
//                            if (Integer.parseInt(str) >= 39650 && Integer.parseInt(str) <= 41589) {
//                                et_band.setText("41");
//                                sp_tf.setSelection(0);
//                            }
//                        }
//                    } else {
//                        et_band.setText("");
//
//
//                        et_up_ping.setText("");
//                    }
//
//                }
//            });


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

    /**
     * Edit非空判断
     */
    private void ViewisEmpty() {
        if (isnull()) return;
        //配置新频点数据插入 SQL
        Log.d("iiii", "ViewisEmpty: " + et_ssb.getText().toString());
        String s = et_ssb.getText().toString();
        String sr = "";
        try {
            List<PinConfigBean5G> studentlist = dbManager.getStudent();
            if (studentlist.size() > 0 && studentlist != null) {
                for (int i = 0; i < studentlist.size(); i++) {
                    if (s.equals(studentlist.get(i).getSsb() + "")) {
                        sr = "1";
                        break;
                    }
                }
                if (!TextUtils.isEmpty(sr)) {
                    ToastUtils.showToast("频点重复");
                    return;
                } else {
                    exeSql();
                }
            } else {
                exeSql();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exceptio", "ViewisEmpty: ");
        }


    }

    /**
     * 执行sql语句
     */
    private void exeSql() {

        savepin();
    }

    private void savepin() {
        PinConfigBean5G pinConfigBean = new PinConfigBean5G();
        pinConfigBean.setBand(Integer.parseInt(spinnerBand));
        pinConfigBean.setDown(0);
        pinConfigBean.setPlmn(et_plmn.getText().toString());
        pinConfigBean.setType(0);
        pinConfigBean.setUp(0);

        if (spinnerBand.equals("41")) {
            pinConfigBean.setTf("TDD");
        } else if (spinnerBand.equals("77")) {
            pinConfigBean.setTf("TDD");
        } else if (spinnerBand.equals("78")) {
            pinConfigBean.setTf("TDD");
        } else if (spinnerBand.equals("79")) {
            pinConfigBean.setTf("TDD");
        } else if (spinnerBand.equals("28")) {
            pinConfigBean.setTf("FDD");
        } else if (spinnerBand.equals("1")) {
            pinConfigBean.setTf("FDD");
        }

//        pinConfigBean.setTf(spinnerTF);
        pinConfigBean.setYy(spinnerYY);
        pinConfigBean.setSsb(et_ssb.getText().toString());
        pinConfigBean.setZhongxinpindian(et_zhongxinpindian.getText().toString());


        long ssbarfcn = Integer.parseInt(et_ssb.getText().toString());
//            sp_band
        int sum = 504990;
        Log.d("TAGsum", "showDialog2: " + et_ssb.getText().toString() + "spinnerBand===" + spinnerBand);
        List<Integer> mt = new ArrayList<>();
        mt.add(1);
        mt.add(3);
        mt.add(5);
        int GSCN = -1;

        if (spinnerBand.equals("41")) {
//            Log.d("TAGssbarfcn", "showDialog2: " + ssbarfcn);

            for (int i : mt) {
                int M = 0;
                long N = 0;

                long yushu = (ssbarfcn * 5 - i * 50L) % 1200;
                if (yushu == 0) {
                    M = i;
                    N = (ssbarfcn * 5 - i * 50L) / 1200;
                    GSCN = (int) (3 * N + (M - 3) / 2);
                    break;
                }

            }
        } else if (spinnerBand.equals("77") || spinnerBand.equals("78") || spinnerBand.equals("79")) {
//                Log.d("627264--::", (7499 + (ssbarfcn - 600000) * 15.0 / 1000 / 1.44) + "");
            GSCN = (int) (7499 + (ssbarfcn - 600000) * 15.0 / 1000 / 1.44);
        } else {
            System.out.print("不支持频段");
        }
        pinConfigBean.setGscn(GSCN);
        if (GSCN == -1) {
            ToastUtils.showToast("SSB频点设置错误");
            return;
        }
        Log.d("5G添加频点", "savepin: " + GSCN);
        if (isError()) return;
        try {
            int i = dbManager.insertStudent(pinConfigBean);
            if (i == 1) {
                Log.d("nan保存的数据", "===" + pinConfigBean);
//                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("添加成功");
                dialog.dismiss();
                //查询所以数据
//                initData();
                setViewpagerData();
            } else {
//                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("添加失败");
            }
//
        } catch (java.sql.SQLException e) {

            e.printStackTrace();
        }
    }

    /**
     * 判断频点输出
     *
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
        if (TextUtils.isEmpty(et_plmn.getText().toString())) {
            ToastUtils.showToast("PLMN不能为空");
            return true;
        }
        if (TextUtils.isEmpty(et_zhongxinpindian.getText().toString())) {
            ToastUtils.showToast("中心频点不能为空");
            return true;
        }
        if (TextUtils.isEmpty(et_ssb.getText().toString())) {
            ToastUtils.showToast("SSB频点不能为空");
            return true;
        }
//        //波段1
//        if (et_band.getText().toString().equals("1")) {
//            if (!spinnerTF.equals("FDD")) {
//                ToastUtils.showToast("BAND1应为FDD");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) < 18000 || Integer.parseInt(et_up_ping.getText().toString()) > 18599) {
//                ToastUtils.showToast("BAND1上行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 0 || Integer.parseInt(et_down_ping.getText().toString()) > 599) {
//                ToastUtils.showToast("BAND1下行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) - Integer.parseInt(et_down_ping.getText().toString()) != 18000) {
//                ToastUtils.showToast("上、下行频点间隔不为18000");
//                return true;
//            }
//        }
//        //波段3
//        if (et_band.getText().toString().equals("3")) {
//            if (!spinnerTF.equals("FDD")) {
//                ToastUtils.showToast("BAND3应为FDD");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) < 19200 || Integer.parseInt(et_up_ping.getText().toString()) > 19949) {
//                ToastUtils.showToast("BAND3上行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 1200 || Integer.parseInt(et_down_ping.getText().toString()) > 1949) {
//                ToastUtils.showToast("BAND3下行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) - Integer.parseInt(et_down_ping.getText().toString()) != 18000) {
//                ToastUtils.showToast("上、下行频点间隔不为18000");
//                return true;
//            }
//        }
//        //波段5
//        if (et_band.getText().toString().equals("5")) {
//            if (!spinnerTF.equals("FDD")) {
//                ToastUtils.showToast("BAND5应为FDD");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) < 20400 || Integer.parseInt(et_up_ping.getText().toString()) > 20649) {
//                ToastUtils.showToast("BAND5上行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 2400 || Integer.parseInt(et_down_ping.getText().toString()) > 2649) {
//                ToastUtils.showToast("BAND5下行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) - Integer.parseInt(et_down_ping.getText().toString()) != 18000) {
//                ToastUtils.showToast("上、下行频点间隔不为18000");
//                return true;
//            }
//        }
//        //波段8
//        if (et_band.getText().toString().equals("8")) {
//            if (!spinnerTF.equals("FDD")) {
//                ToastUtils.showToast("BAND8应为FDD");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) < 21450 || Integer.parseInt(et_up_ping.getText().toString()) > 21799) {
//                ToastUtils.showToast("BAND8上行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 3450 || Integer.parseInt(et_down_ping.getText().toString()) > 3799) {
//                ToastUtils.showToast("BAND8下行频点设置错误");
//                return true;
//            }
//            if (Integer.parseInt(et_up_ping.getText().toString()) - Integer.parseInt(et_down_ping.getText().toString()) != 18000) {
//                ToastUtils.showToast("上、下行频点间隔不为18000");
//                return true;
//            }
//        }
//        //波段34
//        if (et_band.getText().toString().equals("24")) {
//            if (!spinnerTF.equals("TDD")) {
//                ToastUtils.showToast("BAND34应为TDD");
//                return true;
//            }
////            if (Integer.parseInt(et_up_ping.getText().toString()) != 255) {
////                ToastUtils.showToast("上行频点应为255");
////                return true;
////            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 36200 || Integer.parseInt(et_down_ping.getText().toString()) > 36349) {
//                ToastUtils.showToast("BAND34下行频点设置错误");
//                return true;
//            }
//
//        }
//        //波段38
//        if (et_band.getText().toString().equals("38")) {
//            if (!spinnerTF.equals("TDD")) {
//                ToastUtils.showToast("BAND38应为TDD");
//                return true;
//            }
////            if (Integer.parseInt(et_up_ping.getText().toString()) != 255) {
////                ToastUtils.showToast("上行频点应为255");
////                return true;
////            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 37750 || Integer.parseInt(et_down_ping.getText().toString()) > 38249) {
//                ToastUtils.showToast("BAND38下行频点设置错误");
//                return true;
//            }
//
//        }
//        //波段39
//        if (et_band.getText().toString().equals("39")) {
//            if (!spinnerTF.equals("TDD")) {
//                ToastUtils.showToast("BAND39应为TDD");
//                return true;
//            }
////            if (Integer.parseInt(et_up_ping.getText().toString()) != 255) {
////                ToastUtils.showToast("上行频点应为255");
////                return true;
////            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 38250 || Integer.parseInt(et_down_ping.getText().toString()) > 38649) {
//                ToastUtils.showToast("BAND39下行频点设置错误");
//                return true;
//            }
//
//        }
//        //波段40
//        if (et_band.getText().toString().equals("40")) {
//            if (!spinnerTF.equals("TDD")) {
//                ToastUtils.showToast("BAND40应为TDD");
//                return true;
//            }
////            if (Integer.parseInt(et_up_ping.getText().toString()) != 255) {
////                ToastUtils.showToast("上行频点应为255");
////                return true;
////            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 38650 || Integer.parseInt(et_down_ping.getText().toString()) > 39649) {
//                ToastUtils.showToast("BAND40下行频点设置错误");
//                return true;
//            }
//
//        }
//        //波段41
//        if (et_band.getText().toString().equals("41")) {
//            if (!spinnerTF.equals("TDD")) {
//                ToastUtils.showToast("BAND41应为TDD");
//                return true;
//            }
////            if (Integer.parseInt(et_up_ping.getText().toString()) != 255) {
////                ToastUtils.showToast("上行频点应为255");
////                return true;
////            }
//            if (Integer.parseInt(et_down_ping.getText().toString()) < 39650 || Integer.parseInt(et_down_ping.getText().toString()) > 41589) {
//                ToastUtils.showToast("BAND41下行频点设置错误");
//                return true;
//            }
//
//        }
        return false;
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
            PinConfigBean5G pinConfigBeanss = new PinConfigBean5G();
            pinConfigBeanss.setUp(Integer.parseInt(et_up_ping.getText().toString()));
            pinConfigBeanss.setDown(Integer.parseInt(et_down_ping.getText().toString()));
            pinConfigBeanss.setTf(spinnerTF);
            pinConfigBeanss.setPlmn(et_plmn.getText().toString());
            pinConfigBeanss.setBand(Integer.parseInt(et_band.getText().toString()));
            pinConfigBeanss.setId(id);
            pinConfigBeanss.setType(type);
            pinConfigBeanss.setYy(spinnerYY);
            try {
                int i = dbManager.updateData(pinConfigBeanss);
                Log.d("nzq", "updateDataonClick: " + i);
//                queryStudent();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }

    private boolean isnull() {
//        if (TextUtils.isEmpty(et_up_ping.getText().toString())) {
//            Toast.makeText(this, "上行频点不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if (TextUtils.isEmpty(et_ssb.getText().toString())) {
            Toast.makeText(this, "SSB频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (TextUtils.isEmpty(et_zhongxinpindian.getText().toString())) {
            Toast.makeText(this, "中心频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(spinnerBand)) {
            Toast.makeText(this, "频段不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(et_plmn.getText().toString())) {
            Toast.makeText(this, "PLMN不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
