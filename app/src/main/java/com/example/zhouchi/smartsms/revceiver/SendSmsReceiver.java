package com.example.zhouchi.smartsms.revceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.zhouchi.smartsms.utils.ToastUtils;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class SendSmsReceiver extends BroadcastReceiver{
    public static final String ACTION_SEND_SMS = "com.example.zhouchi.sms";
    @Override
    public void onReceive(Context context, Intent intent) {
        int code = getResultCode();
        if (code == Activity.RESULT_OK) {
            ToastUtils.showToastShort(context, "发送成功");
        }
        else {
            ToastUtils.showToastShort(context, "发送失败");
        }

    }
}
