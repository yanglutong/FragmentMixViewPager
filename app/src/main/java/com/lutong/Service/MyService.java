package com.lutong.Service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.lutong.App.MessageEvent;
import com.lutong.OrmSqlLite.Bean.SpBean;
import com.lutong.OrmSqlLite.Bean.ZmBean;
import com.lutong.OrmSqlLite.DBManagerZM;
import com.lutong.Utils.ACacheUtil;
import com.lutong.Utils.ReceiveTask;
import com.lutong.Utils.SPUtils;
import com.lutong.Utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;
import static com.lutong.Constant.Constant.BLACKLISTSB1;
import static com.lutong.Constant.Constant.IP1;
import static com.lutong.Constant.Constant.IP2;
import static com.lutong.Utils.MainUtils.ReceiveMainDateFormat1;
import static com.lutong.Utils.MainUtils.ReceiveMainDateFormat2;
import static com.lutong.Utils.MainUtils.StringAd;
import static com.lutong.Utils.MainUtils.StringPin;
import static com.lutong.Utils.MainUtils.StringTOIMEI;
import static com.lutong.Utils.ReceiveTask.hexStringToString;
import static com.lutong.Constant.Constant.DOWNPIN1;
import static com.lutong.Constant.Constant.DOWNPIN2;
import static com.lutong.Constant.Constant.DEVICENUMBER1;
import static com.lutong.Constant.Constant.HARDWAREVERSION1;
import static com.lutong.Constant.Constant.SOFTWAREVERSION1;
import static com.lutong.Constant.Constant.SNNUMBER1;
import static com.lutong.Constant.Constant.MACADDRESS1;

import static com.lutong.Constant.Constant.UBOOTVERSION1;
import static com.lutong.Constant.Constant.BOARDTEMPERATURE1;
import static com.lutong.Constant.Constant.PLMN1;

import static com.lutong.Constant.Constant.BAND1;
import static com.lutong.Constant.Constant.UP1;
import static com.lutong.Constant.Constant.DWON1;
import static com.lutong.Constant.Constant.DK1;
import static com.lutong.Constant.Constant.TAC1;
import static com.lutong.Constant.Constant.PCI1;
import static com.lutong.Constant.Constant.CELLID1;
import static com.lutong.Constant.Constant.DBM1;
import static com.lutong.Constant.Constant.TYPE1;
import static com.lutong.Constant.Constant.GZMS1;
import static com.lutong.Constant.Constant.ZHZQ1;

import static com.lutong.Constant.Constant.UEIMSI;
import static com.lutong.Constant.Constant.SBZQ1;
import static com.lutong.Constant.Constant.UEMAXOF1;
import static com.lutong.Constant.Constant.UEMAX1;

import static com.lutong.Constant.Constant.ZENGYI1;
import static com.lutong.Constant.Constant.SHUAIJIAN1;

public class MyService extends Service {
    private boolean running = false;
    public static String IP3 = "192.168.2.63";

    public MyService() {
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);

        Log.d("MyServiceTAG", "unbindService: ");
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.d("MyServiceTAG", "bindService: ");
        return super.bindService(service, conn, flags);
    }

    @Override
    public ComponentName startService(Intent service) {
        Log.d("MyServiceTAG", "startService: ");
        return super.startService(service);

    }

    @Override
    public boolean stopService(Intent name) {
        Log.d("MyServiceTAG", "stopService: ");
        return super.stopService(name);
    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.d("MyServiceTAG", "onBind: ");
        return new Binder();
    }

    /**
     * 创建一个类继承Binder,来进行
     */
    public class Binder extends android.os.Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    String ID1TF = "";
    String ID2TF = "";
    DatagramPacket dp = null;
    byte[] buf = null;
    public static DatagramSocket DS = null;
    public static DatagramSocket DS2 = null;
    public static long mPressedTime1 = 0;
    public static long mPressedTime2 = 0;
    private static long ZTLONG = 12000; //连接中
    private static long ZTLONGS = 180000;//连接失败
    public static long mPressedTime15G = 0;
    public static long mPressedTime25G = 0;
    private static long ZTLONG5G = 12000; //连接中
    private static long ZTLONGS5G = 180000;//连接失败
    public static String str3 = "";
    public static String str4 = "";
    public static String session_id = "";
    public static InetAddress ADDRESS2;
    public static String BAND5G = "";
    public static String NREF5G = "";
    public static String PCI5G = "";
    public static String SUBFRAMSSIGN5G = "";
    public static String SPECIALSUBFRM5G = "";
    public static String TAC5G = "";
    public static String POWER5G = "";
    public static String BANDWIDTH5G = "";
    public static List<String> PLMN_LIST = new ArrayList<>();
    public static List<String> IMSI_LIST = new ArrayList<>();
    public static String CELL_STATUS = "";//5G同步类型
    public static String SYNC_STATUS = "";//5G同步状态

    //测试  json
    public static JSONObject createJSONObject() throws JSONException {
        JSONObject result = new JSONObject();
        JSONObject user1 = new JSONObject();
        JSONObject user2 = new JSONObject();

        user1.put("cell_id", 1);
        user1.put("session_id", "0001");
        user1.put("msg_id", "SETUP_LINK_REQ");

        Map<String, Integer> map = new HashMap<>();
        map.put("cell_id", 1);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("cell_id", 2);
        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map2);
        user2.put("cell_list", list);
        // 返回一个JSONArray对象
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        jsonArray.put(0, user1);
        jsonArray2.put(1, user2);


        result.accumulate("header", jsonArray);
        result.accumulate("body", jsonArray2);
        return result;
    }

    public static boolean SETUP_LINK_RSP_FLAG = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        ru
//            @Overridenning = true;
////        new Thread() {
//            public void run() {
//                super.run();
//                int i = 0;
//R.drawable.btn_star_off_normal_holo_light                while (running) {
//                    i++;
//                    if (callback != null) {
//                        Log.e("lcm",i+"");
//                        callback.onDataChanged("数据=" + i);
//
//                        EventBus.getDefault().post(new MessageEvent(1001,"数据=" + i));
//                    }else {
//                        Log.e("lcm",i+"");
//                    }
//                    try {
//                        sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
        mPressedTime1 = System.currentTimeMillis();
        mPressedTime15G = System.currentTimeMillis();
        mPressedTime2 = System.currentTimeMillis();
        Timer timers = new Timer();
        Timer timers2 = new Timer();//判断是否建立5G链接


        timers.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long mNowTime = System.currentTimeMillis();//获取第一次按键时间
                if ((mNowTime - mPressedTime1) > ZTLONG) {//超过八秒就是未启动
//                    ToastUtils.showToast("再按一次退出程序");
//                        mPressedTime = mNowTime;


                    if ((mNowTime - mPressedTime1) > ZTLONGS) {
//                        Message message = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("zt1", "1");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100120;
                        EventBus.getDefault().post(new MessageEvent(100120, "连接失败"));
                    } else {
//                            Log.d(TAG, "mPressedTimerun1: ");
                        //设备1状态设定
//                        Message message = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("zt1", "0");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100120;
                        EventBus.getDefault().post(new MessageEvent(100120, "连接中"));
                    }
                } else {//代表启动
//                        Log.d(TAG, "mPressedTimerun2: ");
//                        System.exit(0);
                }
                if ((mNowTime - mPressedTime15G) > ZTLONG5G) {//超过八秒就是未启动
//                    ToastUtils.showToast("再按一次退出程序");
//                        mPressedTime = mNowTime;
//                        Log.d(TAG, "mPressedTimerun1: ");

//                        //设备2状态设定
//                        Message message = new Message();
//                        bundle.putString("zt2", "0");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 200120;

                    if ((mNowTime - mPressedTime15G) > ZTLONGS5G) {
//                        Message message = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("zt2", "1");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 200120;
//                        EventBus.getDefault().post(new MessageEvent(200120, "连接失败"));
                        EventBus.getDefault().post(new MessageEvent(5101, "连接失败"));
                    } else {
//                        Message message = new Message();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("zt2", "0");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 200120;
//                        EventBus.getDefault().post(new MessageEvent(200120, "连接中"));
                        EventBus.getDefault().post(new MessageEvent(5101, "连接中"));
                    }

                } else {//代表启动
//                        Log.d(TAG, "mPressedTimerun2: ");
//                        System.exit(0);
                }
            }
        }, 0, 5000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                InetAddress ADDRESS2 = null;
                try {
                    DS2 = new DatagramSocket(null);
                    DS2.setReuseAddress(true);
                    DS2.bind(new InetSocketAddress(32002));
                    JSONObject createJSONObject = null;
                    try {
                        createJSONObject = createJSONObject();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println("测试json" + createJSONObject);
                    System.out.println("测试jsonTostring" + createJSONObject.toString());
                    String s = createJSONObject.toString();
                    byte[] buffer = s.getBytes();

                    try {
                        ADDRESS2 = InetAddress.getByName(IP3);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ADDRESS2, 32001);
                    byte[] buf = new byte[1024];
                    dp = new DatagramPacket(buf, buf.length);
                    try {
//                        DS.sendMessage(packet);
                        while (true) {
//                        bundle.putString("msgWifi", 222 + "");
//                        message = new Message();
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100;
                            //通过udp的socket服务将数据包接收到，通过receive方法
//                        ds.setSoTimeout((int) timerDate);//延时操作
//                        if (wifiFlag==false){
//                            return;
//                        }

                            try {
                                DS2.receive(dp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] buf1 = dp.getData();
                            System.out.println("收到测试IP63byte" + dp.getAddress().getHostAddress() + "发送数据：" + dp.getData()

                            );
                            String str = ReceiveTask.toHexString1(buf1);
                            System.out.println("收到测试IP63数据16jinzhi" + ReceiveTask.toHexString1(buf1));
//                            str = str.substring(0, 300);
                            Log.d("TAG0-300", "run: " + str);
                            System.out.println("收到测试IP63数据" + hexStringToString(str));
                            Log.d("TAG0-300", "run: " + str);
                            String s1 = hexStringToString(str);
                            Log.d("TAGs1", "run: " + s1);
                            ;
//                            str.replace("'�'", "");
                            JSONObject jsonObject = new JSONObject(s1);
                            System.out.println("收到测试IP63Json数据jsonObject" + jsonObject.toString());
                            session_id = jsonObject.getJSONObject("header").get("session_id").toString();
                            if (session_id.equals("51111")) {  //5G设置黑名单成功
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51111, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51111, "重复"));
                                }
                            }
                            if (session_id.equals("51112")) {  //5G设置定位模式   scout用于侦码，position用于定位。
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51112, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51112, "失败"));
                                }
                            }
                            if (session_id.equals("52113")) {  //5G停止小区
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(52113, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(52113, "失败"));
                                }
                            }

                            if (session_id.equals("51113")) {  //5G设置定位模式   scout用于侦码，position用于定位。
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51113, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51113, "失败"));
                                }
                            }
                            if (session_id.equals("51115")) {
                                System.out.println("51115收到测试IP63Json数据jsonObject" + jsonObject.toString());
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51115, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51115, "失败"));
                                }
                            }
                            if (session_id.equals("51116")) {
                                System.out.println("51116收到测试IP63Json数据jsonObject" + jsonObject.toString());
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51116, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51116, "失败"));
                                }
                            }
                            if (session_id.equals("51117")) {
                                System.out.println("51117收到测试IP63Json数据jsonObject" + jsonObject.toString());
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51117, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51117, "失败"));
                                }
                            }
                            if (session_id.equals("51118")) {
                                System.out.println("51118收到测试IP63Json数据jsonObject" + jsonObject.toString());
//                                if (jsonObject.getJSONObject("body").getJSONArray("imsi_list").length() > 0) {
//                                    EventBus.getDefault().post(new MessageEvent(51118, "大于1"));
//                                    List<String> list = new ArrayList<>();
//                                    for (int i = 0; i < jsonObject.getJSONObject("body").getJSONArray("imsi_list").length(); i++) {
//
//                                        list.add(jsonObject.getJSONObject("body").getJSONObject("imsi_list").getJSONArray("imsi").toString());
//                                    }
//                                    EventBus.getDefault().post(new MessageEvent(51118, list));
//
//                                } else {
//                                    List<String> list = new ArrayList<>();
//                                    EventBus.getDefault().post(new MessageEvent(51118, list));
//                                }
                                List<String> IMSI_LIST5118 = new ArrayList<>();
                                String s23 = jsonObject.getJSONObject("body").toString();
                                Log.d("5G黑名单列表", "run: " + s23);

                                Gson gson = new Gson();
                                ImsiListBean5G user = gson.fromJson(jsonObject.getJSONObject("body").toString(), ImsiListBean5G.class);

                                Log.d("5G黑名单列表2", "run: " + user.getImsiList());
                                IMSI_LIST5118.clear();
                                for (int i = 0; i < user.getImsiList().size(); i++) {

                                    if (!TextUtils.isEmpty(user.getImsiList().get(i).getImsi())) {
                                        IMSI_LIST5118.add(user.getImsiList().get(i).getImsi().toString());
                                    }
                                }
                                EventBus.getDefault().post(new MessageEvent(51118, IMSI_LIST5118));
//                                Log.d("5G黑名单3", "run: " + IMSI_LIST);
                            }
                            if (session_id.equals("51119")) {
                                System.out.println("51119收到测试IP63Json数据jsonObject" + jsonObject.toString());
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51119, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51119, "失败"));
                                }
                            }
                            if (session_id.equals("51120")) {
                                System.out.println("51120收到测试IP63Json数据jsonObject" + jsonObject.toString());
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("success")) {
                                    EventBus.getDefault().post(new MessageEvent(51120, "成功"));
                                } else {
                                    EventBus.getDefault().post(new MessageEvent(51120, "失败"));
                                }
                            }
                            if (session_id.equals("51114")) {  //查询小区参数
                                if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("QUERY_CELL_PARAM_RSP")) {//得到小区参数
                                    System.out.println("51114收到测试IP63Json数据jsonObject" + jsonObject.toString());
                                    ListBean listBean = new ListBean();
                                    String s2 = jsonObject.getJSONObject("body").toString();
                                    Log.d("QUERY_CELL_PARAM_RSP", "run: " + s2);
                                    BAND5G = jsonObject.getJSONObject("body").get("band").toString();
                                    NREF5G = jsonObject.getJSONObject("body").get("dlnarfcn").toString();
                                    PCI5G = jsonObject.getJSONObject("body").get("pci").toString();
                                    SUBFRAMSSIGN5G = jsonObject.getJSONObject("body").get("subfrmassign").toString();
                                    SPECIALSUBFRM5G = jsonObject.getJSONObject("body").get("specialsubfrm").toString();
                                    TAC5G = jsonObject.getJSONObject("body").get("tac").toString();
                                    POWER5G = jsonObject.getJSONObject("body").get("power").toString();
                                    BANDWIDTH5G = jsonObject.getJSONObject("body").get("bandwidth").toString();
                                    JSONArray jsonArray = jsonObject.getJSONObject("body").getJSONArray("plmn_list");
                                    Gson gson = new Gson();
                                    XiaoquBean5G user = gson.fromJson(s2, XiaoquBean5G.class);
                                    List<XiaoquBean5G.PlmnListDTO> plmnList = user.getPlmnList();
                                    PLMN_LIST.clear();
                                    for (int i = 0; i < plmnList.size(); i++) {
//                                    if (TextUtils.isEmpty(PLMN_LIST.add(plmnList.get(i).toString(){
////                                        PLMN_LIST.add(plmnList.get(i).toString()) ;
//                                    }else {
//                                        PLMN_LIST.add(plmnList.get(i).toString()) ;
//                                    }
                                        if (!TextUtils.isEmpty(plmnList.get(i).getPlmn())) {
                                            PLMN_LIST.add(plmnList.get(i).getPlmn().toString());
                                        }
                                    }
                                    Log.d("5GNREF5G", "run: " + PLMN_LIST);
//                                message = new Message();
//                                Bundle bundle = new Bundle();
//                                bundle.putString("body", s2);
//                                message.setData(bundle);
//                                message.what = 101;
//                                handler.sendMessage(message);
                                }
                            }
                            System.out.println("收到测试IP63Json数据jsonObject.getJSONObjectsession_id" + session_id);
                            System.out.println("收到测试IP63Json数据jmsg_id" + jsonObject.getJSONObject("header").get("msg_id").toString());
                            if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("HEART_BEAT")) {//如果收到HEART_BEAT  返回 HEART_BEAT_ACK
                                if (SETUP_LINK_RSP_FLAG == true) {
                                    Bean bean = new Bean();
                                    Bean.BodyDTO dto = new Bean.BodyDTO();
//                                dto.setResult("success");
                                    Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();

                                    headerDTO.setSession_id(session_id);
                                    headerDTO.setMsg_id("HEART_BEAT_ACK");
                                    headerDTO.setCell_id(1);
                                    bean.setHeader(headerDTO);
                                    bean.setBody(dto);
                                    Log.d("TAGbean", "run: " + bean.toString());

                                    Gson gson = new Gson();
                                    String s2 = gson.toJson(bean);
                                    Log.d("TAGs2HEART_BEAT", "run: " + s2);
                                    byte[] srtbyte = s2.getBytes();
                                    DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, ADDRESS2, 32001);
                                    DS2.send(outputPacket);
                                    EventBus.getDefault().post(new MessageEvent(5101, "就绪"));
                                    mPressedTime15G = System.currentTimeMillis();
                                }


                            }
                            if (jsonObject.getJSONObject("header").get("msd_id").toString().equals("SETUP_LINL_REQS")) {

                                Bean bean = new Bean();


                            }

                            if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("SETUP_LINK_REQ")) {//如果收到HEART_BEAT  返回 HEART_BEAT_ACK
                                SETUP_LINK_RSP_FLAG = true;
                                Bean bean = new Bean();
                                Bean.BodyDTO dto = new Bean.BodyDTO();
                                dto.setResult("success");
                                Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();
                                headerDTO.setSession_id(session_id);
                                headerDTO.setMsg_id("SETUP_LINK_RSP");
                                headerDTO.setCell_id(1);
                                bean.setHeader(headerDTO);
                                bean.setBody(dto);
                                Log.d("TAGbean", "run: " + bean.toString());

                                Gson gson = new Gson();
                                String s2 = gson.toJson(bean);
                                Log.d("TAGs2SETUP_LINK_REQ", "run: " + s2);
                                byte[] srtbyte = s2.getBytes();
                                DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, ADDRESS2, 32001);
                                DS2.send(outputPacket);
                            } else {
//                                if (SETUP_LINK_RSP_FLAG=false){
//                                    Bean bean = new Bean();
//                                    Bean.BodyDTO dto = new Bean.BodyDTO();
//                                    dto.setResult("failure");
//                                    Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();
//
//                                    headerDTO.setSession_id(session_id);
//                                    headerDTO.setMsg_id("SETUP_LINK_RSP");
//                                    headerDTO.setCell_id(1);
//                                    bean.setHeader(headerDTO);
//                                    bean.setBody(dto);
//                                    Log.d("TAGbean", "run: " + bean.toString());
//
//                                    Gson gson = new Gson();
//                                    String s2 = gson.toJson(bean);
//                                    Log.d("TAGs2SETUP_LINK_REQ", "run: " + s2);
//                                    byte[] srtbyte = s2.getBytes();
//                                    DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, ADDRESS2, 32001);
//                                    DS2.sendMessage(outputPacket);
//                                }
                            }

                            if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("RPT_UEINFO_LIST")) {//RPT_UEINFO_LIST  得到  上号  IMSI

                                String s2 = jsonObject.getJSONObject("body").toString();
//                                Log.d("blackList5Gs2", "run: " + s2);
//                                EventBus.getDefault().post(new MessageEvent(5100, s2));

                                Gson gson = new Gson();
                                BlackList5G blackList5G = gson.fromJson(jsonObject.getJSONObject("body").toString(), BlackList5G.class);
                                Log.d("blackList5G", "run: " + blackList5G);
                                Date now;
                                Map<String, String> map;
                                for (int i = 0; i < blackList5G.getUeList().size(); i++) {
                                    now = new Date();
                                    map = new HashMap<>();
                                    map.put("imsi", blackList5G.getUeList().get(i).getImsi().toString());
                                    map.put("sb", "5G");
                                    map.put("zb", "");
                                    map.put("datetime", ReceiveMainDateFormat1.format(now));
                                    map.put("time", ReceiveMainDateFormat2.format(now));
                                    EventBus.getDefault().post(new MessageEvent(30000, map));
                                    EventBus.getDefault().post(new MessageEvent(200147, map));
                                    try {

                                        DBManagerZM dbManagerZM = new DBManagerZM(getApplication());
                                        ZmBean zmBean = new ZmBean();
                                        zmBean.setImsi(blackList5G.getUeList().get(i).getImsi());
                                        zmBean.setZb("");
                                        zmBean.setSb("5G");
                                        zmBean.setDown(ACacheUtil.getSpinner2());
                                        zmBean.setDatatime(ReceiveMainDateFormat1.format(now));
                                        zmBean.setTime(ReceiveMainDateFormat2.format(now));
                                        dbManagerZM.insertAddZmBean(zmBean);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                }
                            }
                            if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("ADD_BLACKLIST")) {
                                Log.d("tagB", "run: 1");
                                if (jsonObject.getJSONObject("body").get("result").toString().equals("succuess")) {
                                    Log.d("tagB", "run: 2");
//                                Log.d("TAGbean", "run: "+"成功");
                                }
//                                Log.d("TAGbean", "run: "+"成功");
                            }


                            if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("QUERY_BLACKLIST_RSP")) {//QUERY_BLACKLIST_REQ  查询黑名单
                                String s23 = jsonObject.getJSONObject("body").toString();
                                Log.d("5G黑名单列表", "run: " + s23);

                                Gson gson = new Gson();
                                ImsiListBean5G user = gson.fromJson(jsonObject.getJSONObject("body").toString(), ImsiListBean5G.class);

                                Log.d("5G黑名单列表2", "run: " + user.getImsiList());
                                IMSI_LIST.clear();
                                for (int i = 0; i < user.getImsiList().size(); i++) {

                                    if (!TextUtils.isEmpty(user.getImsiList().get(i).getImsi())) {
                                        IMSI_LIST.add(user.getImsiList().get(i).getImsi().toString());
                                    }
                                }
                                Log.d("5G黑名单3", "run: " + IMSI_LIST);

                            }
                            //查询小区状态。  同步类型
                            if (jsonObject.getJSONObject("header").get("msg_id").toString().equals("QUERY_CELL_STATUS_RSP")) {//
                                try {
                                    SYNC_STATUS = jsonObject.getJSONObject("body").getJSONObject("sync_status").getString("sync_status");
//                                    CELL_STATUS = jsonObject.getJSONObject("body").getJSONObject("cell_status").getString("cell_status");
                                } catch (JSONException e) {

                                }
                                try {
//                                    SYNC_STATUS = jsonObject.getJSONObject("body").getJSONObject("sync_status").getString("sync_status");
                                    CELL_STATUS = jsonObject.getJSONObject("body").getJSONObject("cell_status").getString("cell_status");
                                } catch (JSONException e) {

                                }
                                Log.d("5G查询小区状态", "run: " + SYNC_STATUS + "--" + CELL_STATUS);
                            }


