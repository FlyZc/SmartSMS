package com.example.zhouchi.smartsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.bean.Group;

/**
 * Created by zhouchi on 2016/8/21.
 */
public class GroupListAdapter extends CursorAdapter {
    public GroupListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return View.inflate(context, R.layout.item_group_list, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = getHolder(view);
        Group group = Group.createFromCursor(cursor);
        holder.tvGroupListName.setText(group.getName() + "(" + group.getThread_count() + ")");
        if (DateUtils.isToday(group.getCreate_date())) {
            holder.tvGroupListDate.setText(DateFormat.getTimeFormat(context).format(group.getCreate_date()));
        }
        else {
            holder.tvGroupListDate.setText(DateFormat.getDateFormat(context).format(group.getCreate_date()));
        }


    }
    public ViewHolder getHolder(View view) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        return viewHolder;
    }
    class ViewHolder{

        private final TextView tvGroupListDate;
        private final TextView tvGroupListName;

        public ViewHolder(View view) {
            tvGroupListName = (TextView)view.findViewById(R.id.tvGroupListName);
            tvGroupListDate = (TextView)view.findViewById(R.id.tvGroupListDate);
        }
    }
}
