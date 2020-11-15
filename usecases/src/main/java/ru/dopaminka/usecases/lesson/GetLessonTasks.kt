package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.ListenTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

/**
 * input = lesson title
 */
class GetLessonTasks(
    private val lessonRepository: Repository<Lesson>,
    private val taskRepository: Repository<Task>,
    private val lessonProgressRepository: Repository<LessonProgress>
) :
    UseCase<Identity, GetLessonTasks.LessonView>() {

    override fun execute(params: Identity): LessonView {
        val lesson = lessonRepository.get(params) ?: throw Exception("Lesson not found")
        val lessonProgress = lessonProgressRepository.get(params) ?: LessonProgress(params)

        val taskIds = lesson.tasksIds
        val tasks = taskRepository.get(taskIds)
        val tasksViews = tasks.map {
            if (it is ListenTask)
                ListenTaskView(
                    it.id,
                    it.image,
                    it.sound,
                    lessonProgress.isTaskCompleted(it)
                )
            else
                TaskView(
                    it.id,
                    lessonProgress.isTaskCompleted(it)
                )
        }

        return LessonView(
            lesson.id,
            lesson.title,
            tasksViews,
            1.0 * lessonProgress.completedTasksCount / lesson.tasksIds.size
        )
    }

    data class LessonView(
        val id: Identity,
        val title: String,
        val tasks: List<TaskView>,
        val progress: Double
    )

    open class TaskView(
        val id: Identity,
        val isCompleted: Boolean
    ) {
        override fun toString(): String {
            return "TaskView(id=$id, isCompleted: $isCompleted)"
        }
    }

    class ListenTaskView(
        id: Identity,
        val image: String,
        val sound: String,
        isCompleted: Boolean,
    ) : TaskView(id, isCompleted) {
        override fun toString(): String {
            return "LetterListeningTaskView(id=$id, image=$image, sound: $sound, isCompleted: $isCompleted)"
        }
    }
}


