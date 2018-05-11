package com.melon.myapp.functions.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.renderscript.Script;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.melon.myapp.ApiManager;
import com.melon.myapp.BaseFragment;
import com.melon.myapp.R;
import com.melon.myapp.bean.Note;
import com.melon.myapp.bean.Password;
import com.melon.myapp.db.DatabaseHelper;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.util.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 笔记主页
 */
public class PasswordFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.lv_password)
    public ListView lv_password;
    private LayoutInflater mInflater;
    private MyAdapter mAdapter;
    private List<Password> mPasswords = new ArrayList<>();
    @BindView(R.id.et_password)
    public EditText et_password;
    @BindView(R.id.fl_password)
    public FrameLayout fl_password;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        this.mInflater = inflater;
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    protected void initData() {
        mPasswords.clear();
        getMyServerNotes();
    }

    @Override
    protected void initView() {
        initEmptyView();

        mAdapter = new MyAdapter();
        lv_password.setAdapter(mAdapter);
        lv_password.setOnItemClickListener(this);

        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getText().toString().trim().equals("android")) {
                    fl_password.setVisibility(View.GONE);
                    CommonUtil.hideInputMode(getActivity(), true);
                }
                return false;
            }
        });
    }

    private void getMyServerNotes() {
        OkHttpUtils
                .post()
                .url(ApiManager.API_PASSWORD_ALL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast(getContext(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            List<Password> serverNotes = new Gson().fromJson(response, new TypeToken<List<Password>>() {
                            }.getType());
                            if (serverNotes != null && serverNotes.size() != 0) {
                                // 获取本地并显示
                                mPasswords.addAll(serverNotes);
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initEmptyView() {
        TextView emptyView = new TextView(getContext());
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无内容");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) lv_password.getParent()).addView(emptyView);
        lv_password.setEmptyView(emptyView);
    }

    @Override
    public void onResume() {
        super.onResume();
        et_password.setText("");
        fl_password.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fab_password_add)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_password_add:
                //添加笔记
                addPassword();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addPassword() {
        final LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        final EditText etTitle = new EditText(getContext());
        etTitle.setHint("输入标题");
        final EditText etUser = new EditText(getContext());
        etUser.setHint("输入账号");
        final EditText etPwd = new EditText(getContext());
        etPwd.setHint("输入密码");
        final EditText etDesc = new EditText(getContext());
        etDesc.setHint("输入其它描述");

        container.addView(etTitle);
        container.addView(etUser);
        container.addView(etPwd);
        container.addView(etDesc);

        final AlertDialog mDialog = new AlertDialog.Builder(getActivity()).setView(container).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positionButton = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = etTitle.getText().toString().trim();
                        String user = etUser.getText().toString().trim();
                        String pwd = etPwd.getText().toString().trim();
                        String desc = etDesc.getText().toString().trim();
                        if (CommonUtil.isEmpty(title)) {
                            ToastUtil.toast(getContext(), "内容不能为空");
                            return;
                        }

                        //上传至服务器
                        uploadPassword(new Password(title, user, pwd, desc), mDialog);
                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
            }

        });
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CommonUtil.hideInputMode(getActivity(), true);
                    }
                });
            }
        });
        mDialog.show();
    }

    private void uploadPassword(final Password password, final AlertDialog mDialog) {
        OkHttpUtils
                .post()
                .url(ApiManager.API_PASSWORD_ADD)
                .addParams("title", password.title)
                .addParams("password", password.password)
                .addParams("user", password.user)
                .addParams("desc", password.desc)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast(getContext(), e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if ("1".equalsIgnoreCase(response)) {
                            ToastUtil.toast(getContext(), "添加成功");
                            mDialog.dismiss();
                            mPasswords.add(0, password);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.toast(getContext(), "添加失败: " + response);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.show(position);
    }

    class MyAdapter extends BaseAdapter {
        private int showPos;

        @Override
        public int getCount() {
            return mPasswords == null ? 0 : mPasswords.size();
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
                convertView = mInflater.inflate(R.layout.item_password, parent, false);
            }

            TextView tv_password_title = ViewHolder.get(convertView, R.id.tv_password_title);
            TextView tv_password_user = ViewHolder.get(convertView, R.id.tv_password_user);
            TextView tv_password_pwd = ViewHolder.get(convertView, R.id.tv_password_pwd);
            TextView tv_password_other = ViewHolder.get(convertView, R.id.tv_password_other);

            //收起/展开
            if (showPos == position) {
                tv_password_user.setVisibility(View.VISIBLE);
                tv_password_pwd.setVisibility(View.VISIBLE);
                tv_password_other.setVisibility(View.VISIBLE);
            } else {
                tv_password_user.setVisibility(View.GONE);
                tv_password_pwd.setVisibility(View.GONE);
                tv_password_other.setVisibility(View.GONE);
            }

            Password password = mPasswords.get(position);
            tv_password_title.setText("(" + (++position) + ") " + password.title);

            if (CommonUtil.isEmpty(password.user)) {
                tv_password_user.setVisibility(View.GONE);
                tv_password_user.setText("");
            } else {
                tv_password_user.setText("账号：" + password.user);
            }

            if (CommonUtil.isEmpty(password.password)) {
                tv_password_pwd.setText("");
                tv_password_pwd.setVisibility(View.GONE);
            } else {
                tv_password_pwd.setText("密码：" + password.password);
            }

            if (CommonUtil.isEmpty(password.desc)) {
                tv_password_other.setText("");
                tv_password_other.setVisibility(View.GONE);
            } else {
                tv_password_other.setText(password.desc);
            }

            return convertView;
        }

        public void show(int position) {
            showPos = position;
            notifyDataSetChanged();
        }
    }
}
