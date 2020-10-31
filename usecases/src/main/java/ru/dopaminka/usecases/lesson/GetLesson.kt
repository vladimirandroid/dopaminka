package ru.dopaminka.usecases.lesson

import ru.dopaminka.entity.Lesson
import ru.dopaminka.entity.LessonProgress
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.entity.tasks.LetterListeningTask
import ru.dopaminka.entity.tasks.Task
import ru.dopaminka.usecases.Repository
import ru.dopaminka.usecases.UseCase

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
        val tasksViews = tasks.map {
            if (it is LetterListeningTask)
                LetterListeningTaskView(
                    it.id,
                    it.illustrations.map {
                        IllustrationView(
                            it.imageFileName,
                            it.soundFileName
                        )
                    },
                    lessonProgress.isTaskCompleted(it),
                    it.letter
                )
            else
                TaskView(
                    it.id,
                    it.illustrations.map {
                        IllustrationView(
                            it.imageFileName,
                            it.soundFileName
                        )
                    },
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
        val illustrations: List<IllustrationView>,
        val isCompleted: Boolean
    ) {
        override fun toString(): String {
            return "TaskView(id=$id, illustrations=$illustrations, isCompleted: $isCompleted)"
        }
    }

    class LetterListeningTaskView(
        id: Identity,
        illustrations: List<IllustrationView>,
        isCompleted: Boolean,
        val letter: String
    ) : TaskView(id, illustrations, isCompleted) {
        override fun toString(): String {
            return "LetterListeningTaskView(id=$id, illustrations=$illustrations, isCompleted: $isCompleted, letter=$letter)"
        }
    }

    data class IllustrationView(val imageFileName: String, val soundFileName: String)
}


