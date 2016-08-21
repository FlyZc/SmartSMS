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

    private static final String authority = "com.zhouchi.smartsms";
    public static final Uri BASE_URI = Uri.parse("content://" + authority);
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_THREAD_GROUP = "thread_group";


    private final int CODE_GROUPS_INSERT = 0;
    private final int CODE_GROUPS_QUERY = 1;
    private final int CODE_GROUPS_UPDATE = 2;
    private final int CODE_GROUPS_DELETE = 3;

    private final int CODE_THREAD_GROUP_INSERT = 4;
    private final int CODE_THREAD_GROUP_QUERY = 5;
    private final int CODE_THREAD_GROUP_UPDATE = 6;
    private final int CODE_THREAD_GROUP_DELETE = 7;

    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    {
        matcher.addURI(authority, "groups/insert", CODE_GROUPS_INSERT);
        matcher.addURI(authority, "groups/query", CODE_GROUPS_QUERY);
        matcher.addURI(authority, "groups/update", CODE_GROUPS_UPDATE);
        matcher.addURI(authority, "groups/delete", CODE_GROUPS_DELETE);

        matcher.addURI(authority, "thread_group/insert", CODE_THREAD_GROUP_INSERT);
        matcher.addURI(authority, "thread_group/query", CODE_THREAD_GROUP_QUERY);
        matcher.addURI(authority, "thread_group/update", CODE_THREAD_GROUP_UPDATE);
        matcher.addURI(authority, "thread_group/delete", CODE_THREAD_GROUP_DELETE);
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
        switch (matcher.match(uri)) {
            case CODE_GROUPS_QUERY:
                Cursor cursor = db.query(TABLE_GROUPS, strings, s, strings1, null, null, s1);
                cursor.setNotificationUri(getContext().getContentResolver(), BASE_URI);
                return cursor;
            case CODE_THREAD_GROUP_QUERY:
                Cursor cursor1 = db.query(TABLE_THREAD_GROUP, strings, s, strings1, null, null, s1);
                cursor1.setNotificationUri(getContext().getContentResolver(), BASE_URI);
                return cursor1;
            default:
                throw new IllegalArgumentException("未识别的Uri:" + uri);

        }
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
                long rawId = db.insert(TABLE_GROUPS, null, contentValues);
                if (rawId == -1) {
                    return null;
                }
                else {
                    getContext().getContentResolver().notifyChange(BASE_URI, null);
                    return ContentUris.withAppendedId(uri, rawId);
                }
            case CODE_THREAD_GROUP_INSERT:
                long rawIdThreadGroup = db.insert(TABLE_THREAD_GROUP, null, contentValues);
                if (rawIdThreadGroup == -1) {
                    return null;
                }
                else {
                    getContext().getContentResolver().notifyChange(BASE_URI, null);
                    return ContentUris.withAppendedId(uri, rawIdThreadGroup);
                }
                default:
                    throw new IllegalArgumentException("未识别的Uri:" + uri);
        }

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_DELETE:
                int deleteLines = db.delete(TABLE_GROUPS,  s, strings);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return deleteLines;
            case CODE_THREAD_GROUP_DELETE:
                int deleteLinesThreadGroup = db.delete(TABLE_THREAD_GROUP,  s, strings);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return deleteLinesThreadGroup;

            default:
                throw new IllegalArgumentException("未识别的Uri:" + uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (matcher.match(uri)) {
            case CODE_GROUPS_UPDATE:
                int updateLines = db.update(TABLE_GROUPS, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return updateLines;
            case CODE_THREAD_GROUP_UPDATE:
                int updateLinesThreadGroup = db.update(TABLE_THREAD_GROUP, contentValues, s, strings);
                getContext().getContentResolver().notifyChange(BASE_URI, null);
                return updateLinesThreadGroup;
            default:
                throw new IllegalArgumentException("未识别的Uri:" + uri);

        }
    }
}
