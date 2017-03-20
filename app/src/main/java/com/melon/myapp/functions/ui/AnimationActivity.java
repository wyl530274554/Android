package com.melon.myapp.functions.ui;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

public class AnimationActivity extends BaseActivity {

    private ImageView iv_anim_frame, iv_anim_frame_2, iv_anim_tween_rotate, iv_anim_tween_scale,iv_anim_tween_trans, iv_anim_tween_alpha, iv_anim_tween_set;
    private AnimationDrawable frameAnim;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_animation);
        iv_anim_frame = (ImageView) findViewById(R.id.iv_anim_frame);
        iv_anim_frame_2 = (ImageView) findViewById(R.id.iv_anim_frame_2);
        iv_anim_tween_rotate = (ImageView) findViewById(R.id.iv_anim_tween_rotate);
        iv_anim_tween_scale = (ImageView) findViewById(R.id.iv_anim_tween_scale);
        iv_anim_tween_trans = (ImageView) findViewById(R.id.iv_anim_tween_trans);
        iv_anim_tween_alpha = (ImageView) findViewById(R.id.iv_anim_tween_alpha);
        iv_anim_tween_set = (ImageView) findViewById(R.id.iv_anim_tween_set);

        AnimationDrawable a = (AnimationDrawable) iv_anim_frame_2.getDrawable();
        a.setOneShot(false);
        a.start();
        iv_anim_frame.setOnClickListener(this);

        findViewById(R.id.bt_anim_tween_rotate).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_scale).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_trans).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_alpha).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_set).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_anim_frame:
                if (frameAnim != null)
                    frameAnim.stop();
                frameAnim = (AnimationDrawable) iv_anim_frame.getDrawable();
                frameAnim.start();
                break;
            case R.id.bt_anim_tween_rotate:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_tween_rotate);
                iv_anim_tween_rotate.startAnimation(animation);
                break;
            case R.id.bt_anim_tween_scale:
                Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.anim_tween_scale);
                iv_anim_tween_scale.startAnimation(animation2);
                break;
            case R.id.bt_anim_tween_trans:
                Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.anim_tween_trans);
                iv_anim_tween_trans.startAnimation(animation3);
                break;
            case R.id.bt_anim_tween_alpha:
                Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.anim_tween_alpha);
                iv_anim_tween_alpha.startAnimation(animation4);
                break;
            case R.id.bt_anim_tween_set:
                Animation animation5 = AnimationUtils.loadAnimation(this, R.anim.anim_tween_set);
                iv_anim_tween_set.startAnimation(animation5);
                break;
        }
    }
}
