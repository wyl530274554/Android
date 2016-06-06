package com.melon.myapp.functions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import me.yifeiyuan.library.PeriscopeLayout;

public class LikeActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_like);

        final PeriscopeLayout periscopeLayout = (PeriscopeLayout) findViewById(R.id.periscope);
        periscopeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                periscopeLayout.addHeart();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
