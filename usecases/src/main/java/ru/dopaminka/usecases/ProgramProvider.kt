package ru.dopaminka.usecases

import ru.dopaminka.entity.program.Program

interface ProgramProvider {
    fun get(): Program
}