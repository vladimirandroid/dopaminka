package ru.dopaminka.persistence.di

import org.koin.dsl.module
import ru.dopaminka.persistence.ProgramProviderImpl
import ru.dopaminka.persistence.ProgressProviderImpl
import ru.dopaminka.usecases.ProgramProvider
import ru.dopaminka.usecases.ProgressProvider

val repositoriesModule = module {
    single<ProgramProvider> { ProgramProviderImpl(get()) }
    single<ProgressProvider> { ProgressProviderImpl(get<ProgramProvider>().get()) }
}