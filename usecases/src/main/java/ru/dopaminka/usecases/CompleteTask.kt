package ru.dopaminka.usecases

import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task

/**
 * input = lesson title
 */
class CompleteTask(private val progressProvider: ProgressProvider) :
    UseCase<CompleteTask.Params, Unit>() {

    override fun execute(params: Params) {
        progressProvider.get().complete(params.lesson, params.task)
    }

    data class Params(val lesson: Lesson, val task: Task)
}