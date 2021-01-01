package ru.dopaminka.tasks.draw.drawTextView

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ru.dopaminka.entity.reading.Shape

class CompletedShapesDrawer {
    var letterSize: Int = 0
    private val paint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 15f
        strokeCap = Paint.Cap.ROUND
    }
    private var completedShapes = mutableListOf<CompletedShape>()

    fun completeShape(shape: Shape, index: Int) {
        completedShapes.add(CompletedShape(shape, index))
    }

    fun draw(canvas: Canvas) {
        completedShapes.forEach {
            for (i in 1 until it.shape.points.size) {
                val point1 = it.shape.points[i - 1]
                val point2 = it.shape.points[i]
                canvas.drawLine(
                    point1.canvasX(it.index, letterSize),
                    point1.canvasY(letterSize),
                    point2.canvasX(it.index, letterSize),
                    point2.canvasY(letterSize),
                    paint
                )
            }
        }
    }

    class CompletedShape(val shape: Shape, val index: Int)
}