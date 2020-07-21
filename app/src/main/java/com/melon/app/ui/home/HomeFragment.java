package com.melon.app.ui.home;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.melon.app.R;
import com.melon.mylibrary.BaseFragment;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.Constants;
import com.melon.mylibrary.util.DialogUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import okhttp3.Call;

import static com.melon.app.ApiManager.API_APP_UPGRADE;
import static com.melon.app.ApiManager.APP_DOWNLOAD;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.et_home_search)
    EditText etHomeSearch;
    @BindView(R.id.iv_home_del)
    ImageView ivHomeDel;
    @BindView(R.id.lv_home_web_collect)
    ListView lvCollect;

    private HomeViewModel mHomeViewModel;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        mHomeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        mHomeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String content) {
                etHomeSearch.setText(content);
            }
        });

        //升级
        getAppUpgradeInfo();

        //网址收藏sp
        SharedPreferences sharedPreference = SpUtil.getSharedPreference(getContext(), SpUtil.COLLECT_WEB);

        //添加ListView尾部
        TextView textView = new TextView(getContext());
        textView.setText("添加网址");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹窗输入网址和标题
                Dialog dialog = DialogUtil.getDown2UpDialog(getActivity(), R.layout.dialog_collect_web);
                EditText etTitle = dialog.findViewById(R.id.et_dialog_collect_web_title);
                EditText etUrl = dialog.findViewById(R.id.et_dialog_collect_web_url);
                Button btOk = dialog.findViewById(R.id.bt_dialog_collect_web_ok);
                btOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = etTitle.getText().toString().trim();
                        String url = etUrl.getText().toString().trim();
                        sharedPreference.edit().putString(title, url).apply();
                        dialog.dismiss();

                        ToastUtil.showLongToast(getContext(), "已添加，下次生效");
                    }
                });

                dialog.show();
            }
        });
        lvCollect.addFooterView(textView);


        //所有网址
        Map<String, ?> allWebs = sharedPreference.getAll();
        Set<String> keySets = allWebs.keySet();
        String[] keys = new String[keySets.size()];
        keySets.toArray(keys);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, keys);
        lvCollect.setAdapter(adapter);

        lvCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                enterWeb((String) allWebs.get(keys[position]));
            }
        });
        lvCollect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                sharedPreference.edit().remove(keys[position]).apply();
                ToastUtil.showLongToast(getContext(), "已删除，下次生效");
                return true;
            }
        });
    }

    private void enterWeb(String url) {
        CommonUtil.enterBrowser(getContext(), url);
    }

    private void getAppUpgradeInfo() {
        OkHttpUtils.get().url(API_APP_UPGRADE).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    LogUtils.d("getAppUpgradeInfo: " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.optInt("code");
                    String name = jsonObject.optString("name");
                    if (code > CommonUtil.getVersionCode(getContext())) {
                        //升级
                        CommonUtil.downFileBySystem(getContext(), APP_DOWNLOAD + name, name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.iv_home_del)
    void onClick(View view) {
        mHomeViewModel.setText("");
    }

    @OnEditorAction(R.id.et_home_search)
    boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String content = textView.getText().toString().trim();
            enterWeb(Constants.URL_BAI_DU + content);
            return true;
        }
        return false;
    }
}