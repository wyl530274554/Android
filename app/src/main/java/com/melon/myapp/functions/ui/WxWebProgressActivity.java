package com.melon.myapp.functions.ui;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.view.SlowlyProgressBar;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class WxWebProgressActivity extends BaseActivity {

    private SlowlyProgressBar slowlyProgressBar;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wx_web_progress);

        ProgressBar pb_wx_web = (ProgressBar) findViewById(R.id.pb_wx_web);

        WebView webView = (WebView) findViewById(R.id.wv_wx_web);
        slowlyProgressBar = new SlowlyProgressBar(pb_wx_web);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                slowlyProgressBar.onProgressStart();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                slowlyProgressBar.onProgressChange(newProgress);
            }
        });

        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (slowlyProgressBar != null) {
            slowlyProgressBar.destroy();
            slowlyProgressBar = null;
        }
    }
}
