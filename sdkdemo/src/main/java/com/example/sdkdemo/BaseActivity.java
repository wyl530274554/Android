package com.example.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

/**
 * 公共基类
 *
 * @author mleon on 2016-3-14
 */

public abstract class BaseActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected abstract void initView();

    protected abstract void initData();
}
