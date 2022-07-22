package com.lutong.fragment.home;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.lutong.App.MessageEvent;
import com.lutong.Service.MyService;
import com.lutong.Utils.MyToast;
import com.lutong.adapter.ViewPageFragmentAdapter;
import com.lutong.base.BaseViewPagerFragment;
import com.lutong.base.OnTabReselectListener;
import com.lutong.fragment.ChildFragment1;
import com.lutong.fragment.ChildFragment2;
import com.lutong.fragment.ChildFragment3;
import com.lutong.fragment.ChildFragment4;

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
 * @time 2022/5/7 17:57
 */
public class ViewPagerFragment1 extends BaseViewPagerFragment implements
        OnTabReselectListener {

    private MyService.Binder binder = null;
    ViewPageFragmentAdapter adapter;

    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = {"目标IMSI", "小区设置", "定位", "侦码"};
        this.adapter = adapter;
        adapter.addTab(title[0], "tag_1", ChildFragment1.class, null);
        adapter.addTab(title[1], "tag_2", ChildFragment2.class, null);
        adapter.addTab(title[2], "tag_3", ChildFragment3.class, null);
        adapter.addTab(title[3], "tag_4", ChildFragment4.class, null);
        int item1 = adapter.getItem1();

        Log.d("TAGTabAdapter", "onSetupTabAdapter: " + item1);

    }

    @Override
    protected void setScreenPageLimit() {
        // TODO Auto-generated method stub
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onClick(View v) {

    }

    ChildFragment1 childFragment1;

    @Override
    public void initView(View view) {
        //注册Event事件
        EventBus.getDefault().register(this);
        Fragment fragment = adapter.getItem(0);
        if (fragment instanceof ChildFragment1) {
            childFragment1 = (ChildFragment1) fragment;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void initData() {
    }

    @Override
    public void onTabReselect() {
        try {
            int currentIndex = mViewPager.getCurrentItem();
            Log.e("yltopo", "onTabReselect: "+currentIndex );
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
//        getActivity().unbindService(servic);
        Log.e("lcm", "onStop");
    }

    public interface ComponentCallBack {
        void onDataChanged(String data);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
        //订阅Event
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(MessageEvent event) {
        if(event.getCode()==1682){
            MyToast.showToast("我是定位");
        }
    }
}
