package com.example.zhouchi.smartsms.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.zhouchi.smartsms.dao.GroupOpenHelper;

/**
 * Created by zhouchi on 2016/8/20.
 */
public class GroupProvider extends ContentProvider{
    GroupOpenHelper groupOpenHelper;
    private SQLiteDatabase db;
    private final String authority = "com.zhouchi.smartsms";
    private final int CODE_GROUPS_INSERT = 0;
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    {
        matcher.addURI(authority, "groups/insert", CODE_GROUPS_INSERT);
    }

    @Override
    public boolean onCreate() {
        groupOpenHelper = GroupOpenHelper.getInstance(getContext());
        db = groupOpenHelper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_INSERT:
                long rawId = db.insert("groups", null, contentValues);
                if (rawId == -1) {
                    return null;
                }
                else {
                    return ContentUris.withAppendedId(uri, rawId);
                }

        }
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
