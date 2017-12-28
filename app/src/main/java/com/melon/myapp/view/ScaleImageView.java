package com.melon.myapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by melon on 2017/12/28.
 * Email 530274554@qq.com
 */

@SuppressLint("AppCompatCustomView")
public class ScaleImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {
    ScaleGestureDetector scaleGestureDetector = null;
    Matrix scaleMatrix = new Matrix();
    Context context;

    public ScaleImageView(Context context) {
        this(context, null);

    }

    public ScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }


    private void init() {
        setOnTouchListener(this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null) {
            return true;
        }
//        scaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
        scaleMatrix.postScale(scaleFactor, scaleFactor,
                detector.getFocusX(), detector.getFocusY());
        checkBorderAndCenterWhenScale();
        setImageMatrix(scaleMatrix);
        return true;
    }

    private void checkBorderAndCenterWhenScale() {
        Matrix matrix = scaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }

        float deltaX = 0;
        float deltaY = 0;
        int width = getWidth();
        int height = getHeight();
        // 如果宽或高大于屏幕，则控制范围
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rectF.width() < width) {
            deltaX = width * 0.5f - rectF.right + 0.5f * rectF.width();
        }
        if (rectF.height() < height) {
            deltaY = height * 0.5f - rectF.bottom + 0.5f * rectF.height();
        }
        scaleMatrix.postTranslate(deltaX, deltaY);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return scaleGestureDetector.onTouchEvent(event);
    }






    /*监听视图布局完成*/

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }


    /*初始化时移动图片到中心*/
    @Override
    public void onGlobalLayout() {
        if (!once)
            return;
        Drawable d = getDrawable();
        if (d == null)
            return;
        //获取ImageView宽高
        int width = getWidth();
        int height = getHeight();

        //获取图片宽高
        int imgWidth = d.getIntrinsicWidth();
        int imgHeight = d.getIntrinsicHeight();

        float scale = 1.0f;

        //如果图片的宽或高大于屏幕，缩放至屏幕的宽或者高
        if (imgWidth > width && imgHeight <= height)
            scale = (float) width / imgWidth;
        if (imgHeight > height && imgWidth <= width)
            scale = (float) height / imgHeight;
        //如果图片宽高都大于屏幕，按比例缩小
        if (imgWidth > width && imgHeight > height)
            scale = Math.min((float) imgWidth / width, (float) imgHeight / height);
        initScale = scale;
        //将图片移动至屏幕中心
        scaleMatrix.postTranslate((width - imgWidth) / 2, (height - imgHeight) / 2);
        setImageMatrix(scaleMatrix);
        once = false;
    }

    /**
     * 默认缩放
     */
    private float initScale = 1.0f;
    boolean once = true;
}
