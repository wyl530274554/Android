package com.melon.myapp.functions.h5;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.Constants;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.NetUtil;
import com.melon.mylibrary.util.SpUtil;
import com.melon.mylibrary.view.SlowlyProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * H5页面
 *
 * @author melon.wang
 * @date 2018/8/21
 */
public class WebActivity extends BaseActivity implements View.OnLongClickListener {
    private static final String TAG = "WebActivity";
    @BindView(R.id.wv_html)
    public WebView mWebView;
    @BindView(R.id.iv_html_share)
    public ImageView ivShare;
    @BindView(R.id.pb_web_view)
    public ProgressBar pb;
    private SlowlyProgressBar mSlowlyProgressBar;

    /**
     * 当前加载的URL
     */
    private String mCurrentUrl;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_html);
        CommonUtil.fullScreen(this);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebViewParam() {
        mWebView.setOnLongClickListener(this);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Https嵌套http图片问题
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        boolean isWebNoImgOpen = SpUtil.getBoolean(getApplicationContext(), "isSmartWebNoImgOpen");
        //智能图片加载 只在wifi下显示
        if (isWebNoImgOpen && !NetUtil.isWifiConnected(this)) {
            settings.setBlockNetworkImage(true);
        } else {
            settings.setBlockNetworkImage(false);
        }
    }

    /**
     * 开启新窗口
     */
    private void openNewWindow(String url) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSlowlyProgressBar != null) {
            mSlowlyProgressBar.destroy();
            mSlowlyProgressBar = null;
        }
    }

    @Override
    protected void initData() {
        setWebViewParam();
        mSlowlyProgressBar = new SlowlyProgressBar(pb);

        //百度一下
        String mUrl = getIntent().getStringExtra("url");

        //搜索按钮
        String searchContent = getIntent().getStringExtra(SearchManager.QUERY);
        if (!CommonUtil.isEmpty(searchContent)) {
            if (searchContent.startsWith(getString(R.string.text_http))) {
                mUrl = searchContent;
            } else {
                mUrl = "http://" + searchContent;
            }
        }

        LogUtils.e(TAG, "URL: " + mUrl);

        mWebView.loadUrl(mUrl);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }

        //监听菜单键
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            CommonUtil.shareWebUrl(this, mCurrentUrl);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.iv_html_share)
    public void onViewClicked() {
        CommonUtil.shareWebUrl(this, mCurrentUrl);
    }

    @Override
    public boolean onLongClick(View view) {
        WebView.HitTestResult result = ((WebView) view).getHitTestResult();
        if (null == result) {
            return false;
        }
        int type = result.getType();
        String extra = result.getExtra();
        LogUtils.e("type: " + type + ", extra: " + extra);
        switch (type) {
            // 选中的文字类型
            case WebView.HitTestResult.EDIT_TEXT_TYPE:
                break;
            // 处理拨号
            case WebView.HitTestResult.PHONE_TYPE:
                break;
            // 处理Email
            case WebView.HitTestResult.EMAIL_TYPE:
                break;
            // 　地图类型
            case WebView.HitTestResult.GEO_TYPE:
                break;
            // 超链接
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                // 另起一页
                if (!CommonUtil.isEmpty(extra)) {
                    openNewWindow(extra);
                }
                break;
            // 带有链接的图片类型
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                // 处理长按图片的菜单项
            case WebView.HitTestResult.IMAGE_TYPE:
                return true;
            //未知
            case WebView.HitTestResult.UNKNOWN_TYPE:
                break;
            default:
        }
        return true;
    }

    /**
     * 自己业务的WebViewClient
     */
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mSlowlyProgressBar != null) {
                mSlowlyProgressBar.onProgressStart();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.e("onPageFinished: " + url);
            mCurrentUrl = url;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("old loading: " + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        /*上面这个过时的是5.0以下可用；下面这个是5.0以上可用。
         *如果测试机是8.0系统，当这两个都存在时，只会调用下面的；而如果只存在上面的，也会调用上面的。(很神奇，很智能)
         *所以暂时可以只处理上面的
         */

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Uri uri = request.getUrl();
            String url = request.getUrl().toString();
            LogUtils.e("shouldOverrideUrlLoading url: " + url);
            //电话处理
            if (url.startsWith(Constants.PROTOCOL_TEL)) {
                startActivity(new Intent(Intent.ACTION_DIAL, uri));
                return true;
            }
            //非http请求处理
            if (!url.startsWith(Constants.NET_PROTOCOL_HTTP)) {
                return true;
            }
            return super.shouldOverrideUrlLoading(view, request);
        }
    }

    /**
     * 自己业务的WebChromeClient
     */
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (mSlowlyProgressBar != null) {
                mSlowlyProgressBar.onProgressChange(newProgress);
            }
        }
    }
}
