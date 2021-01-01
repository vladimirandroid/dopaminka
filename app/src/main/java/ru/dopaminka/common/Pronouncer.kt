package ru.dopaminka.common

import android.content.res.AssetManager
import android.media.MediaPlayer
import ru.dopaminka.entity.reading.AtomicText
import ru.dopaminka.entity.reading.Readable

class Pronouncer(
    private val assets: AssetManager,
    private val player: MediaPlayer,
    private val readableToAtomicsConverter: ReadableToAtomicsConverter
) :
    MediaPlayer.OnCompletionListener {

    private lateinit var atomicTexts: List<AtomicText>
    private var currentIndex: Int = 0
    var listener: Listener? = null

    init {
        player.setOnCompletionListener(this)
    }

    fun pronounce(readable: Readable) {
        atomicTexts = readableToAtomicsConverter.convert(readable)
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
        listener?.onStart(player.duration, currentIndex)
        file.close()
        player.start()
    }


    fun release() {
        player.release()
    }

    abstract class Listener {
        open fun onStart(duration: Int, atomicPosition: Int) {

        }

        open fun onFinish() {

        }
    }
}

