package com.example.zhouchi.smartsms.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.adapter.ConversationAdapter;
import com.example.zhouchi.smartsms.base.BaseActivity;
import com.example.zhouchi.smartsms.bean.Conversation;
import com.example.zhouchi.smartsms.dao.SimpleQueryHandler;
import com.example.zhouchi.smartsms.global.Constant;

/**
 * Created by zhouchi on 2016/8/21.
 */
public class GroupDetailsActivity extends BaseActivity {

    private String groupName;
    private int groupId;
    private ListView lvGroupDetail;
    private SimpleQueryHandler simpleQueryHandler;
    private ConversationAdapter conversationAdapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_group_detail);
        lvGroupDetail = (ListView)findViewById(R.id.lv_group_detail);


    }

    @Override
    public void initListener() {
        lvGroupDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupDetailsActivity.this, ConversationDetailsActivity.class);
                Cursor cursor = (Cursor)conversationAdapter.getItem(i);
                Conversation conversation = Conversation.createFromCursor(cursor);
                intent.putExtra("address", conversation.getAddress());
                intent.putExtra("thread_id", conversation.getThread_id());
                startActivity(intent);

            }
        });

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        groupName = intent.getStringExtra("group_name");
        groupId = intent.getIntExtra("group_id", -1);
        initTitleBar();
        //复用会话列表的所有组件，参数
        conversationAdapter = new ConversationAdapter(this, null);
        lvGroupDetail.setAdapter(conversationAdapter);
        String[] projetion = {
                "sms.body AS snippet",
                "sms.thread_id AS _id",
                "groups.msg_count AS msg_count",
                "address AS address",
                "date AS date"
        };
        simpleQueryHandler = new SimpleQueryHandler(getContentResolver());
        simpleQueryHandler.startQuery(0, conversationAdapter, Constant.URI.URI_SMS_CONVERSATION, projetion, buildQuery(), null, null);




    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarBackBtn:
                finish();
                break;
        }
    }
    private void initTitleBar() {
        ((TextView)findViewById(R.id.tvTitleBarTitle)).setText(groupName);
        findViewById(R.id.ivTitleBarBackBtn).setOnClickListener(this);

    }
    public String buildQuery() {
        Cursor cursor = getContentResolver().query(Constant.URI.URI_THREAD_GROUP_QUERY, new String[] {"thread_id"}, "group_id = " + groupId, null, null);
        String selection = "thread_id in (";
        while (cursor.moveToNext()) {
            if (cursor.isLast()) {
                selection += cursor.getString(0);
            }
            else {
                selection += cursor.getString(0) + ",";
            }
        }
        selection += ")";
        return selection;
    }
}
