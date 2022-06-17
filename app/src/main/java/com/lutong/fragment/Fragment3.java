package com.lutong.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lutong.R;
import com.lutong.base.BaseFragment;


public class Fragment3 extends BaseFragment   {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragmernt3, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
//        TextView tv = (TextView) view.findViewById(R.id.tv_fragment_name);
//        tv.setText("Fragment3");
    }

    @Override
    public void initData() {

    }


}
