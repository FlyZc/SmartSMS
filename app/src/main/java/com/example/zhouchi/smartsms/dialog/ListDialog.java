package com.example.zhouchi.smartsms.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouchi.smartsms.R;

/**
 * Created by zhouchi on 2016/8/21.
 */
public class ListDialog extends BaseDialog{
    private String title;
    private String[] items;
    private TextView tvGroupListTitle;
    private ListView lvGroupListDialog;
    private OnListDialogItemListener onListDialogItemListener;
    private Context context;

    protected ListDialog(Context context, String title, String[] items, OnListDialogItemListener onListDialogItemListener) {
        super(context);
        this.title = title;
        this.items = items;
        this.onListDialogItemListener = onListDialogItemListener;
        this.context = context;

    }
    public static void showDialog(Context context, String title, String[] items, OnListDialogItemListener onListDialogItemListener) {
        ListDialog dialog = new ListDialog(context, title, items, onListDialogItemListener);
        dialog.show();
    }


    @Override
    public void initView() {
        setContentView(R.layout.dialog_list);
        tvGroupListTitle = (TextView)findViewById(R.id.tvGroupListTitle);
        lvGroupListDialog = (ListView)findViewById(R.id.lvGroupListDialog);


    }

    @Override
    public void initListener() {
        lvGroupListDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onListDialogItemListener != null) {
                    onListDialogItemListener.onItemClick(adapterView, view, i, l);
                }
                dismiss();
            }
        });

    }

    @Override
    public void initData() {
        tvGroupListTitle.setText(title);
        lvGroupListDialog.setAdapter(new ListAdapter());

    }

    @Override
    public void processClick(View view) {

    }
    public interface OnListDialogItemListener {
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    }
    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View itemView = View.inflate(context, R.layout.item_dialog_list, null);
            TextView tvItemDialog = (TextView)itemView.findViewById(R.id.tvItemDialog);
            tvItemDialog.setText(items[i]);
            return itemView;
        }
    }
}
