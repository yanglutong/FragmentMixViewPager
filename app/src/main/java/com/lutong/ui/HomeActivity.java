package com.lutong.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lutong.MainActivity;
import com.lutong.R;

public class HomeActivity extends Activity implements View.OnClickListener {
    private LinearLayout liner_jz;
    private LinearLayout liner_gm;
    private LinearLayout liner_dw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setStatBar();

        liner_jz = findViewById(R.id.liner_jz);
        liner_gm = findViewById(R.id.liner_gm);
        liner_dw = findViewById(R.id.liner_dw);

        liner_jz.setOnClickListener(this);
        liner_gm.setOnClickListener(this);
        liner_dw.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.liner_jz:
            case R.id.liner_gm:
            case R.id.liner_dw:
                startActivity();
                break;
        }
    }
    public void startActivity(){
        startActivity(new Intent(this, MainActivity.class));
    }
}