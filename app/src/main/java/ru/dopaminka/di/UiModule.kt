package ru.dopaminka.di

import android.media.MediaPlayer
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.dopaminka.common.AssetAudioPlayer
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.common.ReadableToAtomicsConverter
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.lesson.LessonViewModel

val uiModule = module {
    factory { AssetAudioPlayer(androidContext().assets, MediaPlayer()) }
    factory { Pronouncer(get(), get()) }
    single { ReadableToAtomicsConverter() }
    viewModel { (lesson: Lesson) ->
        LessonViewModel(lesson, androidApplication())
    }
}