package com.melon.myapp.functions.ui;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.mylibrary.util.CommonUtil;

public class AnimationActivity extends BaseActivity {

    private ImageView iv_anim_frame, iv_anim_frame_2, iv_anim_tween_rotate, iv_anim_tween_scale,iv_anim_tween_trans, iv_anim_tween_alpha, iv_anim_tween_set, iv_anim_property_object, iv_anim_property_value, iv_anim_property_value_ball;
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
        iv_anim_property_object = (ImageView) findViewById(R.id.iv_anim_property_object);
        iv_anim_property_value = (ImageView) findViewById(R.id.iv_anim_property_value);
        iv_anim_property_value_ball = (ImageView) findViewById(R.id.iv_anim_property_value_ball);

        AnimationDrawable a = (AnimationDrawable) iv_anim_frame_2.getDrawable();
        a.setOneShot(false);
        a.start();
        iv_anim_frame.setOnClickListener(this);

        findViewById(R.id.bt_anim_tween_rotate).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_scale).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_trans).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_alpha).setOnClickListener(this);
        findViewById(R.id.bt_anim_tween_set).setOnClickListener(this);
        findViewById(R.id.bt_anim_property_object).setOnClickListener(this);
        findViewById(R.id.bt_anim_property_value).setOnClickListener(this);
        findViewById(R.id.bt_anim_property_value_ball).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_anim_frame:
                //帧
                if (frameAnim != null)
                    frameAnim.stop();
                frameAnim = (AnimationDrawable) iv_anim_frame.getDrawable();
                frameAnim.start();
                break;
            case R.id.bt_anim_tween_rotate:
                //补间
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
            case R.id.bt_anim_property_object:
                //属性
                ObjectAnimator.ofFloat(iv_anim_property_object,"rotationX",0,360).setDuration(1000).start();
                break;
            case R.id.bt_anim_property_value:
                //属性
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, CommonUtil.getScreenWidth(this));
                valueAnimator.setTarget(iv_anim_property_value);
                valueAnimator.setDuration(1000).start();
                //上面，不刷新就调用它
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        iv_anim_property_value.setTranslationX((Float) animation.getAnimatedValue());
                    }
                });
                break;
            case R.id.bt_anim_property_value_ball://抛物线
                ValueAnimator valueAnimator2 = new ValueAnimator();
                valueAnimator2.setDuration(3000);
                valueAnimator2.setObjectValues(new PointF(0, 0));
                valueAnimator2.setInterpolator(new LinearInterpolator());
                valueAnimator2.setEvaluator(new TypeEvaluator<PointF>() {
                    @Override
                    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                        PointF point = new PointF();
                        point.x = 200 * fraction * 3;
                        point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                        return point;
                    }
                });
                valueAnimator2.start();
                valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation)
                    {
                        PointF point = (PointF) animation.getAnimatedValue();
                        iv_anim_property_value_ball.setX(point.x);
                        iv_anim_property_value_ball.setY(point.y);
                    }
                });
                break;
        }
    }
}
