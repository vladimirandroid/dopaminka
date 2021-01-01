package ru.dopaminka.tasks

import androidx.fragment.app.Fragment
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task
import ru.dopaminka.lesson.LessonFragment
import ru.dopaminka.tasks.listen.ListenTaskFragment

abstract class TaskFragment<T : Task> : Fragment() {
    protected val task: T by lazy { arguments!!.getSerializable(ListenTaskFragment.taskKey) as T }
    protected val lesson: Lesson by lazy { arguments!!.getSerializable(ListenTaskFragment.lessonKey) as Lesson }

    protected fun onTaskCompleted() {
        (parentFragment as LessonFragment).onTaskCompleted()
    }

}