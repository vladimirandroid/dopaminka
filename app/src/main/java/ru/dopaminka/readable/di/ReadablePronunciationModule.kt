package ru.dopaminka.readable.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.dopaminka.entity.reading.Readable
import ru.dopaminka.readable.ReadablePronunciationViewModel

val readablePronunciationModule = module {

    viewModel { (readable: Readable) -> ReadablePronunciationViewModel(readable, get()) }
}