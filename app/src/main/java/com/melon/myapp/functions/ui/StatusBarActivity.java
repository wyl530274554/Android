package com.melon.myapp.functions.ui;

import android.view.View;
import android.view.WindowManager;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

public class StatusBarActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_status_bar);

        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
