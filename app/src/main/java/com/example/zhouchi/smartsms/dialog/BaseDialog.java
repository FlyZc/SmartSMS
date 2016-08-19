package com.example.zhouchi.smartsms.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.zhouchi.smartsms.R;

/**
 * Created by zhouchi on 2016/8/19.
 */
public abstract class BaseDialog extends AlertDialog implements View.OnClickListener{
    protected BaseDialog(Context context) {
        super(context, R.style.BaseDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }
    public abstract void initView();
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View view);

    @Override
    public void onClick(View view) {
        processClick(view);
    }
}
