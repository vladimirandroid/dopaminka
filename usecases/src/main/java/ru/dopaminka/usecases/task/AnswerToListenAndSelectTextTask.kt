package ru.dopaminka.usecases.task

import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.reading.Readable
import ru.dopaminka.entity.readingProgram.ListenAndSelectTextTask
import ru.dopaminka.usecases.ProgressProvider
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class AnswerToListenAndSelectTextTask(
    private val progressProvider: ProgressProvider
) : UseCase<AnswerToListenAndSelectTextTask.Params, Boolean>() {

    override fun execute(params: Params): Boolean {
        if (params.task !is ListenAndSelectTextTask) throw Exception("Wrong task type")

        if (params.selectedText == params.task.rightText) {
            progressProvider.get().complete(params.lesson, params.task)
            return true
        }
        return false
    }

    class Params(val lesson: Lesson, val task: Task, val selectedText: Readable)
}