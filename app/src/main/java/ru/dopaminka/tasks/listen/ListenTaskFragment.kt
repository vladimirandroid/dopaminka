package ru.dopaminka.tasks.listen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_listen_task.*
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.tasks.TaskFragment

class ListenTaskFragment : TaskFragment<ListenTask>(R.layout.fragment_listen_task) {

    private val pronouncer: Pronouncer by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pronouncer.listener = object : Pronouncer.Listener() {
            override fun onStart(duration: Int, atomicPosition: Int) {
                readableView.highlight(atomicPosition, duration)
            }
        }
        readableView.setReadable(task.text)
        readableView.setOnClickListener { pronouncer.pronounce(task.text) }
        finish.setOnClickListener {
            onTaskCompleted()
        }
    }

    override fun stop() {
        pronouncer.release()
    }

    override fun start() {
        pronouncer.pronounce(task.text)
    }

    companion object {
        fun create(task: ListenTask, lesson: Lesson): Fragment {
            val fragment = ListenTaskFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(taskKey, task)
                putSerializable(lessonKey, lesson)
            }
            return fragment
        }
    }
}