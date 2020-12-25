package ru.dopaminka.di

import android.media.MediaPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.dopaminka.entity.reading.Readable
import ru.dopaminka.common.pronunciationFragment.ReadablePronunciationViewModel
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.common.ReadableToAtomicsConverter

val uiModule = module {
    factory {
        Pronouncer(androidContext().assets, MediaPlayer(), get())
    }
    single {
        ReadableToAtomicsConverter()
    }
    viewModel { (readable: Readable) ->
        ReadablePronunciationViewModel(readable, get(), get())
    }
}