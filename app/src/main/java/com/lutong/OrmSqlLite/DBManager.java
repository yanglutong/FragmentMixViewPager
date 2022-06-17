package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;


import com.lutong.AppContext;
import com.lutong.OrmSqlLite.Bean.CellBean;
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
public class DBManager {
    private static DBManager dbManager;

    static {
        try {
            dbManager = new DBManager(AppContext.getContexts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Dao dao;

    public DBManager(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(CellBean.class);
    }

    /**
     * 插入数据
     *
     * @param CellBean
     * @thro
     */
    public void insertCellBean(CellBean CellBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.create(CellBean);
    }

    /**
     * 批量插入
     * 不能使用循环一个一个的插入，因为这样会一直打开数据库、插入数据、
     * 关闭数据库
     *
     * @param CellBean
     * @throws SQLException
     */
    public void batchInsert(List<CellBean> CellBean) throws SQLException {
        dao.create(CellBean);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<CellBean> getCellBeanList() throws SQLException {
        List<CellBean> list = dao.queryForAll();
        return list;
    }


    /**
     * 查询某个数值的数据
     *
     * @return
     * @throws SQLException
     */
    public List<CellBean> querySelect(String key, String value) throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<CellBean> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq(key, value); //多条件查询
        List<CellBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }


    /**
     * 删除数据
     *
     * @param CellBean
     * @throws SQLException
     */
    public void deleteCellBean(CellBean CellBean) throws SQLException {
        //只看id
        dao.delete(CellBean);
    }

    /**
     * 删除指定数据
     *
     * @throws SQLException
     */
    public void deleteCellBeanID(String id) throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("id", id);
        deleteBuilder.delete();
    }

    /**
     * 修改数据
     *
     * @param CellBean
     * @throws SQLException
     */
    public void updateName(CellBean CellBean, String name) throws SQLException {
        CellBean.setBand(name);
        dao.update(CellBean);
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
