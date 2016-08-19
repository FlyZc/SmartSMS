package com.example.zhouchi.smartsms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhouchi on 2016/8/18.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    //返回一个View对象，作为Fragment的显示内容
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View v);

    @Override
    public void onClick(View view) {
        processClick(view);
    }
}
