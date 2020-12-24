package ru.dopaminka.readable

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ru.dopaminka.entity.reading.Readable
import ru.dopaminka.utils.Pronouncer

class ReadablePronunciationViewModel(
    private val readable: Readable,
    application: Application
) : AndroidViewModel(application) {
    private val pronouncer: Pronouncer by lazy { Pronouncer(application.assets, MediaPlayer()) }

    init {
        Log.d("PODALOG", "create")
        pronouncer.pronounce(readable)

    }

    fun onPronounce() {
        pronouncer.pronounce(readable)
    }

    fun onStart() {}
}