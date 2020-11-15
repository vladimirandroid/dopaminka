package ru.dopaminka.persistence.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.Program
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.persistence.InMemoryRepositoryImpl
import ru.dopaminka.usecases.Repository

val repositoriesModule = module {
    single<Repository<LessonProgress>>(named("lessonProgress")) { InMemoryRepositoryImpl() }
    single<Repository<Program>>(named("program")) { InMemoryRepositoryImpl() }
    single<Repository<Lesson>>(named("lesson")) { InMemoryRepositoryImpl() }
    single<Repository<Task>>(named("task")) { InMemoryRepositoryImpl() }
}