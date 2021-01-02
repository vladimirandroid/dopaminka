package ru.dopaminka.tasks.listenAndSelectText

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_listen_and_select_text.*
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.common.AssetAudioPlayer
import ru.dopaminka.common.Pronouncer
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.ListenAndSelectTextTask
import ru.dopaminka.tasks.TaskFragment
import kotlin.random.Random

class ListenAndSelectTextTaskFragment : TaskFragment<ListenAndSelectTextTask>() {

    private val pronouncer: Pronouncer by inject()
    private val audioPlayer: AssetAudioPlayer by inject()
    private var isAnswered = false

    override fun stop() {
        pronouncer.release()
        audioPlayer.release()
    }

    override fun start() {
        pronouncer.pronounce(task.rightText)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listen_and_select_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text1.setReadable(if (isFirstTextRight) task.rightText else task.wrongText)
        text2.setReadable(if (isFirstTextRight) task.wrongText else task.rightText)

        text1.setOnClickListener { if (isFirstTextRight) right() else wrong() }
        text2.setOnClickListener { if (isFirstTextRight) wrong() else right() }

        finish.setOnClickListener { if (isAnswered) onTaskCompleted() }
    }

    private fun right() {
        isAnswered = true
        audioPlayer.play("17.mp3")
    }

    private fun wrong() {
        audioPlayer.play("15.mp3")
    }

    private val isFirstTextRight: Boolean
        get() = arguments!!.getBoolean(isFirstTextRightKey)

    companion object {
        private const val isFirstTextRightKey = "isFirstRight"
        fun create(lesson: Lesson, task: ListenAndSelectTextTask): Fragment {
            return ListenAndSelectTextTaskFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(lessonKey, lesson)
                    putSerializable(taskKey, task)
                    putBoolean(isFirstTextRightKey, Random.nextBoolean())
                }
            }
        }
    }


}