package com.melon.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

import com.melon.mylibrary.util.CommonUtil;
import com.melon.mylibrary.util.LogUtils;

/**
 * Created by mleon on 2016/6/14.
 */
public class MyListView extends ListView{
    private int screenHeight;
    private GestureDetector mDetector;
    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(screenHeight==0)
            screenHeight = CommonUtil.getScreenHeight(getContext());

        mDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy(0, (int) distanceY);
                return super.onScroll(e1,e2,distanceX,distanceX);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                scrollTo(0,0);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        mDetector.onTouchEvent(ev);
        return true;
    }
}
