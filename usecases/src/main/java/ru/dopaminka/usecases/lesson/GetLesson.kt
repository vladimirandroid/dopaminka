package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.UseCase
import ru.dopaminka.usecases.repository.Repository

/**
 * input = lesson title
 */
class GetLesson(
    private val lessonRepository: Repository<Lesson>,
    private val taskRepository: Repository<Task>,
    private val lessonProgressRepository: Repository<LessonProgress>
) :
    UseCase<Identity, GetLesson.LessonView>() {

    override fun execute(params: Identity): LessonView {
        val lesson = lessonRepository.get(params) ?: throw Exception("Lesson not found")
        val lessonProgress = lessonProgressRepository.get(params) ?: LessonProgress(params)

        val taskIds = lesson.tasksIds
        val tasks = taskRepository.get(taskIds)
        val tasksViews = tasks.map { TaskView(it.id, lessonProgress.isTaskCompleted(it)) }

        return LessonView(
            lesson.id,
            lesson.title,
            tasksViews,
            1.0 * lessonProgress.completedTasksCount / lesson.tasksIds.size
        )
    }

    class LessonView(
        val id: Identity,
        val title: String,
        val tasks: List<TaskView>,
        val progress: Double
    )

    class TaskView(val id: Identity, val isCompleted: Boolean)
}