package com.example.miaksan.sliderview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class SliderView extends View {

    private static final String TAG = "SliderView";

    private Context mContext;

    /**
     * 画笔
     */
    private Paint mPaint;

    private int mMinSize = 154;

    private int mHeight = 154;

    private int mWidth;

    private int mPaddingLeft;

    private int mSliderWidth;

    private float mLastX;

    private float mCurrentX;

    private int mLeft;

    private int mRight;

    private int mTop;

    private int mBottom;

    public SliderView(Context context) {
        this(context, null);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mPaddingLeft = DensityUtils.dp2px(mContext, 10);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        drawBg(canvas);
        //画滑块
        drawSlider(canvas);
    }

    /**
     * 画滑块
     */
    private void drawSlider(Canvas canvas) {
        mPaint.setColor(Color.RED);
        mLeft = mPaddingLeft;
        mRight = mSliderWidth + mLeft;
        mTop = mPaddingLeft;
        mBottom = mHeight - mPaddingLeft;
        if (mRight + mCurrentX >= mWidth - mPaddingLeft) {
            RectF rectF = new RectF(mWidth - mSliderWidth - mPaddingLeft, mTop, mWidth - mPaddingLeft, mBottom);
            canvas.drawRoundRect(rectF, 50, 50, mPaint);
        } else if (mLeft + mCurrentX <= mPaddingLeft) {
            RectF rectF = new RectF(mLeft, mTop, mRight, mBottom);
            canvas.drawRoundRect(rectF, 50, 50, mPaint);
        } else {
            RectF rectF = new RectF(mLeft + mCurrentX, mTop, mRight + mCurrentX, mBottom);
            canvas.drawRoundRect(rectF, 50, 50, mPaint);
        }
    }

    /**
     * 控制滑动事件
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //getX 和 getRawX 的区别
        float downX = event.getX();
        float downY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (downX > mLeft && downX < mRight && downY > mTop && downY < mBottom) {
                    mLastX = downX;
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                mCurrentX = downX - mLastX;
                Log.d(TAG, "current X:" + mCurrentX);
                if (mRight + mCurrentX <= mWidth && mLeft + mCurrentX >= mPaddingLeft) {
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurrentX = 0;
                postInvalidate();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 画背景
     */
    private void drawBg(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rectF, 60, 60, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = getMeasureSize(widthMeasureSpec);
        int measureHeight = getMeasureSize(heightMeasureSpec);
        mWidth = measureWidth;
        mSliderWidth = mWidth * 3 / 8;
        setMeasuredDimension(measureWidth, measureHeight);
    }

    private int getMeasureSize(int size) {
        int ret = 0;
        int mode = MeasureSpec.getMode(size);
        int measureSize = MeasureSpec.getSize(size);
        switch (mode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                ret = measureSize;
                break;
            case MeasureSpec.AT_MOST:
                ret = measureSize > mMinSize ? mMinSize : measureSize;
                break;
            default:
                break;
        }
        return ret;
    }
}
