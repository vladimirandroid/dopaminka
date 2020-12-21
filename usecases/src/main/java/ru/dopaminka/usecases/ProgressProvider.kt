package ru.dopaminka.usecases

import ru.dopaminka.entity.program.Progress

interface ProgressProvider {
    fun get(): Progress
}