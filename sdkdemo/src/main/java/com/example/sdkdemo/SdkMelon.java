package com.example.sdkdemo;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * 主管理类
 */
public class SdkMelon {
    private static final String TAG = "SdkMelon";
    private static SdkMelon sdk = new SdkMelon();

    public static SdkMelon getInstance() {
        return sdk;
    }

    public void init() {
        Log.e(TAG, "SdkMelon初始化");
    }

    public void enterLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
