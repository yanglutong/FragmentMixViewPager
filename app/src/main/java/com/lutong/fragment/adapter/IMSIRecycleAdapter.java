package com.lutong.fragment.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.activity.UpdataParamActivity;
import com.lutong.fragment.TestData;

import java.sql.SQLException;
import java.util.List;

public class IMSIRecycleAdapter extends RecyclerView.Adapter<IMSIRecycleAdapter.MyViewHolder> {
    private Context context;
    private List<TestData> list;
    private View inflater;
    private UpImsiCallBack CallDele;

    //构造方法，传入数据
    public IMSIRecycleAdapter(Context context, List<TestData> list, UpImsiCallBack CallDele) {
        this.context = context;
        this.list = list;
        this.CallDele = CallDele;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.recycler_row4, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //将数据和控件绑定
        holder.textViewxuhao.setText(list.get(position).name);
        holder.IMSI.setText(list.get(position).imsi);
        holder.PHONE.setText(list.get(position).phone);
//        holder.PHONE.setText("15511642000");
        holder.yy.setText(list.get(position).yy);
        holder.ll_bianji.setOnClickListener(new View.OnClickListener() {//编辑修改
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdataParamActivity.class);
                intent.putExtra("id", list.get(position).id + "");
                context.startActivity(intent);
            }
        });
        holder.ll_dele.setOnClickListener(new View.OnClickListener() {//删除IMSI
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定要删除IMSI吗");
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(new Positiv(list.get(position).id, position, dialog));
                Button bt_cancel = inflate.findViewById(R.id.bt_cancel);
                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.CENTER);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//设置Dialog距离底部的距离
//                          将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog.show();//显示对话框
            }
        });
        if (list.get(position).check == false) {
            holder.cb.setChecked(false);
        } else {
            holder.cb.setChecked(true);
        }

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("nzqcb", "onCheckedChanged: " + b);

                CallDele.Call(list.get(position).id, b, holder.cb);
            }
        });
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return list.size();
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        TextView textViewxuhao;
        TextView IMSI;
        TextView PHONE;
        TextView yy;
        LinearLayout ll_bianji;
        LinearLayout ll_dele;

        public MyViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
            textViewxuhao = (TextView) itemView.findViewById(R.id.xh);//名称
            IMSI = (TextView) itemView.findViewById(R.id.name);//名称
            PHONE = (TextView) itemView.findViewById(R.id.pwd);//phone
            yy = (TextView) itemView.findViewById(R.id.note);//phone
            ll_bianji = itemView.findViewById(R.id.ll_bianji);//编辑
            ll_dele = itemView.findViewById(R.id.ll_dele);//删除

        }
    }

    class Positiv implements View.OnClickListener {
        private int position;
        private int id;
        private Dialog dialog;

        public Positiv(int id, int position, Dialog dialog) {
            this.id = id;
            this.position = position;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            DBManagerAddParam dbManagerAddParam = null;
            try {
                dbManagerAddParam = new DBManagerAddParam(context);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                AddPararBean forid = dbManagerAddParam.forid(id);
                int i = dbManagerAddParam.deleteAddPararBean(forid);
                if (i == 1) {
                    ToastUtils.showToast("删除成功");
//                    data.remove(position);
//                    mAdapter.remove(position);
//                    mAdapter.notifyItemRemoved(position);
////
////                    recyclerView.setAdapter(mAdapter);
//                    callBack.CallDele(id, position);
                    CallDele.up();
                    dialog.dismiss();
                    dialog.cancel();
                } else {
                    ToastUtils.showToast("删除失败");
                    dialog.dismiss();
                    dialog.cancel();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public interface UpImsiCallBack {
        void up();

        void Call(int position, boolean checkFlag, CheckBox checkBox);
    }
}

