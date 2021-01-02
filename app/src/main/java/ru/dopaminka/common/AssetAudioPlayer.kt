package ru.dopaminka.common

import android.content.res.AssetManager
import android.media.MediaPlayer

class AssetAudioPlayer(
    private val assets: AssetManager,
    private val player: MediaPlayer
) : MediaPlayer.OnCompletionListener {

    var completeListener: (() -> Unit)? = null
    var startListener: ((duration: Int) -> Unit)? = null

    init {
        player.setOnCompletionListener(this)
    }

    fun play(fileName: String) {
        val file = assets.openFd(fileName)
        player.reset()
        player.setDataSource(file.fileDescriptor, file.startOffset, file.length)
        player.prepare()
        startListener?.invoke(player.duration)
        file.close()
        player.start()
    }

    fun release() {
        player.release()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        completeListener?.invoke()
    }
}