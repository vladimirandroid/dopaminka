package ru.dopaminka.lesson

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task

class LessonViewModel(private val lesson: Lesson, application: Application) :
    AndroidViewModel(application) {

    val currentTask: LiveData<Task> = MutableLiveData<Task>().apply { postValue(lesson.tasks[0]) }

    fun onTaskCompleted() {
        val task = currentTask.value
        if (task != lesson.tasks.last()) {
            val index = lesson.tasks.indexOf(task) + 1
            (currentTask as MutableLiveData).postValue(lesson.tasks[index])
        }
    }
}