package com.lutong;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        double v1 = -0.11;
//        double v2 = -0.1100;
//        double v = getBigDecimal(v1, v2);
//        Log.e("ylt", "onCreate: "+v );
        //将集合的元素添加进去
    }

    public static int getBigDecimal(String v1, String v2) {
        int d = 0;
        BigDecimal of = BigDecimal.valueOf(Double.parseDouble(v1));
        BigDecimal decimal = BigDecimal.valueOf(Double.parseDouble(v2));
        int compare = of.compareTo(decimal);
        if (compare == 1) {//第一位大
            d = 1;
//            d= Double.parseDouble(of.toString());
        } else if (compare == 0) {
            //相等
//            d= Double.parseDouble(of.toString());
            d = 0;
        } else if (compare == -1) {
            //第二位大
//            d= Double.parseDouble(decimal.toString());
            d = -1;
        }
        return d;
    }
}
