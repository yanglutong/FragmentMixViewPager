//package com.lutong.Utils;
//
//import static android.content.ContentValues.TAG;
//import static android.content.Context.SENSOR_SERVICE;
//import static com.baidu.mapapi.utils.CoordinateConverter.CoordType.BD09LL;
//import static com.lutong.Constant.Constant.MAXTA;
//import static com.lutong.Constant.Constant.MINTA;
//import static com.lutong.Constant.Constant.UNIFORMTA;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.icu.text.DecimalFormat;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.os.PowerManager;
//import android.provider.Settings;
//import android.speech.tts.TextToSpeech;
//
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupMenu;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.StaggeredGridLayoutManager;
//
//
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.CircleOptions;
//import com.baidu.mapapi.map.DotOptions;
//import com.baidu.mapapi.map.InfoWindow;
//import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
//import com.baidu.mapapi.map.MapPoi;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.Marker;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.MyLocationConfiguration;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.map.Overlay;
//import com.baidu.mapapi.map.OverlayOptions;
//import com.baidu.mapapi.map.Polyline;
//import com.baidu.mapapi.map.PolylineOptions;
//import com.baidu.mapapi.map.Stroke;
//import com.baidu.mapapi.map.TextOptions;
//import com.baidu.mapapi.map.UiSettings;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.utils.CoordinateConverter;
//import com.baidu.mapapi.utils.DistanceUtil;
//import com.blankj.utilcode.util.ToastUtils;
//
//import com.lutong.AppContext;
//import com.lutong.OrmSqlLite.Bean.DataAliBean;
//import com.lutong.OrmSqlLite.Bean.DataBean;
//import com.lutong.OrmSqlLite.Bean.GuijiViewBean;
//import com.lutong.OrmSqlLite.Bean.GuijiViewBeanjizhan;
//import com.lutong.OrmSqlLite.Bean.JzDataQury;
//import com.lutong.OrmSqlLite.Bean.JzGetData;
//import com.lutong.OrmSqlLite.Bean.SpBean2;
//import com.lutong.OrmSqlLite.DBManagerGuijiView;
//import com.lutong.OrmSqlLite.DBManagerJZ;
//import com.lutong.R;
//import com.lutong.Retrofit.RetrofitFactory;
//import com.lutong.Service.LocationService;
//import com.lutong.activity.JzListActivity;
//import com.lutong.activity.PanoramaDemoActivityMain;
//import com.lutong.activity.TaActivity;
//import com.lutong.fragment.adapter.AddCallBack;
//import com.lutong.fragment.adapter.CallBack;
//import com.lutong.fragment.adapter.DemoAdapter;
//import com.lutong.fragment.adapter.DemoAdapteradd;
//import com.lutong.fragment.adapter.Mycallback;
//import com.lutong.fragment.adapter.SerrnTaAdapter;
//import com.mylhyl.circledialog.CircleDialog;
//import com.pedaily.yc.ycdialoglib.dialog.loading.ViewLoading;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class LocationFragment extends Fragment implements View.OnClickListener, SensorEventListener,PopupMenu.OnMenuItemClickListener {
//
//    private Context mContext;
//    private View view;//??????
//
//    /**
//     * ???????????????
//     */
//    private List<LatLng> trackPoints;
//
//    JzGetData jzGetData;
//    double add;
//    int DATATYPE = 6;
//    public static int BUSINESS = 0;//??????????????????
//    private List<Double> list = new ArrayList<>();
//    private List<Double> list2 = new ArrayList<>();//???????????????Ta ??????
//    private List<Double> listMarker = new ArrayList<>();
//    private DemoAdapter demoAdapter;
//    private TextToSpeech textToSpeech = null;//????????????????????????
//    //?????????????????????
//    Point zuoxiapoint = new Point();
//    Point youshangpoint = new Point();
//    Point zuoshangpoint = new Point();
//    Point youxiapoing = new Point();
//    float x1 = (float) 59;
//    float y1 = (float) 1989;
//    float x2 = (float) 1034;
//    float y2 = (float) 360;
//    LatLng zuoxiaLatLng = null;
//    LatLng youxiaaLatLng = null;
//    // ????????????
//    LocationClient mLocClient;
//    public MyLocationListenner myListener = new MyLocationListenner();
//    private MyLocationConfiguration.LocationMode mCurrentMode;
//    BitmapDescriptor mCurrentMarker;
//    private static final int accuracyCircleFillColor = 0x19e64dff;
//
//    private static final int accuracyCircleStrokeColor = 0x19e64dff;
//    private SensorManager mSensorManager;
//    private Double lastX = 0.0;
//    private int mCurrentDirection = 0;
//    private double mCurrentLat = 0.0;
//    private double mCurrentLon = 0.0;
//    private float mCurrentAccracy;
//
//    MapView mMapView;
//    BaiduMap mBaiduMap;
//
//    // UI??????
//    RadioGroup.OnCheckedChangeListener radioButtonListener;
//    Button requestLocButton;
//    boolean isFirstLoc = true; // ??????????????????
//    private MyLocationData locData;
//    private float direction;
//    //View  ????????????   ????????????     ????????????    ????????????        ??????   ???????????? ????????????  ????????????     ?????????   ?????????      ????????????
//    Button bt_jizhan, bt_qiehuan, bt_clear, bt_location, bt_hot, bt_jt, bt_juli, bt_jingbao, bt_jia, bt_jian, bt_uiceliangclear;
//    //     ????????????-  ????????????-
//    Button bt_text, bt_ikan;
//    //?????????UI View   ?????? ???   ???????????? ??? ??????  ??? ????????????  ??????    ?????????????????????  ???????????????????????????      ????????????     ??????           ??????   ??????
//    LinearLayout ll_qiehuan, ll_clear, ll_jt, ll_rl, ll_baojing, ll_guijis, ll_gjview, ll_gjclear, ll_gensui, ll_fugai, ll_screen,ll_cl,ll_gj;
//    LinearLayout ll_sid, ll_location;
//    //            ???????????????         ??????         ????????????        ??????????????????
//    private Button bt_uilocation, bt_uiceliang, bt_uisearch, bt_jizhan0;
//    //                                                                                                                        //??????  ??????
//    private ImageView ib_qiehuan, ib_clear, ib_jt, ib_rl, ib_baojing, iv_gjstart, iv_viewstart, ib_gensui, ib_fugai, ib_screen,ib_cl,ib_gj;
//    private TextView tv_screen;
//    //    Button bt_hot;//
//    //?????????????????????
//    private Dialog dialog, dialog2, dialogmenu;
//    private View inflate;
//    private EditText et_taclac, et_eci, et_ta, et_sid, ets_lon, ets_lat;
//    private Button bt_adddilao;
//    private CheckBox rb_yidong, rb_ldainxin4, rb_liantong, rb_cdma, rb_bdjz1, rb_bdjz2;
//    private ImageView iv_finish, iv_set;
//    private int jizhanFlag = 0;
//    public static LatLng mylag = null;
//    public static LatLng mylag2 = null;
//    public static LatLng mylag3 = null;
//    public static double longitude;
//    private static double latitude;
//    //menu???????????????
//    EditText et_guijitime;
//    EditText et_baojingtime;
//    EditText et_queryMax;
//    EditText et_querynum;
//    EditText et_data;
//    EditText et_fw;
//    EditText et_kg;
//    Button bt_adddilaomenu;
//    Button bt_m_locations;
//    //?????????????????????
//    private DataBean dataBean;
//    //?????????????????????
//    private DataAliBean dataAliBean;
//    List<LatLng> points = new ArrayList<>();//????????????list
//    private LatLng ll;
//    private double distance2 = 0;
//    private double distance = 0;
//    private int hottype = 1;
//    private int hjttype = 1;
//    private boolean juliFlage = false;
//    //??????????????????????????????
//    private Marker markerMy;
//    private Bundle bundles;//???????????????????????????
//    private GeoCoder mSearch = null; // ???????????????????????????????????????????????????
//    private GeoCoder SdmSearch = null; // ???????????????????????????????????????????????????
//
//    private String address_reverseGeoCodeResult = "";//????????????marker??????
//    private boolean jingbaoflag = false;//????????????????????????
//    private int juliType = 0;
//    private boolean guijiFlag = false;
//    private boolean guijistart = false;
//    private boolean gensuiFlag = false;
//    private boolean fugaiFlag = false;
//    private int ScanSpan = 1000;//?????????????????????
//    //?????????????????????
//    private DBManagerGuijiView dbManagerGuijiView;
//    private LatLng jisuanLant;//???????????????????????????
//    private Handler mHandler;
//    private Polyline mPolyline;
//    private Marker mMoveMarker;
//    // ???????????????????????????????????????????????????????????????????????????
//    private static final int TIME_INTERVAL = 180;
//    private static final double DISTANCE = 0.00001;
//    private BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromAsset("Icon_road_red_arrow.png");
//    private BitmapDescriptor mBlueTexture = BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png");
//    private BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow.png");
//
//    private Timer timer, timer2;
//    LatLng positionjingbaojizhan;
//    List<LatLng> pointsJingbao;
//    private Boolean MapinitFlag = false;
//    private boolean xunFlag = false;
//    DBManagerJZ dbManagerJZ;
//    DemoAdapteradd demoAdapteradd;
//    SerrnTaAdapter serrnAdapters;
//    UiSettings uiSettings;
//    private Handler handler = new Handler() {
//        @SuppressLint("HandlerLeak")
//        @TargetApi(Build.VERSION_CODES.N)
//        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
////            if (msg.what == 1) {
////                Viewgjs();
////            }
//            if (msg.what == 2) {//????????????
////                JingBaos(pointsJingbao, positionjingbaojizhan);
//                JingBaos(pointsJingbao, positionjingbaojizhan, mylag, true);
//                try {
//                    List<GuijiViewBeanjizhan> guijiViewBeanjizhans = dbManagerJZ.queryType();
//                    Log.d(TAG, "JingBaos:Type " + guijiViewBeanjizhans);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            if(msg.what==3){//????????????
//                    if(isGj){
//                        //???????????????
//                        if(null==mylag2){
//                            return;
//                        }
//                        if (null == trackPoints) {
//                            return;
//                        }
//                        Log.e("TAGhandleMessage", "handleMessage:");
//                        DBManagerGuijiView dbManagerGuijiView=null;
//                        try {
//                            dbManagerGuijiView = new DBManagerGuijiView(mContext);
//                            List<GuijiViewBean> list = dbManagerGuijiView.guijiViewBeans();
//                            if(list.size()>0){
//                                trackPoints.clear();
//                                for (int i = 0; i < list.size(); i++) {
//                                    trackPoints.add(new LatLng(list.get(i).getLat(),list.get(i).getLon()));
//                                }
//                                mapUtil.drawHistoryTrack(trackPoints, false, mCurrentDirection);//?????????????????????????????????
//                            }
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                        if(trackPoints!=null&&trackPoints.size()>0){
//                            Log.e("FFFFFF", "handleMessage:fffffffffffffsssf " );
//                            trackPoints.clear();
//                            mBaiduMap.clear();
//                            initdatas2();
//                        }
//                    }
//
//
//
//
//            }
//        }
//    };
//    private boolean isGj=false;
//    private MapUtil mapUtil;
//
//
//    public LocationFragment() {
//        // Required empty public constructor
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.activity_location, container, false);
//        mContext = getActivity();
//        initView(view);
//        return view;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void initView(View view) {
//        //?????????
//        initData(view);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void initData(View view) {
//        init(view);
//        //????????????
//        isIgnoringBatteryOptimizations();
//        requestIgnoreBatteryOptimizations();
//
//
//        jzGetData = new JzGetData();
//        mMapView = view.findViewById(R.id.bmapView);
//        mBaiduMap = mMapView.getMap();
//        mMapView.showZoomControls(false);//????????????
//
//        uiSettings = mBaiduMap.getUiSettings();
//        ViewLoading.show(mContext, "?????????");
//
//
//        // ??????????????????????????????????????????
//        mSearch = GeoCoder.newInstance();
//        SdmSearch = GeoCoder.newInstance();
//        SdmSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//            @Override
//            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//            }
//
//            @Override
//            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//            }
//        });
//
//
//        try {
//            dbManagerGuijiView = new DBManagerGuijiView(mContext);
//            dbManagerJZ = new DBManagerJZ(mContext);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//
//        //???????????????
//
//// ???????????????
//        MyUtils.getPermissions((Activity) mContext);//????????????
//        findView(view);//View?????????
//        initMap();//???????????????
//        initdatas2();//????????????????????????
//
//
//        //??????????????????
//        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            //marker???????????????????????????
//            //??????????????????????????????true???????????????false
//            //????????????false
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onMarkerClick(final Marker marker) {
//                Log.d(TAG, "onMarkerClick: " + marker.getExtraInfo());
//                if (jingbaoflag == true) {
////                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    MyToast.showToast("??????????????????");
//                } else {
//                    setMapMakerView(marker);//??????????????????
//                }
//                return false;
//            }
//        });
//
//        //??????Polyline???????????????
//        mBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
//            @Override
//            public boolean onPolylineClick(Polyline polyline) {
////                Toast.makeText(MainActivity.this, "Click on polyline", Toast.LENGTH_LONG).show();
////                Log.d(TAG, "onPolylineClick: "+polyline.getPoints().);
////                if (points.get(0)==polyline.getPoints().get(0)){
////                    Log.d(TAG, "onPolylineClick: points"+"??????");
////                }
//                return false;
//            }
//        });
//
//        //??????????????????????????????
//        mapUtil.baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onMapClick(LatLng latLng) {
//                if (juliFlage == true) {
//                    DecimalFormat df = new DecimalFormat(".#");
//                    //?????????????????????
//                    points.add(latLng);
//                    //??????Marker??????
//                    if (points.size() > 1) {
//                        //?????????????????????
//                        OverlayOptions mOverlayOptions = new PolylineOptions()
//                                .width(2)
//                                .color(Color.rgb(65, 105, 225))
//                                .points(points);
//                        //????????????????????????
//                        //mPloyline ????????????
//                        Overlay mPolyline = mapUtil.baiduMap.addOverlay(mOverlayOptions);
//                        int sizes = 0;
//
//                        Log.d(TAG, "onMapClickpoints.size: " + points.size());
//                        for (int i = 0; i < points.size(); i++) {
//                            System.out.print("aaaaaaa" + i);
//                            Log.d(TAG, "aaaaaaaonMapClick: " + i);
//                            sizes = i;
////                         distance = DistanceUtil.getDistance(points.get(i), points.get(1));
//                        }
//                        LatLng latLng2 = points.get(sizes - 1);
//                        distance2 = DistanceUtil.getDistance(latLng2, latLng);//???????????????
//                        Log.d(TAG, "distanceonMapClicks: " + distance + distance2 + "???" + "????????????" + distance2 + "???");
//                        //???????????????????????????
//                        LatLng latLngtext = new LatLng(latLng.latitude - 0.00005, latLng.longitude);
//                        //??????TextOptions??????
//                        OverlayOptions mTextOptions = new TextOptions()
//                                .text("" + df.format(distance2) + "???") //????????????
//                                .bgColor(Color.rgb(224, 255, 255)) //?????????
//                                .fontSize(22) //??????
//                                .fontColor(Color.rgb(0, 0, 0)) //????????????
//                                .rotate(0) //????????????
//                                .position(latLngtext);
//                        //?????????????????????????????????
//                        Overlay mText = mapUtil.baiduMap.addOverlay(mTextOptions);
//                        //                   latLngOnclicek = latLng;
//
//                        //?????????????????????
//                        for (int i = 0; i < points.size(); i++) {
//                            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                    .fromResource(R.drawable.celiangbiao);
//                            //??????MarkerOption???????????????????????????Marker
//                            Bundle bundle = new Bundle();
//                            bundle.putString("address", "");
//                            OverlayOptions option = new MarkerOptions()
//                                    .position(points.get(i))
//                                    .extraInfo(bundle)
//                                    .perspective(true)
//                                    .visible(true)
//                                    .icon(bitmap);
//                            //??????????????????Marker????????????
//                            mapUtil.baiduMap.addOverlay(option);
//                            Log.d(TAG, "pointsonMapClick: " + points.size());
//                        }
//                    }
//                    BitmapDescriptor bitmap = BitmapDescriptorFactory
//                            .fromResource(R.drawable.celiangbiao);
//
//
//                    //??????MarkerOption???????????????????????????Marker
//                    Bundle bundle = new Bundle();
//                    bundle.putString("address", "");
//                    OverlayOptions option = new MarkerOptions()
//                            .position(latLng)
//                            .extraInfo(bundle)
//                            .perspective(true)
//                            .visible(true)
//                            .icon(bitmap);
//                    //??????????????????Marker????????????
//                    mapUtil.baiduMap.addOverlay(option);
//                    Log.d(TAG, "pointsonMapClick: " + points.size());
//                }
//
//
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onMapPoiClick(MapPoi mapPoi) {
//                if (juliFlage == true) {
//                    DecimalFormat df = new DecimalFormat(".#");
//                    LatLng latLng = new LatLng(mapPoi.getPosition().latitude, mapPoi.getPosition().longitude);
//                    //?????????????????????
//                    points.add(latLng);
//
//                    if (points.size() > 1) {
//                        //?????????????????????
//                        OverlayOptions mOverlayOptions = new PolylineOptions()
//                                .width(2)
//                                .color(Color.rgb(65, 105, 225))
//                                .points(points);
//                        //????????????????????????
//                        //mPloyline ????????????
//                        Overlay mPolyline = mapUtil.baiduMap.addOverlay(mOverlayOptions);
//                        int sizes = 0;
//                        for (int i = 0; i < points.size(); i++) {
//                            System.out.print("aaaaaaa" + i);
//                            Log.d(TAG, "aaaaaaaonMapClick: " + i);
//                            sizes = i;
//                        }
//                        LatLng latLng2 = points.get(sizes - 1);
//                        double distance2 = DistanceUtil.getDistance(latLng2, latLng);//???????????????
//                        Log.d(TAG, "distanceonMapClick: " + "????????????" + distance2 + "???");
//                        //???????????????????????????
//
//                        //??????TextOptions??????
//                        LatLng latLngtext = new LatLng(latLng.latitude - 0.00005, latLng.longitude);
//                        //??????TextOptions??????
//                        OverlayOptions mTextOptions = new TextOptions()
//                                .text("" + df.format(distance2) + "???") //????????????
//                                .bgColor(Color.rgb(224, 255, 255)) //?????????
//                                .fontSize(22) //??????
//                                .fontColor(Color.rgb(0, 0, 0)) //????????????
//                                .rotate(0) //????????????
//                                .position(latLngtext);
//                        //?????????????????????????????????
//                        Overlay mText = mapUtil.baiduMap.addOverlay(mTextOptions);
//                        //?????????????????????
//                        for (int i = 0; i < points.size(); i++) {
//                            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                    .fromResource(R.drawable.celiangbiao);
//                            //??????MarkerOption???????????????????????????Marker
//                            Bundle bundle = new Bundle();
//                            bundle.putString("address", "");
//                            OverlayOptions option = new MarkerOptions()
//                                    .position(points.get(i))
//                                    .extraInfo(bundle)
//                                    .perspective(true)
//                                    .visible(true)
//                                    .icon(bitmap);
//                            //??????????????????Marker????????????
//                            mapUtil.baiduMap.addOverlay(option);
//                            Log.d(TAG, "pointsonMapClick: " + points.size());
//                        }
//                    }
//                    //??????Marker??????
//                    BitmapDescriptor bitmap = BitmapDescriptorFactory
//                            .fromResource(R.drawable.celiangbiao);
//                    //??????MarkerOption???????????????????????????Marker
//                    Bundle bundle = new Bundle();
//                    bundle.putString("address", "");
//                    OverlayOptions option = new MarkerOptions()
//                            .position(latLng)
//                            .extraInfo(bundle)
//                            .visible(true)
//                            .icon(bitmap);
//                    //??????????????????Marker????????????
//                    mapUtil.baiduMap.addOverlay(option);
//                }
//
////                return false;
//            }
//        });
//
//
//        // ?????????????????????
//        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
//            @Override
//            public void onMapStatusChangeStart(MapStatus mapStatus) {
//
//            }
//
//            @Override
//            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {
//
//            }
//
//            @Override
//            public void onMapStatusChange(MapStatus mapStatus) {
//
//            }
//
//            @Override
//            public void onMapStatusChangeFinish(MapStatus mapStatus) {//?????????????????????????????????
//                double zoomset = mBaiduMap.getMapStatus().zoom;
//                Log.d(TAG, "aaweq: " + zoomset);
//                if (xunFlag == true) {
////
//
//                }
//
////              i
//
//                if (zoomset > 18) {
//                    add = 0.0125f / Math.pow(2, zoomset - 15);
//                    zuoxiapoint.set((int) x1, (int) y1);
//                    youshangpoint.set((int) x2, (int) y2);
//                    LatLng zuoshangLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoshangpoint);//?????????
//                    LatLng youxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(youxiapoing);//?????????
//                    youxiaaLatLng = mBaiduMap.getProjection().fromScreenLocation(youshangpoint);//?????????
//                    //??????????????????
//                    Map<String, Double> stringDoubleMap = GCJ02ToWGS84Util.bd09to84(youxiaaLatLng.longitude, youxiaaLatLng.latitude);
//                    youxiaaLatLng = new LatLng(stringDoubleMap.get("lat"), stringDoubleMap.get("lon"));
//
//
//                    zuoxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoxiapoint);
//                    //??????????????????
//                    Map<String, Double> zuoxial = GCJ02ToWGS84Util.bd09to84(zuoxiaLatLng.longitude, zuoxiaLatLng.latitude);
//                    zuoxiaLatLng = new LatLng(zuoxial.get("lat"), zuoxial.get("lon"));
//                    JzGetData(add);
//
//                }
//
//            }
//        });
//        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
////
//            }
//        });
//
//
//        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//
//            //???Marker???????????????????????????????????????Marker?????????????????????getPosition()????????????
//            //marker ????????????Marker??????
//            @Override
//            public void onMarkerDrag(Marker marker) {
//                //???marker??????????????????
//                if (jingbaoflag == true) {
////                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    MyToast.showToast("??????????????????");
//                    return;
//                }
//            }
//
//            //???Marker????????????????????????????????? ??????Marker??????????????????getPosition()????????????
//            //marker ????????????Marker??????
//            @Override
//            public void onMarkerDragEnd(final Marker marker) {
//                final LatLng positionA = marker.getPosition();
////                GeoCoder mCoder = GeoCoder.newInstance();
//
//                if (jingbaoflag == true) {
////                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    MyToast.showToast("??????????????????");
//                    return;
//                }
//                mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//                    @Override
//                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//                    }
//
//                    @Override
//                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                            //????????????????????????
//                            if (jingbaoflag == true) {
////                                Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                                MyToast.showToast("??????????????????");
//                                return;
//
//                            }
//                            Log.d(TAG, "onGetReverseGeoCodeResult: " + "????????????????????????");
//                            mBaiduMap.clear();
//                            Log.d(TAG, "onMarkerDragEnd: " + positionA);
////                            aaaaaaaa
//                            Bundle extraInfo = marker.getExtraInfo();
//
//                            String ta1 = extraInfo.getString("ta");
//
//                            List<Double> lists = MyUtils.StringTolist(ta1);
//                            int sum = 0;
//                            for (int j = 0; j < lists.size(); j++) {
//                                sum += lists.get(j);
//                            }
//
//                            int size = lists.size();
//                            Log.d(TAG, "DataAll?????????: " + sum / size);
//                            //???????????????
//
//
//                            double sum_db = sum;
//                            double size_db = size;
//                            double Myradius_db = sum_db / size_db * 78;
//                            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
//                            int Myradius = new Double(Myradius_db).intValue();
//                            Log.d(TAG, "JingBaos:Myradius " + Myradius);
//                            if (UNIFORMTA == true) {
//                                OverlayOptions ooCirclepingjun = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(40, 255,
//                                                0,
//                                                0))
//
//                                        .center(positionA)
//
//                                        .stroke(new Stroke(5, Color.rgb(255,
//                                                0,
//                                                0)))
//                                        .radius(Myradius);
//                                mBaiduMap.addOverlay(ooCirclepingjun);
//                                Log.e(TAG, "DataAll:?????? " + Collections.max(lists));
//                            }
//
//                            //??????ta???
//                            if (MAXTA == true) {
//                                OverlayOptions ooCircleaMAx = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(0, 255,
//                                                0,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(2, Color.rgb(255,
//                                                0,
//                                                0)))
//                                        .radius((int) (Collections.max(lists) * 78));
//
//                                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                                mBaiduMap.addOverlay(ooCircleaMAx);
//                            }
//                            Log.d(TAG, "DataAllsum: " + sum);
//                            //??????ta???
//                            if (MINTA == true) {
//                                OverlayOptions ooCircleaMin = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(0, 255,
//                                                0,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(2, Color.rgb(255,
//                                                0,
//                                                0)))
//                                        .radius((int) (Collections.min(lists) * 78));
//
//                                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                                mBaiduMap.addOverlay(ooCircleaMin);
//                            }
//                            Log.d(TAG, "listDataAll: " + lists.get(0));
//
//                            //??????
////                    LatLng llDot = new LatLng(39.90923, 116.447428);
//                            OverlayOptions ooDot = new DotOptions().center(positionA).radius(6).color(0xFF0000FF);
//                            mBaiduMap.addOverlay(ooDot);
//                            //??????Marker??????
//                            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                    .fromResource(R.drawable.jizhan1);
//                            //??????MarkerOption???????????????????????????Marker
//
//                            extraInfo.putString("lat", String.valueOf(positionA.latitude));
//                            extraInfo.putString("lon", String.valueOf(positionA.longitude));
//
//                            extraInfo.putString("address", "???");
//                            OverlayOptions optiona = new MarkerOptions()
//                                    .anchor(10, 30)
//                                    .extraInfo(extraInfo)
//                                    .position(positionA) //????????????
//                                    .perspective(true)
//                                    .icon(bitmap) //????????????
//                                    .draggable(true)
//                                    .draggable(true)
//                                    //?????????????????????????????????????????????????????????
//                                    .flat(true)
//                                    .alpha(0.5f);
//                            //??????????????????Marker????????????
//                            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//??????????????????????????????  Marker marker
//                            if (fugaiFlag == true) {
//                                //????????????
//                                String tas = extraInfo.getString("Radius");
//                                int is = Integer.parseInt(tas);
//                                OverlayOptions ooCirclefugai = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(40, 255,
//                                                165,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(2, Color.rgb(255,
//                                                165,
//                                                0)))
//                                        .radius(is);
//                                mBaiduMap.addOverlay(ooCirclefugai);
//                                return;
//                            }
////
//
//                        } else {
//                            Bundle extraInfo = marker.getExtraInfo();
//                            //????????????
//                            address_reverseGeoCodeResult = reverseGeoCodeResult.getAddress();
//                            Log.d(TAG, "onGetReverseGeoCodeResult: " + address_reverseGeoCodeResult);
//                            mBaiduMap.clear();
//                            Log.d(TAG, "onMarkerDragEnd: " + positionA);
//                            String ta1 = extraInfo.getString("ta");
//                            List<Double> lists = MyUtils.StringTolist(ta1);
//                            int sum = 0;
//                            for (int j = 0; j < lists.size(); j++) {
//                                sum += lists.get(j);
//                            }
//
//                            int size = lists.size();
//                            double sum_db = sum;
//                            double size_db = size;
//                            double Myradius_db = sum_db / size_db * 78;
//                            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
//                            int Myradius = new Double(Myradius_db).intValue();
//                            Log.d(TAG, "JingBaos:Myradius " + Myradius);
//                            //???????????????
//                            if (UNIFORMTA == true) {
//                                OverlayOptions ooCirclepingjun = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(40, 255,
//                                                0,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(10, Color.rgb(255,
//                                                0,
//                                                0)))
//                                        .radius(Myradius);
//                                mBaiduMap.addOverlay(ooCirclepingjun);
//                                Log.e(TAG, "DataAll:?????? " + Collections.max(lists));
//                            }
//
//                            //??????ta???
//                            if (MAXTA == true) {
//                                OverlayOptions ooCircleaMAx = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(0, 255,
//                                                0,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(2, Color.rgb(255,
//                                                0,
//                                                0)))
//                                        .radius((int) (Collections.max(lists) * 78));
//
//                                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                                mBaiduMap.addOverlay(ooCircleaMAx);
//                            }
//                            Log.d(TAG, "DataAllsum: " + sum);
//                            //??????ta???
//                            if (MINTA == true) {
//                                OverlayOptions ooCircleaMin = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(0, 255,
//                                                0,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(2, Color.rgb(255,
//                                                0,
//                                                0)))
//                                        .radius((int) (Collections.min(lists) * 78));
//
//                                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                                mBaiduMap.addOverlay(ooCircleaMin);
//                            }
//                            //??????
////                    LatLng llDot = new LatLng(39.90923, 116.447428);
//                            OverlayOptions ooDot = new DotOptions().center(positionA).radius(6).color(0xFF0000FF);
//                            mBaiduMap.addOverlay(ooDot);
//                            //??????Marker??????
//                            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                    .fromResource(R.drawable.jizhan1);
//                            //??????MarkerOption???????????????????????????Marker
//
//
//                            extraInfo.putString("lat", String.valueOf(positionA.latitude));
//                            extraInfo.putString("lon", String.valueOf(positionA.longitude));
//
//                            extraInfo.putString("address", address_reverseGeoCodeResult);
//                            OverlayOptions optiona = new MarkerOptions()
//                                    .anchor(10, 30)
//                                    .extraInfo(extraInfo)
//                                    .position(positionA) //????????????
//                                    .perspective(true)
//                                    .icon(bitmap) //????????????
//                                    .draggable(true)
//                                    .draggable(true)
//                                    //?????????????????????????????????????????????????????????
//                                    .flat(true)
//                                    .alpha(0.5f);
//                            //??????????????????Marker????????????
//                            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//??????????????????????????????  Marker marker
//                            //????????????
//                            if (fugaiFlag == true) {
//                                String tas = extraInfo.getString("Radius");
//                                int is = Integer.parseInt(tas);
//                                OverlayOptions ooCirclefugai = new CircleOptions()
////                            .fillColor(0x000000FF)
//                                        .fillColor(Color.argb(40, 255,
//                                                165,
//                                                0))
//                                        .center(positionA)
//                                        .stroke(new Stroke(2, Color.rgb(255,
//                                                165,
//                                                0)))
//                                        .radius(is);
//                                mBaiduMap.addOverlay(ooCirclefugai);
//                            }
//
//                            //????????????
//                            int adCode = reverseGeoCodeResult.getCityCode();
//                        }
//                    }
//                });
//                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(positionA).radius(1000).newVersion(1));
//
//
//            }
//
//            //???Marker???????????????????????????????????? ??????Marker??????????????????getPosition()????????????
//            //marker ????????????Marker??????
//            @Override
//            public void onMarkerDragStart(Marker marker) {
//
//            }
//        });
//
//        ViewLoading.dismiss(mContext);
//
//        mBaiduMap.setIndoorEnable(true);//???????????????????????????????????????
//        mBaiduMap.setOnBaseIndoorMapListener(new BaiduMap.OnBaseIndoorMapListener() {
//            @Override
//            public void onBaseIndoorMapMode(boolean on, MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {
//                if (on) {
//                    // ???????????????
//                    // ???????????????????????? mapBaseIndoorMapInfo ????????????????????????
//                    //?????????????????????????????????ID???
//                } else {
//                    // ???????????????
//                }
//            }
//        });
//    }
//    private void init(View view) {
//        mapUtil = MapUtil.getInstance();
//        mapUtil.init((MapView) view.findViewById(R.id.bmapView));
//        mapUtil.setCenter(mCurrentDirection);//?????????????????????
//
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                    Message message = new Message();
//                    message.what=3;
//                    handler.sendMessage(message);
//                    handler.postDelayed(this, 5000);
//            }
//        });
//        trackPoints = new ArrayList<>();
//    }
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void setMapMakerView(final Marker marker) {
//        final Bundle extraInfo = marker.getExtraInfo();
//        if (!TextUtils.isEmpty(extraInfo.getString("address"))) {
//            String address = extraInfo.getString("address");
//            Log.d(TAG, "onMarkerClick: " + address);
//            final String Mylatitude = marker.getPosition().latitude + "";
//            final String Mylongitude = marker.getPosition().longitude + "";
//
//            View view = View.inflate(mContext, R.layout.activity_map_info, null);
//            TextView tv_title = view.findViewById(R.id.tv_title);
//            String str = "";
////            if(null==extraInfo.getString("mnc")){
////                return;
////            }
//            if (extraInfo.getString("mnc")!=null&&extraInfo.getString("mnc").equals("0")) {
//                str = "??????";
//            } else if (extraInfo.getString("mnc")!=null&&extraInfo.getString("mnc").equals("1")) {
//                str = "??????";
//            } else if (extraInfo.getString("mnc")!=null&&extraInfo.getString("mnc").equals("11")) {
//                str = "??????";
//            } else if (extraInfo.getString("mnc")!=null&&TextUtils.isEmpty(extraInfo.getString("mnc"))) {//?????????cdma?????? sid??????
//                str = "";
//                TextView tv_sid = view.findViewById(R.id.tv_sid);
//                tv_sid.setText(extraInfo.getString("sid"));
//                LinearLayout llsid = view.findViewById(R.id.llsid);
//                llsid.setVisibility(View.VISIBLE);
//            } else {
//                str = "CDMA";
//                TextView tv_sid = view.findViewById(R.id.tv_sid);
//                tv_sid.setText(extraInfo.getString("sid"));
//                LinearLayout llsid = view.findViewById(R.id.llsid);
//                llsid.setVisibility(View.VISIBLE);
//            }
//            if (extraInfo.get("resources").equals("????????????")) {//??????????????????????????? band pci ???????????? down
//                LinearLayout ll_types = view.findViewById(R.id.ll_types);
//                LinearLayout ll_pci = view.findViewById(R.id.ll_pci);
//                LinearLayout ll_band = view.findViewById(R.id.ll_band);
//                LinearLayout ll_down = view.findViewById(R.id.ll_down);
//                ll_types.setVisibility(View.VISIBLE);
//                ll_pci.setVisibility(View.VISIBLE);
//                ll_band.setVisibility(View.VISIBLE);
//                ll_down.setVisibility(View.VISIBLE);
//
//                TextView tv_band = view.findViewById(R.id.tv_band);
//                TextView tv_types = view.findViewById(R.id.tv_types);
//                TextView tv_pci = view.findViewById(R.id.tv_pci);
//                TextView tv_down = view.findViewById(R.id.tv_down);
//                tv_band.setText(extraInfo.get("band") + "");
//                tv_types.setText(extraInfo.get("types") + "");
//                tv_pci.setText(extraInfo.get("pci") + "");
//                tv_down.setText(extraInfo.get("down") + "");
//
//
//            }
////            aaa
//            tv_title.setText(str + "");
//            TextView tv_fugai = view.findViewById(R.id.tv_fugai);
//            tv_fugai.setText(extraInfo.getString("Radius") + "");
//            Log.d(TAG, "setMapMakerViaew: " + extraInfo.getString("Radius"));
//            TextView tv_mnc = view.findViewById(R.id.tv_mnc);
//            tv_mnc.setText(extraInfo.getString("mnc"));
//            TextView tv_lac = view.findViewById(R.id.tv_lac);
//            tv_lac.setText(extraInfo.getString("lac"));
//            TextView tv_cid = view.findViewById(R.id.tv_cid);
//            tv_cid.setText(extraInfo.getString("ci"));
//            TextView tv_address = view.findViewById(R.id.tv_address);
//            tv_address.setText(extraInfo.getString("address"));
//            TextView tv_lat_lon = view.findViewById(R.id.tv_lat_lon);
//
//            DecimalFormat df = new DecimalFormat(".######");
//            final String lats = extraInfo.getString("lat");
//            final String lons = extraInfo.getString("lon");
//            double dlat = Double.parseDouble(lats);
//            double dlons = Double.parseDouble(lons);
//            tv_lat_lon.setText("??????:" + df.format(dlat) + ",??????:" + df.format(dlons));
//
//            TextView tv_resources = view.findViewById(R.id.tv_resources);
//
//            tv_resources.setText(extraInfo.getString("resources"));
//            ImageButton bt_openMap = view.findViewById(R.id.bt_openMap);
//            Button bt_quanjing = view.findViewById(R.id.bt_quanjing);
//            bt_m_locations = view.findViewById(R.id.bt_m_location);//??????????????????
//            if (extraInfo.get("type").equals("1")) {
//                bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
//            } else {
//                bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojinglan1));
//            }
//            bt_m_locations.setVisibility(View.GONE);//????????? ????????????
//            bt_m_locations.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "extraInfoidonClick: " + extraInfo.getString("id"));
//
//                    new CircleDialog.Builder()
//                            .setWidth(0.5f)
//                            .setMaxHeight(0.5f)
//                            .setTitle("")
//                            .setText("??????????????????????????????")
//                            .setTitleColor(Color.parseColor("#00acff"))
//                            .setNegative("??????", new Positiv(4, extraInfo))
//                            .setPositive("??????", null)
//                            .show(getFragmentManager());
//                }
//            });
//            final Button bt_taset = view.findViewById(R.id.bt_taset);//TA???????????????
//            bt_taset.setVisibility(View.VISIBLE);
//            bt_taset.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", String.valueOf(extraInfo.get("id")));
//                    Intent intent = new Intent(mContext, TaActivity.class);
//                    intent.putExtras(bundle);
////                    startActivity(intent);
//                    startActivityForResult(intent, 13);
//                }
//            });
//            Button bt_m_dele = view.findViewById(R.id.bt_m_dele);
//            bt_m_dele.setVisibility(View.VISIBLE);
//            if (extraInfo.get("type").equals("2")) {
//                bt_m_dele.setBackground(getResources().getDrawable(R.mipmap.markeradd));
//                bt_taset.setVisibility(View.GONE);
//                bt_m_locations.setVisibility(View.GONE);
//            }
//            bt_m_dele.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    if (extraInfo.get("type").equals("2")) {//?????????????????????
//                        listMarker.clear();
//                        demoAdapteradd = new DemoAdapteradd(listMarker, addcallBack);
//                        Myshow.show(mContext, extraInfo, dbManagerJZ, mycallback, addcallBack, listMarker, demoAdapteradd);
//
//
//                    } else {
//                        try {
//                            String dele = extraInfo.getString("id");
//                            new CircleDialog.Builder()
//                                    .setTitle("")
//                                    .setWidth(0.7f)
//                                    .setMaxHeight(0.7f)
//                                    .setText("????????????????????????")
//                                    .setTitleColor(Color.parseColor("#00acff"))
//                                    .setNegative("??????", new Positiv(3, dele))
//                                    .setPositive("??????", null)
//                                    .show(getFragmentManager());
//
//                        } catch (Exception e) {
//                            Log.d(TAG, "panderonClick: " + "3" + e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            });
//            ImageButton iv_finishs = view.findViewById(R.id.iv_finishs);
//            iv_finishs.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mBaiduMap.hideInfoWindow();
//
//                }
//            });
//            bt_openMap.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//
//                    //???????????????
//
//                    dialog2 = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//                    //????????????????????????
//                    inflate = LayoutInflater.from(mContext).inflate(R.layout.item_dibushowdaohao, null);
//                    //???????????????
//                    TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
////        choosePhoto.setVisibility(View.GONE);
//                    TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
////                            baidu.setOnClickListener(new MyonclickXian(mylag));
//                    baidu.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                LatLng startLatLng = new LatLng(39.940387, 116.29446);
//                                LatLng endLatLng = new LatLng(39.87397, 116.529025);
//                                String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
//                                                "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
//                                        marker.getPosition().latitude, marker.getPosition().longitude);
//                                Intent intent = new Intent();
//                                intent.setData(Uri.parse(uri));
//                                startActivity(intent);
//                            } catch (ActivityNotFoundException e) {
////                    ToastUtil.showShort(this, "?????????????????????");
////                                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
//                                MyToast.showToast("?????????????????????");
//                            }
//                        }
//                    });
//                    gaode.setOnClickListener(new MyonclickXian(mylag, String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude), (Activity) mContext));
//                    //??????????????????Dialog
//                    dialog2.setContentView(inflate);
//                    //????????????Activity???????????????
//                    Window dialogWindow = dialog2.getWindow();
//                    //??????Dialog?????????????????????
//                    dialogWindow.setGravity(Gravity.BOTTOM);
//                    //?????????????????????
//                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                    lp.y = 20;//??????Dialog?????????????????????
////       ????????????????????????
//                    dialogWindow.setAttributes(lp);
//                    dialog2.show();//???????????????
//                }
//            });
//            bt_quanjing.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, PanoramaDemoActivityMain.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("lat", String.valueOf(marker.getPosition().latitude));
//                    bundle.putString("lon", String.valueOf(marker.getPosition().longitude));
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//            final InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
//            mBaiduMap.showInfoWindow(mInfoWindow);
//        } else {
//            //????????????????????????
//
//            final LatLng sl = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
//            String address = extraInfo.getString("address");
//            Log.d(TAG, "onMarkerClick: " + address);
//            final String Mylatitude = marker.getPosition().latitude + "";
//            final String Mylongitude = marker.getPosition().longitude + "";
//
//            final View view = View.inflate(mContext, R.layout.activity_map_info, null);
//            TextView title = view.findViewById(R.id.title);
//            title.setText("???????????????");
//            TextView tv_mnc = view.findViewById(R.id.tv_mnc);
//            tv_mnc.setText("");
//            TextView tv_lac = view.findViewById(R.id.tv_lac);
//            tv_lac.setText("");
//            TextView tv_cid = view.findViewById(R.id.tv_cid);
//            tv_cid.setText("");
//            final TextView tv_address = view.findViewById(R.id.tv_address);
////            tv_address.setText(extraInfo.getString("address"));
//            TextView tv_lat_lon = view.findViewById(R.id.tv_lat_lon);
//            ImageButton iv_finishs = view.findViewById(R.id.iv_finishs);
//            Button bt_m_location = view.findViewById(R.id.bt_m_location);//??????????????????
//            bt_m_location.setVisibility(View.GONE);
//            iv_finishs.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mBaiduMap.hideInfoWindow();
//                }
//            });
//            mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
//                @Override
//                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
//
//                }
//
//                @Override
//                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                    if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                        //????????????????????????
//                        Log.d(TAG, "onGetReverseGeoCodeResult: " + "????????????????????????");
//                        Log.d(TAG, "onMarkerDragEnd: " + sl);
//                        tv_address.setText("???");
//                        return;
//                    } else {
//                        //????????????
//                        address_reverseGeoCodeResult = reverseGeoCodeResult.getAddress();
//                        tv_address.setText(address_reverseGeoCodeResult);
//                    }
//                }
//            });
//            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(sl).radius(1000).newVersion(1));
//
//
//            DecimalFormat df = new DecimalFormat(".######");
//            final String lats = extraInfo.getString("lat");
//            final String lons = extraInfo.getString("lon");
//            double dlat = marker.getPosition().latitude;
//            double dlons = marker.getPosition().longitude;
//            tv_lat_lon.setText("??????:" + df.format(dlat) + ",??????:" + df.format(dlons));
//            ImageButton bt_openMap = view.findViewById(R.id.bt_openMap);
//            Button bt_quanjing = view.findViewById(R.id.bt_quanjing);
//
//            bt_openMap.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //???????????????
//
//                    dialog2 = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//                    //????????????????????????
//                    inflate = LayoutInflater.from(mContext).inflate(R.layout.item_dibushowdaohao, null);
//                    //???????????????
//                    TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
////        choosePhoto.setVisibility(View.GONE);
//                    TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
////                            baidu.setOnClickListener(new MyonclickXian(mylag));
//                    baidu.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                LatLng startLatLng = new LatLng(39.940387, 116.29446);
//                                LatLng endLatLng = new LatLng(39.87397, 116.529025);
//                                String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
//                                                "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
//                                        marker.getPosition().latitude, marker.getPosition().longitude);
//                                Intent intent = new Intent();
//                                intent.setData(Uri.parse(uri));
//                                startActivity(intent);
//                            } catch (ActivityNotFoundException e) {
////                    ToastUtil.showShort(this, "?????????????????????");
////                                Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
//                                MyToast.showToast("?????????????????????");
//                            }
//                        }
//                    });
//
//                    gaode.setOnClickListener(new MyonclickXian(sl, Mylatitude, Mylongitude, (Activity) mContext));
//                    //??????????????????Dialog
//                    dialog2.setContentView(inflate);
//                    //????????????Activity???????????????
//                    Window dialogWindow = dialog2.getWindow();
//                    //??????Dialog?????????????????????
//                    dialogWindow.setGravity(Gravity.BOTTOM);
//                    //?????????????????????
//                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                    lp.y = 20;//??????Dialog?????????????????????
////       ????????????????????????
//                    dialogWindow.setAttributes(lp);
//                    dialog2.show();//???????????????
//                }
//            });
//            bt_quanjing.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, PanoramaDemoActivityMain.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("lat", String.valueOf(marker.getPosition().latitude));
//                    bundle.putString("lon", String.valueOf(marker.getPosition().longitude));
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//            final InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
//            mBaiduMap.showInfoWindow(mInfoWindow);
////??????InfoWindow
////point ??????????????????
////-100 InfoWindow?????????point???y???????????????
//            final InfoWindow mInfoWindows = new InfoWindow(view, marker.getPosition(), -47);
//
////???InfoWindow??????
//            mBaiduMap.showInfoWindow(mInfoWindows);
//        }
//    }
//
//
//    //?????????????????????
//    @Override
//    public void onSensorChanged(SensorEvent sensorEvent) {
//        //?????????????????????????????????????????????????????????????????????????????????
//        double x = sensorEvent.values[SensorManager.DATA_X];
//        if (Math.abs(x - lastX) > 1.0) {// ??????????????????1?????????????????????????????????????????????????????????
//            mCurrentDirection = (int) x;
//            if (!CommonUtil.isZeroPoint(CurrentLocation.latitude, CurrentLocation.longitude)) {
//                mapUtil.updateMapLocation(new LatLng(CurrentLocation.latitude, CurrentLocation.longitude), (float) mCurrentDirection);
//            }
//        }
//        lastX = x;
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int i) {
//    }
//
//
//    /**
//     * @param
//     */
//    private class MyonclickXian implements View.OnClickListener {
//        private String lon;
//        private String lat;
//        private LatLng mylag;
//
//        public MyonclickXian(LatLng mylag) {
//            this.mylag = mylag;
//
//        }
//
//        public MyonclickXian(LatLng mylag, String lat, String lon, Activity activity) {
//            this.mylag = mylag;
//            this.lat = lat;
//            this.lon = lon;
//        }
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.baidu:
//                    try {
//                        LatLng startLatLng = new LatLng(39.940387, 116.29446);
//                        LatLng endLatLng = new LatLng(39.87397, 116.529025);
//                        String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
//                                        "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
//                                lat, lon);
//                        Intent intent = new Intent();
//                        intent.setData(Uri.parse(uri));
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
////                    ToastUtil.showShort(this, "?????????????????????");
////                        Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
//                        MyToast.showToast("?????????????????????");
//                    }
//                    dialog2.dismiss();
//                    break;
//
//                case R.id.gaode://??????
//
//                    try {
////                    double gdLatitude = 39.92848272
////                    double gdLongitude = 116.39560823
//                        //?????????????????????????????????????????????????????????????????????
//                        //?????????????????????????????????????????????????????????????????????
//                        LatLng latLngs = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
//                        CoordinateConverter converter = new CoordinateConverter()
//                                .from(BD09LL)
//                                .coord(latLngs);
//
////????????????
//                        LatLng desLatLng = converter.convert();
//
//                        String uri = String.format("amapuri://route/plan/?dlat=%s&dlon=%s&dname=??????&dev=0&t=0",
//                                desLatLng.latitude, desLatLng.longitude);
//                        Intent intent = new Intent();
//                        intent.setAction("android.intent.action.VIEW");
//                        intent.addCategory("android.intent.category.DEFAULT");
//                        intent.setData(Uri.parse(uri));
//                        intent.setPackage("com.autonavi.minimap");
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
////                        Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
//                        MyToast.showToast("?????????????????????");
//                    }
//
//                    dialog2.dismiss();
//                    break;
//            }
//        }
//    }
//
//    //????????????
//    class Positiv implements View.OnClickListener {
//        private final int t;
//        private Bundle extraInfo;
//        private String dele;
//
//        public Positiv(int t) {
//            this.t = t;
//        }
//
//        public Positiv(int t, String dele) {
//            this.t = t;
//            this.dele = dele;
//        }
//
//        public Positiv(int t, Bundle extraInfo) {
//            this.t = t;
//            this.extraInfo = extraInfo;
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//        @Override
//        public void onClick(View v) {
//            if (t == 2) {
//                guijistart = false;
//
//                Log.d(TAG, "guijistart: " + guijistart);
//                //??????????????????
////                if (guijiFlag == true) {
////                    guijiFlag = false;
//                    Log.d(TAG, "guijiFlagonClick: " + "?????????");
////                    iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_1));
//                    ib_gj.setBackground(getResources().getDrawable(R.mipmap.gj));
//                    mMapView.showZoomControls(false);
//                    mBaiduMap.clear();
////                    MyUtils.Viewjizhan(markerMy, mBaiduMap, dataBean);
//                    if (timer != null) {
//                        timer.cancel();
//                    }
//                    initdatas2();
////                }
//                try {
//                    dbManagerGuijiView.deleteall();
//                    if (guijistart == true) {
//                        GuijiViewBean guijiViewBean = new GuijiViewBean();
//                        if (mylag != null) {
//                            guijiViewBean.setLon(mylag.longitude);
//                            guijiViewBean.setLat(mylag.latitude);
//                        }
//                        try {
//                            dbManagerGuijiView.insertStudent(guijiViewBean);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (t == 1) {//???1??????????????????
//                if (points != null) {
//                    points.clear();
//                    //????????????
//                    Log.d(TAG, "setmapJuli:????????????");
//                    juliFlage = false;
//                    ib_cl.setBackground(getResources().getDrawable(R.mipmap.measure));
//                    initdatas2();
//                } else {
//
//                }
//
////                Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
//
////                MyUtils.Viewjizhan(markerMy, mBaiduMap, dataBean);
//            } else {
//                Log.d(TAG, "onClick: ????????????????????????????????????????????????");
//            }
//            if (t == 13) {//????????????
//                try {
//                    DBManagerGuijiView guijiView = new DBManagerGuijiView(mContext);
//                    guijiView.deleteall();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                ib_gj.setBackground(getResources().getDrawable(R.mipmap.gj));
//                isGj=false;
//            }
//            if (t == 3) {
//
//                int id = 0;
//                try {
//                    id = dbManagerJZ.deleteGuanyu(dele);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                if (id == 1) {
////                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    initdatas();
//                    Log.d(TAG, "panderonClick: " + "1");
//                } else {
////                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    Log.d(TAG, "panderonClick: " + "2");
//                }
//            }
//            if (t == 4) {
//                try {
//                    GuijiViewBeanjizhan guijiViewBeanjizhan = dbManagerJZ.forid(Integer.parseInt(extraInfo.getString("id")));
//                    guijiViewBeanjizhan.setType(1);
//                    int i = dbManagerJZ.updateType(guijiViewBeanjizhan);
////                        ??????
//                    if (guijiFlag == true) {
//                        MyToast.showToast("?????????????????????");
////                        Toast.makeText(MainActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    if (juliFlage == true) {
//                        MyToast.showToast("????????????????????????");
////                        Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//
//                    if (i == 1) {
//                        MyToast.showToast("????????????????????????");
////                        Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                        bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
//                        initdatas();//??????????????????
//                        startJb();//????????????
////                            mBaiduMap.hideInfoWindow();
//                    } else {
//                        MyToast.showToast("??????????????????");
////                        Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    }
////                        Log.d(TAG, "guijiViewBeanjizhanonClick: " + guijiViewBeanjizhan + "?????????????????????" + dbManagerJZ.guijiViewBeans());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void startJb() {
//        jingbaoflag = true;//??????
//        bt_jingbao.setTextColor(Color.RED);
//        Log.d(TAG, "setjingbao: " + jingbaoflag);
//        ib_baojing.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
////
//        int times = 4000;
//        if (!TextUtils.isEmpty(ACacheUtil.getbaojingtime())) {
//            String getguitime = ACacheUtil.getbaojingtime();
//            int i = Integer.parseInt(getguitime + "");
//            times = 1000 * i;
//        } else {
//            times = 4000;
//        }
//        timer2 = new Timer();
//        timer2.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("Timer is running");
//                Message message = new Message();
//                handler.sendMessage(message);
//                message.what = 2;
//                Log.d(TAG, "handlerrun: " + 1);
//            }
//        }, 0, times);
////        aaaa
//    }
//    public void initdatas() {
//        //????????????????????????
//
//        List<GuijiViewBeanjizhan> resultBeans = null;
//        try {
//            mBaiduMap.clear();
//            resultBeans = dbManagerJZ.guijiViewBeans();
//            Log.d(TAG, "????????????resultBeansonCreate: " + resultBeans);
//            Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
//            if (resultBeans.size() > 0 && resultBeans != null) {
//                mBaiduMap.clear();
//                DataAll(resultBeans);
//            } else {
//                Log.d(TAG, "initdatas: aa" + "1111");
////                Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
////                MyToast.showToast("??????????????????");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void initdatas2() {
//        //????????????????????????
//
//        List<GuijiViewBeanjizhan> resultBeans = null;
//        try {
//            mBaiduMap.clear();
//            resultBeans = dbManagerJZ.guijiViewBeans();
//            Log.d(TAG, "????????????resultBeansonCreate: " + resultBeans);
//            Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
//            if (resultBeans.size() > 0 && resultBeans != null) {
//                mBaiduMap.clear();
//                DataAll(resultBeans);
//            } else {
////                Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //?????????????????????  //????????????
//    private void DataAll(List<GuijiViewBeanjizhan> resultBeans) {
//        for (GuijiViewBeanjizhan resultBean : resultBeans) {
//            Log.e("DataAll", "DataeAllffffffffff: "+resultBean.getTa());
//        }
//        mBaiduMap.clear();
//        for (int i = 0; i < resultBeans.size(); i++) {
//            Bundle bundle = new Bundle();
//            bundle.putString("band", resultBeans.get(i).getBand() + "");
//            bundle.putString("types", resultBeans.get(i).getTypes() + "");
//            bundle.putString("pci", resultBeans.get(i).getPci() + "");
//            bundle.putString("down", resultBeans.get(i).getDown() + "");
//            //????????????
//            bundle.putString("id", resultBeans.get(i).getId() + "");
//            bundle.putString("address", resultBeans.get(i).getAddress());
//            bundle.putString("ci", resultBeans.get(i).getCi());
//            bundle.putString("mcc", resultBeans.get(i).getMcc());
//            bundle.putString("mnc", resultBeans.get(i).getMnc());
//            bundle.putString("type", resultBeans.get(i).getType() + "");
//            bundle.putString("Radius", resultBeans.get(i).getRadius() + "");
//            bundle.putString("lac", resultBeans.get(i).getLac());
//            bundle.putString("lat", resultBeans.get(i).getLat());
//            bundle.putString("lon", resultBeans.get(i).getLon());
//            bundle.putString("ta", resultBeans.get(i).getTa());
//            bundle.putString("sid", resultBeans.get(i).getSid());
//            bundle.putString("resources", resultBeans.get(i).getResources() + "");
//            String latresult = resultBeans.get(i).getLat();
//            String lonresult = resultBeans.get(i).getLon();
//            LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//            Log.d(TAG, "dataBeanisShowjizhan: " + resultBeans);
////            LatLng latLngresult = new LatLng(Double.parseDouble("38.031242"), Double.parseDouble("114.450186"));
//            //????????????????????????  //  ?????????????????????????????????????????????
//
////            if (resultBeans.get(i).getResources().equals("????????????")) {
////
////            }
//            CoordinateConverter converter = new CoordinateConverter()
//                    .from(CoordinateConverter.CoordType.GPS)
//                    .coord(latLngresult);
//            //????????????
//            LatLng desLatLngBaidu = new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon()));
//            LatLng desLatLngBaiduD = new LatLng(Double.parseDouble(resultBeans.get(i).getLat()) - 0.00002, Double.parseDouble(resultBeans.get(i).getLon()));
//
//
////??????TextOptions??????
//            OverlayOptions mTextOptions = new TextOptions()
//                    .text(resultBeans.get(i).getLac() + "-" + resultBeans.get(i).getCi()) //????????????
////                    .bgColor(getResources().getColor(R.color.color_f25057)) //?????????
//                    .fontSize(32) //??????
//                    .fontColor(getResources().getColor(R.color.color_f25057)) //????????????
//                    .rotate(0) //????????????
//                    .position(desLatLngBaiduD);
//
////?????????????????????????????????
//            Overlay mText = mBaiduMap.addOverlay(mTextOptions);
//
//
//            //?????????
//            int TAS = 78;
//            String ta = "";
//            if (TextUtils.isEmpty(resultBeans.get(i).getTa() + "")) {
//                ta = "1";
//            } else {
//                ta = resultBeans.get(i).getTa() + "";
//            }
//            List<Double> lists = MyUtils.StringTolist(resultBeans.get(i).getTa());
//            Double sum = Double.valueOf(0);
//            for (int j = 0; j < lists.size(); j++) {
//                sum += lists.get(j);
//            }
//            //???????????????
//            int size = lists.size();
//            double sum_db = sum;
//            double size_db = size;
//            double Myradius_db = sum_db / size_db * 78;
//
//            Log.d(TAG, "DataAlla: " + sum_db + "--" + size_db);
//            Log.d(TAG, "DataAll: aaa0" + sum_db / size_db * 78);
//            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
//            int Myradius = new Double(Myradius_db).intValue();
//            Log.d(TAG, "JingBaos:Myradius " + Myradius);
//            if (UNIFORMTA == true) {
//                OverlayOptions ooCirclepingjun = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(40, 255,
//                                0,
//                                0))
//                        .center(desLatLngBaidu)
//                        .stroke(new Stroke(5, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius((int) Myradius_db);
//                mBaiduMap.addOverlay(ooCirclepingjun);
//                Log.e(TAG, "DataAll:?????? " + Collections.max(lists));
//                Log.d(TAG, "DataAlla:pingjun" + (int) Myradius_db);
//            }
//
//            //??????ta???
//            if (MAXTA == true) {
//                OverlayOptions ooCircleaMAx = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(0, 255,
//                                0,
//                                0))
//                        .center(desLatLngBaidu)
//                        .stroke(new Stroke(2, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius((int) (Collections.max(lists) * 78));
//                Log.d(TAG, "DataAlla:MAXTA" + (int) (Collections.max(lists) * 78));
//                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                mBaiduMap.addOverlay(ooCircleaMAx);
//            }
//            Log.d(TAG, "DataAllsum: " + sum);
//            //??????ta???
//            if (MINTA == true) {
//                OverlayOptions ooCircleaMin = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(0, 255,
//                                0,
//                                0))
//                        .center(desLatLngBaidu)
//                        .stroke(new Stroke(2, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius((int) (Collections.min(lists) * 78));
//                Log.d(TAG, "DataAlla:min" + (int) (Collections.min(lists) * 78));
//                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                mBaiduMap.addOverlay(ooCircleaMin);
//            }
//            Log.d(TAG, "listDataAll: " + lists.get(0));
//
//            //??????
////                    LatLng llDot = new LatLng(39.90923, 116.447428);
//            OverlayOptions ooDot = new DotOptions().center(desLatLngBaidu).radius(6).color(0xFF0000FF);
//            mBaiduMap.addOverlay(ooDot);
//            //??????Marker??????
//            BitmapDescriptor bitmap;
//            if (resultBeans.get(i).getType() == 1) {
//                bitmap = BitmapDescriptorFactory
//                        .fromResource(R.drawable.jizhan2);
//            } else {
//                bitmap = BitmapDescriptorFactory
//                        .fromResource(R.drawable.jizhan1);
//            }
//
//
//            //??????MarkerOption???????????????????????????Marker
//            OverlayOptions optiona = new MarkerOptions()
//                    .anchor(10, 30)
//                    .extraInfo(bundle)
//                    .position(desLatLngBaidu) //????????????
//                    .perspective(true)
//                    .icon(bitmap) //????????????
//                    .draggable(true)
//                    .draggable(true)
//                    //?????????????????????????????????????????????????????????
//                    .flat(true)
//                    .alpha(0.5f);
//            //??????????????????Marker????????????
//            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//??????????????????????????????  Marker marker
//
//            //????????????
//            String tas = resultBeans.get(i).getRadius();
//            int is = 0;
//            if (!TextUtils.isEmpty(tas)) {
//                is = Integer.parseInt(tas);
//            }
//
////            int iss = 0;
////            String tass = resultBeans.get(i).getRadius();
////            if (!TextUtils.isEmpty(tass)) {
////                is = Integer.parseInt(tas);
////            }
//
//
//            if (fugaiFlag == true) {
//
//                OverlayOptions ooCirclefugai = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(40, 255,
//                                165,
//                                0))
//                        .center(desLatLngBaidu)
//                        .stroke(new Stroke(2, Color.rgb(255,
//                                165,
//                                0)))
//                        .radius(is);
//                mBaiduMap.addOverlay(ooCirclefugai);
//
//            }
//            if (!TextUtils.isEmpty(ACacheUtil.getFugaiKG())) {
//                int kg = Integer.parseInt(ACacheUtil.getFugaiKG());
//                if (kg == 0) {//0 ?????????????????? ???????????????
//                    OverlayOptions ooCirclefugai = new CircleOptions()
////                            .fillColor(0x000000FF)
//                            .fillColor(Color.argb(40, 255,
//                                    165,
//                                    0))
//                            .center(desLatLngBaidu)
//                            .stroke(new Stroke(2, Color.rgb(255,
//                                    165,
//                                    0)))
//                            .radius(is);
//                    mBaiduMap.addOverlay(ooCirclefugai);
//                }
//            }
//
//        }
//
//
//    }
//
//
//
//
//
//
//
//
//
//
//    //???????????????
//    private void initMap() {
//        if (mylag != null) {
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(mylag).zoom(18.0f);
//            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            ViewLoading.dismiss(mContext);
//        }
//
//    }
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void findView(View view) {
//        bt_jizhan = view.findViewById(R.id.bt_jizhan);
//        bt_jizhan.setOnClickListener(this);//???????????????
//        bt_qiehuan = view.findViewById(R.id.bt_qiehuan);
//        bt_qiehuan.setOnClickListener(this);//???????????????
//        bt_clear = view.findViewById(R.id.bt_clear);
//        bt_clear.setOnClickListener(this);
//        bt_location = view.findViewById(R.id.bt_location);
//        bt_location.setOnClickListener(this);
//        bt_jt = view.findViewById(R.id.bt_jt);
//        bt_jt.setOnClickListener(this);
//        bt_hot = view.findViewById(R.id.bt_hot);
//        bt_hot.setOnClickListener(this);
//        bt_juli = view.findViewById(R.id.bt_juli);
//        bt_juli.setOnClickListener(this);
//        bt_jingbao =view. findViewById(R.id.bt_jingbao);
//        bt_jingbao.setOnClickListener(this);
//        bt_jia = view.findViewById(R.id.bt_jia);
//        bt_jia.setOnClickListener(this);
//        bt_jian = view.findViewById(R.id.bt_jian);
//        bt_jian.setOnClickListener(this);
//        //???Ui???View ID
//        ll_qiehuan = view.findViewById(R.id.ll_qiehuan);
//        ll_qiehuan.setOnClickListener(this);//?????????????????????
//        ll_clear =view. findViewById(R.id.ll_clear);
//        ll_clear.setOnClickListener(this);//?????????????????????
//        ll_jt = view.findViewById(R.id.ll_jt);
//        ll_jt.setOnClickListener(this);//?????????????????????
//        ll_rl =view. findViewById(R.id.ll_rl);
//        ll_rl.setOnClickListener(this);//????????????????????????
//        ll_baojing = view.findViewById(R.id.ll_baojing);
//        ll_baojing.setOnClickListener(this);//?????????????????????
////        Button
//        bt_uilocation =view. findViewById(R.id.bt_uilocation);//????????????
//        bt_uilocation.setOnClickListener(this);
//        bt_uilocation.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Log.d(TAG, "onLongClick: " + "zoule");
//                if (MapinitFlag == false) {
//
//                    LocationFragment.this.Mapinit(2);
//                    MapinitFlag = true;
////                    Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
//                    MyToast.showToast("?????????????????????");
//                } else if (MapinitFlag = true) {
//                    LocationFragment.this.Mapinit(1);
//                    MapinitFlag = false;
//                    MyToast.showToast("?????????????????????");
////                   Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
//                }
//
//                return false;
//            }
//        });
//        ll_cl = view.findViewById(R.id.ll_cl);//??????
//        ib_cl = view.findViewById(R.id.ib_cl);//??????
//        ll_cl.setOnClickListener(this);
//
//        ll_gj = view.findViewById(R.id.ll_gj);//??????
//        ib_gj = view.findViewById(R.id.ib_gj);//??????
//        ll_gj.setOnClickListener(this);
//
//
//
//
//        bt_uiceliang = view.findViewById(R.id.bt_uiceliang);//??????
//        bt_uiceliang.setOnClickListener(this);
//        bt_uisearch = view.findViewById(R.id.bt_uisearch);//??????
//        bt_uisearch.setOnClickListener(this);
//        bt_jizhan0 = view.findViewById(R.id.bt_jizhan0);
//        bt_jizhan0.setOnClickListener(this);
//        //ImageButton
////        ib_qiehuan, ib_clear,ib_jt,ib_rl,ib_baojing
//        ib_qiehuan = view.findViewById(R.id.ib_qiehuan);
//        ib_clear = view.findViewById(R.id.ib_clear);
//        ib_jt = view.findViewById(R.id.ib_jt);
//        ib_rl = view.findViewById(R.id.ib_rl);
//        ib_baojing = view.findViewById(R.id.ib_baojing);
//        ib_screen = view.findViewById(R.id.ib_screen);
//        iv_gjstart = view.findViewById(R.id.iv_gjstart);
//        iv_viewstart =view. findViewById(R.id.iv_viewstart);
//        ib_gensui =view.findViewById(R.id.ib_gensui);
//        ib_fugai =view. findViewById(R.id.ib_fugai);
//
//        //???????????????????????????
//        bt_text = view.findViewById(R.id.bt_text);//- ?????? ?????????
//        bt_text.setOnClickListener(this);//-
//        bt_ikan = view.findViewById(R.id.bt_ikan);//-
//        bt_ikan.setOnClickListener(this);
//        bt_uiceliangclear =view. findViewById(R.id.bt_uiceliangclear);
//        bt_uiceliangclear.setOnClickListener(this);//????????????????????????
//        iv_set = view.findViewById(R.id.iv_set);//-
//        iv_set.setOnClickListener(this);
//        ll_guijis = view.findViewById(R.id.ll_guijis);
//        ll_guijis.setOnClickListener(this);//??????????????????
//        ll_gjview = view.findViewById(R.id.ll_gjview);
//        ll_gjview.setOnClickListener(this);//?????????????????????
//        ll_gjclear = view.findViewById(R.id.ll_gjclear);
//        ll_gjclear.setOnClickListener(this);//???????????????
//
//        ll_gensui = view.findViewById(R.id.ll_gensui);
//        ll_gensui.setOnClickListener(this);
//        ll_fugai = view.findViewById(R.id.ll_fugai);
//        ll_fugai.setOnClickListener(this);
//        ll_screen =view. findViewById(R.id.ll_screen);//????????????????????????
//        ll_screen.setOnClickListener(this);
//        tv_screen =view. findViewById(R.id.tv_screen);//???????????????????????????????????????
////        Mapinit(1);//???????????????????????????
//
//        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);//???????????????????????????
//
////        if (i == 1) {
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//?????????
////        }
////        if (i == 2) {
////            mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//?????????
////        }
//        // ??????????????????
//        //????????????enable???true???false ???????????????????????????
//
//        mBaiduMap.setMyLocationEnabled(true);
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker));
//        MapStatus.Builder builder1 = new MapStatus.Builder();
//        builder1.overlook(0);
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
//
//
//        // ???????????????
//        try {
//            mLocClient = new LocationClient(mContext);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
//        option.setOpenGps(true); // ??????gps
//        option.setCoorType("bd09ll"); // ??????????????????
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//
//        initTTS();//???????????????
////        MyUtils.setStatBar(this);//????????????????????????????????????
//
//    }
//
//
//
//
//    private void initTTS() {
//        //???????????????????????????
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
//            textToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int status) {
//                    if (status == textToSpeech.SUCCESS) {
//                        // Toast.makeText(MainActivity.this,"??????????????????",
//                        // Toast.LENGTH_SHORT).show();
//                        // Locale loc1=new Locale("us");
//                        // Locale loc2=new Locale("china");
//
//                        textToSpeech.setPitch(1.0f);//????????????????????????
//                        textToSpeech.setSpeechRate(1.0f);//??????????????????
//
//                        //????????????????????????????????????
//                        int result1 = textToSpeech.setLanguage(Locale.US);
//                        int result2 = textToSpeech.setLanguage(Locale.
//                                SIMPLIFIED_CHINESE);
//                        boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
//                        boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);
//
//                        Log.i("zhh_tts", "US????????????--???" + a +
//                                "\nzh-CN????????????--???" + b);
//
//                    } else {
//                        MyToast.showToast("????????????????????????");
//                        //                    Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            });
//        }
//    }
//    private void startAuto(String data) {
//        // ???????????????????????????????????????????????????????????????????????????,1.0?????????
//        textToSpeech.setPitch(1.0f);
//        // ????????????
//        textToSpeech.setSpeechRate(3.01f);
//        textToSpeech.speak(data,//??????????????????????????????????????????????????????
//                TextToSpeech.QUEUE_FLUSH, null);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void Mapinit(int i) {//????????????????????????
//        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);//???????????????????????????
//        if (i == 1) {
//            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//?????????
//        }
//        if (i == 2) {
//            mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//?????????
//        }
////        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
//        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker));
////        MapStatus.Builder builder1 = new MapStatus.Builder();
////        builder1.overlook(0);
////        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
//
//
//    }
//
//
//
////    private long mPressedTime = 0;
//
//
////    @Override
////    public void onBackPressed() {//?????????????????????
////        long mNowTime = System.currentTimeMillis();//???????????????????????????
////        if ((mNowTime - mPressedTime) > 2000) {//???????????????????????????
////            MyToast.showToast("????????????????????????");
////            mPressedTime = mNowTime;
////        } else {//????????????
////            MainActivity mainActivity= (MainActivity) mContext.getApplicationContext();
////            mainActivity.finish();
////            System.exit(0);
////        }
////    }
//
//    @Override
//    public void onPause() {
//        mMapView.onPause();
//        mapUtil.onPause();
//        //???????????????????????????
//        mSensorManager.unregisterListener(this);
//// TODO Auto-generated method stub
//        locationService.unregisterListener(mListener); //???????????????
//        locationService.stop(); //??????????????????
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        mMapView.onResume();
//        super.onResume();
//        mapUtil.onResume();
//        //??????????????????????????????????????????
//        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
//                SensorManager.SENSOR_DELAY_UI);
//
//
//        // -----------location config ------------
//        locationService = AppContext.locationService;
//        //??????locationservice????????????????????????????????????1???location???????????????????????????????????????????????????activity?????????????????????????????????locationservice?????????
//        locationService.registerListener(mListener);
//        //????????????
////        int type = getActivity().getIntent().getIntExtra("from", 0);
////        if (type == 0) {
////            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
////        } else if (type == 1) {
//            //????????????
//            locationService.start();
////        }
//    }
//
//    @Override
//    public void onStop() {
//
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        // ?????????????????????
//        mLocClient.stop();
//        // ??????????????????
//        mBaiduMap.setMyLocationEnabled(false);
//        mMapView.onDestroy();
//        mMapView = null;
//        mSearch.destroy();
//        mLocClient.disableLocInForeground(true);
//        mLocClient.unRegisterLocationListener(myListener);
//        mLocClient.stop();
//        if (timer != null) {
//            timer.cancel();
//        }
//        if (timer2 != null) {
//            timer2.cancel();
//        }
//        mapUtil.clear();
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        locationService.stop();//??????????????????
//        Log.e("locationService", "onDestroyViewHome: " );
//        super.onDestroyView();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private boolean isIgnoringBatteryOptimizations() {
//        boolean isIgnoring = false;
//        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
//        if (powerManager != null) {
//            isIgnoring = powerManager.isIgnoringBatteryOptimizations(mContext.getPackageName());
//        }
//        return isIgnoring;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void requestIgnoreBatteryOptimizations() {
//        try {
//            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isJuli=true;//?????????????????????????????????
//    /**
//     * ??????SDK????????????
//     */
//    public class MyLocationListenner extends BDAbstractLocationListener {
//
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // map view ???????????????????????????????????????
//            if (location == null || mMapView == null) {
//                return;
//            }
//
//            List<GuijiViewBean> list=null;
//            DBManagerGuijiView guijiView=null;
//            if(isGj){
//                try {
//                    guijiView= new DBManagerGuijiView(mContext);
//                    list= guijiView.guijiViewBeans();
//                    if(list.size()>0){
//                        for (GuijiViewBean guijiViewBean : list) {
//                            Log.e("onReceive", "onReceiveLocation: "+guijiViewBean);
//                        }
//                    }else{
//                        GuijiViewBean bean = new GuijiViewBean();
//                        bean.setLat(location.getLatitude());
//                        bean.setLon(location.getLongitude());
//                        guijiView.insertStudent(bean);
//                    }
//                    if (list.size() > 0 && list != null) {
//                        GuijiViewBean guijiViewBean = list.get(list.size() - 1);
//                        double lat = guijiViewBean.getLat();
//                        double lon = guijiViewBean.getLon();
//                        LatLng latLngceliang = new LatLng(lat, lon);
//                        double distance = DistanceUtil.getDistance(mylag2, latLngceliang);//????????????????????????????????????
//                        Double getguis = 0.0;
//                        if (!TextUtils.isEmpty(ACacheUtil.getguitime())) {
//                            String getguitime = ACacheUtil.getguitime();
//                            getguis = Double.parseDouble(getguitime);
//                        } else {
//                            getguis = 10.0;
//                        }
//                        if (distance > getguis) { //??????????????? ???????????? ??????
////                    ToastUtils.showShort("??????"+list.size());
//                            Log.d("onReceive", "ASonReceiveLocation:??????");
//                            GuijiViewBean guijiViewBeanS = new GuijiViewBean();
//                            guijiViewBeanS.setLat(location.getLatitude());
//                            guijiViewBeanS.setLon(location.getLongitude());
//                            try {
//                                guijiView.insertStudent(guijiViewBeanS);
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
////                    ToastUtils.showShort("??????");
//                            Log.d("onReceive", "ASonReceiveLocation:??????"+list.size());
//                        }
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//
//            //?????????????????????????????????????????????????????????????????????????????????????????????
//            //?????????????????????????????????
//            mylag2=new LatLng(location.getLatitude(),location.getLongitude());
//            mylag=new LatLng(location.getLatitude(),location.getLongitude());
//
//
//
//            //?????????????????????????????????????????????????????????????????????????????????????????? ?????????????????????
//
//            mCurrentLat = location.getLatitude();
//            mCurrentLon = location.getLongitude();
//            mCurrentAccracy = location.getRadius();
//            locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // ?????????????????????????????????????????????????????????0-360
//                    .direction(mCurrentDirection).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
//
//
//        }
//
//        public void onReceivePoi(BDLocation poiLocation) {
//
//        }
//    }
//
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    //?????????        ??????????????????                   ?????????????????????   ??????????????????
//    private void JingBaos(List<LatLng> pointsJingbao, LatLng positionjingbaojizhan, LatLng mylag, boolean bb) {
//        List<LatLng> pointsJingbaoLines = new ArrayList<>();
//
//        if (bb == false) {
//
//        } else {
//
////aaaa
//            List<GuijiViewBeanjizhan> guijiViewBeanjizhans = null;
//            try {
//                guijiViewBeanjizhans = dbManagerJZ.queryType();
//
//                Log.d(TAG, "JingBaos:Type " + guijiViewBeanjizhans);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            if (guijiViewBeanjizhans.size() == 0) {
//                MyToast.showToast("????????????????????????");
////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
//                Log.d(TAG, "1JingBaoguijiViewBeanjizhanss: " + guijiViewBeanjizhans + "''''" + guijiViewBeanjizhans.size());
//                jingbaoflag = false; //??????
//                Log.d(TAG, "setjingbao: " + jingbaoflag);
//                bt_jingbao.setTextColor(Color.BLACK);
//                ib_baojing.setBackground(getResources().getDrawable(R.mipmap.baojing_up));
//                timer2.cancel();
//                return;
//            } else {
//                Log.d(TAG, "2JingBaoguijiViewBeanjizhanss: " + guijiViewBeanjizhans);
//            }
//            mBaiduMap.clear();
//            //????????????????????????
//            List<GuijiViewBeanjizhan> resultBeans = null;
//            try {
//                resultBeans = dbManagerJZ.guijiViewBeans();
//                Log.d(TAG, "????????????resultBeansonCreate: " + resultBeans);
//                if (resultBeans.size() > 0 && resultBeans != null) {
//                    DataAll(resultBeans);
//                    for (int i = 0; i < resultBeans.size(); i++) {
//                        pointsJingbaoLines.clear();
//                        pointsJingbaoLines.add(mylag);
//                        pointsJingbaoLines.add(new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon())));
//                        OverlayOptions mOverlayOptions = new PolylineOptions()
//                                .width(5)
//                                .color(Color.rgb(65, 105, 225))
//                                .points(pointsJingbaoLines);
//                        //????????????????????????
//                        //mPloyline ????????????
//                        Overlay mPolylines = mBaiduMap.addOverlay(mOverlayOptions);
//                        double distancejizhan = DistanceUtil.getDistance(mylag, new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon())));//???????????????????????????
//                        Log.d(TAG, "distanceonMapClicks: " + "distancejizhan" + distancejizhan + "???");
//                        //??????
//                        final String tallk = Math.round(distancejizhan) + "???";
//                        //??????TextOptions??????
//                        OverlayOptions mTextOptions = new TextOptions()
//                                .text("" + tallk) //????????????
//                                .bgColor(Color.rgb(224, 255, 255)) //?????????
//                                .fontSize(26) //??????
//                                .fontColor(Color.rgb(0, 0, 0)) //????????????
//                                .rotate(0) //????????????
//                                .position(new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon())));
//                        //?????????????????????????????????
//                        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
//                        //???????????? lac eci
////                        LatLng desLatLngBaiduD = new LatLng(Double.parseDouble(resultBeans.get(i).getLat())-0.00002, Double.parseDouble(resultBeans.get(i).getLon()));
////??????TextOptions??????
//                        OverlayOptions mTextOptionss = new TextOptions()
//                                .text(resultBeans.get(i).getLac() + "-" + resultBeans.get(i).getCi()) //????????????
////                    .bgColor(getResources().getColor(R.color.color_f25057)) //?????????
//                                .fontSize(32) //??????
//                                .fontColor(getResources().getColor(R.color.color_f25057)) //????????????
//                                .rotate(0) //????????????
//                                .position(new LatLng(Double.parseDouble(resultBeans.get(i).getLat()) - 0.00002, Double.parseDouble(resultBeans.get(i).getLon())));
//
////?????????????????????????????????
//                        mBaiduMap.addOverlay(mTextOptionss);
//                    }
//
//
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            Log.d(TAG, "onReceiveLocation: " + "??????");
//            DecimalFormat df = new DecimalFormat(".");
//            //?????????????????????
//            pointsJingbao.add(mylag);
//            pointsJingbao.add(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())));
//
////            List<Double> lists = MyUtils.StringTolist(guijiViewBeanjizhans.get(0).getTa());
////            int sum = 0;
////            for (int j = 0; j < lists.size(); j++) {
////                sum += lists.get(j);
////            }
////
////
////            //???????????????
////            int size = lists.size();
////
////            double sum_db = sum;
////            double size_db = size;
////            double Myradius_db = sum_db / size_db * 78;
////            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
////            int Myradius = new Double(Myradius_db).intValue();
////            Log.d(TAG, "JingBaos:Myradius " + Myradius);
////            if (UNIFORMTA == true) {
////                OverlayOptions ooCircle = new CircleOptions()
//////                            .fillColor(0x000000FF)
////                        .fillColor(Color.argb(40, 255,
////                                0,
////                                0))
////                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
////                        .stroke(new Stroke(2, Color.rgb(255,
////                                0,
////                                0)))
////                        .radius(Myradius);
////                LatLng center = ((CircleOptions) ooCircle).getCenter();
////                Log.d("nzq", "onCreate: " + center);
////                mBaiduMap.addOverlay(ooCircle);
////            }
////            //??????
//////                    LatLng llDot = new LatLng(39.90923, 116.447428);
////            OverlayOptions ooDot = new DotOptions().center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon()))).radius(6).color(0xFF0000FF);
////            mBaiduMap.addOverlay(ooDot);
//
//            List<Double> lists = MyUtils.StringTolist(guijiViewBeanjizhans.get(0).getTa());
//            Double sum = Double.valueOf(0);
//            for (int j = 0; j < lists.size(); j++) {
//                sum += lists.get(j);
//            }
//            //???????????????
//            int size = lists.size();
//            double sum_db = sum;
//            double size_db = size;
//            double Myradius_db = sum_db / size_db * 78;
//
//            Log.d(TAG, "DataAlla: " + sum_db + "--" + size_db);
//            Log.d(TAG, "DataAll: aaa0" + sum_db / size_db * 78);
//            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
//            int Myradius = new Double(Myradius_db).intValue();
//            Log.d(TAG, "JingBaos:Myradius " + Myradius);
//            if (UNIFORMTA == true) {
//                OverlayOptions ooCirclepingjun = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(40, 255,
//                                0,
//                                0))
//                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
//                        .stroke(new Stroke(5, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius((int) Myradius_db);
//                mBaiduMap.addOverlay(ooCirclepingjun);
//                Log.e(TAG, "DataAll:?????? " + Collections.max(lists));
//                Log.d(TAG, "DataAlla:pingjun" + (int) Myradius_db);
//            }
//
////            //??????ta???
////            if (MAXTA == true) {
////                OverlayOptions ooCircleaMAx = new CircleOptions()
//////                            .fillColor(0x000000FF)
////                        .fillColor(Color.argb(0, 255,
////                                0,
////                                0))
////                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
////                        .stroke(new Stroke(2, Color.rgb(255,
////                                0,
////                                0)))
////                        .radius((int) (Collections.max(lists) * 78));
////                Log.d(TAG, "DataAlla:MAXTA" + (int) (Collections.max(lists) * 78));
////                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
//////            Log.d("nzq", "onCreate: " + center);
////                mBaiduMap.addOverlay(ooCircleaMAx);
////            }
////            Log.d(TAG, "DataAllsum: " + sum);
////            //??????ta???
////            if (MINTA == true) {
////                OverlayOptions ooCircleaMin = new CircleOptions()
//////                            .fillColor(0x000000FF)
////                        .fillColor(Color.argb(0, 255,
////                                0,
////                                0))
////                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
////                        .stroke(new Stroke(2, Color.rgb(255,
////                                0,
////                                0)))
////                        .radius((int) (Collections.min(lists) * 78));
////                Log.d(TAG, "DataAlla:min" + (int) (Collections.min(lists) * 78));
////                Log.e(TAG, "DataAll:?????? " + Collections.min(lists));
//////            Log.d("nzq", "onCreate: " + center);
////                mBaiduMap.addOverlay(ooCircleaMin);
////            }
//
//
//            //??????Marker??????
////            jizhan1
//            BitmapDescriptor bitmap = BitmapDescriptorFactory
//                    .fromResource(R.drawable.arrow);
//            //??????MarkerOption???????????????????????????Marker
//            Bundle bundles = new Bundle();
//            bundles.putString("id", String.valueOf(guijiViewBeanjizhans.get(0).getId()));
//            bundles.putString("type", String.valueOf(guijiViewBeanjizhans.get(0).getType()));
//            bundles.putString("mcc", guijiViewBeanjizhans.get(0).getMcc());
//            bundles.putString("mnc", guijiViewBeanjizhans.get(0).getMnc());
//            bundles.putString("lac", guijiViewBeanjizhans.get(0).getLac());
//            bundles.putString("ci", guijiViewBeanjizhans.get(0).getCi());
//            bundles.putString("lat", String.valueOf(guijiViewBeanjizhans.get(0).getLat()));
//            bundles.putString("lon", String.valueOf(guijiViewBeanjizhans.get(0).getLon()));
//            bundles.putString("radius", guijiViewBeanjizhans.get(0).getRadius());
//            bundles.putString("address", guijiViewBeanjizhans.get(0).getAddress());
//            OverlayOptions optiona = new MarkerOptions()
//                    .anchor(10, 30)
//                    .extraInfo(bundles)
//                    .position(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon()))) //????????????
//                    .perspective(true)
//                    .icon(bitmap) //????????????
//                    .draggable(true)
//                    .draggable(true)
//                    //?????????????????????????????????????????????????????????
//                    .flat(true)
//                    .alpha(0.5f);
//            //??????????????????Marker????????????
////            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//??????????????????????????????  Marker marker
//            //??????Marker??????
//
//            //????????????
//            String tas = guijiViewBeanjizhans.get(0).getRadius();
//            int is = Integer.parseInt(tas);
//            if (!TextUtils.isEmpty(ACacheUtil.getFugaiKG())) {
//                int kg = Integer.parseInt(ACacheUtil.getFugaiKG());
//                if (kg == 0) {//0 ?????????????????? ???????????????
//                    OverlayOptions ooCirclefugai = new CircleOptions()
////                            .fillColor(0x000000FF)
//                            .fillColor(Color.argb(40, 255,
//                                    165,
//                                    0))
//                            .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
//                            .stroke(new Stroke(2, Color.rgb(255,
//                                    165,
//                                    0)))
//                            .radius(is);
//                    mBaiduMap.addOverlay(ooCirclefugai);
//                }
//            }
//
//            Log.d(TAG, "pointsonMapClick: " + points.size());
//            //?????????????????????????????????
//            if (pointsJingbao.size() > 1) {
//                //?????????????????????
//                OverlayOptions mOverlayOptions = new PolylineOptions()
//                        .width(5)
//                        .color(Color.rgb(255, 0, 0))
//                        .points(pointsJingbao);
//                //????????????????????????
//                //mPloyline ????????????
//                Overlay mPolylines = mBaiduMap.addOverlay(mOverlayOptions);
//
//
//                double distancejizhan = DistanceUtil.getDistance(mylag, new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())));//???????????????????????????
//                Log.d(TAG, "distanceonMapClicks: " + "distancejizhan" + distancejizhan + "???");
//                //????????????
//                Math.round(distancejizhan);
//                //??????
//                final String tallk = Math.round(distancejizhan) + "???";
//                Log.d(TAG, "onReceiveLocation: " + tallk);
//                if (Myradius >= Math.round(distancejizhan)) {
////                    TimerTask task = new TimerTask() {
////                        @Override
////                        public void run() {
////                            /**
////                             *??????????????????
////                             */
//                    startAuto("??????????????????");
////                        }
////                    };
////                    Timer timer = new Timer();
////                    timer.schedule(task, 3000);//3????????????TimeTask???run??????
//
//                } else {
//
////                    TimerTask task = new TimerTask() {
////                        @Override
////                        public void run() {
////                            /**
////                             *??????????????????
////                             */
//                    startAuto(tallk);
////                        }
////                    };
////                    Timer timer = new Timer();
////                    timer.schedule(task, 3000);//3????????????TimeTask???run??????
//                }
//
//                //???????????????????????????
//                //??????TextOptions??????
//                OverlayOptions mTextOptions = new TextOptions()
//                        .text("" + tallk) //????????????
//                        .bgColor(Color.rgb(224, 255, 255)) //?????????
//                        .fontSize(26) //??????
//                        .fontColor(Color.rgb(0, 0, 0)) //????????????
//                        .rotate(0) //????????????
//                        .position(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())));
//                //?????????????????????????????????
//                Overlay mText = mBaiduMap.addOverlay(mTextOptions);
//                //                   latLngOnclicek = latLng;
//
//            }
//        }
//
//    }
//
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void Viewgjs() {
//        List<GuijiViewBean> guijiViewBeans = null;
//        try {
//            guijiViewBeans = dbManagerGuijiView.guijiViewBeans();
//            Log.d(TAG, "Viewgjs: aa" + guijiViewBeans);
//            if (guijiViewBeans.size() > 1 && guijiViewBeans != null) {
//                mBaiduMap.clear();
//                List<GuijiViewBeanjizhan> resultBeans = null;
//                try {
//                    mBaiduMap.clear();
//                    resultBeans = dbManagerJZ.guijiViewBeans();
//                    Log.d(TAG, "????????????resultBeansonCreate: " + resultBeans);
//                    Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
//                    if (resultBeans.size() > 0 && resultBeans != null) {
//                        mBaiduMap.clear();
//                        DataAll(resultBeans);
//                    } else {
//                        Log.d(TAG, "initdatas: aa" + "1111");
//
////                        MyToast.showToast("??????????????????");
//
//
//                    }
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(new LatLng(guijiViewBeans.get(0).getLat(), guijiViewBeans.get(0).getLon()));
//                builder.zoom(19.0f);
////                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//???????????????
//                // ????????????????????????????????????
//
//                List<LatLng> points11 = new ArrayList<>();
//                for (int i = 0; i < guijiViewBeans.size(); i++) {
//                    points11.add(new LatLng(guijiViewBeans.get(i).getLat(), guijiViewBeans.get(i).getLon()));
//                }
//                List<BitmapDescriptor> textureList = new ArrayList<>();
////                            textureList.add(mRedTexture);
//                textureList.add(mBlueTexture);
////                            textureList.add(mGreenTexture);
//                List<Integer> textureIndexs = new ArrayList<>();
//                textureIndexs.add(0);
////                            textureIndexs.add(1);
////                            textureIndexs.add(2);
//                //??????
//                OverlayOptions ooPolyline11 = new PolylineOptions()
//                        .width(10)
//                        .points(points11)
//                        .dottedLine(true)
//                        .customTextureList(textureList)
//                        .textureIndex(textureIndexs);
//                Polyline mTexturePolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline11);
//
//                guijiFlag = true;
//                Log.d(TAG, "guijiFlagonClick: " + "?????????");
//                iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_0));
//                guijiViewBeans.clear();
//            } else {
//                guijistart = false;
//                if (timer != null) {
//                    timer.cancel();
//                }
////                Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_LONG).show();
//                MyToast.showToast("???????????????");
//                ib_gj.setBackground(getResources().getDrawable(R.mipmap.gj));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Log.d(TAG, "Viewgjs: erro" + e.getMessage());
//        }
//    }
//
//    Mycallback mycallback = new Mycallback() {
//        @Override
//        public void call() {
//            initdatas();
//        }
//
//        @Override
//        public void isCheck(boolean b, int t) {
//            Log.d(TAG, "isCheckbs: " + b);
//            xunFlag = b;
//            double zoomset = mBaiduMap.getMapStatus().zoom;
//            add = 0.0125f / Math.pow(2, zoomset - 15);
//            zuoxiapoint.set((int) x1, (int) y1);
//            youshangpoint.set((int) x2, (int) y2);
//            LatLng zuoshangLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoshangpoint);//?????????
//            LatLng youxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(youxiapoing);//?????????
//            youxiaaLatLng = mBaiduMap.getProjection().fromScreenLocation(youshangpoint);//?????????
//            //??????????????????
//            Map<String, Double> stringDoubleMap = GCJ02ToWGS84Util.bd09to84(youxiaaLatLng.longitude, youxiaaLatLng.latitude);
//            youxiaaLatLng = new LatLng(stringDoubleMap.get("lat"), stringDoubleMap.get("lon"));
//
//
//            zuoxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoxiapoint);
//            //??????????????????
//            Map<String, Double> zuoxial = GCJ02ToWGS84Util.bd09to84(zuoxiaLatLng.longitude, zuoxiaLatLng.latitude);
//            zuoxiaLatLng = new LatLng(zuoxial.get("lat"), zuoxial.get("lon"));
//            BUSINESS = t;
//            if (b == true) {
//                mBaiduMap.clear();
//                JzGetData(add);
//            } else {
//                initdatas3();
//            }
//
//        }
//
//        @Override
//        public void DataShow(boolean b) {
//            if (b == true) {
//                initdatas3();
//            }
//        }
//
//
//    };
//    public void initdatas3() {
//        //????????????????????????
//
//        List<GuijiViewBeanjizhan> resultBeans = null;
//        try {
////            mBaiduMap.clear();
//            resultBeans = dbManagerJZ.guijiViewBeans();
//            Log.d(TAG, "????????????resultBeansonCreate: " + resultBeans);
//            Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
//            if (resultBeans.size() > 0 && resultBeans != null) {
//                mBaiduMap.clear();
//                DataAll(resultBeans);
//            } else {
////                Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    public void JzGetData(double add) {
//        double daoxianData_zoomset = mBaiduMap.getMapStatus().zoom;
//        if (xunFlag == true) {
//            Log.d(TAG, "JzGetDatabusiness: " + BUSINESS);
//            if (daoxianData_zoomset > 18) {
//                RetrofitFactory.getInstence().API().JzData(String.valueOf(BUSINESS),
//                        String.valueOf(zuoxiaLatLng.longitude - add),
//                        String.valueOf(zuoxiaLatLng.latitude),
//                        String.valueOf(youxiaaLatLng.longitude + add),
//                        String.valueOf(youxiaaLatLng.latitude + 1.3f * add)
//
//
//                ).enqueue(new Callback<JzGetData>() {
//                    @RequiresApi(api = Build.VERSION_CODES.N)
//                    @Override
//                    public void onResponse(Call<JzGetData> call, Response<JzGetData> response) {
//                        Log.e(TAG, "daoxian: " + response.body().toString());
//                        jzGetData = response.body();
//
//                        if (jzGetData.getCode() == 1 && jzGetData.getData().size() > 0 && jzGetData.getData() != null) {
//                            Log.d(TAG, "onResponse:jzgetData" + "????????????" + jzGetData.getData().size());
//                            Log.d(TAG, "onResponse:??????????????????" + jzGetData.getData().size());
//
//                            tv_screen.setText(jzGetData.getData().size() + "???");
//
//                            for (int i = 0; i < jzGetData.getData().size(); i++) {
//                                LatLng latLng = new LatLng(jzGetData.getData().get(i).getLatitude(), jzGetData.getData().get(i).getLongitude());
//                                CoordinateConverter converter = new CoordinateConverter()
//                                        .from(CoordinateConverter.CoordType.GPS)
//                                        .coord(latLng);
//                                //???????????? 84?????????09
//                                LatLng desLatLngBaidus = converter.convert();
//
//                                Bundle bundle = new Bundle();
//                                //??????eci
//                                int ECIS = jzGetData.getData().get(i).getENodeBid() * 256 + jzGetData.getData().get(i).getAreaMark();
//                                DecimalFormat df = new DecimalFormat("#.000000");
//                                String lat = df.format(desLatLngBaidus.latitude);
//                                String lon = df.format(desLatLngBaidus.longitude);
//                                bundle.putString("address", jzGetData.getData().get(i).getAreaName() + "");
//                                bundle.putString("ci", ECIS + "");
//                                bundle.putString("mcc", "");
//                                bundle.putString("mnc", jzGetData.getData().get(i).getMnc() + "");
//                                bundle.putString("type", "2");//2 ???????????????????????????????????????????????????
//                                bundle.putString("Radius", "0");
//                                bundle.putString("lac", jzGetData.getData().get(i).getTac() + "");
//                                bundle.putString("lat", lat);
//                                bundle.putString("lon", lon);
//                                //
//                                bundle.putString("resources", "????????????");
//                                bundle.putString("band", jzGetData.getData().get(i).getBand() + "");
//                                bundle.putString("types", jzGetData.getData().get(i).getType() + "");
//                                bundle.putString("pci", jzGetData.getData().get(i).getPci() + "");
//                                bundle.putString("down", jzGetData.getData().get(i).getDownFrequencyPoint() + "");
//                                BitmapDescriptor bitmap = BitmapDescriptorFactory
//                                        .fromResource(R.drawable.jizhan1);
//                                //??????MarkerOption???????????????????????????Marker
//                                OverlayOptions optiona = new MarkerOptions()
//                                        .extraInfo(bundle)
//                                        .anchor(10, 30)
//                                        .position(desLatLngBaidus) //????????????
//                                        .perspective(true)
//                                        .icon(bitmap) //????????????
//                                        .draggable(true)
//                                        .draggable(true)
//                                        //?????????????????????????????????????????????????????????
//                                        .flat(true)
//                                        .alpha(0.5f);
//                                //??????????????????Marker????????????
//                                markerMy = (Marker) mBaiduMap.addOverlay(optiona);//??????????????????????????????  Marker marker
//                            }
//
//                        } else {
//                            tv_screen.setText(0 + "???");
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<JzGetData> call, Throwable t) {
//
//                    }
//                });
//            }
//        }
//
//    }
//    private AddCallBack addcallBack = new AddCallBack() {
//        @Override
//        public void call(String data, int i) {
//            listMarker.remove(i);
//            demoAdapteradd.notifyDataSetChanged();
//        }
//    };
//
//
//    //??????
//    @SuppressLint("Range")
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void setmapJuli() {
//        if(isGj){
//            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
//            ToastUtils.setBgColor(Color.BLACK);
//            ToastUtils.setMsgColor(Color.WHITE);
//            ToastUtils.showShort("??????????????????");
//            return;
//        }
//        if (jingbaoflag == false) {
//            if (guijiFlag == true) {
//                MyToast.showToast("??????????????????");
//                return;
//            }
//            if (juliFlage == false) {
//                //????????????
//                Log.d(TAG, "setmapJuli:????????????");
//                juliFlage = true;
//                ib_cl.setBackground(getResources().getDrawable(R.mipmap.measuretrue));
//            } else if (juliFlage == true) {
//                new CircleDialog.Builder()
////                        .setTitle(null)
//                        .setWidth(0.7f)
//                        .setMaxHeight(0.7f)
//                        .setText("???????????????????????????????")
//                        .setTitleColor(Color.parseColor("#00acff"))
//                        .setNegative("??????", new Positiv(1))
//                        .setPositive("??????", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        })
//                        .show(getFragmentManager());
//            }
//        } else {
//            MyToast.showToast("??????????????????");
//        }
//    }
//
//    private void setDialog() {
//
//
//    }
//
////    /**
////     * ?????????????????????
////     * @param view
////     */
////    public void alertDialog(View view) {
////        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
////        builder.setTitle("?????????");
////        builder.setMessage("???????????????");
////        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                System.out.println("??????????????????");
////            }
////        }).setNegativeButton("??????", null);
////        builder.show();
////    }
//
////    /**
////     * ???????????????????????????
////     * @param view
////     */
////    public void alertDialog2(View view) {
////        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
////        // ????????? title??????
////        TextView title = new TextView(mContext);
////        title.setText("??????????????????");
////        title.setTextSize(24);
////        title.setGravity(Gravity.CENTER);
////        title.setPadding(0,20,0,20);
////        builder.setCustomTitle(title);
////        // ????????????????????????view?????????????????????
////        TextView msg = new TextView(mContext);
////        msg.setText("???????????????????????????");
////        msg.setTextSize(24);
////        msg.setGravity(Gravity.CENTER);
////        msg.setPadding(20, 40, 20, 40);
////        builder.setView(msg);
////
////        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int i) {
////                System.out.println("??????????????????");
////            }
////        }).setNegativeButton("??????", null);
////        // ?????? show()??????????????? dialog??????
////        AlertDialog dialog = builder.show();
////        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
////        final Button negativeButton=dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
////        LinearLayout.LayoutParams positiveParams =(LinearLayout.LayoutParams)positiveButton.getLayoutParams();
////        positiveParams.gravity = Gravity.CENTER;
////        positiveParams.setMargins(10,10,10,10);
////        positiveParams.width = 0;
////        // ?????????????????????????????????????????????????????? 1,????????? 500?????????????????????????????????????????????
////        positiveParams.weight = 500;
////        LinearLayout.LayoutParams negativeParams =(LinearLayout.LayoutParams)negativeButton.getLayoutParams();
////        negativeParams.gravity = Gravity.CENTER;
////        negativeParams.setMargins(10,10,10,10);
////        negativeParams.width = 0;
////        negativeParams.weight = 500;
////        positiveButton.setLayoutParams(positiveParams);
////        negativeButton.setLayoutParams(negativeParams);
////        positiveButton.setBackgroundColor(Color.parseColor("#FF733E"));
////        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
////        negativeButton.setBackgroundColor(Color.parseColor("#DDDDDD"));
////    }
//
//
//    //??????
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @SuppressLint("ResourceAsColor")
//    private void setmapHot() {
//        if (hottype == 2) {
//            //???????????????
//            bt_hot.setTextColor(Color.BLACK);
//            mBaiduMap.setBaiduHeatMapEnabled(false);
//            hottype = 1;
//            ib_rl.setBackground(getResources().getDrawable(R.mipmap.reli_up));
//
//        } else if (hottype == 1) {
//            //???????????????
//
//            bt_hot.setTextColor(Color.RED);
//            mBaiduMap.setBaiduHeatMapEnabled(true);
//            hottype = 2;
//
//            ib_rl.setBackground(getResources().getDrawable(R.mipmap.reli_down));
//        }
//    }
//    //??????
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @SuppressLint("ResourceAsColor")
//    private void setmapJT() {
//        if (hjttype == 2) {
//            //??????
//            mBaiduMap.setTrafficEnabled(false);
//            hjttype = 1;
//            bt_jt.setTextColor(Color.BLACK);
//
//            ib_jt.setBackground(getResources().getDrawable(R.mipmap.jiaotong_up));
//        } else if (hjttype == 1) {
//            mBaiduMap.setTrafficEnabled(true);
//            hjttype = 2;
//            bt_jt.setTextColor(Color.RED);
//
//
//            ib_jt.setBackground(getResources().getDrawable(R.mipmap.jiaotong_down));
//        }
//    }
//
//    //??????
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private void qiehuan() {
//        int mapType = mBaiduMap.getMapType();
//        if (mapType == 1) {
//            ib_qiehuan.setBackground(getResources().getDrawable(R.mipmap.satallitetrue));
//            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//        } else if (mapType == 2) {
//            ib_qiehuan.setBackground(getResources().getDrawable(R.mipmap.statllite));//????????????
//            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        }
//        Log.d(TAG, "qiehuan: " + mapType);
//
//        Log.d(TAG, "qiehuan: " + mapType);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ll_screen:
//                break;
//            case R.id.bt_jizhan:
//
//                break;
//            case R.id.bt_qiehuan:
//
//                break;
//            case R.id.bt_location:
//                initMap();
//
//                break;
//            case R.id.bt_clear:
//                mBaiduMap.clear();
//                points.clear();
//                distance = 0;
//                break;
//
//            case R.id.bt_jt:
//               break;
//            case R.id.bt_hot:
//
//                break;
//
//            case R.id.bt_juli:
//
//                break;
//
//            case R.id.bt_jingbao:
//
//                break;
//            case R.id.ll_qiehuan://????????????
//                qiehuan();
//                break;
//            case R.id.ll_clear:
//
//
//                break;
//            case R.id.ll_jt://????????????
//                setmapJT();
//                break;
//            case R.id.ll_rl://??????
//                setmapHot();
//                break;
//            case R.id.ll_baojing:
//
//                break;
//            case R.id.ll_gensui:
////                gensui();
//                if (MapinitFlag == false) {
//                    Mapinit(2);
//                    MapinitFlag = true;
//
//                    ib_gensui.setBackground(getResources().getDrawable(R.mipmap.gen2));
////                    Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
//                } else if (MapinitFlag = true) {
//                    Mapinit(1);
//                    MapinitFlag = false;
////                    Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
//                    ib_gensui.setBackground(getResources().getDrawable(R.mipmap.gen1));
//                }
//                break;
//            case R.id.bt_uilocation:
////                Toast.makeText(MainActivity.this, "????????????LL????????????", Toast.LENGTH_LONG).show();
//                initMap();
////                mLocClient.requestLocation();
////                Mapinit();
//                break;
//            case R.id.bt_uiceliang://????????????
////                setmapJuli();
//                break;
//            case R.id.ll_cl://????????????
//                setmapJuli();
//                break;
//            case R.id.ll_gj://????????????
//                if(juliFlage){
//                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
//                    ToastUtils.setBgColor(Color.BLACK);
//                    ToastUtils.setMsgColor(Color.WHITE);
//                    ToastUtils.showShort("??????????????????");
//                    return;
//                }
//                if(isGj){
//                    new CircleDialog.Builder()
////                            .setTitle("")
//                            .setWidth(0.7f)
//                            .setMaxHeight(0.7f)
//                            .setText("???????????????????????????????")
//                            .setTitleColor(Color.parseColor("#00acff"))
//                            .setNegative("??????", new Positiv(13))
//                            .setPositive("??????", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                }
//                            })
//                            .show(getFragmentManager());
//                }else{
//                    ib_gj.setBackground(getResources().getDrawable(R.mipmap.gjtrue));
//                    isGj=true;
//                }
////                Toast.makeText(mContext, ""+isGj, Toast.LENGTH_SHORT).show();
//                //?????????????????????
//                break;
//            case R.id.bt_uisearch:
//                int remainder = 1;
//                if (!TextUtils.isEmpty(ACacheUtil.getNumberremainder())) {
//                    remainder = Integer.parseInt(ACacheUtil.getNumberremainder());
//                }
//                if (remainder == 0) {
////                    Toast.makeText(MainActivity.this, "?????????????????????!", Toast.LENGTH_SHORT).show();
//                    MyToast.showToast("?????????????????????!");
//                    break;
//                }
//                list.clear();
//                showDoliag();
//                break;
//            case R.id.bt_jia:
//                float izoomjia = mBaiduMap.getMapStatus().zoom;
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
//                        .zoom(izoomjia + 1) //???????????????????????????????????????,
//                        .build()));
//
//                break;
//
//            case R.id.bt_jian:
//                float izoomjian = mBaiduMap.getMapStatus().zoom;
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
//                        .zoom(izoomjian - 1) //???????????????????????????????????????,
//                        .build()));
//                break;
//
//            case R.id.bt_adddilao:
//                if (TextUtils.isEmpty(et_taclac.getText().toString())) {
////                    Toast.makeText(this, "TAC/LAC????????????", Toast.LENGTH_SHORT).show();
//                    MyToast.showToast("TAC/LAC????????????");
//                    break;
//                }
//                if (TextUtils.isEmpty(et_eci.getText().toString())) {
////                    Toast.makeText(this, "ECI????????????", Toast.LENGTH_SHORT).show();
//                    MyToast.showToast("ECI????????????");
//                    break;
//                }
//                if(jizhanFlag==4){
//                    if(TextUtils.isEmpty(et_sid.getText().toString())){
//                        MyToast.showToast("Sid????????????");
//                    }
//                }
//
////                if (TextUtils.isEmpty(et_ta.getText().toString())) {
////                    Toast.makeText(this, "TA????????????", Toast.LENGTH_SHORT).show();
////                    break;
////                }
//                if (list.size() > 0) {
//                } else {
//                    list.add(0.0);
////                    MyToast.showToast("?????????????????????TA???");
//                    break;
//                }
//                //????????????????????????
//                if (rb_yidong.isChecked() == false && rb_liantong.isChecked() == false && rb_ldainxin4.isChecked() == false && rb_cdma.isChecked() == false && rb_bdjz1.isChecked() == false && rb_bdjz2.isChecked() == false) {
//                    Log.d(TAG, "onClick: " + rb_liantong.isChecked());
//                    MyToast.showToast("?????????????????????");
//                    break;
//                } else {
////                    Toast.makeText(MainActivity.this,"22",Toast.LENGTH_LONG).show();
//                    saveIsShow();//?????????????????????
////                    mBaiduMap.clear();
//                    sendPost();
//                    Log.d("2ilssDetail144", "onClick: ");
////
//                }
//
//                break;
//            case R.id.iv_finish:
//                dialog.dismiss();
//                break;
//
////            case R.id.ll_guijis://??????????????????
////                if (guijistart == false) {
////                    guijistart = true;
//////                    MyToast.showToast("??????????????????");
////                    Log.d(TAG, "guijistart: " + guijistart);
////                    iv_gjstart.setBackground(getResources().getDrawable(R.mipmap.huizhi_0));
////                    GuijiViewBean guijiViewBean = new GuijiViewBean();
////                    if (mylag != null) {
////                        guijiViewBean.setLon(mylag.longitude);
////                        guijiViewBean.setLat(mylag.latitude);
////                    }
////                    try {
////                        dbManagerGuijiView.insertStudent(guijiViewBean);
////                    } catch (SQLException e) {
////                        e.printStackTrace();
////                    }
////                } else if (guijistart == true) {
////                    guijistart = false;
////                    Log.d(TAG, "guijistart: " + guijistart);
//////                    MyToast.showToast("??????????????????");
////                    iv_gjstart.setBackground(getResources().getDrawable(R.mipmap.huizhi_1));
////                }
////                break;
////            case R.id.ll_gjview://??????????????????
//////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
////                if (guijiFlag == false) {
////                    if (jingbaoflag == true) {
////                        MyToast.showToast("??????????????????");
////                        break;
////                    }
////                    if (juliFlage == true) {
////                        MyToast.showToast("??????????????????");
////                        break;
////                    }
//////aaa
////                    guijiFlag = true;
////                    iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_0));
////                    /**
////                     * ??????
////                     */
////                    timer = new Timer();
////                    timer.scheduleAtFixedRate(new TimerTask() {
////                        @Override
////                        public void run() {
////                            System.out.println("Timer is running");
////                            Message message = new Message();
////                            handler.sendMessage(message);
////                            message.what = 1;
////                            Log.d(TAG, "handlerrun: " + 1);
////                        }
////                    }, 0, 1000);
//////                    Viewgjs();//??????
////                } else if (guijiFlag == true) {
////                    guijiFlag = false;
////                    Log.d(TAG, "guijiFlagonClick: " + "?????????");
////                    iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_1));
////                    mMapView.showZoomControls(false);
////                    mBaiduMap.clear();
//////                    MyUtils.Viewjizhan(markerMy, mBaiduMap, dataBean);
////                    if (timer != null) {
////                        timer.cancel();
////                    }
////
////                    initdatas2();
////                }
////                break;
//            case R.id.ll_gjclear:
////                if (guijiFlag == true) {
//////
////                    MyToast.showToast("??????????????????");
////                    break;
////                }
//////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
////                new CircleDialog.Builder()
////                        .setTitle("")
////                        .setText("??????????????????????????????")
////                        .setTitleColor(Color.parseColor("#00acff"))
////                        .setNegative("??????", new Positiv(2))
////                        .setPositive("??????", null)
////                        .show(getSupportFragmentManager());
//
//                break;
//
//            case R.id.bt_uiceliangclear:
////                if (guijiFlag == true) {
////                    MyToast.showToast("??????????????????");
////                    break;
////                }
//////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
////                new CircleDialog.Builder()
////                        .setTitle("")
////                        .setText("???????????????????????????????")
////                        .setTitleColor(Color.parseColor("#00acff"))
////                        .setNegative("??????", new Positiv(1))
////                        .setPositive("??????", null)
////                        .show(getSupportFragmentManager());
//                break;
//            case R.id.bt_ikan:
//////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
////
////                Intent intent = new Intent(mContext, TrackShowDemo.class);
////                startActivity(intent);
//                break;
//
//            case R.id.iv_set:
//                PopupMenu popupMenu = new PopupMenu(mContext, view);
//                Menu menu = popupMenu.getMenu();
//                popupMenu.getMenuInflater().inflate(R.menu.menutc, popupMenu.getMenu());
//                popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) LocationFragment.this);
//                popupMenu.show();
//                break;
//
//            case R.id.bt_adddilaomenu: //menu ??????????????? ????????????
//                ACacheUtil.putjguitime(et_guijitime.getText().toString());//??????????????????
//                ACacheUtil.putjbaojingtime(et_baojingtime.getText().toString());//??????????????????
//
//                if (!TextUtils.isEmpty(ACacheUtil.getbaojingtime())) {
//                    String getbaojingtime = ACacheUtil.getbaojingtime();
//                    int as = Integer.parseInt(getbaojingtime);
//                    ScanSpan = as * 1000;
//                    LocationClientOption option = new LocationClientOption();
//                    option.setOpenGps(true); // ??????gps
//                    option.setCoorType("bd09ll"); // ??????????????????
//                    option.setScanSpan(1000);//??????????????????
//                    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//                    mLocClient.setLocOption(option);
//                    mLocClient.start();
//                } else {
//                    LocationClientOption option = new LocationClientOption();
//                    option.setOpenGps(true); // ??????gps
//                    option.setCoorType("bd09ll"); // ??????????????????
//                    option.setScanSpan(1000);//??????????????????
//                    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//                    mLocClient.setLocOption(option);
//                    mLocClient.start();
//                }
//                ACacheUtil.putFugaiKG(et_kg.getText().toString());
//                dialogmenu.dismiss();
//                MyToast.showToast("????????????");
//                if (timer2 != null) {
//                    if (jingbaoflag == true) {
//                        timer2.cancel();
//                        startJb();//?????????????????????
//                    }
//
//                }
//                break;
//            case R.id.bt_qx://??????????????????
//                dialogmenu.dismiss();
//                break;
//
//            case R.id.bt_jizhan0:
//                initdatas();//??????????????????
//                break;
//            case R.id.ll_fugai:
//
//////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
////                cover();//????????????
//                ////
//                break;
//        }
//    }
//    private void setGj() {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        new Timer().schedule(new TimerTask() {
////            @Override
////            public void run() {
////                getActivity().runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        mLocClient.start();
////                        mBaiduMap.clear();//?????????????????????????????????
////                        lngs.clear();
////                        lngs.add(new LatLng(38.031832, 114.449706));
////                        lngs.add(new LatLng(mylag2.latitude, mylag2.longitude));
////
//////????????????????????????
////                        BitmapDescriptor trace = BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow.png");
////                        BitmapDescriptor start = BitmapDescriptorFactory.fromResource(R.mipmap.m_location);
////                        BitmapDescriptor end = BitmapDescriptorFactory.fromResource(R.mipmap.m_location);
////                        if (lngs.size() > 0) {
////                            //?????????????????????
////                            OverlayOptions startOptions = new MarkerOptions().position(lngs.get(0)).icon(start).visible(false);
////                            OverlayOptions endOptions = new MarkerOptions().position(lngs.get(lngs.size() - 1)).icon(end).visible(false);
////                            mBaiduMap.addOverlay(startOptions);
////                            mBaiduMap.addOverlay(endOptions);
////                        }
//////????????????
////                        PolylineOptions polylineOptions = new PolylineOptions()
////                                .width(20).customTexture(trace)
////                                .points(lngs)
////                                .dottedLine(false);
////                        mBaiduMap.addOverlay(polylineOptions);
////
////                    }
////                });
////            }
////        }, 0, 1000);
//    }
//
//
//    private void showDoliag() {//???????????????
//        dialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//        //????????????????????????
//        inflate = LayoutInflater.from(mContext).inflate(R.layout.item_dibushow53, null);
//        RadioGroup rgp_main = inflate.findViewById(R.id.rg_main);
//        //????????????
//        RadioButton rb_open1 = inflate.findViewById(R.id.rb_open_check1);
////        RadioButton rb_open2 = inflate.findViewById(R.id.rb_open_check2);
//        RadioButton rb_oneself = inflate.findViewById(R.id.rb_oneself);
//        RadioButton rb_Manuallyenter = inflate.findViewById(R.id.rb_Manuallyenter);
//        //???????????????
//        ll_sid = inflate.findViewById(R.id.ll_sid);
//        iv_finish = inflate.findViewById(R.id.iv_finish);
//        iv_finish.setOnClickListener(this);
//        et_sid = inflate.findViewById(R.id.et_sid);
//        et_taclac = inflate.findViewById(R.id.et_taclac);
//        et_eci = inflate.findViewById(R.id.et_eci);
//        et_ta = inflate.findViewById(R.id.et_ta);
//
//
//        //??????????????? view
//        ll_location = inflate.findViewById(R.id.ll_location);//???????????????????????????
//        ets_lon = inflate.findViewById(R.id.ets_lon);//??????
//        ets_lat = inflate.findViewById(R.id.ets_lat);//??????
//        final RecyclerView recyclerView = inflate.findViewById(R.id.recylerview);
//
//        ll_location.setVisibility(View.GONE);
//
//        if(DATATYPE==6){
//            rb_open1.setChecked(true);
//        }
//       /* if (DATATYPE == 1) {
//            rb_open2.setChecked(true);
//        }*/
//        if (DATATYPE == 2) {
//            rb_oneself.setChecked(true);
//        }
//        rgp_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    //??????????????????1??????????????????
//                    case R.id.rb_open_check1:
////   Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
////                        MyToast.showToast("????????????1--??????");
//                        Log.d(TAG, "qonCheckedChanged: " + "6");
//                        DATATYPE = 6;
//                        ll_location.setVisibility(View.GONE);
//                        break;
//                   /* case R.id.rb_open_check2:
////                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
////                        MyToast.showToast("????????????2--??????");
//                        Log.d(TAG, "qonCheckedChanged: " + "1");
//                        DATATYPE = 1;
//                        ll_location.setVisibility(View.GONE);
//                        break;*/
//                    case R.id.rb_oneself:
////                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
////                        MyToast.showToast("????????????");
//                        Log.d(TAG, "qonCheckedChanged: " + "2");
//                        DATATYPE = 2;
//                        ll_location.setVisibility(View.GONE);
//                        break;
//                    case R.id.rb_Manuallyenter:
////                        Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
////                        MyToast.showToast("????????????");
//                        Log.d(TAG, "qonCheckedChanged: " + "2");
//                        DATATYPE = 3;
//                        ll_location.setVisibility(View.VISIBLE);
//                        break;
//
//                }
//            }
//        });
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        demoAdapter = new DemoAdapter(list, callBack);
//        recyclerView.setAdapter(demoAdapter);
//        Button btadd = inflate.findViewById(R.id.btadd);
//        btadd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TextUtils.isEmpty(et_ta.getText().toString())) {
////                    Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//                    MyToast.showToast("??????????????????");
//                    return;
//                }
////                if (et_ta.getText().length() > 3) {
////                    Toast.makeText(MainActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
////                    return;
////                }
//
//
////                if(list.size()>0){//TA??????????????????????????????
////                    list.clear();
////                }
//                list.add(Double.parseDouble(et_ta.getText().toString()));
////                mAdapter.notifyDataSetChanged();
//                et_ta.setText("");
//                demoAdapter.notifyDataSetChanged();
////                aaa
//            }
//        });
//        String sid = ACacheUtil.getSID();
//        et_sid.setText(sid);
//        String tl = ACacheUtil.getTl();
//        et_taclac.setText(tl);
//        String eci = ACacheUtil.getEci();
//        et_eci.setText(eci);
//        String ta = ACacheUtil.getTa();
////        et_ta.setText(ta);
//        rb_yidong = inflate.findViewById(R.id.rb_yidong);
//        rb_yidong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    rb_liantong.setChecked(false);
//                    rb_ldainxin4.setChecked(false);
//                    rb_cdma.setChecked(false);
//                    rb_bdjz1.setChecked(false);
//                    rb_bdjz2.setChecked(false);
//                    jizhanFlag = 0;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                    ll_sid.setVisibility(View.GONE);
//                } else {
//                    jizhanFlag = 44;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                }
//
//            }
//        });
//
//        rb_liantong = inflate.findViewById(R.id.rb_liantong);
//        rb_liantong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    rb_yidong.setChecked(false);
//                    rb_ldainxin4.setChecked(false);
//                    rb_cdma.setChecked(false);
//                    rb_bdjz1.setChecked(false);
//                    rb_bdjz2.setChecked(false);
//                    jizhanFlag = 1;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                    ll_sid.setVisibility(View.GONE);
//                } else {
//                    jizhanFlag = 44;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                }
////
//            }
//        });
//        rb_ldainxin4 = inflate.findViewById(R.id.rb_ldainxin4);
//        rb_ldainxin4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    rb_yidong.setChecked(false);
//                    rb_liantong.setChecked(false);
//                    rb_cdma.setChecked(false);
//                    rb_bdjz1.setChecked(false);
//                    rb_bdjz2.setChecked(false);
//                    jizhanFlag = 11;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                    ll_sid.setVisibility(View.GONE);
//                } else {
//                    jizhanFlag = 44;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                }
//
//
//            }
//        });
//        rb_cdma = inflate.findViewById(R.id.rb_cdma);
//
//        rb_cdma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    rb_yidong.setChecked(false);
//                    rb_liantong.setChecked(false);
//                    rb_ldainxin4.setChecked(false);
//                    rb_bdjz1.setChecked(false);
//                    rb_bdjz2.setChecked(false);
//
//                    jizhanFlag = 4;
//                    ll_sid.setVisibility(View.VISIBLE);
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                } else {
//                    jizhanFlag = 44;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                }
//
//            }
//        });
//        rb_bdjz1 = inflate.findViewById(R.id.rb_bdjzl);
//        rb_bdjz1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    rb_yidong.setChecked(false);
//                    rb_liantong.setChecked(false);
//                    rb_cdma.setChecked(false);
//                    rb_ldainxin4.setChecked(false);
//                    rb_bdjz2.setChecked(false);
//
////                    jizhanFlag = 4;
//                    ll_sid.setVisibility(View.GONE);
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                    List<SpBean2> gsmInfoList = DtUtils.getGsmInfoList(mContext);
//                    List<SpBean2> list11 = new ArrayList<>();
//                    if (gsmInfoList.size() > 0 && gsmInfoList != null) {//?????????
//                        if (gsmInfoList.size() == 1) {//????????????
//                            for (int i = 0; i < gsmInfoList.size(); i++) {
//
//                                int cid =Integer.parseInt(gsmInfoList.get(i).getCid()) ;
//                                int tac = Integer.parseInt(gsmInfoList.get(i).getTac());
//                                if (cid != 2147483647 && tac != 2147483647) {
////                                gsmInfoList.remove(i);
//                                    list11.add(gsmInfoList.get(i));
//                                }
//                            }
//
//                            et_taclac.setText(gsmInfoList.get(0).getTac() + "");
//                            et_eci.setText(gsmInfoList.get(0).getCid() + "");
//                            jizhanFlag = DtUtils.YY2(gsmInfoList.get(0).getPlmn());
//                            Log.d("gsmInfoList", "onCheckedChanged:?????? ");
//                        } else {//??????
//                            for (int i = 0; i < gsmInfoList.size(); i++) {
//                                int cid = Integer.parseInt(gsmInfoList.get(i).getCid());
//                                int tac = Integer.parseInt(gsmInfoList.get(i).getTac());
//                                if (cid != 2147483647 && tac != 2147483647) {
////                                gsmInfoList.remove(i);
//                                    list11.add(gsmInfoList.get(i));
//                                }
//                            }
//                            if (list11.size() > 0) {
//                                et_taclac.setText(list11.get(0).getTac() + "");
//                                et_eci.setText(list11.get(0).getCid() + "");
//                                jizhanFlag = DtUtils.YY2(list11.get(0).getPlmn());
//                                Log.d("gsmInfoList", "onCheckedChanged:?????? ");
//                            } else {
//                                et_taclac.setText("");
//                                et_eci.setText("");
//                                jizhanFlag = 44;
//                            }
//
//                        }
//
//                    } else {
//                        et_taclac.setText("");
//                        et_eci.setText("");
//                        jizhanFlag = 44;
//                    }
//
//                } else {
//                    et_taclac.setText("");
//                    et_eci.setText("");
//                    jizhanFlag = 44;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                }
//
//            }
//        });
//        rb_bdjz2 = inflate.findViewById(R.id.rb_bdjz2);
//        rb_bdjz2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    rb_yidong.setChecked(false);
//                    rb_liantong.setChecked(false);
//                    rb_ldainxin4.setChecked(false);
//                    rb_bdjz1.setChecked(false);
//                    rb_cdma.setChecked(false);
////                    jizhanFlag = 4;
//                    ll_sid.setVisibility(View.GONE);
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//
//
//                    List<SpBean2> gsmInfoList = DtUtils.getGsmInfoList(mContext);
//                    List<SpBean2> gsmInfoListremove = new ArrayList<>();
//                    List<SpBean2> list11 = new ArrayList<>();
//                    List<SpBean2> list22 = new ArrayList<>();
//                    if (gsmInfoList.size() > 0 && gsmInfoList != null) {//?????????
//                        if (gsmInfoList.size() == 1) {//????????????
//                            for (int i = 0; i < gsmInfoList.size(); i++) {
//
//                                int cid = Integer.parseInt(gsmInfoList.get(i).getCid());
//                                int tac = Integer.parseInt(gsmInfoList.get(i).getTac());
//                                if (cid != 2147483647 && tac != 2147483647) {
////                                gsmInfoList.remove(i);
//                                    list11.add(gsmInfoList.get(i));
//                                }
//                            }
//
//                            et_taclac.setText(gsmInfoList.get(0).getTac() + "");
//                            et_eci.setText(gsmInfoList.get(0).getCid() + "");
//                            jizhanFlag = DtUtils.YY2(gsmInfoList.get(0).getPlmn());
//                            Log.d("gsmInfoList", "onCheckedChanged:?????? ");
//                        } else {//??????
//                            for (int i = 0; i < gsmInfoList.size(); i++) {
//                                int cid = Integer.parseInt(gsmInfoList.get(i).getCid());
//                                int tac = Integer.parseInt(gsmInfoList.get(i).getTac());
//
//                                if (cid != 2147483647 && tac != 2147483647) {
////                                gsmInfoList.remove(i);
//                                    gsmInfoListremove.add(gsmInfoList.get(i));
//                                }
//
//                            }
//                            if (gsmInfoListremove.size() > 1) {
//                                list11.add(gsmInfoListremove.get(0));
//                                list22.add(gsmInfoListremove.get(1));
//                            } else if (gsmInfoListremove.size() == 1) {
//                                list11.add(gsmInfoListremove.get(0));
//                            }
//                            if (list22.size() > 0) {
//                                et_taclac.setText(list22.get(0).getTac() + "");
//                                et_eci.setText(list22.get(0).getCid() + "");
//                                jizhanFlag = DtUtils.YY2(list22.get(0).getPlmn());
//                                Log.d("gsmInfoList", "onCheckedChanged:?????? ");
//                            } else {
//                                et_taclac.setText("");
//                                et_eci.setText("");
//                                jizhanFlag = 44;
//                            }
//
//                        }
//
//                    } else {
//                        et_taclac.setText("");
//                        et_eci.setText("");
//                        jizhanFlag = 44;
//                    }
//                } else {
//                    et_taclac.setText("");
//                    et_eci.setText("");
//                    jizhanFlag = 44;
//                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
//                }
//
//            }
//        });
//        bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
//        bt_adddilao.setOnClickListener(this);
//        String s = ACacheUtil.getjzType();
//        if (TextUtils.isEmpty(s)) {
//
//
//        } else {
//            jizhanFlag = Integer.parseInt(s + "");
//        }
//
//        if (jizhanFlag == 0) {//?????????0 ????????????
//            rb_yidong.setChecked(true);
//            rb_liantong.setChecked(false);
//            rb_cdma.setChecked(false);
//            rb_ldainxin4.setChecked(false);
//        }
//        if (jizhanFlag == 1) {//?????????1 ????????????
//            rb_yidong.setChecked(false);
//            rb_liantong.setChecked(true);
//            rb_cdma.setChecked(false);
//            rb_ldainxin4.setChecked(false);
//        }
//        if (jizhanFlag == 11) {//?????????11 ????????????
//            rb_yidong.setChecked(false);
//            rb_liantong.setChecked(false);
//            rb_cdma.setChecked(false);
//            rb_ldainxin4.setChecked(true);
//        }
//        if (jizhanFlag == 4) {//?????????4 ??????cdma
//            rb_yidong.setChecked(false);
//            rb_liantong.setChecked(false);
//            rb_cdma.setChecked(true);
//            rb_ldainxin4.setChecked(false);
//        }
//
//        //??????????????????Dialog
//        dialog.setContentView(inflate);
//        //????????????Activity???????????????
//        Window dialogWindow = dialog.getWindow();
//        //??????Dialog?????????????????????
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        //?????????????????????
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
////       ????????????????????????
//        dialogWindow.setAttributes(lp);
//        dialog.show();//???????????????
//    }
//
//
//    private CallBack callBack = new CallBack() {
//        @Override
//        public void call(String data, int i) {
//            Log.d("nzq", "call: " + data + "???--" + i);
//            list.remove(i);//?????????button// ??????
////            list.set(i, 6);//??????
//            if (demoAdapter != null) {
//                demoAdapter.notifyDataSetChanged();
//            }
//            if (serrnAdapters != null) {
//                serrnAdapters.notifyDataSetChanged();
//            }
//
//        }
//
//
//    };
//
//    private void saveIsShow() {
//        ACacheUtil.putTl(et_taclac.getText().toString());
//        ACacheUtil.putEci(et_eci.getText().toString());
//        ACacheUtil.putSID(et_sid.getText().toString() + "");
////        ACacheUtil.putTa(et_ta.getText().toString());
////        ACacheUtil.putTa(MyUtils.listToString(list));
//        ACacheUtil.putjzType(jizhanFlag + "");
//    }
//
//
//    //????????????????????????
//    private void sendPost() {
//        if(DATATYPE==6){//?????????????????????
////            sendMax();
//            String key = "APPCODE eb358bdc0f28475e95ccbe24eb2e5ee7";
//            Log.d(TAG, "sendPostjizhanFlag:" + jizhanFlag);
//            final int Flag;
//            if (jizhanFlag == 4) {
//                Flag = Integer.parseInt(et_sid.getText().toString());
//            } else {
//                Flag = jizhanFlag;
//            }
////            MyUtils.getNumber(ACacheUtil.getID());//????????????
//            dialog.dismiss();
//
//
//            if(jizhanFlag==4||jizhanFlag==11){//?????????????????????4G??????2G??????
//                Call<DataAliBean> call = RetrofitFactory.getInstence().API().GETBaseStationAliCdm(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()), key, "application/json; charset=UTF-8");
//                call.enqueue(new Callback<DataAliBean>() {
//                    @Override
//                    public void onResponse(Call<DataAliBean> call, Response<DataAliBean> response) {
//                        dataAliBean = response.body();
//                        Log.d("?????????", "nzqonResponse: " + response.toString());
//                        if (dataAliBean!=null){
//                            if (dataAliBean.getMsg().equals("ok") && dataAliBean.getStatus() == 0) {
//                                try {
//                                    String latresult = dataAliBean.getResult().getLat();
//                                    String lonresult = dataAliBean.getResult().getLng();
//                                    LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                                    Log.d("?????????", "dataBeanisShowjizhan: " + dataAliBean.getResult());
//                                    CoordinateConverter converter = new CoordinateConverter()
//                                            .from(CoordinateConverter.CoordType.GPS)
//                                            .coord(latLngresult);
//
//                                    //????????????
//                                    LatLng desLatLngBaidu = converter.convert();
//                                    GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//
//                                    if(jizhanFlag==4){
//                                        d.setSid(et_sid.getText().toString());
//                                    }else{
//                                        d.setSid("");
//                                    }
//                                    d.setTypes("");
//                                    d.setBand("");
//                                    d.setPci("");
//                                    d.setDown("");
//                                    d.setId(1);
//                                    d.setAddress(dataAliBean.getResult().getAddr());
//                                    d.setCi(et_eci.getText().toString());
//                                    d.setLac(et_taclac.getText().toString());
//                                    d.setMcc("460");
//                                    if(jizhanFlag==4){
//                                        d.setMnc(et_sid.getText().toString());
//                                    }else{
//                                        d.setMnc("11");
//                                    }
//                                    d.setRadius("0");//???????????????????????????0
//                                    d.setTa(MyUtils.listToString(list));
//                                    d.setType(0);
//                                    d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                                    d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                                    d.setResources("????????????");
//                                    int i = dbManagerJZ.insertStudent(d);
//                                    MapStatus.Builder builder = new MapStatus.Builder();
//                                    builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                    initdatas();
//                                    list.clear();
//                                    dialog.dismiss();
//                                    isShowAliJz(true);//??????????????????
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                    Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                                }
//
//                            }else if(dataAliBean.getMsg().equals("????????????")){
////                                MyToast.showToast("?????????????????????????????????????????????");
//                                //??????????????????????????????????????????????????????
//                                getJuHeData();
////                                 dialog.dismiss();
//                            }
//                        }else {
////                            MyToast.showToast("??????????????????????????????????????????");
//                            getJuHeData();//???????????????????????????
////                                dialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<DataAliBean> call, Throwable t) {
//
//                    }
//                });
//            }else{//????????????????????????
//                try {
//                    RetrofitFactory.getInstence().API().GETBaseStationAli(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()),key, "application/json; charset=UTF-8").enqueue(new Callback<DataAliBean>() {
//                        @Override
//                        public void onResponse(Call<DataAliBean> call, Response<DataAliBean> response) {
//                            dataAliBean = response.body();
//                            Log.d("?????????", "nzqonResponse: " + response.toString());
//                            if (dataAliBean!=null){
//                                if (dataAliBean.getMsg().equals("ok") && dataAliBean.getStatus() == 0) {
//                                    try {
//                                        String latresult = dataAliBean.getResult().getLat();
//                                        String lonresult = dataAliBean.getResult().getLng();
//                                        LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                                        Log.d("?????????", "dataBeanisShowjizhan: " + dataAliBean.getResult());
//                                        CoordinateConverter converter = new CoordinateConverter()
//                                                .from(CoordinateConverter.CoordType.GPS)
//                                                .coord(latLngresult);
//                                        //????????????
//                                        LatLng desLatLngBaidu = converter.convert();
//                                        GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                                        if (jizhanFlag == 0||jizhanFlag==1) {
//                                            d.setSid("");
//                                        }
//                                        d.setTypes("");
//                                        d.setBand("");
//                                        d.setPci("");
//                                        d.setDown("");
//                                        d.setId(1);
//                                        d.setAddress(dataAliBean.getResult().getAddr());
//                                        d.setCi(et_eci.getText().toString());
//                                        d.setLac(et_taclac.getText().toString());
//                                        if(Flag==0){//????????????
//                                            d.setMcc("460");
//                                            d.setMnc("0");
//                                        }
//                                        if(Flag==1){//??????????????????
//                                            d.setMcc("460");
//                                            d.setMnc("1");
//                                        }
//                                        d.setRadius("0");//???????????????????????????0
//                                        Log.d("?????????", "onResponse:list" + list);
//                                        d.setTa(MyUtils.listToString(list));
//                                        d.setType(0);
//                                        d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                                        d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                                        d.setResources("????????????");
//                                        int i = dbManagerJZ.insertStudent(d);
//                                        MapStatus.Builder builder = new MapStatus.Builder();
//                                        builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                        initdatas();
//                                        list.clear();
//                                        dialog.dismiss();
//                                        isShowAliJz(true);//??????????????????
//                                    } catch (SQLException e) {
//                                        e.printStackTrace();
//                                        Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                                    }
//                                }else if(dataAliBean.getMsg().equals("????????????")){
////                                    MyToast.showToast("?????????????????????????????????????????????");
//                                    //??????????????????????????????????????????????????????
//                                    getJuHeData();
////                                    dialog.dismiss();
//                                }
//
//                            }else {
////                                MyToast.showToast("??????????????????????????????????????????");
//                                getJuHeData();//???????????????????????????
////                                dialog.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DataAliBean> call, Throwable t) {
//                            Log.d("?????????", "nzqonResponse: " + t.getMessage());
//                        }
//                    });
//                } catch (Exception e) {
//                    MyToast.showToast("?????????????????????????????????????????????");
//                }
//            }
////            Toast.makeText(MainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
//        }
//        if (DATATYPE == 2) {
//            if (jizhanFlag == 11) {
////                Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
//                MyToast.showToast("????????????????????????");
//                dialog.dismiss();
//                return;
//            }
//            if (jizhanFlag == 4) {
//
//                MyToast.showToast("????????????????????????");
//                dialog.dismiss();
//                return;
//            }
//            RetrofitFactory.getInstence().API().QuerySm(String.valueOf(jizhanFlag), Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString())).enqueue(new Callback<JzDataQury>() {
//                @Override
//                public void onResponse(Call<JzDataQury> call, Response<JzDataQury> response) {
//                    Log.d(TAG, "DATATYPEonResponse: " + "" + response.body().toString());
//                    JzDataQury jzGetData = response.body();
//                    if (jzGetData.getCode() == 1 && jzGetData.getData() != null) {
//                        Log.d(TAG, "onResponse:jzgetData" + "????????????" + jzGetData.getData());
//                        try {
//                            String latresult = String.valueOf(jzGetData.getData().getLatitude());
//                            String lonresult = String.valueOf(jzGetData.getData().getLongitude());
//                            LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
////                            Log.d(TAG, "dataBeanisShowjizhan: " + dataBean.getResult());
////            LatLng latLngresult = new LatLng(Double.parseDouble("38.031242"), Double.parseDouble("114.450186"));
//
//                            CoordinateConverter converter = new CoordinateConverter()
//                                    .from(CoordinateConverter.CoordType.GPS)
//                                    .coord(latLngresult);
//                            //????????????
//                            LatLng desLatLngBaidu = converter.convert();
//                            GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                            if (jizhanFlag == 4) {
//                                d.setSid(et_sid.getText().toString());
//                            } else {
//                                d.setSid("");
//                            }
//                            d.setTypes("" + jzGetData.getData().getType());
//                            d.setBand("" + jzGetData.getData().getBand());
//                            d.setPci("" + jzGetData.getData().getPci());
//                            d.setDown("" + jzGetData.getData().getDownFrequencyPoint());
//                            d.setId(1);
//                            d.setId(1);
//                            d.setAddress(jzGetData.getData().getAreaName() + "");
//                            d.setCi(et_eci.getText().toString() + "");
//                            d.setLac(et_taclac.getText().toString() + "");
//                            d.setMcc("");
//                            d.setMnc(jzGetData.getData().getMnc() + "");
//                            d.setRadius("0");
////                        d.setTa(et_ta.getText().toString());
//                            d.setTa(MyUtils.listToString(list));
//                            d.setType(0);
//                            d.setResources("????????????");
//                            d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                            d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                            dbManagerJZ.insertStudent(d);
//                            initdatas();
//                            //????????????
//                            MapStatus.Builder builder = new MapStatus.Builder();
//                            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                            list.clear();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                            Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                        }
//                    }
//                    dialog.dismiss();
//
//                    if (jzGetData.getCode() == 0 && jzGetData.getData() == null) {
//
//                        MyToast.showToast("????????????????????????");
//                        dialog.dismiss();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<JzDataQury> call, Throwable t) {
//
//                }
//            });
//        }
////        rb_Manuallyenter  116.33068,40.050716
//        if (DATATYPE == 3) {//????????????
////            LatLng latLng = new LatLng(Double.parseDouble("40.050716"), Double.parseDouble("116.33068"));
//            if (TextUtils.isEmpty(ets_lon.getText().toString())) {
//                ToastUtils.showShort("??????????????????");
//                return;
//            }
//            if (TextUtils.isEmpty(ets_lat.getText().toString())) {
//                ToastUtils.showShort("??????????????????");
//                return;
//            }
//            LatLng latLng = new LatLng(Double.parseDouble(ets_lat.getText().toString()), Double.parseDouble(ets_lon.getText().toString()));
//            SdmSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).pageNum(0).pageSize(100));
//
//            try {
//                dbManagerJZ = new DBManagerJZ(mContext);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
////            LatLng latLng=new LatLng(Double.parseDouble(ets_lon.getText().toString()),Double.parseDouble(ets_lat.getText().toString()));
////                LatLng latLng = new LatLng(Double.parseDouble("40.050716"), Double.parseDouble("116.33068"));
//            CoordinateConverter converter = new CoordinateConverter()
//                    .from(CoordinateConverter.CoordType.GPS)
//                    .coord(latLng);
//            //????????????
//            LatLng desLatLngBaidu = converter.convert();
//            GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
////                if (jizhanFlag == 4) {
////                    d.setSid(et_sid.getText().toString());
////                } else {
////                    d.setSid("");
////                }
//            d.setTypes("");
//            d.setBand("");
//            d.setPci("");
//            d.setDown("");
//            d.setId(1);
//            d.setAddress("");
//            d.setCi(et_eci.getText().toString() + "");
//            d.setLac(et_taclac.getText().toString() + "");
//            d.setMcc("");
//            d.setMnc("");
//            d.setRadius("");
////                        d.setTa(et_ta.getText().toString());
//            Log.d(TAG, "onResponse:aalist" + list);
//            d.setTa(MyUtils.listToString(list));
//            d.setType(0);
//            d.setLat(String.valueOf(desLatLngBaidu.latitude));
//            d.setLon(String.valueOf(desLatLngBaidu.longitude));
//            d.setResources("????????????");
//            try {
//                dbManagerJZ.insertStudent(d);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            MapStatus.Builder builder = new MapStatus.Builder();
//            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            initdatas();
//
//
////            Log.d("2ilssDetail1", "sendPost: ");
//            dialog.dismiss();
//        }
//
//    }
//
//    private void getJuHeData() {
////        if (DATATYPE == 1) {//?????????????????? ??????
////            sendMax();
//        String key = "4283a37b42d1e381f4ffa6bf9e8ecc96";
//        Log.d(TAG, "sendPostjizhanFlag:" + jizhanFlag);
//        int Flag = 0;
//        if (jizhanFlag == 4) {
//            Flag = Integer.parseInt(et_sid.getText().toString());
//        } else {
//            Flag = jizhanFlag;
//        }
////        MyUtils.getNumber(ACacheUtil.getID());//????????????
//        dialog.dismiss();
//        try {
//            RetrofitFactory.getInstence().API().GETBaseStation(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()), key).enqueue(new Callback<DataBean>() {
//                @Override
//                public void onResponse(Call<DataBean> call, Response<DataBean> response) {
//                    Log.d(TAG, "nzqonResponse: " + response.toString());
//
//                    dataBean = response.body();
//                    if (dataBean.getReason().equals("????????????") && dataBean.getError_code() == 0) {
//                        try {
//                            String latresult = dataBean.getResult().getLat();
//                            String lonresult = dataBean.getResult().getLon();
//                            LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                            Log.d(TAG, "dataBeanisShowjizhan: " + dataBean.getResult());
////            LatLng latLngresult = new LatLng(Double.parseDouble("38.031242"), Double.parseDouble("114.450186"));
//
//                            CoordinateConverter converter = new CoordinateConverter()
//                                    .from(CoordinateConverter.CoordType.GPS)
//                                    .coord(latLngresult);
//                            //????????????
//                            LatLng desLatLngBaidu = converter.convert();
//                            GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                            if (jizhanFlag == 4) {
//                                d.setSid(et_sid.getText().toString());
//                            } else {
//                                d.setSid("");
//                            }
//                            d.setTypes("");
//                            d.setBand("");
//                            d.setPci("");
//                            d.setDown("");
//                            d.setId(1);
//                            d.setAddress(dataBean.getResult().getAddress());
//                            d.setCi(dataBean.getResult().getCi());
//                            d.setLac(dataBean.getResult().getLac());
//                            d.setMcc(dataBean.getResult().getMcc());
//                            d.setMnc(dataBean.getResult().getMnc());
//                            d.setRadius(dataBean.getResult().getRadius());
////                        d.setTa(et_ta.getText().toString());
//                            Log.d(TAG, "onResponse:aalist" + list);
//                            d.setTa(MyUtils.listToString(list));
//                            d.setType(0);
//                            d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                            d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                            d.setResources("????????????");
//                            int i = dbManagerJZ.insertStudent(d);
//                            MapStatus.Builder builder = new MapStatus.Builder();
//                            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                            initdatas();
//                            list.clear();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                            Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                        }
//
//                    }
//                    dialog.dismiss();
//                    isShowjizhan(true);//????????????
//                }
//
//                @Override
//                public void onFailure(Call<DataBean> call, Throwable t) {
//                    Log.d(TAG, "nzqonResponse: " + t.getMessage());
//
//                }
//            });
//        } catch (Exception e) {
//            MyToast.showToast("?????????????????????????????????????????????");
//        }
//    }
//    //????????????????????????
//    private void isShowAliJz(boolean b){
//        if(dataAliBean.getStatus()==0&&dataAliBean.getMsg().equals("ok")){
//            MyToast.showToast("????????????");
////            MyToast.showToast(dataAliBean.getResult().toString());
//        }
//        if(dataAliBean.getStatus()==201){
//            dialog.dismiss();
//        }
//        if(dataAliBean.getStatus()==202){
//            dialog.dismiss();
//
//        }
//        if(dataAliBean.getStatus()==210){
//            MyToast.showToast(dataAliBean.getMsg());
//            dialog.dismiss();
//        }
//    }
//
//    //????????????
//    private void isShowjizhan(boolean tag) {
//        if (dataBean.getReason().equals("????????????") && dataBean.getError_code() == 0) {
//
//            MyToast.showToast("????????????");
//            Log.d(TAG, "onResponse: " + dataBean.getResult());
////
//
//        }
//        if (dataBean.getError_code() == 200801) {
//            MyToast.showToast("?????????LAC???CELLID");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 200803) {
//
//            MyToast.showToast("???????????????????????????");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10001) {
//            MyToast.showToast("???????????????KEY");
////            Toast.makeText(MainActivity.this, "???????????????KEY", Toast.LENGTH_LONG).show();
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10002) {
//            MyToast.showToast("???KEY???????????????");
////            Toast.makeText(MainActivity.this, "???KEY???????????????", Toast.LENGTH_LONG).show();
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10003) {
////            Toast.makeText(MainActivity.this, "KEY??????", Toast.LENGTH_LONG).show();
//            MyToast.showToast("KEY??????");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10004) {
////            Toast.makeText(MainActivity.this, "?????????OPENID", Toast.LENGTH_LONG).show();
//            MyToast.showToast("?????????OPENID");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10005) {
////            Toast.makeText(MainActivity.this, "???????????????????????????????????????", Toast.LENGTH_LONG).show();
//            MyToast.showToast("???????????????????????????????????????");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10007) {
//            MyToast.showToast("??????????????????");
////            Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10008) {
//            MyToast.showToast("????????????IP");
////            Toast.makeText(MainActivity.this, "????????????IP", Toast.LENGTH_LONG).show();
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10009) {
////            Toast.makeText(MainActivity.this, "????????????KEY", Toast.LENGTH_LONG).show();
//            MyToast.showToast("????????????KEY");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10011) {
////            Toast.makeText(MainActivity.this, "??????IP??????????????????", Toast.LENGTH_LONG).show();
//            MyToast.showToast("??????IP??????????????????");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10012) {
//            MyToast.showToast("????????????????????????");
////            Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_LONG).show();
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10013) {
////            Toast.makeText(MainActivity.this, "??????KEY??????????????????", Toast.LENGTH_LONG).show();
//            MyToast.showToast("??????KEY??????????????????");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10014) {
////            Toast.makeText(MainActivity.this, "??????????????????", Toast.LENGTH_LONG).show();
//            MyToast.showToast("??????????????????");
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10020) {
//            MyToast.showToast("????????????");
////            Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
//            dialog.dismiss();
//        }
//        if (dataBean.getError_code() == 10022) {
////            Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_LONG).show();
//            MyToast.showToast("????????????");
//            dialog.dismiss();
//        }
//
//    }
//
//    @TargetApi(Build.VERSION_CODES.N)
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (resultCode == 11) {//JzListActivity  ???????????? id ????????????
//            initdatas2();//??????????????????????????? ?????? ???????????????
//
//            try {
//                String id = data.getStringExtra("id");
//                final GuijiViewBeanjizhan guijiViewBeanjizhan = dbManagerJZ.forid(Integer.parseInt(id));
//                Log.d(TAG, "aaonActivityResult: " + guijiViewBeanjizhan);
//
//                View view = View.inflate(mContext, R.layout.activity_map_info, null);
//
//                TextView tv_title = view.findViewById(R.id.tv_title);
//                String str = "";
//                if (guijiViewBeanjizhan.getMnc().equals("0")) {
//                    str = "??????";
//                } else if (guijiViewBeanjizhan.getMnc().equals("1")) {
//                    str = "??????";
//                } else if (guijiViewBeanjizhan.getMnc().equals("11")) {
//                    str = "??????";
//                } else {//?????????cdma?????? sid??????
//                    str = "CDMA";
//                    TextView tv_sid = view.findViewById(R.id.tv_sid);
//                    tv_sid.setText(guijiViewBeanjizhan.getSid());
//                    LinearLayout llsid = view.findViewById(R.id.llsid);
//                    llsid.setVisibility(View.VISIBLE);
//                }
//                TextView tv_resources = view.findViewById(R.id.tv_resources);
////            aaa
//                if (guijiViewBeanjizhan.getResources().equals("????????????")) {
//                    LinearLayout ll_types = view.findViewById(R.id.ll_types);
//                    LinearLayout ll_pci = view.findViewById(R.id.ll_pci);
//                    LinearLayout ll_band = view.findViewById(R.id.ll_band);
//                    LinearLayout ll_down = view.findViewById(R.id.ll_down);
//                    ll_types.setVisibility(View.VISIBLE);
//                    ll_pci.setVisibility(View.VISIBLE);
//                    ll_band.setVisibility(View.VISIBLE);
//                    ll_down.setVisibility(View.VISIBLE);
//
//                    TextView tv_band = view.findViewById(R.id.tv_band);
//                    TextView tv_types = view.findViewById(R.id.tv_types);
//                    TextView tv_pci = view.findViewById(R.id.tv_pci);
//                    TextView tv_down = view.findViewById(R.id.tv_down);
//                    tv_band.setText(guijiViewBeanjizhan.getBand() + "");
//                    tv_types.setText(guijiViewBeanjizhan.getType() + "");
//                    tv_pci.setText(guijiViewBeanjizhan.getPci() + "");
//                    tv_down.setText(guijiViewBeanjizhan.getDown() + "");
//
//                    tv_resources.setText(guijiViewBeanjizhan.getResources() + "");
//                }
//                tv_resources.setText(guijiViewBeanjizhan.getResources() + "");
//
//                tv_title.setText(str + "");
//                TextView tv_fugai = view.findViewById(R.id.tv_fugai);
//                tv_fugai.setText(guijiViewBeanjizhan.getRadius() + "");
//                TextView tv_mnc = view.findViewById(R.id.tv_mnc);
//                tv_mnc.setText(guijiViewBeanjizhan.getMnc());
//                TextView tv_lac = view.findViewById(R.id.tv_lac);
//                tv_lac.setText(guijiViewBeanjizhan.getLac());
//                TextView tv_cid = view.findViewById(R.id.tv_cid);
//                tv_cid.setText(guijiViewBeanjizhan.getCi());
//                TextView tv_address = view.findViewById(R.id.tv_address);
//                tv_address.setText(guijiViewBeanjizhan.getAddress());
//                TextView tv_lat_lon = view.findViewById(R.id.tv_lat_lon);
//
//                DecimalFormat df = new DecimalFormat(".######");
//                final String lats = guijiViewBeanjizhan.getLat();
//                final String lons = guijiViewBeanjizhan.getLon();
//                double dlat = Double.parseDouble(lats);
//                double dlons = Double.parseDouble(lons);
//                tv_lat_lon.setText("??????:" + df.format(dlat) + ",??????:" + df.format(dlons));
//                ImageButton bt_openMap = view.findViewById(R.id.bt_openMap);
//                Button bt_quanjing = view.findViewById(R.id.bt_quanjing);
//                bt_m_locations = view.findViewById(R.id.bt_m_location);//??????????????????
//                if (String.valueOf(guijiViewBeanjizhan.getType()).equals("1")) {
//                    bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
//                } else {
//                    bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojinglan1));
//                }
//                bt_m_locations.setVisibility(View.GONE); //??????????????????
//                bt_m_locations.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Bundle extraInfo = new Bundle();
//                        extraInfo.putString("id", guijiViewBeanjizhan.getId() + "");
////                    aaaaa
//                        new CircleDialog.Builder()
//                                .setTitle("")
//                                .setWidth(0.5f)
//                                .setMaxHeight(0.5f)
//                                .setText("??????????????????????????????")
//                                .setTitleColor(Color.parseColor("#00acff"))
//                                .setNegative("??????", new Positiv(4, extraInfo))
//                                .setPositive("??????", null)
//                                .show(getFragmentManager());
//                    }
//                });
//                Button bt_taset = view.findViewById(R.id.bt_taset);//TA???????????????
//                bt_taset.setVisibility(View.VISIBLE);
//                bt_taset.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id", String.valueOf(guijiViewBeanjizhan.getId()));
//                        Intent intent = new Intent(mContext, TaActivity.class);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        startActivityForResult(intent, 13);
//                    }
//                });
//                Button bt_m_dele = view.findViewById(R.id.bt_m_dele);
//                bt_m_dele.setVisibility(View.VISIBLE);
//                if (String.valueOf(guijiViewBeanjizhan.getType()).equals("2")) {
//                    bt_m_dele.setBackground(getResources().getDrawable(R.mipmap.markeradd));
//                }
//                bt_m_dele.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        if (String.valueOf(guijiViewBeanjizhan.getType()).equals("2")) {//?????????????????????
//                            listMarker.clear();
//                            demoAdapteradd = new DemoAdapteradd(listMarker, addcallBack);
////                            Myshow.show(MainActivity.this, extraInfo, dbManagerJZ, mycallback, addcallBack, listMarker, demoAdapteradd);
//
//
//                        } else {
//                            try {
//                                String dele = guijiViewBeanjizhan.getId() + "";
//                                new CircleDialog.Builder()
//                                        .setTitle("")
//                                        .setWidth(0.7f)
//                                        .setMaxHeight(0.7f)
//                                        .setText("????????????????????????")
//                                        .setTitleColor(Color.parseColor("#00acff"))
//                                        .setNegative("??????", new Positiv(3, dele))
//                                        .setPositive("??????", null)
//                                        .show(getFragmentManager());
//
//                            } catch (Exception e) {
//                                Log.d(TAG, "panderonClick: " + "3" + e.getMessage());
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//                });
//                ImageButton iv_finishs = view.findViewById(R.id.iv_finishs);
//                iv_finishs.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mBaiduMap.hideInfoWindow();
//
//                    }
//                });
//                bt_openMap.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                        //???????????????
//
//                        dialog2 = new Dialog(mContext, R.style.ActionSheetDialogStyle);
//                        //????????????????????????
//                        inflate = LayoutInflater.from(mContext).inflate(R.layout.item_dibushowdaohao, null);
//                        //???????????????
//                        TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
////        choosePhoto.setVisibility(View.GONE);
//                        TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
////                            baidu.setOnClickListener(new MyonclickXian(mylag));
//                        baidu.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                try {
//                                    LatLng startLatLng = new LatLng(39.940387, 116.29446);
//                                    LatLng endLatLng = new LatLng(39.87397, 116.529025);
//                                    String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
//                                                    "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
//                                            guijiViewBeanjizhan.getLat(), guijiViewBeanjizhan.getLon());
//                                    Intent intent = new Intent();
//                                    intent.setData(Uri.parse(uri));
//                                    startActivity(intent);
//                                } catch (ActivityNotFoundException e) {
//                                    MyToast.showToast("?????????????????????");
//                                }
//                            }
//                        });
//                        gaode.setOnClickListener(new MyonclickXian(mylag, guijiViewBeanjizhan.getLat(), guijiViewBeanjizhan.getLon(), (Activity) mContext));
//                        //??????????????????Dialog
//                        dialog2.setContentView(inflate);
//                        //????????????Activity???????????????
//                        Window dialogWindow = dialog2.getWindow();
//                        //??????Dialog?????????????????????
//                        dialogWindow.setGravity(Gravity.BOTTOM);
//                        //?????????????????????
//                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                        lp.y = 20;//??????Dialog?????????????????????
////       ????????????????????????
//                        dialogWindow.setAttributes(lp);
//                        dialog2.show();//???????????????
//                    }
//                });
//                bt_quanjing.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(mContext, PanoramaDemoActivityMain.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("lat", guijiViewBeanjizhan.getLat());
//                        bundle.putString("lon", guijiViewBeanjizhan.getLon());
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                    }
//                });
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(new LatLng(Double.parseDouble(guijiViewBeanjizhan.getLat()), Double.parseDouble(guijiViewBeanjizhan.getLon()))).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                final InfoWindow mInfoWindow = new InfoWindow(view, new LatLng(Double.parseDouble(guijiViewBeanjizhan.getLat()), Double.parseDouble(guijiViewBeanjizhan.getLon())), -47);
//                mBaiduMap.showInfoWindow(mInfoWindow);
//
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//                Log.d(TAG, "onActivityResult: " + e.getMessage());
//            }
//
//        }
//        if (resultCode == 12) {
//            initdatas2();
//            Log.d(TAG, "onActivityResult: 12");
//        }
//        if (resultCode == 13) {
//            initdatas2();
//            Log.d(TAG, "onActivityResult: 12");
//        }
//        if (resultCode == 14) {
//            initdatas2();
//            ll_screen.setVisibility(View.VISIBLE);
//            Log.d(TAG, "onActivityResult: 12");
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.jzlist://????????????
//                Intent intent = new Intent(mContext, JzListActivity.class);
//                startActivityForResult(intent, 1);
//                break;
//        }
//        return false;
//    }
//
//
//
//    @Override
//    public void onStart() {
//        // TODO Auto-generated method stub
//        super.onStart();
//
//    }
//    private LocationService locationService;
//    /*****
//     *
//     * ???????????????????????????onReceiveLocation???????????????????????????????????????????????????????????????
//     *
//     */
//    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
//
//        /**
//         * ????????????????????????
//         * @param location ????????????
//         */
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            Log.e("ylt", "onReceiveLocation: " );
//            // TODO Auto-generated method stub
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
//                int tag = 1;
//                StringBuffer sb = new StringBuffer(256);
//                sb.append("time : ");
//                /**
//                 * ?????????????????????systemClock.elapsedRealtime()?????? ?????????????????????????????????????????????????????????
//                 * location.getTime() ???????????????????????????????????????????????????????????????????????????????????????
//                 */
//                sb.append(location.getTime());
//                sb.append("\nlocType : ");// ????????????
//                sb.append(location.getLocType());
//                sb.append("\nlocType description : ");// *****???????????????????????????*****
//                sb.append(location.getLocTypeDescription());
//                sb.append("\nlatitude : ");// ??????
//                sb.append(location.getLatitude());
//                sb.append("\nlongtitude : ");// ??????
//                sb.append(location.getLongitude());
//                sb.append("\nradius : ");// ??????
//                sb.append(location.getRadius());
//                sb.append("\nCountryCode : ");// ?????????
//                sb.append(location.getCountryCode());
//                sb.append("\nProvince : ");// ????????????
//                sb.append(location.getProvince());
//                sb.append("\nCountry : ");// ????????????
//                sb.append(location.getCountry());
//                sb.append("\ncitycode : ");// ????????????
//                sb.append(location.getCityCode());
//                sb.append("\ncity : ");// ??????
//                sb.append(location.getCity());
//                sb.append("\nDistrict : ");// ???
//                sb.append(location.getDistrict());
//                sb.append("\nTown : ");// ???????????????
////                sb.append(location.getTown());
//                sb.append("\nStreet : ");// ??????
//                sb.append(location.getStreet());
//                sb.append("\naddr : ");// ????????????
//                sb.append(location.getAddrStr());
//                sb.append("\nStreetNumber : ");// ??????????????????
//                sb.append(location.getStreetNumber());
//                sb.append("\nUserIndoorState: ");// *****?????????????????????????????????*****
//                sb.append(location.getUserIndoorState());
//                sb.append("\nDirection(not all devices have value): ");
//                sb.append(location.getDirection());// ??????
//                sb.append("\nlocationdescribe: ");
//                sb.append(location.getLocationDescribe());// ?????????????????????
//                sb.append("\nPoi: ");// POI??????
////                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
////                    for (int i = 0; i < location.getPoiList().size(); i++) {
////                        Poi poi = (Poi) location.getPoiList().get(i);
////                        sb.append("poiName:");
////                        sb.append(poi.getName() + ", ");
////                        sb.append("poiTag:");
////                        sb.append(poi.getTags() + "\n");
////                    }
////                }
////                if (location.getPoiRegion() != null) {
////                    sb.append("PoiRegion: ");// ????????????????????????poi?????????????????????????????????????????????POI????????????????????????????????????????????????????????????????????????null
////                    PoiRegion poiRegion = location.getPoiRegion();
////                    sb.append("DerectionDesc:"); // ??????POIREGION??????????????????ex:"???"
////                    sb.append(poiRegion.getDerectionDesc() + "; ");
////                    sb.append("Name:"); // ??????POIREGION??????????????????
////                    sb.append(poiRegion.getName() + "; ");
////                    sb.append("Tags:"); // ??????POIREGION?????????
////                    sb.append(poiRegion.getTags() + "; ");
////                    sb.append("\nSDK??????: ");
////                }
//                sb.append(locationService.getSDKVersion()); // ??????SDK??????
//                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS????????????
//                    sb.append("\nspeed : ");
//                    sb.append(location.getSpeed());// ?????? ?????????km/h
//                    sb.append("\nsatellite : ");
//                    sb.append(location.getSatelliteNumber());// ????????????
//                    sb.append("\nheight : ");
//                    sb.append(location.getAltitude());// ???????????? ????????????
//                    sb.append("\ngps status : ");
//                    sb.append(location.getGpsAccuracyStatus());// *****gps????????????*****
//                    sb.append("\ndescribe : ");
//                    sb.append("gps????????????");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ??????????????????
//                    // ???????????????
//                    if (location.hasAltitude()) {// *****?????????????????????*****
//                        sb.append("\nheight : ");
//                        sb.append(location.getAltitude());// ????????????
//                    }
//                    sb.append("\noperationers : ");// ???????????????
//                    sb.append(location.getOperators());
//                    sb.append("\ndescribe : ");
//                    sb.append("??????????????????");
//                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ??????????????????
//                    sb.append("\ndescribe : ");
//                    sb.append("??????????????????????????????????????????????????????");
//                } else if (location.getLocType() == BDLocation.TypeServerError) {
//                    sb.append("\ndescribe : ");
//                    sb.append("??????????????????????????????????????????IMEI???????????????????????????loc-bugs@baidu.com????????????????????????");
//                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("????????????????????????????????????????????????????????????");
//                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
//                    sb.append("\ndescribe : ");
//                    sb.append("???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
//                }
////                logMsg(sb.toString(), tag);
//            }
//        }
//
//        @Override
//        public void onConnectHotSpotMessage(String s, int i) {
//            super.onConnectHotSpotMessage(s, i);
//        }
//
//        /**
//         * ?????????????????????????????????????????????????????????????????????????????????????????????
//         * @param locType ??????????????????
//         * @param diagnosticType ???????????????1~9???
//         * @param diagnosticMessage ???????????????????????????
//         */
//        @Override
//        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
//            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
//            int tag = 2;
//            StringBuffer sb = new StringBuffer(256);
//            sb.append("????????????: ");
//            if (locType == BDLocation.TypeNetWorkLocation) {
//                if (diagnosticType == 1) {
//                    sb.append("?????????????????????????????????GPS???????????????GPS?????????");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 2) {
//                    sb.append("?????????????????????????????????Wi-Fi???????????????Wi-Fi?????????");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            } else if (locType == BDLocation.TypeOffLineLocationFail) {
//                if (diagnosticType == 3) {
//                    sb.append("?????????????????????????????????????????????");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            } else if (locType == BDLocation.TypeCriteriaException) {
//                if (diagnosticType == 4) {
//                    sb.append("???????????????????????????????????????????????????");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 5) {
//                    sb.append("??????????????????????????????????????????????????????????????????????????????Wi-Fi???????????????????????????????????????????????????");
//                    sb.append(diagnosticMessage);
//                } else if (diagnosticType == 6) {
//                    sb.append("?????????????????????????????????????????????????????????????????????sim????????????Wi-Fi??????");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 7) {
//                    sb.append("??????????????????????????????????????????????????????????????????????????????????????????");
//                    sb.append("\n" + diagnosticMessage);
//                } else if (diagnosticType == 9) {
//                    sb.append("???????????????????????????????????????????????????");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            } else if (locType == BDLocation.TypeServerError) {
//                if (diagnosticType == 8) {
//                    sb.append("?????????????????????????????????????????????????????????????????????APP????????????");
//                    sb.append("\n" + diagnosticMessage);
//                }
//            }
////            logMsg(sb.toString(), tag);
//        }
//    };
//}