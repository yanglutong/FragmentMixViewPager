package com.lutong.Views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UtilsView {

    public static void setViewVisibility(final Context context, LinearLayout linearLayout, TextView titles, ImageView immenu, String text, boolean menuFlag, final ImageView iv_finish, boolean ivfinishFlag) {
        linearLayout.setVisibility(View.VISIBLE);
        titles.setText(text);
        if (menuFlag == true) {
            immenu.setVisibility(View.VISIBLE);
        } else {
            immenu.setVisibility(View.GONE);
        }
        if (ivfinishFlag == true) {
            iv_finish.setVisibility(View.VISIBLE);
        } else {
            iv_finish.setVisibility(View.GONE);
        }


    }
}
