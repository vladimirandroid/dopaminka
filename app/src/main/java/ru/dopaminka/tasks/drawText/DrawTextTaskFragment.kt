package ru.dopaminka.tasks.drawText

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_draw_text_task.*
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.DrawTextTask
import ru.dopaminka.tasks.TaskFragment
import ru.dopaminka.tasks.drawText.drawTextView.DrawTextView

class DrawTextTaskFragment : TaskFragment<DrawTextTask>(R.layout.fragment_draw_text_task),
    DrawTextView.CompleteListener {

    private val pronouncer: Pronouncer by inject()


    override fun stop() {
        pronouncer.release()
    }

    override fun start() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drawTextView = DrawTextView(context!!, task.text)
        drawContainer.addView(
            drawTextView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        drawTextView.listener = this
        drawTextView.setOnClickListener {
            pronouncer.pronounce(task.text)
        }

        finish.setOnClickListener {
            if (drawTextView.isCompleted) onTaskCompleted()
        }
    }

    override fun onComplete() {
        pronouncer.pronounce(task.text)
    }

    companion object {
        const val taskKey = "taskKey"
        const val lessonKey = "lessonKey"
        fun create(task: DrawTextTask, lesson: Lesson): Fragment {
            val fragment = DrawTextTaskFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(taskKey, task)
                putSerializable(lessonKey, lesson)
            }
            return fragment
        }
    }
}