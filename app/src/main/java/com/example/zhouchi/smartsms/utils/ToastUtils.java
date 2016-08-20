package com.example.zhouchi.smartsms.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class ToastUtils {
    public static void showToastShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
