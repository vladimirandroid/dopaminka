package ru.dopaminka.usecases.lessonProgress

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.Repository

/**
 * input = lesson title
 */
class CompleteTask(
    private val lessonRepository: Repository<Lesson>,
    private val taskRepository: Repository<Task>,
    private val lessonProgressRepository: Repository<LessonProgress>
) :
    UseCase<CompleteTask.Params, Unit>() {

    override fun execute(params: Params) {
        lessonRepository.get(params.lessonId) ?: throw Exception("Lesson not found")
        val task = taskRepository.get(params.taskId) ?: throw Exception("Task not found")
        val lessonProgress =
            lessonProgressRepository.get(params.lessonId) ?: LessonProgress(params.lessonId)

        if (lessonProgress.isTaskCompleted(task)) return

        lessonProgress.completeTask(task)
        lessonProgressRepository.save(lessonProgress)
    }

    class Params(val lessonId: Identity, val taskId: Identity)
}