package ru.dopaminka.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dopaminka.usecases.alphabet.*
import ru.dopaminka.usecases.lesson.*
import ru.dopaminka.usecases.lessonProgress.CompleteTask
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetProgram
import ru.dopaminka.usecases.program.SetProgramLessons
import ru.dopaminka.usecases.task.*

val useCasesModule = module {
    //alphabet:
    factory { CreateAlphabet(get(qualifier = named("alphabet"))) }
    factory { AddLetter(get(qualifier = named("alphabet"))) }
    factory { GetAlphabet(get(qualifier = named("alphabet"))) }
    factory { RemoveLetter(get(qualifier = named("alphabet"))) }

    //lesson:
    factory { AddTask(get(qualifier = named("lesson")), get(qualifier = named("task"))) }
    factory { CreateLesson(get(qualifier = named("lesson"))) }
    factory { DeleteLesson(get(qualifier = named("lesson"))) }
    factory {
        GetLesson(
            get(qualifier = named("lesson")),
            get(qualifier = named("task")),
            get(qualifier = named("lessonProgress"))
        )
    }
    factory { RemoveTask(get(qualifier = named("lesson")), get(qualifier = named("task"))) }
    factory {
        GetUnassignedLessons(
            get(qualifier = named("program")),
            get(qualifier = named("lesson"))
        )
    }

    //lesson progress:
    factory {
        CompleteTask(
            get(qualifier = named("lesson")),
            get(qualifier = named("task")),
            get(qualifier = named("lessonProgress"))
        )
    }

    //program:
    factory { CreateProgram(get(qualifier = named("program"))) }
    factory { GetProgram(get(qualifier = named("program")), get(qualifier = named("lesson"))) }
    factory {
        SetProgramLessons(
            get(qualifier = named("program")),
            get(qualifier = named("lesson"))
        )
    }

    //task:
    factory { AddIllustration(get(qualifier = named("task"))) }
    factory { CreateLetterListeningTask(get(qualifier = named("task"))) }
    factory { GetTask(get(qualifier = named("task"))) }
    factory { RemoveIllustration(get(qualifier = named("task"))) }
    factory { GetUnassignedTasks(get(qualifier = named("task")), get(qualifier = named("lesson"))) }
}