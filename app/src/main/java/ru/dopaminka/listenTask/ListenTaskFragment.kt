package ru.dopaminka.listenTask

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_listen_task.*
import ru.dopaminka.R
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.utils.Pronouncer

class ListenTaskFragment : Fragment() {
    private val pronouncer: Pronouncer by lazy { Pronouncer(activity!!.assets, MediaPlayer()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listen_task, container, false)
    }

    override fun onStart() {
        val task = getTask()

//        val imageId = resources.getIdentifier(
//            task.image,
//            "drawable",
//            context!!.getPackageName()
//        )

//        illustration.setImageResource(imageId)

        illustration.setOnClickListener {
            pronouncer.pronounce(task.text)
        }

        finish.setOnClickListener {
//            (activity as TaskCompleteListener).onCompleteTask(task.id)
        }
        super.onStart()
    }

    override fun onDestroy() {
        pronouncer.release()
        super.onDestroy()
    }

    private fun getTask() = arguments!!.getSerializable(taskKey) as ListenTask
    private fun getLesson() = arguments!!.getSerializable(lessonKey) as Lesson

    companion object {
        const val taskKey = "taskKey"
        const val lessonKey = "lessonKey"
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