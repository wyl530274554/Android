package com.melon.myapp.functions.h5;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.melon.myapp.BaseActivity;
import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.NetUtil;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.view.ProgressWebView;

public class HtmlActivity extends BaseActivity {

    private static final java.lang.String TAG = "HtmlActivity";
    private ProgressWebView mWebView;


    @Override
    protected void initView() {
//        CommonUtil.fullScreen(this);
        setContentView(R.layout.activity_html);
        mWebView = (ProgressWebView) findViewById(R.id.wv_html);

        setWebViewParam();

    }
boolean isLoading = true;
    private void setWebViewParam() {
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //是hittype=0，就开新窗口， 并且

//                LogUtils.e("老loading: "+" url: "+url);
                if(url.startsWith("http")){
                    if(isLoading){
                        view.loadUrl(url);
                    }else {
                        Intent intent = new Intent(getApplicationContext(), HtmlActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.e("onPageStarted: "+url);
                isLoading = true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.e("onPageFinished: "+url);
                isLoading=false;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);//解决百度新闻，第二次打不开的问题。
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;//解决三星手机 长按弹出复制、粘贴，滑出界面时，闪退问题。
            }
        });


        boolean isWebNoImgOpen = SpUtil.getBoolean(getApplicationContext(), "isSmartWebNoImgOpen");
        //智能图片加载 只在wifi下显示
        if(isWebNoImgOpen && !NetUtil.isWifiConnected(this)){
            settings.setBlockNetworkImage(true);
        }else {
            settings.setBlockNetworkImage(false);
        }
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
