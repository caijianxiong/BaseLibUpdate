package com.example.testdemo.activity.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cjx.liyueyun.baselib.base.mvp.utils.logUtil;

/**
 * @author caicai
 * @create 2019/12/26
 * @Describe
 */
public class VerticalStepView extends View {

    private String TAG = this.getClass().getSimpleName();

    private List<String> textRight;
    private List<String> textLeft;
    private List<Bitmap> iconNomals;
    private List<Bitmap> iconChecks;
    private int contentWidth;
    private int contentHeight;
    /**
     * 所有图标的长宽，，或者圆的半径
     */
    private float iconWidth;
    /**
     * 总的步骤数
     */
    private int stepsNum;
    /**
     * 当前到达第几步
     */
    private int currentStep;
    private float marginTopAndBottom;
    private float marginStartAndEnd;
    private float marginMiddle;

    /**
     * 文字相关
     */
    private int leftTextSize = 18;
    private float leftTextWidth;
    private float rightTextWidth;
    private float rightTextHeight;

    /**
     * 保存每个TextView的测量矩形数据
     */
    private List<Rect> mBoundsLeft;
    private List<Rect> mBoundsRight;
    private List<Float> mHieghtRights;
    private TextPaint mTextLeftPaint;
    private TextPaint mTextRightPaint;


    public VerticalStepView(Context context) {
        super(context);
        init(context);
    }

    public VerticalStepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        textLeft = new ArrayList<>();
        textRight = new ArrayList<>();
        iconNomals = new ArrayList<>();
        iconChecks = new ArrayList<>();
        mHieghtRights = new ArrayList<>();
        mBoundsLeft = new ArrayList<>();
        mBoundsRight = new ArrayList<>();


        textLeft.add("12-20");
        textLeft.add("12-21");
        textLeft.add("12-22");
        textLeft.add("12-23");
        textLeft.add("12-24");
        textLeft.add("昨天");
        textLeft.add("昨天");
        textLeft.add("昨天");
        textLeft.add("昨天");
        textLeft.add("今天");
        textLeft.add("今天");
        textLeft.add("今天");

        for (int i = 0; i < textLeft.size(); i++) {
            textRight.add("发挥额达到嗯嗯哒的是的阿萨德阿萨德阿萨德啊的阿萨德阿萨德" + (i * 123454));
        }


        //左边文字画笔
        mTextLeftPaint = new TextPaint();
        mTextLeftPaint.setTextSize(leftTextSize);
        mTextLeftPaint.setColor(Color.BLACK);
        mTextLeftPaint.setAntiAlias(true);


        mTextRightPaint = new TextPaint();
        mTextRightPaint.setTextSize(20);
        mTextRightPaint.setColor(Color.BLACK);
        mTextRightPaint.setAntiAlias(true);

        measureText();
    }

    private void measureText() {
        //左侧的文字不能超过

        for (int i = 0; i < textLeft.size(); i++) {
            Rect rect = new Rect();
            mTextLeftPaint.getTextBounds(textLeft.get(i), 0, textLeft.get(i).length(), rect);
            mBoundsLeft.add(rect);
        }

        for (int i = 0; i < textRight.size(); i++) {
            Rect rect = new Rect();
            mTextRightPaint.getTextBounds(textRight.get(i), 0, textRight.get(i).length(), rect);
            rightTextHeight = rect.height();
            mBoundsRight.add(rect);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentWidth = w - getPaddingEnd();
        contentHeight = h - getPaddingBottom();


        logUtil.d_2(TAG,"currentThread:"+Thread.currentThread().getName());

        //根据父布局的宽度，大概计算出，，左侧文字，，右侧文字，中间流程图标占据的宽度
        //各位置水平所占百分比
        marginStartAndEnd = contentWidth * 0.03f;
        marginTopAndBottom = contentWidth * 0.03f;
        marginMiddle = contentWidth * 0.01f;
        leftTextWidth = contentWidth * 0.12f;
        rightTextWidth = contentWidth * 0.74f;//这个是自适应的，，后期等于1-其他的百分比
        iconWidth = contentWidth * 0.06f;

        //根据右边文字的多少计算每一个文字占据的高度
        mHieghtRights.clear();
        for (int i = 0; i < textRight.size(); i++) {
            float hang = (float) Math.ceil(mBoundsRight.get(i).width() / rightTextWidth);
            float canvasTextHeight = hang * rightTextHeight;
            logUtil.d_2(TAG, "hangCount=" + hang + "----singleHeight=" + rightTextHeight);
            mHieghtRights.add(canvasTextHeight);
        }

        logUtil.d_2(TAG, "\n");

        for (int i = 0; i < mHieghtRights.size(); i++) {
            logUtil.d_2(TAG, "height：" + mHieghtRights.get(i));
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

//        for (int i = 0; i < textRight.size(); i++) {
//
//            float startX = contentWidth - rightTextWidth;
//            float startY = 0;
//
//            if (mBoundsRight.get(i).width() > rightTextWidth) {
//                canvas.save();
//                canvas.translate(startX, startTextY);
////            StaticLayout myStaticLayout = new StaticLayout(texts.get(i), mTextPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, true);
//                @SuppressLint("DrawAllocation") StaticLayout myStaticLayout = new StaticLayout(texts.get(i),
//                        0, texts.get(i).length() - 1,
//                        mTextPaint,
//                        (int) maxTextWidth,
//                        Layout.Alignment.ALIGN_NORMAL,
//                        1.0f,
//                        0.0f,
//                        true,
//                        TextUtils.TruncateAt.END,
//                        2
//                );
//                myStaticLayout.draw(canvas);
//                canvas.restore();
//            }
//
//            canvas.drawText();
//        }


    }

}
