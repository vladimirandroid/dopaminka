package ru.dopaminka.tasks.joinTwoLetters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Size
import android.view.MotionEvent
import kotlin.math.pow

class FingerDrawer(
    private val horizontalPadding: Float,
    private val lineWidth: Float,
    private val onComplete: () -> Unit,
    var size: Size
) {

    private val completeThreshold = horizontalPadding * 2
    private var currentX: Float? = null
    private var currentY: Float? = null
    private var _isCompleted = false
    val isCompleted: Boolean
        get() = _isCompleted

    private val paint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = lineWidth
    }

    fun draw(canvas: Canvas) {
        if (_isCompleted || currentX == null || currentY == null) return
        val y1 = size.height / 2f
        val x1 = horizontalPadding
        canvas.drawLine(x1, y1, currentX!!, currentY!!, paint)
    }

    fun onTouch(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                currentX = event.x
                currentY = event.y
                _isCompleted = isNearTheEnd()
                if (_isCompleted) onComplete()
            }
            MotionEvent.ACTION_UP -> {
                currentX = null
                currentY = null
            }
        }
    }

    private fun isNearTheEnd(): Boolean {
        val endX = size.width - horizontalPadding
        val endY = size.height / 2f
        return ((currentX!! - endX).pow(2) + (currentY!! - endY).pow(2)).pow(0.5f) < completeThreshold
    }
}