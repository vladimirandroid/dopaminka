package ru.dopaminka.usecases.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dopaminka.usecases.lesson.*
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetLessons
import ru.dopaminka.usecases.program.SetLessons
import ru.dopaminka.usecases.task.*

val useCasesModule = module {

    //lesson:
    factory { AddTask(get(qualifier = named("lesson")), get(qualifier = named("task"))) }
    factory { CreateLesson(get(qualifier = named("lesson"))) }
    factory { DeleteLesson(get(qualifier = named("lesson"))) }
    factory {
        GetLessonTasks(
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
        CompleteListenTask(
            get(qualifier = named("lesson")),
            get(qualifier = named("task")),
            get(qualifier = named("lessonProgress"))
        )
    }
    factory {
        AnswerToListenAndSelectTask(
            get(qualifier = named("lesson")),
            get(qualifier = named("task")),
            get(qualifier = named("lessonProgress"))
        )
    }

    //program:
    factory { CreateProgram(get(qualifier = named("program"))) }
    factory { GetLessons(get(qualifier = named("program")), get(qualifier = named("lesson"))) }
    factory {
        SetLessons(
            get(qualifier = named("program")),
            get(qualifier = named("lesson"))
        )
    }

    //task:
    factory { CreateListenTask(get(qualifier = named("task"))) }
    factory { CreateListenAndSelectTask(get(qualifier = named("task"))) }
    factory { GetTask(get(qualifier = named("task"))) }
    factory { GetUnassignedTasks(get(qualifier = named("task")), get(qualifier = named("lesson"))) }
}