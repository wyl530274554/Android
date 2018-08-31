package com.melon.myapp.functions.ui;

import android.animation.Animator;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by melon on 2017/11/25.
 *
 * @author melon.wang
 * @date 2018/8/22
 */
public class MaterialDesignActivity extends BaseActivity {
    @BindView(R.id.secondView)
    public View secondView;
    @BindView(R.id.iv_shared_element)
    public ImageView ivSharedElement;

    @Override
    protected void initView() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_material_design);
    }

    @Override
    protected void initData() {
        secondView.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //抬起
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();

                    startRevealByFinger(x, y);
                }
                return true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.bt_material_circular, R.id.bt_material_circular_back, R.id.iv_shared_element})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_material_circular:
                startReveal();
                break;
            case R.id.bt_material_circular_back:
                startRevealBack();
                break;
            case R.id.iv_shared_element:
                Intent intent = new Intent(this, MaterialDesignSharedElementActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,ivSharedElement,"melonShareImg");
                startActivity(intent,options.toBundle());
                break;
            default:
        }
    }

    /**
     * 跟随手指 揭露
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRevealByFinger(int centerX, int centerY) {
        secondView.setVisibility(View.VISIBLE);

        // 获取动画显示的View的中心点的坐标
        // 获取扩散的半径
        float finalRadius = (float) Math.hypot(centerX, centerY);
        // 定义揭露动画
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                secondView, centerX, centerY, 0, finalRadius);
        // 设置动画持续时间，并开始动画
        mCircularReveal.setDuration(1000).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startReveal() {
        secondView.setVisibility(View.VISIBLE);
        // 获取动画显示的View的中心点的坐标
        int centerX = secondView.getWidth() / 2;
        int centerY = secondView.getHeight() / 2;
        // 获取扩散的半径
        float finalRadius = (float) Math.hypot(centerX, centerY);
        // 定义揭露动画
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                secondView, centerX, centerY, 0, finalRadius);
        // 设置动画持续时间，并开始动画
        mCircularReveal.setDuration(1000).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startRevealBack() {
        // 获取动画显示的View的中心点的坐标
        int centerX = secondView.getWidth() / 2;
        int centerY = secondView.getHeight() / 2;
        // 获取扩散的半径
        float finalRadius = (float) Math.hypot(centerX, centerY);
        // 定义揭露动画
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                secondView, centerX, centerY, finalRadius, 0);
        // 设置动画持续时间，并开始动画
        mCircularReveal.setDuration(1000).start();

        mCircularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                secondView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
