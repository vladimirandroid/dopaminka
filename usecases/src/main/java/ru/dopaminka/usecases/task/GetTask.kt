package ru.dopaminka.usecases.task

import ru.dopaminka.entity.Alphabet
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.LetterListeningTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class GetTask(private val taskRepository: Repository<Task>) :
    UseCase<Identity, GetTask.TaskView>() {

    override fun execute(params: Identity): TaskView {
        val task = taskRepository.get(params) ?: throw Exception("Task not found")
        if (task is LetterListeningTask) {
            return LetterListeningTaskView(
                task.id,
                task.illustrations.map { IllustrationView(it.imageFileName, it.soundFileName) },
                task.letter
            )
        }
        return TaskView(
            task.id,
            task.illustrations.map { IllustrationView(it.imageFileName, it.soundFileName) })
    }

    open class TaskView(val id: Identity, val illustrations: List<IllustrationView>)

    class LetterListeningTaskView(
        id: Identity,
        illustrations: List<IllustrationView>,
        val letter: Alphabet.Letter
    ) : TaskView(id, illustrations)

    data class IllustrationView(val imageFileName: String, val soundFileName: String)
}