package com.lutong.activity.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.R;
import com.mylhyl.circledialog.CircleDialog;

import java.util.List;

/**
 * show-站
 */

public class TaAdapter extends RecyclerView.Adapter<TaAdapter.MyViewHolder> {

    private TaCallBack callBack;
    private List<Double> list;
    private Context mcontext;

    public TaAdapter(Context mcontext, List<Double> list, TaCallBack callBack) {
        this.mcontext = mcontext;
        this.list = list;
        this.callBack = callBack;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ta_item, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        myViewHolder.tv_text.setText(list.get(i) + "");
        myViewHolder.iv_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mcontext, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                View inflate = LayoutInflater.from(mcontext).inflate(R.layout.item_dibushow6, null);
                //初始化控件
                final EditText et_ta = inflate.findViewById(R.id.et_ta);
                et_ta.setText(list.get(i) + "");
                ImageView iv_finish=inflate.findViewById(R.id.iv_finish);
                iv_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
                bt_adddilao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!TextUtils.isEmpty(et_ta.getText().toString())) {
                            double data = Double.parseDouble(et_ta.getText().toString());
                            callBack.call(i, data);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(mcontext, "输入不能为空", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //将布局设置给Dialog
                dialog.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.CENTER);
                dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog.show();//显示对话框
            }
        });
        myViewHolder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callBack.call(i, list.get(i) + "");
                new CircleDialog.Builder((FragmentActivity) mcontext)
                        .setTitle("")
                        .setText("确定删除TA值么吗")
                        .setTitleColor(Color.parseColor("#00acff"))
                        .setNegative("确定", new myclic(i, list, 2))
                        .setPositive("取消", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myclic implements View.OnClickListener {
        private List<Double> list;
        private String data;
        private int type;
        int i;

        public myclic(int i, int type) {
            this.i = i;
        }

        public myclic(int i, List<Double> list, int type) {
            this.i = i;
            this.type = type;
            this.list = list;
        }

        @Override
        public void onClick(View view) {
            if (list.size() == 1) {
                Toast.makeText(mcontext, "至少一个Ta值,不可删", Toast.LENGTH_LONG).show();
                return;
            }
            if (type == 2)
                callBack.callDele(i);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_text;
        ImageView iv_close, iv_bianji;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
            iv_close = itemView.findViewById(R.id.iv_close);
            iv_bianji = itemView.findViewById(R.id.iv_bianji);

        }
    }
}
