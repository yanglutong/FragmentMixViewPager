package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;

import com.lutong.OrmSqlLite.Bean.PinConfigBean;
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
public class DBManagerPinConfig {

    private Dao dao;

    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */

    public DBManagerPinConfig(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(PinConfigBean.class);
    }

    /**
     * 插入数据
     *
     * @param
     * @thro
     */
    public int insertStudent(PinConfigBean pinbean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        int i = dao.create(pinbean);
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
    public void batchInsert(List<PinConfigBean> pinbean) throws SQLException {
        dao.create(pinbean);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<PinConfigBean> getStudent() throws SQLException {
        List<PinConfigBean> list = dao.queryForAll();
        return list;
    }

    public PinConfigBean forid(int id) throws SQLException {
        PinConfigBean pinConfigBean = (PinConfigBean) dao.queryForId(id);
        return pinConfigBean;
    }

    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<PinConfigBean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq("name", "关羽"); //多条件查询
        List<PinConfigBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }
    /**
     * 查询下旬频点对应的数据
     * @param s
     * @return
     * @throws SQLException
     */
    public List<PinConfigBean> queryData(int s) throws SQLException {
        List pinConfigBeanlist =  dao.queryBuilder().where().eq("down", s).query();
        return pinConfigBeanlist;
    }

    /**
     * 删除数据
     *
     * @param pinConfigBean
     * @throws SQLException
     */
    public int deleteStudent(PinConfigBean pinConfigBean) throws SQLException {
        //只看id
        int delete = dao.delete(pinConfigBean);
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
     * @param pinConfigBean
     * @throws SQLException
     */
    public void updateStudent(PinConfigBean pinConfigBean) throws SQLException {
        pinConfigBean.setUp(91);
        dao.update(pinConfigBean);
    }
    /**
     * 修改数据
     *
     * @param pinConfigBean
     * @throws SQLException
     */
    public void updateStudentfalse(PinConfigBean pinConfigBean) throws SQLException {
        pinConfigBean.setType(0);
        dao.update(pinConfigBean);
    }
    public void updateStudenttrue(PinConfigBean pinConfigBean) throws SQLException {
        pinConfigBean.setType(1);
        dao.update(pinConfigBean);
    }
    //checkBox选中事件
    public int updatecheckBox(PinConfigBean pinConfigBean) throws SQLException {
        pinConfigBean.setType(1);
        int update = dao.update(pinConfigBean);
        return update;
    }
    //checkBox选中事件
    public int updatecheckFlaseBox(PinConfigBean pinConfigBean) throws SQLException {
        pinConfigBean.setType(0);
        int update = dao.update(pinConfigBean);
        return update;
    }

    //checkBox选中事件
    public int updateData(PinConfigBean pinConfigBean) throws SQLException {
        int update = dao.update(pinConfigBean);
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
