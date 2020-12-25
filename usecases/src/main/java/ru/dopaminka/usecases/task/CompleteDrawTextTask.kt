package ru.dopaminka.usecases.task

import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.DrawTextTask
import ru.dopaminka.usecases.ProgressProvider
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class CompleteDrawTextTask(private val progressProvider: ProgressProvider) :
    UseCase<CompleteDrawTextTask.Params, Unit>() {

    override fun execute(params: Params) {
        progressProvider.get().complete(params.lesson, params.task)
    }

    data class Params(val lesson: Lesson, val task: DrawTextTask)
}