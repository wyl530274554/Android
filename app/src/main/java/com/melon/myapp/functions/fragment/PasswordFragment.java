package com.melon.myapp.functions.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Handler;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melon.myapp.ApiManager;
import com.melon.myapp.BaseFragment;
import com.melon.myapp.R;
import com.melon.myapp.bean.Password;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.util.ViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 笔记主页
 */
public class PasswordFragment extends BaseFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @BindView(R.id.lv_password)
    public ListView lv_password;
    private LayoutInflater mInflater;
    private MyAdapter mAdapter;
    private List<Password> mPasswords = new ArrayList<>();
    private List<Password> mPasswordsBackup = new ArrayList<>();
    @BindView(R.id.et_password)
    public EditText et_password;
    @BindView(R.id.fl_password)
    public FrameLayout fl_password;
    @BindView(R.id.empty)
    public TextView emptyView;
    @BindView(R.id.et_pwd_search)
    public EditText et_pwd_search;
    private FingerprintManagerCompat mFingerprintManagerCompat;
    private CancellationSignal mCancellationSignal;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        this.mInflater = inflater;
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    protected void initData() {
        loadLocalData();

        //指纹
//        initFingerPrinter();
    }

    private void initFingerPrinter() {
        mFingerprintManagerCompat = FingerprintManagerCompat.from(getContext());
        if (!mFingerprintManagerCompat.isHardwareDetected()) {
            ToastUtil.toast(getContext(), "此设备不支持指纹识别");
            return;
        }

        if (!mFingerprintManagerCompat.hasEnrolledFingerprints()) {
            ToastUtil.toast(getContext(), "此设备没有录入指纹");
            return;
        }
    }

    @Override
    protected void initView() {
        initEmptyView();

        mAdapter = new MyAdapter();
        lv_password.setAdapter(mAdapter);
        lv_password.setOnItemClickListener(this);
        lv_password.setOnItemLongClickListener(this);

        et_pwd_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = s.toString().trim();
                if (TextUtils.isEmpty(content)) {
                    lv_password.clearTextFilter();
                } else {
                    lv_password.setFilterText(content);
                }
                LogUtils.e("onTextChanged: " + content);
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.e("afterTextChanged: " + s.toString());
            }
        });
    }

    private void getServerNotes() {
        OkHttpUtils.get().url(ApiManager.API_PASSWORD_ALL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //刷新完成
                LogUtils.e("get notes error: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    List<Password> serverNotes = new Gson().fromJson(response, new TypeToken<List<Password>>() {
                    }.getType());
                    if (serverNotes != null && serverNotes.size() != 0) {
                        // 获取本地并显示
                        initDataShow(serverNotes);
                        // 记录在本地
                        SpUtil.setString(getContext(), "pwd", response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void initDataShow(List<Password> pwds) {
        mPasswords.clear();
        mPasswordsBackup.clear();

        // 模拟刷新
//        pwds.add(0, new Password("刷新"));

        mPasswords.addAll(pwds);
        mPasswordsBackup.addAll(pwds);
        mAdapter.notifyDataSetChanged();
    }

    private void loadLocalData() {
        //加载本地的
        String pwd = SpUtil.getString(getContext(), "pwd");
        if (!CommonUtil.isEmpty(pwd)) {
            List<Password> serverNotes = new Gson().fromJson(pwd, new TypeToken<List<Password>>() {
            }.getType());
            if (serverNotes != null && serverNotes.size() != 0) {
                // 获取本地并显示
                initDataShow(serverNotes);
            }
        } else {
            getServerNotes();
        }
    }

    private void initEmptyView() {
        lv_password.setEmptyView(emptyView);
    }

    @Override
    public void onResume() {
        super.onResume();
//        et_password.setText("");
//        fl_password.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelFingerprintAuth();
    }

    private void cancelFingerprintAuth() {
        if (mCancellationSignal != null)
            mCancellationSignal.cancel();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
//            cancelFingerprintAuth();
        }
    }

    @OnClick({R.id.fab_password_add, R.id.fl_password})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_password_add:
                //添加笔记
                addPassword();
                break;
            case R.id.fl_password:
                //验证指纹
                authFinger();
                break;
            default:
        }
    }

    private void authFinger() {
        ToastUtil.toast(getContext(), "请验证指纹");
        mCancellationSignal = new CancellationSignal();
        mFingerprintManagerCompat.authenticate(null, 0, mCancellationSignal, new FingerprintCallback(), new Handler());
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
                Button positiveBtn = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
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
        Map<String, String> map = new HashMap<>();
        map.put("title", password.title);
        map.put("password", password.password);
        map.put("user", password.user);
        map.put("desc", password.desc);

        OkHttpUtils.post().url(ApiManager.API_PASSWORD_ADD).params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.d("net error: " + e.getCause());
                ToastUtil.toast(getContext(), e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if ("1".equalsIgnoreCase(response)) {
                    ToastUtil.toast(getContext(), "添加成功");
                    mDialog.dismiss();
                    mPasswords.add(0, password);
                    mAdapter.notifyDataSetChanged();

                    getServerNotes();
                } else {
                    ToastUtil.toast(getContext(), "添加失败: " + response);
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (position == 0) {
//            ToastUtil.toast(getContext(), "刷新");
//            getServerNotes();
//        }

        mAdapter.show(position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // 修改密码
        Password password = mPasswords.get(position);
        updatePassword(password);
        return true;
    }

    public void updatePassword(final Password password) {
        //显示修改对话框
        final LinearLayout container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        final EditText etTitle = new EditText(getContext());
        etTitle.setText(password.title);
        final EditText etUser = new EditText(getContext());
        etUser.setText(password.user);
        final EditText etPwd = new EditText(getContext());
        etPwd.setText(password.password);
        final EditText etDesc = new EditText(getContext());
        etDesc.setText(password.desc);

        container.addView(etTitle);
        container.addView(etUser);
        container.addView(etPwd);
        container.addView(etDesc);
        final AlertDialog mDialog = new AlertDialog.Builder(getActivity()).setView(container).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveBtn = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = etTitle.getText().toString().trim();
                        password.title = title;
                        String user = etUser.getText().toString().trim();
                        password.user = user;
                        String pwd = etPwd.getText().toString().trim();
                        password.password = pwd;
                        String desc = etDesc.getText().toString().trim();
                        password.desc = desc;
                        if (CommonUtil.isEmpty(title)) {
                            ToastUtil.toast(getContext(), "内容不能为空");
                            return;
                        }
                        //上传至服务器
                        uploadUpdatedPassword(password, mDialog);
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
        mDialog.show();
    }

    /**
     * 更新密码
     */
    private void uploadUpdatedPassword(final Password password, final AlertDialog mDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("id", password.id + "");
        map.put("password", password.password);
        map.put("user", password.user);
        map.put("desc", password.desc);
        map.put("title", password.title);
        OkHttpUtils.post().url(ApiManager.API_PASSWORD_UPDATE).params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.d("net error: " + e.getCause());
                ToastUtil.toast(getContext(), e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                if ("1".equalsIgnoreCase(response)) {
                    ToastUtil.toast(getContext(), "修改成功");
                    mDialog.dismiss();

                    getServerNotes();
                } else {
                    ToastUtil.toast(getContext(), "修改失败: " + response);
                }
            }
        });

    }

    class MyAdapter extends BaseAdapter implements Filterable {
        private int showPos;
        MyFilter mFilter;

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

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }
    }

    class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            LogUtils.e("performFiltering: " + charSequence);

            //定义过滤规则
            FilterResults result = new FilterResults();

            List<Password> list;
            if (TextUtils.isEmpty(charSequence)) {
                //当过滤的关键字为空的时候，显示所有的数据
                list = mPasswordsBackup;
            } else {
                //否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (Password pwd : mPasswordsBackup) {
                    if (pwd.title.contains(charSequence) || pwd.desc.contains(charSequence)) {
                        list.add(pwd);
                    }
                }
            }
            result.values = list;
            result.count = list.size();

            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            LogUtils.e("filter: " + filterResults.count);
            mPasswords = (List<Password>) filterResults.values;
            //告诉适配器更新界面
            if (filterResults.count > 0) {
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyDataSetInvalidated();
            }
        }
    }

    private class FingerprintCallback extends FingerprintManagerCompat.AuthenticationCallback {
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            super.onAuthenticationError(errMsgId, errString);
            ToastUtil.toast(getContext(), "指纹错误");
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            ToastUtil.toast(getContext(), "指纹验证失败");
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            ToastUtil.toast(getContext(), "指纹验证成功");
            fl_password.setVisibility(View.GONE);
        }
    }
}
