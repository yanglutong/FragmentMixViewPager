package com.lutong.Device;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.R;

import java.util.List;

public class RyImsiAdapter extends RecyclerView.Adapter<RyImsiAdapter.ViewHolder> {
    private List<String> list;


    private List<Integer> list1size;
    private Context basecontext;

    private List<AddPararBean> pinConfigBeanList;
    private Context context;
    int add;

    public RyImsiAdapter(Context basecontext, List<Integer> list1size, List<String> list) {
        this.basecontext = basecontext;
        this.list1size = list1size;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_fragment_lies2, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        add++;
        holder.content.setText(list.get(position) + "");
//        holder.title.setText(list1size.get(position) + 1 + "");
        holder.title.setText(add+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
        }
    }


}
