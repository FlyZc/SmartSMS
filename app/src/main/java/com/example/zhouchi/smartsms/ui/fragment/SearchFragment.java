package com.example.zhouchi.smartsms.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.adapter.ConversationAdapter;
import com.example.zhouchi.smartsms.base.BaseFragment;
import com.example.zhouchi.smartsms.bean.Conversation;
import com.example.zhouchi.smartsms.dao.SimpleQueryHandler;
import com.example.zhouchi.smartsms.global.Constant;
import com.example.zhouchi.smartsms.ui.activity.ConversationDetailsActivity;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class SearchFragment extends BaseFragment {

    private EditText etSearchInput;
    private ListView lvSearchList;
    private ConversationAdapter conversationAdapter;
    private SimpleQueryHandler simpleQueryHandler;
    String[] projetion = {
            "sms.body AS snippet",
            "sms.thread_id AS _id",
            "groups.msg_count AS msg_count",
            "address AS address",
            "date AS date"
    };

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        etSearchInput = (EditText)view.findViewById(R.id.etSearchInput);
        lvSearchList = (ListView)view.findViewById(R.id.lvSearchList);
        return view;
    }

    @Override
    public void initListener() {
        etSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //文本一改变，此方法就会被调用
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);

                simpleQueryHandler.startQuery(0, conversationAdapter, Constant.URI.URI_SMS_CONVERSATION,
                        projetion, "body like '%" + charSequence + "%'", null, "date desc");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lvSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversationDetailsActivity.class);
                Cursor cursor = (Cursor)conversationAdapter.getItem(i);
                Conversation conversation = Conversation.createFromCursor(cursor);
                intent.putExtra("address", conversation.getAddress());
                intent.putExtra("thread_id", conversation.getThread_id());
                startActivity(intent);

            }
        });

        lvSearchList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });


    }

    @Override
    public void initData() {
        conversationAdapter = new ConversationAdapter(getActivity(), null);
        lvSearchList.setAdapter(conversationAdapter);

        simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        conversationAdapter = new ConversationAdapter(getActivity(), null);
        lvSearchList.setAdapter(conversationAdapter);
        simpleQueryHandler = new SimpleQueryHandler(getActivity().getContentResolver());

    }

    @Override
    public void processClick(View v) {

    }
}
