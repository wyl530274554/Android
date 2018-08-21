package com.melon.myapp.functions.ui;

import android.view.View;
import android.widget.ImageView;

import com.melon.myapp.BaseActivity;
import com.melon.myapp.R;
import com.melon.myapp.view.ZoomImageView;

/**
 * Created by melon on 2017/12/28.
 * Email 530274554@qq.com
 */

public class ScaleImageViewActivity extends BaseActivity {
    @Override
    protected void initView() {
        setContentView(R.layout.activity_scale_image_view);
        ZoomImageView zoom_image= (ZoomImageView) findViewById(R.id.zoom_image);
        zoom_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        zoom_image.setImageResource(R.drawable.img1);
    }

    @Override
    protected void initData() {

    }

}
