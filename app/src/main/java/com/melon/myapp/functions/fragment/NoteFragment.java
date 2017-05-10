package com.melon.myapp.functions.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
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
import com.melon.myapp.R;
import com.melon.myapp.bean.Note;
import com.melon.myapp.db.DatabaseHelper;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.util.ViewHolder;

import java.sql.SQLException;
import java.util.List;

/**
 * 笔记主页
 */
public class NoteFragment extends BaseFragment {

    private ListView lv_note;
    private LayoutInflater mInflater;
    private MyAdapter mAdapter;
    private DatabaseHelper mDatabaseHelper;
    private RuntimeExceptionDao mDao;
    private List<Note> mNotes;

    @Override
    protected void initData() {
        mDao = getDBHelper().getNoteDao();
        try {
            mNotes = mDao.queryBuilder().orderBy("time", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mAdapter = new MyAdapter();
        lv_note.setAdapter(mAdapter);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        lv_note = (ListView) view.findViewById(R.id.lv_note);
        this.mInflater = inflater;

        view.findViewById(R.id.fab_note_add).setOnClickListener(this);
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
                            mNotes.add(0, new Note(System.currentTimeMillis()+"", input));
                            mAdapter.notifyDataSetChanged();
                            // 记录到数据库
                            mDao.create(new Note(System.currentTimeMillis()+"", input));
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        CommonUtil.hideInputMode(getActivity(), true);
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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNotes.size();
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
                convertView = mInflater.inflate(R.layout.my_text_view, parent, false);
            }
            TextView tv_text_view_content = ViewHolder.get(convertView, R.id.tv_text_view_content);
            tv_text_view_content.setText(mNotes.get(position).content);
            return convertView;
        }
    }
}
