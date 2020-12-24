package ru.dopaminka.readable

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import ru.dopaminka.entity.reading.*

class ReadableView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
    }

    fun highlight(progress: Float) {
        //TODO
    }

    fun setReadable(readable: Readable) {
        val characterViews = getCharacterViewsForReadable(readable)
        characterViews.forEach {
            addView(it)
        }
    }

    private fun getCharacterViewsForReadable(readable: Readable): List<CharacterView> {
        return when (readable) {
            is Letter -> listOf(CharacterView(context).apply { text = readable.text })
            is Syllable -> getCharacterViewsForSyllable(readable)
            is Word -> getCharacterViewsForWord(readable)
            is Text -> getCharacterViewsForText(readable)
            is Unpronounceable -> listOf(CharacterView(context).apply { text = readable.text })
            is AtomicText -> emptyList() //can't be
        }
    }

    private fun getCharacterViewsForText(text: Text): List<CharacterView> {
        return text.readables.map { getCharacterViewsForReadable(it) }.flatten()
    }

    private fun getCharacterViewsForSyllable(syllable: Syllable): MutableList<CharacterView> {
        return syllable.letters.map {
            CharacterView(context).apply { text = it.text }
        }.toMutableList()
    }

    private fun getCharacterViewsForWord(word: Word): MutableList<CharacterView> {
        return word.syllables.map {
            val list = getCharacterViewsForSyllable(it)
            if (it != word.syllables.last()) {
                list.add(CharacterView(context).apply { text = "-" })
            }
            list
        }.flatten().toMutableList()
    }
}