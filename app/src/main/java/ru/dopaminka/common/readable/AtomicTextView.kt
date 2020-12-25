package ru.dopaminka.common.readable

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import ru.dopaminka.entity.reading.AtomicText

open class AtomicTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CharacterView(context, attrs, defStyleAttr) {

    var atomicText: AtomicText? = null
        set(value) {
            field = value
            text = value?.text
        }

    fun highlight(duration: Int) {
        Log.d("DOPALOG", "AtomicText.highlight duration=$duration")

        val growX = ObjectAnimator
            .ofFloat(this, View.SCALE_X, 1f, 1.5f)
            .setDuration((duration / 2).toLong())
        val growY = ObjectAnimator
            .ofFloat(this, View.SCALE_Y, 1f, 1.5f)
            .setDuration((duration / 2).toLong())

        val collapseX = ObjectAnimator
            .ofFloat(this, View.SCALE_X, 1.5f, 1f)
            .setDuration((duration / 2).toLong())

        val collapseY = ObjectAnimator
            .ofFloat(this, View.SCALE_Y, 1.5f, 1f)
            .setDuration((duration / 2).toLong())

        val grow = AnimatorSet().apply { playTogether(growX, growY) }
        val collapse = AnimatorSet().apply { playTogether(collapseX, collapseY) }

        val resultAnimator = AnimatorSet().apply { playSequentially(grow, collapse) }
        resultAnimator.start()
    }
}
