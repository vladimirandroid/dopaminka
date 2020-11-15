package ru.dopaminka.usecases.task

import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.ListenTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class CreateListenTask(private val taskRepository: Repository<Task>) :
    UseCase<CreateListenTask.Params, Identity>() {

    override fun execute(params: Params): Identity {
        val id = Identity.generate()
        val task = ListenTask(id, params.image, params.sound)
        taskRepository.save(task)
        return id
    }

    class Params(val image: String, val sound: String)
}