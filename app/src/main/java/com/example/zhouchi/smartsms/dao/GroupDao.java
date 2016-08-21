package com.example.zhouchi.smartsms.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.zhouchi.smartsms.global.Constant;

/**
 * Created by zhouchi on 2016/8/21.
 */
public class GroupDao {
    public static void insertGroup(ContentResolver contentResolver, String groupName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", groupName);
        contentValues.put("thread_count", 0);
        contentValues.put("create_date", System.currentTimeMillis());
        contentResolver.insert(Constant.URI.URI_GROUP_INSERT, contentValues);

    }
    public static void updateGroupName(ContentResolver contentResolver, String newGroupName, int _id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newGroupName);
        contentResolver.update(Constant.URI.URI_GROUP_UPDATE, contentValues, "_id = " + _id, null);
    }
    public static void deleteGroup(ContentResolver contentResolver, int _id) {
        contentResolver.delete(Constant.URI.URI_GROUP_DELETE, "_id = " + _id, null);
    }
    public static String getGroupNameByGroupId(ContentResolver contentResolver, int _id) {
        String name = null;
        Cursor cursor = contentResolver.query(Constant.URI.URI_GROUP_QUERY, new String[] {"name"}, "_id = " + _id, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        return name;
    }
    public static int getThreadCount(ContentResolver contentResolver, int _id) {
        int threadCount = -1;
        Cursor cursor = contentResolver.query(Constant.URI.URI_GROUP_QUERY, new String[] {"thread_count"}, "_id = " + _id, null, null);
        if (cursor.moveToNext()) {
            threadCount = cursor.getInt(0);
        }
        return threadCount;

    }
    public static void updateThreadCount(ContentResolver contentResolver, int _id, int newThreadCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("thread_count", newThreadCount);
        contentResolver.update(Constant.URI.URI_GROUP_UPDATE, contentValues, "_id = " + _id, null);
    }

}
