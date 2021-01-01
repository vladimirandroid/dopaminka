package ru.dopaminka.tasks.draw.drawTextView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import ru.dopaminka.entity.reading.Point
import ru.dopaminka.entity.reading.Shape
import kotlin.math.absoluteValue

class FingerDrawer(
    private val onChange: () -> Unit,
    private val onShapeCompleted: () -> Unit
) {
    var letterSize: Int = 0

    private val paint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.STROKE
        strokeWidth = 15f
        strokeCap = Paint.Cap.ROUND
    }
    private var segments = mutableListOf<Segment>()
    private var currentSegment: Segment? = null
    private var completedSegments = mutableListOf<Segment>()
    private var letterIndex: Int = 0
    private var isDrawing = false
    private var lastMoveEvent: MotionEvent? = null
    private var lastEventX = 0f
    private var lastEventY = 0f

    fun setShape(shape: Shape, letterIndex: Int) {
        isDrawing = false
        segments.clear()
        completedSegments.clear()
        for (i in 1 until shape.points.size) {
            segments.add(Segment(shape.points[i - 1], shape.points[i]))
        }
        this.letterIndex = letterIndex
        currentSegment = segments.first()
    }

    fun draw(canvas: Canvas) {
        if (!isDrawing) return

        drawCompletedSegments(canvas)

        val fromX = currentSegment!!.point1.canvasX(letterIndex, letterSize)
        val fromY = currentSegment!!.point1.canvasY(letterSize)

        canvas.drawLine(fromX, fromY, lastEventX, lastEventY, paint)
    }

    private fun drawCompletedSegments(canvas: Canvas) {
        completedSegments.forEach {
            val x1 = it.point1.canvasX(letterIndex, letterSize)
            val y1 = it.point1.canvasY(letterSize)
            val x2 = it.point2.canvasX(letterIndex, letterSize)
            val y2 = it.point2.canvasY(letterSize)

            canvas.drawLine(
                x1,
                y1,
                x2,
                y2,
                paint
            )
        }
    }

    fun onTouch(event: MotionEvent): Boolean {
        var consumed = false
        if (event.action == MotionEvent.ACTION_DOWN &&
            isNearPoint(segments.first().point1, event)
        ) {
            isDrawing = true
            currentSegment = segments.first()
            lastEventX = event.x
            lastEventY = event.y
            consumed = true
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            lastEventX = event.x
            lastEventY = event.y
            lastMoveEvent = event
            if (isNearPoint(currentSegment!!.point2, event)) {
                completeSegment()
            }
            consumed = true
        } else if (event.action == MotionEvent.ACTION_UP) {
            lastMoveEvent = null
            isDrawing = false
            completedSegments.clear()
            consumed = true
        }
        if (consumed) onChange()
        return consumed
    }

    private fun completeSegment() {
        completedSegments.add(currentSegment!!)
        if (completedSegments.size == segments.size) {
            onShapeCompleted()
        } else {
            currentSegment = segments[segments.indexOf(currentSegment) + 1]
        }
    }

    private fun isNearPoint(point: Point, event: MotionEvent): Boolean {
        val x = letterSize * point.x / 10 + letterSize * letterIndex
        val y = letterSize * point.y / 10

        return (event.x - x).absoluteValue < 50 && (event.y - y).absoluteValue < 50
    }

    private class Segment(val point1: Point, val point2: Point)
}