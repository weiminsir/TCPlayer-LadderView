package com.wick.player;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


public class LadderProgressView extends View {

    private final String DIRECTION_LEFT = "left";
    private Path mPath = new Path();
    private Paint mPaint;
    private String mText = "";
    private int mTextColor = Color.parseColor("#ffffff");
    private int mBackGroungColor = Color.RED;
    private float mTextSize = 40f;
    private float mWidth = 0f;
    private float mHeight = 0f;
    private String mDirection = DIRECTION_LEFT;
    private int mAngle = 45;//预留倾斜角度


    public LadderProgressView(Context context) {
        this(context, null);
    }

    public LadderProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LadderProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ladderView);
        mText = ta.getString(R.styleable.ladderView_lad_text);
        mDirection = ta.getString(R.styleable.ladderView_lad_direction);
        mTextColor = ta.getColor(R.styleable.ladderView_lad_text_color, Color.WHITE);
        mTextSize = ta.getDimension(R.styleable.ladderView_lad_text_size, 0f);
        mBackGroungColor = ta.getColor(R.styleable.ladderView_lad_background_color, 0);
        mAngle = ta.getInt(R.styleable.ladderView_lad_angle, 0);
        ta.recycle();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackGroungColor);
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeight = h;
        this.mWidth = w;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        if (mDirection.equals(DIRECTION_LEFT)) {
            setLadderLeftDraw();
        } else {
            setLadderRightDraw();
        }
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        if (!TextUtils.isEmpty(mText)) {
            drawText(canvas);
        }
    }

    public void setLadderLeftDraw() {
        //设置将要用来画扇形的矩形的轮廓
        float r = mHeight / 2;//做圆弧半径
//        mPath.addCircle(mHeight / 2, mHeight / 2, mHeight / 2, Path.Direction.CW)
        //先画半圆
        RectF roundRectT = new RectF(0f, 0f, mHeight / 2, mHeight);
        float[] array = {r, r, 0f, 0f, 0f, 0f, r, r};
        mPath.addRoundRect(roundRectT, array, Path.Direction.CCW);
        //右边斜边
        mPath.moveTo(mHeight / 2, 0f);
        // 连接路径到点
        mPath.lineTo(mWidth, 0f);
        double x = mWidth - mHeight * (Math.tan(Math.toRadians(mAngle)));
        mPath.lineTo((float) x, mHeight);
        mPath.lineTo(mHeight / 2, mHeight);

    }

    public void setLadderRightDraw() {
        float r = mHeight / 2;
        RectF roundRectT = new RectF(mWidth - mHeight / 2, 0f, mWidth, mHeight);
        float[] array = {0f, 0f, r, r, r, r, 0f, 0f};
        mPath.addRoundRect(roundRectT, array, Path.Direction.CCW);
        double x = mHeight * (Math.tan(Math.toRadians(mAngle)));
        mPath.moveTo((float) x, 0f);
        // 连接路径到点
        mPath.lineTo(mWidth - mHeight / 2, 0f);
        mPath.lineTo(mWidth - mHeight / 2, mHeight);
        mPath.lineTo(0f, mHeight);
    }


    public void drawText(Canvas canvas) {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setTextSize(mTextSize);
        paint.setColor(mTextColor);
        paint.setTextAlign(Paint.Align.CENTER);
        Rect targetRect = new Rect(0, 0, (int) mWidth, (int) mHeight);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        float baseLineY = (targetRect.centerY() - top / 2 - bottom / 2);
//            val fontMetrics = paint.fontMetricsInt
//            val baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        canvas.drawText(mText, targetRect.centerX(), baseLineY, paint);
    }
}
