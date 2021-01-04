package ru.dopaminka.tasks.joinTwoLetters

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View


class JoinView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val heightPx by lazy {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            heightDp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
    private val radiusPx by lazy {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            radiusDp.toFloat(),
            resources.displayMetrics
        )
    }
    private val promptPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private val progressCirclePaint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    private val completedCirclePaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }
    private val progressLinePaint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(20f, 40f), 0f)
        strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            lineWidth.toFloat(),
            resources.displayMetrics
        )
    }
    private val completedLinePaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            lineWidth.toFloat(),
            resources.displayMetrics
        )
    }

    private var isCompleted = false

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { invalidate() }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val left = radiusPx
        val allowedWidth = width - 2 * radiusPx
        val right = left + allowedWidth
        val y = height / 2f
        if (isCompleted) {
            canvas.drawCircle(left, y, radiusPx, completedCirclePaint)
            canvas.drawCircle(right, y, radiusPx, completedCirclePaint)
            canvas.drawLine(left, y, right, y, completedLinePaint)
        } else {
            canvas.drawCircle(left, y, radiusPx, progressCirclePaint)
            canvas.drawCircle(right, y, radiusPx, progressCirclePaint)
            canvas.drawLine(left, y, right, y, progressLinePaint)

            val position = left + allowedWidth * (animator.animatedValue as Float)
            canvas.drawCircle(position, y, radiusPx, promptPaint)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startPromptIfNotCompleted()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopPrompt()
    }

    private fun stopPrompt() {
        animator.cancel()
    }

    private fun startPromptIfNotCompleted() {
        if (isCompleted) return

        animator.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec = MeasureSpec.makeMeasureSpec(heightPx, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    companion object {
        private const val heightDp = 20
        private const val radiusDp = heightDp / 2
        private const val lineWidth = 5
    }
}