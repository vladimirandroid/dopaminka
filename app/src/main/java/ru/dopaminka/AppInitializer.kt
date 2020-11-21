package ru.dopaminka

import android.util.Log
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import ru.dopaminka.usecases.lesson.AddTask
import ru.dopaminka.usecases.lesson.CreateLesson
import ru.dopaminka.usecases.lesson.GetUnassignedLessons
import ru.dopaminka.usecases.program.CreateProgram
import ru.dopaminka.usecases.program.GetLessons
import ru.dopaminka.usecases.program.SetLessons
import ru.dopaminka.usecases.task.CreateListenTask
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

        val lessonId = get<CreateLesson>().execute(CreateLesson.Params("Первый урок"))
        for (t in tasks) {
            get<AddTask>().execute(AddTask.Params(lessonId, t.id))
        }
    }

    private fun setUpTasks() {
        val createTask: CreateListenTask = get()
        for (i in 1..33) {
            val number = i.toString().padStart(2, '0')
            createTask.execute(
                CreateListenTask.Params(
                    "char_$number",
                    "$number.mp3"
                )
            )
        }
    }

    companion object {
        const val TAG = "initialize"
    }
}