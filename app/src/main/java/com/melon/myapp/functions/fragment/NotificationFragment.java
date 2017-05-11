package com.melon.myapp.functions.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.melon.myapp.BaseFragment;
import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.myapp.bean.Notify;
import com.melon.myapp.db.DatabaseHelper;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.util.ViewHolder;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NotificationFragment extends BaseFragment {
    private EditText et_notify_input;
    private ListView lv_notification;
    private LayoutInflater mInflater;
    private DatabaseHelper mDatabaseHelper;
    private RuntimeExceptionDao mDao;
    private MyAdapter mAdapter;
    private List<Notify> mNotify = new ArrayList<>();
    private BroadcastReceiver mNotifyReceiver;

    @Override
    protected void initData() {
        mDao = getDBHelper().getNotifyDao();
        getMyData();
        mAdapter = new MyAdapter();
        lv_notification.setAdapter(mAdapter);
        lv_notification.setSelection(mNotify.size());

        registerBroadcast();
    }

    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_NEW_NOTIFY);
        mNotifyReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 更新信息
                LogUtils.e("onReceive : "+mNotify.size());
                getMyData();
                mAdapter.notifyDataSetChanged();

                lv_notification.setSelection(mNotify.size());
            }
        };
        getActivity().registerReceiver(mNotifyReceiver, intentFilter);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        this.mInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        et_notify_input = (EditText) view.findViewById(R.id.et_notify_input);
        view.findViewById(R.id.et_notify_send).setOnClickListener(this);
        lv_notification = (ListView) view.findViewById(R.id.lv_notification);
//        view.findViewById(R.id.et_notify_clear).setOnClickListener(this);
        initEmptyView();
        return view;
    }

    private void getMyData() {
        try {
            List notifys = mDao.queryBuilder().orderBy("time", false).limit(80L).query();
            if(notifys!=null){
                Collections.reverse(notifys);
                mNotify.clear();
                mNotify.addAll(notifys);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initEmptyView() {
        TextView emptyView = new TextView(getContext());
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无内容");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) lv_notification.getParent()).addView(emptyView);
        lv_notification.setEmptyView(emptyView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        getActivity().unregisterReceiver(mNotifyReceiver);
        if (mDatabaseHelper != null) {
            OpenHelperManager.releaseHelper();
            mDatabaseHelper = null;
        }
    }

    private DatabaseHelper getDBHelper() {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = OpenHelperManager.getHelper(getContext(), DatabaseHelper.class);
        }
        return mDatabaseHelper;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_notify_send:
                sendNotify();
                break;
//            case R.id.et_notify_clear:
//                et_notify_input.setText("");
//                break;
        }
    }

    private void sendNotify() {
        if(isSending == true){
            ToastUtil.toast(getContext(), "发送中，请稍等...");
            return;
        }

        String content = et_notify_input.getText().toString().trim();
        if (!CommonUtil.isEmpty(content)) {
            send(content);
        } else {
            ToastUtil.toast(getContext(), "请输入内容");
        }
    }

    boolean isSending;
    private void send(final String content) {
        isSending = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("platform", "all");
                    dataObj.put("audience", "all");
                    JSONObject notifyObj = new JSONObject();
                    notifyObj.put("alert", content);
                    dataObj.put("notification", notifyObj);

                    String data = dataObj.toString();

                    URL url = new URL(Constants.URL_JPUSH_API);
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url
                            .openConnection();
                    urlConnection.setRequestMethod("POST");
                    // 设置请求的头
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestProperty("Authorization", Constants.JPUSH_AUTH);
                    urlConnection.setDoOutput(true); // 发送POST请求必须设置允许输出
                    urlConnection.setDoInput(true); // 发送POST请求必须设置允许输入
                    //获取输出流
                    OutputStream os = urlConnection.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        success();
                    } else {
                        failure(1 + "---responseCode: " + responseCode);
                    }
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    failure("2" + "---" + e.getMessage());
                }

                isSending = false;
            }
        }.start();
    }

    private void failure(final String errorStr) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.toast(getContext(), "failure " + errorStr);
            }
        });
    }

    private void success() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_notify_input.setText("");
                ToastUtil.toast(getContext(), "success");
                CommonUtil.hideInputMode(getActivity(), true);
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNotify == null ? 0 : mNotify.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_note, parent, false);
            }

            TextView tv_note_time = ViewHolder.get(convertView, R.id.tv_note_time);
            TextView tv_note_content = ViewHolder.get(convertView, R.id.tv_note_content);
            tv_note_content.setText(mNotify.get(position).content);
            tv_note_time.setText(CommonUtil.getMyDateFormat(mNotify.get(position).time));
            return convertView;
        }
    }
}