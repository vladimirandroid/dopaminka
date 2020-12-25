package ru.dopaminka.tasks

import androidx.fragment.app.Fragment
import ru.dopaminka.lesson.LessonFragment

abstract class TaskFragment : Fragment() {

    protected fun onTaskCompleted() {
        (parentFragment as LessonFragment).onTaskCompleted()
    }

}