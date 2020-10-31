package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class AddTask(
    private val lessonRepository: Repository<Lesson>,
    private val taskRepository: Repository<Task>
) :
    UseCase<AddTask.Params, Unit>() {

    override fun execute(params: Params) {
        val lesson = lessonRepository.get(params.lessonId) ?: throw Exception("Lesson not found")
        val task = taskRepository.get(params.taskId) ?: throw Exception("Task not found")
        lesson.addTask(task)
        lessonRepository.save(lesson)
    }

    class Params(val lessonId: Identity, val taskId: Identity)
}