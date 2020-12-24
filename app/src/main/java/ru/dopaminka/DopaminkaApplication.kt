package ru.dopaminka

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import ru.dopaminka.persistence.di.repositoriesModule
import ru.dopaminka.readable.di.readablePronunciationModule
import ru.dopaminka.usecases.di.useCasesModule

class DopaminkaApplication : Application() {

    @KoinApiExtension
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Koin Android logger
            androidLogger()
            //inject Android context
            androidContext(this@DopaminkaApplication)
            // use modules
            modules(repositoriesModule, useCasesModule, readablePronunciationModule)
        }

        Log.d("asd", "initialize app")
    }
}