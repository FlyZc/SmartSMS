package com.example.zhouchi.smartsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;

import org.w3c.dom.Text;

/**
 * Created by zhouchi on 2016/8/20.
 */
public class AutoSearchAdapter extends CursorAdapter{
    public AutoSearchAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return View.inflate(context, R.layout.item_auto_search, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getHolder(view);
        viewHolder.tvAutoSearchName.setText(cursor.getString(cursor.getColumnIndex("display_name")));
        viewHolder.tvAutoSearchAddress.setText(cursor.getString(cursor.getColumnIndex("data1")));
    }
    public ViewHolder getHolder(View view) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }
    class ViewHolder {

        private final TextView tvAutoSearchName;
        private final TextView tvAutoSearchAddress;

        public ViewHolder(View view) {
            tvAutoSearchName = (TextView)view.findViewById(R.id.tvAutoSearchName);
            tvAutoSearchAddress = (TextView)view.findViewById(R.id.tvAutoSearchAddress);

        }
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("data1"));
    }
}
