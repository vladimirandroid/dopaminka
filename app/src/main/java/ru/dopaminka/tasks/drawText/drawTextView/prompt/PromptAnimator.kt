package ru.dopaminka.tasks.drawText.drawTextView.prompt

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.view.View
import ru.dopaminka.entity.reading.Shape

class PromptAnimator(private val view: View) {

    private var letterIndex: Int = 0
    private var shape: Shape? = null
    private val drawer = PromptDrawer()
    private val animator = ValueAnimator.ofFloat(0f, 1f)

    var letterSize: Int
        set(value) {
            drawer.letterSize = value
        }
        get() = drawer.letterSize

    init {
        animator.addUpdateListener {
            view.invalidate()
        }
    }

    fun setShape(shape: Shape, letterIndex: Int) {
        animator.start()
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        this.shape = shape
        this.letterIndex = letterIndex
        drawer.shape = shape
        animator.duration = (drawer.totalPath / speed).toLong()
    }

    fun draw(canvas: Canvas, index: Int) {
        if (animator.isPaused) return
        drawer.draw(canvas, animator.animatedValue as Float, index)
    }


    fun start() {
        animator.start()
    }

    fun stop() {
        animator.pause()
    }

    companion object {
        private const val speed = 0.005f
    }
}