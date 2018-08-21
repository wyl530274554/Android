package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.EditText;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.view.ProgressWebView;

import butterknife.OnClick;

public class WxProgressBarActivity extends BaseActivity {
    protected ProgressWebView mWebView;
    private EditText etUrl;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wx_progress_bar);
        mWebView = (ProgressWebView) findViewById(R.id.wv_progress_bar);

        etUrl = (EditText) findViewById(R.id.et_wx_progress_bar);
    }


    @Override
    protected void initData() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://www.sina.com.cn");
    }

    @OnClick(R.id.bt_wx_progress_bar)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_wx_progress_bar:
                mWebView.loadUrl(etUrl.getText().toString().trim());
                break;
        }
    }
}
