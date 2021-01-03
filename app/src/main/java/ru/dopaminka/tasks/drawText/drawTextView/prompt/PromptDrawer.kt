package ru.dopaminka.tasks.drawText.drawTextView.prompt

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import ru.dopaminka.entity.reading.Point
import ru.dopaminka.entity.reading.Shape

class PromptDrawer {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL_AND_STROKE
    }
    var letterSize: Int = 0
    private var segments = emptyList<Segment>()
    var shape: Shape? = null
        set(value) {
            field = value
            if (value == null || value.points.isEmpty()) {
                segments = emptyList()
            } else {
                segments = getSegments(value)
            }
        }
    var totalPath: Float = 0f


    fun draw(canvas: Canvas, progress: Float, index: Int) {
        if (segments.isEmpty()) return

        val currentSegment = segments.first { it.toProgress >= progress }
        val localProgress = getLocalProgress(currentSegment, progress)

        val x =
            currentSegment.from.x + (currentSegment.to.x - currentSegment.from.x) * localProgress
        val y =
            currentSegment.from.y + (currentSegment.to.y - currentSegment.from.y) * localProgress

        canvas.drawCircle(
            x / 10 * letterSize + index * letterSize,
            y / 10 * letterSize,
            20f,
            paint
        )
    }

    private fun getLocalProgress(segment: Segment, globalProgress: Float): Float {
        return (globalProgress - segment.fromProgress) / (segment.toProgress - segment.fromProgress)
    }

    private fun getSegments(shape: Shape): List<Segment> {
        val segmentsPaths = mutableListOf<Float>()
        for (i in 1 until shape.points.size) {
            val p1 = shape.points[i - 1]
            val p2 = shape.points[i]
            segmentsPaths.add(p1.distanceTo(p2))
        }
        totalPath = segmentsPaths.sum()

        var fromWeight = 0f
        var toWeight: Float

        val result = mutableListOf<Segment>()
        for (i in 1 until shape.points.size) {
            toWeight = fromWeight + segmentsPaths[i - 1] / totalPath
            result.add(
                Segment(
                    shape.points[i - 1],
                    shape.points[i],
                    fromWeight,
                    toWeight
                )
            )
            fromWeight = toWeight
        }

        return result
    }

    companion object {
        private const val speed = 1
    }

    private class Segment(
        val from: Point,
        val to: Point,
        val fromProgress: Float,
        val toProgress: Float
    )
}