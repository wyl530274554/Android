package com.melon.myapp.functions.h5;

import android.app.SearchManager;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.Constants;
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
    protected void initData() {
        //百度一下
        String url = getIntent().getStringExtra("url");

        //搜索按钮
        String searchContent = getIntent().getStringExtra(SearchManager.QUERY);
        if(!CommonUtil.isEmpty(searchContent)){
            if(searchContent.startsWith("http")){
                url = searchContent;
            }else{
                url = "http://" + searchContent;
            }

            //自动加.com
            if(!url.contains(".")){
                url+=".com";
            }
        }

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
