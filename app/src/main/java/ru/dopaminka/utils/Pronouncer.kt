package ru.dopaminka.utils

import android.content.res.AssetManager
import android.media.MediaPlayer
import ru.dopaminka.entity.reading.*

class Pronouncer(private val assets: AssetManager, private val player: MediaPlayer) :
    MediaPlayer.OnCompletionListener {

    private lateinit var atomicTexts: List<AtomicText>
    private lateinit var currentAtomicText: AtomicText

    init {
        player.setOnCompletionListener(this)
    }

    fun pronounce(readable: Readable) {
        atomicTexts = getAtomicTexts(readable)
        currentAtomicText = atomicTexts.first()
        playAtomicText(currentAtomicText)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (currentAtomicText != atomicTexts.last()) {
            val index = atomicTexts.indexOf(currentAtomicText) + 1
            currentAtomicText = atomicTexts[index]
            playAtomicText(currentAtomicText)
        }
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