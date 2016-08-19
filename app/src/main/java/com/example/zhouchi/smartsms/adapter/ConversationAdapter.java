package com.example.zhouchi.smartsms.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.bean.Conversation;
import com.example.zhouchi.smartsms.dao.ContactDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class ConversationAdapter extends CursorAdapter {

    private boolean isSelectedMode = false;
    private List<Integer> selectedConversationIds = new ArrayList<Integer>();


    public List<Integer> getSelectedConversationIds() {
        return selectedConversationIds;
    }

    public boolean isSelectedMode() {
        return isSelectedMode;
    }

    public void setSelectedMode(boolean selectedMode) {
        isSelectedMode = selectedMode;
    }

    public ConversationAdapter(Context context, Cursor c) {
        super(context, c);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return View.inflate(context, R.layout.item_conversation_list, null);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = getHolder(view);
        Conversation conversation = Conversation.createFromCursor(cursor);
        //判断是否是选择模式
        if (isSelectedMode) {
            viewHolder.ivConversationSelect.setVisibility(View.VISIBLE);
            if (selectedConversationIds.contains(conversation.getThread_id())) {
                viewHolder.ivConversationSelect.setBackgroundResource(R.drawable.common_checkbox_checked);
            }
            else {
                viewHolder.ivConversationSelect.setBackgroundResource(R.drawable.common_checkbox_normal);
            }

        }
        else {
            viewHolder.ivConversationSelect.setVisibility(View.GONE);
        }
        //设置短信内容
        viewHolder.tvConversationContent.setText(conversation.getSnippet());

        String name = ContactDao.getNameByAddress(context.getContentResolver(), conversation.getAddress());
        if (TextUtils.isEmpty(name)) {
            name = conversation.getAddress();
        }
        viewHolder.tvConversationAddress.setText(name + "(" + conversation.getMsg_count() + ")");
        //设置短信时间
        long date = conversation.getDate();
        if (DateUtils.isToday(date)) {
            viewHolder.tvConversationData.setText(DateFormat.getTimeFormat(context).format(date));
        }
        else {
            viewHolder.tvConversationData.setText(DateFormat.getDateFormat(context).format(date));
        }
        //获取联系人的头像
        Bitmap avatar = ContactDao.getAvatarByAddress(context.getContentResolver(), conversation.getAddress());
        if (avatar == null) {
            viewHolder.ivConversationAvata.setBackgroundResource(R.drawable.account_default_header);
        }
        else {
            viewHolder.ivConversationAvata.setBackgroundDrawable(new BitmapDrawable(avatar));
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
    class ViewHolder {

        private TextView tvConversationAddress;
        private TextView tvConversationContent;
        private ImageView ivConversationAvata;
        private TextView tvConversationData;
        private ImageView ivConversationSelect;

        public ViewHolder(View view) {
            ivConversationAvata = (ImageView)view.findViewById(R.id.ivConversationAvtar);
            tvConversationAddress = (TextView)view.findViewById(R.id.tvConversationAddress);
            tvConversationContent =  (TextView)view.findViewById(R.id.tvConversationContent);
            tvConversationData = (TextView)view.findViewById(R.id.tvConversationTime);
            ivConversationSelect = (ImageView)view.findViewById(R.id.ivCheck);
        }
    }
    public void selectSingle(int position) {
        Cursor cursor = (Cursor)getItem(position);
        Conversation conversation = Conversation.createFromCursor(cursor);
        if (selectedConversationIds.contains(conversation.getThread_id())) {
            selectedConversationIds.remove((Integer)conversation.getThread_id());
        }
        else {
            selectedConversationIds.add(conversation.getThread_id());
        }
        notifyDataSetChanged();
    }
    public void selectAll() {
        //遍历cursor取出threadID
        Cursor cursor = getCursor();
        cursor.moveToPosition(-1);
        selectedConversationIds.clear();
        while (cursor.moveToNext()) {
            Conversation conversation = Conversation.createFromCursor(cursor);
            selectedConversationIds.add(conversation.getThread_id());
        }
        notifyDataSetChanged();
    }
    public void cancelSelect() {
        selectedConversationIds.clear();
        notifyDataSetChanged();
    }

}
