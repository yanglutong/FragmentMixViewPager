package com.lutong.Utils;

import android.widget.Toast;

import com.lutong.AppContext;


/**
 * @author: 小杨同志
 * @date: 2021/9/23
 */
public class MyToast {
    public static void showToast(String msg){
        Toast.makeText(AppContext.getContexts(), ""+msg, Toast.LENGTH_SHORT).show();
    }
}
