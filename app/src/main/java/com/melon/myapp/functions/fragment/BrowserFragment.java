package com.melon.myapp.functions.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.myapp.functions.h5.WebActivity;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 搜索
 *
 * @author melon.wang
 * @date 2018/8/21
 */
public class BrowserFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        final EditText etBrowserSearch = view.findViewById(R.id.et_browser_search);
        Button btBrowserSearch = view.findViewById(R.id.bt_browser_search);
        ImageView ivBrowserLogo = view.findViewById(R.id.iv_browser_logo);
        btBrowserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索
                String key = etBrowserSearch.getText().toString().trim();

                if (!CommonUtil.isEmpty(key)) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url", Constants.URL_BAIDU + key);
                    startActivity(intent);
                }

            }
        });
        btBrowserSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etBrowserSearch.setText("");
                return true;
            }
        });
        ivBrowserLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpUtils.post()
                        .addParams("signature", "RXF1aXR5QmFuayw4LG9pazBRNFRVQXpwb0ZxQUdscVBuY0pWaVBQVGdJeWhneEJCQWRQTno")
                        .addParams("openid", "kilimall_testtest")
                        .url("https://api.kilimall.com/v1/ke/authorization")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("res: " + response);

                        try {
                            JSONObject resObj = new JSONObject(response);
                            String redirectUrl = resObj.optString("redirect_url");

                            Intent intent = new Intent(getContext(), WebActivity.class);
                            intent.putExtra("url", redirectUrl);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}