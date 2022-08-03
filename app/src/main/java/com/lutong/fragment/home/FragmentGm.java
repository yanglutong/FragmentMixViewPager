
package com.lutong.fragment.home;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lutong.App.MessageEvent;
import com.lutong.R;
import com.lutong.Utils.MyToast;
import com.lutong.adapter.ViewPageFragmentAdapter;
import com.lutong.base.BaseViewPagerFragment;
import com.lutong.base.OnTabReselectListener;
import com.lutong.fragment.GijChildFragment4;
import com.lutong.fragment.GijChildFragment5;
import com.lutong.widget.PagerSlidingTabStrip;

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


        View child = mTabStrip.getChild(1);
        TextView textView = child.findViewById(R.id.tab_title);
        textView.setTextColor(Color.parseColor("#202020"));


        View child2 = mTabStrip.getChild(0);
        TextView textView2 = child2.findViewById(R.id.tab_title);
        textView2.setTextColor(Color.parseColor("#2f8dff"));

        mTabStrip.setOnClickTabListener(new PagerSlidingTabStrip.OnClickTabListener() {
            @Override
            public void onClickTab(View tab, int index) {
//                View child = mPagerStrip.getChildAt(index);
//				TextView textView = child.findViewById(R.id.tab_title);
//				Toast.makeText(mContext, "child"+textView.getText().toString(), Toast.LENGTH_SHORT).show();
////                TextView view = tab.findViewById(R.id.tab_title);
////                view.setTextColor(Color.parseColor("#2f8dff"));
            }
        });
        mTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    View child = mTabStrip.getChild(1);
                    TextView textView = child.findViewById(R.id.tab_title);
                    textView.setTextColor(Color.parseColor("#202020"));


                    View child2 = mTabStrip.getChild(0);
                    TextView textView2 = child2.findViewById(R.id.tab_title);
                    textView2.setTextColor(Color.parseColor("#2f8dff"));

                }else if(position == 1){
                    View child = mTabStrip.getChild(0);
                    TextView textView = child.findViewById(R.id.tab_title);
                    textView.setTextColor(Color.parseColor("#202020"));


                    View child2 = mTabStrip.getChild(1);
                    TextView textView2 = child2.findViewById(R.id.tab_title);
                    textView2.setTextColor(Color.parseColor("#2f8dff"));
                }




            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

