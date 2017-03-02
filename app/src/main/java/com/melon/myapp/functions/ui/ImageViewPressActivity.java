package com.melon.myapp.functions.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.melon.myapp.R;
import com.melon.mylibrary.util.ToastUtil;
import com.melon.mylibrary.view.MyPressTouchListener;


public class ImageViewPressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_view_press);

        ImageView iv_image_press = (ImageView) findViewById(R.id.iv_image_press);
        iv_image_press.setOnTouchListener(new MyPressTouchListener());
        iv_image_press.setImageResource(R.mipmap.ic_1);

        iv_image_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(getApplicationContext(), "点击");
            }
        });
    }
//    @Override
//    protected void initView() {
//        setContentView(R.layout.activity_image_view_press);
//        ImageView iv_image_press = (ImageView) findViewById(R.id.iv_image_press);
//        iv_image_press.setOnTouchListener(VIEW_TOUCH_DARK);
//        iv_image_press.setImageResource(R.mipmap.ic_1);
//
//        iv_image_press.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showShortToast(getApplicationContext(),"点击");
//            }
//        });
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }

}
