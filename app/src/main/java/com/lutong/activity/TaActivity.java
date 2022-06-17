package com.lutong.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;


import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.lutong.OrmSqlLite.DBManagerJZ;
import com.lutong.R;
import com.lutong.Utils.MyToast;
import com.lutong.Utils.MyUtils;
import com.lutong.activity.Adapter.TaAdapter;
import com.lutong.activity.Adapter.TaCallBack;
import com.lutong.ui.MainActivity2;

import java.sql.SQLException;
import java.util.List;

public class TaActivity extends FragmentActivity implements View.OnClickListener {
    DBManagerJZ dbManagerJZ;
    public static String TAG = TaActivity.ACTIVITY_SERVICE;
    private RecyclerView ry_ta;
    private TaAdapter taAdapter;
    private ImageView finsh, add;
    List<Double> list;
    GuijiViewBeanjizhan forid;
    TaCallBack taCallBack = new TaCallBack() {
        @Override
        public void call(int posion, Double data) {//修改ta的方法

            try {
                list.set(posion, data);
                taAdapter.notifyDataSetChanged();
                String s = MyUtils.listToString(list);
                forid.setTa(s);
                int i = dbManagerJZ.upTa(forid);//
                Log.d(TAG, "dbManagerJZ.upTacall: " + i);
                if (i == 1) {
//                    Toast.makeText(TaActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                    MyToast.showToast("修改成功");
                } else {
//                    Toast.makeText(TaActivity.this, "修改失败", Toast.LENGTH_LONG).show();
                    MyToast.showToast("修改失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void callDele(int i) {//ta值删除的方法


            try {
                if (list.size() > 1) {
                    list.remove(i);
                    taAdapter.notifyDataSetChanged();
                    String s = MyUtils.listToString(list);
                    forid.setTa(s);
                    int ia = dbManagerJZ.upTa(forid);//
                    Log.d(TAG, "dbManagerJZ.upTacall: " + ia);
                    if (ia == 1) {
//                        Toast.makeText(TaActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                        MyToast.showToast("删除成功");
                    } else {
//                        Toast.makeText(TaActivity.this, "删除失败", Toast.LENGTH_LONG).show();
                        MyToast.showToast("删除失败");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ta);
        setStatBar();
        findViews();
        LinearLayoutManager layoutManager = new LinearLayoutManager(TaActivity.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        ry_ta.setLayoutManager(layoutManager);
        Intent d = getIntent();
        if (!TextUtils.isEmpty(d.getStringExtra("id"))) {
            String str_id = d.getStringExtra("id");
            final int id = Integer.parseInt(str_id);
            try {
                dbManagerJZ = new DBManagerJZ(TaActivity.this);
                forid = dbManagerJZ.forid(id);
                String s = forid.getTa();
                list = MyUtils.StringTolist(s);
                taAdapter = new TaAdapter(TaActivity.this, list, taCallBack);
                ry_ta.setAdapter(taAdapter);
                Log.d(TAG, "foridonCreate: " + forid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
//            Toast.makeText(TaActivity.this, "数据错误", Toast.LENGTH_LONG).show();
            MyToast.showToast("数据错误");
        }
    }

    private void findViews() {
        finsh = findViewById(R.id.finsh);
        finsh.setOnClickListener(this);
        add = findViewById(R.id.add);
        add.setOnClickListener(this);
        ry_ta = findViewById(R.id.ry_ta);
    }

    @SuppressLint("NewApi")
    public void setStatBar() {//根据版本设置沉浸式状态栏
        View decorView = getWindow().getDecorView();
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                Intent intent = new Intent(TaActivity.this, MainActivity2.class);
                intent.putExtra("JzListActivity", "0");
                setResult(13, intent);
                finish();
                break;
            case R.id.add:
//                Toast.makeText(TaActivity.this, "你点击了添加", Toast.LENGTH_LONG).show();

                showdilog();
                break;

        }
    }

    private void showdilog() {
        final Dialog dialog = new Dialog(TaActivity.this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(TaActivity.this).inflate(R.layout.item_dibushow6, null);
        //初始化控件
        final EditText et_ta = inflate.findViewById(R.id.et_ta);
//        et_ta.setText("");
        ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et_ta.getText().toString())) {
                    Double data = Double.parseDouble(et_ta.getText().toString());
                    list.add(data);

                    try {
                        taAdapter.notifyDataSetChanged();
                        String s = MyUtils.listToString(list);
                        forid.setTa(s);
                        int ia = dbManagerJZ.upTa(forid);//
                        ry_ta.setAdapter(taAdapter);
                        Log.d(TAG, "dbManagerJZ.upTacall: " + ia);
                        if (ia == 1) {
//                            Toast.makeText(TaActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                            MyToast.showToast("添加成功");
                        } else {
//                            Toast.makeText(TaActivity.this, "添加失败", Toast.LENGTH_LONG).show();
                            MyToast.showToast("添加失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    dialog.dismiss();
                } else {
//                    Toast.makeText(TaActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                    MyToast.showToast("输入不能为空");
                }
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
    @Override
    public void onBackPressed() {//重写返回键方法
        Intent intent = new Intent(TaActivity.this, MainActivity2.class);
        intent.putExtra("JzListActivity", "0");
        setResult(13, intent);
        finish();
    }
}
