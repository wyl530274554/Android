package com.melon.myapp.functions.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.view.HProgressBarLoading;
import com.melon.mylibrary.view.MyUtils;

/**
 * Created by melon on 2017/8/7.
 * Email 530274554@qq.com
 */

public class WxWebProgressActivity extends BaseActivity {

    private HProgressBarLoading mTopProgress;
    private boolean isContinue = false;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_wx_web_progress);

        mTopProgress = (HProgressBarLoading) findViewById(R.id.pb_wx_web);

        WebView webView = (WebView) findViewById(R.id.wv_wx_web);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //如果没有网络直接跳出方法
                if (!MyUtils.isNetworkAvailable(WxWebProgressActivity.this)) {
                    return;
                }
                //如果进度条隐藏则让它显示
                if (View.INVISIBLE == mTopProgress.getVisibility()) {
                    mTopProgress.setVisibility(View.VISIBLE);
                }
                //大于80的进度的时候,放慢速度加载,否则交给自己加载
                if (newProgress >= 80) {
                    //拦截webView自己的处理方式
                    if (isContinue) {
                        return;
                    }
                    mTopProgress.setCurProgress(100, 3000, new HProgressBarLoading.OnEndListener() {
                        @Override
                        public void onEnd() {
                            finishOperation(true);
                            isContinue = false;
                        }
                    });

                    isContinue = true;
                } else {
                    mTopProgress.setNormalProgress(newProgress);
                }
            }
        });

        webView.loadUrl("https://www.baidu.com");
    }

    /**
     * 结束进行的操作
     */
    private void finishOperation(boolean flag) {
        //最后加载设置100进度
        mTopProgress.setNormalProgress(100);
        hideProgressWithAnim();
    }
    /**
     * 隐藏加载对话框
     */
    private void hideProgressWithAnim() {
        AnimationSet animation = getDismissAnim(this);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTopProgress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mTopProgress.startAnimation(animation);
    }

    /**
     * 获取消失的动画
     *
     * @param context
     * @return
     */
    private AnimationSet getDismissAnim(Context context) {
        AnimationSet dismiss = new AnimationSet(context, null);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        dismiss.addAnimation(alpha);
        return dismiss;
    }

    @Override
    protected void initData() {

    }

}
