package ru.dopaminka.usecases.task

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.ListenAndSelectTask
import ru.dopaminka.entity.tasks.ListenTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class CompleteListenTask(
    private val lessonRepository: Repository<Lesson>,
    private val taskRepository: Repository<Task>,
    private val lessonProgressRepository: Repository<LessonProgress>
) :
    UseCase<CompleteListenTask.Params, Unit>() {

    override fun execute(params: Params) {
        lessonRepository.get(params.lessonId) ?: throw Exception("Lesson not found")
        val task = taskRepository.get(params.taskId) ?: throw Exception("Task not found")
        if (task !is ListenTask || task is ListenAndSelectTask) throw Exception("Wrong task type")

        val lessonProgress =
            lessonProgressRepository.get(params.lessonId) ?: LessonProgress(params.lessonId)

        if (lessonProgress.isTaskCompleted(task)) return

        lessonProgress.completeTask(task)
        lessonProgressRepository.save(lessonProgress)
    }

    class Params(val lessonId: Identity, val taskId: Identity)
}