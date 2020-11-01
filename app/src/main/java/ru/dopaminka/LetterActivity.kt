package ru.dopaminka

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.get
import ru.dopaminka.entity.Alphabet
import ru.dopaminka.usecases.lesson.GetLesson
import ru.dopaminka.usecases.lessonProgress.CompleteTask
import ru.dopaminka.usecases.program.GetProgram

class LetterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter)

        val program = get<GetProgram>().execute(GetProgram.Params(Alphabet.Language.ru))
        val lesson = get<GetLesson>().execute(program.lessons.first().id)
        val task = lesson.tasks[0]
        get<CompleteTask>().execute(CompleteTask.Params(lesson.id, task.id))
        Log.d(this.localClassName, "lesson = $lesson")
    }
}