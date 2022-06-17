package com.lutong.activity;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.lutong.OrmSqlLite.Bean.ZcBean;
import com.lutong.OrmSqlLite.DBManagerZX;
import com.lutong.R;
import com.lutong.Utils.ACacheUtil;
import com.lutong.Utils.MyUtils;
import com.lutong.Utils.ToastUtils;
import com.lutong.base.BaseActivity;


import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfigsActivity extends BaseActivity {
    TextView titles, tv_time;
    ImageView iv_finish;
    EditText ed_zc;
    Button button, bt_cancel;

    @Override
    protected void initQx() {

    }

    @Override
    protected void init() throws UnsupportedEncodingException {
        titles.setText("配置权限");
        iv_finish.setVisibility(View.VISIBLE);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ed_zc.setText(MyUtils.getZC());
        DBManagerZX dbManagerZX = null;
        try {
            dbManagerZX = new DBManagerZX(ConfigsActivity.this);
            ZcBean forid = dbManagerZX.forid(1);
            String date = forid.getDate();
            tv_time.setText(date + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TYPE_ALL–年、月、日、星期、时、分
//                TYPE_YMDHM–年、月、日、时、分
//                TYPE_YMDH–年、月、日、时
//                TYPE_YMD–年、月、日
//                TYPE_HM–时、分
                DatePickDialog dialog = new DatePickDialog(ConfigsActivity.this);
                //设置上下年分限制
                dialog.setYearLimt(100);
//                dialog.setStartDate();
                //设置标题
                dialog.setTitle("使用时间设置");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        tv_time.setText(sdf.format(date));
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        tv_time.setText(sdf.format(date));
                    }
                });
                dialog.show();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String zcstr = ed_zc.getText().toString();
                String zctime = tv_time.getText().toString();
//                String macAddress = DeviceUtils.getMacAddress();//获取mac
                String bjzcm = "";
//                if (!TextUtils.isEmpty(macAddress)) {
//                    String replace = macAddress.replace(":", "");
//                    String substring1 = replace.substring(0, 2);
//                    String substring2 = replace.substring(2, 4);
//                    String substring3 = replace.substring(4, 6);
//                    String substring4 = replace.substring(6, 8);
//                    String substring5 = replace.substring(8, 10);
//                    String substring6 = replace.substring(10, 12);
//                    BigInteger bigint1 = new BigInteger(substring1, 16);
//                    int numb1 = bigint1.intValue();
//                    BigInteger bigint2 = new BigInteger(substring2, 16);
//                    int numb2 = bigint2.intValue();
//                    BigInteger bigint3 = new BigInteger(substring3, 16);
//                    int numb3 = bigint3.intValue();
//                    BigInteger bigint4 = new BigInteger(substring4, 16);
//                    int numb4 = bigint4.intValue();
//                    BigInteger bigint5 = new BigInteger(substring5, 16);
//                    int numb5 = bigint5.intValue();
//                    BigInteger bigint6 = new BigInteger(substring6, 16);
//                    int numb6 = bigint6.intValue();
//                    Log.d("10进制", "setUser_pwd: " + numb1 + "--" + numb2 + "--" + numb3 + "--" + numb4 + "--" + numb5 + "--" + numb6);
//                    int el = numb1 + numb2 + numb3 + numb4 + numb5;//和
//                    int d1 = (el - numb1) % 10;
//                    int d2 = (el - numb2) % 10;
//                    int d3 = (el - numb3) % 10;
//                    int d4 = (el - numb4) % 10;
//                    int d5 = (el - numb5) % 10;
//                    int d6 = (el - numb6) % 10;
//                    Log.d("10进制加前五位", "setUser_pwd: " + d1 + "" + d2 + "" + d3 + "" + d4 + "" + d5 + "" + d6);
//                    bjzcm = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
//                    ed_zc.setText(bjzcm);
//                } else {
//                    ToastUtils.showToast("获取设备识别码失败");
//                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//
//                    String uniqueID = UUID.randomUUID().toString();
//                    String replace = uniqueID.replace(":", "");
//                    String replace2 = uniqueID.replace("-", "");
//                    String substring1 = replace2.substring(0, 2);
//                    String substring2 = replace2.substring(2, 4);
//                    String substring3 = replace2.substring(4, 6);
//                    String substring4 = replace2.substring(6, 8);
//                    String substring5 = replace2.substring(8, 10);
//                    String substring6 = replace2.substring(10, 12);
//                    BigInteger bigint1 = new BigInteger(substring1, 16);
//                    int numb1 = bigint1.intValue();
//                    BigInteger bigint2 = new BigInteger(substring2, 16);
//                    int numb2 = bigint2.intValue();
//                    BigInteger bigint3 = new BigInteger(substring3, 16);
//                    int numb3 = bigint3.intValue();
//                    BigInteger bigint4 = new BigInteger(substring4, 16);
//                    int numb4 = bigint4.intValue();
//                    BigInteger bigint5 = new BigInteger(substring5, 16);
//                    int numb5 = bigint5.intValue();
//                    BigInteger bigint6 = new BigInteger(substring6, 16);
//                    int numb6 = bigint6.intValue();
//                    Log.d("10进制", "setUser_pwd: " + numb1 + "--" + numb2 + "--" + numb3 + "--" + numb4 + "--" + numb5 + "--" + numb6);
//                    int el = numb1 + numb2 + numb3 + numb4 + numb5;//和
//                    int d1 = (el - numb1) % 10;
//                    int d2 = (el - numb2) % 10;
//                    int d3 = (el - numb3) % 10;
//                    int d4 = (el - numb4) % 10;
//                    int d5 = (el - numb5) % 10;
//                    int d6 = (el - numb6) % 10;
//                    Log.d("10进制加前五位", "setUser_pwd: " + d1 + "" + d2 + "" + d3 + "" + d4 + "" + d5 + "" + d6);
////                    return String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
//                    bjzcm = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
//                    ed_zc.setText(bjzcm);
//                } else {
//
//                    String macAddress = DeviceUtils.getMacAddress();//获取mac
//                    String replace = macAddress.replace(":", "");
//                    String substring1 = replace.substring(0, 2);
//                    String substring2 = replace.substring(2, 4);
//                    String substring3 = replace.substring(4, 6);
//                    String substring4 = replace.substring(6, 8);
//                    String substring5 = replace.substring(8, 10);
//                    String substring6 = replace.substring(10, 12);
//                    BigInteger bigint1 = new BigInteger(substring1, 16);
//                    int numb1 = bigint1.intValue();
//                    BigInteger bigint2 = new BigInteger(substring2, 16);
//                    int numb2 = bigint2.intValue();
//                    BigInteger bigint3 = new BigInteger(substring3, 16);
//                    int numb3 = bigint3.intValue();
//                    BigInteger bigint4 = new BigInteger(substring4, 16);
//                    int numb4 = bigint4.intValue();
//                    BigInteger bigint5 = new BigInteger(substring5, 16);
//                    int numb5 = bigint5.intValue();
//                    BigInteger bigint6 = new BigInteger(substring6, 16);
//                    int numb6 = bigint6.intValue();
//                    Log.d("10进制", "setUser_pwd: " + numb1 + "--" + numb2 + "--" + numb3 + "--" + numb4 + "--" + numb5 + "--" + numb6);
//                    int el = numb1 + numb2 + numb3 + numb4 + numb5;//和
//                    int d1 = (el - numb1) % 10;
//                    int d2 = (el - numb2) % 10;
//                    int d3 = (el - numb3) % 10;
//                    int d4 = (el - numb4) % 10;
//                    int d5 = (el - numb5) % 10;
//                    int d6 = (el - numb6) % 10;
//                    Log.d("10进制加前五位", "setUser_pwd: " + d1 + "" + d2 + "" + d3 + "" + d4 + "" + d5 + "" + d6);
////                    return String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
//                    bjzcm = String.valueOf(d1) + String.valueOf(d2) + String.valueOf(d3) + String.valueOf(d4) + String.valueOf(d5) + String.valueOf(d6);
//                    ed_zc.setText(bjzcm);
//                }
                if (!TextUtils.isEmpty(zcstr)) {


                } else {
                    ToastUtils.showToast("注册码不能为空");
                    return;
                }
                if (!TextUtils.isEmpty(zctime)) {

                } else {
                    ToastUtils.showToast("使用日期不能为空");
                    return;
                }
                //
//                if (zcstr.equals(bjzcm)) {
                    ToastUtils.showToast("注册码有效,保存成功");
                    DBManagerZX dbManagerZX = null;
                    try {
                        dbManagerZX = new DBManagerZX(ConfigsActivity.this);
                        ZcBean zcBean = new ZcBean();
                        zcBean.setId(1);
                        zcBean.setZhucema(ed_zc.getText().toString());
                        zcBean.setDate(tv_time.getText().toString());
                        dbManagerZX.updateConmmunit01Bean(zcBean);
                        //当前时间
                        ZcBean zcBean2 = new ZcBean();
                        zcBean2.setId(2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        zcBean2.setDate(simpleDateFormat.format(new Date()));
                        dbManagerZX.updateConmmunit01Bean(zcBean2);
                        ZcBean forid = dbManagerZX.forid(1);
                        Log.d("aaaa", "onClick: " + forid);
                        ACacheUtil.putsbSpTYPE(sbsptype);
//                        finish();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//                } else {
//                    ToastUtils.showToast("注册码无效");
//                    return;
//                }
            }
        });
    }

    String sbsptype = "";

    @Override
    protected void findViews() {
        titles = findViewById(R.id.titles);
        iv_finish = findViewById(R.id.iv_finish);
        ed_zc = findViewById(R.id.ed_zc);
        tv_time = findViewById(R.id.tv_time);
        button = findViewById(R.id.button);
        bt_cancel = findViewById(R.id.bt_cancel);
        iv_add = findViewById(R.id.iv_add);
        iv_add.setVisibility(View.GONE);
        iv_menu = findViewById(R.id.iv_menu);
        iv_menu.setVisibility(View.GONE);
        sp1 = findViewById(R.id.sp1);
        List<String> list1 = new ArrayList<>();
        list1.add("4G(双载波)设备");
        list1.add("5G(双载波)4G(单载波)设备");
        list1.add("5G(双载波)4G(双载波)设备");
        list1.add("5G(双载波)设备");
        list1.add("4G(单载波)5G(单载波)设备");
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item
                , list1);

        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sbsptype = adapter1.getItem(i);//下拉框1选择的数据

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_configs;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    ImageView iv_add, iv_menu;
    Spinner sp1;
}
