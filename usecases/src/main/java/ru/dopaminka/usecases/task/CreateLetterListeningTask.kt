package ru.dopaminka.usecases.task

import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.LetterListeningTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class CreateLetterListeningTask(private val taskRepository: Repository<Task>) :
    UseCase<CreateLetterListeningTask.Params, Identity>() {

    override fun execute(params: Params): Identity {
        val id = Identity.generate()
        val task = LetterListeningTask(id, params.letter)
        taskRepository.save(task)
        return id
    }

    class Params(val letter: String)
}