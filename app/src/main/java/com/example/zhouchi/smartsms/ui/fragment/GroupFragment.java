package com.example.zhouchi.smartsms.ui.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.base.BaseFragment;
import com.example.zhouchi.smartsms.global.Constant;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class GroupFragment extends BaseFragment {

    private Button btnAddGroup;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, null);
        btnAddGroup = (Button)view.findViewById(R.id.btnAddGroup);
        return view;
    }

    @Override
    public void initListener() {
        btnAddGroup.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddGroup:
                ContentValues values = new ContentValues();
                values.put("name", "天下英雄也就这几个");
                values.put("create_date", System.currentTimeMillis());
                values.put("thread_count", 0);
                getActivity().getContentResolver().insert(Constant.URI.URI_GROUP_INSERT, values);
                break;
        }

    }
}
