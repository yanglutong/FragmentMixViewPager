package com.lutong.OrmSqlLite;

/**
 * Created by Administrator on 2018/3/22 0022.
 */


import android.content.Context;


import com.lutong.OrmSqlLite.Bean.GuijiViewBean;
import com.lutong.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/14.
 * 封装各种操作数据库的方法
 */
public class DBManagerJZ {

    private Dao dao;

    /**
     * 在构造中获取数据库的操作类
     *
     * @param context
     * @throws SQLException
     */

    public DBManagerJZ(Context context) throws SQLException {

        OrmHelper ormHelper = new OrmHelper(context);
        //        SQLiteDatabase readableDatabase = ormHelper.getReadableDatabase(); //可以获取一个原生的数据库
        //Dao相当于原生的SQLiteDatabase，可以操作数据库,一个Dao只能操作一张表
        dao = ormHelper.getDao(GuijiViewBeanjizhan.class);
    }

    /**
     * 插入数据
     *
     * @param
     * @thro
     */
    public int insertStudent(GuijiViewBeanjizhan resultBean) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        int i = dao.create(resultBean);
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
    public void batchInsert(List<GuijiViewBeanjizhan> resultBeanList) throws SQLException {
        dao.create(resultBeanList);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<GuijiViewBeanjizhan> guijiViewBeans() throws SQLException {
        List<GuijiViewBeanjizhan> list = dao.queryForAll();
        return list;
    }

    /**
     * 查询数据是否存在
     * lac,ci
     *
     * @return
     * @throws SQLException
     */
    public List<GuijiViewBeanjizhan> QueryVules(String lac, String ci) throws SQLException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("lac", lac);
        map.put("ci", ci);
        List list1 = dao.queryForFieldValues(map);

        return list1;
    }

    public GuijiViewBeanjizhan forid(int id) throws SQLException {
        GuijiViewBeanjizhan guijiViewBean = (GuijiViewBeanjizhan) dao.queryForId(id);
        return guijiViewBean;
    }

    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<GuijiViewBeanjizhan> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq("name", "关羽"); //多条件查询
        List<GuijiViewBeanjizhan> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }

    public List<GuijiViewBeanjizhan> queryType() throws SQLException {
        //Eq是equals的缩写
        //方法1
        //List<Student> list = dao.queryForEq("name", "张飞");

        //方法2
        QueryBuilder queryBuilder = dao.queryBuilder();
//        queryBuilder.offset(); //偏移量
//        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
//        queryBuilder.orderBy("age",true);
        queryBuilder.where().eq("type", "1"); //多条件查询
        List<GuijiViewBeanjizhan> query = queryBuilder.query();//此方法相当于build，提交设置
        return query;
    }

    /**
     * 查询下旬频点对应的数据
     *
     * @param s
     * @return
     * @throws SQLException
     */
    public List<GuijiViewBeanjizhan> queryData(int s) throws SQLException {
        List pinConfigBeanlist = dao.queryBuilder().where().eq("down", s).query();
        return pinConfigBeanlist;
    }

    /**
     * 删除数据
     *
     * @param resultBean
     * @throws SQLException
     */
    public int deleteStudent(GuijiViewBeanjizhan resultBean) throws SQLException {
        //只看id
        int delete = dao.delete(resultBean);
        return delete;
    }

    public void deleteall() throws SQLException {
        List<GuijiViewBean> list = dao.queryForAll();
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
    public int deleteGuanyu(String id) throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("id", id);
        int delete = deleteBuilder.delete();
        return delete;
    }

    public void deleteTable() throws SQLException {
//        dao.execSQL("delete from tab_name");

    }


    /**
     * 修改数据
     *
     * @param guijiViewBeanjizhans
     * @throws SQLException
     */
    public int updateType(GuijiViewBeanjizhan guijiViewBeanjizhans) throws SQLException {

//        dao.queryForId()
        List<GuijiViewBeanjizhan> list = dao.queryForAll();
        if (list.size() > 0 && list != null) {
            for (int i = 0; i < list.size(); i++) {
                GuijiViewBeanjizhan guijiViewBeanjizhan = list.get(i);
                guijiViewBeanjizhan.setType(0);
                dao.update(guijiViewBeanjizhan);
            }
        }
        guijiViewBeanjizhans.setType(1);
        int update = dao.update(guijiViewBeanjizhans);
        return update;
    }

    /**
     * 修改jzTa数据
     */
    public int upTa(GuijiViewBeanjizhan guijiViewBeanjizhans) throws SQLException {
        int update = dao.update(guijiViewBeanjizhans);
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
