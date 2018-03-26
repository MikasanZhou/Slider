package com.example.miaksan.sliderview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class SliderView extends View {

    private Context mContext;

    /**
     * 画笔
     */
    private Paint mPaint;

    private int mMinSize = 154;

    private int mHeight = 154;

    private int mWidth;

    private int mPaddingLeft = DensityUtils.dp2px(10);

    private int mSliderWidth;

    public SliderView(Context context) {
        this(context, null);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        drawBg(canvas);

        drawSlider(canvas);
    }

    private void drawSlider(Canvas canvas) {
        mPaint.setColor(Color.RED);
        RectF rectF = new RectF(mPaddingLeft, mPaddingLeft, mSliderWidth - mPaddingLeft, mHeight - mPaddingLeft);
        canvas.drawRoundRect(rectF,50,50,mPaint);
    }

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
        mSliderWidth = mWidth*3/8;
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
