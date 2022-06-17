package com.lutong.Device;


import android.os.Build;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lutong.R;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;
import com.lutong.base.BaseFragment;
import com.lutong.Constant.Constant;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 设备信息
 */
public class DeviceInfoActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout layout;
    TextView title;
    ImageView iv_finish, iv_add, imageView;
    Button button0, button1, button2, button3;
    ViewPager viewpage;
    List<Button> buttonList = new ArrayList<>();

    @Override
    protected void initQx() {

    }

    @Override
    protected void init() throws UnsupportedEncodingException {
//        setStatBar();//去导航栏
        UtilsView.setViewVisibility(this, layout, title, imageView, getResources().getString(R.string.deviceinfo), false, iv_finish, true);
    }

    /**
     * findview
     * 按钮监听
     */
    @Override
    protected void findViews() {
        iv_add=findViewById(R.id.iv_add);
        iv_add.setVisibility(View.GONE);
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
                    return;
                }
                if (i == 1) {
                    setBackground(buttonList, 1);
                    return;

                }
                if (i == 2) {
                    setBackground(buttonList, 2);
                    return;

                }
                if (i == 3) {
                    setBackground(buttonList, 3);
                    return;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.d("nzq", "onPageScrollStateChanged: " + i);
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
            fragments.add(new TextFragment0());
            fragments.add(new TextFragment1());
            fragments.add(new TextFragment2());
            fragments.add(new TextFragment3());
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
        int activity_device_info = R.layout.activity_device_info;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //设备信息的变量------
        //设备号
        Constant.DEVICENUMBER1 = "";
        Constant.DEVICENUMBER2 = "";
        //硬件版本号
        Constant.HARDWAREVERSION1 = "";
        Constant.HARDWAREVERSION2 = "";
        //软件版本
        Constant.SOFTWAREVERSION1 = "";
        Constant.SOFTWAREVERSION2 = "";
        //SN号
        Constant.SNNUMBER1 = "";
        Constant.SNNUMBER2 = "";
        //MAC地址
        Constant.MACADDRESS1 = "";
        Constant.MACADDRESS2 = "";
        //UBOOT 版本号
        Constant.UBOOTVERSION1 = "";
        Constant.UBOOTVERSION2 = "";
        //板卡温度
        Constant.BOARDTEMPERATURE1 = "";
        Constant.BOARDTEMPERATURE2 = "";
        //当前小区信息状态响应
        Constant.PLMN1 = "";
        Constant.PLMN2 = "";
        Constant.UP1 = "";
        Constant.UP2 = "";
        Constant.DWON1 = "";
        Constant.DWON2 = "";
        Constant.BAND1 = "";
        Constant.BAND2 = "";
        Constant.DK1 = "";
        Constant.DK2 = "";
        Constant.TAC1 = "";
        Constant.TAC2 = "";
        Constant.PCI1 = "";
        Constant.PCI2 = "";
        Constant.CELLID1 = "";
        Constant.CELLID2 = "";
        Constant.DBM1 = "";
        Constant.DBM2 = "";
        Constant.TYPE1 = "";
        Constant.TYPE2 = "";
        //定位模式
        Constant.GZMS1 = "";//工作模式
        Constant.ZHZQ1 = "";//抓号周期
        Constant.UEIMSI = "";//UEIMSO
        Constant.SBZQ1 = "";//测量值上报周期
        Constant.UEMAXOF1 = "";//ue最大功率发设开关
        Constant.UEMAX1 = "";//ue最大发设功率

        Constant.GZMS2 = "";//工作模式
        Constant.ZHZQ2 = "";//抓号周期
        Constant.UEIMS2 = "";//UEIMSO
        Constant.SBZQ2 = "";//测量值上报周期
        Constant.UEMAXOF2 = "";//ue最大功率发设开关
        Constant.UEMAX2 = "";//ue最大发设功率

        //接受增益和衰减
        Constant.ZENGYI1 = "";
        Constant.SHUAIJIAN1 = "";
        Constant.ZENGYI2 = "";
        Constant.SHUAIJIAN2 = "";
    }
}
