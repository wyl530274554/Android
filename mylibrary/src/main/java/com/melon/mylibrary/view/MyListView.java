package com.melon.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;

/**
 * Created by mleon on 2016/6/14.
 */
public class MyListView extends ListView{
    int startY;
    private int screenHeight;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(screenHeight==0)
            screenHeight = CommonUtil.getScreenHeight(getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                LogUtils.e(startY+"");
                break;
            case MotionEvent.ACTION_MOVE:
                int tempY = (int) ev.getRawY();
                int dy = tempY-startY;

                int newTop = this.getTop()+dy;
                int  newBottom = this.getBottom()+dy;

                LogUtils.e(newBottom+"---"+screenHeight+"---"+dy+"---"+this.getBottom());
                //边界控制
                if(newTop<0  || newBottom> screenHeight-40){
                    break;
                }
                this.layout(this.getLeft(),newTop,this.getRight(),newBottom);

                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
