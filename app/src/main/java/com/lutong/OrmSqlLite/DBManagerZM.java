package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;

import com.lutong.OrmSqlLite.Bean.ZmBean;
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
public class DBManagerZM {

    private Dao dao;

    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */

    public DBManagerZM(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(ZmBean.class);
    }

    /**
     * 插入数据
     *
     * @param
     * @thro
     */
    public int insertAddZmBean(ZmBean zmBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        int i = dao.create(zmBean);
        return i;
    }

    /**
     * 批量插入
     * 不能使用循环一个一个的插入，因为这样会一直打开数据库、插入数据、
     * 关闭数据库
     *
     * @param
     * @throws SQLException
     */
    public void batchInsert(List<ZmBean> zmBeans) throws SQLException {
        dao.create(zmBeans);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<ZmBean> getDataAll() throws SQLException {
        List<ZmBean> list = dao.queryForAll();
        return list;
    }

    public ZmBean forid(int id) throws SQLException {
        ZmBean zmBean = (ZmBean) dao.queryForId(id);
        return zmBean;
    }

    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<ZmBean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
//        queryBuilder.where().eq("name", "关羽"); //多条件查询
        List<ZmBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }

//    /**
//     * 查询下旬频点对应的数据
//     *
//     * @param s
//     * @return
//     * @throws SQLException
//     */
//    public List<ZmBean> queryData(int s) throws SQLException {
//        List pinConfigBeanlist = dao.queryBuilder().where().eq("down", s).query();
//        return pinConfigBeanlist;
//    }

    /**
     * 删除数据
     *
     * @param zmBean
     * @throws SQLException
     */
    public int deleteAddPararBean(ZmBean zmBean) throws SQLException {
        //只看id
        int delete = dao.delete(zmBean);
        return delete;
    }

    public void deleteall() throws SQLException {
        List<ZmBean> list = dao.queryForAll();
        if (list.size() > 0 && list != null) {
            for (int i = 0; i < list.size(); i++) {
                int delete = dao.delete(list.get(i));
            }
        }
    }
//


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

    public void deleteTable() throws SQLException {
//        dao.execSQL("delete from tab_name");

    }


    /**
     * 修改数据
     *
     * @param
     * @throws SQLException
     */
    public int updateCheck(ZmBean zmBean) throws SQLException {
//        guijiViewBean.setUp(91);

        int update = dao.update(zmBean);
        return update;
    }

    public int updateCheck2(ZmBean zmBean) throws SQLException {
//        guijiViewBean.setUp(91);

        int update = dao.update(zmBean);
        return update;
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
