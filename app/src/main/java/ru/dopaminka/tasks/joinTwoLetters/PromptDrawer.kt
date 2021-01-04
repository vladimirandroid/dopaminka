package ru.dopaminka.tasks.joinTwoLetters

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class PromptDrawer(val horizontalPadding: Float, val circleRadius: Float, onChange: () -> Unit) {

    private val promptPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatMode = ValueAnimator.RESTART
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { onChange() }
    }

    fun draw(canvas: Canvas) {
        val y = canvas.height / 2f
        val allowedWidth = canvas.width - 2 * horizontalPadding
        val position = horizontalPadding + allowedWidth * (animator.animatedValue as Float)
        canvas.drawCircle(position, y, circleRadius, promptPaint)
    }


    fun start() {
        animator.start()
    }

    fun stop() {
        animator.cancel()
    }
}