package com.melon.myapp.functions.h5;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.NetUtil;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.view.ProgressWebView;
//FIXME 网页有重定向时，返回有问题。(直接：mWebView.setWebViewClient(new WebViewClient())可解决这问题，但不能自定义形为)
public class HtmlActivity extends BaseActivity {

    private static final java.lang.String TAG = "HtmlActivity";
    private ProgressWebView mWebView;
    private boolean isLoading = true;

    @Override
    protected void initView() {
//        CommonUtil.fullScreen(this);
        setContentView(R.layout.activity_html);
        mWebView = (ProgressWebView) findViewById(R.id.wv_html);

        setWebViewParam();
    }

    private void setWebViewParam() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    if (isLoading) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(getApplicationContext(), HtmlActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.e("onPageStarted: " + url);
                isLoading = true;
                mHandler.removeCallbacks(stopLoadingTask);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtils.e("onPageFinished: " + url);
                mHandler.postDelayed(stopLoadingTask,1000);
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
        if (isWebNoImgOpen && !NetUtil.isWifiConnected(this)) {
            settings.setBlockNetworkImage(true);
        } else {
            settings.setBlockNetworkImage(false);
        }
    }

    private StopLoadingTask stopLoadingTask = new StopLoadingTask();
    class StopLoadingTask implements Runnable{
        @Override
        public void run() {
            isLoading = false;
        }
    }

    @Override
    protected void initData() {
        //百度一下
        String url = getIntent().getStringExtra("url");

        //搜索按钮
        String searchContent = getIntent().getStringExtra(SearchManager.QUERY);
        if (!CommonUtil.isEmpty(searchContent)) {
            if (searchContent.startsWith("http")) {
                url = searchContent;
            } else {
                url = "http://" + searchContent;
            }

            //自动加.com
            if (!url.contains(".")) {
                url += ".com";
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
