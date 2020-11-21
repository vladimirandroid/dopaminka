package ru.dopaminka.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_story.*
import org.koin.android.ext.android.get
import ru.dopaminka.R
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.usecases.lesson.GetLessonTasks
import ru.dopaminka.usecases.program.GetLessons

class StoryActivity : AppCompatActivity(), TaskCompleteListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        val lessons = get<GetLessons>().execute(Unit)
        val lessonId = lessons.single().id
        val tasks = get<GetLessonTasks>().execute(lessonId).tasks
        viewPager.adapter = StoryAdapter(this, tasks)
    }

    override fun onCompleteTask(taskId: Identity) {
        next()
    }

    private fun next() {
        if (viewPager.currentItem + 1 < viewPager.adapter!!.itemCount) {
            viewPager.currentItem++
        }
    }
}

interface TaskCompleteListener {
    fun onCompleteTask(taskId: Identity)
}