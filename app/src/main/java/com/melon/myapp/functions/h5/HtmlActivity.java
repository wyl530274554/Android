package com.melon.myapp.functions.h5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.view.ProgressWebView;

public class HtmlActivity extends BaseActivity {

    private static final java.lang.String TAG = "HtmlActivity";
    private ProgressWebView mWebView;


    @Override
    protected void initView() {
        CommonUtil.fullScreen(this);
        setContentView(R.layout.activity_html);
        mWebView = (ProgressWebView) findViewById(R.id.wv_html);
        setWebViewParam();

        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeEdge(200);
    }

    private void setWebViewParam() {
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);//解决百度新闻，第二次打不开的问题。
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;//解决三星手机 长按弹出复制、粘贴，滑出界面时，闪退问题。
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("url");
        LogUtils.e(TAG, "URL: " + url);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

    }
}
