package com.example.zhouchi.smartsms.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.bean.Sms;
import com.example.zhouchi.smartsms.global.Constant;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class ConversationDetailAdapter extends CursorAdapter {
    static final int DURATION = 3 * 60 * 1000;
    private Sms sms;
    private ViewHolder viewHolder;
    private ListView lv;

    public ConversationDetailAdapter(Context context, Cursor c, ListView lv) {
        super(context, c);
        this.lv = lv;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return View.inflate(context, R.layout.item_conversation_detail, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //组件在viewHolder里
        //数据在cursor里
        viewHolder = getHolder(view);
        sms = Sms.createFromCursor(cursor);

        //判断这条短信的时间是否与上一条短信相差三分钟
        if (cursor.getPosition() == 0) {
            //第一条短信不需要处理，直接显示就好
            viewHolder.tvConversationDetailsTime.setVisibility(View.VISIBLE);
            showDate(context);
        }
        else {
            long preDate = getPreviousSmsDate(cursor.getPosition());
            if (sms.getDate() - preDate > DURATION) {
                viewHolder.tvConversationDetailsTime.setVisibility(View.VISIBLE);
                showDate(context);
            }
            else {
                viewHolder.tvConversationDetailsTime.setVisibility(View.GONE);
            }
        }
        //设置短信的内容
        viewHolder.tvConservationDetailsReceive.setVisibility((sms.getType() == Constant.SMS.TYPE_RECEIVE) ? View.VISIBLE : View.GONE);
        viewHolder.tvConservationDetailsSend.setVisibility((sms.getType() == Constant.SMS.TYPE_SEND) ? View.VISIBLE : View.GONE);
        if (sms.getType() == Constant.SMS.TYPE_RECEIVE) {
            viewHolder.tvConservationDetailsReceive.setText(sms.getBody());
        }
        else {
            viewHolder.tvConservationDetailsSend.setText(sms.getBody());
        }

    }
    private ViewHolder getHolder(View view) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        return viewHolder;

    }
    class ViewHolder {

        private final TextView tvConversationDetailsTime;
        private final TextView tvConservationDetailsReceive;
        private final TextView tvConservationDetailsSend;

        public ViewHolder(View view) {
            tvConversationDetailsTime = (TextView)view.findViewById(R.id.tvConversationDetailsTime);
            tvConservationDetailsReceive = (TextView)view.findViewById(R.id.tvConversationDetailsReceive);
            tvConservationDetailsSend = (TextView)view.findViewById(R.id.tvConversationDetailsSend);

        }

    }
    public void showDate(Context context) {
        if (DateUtils.isToday(sms.getDate())) {
            System.out.println(sms.getDate());
            viewHolder.tvConversationDetailsTime.setText(DateFormat.getTimeFormat(context).format(sms.getDate()));
        }
        else {
            viewHolder.tvConversationDetailsTime.setText(DateFormat.getDateFormat(context).format(sms.getDate()));
        }
    }
    private long getPreviousSmsDate(int position) {
        Cursor cursor = (Cursor)getItem(position - 1);
        Sms sms = Sms.createFromCursor(cursor);
        return sms.getDate();
    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
        //让listView滑动到指定的条目上
        lv.setSelection(getCount());

    }
}
