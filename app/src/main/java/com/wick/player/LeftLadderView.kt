package com.wick.player

import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View


/**
 * 涨跌版左视图  暂时，宽高必须不为具体数值
 *
 * */

class LeftLadderView : View {


    private var mPath: Path = Path()
    private var mPaint: Paint = Paint()
    private var mText = "看涨"
    private var mTextColor: Int = Color.parseColor("#ffffff")
    private var mBackGroungColor = Color.RED
    private var mTextSize = 40f
    private var mWidth = 0f
    private var mHeight = 0f
    private var mAngle = 45//预留倾斜角度

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.mHeight = h.toFloat()
        this.mWidth = w.toFloat()
    }


    override fun onDraw(canvas: Canvas?) {
        //设置将要用来画扇形的矩形的轮廓

        mPaint.style = Paint.Style.FILL
        mPaint.color = mBackGroungColor
        mPaint.isAntiAlias = true;//消除锯齿

        mPath?.reset()
        val r = mHeight / 2//做圆弧半径
//        mPath.addCircle(mHeight / 2, mHeight / 2, mHeight / 2, Path.Direction.CW)
        //先画半圆
        val roundRectT = RectF(0f, 0f, mHeight / 2, mHeight)
        mPath.addRoundRect(roundRectT, floatArrayOf(r, r, 0f, 0f, 0f, 0f, r, r), Path.Direction.CCW)
        //右边斜边
        mPath.moveTo(mHeight / 2, 0f)
        // 连接路径到点
        mPath.lineTo(mWidth, 0f)
        mPath.lineTo(mWidth - mHeight, mHeight)
        mPath.lineTo(mHeight / 2, mHeight)
        mPath.close()

        canvas?.drawPath(mPath, mPaint)
//        RectF(float left, float top, float right, float bottom)
//        方法对应的是个点。然而中间这个矩形的宽度width=C(right)-A(left)，高度height=D(bottom)-B(top)
        if (!TextUtils.isEmpty(mText)) {
            drawText(canvas)
        }
    }

    fun drawText(canvas: Canvas?) {

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.isAntiAlias = true;//消除锯齿
        paint.textSize = mTextSize
        paint.color = mTextColor
        paint.textAlign = Paint.Align.CENTER
        val targetRect = Rect(0, 0, width, height)

        val fontMetrics = paint.getFontMetrics()
        val top = fontMetrics.top//为基线到字体上边框的距离,即上图中的top
        val bottom = fontMetrics.bottom//为基线到字体下边框的距离,即上图中的bottom

        val baseLineY = (targetRect.centerY() - top / 2 - bottom / 2)
//            val fontMetrics = paint.fontMetricsInt
//            val baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        canvas?.drawText(mText, targetRect.centerX().toFloat(), baseLineY, paint)

    }

    fun initView(context: Context, attrs: AttributeSet?) {


    }
}