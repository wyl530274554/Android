package com.melon.myapp.functions.ui;

import android.animation.Animator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_material_design);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_material_circular, R.id.bt_material_circular_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_material_circular:
                startReveal();
                break;
            case R.id.bt_material_circular_back:
                startRevealBack();
                break;
            default:
        }
    }

    private void startReveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
    }

    private void startRevealBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
}
