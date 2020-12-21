package ru.dopaminka.specification.di

import org.koin.dsl.module
import ru.dopaminka.persistence.RawFileTextProvider
import ru.dopaminka.specification.TestProgramProvider
import ru.dopaminka.specification.steps.SpecificationRawFileTextProvider
import ru.dopaminka.usecases.ProgramProvider

val testModule = module {
    single<ProgramProvider>(override = true) {
        TestProgramProvider()
    }
    single<RawFileTextProvider> {
        SpecificationRawFileTextProvider()
    }
}