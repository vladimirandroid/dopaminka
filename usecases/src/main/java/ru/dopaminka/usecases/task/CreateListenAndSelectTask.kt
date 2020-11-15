package ru.dopaminka.usecases.task

import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.ListenAndSelectTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class CreateListenAndSelectTask(private val taskRepository: Repository<Task>) :
    UseCase<CreateListenAndSelectTask.Params, Identity>() {

    override fun execute(params: Params): Identity {
        val id = Identity.generate()
        val task = ListenAndSelectTask(id, params.image, params.sound, params.wrongImage)
        taskRepository.save(task)
        return id
    }

    class Params(val image: String, val sound: String, val wrongImage: String)
}