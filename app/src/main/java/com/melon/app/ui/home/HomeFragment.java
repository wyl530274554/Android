package com.melon.app.ui.home;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.melon.app.R;
import com.melon.app.ui.activity.WebActivity;
import com.melon.mylibrary.BaseFragment;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.Constants;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.SpUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        getAppUpgradeInfo();

        //收藏的网址
        String collects = SpUtil.getString(getContext(), "webCollect");
        Gson gson = new Gson();
        String[] items = gson.fromJson(collects, String[].class);
        if (items == null) items = new String[]{};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        lvCollect.setAdapter(adapter);

        String[] finalItems = items;
        lvCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonUtil.enterActivity(getActivity(), WebActivity.class, "url", finalItems[position]);
            }
        });
        lvCollect.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> arrayList = new ArrayList<>(finalItems.length);
                Collections.addAll(arrayList, finalItems);
                arrayList.remove(position);
                SpUtil.setString(getContext(), "webCollect", gson.toJson(arrayList));

                //更新界面
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);
                lvCollect.setAdapter(adapter);
                return true;
            }
        });
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
            CommonUtil.enterActivity(getActivity(), WebActivity.class, "url", Constants.URL_BING + content);
            return true;
        }
        return false;
    }
}