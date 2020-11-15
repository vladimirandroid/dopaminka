package ru.dopaminka

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.get
import ru.dopaminka.usecases.lesson.GetLessonTasks
import ru.dopaminka.usecases.task.CompleteListenTask
import ru.dopaminka.usecases.program.GetLessons

class LetterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter)

        val lessons = get<GetLessons>().execute(Unit)
        val lesson = get<GetLessonTasks>().execute(lessons.first().id)
        val task = lesson.tasks[0]
        get<CompleteListenTask>().execute(CompleteListenTask.Params(lesson.id, task.id))
        Log.d(this.localClassName, "lesson = $lesson")
    }
}