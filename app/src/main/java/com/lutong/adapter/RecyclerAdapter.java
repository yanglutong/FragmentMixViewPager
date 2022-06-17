package com.lutong.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.R;
import com.lutong.tcp_connect.RecJsonBean;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<RecJsonBean> mDatas;
    private String NetWork;

    public RecyclerAdapter(Context context, ArrayList<RecJsonBean> datas, String i) {
        mContext = context;
        mDatas = datas;
        NetWork = i;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pin_config_list, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        RecJsonBean jsonNrBean = mDatas.get(position);
        if (jsonNrBean != null) {
            if (jsonNrBean.isJzBjState()) {//报警状态
                normalHolder.view_jzBj.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
//                normalHolder.view_jzBj_top.setBackgroundColor(mContext.getResources().getColor(R.color.black));
                normalHolder.liner_item.setBackgroundColor(mContext.getResources().getColor(R.color.redfen));
                normalHolder.view3.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                normalHolder.view1.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                normalHolder.view2.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            } else {
                normalHolder.view_jzBj.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
//                normalHolder.view_jzBj_top.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                normalHolder.liner_item.setBackgroundColor(mContext.getResources().getColor(R.color.wat));
                normalHolder.view3.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                normalHolder.view1.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
                normalHolder.view2.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }
            setDates(normalHolder, position);
        }
    }
    private void setDates(NormalHolder normalHolder, int position){
        String tv_rsrp = mDatas.get(position).getTv_rsrp();
        if (!TextUtils.isEmpty(mDatas.get(position).getTv_rsrp())) {
            if (tv_rsrp.contains(".")) {//去除小数点
                int idx = tv_rsrp.lastIndexOf(".");//查找小数点的位置
                String strNum = tv_rsrp.substring(0, idx);//截取从字符串开始到小数点位置的字符串，就是整数部分
                tv_rsrp = strNum;
            }
        }
        normalHolder.tv_rsrp.setText(tv_rsrp);
        normalHolder.tv_arfcn.setText(mDatas.get(position).getTv_arfcn() + "");
        normalHolder.tv_pci.setText(mDatas.get(position).getTv_pci() + "");
        normalHolder.tv_plmn.setText(mDatas.get(position).getTv_plmn());
        normalHolder.tv_tac.setText(mDatas.get(position).getTv_tac());
        normalHolder.tv_cid.setText(mDatas.get(position).getTv_cid());
        normalHolder.tv_yxj.setText(mDatas.get(position).getTv_yxj() + "");
        normalHolder.tv_tdd.setText(mDatas.get(position).getTv_tdd());
        normalHolder.tv_NetWorkType.setText(mDatas.get(position).getTv_NetWorkType());
        normalHolder.tv_cj_time.setText(mDatas.get(position).getTv_cj_time());
        normalHolder.tv_band.setText(mDatas.get(position).getTv_band());
        normalHolder.tv_forever.setText(mDatas.get(position).getIndex() + "");


        if (mDatas.get(position).getTv_yxj() == 0) {
            normalHolder.tv_yxj.setText("0");
        }
        if (mDatas.get(position).getTv_NetWorkType().equals("NR")) {
            if (mDatas.get(position).getTv_cid().equals("0")) {
                normalHolder.tv_arfcn.setText(mDatas.get(position).getTv_arfcn() + "(0)");
                normalHolder.tv_cid.setText("0");
            } else {
                if (mDatas.get(position).getNrCenterArfcn().equals("0")) {
                    normalHolder.tv_arfcn.setText(mDatas.get(position).getTv_arfcn() + "(0)");
                } else {
                    normalHolder.tv_arfcn.setText(mDatas.get(position).getTv_arfcn() + "(" + mDatas.get(position).getNrCenterArfcn() + ")");
                }
                normalHolder.tv_cid.setText(mDatas.get(position).getTv_cid());
            }
        }
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        private TextView tv_arfcn;
        private TextView tv_cj_time;
        private TextView tv_plmn;
        private TextView tv_rsrp;
        private TextView tv_tac;
        private TextView tv_tdd;
        private TextView tv_yxj;
        private TextView tv_NetWorkType;
        private TextView tv_pci;
        private TextView tv_cid;
        private TextView tv_band;
        private TextView tv_forever;
        private View view_jzBj;
        private View view_jzBj_top;
        private View view1;
        private View view2;
        private View view3;
        private LinearLayout liner_item;

        public NormalHolder(View itemView) {
            super(itemView);
            tv_arfcn = itemView.findViewById(R.id.tv_arfcn);
            tv_cid = itemView.findViewById(R.id.tv_cid);
            tv_pci = itemView.findViewById(R.id.tv_pci);
            tv_plmn = itemView.findViewById(R.id.tv_plmn);
            tv_rsrp = itemView.findViewById(R.id.tv_rsrp);
            tv_tac = itemView.findViewById(R.id.tv_tac);
            tv_tdd = itemView.findViewById(R.id.tv_tdd);
            tv_NetWorkType = itemView.findViewById(R.id.tv_NetWorkType);
            tv_band = itemView.findViewById(R.id.tv_band);
            tv_yxj = itemView.findViewById(R.id.tv_yxj);
            tv_cj_time = itemView.findViewById(R.id.tv_cj_time);
            tv_forever = itemView.findViewById(R.id.tv_forever);
            view_jzBj = itemView.findViewById(R.id.view_jzBj);
//            view_jzBj_top = itemView.findViewById(R.id.view_jzBj_top);
            liner_item = itemView.findViewById(R.id.liner_item);
            view1 = itemView.findViewById(R.id.view1);
            view2 = itemView.findViewById(R.id.view2);
            view3 = itemView.findViewById(R.id.view3);
        }

    }
}