package ru.dopaminka.readable

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CharacterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setTextColor(Color.RED)
        textSize = 130.0f
    }

    fun highlight(progress: Float) {
        scaleX = if (progress < 0.5) {
            1 + progress
        } else {
            2 - progress
        }

        scaleY = scaleX
    }
}
