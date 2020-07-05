package com.melon.mylibrary;

import android.app.Application;
import android.content.Context;

import com.melon.mylibrary.util.CustomExceptionHandler;
import com.melon.mylibrary.util.SystemUtils;

/**
 * Created by melon on 2017/4/8.
 */

public class CustomApplication extends Application {
    private static CustomApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        mApplication = this;
    }

    /**
     * 获取全局上下文
     * 少用，需要用的地方，用方法或初始化时传入，有利于单元测试
     *
     * @return 上下文
     */
    public static Context getInstance() {
        return mApplication;
    }
}
