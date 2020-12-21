package ru.dopaminka.usecases.task

import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task
import ru.dopaminka.usecases.ProgressProvider
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class CompleteListenTask(private val progressProvider: ProgressProvider) :
    UseCase<CompleteListenTask.Params, Unit>() {

    override fun execute(params: Params) {
        progressProvider.get().complete(params.lesson, params.task)
    }

    data class Params(val lesson: Lesson, val task: Task)
}