package com.melon.myapp;

import com.melon.mylibrary.util.CommonUtil;

/**
 * App启动
 *
 * @author melon.wang
 * @date 2018/8/22
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
//        setContentView(R.layout.my_demo);
        CommonUtil.setTransparentStateBar(this);
        setSlideRight(false);
    }

    @Override
    protected void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonUtil.enterActivity(getApplicationContext(), MainActivity.class);
                finish();
            }
        }, 500);
    }

}
