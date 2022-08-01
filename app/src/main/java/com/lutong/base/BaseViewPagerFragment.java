package com.lutong.base;

import com.lutong.R;
import com.lutong.adapter.ViewPageFragmentAdapter;
import com.lutong.widget.PagerSlidingTabStrip;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

/**
 * 带有导航条的基类
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年11月6日 下午4:59:50
 */
public abstract class BaseViewPagerFragment extends BaseFragment {

    protected PagerSlidingTabStrip mTabStrip;
    protected ViewPager mViewPager;
    protected ViewPageFragmentAdapter mTabsAdapter;

    // protected EmptyLayout mErrorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_viewpage_fragment, null);


        mTabStrip = (PagerSlidingTabStrip) view
                .findViewById(R.id.pager_tabstrip);



        mViewPager = (ViewPager) view.findViewById(R.id.pager);
//		mViewPager.setOffscreenPageLimit(4);
        // mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);

        mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
                mTabStrip, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
        int count = mTabsAdapter.getItem1();
        Log.d("TAG1", "onCreateView: "+count);
        initView(view);
        return view;
    }

//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//		mTabStrip = (PagerSlidingTabStrip) view
//				.findViewById(R.id.pager_tabstrip);
//
//		mViewPager = (ViewPager) view.findViewById(R.id.pager);
////		mViewPager.setOffscreenPageLimit(4);
//		// mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
//
//		mTabsAdapter = new ViewPageFragmentAdapter(getChildFragmentManager(),
//				mTabStrip, mViewPager);
//		setScreenPageLimit();
//		onSetupTabAdapter(mTabsAdapter);
//
//
//		// if (savedInstanceState != null) {
//		// int pos = savedInstanceState.getInt("position");
//		// mViewPager.setCurrentItem(pos, true);
//		// }
//
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//		initData();
//	}

    /**
     * 需要子类进行重写，设置viewpager的缓存页数
     */
    protected void setScreenPageLimit() {
    }

    // @Override
    // public void onSaveInstanceState(Bundle outState) {
    // //No call for super(). Bug on API Level > 11.
    // if (outState != null && mViewPager != null) {
    // outState.putInt("position", mViewPager.getCurrentItem());
    // }
    // //super.onSaveInstanceState(outState);
    // }

    protected abstract void onSetupTabAdapter(ViewPageFragmentAdapter adapter);
}