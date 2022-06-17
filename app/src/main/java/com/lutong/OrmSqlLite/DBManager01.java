package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;

import com.lutong.OrmSqlLite.Bean.Conmmunit01Bean;
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
public class DBManager01 {

    private Dao dao;

    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */
    public DBManager01(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(Conmmunit01Bean.class);
    }

    /**
     * 插入数据
     *
     * @param cumm01bean
     * @thro
     */
    public void insertConmmunit01Bean(Conmmunit01Bean cumm01bean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.create(cumm01bean);
    }
    /**
     * 批量插入
     * 不能使用循环一个一个的插入，因为这样会一直打开数据库、插入数据、
     * 关闭数据库
     *
     * @param cumm01bean
     * @throws SQLException
     */
    public void batchInsert(List<Conmmunit01Bean> cumm01bean) throws SQLException {
        dao.create(cumm01bean);
    }

    /**
     * 根据ID查找指定数据
     *
     * @param i
     * @return
     * @throws SQLException
     */
    public Conmmunit01Bean forid(int i) throws SQLException {
        Conmmunit01Bean conmmunit01Beans = (Conmmunit01Bean) dao.queryForId(i);
        return conmmunit01Beans;
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<Conmmunit01Bean> getConmmunit01Beans() throws SQLException {
        List<Conmmunit01Bean> list = dao.queryForAll();
        return list;
    }


    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<Conmmunit01Bean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
//        List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq("name", "关羽"); //多条件查询
        List<Conmmunit01Bean> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }




//    /**
//     * 查询某个数据
//     *
//     * @return
//     * @throws SQLException
//     */
//    public List<Conmmunit01Bean> queryGuanyu1() throws SQLException {
//        //Eq是equals的缩写
//        //方法1
//        List<Student> list = dao.queryForEq("name", "张飞");
//
//        //方法2
//        QueryBuilder queryBuilder = dao.queryBuilder();
////        queryBuilder.offset(); //偏移量
////        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
////        queryBuilder.orderBy("age",true);
//        queryBuilder.where().eq("name", "关羽"); //多条件查询
//        List<Conmmunit01Bean> query = queryBuilder.query();//此方法相当于build，提交设置
//        return query;
//    }


    /**
     * 删除数据
     *
     * @param cumm01bean
     * @throws SQLException
     */
    public void deleteConmmunit01Bean(Conmmunit01Bean cumm01bean) throws SQLException {
        //只看id
        dao.delete(cumm01bean);
    }

    /**
     * 删除指定数据
     *
     * @throws SQLException
     */
    public void deleteGuanyu() throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("name", "关羽");
        deleteBuilder.delete();
    }

    /**
     * 修改数据
     *
     * @param cumm01bean
     * @throws SQLException
     */
    public void updateConmmunit01Bean(Conmmunit01Bean cumm01bean) throws SQLException {
//        cumm01bean.setName("关羽");
        dao.update(cumm01bean);
    }

    /**
     * 修改指定数据
     *
     * @throws SQLException
     */
    public void updateGuanyu() throws SQLException {
        UpdateBuilder updateBuilder = dao.updateBuilder();
        updateBuilder.where().eq("name", "关羽");
        updateBuilder.updateColumnValue("sex", "女");
        updateBuilder.update();
    }
}
