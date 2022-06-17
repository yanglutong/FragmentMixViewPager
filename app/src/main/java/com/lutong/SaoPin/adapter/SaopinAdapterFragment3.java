package com.lutong.SaoPin.adapter;

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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.OrmSqlLite.Bean.SaopinBean;
import com.lutong.OrmSqlLite.DBManagersaopin;
import com.lutong.R;
import com.lutong.SaoPin.SaopinList.SaoPinSetingActivity;
import com.lutong.Utils.ToastUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaopinAdapterFragment3 extends RecyclerView.Adapter<SaopinAdapterFragment3.ViewHolder> {
    private  String name;
    //    private IDialogSaopin dialogPinConfig;
    private List<SaopinBean> spBeanList;
    private Context context;
    DBManagersaopin dbManager = null;
    int add;
    IDialogSaopin3 dialogSaopin;
    List<Integer> numlist;
    public SaopinAdapterFragment3(SaoPinSetingActivity saoPinSetingActivity) {
        this.context = saoPinSetingActivity;
    }

    public SaopinAdapterFragment3(Context saoPinSetingActivity, List<SaopinBean> spBeanList, IDialogSaopin3 dialogSaopin, String name, List<Integer> numlist) {
        this.context = saoPinSetingActivity;
        this.spBeanList = spBeanList;
        this.dialogSaopin = dialogSaopin;
        this.name = name;
        this.numlist = numlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saopin_c_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        add++;
        holder.tv_pin.setText(spBeanList.get(position).getUp() + "");
        holder.tv_down.setText(spBeanList.get(position).getDown() + "");
        holder.tv_plmn.setText(spBeanList.get(position).getPlmn() + "");
        holder.tv_tf.setText(spBeanList.get(position).getTf() + "");
//        holder.tv_xulie.setText(add + "");
//        if (spBeanList.get(position).getId() == 3) {
//            holder.cb_deful.setChecked(true);
//        }
        holder.tv_xulie.setText(numlist.get(position) + 1 + "");
        holder.tv_band.setText(spBeanList.get(position).getBand() + "");
        holder.ll_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(context, R.style.menuDialogStyleDialogStyle);
                View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_item_title, null);
                TextView tv_title = inflate.findViewById(R.id.tv_title);
                tv_title.setText("确定要删除该频点吗?");
                Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
                bt_confirm.setOnClickListener(new Positiv(spBeanList.get(position).getId(), position, dialog));
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
        holder.tv_yy.setText(spBeanList.get(position).getYy());

        if (spBeanList.get(position).getType() == 1) {
            holder.cb_deful.setChecked(true);
        } else {
            holder.cb_deful.setChecked(false);
        }
        holder.cb_deful.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean cheecked) {
                if (cheecked) {
                    Log.d("nzq", "onCheckedChanged: " + "选中");
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0; i < spBeanList.size(); i++) {
                        if (spBeanList.get(i).getYy().equals(name)) {
                            list.add(i);
                        }

                    }
                    List<Integer>trues=new ArrayList<>();
                    for (int i = 0; i < spBeanList.size(); i++) {
                        if (spBeanList.get(i).getType() == 1) {
                            trues.add(i);
                        }

                    }
                    if (list.size() > 9&&trues.size()>9) {
                        ToastUtils.showToast("最多同时选10个频点");
                        holder.cb_deful.setChecked(false);
                        return;
                    }
                    try {
                        dbManager = new DBManagersaopin(context);
                        SaopinBean saopinBean = spBeanList.get(position);
                        dbManager.updateStudenttrue(saopinBean);
                        List<SaopinBean> student = dbManager.getStudent();
                        List<Integer> lists = new ArrayList<>();//一共的
                        List<Integer> listtrue = new ArrayList<>();//一共的又选中的

                        if (student.size() > 0 && student != null) {
                            for (int i = 0; i < student.size(); i++) {
                                if (student.get(i).getYy().equals(name)) {
                                    lists.add(i);
                                }
                            }
                            for (int i = 0; i < student.size(); i++) {
                                if (student.get(i).getYy().equals(name) && student.get(i).getType() == 1) {
                                    listtrue.add(i);
                                }

                            }
                        }
                        Log.d("nzq", "onCheckedChanged:lists " + lists.size());
                        Log.d("nzq", "onCheckedChanged:student " + student.size());
                        if (listtrue.size() == lists.size()) {//全部选中
                            dialogSaopin.update(1);
                        } else if (lists.size() > 0 && listtrue.size() < lists.size()) {
                            dialogSaopin.update(2);////为全部选中有 一个以上且不是全部选中
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("nzq", "onCheckedChanged: " + "wei选中");
                    try {
//                        dbManager = new DBManagersaopin(context);
//                        dbManager.updateStudentfalse(spBeanList.get(position));
//                        dialogPinConfig.update();

                        dbManager = new DBManagersaopin(context);
                        SaopinBean saopinBean = spBeanList.get(position);
                        dbManager.updateStudentfalse(saopinBean);
                        dialogSaopin.update(3);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        holder.ll_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaopinBean saopinBean = spBeanList.get(position);
                dialogSaopin.upbianji(saopinBean);
            }
        });


    }

    @Override
    public int getItemCount() {
        return spBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_pin, tv_down, tv_plmn, tv_tf, tv_xulie, tv_band, tv_yy;
        CheckBox cb_deful;
        LinearLayout ll_dele, ll_bianji;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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

//
    class Positiv implements View.OnClickListener {
        private int position;
        int id;
        Dialog dialog;

        public Positiv(int id, int position, Dialog dialog) {
            this.id = id;
            this.position = position;
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {
            try {
                dbManager = new DBManagersaopin(context);
                int i1 = dbManager.deleteStudent(spBeanList.get(position));
                notifyDataSetChanged();
                Log.e("nzq", "deleteStudentonClick: " + "执行了" + i1);
                if (i1 == 1) {
                    //删除成功
//                    dialogPinConfig.deleID(i1);
                    spBeanList.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dialog.cancel();
//                    dialogPinConfig.update();
                } else {
                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dialog.cancel();
//                    dialogPinConfig.update();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * //     * @param check 是否全选
     */
    public void allCheck(CompoundButton compoundButton) {


    }

    // 内部接口
    public interface IDialogSaopin3 {
        public void getPosition(String position, int index, String linename);

        public void deleID(int deleid);

        public void up(SaopinBean saopinBean);

        public void update(int type);

        public void update2();

        public void upbianji(SaopinBean saopinBean);//编辑的回调
    }
}
