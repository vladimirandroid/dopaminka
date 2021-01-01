package ru.dopaminka.tasks.draw.drawTextView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ru.dopaminka.entity.reading.Letter
import ru.dopaminka.entity.reading.Point
import ru.dopaminka.entity.reading.Shape

class PointsDrawer {

    var letterSize: Int = 0
    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL_AND_STROKE
    }

    fun draw(canvas: Canvas, letter: Letter, index: Int) {
        letter.shapes.forEach { drawShape(canvas, it, index) }
    }

    private fun drawShape(canvas: Canvas, shape: Shape, index: Int) {
        shape.points.forEach { drawPoint(canvas, it, index) }
    }

    private fun drawPoint(canvas: Canvas, point: Point, index: Int) {
        canvas.drawCircle(
            point.x / 10 * letterSize + index * letterSize,
            point.y / 10 * letterSize,
            20f,
            paint
        )
    }
}