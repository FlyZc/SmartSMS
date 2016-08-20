package com.example.zhouchi.smartsms.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.adapter.ConversationDetailAdapter;
import com.example.zhouchi.smartsms.base.BaseActivity;
import com.example.zhouchi.smartsms.dao.ContactDao;
import com.example.zhouchi.smartsms.dao.SimpleQueryHandler;
import com.example.zhouchi.smartsms.dao.SmsDao;
import com.example.zhouchi.smartsms.global.Constant;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class ConversationDetailsActivity extends BaseActivity{

    private String address;
    private int thread_id;
    private SimpleQueryHandler queryHandler;
    private ConversationDetailAdapter conversationDetailAdapter;
    private ListView lvConversationDetails;
    private EditText etConversationDetails;
    private Button btnConversationDetails;

    @Override
    public void initView() {
        setContentView(R.layout.activity_conversation_details);
        lvConversationDetails = (ListView)findViewById(R.id.lvConversationDetails);
        etConversationDetails = (EditText)findViewById(R.id.etConversationDetails);
        btnConversationDetails = (Button)findViewById(R.id.btnConversationDetails);
        lvConversationDetails.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    @Override
    public void initListener() {
        btnConversationDetails.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //获取传递过来的intent对象
        Intent intent = getIntent();
        if (intent != null) {
            address = intent.getStringExtra("address");
            thread_id = intent.getIntExtra("thread_id", -1);
            initTitleBar();
        }

        conversationDetailAdapter = new ConversationDetailAdapter(this, null, lvConversationDetails);
        lvConversationDetails.setAdapter(conversationDetailAdapter);
        String[] projection = {
                "_id",
                "body",
                "type",
                "date"
        };
        String selection = "thread_id = " + thread_id;
        queryHandler = new SimpleQueryHandler(getContentResolver());
        queryHandler.startQuery(0, conversationDetailAdapter, Constant.URI.URI_SMS, projection, selection, null, "date");
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarBackBtn:
                finish();
                break;
            case R.id.btnConversationDetails:
                String body = etConversationDetails.getText().toString();
                if (!TextUtils.isEmpty(body)) {
                    SmsDao.sendSms(this, address, body);
                    etConversationDetails.setText("");
                }
                break;

        }

    }
    public void initTitleBar() {
        findViewById(R.id.ivTitleBarBackBtn).setOnClickListener(this);
        String name = ContactDao.getNameByAddress(getContentResolver(), address);

        ((TextView)findViewById(R.id.tvTitleBarTitle)).setText(TextUtils.isEmpty(name) ? address : name);
    }
}
