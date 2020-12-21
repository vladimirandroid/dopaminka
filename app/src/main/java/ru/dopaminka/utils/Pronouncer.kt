package ru.dopaminka.utils

import android.content.res.AssetManager
import android.media.MediaPlayer
import ru.dopaminka.entity.reading.Letter
import ru.dopaminka.entity.reading.Readable

class Pronouncer(private val assets: AssetManager, private val player: MediaPlayer) {

    fun pronounce(readable: Readable) {
        if (readable is Letter) {
            val file = assets.openFd(readable.sound)
            player.reset()

            player.setDataSource(file.fileDescriptor, file.startOffset, file.length)
            file.close()
            player.prepare()
            player.start()
        }
    }

    fun release(){
        player.release()
    }
}