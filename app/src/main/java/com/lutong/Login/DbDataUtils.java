package com.lutong.Login;

import android.content.Context;

import com.lutong.OrmSqlLite.Bean.AdminBean;
import com.lutong.OrmSqlLite.DBManagerAdmin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbDataUtils {
    public static List<AdminBean> getdata(Context context) {
        List<AdminBean> listBeans = new ArrayList<>();
        try {
            DBManagerAdmin dbManagerAdmin = new DBManagerAdmin(context);
            listBeans = dbManagerAdmin.getListBeans();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listBeans;
    }
}
