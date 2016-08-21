package com.example.zhouchi.smartsms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.example.zhouchi.smartsms.dialog.ConfirmDialog;
import com.example.zhouchi.smartsms.global.Constant;

/**
 * Created by zhouchi on 2016/8/21.
 */
public class ThreadGroupDao {
    public static boolean hasGroup(ContentResolver contentResolver, int thread_id) {
        Cursor cursor = contentResolver.query(Constant.URI.URI_THREAD_GROUP_QUERY, null, "thread_id = " + thread_id, null, null);
        return cursor.moveToNext();
    }
    public static int getGroupIdByThreadId(ContentResolver contentResolver, int thread_id) {
        Cursor cursor = contentResolver.query(Constant.URI.URI_THREAD_GROUP_QUERY, new String[] {"group_id"}, "thread_id = " + thread_id, null, null);
        cursor.moveToFirst();
        int group_id = cursor.getInt(0);
        return group_id;
    }
    public static boolean insertThreadGroup(ContentResolver contentResolver, int thread_id, int group_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("thread_id", thread_id);
        contentValues.put("group_id", group_id);
        Uri uri = contentResolver.insert(Constant.URI.URI_THREAD_GROUP_INSERT, contentValues);
        //插入会话后，改变群组的会话数量
        if (uri != null) {
            int threadCount = GroupDao.getThreadCount(contentResolver, group_id);
            GroupDao.updateThreadCount(contentResolver, group_id, threadCount + 1);
        }
        return uri != null;

    }
    public static boolean deleteThreadGroupByThreadId(ContentResolver contentResolver, int thrad_id, int group_id) {
        int number = contentResolver.delete(Constant.URI.URI_THREAD_GROUP_DELETE, "thread_id = " + thrad_id, null);
        if (number > 0) {
            int threadCount = GroupDao.getThreadCount(contentResolver, group_id);
            GroupDao.updateThreadCount(contentResolver, group_id, threadCount - 1);
        }
        return number > 0;
    }

}
