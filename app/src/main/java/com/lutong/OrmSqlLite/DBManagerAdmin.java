package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;

import com.lutong.OrmSqlLite.Bean.AdminBean;
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
public class DBManagerAdmin {
    //注册码
    private Dao dao;

    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */
    public DBManagerAdmin(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(AdminBean.class);
    }

    /**
     * 插入数据
     *
     * @param adminBean
     * @thro
     */
    public void insertConmmunit01Bean(AdminBean adminBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.create(adminBean);
    }

    /**
     * 批量插入
     * 不能使用循环一个一个的插入，因为这样会一直打开数据库、插入数据、
     * 关闭数据库
     *
     * @param
     * @throws SQLException
     */
    public void batchInsert(List<AdminBean> zcBeans) throws SQLException {
        dao.create(zcBeans);
    }

    /**
     * 根据ID查找指定数据
     *
     * @param i
     * @return
     * @throws SQLException
     */
    public AdminBean forid(int i) throws SQLException {
        AdminBean zcBean = (AdminBean) dao.queryForId(i);
        return zcBean;
    }
    public List<AdminBean> foridName(String str) throws SQLException {
        List list = dao.queryForEq("name", str);
        return list;
    }
    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<AdminBean> getListBeans() throws SQLException {
        List<AdminBean> list = dao.queryForAll();
        return list;
    }


    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<AdminBean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
//        List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq("name", "关羽"); //多条件查询
        List<AdminBean> query = queryBuilder.query();//此方法相当于build，提交设置
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
     * @param adminBean
     * @throws SQLException
     */
    public void deleteConmmunit01Bean(AdminBean adminBean) throws SQLException {
        //只看id
        dao.delete(adminBean);
    }
    public int deleteAddPararBean(AdminBean adminBean) throws SQLException {
        //只看id
        int delete = dao.delete(adminBean);
        return delete;
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
     * @param adminBean
     * @throws SQLException
     */
    public void updateConmmunit01Bean(AdminBean adminBean) throws SQLException {
//        cumm01bean.setName("关羽");
        dao.update(adminBean);
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
