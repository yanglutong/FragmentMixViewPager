package com.lutong.PinConfig;

import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.lutong.OrmSqlLite.Bean.PinConfigBean;
import com.lutong.OrmSqlLite.Bean.Student;
import com.lutong.OrmSqlLite.DBManagerPinConfig;
import com.lutong.PinConfig.Adapter.PinConfigAdapter;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import static com.lutong.AppContext.context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PinConfigActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 频点配置 和增加频点ACT
     *
     * @param
     */
    Button bt_add;
    ImageView iv_no, image_jiahao;
    TextView nullData;
    TextView tv_no;
    //用于底部弹出窗
    private Dialog dialog;
    private View inflate;
    private Button bt_adddilao, bt_canel;
    private ImageView iv_show_finish;
    private EditText et_up_ping, et_down_ping, et_plmn, et_band;
    private String spinnerYY = "";
    private String spinnerTF = "";
    private Spinner sp_yy, sp_tf;
    private TextView titles;
    int sizeinit;//用來判斷是否添加成功的
    /**
     * 数据库操作
     */
    private DBManagerPinConfig dbManager;
    private List<Student> mStudents;
    private List<PinConfigBean> pinConfigBeanList;
    ImageView iv_finish, iv_add;
    /**
     * 频点设置列表适配器
     */
    PinConfigAdapter pinConfigAdapter;
    RecyclerView ry;
    //结束

    PinConfigAdapter.IDialogPinConfig dialogPinConfig = new PinConfigAdapter.IDialogPinConfig() {
        @Override
        public void getPosition(String position, int index, String linename) {

        }

        @Override
        public void deleID(int deleid) {
            queryStudent();
        }

        @Override
        public void up(PinConfigBean pinConfigBeanup) {
//            queryStudent();
            try {
                pinConfigBeanList = dbManager.getStudent();
                sizeinit = pinConfigBeanList.size();
                Log.e("nzqpinConfigBeanList", "queryStudent: " + pinConfigBeanList);
                if (pinConfigBeanList != null) {
                    for (int i = 0; i < pinConfigBeanList.size(); i++) {
                        dbManager.updatecheckFlaseBox(pinConfigBeanList.get(i));
                    }
                }

                dbManager.updatecheckBox(pinConfigBeanup);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            queryStudent();
        }

        @Override
        public void upData() {

        }

        @Override
        public void upbianji(PinConfigBean pinConfigBean) {

            showDialog(true, pinConfigBean);
        }
    };


    protected void initData() {
//        setStatBar();
        iv_add = findViewById(R.id.iv_add);
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setOnClickListener(this);
        nullData = findViewById(R.id.nullData);
        LinearLayout layout = findViewById(R.id.ll_tab);
        TextView title = findViewById(R.id.titles);
        ImageView imageView = findViewById(R.id.iv_menu);
        iv_finish = findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        UtilsView.setViewVisibility(this, layout, title, imageView, getResources().getString(R.string.pin_activity2), false, iv_finish, true);
        findview();
        //初始化DBManager
        try {
            dbManager = new DBManagerPinConfig(this);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //查询所以数据
        queryStudent();


    }

    private void savepin() {
        PinConfigBean pinConfigBean = new PinConfigBean();
        pinConfigBean.setBand(Integer.parseInt(et_band.getText().toString()));
        pinConfigBean.setDown(Integer.parseInt(et_down_ping.getText().toString()));
        pinConfigBean.setPlmn(et_plmn.getText().toString());
        pinConfigBean.setType(0);
        pinConfigBean.setUp(Integer.parseInt(et_up_ping.getText().toString()));
        pinConfigBean.setTf(spinnerTF);
        pinConfigBean.setYy(spinnerYY);
        try {
            int i = dbManager.insertStudent(pinConfigBean);
            if (i == 1) {
                Log.d("nan保存的数据", "===" + pinConfigBean);
//                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("添加成功");
                dialog.dismiss();
                //查询所以数据
                initData();
            } else {
//                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("添加失败");
            }
//
        } catch (java.sql.SQLException e) {

            e.printStackTrace();
        }
    }

    public void queryStudent() {
        try {
            pinConfigBeanList = dbManager.getStudent();
            sizeinit = pinConfigBeanList.size();
            Log.e("nzqpinConfigBeanList", "queryStudent: " + pinConfigBeanList);
//            myAdapte.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        if (pinConfigBeanList != null) {
            int size = pinConfigBeanList.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < pinConfigBeanList.size(); i++) {
                list.add(i);
            }
            pinConfigAdapter = new PinConfigAdapter(this, pinConfigBeanList, dialogPinConfig, list);

            Log.e("nzq", "queryStudentsize: ." + pinConfigBeanList.size());
//            ry.setFocusableInTouchMode(false);
//            ry.setFocusable(false);
//            ry.setHasFixedSize(true);
//            ry.setFocusableInTouchMode(false);
//            ry.setFocusableInTouchMode(false);
//            ry.setFocusable(false);
            ry.setAdapter(pinConfigAdapter);
//            ry.setFocusableInTouchMode(false);
//            ry.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                    Log.e("nzq","scrollStateChanged");
//                }
//
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                }
//            });
            tv_no.setVisibility(View.GONE);
            iv_no.setVisibility(View.GONE);
        } else {
            tv_no.setVisibility(View.VISIBLE);
            iv_no.setVisibility(View.VISIBLE);
            ry.setVisibility(View.GONE);
        }
//        Log.e("nzq", "queryStudent: " + pinConfigBeanList.size());
    }

    private void findview() {
        bt_add = findViewById(R.id.bt_add);
        bt_add.setVisibility(View.GONE);
        bt_add.setOnClickListener(this);

        tv_no = findViewById(R.id.tv_no);
        iv_no = findViewById(R.id.iv_no);
        ry = findViewById(R.id.ry);
        ry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    protected void initQx() {

    }

    @Override
    protected void init() {
        initData();
        Intent intent = getIntent();
        String down = "";
        down = intent.getStringExtra("down");
        String band = "";
        String pci = "";
        String plmn = "";
        String priority = "";
        String rsrp = "";
        String tac = "";
        String up = "";
        String cid = "";

        if (!TextUtils.isEmpty(down)) {
            band = intent.getStringExtra("band");
            pci = intent.getStringExtra("pci");
            plmn = intent.getStringExtra("plmn");
            priority = intent.getStringExtra("priority");
            rsrp = intent.getStringExtra("rsrp");
            tac = intent.getStringExtra("tac");
            up = intent.getStringExtra("up");
            cid = intent.getStringExtra("cid");
            //
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow, null);
            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            bt_adddilao.setOnClickListener(this);
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_up_ping.setText(up);

            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            et_down_ping.setText(down);
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD   下拉框
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
            et_plmn.setText(plmn);
            et_band = inflate.findViewById(R.id.et_band);//波段
            String band1 = getBand(Integer.parseInt(down));
            et_band.setText(band1);
//            LinearLayout lla = inflate.findViewById(R.id.llA);
////            lla.setVisibility(View.VISIBLE);
//            LinearLayout llb = inflate.findViewById(R.id.llB);
////            llb.setVisibility(View.VISIBLE);
//            EditText et_pci = inflate.findViewById(R.id.et_pci);
//            if (pci.equals(0)){
//                et_pci.setText("-");
//            }else {
//                et_pci.setText(pci);
//            }
//
//            EditText et_priority = inflate.findViewById(R.id.et_priority);
//            if (priority.equals("0")){
//                et_priority.setText("-");
//            }else {
//                et_priority.setText(priority);
//            }
//
//            EditText et_tac = inflate.findViewById(R.id.et_tac);
//            if (tac.equals("0")){
//                et_tac.setText("-");
//            }else {
//                et_tac.setText(tac);
//            }
//
//
//            EditText et_rsrq = inflate.findViewById(R.id.et_rsrq);
//            if (rsrp.equals("0")){
//                et_rsrq.setText("-");
//            }else {
//                Integer.parseInt(rsrp);
//                et_rsrq.setText(rsrp);
//            }

            sp_yy = inflate.findViewById(R.id.sp_yy);//运营商   下拉框
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(this);
            List<String> list1 = new ArrayList<>();
            list1.add("移动");
            list1.add("联通");
            list1.add("电信");
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list1);
            sp_yy.setAdapter(adapter1);
            sp_yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerYY = adapter1.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            List<String> list2 = new ArrayList<>();
            list2.add("TDD");
            list2.add("FDD");
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list2);
            sp_tf.setAdapter(adapter2);
            sp_tf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerTF = adapter2.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            dialog.setCanceledOnTouchOutside(false);
            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
