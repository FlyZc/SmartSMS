package com.example.zhouchi.smartsms.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;
import com.example.zhouchi.smartsms.adapter.AutoSearchAdapter;
import com.example.zhouchi.smartsms.base.BaseActivity;
import com.example.zhouchi.smartsms.dao.SmsDao;
import com.example.zhouchi.smartsms.utils.ToastUtils;

/**
 * Created by zhouchi on 2016/8/19.
 */
public class NewMsgActivity extends BaseActivity {

    private AutoCompleteTextView etNewMsgAddress;
    private EditText etNewMsgContent;
    private AutoSearchAdapter adapter;
    private ImageView ivNewMsgContacts;
    private Button btnNewMsgSend;

    @Override
    public void initView() {
        setContentView(R.layout.activity_new_msg);
        etNewMsgAddress = (AutoCompleteTextView)findViewById(R.id.etNewMsgAddress);
        ivNewMsgContacts = (ImageView)findViewById(R.id.ivNewMsgContacts);
        etNewMsgContent = (EditText)findViewById(R.id.etNewMsgContent);
        btnNewMsgSend = (Button)findViewById(R.id.btnNewMsgSend);
    }

    @Override
    public void initListener() {
        ivNewMsgContacts.setOnClickListener(this);
        btnNewMsgSend.setOnClickListener(this);

    }

    @Override
    public void initData() {

        adapter = new AutoSearchAdapter(this, null);
        etNewMsgAddress.setAdapter(adapter);
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                String[] projection = {
                        "data1",
                        "display_name",
                        "_id"
                };
                String selection = "data1 like '%" + charSequence + "%'";
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, null, null);
                return cursor;

            }
        });
        initTitleBar();

    }

    @Override
    public void processClick(View v) {
        switch (v.getId()) {
            case R.id.ivTitleBarBackBtn:
                finish();
                break;
            case R.id.ivNewMsgContacts:
                //跳转至系统提供的联系人选择Activity
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("vnd.android.cursor.dir/contact");
                startActivityForResult(intent, 0);
                break;
            case R.id.btnNewMsgSend:
                String content = etNewMsgContent.getText().toString();
                String address = etNewMsgAddress.getText().toString();
                if (!TextUtils.isEmpty(content) && !TextUtils.isEmpty(address)) {
                    SmsDao.sendSms(this, address, content);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        if (uri != null) {
            String[] projection = {
                    "_id",
                    "has_phone_number"
            };
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            String _id = cursor.getString(0);
            int has_phone_number = cursor.getInt(1);
            if (has_phone_number == 0) {
                ToastUtils.showToastShort(this, "该联系人没有号码");
            }
            else {
                String selection = "contact_id = " + _id;
                Cursor addressCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {"data1"}, selection, null, null);
                addressCursor.moveToFirst();
                String address = addressCursor.getString(0);
                etNewMsgAddress.setText(address);
                etNewMsgContent.requestFocus();
            }
        }
    }

    public void initTitleBar() {
        findViewById(R.id.ivTitleBarBackBtn).setOnClickListener(this);
        ((TextView)findViewById(R.id.tvTitleBarTitle)).setText("发送短信");
    }
}
