package ru.dopaminka.usecases.di

import org.koin.dsl.module
import ru.dopaminka.usecases.task.AnswerToListenAndSelectTextTask
import ru.dopaminka.usecases.task.CompleteListenTask

val useCasesModule = module {

    //lesson progress:
    factory {
        CompleteListenTask(
            get(),
        )
    }
    factory {
        AnswerToListenAndSelectTextTask(get())
    }
}