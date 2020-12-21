package ru.dopaminka.persistence

import ru.dopaminka.entity.program.Program
import ru.dopaminka.entity.program.Progress
import ru.dopaminka.usecases.ProgressProvider

class ProgressProviderImpl(program: Program) : ProgressProvider {

    private val progress = Progress(
        program.lessons.first(),
        program.lessons.first().tasks.first(),
        false,
        program
    )

    override fun get() = progress
}