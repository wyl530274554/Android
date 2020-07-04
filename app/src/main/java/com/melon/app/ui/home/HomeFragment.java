package com.melon.app.ui.home;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.melon.app.ApiManager;
import com.melon.app.R;
import com.melon.app.ui.activity.WebActivity;
import com.melon.mylibrary.BaseFragment;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.Constants;
import com.melon.mylibrary.util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

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

        getAppUpgradeInfo();
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
            CommonUtil.enterActivity(getActivity(), WebActivity.class, "url", Constants.URL_BAI_DU + content);
            return true;
        }
        return false;
    }
}