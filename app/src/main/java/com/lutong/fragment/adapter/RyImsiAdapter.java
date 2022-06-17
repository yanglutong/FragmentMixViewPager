package com.lutong.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.R;

import java.util.ArrayList;
import java.util.List;

public class RyImsiAdapter extends RecyclerView.Adapter<RyImsiAdapter.ViewHolder> {
    private TextView tv_imsi2;
    private TextView tv_imsi1;
    private IDialogPinConfig config;
    private List<Integer> list1size;
    private List<AddPararBean> dataAll;
    private Context basecontext;
    private IDialogPinConfig dialogPinConfig;
    private List<AddPararBean> pinConfigBeanList;
    private Context context;
    int add;
    private List<AddPararBean> dataAllacl = new ArrayList<>();

    public RyImsiAdapter(Context basecontext, List<AddPararBean> dataAll, List<Integer> list1size, IDialogPinConfig config, TextView tv_imsi1, TextView tv_imsi2) {
        this.basecontext = basecontext;
        this.dataAll = dataAll;
        this.list1size = list1size;
        this.config = config;
        this.tv_imsi1 = tv_imsi1;
        this.tv_imsi2 = tv_imsi2;
        for (int i = 0; i < dataAll.size(); i++) {
            if (dataAll.get(i).getSb().equals("4G")) {
                dataAllacl.add(dataAll.get(i));
            }
        }
        for (int i = 0; i < dataAll.size(); i++) {
            if (dataAll.get(i).getSb().equals("5G")) {
                dataAllacl.add(dataAll.get(i));
            }
        }
        for (int i = 0; i < dataAll.size(); i++) {
            if (dataAll.get(i).getSb().equals("")) {
                dataAllacl.add(dataAll.get(i));
            }
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_lies2, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.tv_num.setText(list1size.get(position) + "");
        holder.tv_imsi.setText(dataAllacl.get(position).getImsi() + "");

        holder.tv_name.setText(dataAllacl.get(position).getName() + "");
        holder.cb_location.setEnabled(false
        );
        if (!TextUtils.isEmpty(dataAllacl.get(position).getSb())) {
            Log.d("aaaonBindViewHolder", "onBindViewHolder: ");
        }
        if (dataAllacl.get(position).getSb() != null) {
            if (dataAllacl.get(position).getSb().equals("4G")) {
                int clolor = basecontext.getResources().getColor(R.color.main_green);
                holder.tv_num.setTextColor(clolor);
                holder.tv_imsi.setTextColor(clolor);
                holder.tv_name.setTextColor(clolor);
                holder.tv_type.setTextColor(clolor);
                holder.tv_device.setTextColor(clolor);
                holder.tv_type.setText("中标");
                if (dataAllacl.get(position).getZb().equals("1")) {
                    holder.cb_location.setChecked(true);
                }
            }
            if (dataAllacl.get(position).getSb().equals("5G")) {
                int clolor = basecontext.getResources().getColor(R.color.redText);
                holder.tv_num.setTextColor(clolor);
                holder.tv_imsi.setTextColor(clolor);
                holder.tv_name.setTextColor(clolor);
                holder.tv_type.setTextColor(clolor);
                holder.tv_device.setTextColor(clolor);
                holder.tv_type.setText("中标");
                if (dataAllacl.get(position).getZb().equals("2")) {
                    holder.cb_location.setChecked(true);
                }
            }
            if (dataAllacl.get(position).getSb().equals("")) {
                holder.tv_type.setText("离线");
                holder.cb_location.setChecked(false);
            }
        }
        holder.tv_device.setText(dataAllacl.get(position).getSb());

        holder.ll_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.call(dataAllacl.get(position).getImsi(), dataAllacl.get(position).getSb());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataAllacl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num, tv_imsi, tv_type, tv_device, tv_name;
        CheckBox cb_location;
        RelativeLayout ll_tab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_imsi = itemView.findViewById(R.id.tv_imsi);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_device = itemView.findViewById(R.id.tv_device);
            cb_location = itemView.findViewById(R.id.cb_location);
            ll_tab = itemView.findViewById(R.id.ll_tab);
        }
    }


    class Positiv implements View.OnClickListener {
        private int position;
        int id;

        public Positiv(int id, int position) {
            this.id = id;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
//            try {
//                DBManagerPinConfig dbManager = new DBManagerPinConfig(context);
//                int i1 = dbManager.deleteStudent(pinConfigBeanList.get(position));
//                notifyDataSetChanged();
//                Log.e("nzq", "deleteStudentonClick: " + "执行了" + i1);
//                if (i1 == 1) {
//                    //删除成功
//                    dialogPinConfig.deleID(i1);
//                    pinConfigBeanList.remove(position);
//                    notifyItemRemoved(position);
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//
//            }
        }
    }

    public interface IDialogPinConfig {
        void call(String imsi, String sb);
    }
}
