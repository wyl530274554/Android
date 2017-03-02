package com.melon.mylibrary.view;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.melon.mylibrary.util.LogUtils;

/**
 * Created by admin on 2017/3/2.
 * Email 530274554@qq.com
 */

public class MyPressTouchListener implements View.OnTouchListener {
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
}
