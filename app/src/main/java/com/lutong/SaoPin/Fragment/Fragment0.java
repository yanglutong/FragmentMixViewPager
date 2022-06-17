package com.lutong.SaoPin.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.SaopinBean;
import com.lutong.OrmSqlLite.DBManagersaopin;
import com.lutong.R;
import com.lutong.SaoPin.SaopinIT;
import com.lutong.SaoPin.adapter.SaopinAdapterFragment0;
import com.lutong.Utils.ToastUtils;
import com.lutong.base.BaseFragment;


import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class Fragment0 extends BaseFragment implements View.OnClickListener, SaopinIT {

    private String name;

    @SuppressLint("ValidFragment")
    public Fragment0(String name) {
        this.name = name;
    }

    private static String TAG = "TextFragment0";
    DecimalFormat df2;
    private View view;
    private CheckBox cb_all;//全选按钮
    RecyclerView ry;
    DBManagersaopin dbManagersaopin = null;
    SaopinAdapterFragment0 saopinAdapter1;
    //用于底部弹出窗
    private Dialog dialog;
    private View inflate;
    private Button bt_adddilao, bt_canel;
    private ImageView iv_show_finish;
    private EditText et_up_ping, et_down_ping, et_plmn, et_band;
    private String spinnerYY = "";
    private String spinnerTF = "";
    private Spinner sp_yy, sp_tf;
    private TextView titles;
    private Button bt_fdd, bt_tdd, bt_lt, bt_ds;

    private TextView tv_choose;//已选择的数量
    int sizeinit;//用來判斷是否添加成功的
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.saopinfragment0, null);
            try {
                dbManagersaopin = new DBManagersaopin(mContext);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            initData();
        }

        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
            initData();
        }
        return view;
    }
    SaopinAdapterFragment0.IDialogSaopin0 dialogSaopin = new SaopinAdapterFragment0.IDialogSaopin0() {
        @Override
        public void getPosition(String position, int index, String linename) {

        }

        @Override
        public void deleID(int deleid) {

        }

        @Override
        public void up(SaopinBean saopinBean) {

        }

        @Override
        public void update(int type) {
//            ToastUtils.showToast("全部选中" + type);
            if (type == 1) {//全部选中
                cb_all.setOnCheckedChangeListener(new MyOnclicklist(1));
                cb_all.setChecked(true);
            } else if (type == 2) {//
                cb_all.setOnCheckedChangeListener(new MyOnclicklist(2));
                cb_all.setChecked(false);
            } else if (type == 3) {
                cb_all.setOnCheckedChangeListener(new MyOnclicklist(2));
                cb_all.setChecked(false);

            }
            try {
                List<Integer>list=new ArrayList<>();
                List<SaopinBean> student = dbManagersaopin.getStudent();
                if (student != null && student.size() > 0) {
                    for (int i = 0; i < student.size(); i++) {
                        if (student.get(i).getType() == 1 && student.get(i).getYy().equals(name)) {
                            list.add(i);
                        }
                    }
                    if (list.size()>0){
                        tv_choose.setText("已选择:"+list.size());
                    }else {
                        tv_choose.setText("已选择:"+"0");

                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //            try {
//                List<SaopinBean> student = dbManagersaopin.getStudent();
//                List<Integer> list = new ArrayList<>();
//                for (int i = 0; i < student.size(); i++) {
//                    if (student.get(i).getType() == 1) {
//                        list.add(i);
//                    }
//
//                }
//                if (student.size() == list.size()) {//全部选中 1
//                    ToastUtils.showToast("全部选中");
//                } else {//为全部选中  2
//                    cb_all.setOnCheckedChangeListener(new MyOnclicklist(2));
//                    ToastUtils.showToast("为全部选中");
//                }
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void update2() {

        }

        @Override
        public void upbianji(SaopinBean saopinBean) {
            add(true, saopinBean);//true 编辑
        }
    };

    private void add(boolean b, SaopinBean saopinBean) {
        if (b) {
            dialog = new Dialog(mContext, R.style.ActionSheetDialogStyleDialog);
//            //填充对话框的布局
            inflate = LayoutInflater.from(mContext).inflate(R.layout.saopinlist_dibushow, null);
//            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            titles = inflate.findViewById(R.id.titles);
            titles.setText("配置频点");
            bt_adddilao.setText("确认");
            bt_adddilao.setOnClickListener(new MySetUpdate(saopinBean, saopinBean.getId(), saopinBean.getType()));
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_up_ping.setText(saopinBean.getUp() + "");
            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            et_down_ping.setText(saopinBean.getDown() + "");
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
//            et_plmn.setText(saopinBean.getPlmn());
            et_band = inflate.findViewById(R.id.et_band);//波段
//            et_band.setText(saopinBean.getBand() + "");
            sp_yy = inflate.findViewById(R.id.sp_yy);
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            });
            spinnerTF = saopinBean.getTf();
            spinnerYY = saopinBean.getYy();
//
            dialog.setCanceledOnTouchOutside(false);
//            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        } else {
            dialog = new Dialog(mContext, R.style.ActionSheetDialogStyleDialog);
            //填充对话框的布局
            inflate = LayoutInflater.from(mContext).inflate(R.layout.saopinlist_dibushow, null);
            //初始化控件
            bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
            bt_adddilao.setOnClickListener(this);
            iv_show_finish = inflate.findViewById(R.id.iv_show_finish);
            iv_show_finish.setOnClickListener(this);
            et_up_ping = inflate.findViewById(R.id.et_up_ping);//上频
            et_down_ping = inflate.findViewById(R.id.et_down_ping);//下频
            sp_tf = inflate.findViewById(R.id.sp_tf);//TDD FDD   下拉框
            et_plmn = inflate.findViewById(R.id.et_plmn);//plmn
            et_band = inflate.findViewById(R.id.et_band);//波段
            sp_yy = inflate.findViewById(R.id.sp_yy);//运营商   下拉框
            bt_canel = inflate.findViewById(R.id.bt_canel);
            bt_canel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            });

//
            dialog.setCanceledOnTouchOutside(false);
            //将布局设置给Dialog
            dialog.setContentView(inflate);
            //获取当前Activity所在的窗体
            Window dialogWindow = dialog.getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //获得窗体的属性
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
            dialogWindow.setAttributes(lp);
            dialog.show();//显示对话框
        }
    }

    @Override
    public void Callback() {
        initData();
    }


    class MySetUpdate implements View.OnClickListener {
        private int type;
        private int id;
        SaopinBean saopinBean;

        public MySetUpdate(SaopinBean saopinBean, int id, int type) {
            this.saopinBean = saopinBean;
            this.id = id;
            this.type = type;
        }

        @Override
        public void onClick(View view) {
            if (isnull()) return;
            SaopinBean pinConfigBeanss = new SaopinBean();
//            pinConfigBeanss.setUp(Integer.parseInt(et_up_ping.getText().toString()));
            pinConfigBeanss.setDown(Integer.parseInt(et_down_ping.getText().toString()));
            pinConfigBeanss.setTf(spinnerTF);
//            pinConfigBeanss.setPlmn(et_plmn.getText().toString());
//            pinConfigBeanss.setBand(Integer.parseInt(et_band.getText().toString()));
            pinConfigBeanss.setId(id);
            pinConfigBeanss.setType(type);
            pinConfigBeanss.setYy(spinnerYY);
            try {
                int i = dbManagersaopin.updateData(pinConfigBeanss);
                Log.d("nzq", "updateDataonClick: " + i);
                getData();
                dialog.dismiss();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean isnull() {
//        if (TextUtils.isEmpty(et_up_ping.getText().toString())) {
//            Toast.makeText(this, "上行频点不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        if (TextUtils.isEmpty(et_down_ping.getText().toString())) {
            Toast.makeText(mContext, "下行频点不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }

//        if (TextUtils.isEmpty(et_tf.getText().toString())) {
//            Toast.makeText(this, "TDD/FDD不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (TextUtils.isEmpty(et_band.getText().toString())) {
//            Toast.makeText(this, "频段不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (TextUtils.isEmpty(et_plmn.getText().toString())) {
//            Toast.makeText(this, "PLMN不能为空", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        return false;
    }

//    @Override
//    public View initView() {
////        view = inflater.inflate(R.layout.deviceinfragment0, container, false);
//        if (view == null) {
//            view = LayoutInflater.from(mContext).inflate(R.layout.saopinfragment0, null);
//            try {
//                dbManagersaopin = new DBManagersaopin(mContext);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            initData();
//        }
//
//        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeView(view);
//            initData();
//        }
//        return view;
//
//
//    }

    @Override
    public void initData() {
        findViews();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        findViews();
        getData();
//
    }

    private void findViews() {
        ry = view.findViewById(R.id.ry);
        ry.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        cb_all = view.findViewById(R.id.cb_all);
        cb_all.setOnCheckedChangeListener(new MyOnclicklist(2));
        tv_choose = view.findViewById(R.id.tv_choose);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.bt_adddilao:
                //底部弹出窗确认添加按钮
                ViewisEmpty();
                break;
        }
    }

    /**
     * Edit非空判断
     */
    private void ViewisEmpty() {
        if (isnull()) return;
        //配置新频点数据插入 SQL
        Log.d("iiii", "ViewisEmpty: ");
        exeSql();
    }

    private void exeSql() {
//        savepin();
    }

    //    private void savepin() {
//        SaopinBean saopinBean = new SaopinBean();
////        saopinBean.setBand(Integer.parseInt(et_band.getText().toString()));
//        saopinBean.setDown(Integer.parseInt(et_down_ping.getText().toString()));
////        saopinBean.setPlmn(et_plmn.getText().toString());
////        saopinBean.setType(0);
//        saopinBean.setUp(Integer.parseInt(et_up_ping.getText().toString()));
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
//                ToastUtils.showToast("添加成功");
//                dialog.dismiss();
//                //查询所以数据
//                setadapter();
//            } else {
////                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
//                ToastUtils.showToast("添加失败");
//            }
////
//        } catch (java.sql.SQLException e) {
//
//            e.printStackTrace();
//        }
//    }
    class MyOnclicklist implements CompoundButton.OnCheckedChangeListener {
        private int i;

        public MyOnclicklist(int i) {
            this.i = i;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {//全选
                try {
                    dbManagersaopin = new DBManagersaopin(mContext);
                    List<SaopinBean> student = dbManagersaopin.getStudent();
                    if (student != null && student.size() > 0) {
                        List<Integer> list = new ArrayList<>();
                        for (int i = 0; i < student.size(); i++) {
                            if (student.get(i).getYy().equals(name)) {
                                list.add(i);
                            }

                        }
                        if (list.size() > 9) {
                            compoundButton.setChecked(false);
//                                getData();
                            ToastUtils.showToast("最多同时选10个频点");
                            return;
                        }
                        for (int i = 0; i < student.size(); i++) {
                            try {

                                if (student.get(i).getYy().equals(name)) {
                                    SaopinBean saopinBean = student.get(i);
                                    dbManagersaopin.updateStudenttrue(saopinBean);
                                }


//                        dialogPinConfig.update();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        getData();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                if (i == 1) {
                    try {
//                        ToastUtils.showToast(111 + "");
                        dbManagersaopin = new DBManagersaopin(mContext);
                        List<SaopinBean> student = dbManagersaopin.getStudent();
                        if (student != null && student.size() > 0) {

                            for (int i = 0; i < student.size(); i++) {
                                try {
                                    if (student.get(i).getYy().equals(name)) {
                                        SaopinBean saopinBean = student.get(i);
                                        dbManagersaopin.updateStudentfalse(saopinBean);
                                    }

//                        dialogPinConfig.update();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            getData();

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                List<SaopinBean> student = null;
                try {
                    student = dbManagersaopin.getStudent();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                List<Integer> lists = new ArrayList<>();//一共的
                List<Integer> listtrue = new ArrayList<>();//一共的又选中的

                if (student.size() > 0 && student != null) {
                    for (int i = 0; i < student.size(); i++) {
                        if (student.get(i).getYy().equals(name)) {
                            lists.add(i);
                        }
                    }
                    for (int i = 0; i < student.size(); i++) {
                        if (student.get(i).getYy().equals(name) && student.get(i).getType() == 1) {
                            listtrue.add(i);
                        }

                    }
                }
                Log.d("nzq", "onCheckedChanged:lists " + lists.size());
                Log.d("nzq", "onCheckedChanged:student " + student.size());
                if (listtrue.size() == lists.size()) {//全部选中
                    for (int i = 0; i < student.size(); i++) {
                        try {
                            if (student.get(i).getYy().equals(name)) {
                                SaopinBean saopinBean = student.get(i);
                                dbManagersaopin.updateStudentfalse(saopinBean);
                            }


//                        dialogPinConfig.update();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    getData();
                } else if (lists.size() > 0 && listtrue.size() < lists.size()) {

                }
            }
//            if (i == 1) { //全部选中
//                cb_all.setChecked(true);
//                Log.d(TAG, "onCheckedChanged:1 ");
//            } else if (i == 2) {//未全部选中
//                cb_all.setChecked(false);
//                Log.d(TAG, "onCheckedChanged:2 ");
//            } else {
//                Log.d(TAG, "onCheckedChanged:2 ");
//            }
//            if ()
//            if (b) { //true  选择全选
//                try {
//                    dbManagersaopin = new DBManagersaopin(mContext);
//                    List<SaopinBean> student = dbManagersaopin.getStudent();
//                    if (student != null && student.size() > 0) {
//                        List<Integer> list = new ArrayList<>();
//                        for (int i = 0; i < student.size(); i++) {
//                            if (student.get(i).getYy().equals("移动TDD")) {
//                                list.add(i);
//                            }
//
//                        }
//                        if (list.size() > 9) {
//                            compoundButton.setChecked(false);
////                                getData();
//                            ToastUtils.showToast("最多同时选10个频点fragment");
//                            return;
//                        }
//                        for (int i = 0; i < student.size(); i++) {
//                            try {
//                                SaopinBean saopinBean = student.get(i);
//                                dbManagersaopin.updateStudenttrue(saopinBean);
////                        dialogPinConfig.update();
//                            } catch (SQLException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        getData();
//
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            } else {//false  取消全选
//
//                if (i == 1) {
//                    try {
//                        ToastUtils.showToast(111 + "");
//                        dbManagersaopin = new DBManagersaopin(mContext);
//                        List<SaopinBean> student = dbManagersaopin.getStudent();
//                        if (student != null && student.size() > 0) {
//
//                            for (int i = 0; i < student.size(); i++) {
//                                try {
//                                    SaopinBean saopinBean = student.get(i);
//                                    dbManagersaopin.updateStudentfalse(saopinBean);
////                        dialogPinConfig.update();
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            getData();
//
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    ToastUtils.showToast(222 + "");
//                    cb_all.setChecked(false);
//                    getData();
//                }
//
//            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法

            Log.d(TAG, "onResume: " + "执行了1");
        } else {
//            getData();

//
        }
    }

    private void getData() { //刷新
        Log.d(TAG, "onResume: " + "执行了2");
        // 相当于onpause()方法
        List<SaopinBean> saopinBeans1 = new ArrayList<>();
        try {
            dbManagersaopin = new DBManagersaopin(mContext);
            List<SaopinBean> studentlist = dbManagersaopin.getStudent();
            if (studentlist != null && studentlist.size() > 0) {
                for (int i = 0; i < studentlist.size(); i++) {
                    if (studentlist.get(i).getYy().equals(name)) {
                        saopinBeans1.add(studentlist.get(i));
                    }
                }
                List<Integer> numlist = new ArrayList<>();
                for (int i = 0; i < saopinBeans1.size(); i++) {
                    numlist.add(i);

                }
                saopinAdapter1 = new SaopinAdapterFragment0(mContext, saopinBeans1, dialogSaopin, name, numlist);
                ry.setAdapter(saopinAdapter1);

                try {
                    dbManagersaopin = new DBManagersaopin(mContext);

                    List<SaopinBean> student = dbManagersaopin.getStudent();
                    List<Integer> lists = new ArrayList<>();//一共的
                    List<Integer> listtrue = new ArrayList<>();//一共的又选中的

                    if (student.size() > 0 && student != null) {
                        for (int i = 0; i < student.size(); i++) {
                            if (student.get(i).getYy().equals(name)) {
                                lists.add(i);
                            }
                        }
                        for (int i = 0; i < student.size(); i++) {
                            if (student.get(i).getYy().equals(name) && student.get(i).getType() == 1) {
                                listtrue.add(i);
                            }

                        }
                    }
                    Log.d("nzq", "onCheckedChanged:lists " + lists.size());
                    Log.d("nzq", "onCheckedChanged:student " + student.size());
                    if (listtrue.size() == lists.size()) {//全部选中
                        dialogSaopin.update(1);
                    }
                    tv_choose.setText("已选择:" + listtrue.size());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//
    }
}
