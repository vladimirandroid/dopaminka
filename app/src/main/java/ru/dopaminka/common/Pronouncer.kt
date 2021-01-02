package ru.dopaminka.common

import ru.dopaminka.entity.reading.AtomicText
import ru.dopaminka.entity.reading.Readable

class Pronouncer(
    private val player: AssetAudioPlayer,
    private val readableToAtomicsConverter: ReadableToAtomicsConverter
) {

    private lateinit var atomicTexts: List<AtomicText>
    private var currentIndex: Int = 0
    var listener: Listener? = null

    init {
        player.completeListener = this::onComplete
        player.startListener = this::onStart
    }

    fun pronounce(readable: Readable) {
        atomicTexts = readableToAtomicsConverter.convert(readable)
        if (atomicTexts.isEmpty()) return

        currentIndex = 0
        playAtomicText(atomicTexts[currentIndex])
    }

    private fun onStart(duration: Int) {
        listener?.onStart(duration, currentIndex)
    }

    private fun onComplete() {
        if (currentIndex + 1 == atomicTexts.size) return

        currentIndex++
        playAtomicText(atomicTexts[currentIndex])
    }

    private fun playAtomicText(atomicText: AtomicText) {
        player.play(atomicText.sound)
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

