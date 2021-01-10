package ru.dopaminka.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task
import ru.dopaminka.lesson.LessonFragment

abstract class TaskFragment<T : Task>(@LayoutRes private val layoutId: Int? = null) : Fragment() {

    val task: T by lazy { arguments!!.getSerializable(taskKey) as T }
    val lesson: Lesson by lazy { arguments!!.getSerializable(lessonKey) as Lesson }

    abstract fun stop()
    abstract fun start()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutId?.let {
            return inflater.inflate(layoutId, container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected fun onTaskCompleted() {
        (parentFragment as LessonFragment).onTaskCompleted()
    }

    companion object {
        const val taskKey = "taskKey"
        const val lessonKey = "lessonKey"
    }
}