package ru.dopaminka.di

import android.media.MediaPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.dopaminka.common.AssetAudioPlayer
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.common.ReadableToAtomicsConverter

val uiModule = module {
    factory { AssetAudioPlayer(androidContext().assets, MediaPlayer()) }
    factory { Pronouncer(get(), get()) }
    single { ReadableToAtomicsConverter() }
}