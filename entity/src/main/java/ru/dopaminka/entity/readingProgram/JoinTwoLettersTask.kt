package ru.dopaminka.entity.readingProgram

import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.Letter
import ru.dopaminka.entity.reading.Readable


data class JoinTwoLettersTask(
    val letter1: Letter,
    val letter2: Letter,
    val result: Readable
) : Task