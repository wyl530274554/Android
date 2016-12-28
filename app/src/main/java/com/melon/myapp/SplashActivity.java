package com.melon.myapp;

import android.view.View;

import com.melon.mylibrary.util.CommonUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonUtil.enterActivity(getApplicationContext(),MainActivity.class);
                finish();
            }
        },2000);
    }

    @Override
    public void onClick(View v) {

    }
}
