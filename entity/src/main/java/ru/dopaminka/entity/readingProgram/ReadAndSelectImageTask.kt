package ru.dopaminka.entity.readingProgram

import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.Readable

data class ReadAndSelectImageTask(
    val text: Readable,
    val rightImage: String,
    val wrongImage: String
) : Task