package com.melon.myapp.functions.ui;

import android.app.Activity;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.melon.myapp.R;
import com.melon.mylibrary.util.LogUtils;
import com.melon.mylibrary.util.ToastUtil;


public class ImageViewPressActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_view_press);

        ImageView iv_image_press = (ImageView) findViewById(R.id.iv_image_press);
        iv_image_press.setOnTouchListener(VIEW_TOUCH_DARK);
        iv_image_press.setImageResource(R.mipmap.ic_1);

        iv_image_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(getApplicationContext(),"点击");
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

    /**
     * 让控件点击时，颜色变深
     * */
    public static final View.OnTouchListener VIEW_TOUCH_DARK = new View.OnTouchListener() {

        public final float[] BT_SELECTED = new float[] {
                1, 0, 0, 0, -20,
                0, 1, 0, 0, -20,
                0, 0, 1, 0, -20,
                0, 0, 0, 1, 0 };
        public final float[] BT_NOT_SELECTED = new float[] {
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0 };

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            LogUtils.e("ACTION: "+event.getAction());
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                LogUtils.e("ACTION_DOWN");
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) ) ;
                    LogUtils.e("ImageView BT_SELECTED");
                }else{
                    v.getBackground().setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) );
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                LogUtils.e("ACTION_UP");
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_NOT_SELECTED) ) ;
                    LogUtils.e("ImageView BT_NOT_SELECTED");
                }else{
                    v.getBackground().setColorFilter(
                            new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    };

    /**
     * 让控件点击时，颜色变亮
     * */
    public static final View.OnTouchListener VIEW_TOUCH_LIGHT = new View.OnTouchListener(){

        public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0, 1,
                0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
                1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setDrawingCacheEnabled(true);

                    iv.setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) ) ;
                }else{
                    v.getBackground().setColorFilter( new ColorMatrixColorFilter(BT_SELECTED) );
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if(v instanceof ImageView){
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter( new ColorMatrixColorFilter(BT_NOT_SELECTED) ) ;
                    System.out.println( "变回来" );
                }else{
                    v.getBackground().setColorFilter(
                            new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    };
}
