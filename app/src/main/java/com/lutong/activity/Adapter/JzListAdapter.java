package com.lutong.activity.Adapter;

import static com.baidu.mapapi.utils.CoordinateConverter.CoordType.BD09LL;
import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;

import com.lutong.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.lutong.OrmSqlLite.DBManagerJZ;
import com.lutong.R;
import com.lutong.Utils.MyToast;
import com.lutong.activity.PanoramaDemoActivityMain;
import com.lutong.activity.TaActivity;
import com.mylhyl.circledialog.CircleDialog;

import java.sql.SQLException;
import java.util.List;

/**
 * show-站
 */

public class JzListAdapter extends RecyclerView.Adapter<JzListAdapter.MyViewHolder> {


    private  List<Integer> listnum;
    private  int size;
    private JzListCallBack callBack;
    private DBManagerJZ dbManagerJZ;
    private LatLng mylag;
    private List<GuijiViewBeanjizhan> list;
    private Context mcontext;
    int num = 00;

    public JzListAdapter(Context mcontext, List<GuijiViewBeanjizhan> list, LatLng mylag, DBManagerJZ dbManagerJZ, JzListCallBack callBack, List<Integer> listnum ) {
        this.mcontext = mcontext;
        this.list = list;
        this.mylag = mylag;
        this.dbManagerJZ = dbManagerJZ;
        this.callBack = callBack;
        this. listnum= listnum;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_map_info_list2, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {

//        myViewHolder.title.setText(list.get(i).getAddress() + "");
        myViewHolder.title_num.setText("第" +listnum.get(i)  + "" + "条");//设置条数
        String str = "";
        if ("0".equals(list.get(i).getMnc())) {
            str = "移动";
        } else if ("1".equals(list.get(i).getMnc())) {
            str = "联通";
        } else if ("11".equals(list.get(i).getMnc())) {
            str = "电信";
        } else if (TextUtils.isEmpty(list.get(i).getMnc())){//如果是cdma显示 sid数据
            str = "";
        }else {
            str = "CDMA";
        }
        if (list.get(i).getResources().equals("内部数据")){
            myViewHolder.  ll_types.setVisibility(View.VISIBLE);
            myViewHolder.   ll_pci.setVisibility(View.VISIBLE);
            myViewHolder.    ll_band.setVisibility(View.VISIBLE);
            myViewHolder.    ll_down.setVisibility(View.VISIBLE);

            myViewHolder.    tv_band.setText(list.get(i).getBand()+"");
            myViewHolder.  tv_types.setText(list.get(i).getTypes()+"");
            myViewHolder.  tv_pci.setText(list.get(i).getPci()+"");
            myViewHolder.  tv_down.setText(list.get(i).getDown()+"");
        }

        myViewHolder.tv_title.setText(str);//设置运营商
//        myViewHolder.tv_fugai.setText(list.get(i).getRadius());//设置覆盖范围
//        myViewHolder.tv_mnc.setText(list.get(i).getMnc());
        myViewHolder.tv_lac.setText(list.get(i).getLac());
        myViewHolder.tv_cid.setText(list.get(i).getCi());
        myViewHolder.tv_address.setText(list.get(i).getAddress());
        DecimalFormat df = new DecimalFormat("#.000000");
        String lat = df.format(Double.parseDouble(list.get(i).getLat()));
        String lon = df.format(Double.parseDouble(list.get(i).getLon()));
        myViewHolder.tv_lat_lon.setText(lat);
        myViewHolder.tv_lat_lon2.setText(lon);
//        myViewHolder.tv_resources.setText(list.get(i).getResources() + "");
        //点击事件处理
        start_lication(myViewHolder, i);//地图点击事件
        start_qj(myViewHolder, i);//跳转到全景地图
        start_default(myViewHolder, i);//设为默认地址
        if (list.get(i).getType() == 1) {
            myViewHolder.bt_m_location.setBackground(mcontext.getResources().getDrawable(R.mipmap.baojing_down));
        }
        start_dele(myViewHolder, i);//删除事件
        start_taset(myViewHolder, i);//跳转到ta值设置
        myViewHolder.bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBack.showCALL(list.get(i).getId(), list.get(i).getLat(), list.get(i).getLon());
            }
        });



    }

    private void start_taset(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bt_taset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(i).getId() + "");
                Intent intent = new Intent(mcontext, TaActivity.class);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
    }

    private void start_dele(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bt_m_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CircleDialog.Builder((FragmentActivity) mcontext)
                        .setTitle("")
                        .setWidth(0.7f)
                        .setMaxHeight(0.7f)
                        .setText("确定要删除基站吗")
                        .setTitleColor(Color.parseColor("#00acff"))
                        .setNegative("确定", new myclic(3, list.get(i).getId()))
                        .setPositive("取消", null)
                        .show();
            }
        });
    }

    private void start_default(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bt_m_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CircleDialog.Builder((FragmentActivity) mcontext)
                        .setTitle("")
                        .setText("确定要设为报警基站吗")
                        .setTitleColor(Color.parseColor("#00acff"))
                        .setNegative("确定", new myclic(4, list.get(i).getId()))
                        .setPositive("取消", null)
                        .show();
            }
        });
    }

    private void start_qj(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bt_quanjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, PanoramaDemoActivityMain.class);
                Bundle bundle = new Bundle();
                bundle.putString("lat", list.get(i).getLat());
                bundle.putString("lon", list.get(i).getLon());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void start_lication(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.bt_openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //底部弹出窗

                final Dialog dialog2 = new Dialog(mcontext, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                View inflate = LayoutInflater.from(mcontext).inflate(R.layout.item_dibushowdaohao, null);
                //初始化控件
                TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
//        choosePhoto.setVisibility(View.GONE);
                TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
//                            baidu.setOnClickListener(new MyonclickXian(mylag));
                baidu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            LatLng startLatLng = new LatLng(39.940387, 116.29446);
                            LatLng endLatLng = new LatLng(39.87397, 116.529025);
                            String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
                                            "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
                                    list.get(i).getLat(), list.get(i).getLon());
//                            String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
//                                            "%s,%s&mode=driving&src=com.34xian.demo", startLatLng.latitude, startLatLng.longitude,
//                                    list.get(i).getLat(), list.get(i).getLon());
                            Intent intent = new Intent();
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
//                    ToastUtil.showShort(this, "请安装百度地图");
//                            Toast.makeText(mcontext, "请安装百度地图", Toast.LENGTH_SHORT).show();
                            MyToast.showToast("请先安装百度地图");
                        }
                    }
                });
                gaode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
