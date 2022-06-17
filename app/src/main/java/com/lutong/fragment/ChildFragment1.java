package com.lutong.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lutong.App.MessageEvent;
import com.lutong.IT.ITMailister;
import com.lutong.OrmSqlLite.Bean.AddPararBean;
import com.lutong.OrmSqlLite.DBManagerAddParam;
import com.lutong.R;
import com.lutong.Service.MyService;
import com.lutong.activity.AddParamActivity;
import com.lutong.base.BaseFragment;
import com.lutong.fragment.adapter.IMSIRecycleAdapter;
import com.lutong.fragment.home_fragment.ViewPagerFragment1;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 目标IMSI-Fragment
 */
public class ChildFragment1 extends BaseFragment implements ITMailister, ViewPagerFragment1.ComponentCallBack {
    private ImageView iv_parmars;
    private View view;
    private RecyclerView recyclerView;
    private DBManagerAddParam dbManagerAddParam;
    private List<AddPararBean> dataAll = null;
    private List<TestData> data = new ArrayList<>();
    TextView nullData;
    private IMSIRecycleAdapter imsiRecycleAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_param, null);
        initView(view);

        return view;
    }

    private MyService.Binder binder = null;

    TextView tv;

    public void initView(View view) {
//        tv = (TextView) view.findViewById(R.id.tv_childfragment_name);
//        tv.setText("Child_Fragment1");
        EventBus.getDefault().register(this);
//        Intent intent=  new Intent(getActivity(), MyService.class);
//        getActivity().bindService(intent, servic, Context.BIND_AUTO_CREATE);
        initData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("Child1", "ChildonHiddenChanged: " + hidden);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("目标IMSI", "onResume: ");
        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {
        iv_parmars = view.findViewById(R.id.iv_parmars);
        iv_parmars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AddParamActivity.class));

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        nullData = view.findViewById(R.id.nullData);
////        bindService(new Intent(this, MyService.class), this, Context.BIND_AUTO_CREATE);
        try {
            dbManagerAddParam = new DBManagerAddParam(getActivity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            dataAll = dbManagerAddParam.getDataAll();
            Log.d("nzq", "findViews: " + dataAll);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dataAll.size() > 0 && dataAll != null) {
            data.clear();
            nullData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < dataAll.size(); i++) {
                TestData testData = new TestData();
                testData.imsi = dataAll.get(i).getImsi();
                testData.name = dataAll.get(i).getName();
                testData.phone = dataAll.get(i).getPhone();
                testData.id = dataAll.get(i).getId();
                testData.yy = dataAll.get(i).getYy();
                testData.check = dataAll.get(i).isCheckbox();
                data.add(testData);
                Log.d("nzqqqqq", "setadapter: " + data);
            }
        } else {
            nullData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
//
//        Log.d("nzqqqqqa", "setadapter: "+data.toString());
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        imsiRecycleAdapter = new IMSIRecycleAdapter(getContext(), data, callBack);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 12, false));
//        mAdapter = new TestBaseAdapter(ParamActivity.this, itTestBaseAdapterCallBack);
//        mAdapter.setDataList(data);
//        mAdapter.setHasMoreData(true);
        recyclerView.setAdapter(imsiRecycleAdapter);

    }

    @Override
    public void doSomething(String value) {
        Log.d("nzqtag", "doSomething: " + value);
    }


    ServiceConnection servic = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MyService.Binder) service;
            binder.getService().setCallback(new MyService.CallBack() {
                @Override
                public void onDataChanged(String data) {//因为在Service里面赋值data是在Thread中进行的，所以我们不能直接在这里将返回的值展示在TextView上。
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e("lcm",data);
////                            tv.setText("data : " + data);
//                        }
//                    });
                }
            });


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onStop() {
        super.onStop();
//        getActivity().unbindService(servic);
        Log.e("lcm", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("lcm", "onDestroyView");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("setUserVisibleHint", "isVisibleToUser" + isVisibleToUser);
        if (isVisibleToUser) {
//            initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.e("lcm", "onDestroy");
    }

    //    /**
//     * 传递数据
//     *
//     * @param message
//     */
//    @Override
//    public void onTypeClick(String message) {
//        Log.d("chiildFragment1", "onTypeClick: "+message);
//    }


    @Override
    public void onDataChanged(String data) {
//        if (tv!=null){
//            tv.setText(data);
//        }
        Log.e("nuqlcm", data);
    }

    IMSIRecycleAdapter.UpImsiCallBack callBack = new IMSIRecycleAdapter.UpImsiCallBack() {
        @Override
        public void up() {
            initData();
        }

        @Override
        public void Call(int id, boolean checkFlag, CheckBox checkBox) {
            try {
                AddPararBean forid = dbManagerAddParam.forid(id);
                forid.setCheckbox(checkFlag);
                int i1 = dbManagerAddParam.updateCheck(forid);
                Log.d("nzqqqqqqqq", "Call: " + i1);


                EventBus.getDefault().post(new MessageEvent(111111, "空口同步成功"));
                List<AddPararBean> dataAll = dbManagerAddParam.getDataAll();
                List<AddPararBean> list = new ArrayList<>();
//                if (dataAll.size() > 0 && dataAll != null) {
//                    for (int i = 0; i < dataAll.size(); i++) {
//                        if (dataAll.get(i).isCheckbox() == true) {
//                            list.add(dataAll.get(i));
//                        }
//                    }
//                    if (list.size() > 0 && list != null) {
//                        if (list.size() > 20) {
//                            ToastUtils.showToast("大于20了");
//                        }
//                    } else {
//                        Log.d("nzqcheck", "Call:3 ");
////
//                    }
//                } else {
//                    Log.d("nzqcheck", "Call:4 ");
//                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
//        String data = (String) event.getData();
//        tv.setText(data);
//        Log.d("ChildFragment1", "onMessageEvent: "+data);

//        switch (event.getCode()){
//            case 100110:
//                Object data1 = event.getData();
//                Log.d("ChildFragment1data1", "onMessageEvent: "+data1);
//                break;
//        }
    }


    ;

}