//                            Log.d("TAG", "run: ");   stringToAscii(str);
//                            str="{\n" +
//                                    "    \t\"header\":\t{\n" +
//                                    "    \t\t\"cell_id\":\t1,\n" +
//                                    "    \t\t\"session_id\":\t\"0000001112\",\n" +
//                                    "    \t\t\"msg_id\":\t\"SETUP_LINK_REQ\"\n" +
//                                    "    \t},\n" +
//                                    "    \t\"body\":\t{\n" +
//                                    "    \t\t\"cell_list\":\t[{\n" +
//                                    "    \t\t\t\t\"cell_id\":\t1\n" +
//                                    "    \t\t\t}]\n" +
//                                    "    \t}\n" +
//                                    "    }";

//                            Bean jsonRootBean = new Gson().fromJson(s1, Bean.class);
//                            Log.d("TAGjsonRootBean", "run: " + jsonRootBean.getHeader().getSession_id());
//                            session_id = jsonRootBean.getHeader().getSession_id();

//                            if (jsonRootBean.getBody().getResult().equals("success")){
//                                Log.d("TAGbean", "run: "+"成功");
//                            }

//                            Bean bean = new Bean();
//                            Bean.BodyDTO dto = new Bean.BodyDTO();
//                            dto.setResult("success");
//                            Bean.HeaderDTO headerDTO = new Bean.HeaderDTO();
//
//                            headerDTO.setSession_id(session_id);
//                            headerDTO.setMsg_id("HEART_BEAT_ACK");
//                            headerDTO.setCell_id(1);
//                            bean.setHeader(headerDTO);
//                            bean.setBody(dto);
//                            Log.d("TAGbean", "run: " + bean.toString());
//
//                            Gson gson = new Gson();
//                            String s2 = gson.toJson(bean);
//                            Log.d("TAGs2", "run: " + s2);
//                            byte[] srtbyte = s2.getBytes();
//
//                            DatagramPacket outputPacket = new DatagramPacket(srtbyte, srtbyte.length, address, 32001);
//                            DS.sendMessage(outputPacket);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new Thread() {
            @Override
            public void run() {
                super.run();
                buf = new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
//                DatagramSocket DS = null;
//                try {
//                    DS = new DatagramSocket(null);
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {


                    if (DS == null) {
                        DS = new DatagramSocket(3345);
                        DS.setReuseAddress(true);
                        DS.bind(new InetSocketAddress(3345));
                    }

//                    //设备1状态设定
//                    message = new Message();
//                    bundle.putString("zt1", "0");
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                    message.what = 100120;
//                    //设备2状态设定
//                    message = new Message();
//                    bundle.putString("zt2", "0");
//                    message.setData(bundle);
//                    handler.sendMessage(message);
//                    message.what = 200120;
//                    byte[] buf = new byte[1024];
//                     dp = new DatagramPacket(buf, buf.length);
//                    mPressedTime1 = System.currentTimeMillis();
//                    mPressedTime2 = System.currentTimeMillis();

                } catch (Exception e) {

                }
                while (true) {
//                        bundle.putString("msgWifi", 222 + "");
//                        message = new Message();
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100;
                    //通过udp的socket服务将数据包接收到，通过receive方法
//                        ds.setSoTimeout((int) timerDate);//延时操作
//                        if (wifiFlag==false){
//                            return;
//                        }
//                    try {
//                        if (DS == null) {
//                            DS = new DatagramSocket(null);
//                            DS.setReuseAddress(true);
//                            DS.bind(new InetSocketAddress(3345));
//                            DS.receive(dp);
//                        } else {
//                            DS.receive(dp);
//                        }
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    try {
                        if (DS == null) {
                            DS = new DatagramSocket(null);
                            DS.setReuseAddress(true);
                            DS.bind(new InetSocketAddress(3345));
                            DS.receive(dp);
                        } else {
                            DS.receive(dp);
                        }
//                        DS.receive(dp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    byte[] buf1 = dp.getData();
//                    System.out.println("收到所有数据" + dp.getAddress().getHostAddress() + "发送数据：" + buf1);
                    System.out.println("收到所有数据" + buf1);
                    String str = ReceiveTask.toHexString1(buf1);
                    EventBus.getDefault().post(new MessageEvent(1001, "数据=" + "i"));
                    SPUtils.putParam(getApplicationContext(), "1", str);
                    Object param = SPUtils.getParam(getApplicationContext(), "1", "0");//测试工具类
                    Log.d(TAG, "Testrun: " + param.toString());
                    if (IP1.equals(dp.getAddress().getHostAddress())) {

                    }
                    if (IP1.equals(dp.getAddress().getHostAddress())) {
                        mPressedTime1 = System.currentTimeMillis();

                        //UE测量配置查询响应信息
                        if ("3ef0".equals(str.substring(8, 12))) {
                            //工作模式
                            if ("00".equals(str.substring(24, 26))) {
                                System.out.println("持续侦码模式");
                                GZMS1 = "持续侦码模式";
                            } else if ("01".equals(str.substring(24, 26))) {
                                System.out.println("周期侦码模式");
                                //周期模式参数，指示针对同一个IMSI 的抓号周期
                                System.out.println(Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                ZHZQ1 = "" + Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                GZMS1 = "周期侦码模式";
                            } else if ("02".equals(str.substring(24, 26))) {
                                System.out.println("定位模式");
                                GZMS1 = "定位模式";
                                //定位模式，定位的终端 IMSI
                                UEIMSI = StringTOIMEI(str.substring(32, 62));
                                System.out.println("imsi:" + StringTOIMEI(str.substring(32, 62)));
                                //定位模式，终端测量值的上报周期,建议设置为 1024ms, 0：120ms,1：240ms,2：480ms,3：640ms,4：1024ms,5：2048ms

                                if ("00".equals(str.substring(66, 68))) {
                                    System.out.println("120ms");
                                    SBZQ1 = "120ms";
                                } else if ("01".equals(str.substring(66, 68))) {
                                    System.out.println("240ms");
                                    SBZQ1 = "240ms";
                                } else if ("02".equals(str.substring(66, 68))) {
                                    System.out.println("480ms");
                                    SBZQ1 = "480ms";
                                } else if ("03".equals(str.substring(66, 68))) {
                                    System.out.println("640ms");
                                    SBZQ1 = "640ms";
                                } else if ("04".equals(str.substring(66, 68))) {
                                    System.out.println("1024ms");
                                    SBZQ1 = "1024ms";
                                } else if ("05".equals(str.substring(66, 68))) {
                                    System.out.println("2048ms");
                                    SBZQ1 = "2048ms";
                                }
                                //定位模式，调度定位终端最大功率发射开关，需要设置为 0,0：enable,1：disable
                                if ("00".equals(str.substring(68, 70))) {
                                    System.out.println("enable");
                                    UEMAXOF1 = "开";
                                } else if ("01".equals(str.substring(68, 70))) {
                                    System.out.println("disable");
                                    UEMAXOF1 = "关";
                                }
                                //定位模式，UE 最大发射功率，最大值 不 超 过wrFLLmtToEnbSysArfcnCfg 配置的UePMax，建议设置为 23dBm
                                UEMAX1 = Integer.parseInt(str.substring(70, 72), 16) + "";
                                System.out.println(Integer.parseInt(str.substring(70, 72), 16));

                                //定位模式，由于目前都采用 SRS 方案配合单兵，因此此值需要设置为disable，否则大用户量时有定位终端掉线概率。0: disable,1: enable
                                if ("00".equals(str.substring(72, 74))) {
                                    System.out.println("disable");
                                } else if ("01".equals(str.substring(72, 74))) {
                                    System.out.println("enable");
                                }
                                //定位模式，是否把非定位终端踢回公网的配置，建议设置为 0。设置为 1 的方式是为了发布版本时保留用户反复接入基站测试使用。1：非定位终端继续被本小区吸附, 0：把非定位终端踢回公网
                                if ("00".equals(str.substring(76, 78))) {
                                    System.out.println("把非定位终端踢回公网");
                                } else if ("01".equals(str.substring(76, 78))) {
                                    System.out.println("非定位终端继续被本小区吸附");
                                }
                                //定位模式，是否对定位终端建立SRS 配置。
                                if ("00".equals(str.substring(78, 80))) {
                                    System.out.println("disable");
                                } else if ("01".equals(str.substring(78, 80))) {
                                    System.out.println("enable");
                                }
                            } else if ("03".equals(str.substring(24, 26))) {
                                System.out.println("管控模式");
                                GZMS1 = "管控模式";
                                //管控模式的子模式0：黑名单子模式；1：白名单子模式
                                if ("00".equals(str.substring(80, 82))) {
                                    System.out.println("黑名单子模式");
                                    GZMS1 = "黑名单子模式";
                                } else if ("01".equals(str.substring(80, 82))) {
                                    System.out.println("白名单子模式");
                                    GZMS1 = "白名单子模式";
                                }
                            } else if ("04".equals(str.substring(24, 26))) {
                                System.out.println("重定向模式");
                                GZMS1 = "重定向模式";
                            /*0: 名单中的用户执行重定向；名单外的全部踢回公网
                            1: 名单中的用户踢回公网；名单外的全部重定向
							2: 名单中的用户执行重定向；名单外的全部吸附在本站
							3: 名单中的用户吸附在本站;名单外的全部重定向
							4: 所有目标重定向*/
                                System.out.println(Integer.parseInt(str.substring(26, 28), 16));

                            }

                        }
                        //接受增益和发射功率查询的响应
                        if ("32f0".equals(str.substring(8, 12))) {
                            //增益
                            //寄存器中的值，实际生效的值（FDD 模式下仅在建立完小区查询，该值有效）
                            System.out.println("我增益1" + Integer.parseInt(str.substring(24, 26), 16));
                            ZENGYI1 = Integer.parseInt(str.substring(24, 26), 16) + "";
                            //发射功率  衰减
                            //寄存器中的值，实际生效的值（FDD 模式下仅在建立完小区查询，该值有效）
                            System.out.println(Integer.parseInt(str.substring(28, 30), 16));
                            SHUAIJIAN1 = Integer.parseInt(str.substring(28, 30), 16) + "";
                            System.out.println("我衰减1" + Integer.parseInt(str.substring(24, 26), 16));

//                            //数据库中的保存值，重启保留生效的值,
//                            System.out.println(Integer.parseInt(str.substring(26,28),16));
//
//                            //数据库中的保存值，重启保留生效的值
//                            System.out.println(Integer.parseInt(str.substring(30,32),16));
//                            //FDD AGC 开关
//                            if("00".equals(str.substring(30,32))){
//                                System.out.println("FDD AGC 开关关闭");
//                            }else if("01".equals(str.substring(30,32))){
//                                System.out.println("FDD AGC 开关打开");
//                            }

                            //增益
                            //只在FDD模式下有效，寄存器中的值，实际生效的值,该值只有在扫频完成后，建立小区前查询有效
//                            System.out.println(Integer.parseInt(str.substring(34,36),16));
//                            //eeprom 中的保存值，重启保留生效的值
//                            System.out.println(Integer.parseInt(str.substring(36,38),16));
                        }
                        if ("2cf0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设备型号：" + hexStringToString(str.substring(32, 46)));
                                DEVICENUMBER1 = hexStringToString(str.substring(32, 46));
                                SPUtils.putParam(getApplicationContext(), "DEVICENUMBER1", hexStringToString(str.substring(32, 46)));
                            } else if ("01000000".equals(str.substring(24, 32))) {
                                System.out.println("硬件版本：" + hexStringToString(str.substring(32, 34)));
                                HARDWAREVERSION1 = hexStringToString(str.substring(32, 34));
                                SPUtils.putParam(getApplicationContext(), "HARDWAREVERSION1", hexStringToString(str.substring(32, 34)));
                            } else if ("02000000".equals(str.substring(24, 32))) {
                                System.out.println("软件版本：" + hexStringToString(str.substring(32, 106)));
                                SOFTWAREVERSION1 = hexStringToString(str.substring(32, 106));
                                SPUtils.putParam(getApplicationContext(), "SOFTWAREVERSION1", hexStringToString(str.substring(32, 106)));
                            } else if ("03000000".equals(str.substring(24, 32))) {
                                System.out.println("序列号SN:" + hexStringToString(str.substring(32, 70)));
                                SNNUMBER1 = hexStringToString(str.substring(32, 70));
                                SPUtils.putParam(getApplicationContext(), "SNNUMBER1", hexStringToString(str.substring(32, 70)));

                            } else if ("04000000".equals(str.substring(24, 32))) {
                                System.out.println("MAC地址：" + hexStringToString(str.substring(32, 66)));
                                MACADDRESS1 = hexStringToString(str.substring(32, 66));
                                SPUtils.putParam(getApplicationContext(), "MACADDRESS1", hexStringToString(str.substring(32, 66)));
                            } else if ("05000000".equals(str.substring(24, 32))) {
                                UBOOTVERSION1 = hexStringToString(str.substring(32, 47));
                                System.out.println("uboot版本号：" + hexStringToString(str.substring(32, 47)));
                                SPUtils.putParam(getApplicationContext(), "UBOOTVERSION1", hexStringToString(str.substring(32, 47)));

                            } else if ("06000000".equals(str.substring(24, 32))) {
                                System.out.println("板卡温度：" + hexStringToString(str.substring(32, 50)));
                                BOARDTEMPERATURE1 = hexStringToString(str.substring(32, 50));
                                SPUtils.putParam(getApplicationContext(), "BOARDTEMPERATURE1", hexStringToString(str.substring(32, 50)));
                            }
                        }
                        if ("56f0".equals(str.substring(8, 12))) {

                            //黑名单数量
                            Integer blacklistNum = Integer.parseInt(StringPin(str.substring(24, 28)), 16);
                            int begin = 28;
                            int end = 58;
                            for (int i = 0; i < blacklistNum; i++) {
                                System.out.println("黑名单打印" + StringTOIMEI(str.substring(begin, end)));
                                BLACKLISTSB1.add(StringTOIMEI(str.substring(begin, end)));
                                begin = begin + 34;
                                end = end + 34;
                            }

                        }
                        if ("28f0".equals(str.substring(8, 12))) {
                            //PLMN
                            System.out.println(StringTOIMEI(str.substring(40, 50)));
                            PLMN1 = StringTOIMEI(str.substring(40, 50));
                            SPUtils.putParam(getApplicationContext(), "PLMN1", hexStringToString(str.substring(40, 50)));

                            //上行频点
                            System.out.println("我上行上行频点" + Integer.parseInt(StringPin(str.substring(24, 32)), 16));
                            UP1 = Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "UP1", Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "");

                            //下行频点
                            System.out.println(Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                            DWON1 = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "DWON1", Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "");

                            //Band
                            System.out.println(Integer.parseInt(StringPin(str.substring(56, 64)), 16));
                            BAND1 = Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "BAND1", Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "");

                            //带宽
                            System.out.println(Integer.parseInt(str.substring(54, 56), 16));
                            DK1 = Integer.parseInt(str.substring(54, 56), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "DK1", Integer.parseInt(str.substring(54, 56), 16) + "");

                            //TAC
                            System.out.println(Integer.parseInt(StringPin(str.substring(68, 72)), 16));
                            TAC1 = Integer.parseInt(StringPin(str.substring(68, 72)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "TAC1", Integer.parseInt(StringPin(str.substring(68, 72)), 16) + "");
                            //PCI
                            System.out.println(Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                            PCI1 = Integer.parseInt(StringPin(str.substring(64, 68)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "PCI1", Integer.parseInt(StringPin(str.substring(64, 68)), 16) + "");

                            //Cellld
                            System.out.println(Integer.parseInt(StringPin(str.substring(72, 80)), 16));
                            CELLID1 = Integer.parseInt(StringPin(str.substring(72, 80)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "CELLID1", Integer.parseInt(StringPin(str.substring(72, 80)), 16));

                            //UePMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(80, 84)), 16));
                            DBM1 = Integer.parseInt(StringPin(str.substring(80, 84)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "DBM1", Integer.parseInt(StringPin(str.substring(80, 84)), 16) + "");

                            //EnodeBPMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(84, 88)), 16));
                            TYPE1 = Integer.parseInt(StringPin(str.substring(84, 88)), 16) + "";

                        }
                        //基站执行状态/
                        if ("19f0".equals(str.substring(8, 12))) {
                            String state = str.substring(24, 32);
                            System.out.println("state" + state);
                            Log.d("wtto", "staterun: " + state);
                            if ("00000000".equals(state)) {
                                System.err.println("空口同步成功");
                                Log.d("wtto", "qqqrun:空口同步成功");
//                                message = new Message();
//                                bundle.putString("test", "1");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
//                                                        EventBus.getDefault().post(new MessageEvent(1001,"数据=" + i));
                                EventBus.getDefault().post(new MessageEvent(100120, "空口同步成功"));
                            } else if ("01000000".equals(state)) {
                                System.err.println("空口同步失败");
                                Log.d("wtto", "qqqrun:空口同步失败");
//                                message = new Message();
//                                bundle.putString("test", "2");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "空口同步失败"));
                            } else if ("02000000".equals(state)) {
                                System.err.println("GPS同步成功");
                                Log.d("wtto", "qqqrun:GPS同步成功");
//                                message = new Message();
//                                bundle.putString("test", "3");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "GPS同步成功"));
                            } else if ("03000000".equals(state)) {
                                System.err.println("GPS同步失败");
                                Log.d("wtto", "qqqrun:GPS同步失败");
//                                message = new Message();
//                                bundle.putString("test", "4");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "GPS同步失败"));
                            } else if ("04000000".equals(state)) {
                                System.err.println("扫频成功");
                                Log.d("wtto", "qqqrun:扫频成功");
//                                message = new Message();
//                                bundle.putString("test", "5");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100121, "扫频成功"));
                            } else if ("05000000".equals(state)) {
                                System.err.println("扫频失败");
                                Log.d("wtto", "qqqrun:扫频失败");
//                                message = new Message();
//                                bundle.putString("test", "6");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100121, "扫频失败"));
                            } else if ("06000000".equals(state)) {
                                System.err.println("小区激活成功");
                                Log.d("wtto", "qqqrun:小区激活成功");
//                                message = new Message();
//                                bundle.putString("test", "7");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "小区激活成功"));
                            } else if ("07000000".equals(state)) {
                                System.err.println("小区激活失败");
                                Log.d("wtto", "qqqrun:小区激活失败");
//                                message = new Message();
//                                bundle.putString("test", "8");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "小区激活失败"));
                            } else if ("08000000".equals(state)) {
                                System.err.println("小区去激活");
                                Log.d("wtto", "qqqrun:小区去激活");
//                                message = new Message();
//                                bundle.putString("test", "9");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "小区去激活"));
                            } else if ("09000000".equals(state)) {
                                System.err.println("空口同步中");
                                Log.d("wtto", "qqqrun:空口同步中");
//                                message = new Message();
//                                bundle.putString("test", "10");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "空口同步中"));
                            } else if ("0a000000".equals(state)) {
                                System.err.println("GPS同步中");
                                Log.d("wtto", "qqqrun:GPS同步中");
//                                message = new Message();
//                                bundle.putString("test", "11");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "GPS同步中"));
                            } else if ("0b000000".equals(state)) {
                                System.err.println("扫频中");
                                Log.d("wtto", "qqqrun:扫频中");
//                                message = new Message();
//                                bundle.putString("test", "12");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                            } else if ("0c000000".equals(state)) {
                                System.err.println("小区激活中");
                                Log.d("wtto", "qqqrun:小区激活中");
//                                message = new Message();
//                                bundle.putString("test", "13");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "小区激活中"));
                            } else if ("0d000000".equals(state)) {
                                System.err.println("小区去激活中");
                                Log.d("wtto", "qqqrun:小区去激活中");
//                                message = new Message();
//                                bundle.putString("test", "14");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(100120, "小区去激活中"));
                            }

                        }
                        if ("10f0".equals(str.substring(8, 12))) {
                            //心跳解析
                            //查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
//                        message = new Message();
//                        bundle.putString("zt1", "2");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100120;
                            if ("0000".equals(str.substring(24, 28))) {
                                System.out.println("0：小区 IDLE态");
//                            message = new Message();
//                            bundle.putString("zt1", "2");//IDLE态
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(100120, "就绪"));
                            } else if ("0100".equals(str.substring(24, 28))) {
                                System.out.println("1：扫频/同步进行中");
//                            message = new Message();
//                            bundle.putString("zt1", "3");//扫频同步进行中
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(100120, "扫频同步进行中"));

                            } else if ("0200".equals(str.substring(24, 28))) {
                                System.out.println("2：小区激活中");
//                            message = new Message();
//                            bundle.putString("zt1", "4");//小区激活中
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(100120, "小区激活中"));
                            } else if ("0300".equals(str.substring(24, 28))) {
                                System.out.println("3：定位中");
//                            message = new Message();
//                            bundle.putString("zt1", "5");//小区激活态
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(100120, "定位中"));
                                //Band号
                                Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                System.out.println("Band号：" + Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                //上行频点
                                Integer.parseInt(StringPin(str.substring(32, 40)), 16);
                                System.out.println("上行频点：" + Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                                //下行频点
                                Integer.parseInt(StringPin(str.substring(40, 48)), 16);
                                System.out.println("下行频点：" + Integer.parseInt(StringPin(str.substring(40, 48)), 16));
                                DOWNPIN1 = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
                                EventBus.getDefault().post(new MessageEvent(100151, Integer.parseInt(StringPin(str.substring(40, 48)), 16) + ""));
//                            message = new Message();
//                            bundle.putString("down", Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");//查询增益
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100151;
                                System.out.println("100151" + Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");
                                //移动联通电信
                                if ("3436303030".equals(str.substring(48, 58))) {
                                    //设置中国移动
                                }
                                if ("3436303031".equals(str.substring(48, 58))) {
                                    //设置中国联通
                                }
                                if ("3436303033".equals(str.substring(48, 58)) || "3436303131".equals(str.substring(48, 58))) {
                                    //设置中国电信
                                }

                                //PCI
                                Integer.parseInt(StringPin(str.substring(64, 68)), 16);
                                System.out.println("PCI:" + Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                                //TAC
                                Integer.parseInt(StringPin(str.substring(68, 72)), 16);
                                System.out.println("TAC:" + Integer.parseInt(StringPin(str.substring(68, 72)), 16));

                            } else if ("0400".equals(str.substring(24, 28))) {
                                System.out.println("4：小区去激活中");
                            }
                            //模式：FDD TDD
                            if ("00ff".equals(str.substring(16, 20))) {
                                //设置模式FDD
                                ID1TF = "FDD";
//                            System.err.println("FDD");
//                            message = new Message();
//                            bundle.putString("tf1", "FDD");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100110;
                                EventBus.getDefault().post(new MessageEvent(100110, "FDD"));

                            }
                            if ("ff00".equals(str.substring(16, 20))) {
                                //设置模式TDD
                                ID1TF = "TDD";
//                            System.out.println("TDD");
//                            message = new Message();
//                            bundle.putString("tf1", "TDD");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100110;
                                EventBus.getDefault().post(new MessageEvent(100110, "TDD"));
                            }

                        }

//                        //设置黑名单响应 清空响应
//                        if ("54f0".equals(str.substring(8, 12))) {
//                            if ("00000000".equals(str.substring(24, 32))) {
//                                opionts1++;
//                                System.out.println("设置基站黑名单成功" + opionts1);
//                                if (opionts1 % 2 == 0) {
//                                    Log.d("jsgs", "run:偶数");
//                                    message = new Message();
//                                    bundle.putString("100130", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100131;
//                                } else {
//                                    Log.d("jsgs", "run:基数");
//                                    message = new Message();
//                                    bundle.putString("100131", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100130;
//                                }
//                            } else {
//                                System.out.println("设置基站黑名单失败");
//
//                                if (opionts2 % 2 == 0) {
//                                    Log.d("jsgs", "run:偶数");
//                                    message = new Message();
//                                    bundle.putString("100130", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100135;
//                                } else {
//                                    Log.d("jsgs", "run:基数");
//                                    message = new Message();
//                                    bundle.putString("100131", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100134;
//                                }
//
//
//                            }
//                        }

                        //设置定位模式的应答信息
                        if ("07f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设置基站ue定位成功");

//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100136;
//                                Log.d("str10010001", "run: " + str.toString());
                                EventBus.getDefault().post(new MessageEvent(100136, "0"));
                            } else {
                                System.out.println("设置基站ue定位失败");
                                Log.d("str10010002", "run: " + str.toString());
//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100137;
                                EventBus.getDefault().post(new MessageEvent(100137, "0"));
                            }
                        }
                        //重启是否成功
                        if ("0cf0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("重启设置成功！");
//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100138;
                                EventBus.getDefault().post(new MessageEvent(100138, "0"));
                            } else {
                                System.err.println("重启失败！");
//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100139;
                                EventBus.getDefault().post(new MessageEvent(100139, "0"));
                            }
                        }
                        //制式切换是否成功
                        if ("02f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("1制式切换成功！");
//                            message = new Message();
//                            bundle.putString("test", "");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100140;
                                EventBus.getDefault().post(new MessageEvent(100140, "1"));

                            } else {
                                System.err.println("制式切换失败");
//                            message = new Message();
//                            bundle.putString("test", "");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100141;
                                EventBus.getDefault().post(new MessageEvent(100141, "0"));

                            }
                        }
                        //建立小区是发送否成功1
                        if ("04f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            Log.d("wtto", "04f0run:0 ");
                            if (row == 0) {
//                            message = new Message();
//                            bundle.putString("test", "0");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100145;
                                System.out.println("设置成功！开始建立小区！");
                                EventBus.getDefault().post(new MessageEvent(100145, "小区设置成功"));
                                Log.d("wtto", "04f0run:1 ");
                            } else {
                                System.err.println("设置失败！");
//                            message = new Message();
//                            bundle.putString("test", "0");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100146;
                                EventBus.getDefault().post(new MessageEvent(100146, "小区设置失败"));
                                Log.d("wtto", "04f0run:2 ");
                            }
                        }
                        //定位IMSI   能量值,
                        if ("08f0".equals(str.substring(8, 12))) {

                            //目标距离（16进制字符串转换成十进制）
                            Integer.parseInt(str.substring(24, 26), 16);
                            System.out.println("距离：" + Integer.parseInt(str.substring(24, 26), 16));
//                        message = new Message();
//                        bundle.putString("sb1jl", Integer.parseInt(str.substring(24, 26), 16) + "");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100147;
                            EventBus.getDefault().post(new MessageEvent(100147, Integer.parseInt(str.substring(24, 26), 16) + ""));

                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(26, 56));
//                        message = new Message();
//                        bundle.putString("imsi", imsi);
//                        bundle.putString("sb", "1");
//                        bundle.putString("zb", "1");
//                        bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                        bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 30000;
                            Map<String, String> map = new HashMap<>();
                            map.put("imsi", imsi);
                            map.put("sb", "4G");
                            map.put("zb", "1");
                            map.put("datetime", ReceiveMainDateFormat1.format(now));
                            map.put("time", ReceiveMainDateFormat2.format(now));
                            EventBus.getDefault().post(new MessageEvent(30000, map));
                            //IMSI号
                            StringTOIMEI(str.substring(26, 56));
                            System.out.println("IMSI号：" + hexStringToString(str.substring(26, 56)));


//                        message = new Message();
//                        bundle.putString("imsi1", imsi);
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100148;
                            Log.d(TAG, "loggnzqrun: " + hexStringToString(str.substring(26, 56)));
                            EventBus.getDefault().post(new MessageEvent(100148, imsi));
                        }
                        //黑名单中标   侦码IMSI
                        if ("05f0".equals(str.substring(8, 12))) {
                            String down = "";
                            System.out.println("1黑名单中标IMSI号：" + hexStringToString(str.substring(32, 62)));
                            //当前小区信息状态响应
                            if ("28f0".equals(str.substring(8, 12))) {
                                down = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            }
//                        down = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(32, 62));
                            Log.d("jsgs", "run:基数");
//                        message = new Message();
//                        bundle.putString("imsi", imsi);
//                        bundle.putString("sb", "1");
//                        bundle.putString("zb", "");
//                        bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                        bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
////                            bundle.putString("zmdown", "" + down);
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 30000;
                            Map<String, String> map = new HashMap<>();
                            map.put("imsi", imsi);
                            map.put("sb", "4G");
                            map.put("zb", "");
                            map.put("datetime", ReceiveMainDateFormat1.format(now));
                            map.put("time", ReceiveMainDateFormat2.format(now));
                            EventBus.getDefault().post(new MessageEvent(30000, map));
                            Log.d("Aaims05f0i", "imsirun: " + imsi);
                            System.out.println("Imsi黑名单中标" + imsi + "down" + down);
                            if (!TextUtils.isEmpty(DOWNPIN1)) {
                                try {
                                    DBManagerZM dbManagerZM = new DBManagerZM(getApplication());
                                    ZmBean zmBean = new ZmBean();
                                    zmBean.setImsi(imsi);
                                    zmBean.setZb("");
                                    zmBean.setSb("4G");
                                    zmBean.setDown(DOWNPIN1);
                                    zmBean.setDatatime(ReceiveMainDateFormat1.format(now));
                                    zmBean.setTime(ReceiveMainDateFormat2.format(now));
                                    dbManagerZM.insertAddZmBean(zmBean);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
//                            bundle.putString("imsi", imsi);
//                            bundle.putString("sb", "1");
//                            bundle.putString("zb", "");
//                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));

//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 3000000;

                            }

                        }
                        if ("14f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
//                                System.out.println("增益值设置成功!");
//                                message = new Message();
//                                bundle.putString("zyset1", "增益值设置成功");//小区激活中
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100150;
//                                System.out.println("100150");
                                EventBus.getDefault().post(new MessageEvent(100150, "增益值设置成功"));


                            } else {
                                System.err.println("增益值设置失败!");
//                                message = new Message();
//                                bundle.putString("zyset1", "增益值设置失败");//小区激活中
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100150;
//                                System.out.println("100150");
                                EventBus.getDefault().post(new MessageEvent(100150, "增益值设置失败"));
                            }
                        }

                        //判断f019是否 包含 0af
                        boolean Flag19F0 = false;
                        if ("19f0".equals(str.substring(8, 12))) {
                            if ("0af0".equals(str.substring(40, 44))) {
                                Flag19F0 = true;
                                String sa = str;
                                str = sa.substring(32);
                                List<SpBean> spBeanList = new ArrayList<>();
                                //消息头长度
                                String length = str.substring(12, 16);
                                String len = StringPin(length);
                                Integer strlen = Integer.parseInt(len, 16);
                                //是否发送完
                                Integer.parseInt(StringPin(str.substring(20, 24)), 16);
                                String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));

                                code = StringAd(code);
                                System.out.println("南志强2F--" + str3);
                                System.err.println("code:" + code);
                                String s = str.substring(24, strlen * 2);
                                System.out.println("ss___" + s);
                                str3 = str3 + s;
                                System.err.println("s3___" + str3);
                                if ("0".equals(code.substring(0, 1))) {

                                    str3 = "aaaa55550af0240000ff0000" + str3;
                                    System.err.println("str3:+++++++++++++++" + str3);
                                    //采集的小区数目
                                    int row;
                                    if ("ffff".equals(str3.substring(24, 28))) {
                                        row = 0;
                                    } else {
                                        row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
                                        System.out.println("小区个数：" + row);
                                        int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                        System.out.println("小区个数下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                    }
                                    System.out.println("南志强F--" + str3);
                                    int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                    int pciBegin = 40, pciEnd = 44;
                                    int tacBegin = 44, tacEnd = 48;
                                    int plmnBegin = 48, plmnEnd = 52;
                                    int celldBegin = 56, celldEnd = 64;
                                    int priorityBegin = 64, priorityEnd = 72;
                                    int rsrpBegin = 72, rsrpEnd = 74;
                                    int rsrqBegin = 74, rsrqEnd = 76;
                                    int bandWidthBegin = 76, bandWidthEnd = 78;
                                    int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
                                    int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
                                    int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
                                    for (int i = 0; i < row; i++) {
                                        //下行频点
                                        SpBean spBean = new SpBean();
                                        try {
                                            System.out.println(str3.substring(dlEarfcnBegin, dlEarfcnEnd));
                                            if ("ffffffff".equals(str3.substring(dlEarfcnBegin, dlEarfcnEnd))) {
                                                System.out.println("null");
//                                            continue;
                                            } else {
                                                try {
                                                    System.out.println("下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                                    spBean.setDown(Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                                } catch (NumberFormatException e) {
                                                    spBean.setDown(null);
                                                }

                                            }
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setDown(null);
                                        }

                                        if (ID1TF.equals("TDD")) {
                                            spBean.setUp(255 + "");
                                        } else {
                                            if (!TextUtils.isEmpty(spBean.getDown())) {
                                                int i1 = Integer.parseInt(spBean.getDown()) + 18000;
                                                spBean.setUp(i1 + "");
                                            }
                                        }
                                        //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(0);
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);


                                        try {
                                            str3.substring(pciBegin, pciEnd);
                                            spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setPci(0);
                                        }
                                        //TAC
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                    spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));

                                        try {
                                            str3.substring(tacBegin, tacEnd);
                                            spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setTac(0);
                                        }


//                                    Log.d(TAG, "spBeanTAcrun: " + spBean.getTac());
//                                    spBean.setTac(0);
                                        //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
                                        try {
                                            if ("ffff".equals(str3.substring(plmnBegin, plmnEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPlmn(0 + "");
                                            } else {
                                                spBean.setPlmn(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (StringIndexOutOfBoundsException E) {
                                            spBean.setPlmn(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区PLMN error");
                                        }


//                                    spBean.setPlmn("0");
                                        //CellId

                                        try {
                                            System.out.println("ffffffff".equals(str3.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "------CellId：");
                                            if ("ffffffff".equals(str3.substring(celldBegin, celldEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setCid(0 + "");
                                            } else {
                                                spBean.setCid(Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setCid(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区CellId error");
                                        }

                                        //Priority 本小区频点优先级
                                        try {
                                            System.out.println(str3.substring(64, 72));
//                                        System.out.println("ffffffff".equals(str3.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
                                            if ("ffffffff".equals(str3.substring(priorityBegin, priorityEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPriority(0);
                                            } else {
                                                spBean.setPriority(Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setPriority(0);
                                            Log.d("nzqExceloing", "run:1 小区Priority error");
                                        }

                                        //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrpBegin, rsrpEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrp(0);
                                            } else {
                                                spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setRsrp(0);
                                            Log.d("nzqExceloing", "run:1 小区RSRPerror");
                                        }

//                                    spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));

                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrqBegin, rsrqEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrq(0);
                                            } else {
                                                spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区RSQPerror");
                                        }

                                        //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                    spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                        try {
                                            if ("ffffffff".equals(str3.substring(bandWidthBegin, bandWidthEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setBand("");
                                            } else {
                                                spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区带宽error");
                                        }

                                        try {
                                            if (spBean.getDown().equals("0")) {

                                            } else {
                                                spBeanList.add(spBean);//测试代码add
                                            }
                                        } catch (Exception e) {

                                        }

                                        //TddSpecialSf Patterns TDD特殊子帧配置
//                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str3.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
                                        //异频小区个数
                                        int InterFreqLstNum;
                                        try {
                                            if ("ffffffff".equals(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
                                                InterFreqLstNum = 0;
                                            } else {
                                                try {
                                                    InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                                } catch (Exception e) {
                                                    InterFreqLstNum = 0;
                                                    Log.d("nzqexce1", "run: " + e.getMessage());
                                                }
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                            }
                                        } catch (Exception e) {
                                            InterFreqLstNum = 0;
                                            Log.d("nzqexce3", "run: " + e.getMessage());
                                        }


                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
                                        System.out.println("异频小区个数：------" + InterFreqLstNum);

                                        dlEarfcnBegin = dlEarfcnBegin + 64;
                                        dlEarfcnEnd = dlEarfcnEnd + 64;
                                        pciBegin = pciBegin + 64;
                                        pciEnd = pciEnd + 64;
                                        tacBegin = tacBegin + 64;
                                        tacEnd = tacEnd + 64;
                                        plmnBegin = plmnBegin + 64;
                                        plmnEnd = plmnEnd + 64;
                                        celldBegin = celldBegin + 64;
                                        celldEnd = celldEnd + 64;
                                        priorityBegin = priorityBegin + 64;
                                        priorityEnd = priorityEnd + 64;
                                        rsrpBegin = rsrpBegin + 64;
                                        rsrpEnd = rsrpEnd + 64;
                                        rsrqBegin = rsrqBegin + 64;
                                        rsrqEnd = rsrqEnd + 64;
                                        bandWidthBegin = bandWidthBegin + 64;
                                        bandWidthEnd = bandWidthEnd + 64;
                                        tddSpecialSfBegin = tddSpecialSfBegin + 64;
                                        tddSpecialSfEnd = tddSpecialSfEnd + 64;
                                        //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;

                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                        for (int r = 0; r < InterFreqLstNum; r++) {
                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                            interFreqNghNumBegin = interFreqLstNumBegin + 24;
                                            interFreqNghNumEnd = interFreqLstNumEnd + 24;
                                            //异频小区的领区数目
//                                            System.out.println(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                            int interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                            System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                            int interFreqNghNum;
//                                        try {
//                                        if (str4.length() < interFreqNghNumEnd) {
//                                            continue;
//                                        }
                                            try {
                                                if (!TextUtils.isEmpty(str3)) {

                                                } else {
                                                    continue;
                                                }
                                                if ("ffffffff".equals(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {

                                                    interFreqNghNum = 0;
                                                } else {
                                                    interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                                }
                                            } catch (Exception e) {
                                                interFreqNghNum = 0;
//                                            Log.d("nzqexce2", "run: " + e.getMessage());
                                            }

//                                        } catch (Exception e) {
//                                            continue;
//                                        }

                                            for (int n = 0; n < interFreqNghNum; n++) {
                                                dlEarfcnBegin = dlEarfcnBegin + 8;
                                                dlEarfcnEnd = dlEarfcnEnd + 8;
                                                pciBegin = pciBegin + 8;
                                                pciEnd = pciEnd + 8;
                                                tacBegin = tacBegin + 8;
                                                tacEnd = tacEnd + 8;
                                                plmnBegin = plmnBegin + 8;
                                                plmnEnd = plmnEnd + 8;
                                                celldBegin = celldBegin + 8;
                                                celldEnd = celldEnd + 8;
                                                priorityBegin = priorityBegin + 8;
                                                priorityEnd = priorityEnd + 8;
                                                rsrpBegin = rsrpBegin + 8;
                                                rsrpEnd = rsrpEnd + 8;
                                                rsrqBegin = rsrqBegin + 8;
                                                rsrqEnd = rsrqEnd + 8;
                                                bandWidthBegin = bandWidthBegin + 8;
                                                bandWidthEnd = bandWidthEnd + 8;
                                                tddSpecialSfBegin = tddSpecialSfBegin + 8;
                                                tddSpecialSfEnd = tddSpecialSfEnd + 8;
                                                interFreqLstNumBegin = interFreqLstNumBegin + 8;
                                                interFreqLstNumEnd = interFreqLstNumEnd + 8;
                                                interFreqNghNumBegin = interFreqNghNumBegin + 8;
                                                interFreqNghNumEnd = interFreqNghNumEnd + 8;
                                            }

									/*int number = InterFreqLstNum-r;
                                    if(number!=1){
										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
									}*/
                                            dlEarfcnBegin = dlEarfcnBegin + 24;
                                            dlEarfcnEnd = dlEarfcnEnd + 24;
                                            pciBegin = pciBegin + 24;
                                            pciEnd = pciEnd + 24;
                                            tacBegin = tacBegin + 24;
                                            tacEnd = tacEnd + 24;
                                            plmnBegin = plmnBegin + 24;
                                            plmnEnd = plmnEnd + 24;
                                            celldBegin = celldBegin + 24;
                                            celldEnd = celldEnd + 24;
                                            priorityBegin = priorityBegin + 24;
                                            priorityEnd = priorityEnd + 24;
                                            rsrpBegin = rsrpBegin + 24;
                                            rsrpEnd = rsrpEnd + 24;
                                            rsrqBegin = rsrqBegin + 24;
                                            rsrqEnd = rsrqEnd + 24;
                                            bandWidthBegin = bandWidthBegin + 24;
                                            bandWidthEnd = bandWidthEnd + 24;
                                            tddSpecialSfBegin = tddSpecialSfBegin + 24;
                                            tddSpecialSfEnd = tddSpecialSfEnd + 24;
                                            interFreqLstNumBegin = interFreqLstNumBegin + 24;
                                            interFreqLstNumEnd = interFreqLstNumEnd + 24;

                                        }
                                        interFreqLstNumBegin = interFreqLstNumBegin + 64;
                                        interFreqLstNumEnd = interFreqLstNumEnd + 64;
                                        interFreqNghNumBegin = interFreqNghNumBegin + 64;
                                        interFreqNghNumEnd = interFreqNghNumEnd + 64;
                                    }
                                    str3 = "";
                                    Log.d("nzqrun77spBeanList", "nzqrun: " + "执行" + spBeanList);
                                    SPBEANLIST1Fragment = spBeanList;
                                    if (spBeanList.size() == 0) {
                                        Log.d(TAG, "nzqrunrun: " + "等于0");
                                    } else {
                                        Log.d(TAG, "nzqrunrun: " + "大于0");
                                        SPBEANLIST1 = spBeanList;
                                        Log.d("nzqspBeanList1", "" + spBeanList);
//                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
                                        //先根据优先级,如果优先级一样根据RSRP
                                        List<Integer> list = new ArrayList();
                                        String down1 = "";
                                        SpBean spBean1 = new SpBean();
                                        SpBean spBean2 = new SpBean();
                                        if (spBeanList.size() >= 2) {
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                list.add(spBeanList.get(i).getPriority());
                                            }
                                            Integer max = Collections.max(list);
                                            Log.d("Anzqmax", "大于2条run: " + max);
                                            list.remove(max);//最大的优先

                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max.equals(spBeanList.get(i).getPriority())) {
                                                    down1 = spBeanList.get(i).getDown();
                                                    spBean1 = spBeanList.get(i);
                                                }
                                            }
                                            //第二个优先
                                            String down2 = "";
                                            Integer max2 = Collections.max(list);
//                                    Log.d("Anzqmax2", "run: " + max);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max2.equals(spBeanList.get(i).getPriority())) {

                                                    down2 = spBeanList.get(i).getDown();
                                                    spBean2 = spBeanList.get(i);
                                                }
                                            }
                                            Log.d("down2a", "run: " + down2);
                                            if (max != max2) {

                                                if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "A大于2条run且优先级有区别但是频点一致: " + max);
                                                } else {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }
                                                    }


//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
                                                }


                                            } else {//根据优先级比较一致  ,通过rsrp比较

                                                int rssp1;
                                                int rssp2;
                                                List<Integer> list1rsp = new ArrayList<>();
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    list1rsp.add(spBeanList.get(i).getRsrp());
                                                }
                                                //最大的rsrp
                                                rssp1 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp1 == spBeanList.get(i).getRsrp()) {
                                                        down1 = spBeanList.get(i).getDown();
                                                        spBean1 = spBeanList.get(i);
                                                    }
                                                }
                                                for (int i = 0; i < list1rsp.size(); i++) {
                                                    if (list1rsp.get(i).equals(rssp1)) {
                                                        list1rsp.remove(i);
                                                    }
                                                }
                                                //求第二个rsrp
                                                rssp2 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp2 == spBeanList.get(i).getRsrp()) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                    }
                                                }
                                                if (down1.equals(down2)) {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }

                                                    }
//                                                    bundle.putString("sp1MAX2value", "");
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
                                                } else {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
                                                }


//                                            ToastUtils.showToast("当前条数为多条");
//                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
                                            }

                                        } else {
                                            //

                                            if (spBeanList.size() > 0 && spBeanList.size() == 1) {
                                                down1 = spBeanList.get(0).getDown();
//                                            spBean2 = spBeanList.get(0);
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);
//                                                bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                                bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                                bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                                bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                                bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", "");
//                                                bundle.putString("sp2up", "");
//                                                bundle.putString("sp2pci", "");
//                                                bundle.putString("sp2plmn", "");
//                                                bundle.putString("sp2band", "");
//                                                bundle.putString("sp2tac", "");
//
//
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
                                                EventBus.getDefault().post(new MessageEvent(100152, down1));
//                                            ToastUtils.showToast("当前条数为1");
                                                Log.d("Anzqmax", "当前条数为1: ");
                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", "");
//                                                bundle.putString("sp1MAX2value", "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
                                                EventBus.getDefault().post(new MessageEvent(100152, ""));
//                                            ToastUtils.showToast("当前条数为0");
                                                Log.d("Anzqmax", "当前条数为0: ");
                                            }
                                        }
                                    }


                                }

                            }


                        }

                        if ("0af0".equals(str.substring(8, 12))) {
                            if (!Flag19F0) {//判断是否 f019和 0af合并


                                List<SpBean> spBeanList = new ArrayList<>();
                                //消息头长度
                                String length = str.substring(12, 16);
                                String len = StringPin(length);
                                Integer strlen = Integer.parseInt(len, 16);
                                //是否发送完
                                Integer.parseInt(StringPin(str.substring(20, 24)), 16);
                                String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));

                                code = StringAd(code);
                                System.out.println("南志强2F--" + str3);
                                System.err.println("code:" + code);
                                String s = str.substring(24, strlen * 2);
                                System.out.println("ss___" + s);
                                str3 = str3 + s;
                                System.err.println("s3___" + str3);
                                if ("0".equals(code.substring(0, 1))) {

                                    str3 = "aaaa55550af0240000ff0000" + str3;
                                    System.err.println("str3:+++++++++++++++" + str3);
                                    //采集的小区数目
                                    int row;
                                    if ("ffff".equals(str3.substring(24, 28))) {
                                        row = 0;
                                    } else {
                                        if (Integer.parseInt(StringPin(str3.substring(24, 28)), 16) == 0) {
                                            row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
                                            System.out.println("小区个数：" + row);
                                        } else {
                                            row = Integer.parseInt(StringPin(str3.substring(24, 28)), 16);
                                            System.out.println("小区个数：" + row);
                                            int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                            System.out.println("小区个数下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                        }

                                    }
                                    System.out.println("南志强F--" + str3);
                                    int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                    int pciBegin = 40, pciEnd = 44;
                                    int tacBegin = 44, tacEnd = 48;
                                    int plmnBegin = 48, plmnEnd = 52;
                                    int celldBegin = 56, celldEnd = 64;
                                    int priorityBegin = 64, priorityEnd = 72;
                                    int rsrpBegin = 72, rsrpEnd = 74;
                                    int rsrqBegin = 74, rsrqEnd = 76;
                                    int bandWidthBegin = 76, bandWidthEnd = 78;
                                    int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
                                    int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
                                    int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
                                    for (int i = 0; i < row; i++) {
                                        //下行频点
                                        SpBean spBean = new SpBean();
                                        try {
                                            System.out.println(str3.substring(dlEarfcnBegin, dlEarfcnEnd));
                                            if ("ffffffff".equals(str3.substring(dlEarfcnBegin, dlEarfcnEnd))) {
                                                System.out.println("null");
//                                            continue;
                                            } else {
                                                try {
                                                    System.out.println("下行频点：------" + Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                                    spBean.setDown(Integer.parseInt(StringPin(str3.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                                } catch (NumberFormatException e) {
                                                    spBean.setDown(null);
                                                }

                                            }
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setDown(null);
                                        }

                                        if (ID1TF.equals("TDD")) {
                                            spBean.setUp(255 + "");
                                        } else {
                                            if (!TextUtils.isEmpty(spBean.getDown())) {
                                                int i1 = Integer.parseInt(spBean.getDown()) + 18000;
                                                spBean.setUp(i1 + "");
                                            }
                                        }
                                        //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
//                                    spBean.setPci(0);
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);


                                        try {
                                            str3.substring(pciBegin, pciEnd);
                                            spBean.setPci(Integer.parseInt(StringPin(str3.substring(pciBegin, pciEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setPci(0);
                                        }
                                        //TAC
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
//                                    spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));

                                        try {
                                            str3.substring(tacBegin, tacEnd);
                                            spBean.setTac(Integer.parseInt(StringPin(str3.substring(tacBegin, tacEnd)), 16));
                                        } catch (StringIndexOutOfBoundsException e) {
                                            spBean.setTac(0);
                                        }


//                                    Log.d(TAG, "spBeanTAcrun: " + spBean.getTac());
//                                    spBean.setTac(0);
                                        //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
                                        try {
                                            if ("ffff".equals(str3.substring(plmnBegin, plmnEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPlmn(0 + "");
                                            } else {
                                                spBean.setPlmn(Integer.parseInt(StringPin(str3.substring(plmnBegin, plmnEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (StringIndexOutOfBoundsException E) {
                                            spBean.setPlmn(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区PLMN error");
                                        }


//                                    spBean.setPlmn("0");
                                        //CellId

                                        try {
                                            System.out.println("ffffffff".equals(str3.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "------CellId：");
                                            if ("ffffffff".equals(str3.substring(celldBegin, celldEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setCid(0 + "");
                                            } else {
                                                spBean.setCid(Integer.parseInt(StringPin(str3.substring(celldBegin, celldEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setCid(0 + "");
                                            Log.d("nzqExceloing", "run:1 小区CellId error");
                                        }

                                        //Priority 本小区频点优先级
                                        try {
                                            System.out.println(str3.substring(64, 72));
//                                        System.out.println("ffffffff".equals(str3.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
                                            if ("ffffffff".equals(str3.substring(priorityBegin, priorityEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setPriority(0);
                                            } else {
                                                spBean.setPriority(Integer.parseInt(StringPin(str3.substring(priorityBegin, priorityEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setPriority(0);
                                            Log.d("nzqExceloing", "run:1 小区Priority error");
                                        }

                                        //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrpBegin, rsrpEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrp(0);
                                            } else {
                                                spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            spBean.setRsrp(0);
                                            Log.d("nzqExceloing", "run:1 小区RSRPerror");
                                        }

//                                    spBean.setRsrp(Integer.parseInt(StringPin(str3.substring(rsrpBegin, rsrpEnd)), 16));
                                        //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));

                                        try {
                                            if ("ffffffff".equals(str3.substring(rsrqBegin, rsrqEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setRsrq(0);
                                            } else {
                                                spBean.setRsrq(Integer.parseInt(StringPin(str3.substring(rsrqBegin, rsrqEnd)), 16));
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区RSQPerror");
                                        }

                                        //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16));
//                                    spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                        try {
                                            if ("ffffffff".equals(str3.substring(bandWidthBegin, bandWidthEnd))) {
                                                Log.d("1nzqffffffff", "run:1 ");
                                                spBean.setBand("");
                                            } else {
                                                spBean.setBand(Integer.parseInt(StringPin(str3.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                                Log.d("2nzqffffffff", "run:1 ");
                                            }
                                        } catch (Exception e) {
                                            Log.d("nzqExceloing", "run:1 小区带宽error");
                                        }

                                        try {
                                            if (spBean.getDown().equals("0")) {

                                            } else {
                                                spBeanList.add(spBean);//测试代码add
                                            }
                                        } catch (Exception e) {

                                        }

                                        //TddSpecialSf Patterns TDD特殊子帧配置
//                                    System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str3.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
                                        //异频小区个数
                                        int InterFreqLstNum;
                                        try {
                                            if ("ffffffff".equals(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
                                                InterFreqLstNum = 0;
                                            } else {
                                                try {
                                                    InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                                } catch (Exception e) {
                                                    InterFreqLstNum = 0;
                                                    Log.d("nzqexce1", "run: " + e.getMessage());
                                                }
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                            }
                                        } catch (Exception e) {
                                            InterFreqLstNum = 0;
                                            Log.d("nzqexce3", "run: " + e.getMessage());
                                        }


                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
                                        System.out.println("异频小区个数：------" + InterFreqLstNum);

                                        dlEarfcnBegin = dlEarfcnBegin + 64;
                                        dlEarfcnEnd = dlEarfcnEnd + 64;
                                        pciBegin = pciBegin + 64;
                                        pciEnd = pciEnd + 64;
                                        tacBegin = tacBegin + 64;
                                        tacEnd = tacEnd + 64;
                                        plmnBegin = plmnBegin + 64;
                                        plmnEnd = plmnEnd + 64;
                                        celldBegin = celldBegin + 64;
                                        celldEnd = celldEnd + 64;
                                        priorityBegin = priorityBegin + 64;
                                        priorityEnd = priorityEnd + 64;
                                        rsrpBegin = rsrpBegin + 64;
                                        rsrpEnd = rsrpEnd + 64;
                                        rsrqBegin = rsrqBegin + 64;
                                        rsrqEnd = rsrqEnd + 64;
                                        bandWidthBegin = bandWidthBegin + 64;
                                        bandWidthEnd = bandWidthEnd + 64;
                                        tddSpecialSfBegin = tddSpecialSfBegin + 64;
                                        tddSpecialSfEnd = tddSpecialSfEnd + 64;
                                        //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;

                                        System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                        for (int r = 0; r < InterFreqLstNum; r++) {
                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                            interFreqNghNumBegin = interFreqLstNumBegin + 24;
                                            interFreqNghNumEnd = interFreqLstNumEnd + 24;
                                            //异频小区的领区数目
//                                            System.out.println(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                            int interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                            System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                            int interFreqNghNum;
//                                        try {
//                                        if (str4.length() < interFreqNghNumEnd) {
//                                            continue;
//                                        }
                                            try {
                                                if (!TextUtils.isEmpty(str3)) {

                                                } else {
                                                    continue;
                                                }
                                                if ("ffffffff".equals(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {

                                                    interFreqNghNum = 0;
                                                } else {
                                                    interFreqNghNum = Integer.parseInt(StringPin(str3.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                                }
                                            } catch (Exception e) {
                                                interFreqNghNum = 0;
//                                            Log.d("nzqexce2", "run: " + e.getMessage());
                                            }

//                                        } catch (Exception e) {
//                                            continue;
//                                        }

                                            for (int n = 0; n < interFreqNghNum; n++) {
                                                dlEarfcnBegin = dlEarfcnBegin + 8;
                                                dlEarfcnEnd = dlEarfcnEnd + 8;
                                                pciBegin = pciBegin + 8;
                                                pciEnd = pciEnd + 8;
                                                tacBegin = tacBegin + 8;
                                                tacEnd = tacEnd + 8;
                                                plmnBegin = plmnBegin + 8;
                                                plmnEnd = plmnEnd + 8;
                                                celldBegin = celldBegin + 8;
                                                celldEnd = celldEnd + 8;
                                                priorityBegin = priorityBegin + 8;
                                                priorityEnd = priorityEnd + 8;
                                                rsrpBegin = rsrpBegin + 8;
                                                rsrpEnd = rsrpEnd + 8;
                                                rsrqBegin = rsrqBegin + 8;
                                                rsrqEnd = rsrqEnd + 8;
                                                bandWidthBegin = bandWidthBegin + 8;
                                                bandWidthEnd = bandWidthEnd + 8;
                                                tddSpecialSfBegin = tddSpecialSfBegin + 8;
                                                tddSpecialSfEnd = tddSpecialSfEnd + 8;
                                                interFreqLstNumBegin = interFreqLstNumBegin + 8;
                                                interFreqLstNumEnd = interFreqLstNumEnd + 8;
                                                interFreqNghNumBegin = interFreqNghNumBegin + 8;
                                                interFreqNghNumEnd = interFreqNghNumEnd + 8;
                                            }

									/*int number = InterFreqLstNum-r;
                                    if(number!=1){
										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
									}*/
                                            dlEarfcnBegin = dlEarfcnBegin + 24;
                                            dlEarfcnEnd = dlEarfcnEnd + 24;
                                            pciBegin = pciBegin + 24;
                                            pciEnd = pciEnd + 24;
                                            tacBegin = tacBegin + 24;
                                            tacEnd = tacEnd + 24;
                                            plmnBegin = plmnBegin + 24;
                                            plmnEnd = plmnEnd + 24;
                                            celldBegin = celldBegin + 24;
                                            celldEnd = celldEnd + 24;
                                            priorityBegin = priorityBegin + 24;
                                            priorityEnd = priorityEnd + 24;
                                            rsrpBegin = rsrpBegin + 24;
                                            rsrpEnd = rsrpEnd + 24;
                                            rsrqBegin = rsrqBegin + 24;
                                            rsrqEnd = rsrqEnd + 24;
                                            bandWidthBegin = bandWidthBegin + 24;
                                            bandWidthEnd = bandWidthEnd + 24;
                                            tddSpecialSfBegin = tddSpecialSfBegin + 24;
                                            tddSpecialSfEnd = tddSpecialSfEnd + 24;
                                            interFreqLstNumBegin = interFreqLstNumBegin + 24;
                                            interFreqLstNumEnd = interFreqLstNumEnd + 24;

                                        }
                                        interFreqLstNumBegin = interFreqLstNumBegin + 64;
                                        interFreqLstNumEnd = interFreqLstNumEnd + 64;
                                        interFreqNghNumBegin = interFreqNghNumBegin + 64;
                                        interFreqNghNumEnd = interFreqNghNumEnd + 64;
                                    }
                                    str3 = "";
                                    Log.d("nzqrun77spBeanList", "nzqrun: " + "执行" + spBeanList);
                                    SPBEANLIST1Fragment = spBeanList;
                                    if (spBeanList.size() == 0) {
                                        Log.d(TAG, "nzqrunrun: " + "等于0");
                                    } else {
                                        Log.d(TAG, "nzqrunrun: " + "大于0");
                                        SPBEANLIST1 = spBeanList;
                                        Log.d("nzqspBeanList1", "" + spBeanList);
//                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
                                        //先根据优先级,如果优先级一样根据RSRP
                                        List<Integer> list = new ArrayList();
                                        String down1 = "";
                                        SpBean spBean1 = new SpBean();
                                        SpBean spBean2 = new SpBean();
                                        if (spBeanList.size() >= 2) {
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                list.add(spBeanList.get(i).getPriority());
                                            }
                                            Integer max = Collections.max(list);
                                            Log.d("Anzqmax", "大于2条run: " + max);
                                            list.remove(max);//最大的优先

                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max.equals(spBeanList.get(i).getPriority())) {
                                                    down1 = spBeanList.get(i).getDown();
                                                    spBean1 = spBeanList.get(i);
                                                }
                                            }
                                            //第二个优先
                                            String down2 = "";
                                            Integer max2 = Collections.max(list);
//                                    Log.d("Anzqmax2", "run: " + max);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (max2.equals(spBeanList.get(i).getPriority())) {

                                                    down2 = spBeanList.get(i).getDown();
                                                    spBean2 = spBeanList.get(i);
                                                }
                                            }
                                            Log.d("down2a", "run: " + down2);
                                            if (max != max2) {

                                                if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    Log.d("Anzqmax", "A大于2条run且优先级有区别但是频点一致: " + max);
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                } else {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");

                                                    for (int i = 0; i < spBeanList.size(); i++) {
                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
                                                            down2 = spBeanList.get(i).getDown();
                                                            spBean2 = spBeanList.get(i);
                                                            break;
                                                        }
                                                    }


//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
                                                }


                                            } else {//根据优先级比较一致  ,通过rsrp比较

                                                int rssp1;
                                                int rssp2;
                                                List<Integer> list1rsp = new ArrayList<>();
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    list1rsp.add(spBeanList.get(i).getRsrp());
                                                }
                                                //最大的rsrp
                                                rssp1 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp1 == spBeanList.get(i).getRsrp()) {
                                                        down1 = spBeanList.get(i).getDown();
                                                        spBean1 = spBeanList.get(i);
                                                    }
                                                }
                                                for (int i = 0; i < list1rsp.size(); i++) {
                                                    if (list1rsp.get(i).equals(rssp1)) {
                                                        list1rsp.remove(i);
                                                    }
                                                }
                                                //求第二个rsrp
                                                rssp2 = Collections.max(list1rsp);
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (rssp2 == spBeanList.get(i).getRsrp()) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                    }
                                                }
                                                if (down1.equals(down2)) {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//                                                    for (int i = 0; i < spBeanList.size(); i++) {
//                                                        if (!down1.equals(spBeanList.get(i).getDown())) {
//                                                            down2 = spBeanList.get(i).getDown();
//                                                            spBean2 = spBeanList.get(i);
//                                                            break;
//                                                        }
//
//                                                    }
//                                                    bundle.putString("sp1MAX2value", "");
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
                                                } else {
//                                                    message = new Message();
//                                                    bundle.putString("sp1MAX1value", down1);//下行
//                                                    bundle.putString("sp1up", spBean1.getUp() + "");
//                                                    bundle.putString("sp1pci", spBean1.getPci() + "");
//                                                    bundle.putString("sp1plmn", spBean1.getPlmn() + "");
//                                                    bundle.putString("sp1band", spBean1.getBand() + "");
//                                                    bundle.putString("sp1tac", spBean1.getTac() + "");
//
//                                                    bundle.putString("sp1MAX2value", down2);
//                                                    bundle.putString("sp2up", spBean2.getUp() + "");
//                                                    bundle.putString("sp2pci", spBean2.getPci() + "");
//                                                    bundle.putString("sp2plmn", spBean2.getPlmn() + "");
//                                                    bundle.putString("sp2band", spBean2.getBand() + "");
//                                                    bundle.putString("sp2tac", spBean2.getTac() + "");
//                                                    message.setData(bundle);
//                                                    handler.sendMessage(message);
//                                                    message.what = 100152;
                                                    EventBus.getDefault().post(new MessageEvent(100152, down1));
                                                    Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
                                                }


//                                            ToastUtils.showToast("当前条数为多条");
//                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
                                            }

                                        } else {
                                            //

                                            if (spBeanList.size() > 0 && spBeanList.size() == 1) {
                                                down1 = spBeanList.get(0).getDown();
//                                            spBean2 = spBeanList.get(0);
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", down1);
//                                                bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                                bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                                bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                                bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                                bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                                bundle.putString("sp1MAX2value", "");
//                                                bundle.putString("sp2up", "");
//                                                bundle.putString("sp2pci", "");
//                                                bundle.putString("sp2plmn", "");
//                                                bundle.putString("sp2band", "");
//                                                bundle.putString("sp2tac", "");
//
//
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
                                                EventBus.getDefault().post(new MessageEvent(100152, down1));
//                                            ToastUtils.showToast("当前条数为1");
                                                Log.d("Anzqmax", "当前条数为1: ");
                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value", "");
//                                                bundle.putString("sp1MAX2value", "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 100152;
                                                EventBus.getDefault().post(new MessageEvent(100152, ""));
//                                            ToastUtils.showToast("当前条数为0");
                                                Log.d("Anzqmax", "当前条数为0: ");
                                            }
                                        }
                                    }


                                }

                            }
                        }
                    }

                    //设备2
                    if (IP2.equals(dp.getAddress().getHostAddress())) {
                        mPressedTime2 = System.currentTimeMillis();
                        if ("2cf0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("设备型号：" + hexStringToString(str.substring(32, 46)));
//                                DEVICENUMBER2 = hexStringToString(str.substring(32, 46));
                                SPUtils.putParam(getApplicationContext(), "DEVICENUMBER2", hexStringToString(str.substring(32, 46)));
                            } else if ("01000000".equals(str.substring(24, 32))) {
                                System.out.println("硬件版本：" + hexStringToString(str.substring(32, 34)));
//                                HARDWAREVERSION2 = hexStringToString(str.substring(32, 34));
                                SPUtils.putParam(getApplicationContext(), "HARDWAREVERSION2", hexStringToString(str.substring(32, 34)));
                            } else if ("02000000".equals(str.substring(24, 32))) {
                                System.out.println("软件版本：" + hexStringToString(str.substring(32, 106)));
//                                SOFTWAREVERSION2 = hexStringToString(str.substring(32, 106));
                                SPUtils.putParam(getApplicationContext(), "SOFTWAREVERSION2", hexStringToString(str.substring(32, 106)));
                            } else if ("03000000".equals(str.substring(24, 32))) {
                                System.out.println("序列号SN:" + hexStringToString(str.substring(32, 70)));
//                                SNNUMBER2 = hexStringToString(str.substring(32, 70));
                                SPUtils.putParam(getApplicationContext(), "SNNUMBER2", hexStringToString(str.substring(32, 70)));

                            } else if ("04000000".equals(str.substring(24, 32))) {
                                System.out.println("MAC地址：" + hexStringToString(str.substring(32, 66)));
//                                MACADDRESS2 = hexStringToString(str.substring(32, 66));
                                SPUtils.putParam(getApplicationContext(), "MACADDRESS2", hexStringToString(str.substring(32, 66)));
                            } else if ("05000000".equals(str.substring(24, 32))) {
//                                UBOOTVERSION2 = hexStringToString(str.substring(32, 47));
                                System.out.println("uboot版本号：" + hexStringToString(str.substring(32, 47)));
                                SPUtils.putParam(getApplicationContext(), "UBOOTVERSION2", hexStringToString(str.substring(32, 47)));

                            } else if ("06000000".equals(str.substring(24, 32))) {
                                System.out.println("板卡温度：" + hexStringToString(str.substring(32, 50)));
//                                BOARDTEMPERATURE2 = hexStringToString(str.substring(32, 50));
                                SPUtils.putParam(getApplicationContext(), "BOARDTEMPERATURE2", hexStringToString(str.substring(32, 50)));
                            }
                        }
                        if ("28f0".equals(str.substring(8, 12))) {
                            //PLMN
                            System.out.println(StringTOIMEI(str.substring(40, 50)));
//                            PLMN2 = StringTOIMEI(str.substring(40, 50));
                            SPUtils.putParam(getApplicationContext(), "PLMN2", hexStringToString(str.substring(40, 50)));

                            //上行频点
                            System.out.println("我上行上行频点" + Integer.parseInt(StringPin(str.substring(24, 32)), 16));
//                            UP2 = Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "UP2", Integer.parseInt(StringPin(str.substring(24, 32)), 16) + "");

                            //下行频点
                            System.out.println(Integer.parseInt(StringPin(str.substring(32, 40)), 16));
//                            DWON2 = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "DWON2", Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "");

                            //Band
                            System.out.println(Integer.parseInt(StringPin(str.substring(56, 64)), 16));
//                            BAND1 = Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "BAND2", Integer.parseInt(StringPin(str.substring(56, 64)), 16) + "");

                            //带宽
                            System.out.println(Integer.parseInt(str.substring(54, 56), 16));
//                            DK1 = Integer.parseInt(str.substring(54, 56), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "DK2", Integer.parseInt(str.substring(54, 56), 16) + "");

                            //TAC
                            System.out.println(Integer.parseInt(StringPin(str.substring(68, 72)), 16));
//                            TAC1 = Integer.parseInt(StringPin(str.substring(68, 72)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "TAC2", Integer.parseInt(StringPin(str.substring(68, 72)), 16) + "");
                            //PCI
                            System.out.println(Integer.parseInt(StringPin(str.substring(64, 68)), 16));
//                            PCI1 = Integer.parseInt(StringPin(str.substring(64, 68)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "PCI2", Integer.parseInt(StringPin(str.substring(64, 68)), 16) + "");

                            //Cellld
                            System.out.println(Integer.parseInt(StringPin(str.substring(72, 80)), 16));
//                            CELLID2 = Integer.parseInt(StringPin(str.substring(72, 80)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "CELLID2", Integer.parseInt(StringPin(str.substring(72, 80)), 16));

                            //UePMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(80, 84)), 16));
//                            DBM2 = Integer.parseInt(StringPin(str.substring(80, 84)), 16) + "";
                            SPUtils.putParam(getApplicationContext(), "DBM2", Integer.parseInt(StringPin(str.substring(80, 84)), 16) + "");

                            //EnodeBPMax
                            System.out.println(Integer.parseInt(StringPin(str.substring(84, 88)), 16));
//                            TYPE1 = Integer.parseInt(StringPin(str.substring(84, 88)), 16) + "";

                        }
                        //基站执行状态/
                        if ("19f0".equals(str.substring(8, 12))) {
                            String state = str.substring(24, 32);
                            System.out.println("state" + state);
                            Log.d("wtto", "staterun: " + state);
                            if ("00000000".equals(state)) {
                                System.err.println("空口同步成功");
                                Log.d("wtto", "qqqrun:空口同步成功");
//                                message = new Message();
//                                bundle.putString("test", "1");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
//                                                        EventBus.getDefault().post(new MessageEvent(1001,"数据=" + i));
                                EventBus.getDefault().post(new MessageEvent(200120, "空口同步成功"));

                            } else if ("01000000".equals(state)) {
                                System.err.println("空口同步失败");
                                Log.d("wtto", "qqqrun:空口同步失败");
//                                message = new Message();
//                                bundle.putString("test", "2");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "空口同步失败"));
                            } else if ("02000000".equals(state)) {
                                System.err.println("GPS同步成功");
                                Log.d("wtto", "qqqrun:GPS同步成功");
//                                message = new Message();
//                                bundle.putString("test", "3");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "GPS同步成功"));
                            } else if ("03000000".equals(state)) {
                                System.err.println("GPS同步失败");
                                Log.d("wtto", "qqqrun:GPS同步失败");
//                                message = new Message();
//                                bundle.putString("test", "4");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "GPS同步失败"));
                            } else if ("04000000".equals(state)) {
                                System.err.println("扫频成功");
                                Log.d("wtto", "qqqrun:扫频成功");
//                                message = new Message();
//                                bundle.putString("test", "5");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;

                            } else if ("05000000".equals(state)) {
                                System.err.println("扫频失败");
                                Log.d("wtto", "qqqrun:扫频失败");
//                                message = new Message();
//                                bundle.putString("test", "6");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;

                            } else if ("06000000".equals(state)) {
                                System.err.println("小区激活成功");
                                Log.d("wtto", "qqqrun:小区激活成功");
//                                message = new Message();
//                                bundle.putString("test", "7");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "小区激活成功"));
                            } else if ("07000000".equals(state)) {
                                System.err.println("小区激活失败");
                                Log.d("wtto", "qqqrun:小区激活失败");
//                                message = new Message();
//                                bundle.putString("test", "8");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "小区激活失败"));
                            } else if ("08000000".equals(state)) {
                                System.err.println("小区去激活");
                                Log.d("wtto", "qqqrun:小区去激活");
//                                message = new Message();
//                                bundle.putString("test", "9");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "小区去激活"));
                            } else if ("09000000".equals(state)) {
                                System.err.println("空口同步中");
                                Log.d("wtto", "qqqrun:空口同步中2");
//                                message = new Message();
//                                bundle.putString("test", "10");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "空口同步中"));
                            } else if ("0a000000".equals(state)) {
                                System.err.println("GPS同步中2");
                                Log.d("wtto", "qqqrun:GPS同步中2");
//                                message = new Message();
//                                bundle.putString("test", "11");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "GPS同步中"));
                            } else if ("0b000000".equals(state)) {
                                System.err.println("扫频中2");
                                Log.d("wtto", "qqqrun:扫频中2");
//                                message = new Message();
//                                bundle.putString("test", "12");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "扫频中"));
                            } else if ("0c000000".equals(state)) {
                                System.err.println("小区激活中2");
                                Log.d("wtto", "qqqrun:小区激活中");
//                                message = new Message();
//                                bundle.putString("test", "13");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "小区激活中"));
                            } else if ("0d000000".equals(state)) {
                                System.err.println("小区去激活中2");
                                Log.d("wtto", "qqqrun:小区去激活中");
//                                message = new Message();
//                                bundle.putString("test", "14");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 1001200;
                                EventBus.getDefault().post(new MessageEvent(200120, "小区去激活中"));
                            }

                        }
                        if ("10f0".equals(str.substring(8, 12))) {
                            //心跳解析
                            //查看小区是否建立成功（0：小区 IDLE态；1：扫频/同步进行中；2：小区激活中；3：小区激活态；4：小区去激活中）
//                        message = new Message();
//                        bundle.putString("zt1", "2");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100120;
                            if ("0000".equals(str.substring(24, 28))) {
                                System.out.println("0：小区 IDLE态");
//                            message = new Message();
//                            bundle.putString("zt1", "2");//IDLE态
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(200120, "就绪"));
                            } else if ("0100".equals(str.substring(24, 28))) {
                                System.out.println("2：扫频/同步进行中");
//                            message = new Message();
//                            bundle.putString("zt1", "3");//扫频同步进行中
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(200120, "扫频同步进行中"));

                            } else if ("0200".equals(str.substring(24, 28))) {
                                System.out.println("2：小区激活中");
//                            message = new Message();
//                            bundle.putString("zt1", "4");//小区激活中
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(200120, "小区激活中"));
                            } else if ("0300".equals(str.substring(24, 28))) {
                                System.out.println("3：定位中");
//                            message = new Message();
//                            bundle.putString("zt1", "5");//小区激活态
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100120;
                                EventBus.getDefault().post(new MessageEvent(200120, "定位中"));
                                //Band号
                                Integer.parseInt(StringPin(str.substring(28, 32)), 16);
                                System.out.println("Band2号：" + Integer.parseInt(StringPin(str.substring(28, 32)), 16));
                                //上行频点
                                Integer.parseInt(StringPin(str.substring(32, 40)), 16);
                                System.out.println("上行频点2：" + Integer.parseInt(StringPin(str.substring(32, 40)), 16));
                                //下行频点
                                Integer.parseInt(StringPin(str.substring(40, 48)), 16);
                                System.out.println("下行频点2：" + Integer.parseInt(StringPin(str.substring(40, 48)), 16));
                                DOWNPIN2 = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
                                EventBus.getDefault().post(new MessageEvent(200151, Integer.parseInt(StringPin(str.substring(40, 48)), 16) + ""));
//                            message = new Message();
//                            bundle.putString("down", Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");//查询增益
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100151;
                                System.out.println("100151" + Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "");
                                //移动联通电信
                                if ("3436303030".equals(str.substring(48, 58))) {
                                    //设置中国移动
                                }
                                if ("3436303031".equals(str.substring(48, 58))) {
                                    //设置中国联通
                                }
                                if ("3436303033".equals(str.substring(48, 58)) || "3436303131".equals(str.substring(48, 58))) {
                                    //设置中国电信
                                }

                                //PCI
                                Integer.parseInt(StringPin(str.substring(64, 68)), 16);
                                System.out.println("PCI2:" + Integer.parseInt(StringPin(str.substring(64, 68)), 16));
                                //TAC
                                Integer.parseInt(StringPin(str.substring(68, 72)), 16);
                                System.out.println("TAC2:" + Integer.parseInt(StringPin(str.substring(68, 72)), 16));

                            } else if ("0400".equals(str.substring(24, 28))) {
                                System.out.println("4：小区去激活中2");
                            }
                            //模式：FDD TDD
                            if ("00ff".equals(str.substring(16, 20))) {
                                //设置模式FDD
                                ID2TF = "FDD";
//                            System.err.println("FDD");
//                            message = new Message();
//                            bundle.putString("tf1", "FDD");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100110;
                                EventBus.getDefault().post(new MessageEvent(200110, "FDD"));

                            }
                            if ("ff00".equals(str.substring(16, 20))) {
                                //设置模式TDD
                                ID2TF = "TDD";
//                            System.out.println("TDD");
//                            message = new Message();
//                            bundle.putString("tf1", "TDD");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100110;
                                EventBus.getDefault().post(new MessageEvent(200110, "TDD"));
                            }

                        }

//                        //设置黑名单响应 清空响应
//                        if ("54f0".equals(str.substring(8, 12))) {
//                            if ("00000000".equals(str.substring(24, 32))) {
//                                opionts1++;
//                                System.out.println("设置基站黑名单成功" + opionts1);
//                                if (opionts1 % 2 == 0) {
//                                    Log.d("jsgs", "run:偶数");
//                                    message = new Message();
//                                    bundle.putString("100130", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100131;
//                                } else {
//                                    Log.d("jsgs", "run:基数");
//                                    message = new Message();
//                                    bundle.putString("100131", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100130;
//                                }
//                            } else {
//                                System.out.println("设置基站黑名单失败");
//
//                                if (opionts2 % 2 == 0) {
//                                    Log.d("jsgs", "run:偶数");
//                                    message = new Message();
//                                    bundle.putString("100130", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100135;
//                                } else {
//                                    Log.d("jsgs", "run:基数");
//                                    message = new Message();
//                                    bundle.putString("100131", "FDD");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 100134;
//                                }
//
//
//                            }
//                        }

                        //设置定位模式的应答信息
                        if ("07f0".equals(str.substring(8, 12))) {
                            if ("00000000".equals(str.substring(24, 32))) {
                                System.out.println("2设置基站ue定位成功");

//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100136;
//                                Log.d("str10010001", "run: " + str.toString());
                                EventBus.getDefault().post(new MessageEvent(200136, "0"));
                            } else {
                                System.out.println("2设置基站ue定位失败");
                                Log.d("str10010002", "run: " + str.toString());
//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100137;
                                EventBus.getDefault().post(new MessageEvent(200137, "0"));
                            }
                        }
                        //制式切换是否成功
                        if ("02f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("2制式切换成功！");
//                            message = new Message();
//                            bundle.putString("test", "");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100140;
                                EventBus.getDefault().post(new MessageEvent(200140, "1"));

                            } else {
                                System.err.println("2制式切换失败");
//                            message = new Message();
//                            bundle.putString("test", "");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100141;
                                EventBus.getDefault().post(new MessageEvent(200141, "0"));

                            }
                        }
                        if ("0cf0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
                                System.out.println("重启设置成功！");
//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100138;
                                EventBus.getDefault().post(new MessageEvent(200138, "0"));
                            } else {
                                System.err.println("重启失败！");
//                                message = new Message();
//                                bundle.putString("test", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100139;
                                EventBus.getDefault().post(new MessageEvent(200139, "0"));
                            }
                        }
                        //建立小区是发送否成功1
                        if ("04f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            Log.d("wtto", "04f0run:0 ");
                            if (row == 0) {
//                            message = new Message();
//                            bundle.putString("test", "0");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100145;
                                System.out.println("2设置成功！开始建立小区！");
                                EventBus.getDefault().post(new MessageEvent(200145, "小区设置成功"));
                                Log.d("wtto", "04f0run:1 ");
                            } else {
                                System.err.println("设置失败！");
//                            message = new Message();
//                            bundle.putString("test", "0");
//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 100146;
                                EventBus.getDefault().post(new MessageEvent(200146, "小区设置失败"));
                                Log.d("wtto", "04f0run:2 ");
                            }
                        }
                        //定位IMSI   能量值,
                        if ("08f0".equals(str.substring(8, 12))) {

                            //目标距离（16进制字符串转换成十进制）
                            Integer.parseInt(str.substring(24, 26), 16);
                            System.out.println("距离：" + Integer.parseInt(str.substring(24, 26), 16));
//                        message = new Message();
//                        bundle.putString("sb1jl", Integer.parseInt(str.substring(24, 26), 16) + "");
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100147;
                            EventBus.getDefault().post(new MessageEvent(200147, Integer.parseInt(str.substring(24, 26), 16) + ""));

                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(26, 56));
//                        message = new Message();
//                        bundle.putString("imsi", imsi);
//                        bundle.putString("sb", "1");
//                        bundle.putString("zb", "1");
//                        bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                        bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 30000;
                            Map<String, String> map = new HashMap<>();
                            map.put("imsi", imsi);
                            map.put("sb", "4G");
                            map.put("zb", "1");
                            map.put("datetime", ReceiveMainDateFormat1.format(now));
                            map.put("time", ReceiveMainDateFormat2.format(now));
                            EventBus.getDefault().post(new MessageEvent(30000, map));


                            //IMSI号
                            StringTOIMEI(str.substring(26, 56));
                            System.out.println("IMSI号：" + hexStringToString(str.substring(26, 56)));


//                        message = new Message();
//                        bundle.putString("imsi1", imsi);
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 100148;
                            Log.d(TAG, "loggnzqrun: " + hexStringToString(str.substring(26, 56)));
                            EventBus.getDefault().post(new MessageEvent(200148, imsi));
                        }
                        //黑名单中标   侦码IMSI
                        if ("05f0".equals(str.substring(8, 12))) {
                            String down = "";
                            System.out.println("1黑名单中标IMSI号：" + hexStringToString(str.substring(32, 62)));
                            //当前小区信息状态响应
                            if ("28f0".equals(str.substring(8, 12))) {
                                down = Integer.parseInt(StringPin(str.substring(32, 40)), 16) + "";
                            }
//                        down = Integer.parseInt(StringPin(str.substring(40, 48)), 16) + "";
                            Date now = new Date();
                            String imsi = hexStringToString(str.substring(32, 62));
                            Log.d("jsgs", "run:基数");
//                        message = new Message();
//                        bundle.putString("imsi", imsi);
//                        bundle.putString("sb", "1");
//                        bundle.putString("zb", "");
//                        bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                        bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));
////                            bundle.putString("zmdown", "" + down);
//                        message.setData(bundle);
//                        handler.sendMessage(message);
//                        message.what = 30000;
                            Map<String, String> map = new HashMap<>();
                            map.put("imsi", imsi);
                            map.put("sb", "5G");
                            map.put("zb", "");
                            map.put("datetime", ReceiveMainDateFormat1.format(now));
                            map.put("time", ReceiveMainDateFormat2.format(now));
                            EventBus.getDefault().post(new MessageEvent(30000, map));
                            Log.d("Aaims05f0i", "imsirun: " + imsi);
                            System.out.println("Imsi黑名单中标" + imsi + "down" + down);
                            if (!TextUtils.isEmpty(DOWNPIN2)) {
                                try {
                                    DBManagerZM dbManagerZM = new DBManagerZM(getApplication());
                                    ZmBean zmBean = new ZmBean();
                                    zmBean.setImsi(imsi);
                                    zmBean.setZb("");
                                    zmBean.setSb("2");
                                    zmBean.setDown(DOWNPIN2);
                                    zmBean.setDatatime(ReceiveMainDateFormat1.format(now));
                                    zmBean.setTime(ReceiveMainDateFormat2.format(now));
                                    dbManagerZM.insertAddZmBean(zmBean);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
//                            bundle.putString("imsi", imsi);
//                            bundle.putString("sb", "1");
//                            bundle.putString("zb", "");
//                            bundle.putString("datetime", "" + ReceiveMainDateFormat1.format(now));
//                            bundle.putString("time", "" + ReceiveMainDateFormat2.format(now));

//                            message.setData(bundle);
//                            handler.sendMessage(message);
//                            message.what = 3000000;

                            }

                        }
                        if ("14f0".equals(str.substring(8, 12))) {
                            //成功0；不成功>0（16进制字符串转换成十进制）
                            int row = Integer.parseInt(str.substring(24, 32), 16);
                            if (row == 0) {
//                                System.out.println("增益值设置成功!");
//                                message = new Message();
//                                bundle.putString("zyset1", "增益值设置成功");//小区激活中
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100150;
//                                System.out.println("100150");
                                EventBus.getDefault().post(new MessageEvent(200150, "增益值设置成功"));


                            } else {
                                System.err.println("增益值设置失败!");
//                                message = new Message();
//                                bundle.putString("zyset1", "增益值设置失败");//小区激活中
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 100150;
//                                System.out.println("100150");
                                EventBus.getDefault().post(new MessageEvent(200150, "增益值设置失败"));
                            }
                        }
                        if ("0af0".equals(str.substring(8, 12))) {
                            //消息头长度
                            List<SpBean> spBeanList = new ArrayList<>();
                            String length = str.substring(12, 16);
                            String len = StringPin(length);
                            Integer strlen = Integer.parseInt(len, 16);
                            //是否发送完
                            Integer.parseInt(StringPin(str.substring(20, 24)), 16);
                            String code = Integer.toBinaryString(Integer.parseInt(StringPin(str.substring(20, 24)), 16));

                            code = StringAd(code);

                            System.err.println("code:" + code);
                            String s = str.substring(24, strlen * 2);
                            System.out.println("ss___" + s);
                            str4 = str4 + s;
                            System.err.println("s3___" + str4);
                            if ("0".equals(code.substring(0, 1))) {

                                str4 = "aaaa55550af0240000ff0000" + str4;
                                System.err.println("str3:+++++++++++++++" + str4);
                                //采集的小区数目
                                int row;
                                if ("ffff".equals(str4.substring(24, 28))) {
                                    row = 0;
                                } else {
                                    row = Integer.parseInt(StringPin(str4.substring(24, 28)), 16);
                                    System.out.println("小区个数：" + row);
                                }

                                int dlEarfcnBegin = 32, dlEarfcnEnd = 40;
                                int pciBegin = 40, pciEnd = 44;
                                int tacBegin = 44, tacEnd = 48;
                                int plmnBegin = 48, plmnEnd = 52;
                                int celldBegin = 56, celldEnd = 64;
                                int priorityBegin = 64, priorityEnd = 72;
                                int rsrpBegin = 72, rsrpEnd = 74;
                                int rsrqBegin = 74, rsrqEnd = 76;
                                int bandWidthBegin = 76, bandWidthEnd = 78;
                                int tddSpecialSfBegin = 78, tddSpecialSfEnd = 80;
                                int interFreqLstNumBegin = 88, interFreqLstNumEnd = 96;
                                int interFreqNghNumBegin = 0, interFreqNghNumEnd = 0;
                                for (int i = 0; i < row; i++) {
                                    //下行频点
                                    //下行频点
                                    SpBean spBean = new SpBean();
                                    System.out.println(str4.substring(dlEarfcnBegin, dlEarfcnEnd));
                                    if ("ffffffff".equals(str4.substring(dlEarfcnBegin, dlEarfcnEnd))) {
                                        System.out.println("null");

                                    } else {
                                        try {
//                                            System.out.println("下行频点：------" + Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                            spBean.setDown(Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                            try {
                                                System.out.println("下行频点：------" + Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
                                                spBean.setDown(Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                            } catch (NumberFormatException e) {
                                                spBean.setDown(null);
                                            }
                                        } catch (NumberFormatException e) {
                                            spBean.setDown(null);
                                        }
//                                        System.out.println("下行频点：------" + Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16));
//                                        spBean.setDown(Integer.parseInt(StringPin(str4.substring(dlEarfcnBegin, dlEarfcnEnd)), 16) + "");
                                    }
                                    if (ID2TF.equals("TDD")) {
                                        spBean.setUp(255 + "");
                                    } else {
                                        if (!TextUtils.isEmpty(spBean.getDown())) {
                                            int i1 = Integer.parseInt(spBean.getDown()) + 18000;
                                            spBean.setUp(i1 + "");
                                        }
                                    }
                                    //PCI
//                                    System.out.println("PCI：------" + Integer.parseInt(StringPin(str4.substring(pciBegin, pciEnd)), 16));
                                    if ("ffff".equals(str4.substring(pciBegin, pciEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setPci(0);
                                    } else {
                                        spBean.setPci(Integer.parseInt(StringPin(str4.substring(pciBegin, pciEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }

//                                    spBean.setPci(Integer.parseInt(StringPin(str4.substring(pciBegin, pciEnd)), 16));
//                                    System.out.println(dlEarfcnBegin + "+" + dlEarfcnEnd);
//                                    spBean.setPci(0);
//                                    TAC
                                    try {
                                        str4.substring(tacBegin, tacEnd);
                                        spBean.setTac(Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));
                                    } catch (StringIndexOutOfBoundsException e) {
                                        spBean.setTac(0);
                                    }
//                                    System.out.println("TAC：------" + Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));

//                                    spBean.setTac(Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));
//                                    if (str4.length() > tacEnd) {
//                                        if ("ffffffff".equals(str4.substring(tacBegin, tacEnd))) {
////
//                                            spBean.setTac(0);
//                                        } else {
//                                            spBean.setTac(Integer.parseInt(StringPin(str4.substring(tacBegin, tacEnd)), 16));
////
//                                        }
//                                    } else {
//                                        spBean.setTac(0);
//                                    }
//                                    spBean.setTac(0);
                                    //PLMN
//                                    System.out.println(Integer.parseInt(StringPin(str4.substring(plmnBegin, plmnEnd)), 16) + "---PLMN：");
//                                    spBean.setPlmn(Integer.parseInt(StringPin(str4.substring(plmnBegin, plmnEnd)), 16) + "");
//                                    spBean.setPlmn("");

                                    try {
                                        if ("ffff".equals(str4.substring(plmnBegin, plmnEnd))) {
                                            Log.d("1nzqffffffff", "run:1 ");
                                            spBean.setPlmn(0 + "");
                                        } else {
                                            spBean.setPlmn(Integer.parseInt(StringPin(str4.substring(plmnBegin, plmnEnd)), 16) + "");
                                            Log.d("2nzqffffffff", "run:1 ");
                                        }
                                    } catch (StringIndexOutOfBoundsException e) {
                                        spBean.setPlmn(0 + "");
                                    }


                                    //CellId
//                                    System.out.println("ffffffff".equals(str4.substring(celldBegin, celldEnd)) ? "null" : Integer.parseInt(StringPin(str4.substring(celldBegin, celldEnd)), 16) + "------CellId：");
                                    try {
                                        if ("ffffffff".equals(str4.substring(celldBegin, celldEnd))) {
                                            Log.d("1nzqffffffff", "run:1 ");
                                            spBean.setCid(0 + "");
                                        } else {
                                            spBean.setCid(Integer.parseInt(StringPin(str4.substring(celldBegin, celldEnd)), 16) + "");
                                            Log.d("2nzqffffffff", "run:1 ");
                                        }
                                    } catch (NumberFormatException e) {
                                        spBean.setCid(0 + "");
                                    }

                                    //Priority 本小区频点优先级
//                                    System.out.println(str4.substring(64, 72));
                                    System.out.println("ffffffff".equals(str4.substring(priorityBegin, priorityEnd)) ? "Priority_null" : Integer.parseInt(StringPin(str4.substring(priorityBegin, priorityEnd)), 16) + "------Priority 本小区频点优先级：");
                                    if ("ffffffff".equals(str4.substring(priorityBegin, priorityEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setPriority(0);
                                    } else {
                                        spBean.setPriority(Integer.parseInt(StringPin(str4.substring(priorityBegin, priorityEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }
                                    //RSRP
//                                    System.out.println("RSRP：------" + Integer.parseInt(StringPin(str4.substring(rsrpBegin, rsrpEnd)), 16));
//                                    spBean.setRsrp(Integer.parseInt(StringPin(str4.substring(rsrpBegin, rsrpEnd)), 16));

                                    if ("ffffffff".equals(str4.substring(rsrpBegin, rsrpEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setRsrp(0);
                                    } else {
                                        spBean.setRsrp(Integer.parseInt(StringPin(str4.substring(rsrpBegin, rsrpEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }


                                    //RSRQ
//                                    System.out.println("RSRQ：------" + Integer.parseInt(StringPin(str4.substring(rsrqBegin, rsrqEnd)), 16));
//                                    spBean.setRsrq(Integer.parseInt(StringPin(str4.substring(rsrqBegin, rsrqEnd)), 16));

                                    if ("ffffffff".equals(str4.substring(rsrqBegin, rsrqEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setRsrq(0);
                                    } else {
                                        spBean.setRsrq(Integer.parseInt(StringPin(str4.substring(rsrqBegin, rsrqEnd)), 16));
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }
                                    //Bandwidth小区工作带宽
//                                    System.out.println("Bandwidth：------" + Integer.parseInt(StringPin(str4.substring(bandWidthBegin, bandWidthEnd)), 16));

                                    if ("ffffffff".equals(str4.substring(bandWidthBegin, bandWidthEnd))) {
                                        Log.d("1nzqffffffff", "run:1 ");
                                        spBean.setBand("");
                                    } else {
                                        spBean.setBand(Integer.parseInt(StringPin(str4.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                        Log.d("2nzqffffffff", "run:1 ");
                                    }
//                                    spBean.setBand(Integer.parseInt(StringPin(str4.substring(bandWidthBegin, bandWidthEnd)), 16) + "");
                                    try {
                                        if (spBean.getDown().equals("0")) {

                                        } else {
                                            spBeanList.add(spBean);//测试代码add
                                        }
                                    } catch (Exception e) {

                                    }

                                    //TddSpecialSf Patterns TDD特殊子帧配置
//                                        System.out.println("TDD特殊子帧配置：------" + Integer.parseInt(StringPin(str4.substring(tddSpecialSfBegin, tddSpecialSfEnd)), 16));
                                    //异频小区个数
//                                    int InterFreqLstNum;
//                                    if ("ffffffff".equals(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
//                                        InterFreqLstNum = 0;
//                                    } else {
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
//                                    }
                                    int InterFreqLstNum;
                                    try {
                                        if ("ffffffff".equals(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd))) {
                                            InterFreqLstNum = 0;
                                        } else {
                                            try {
                                                InterFreqLstNum = Integer.parseInt(StringPin(str4.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                            } catch (Exception e) {
                                                InterFreqLstNum = 0;
                                                Log.d("nzqexce1", "run: " + e.getMessage());
                                            }
//                                        InterFreqLstNum = Integer.parseInt(StringPin(str3.substring(interFreqLstNumBegin, interFreqLstNumEnd)), 16);
                                        }
                                    } catch (Exception e) {
                                        InterFreqLstNum = 0;
                                        Log.d("nzqexce3", "run: " + e.getMessage());
                                    }
//                                        System.out.println(interFreqLstNumBegin + "---" + interFreqLstNumEnd);
//                                        System.out.println("异频小区个数：------" + InterFreqLstNum);

                                    dlEarfcnBegin = dlEarfcnBegin + 64;
                                    dlEarfcnEnd = dlEarfcnEnd + 64;
                                    pciBegin = pciBegin + 64;
                                    pciEnd = pciEnd + 64;
                                    tacBegin = tacBegin + 64;
                                    tacEnd = tacEnd + 64;
                                    plmnBegin = plmnBegin + 64;
                                    plmnEnd = plmnEnd + 64;
                                    celldBegin = celldBegin + 64;
                                    celldEnd = celldEnd + 64;
                                    priorityBegin = priorityBegin + 64;
                                    priorityEnd = priorityEnd + 64;
                                    rsrpBegin = rsrpBegin + 64;
                                    rsrpEnd = rsrpEnd + 64;
                                    rsrqBegin = rsrqBegin + 64;
                                    rsrqEnd = rsrqEnd + 64;
                                    bandWidthBegin = bandWidthBegin + 64;
                                    bandWidthEnd = bandWidthEnd + 64;
                                    tddSpecialSfBegin = tddSpecialSfBegin + 64;
                                    tddSpecialSfEnd = tddSpecialSfEnd + 64;
                                    //interFreqLstNumBegin = interFreqLstNumBegin+64;interFreqLstNumEnd = interFreqLstNumEnd+64;

                                    System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                    for (int r = 0; r < InterFreqLstNum; r++) {
//                                            System.out.println(interFreqNghNumBegin + "---" + interFreqNghNumEnd);
                                        interFreqNghNumBegin = interFreqLstNumBegin + 24;
                                        interFreqNghNumEnd = interFreqLstNumEnd + 24;
                                        //异频小区的领区数目
//                                            System.out.println(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd));
//                                            System.out.println("pin:" + StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)));
//                                            System.out.println(Integer.parseInt(StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16));
//                                        int interFreqNghNum;
//                                        if ("ffffffff".equals(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {
//
//                                            continue;
//                                        } else {
//                                            interFreqNghNum = Integer.parseInt(StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
////                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
//                                        }
                                        int interFreqNghNum;
//                                        try {
//                                        if (str4.length() < interFreqNghNumEnd) {
//                                            continue;
//                                        }
                                        try {
                                            if (!TextUtils.isEmpty(str4)) {

                                            } else {
                                                continue;
                                            }
                                            if ("ffffffff".equals(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd))) {

                                                interFreqNghNum = 0;
                                            } else {
                                                interFreqNghNum = Integer.parseInt(StringPin(str4.substring(interFreqNghNumBegin, interFreqNghNumEnd)), 16);
//                                                System.out.println("异频小区的邻区个数：" + interFreqNghNum);
                                            }
                                        } catch (Exception e) {
                                            interFreqNghNum = 0;
//                                            Log.d("nzqexce2", "run: " + e.getMessage());
                                        }

                                        for (int n = 0; n < interFreqNghNum; n++) {
                                            dlEarfcnBegin = dlEarfcnBegin + 8;
                                            dlEarfcnEnd = dlEarfcnEnd + 8;
                                            pciBegin = pciBegin + 8;
                                            pciEnd = pciEnd + 8;
                                            tacBegin = tacBegin + 8;
                                            tacEnd = tacEnd + 8;
                                            plmnBegin = plmnBegin + 8;
                                            plmnEnd = plmnEnd + 8;
                                            celldBegin = celldBegin + 8;
                                            celldEnd = celldEnd + 8;
                                            priorityBegin = priorityBegin + 8;
                                            priorityEnd = priorityEnd + 8;
                                            rsrpBegin = rsrpBegin + 8;
                                            rsrpEnd = rsrpEnd + 8;
                                            rsrqBegin = rsrqBegin + 8;
                                            rsrqEnd = rsrqEnd + 8;
                                            bandWidthBegin = bandWidthBegin + 8;
                                            bandWidthEnd = bandWidthEnd + 8;
                                            tddSpecialSfBegin = tddSpecialSfBegin + 8;
                                            tddSpecialSfEnd = tddSpecialSfEnd + 8;
                                            interFreqLstNumBegin = interFreqLstNumBegin + 8;
                                            interFreqLstNumEnd = interFreqLstNumEnd + 8;
                                            interFreqNghNumBegin = interFreqNghNumBegin + 8;
                                            interFreqNghNumEnd = interFreqNghNumEnd + 8;
                                        }

									/*int number = InterFreqLstNum-r;
                                    if(number!=1){
										interFreqNghNumBegin = interFreqNghNumBegin+24; interFreqNghNumEnd = interFreqNghNumEnd+24;
									}*/
                                        dlEarfcnBegin = dlEarfcnBegin + 24;
                                        dlEarfcnEnd = dlEarfcnEnd + 24;
                                        pciBegin = pciBegin + 24;
                                        pciEnd = pciEnd + 24;
                                        tacBegin = tacBegin + 24;
                                        tacEnd =
                                                +24;
                                        plmnBegin = plmnBegin + 24;
                                        plmnEnd = plmnEnd + 24;
                                        celldBegin = celldBegin + 24;
                                        celldEnd = celldEnd + 24;
                                        priorityBegin = priorityBegin + 24;
                                        priorityEnd = priorityEnd + 24;
                                        rsrpBegin = rsrpBegin + 24;
                                        rsrpEnd = rsrpEnd + 24;
                                        rsrqBegin = rsrqBegin + 24;
                                        rsrqEnd = rsrqEnd + 24;
                                        bandWidthBegin = bandWidthBegin + 24;
                                        bandWidthEnd = bandWidthEnd + 24;
                                        tddSpecialSfBegin = tddSpecialSfBegin + 24;
                                        tddSpecialSfEnd = tddSpecialSfEnd + 24;
                                        interFreqLstNumBegin = interFreqLstNumBegin + 24;
                                        interFreqLstNumEnd = interFreqLstNumEnd + 24;
                                    }
                                    interFreqLstNumBegin = interFreqLstNumBegin + 64;
                                    interFreqLstNumEnd = interFreqLstNumEnd + 64;
                                    interFreqNghNumBegin = interFreqNghNumBegin + 64;
                                    interFreqNghNumEnd = interFreqNghNumEnd + 64;
                                }
                                str4 = "";

                                SPBEANLIST2Fragment = spBeanList;
                                if (spBeanList.size() == 0) {
//
                                } else {
//                                    Log.d("nzqspBeanList2", "" + spBeanList);
//                                    bundle.putParcelableArrayList("List,", (ArrayList<? extends Parcelable>) spBeanList);
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 200152;

                                    SPBEANLIST2 = spBeanList;
//                                spBeanList.sort(Comparator.comparing(SpBean::getPriority));
                                    //先根据优先级,如果优先级一样根据RSRP
                                    List<Integer> list = new ArrayList();
                                    String down1 = "";
                                    SpBean spBean1 = new SpBean();
                                    SpBean spBean2 = new SpBean();
                                    if (spBeanList.size() >= 2) {
                                        for (int i = 0; i < spBeanList.size(); i++) {
                                            list.add(spBeanList.get(i).getPriority());
                                        }
                                        Integer max = Collections.max(list);
                                        Log.d("Anzqmax", "大于2条run: " + max);
                                        list.remove(max);//最大的优先

                                        for (int i = 0; i < spBeanList.size(); i++) {
                                            if (max.equals(spBeanList.get(i).getPriority())) {
                                                down1 = spBeanList.get(i).getDown();
                                                spBean1 = spBeanList.get(i);
                                                break;
                                            }
                                        }

                                        //第二个优先
                                        String down2 = "";
                                        Integer max2 = Collections.max(list);
//                                    Log.d("Anzqmax2", "run: " + max);
                                        for (int i = 0; i < spBeanList.size(); i++) {
                                            if (max2.equals(spBeanList.get(i).getPriority())) {

                                                down2 = spBeanList.get(i).getDown();
                                                spBean2 = spBeanList.get(i);
                                            }
                                        }
                                        Log.d("down2a", "run: " + down2);
                                        if (max != max2) {

                                            if (!down1.equals(down2)) {//优先级的频点一致 比较频点是否一致
//                                                message = new Message();
//
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value54", down2);
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
                                                EventBus.getDefault().post(new MessageEvent(200152, down1));
                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点一致: " + max);
                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");

                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                        break;
                                                    }
                                                }


//                                                bundle.putString("sp1MAX2value54", down2);
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
                                                EventBus.getDefault().post(new MessageEvent(200152, down1));
                                                Log.d("Anzqmax", "大于2条run且优先级有区别但是频点不一致: " + down1 + "--" + down2);
                                            }


                                        } else {//根据优先级比较一致  ,通过rsrp比较

                                            int rssp1;
                                            int rssp2;
                                            List<Integer> list1rsp = new ArrayList<>();
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                list1rsp.add(spBeanList.get(i).getRsrp());
                                            }
                                            //最大的rsrp
                                            rssp1 = Collections.max(list1rsp);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (rssp1 == spBeanList.get(i).getRsrp()) {
                                                    down1 = spBeanList.get(i).getDown();
                                                    spBean1 = spBeanList.get(i);
                                                }
                                            }
                                            for (int i = 0; i < list1rsp.size(); i++) {
                                                if (list1rsp.get(i).equals(rssp1)) {
                                                    list1rsp.remove(i);
                                                }
                                            }
                                            //求第二个rsrp
                                            rssp2 = Collections.max(list1rsp);
                                            for (int i = 0; i < spBeanList.size(); i++) {
                                                if (rssp2 == spBeanList.get(i).getRsrp()) {
                                                    down2 = spBeanList.get(i).getDown();
                                                    spBean2 = spBeanList.get(i);
                                                }
                                            }
                                            if (down1.equals(down2)) {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
                                                for (int i = 0; i < spBeanList.size(); i++) {
                                                    if (!down1.equals(spBeanList.get(i).getDown())) {
                                                        down2 = spBeanList.get(i).getDown();
                                                        spBean2 = spBeanList.get(i);
                                                        break;
                                                    }

                                                }
//                                                bundle.putString("sp1MAX2value54", "");
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
                                                EventBus.getDefault().post(new MessageEvent(200152, down1));
                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频一致取第二个不应的: " + down1 + "--" + down2);
                                            } else {
//                                                message = new Message();
//                                                bundle.putString("sp1MAX1value54", down1);//下行
//                                                bundle.putString("sp1up54", spBean1.getUp() + "");
//                                                bundle.putString("sp1pci54", spBean1.getPci() + "");
//                                                bundle.putString("sp1plmn54", spBean1.getPlmn() + "");
//                                                bundle.putString("sp1band54", spBean1.getBand() + "");
//                                                bundle.putString("sp1tac54", spBean1.getTac() + "");
//
//                                                bundle.putString("sp1MAX2value54", down2);
//                                                bundle.putString("sp2up54", spBean2.getUp() + "");
//                                                bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                                bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                                bundle.putString("sp2band54", spBean2.getBand() + "");
//                                                bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                                message.setData(bundle);
//                                                handler.sendMessage(message);
//                                                message.what = 200152;
                                                EventBus.getDefault().post(new MessageEvent(200152, down1));
                                                Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较且下频不一致: " + down1 + "--" + down2);
                                            }


//                                            ToastUtils.showToast("当前条数为多条");
//                                        Log.d("Anzqmax", "大于2条run且优先级没区别用RSRP比较: "+down1+"--"+down2 );
                                        }

                                    } else {

                                        if (spBeanList.size() > 0 && spBeanList.size() == 1) {
                                            down1 = spBeanList.get(0).getDown();
//                                            spBean2 = spBeanList.get(0);
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value54", down1);
//                                            bundle.putString("sp1up54", spBeanList.get(0).getUp() + "");
//                                            bundle.putString("sp1pci54", spBeanList.get(0).getPci() + "");
//                                            bundle.putString("sp1plmn54", spBeanList.get(0).getPlmn() + "");
//                                            bundle.putString("sp1band54", spBeanList.get(0).getBand() + "");
//                                            bundle.putString("sp1tac54", spBeanList.get(0).getTac() + "");

//                                            bundle.putString("sp1MAX2value54", "");
//                                            bundle.putString("sp2up54", spBean2.getUp() + "");
//                                            bundle.putString("sp2pci54", spBean2.getPci() + "");
//                                            bundle.putString("sp2plmn54", spBean2.getPlmn() + "");
//                                            bundle.putString("sp2band54", spBean2.getBand() + "");
//                                            bundle.putString("sp2tac54", spBean2.getTac() + "");
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 200152;
                                            EventBus.getDefault().post(new MessageEvent(200152, down1));
                                            ToastUtils.showToast("当前条数为1");
                                            Log.d("Anzqmax", "当前条数为1: ");
                                        } else {
//                                            message = new Message();
//                                            bundle.putString("sp1MAX1value54", "");
//                                            bundle.putString("sp1MAX2value54", "");
//                                            message.setData(bundle);
//                                            handler.sendMessage(message);
//                                            message.what = 200152;
                                            EventBus.getDefault().post(new MessageEvent(200152, down1));
                                            ToastUtils.showToast("当前条数为0");
                                            Log.d("Anzqmax", "当前条数为0: ");
                                        }
                                    }
                                }

                            } else if (spBeanList.size() > 0 && spBeanList.size() == 1) {//如果只有一条
                                if (!spBeanList.get(0).getDown().equals("0")) {
                                    String down1 = spBeanList.get(0).getDown();
                                    SpBean spBean2 = spBeanList.get(0);
//                                    message = new Message();
//                                    bundle.putString("sp1MAX1value", down1);
//                                    bundle.putString("sp1up", spBeanList.get(0).getUp() + "");
//                                    bundle.putString("sp1pci", spBeanList.get(0).getPci() + "");
//                                    bundle.putString("sp1plmn", spBeanList.get(0).getPlmn() + "");
//                                    bundle.putString("sp1band", spBeanList.get(0).getBand() + "");
//                                    bundle.putString("sp1tac", spBeanList.get(0).getTac() + "");
//
//                                    bundle.putString("sp1MAX2value", "");
//                                    message.setData(bundle);
//                                    handler.sendMessage(message);
//                                    message.what = 200152;
//                                    ToastUtils.showToast("当前条数为1");
                                    Log.d("Anzqmax", "当前条数为1: ");
                                    EventBus.getDefault().post(new MessageEvent(200152, down1));
                                }
                            } else { //当没有大于1 或者大于2
//                                message = new Message();
//                                bundle.putString("sp1MAX1value", "");
//                                bundle.putString("sp1MAX2value", "");
//                                message.setData(bundle);
//                                handler.sendMessage(message);
//                                message.what = 200152;
//                                ToastUtils.showToast("当前条数为0");
                                EventBus.getDefault().post(new MessageEvent(200152, "down1"));
                                Log.d("Anzqmax", "当前条数为0: ");
                            }
                        }

                    }


                }
            }
        }.start();
//            };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }

    private CallBack callback = null;

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    public static interface CallBack {
        void onDataChanged(String data);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public static List<SpBean> SPBEANLIST1Fragment = new ArrayList<>();
    public static List<SpBean> SPBEANLIST2Fragment = new ArrayList<>();
    public static List<SpBean> SPBEANLIST1 = new ArrayList<>();
    public static List<SpBean> SPBEANLIST2 = new ArrayList<>();
}
