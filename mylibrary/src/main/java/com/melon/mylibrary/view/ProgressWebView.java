package com.melon.mylibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.melon.mylibrary.R;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;

/**
 * 进度条WebView
 *
 * @author melon
 * @date 20181208
 */
public class ProgressWebView extends WebView {
    public SlowlyProgressBar mSlowlyProgressBar;
    /**
     * 当前加载的URL
     */
    private String mCurrentUrl;

    public SlowlyProgressBar getSlowlyProgressBar() {
        return mSlowlyProgressBar;
    }

    public String getCurrentUrl() {
        return mCurrentUrl;
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ProgressBar pb = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        pb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, CommonUtil.dip2px(getContext(), 5), 0, 0));
        pb.setPadding(0, 0, 0, 4);
        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        pb.setProgressDrawable(drawable);
        addView(pb);
        mSlowlyProgressBar = new SlowlyProgressBar(pb);
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mSlowlyProgressBar.onProgressChange(newProgress);
            }
        });
        setWebViewClient(new WebViewClient() {
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

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (!url.startsWith("http")) {
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
}
