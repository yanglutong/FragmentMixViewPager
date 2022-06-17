package com.lutong.Utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;


import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.Bean.Conmmunit01Bean;
import com.lutong.OrmSqlLite.Bean.PinConfigBean;
import com.lutong.OrmSqlLite.Bean.PinConfigBean5G;
import com.lutong.OrmSqlLite.Bean.SaopinBean;
import com.lutong.OrmSqlLite.Bean.TDDFDDzyBean;
import com.lutong.OrmSqlLite.Bean.ZcBean;
import com.lutong.OrmSqlLite.DBManager01;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.OrmSqlLite.DBManagerPinConfig;
import com.lutong.OrmSqlLite.DBManagerPinConfig5G;
import com.lutong.OrmSqlLite.DBManagerZX;
import com.lutong.OrmSqlLite.DBManagerZY;
import com.lutong.OrmSqlLite.DBManagersaopin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/12/9.
 */

public class DbUtils {
    public static void insertDB(Context context, int band, int down, String plmn, int type, int up, String tf, String yy) {
        DBManagerPinConfig dbManager = null;
        try {
            dbManager = new DBManagerPinConfig(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PinConfigBean pinConfigBean = new PinConfigBean();
        pinConfigBean.setBand(band); //int
        pinConfigBean.setDown(down); //int
        pinConfigBean.setPlmn(plmn);//string
        pinConfigBean.setType(type);//int
        pinConfigBean.setUp(up); //int
        pinConfigBean.setTf(tf);//String
        pinConfigBean.setYy(yy);//String
        try {
            int i = dbManager.insertStudent(pinConfigBean);
            if (i == 1) {
                Log.d("nan保存的数据", "===" + pinConfigBean);
//                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
//                ToastUtils.showToast("添加成功");
//                dialog.dismiss();
//                //查询所以数据
//                initData();
            } else {
//                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
//                ToastUtils.showToast("添加失败");
            }
//
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }
//    /**
//     * 轮循设置数据
//     *
//     * @param context
//     * @param down
//     * @param up
//     * @param typeSP
//     */
//    public static void insertDBLunxun(Context context, int down, int up, int typeSP) {
//        DBManagerLunxun dbManagersaopin = null;
//        try {
//            dbManagersaopin = new DBManagerLunxun(context);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        LunxunBean saopinBean = new LunxunBean();
////        saopinBean.setBand(Integer.parseInt(et_band.getText().toString()));
//        saopinBean.setDown(down);
////        saopinBean.setPlmn(et_plmn.getText().toString());
//        saopinBean.setType(1);
//        saopinBean.setUp(up);
////        saopinBean.setTf(spinnerTF);
//        if (typeSP == 1) {
//            saopinBean.setTf("TDD");
//            saopinBean.setYy("移动TDD");
//        }
//        if (typeSP == 2) {
//            saopinBean.setTf("FDD");
//            saopinBean.setYy("移动FDD");
//        }
//        if (typeSP == 3) {
//            saopinBean.setTf("FDD");
//            saopinBean.setYy("联通");
//        }
//        if (typeSP == 4) {
//            saopinBean.setTf("FDD");
//            saopinBean.setYy("电信");
//        }
//        try {
//            int i = dbManagersaopin.insertStudent(saopinBean);
//            if (i == 1) {
//                Log.d("nan保存的数据", "===" + saopinBean);
////                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
////                ToastUtils.showToast("添加成功");
//
//            } else {
////                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
//                ToastUtils.showToast("添加失败");
//            }
////
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//        }
//
//    }

    /**
     * 数据扫频数据
     *
     * @param context
     * @param down
     * @param up
     * @param typeSP
     */
    public static void insertDBSaopin(Context context, int down, int up, int typeSP) {
        DBManagersaopin dbManagersaopin = null;
        try {
            dbManagersaopin = new DBManagersaopin(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SaopinBean saopinBean = new SaopinBean();
//        saopinBean.setBand(Integer.parseInt(et_band.getText().toString()));
        saopinBean.setDown(down);
//        saopinBean.setPlmn(et_plmn.getText().toString());
        saopinBean.setType(1);
        saopinBean.setUp(up);
//        saopinBean.setTf(spinnerTF);
        if (typeSP == 1) {
            saopinBean.setTf("TDD");
            saopinBean.setYy("移动TDD");
        }
        if (typeSP == 2) {
            saopinBean.setTf("FDD");
            saopinBean.setYy("移动FDD");
        }
        if (typeSP == 3) {
            saopinBean.setTf("FDD");
            saopinBean.setYy("联通");
        }
        if (typeSP == 4) {
            saopinBean.setTf("FDD");
            saopinBean.setYy("电信");
        }
        try {
            int i = dbManagersaopin.insertStudent(saopinBean);
            if (i == 1) {
                Log.d("nan保存的数据", "===" + saopinBean);
//                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
//                ToastUtils.showToast("添加成功");

            } else {
//                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast("添加失败");
            }
//
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    //
    public static int blackListIsyidong(Context context) {
        int sum = 0;
        try {
            DBManagerAddParam dbManagerAddParam = new DBManagerAddParam(context);
            List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
            List<String> list = new ArrayList<>();

            for (int i = 0; i < dataAll.size(); i++) {
                if (dataAll.get(i).isCheckbox() && dataAll.get(i).getYy().equals("移动")) {
                    list.add(dataAll.get(i).getImsi());
                }
            }
            if (list.size() == 1) {
                sum = 1;

            } else if (list.size() == 0) {
                sum = 0;
            } else if (list.size() > 1) {
                sum = dataAll.size();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return sum;
    }
    //4G下行频点
    public static List<String> getSpinnerData(Context context) {
        List<String> listdata = new ArrayList<>();
        listdata.add("");
        try {
            DBManagerPinConfig dbManagerPinConfig = new DBManagerPinConfig(context);
            List<PinConfigBean> student = dbManagerPinConfig.getStudent();
            for (int i = 0; i < student.size(); i++) {
                int down = student.get(i).getDown();
                listdata.add(down + "");

            }
        } catch (Exception throwables) {

        }
        return listdata;

    }
    //下行频点SSB频点
    // 5G
    public static List<String> getSpinnerData5G(Context context) {
        List<String> listdata = new ArrayList<>();
        listdata.add("");
        try {
            DBManagerPinConfig5G dbManagerPinConfig = new DBManagerPinConfig5G(context);
            List<PinConfigBean5G> student = dbManagerPinConfig.getStudent();
            for (int i = 0; i < student.size(); i++) {
                int down = Integer.parseInt(student.get(i).getSsb());
                listdata.add(down + "");

            }
        } catch (Exception throwables) {

        }
        return listdata;

    }

    /**
     * 初始化小区数据
     *
     * @param context
     */
    public static void xiaoqu(Context context) {
        DBManager01 dbManager01;
        try {
            dbManager01 = new DBManager01(context);
            Conmmunit01Bean forid = dbManager01.forid(1);
            if (forid == null) {
                Log.e("nzqforid", "等于nullinitData: " + forid);
                Conmmunit01Bean conmmunit01Bean = new Conmmunit01Bean();
                conmmunit01Bean.setCid("");
                conmmunit01Bean.setEnodebpmax("");
                conmmunit01Bean.setUepmax("");
                conmmunit01Bean.setTac("1111");
                conmmunit01Bean.setPci("111");
                conmmunit01Bean.setCid("1111");
                conmmunit01Bean.setType("0");
                conmmunit01Bean.setCycle("5");
                conmmunit01Bean.setTime("60");
                conmmunit01Bean.setCb(false);
                dbManager01.insertConmmunit01Bean(conmmunit01Bean);
            }

            Conmmunit01Bean forid2 = dbManager01.forid(2);
            if (forid2 == null) {
                Log.e("nzqforid", "等于nullinitData: " + forid);
                Conmmunit01Bean conmmunit01Bean = new Conmmunit01Bean();
                conmmunit01Bean.setCid("1111");
                conmmunit01Bean.setEnodebpmax("");
                conmmunit01Bean.setUepmax("");
                conmmunit01Bean.setTac("2222");
                conmmunit01Bean.setPci("112");
                conmmunit01Bean.setCid("2222");
                conmmunit01Bean.setType("0");
                conmmunit01Bean.setCycle("5");
                conmmunit01Bean.setTime("60");
                conmmunit01Bean.setCb(false);
                dbManager01.insertConmmunit01Bean(conmmunit01Bean);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void xiaoquCB(Context context, int number, boolean b) {
        DBManager01 dbManager01;
        try {
            dbManager01 = new DBManager01(context);
            if (number == 1) {
                Conmmunit01Bean forid = dbManager01.forid(1);
                if (forid != null) {

                    forid.setCb(b);

                    dbManager01.updateConmmunit01Bean(forid);
                    Log.e("nzqforid", "等于nullinitData: " + forid);
                }
            }
            if (number == 2) {
                Conmmunit01Bean forid = dbManager01.forid(2);
                if (forid != null) {
                    forid.setCb(b);

                    dbManager01.updateConmmunit01Bean(forid);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取小区参数设置
     *
     * @param context
     * @param et_tac1
     * @param et_pci1
     * @param et_eci1
     * @param et_tac2
     * @param et_pci2
     * @param et_eci2
     */
    public static void setCellData(Context context, EditText et_tac1, EditText et_pci1, EditText et_eci1, EditText et_tac2, EditText et_pci2, EditText et_eci2, CheckBox cb1, CheckBox cb2) {
        DBManager01 dbManager01;
        try {
            dbManager01 = new DBManager01(context);
            Conmmunit01Bean forid1 = dbManager01.forid(1);
            et_tac1.setText(forid1.getTac());
            et_pci1.setText(forid1.getPci());
            et_eci1.setText(forid1.getCid());
            if (forid1.getCb()) {
                cb1.setChecked(true);
            } else {
                cb1.setChecked(false);
            }
            //
            Conmmunit01Bean forid2 = dbManager01.forid(2);
            et_tac2.setText(forid2.getTac());
            et_pci2.setText(forid2.getPci());
            et_eci2.setText(forid2.getCid());
            if (forid2.getCb()) {
                cb2.setChecked(true);
            } else {
                cb2.setChecked(false);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * 获取小区参数设置
     *
     * @param context
     */
    public static boolean GetCellDatacheck(Context context, int num) {
        DBManager01 dbManager01 = null;
        boolean b = false;
        try {
            dbManager01 = new DBManager01(context);
            if (num == 1) {
                dbManager01 = new DBManager01(context);
                Conmmunit01Bean forid1 = dbManager01.forid(1);
                b = forid1.getCb();
            }

            if (num == 2) {
                Conmmunit01Bean forid2 = dbManager01.forid(2);
                b = forid2.getCb();
            }
            //


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return b;
    }

    /**
     * 设置TAC参数
     *
     * @param context
     * @param num
     * @param editText
     */
    public static void setTac(Context context, int num, EditText editText) {
        DBManager01 dbManager01 = null;
        try {
            dbManager01 = new DBManager01(context);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (num == 1) {
            if (!TextUtils.isEmpty(editText.getText())) {
                String scid = editText.getText().toString();
                int i = Integer.parseInt(scid);
                if (0 <= i && i <= 65535) {
                    try {
                        Conmmunit01Bean forid = dbManager01.forid(num);
                        forid.setTac(editText.getText().toString());
                        dbManager01.updateConmmunit01Bean(forid);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("TAC应为0-65535之间");

                }

            } else {
                ToastUtils.showToast("小区" + num + "TAC参数不能为空");
            }
        }
        if (num == 2) {
            if (!TextUtils.isEmpty(editText.getText())) {
                String scid = editText.getText().toString();
                int i = Integer.parseInt(scid);
                if (0 <= i && i <= 65535) {
                    try {
                        Conmmunit01Bean forid = dbManager01.forid(num);
                        forid.setTac(editText.getText().toString());
                        dbManager01.updateConmmunit01Bean(forid);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("TAC应为0-65535之间");

                }
            } else {
                ToastUtils.showToast("小区" + num + "TAC参数不能为空");
            }
        }

    }

    /**
     * 设置Pci参数
     *
     * @param context
     * @param num
     * @param editText
     */
    public static void setPci(Context context, int num, EditText editText) {
        DBManager01 dbManager01 = null;
        try {
            dbManager01 = new DBManager01(context);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (num == 1) {
            if (!TextUtils.isEmpty(editText.getText())) {
                String scid = editText.getText().toString();
                int i = Integer.parseInt(scid);
                if (0 <= i && i <= 503) {
                    try {
                        Conmmunit01Bean forid = dbManager01.forid(num);
                        forid.setPci(editText.getText().toString());
                        dbManager01.updateConmmunit01Bean(forid);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("PCI应为0-503之间");

                }

            } else {
                ToastUtils.showToast("小区" + num + "ECI参数不能为空");
            }
        }
        if (num == 2) {
            if (!TextUtils.isEmpty(editText.getText())) {
                String scid = editText.getText().toString();
                int i = Integer.parseInt(scid);
                if (0 <= i && i <= 503) {
                    try {
                        Conmmunit01Bean forid = dbManager01.forid(num);
                        forid.setPci(editText.getText().toString());
                        dbManager01.updateConmmunit01Bean(forid);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("PCI应为0-503之间");

                }
            } else {
                ToastUtils.showToast("小区" + num + "ECI参数不能为空");
            }
        }
    }

    /**
     * 设置CID参数
     *
     * @param context
     * @param num
     * @param editText
     */
    public static void setCid(Context context, int num, EditText editText) {
        DBManager01 dbManager01 = null;
        try {
            dbManager01 = new DBManager01(context);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (num == 1) {
            if (!TextUtils.isEmpty(editText.getText())) {
                String scid = editText.getText().toString();
                int i = Integer.parseInt(scid);
                if (0 <= i && i <= 268435455) {
                    try {
                        Conmmunit01Bean forid = dbManager01.forid(num);
                        forid.setCid(editText.getText().toString());
                        dbManager01.updateConmmunit01Bean(forid);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("CID应为0-268435455之间");


                }

            } else {
                ToastUtils.showToast("小区" + num + "CID参数不能为空");
            }
        }
        if (num == 2) {
            if (!TextUtils.isEmpty(editText.getText())) {
                String scid = editText.getText().toString();
                int i = Integer.parseInt(scid);
                if (0 <= i && i <= 268435455) {
                    try {
                        Conmmunit01Bean forid = dbManager01.forid(num);
                        forid.setCid(editText.getText().toString());
                        dbManager01.updateConmmunit01Bean(forid);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    ToastUtils.showToast("CID应为0-268435455之间");
                }
            } else {
                ToastUtils.showToast("小区" + num + "CID参数不能为空");
            }
        }
    }

    /**
     * 初始化APP安装  此为测试数据
     *
     * @param context
     * @param imsi
     * @param name
     * @param phone
     * @param yy
     * @param Checkboix
     */
    public static void TestIMSI(Context context, String imsi, String name, String phone, String yy, boolean Checkboix) {
        DBManagerAddParam dbManagerAddParam = null;
        try {
            dbManagerAddParam = new DBManagerAddParam(context);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        AddPararBean addPararBean = new AddPararBean();
//        addPararBean.setId(0);
        addPararBean.setImsi(imsi + "");
        addPararBean.setName(name + "");
        addPararBean.setPhone(phone + "");
        addPararBean.setYy(yy + "");
        addPararBean.setCheckbox(Checkboix);
        try {
            int i = dbManagerAddParam.insertAddPararBean(addPararBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * 初始化APP安装  此为测试数据
//     *
//     * @param context
//     * @param imsi
//     * @param name
//     * @param phone
//     * @param yy
//     * @param Checkboix
//     */
//    public static void TestIMSIWhite(Context context, String imsi, String name, String phone, String yy, boolean Checkboix) {
//        DBManagerAddParamWhite dbManagerAddParam = null;
//        try {
//            dbManagerAddParam = new DBManagerAddParamWhite(context);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        AddPararBeanWhite addPararBean = new AddPararBeanWhite();
////        addPararBean.setId(0);
//        addPararBean.setImsi(imsi + "");
//        addPararBean.setName(name + "");
//        addPararBean.setPhone(phone + "");
//        addPararBean.setYy(yy + "");
//        addPararBean.setCheckbox(Checkboix);
//        try {
//            int i = dbManagerAddParam.insertAddPararBean(addPararBean);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
    //增益设置设置查询
    public static void ZY(Context context, String TDDd, String TDDz, String TDDg, int id, String FDDd, String FDDz, String FDDg) {
        DBManagerZY zy = null;
        try {
            zy = new DBManagerZY(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TDDFDDzyBean tddfdDzyBean = new TDDFDDzyBean();
        tddfdDzyBean.setId(id);
        tddfdDzyBean.setTDDzyd(TDDd);
        tddfdDzyBean.setTDDzyz(TDDz);
        tddfdDzyBean.setTDDzyg(TDDg);
        tddfdDzyBean.setFDDzyd(FDDd);
        tddfdDzyBean.setFDDzyz(FDDz);
        tddfdDzyBean.setFDDzyg(FDDg);
        try {
            zy.insertAddZmBean(tddfdDzyBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //注册码
    public static void zc(Context context) {
        DBManagerZX dbManagerZX = null;
        try {
            dbManagerZX = new DBManagerZX(context);
            ZcBean zcBean = new ZcBean();
            zcBean.setId(1);
            zcBean.setDate("");
            dbManagerZX.insertConmmunit01Bean(zcBean);
            ZcBean zcBean2 = new ZcBean();
            zcBean2.setId(2);
            zcBean2.setDate("");
            dbManagerZX.insertConmmunit01Bean(zcBean2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public static int getTime(Context context,int device){
//        Conmmunit01Bean forid = null;
//        DBManager01 dbManager01= null;
//        try {
//            dbManager01 = new DBManager01(context);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        try {
//            forid = dbManager01.forid(device);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        String time = forid.getTime();
//        if (!TextUtils.isEmpty(time)){
//            return Integer.parseInt(time);
//        }else {
//            return 60;
//        }
//    }
}
