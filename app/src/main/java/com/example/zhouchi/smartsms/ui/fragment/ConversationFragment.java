package com.example.zhouchi.smartsms.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.adapter.ConversationAdapter;
import com.example.zhouchi.smartsms.base.BaseFragment;
import com.example.zhouchi.smartsms.bean.Conversation;
import com.example.zhouchi.smartsms.dao.SimpleQueryHandler;
import com.example.zhouchi.smartsms.dialog.ConfirmDialog;
import com.example.zhouchi.smartsms.dialog.DeleteDialog;
import com.example.zhouchi.smartsms.global.Constant;
import com.example.zhouchi.smartsms.ui.activity.ConversationDetailsActivity;
import com.example.zhouchi.smartsms.ui.activity.NewMsgActivity;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.List;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class ConversationFragment extends BaseFragment {

    private Button btnEdit;
    private Button btnSeleceAll;
    private Button btnCancleSelect;
    private Button btnDelete;
    private Button btnNew;
    private LinearLayout llSelectMenu;
    private LinearLayout llEditMenu;
    private ListView lvConversation;
    private ConversationAdapter conversationAdapter;
    private List<Integer> selectedConversationIds;
    private DeleteDialog.OnDeleteCancelListener onDeleteCancelListener;
    static final int WHAT_DELETE_COMPLETE = 0;
    static final int WHAT_UPDATE_PROGRESS = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_DELETE_COMPLETE:
                    conversationAdapter.setSelectedMode(false);
                    conversationAdapter.notifyDataSetChanged();
                    showEditMenu();
                    deleteDialog.dismiss();
                    break;
                case WHAT_UPDATE_PROGRESS:
                    deleteDialog.updateProgressAndTitle(msg.arg1);
                    break;
            }
        }
    };
    private DeleteDialog deleteDialog;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, null);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);
        btnNew = (Button) view.findViewById(R.id.btnNew);
        btnSeleceAll = (Button) view.findViewById(R.id.btnSelectAll);
        btnCancleSelect = (Button) view.findViewById(R.id.btnCancelSelect);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        llEditMenu = (LinearLayout)view.findViewById(R.id.llEditMenu);
        llSelectMenu = (LinearLayout) view.findViewById(R.id.llSelectMenu);
        lvConversation = (ListView)view.findViewById(R.id.lvConversation);

        return view;
    }

    @Override
    public void initListener() {
        btnEdit.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnCancleSelect.setOnClickListener(this);
        btnSeleceAll.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        lvConversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (conversationAdapter.isSelectedMode()) {
                    conversationAdapter.selectSingle(i);
                }
                else {
                    Intent intent = new Intent(getActivity(), ConversationDetailsActivity.class);
                    //携带数据，address 和 thread_id
                    Cursor cursor = (Cursor)conversationAdapter.getItem(i);
                    Conversation conversation = Conversation.createFromCursor(cursor);
                    intent.putExtra("address", conversation.getAddress());
                    intent.putExtra("thread_id", conversation.getThread_id());
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    public void initData() {
        //创建一个cursorAdapter对象
        conversationAdapter = new ConversationAdapter(getActivity(), null);
        lvConversation.setAdapter(conversationAdapter);
        SimpleQueryHandler queryHandler = new SimpleQueryHandler(getActivity().getContentResolver());
        String[] projetion = {
                "sms.body AS snippet",
                "sms.thread_id AS _id",
                "groups.msg_count AS msg_count",
                "address AS address",
                "date AS date"
        };
        queryHandler.startQuery(0, conversationAdapter, Constant.URI.URI_SMS_CONVERSATION, projetion, null, null, "date desc");


        //Cursor cursor = getActivity().getContentResolver().query(Constant.URI.URI_SMS_CONVERSATION, null, null, null, null);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btnEdit:
                showSelectMenu();
                conversationAdapter.setSelectedMode(true);
                conversationAdapter.notifyDataSetChanged();
                break;
            case R.id.btnCancelSelect:
                showEditMenu();
                conversationAdapter.setSelectedMode(false);
                conversationAdapter.cancelSelect();
                break;
            case R.id.btnSelectAll:
                conversationAdapter.selectAll();
                break;
            case R.id.btnDelete:
                selectedConversationIds = conversationAdapter.getSelectedConversationIds();
                if (selectedConversationIds.size() == 0) {
                    Toast.makeText(getActivity(), "请选择要删除的对象", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    showDeleteDialog();
                }
                break;
            case R.id.btnNew:
                Intent intent = new Intent(getActivity(), NewMsgActivity.class);
                startActivity(intent);
                break;

        }

    }
    private void showSelectMenu() {
        ViewPropertyAnimator.animate(llEditMenu).translationY(llEditMenu.getHeight()).setDuration(200);
        //延时执行
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 200);
        ViewPropertyAnimator.animate(llSelectMenu).translationY(0).setDuration(200);

    }
    private void showEditMenu() {
        ViewPropertyAnimator.animate(llSelectMenu).translationY(llSelectMenu.getHeight()).setDuration(200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(llEditMenu).translationY(0).setDuration(200);
            }
        }, 200);
    }
    boolean isStopDelete = false;
    public void deleteSms() {
        deleteDialog = DeleteDialog.showDeleteDialog(getActivity(), selectedConversationIds.size(), new DeleteDialog.OnDeleteCancelListener() {
            @Override
            public void onCancle() {
                isStopDelete = true;
            }
        });
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < selectedConversationIds.size(); i++) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isStopDelete) {
                        isStopDelete = false;
                        break;
                    }
                    String where = "thread_id = " + selectedConversationIds.get(i);
                    getActivity().getContentResolver().delete(Constant.URI.URI_SMS, where, null);
                    Message message = handler.obtainMessage();
                    message.what = WHAT_UPDATE_PROGRESS;
                    message.arg1 = i + 1;
                    handler.sendMessage(message);
                }
                selectedConversationIds.clear();
                handler.sendEmptyMessage(WHAT_DELETE_COMPLETE);
            }
        };
        thread.start();
    }
    private void showDeleteDialog() {
        ConfirmDialog.showDialog(getActivity(), "提示", "真的要删除会话吗", new ConfirmDialog.ConfirmListener() {
            @Override
            public void onCancle() {
            }

            @Override
            public void onConfirm() {
                deleteSms();
            }
        });
    }
}
