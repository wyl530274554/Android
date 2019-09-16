package com.melon.myapp.functions.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.melon.myapp.BaseFragment;
import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.myapp.functions.h5.WebActivity;
import com.melon.mylibrary.util.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索
 *
 * @author melon.wang
 * @date 2018/8/21
 */
public class BrowserFragment extends BaseFragment {

    @BindView(R.id.et_browser_search)
    EditText etBrowserSearch;
    @BindView(R.id.bt_browser_search)
    Button btBrowserSearch;
    @BindView(R.id.iv_browser_del)
    ImageView ivBrowserDel;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_browser, container, false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        etBrowserSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    ivBrowserDel.setVisibility(View.VISIBLE);
                } else {
                    ivBrowserDel.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clear() {
        etBrowserSearch.setText("");
    }

    private void search() {
        //搜索
        String key = etBrowserSearch.getText().toString().trim();
        search(key);
    }

    private void search(String key) {
        if (!CommonUtil.isEmpty(key)) {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("url", Constants.URL_BAI_DU + key);
            startActivity(intent);
        }
    }

    private void enterWeb(String url) {
        if (!CommonUtil.isEmpty(url)) {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        }
    }

    @OnClick({R.id.bt_browser_search, R.id.iv_browser_del, R.id.bt_weather, R.id.bt_jd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_browser_search:
                search();
                break;
            case R.id.iv_browser_del:
                clear();
                break;
            case R.id.bt_weather:
                search("天气预报");
                break;
            case R.id.bt_jd:
                enterWeb(Constants.URL_JD);
                break;
        }
    }

}