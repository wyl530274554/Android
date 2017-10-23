package com.melon.myapp.functions.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.melon.myapp.BaseFragment;
import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.myapp.bean.Note;
import com.melon.myapp.db.DatabaseHelper;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.util.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.sql.SQLException;
import java.util.List;

import okhttp3.Call;

/**
 * 笔记主页
 */
public class NoteFragment extends BaseFragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, View.OnLongClickListener {

    private static final java.lang.String TAG = "NoteFragment";
    private ListView lv_note;
    private LayoutInflater mInflater;
    private MyAdapter mAdapter;
    private DatabaseHelper mDatabaseHelper;
    private RuntimeExceptionDao mDao;
    private List<Note> mNotes;

    @Override
    protected void initData() {
        mDao = getDBHelper().getNoteDao();
        getMyNotes();
        mAdapter = new MyAdapter();
        lv_note.setAdapter(mAdapter);
    }

    private void initEmptyView() {
        TextView emptyView = new TextView(getContext());
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无内容");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) lv_note.getParent()).addView(emptyView);
        lv_note.setEmptyView(emptyView);
    }

    private void getMyNotes() {
        try {
            mNotes = mDao.queryBuilder().orderBy("time", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        lv_note = (ListView) view.findViewById(R.id.lv_note);
        lv_note.setOnItemLongClickListener(this);
        lv_note.setOnItemClickListener(this);
        this.mInflater = inflater;
        initEmptyView();

        View addBtn = view.findViewById(R.id.fab_note_add);
        addBtn.setOnClickListener(this);
        addBtn.setOnLongClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_note_add:
                //添加笔记
                addNote();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addNote() {
        final EditText et = new EditText(getContext());
        et.setHint("输入内容");
        et.setMinimumHeight(200);
        new AlertDialog.Builder(getActivity())
                .setView(et)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString().trim();
                        if (CommonUtil.isEmpty(input)) {
                            ToastUtil.toast(getContext(), "内容不能为空");
                        } else {
                            //显示在当前列表
                            Note note = new Note(System.currentTimeMillis() + "", input);
                            mNotes.add(0, note);
                            mAdapter.notifyDataSetChanged();
                            // 记录到数据库
                            mDao.create(note);

                            //上传至服务器
                            uploadNote(note);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtil.hideInputMode(getActivity(), true);
                            }
                        }, 300);

                    }
                })
                .show();
        CommonUtil.hideInputMode(getActivity(), false);

        //对话框点击确定，不自动消失
//        final AlertDialog mDialog=new AlertDialog.Builder(this).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
//        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                Button positionButton=mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                Button negativeButton=mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//                positionButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
//                        mDialog.dismiss();
//                    }
//                });
//                negativeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this,"取消",Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        });
//        mDialog.show();
    }

    private void uploadNote(final Note note) {
        OkHttpUtils
                .post()
                .url(Constants.API_NOTE_ADD)
                .addParams("content", note.content)
                .addParams("user", Build.MODEL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast(getContext(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //TODO 更新本地note信息 sid
                        note.sid = response;
                        int update = mDao.update(note);
                        ToastUtil.toast(getContext(), "sid: "+response);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(getActivity())
                .setMessage("要删除吗？")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //显示在当前列表
                        Note note = mNotes.get(position);
                        mNotes.remove(note);
                        mAdapter.notifyDataSetChanged();
                        // 记录到数据库
                        mDao.delete(note);

                        //上传至服务器
                        delNote(note);
                    }
                })
                .setNegativeButton("取消", null)
                .show();
        return true;
    }

    private void delNote(Note note) {
        if(CommonUtil.isEmpty(note.sid)){
            ToastUtil.toast(getContext(), "服务器中不存在此信息");
            return;
        }

        OkHttpUtils
                .post()
                .url(Constants.API_NOTE_DEL)
                .addParams("sid", note.sid+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast(getContext(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ToastUtil.toast(getContext(), response);
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Note note = mNotes.get(position);

        final EditText et = new EditText(getContext());
        et.setText(note.content);
        et.setMinimumHeight(200);
        new AlertDialog.Builder(getActivity())
                .setView(et)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString().trim();
                        if (CommonUtil.isEmpty(input)) {
                            ToastUtil.toast(getContext(), "内容不能为空");
                        } else {
                            //显示在当前列表
                            note.content=input;
                            note.time = System.currentTimeMillis() + "";
                            mNotes.remove(note);
                            mNotes.add(0, note);
                            mAdapter.notifyDataSetChanged();
                            // 记录到数据库
                            mDao.update(note);

                            //上传至服务器
                            updateNote(note);
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtil.hideInputMode(getActivity(), true);
                            }
                        }, 300);

                    }
                })
                .show();
        CommonUtil.hideInputMode(getActivity(), false);
    }

    private void updateNote(Note note) {
        if(CommonUtil.isEmpty(note.sid)){
            ToastUtil.toast(getContext(), "服务器中不存在此信息");
            return;
        }

        OkHttpUtils
                .post()
                .url(Constants.API_NOTE_UPDATE)
                .addParams("sid", note.sid+"")
                .addParams("content", note.content)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast(getContext(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ToastUtil.toast(getContext(), response);
                    }
                });
    }

    @Override
    public boolean onLongClick(View v) {
        //长按添加按钮，查询服务器数据
        ToastUtil.toast(getContext(), "查询服务器");
        OkHttpUtils
                .post()
                .url(Constants.API_NOTE_QUERY)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast(getContext(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ToastUtil.toast(getContext(),"查询成功,  共："+response+"条数据");
                    }
                });
        return true;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNotes == null ? 0 : mNotes.size();
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
            tv_note_content.setText(mNotes.get(position).content);
            tv_note_time.setText(CommonUtil.getMyDateFormat(mNotes.get(position).time));
            return convertView;
        }
    }
}
