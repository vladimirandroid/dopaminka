package ru.dopaminka.common

import ru.dopaminka.entity.reading.*

class ReadableToAtomicsConverter {
    fun convert(readable: Readable) = getAtomicTexts(readable)

    private fun getAtomicTexts(readable: Readable): List<AtomicText> {
        return when (readable) {
            is AtomicText -> listOf(readable)
            is Word -> getAtomicTexts(readable)
            is Text -> getAtomicTexts(readable)
            is Unpronounceable -> emptyList()
        }
    }

    private fun getAtomicTexts(text: Text) = text.readables.map { getAtomicTexts(it) }.flatten()

    private fun getAtomicTexts(word: Word) = word.syllables
}