package ru.dopaminka.tasks.joinTwoLetters

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_join_two_letters_task.*
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.JoinTwoLettersTask
import ru.dopaminka.tasks.TaskFragment

class JoinTwoLettersTaskFragment :
    TaskFragment<JoinTwoLettersTask>(R.layout.fragment_join_two_letters_task) {

    private val pronouncer: Pronouncer by inject()
    private var isCompleted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        letter1.setReadable(task.letter1)
        letter2.setReadable(task.letter2)
        joinView.completeListener = {
            isCompleted = true
            pronouncer.pronounce(task.result)
        }
        finish.setOnClickListener { if (isCompleted) onTaskCompleted() }
    }

    override fun stop() {
    }

    override fun start() {
    }

    companion object {
        fun create(task: JoinTwoLettersTask, lesson: Lesson): JoinTwoLettersTaskFragment {
            return JoinTwoLettersTaskFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(taskKey, task)
                    putSerializable(lessonKey, lesson)
                }
            }

        }
    }
}