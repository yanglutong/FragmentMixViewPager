package com.lutong.fragment;

import android.content.Context;
import android.text.TextUtils;

import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.Utils.ACacheUtil;
import com.lutong.Utils.DbUtils;

import java.sql.SQLException;

public class FragmentUtils {
    public  static int ProgressBarType(Context mContext, String down1, String down2) {

        int num = 0;
        if (ACacheUtil.getZD().equals("0")) {//手动建站
            Boolean b1 = DbUtils.GetCellDatacheck(mContext, 1);//  设备1是否选中
            Boolean b2 = DbUtils.GetCellDatacheck(mContext, 2);//设备2是否选中
            if (b1 == true && b2 == true) {//同时选中
//                ToastUtils.showToast("同时选中");
//                sb1FirstFlag = true;
//                num = 3;
                try {
                    DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(mContext);
                    if (TextUtils.isEmpty(down1)){
                        down1="0";
                    }
                    if (TextUtils.isEmpty(down2)){
                        down2="0";
                    }
                    boolean b11 = dbManagerAddParam.queryDataboolean(down1,mContext);
                    boolean b22 = dbManagerAddParam.queryDataboolean(down2,mContext);

                    if (b11 == true && b22 == true) {

                        if (!TextUtils.isEmpty(ACacheUtil.getSpinner1()) && !TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                            num = 3;//同时有选中的IMSI
                        }
                        if (!TextUtils.isEmpty(ACacheUtil.getSpinner1()) && TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                            num = 1;//有选中1的IMSI
                        }
                        if (TextUtils.isEmpty(ACacheUtil.getSpinner1()) && !TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                            num = 2;//有选中2的IMSI
                        }
                        if (TextUtils.isEmpty(ACacheUtil.getSpinner1()) && !TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                            num = 4;//没有有选中的IMSI
                        }
                    }

                    if (b11 == true && b22 == false) {

                        if (!TextUtils.isEmpty(ACacheUtil.getSpinner1())) {
                            num = 1;    //只有设备1 有选中的IMSI
                        }
                    }
                    if (b11 == false && b22 == true) {
                        if (!TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
                            num = 2;    //只有设备1 有选中的IMSI
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else if (b1 == true && b2 == false) {//选中了设备1
//                ToastUtils.showToast("选中了设备1");
                if (!TextUtils.isEmpty(ACacheUtil.getSpinner1())) {
//                    presenter.startsd(1, tf1, mContext, ACacheUtil.getSpinner1(), type1);
//                    sb1FirstFlag = true;
                    num = 1;
                } else {
//                    ToastUtils.showToast("设备1下行频点不能为空");
                }
            } else if (b1 == false && b2 == true) { //选中了设备2
//                ToastUtils.showToast("选中了设备2");
                if (!TextUtils.isEmpty(ACacheUtil.getSpinner2())) {
//                    presenter.startsd(2, tf2, mContext, ACacheUtil.getSpinner2(), type2);
//                    sb2FirstFlag = true;
                    num = 2;
                } else {
//                    ToastUtils.showToast("设备2下行频点不能为空");
                }
            } else if (b1 == false && b2 == false) {//同时未选中
//                ToastUtils.showToast("同时未选中");
                num = 4;
            }

        } else {//自动建站

        }

        return num;
    }
}
