package com.lutong.SaoPin.SaopinList;

import android.app.Dialog;


import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.lutong.OrmSqlLite.Bean.SaopinBean;
import com.lutong.OrmSqlLite.DBManagersaopin;
import com.lutong.R;
import com.lutong.SaoPin.Fragment.Fragment0;
import com.lutong.SaoPin.Fragment.Fragment1;
import com.lutong.SaoPin.Fragment.Fragment2;
import com.lutong.SaoPin.Fragment.Fragment3;
import com.lutong.SaoPin.SaopinIT;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;
import com.lutong.base.BaseFragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备信息
 */
public class SaoPinSetingActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout layout;
    TextView title;
    ImageView iv_finish, iv_add, imageView;
    Button button0, button1, button2, button3;
    ViewPager viewpage;
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
    private Spinner sp_yy, sp_tf;
    private TextView titles;
    private Button bt_fdd, bt_tdd, bt_lt, bt_ds;
    private CheckBox cb_all;//全选按钮
    private TextView tv_choose;//已选择的数量
    int sizeinit;//用來判斷是否添加成功的



    SaopinIT saopinIT=new SaopinIT() {
        @Override
        public void Callback() {

        }
    };

    @Override
    protected void initQx() {

    }

    @Override
    protected void init() throws UnsupportedEncodingException {
//        setStatBar();//去导航栏
        UtilsView.setViewVisibility(this, layout, title, imageView, getResources().getString(R.string.saopinlistActivity), false, iv_finish, true);
    }

    /**
     * findview
     * 按钮监听
     */
    @Override
    protected void findViews() {
        iv_add = findViewById(R.id.iv_add);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(SaoPinSetingActivity.this, R.style.ActionSheetDialogStyleDialog);
                //填充对话框的布局
                inflate = LayoutInflater.from(SaoPinSetingActivity.this).inflate(R.layout.saopinlist_dibushow, null);
                //初始化控件
                bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
//                bt_adddilao.setOnClickListener(this);
                bt_adddilao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("bt_adddilao", "onClick: ");
                        ViewisEmpty();
                    }
                });
                iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
                iv_show_finish.setOnClickListener(this);
                et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
                et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
                sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD   下拉框
                et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
                et_band = inflate.findViewById(R.id.et_band);//波段
                sp_yy = inflate.findViewById(R.id.sp_yy);//运营商   下拉框
                bt_canel = inflate.findViewById(R.id.bt_canel);
                bt_canel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });

//
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
        });
        layout = findViewById(R.id.ll_tab);
        title = findViewById(R.id.titles);
        imageView = findViewById(R.id.iv_menu);
        iv_finish = findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {//关闭
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        viewpage = findViewById(R.id.viewpager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.getItem(0);
        viewpage.setAdapter(myFragmentPagerAdapter);
        viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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

//            case R.id.bt_adddilao:
//                ToastUtils.showToast("111");
//                Log.d("bt_adddilao", "onClick: ");
//                //底部弹出窗确认添加按钮
////                ViewisEmpty();
//                break;
        }
    }

    /**
     * Edit非空判断
     */
    private void ViewisEmpty() {
        if (isnull()) return;
        //配置新频点数据插入 SQL
        Log.d("iiii", "ViewisEmpty: ");
        exeSql();
    }

    private void exeSql() {
        savepin();
    }

    private boolean isnull() {
//        if (TextUtils.isEmpty(et_up_ping.getText().toString())) {
//            Toast.makeText(this, "上行频点不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if (TextUtils.isEmpty(et_down_ping.getText().toString())) {
            Toast.makeText(SaoPinSetingActivity.this, "下行频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

//        if (TextUtils.isEmpty(et_tf.getText().toString())) {
//            Toast.makeText(this, "TDD/FDD不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (TextUtils.isEmpty(et_band.getText().toString())) {
//            Toast.makeText(this, "频段不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (TextUtils.isEmpty(et_plmn.getText().toString())) {
//            Toast.makeText(this, "PLMN不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        return false;
    }

    private void savepin() {
        SaopinBean saopinBean = new SaopinBean();
//        saopinBean.setBand(Integer.parseInt(et_band.getText().toString()));
        saopinBean.setDown(Integer.parseInt(et_down_ping.getText().toString()));
//        saopinBean.setPlmn(et_plmn.getText().toString());
//        saopinBean.setType(0);
        saopinBean.setUp(Integer.parseInt(et_up_ping.getText().toString()));
//        saopinBean.setTf(spinnerTF);
        if (type == 0) {
            saopinBean.setTf("TDD");
            saopinBean.setYy("移动TDD");
        }
        if (type == 1) {
            saopinBean.setTf("FDD");
            saopinBean.setYy("移动FDD");
        }
        if (type == 2) {
            saopinBean.setTf("FDD");
            saopinBean.setYy("联通");
        }
        if (type == 3) {
            saopinBean.setTf("FDD");
            saopinBean.setYy("电信");
        }
        try {
            DBManagersaopin dbManagersaopin = new DBManagersaopin(SaoPinSetingActivity.this);
            int i = dbManagersaopin.insertStudent(saopinBean);
            if (i == 1) {
                Log.d("nan保存的数据", "===" + saopinBean);
//                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("添加成功");
                MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
                viewpage.setAdapter(myFragmentPagerAdapter);
                if (type==0){
                    viewpage.setCurrentItem(0);
                }
                if (type==1){
                    viewpage.setCurrentItem(1);
                }
                if (type==2){
                    viewpage.setCurrentItem(2);
                }
                if (type==3){
                    viewpage.setCurrentItem(4);
                }

                dialog.dismiss();

                //查询所以数据
//                setadapter();
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
     * 轮播图适配器
     */
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> fragments = new ArrayList<>();
        private String[] titles = {//
                "第一页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
                "第二页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度",//
                "第三页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度", //
                "第四页\n\n重点看下面的的图标是渐变色，随着滑动距离的增加，颜色逐渐过度"};

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new Fragment0("移动TDD"));
            fragments.add(new Fragment1("移动FDD"));
            fragments.add(new Fragment2("联通"));
            fragments.add(new Fragment3("电信"));
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
    protected int getView() {
        int activity_device_info = R.layout.activity_saopin_info;
        return activity_device_info;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
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
}