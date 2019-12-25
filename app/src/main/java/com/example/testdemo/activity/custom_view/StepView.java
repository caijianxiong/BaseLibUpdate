package com.example.testdemo.activity.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cjx.liyueyun.baselib.base.mvp.utils.logUtil;

/**
 * @author caicai
 * @create 2019/12/25
 * @Describe
 */
public class StepView extends View {
    private String TAG = this.getClass().getSimpleName();

    public StepView(Context context) {
        super(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        logUtil.d_2(TAG, "onSizeChanged: ");
        super.onSizeChanged(w, h, oldw, oldh);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        logUtil.d_2(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
}
