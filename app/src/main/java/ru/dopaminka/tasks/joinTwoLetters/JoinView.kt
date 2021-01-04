package ru.dopaminka.tasks.joinTwoLetters

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Size
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class JoinView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var completeListener: (() -> Unit)? = null
    val isCompleted: Boolean
        get() = fingerDrawer.isCompleted

    private val radiusPx by lazy {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            radiusDp.toFloat(),
            resources.displayMetrics
        )
    }
    private val lineWidthPx by lazy {
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            lineWidthDp.toFloat(),
            resources.displayMetrics
        )
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
        pathEffect = DashPathEffect(floatArrayOf(50f, 20f), 0f)
        strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            lineWidthPx/2,
            resources.displayMetrics
        )
    }
    private val completedLinePaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            lineWidthPx,
            resources.displayMetrics
        )
    }
    private val promptDrawer by lazy { PromptDrawer(radiusPx, radiusPx) { invalidate() } }
    private val fingerDrawer by lazy {
        FingerDrawer(
            radiusPx,
            lineWidthPx,
            { completeListener?.invoke() },
            Size(0, 0)
        )
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
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

            promptDrawer.draw(canvas)
            if (isCompleted) {
                canvas.drawLine(left, y, right, y, completedLinePaint)
            } else {
                fingerDrawer.draw(canvas)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isCompleted) return false
        fingerDrawer.onTouch(event!!)
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fingerDrawer.size = Size(w, h)
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
        promptDrawer.stop()
    }

    private fun startPromptIfNotCompleted() {
        if (isCompleted) return

        promptDrawer.start()
    }

    companion object {
        private const val radiusDp = 10
        private const val lineWidthDp = 5
    }
}