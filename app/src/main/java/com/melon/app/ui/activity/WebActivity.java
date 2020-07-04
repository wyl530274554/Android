package com.melon.app.ui.activity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.melon.app.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.Constants;
import com.melon.mylibrary.util.LogUtils;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;
    private RelativeLayout mWebContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.wv_web);
        mWebContainer = findViewById(R.id.rl_web_container);

        //百度一下
        String mUrl = getIntent().getStringExtra("url");

        //搜索按钮
        String searchContent = getIntent().getStringExtra(SearchManager.QUERY);
        if (!CommonUtil.isEmpty(searchContent)) {
            mUrl = "http://" + searchContent;
            LogUtils.e("URL: " + mUrl);
            mWebView.loadUrl(mUrl);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        switch (config.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                screenLight(true);
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                screenLight(false);
                break;
            default:
                break;
        }
    }

    PowerManager.WakeLock mWakeLock;

    @SuppressLint("InvalidWakeLockTag")
    private void screenLight(boolean isLight) {
        if (isLight) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "WebHtml");
            mWakeLock.acquire();
        } else {
            if (mWakeLock != null)
                mWakeLock.release();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
