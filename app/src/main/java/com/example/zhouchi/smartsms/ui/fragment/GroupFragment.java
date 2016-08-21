package com.example.zhouchi.smartsms.ui.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.adapter.GroupListAdapter;
import com.example.zhouchi.smartsms.base.BaseFragment;
import com.example.zhouchi.smartsms.bean.Group;
import com.example.zhouchi.smartsms.dao.GroupDao;
import com.example.zhouchi.smartsms.dao.SimpleQueryHandler;
import com.example.zhouchi.smartsms.dialog.InputDialog;
import com.example.zhouchi.smartsms.dialog.ListDialog;
import com.example.zhouchi.smartsms.global.Constant;
import com.example.zhouchi.smartsms.ui.activity.GroupDetailsActivity;
import com.example.zhouchi.smartsms.utils.ToastUtils;

/**
 * Created by zhouchi on 2016/8/18.
 */
public class GroupFragment extends BaseFragment {

    private Button btnAddGroup;
    private ListView lvGroupList;
    private GroupListAdapter adapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, null);
        btnAddGroup = (Button)view.findViewById(R.id.btnAddGroup);
        lvGroupList = (ListView)view.findViewById(R.id.lvGroupList);
        return view;
    }

    @Override
    public void initListener() {
        btnAddGroup.setOnClickListener(this);
        lvGroupList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)adapter.getItem(i);
                final Group group = Group.createFromCursor(cursor);
                ListDialog.showDialog(getActivity(), "选择操作", new String[] {"修改", "删除"},
                new ListDialog.OnListDialogItemListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                InputDialog.showDialog(getActivity(), "修改群组", new InputDialog.OnInputDialogListener() {
                                    @Override
                                    public void onCancle() {

                                    }

                                    @Override
                                    public void onConfirm(String groupName) {
                                        GroupDao.updateGroupName(getActivity().getContentResolver(), groupName, group.get_id());
                                    }
                                });
                                break;
                            case 1:
                                GroupDao.deleteGroup(getActivity().getContentResolver(), group.get_id());
                                break;
                        }
                    }
                });
                return false;
            }
        });
        lvGroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击群组的条目，跳转至详细会话

                Intent intent = new Intent(getActivity(), GroupDetailsActivity.class);
                Cursor cursor = (Cursor)adapter.getItem(i);
                Group group = Group.createFromCursor(cursor);
                if (group.getThread_count() > 0) {
                    intent.putExtra("group_name", group.getName());
                    intent.putExtra("group_id", group.get_id());
                    startActivity(intent);

                }
                else {
                    ToastUtils.showToastShort(getActivity(), "当前群组没有任何会话");
                }

            }
        });

    }

    @Override
    public void initData() {
        adapter = new GroupListAdapter(getActivity(), null);
        lvGroupList.setAdapter(adapter);
        SimpleQueryHandler handler = new SimpleQueryHandler(getActivity().getContentResolver());
        handler.startQuery(0, adapter, Constant.URI.URI_GROUP_QUERY, null, null, null, "create_date desc");


    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddGroup:
                InputDialog.showDialog(getActivity(), "创建群组", new InputDialog.OnInputDialogListener() {
                    @Override
                    public void onCancle() {

                    }

                    @Override
                    public void onConfirm(String groupName) {
                        if (!TextUtils.isEmpty(groupName)) {
                            GroupDao.insertGroup(getActivity().getContentResolver(), groupName);
                        }
                        else {
                            Toast.makeText(getActivity(), "群组名不能为空", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;
        }

    }
}
