package ru.dopaminka.entity.readingProgram

import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.Readable

data class DrawTextTask(val text: Readable) : Task