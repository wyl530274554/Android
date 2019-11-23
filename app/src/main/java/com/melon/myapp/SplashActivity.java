package com.melon.myapp;

import android.view.MotionEvent;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.SpUtil;

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
    public boolean dispatchTouchEvent(MotionEvent ev) {

        LogUtils.e("getAction: "+ev.getAction());
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            LogUtils.e("ACTION_DOWN");

            boolean isDeveloper = SpUtil.getBoolean(getApplicationContext(), "isDeveloper");

            SpUtil.setBoolean(getApplicationContext(), "isDeveloper", !isDeveloper);
            System.exit(0);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void initData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isDeveloper = SpUtil.getBoolean(getApplicationContext(), "isDeveloper");
                if(isDeveloper){
                    CommonUtil.enterActivity(getApplicationContext(), MainActivity.class);
                }else {
                    CommonUtil.enterActivity(getApplicationContext(), MainSimpleActivity.class);
                }
                finish();
            }
        }, 500);
    }

}
