package ru.dopaminka.tasks

import androidx.fragment.app.Fragment
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task
import ru.dopaminka.lesson.LessonFragment

abstract class TaskFragment<T : Task> : Fragment() {
    protected val task: T by lazy { arguments!!.getSerializable(taskKey) as T }
    protected val lesson: Lesson by lazy { arguments!!.getSerializable(lessonKey) as Lesson }

    abstract fun stop()
    abstract fun start()

    protected fun onTaskCompleted() {
        (parentFragment as LessonFragment).onTaskCompleted()
    }

    companion object {
        const val taskKey = "taskKey"
        const val lessonKey = "lessonKey"
    }
}