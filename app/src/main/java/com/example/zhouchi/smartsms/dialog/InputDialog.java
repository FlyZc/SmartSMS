package com.example.zhouchi.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;

/**
 * Created by zhouchi on 2016/8/21.
 */
public class InputDialog extends BaseDialog {
    private String title;
    private TextView tvNewGroupTitle;
    private EditText etNewGroupInput;
    private Button btnNewGroupCancle;
    private Button btnNewGroupSure;
    private OnInputDialogListener onInputDialogListener;
    protected InputDialog(Context context, String title, OnInputDialogListener onInputDialogListener) {
        super(context);
        this.title = title;
        this.onInputDialogListener = onInputDialogListener;
    }
    public static void showDialog(Context context, String title, OnInputDialogListener onInputDialogListener) {
        InputDialog dialog = new InputDialog(context, title, onInputDialogListener);
        dialog.setView(new EditText(context));
        dialog.show();
    }
    @Override
    public void initView() {
        setContentView(R.layout.dialog_new_group);
        tvNewGroupTitle = (TextView)findViewById(R.id.tvNewGroupTitle);
        etNewGroupInput = (EditText)findViewById(R.id.etNewGroupInput);
        btnNewGroupCancle = (Button)findViewById(R.id.btnNewGroupCancel);
        btnNewGroupSure = (Button)findViewById(R.id.btnNewGroupSure);

    }

    @Override
    public void initListener() {
        btnNewGroupCancle.setOnClickListener(this);
        btnNewGroupSure.setOnClickListener(this);

    }

    @Override
    public void initData() {
        tvNewGroupTitle.setText(title);

    }

    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            case R.id.btnNewGroupCancel:
                if (onInputDialogListener != null) {
                    onInputDialogListener.onCancle();
                }
                break;
            case R.id.btnNewGroupSure:
                if (onInputDialogListener != null) {
                    onInputDialogListener.onConfirm(etNewGroupInput.getText().toString());
                }
                break;
        }
        dismiss();

    }
    public interface OnInputDialogListener {
        void onCancle();
        void onConfirm(String groupName);
    }
}
