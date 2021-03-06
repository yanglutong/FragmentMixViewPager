package com.lutong.activity;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.R;
import com.lutong.Utils.MainUtils2;
import com.lutong.Utils.ToastUtils;
import com.lutong.Views.UtilsView;
import com.lutong.base.BaseActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AddParamActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_findish;
    EditText ed_phone, ed_yy, ed_name, ed_imsi;
    Button button, bt_cancel;
    DBManagerAddParam dbManagerAddParam = null;
    Spinner sp1;
    ArrayAdapter adapter1;
    private String spinnerS1 = "";
    ImageView ivadd;
    @Override
    protected void initQx() {

    }

    @Override
    protected void init() {
        try {
            dbManagerAddParam = new DBManagerAddParam(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void findViews() {
        ivadd=findViewById(R.id.iv_add);
        ivadd.setVisibility(View.GONE);
        bt_cancel = findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sp1 = findViewById(R.id.sp1);
        List<String> list = new ArrayList<>();
        list.add("็งปๅจ");
        list.add("่้");
        list.add("็ตไฟก");
        adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item
                , list);
        sp1.setAdapter(adapter1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerS1 = (String) adapter1.getItem(i);//ไธๆๆก1้ๆฉ็ๆฐๆฎ

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        LinearLayout layout = findViewById(R.id.ll_tab);
        TextView title = findViewById(R.id.titles);
        ImageView imageView = findViewById(R.id.iv_menu);
        iv_findish = findViewById(R.id.iv_finish);
        iv_findish.setOnClickListener(this);
        UtilsView.setViewVisibility(this, layout, title, imageView, getResources().getString(R.string.addparam_activity), false, iv_findish, true);
        //็จๅฐ็็ปไปถView
        ed_phone = findViewById(R.id.ed_phone);
        ed_imsi = findViewById(R.id.ed_imsi);
        ed_name = findViewById(R.id.ed_name);
        ed_yy = findViewById(R.id.ed_yy);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        //ๆ?นๆฎ่พๅฅ็ๆฐๆฎๅคๆญ่ฟ่ฅๅ
        ed_imsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(ed_imsi.getText().toString())) {
                    String yy = MainUtils2.YY(ed_imsi.getText().toString());
                    ed_yy.setText(yy);
                }

            }
        });
    }

    @Override
    protected int getView() {
        return R.layout.activity_add_param;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_finish:
                finish();
                break;
            case R.id.button:
                IsImpty();
                break;
        }
    }

    private void IsImpty() {
        List<AddPararBean> addPararBeans = null;
        try {
            addPararBeans = dbManagerAddParam.getDataAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d("AddPararBean", "insertData: " + addPararBeans);
//        if (TextUtils.isEmpty(ed_name.getText().toString())) {
//            ToastUtils.showToast("ๅ็งฐไธ่ฝไธบ็ฉบ");
//            return;
//        }
//        if (TextUtils.isEmpty(ed_phone.getText().toString())) {
//            ToastUtils.showToast("็ต่ฏไธ่ฝไธบ็ฉบ");
//            return;
//        }
//        if (!RegexUtils.isMobileExact(ed_phone.getText().toString())) {
//            ToastUtils.showToast("่ฏท่พๅฅๆญฃ็กฎ็ๆๆบๅท");
//            return;
//        }

        if (TextUtils.isEmpty(ed_imsi.getText().toString())) {
            ToastUtils.showToast("IMSIไธ่ฝไธบ็ฉบ");
            return;
        }
        if (ed_imsi.getText().length() != 15) {
            ToastUtils.showToast("่ฏท่พๅฅ15ไฝIMSIๅท");
            return;
        }
//        if (TextUtils.isEmpty(ed_yy.getText().toString())) {
//            ToastUtils.showToast("่ฟ่ฅๅไธ่ฝไธบ็ฉบ");
//            return;
//        }
//        new CircleDialog.Builder()
//                .setTitle("")
//                .setText("็กฎๅฎ่ฆๆทปๅ?IMSI็ฎก็ๅ")
//                .setTitleColor(Color.parseColor("#00acff"))
//                .setNegative("็กฎๅฎ", new Positiv())
//                .setPositive("ๅๆถ", null)
//                .show(getSupportFragmentManager());
        final Dialog dialog = new Dialog(AddParamActivity.this, R.style.menuDialogStyleDialogStyle);
        View inflate = LayoutInflater.from(AddParamActivity.this).inflate(R.layout.dialog_item_title, null);
        TextView tv_title = inflate.findViewById(R.id.tv_title);
        tv_title.setText("็กฎๅฎ่ฆๆทปๅ?็ฎๆ?IMSIๅ?");
        Button bt_confirm = inflate.findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(new Positiv(dialog));
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
        //่ทๅๅฝๅActivityๆๅจ็็ชไฝ
        Window dialogWindow = dialog.getWindow();
        //่ฎพ็ฝฎDialogไป็ชไฝๅบ้จๅผนๅบ
        dialogWindow.setGravity(Gravity.CENTER);
        //่ทๅพ็ชไฝ็ๅฑๆง
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                           lp.y = 20;//่ฎพ็ฝฎDialog่ท็ฆปๅบ้จ็่ท็ฆป
//                          ๅฐๅฑๆง่ฎพ็ฝฎ็ป็ชไฝ
        dialogWindow.setAttributes(lp);
        dialog.show();//ๆพ็คบๅฏน่ฏๆก
    }

    private void insertData() throws SQLException {
        AddPararBean addPararBean = new AddPararBean();
        addPararBean.setId(0);
        addPararBean.setImsi(ed_imsi.getText().toString() + "");
        addPararBean.setName(ed_name.getText().toString() + "");
        addPararBean.setPhone(ed_phone.getText().toString() + "");
        addPararBean.setYy(spinnerS1 + "");
        addPararBean.setCheckbox(true);
        int i = dbManagerAddParam.insertAddPararBean(addPararBean);

        if (i == 1) {
            ToastUtils.showToast("ๆทปๅ?็ฎๆ?IMSIๆๅ");
            finish();
        } else {
            ToastUtils.showToast("ๆทปๅ?็ฎๆ?IMSIๅคฑ่ดฅ");

        }
        List<AddPararBean> addPararBeans = dbManagerAddParam.getDataAll();
        Log.d("AddPararBean", "insertData: " + addPararBeans);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    class Positiv implements View.OnClickListener {
        Dialog dialog;

        public Positiv(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            try {
                insertData();
                dialog.dismiss();
                dialog.cancel();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
