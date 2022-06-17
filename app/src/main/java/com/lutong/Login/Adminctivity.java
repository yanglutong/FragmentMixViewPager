package com.lutong.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.AdminBean;
import com.lutong.R;
import com.lutong.activity.UpdateAdminActivity;
import com.lutong.base.BaseActivity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//用户列表
public class Adminctivity extends BaseActivity {
    ImageView iv_menu, iv_add, iv_finish;
    RecyclerView ry;
    AdminiAdapter adminiAdapter;
    AdminiAdapter.Callback callback = new AdminiAdapter.Callback() {
        @Override
        public void call(String id) {

        }

        @Override
        public void CallDele(int id, int position) {
            extracted();
        }
    };


    @Override
    protected void initQx() {

    }

    @Override
    protected void init() throws UnsupportedEncodingException {
        iv_finish = findViewById(R.id.iv_finish);
        iv_finish.setVisibility(View.VISIBLE);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_add = findViewById(R.id.iv_add);
        iv_add.setOnClickListener(new View.OnClickListener() {//添加人员
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adminctivity.this, UpdateAdminActivity.class));

            }
        });
        ry = findViewById(R.id.ry);
        ry.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        extracted();
    }

    private void extracted() {
        List<AdminBean> getdata = DbDataUtils.getdata(this);
        List<Integer> list1size = new ArrayList<>();
        for (int i = 0; i < getdata.size(); i++) {
            list1size.add(i);
        }
        Log.d("Adminctivity", "init: " + getdata.toString());
        adminiAdapter = new AdminiAdapter(this, DbDataUtils.getdata(this), list1size, callback);
        ry.setAdapter(adminiAdapter);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected int getView() {
        return R.layout.activity_adminctivity2;
    }

    @SuppressLint("NewApi")
    public void setStatBar() {//根据版本设置沉浸式状态栏
        View decorView = getWindow().getDecorView();
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT
        );
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        extracted();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}