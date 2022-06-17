package com.lutong.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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


import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UpdataParamActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_findish;
    EditText ed_phone, ed_yy, ed_name, ed_imsi;
    Button button, bt_cancel;
    DBManagerAddParam dbManagerAddParam = null;
    String id = "";
    Spinner sp1;
    ArrayAdapter adapter1;
    private String spinnerS1 = "";
    ImageView ivadd;
    @Override
    protected void initQx() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void findViews() {
        ivadd=findViewById(R.id.iv_add);
        ivadd.setVisibility(View.GONE);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sp1 = findViewById(R.id.sp1);
        List<String> list = new ArrayList<>();
        list.add("移动");
        list.add("联通");
        list.add("电信");
        adapter1 = new ArrayAdapter<String>(this
                , R.layout.spinner_item
                , list);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerS1 = (String) adapter1.getItem(i);//下拉框1选择的数据
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        LinearLayout layout = findViewById(R.id.ll_tab);
        TextView title = findViewById(R.id.titles);
        ImageView imageView = findViewById(R.id.iv_menu);
        iv_findish = findViewById(R.id.iv_finish);
        iv_findish.setOnClickListener(this);
        UtilsView.setViewVisibility(this, layout, title, imageView, getResources().getString(R.string.upparam_activity), false, iv_findish, true);
        //用到的组件View
        ed_phone = findViewById(R.id.ed_phone);
        ed_imsi = findViewById(R.id.ed_imsi);
        ed_name = findViewById(R.id.ed_name);
        ed_yy = findViewById(R.id.ed_yy);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            try {
                dbManagerAddParam = new DBManagerAddParam(UpdataParamActivity.this);
                AddPararBean foriddata = dbManagerAddParam.forid(Integer.parseInt(id));
                ed_phone.setText(foriddata.getPhone());
                ed_imsi.setText(foriddata.getImsi());
                ed_name.setText(foriddata.getName());
                if (foriddata.getYy().equals("移动")) {
                    sp1.setSelection(0);
                }
                if (foriddata.getYy().equals("联通")) {
                    sp1.setSelection(1);
                }
                if (foriddata.getYy().equals("电信")) {
                    sp1.setSelection(2);
                }
                ed_yy.setText(foriddata.getYy());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected int getView() {
        return R.layout.activity_add_param;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.button:
                IsImpty();
                break;
        }
    }

    private void IsImpty() {
        List<AddPararBean> addPararBeans = null;
        try {
            addPararBeans = dbManagerAddParam.getDataAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("AddPararBean", "insertData: " + addPararBeans);
//        if (TextUtils.isEmpty(ed_name.getText().toString())) {
//            ToastUtils.showToast("名称不能为空");
//            return;
//        }
//        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
//            ToastUtils.showToast("电话不能为空");
//            return;
//        }
//        if (!RegexUtils.isMobileExact(ed_phone.getText().toString())) {
//            ToastUtils.showToast("请输入正确的手机号");
//            return;
//        }

        if (TextUtils.isEmpty(ed_imsi.getText().toString())) {
            ToastUtils.showToast("IMSI不能为空");
            return;
        }
        if (ed_imsi.getText().toString().length() != 15) {
            ToastUtils.showToast("请输入15位IMSI号");
            return;
        }
        if (TextUtils.isEmpty(ed_yy.getText().toString())) {
            ToastUtils.showToast("运营商不能为空");
            return;
        }
//        new CircleDialog.Builder()
//                .setTitle("")
//                .setText("确定要修改IMSI管理吗")
//                .setTitleColor(Color.parseColor("#00acff"))
//                .setNegative("确定", new Positiv())
//                .setPositive("取消", null)
//                .show(getSupportFragmentManager());


        final Dialog dialog = new Dialog(UpdataParamActivity.this, R.style.menuDialogStyleDialogStyle);
        View inflate = LayoutInflater.from(UpdataParamActivity.this).inflate(R.layout.dialog_item_title, null);
        TextView tv_title = inflate.findViewById(R.id.tv_title);
        tv_title.setText("确定要修改目标IMSI吗?");
        Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(new Positiv(dialog));
        Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.cancel();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//设置Dialog距离底部的距离
//                          将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

    }

    private void uptaData() throws SQLException {
        AddPararBean addPararBean = dbManagerAddParam.forid(Integer.parseInt(id));
        addPararBean.setImsi(ed_imsi.getText().toString());
        addPararBean.setName(ed_name.getText().toString());
        addPararBean.setPhone(ed_phone.getText().toString());
        addPararBean.setYy(spinnerS1);
        int i = dbManagerAddParam.updateCheck2(addPararBean);

        if (i == 1) {
            ToastUtils.showToast("修改目标IMSI成功");
            finish();
        } else {
            ToastUtils.showToast("修改目标IMSI失败");

        }
        List<AddPararBean> addPararBeans = dbManagerAddParam.getDataAll();
        Log.d("AddPararBean", "insertData: " + addPararBeans);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    class Positiv implements View.OnClickListener {
        Dialog dialog;

        public Positiv(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            try {
                uptaData();
                dialog.dismiss();
                dialog.cancel();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
