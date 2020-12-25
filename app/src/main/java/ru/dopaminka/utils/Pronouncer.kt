package ru.dopaminka.utils

import android.content.res.AssetManager
import android.media.MediaPlayer
import ru.dopaminka.entity.reading.*

class Pronouncer(private val assets: AssetManager, private val player: MediaPlayer) :
    MediaPlayer.OnCompletionListener {

    private lateinit var atomicTexts: List<AtomicText>
    private var currentIndex: Int = 0

    init {
        player.setOnCompletionListener(this)
    }

    fun pronounce(readable: Readable) {
        atomicTexts = getAtomicTexts(readable)
        if (atomicTexts.isEmpty()) return

        currentIndex = 0
        playAtomicText(atomicTexts[currentIndex])
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (currentIndex + 1 == atomicTexts.size) return

        currentIndex++
        playAtomicText(atomicTexts[currentIndex])
    }

    private fun playAtomicText(atomicText: AtomicText) {
        val file = assets.openFd(atomicText.sound)
        player.reset()
        player.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        player.prepare()
        file.close()
        player.start()
    }

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

    fun release() {
        player.release()
    }
}