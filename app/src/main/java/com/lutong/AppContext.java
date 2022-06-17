package com.lutong;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import androidx.multidex.MultiDex;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.lutong.Service.LocationService;
import com.lutong.Utils.DbUtils;


public class AppContext extends Application {
    public static Context context;//需要使用的上下文对象;application实例
    private static AppContext instance;
	public BMapManager mBMapManager = null;
	public static LocationService locationService;
    public void onCreate() {
        super.onCreate();
		SDKInitializer.setAgreePrivacy(getApplicationContext(), true);
		LocationClient.setAgreePrivacy(true);
//        SDKInitializer.setAgreePrivacy(this, false);
		SDKInitializer.initialize(this);
        instance = this;
        context=this;
		MultiDex.install(this);

//		//自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//		//包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
		SDKInitializer.setCoordType(CoordType.BD09LL);
        isFirstStart(instance);//是否第一次启动
		locationService = new LocationService(getApplicationContext());
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
//        AutoSize.initCompatMultiProcess(this);
//
//        //如果在某些特殊情况下出现 InitProvider 未能正常实例化, 导致 AndroidAutoSize 未能完成初始化
//        //可以主动调用 AutoSize.checkAndInit(this) 方法, 完成 AndroidAutoSize 的初始化后即可正常使用
////        AutoSize.checkAndInit(this);
//
////        如何控制 AndroidAutoSize 的初始化，让 AndroidAutoSize 在某些设备上不自动启动？https://github.com/JessYanCoding/AndroidAutoSize/issues/249
//
//        /**
//         * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
//         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
//         */
//        AutoSizeConfig.getInstance()
//
//                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
//                //如果没有这个需求建议不开启
//                .setCustomFragment(true)
//
//                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
//                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
////                .setExcludeFontScale(true)
//
//                //区别于系统字体大小的放大比例, AndroidAutoSize 允许 APP 内部可以独立于系统字体大小之外，独自拥有全局调节 APP 字体大小的能力
//                //当然, 在 APP 内您必须使用 sp 来作为字体的单位, 否则此功能无效, 不设置或将此值设为 0 则取消此功能
////                .setPrivateFontScale(0.8f)
//
//                //屏幕适配监听器
//                .setOnAdaptListener(new onAdaptListener() {
//                    @Override
//                    public void onAdaptBefore(Object target, Activity activity) {
//                        //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
//                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
////                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
////                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
//                        AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
//                    }
//
//                    @Override
//                    public void onAdaptAfter(Object target, Activity activity) {
//                        AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
//                    }
//                })
//
//        //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
////                .setLog(false)
//
//        //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
//        //AutoSize 会将屏幕总高度减去状态栏高度来做适配
//        //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//        //在全面屏或刘海屏幕设备中, 获取到的屏幕高度可能不包含状态栏高度, 所以在全面屏设备中不需要减去状态栏高度，所以可以 setUseDeviceSize(true)
////                .setUseDeviceSize(true)
//
//        //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
////                .setBaseOnWidth(false)
//
//        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
////                .setAutoAdaptStrategy(new AutoAdaptStrategy())
//        ;
    }
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetPermissionState(int iError) {
			// 非零值表示key验证未通过
			if (iError != 0) {
				// 授权Key错误：
//                Toast.makeText(AppLication.getInstance().getApplicationContext(),
//                        "请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError, Toast.LENGTH_LONG).show();
			} else {
//                Toast.makeText(AppLication.getInstance().getApplicationContext(), "key认证成功", Toast.LENGTH_LONG)
//                        .show();
			}
		}
	}
    public static AppContext getInstance() {
        return instance;
    }

    public static Context getContexts() {
        return context;
    }

    //判断是否是第一次安装如果是第一次安装 则插入自带的系统频点
    public boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStart", false).commit();
