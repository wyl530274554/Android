package com.melon.mylibrary;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.melon.mylibrary.util.StatusBarCompat;

import butterknife.ButterKnife;

/**
 * Activity 基类
 *
 * @author melon.wang
 * @date 2018/8/21
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static Handler mHandler = new Handler();
    Context mContext;
    private boolean isSideRight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        initView();
        ButterKnife.bind(this);
        initData();

        //加上此段，可以让状态栏无色，并且浮在主内容之上
        StatusBarCompat.compat(this);

//        if (isSideRight) {
//            SwipeBackHelper.onCreate(this);
//            SwipeBackHelper.getCurrentPage(this).setSwipeRelateEnable(true);
//        }

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
//            SwipeBackHelper.onPostCreate(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isSideRight) {
//            SwipeBackHelper.onDestroy(this);
        }
    }
}
