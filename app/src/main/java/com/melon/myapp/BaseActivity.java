package com.melon.myapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.StatusBarCompat;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {
    public static Handler mHandler = new Handler();
    Context mContext;
    private boolean isSideRight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CommonUtil.fullScreen(this);
        mContext = getApplicationContext();
        initView();
        ButterKnife.bind(this);
        initData();

        //加上此段，可以让状态栏无色，并且浮在主内容之上
        StatusBarCompat.compat(this);

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
