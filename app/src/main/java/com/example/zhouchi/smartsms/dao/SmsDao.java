package com.example.zhouchi.smartsms.dao;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.example.zhouchi.smartsms.global.Constant;
import com.example.zhouchi.smartsms.revceiver.SendSmsReceiver;

import java.util.List;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class SmsDao {
    public static void sendSms(Context context, String address, String body) {
        SmsManager manager = SmsManager.getDefault();
        List<String> smss = manager.divideMessage(body);
        Intent intent = new Intent(SendSmsReceiver.ACTION_SEND_SMS);
        PendingIntent sendIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        for (String text : smss) {
            manager.sendTextMessage(address, null, text, sendIntent, null);
            insertSms(context, address, body);
        }
    }
    public static void insertSms(Context context, String address, String body) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", address);
        contentValues.put("body", body);
        contentValues.put("type", Constant.SMS.TYPE_SEND);
        context.getContentResolver().insert(Constant.URI.URI_SMS, contentValues);

    }

}
