package com.lutong.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.Iterator;

public class GPSLocationUtil {
	Context context;
	LocationManager locationManager;
	MyLocationChangeListener mChangeListener;
	static GPSLocationUtil gLocationUtil;

	public static GPSLocationUtil getInstance(Context conte) {
		if (gLocationUtil == null) {
			gLocationUtil = new GPSLocationUtil();
			gLocationUtil.context = conte;
			gLocationUtil.initGPS();
		}
		return gLocationUtil;
	}
	/*
	 * 初始化GPS
	 */
	@SuppressLint("MissingPermission")
	public void initGPS(){
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, locationListener);
		locationManager.addGpsStatusListener(listener);

	}
	public int getGPSSateCount(){
		@SuppressLint("MissingPermission") GpsStatus gStatus = locationManager.getGpsStatus(null);
		Iterator<GpsSatellite> it = gStatus.getSatellites().iterator();//创建一个迭代器保存所有卫星
		int count = 0;
		int maxSatellites = gStatus.getMaxSatellites();
		while (it.hasNext()&&count<maxSatellites) {
			if(it.next().usedInFix())
				count++;
		}
		return count;
	}
	public Listener listener = new Listener() {

		@Override
		public void onGpsStatusChanged(int arg0) {
			// TODO Auto-generated method stub

		}
	};
	/*
	 * 监听
	 */
	public LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			// Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			//  Provider被enable时触发此函数，比如GPS被打开
//			if(mChangeListener)
//			mChangeListener.myLocationChanged(true);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			// Provider被disable时触发此函数，比如GPS被关闭
//			mChangeListener.myLocationChanged(false);
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			long time = location.getTime();
			Log.d("nzqtime", "onLocationChanged: "+time);
		}
	};
	public void setMyListener(MyLocationChangeListener mChangeListener){
		this.mChangeListener = mChangeListener;
	}
	public interface MyLocationChangeListener{
		void myLocationChanged(boolean flag);
	}
	public void removeListen(){
		locationManager.removeUpdates(locationListener);
		locationManager.removeGpsStatusListener(listener);
	}
}
