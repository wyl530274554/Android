package com.melon.app.ui.web;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.melon.app.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.Constants;
import com.melon.mylibrary.util.DialogUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.MimeType;
import com.melon.mylibrary.util.SpUtil;

public class WebActivity extends AppCompatActivity implements View.OnLongClickListener {

    private WebView mWebView;
    private RelativeLayout mWebContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = findViewById(R.id.wv_web);
        mWebContainer = findViewById(R.id.rl_web_container);
        initWebParams();

        //百度一下
        String mUrl = getIntent().getStringExtra("url");

        //搜索按钮
        String searchContent = getIntent().getStringExtra(SearchManager.QUERY);
        if (!CommonUtil.isEmpty(searchContent)) {
            if (searchContent.startsWith(Constants.NET_PROTOCOL_HTTP)) {
                mUrl = searchContent;
            } else {
                mUrl = "http://" + searchContent;
            }
        }

        LogUtils.e("URL: " + mUrl);

        mWebView.loadUrl(mUrl);
    }

    private void initWebParams() {
        mWebView.setOnLongClickListener(this);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        WebView.setWebContentsDebuggingEnabled(true);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Https嵌套http图片问题
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        boolean isWebNoImgOpen = SpUtil.getBoolean(getApplicationContext(), "isSmartWebNoImgOpen");
        //智能图片加载 只在wifi下显示
        if (isWebNoImgOpen) {
            settings.setBlockNetworkImage(true);
        } else {
            settings.setBlockNetworkImage(true);
        }

        //支持下载
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(final String url, String userAgent, final String contentDisposition, String mimetype, long contentLength) {
                // 调用系统下载
                LogUtils.e("url: " + url + ", userAgent: " + userAgent + ", contentDisposition: " + contentDisposition + ", mimetype: " + mimetype + ", contentLength: " + contentLength);

                //非apk下载，跳转至浏览器
                if (!MimeType.APK.equals(mimetype)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return;
                }

                //弹出确认对话框
                DialogUtil.show(WebActivity.this, CommonUtil.getDataSize(contentLength) + "\n确定下载吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downFileBySystem(url);
                    }
                });
            }
        });
    }

    private void downFileBySystem(String url) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //下载时，下载完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
        request.setDestinationInExternalFilesDir(WebActivity.this, null, System.currentTimeMillis() + ".apk");
        request.setVisibleInDownloadsUi(true);
        downloadManager.enqueue(request);
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
            // 带有链接的图片类型
            case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                // 处理长按图片的菜单项
            case WebView.HitTestResult.IMAGE_TYPE:
                // 超链接
            case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                // 另起一页
                if (!CommonUtil.isEmpty(extra)) {
                    openNewWindow(extra);
                }
                break;
            //未知
            case WebView.HitTestResult.UNKNOWN_TYPE:
                break;
            default:
        }
        return true;
    }

    /**
     * 开启新窗口
     */
    private void openNewWindow(String url) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /**
     * 自己业务的WebViewClient
     */
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(final WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.e("onPageFinished: " + url);
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

    private class MyWebChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mCustomView = view;
            mWebContainer.addView(mCustomView);
            mCustomViewCallback = callback;
            mWebView.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        @Override
        public void onHideCustomView() {
            mWebView.setVisibility(View.VISIBLE);
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mWebContainer.removeView(mCustomView);
            mCustomViewCallback.onCustomViewHidden();
            mCustomView = null;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onHideCustomView();
        }
    }
}
