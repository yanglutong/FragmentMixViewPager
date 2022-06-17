package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;

import com.lutong.AppContext;

import com.lutong.OrmSqlLite.Bean.LiShiBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 * 封装各种操作数据库的方法
 */
public class DBManagerLiShi {
    private static DBManagerLiShi dbManager;

    static {
        try {
            dbManager = new DBManagerLiShi(AppContext.getContexts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Dao dao;

    public DBManagerLiShi(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(LiShiBean.class);
    }

    /**
     * 插入数据
     *
     * @param LiShiBean
     * @thro
     */
    public void insertLiShiBean(LiShiBean LiShiBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.create(LiShiBean);
    }

    /**
     * 批量插入
     * 不能使用循环一个一个的插入，因为这样会一直打开数据库、插入数据、
     * 关闭数据库
     *
     * @param LiShiBean
     * @throws SQLException
     */
    public void batchInsert(List<LiShiBean> LiShiBean) throws SQLException {
        dao.create(LiShiBean);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<LiShiBean> getLiShiBeanList() throws SQLException {
        List<LiShiBean> list = dao.queryForAll();
        return list;
    }


    /**
     * 查询某个数值的数据
     *
     * @return
     * @throws SQLException
     */
    public List<LiShiBean> querySelect(String key, String value) throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<LiShiBean> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq(key, value); //多条件查询
        List<LiShiBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }


    /**
     * 删除数据
     *
     * @param LiShiBean
     * @throws SQLException
     */
    public void deleteLiShiBean(LiShiBean LiShiBean) throws SQLException {
        //只看id
        dao.delete(LiShiBean);
    }

    /**
     * 删除指定数据
     *
     * @throws SQLException
     */
    public void deleteLiShiBeanID(String id) throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("id", id);
        deleteBuilder.delete();
    }

    /**
     * 修改数据
     *
     * @param LiShiBean
     * @throws SQLException
     */
    public void updateName(LiShiBean LiShiBean, String name) throws SQLException {
        LiShiBean.setBand(name);
        dao.update(LiShiBean);
    }

    /**
     * 修改指定数据
     *
     * @throws SQLException
     */
    public void update() throws SQLException {
        UpdateBuilder updateBuilder = dao.updateBuilder();
        updateBuilder.where().eq("name", "关羽");
        updateBuilder.updateColumnValue("sex", "女");
        updateBuilder.update();
    }
}
