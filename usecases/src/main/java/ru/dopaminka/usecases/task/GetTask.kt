package ru.dopaminka.usecases.task

import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.ListenAndSelectTask
import ru.dopaminka.entity.tasks.ListenTask
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
        if (task is ListenAndSelectTask) {
            return ListenAndSelectTaskView(
                task.id,
                task.image,
                task.sound,
                task.wrongImage
            )
        } else if (task is ListenTask) {
            return ListenTaskView(
                task.id,
                task.image,
                task.sound
            )
        }
        return TaskView(task.id)
    }

    open class TaskView(val id: Identity)

    open class ListenTaskView(
        id: Identity,
        val image: String,
        val sound: String,
    ) : TaskView(id) {
        override fun toString() = "ListenTaskView(id: $id, image: $image, sound: $sound)"
    }

    class ListenAndSelectTaskView(
        id: Identity,
        image: String,
        sound: String,
        val wrongImage: String
    ) : ListenTaskView(id, image, sound) {
        override fun toString() =
            "ListenTaskView(id: $id, image: $image, sound: $sound, wrongImage: $wrongImage)"
    }
}