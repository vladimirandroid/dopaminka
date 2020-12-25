package ru.dopaminka.common.pronunciationFragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.dopaminka.common.AtomicTextPronouncingListener
import ru.dopaminka.common.ConsumableEvent
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.entity.reading.Readable

class ReadablePronunciationViewModel(
    private val readable: Readable,
    private val pronouncer: Pronouncer,
    application: Application,
) : AndroidViewModel(application), AtomicTextPronouncingListener {

    val pronunciationEvent: LiveData<ConsumableEvent<PronunciationEvent>> = MutableLiveData()

    init {
        pronouncer.listener = this
        pronouncer.pronounce(readable)
    }

    fun onPronounce() {
        pronouncer.pronounce(readable)
    }

    override fun onStart(duration: Int, atomicPosition: Int) {
        Log.d(
            "DOPALOG",
            "ReadablePronunciationViewModel onStart duration=$duration, atomicPosition=$atomicPosition"
        )
        val liveData = (pronunciationEvent as MutableLiveData)
        liveData.postValue(
            ConsumableEvent(PronunciationEvent(duration, atomicPosition))
        )
    }

    override fun onCleared() {
        super.onCleared()
        pronouncer.release()
    }
}

class PronunciationEvent(val duration: Int, val atomicPosition: Int)