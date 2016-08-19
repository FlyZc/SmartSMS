package com.example.zhouchi.smartsms.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class DeleteDialog extends BaseDialog{

    private TextView tvDeleteTitle;
    private ProgressBar pbDeleteProgress;
    private Button btnDeleteProgress;
    private OnDeleteCancelListener onDeleteCancelListener;
    private int maxProgress;

    protected DeleteDialog(Context context, int maxProgress, OnDeleteCancelListener onDeleteCancelListener) {
        super(context);
        this.maxProgress = maxProgress;
        this.onDeleteCancelListener = onDeleteCancelListener;
    }
    public static DeleteDialog showDeleteDialog(Context context, int maxProgress, OnDeleteCancelListener onDeleteCancelListener) {
        DeleteDialog deleteDialog = new DeleteDialog(context, maxProgress, onDeleteCancelListener);
        deleteDialog.show();
        return deleteDialog;
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_delete_progress);
        tvDeleteTitle = (TextView) findViewById(R.id.tvDeleteTitle);
        pbDeleteProgress = (ProgressBar)findViewById(R.id.pbDeleProgress);
        btnDeleteProgress = (Button)findViewById(R.id.btnDeleteCancel);


    }

    @Override
    public void initListener() {
        btnDeleteProgress.setOnClickListener(this);

    }

    @Override
    public void initData() {
        tvDeleteTitle.setText("正在删除0/" + maxProgress);
        pbDeleteProgress.setMax(maxProgress);



    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.btnDeleteCancel:
                if (onDeleteCancelListener != null) {
                    onDeleteCancelListener.onCancle();
                }
                dismiss();
                break;

        }
    }
    public interface OnDeleteCancelListener {
        void onCancle();
    }
    public void updateProgressAndTitle(int progress) {
        pbDeleteProgress.setProgress(progress);
        tvDeleteTitle.setText("正在删除(" + progress + "/" + maxProgress + ")");

    }
}
