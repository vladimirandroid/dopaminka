package ru.dopaminka

import android.util.Log
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.dopaminka.usecases.lesson.AddTask
import ru.dopaminka.usecases.lesson.CreateLesson
import ru.dopaminka.usecases.lesson.GetLessonTasks
import ru.dopaminka.usecases.lesson.GetUnassignedLessons
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetLessons
import ru.dopaminka.usecases.program.SetLessons
import ru.dopaminka.usecases.task.CreateListenTask
import ru.dopaminka.usecases.task.GetTask
import ru.dopaminka.usecases.task.GetUnassignedTasks

@KoinApiExtension
class AppInitializer : KoinComponent {
    fun initialize() {
        setUpTasks()
        setUpLessons()
        setUpProgram()
    }

    private fun setUpProgram() {
        val lessons = get<GetUnassignedLessons>().execute(Unit)
        val lessonsIds = lessons.map { it.id }

        get<CreateProgram>().execute(Unit)
        get<SetLessons>().execute(SetLessons.Params(lessonsIds))

        val programView = get<GetLessons>().execute(Unit)
        Log.d(TAG, "Program = $programView")
    }

    private fun setUpLessons() {
        val tasks = get<GetUnassignedTasks>().execute(Unit)

        var lessonId = get<CreateLesson>().execute(CreateLesson.Params("Первый урок"))
        get<AddTask>().execute(AddTask.Params(lessonId, tasks.first().id))

        lessonId = get<CreateLesson>().execute(CreateLesson.Params("Второй урок"))
        get<AddTask>().execute(AddTask.Params(lessonId, tasks.first().id))

        lessonId = get<CreateLesson>().execute(CreateLesson.Params("Третий урок"))
        get<AddTask>().execute(AddTask.Params(lessonId, tasks.first().id))

        val lesson = get<GetLessonTasks>().execute(lessonId)
        Log.d(TAG, "Lesson = $lesson")
    }

    private fun setUpTasks() {
        val createTask: CreateListenTask = get()
        val taskId = createTask.execute(CreateListenTask.Params("char_01.png", "01.mp3"))
        val taskView = get<GetTask>().execute(taskId)
        Log.d(TAG, "Task = $taskView");
    }

    companion object {
        const val TAG = "initialize"
    }
}