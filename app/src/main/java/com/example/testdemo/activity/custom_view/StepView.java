package com.example.testdemo.activity.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cjx.liyueyun.baselib.base.mvp.utils.logUtil;

/**
 * @author caicai
 * @create 2019/12/25
 * @Describe
 */
public class StepView extends View {
    private String TAG = this.getClass().getSimpleName();

    private List<String> texts;
    private List<Bitmap> iconNomals;
    private List<Bitmap> iconChecks;
    /**
     * 所有图标的长宽，，或者圆的半径
     */
    private int iconWidth;
    /**
     * 总的步骤数
     */
    private int stepsNum;

    /**
     * 第一个icon喝醉后一个icon距离左边和右边的距离
     *
     * @param context
     */
    private int marginStartAndEnd;

    /**
     * 保存每个TextView的测量矩形数据
     */
    private List<Rect> mBounds;

    /**
     * 文字距离上面的margin
     */
    private int textMarginTop;


    private int contentWidth;
    private int contentHieght;
    private float middleLinWidth;

    private int mColorTextDefault;
    private int mColorTextSelect;
    private int mTextSize;
    private Paint mTextPaint;
    private Paint mLinPaint;
    private Paint mCirclePaint;

    public StepView(Context context) {
        super(context);
        init(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        iconWidth = 20;
        stepsNum = 5;
        marginStartAndEnd = 80;
        textMarginTop = 60;

        texts = new ArrayList<>();
        mBounds = new ArrayList<>();
        iconNomals = new ArrayList<>();
        iconChecks = new ArrayList<>();
        //添加加数据
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打算打双打");
        texts.add("订单已支付");
        texts.add("商家已接单");
        texts.add("骑手已接单");
        texts.add("订单已送达");
        mColorTextDefault = Color.GRAY;
        mColorTextSelect = Color.BLUE;


        //初始化文字属性
        mTextSize = 20;
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColorTextDefault);
        mTextPaint.setAntiAlias(true);
        //初始化直线画笔
        mLinPaint = new Paint();
        mLinPaint.setColor(mColorTextDefault);
        mLinPaint.setStyle(Paint.Style.FILL);
        mLinPaint.setStrokeWidth(4);
        mLinPaint.setAntiAlias(true);

        //初始化被选中的圆圈
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.GRAY);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        //测量文字的大小
        measureText();

    }

    private void measureText() {
        for (int i = 0; i < texts.size(); i++) {
            Rect rect = new Rect();
            mTextPaint.getTextBounds(texts.get(i), 0, texts.get(i).length(), rect);
            mBounds.add(rect);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        logUtil.d_2(TAG, "onSizeChanged: ");
        contentWidth = w - getPaddingLeft() - getPaddingRight();
        contentHieght = h;
        middleLinWidth = (float) (contentWidth - marginStartAndEnd * 2 - stepsNum * iconWidth) / (stepsNum - 1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        logUtil.d_2(TAG, "onDraw: ");
//        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        for (int i = 1; i <= stepsNum; i++) {
            float startX = marginStartAndEnd + iconWidth / 2;
            float stopX = marginStartAndEnd + (i - 0.5f) * iconWidth + (i - 1f) * middleLinWidth;
            canvas.drawLine(startX, contentHieght / 2, stopX, contentHieght / 2, mLinPaint);
        }


        for (int i = 1; i <= stepsNum; i++) {
            float x = marginStartAndEnd + (i - 0.5f) * iconWidth + (i - 1) * middleLinWidth;
            float y = contentHieght / 2f;

            logUtil.d_2(TAG, "cx=" + x+"---iconWidth="+iconWidth/2f);
            canvas.drawCircle(x, y, iconWidth, mCirclePaint);
        }


//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        int startTextY = getHeight() - (int) fontMetrics.bottom;

        for (int i = 0; i <stepsNum; i++) {
            float startTextY = contentHieght / 2 + textMarginTop + iconWidth / 2;
            float startX=marginStartAndEnd+(i+0.5f)*iconWidth+(i)*middleLinWidth-mBounds.get(i).width()/2;
            canvas.drawText(texts.get(i), startX, startTextY, mTextPaint);


//            if (i == 1) {
//                canvas.drawText(texts.get(i), 0, startTextY, mTextPaint);
//            } else if (i == stepsNum) {
//                canvas.drawText(texts.get(i), getWidth() - mBounds.get(i).width(), startTextY, mTextPaint);
//            } else {
//                canvas.drawText(texts.get(i), mCircleRadius + ((mLineLength / (texts.size() - 1)) * i) - (mBounds.get(i).width() / 2), startTextY, mTextPaint);
//            }

        }


    }
}
