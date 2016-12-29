package com.melon.myapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.jude.swipbackhelper.SwipeBackHelper;

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {
    public static Handler mHandler = new Handler();
    Context mContext;
    private boolean isSideRight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        initView();
        initData();

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if (isSideRight) {
            SwipeBackHelper.onCreate(this);
            SwipeBackHelper.getCurrentPage(this).setSwipeEdge(50);
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    public void setSlideRight(boolean isSideRight) {
        this.isSideRight = isSideRight;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isSideRight) {
            SwipeBackHelper.onPostCreate(this);
        }
    }
}
