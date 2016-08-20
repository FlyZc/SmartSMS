package com.example.zhouchi.smartsms.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhouchi on 2016/8/20.
 */
public class GroupOpenHelper extends SQLiteOpenHelper{
    private static GroupOpenHelper instance;
    public static GroupOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new GroupOpenHelper(context, "group.db", null, 1);
        }
        return instance;
    }
    private GroupOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table groups(" +
            "_id integer primary key autoincrement, " +
            "name varchar, " +
            "create_date integer, " +
            "thread_count integer" +
            ")");
        sqLiteDatabase.execSQL("create table thread_group(" +
                "_id integer primary key autoincrement, " +
                "group_id integer, " +
                "thread_id integer" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
