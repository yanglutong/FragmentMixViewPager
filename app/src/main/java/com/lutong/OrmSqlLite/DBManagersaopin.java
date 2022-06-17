package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;
import android.util.Log;

import com.lutong.OrmSqlLite.Bean.SaopinBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 * 封装各种操作数据库的方法
 */
public class DBManagersaopin {

    private Dao dao;

    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */

    public DBManagersaopin(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(SaopinBean.class);
    }

    /**
     * 插入数据
     *
     * @param
     * @thro
     */
    public int insertStudent(SaopinBean saopinBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        int i = dao.create(saopinBean);
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
    public void batchInsert(List<SaopinBean> saopinBeans) throws SQLException {
        dao.create(saopinBeans);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<SaopinBean> getStudent() throws SQLException {
        List<SaopinBean> list = dao.queryForAll();
        return list;
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<SaopinBean> getStudentquery(String name, String Tf) throws SQLException {

        List<SaopinBean> list = dao.queryForAll();
        List<SaopinBean> listdata = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getYy().equals(name) && list.get(i).getTf().equals(Tf)) {
                listdata.add(list.get(i));
            }
        }
        Log.d("TAG", "getStudentquery list: "+list);
        Log.d("TAG", "getStudentquery listdata: "+listdata);
        return listdata;
    }
    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<SaopinBean> getStudentqueryYIDONG(String name, String Tf) throws SQLException {

        List<SaopinBean> list = dao.queryForAll();
        List<SaopinBean> listdata = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getYy().equals(name) && list.get(i).getTf().equals(Tf)) {
                listdata.add(list.get(i));
            }
        }
        Log.d("TAG", "getStudentquery list: "+list);
        Log.d("TAG", "getStudentquery listdata: "+listdata);
        return listdata;
    }

    /**
     * 查询运营商数据
     *
     * @return
     * @throws SQLException
     * @ name 运营商名称
     */
    public List<SaopinBean> getStudentNameList(String name) throws SQLException {
        List<SaopinBean> list = dao.queryForAll();
        List<SaopinBean> listyy = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getYy().equals(name)) {
                listyy.add(list.get(i));
            }


        }
        return listyy;
    }

    public SaopinBean forid(int id) throws SQLException {
        SaopinBean saopinBean = (SaopinBean) dao.queryForId(id);
        return saopinBean;
    }

    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<SaopinBean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq("name", "关羽"); //多条件查询
        List<SaopinBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }

    /**
     * 查询下旬频点对应的数据
     *
     * @param s
     * @return
     * @throws SQLException
     */
    public List<SaopinBean> queryData(int s) throws SQLException {
        List list = dao.queryBuilder().where().eq("down", s).query();
        return list;
    }

    /**
     * 删除数据
     *
     * @param saopinBean
     * @throws SQLException
     */
    public int deleteStudent(SaopinBean saopinBean) throws SQLException {
        //只看id
        int delete = dao.delete(saopinBean);
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
     * @param saopinBean
     * @throws SQLException
     */
    public void updateStudentfalse(SaopinBean saopinBean) throws SQLException {
        saopinBean.setType(0);
        dao.update(saopinBean);
    }

    public void updateStudenttrue(SaopinBean saopinBean) throws SQLException {
        saopinBean.setType(1);
        dao.update(saopinBean);
    }

    //checkBox选中事件
    public int updatecheckBox(SaopinBean saopinBean) throws SQLException {
        saopinBean.setType(1);
        int update = dao.update(saopinBean);
        return update;
    }

    //checkBox选中事件
    public int updatecheckFlaseBox(SaopinBean saopinBean) throws SQLException {
        saopinBean.setType(0);
        int update = dao.update(saopinBean);
        return update;
    }

    //checkBox选中事件
    public int updateData(SaopinBean saopinBean) throws SQLException {
        int update = dao.update(saopinBean);
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
