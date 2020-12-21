package ru.dopaminka.entity.readingProgram

import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.Readable

data class ListenAndSelectTextTask(val rightText: Readable, val wrongText: Readable) : Task