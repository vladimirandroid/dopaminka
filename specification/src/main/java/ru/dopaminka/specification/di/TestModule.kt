package ru.dopaminka.specification.di

import org.koin.dsl.module
import ru.dopaminka.persistence.RawFileTextProvider
import ru.dopaminka.specification.SpecificationProgramProvider
import ru.dopaminka.specification.steps.SpecificationRawFileTextProviderImpl
import ru.dopaminka.usecases.ProgramProvider

val testModule = module {
    single<ProgramProvider>(override = true) { SpecificationProgramProvider() }
    single<RawFileTextProvider>(override = true) { SpecificationRawFileTextProviderImpl() }
}