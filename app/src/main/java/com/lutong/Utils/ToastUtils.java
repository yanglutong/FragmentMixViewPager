package com.lutong.Utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lutong.AppContext;
import com.lutong.R;


public class ToastUtils {
    private static long mPressedTime = 0;
    //    //居中 自定义布局Toast
    public static void showToast(String text) {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
//        Log.d("nzqshowToastif", "onBackPressed: "+mNowTime);
//        if ((mNowTime - mPressedTime) > 1000) {//比较两次按键时间差
            Log.d("nzqshowToastif", "onBackPressed: "+mNowTime);

            Context contexts = AppContext.getInstance();
            try {
                Toast toast = Toast.makeText(contexts, text, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, -250);

                View view = LayoutInflater.from(contexts).inflate(R.layout.toast_item, null);
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(text + "");
                toast.setView(view);
                toast.show();
                Log.d("nzqshowToastelse", "onBackPressed: "+mNowTime);
//                mPressedTime = mNowTime;
            } catch (Exception e) {
                Log.d("ToastUtils", "showToast: "+e.getMessage());
                Log.d("nzqshowToastException", "onBackPressed: "+mNowTime);
            }
//        } else {//退出程序
//
//        }

    }

    //    //居中 自定义布局Toast
    public static void showToastDown(String text) {

        Context contexts = AppContext.getContexts();
        try {
            Toast toast = Toast.makeText(contexts, text, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, -250);
            View view = LayoutInflater.from(contexts).inflate(R.layout.toast_item, null);
            TextView tv = (TextView) view.findViewById(R.id.tv);
            tv.setText(text + "");
            toast.setView(view);
            toast.show();
        } catch (Exception e) {
        }
    }
}