//                    double gdLatitude = 39.92848272
//                    double gdLongitude = 116.39560823
                            //初始化坐标转换工具类，设置源坐标类型和坐标数据
                            //初始化坐标转换工具类，设置源坐标类型和坐标数据
                            LatLng latLngs = new LatLng(Double.parseDouble(list.get(i).getLat()), Double.parseDouble(list.get(i).getLon()));
                            CoordinateConverter converter = new CoordinateConverter()
                                    .from(BD09LL)
                                    .coord(latLngs);

//转换坐标
                            LatLng desLatLng = converter.convert();

                            String uri = String.format("amapuri://route/plan/?dlat=%s&dlon=%s&dname=终点&dev=0&t=0",
                                    desLatLng.latitude, desLatLng.longitude);
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            intent.setData(Uri.parse(uri));
                            intent.setPackage("com.autonavi.minimap");
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
//                            Toast.makeText(mcontext, "请安装高德地图", Toast.LENGTH_SHORT).show();
                            MyToast.showToast("请先安装高德地图");
                        }

                        dialog2.dismiss();
                    }
                });
                //将布局设置给Dialog
                dialog2.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog2.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.BOTTOM);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog2.show();//显示对话框
            }
        });
    }

    @Override
    public int getItemCount() {
//        return pdfBean.getList().size();
        return list.size();
    }

    class myclic implements View.OnClickListener {
        private int id;
        int i;

        public myclic(int i) {
            this.i = i;
        }

        public myclic(int i, int id) {
            this.i = i;
            this.id = id;
        }

        @Override
        public void onClick(View view) {
            if (i == 3) {
                callBack.callDele(id);
            }
            if (i == 4) {
                try {
                    GuijiViewBeanjizhan guijiViewBeanjizhan = dbManagerJZ.forid(id);
                    guijiViewBeanjizhan.setType(1);
                    int is = dbManagerJZ.updateType(guijiViewBeanjizhan);
                    if (is == 1) {
//                        Toast.makeText(mcontext, "设置默认成功", Toast.LENGTH_LONG).show();
                        MyToast.showToast("设置默认成功");
                        callBack.call();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title_num, tv_title, tv_fugai, tv_mnc, tv_lac, tv_cid, tv_address, tv_lat_lon, tv_lat_lon2, tv_resources,
        tv_band,tv_types,tv_pci,tv_down;
        LinearLayout ll_types,ll_pci,ll_band,ll_down;
        ImageButton bt_openMap;
        Button bt_quanjing, bt_m_location, bt_m_dele, bt_taset, bt_show;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_num = itemView.findViewById(R.id.title_num);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_fugai = itemView.findViewById(R.id.tv_fugai);
            tv_mnc = itemView.findViewById(R.id.tv_mnc);
            tv_lac = itemView.findViewById(R.id.tv_lac);
            tv_cid = itemView.findViewById(R.id.tv_cid);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_lat_lon = itemView.findViewById(R.id.tv_lat_lon);
            tv_lat_lon2 = itemView.findViewById(R.id.tv_lat_lon2);
            tv_resources = itemView.findViewById(R.id.tv_resources);

            tv_band = itemView.findViewById(R.id.tv_band);
            tv_types = itemView.findViewById(R.id.tv_types);
            tv_pci = itemView.findViewById(R.id.tv_pci);
            tv_down = itemView.findViewById(R.id.tv_down);

            ll_types = itemView.findViewById(R.id.ll_types);
            ll_pci = itemView.findViewById(R.id.ll_pci);
            ll_band = itemView.findViewById(R.id.ll_band);
            ll_down = itemView.findViewById(R.id.ll_down);



            //点击事件id
            bt_openMap = itemView.findViewById(R.id.bt_openMap);
            bt_quanjing = itemView.findViewById(R.id.bt_quanjing);
            bt_m_location = itemView.findViewById(R.id.bt_m_location);
            bt_m_dele = itemView.findViewById(R.id.bt_m_dele);
            bt_taset = itemView.findViewById(R.id.bt_taset);
            bt_show = itemView.findViewById(R.id.bt_show);
        }
    }

    public interface JzListCallBack {
        void call();

        void callDele(int id);

        void showCALL(int id, String lat, String lon);
    }
}
