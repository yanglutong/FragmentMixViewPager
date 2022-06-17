package com.lutong.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.panoramaview.PanoramaView;
import com.lutong.AppContext;
import com.lutong.R;


public class PanoramaDemoActivityMain extends Activity {
    private PanoramaView mPanoView;
    private ImageView finsh;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 先初始化BMapManager
        initBMapManager();

        setContentView(R.layout.activity_panorama_demo_main);
        findViewById(R.id.finsh).setOnClickListener(new View.OnClickListener() {//返回键
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setStatBar();
        title = findViewById(R.id.title);
        title.setVisibility(View.VISIBLE);
        
        mPanoView = findViewById(R.id.panorama);
        mPanoView.setPanoramaImageLevel(PanoramaView.ImageDefinition.ImageDefinitionHigh); // 设置清晰度
        mPanoView.setArrowTextureByUrl("http://d.lanrentuku.com/down/png/0907/system-cd-disk/arrow-up.png");
        //是否显示邻接街景箭头（有邻接全景的时候）
        mPanoView.setShowTopoLink(true);
//        mPanoView.setPanoramaHeading(heading);
        Intent intent = getIntent();
        if (intent != null) {
            String lat = intent.getStringExtra("lat");
            String lon = intent.getStringExtra("lon");
            double v1 = Double.parseDouble(lat);
            double v2 = Double.parseDouble(lon);

            try {
                mPanoView.setPanorama(v2, v1, PanoramaView.COORDTYPE_BD09LL);
                mPanoView.setShowTopoLink(false);
            } catch (Exception e) {
                Log.d("TAG", "PanoramaDemoActivityMainonCreate: " + e.getMessage());
            }
        }


//        double lat = 39.91403075654526;
//        double lon = 116.40391285827147;
//        mPanoView.setPanorama(lon, lat, PanoramaView.COORDTYPE_BD09LL);
    }

    private void initBMapManager() {
        AppContext app = (AppContext) this.getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(app);
            app.mBMapManager.init(new AppContext.MyGeneralListener());
        }
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
}
