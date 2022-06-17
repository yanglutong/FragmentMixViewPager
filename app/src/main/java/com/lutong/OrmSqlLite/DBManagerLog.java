package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;

import com.lutong.OrmSqlLite.Bean.LogBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;


import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 * 封装各种操作数据库的方法
 */
public class DBManagerLog {
    //注册码
    private Dao dao;
    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */
    public DBManagerLog(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(LogBean.class);
    }

    /**
     * 插入数据
     *
     * @param logBean
     * @thro
     */
    public void insertConmmunit01Bean(LogBean logBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.create(logBean);
    }

    /**
     * 批量插入
     * 不能使用循环一个一个的插入，因为这样会一直打开数据库、插入数据、
     * 关闭数据库
     *
     * @param
     * @throws SQLException
     */
    public void batchInsert(List<LogBean> logBeans) throws SQLException {
        dao.create(logBeans);
    }

    /**
     * 根据ID查找指定数据
     *
     * @param i
     * @return
     * @throws SQLException
     */
    public LogBean forid(int i) throws SQLException {
        LogBean logBean = (LogBean) dao.queryForId(i);
        return logBean;
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<LogBean> getZcBean1Beans() throws SQLException {
        List<LogBean> logBeanList = dao.queryForAll();
        return logBeanList;
    }


//    /**
//     * 查询某个数据
//     *
//     * @return
//     * @throws SQLException
//     */
//    public List<LogBean> queryGuanyu() throws SQLException {
//        //Eq是equals的缩写
//        //方法1
////        List<Student> list = dao.queryForEq("name", "张飞");
//
//        //方法2
//        QueryBuilder queryBuilder = dao.queryBuilder();
////        queryBuilder.offset(); //偏移量
////        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
////        queryBuilder.orderBy("age",true);
//        queryBuilder.where().eq("name", "关羽"); //多条件查询
//        List<LogBean> query = queryBuilder.query();//此方法相当于build，提交设置
//        return query;
//    }


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
     * @param logBean
     * @throws SQLException
     */
    public void deleteBean(LogBean logBean) throws SQLException {
        //只看id
        dao.delete(logBean);
    }

//    /**
//     * 删除指定数据
//     *
//     * @throws SQLException
//     */
//    public void deleteGuanyu() throws SQLException {
//        DeleteBuilder deleteBuilder = dao.deleteBuilder();
//        deleteBuilder.where().eq("name", "关羽");
//        deleteBuilder.delete();
//    }

    /**
     * 修改数据
     *
     * @param logBean
     * @throws SQLException
     */
    public void updateConmmunit01Bean(LogBean logBean) throws SQLException {
//        cumm01bean.setName("关羽");
        dao.update(logBean);
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
