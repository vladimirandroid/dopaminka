package ru.dopaminka.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.lesson.LessonFragment
import ru.dopaminka.usecases.ProgramProvider

class StoryActivity : AppCompatActivity() {
    private val programProvider: ProgramProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        val program = programProvider.get()
        val lesson = program.lessons.first()

        if (shouldAddTask()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, LessonFragment.create(lesson), "lesson")
                .commit()
        }
    }

    private fun shouldAddTask() = supportFragmentManager.findFragmentByTag("lesson") == null


//    override fun onCompleteTask(taskId: Identity) {
//        next()
//    }
}

//interface TaskCompleteListener {
//    fun onCompleteTask(taskId: Identity)
//}