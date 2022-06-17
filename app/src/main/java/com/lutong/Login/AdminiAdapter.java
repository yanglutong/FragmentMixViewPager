package com.lutong.Login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.AdminBean;
import com.lutong.OrmSqlLite.DBManagerAdmin;
import com.lutong.R;
import com.lutong.Utils.ToastUtils;
import com.lutong.activity.UpdateAdminActivity;

import java.sql.SQLException;
import java.util.List;

public class AdminiAdapter extends RecyclerView.Adapter<AdminiAdapter.ViewHolder> {
    //    private TextView tv_imsi2;
//    private TextView tv_imsi1;
//    private IDialogPinConfig config;
    private List<Integer> list1size;
    private List<AdminBean> dataAll;
    private Context basecontext;
    //    private IDialogPinConfig dialogPinConfig;
//    private List<AddPararBean> pinConfigBeanList;
//    private Context context;
//    int add;
//    private List<AddPararBean> dataAllacl = new ArrayList<>();
    private Callback callback;

    public AdminiAdapter(Context basecontext, List<AdminBean> dataAll, List<Integer> list1size, Callback callback) {
        this.basecontext = basecontext;
        this.dataAll = dataAll;
        this.list1size = list1size;
        this.callback = callback;
//        this.config = config;
//        this.tv_imsi1 = tv_imsi1;
//        this.tv_imsi2 = tv_imsi2;
//        for (int i = 0; i < dataAll.size(); i++) {
//            if (dataAll.get(i).getSb().equals("1")) {
//                dataAllacl.add(dataAll.get(i));
//            }
//        }
//        for (int i = 0; i < dataAll.size(); i++) {
//            if (dataAll.get(i).getSb().equals("2")) {
//                dataAllacl.add(dataAll.get(i));
//            }
//        }
//        for (int i = 0; i < dataAll.size(); i++) {
//            if (dataAll.get(i).getSb().equals("")) {
//                dataAllacl.add(dataAll.get(i));
//            }
//        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_row2, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.xh.setText(list1size.get(position) + 1 + "");
        holder.name.setText(dataAll.get(position).getName() + "");
        holder.pwd.setText(dataAll.get(position).getPwd() + "");
        holder.note.setText(dataAll.get(position).getNote() + "");
        holder.dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(basecontext, R.style.menuDialogStyleDialogStyle);
                View inflate = LayoutInflater.from(basecontext).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定要删除" + dataAll.get(position).getName() + "吗？");
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(new Positiv(dataAll.get(position).getId(), position, dialog));
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
//        holder.tv_imsi.setText(dataAllacl.get(position).getImsi() + "");
//
//        holder.tv_name.setText(dataAllacl.get(position).getName() + "");
//        holder.cb_location.setEnabled(false
//        );
//        if (!TextUtils.isEmpty(dataAllacl.get(position).getSb())) {
//            Log.d("aaaonBindViewHolder", "onBindViewHolder: ");
//        }
//        if (dataAllacl.get(position).getSb() != null) {
//            if (dataAllacl.get(position).getSb().equals("1")) {
//                int clolor = basecontext.getResources().getColor(R.color.main_green);
//                holder.tv_num.setTextColor(clolor);
//                holder.tv_imsi.setTextColor(clolor);
//                holder.tv_name.setTextColor(clolor);
//                holder.tv_type.setTextColor(clolor);
//                holder.tv_device.setTextColor(clolor);
//                holder.tv_type.setText("中标");
//                if (dataAllacl.get(position).getZb().equals("1")) {
//                    holder.cb_location.setChecked(true);
//                }
//            }
//            if (dataAllacl.get(position).getSb().equals("2")) {
//                int clolor = basecontext.getResources().getColor(R.color.redText);
//                holder.tv_num.setTextColor(clolor);
//                holder.tv_imsi.setTextColor(clolor);
//                holder.tv_name.setTextColor(clolor);
//                holder.tv_type.setTextColor(clolor);
//                holder.tv_device.setTextColor(clolor);
//                holder.tv_type.setText("中标");
//                if (dataAllacl.get(position).getZb().equals("2")) {
//                    holder.cb_location.setChecked(true);
//                }
//            }
//            if (dataAllacl.get(position).getSb().equals("")) {
//                holder.tv_type.setText("离线");
//                holder.cb_location.setChecked(false);
//            }
//        }
//        holder.tv_device.setText(dataAllacl.get(position).getSb());
//
//        holder.ll_tab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                config.call(dataAllacl.get(position).getImsi(), dataAllacl.get(position).getSb());
//            }
//        });
        holder.llbianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(basecontext, UpdateAdminActivity.class);
                intent.putExtra("id", dataAll.get(position).getId() + "");
                basecontext.startActivity(intent);
            }
        });
        holder.ll_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(basecontext, LogsActivity.class);
//                intent.putExtra("id", dataAll.get(position).getId() + "");
//                intent.putExtra("name", dataAll.get(position).getName() + "");
//                basecontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataAll.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        TextView tv_num, tv_imsi, tv_type, tv_device, tv_name;
//        CheckBox cb_location;
//        RelativeLayout ll_tab;
//
        TextView xh, name, pwd, note, dele;
        LinearLayout llbianji, ll_log;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            xh = itemView.findViewById(R.id.xh);
            name = itemView.findViewById(R.id.name);
            pwd = itemView.findViewById(R.id.pwd);
            note = itemView.findViewById(R.id.note);
            dele=itemView.findViewById(R.id.dele);
            llbianji = itemView.findViewById(R.id.ll_bianji);
            ll_log = itemView.findViewById(R.id.ll_log);
//            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_num = itemView.findViewById(R.id.tv_num);
//            tv_imsi = itemView.findViewById(R.id.tv_imsi);
//            tv_type = itemView.findViewById(R.id.tv_type);
//            tv_device = itemView.findViewById(R.id.tv_device);
//            cb_location = itemView.findViewById(R.id.cb_location);
//            ll_tab = itemView.findViewById(R.id.ll_tab);
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
            DBManagerAdmin dbManagerAdmin = null;
            try {
                dbManagerAdmin = new DBManagerAdmin(basecontext);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                AdminBean forid = dbManagerAdmin.forid(id);
                int i = dbManagerAdmin.deleteAddPararBean(forid);
                if (i == 1) {
                    ToastUtils.showToast("删除成功");
//                    data.remove(position);
//                    mAdapter.remove(position);
//                    mAdapter.notifyItemRemoved(position);
////
////                    recyclerView.setAdapter(mAdapter);
                    callback.CallDele(id, position);
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

    public interface Callback {
        void call(String id);

        void CallDele(int id, int position);
    }
}
