package com.lutong.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.Bean.ZmBean;
import com.lutong.R;

import java.util.List;

public class RyZmAdapter extends RecyclerView.Adapter<RyZmAdapter.ViewHolder> {
    private  List<Integer> listsize;
    private List<ZmBean> list;

    private List<Integer> list1size;
    private List<AddPararBean> dataAll;
    private Context basecontext;
    int add;

    public RyZmAdapter(Context basecontext, List<ZmBean> list, List<Integer>listsize) {
        this.list = list;
        this.basecontext = basecontext;
        this.listsize=listsize;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_lies_zhenma_search2, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_num.setText(listsize.get(position)+"");
        holder.tv_imsi.setText(list.get(position).getImsi() + "");
//        holder.tv_datatime.setText(list.get(position).getDatatime()+"");
        holder.tv_datatime.setText(list.get(position).getTime()+"");
        holder.tv_down.setText(list.get(position).getDown()+"");
        holder.tv_sb.setText(list.get(position).getSb()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_num, tv_datatime, tv_imsi, tv_down, tv_sb;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_imsi = itemView.findViewById(R.id.tv_imsi);
            tv_datatime = itemView.findViewById(R.id.tv_datatime);
            tv_down = itemView.findViewById(R.id.tv_down);
            tv_sb = itemView.findViewById(R.id.tv_sb);


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
//
        }
    }

    public interface IDialogPinConfig {
        void call(String imsi, String sb);
    }
}
