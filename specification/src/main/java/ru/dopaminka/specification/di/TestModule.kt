package ru.dopaminka.specification.di

import org.koin.dsl.module
import ru.dopaminka.persistence.RawFileTextProvider
import ru.dopaminka.specification.SpecificationStoryProvider
import ru.dopaminka.specification.steps.SpecificationRawFileTextProviderImpl
import ru.dopaminka.usecases.StoryProvider

val testModule = module {
    single<StoryProvider>(override = true) { SpecificationStoryProvider() }
    single<RawFileTextProvider>(override = true) { SpecificationRawFileTextProviderImpl() }
}