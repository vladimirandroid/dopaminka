package ru.dopaminka.usecases.task

import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.LetterListeningTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

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

    open class TaskView(val id: Identity, val illustrations: List<IllustrationView>) {
        override fun toString(): String {
            return "TaskView(id=$id, illustrations=$illustrations)"
        }
    }

    class LetterListeningTaskView(
        id: Identity,
        illustrations: List<IllustrationView>,
        val letter: String
    ) : TaskView(id, illustrations)

    data class IllustrationView(val imageFileName: String, val soundFileName: String)
}