//            Log.i("GFA", "一次");
//            int band, int down, String plmn, int type, int up, String tf, String yy
            //以下是插入频点设置 列表
            //移动TDD
			DbUtils.insertDB(context, 38, 37900, "46000", 0, 255, "TDD", "移动");
			DbUtils.insertDB(context, 38, 38098, "46000", 0, 255, "TDD", "移动");
			DbUtils.insertDB(context, 39, 38400, "46000", 0, 255, "TDD", "移动");
			DbUtils.insertDB(context, 39, 38544, "46000", 0, 255, "TDD", "移动");
			DbUtils.insertDB(context, 40, 38950, "46000", 0, 255, "TDD", "移动");
			DbUtils.insertDB(context, 41, 40936, "46000", 0, 255, "TDD", "移动");
			//移动FDD
			DbUtils.insertDB(context, 3, 1300, "46000", 0, 19300, "FDD", "移动");
			DbUtils.insertDB(context, 8, 3683, "46000", 0, 21683, "FDD", "移动");
			//联通FDD
			DbUtils.insertDB(context, 1, 375, "46001", 0, 18375, "FDD", "联通");
			DbUtils.insertDB(context, 3, 1650, "46001", 0, 19650, "FDD", "联通");
			//电信
			DbUtils.insertDB(context, 1, 100, "46011", 0, 18100, "FDD", "电信");
			DbUtils.insertDB(context, 3, 1825, "46011", 0, 19825, "FDD", "电信");
			DbUtils.insertDB(context, 3, 1850, "46011", 0, 19850, "FDD", "电信");
			DbUtils.insertDB(context, 5, 2452, "46011", 0, 20452, "FDD", "电信");
            //以下是插入扫频列表
            //移动TDD
			DbUtils.insertDBSaopin(context, 37900, 255, 1);
			DbUtils.insertDBSaopin(context, 38098, 255, 1);
			DbUtils.insertDBSaopin(context, 38400, 255, 1);
			DbUtils.insertDBSaopin(context, 38544, 255, 1);
			DbUtils.insertDBSaopin(context, 38950, 255, 1);
			DbUtils.insertDBSaopin(context, 40936, 255, 1);
//            移动FDD
			DbUtils.insertDBSaopin(context, 1300, 19300, 2);
			DbUtils.insertDBSaopin(context, 3683, 21683, 2);
			//联通FDD
			DbUtils.insertDBSaopin(context, 375, 18375, 3);
			DbUtils.insertDBSaopin(context, 1650, 19650, 3);
			//电信FDD
			DbUtils.insertDBSaopin(context, 100, 18100, 4);
			DbUtils.insertDBSaopin(context, 1825, 19825, 4);
			DbUtils.insertDBSaopin(context, 1850, 19850, 4);
			DbUtils.insertDBSaopin(context, 2452, 20452, 4);

            //以下是插入轮循设置列表
            //移动TDD
//			DbUtils.insertDBLunxun(context, 37900, 255, 1);
//			DbUtils.insertDBLunxun(context, 38098, 255, 1);
//			DbUtils.insertDBLunxun(context, 38400, 255, 1);
//			DbUtils.insertDBLunxun(context, 38544, 255, 1);
//			DbUtils.insertDBLunxun(context, 38950, 255, 1);
//			DbUtils.insertDBLunxun(context, 40936, 255, 1);
            //移动FDD
//			DbUtils.insertDBLunxun(context, 1300, 19300, 2);
//			DbUtils.insertDBLunxun(context, 3683, 21683, 2);
            //联通FDD
//			DbUtils.insertDBLunxun(context, 375, 18375, 3);
//			DbUtils.insertDBLunxun(context, 1650, 19650, 3);
            //电信FDD
//			DbUtils.insertDBLunxun(context, 100, 18100, 4);
//			DbUtils.insertDBLunxun(context, 1825, 19825, 4);
//			DbUtils.insertDBLunxun(context, 1850, 19850, 4);
//			DbUtils.insertDBLunxun(context, 2452, 20452, 4);
            //以下是插入小区配置
            DbUtils.xiaoqu(context);//小区参数初始化
            //插入增益初始化数据
			DbUtils.ZY(context, "20", "52", "72", 1, "20", "40", "72");//TDD
			DbUtils.ZY(context, "20", "52", "72", 2, "20", "40", "72");//FDD
//            以下是插入临时测试imsi
//            460005192822841
            DbUtils.TestIMSI(context, "460002360893648", "王", "", "移动", false);
            DbUtils.TestIMSI(context, "460005192822841", "南移动", "", "移动", false);
            DbUtils.TestIMSI(context, "460010619201205", "范", "", "联通", false);
//            DbUtils.TestIMSI(context, "460011641414031", "南联通", "", "联通", false);
//
//
//            DbUtils.TestIMSIWhite(context, "460002360893648", "南玻万", "", "移动", true);
//            DbUtils.TestIMSIWhite(context, "460005192822841", "南移动", "", "移动", false);
//            DbUtils.TestIMSIWhite(context, "460010619201205", "范", "", "联通", false);
//            DbUtils.TestIMSIWhite(context, "460011641414031", "南联通", "", "联通", false);
            //注册码
			DbUtils.zc(context);
            return true;
        } else {
//            startActivity(new Intent(context, MainActivity.class));
//            finish();
//            Log.i("GFA", "N次");
//            Toast.makeText(getApplicationContext(),"N次",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
