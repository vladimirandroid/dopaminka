package ru.dopaminka.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.listenTask.ListenTaskFragment
import ru.dopaminka.usecases.ProgramProvider

class StoryActivity : AppCompatActivity() {
    private val programProvider: ProgramProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        val program = programProvider.get()
        val lesson = program.lessons.first()
        val task = lesson.tasks.first() as ListenTask

        if (shouldAddTask()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ListenTaskFragment.create(task, lesson), "task")
                .commit()
        }
    }

    private fun shouldAddTask() = supportFragmentManager.findFragmentByTag("task") == null


//    override fun onCompleteTask(taskId: Identity) {
//        next()
//    }
}

//interface TaskCompleteListener {
//    fun onCompleteTask(taskId: Identity)
//}