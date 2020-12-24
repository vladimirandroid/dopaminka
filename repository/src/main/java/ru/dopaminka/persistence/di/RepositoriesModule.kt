package ru.dopaminka.persistence.di

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import org.koin.dsl.module
import ru.dopaminka.persistence.ProgramProviderImpl
import ru.dopaminka.persistence.ProgressProviderImpl
import ru.dopaminka.persistence.RawFileTextProvider
import ru.dopaminka.usecases.ProgramProvider
import ru.dopaminka.usecases.ProgressProvider

val repositoriesModule = module {
    single<ProgramProvider> { ProgramProviderImpl(get()) }
    single<ProgressProvider> { ProgressProviderImpl(get<ProgramProvider>().get()) }
    single<RawFileTextProvider> {
        object : RawFileTextProvider {
            override fun get(id: Int) = get<Context>().resources.getRawTextFile(id)
        }
    }
}

fun Resources.getRawTextFile(@RawRes id: Int) =
    openRawResource(id).bufferedReader().use { it.readText() }