//            ToastUtils.showToast("" + down);

        } else {

        }
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected int getView() {
        return R.layout.activity_pin_config;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add://Activity添加button
//                Toast.makeText(PinConfigActivity.this, "你点击了配置新频点", Toast.LENGTH_SHORT).show();
//                PinConfigBean pinConfigBean = new PinConfigBean();
////                showDialog(false, pinConfigBean);
                break;

            case R.id.iv_add:
                PinConfigBean pinConfigBean = new PinConfigBean();
                showDialog(false, pinConfigBean);
                break;
            case R.id.iv_finish:
                finish();
                break;
            case R.id.bt_adddilao:
                //底部弹出窗确认添加按钮
                ViewisEmpty();
                break;
            case R.id.iv_show_finish:
                dialog.dismiss();
                break;
            case R.id.bt_canel:
                dialog.cancel();
                break;

        }
    }

    /**
     * Edit非空判断
     */
    private void ViewisEmpty() {
        if (isnull()) return;
        //配置新频点数据插入 SQL
        Log.d("iiii", "ViewisEmpty: ");
        String s = et_down_ping.getText().toString();
        String sr = "";
        try {
            List<PinConfigBean> studentlist = dbManager.getStudent();
            if (studentlist.size()>0&&studentlist!=null){
                for (int i = 0; i < studentlist.size(); i++) {
                    if (s.equals(studentlist.get(i).getDown()+"")) {
                        sr = "1";
                        break;
                    }

                }
                if (!TextUtils.isEmpty(sr)) {
                    ToastUtils.showToast("频点重复");
                    return;
                } else {
                    exeSql();
                }
            }else {
                exeSql();
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }




    }

    private boolean isnull() {
        if (TextUtils.isEmpty(et_up_ping.getText().toString())) {
            Toast.makeText(this, "上行频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(et_down_ping.getText().toString())) {
            Toast.makeText(this, "下行频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

//        if (TextUtils.isEmpty(et_tf.getText().toString())) {
//            Toast.makeText(this, "TDD/FDD不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if (TextUtils.isEmpty(et_band.getText().toString())) {
            Toast.makeText(this, "频段不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(et_plmn.getText().toString())) {
            Toast.makeText(this, "PLMN不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 执行sql语句
     */
    private void exeSql() {

        savepin();
    }


    /**
     * 底部弹出窗
     */
    private void showDialog(boolean b, PinConfigBean pinConfigBean) {
        if (b) {
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
//            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow, null);
//            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            titles = inflate.findViewById(R.id.titles);
            titles.setText("配置频点");
            bt_adddilao.setText("确认");
            bt_adddilao.setOnClickListener(new MySetUpdate(pinConfigBean, pinConfigBean.getId(), pinConfigBean.getType()));
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_up_ping.setText(pinConfigBean.getUp() + "");
            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            et_down_ping.setText(pinConfigBean.getDown() + "");
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
            et_plmn.setText(pinConfigBean.getPlmn());
            et_band = inflate.findViewById(R.id.et_band);//波段
            et_band.setText(pinConfigBean.getBand() + "");
            sp_yy = inflate.findViewById(R.id.sp_yy);
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(this);
            String yy = pinConfigBean.getYy();
            Log.d("pinConfigBeanaa", "showDialog: " + yy);
            List<String> list1 = new ArrayList<>();
            list1.add("移动");
            list1.add("联通");
            list1.add("电信");
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item
                    , list1);


            sp_yy.setAdapter(adapter1);
//            sp_yy.setSelection(2);
            if (!TextUtils.isEmpty(yy)) {
                if (yy.equals("移动")) {
                    sp_yy.setSelection(0);
                }
                if (yy.equals("联通")) {
                    sp_yy.setSelection(1);
                }
                if (yy.equals("电信")) {
                    sp_yy.setSelection(2);
                }
            }
            sp_yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerYY = adapter1.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            String tf = pinConfigBean.getTf();
            Log.d("pinConfigBeanaa", "showDialog: " + tf);
            List<String> list2 = new ArrayList<>();
            list2.add("TDD");
            list2.add("FDD");
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item
                    , list2);


            sp_tf.setAdapter(adapter2);
//            sp_yy.setSelection(2);
            if (!TextUtils.isEmpty(tf)) {
                if (yy.equals("TDD")) {
                    sp_tf.setSelection(0);
                }
                if (tf.equals("FDD")) {
                    sp_tf.setSelection(1);
                }
            }
            sp_tf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerTF = adapter2.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            dialog.setCanceledOnTouchOutside(false);
////            et_yy.setText(pinConfigBean.getYy());
//            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        } else {
            dialog = new Dialog(this, R.style.ActionSheetDialogStyleDialog);
            //填充对话框的布局
            inflate = LayoutInflater.from(this).inflate(R.layout.configlist_dibushow, null);
            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            bt_adddilao.setOnClickListener(this);
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD   下拉框
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
            et_band = inflate.findViewById(R.id.et_band);//波段
            sp_yy = inflate.findViewById(R.id.sp_yy);//运营商   下拉框
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(this);
            List<String> list1 = new ArrayList<>();
            list1.add("移动");
            list1.add("联通");
            list1.add("电信");
            final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list1);
            sp_yy.setAdapter(adapter1);
            sp_yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerYY = adapter1.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            List<String> list2 = new ArrayList<>();
            list2.add("TDD");
            list2.add("FDD");
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(context, R.layout.spinner_item
                    , list2);
            sp_tf.setAdapter(adapter2);
            sp_tf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerTF = adapter2.getItem(i);//下拉框1选择的数据
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            dialog.setCanceledOnTouchOutside(false);
            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        }

    }

    class MySetUpdate implements View.OnClickListener {
        private int type;
        private int id;
        PinConfigBean pinConfigBean;

        public MySetUpdate(PinConfigBean pinConfigBean, int id, int type) {
            this.pinConfigBean = pinConfigBean;
            this.id = id;
            this.type = type;
        }

        @Override
        public void onClick(View view) {
            if (isnull()) return;
            PinConfigBean pinConfigBeanss = new PinConfigBean();
            pinConfigBeanss.setUp(Integer.parseInt(et_up_ping.getText().toString()));
            pinConfigBeanss.setDown(Integer.parseInt(et_down_ping.getText().toString()));
            pinConfigBeanss.setTf(spinnerTF);
            pinConfigBeanss.setPlmn(et_plmn.getText().toString());
            pinConfigBeanss.setBand(Integer.parseInt(et_band.getText().toString()));
            pinConfigBeanss.setId(id);
            pinConfigBeanss.setType(type);
            pinConfigBeanss.setYy(spinnerYY);
            try {
                int i = dbManager.updateData(pinConfigBeanss);
                Log.d("nzq", "updateDataonClick: " + i);
                queryStudent();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }
    public static String getBand(int down) {
        String BAND = "1";

        if (down >= 0 && down <= 599) {
            BAND = "1";
            return BAND;
        }

        if (down >= 1200 && down <= 1949) {
            BAND = "3";
            return BAND;
        }

        if (down >= 2400 && down <= 2649) {
            BAND = "5";
            return BAND;
        }


        if (down >= 3450 && down <= 3799) {
            BAND = "8";
            return BAND;
            //FDD
        }

        if (down >= 36200 && down <= 36349) {
            BAND = "34";
            return BAND;
        }
        if (down >= 37750 && down <= 38249) {
            BAND = "38";
            return BAND;
        }
        if (down >= 38250 && down <= 38649) {
            BAND = "39";
            return BAND;
        }
        if (down >= 38650 && down <= 39649) {
            BAND = "40";
            return BAND;
        }
        if (down >= 39650 && down <= 41589) {
            BAND = "41";
            return BAND;
        }
        return BAND;

    }
}
