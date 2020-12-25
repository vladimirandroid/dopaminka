package ru.dopaminka.common.readable

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.core.view.children
import ru.dopaminka.entity.reading.*

class ReadableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun highlight(atomicPosition: Int, duration: Int) {
        val atomics = children.filterIsInstance<AtomicTextView>().toList()
        atomics[atomicPosition].highlight(duration)
    }

    fun setReadable(readable: Readable) {
        val characterViews = getCharacterViewsForReadable(readable)
        characterViews.forEach {
            addView(it)
        }
    }

    private fun getCharacterViewsForReadable(readable: Readable): List<CharacterView> {
        return when (readable) {
            is Word -> getViews(readable)
            is Text -> getViews(readable)
            is Unpronounceable -> listOf(CharacterView(context).apply { text = readable.text })
            is AtomicText -> listOf(AtomicTextView(context).apply { atomicText = readable })
        }
    }

    private fun getViews(text: Text): List<CharacterView> {
        return text.readables.map { getCharacterViewsForReadable(it) }.flatten()
    }

    private fun getViews(word: Word): MutableList<CharacterView> {
        return word.syllables.map {
            val list: MutableList<CharacterView> =
                mutableListOf(AtomicTextView(context).apply { atomicText = it })
            if (it != word.syllables.last()) {
                list.add(CharacterView(context).apply { text = "-" })
            }
            list
        }.flatten().toMutableList()
    }
}