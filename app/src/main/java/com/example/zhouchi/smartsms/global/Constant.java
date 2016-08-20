package com.example.zhouchi.smartsms.global;

import android.net.Uri;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class Constant {
    public interface URI {
        Uri URI_SMS_CONVERSATION = Uri.parse("content://sms/conversations");
        Uri URI_SMS = Uri.parse("content://sms");
        Uri URI_GROUP_INSERT = Uri.parse("content://com.zhouchi.smartsms/groups/insert");
    }
    public interface SMS {
        int TYPE_RECEIVE = 1;
        int TYPE_SEND = 2;
    }


}
