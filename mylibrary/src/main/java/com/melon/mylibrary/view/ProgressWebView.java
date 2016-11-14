package com.melon.mylibrary.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.melon.mylibrary.R;
import com.melon.mylibrary.util.CommonUtil;

/**
 * Created by admin on 2016/6/22.
 */
public class ProgressWebView extends WebView {

    private ProgressBar progressbar;
    private Handler mHandler = new Handler();

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, CommonUtil.dip2px(getContext(), 4), 0, 0));
        progressbar.setPadding(0, 0, 0, 4);

        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        progressbar.setInterpolator(new AccelerateDecelerateInterpolator());
        addView(progressbar);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //是否可以缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressbar.setProgress(newProgress);
            if (newProgress == 100) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
                animation.setDuration(1000);
                progressbar.startAnimation(animation);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(GONE);
                    }
                }, 1000);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
