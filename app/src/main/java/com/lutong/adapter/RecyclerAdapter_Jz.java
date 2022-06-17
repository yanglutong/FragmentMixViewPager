package com.lutong.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lutong.R;
import com.lutong.ormlite.JzbJBean;

import java.util.ArrayList;


/**
 * @param
 * @author lutong 基站报警
 * @description
 * @return
 * @time 2022/4/6 15:02
 */

public class RecyclerAdapter_Jz extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JzbJBean> mDatas;

    public RecyclerAdapter_Jz(Context context, ArrayList<JzbJBean> datas) {
        mContext = context;
        mDatas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recycler_jzbj_item, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.tv_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onClick(position);
                }
            }
        });
        JzbJBean jzbJBean = mDatas.get(position);
        if (jzbJBean != null) {
            if (position == 0) {
                normalHolder.tv_delete.setVisibility(View.VISIBLE);
                normalHolder.tv_delete2.setVisibility(View.GONE);
                normalHolder.tv_cid.setText("CID");
                normalHolder.tv_tac.setText("TAC");
                normalHolder.tv_count_0.setText("序号");
                normalHolder.tv_count_0.setVisibility(View.VISIBLE);
                normalHolder.tv_count.setVisibility(View.GONE);
                normalHolder.tv_xHao.setVisibility(View.GONE);

            } else {
                normalHolder.tv_count.setText(mDatas.get(position).getCount() + "");
                normalHolder.tv_count_0.setVisibility(View.GONE);
                normalHolder.tv_count.setVisibility(View.VISIBLE);
                normalHolder.tv_xHao.setVisibility(View.VISIBLE);
                normalHolder.tv_delete.setVisibility(View.GONE);
                normalHolder.tv_delete2.setVisibility(View.VISIBLE);
                normalHolder.tv_cid.setText(mDatas.get(position).getCid() + "");
                normalHolder.tv_tac.setText(mDatas.get(position).getTac() + "");
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.e("ylt", "getItemCount: "+mDatas.size()+mDatas.toString());
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {


        private TextView tv_tac;
        private TextView tv_cid;
        private TextView tv_count;
        private TextView tv_delete;
        private TextView tv_delete2;
        private TextView tv_xHao;
        private TextView tv_count_0;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_tac = itemView.findViewById(R.id.tv_tac);
            tv_cid = itemView.findViewById(R.id.tv_cid);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_delete2 = itemView.findViewById(R.id.tv_delete2);
            tv_xHao = itemView.findViewById(R.id.tv_xHao);
            tv_count_0 = itemView.findViewById(R.id.tv_count_0);
        }

    }

    public interface onItemClick {
        void onClick(int view);
    }

    onItemClick onItemClick;

    public void setOnItemClick(RecyclerAdapter_Jz.onItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}