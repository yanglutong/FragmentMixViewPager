package com.lutong.fragment.adapter;



import static com.lutong.Constant.Constant.isCell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lutong.OrmSqlLite.Bean.CellBean;
import com.lutong.OrmSqlLite.Bean.CellBeanGSM;
import com.lutong.OrmSqlLite.Bean.CellBeanNr;
import com.lutong.R;
import com.lutong.Utils.DtUtils;

import java.util.ArrayList;
import java.util.List;


public class My4GAdapter<T> extends RecyclerView.Adapter {
    private List<T> arrayList;
    private Context context;
    private int type;
    public My4GAdapter(int type, ArrayList<T> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.type=type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recy_item, null);
        MyViewHolder holder = new MyViewHolder(inflate);
        return holder;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder baseViewHolder, int position) {
        if(type==2){
            baseViewHolder.itemView.findViewById(R.id.tv_RSRP).setVisibility(View.GONE);
            baseViewHolder.itemView.findViewById(R.id.view_rsrp).setVisibility(View.GONE);
        }else{
            baseViewHolder.itemView.findViewById(R.id.tv_RSRP).setVisibility(View.VISIBLE);
            baseViewHolder.itemView.findViewById(R.id.view_rsrp).setVisibility(View.VISIBLE);
        }
        TextView tv_band = baseViewHolder.itemView.findViewById(R.id.tv_band);
        TextView tv_ARFCN = baseViewHolder.itemView.findViewById(R.id.tv_ARFCN);
        TextView tv_PCI = baseViewHolder.itemView.findViewById(R.id.tv_PCI);
        TextView tv_RSSI = baseViewHolder.itemView.findViewById(R.id.tv_RSSI);
        TextView tv_RSRP = baseViewHolder.itemView.findViewById(R.id.tv_RSRP);
        TextView tv_RSRQ = baseViewHolder.itemView.findViewById(R.id.tv_RSRQ);
        TextView tv_addPD = baseViewHolder.itemView.findViewById(R.id.tv_addPD);
        ImageView iv_addPD = baseViewHolder.itemView.findViewById(R.id.iv_addPD);
        if(type==5){
            List<CellBeanNr> arrayList = (List<CellBeanNr>) this.arrayList;
            if(arrayList.size()>0){
                Log.e("测试数据", "onBindViewHolder: "+arrayList.get(position).getSsSinr() );
                tv_band.setText(arrayList.get(position).getBand());
                tv_ARFCN.setText(arrayList.get(position).getArfcn());
                tv_PCI.setText(arrayList.get(position).getPci());
                tv_RSSI.setText(arrayList.get(position).getSsSinr());
                tv_RSRP.setText(arrayList.get(position).getRsrp());
                tv_RSRQ.setText(arrayList.get(position).getRsrq());
                tv_addPD.setText(arrayList.get(position).getAddArfcn());

                if(position==0){
                    tv_addPD.setVisibility(View.VISIBLE);
                    iv_addPD.setVisibility(View.GONE);
                    tv_addPD.setText(arrayList.get(position).getAddArfcn());
                }else{
                    iv_addPD.setVisibility(View.VISIBLE);
                    iv_addPD.setImageResource(R.mipmap.addblue);
                    iv_addPD.setScaleType(ImageView.ScaleType.CENTER);//按图片的原来size居中显示
                    tv_addPD.setVisibility(View.GONE);
                }


                iv_addPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DtUtils.showDialog(context, "确定插入该频点？", new DtUtils.ShowDialog() {
                            @Override
                            public void showDialog(View view, String type) {

                            }
                        });
                    }
                });//条目的添加频点按钮
                if(isCell){
                    baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
                }else{
                    if(arrayList.get(position).isCellShow()){
                        baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
                    }else{
                        baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.colorJigTextColor );
                    }
                }
            }
            if("NR ARFCN".equals(arrayList.get(position).getArfcn())){
                baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
            }
        }else if(type==2){
            List<CellBeanGSM> arrayList = (List<CellBeanGSM>) this.arrayList;
            if(arrayList.size()>0){
                tv_band.setText(arrayList.get(position).getLac());
                tv_ARFCN.setText(arrayList.get(position).getCid());
                tv_PCI.setText(arrayList.get(position).getDbmRXL());
                tv_RSSI.setText(arrayList.get(position).getArfcn());
                tv_RSRQ.setText(arrayList.get(position).getBsic());
                tv_addPD.setText(arrayList.get(position).getAddArfcn());

                if(position==0){
                    tv_addPD.setVisibility(View.VISIBLE);
                    iv_addPD.setVisibility(View.GONE);
                    tv_addPD.setText(arrayList.get(position).getAddArfcn());
                }else{
                    iv_addPD.setVisibility(View.VISIBLE);
                    iv_addPD.setImageResource(R.mipmap.addblue);
                    iv_addPD.setScaleType(ImageView.ScaleType.CENTER);//按图片的原来size居中显示
                    tv_addPD.setVisibility(View.GONE);
                }


                iv_addPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DtUtils.showDialog(context, "确定插入该频点？", new DtUtils.ShowDialog() {
                            @Override
                            public void showDialog(View view, String type) {

                            }
                        });
                    }
                });//条目的添加频点按钮

        if(isCell){
              baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
        }else{
            if(arrayList.get(position).isCellShow()){
                baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
                }else{
                baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.colorJigTextColor );
                }
        }
            }
         if(arrayList.get(position).getLac().equals("LAC")){
             baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
         }
        }else if(type==4){
            List<CellBean> arrayList = (List<CellBean>) this.arrayList;
            if(arrayList.size()>0){
                tv_band.setText(arrayList.get(position).getBand()+"");
                tv_ARFCN.setText(arrayList.get(position).getEarfcn());
                tv_PCI.setText(arrayList.get(position).getPci());
                tv_RSSI.setText(arrayList.get(position).getRssi());
                tv_RSRP.setText(arrayList.get(position).getRsrp());
                tv_RSRQ.setText(arrayList.get(position).getRsrq());
                tv_addPD.setText(arrayList.get(position).getAddEarfcn());

                if(position==0){
                    tv_addPD.setVisibility(View.VISIBLE);
                    iv_addPD.setVisibility(View.GONE);
                    tv_addPD.setText(arrayList.get(position).getAddEarfcn());
                }else{
                    iv_addPD.setVisibility(View.VISIBLE);
                    iv_addPD.setImageResource(R.mipmap.addblue);
                    iv_addPD.setScaleType(ImageView.ScaleType.CENTER);//按图片的原来size居中显示
                    tv_addPD.setVisibility(View.GONE);
                }


                iv_addPD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DtUtils.showDialog(context, "确定插入该频点？", new DtUtils.ShowDialog() {
                            @Override
                            public void showDialog(View view, String type) {

                            }
                        });
                    }
                });//条目的添加频点按钮
                if(isCell){
                    baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
                }else{
                    if(arrayList.get(position).isCellLiShi()){
                        baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
                    }else{
                        baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.colorJigTextColor );
                    }
                }
            }
            if(arrayList.get(position).getTac().equals("TAC")){
                baseViewHolder.itemView.findViewById(R.id.Re_cell_item).setBackgroundResource(R.color.white );
            }
        }



        //动态添加textview线
            if(position< this.arrayList.size()-1){
                LinearLayout linearLayout = baseViewHolder.itemView.findViewById(R.id.recycler_Line);
                TextView textView = new TextView(context);
                textView.setBackgroundResource(R.color.colorMyr);
                textView.setHeight(1);
                textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                linearLayout.addView(textView);
            }
    }

    @Override
    public int getItemCount() {
            return arrayList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_band ;
        TextView tv_ARFCN ;
        TextView tv_PCI ;
        TextView tv_RSSI ;
        TextView tv_RSRP;
        TextView tv_RSRQ ;
        TextView tv_addPD;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_band = itemView.findViewById(R.id.tv_BAND);
            tv_ARFCN = itemView.findViewById(R.id.tv_ARFCN);
            tv_PCI = itemView.findViewById(R.id.tv_PCI);
            tv_RSRP = itemView.findViewById(R.id.tv_RSRP);
            tv_RSSI = itemView.findViewById(R.id.tv_RSSI);
            tv_RSRQ = itemView.findViewById(R.id.tv_RSRQ);
            tv_addPD = itemView.findViewById(R.id.tv_addPD);
        }
    }
}
