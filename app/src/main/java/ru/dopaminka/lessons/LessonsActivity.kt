package ru.dopaminka.lessons

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_lessons.*
import org.koin.android.ext.android.get
import ru.dopaminka.R
import ru.dopaminka.usecases.lesson.GetLessonTasks
import ru.dopaminka.usecases.program.GetLessons

class LessonsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lessons)

        lessons.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()
        val adapter = LessonsAdapter(get<GetLessons>().execute(Unit)) { startLesson(it) }
        lessons.adapter = adapter
    }

    private fun startLesson(lesson: GetLessons.LessonView) {
        Log.d(localClassName, "open lesson: $lesson")
        Toast.makeText(
            this,
            "Щас погоди, начнется ${lesson.title}, только закодим его...",
            Toast.LENGTH_SHORT
        ).show()
        Log.d("TAG", "lesson = ${get<GetLessonTasks>().execute(lesson.id)}")
    }
}