package com.melon.myapp;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.melon.mylibrary.util.CommonUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
//        setContentView(R.layout.my_demo);
        CommonUtil.setTransparentStateBar(this);
    }

    @Override
    protected void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonUtil.enterActivity(getApplicationContext(),MainActivity.class);
                finish();
            }
        },1000);
    }

}
