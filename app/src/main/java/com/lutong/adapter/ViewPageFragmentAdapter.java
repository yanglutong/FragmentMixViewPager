package com.lutong.adapter;

import java.util.ArrayList;

import com.lutong.R;
import com.lutong.widget.PagerSlidingTabStrip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

@SuppressLint("Recycle")
public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter {

	private final Context mContext;
	protected PagerSlidingTabStrip mPagerStrip;
	private final ViewPager mViewPager;
	private final ArrayList<ViewPageInfo> mTabs = new ArrayList<ViewPageInfo>();

	public ViewPageFragmentAdapter(FragmentManager fm,
								   PagerSlidingTabStrip pageStrip, ViewPager pager) {
		super(fm);
		mContext = pager.getContext();
		mPagerStrip = pageStrip;
		mViewPager = pager;
		mViewPager.setAdapter(this);
		mPagerStrip.setViewPager(mViewPager);
	}

	public void addTab(String title, String tag, Class<?> clss, Bundle args) {
		ViewPageInfo viewPageInfo = new ViewPageInfo(title, tag, clss, args);
		addFragment(viewPageInfo);
	}

	public void addAllTab(ArrayList<ViewPageInfo> mTabs) {
		for (ViewPageInfo viewPageInfo : mTabs) {
			addFragment(viewPageInfo);
		}
	}

	private void addFragment(ViewPageInfo info) {
		if (info == null) {
			return;
		}

		// 加入tab title
		View v = LayoutInflater.from(mContext).inflate(
				R.layout.base_viewpage_fragment_tab_item, null, false);
		TextView title = (TextView) v.findViewById(R.id.tab_title);
		title.setText(info.title);
		mPagerStrip.addTab(v);

		mTabs.add(info);
		notifyDataSetChanged();
	}

	/**
	 * 移除第一次
	 */
	public void remove() {
		remove(0);
	}

	/**
	 * 移除一个tab
	 * 
	 * @param index
	 *            备注：如果index小于0，则从第一个开始删 如果大于tab的数量值则从最后一个开始删除
	 */
	public void remove(int index) {
		if (mTabs.isEmpty()) {
			return;
		}
		if (index < 0) {
			index = 0;
		}
		if (index >= mTabs.size()) {
			index = mTabs.size() - 1;
		}
		mTabs.remove(index);
		mPagerStrip.removeTab(index, 1);
		notifyDataSetChanged();
	}

	/**
	 * 移除所有的tab
	 */
	public void removeAll() {
		if (mTabs.isEmpty()) {
			return;
		}
		mPagerStrip.removeAllTab();
		mTabs.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTabs.size();
	}

	@Override
	public int getItemPosition(Object object) {
		Log.d("TAG", "getItemPosition: ");
		return PagerAdapter.POSITION_NONE;
	}

	@Override
	public Fragment getItem(int position) {
		ViewPageInfo info = mTabs.get(position);
		Log.d("TAGgetItem", "getItemPosition: "+position);
		return Fragment.instantiate(mContext, info.clss.getName(), info.args);
	}

	public int getItem1() {
		int currentItem = mViewPager.getCurrentItem();
		Log.d("tagmViewPager", "getItem1: "+currentItem);

		return currentItem;
	}
	@Override
	public CharSequence getPageTitle(int position) {
		Log.d("getPageTitle", "getItem1: "+position);
		return mTabs.get(position).title;
	}
}