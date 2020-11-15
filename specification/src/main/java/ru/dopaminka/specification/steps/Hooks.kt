package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dopaminka.persistence.di.repositoriesModule
import ru.dopaminka.specification.State
import ru.dopaminka.usecases.di.useCasesModule

val testModule = module {
    single {
        State(
            get(named("lessonProgress")),
            get(named("program")),
            get(named("lesson")),
            get(named("task"))
        )
    }
}

class Hooks : En {
    init {
        Before(HookNoArgsBody {
            stopKoin()
            startKoin {
                modules(repositoriesModule, useCasesModule, testModule)
            }
        })
    }
}

