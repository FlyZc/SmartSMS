package com.example.zhouchi.smartsms.dao;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class SimpleQueryHandler extends AsyncQueryHandler{
    public SimpleQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        if (cookie != null && cookie instanceof CursorAdapter) {
            ((CursorAdapter) cookie).changeCursor(cursor);
        }
    }

}
