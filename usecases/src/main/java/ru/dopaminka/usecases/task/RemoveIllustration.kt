package ru.dopaminka.usecases.task

import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class RemoveIllustration(private val taskRepository: Repository<Task>) :
    UseCase<RemoveIllustration.Params, Unit>() {

    override fun execute(params: Params) {
        val task = taskRepository.get(params.taskId) ?: throw Exception("Task not found")
        task.removeIllustration(Task.Illustration(params.imageFileName, params.soundFileName))
        taskRepository.save(task)
    }

    class Params(val taskId: Identity, val imageFileName: String, val soundFileName: String)
}