package ru.dopaminka.entity.readingProgram

import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.Letter
import ru.dopaminka.entity.reading.Readable

data class InsertMissingLetterTask(
    val text: Readable,
    val wrongLetters: List<Letter>,
    val missingLetterIndex: Int
) : Task