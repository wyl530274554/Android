package com.melon.app.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.melon.app.ApiManager;
import com.melon.app.R;
import com.melon.app.bean.Contacts;
import com.melon.app.bean.Password;
import com.melon.mylibrary.BaseFragment;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnEditorAction;
import okhttp3.Call;

public class ContactsFragment extends BaseFragment {
    @BindView(R.id.et_contacts_search)
    EditText etSearch;

    @BindView(R.id.lv_contacts)
    public ListView lvContacts;

    @BindView(R.id.fab_contacts_add)
    public FloatingActionButton fabContacts;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        fabContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout container = new LinearLayout(getContext());
                container.setOrientation(LinearLayout.VERTICAL);
                final EditText etTitle = new EditText(getContext());
                etTitle.setHint("输入姓名");
                final EditText etUser = new EditText(getContext());
                etUser.setHint("输入手机号");

                container.addView(etTitle);
                container.addView(etUser);

                final AlertDialog mDialog = new AlertDialog.Builder(getActivity()).setView(container).setPositiveButton("确定", null).setNegativeButton("取消", null).create();
                mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveBtn = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button negativeButton = mDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                        positiveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = etTitle.getText().toString().trim();
                                String phone = etUser.getText().toString().trim();
                                if (CommonUtil.isEmpty(name)) {
                                    ToastUtil.toast(getContext(), "姓名不能为空");
                                    return;
                                }

                                //上传至服务器
                                uploadContacts(new Contacts(name, phone), mDialog);
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
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                CommonUtil.hideInputMode(getActivity(), true);
                            }
                        });
                    }
                });
                mDialog.show();
            }
        });
    }

    private void uploadContacts(Contacts contacts, AlertDialog mDialog) {
        Map<String, String> map = new HashMap<>();
        map.put("name", contacts.getName());
        map.put("phone", contacts.getPhone());

        OkHttpUtils.post().url(ApiManager.API_CONTACTS).params(map).build().execute(new StringCallback() {
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
                } else {
                    ToastUtil.toast(getContext(), "添加失败: " + response);
                }
            }
        });
    }

    @OnEditorAction(R.id.et_contacts_search)
    boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //搜索
            String content = etSearch.getText().toString().trim();
            getServerData(content);
            LogUtils.d("query contacts");
            return true;
        }
        return false;
    }

    private void getServerData(String content) {
        OkHttpUtils.get().url(ApiManager.API_CONTACTS + content).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //刷新完成
                LogUtils.e("get notes error: " + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    List<Contacts> list = new Gson().fromJson(response, new TypeToken<List<Contacts>>() {
                    }.getType());
                    if (list != null && list.size() != 0) {
                        List<String> items = new ArrayList<>();
                        for (Contacts contacts : list) {
                            items.add(contacts.toString());
                        }

                        //显示出来
                        lvContacts.setAdapter(new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_expandable_list_item_1,
                                items));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
