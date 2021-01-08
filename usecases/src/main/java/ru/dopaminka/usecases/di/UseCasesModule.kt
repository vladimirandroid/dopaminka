package ru.dopaminka.usecases.di

import org.koin.dsl.module
import ru.dopaminka.usecases.CompleteStoryFragment

val useCasesModule = module {

    //lesson progress:
    factory { CompleteStoryFragment(get()) }
}