package com.lutong.PinConfig.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lutong.OrmSqlLite.Bean.PinConfigBean5G;
import com.lutong.OrmSqlLite.DBManagerPinConfig5G;
import com.lutong.R;

import java.util.List;

public class PinConfigViewPagerAdapter5G extends RecyclerView.Adapter<PinConfigViewPagerAdapter5G.ViewHolder> {
    private IDialogPinConfig5G dialogPinConfig;
    private List<PinConfigBean5G> pinConfigBeanList;
    private Context context;
    int add;
    List<Integer> integerList;

    public PinConfigViewPagerAdapter5G(Context context, List<PinConfigBean5G> pinConfigBeanList, IDialogPinConfig5G dialogPinConfig, List<Integer> integerList) {
        this.context = context;
        this.pinConfigBeanList = pinConfigBeanList;
        this.dialogPinConfig = dialogPinConfig;
        this.integerList = integerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pin_config_list5g, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        add++;
        holder.tv_zhongxin.setText(pinConfigBeanList.get(position).getZhongxinpindian());
        holder.tv_ssb.setText(pinConfigBeanList.get(position).getSsb());
        holder.tv_pin.setText(pinConfigBeanList.get(position).getUp() + "");
        holder.tv_down.setText(pinConfigBeanList.get(position).getDown() + "");
        holder.tv_plmn.setText(pinConfigBeanList.get(position).getPlmn() + "");
        holder.tv_tf.setText(pinConfigBeanList.get(position).getTf() + "");
        holder.tv_xulie.setText(integerList.get(position)+1 + "");
        if (pinConfigBeanList.get(position).getId() == 3) {
            holder.cb_deful.setChecked(true);
        }
        holder.tv_band.setText(pinConfigBeanList.get(position).getBand() + "");
        holder.ll_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new CircleDialog.Builder((FragmentActivity) context)
//                        .setTitle("")
//                        .setCanceledOnTouchOutside(false)
//                        .setText("确定要删除该频点吗?")
//                        .setTitleColor(Color.parseColor("#00acff"))
//                        .setNegative("确定", new Positiv(pinConfigBeanList.get(position).getId(), position))
//                        .setPositive("取消", null)
//                        .show();
                final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定要删除该频点吗?");
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(new Positiv(pinConfigBeanList.get(position).getId(), position,dialog));
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


        holder.tv_yy.setText(pinConfigBeanList.get(position).getYy());

        if (pinConfigBeanList.get(position).getType() == 1) {
            holder.cb_deful.setChecked(true);
        } else {
            holder.cb_deful.setChecked(false);
        }
//        holder.cb_deful.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean cheecked) {
//                if (cheecked) {
//                    Log.d("nzq", "onCheckedChanged: " + "选中");
//                    try {
//                        DBManagerPinConfig dbManager = new DBManagerPinConfig(context);
//                        PinConfigBean pinConfigBeanup = pinConfigBeanList.get(position);
//                        dialogPinConfig.up(pinConfigBeanup);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.d("nzq", "onCheckedChanged: " + "wei选中");
//
//                        try {
//                            DBManagerPinConfig dbManager = new DBManagerPinConfig(context);
//                            PinConfigBean pinConfigBeanup = pinConfigBeanList.get(position);
//                            dialogPinConfig.up(pinConfigBeanup);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                }
//
//            }
//        });
        holder.ll_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PinConfigBean5G pinConfigBeanup = pinConfigBeanList.get(position);
                dialogPinConfig.upbianji(pinConfigBeanup);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pinConfigBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pin, tv_down, tv_plmn, tv_tf, tv_xulie, tv_band, tv_yy,tv_zhongxin,tv_ssb;
        CheckBox cb_deful;
        LinearLayout ll_dele, ll_bianji;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_zhongxin = itemView.findViewById(R.id.tv_zhongxin);
            tv_ssb = itemView.findViewById(R.id.tv_ssb);
            tv_pin = itemView.findViewById(R.id.tv_pin);
            tv_down = itemView.findViewById(R.id.tv_down);
            tv_plmn = itemView.findViewById(R.id.tv_plmn);
            tv_tf = itemView.findViewById(R.id.tv_tf);
            tv_xulie = itemView.findViewById(R.id.tv_xulie);
            tv_band = itemView.findViewById(R.id.tv_band);
//            tv_pin=itemView.findViewById(R.id.tv_pin);

            tv_yy = itemView.findViewById(R.id.tv_yy);
            cb_deful = itemView.findViewById(R.id.cb_deful);
            ll_bianji = itemView.findViewById(R.id.ll_bianji);
            ll_dele = itemView.findViewById(R.id.ll_dele);

        }
    }


    class Positiv implements View.OnClickListener {
        private  Dialog dialog;
        private int position;
        int id;

        public Positiv(int id, int position,Dialog dialog) {
            this.id = id;
            this.position = position;
            this.dialog=dialog;
        }

        @Override
        public void onClick(View v) {
            try {
                DBManagerPinConfig5G dbManager = new DBManagerPinConfig5G(context);
                int i1 = dbManager.deleteStudent(pinConfigBeanList.get(position));
                notifyDataSetChanged();
                Log.e("nzq", "deleteStudentonClick: " + "执行了" + i1);
                if (i1 == 1) {
                    //删除成功
                    dialogPinConfig.deleID(i1);
                    pinConfigBeanList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } catch (Exception e) {

            }
        }
    }

    // 内部接口
    public interface IDialogPinConfig5G {
        public void getPosition(String position, int index, String linename);

        public void deleID(int deleid);

        public void up(PinConfigBean5G pinConfigBean);
        public void upData();

        public void upbianji(PinConfigBean5G pinConfigBean);//编辑的回调
    }
}
