package ru.dopaminka.specification.steps

import com.example.specification.R
import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ru.dopaminka.persistence.RawFileTextProvider
import ru.dopaminka.persistence.di.repositoriesModule
import ru.dopaminka.specification.di.testModule
import ru.dopaminka.usecases.di.useCasesModule
import java.io.File

class SpecificationRawFileTextProvider : RawFileTextProvider {

    override fun get(id: Int): String {
        val projectDir = File("").absoluteFile.parentFile
        val file: File = if (id == R.raw.program) {
            File(projectDir, "repository/src/main/res/raw/program.json")
        } else if (id == R.raw.alphabet) {
            File(projectDir, "repository/src/main/res/raw/alphabet.json")
        } else {
            throw IllegalArgumentException("Unknown raw file id")
        }
        return file.readText()
    }

}

class Hooks : En {
    init {
        Before(HookNoArgsBody {
            stopKoin()
            startKoin {
                modules(repositoriesModule, useCasesModule, testModule)
            }
        })
    }
}

