package ru.dopaminka.tasks.drawText.drawTextView

import ru.dopaminka.entity.reading.Point

fun Point.canvasX(letterIndex: Int, letterSize: Int): Float {
    return x * letterSize / 10f + letterIndex * letterSize
}

fun Point.canvasY(letterSize: Int): Float {
    return y * letterSize / 10f
}