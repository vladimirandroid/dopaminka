package ru.dopaminka.usecases.task

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class GetUnassignedTasks(
    private val taskRepository: Repository<Task>,
    private val lessonRepository: Repository<Lesson>
) :
    UseCase<Unit, List<GetUnassignedTasks.TaskView>>() {

    override fun execute(params: Unit): List<TaskView> {
        val lessons = lessonRepository.all()
        val assignedTasksIds = mutableSetOf<Identity>()
        lessons.forEach { assignedTasksIds.addAll(it.tasksIds) }

        val allTasks = taskRepository.all()
        val unassignedTasks = allTasks.filter { !assignedTasksIds.contains(it.id) }

        return unassignedTasks.map {
            TaskView(
                it.id,
                it.illustrations.map { IllustrationView(it.imageFileName, it.soundFileName) })
        }
    }

    open class TaskView(val id: Identity, val illustrations: List<IllustrationView>)

    data class IllustrationView(val imageFileName: String, val soundFileName: String)

}