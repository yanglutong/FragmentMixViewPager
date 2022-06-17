//package com.lutong.fragment.home_fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.lutong.R;
//import com.lutong.Utils.TitleBar;
//import com.lutong.base.BaseFragment;
//import com.lutong.fragment.viewpager.JigPagerAdapter;
//
//import java.util.ArrayList;
//
///**主界面3 工模
// * @description
// * @param
// * @return
// * @author lutong
// * @time 2022/5/7 17:57
// */
//public class FragmentGm extends BaseFragment implements View.OnClickListener {
//	private Context mContext;
//	private View view;//布局
//	private static final String TAG = "FragmentGm";
//	private ViewPager vp;
//	private ImageView iv_settings;//标题栏 设置按钮
//	private TextView tv_title;//标题栏 头部标题
//	private TitleBar titleBar;//标题栏
//	private ImageView iv_scrollbar1,iv_scrollbar2,iv_scrollbar3;//下划线
//	private TextView tv_Device,tv_SimI1,tv_SimI2;//卡槽字体
//	private RelativeLayout re_simI1,re_simI2,re_device;//卡槽界面
//	private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();;//用来装载Fragment
//	private static  int position=0;//viewpager滑动的下标
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		super.onCreateView(inflater, container, savedInstanceState);
//		View view = inflater.inflate(R.layout.fragment_gij, null);
//		mContext = getActivity();
//		initView(view);
//		return view;
//	}
//
//	public void initView(View view) {
////		TextView tv = (TextView) view.findViewById(R.id.tv_fragment_name);
////		tv.setText("Fragment2");
//		titleBar = view.findViewById(R.id.title);
//		tv_title = titleBar.findViewById(R.id.tv_title);
//		iv_settings = titleBar.findViewById(R.id.iv_settings);
//		vp =view. findViewById(R.id.Vp);
//		re_device = view.findViewById(R.id.re_Device);
//		re_simI1 = view.findViewById(R.id.re_SimI1);
//		re_simI2 = view.findViewById(R.id.re_SimI2);
//		iv_scrollbar1 = view.findViewById(R.id.iv_scrollbar1);
//		iv_scrollbar2 = view.findViewById(R.id.iv_scrollbar2);
//		iv_scrollbar3 =view. findViewById(R.id.iv_scrollbar3);
//		tv_SimI1 =view. findViewById(R.id.tv_SimI1);
//		tv_SimI2 = view.findViewById(R.id.tv_SimI2);
//		tv_Device = view.findViewById(R.id.tv_Device);
//
//
//
//
//		tv_title.setOnClickListener(this);//标题监听
//		iv_settings.setOnClickListener(this);//选项监听
//		re_simI1.setOnClickListener(this);//卡槽1监听
//		re_simI1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				iv_scrollbar1.setVisibility(View.VISIBLE);
//				iv_scrollbar2.setVisibility(View.GONE);
//				iv_scrollbar3.setVisibility(View.GONE);
//
//				vp.setCurrentItem(0);//设置当前ViewPager的条目
//				position=0;
//			}
//		});
//		re_simI2.setOnClickListener(this);//卡槽2监听
//		re_simI2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//点击卡槽2将其他的按钮下划线隐藏
//				iv_scrollbar2.setVisibility(View.VISIBLE);
//				iv_scrollbar1.setVisibility(View.GONE);
//				iv_scrollbar3.setVisibility(View.GONE);
//
//				vp.setCurrentItem(1);//设置当前ViewPager的条目
//			position=1;
//			}
//		});
//		re_device.setOnClickListener(this);//设备监听
//
//
//
//		setDataVpAndButton();//ViewPager和Button联动
//	}
//	private void setDataVpAndButton() {
//		//创建Fragment
//		fragmentArrayList.add(new GijFragment());
//		fragmentArrayList.add(new GijFragment());
//		vp.setAdapter(new JigPagerAdapter(getChildFragmentManager(),fragmentArrayList));
//		//默认卡槽1选中状态
//		iv_scrollbar2.setVisibility(View.GONE);
//		iv_scrollbar3.setVisibility(View.GONE);
//		tv_SimI1.setTextColor(getResources().getColor(R.color.colorJigTextColor));
//		vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//			@Override
//			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//				//ViewPager 这个方法会在屏幕滚动过程中不断被调用
//			}
//			@Override
//			public void onPageSelected(int position) {
//				FragmentGm.this.position=position;
//				//代表哪个页面被选中  position下标
//				if(position==0){
//					//设置字体颜色
//					tv_SimI1.setTextColor(getResources().getColor(R.color.colorJigTextColor));
//					tv_SimI2.setTextColor(getResources().getColor(R.color.colorJigTextColorCancel));
//					tv_Device.setTextColor(getResources().getColor(R.color.colorJigTextColorCancel));
//					//下划线显示隐藏
//					iv_scrollbar1.setVisibility(View.VISIBLE);
//					iv_scrollbar2.setVisibility(View.GONE);
//					iv_scrollbar3.setVisibility(View.GONE);
//				}else if(position==1){
//					//设置字体颜色
//					tv_SimI1.setTextColor(getResources().getColor(R.color.colorJigTextColorCancel));
//					tv_SimI2.setTextColor(getResources().getColor(R.color.colorJigTextColor));
//					tv_Device.setTextColor(getResources().getColor(R.color.colorJigTextColorCancel));
//					//下划线隐藏
//					iv_scrollbar2.setVisibility(View.VISIBLE);
//					iv_scrollbar1.setVisibility(View.GONE);
//					iv_scrollbar3.setVisibility(View.GONE);
//				}else if(position==2){
//					//设置字体颜色
//					tv_SimI1.setTextColor(getResources().getColor(R.color.colorJigTextColorCancel));
//					tv_SimI2.setTextColor(getResources().getColor(R.color.colorJigTextColorCancel));
//					tv_Device.setTextColor(getResources().getColor(R.color.colorJigTextColor));
//					//下划线隐藏
//					iv_scrollbar3.setVisibility(View.VISIBLE);
//					iv_scrollbar1.setVisibility(View.GONE);
//					iv_scrollbar2.setVisibility(View.GONE);
//				}
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int state) {
//				//这个方法在手指操作屏幕的时候发生变化  手指按下为1 抬起时滑动了为2
//			}
//		});
//	}
//}
package com.lutong.fragment.home_fragment;

import static com.lutong.Constants.page;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.lutong.App.MessageEvent;
import com.lutong.Constants;
import com.lutong.Utils.MyToast;
import com.lutong.adapter.ViewPageFragmentAdapter;
import com.lutong.base.BaseViewPagerFragment;
import com.lutong.base.OnTabReselectListener;
import com.lutong.fragment.GijChildFragment4;
import com.lutong.fragment.GijChildFragment5;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 主界面2
 *
 * @param
 * @author lutong
 * @description
 * @return
 * @time 2022/5/7 17:57f
 */
public class FragmentGm extends BaseViewPagerFragment implements
        OnTabReselectListener {
    ViewPageFragmentAdapter adapter;

    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = {"4G基站", "5G基站"};
        this.adapter = adapter;
        adapter.addTab(title[0], "tag_1", GijChildFragment4.class, null);
        adapter.addTab(title[1], "tag_2", GijChildFragment5.class, null);
        int item1 = adapter.getItem1();
        Log.d("TAGTabAdapter", "onSetupTabAdapter: " + item1);
    }

    @Override
    protected void setScreenPageLimit() {
        // TODO Auto-generated method stub
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onClick(View v) {

    }

    GijChildFragment4 childFragment4;

    @Override
    public void initView(View view) {
        Fragment fragment = adapter.getItem(0);
        if (fragment instanceof GijChildFragment4) {
            childFragment4 = (GijChildFragment4) fragment;
        }
        //注册Event
        EventBus.getDefault().register(this);
    }


    @Override
    public void onResume() {
        super.onResume();
//        Log.d("TAGcount", "initView: "+adapter.getItem1());
    }


    @Override
    public void initData() {
    }

    @Override
    public void onTabReselect() {
        try {
            int currentIndex = mViewPager.getCurrentItem();
            Fragment currentFragment = getChildFragmentManager().getFragments()
                    .get(currentIndex);
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
            }
        } catch (NullPointerException e) {


        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //订阅Event
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(MessageEvent event) {
        if(event.getCode()==1681){
            MyToast.showToast("我是工模");
        }
    }
}

