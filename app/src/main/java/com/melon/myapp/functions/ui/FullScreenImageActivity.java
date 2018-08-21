package com.melon.myapp.functions.ui;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

public class FullScreenImageActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_full_screen_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void initData() {

    }

}
