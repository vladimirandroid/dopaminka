package ru.dopaminka.tasks.listen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_listen_task.*
import ru.dopaminka.R
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.readingProgram.ListenTask
import ru.dopaminka.common.pronunciationFragment.ReadablePronunciationFragment

class ListenTaskFragment : Fragment() {
    private val task: ListenTask by lazy { arguments!!.getSerializable(taskKey) as ListenTask }
    private val lesson: Lesson by lazy { arguments!!.getSerializable(lessonKey) as Lesson }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listen_task, container, false)
    }

    override fun onStart() {

        if (shouldAddReadableFragment()) {
            childFragmentManager.beginTransaction()
                .add(
                    R.id.readableContainer,
                    ReadablePronunciationFragment.create(task.text),
                    "readable"
                )
                .commit()
        }

        finish.setOnClickListener {
//            (activity as TaskCompleteListener).onCompleteTask(task.id)
        }
        super.onStart()
    }

    private fun shouldAddReadableFragment() =
        childFragmentManager.findFragmentByTag("readable") == null

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