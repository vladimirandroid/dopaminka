package ru.dopaminka.persistence.di

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import org.koin.dsl.module
import ru.dopaminka.persistence.*
import ru.dopaminka.usecases.*

val repositoriesModule = module {
    single<ProgramProvider> { ProgramProviderImpl(get()) }
    single<StoryProvider> { StoryProviderImpl(get<ProgramProvider>().get(), get()) }
    single<StoryProgressProvider> { get<StoryProgressRepository>() }
    single<StoryProgressRepository> { StoryProgressRepositoryImpl(get<StoryProvider>().get()) }
    single<RawFileTextProvider> {
        object : RawFileTextProvider {
            override fun get(id: Int) = get<Context>().resources.getRawTextFile(id)
        }
    }
}

fun Resources.getRawTextFile(@RawRes id: Int) =
    openRawResource(id).bufferedReader().use { it.readText() }