package com.lutong.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.lutong.OrmSqlLite.Bean.AdminBean;
import com.lutong.OrmSqlLite.DBManagerAdmin;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;
import com.lutong.AppContext;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UpdateAdminActivity extends BaseActivity implements View.OnClickListener {
    String id = "";
    ImageView iv_findish;
    EditText ed_name, ed_pwd, ed_note;
    Spinner spinner;
    Button button, bt_cancel;
    ArrayAdapter adapter1;
    private String spinnerStr = "";
    private int spinnerType = 0;
    ImageView ivadd;

    @Override
    protected void initQx() {

    }

    @Override
    protected void init() throws UnsupportedEncodingException {

    }

    @Override
    protected void findViews() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        ivadd = findViewById(R.id.iv_add);
        ivadd.setVisibility(View.GONE);
        ed_name = findViewById(R.id.ed_name);
        ed_pwd = findViewById(R.id.ed_pwd);
        ed_note = findViewById(R.id.ed_note);
        button = findViewById(R.id.button);//确定
        bt_cancel = findViewById(R.id.bt_cancel);//取消
        button.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        LinearLayout layout = findViewById(R.id.ll_tab);
        TextView title = findViewById(R.id.titles);
        ImageView imageView = findViewById(R.id.iv_menu);
        iv_findish = findViewById(R.id.iv_finish);
        iv_findish.setOnClickListener(this);
        UtilsView.setViewVisibility(this, layout, title, imageView, "人员修改", false, iv_findish, true);
        Log.d("nzq", "findViews: " + id);
        if (!TextUtils.isEmpty(id)) {//修改
            DBManagerAdmin dbManagerAdmin = null;
            try {
                int i = Integer.parseInt(id);
                dbManagerAdmin = new DBManagerAdmin(UpdateAdminActivity.this);
                AdminBean forid = dbManagerAdmin.forid(i);
                ed_name.setText(forid.getName());
                ed_name.setEnabled(false);
                ed_pwd.setText(forid.getPwd());
                ed_note.setText(forid.getNote());
                spinner = findViewById(R.id.sp1);
                List<String> list = new ArrayList<>();
                list.add("启动");
                list.add("关闭");


                adapter1 = new ArrayAdapter<String>(AppContext.context, R.layout.spinner_item
                        , list);
                spinner.setAdapter(adapter1);
                Log.d("forid.getType()", "findViews: " + forid.getType());
                if (forid.getType() == 1) {
                    spinner.setSelection(0);
                } else {
                    spinner.setSelection(1);
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        spinnerStr = (String) adapter1.getItem(i);//下拉框1选择的数据
                        if (spinnerStr.equals("关闭")) {
                            spinnerType = 0;
                        }
                        if (spinnerStr.equals("启用")) {
                            spinnerType = 1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {//增加
            UtilsView.setViewVisibility(this, layout, title, imageView, "人员添加", false, iv_findish, true);
            ed_name.setEnabled(true);
            spinner = findViewById(R.id.sp1);
            List<String> list = new ArrayList<>();
            list.add("启用");
            list.add("关闭");


            adapter1 = new ArrayAdapter<String>(AppContext.context, R.layout.spinner_item
                    , list);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerStr = (String) adapter1.getItem(i);//下拉框1选择的数据
                    if (spinnerStr.equals("关闭")) {
                        spinnerType = 0;
                    }
                    if (spinnerStr.equals("启用")) {
                        spinnerType = 1;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Override
    protected int getView() {
        return R.layout.update_admin;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                finish();
                break;

            case R.id.button:

                if (!TextUtils.isEmpty(id)) {
                    if (isNull()) return;
                    DBManagerAdmin dbManagerAdmin = null;
                    ToastUtils.showToast("点击了1");
//
                    try {
                        dbManagerAdmin = new DBManagerAdmin(UpdateAdminActivity.this);
                        AdminBean adminBean = new AdminBean();
                        adminBean.setId(Integer.parseInt(id));
                        adminBean.setType(spinnerType);
                        adminBean.setNote(ed_note.getText().toString());
                        adminBean.setName(ed_name.getText().toString());
                        adminBean.setPwd(ed_pwd.getText().toString());

                        dbManagerAdmin.updateConmmunit01Bean(adminBean);
                        ToastUtils.showToast("修改成功");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {//添加
//                    ToastUtils.showToast("点击了2");
                    if (isNull()) return;
//                    ToastUtils.showToast("点击了3");
                    DBManagerAdmin dbManagerAdmin = null;
                    try {
                        dbManagerAdmin = new DBManagerAdmin(UpdateAdminActivity.this);

                        //判断用户名是否存在了
                        List<AdminBean> adminBeans = dbManagerAdmin.foridName(ed_name.getText().toString());
                        if (adminBeans.size() > 0) {
                            ToastUtils.showToast("用户名已存在");
                            Log.d("adminBean1", "onClick: " + adminBeans.size());

                            return;
                        } else {
                            Log.d("adminBean1", "onClick: " + adminBeans.size());

                            AdminBean adminBean = new AdminBean();
                            adminBean.setType(spinnerType);
                            adminBean.setNote(ed_note.getText().toString());
                            adminBean.setName(ed_name.getText().toString());
                            adminBean.setPwd(ed_pwd.getText().toString());
                            dbManagerAdmin.insertConmmunit01Bean(adminBean);
                            ToastUtils.showToast("添加成功");
                            finish();
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.bt_cancel:

                finish();
                break;
        }
    }

    private boolean isNull() {
        if (!TextUtils.isEmpty(ed_name.getText().toString())) {

        } else {
            ToastUtils.showToast("名称不能为空");
            return true;
        }
        if (!TextUtils.isEmpty(ed_pwd.getText().toString())) {

        } else {
            ToastUtils.showToast("密码不能为空");
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
