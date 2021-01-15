package cmy.project.lib_base_ui.widget.checkbox

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Checkable
import cmy.project.lib_base.utils.ScreenUtils
import cmy.project.lib_base_ui.R
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

/**简单的选择框*/
class SmoothCheckBox @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0):
    View(context, attributeSet, defStyle) , Checkable {

    companion object{
       // private const val KEY_INSTANCE_STATE = "InstanceState"

        private const val COLOR_TICK = Color.WHITE
        private const val COLOR_UNCHECKED = Color.WHITE
        private val COLOR_CHECKED = Color.parseColor("#FB4846")
        private val COLOR_FLOOR_UNCHECKED = Color.parseColor("#DFDFDF")

        private const val DEF_DRAW_SIZE = 25
        private const val DEF_ANIM_DURATION = 300


        private fun getGradientColor(startColor: Int, endColor: Int, percent: Float): Int {
            val startA = Color.alpha(startColor)
            val startR = Color.red(startColor)
            val startG = Color.green(startColor)
            val startB = Color.blue(startColor)
            val endA = Color.alpha(endColor)
            val endR = Color.red(endColor)
            val endG = Color.green(endColor)
            val endB = Color.blue(endColor)
            val currentA = (startA * (1 - percent) + endA * percent).toInt()
            val currentR = (startR * (1 - percent) + endR * percent).toInt()
            val currentG = (startG * (1 - percent) + endG * percent).toInt()
            val currentB = (startB * (1 - percent) + endB * percent).toInt()
            return Color.argb(currentA, currentR, currentG, currentB)
        }
    }

      var mListener: OnCheckedChangeListener? = null
        set(value) {
          field = value
        }



    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private  var mTickPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private  var mFloorPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mTickPoints: Array<Point> = arrayOf(Point(), Point(), Point())
    private var mCenterPoint: Point = Point()
    private var mTickPath: Path = Path()


    private var mLeftLineDistance = 0f
    private  var mRightLineDistance:Float = 0f
    private  var mDrewDistance:Float = 0f
    private var mScaleVal = 1.0f
    private  var mFloorScale:Float = 1.0f
    private var mWidth = 0
    private  var mAnimDuration:Int = 0
    private  var mStrokeWidth:Int = 0
    private var mCheckedColor =0
    private  var mUnCheckedColor:Int = 0
    private  var mFloorColor:Int = 0
    private  var mFloorUnCheckedColor:Int = 0
    private var mChecked = false
    private var mTickDrawing = false


    /****************************************/
    init {
        init(attributeSet)
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        this.isChecked = !isChecked
    }

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        reset()
        invalidate()
        mListener?.onCheckedChanged(this@SmoothCheckBox, mChecked)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec))
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mWidth = measuredWidth
        mStrokeWidth = if (mStrokeWidth == 0) measuredWidth / 10 else mStrokeWidth
        mStrokeWidth = if (mStrokeWidth > measuredWidth / 5) measuredWidth / 5 else mStrokeWidth
        mStrokeWidth = if (mStrokeWidth < 3) 3 else mStrokeWidth
        mCenterPoint.x = mWidth / 2
        mCenterPoint.y = measuredHeight / 2
        mTickPoints[0].x = (measuredWidth.toFloat() / 30 * 7).roundToInt()
        mTickPoints[0].y = (measuredHeight.toFloat() / 30 * 14).roundToInt()
        mTickPoints[1].x = (measuredWidth.toFloat() / 30 * 13).roundToInt()
        mTickPoints[1].y = (measuredHeight.toFloat() / 30 * 20).roundToInt()
        mTickPoints[2].x = (measuredWidth.toFloat() / 30 * 22).roundToInt()
        mTickPoints[2].y = (measuredHeight.toFloat() / 30 * 10).roundToInt()
        mLeftLineDistance = sqrt(
            (mTickPoints[1].x - mTickPoints[0].x).toDouble().pow(2.0) +
                    (mTickPoints[1].y - mTickPoints[0].y).toDouble().pow(2.0)
        ).toFloat()
        mRightLineDistance = sqrt(
            (mTickPoints[2].x - mTickPoints[1].x).toDouble().pow(2.0) +
                    (mTickPoints[2].y - mTickPoints[1].y).toDouble().pow(2.0)
        ).toFloat()
        mTickPaint.strokeWidth = mStrokeWidth.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        drawBorder(canvas)
        drawCenter(canvas)
        drawTick(canvas)
    }

    /***************************************/
    /**初始化代码*/
    private fun init(attrs: AttributeSet?) {
        var tickColor = COLOR_TICK
        attrs?.let {
            val ta = context.obtainStyledAttributes(it, R.styleable.SmoothCheckBox)
            tickColor =  ta.getColor(R.styleable.SmoothCheckBox_color_tick, COLOR_TICK)
            mAnimDuration = ta.getInt(R.styleable.SmoothCheckBox_duration, DEF_ANIM_DURATION)
            mFloorColor =
                ta.getColor(
                    R.styleable.SmoothCheckBox_color_unchecked_stroke,
                        COLOR_FLOOR_UNCHECKED
                )
            mCheckedColor = ta.getColor(R.styleable.SmoothCheckBox_color_checked, COLOR_CHECKED)
            mUnCheckedColor = ta.getColor(
                R.styleable.SmoothCheckBox_color_unchecked,
                    COLOR_UNCHECKED
            )
            mStrokeWidth = ta.getDimensionPixelSize(
                R.styleable.SmoothCheckBox_stroke_width, ScreenUtils.dpToPx(0)
            )
            ta.recycle()
        }

        mFloorUnCheckedColor = mFloorColor

        mTickPaint.style = Paint.Style.STROKE
        mTickPaint.strokeCap = Paint.Cap.ROUND
        mTickPaint.color = tickColor

        mFloorPaint.style = Paint.Style.FILL
        mFloorPaint.color = mFloorColor

        mPaint.style = Paint.Style.FILL
        mPaint.color = mCheckedColor


        setOnClickListener {
            toggle()
            mTickDrawing = false
            mDrewDistance = 0f
            if (isChecked) {
                startCheckedAnimation()
            } else {
                startUnCheckedAnimation()
            }
        }
    }

    /**启动取消动画*/
    private fun startUnCheckedAnimation() {
        val animator = ValueAnimator.ofFloat(0f, 1.0f)
        animator.duration = mAnimDuration.toLong()
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            mScaleVal = animation.animatedValue as Float
            mFloorColor =
                getGradientColor(mCheckedColor, mFloorUnCheckedColor, mScaleVal)
            postInvalidate()
        }
        animator.start()
        val floorAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f)
        floorAnimator.duration = mAnimDuration.toLong()
        floorAnimator.interpolator = LinearInterpolator()
        floorAnimator.addUpdateListener { animation ->
            mFloorScale = animation.animatedValue as Float
            postInvalidate()
        }
        floorAnimator.start()
    }

    /**启动选中动画*/
    private fun startCheckedAnimation() {
        val animator = ValueAnimator.ofFloat(1.0f, 0f)
        animator.duration = (mAnimDuration / 3 * 2).toLong()
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            mScaleVal = animation.animatedValue as Float
            mFloorColor = getGradientColor(mUnCheckedColor, mCheckedColor, 1 - mScaleVal)
            postInvalidate()
        }
        animator.start()
        val floorAnimator = ValueAnimator.ofFloat(1.0f, 0.8f, 1.0f)
        floorAnimator.duration = mAnimDuration.toLong()
        floorAnimator.interpolator = LinearInterpolator()
        floorAnimator.addUpdateListener { animation ->
            mFloorScale = animation.animatedValue as Float
            postInvalidate()
        }
        floorAnimator.start()
        drawTickDelayed()
    }

    /**绘画线程*/
    private fun drawTickDelayed() {
        postDelayed({
            mTickDrawing = true
            postInvalidate()
        }, mAnimDuration.toLong())
    }


    /**
     * 设置选中状态
     * @param checked checked
     * @param animate change with animation
     */
    fun setChecked(checked: Boolean, animate: Boolean) {
        if (animate) {
            mTickDrawing = false
            mChecked = checked
            mDrewDistance = 0f
            if (checked) {
                startCheckedAnimation()
            } else {
                startUnCheckedAnimation()
            }
            mListener?.onCheckedChanged(this@SmoothCheckBox, mChecked)
        } else {
            this.isChecked = checked
        }
    }

    /**数据重置*/
    private fun reset() {
        mTickDrawing = true
        mFloorScale = 1.0f
        mScaleVal = if (isChecked) 0f else 1.0f
        mFloorColor = if (isChecked) mCheckedColor else mFloorUnCheckedColor
        mDrewDistance = if (isChecked) mLeftLineDistance + mRightLineDistance else 0f
    }


    /**重新计算宽高*/
    private fun measureSize(measureSpec: Int): Int {
        val defSize: Int = ScreenUtils.dpToPx(DEF_DRAW_SIZE)
        val specSize = MeasureSpec.getSize(measureSpec)
        val specMode = MeasureSpec.getMode(measureSpec)
        var result = 0
        when (specMode) {
            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> result = defSize.coerceAtMost(specSize)
            MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }


    private fun drawCenter(canvas: Canvas) {
        mPaint.color = mUnCheckedColor
        val radius = (mCenterPoint.x - mStrokeWidth) * mScaleVal
        canvas.drawCircle(mCenterPoint.x.toFloat(), mCenterPoint.y.toFloat(), radius, mPaint)
    }

    private fun drawBorder(canvas: Canvas) {
        mFloorPaint.color = mFloorColor
        val radius = mCenterPoint.x
        canvas.drawCircle(
                mCenterPoint.x.toFloat(),
                mCenterPoint.y.toFloat(),
                radius * mFloorScale,
                mFloorPaint
        )
    }

    private fun drawTick(canvas: Canvas) {
        if (mTickDrawing && isChecked) {
            drawTickPath(canvas)
        }
    }


    private fun drawTickPath(canvas: Canvas) {
        mTickPath.reset()
        // draw left of the tick
        if (mDrewDistance < mLeftLineDistance) {
            val step: Float = if (mWidth / 20.0f < 3){ 3f }else{ mWidth / 20.0f}
            mDrewDistance += step
            val stopX =
                mTickPoints[0].x + (mTickPoints[1].x - mTickPoints[0].x) * mDrewDistance / mLeftLineDistance
            val stopY =
                mTickPoints[0].y + (mTickPoints[1].y - mTickPoints[0].y) * mDrewDistance / mLeftLineDistance
            mTickPath.moveTo(mTickPoints[0].x.toFloat(), mTickPoints[0].y.toFloat())
            mTickPath.lineTo(stopX, stopY)
            canvas.drawPath(mTickPath, mTickPaint)
            if (mDrewDistance > mLeftLineDistance) {
                mDrewDistance = mLeftLineDistance
            }
        } else {
            mTickPath.moveTo(mTickPoints[0].x.toFloat(), mTickPoints[0].y.toFloat())
            mTickPath.lineTo(mTickPoints[1].x.toFloat(), mTickPoints[1].y.toFloat())
            canvas.drawPath(mTickPath, mTickPaint)

            // draw right of the tick
            if (mDrewDistance < mLeftLineDistance + mRightLineDistance) {
                val stopX =
                    mTickPoints[1].x + (mTickPoints[2].x - mTickPoints[1].x) * (mDrewDistance - mLeftLineDistance) / mRightLineDistance
                val stopY =
                    mTickPoints[1].y - (mTickPoints[1].y - mTickPoints[2].y) * (mDrewDistance - mLeftLineDistance) / mRightLineDistance
                mTickPath.reset()
                mTickPath.moveTo(mTickPoints[1].x.toFloat(), mTickPoints[1].y.toFloat())
                mTickPath.lineTo(stopX, stopY)
                canvas.drawPath(mTickPath, mTickPaint)
                val step: Float = if (mWidth / 20 < 3) 3f else (mWidth / 20).toFloat()
                mDrewDistance += step
            } else {
                mTickPath.reset()
                mTickPath.moveTo(mTickPoints[1].x.toFloat(), mTickPoints[1].y.toFloat())
                mTickPath.lineTo(mTickPoints[2].x.toFloat(), mTickPoints[2].y.toFloat())
                canvas.drawPath(mTickPath, mTickPaint)
            }
        }

        // invalidate
        if (mDrewDistance < mLeftLineDistance + mRightLineDistance) {
            postDelayed({ postInvalidate() }, 10)
        }
    }


    fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mListener = listener
    }

    /**选择监听接口*/
    interface OnCheckedChangeListener {
        fun onCheckedChanged(checkBox: SmoothCheckBox, isChecked: Boolean)
    }


}