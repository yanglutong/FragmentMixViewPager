package com.lutong.ui;

import com.lutong.fragment.home.FragmentGm;
import com.lutong.fragment.home.HomeFragment;
import com.lutong.R;
import com.lutong.fragment.home.HomeFragment2;
import com.lutong.fragment.home.ViewPagerFragment1;


public enum MainTab {

//	NO1(0, R.string.no1, R.drawable.tab_icon1,
//	        ViewPagerFragment1.class),
//	NO2(1, R.string.no2, R.drawable.tab_icon2,
//	        Fragment1.class),
//
//	NO3(3, R.string.no3, R.drawable.tab_icon3,
//	        Fragment2.class);
//
////	NO4(4, R.string.no4, R.drawable.tab_icon,
////	        Fragment3.class);



//	NO1(0, R.string.no2, R.drawable.tab_icon2,
//			HomeFragment.class),
////	NO1(0, R.string.no2, R.drawable.tab_icon2,
////			Fragment1.class),
//
//	NO2(1, R.string.no3, R.drawable.tab_icon3,
//			Fragment2.class),
//
//	NO3(3, R.string.no1, R.drawable.tab_icon1,
//			ViewPagerFragment1.class);


	NO1(0, R.string.no2, R.drawable.tab_icon2,
			HomeFragment.class),
//	NO1(0, R.string.no2, R.drawable.tab_icon2,
//			Fragment1.class),

	NO2(1, R.string.no3, R.drawable.tab_icon3,
			FragmentGm.class),

	NO3(3, R.string.no1, R.drawable.tab_icon1,
			ViewPagerFragment1.class);

//	NO4(4, R.string.no4, R.drawable.tab_icon,
//	        Fragment3.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
