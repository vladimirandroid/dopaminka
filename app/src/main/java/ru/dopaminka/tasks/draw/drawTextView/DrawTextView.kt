package ru.dopaminka.tasks.draw.drawTextView

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import ru.dopaminka.entity.reading.*
import ru.dopaminka.tasks.draw.drawTextView.prompt.PromptAnimator

class DrawTextView(context: Context, val readable: Readable) : View(context) {

    var listener: CompleteListener? = null
    val isCompleted: Boolean
        get() = _isCompleted

    private var _isCompleted = false
    private val letters: List<Letter> = getLetters(readable)
    private var letterSize: Int = 0
    private val pointsDrawer = PointsDrawer()
    private val promptDrawer = PromptAnimator(this)
    private val fingerDrawer = FingerDrawer(this::invalidate) { startNextShape() }
    private val completedLettersDrawer = CompletedShapesDrawer()

    private var currentLetterIndex: Int = 0
    private var currentShape = letters[currentLetterIndex].shapes.first()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (_isCompleted) return false

        return fingerDrawer.onTouch(event)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        promptDrawer.start()
        fingerDrawer.setShape(currentShape, currentLetterIndex)
        promptDrawer.setShape(currentShape, currentLetterIndex)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        promptDrawer.stop()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in letters.indices) {
            pointsDrawer.draw(canvas, letters[i], i)
        }
        completedLettersDrawer.draw(canvas)
        if (!_isCompleted) {
            fingerDrawer.draw(canvas)
            promptDrawer.draw(canvas, currentLetterIndex)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxLetterSize = MeasureSpec.getSize(widthMeasureSpec) / letters.size
        val heightSpec = MeasureSpec.makeMeasureSpec(maxLetterSize, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        letterSize = w / letters.size
        if (letterSize > h) letterSize = h
        pointsDrawer.letterSize = letterSize
        promptDrawer.letterSize = letterSize
        fingerDrawer.letterSize = letterSize
        completedLettersDrawer.letterSize = letterSize
    }

    private fun getLetters(readable: Readable): List<Letter> {
        return when (readable) {
            is Letter -> listOf(readable)
            is Syllable -> readable.letters
            is Word -> readable.syllables.map { it.letters }.flatten()
            is Text -> readable.readables.map { getLetters(it) }.flatten()
            else -> emptyList()
        }
    }

    private fun startNextShape() {
        completedLettersDrawer.completeShape(currentShape, currentLetterIndex)
        val letter = letters[currentLetterIndex]
        if (currentShape == letter.shapes.last()) {
            startNextLetter()
        } else {
            val shapeIndex = letter.shapes.indexOf(currentShape) + 1
            currentShape = letter.shapes[shapeIndex]
        }
        fingerDrawer.setShape(currentShape, currentLetterIndex)
        promptDrawer.setShape(currentShape, currentLetterIndex)
    }

    private fun startNextLetter() {
        if (currentLetterIndex + 1 == letters.size) {
            _isCompleted = true
            listener?.onComplete()
            promptDrawer.stop()
        } else {
            currentLetterIndex++
            currentShape = letters[currentLetterIndex].shapes.first()
        }
    }


    interface CompleteListener {
        fun onComplete()
    }
}