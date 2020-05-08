package com.example.testdemo.activity.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.example.testdemo.R;

import java.util.ArrayList;
import java.util.List;

import cjx.baselib.log.logUtil;

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
     * 当前到达第几步
     */
    private int currentStep;

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
    private TextPaint mTextPaint;
    private Paint mLinPaint;
    private Paint mCirclePaint;
    private Paint mEffectPaint;
    private Paint bitmapPaint;

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


    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
        invalidate();
    }

    private void init(Context context) {
        iconWidth = 50;
        marginStartAndEnd = 80;
        textMarginTop = 30;
        currentStep = 4;

        texts = new ArrayList<>();
        mBounds = new ArrayList<>();
        iconNomals = new ArrayList<>();
        iconChecks = new ArrayList<>();


        //添加加数据
//        texts.add("订单待支付");
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打的咋打算阿萨德阿萨德啊算打双打");
        texts.add("订单已支付");
        texts.add("商家已接单");
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打的咋打算阿萨德阿萨德啊算打双打");
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打的咋打算阿萨德阿萨德啊算打双打");
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打的咋打算阿萨德阿萨德啊算打双打");
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打的咋打算阿萨德阿萨德啊算打双打");
        texts.add("骑手已接单");
        texts.add("订单已送达");
        texts.add("订单待支付大大打算阿达是的大叔大婶打算打的咋打算阿萨德阿萨德啊算打双打");

        mColorTextDefault = Color.GRAY;
        mColorTextSelect = Color.BLUE;
        stepsNum = texts.size();

        for (int i = 0; i < stepsNum; i++) {
            Bitmap bitmap01 = BitmapFactory.decodeResource(getResources(), R.mipmap.complete);
            Bitmap bitmap02 = BitmapFactory.decodeResource(getResources(), R.mipmap.uncomplete);
            iconChecks.add(bitmap01);
            iconNomals.add(bitmap02);
        }

        //初始化文字属性
        mTextSize = 20;
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mColorTextDefault);
        mTextPaint.setAntiAlias(true);
        //初始化直线画笔
        mLinPaint = new Paint();
        mLinPaint.setColor(mColorTextDefault);
        mLinPaint.setStyle(Paint.Style.FILL);
        mLinPaint.setStrokeWidth(3);
        mLinPaint.setAntiAlias(true);

        //初始化虚线画笔
        mEffectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEffectPaint.setColor(Color.GRAY);
        mEffectPaint.setStrokeWidth(3);
        mEffectPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));


        //初始化被选中的圆圈
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        //初始化画icon的画笔
        bitmapPaint = new Paint();

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
       logUtil.d(TAG, "onSizeChanged: ");
        contentWidth = w - getPaddingLeft() - getPaddingRight();
        contentHieght = h;
        middleLinWidth = (float) (contentWidth - marginStartAndEnd * 2 - stepsNum * iconWidth) / (stepsNum - 1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
       logUtil.d(TAG, "onDraw: ");
//        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);//此方法画虚线，需要关闭硬件加速

        canvas.drawColor(Color.WHITE);

        //已完成步数---画实线
        float startStartX = marginStartAndEnd + iconWidth / 2;
        float satrtStopX = marginStartAndEnd + (currentStep - 0.5f) * iconWidth + (currentStep - 1f) * middleLinWidth;
        canvas.drawLine(startStartX, contentHieght / 2, satrtStopX, contentHieght / 2, mLinPaint);
//        //未完成步数--画虚线
        float endX = marginStartAndEnd + (currentStep - 0.5f) * iconWidth + (currentStep - 1f) * middleLinWidth;
        float endStopX = marginStartAndEnd + (stepsNum - 0.5f) * iconWidth + (stepsNum - 1f) * middleLinWidth;
        canvas.drawLine(endX, contentHieght / 2, endStopX, contentHieght / 2, mEffectPaint);


        //画圆
        for (int i = 1; i <= stepsNum; i++) {
            float x = marginStartAndEnd + (i - 0.5f) * iconWidth + (i - 1) * middleLinWidth;
            float y = contentHieght / 2f;
//            if (i <= currentStep) {
//                mCirclePaint.setColor(Color.WHITE);
//            } else {
//                mCirclePaint.setColor(Color.GRAY);
//            }
            canvas.drawCircle(x, y, iconWidth / 2, mCirclePaint);
//           logUtil.d(TAG, "cx=" + x + "---iconWidth=" + iconWidth / 2f);


            int left = (int) (marginStartAndEnd + (i - 1) * iconWidth + (i - 1) * middleLinWidth);
            int top = contentHieght / 2 - iconWidth / 2;
            //Rect src：对原图片的裁剪区域
            //RectF dst：将（裁剪完的）原图片绘制到View控件上的区域
            if (i <= currentStep) {
                canvas.drawBitmap(iconChecks.get(i - 1), null, new Rect(left, top, left + iconWidth, top + iconWidth), bitmapPaint);
            } else {
                canvas.drawBitmap(iconNomals.get(i - 1), null, new Rect(left, top, left + iconWidth, top + iconWidth), bitmapPaint);
            }

        }


//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        int startTextY = getHeight() - (int) fontMetrics.bottom;

        //画文字
        for (int i = 0; i < stepsNum; i++) {
            float startTextY = contentHieght / 2 + textMarginTop + iconWidth / 2;

            float maxTextWidth;
            if (i == 0 || i == stepsNum - 1) {
                maxTextWidth = marginStartAndEnd + iconWidth + middleLinWidth / 2f;
            } else {
                maxTextWidth = middleLinWidth + iconWidth;
            }
            if (i == 3) {
               logUtil.d(TAG, "textWidth=" + maxTextWidth + "---reallyWidth=" + mBounds.get(i).width());
            }

            float halfText;

            if (maxTextWidth < mBounds.get(i).width()) {
                halfText = maxTextWidth / 2;
            } else {
                halfText = mBounds.get(i).width() / 2;
            }

            float startX = marginStartAndEnd + (i + 0.5f) * iconWidth + (i) * middleLinWidth - halfText;
            if (startX < 0) {
                startX = 0;
            }

            if (mBounds.get(i).width() > maxTextWidth) {//text的宽度超出给定的宽度，换行
                canvas.save();
                canvas.translate(startX, startTextY);
//            StaticLayout myStaticLayout = new StaticLayout(texts.get(i), mTextPaint, 300, Layout.Alignment.ALIGN_NORMAL, 1.0f, 1.0f, true);
                @SuppressLint("DrawAllocation") StaticLayout myStaticLayout = new StaticLayout(texts.get(i),
                        0, texts.get(i).length() - 1,
                        mTextPaint,
                        (int) maxTextWidth,
                        Layout.Alignment.ALIGN_NORMAL,
                        1.0f,
                        0.0f,
                        true,
                        TextUtils.TruncateAt.END,
                        2
                );
                myStaticLayout.draw(canvas);
                canvas.restore();
            } else {
                canvas.drawText(texts.get(i), startX, startTextY + mBounds.get(i).height(), mTextPaint);//画文字的基准点是文字的左下角
            }
        }


    }
}
