package com.example.zhouchi.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;

import org.w3c.dom.Text;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class ConfirmDialog extends BaseDialog {
    private String title;
    private String content;
    private TextView tvDialogTitle;
    private TextView tvDialogContent;
    private Button btnDialogSure;
    private Button btnDialogCancel;
    private ConfirmListener confirmListener;

    public void setConfirmListener(ConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    protected ConfirmDialog(Context context) {
        super(context);
    }
    public static void showDialog(Context context, String title, String content, ConfirmListener confirmListener) {
        ConfirmDialog dialog = new ConfirmDialog(context);
        dialog.setTitle(title);
        dialog.setContent(content);
        dialog.setConfirmListener(confirmListener);
        dialog.show();

    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_confirm);
        tvDialogTitle = (TextView)findViewById(R.id.tvDialogTitle);
        tvDialogContent = (TextView)findViewById(R.id.tvDialogContent);
        btnDialogCancel = (Button)findViewById(R.id.btnDialogCancel);
        btnDialogSure = (Button)findViewById(R.id.btnDialogSure);

    }

    @Override
    public void initListener() {
        btnDialogCancel.setOnClickListener(this);
        btnDialogSure.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tvDialogTitle.setText(title);
        tvDialogContent.setText(content);

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btnDialogCancel:
                if (confirmListener != null) {
                    confirmListener.onCancle();
                }
                break;
            case R.id.btnDialogSure:
                if (confirmListener != null) {
                    confirmListener.onConfirm();
                }
                break;
        }
        dismiss();
    }
    public interface ConfirmListener {
        void onCancle();
        void onConfirm();
    }
}
