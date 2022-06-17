package com.lutong.fragment.adapter;

import android.annotation.SuppressLint;
import android.content.res.Resources;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lutong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangChang on 2016/4/1.
 */
public class DemoAdapteradd extends RecyclerView.Adapter<DemoAdapteradd.MyViewHolder> {
    private AddCallBack callBack;
    private List list;
    private ArrayList<String> dataList = new ArrayList<>();
    private Resources res;

    public DemoAdapteradd(List list, AddCallBack callBack) {
        this.callBack = callBack;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.one, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        myViewHolder.button.setText(list.get(i) + "");
        myViewHolder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.call(list.get(i) + "", +i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button button;
        ImageView iv_close;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.bt);
//            linearLayout = itemView.findViewById(R.id.ll);
            iv_close = itemView.findViewById(R.id.iv_close);
        }
    }


}
