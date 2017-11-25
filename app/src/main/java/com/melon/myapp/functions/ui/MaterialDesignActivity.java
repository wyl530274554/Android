package com.melon.myapp.functions.ui;

import android.animation.Animator;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

/**
 * Created by melon on 2017/11/25.
 * Email 530274554@qq.com
 */

public class MaterialDesignActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_material_design);

        findViewById(R.id.bt_material_circular).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_material_circular:
                startReveal(v);
                break;
        }
    }

    private void startReveal(View clickView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(clickView, clickView.getWidth()/2, clickView.getHeight()/2,0,clickView.getHeight());
            circularReveal.setInterpolator(new AccelerateInterpolator());
            circularReveal.setDuration(1000);
            circularReveal.start();
        }
    }
}
