package ru.dopaminka.usecases.di

import org.koin.dsl.module
import ru.dopaminka.usecases.CompleteTask

val useCasesModule = module {

    //lesson progress:
    factory { CompleteTask(get()) }
}