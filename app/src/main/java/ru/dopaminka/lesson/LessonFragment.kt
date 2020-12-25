package ru.dopaminka.lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lesson.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.dopaminka.R
import ru.dopaminka.entity.program.Lesson

class LessonFragment : Fragment() {

    private val lesson: Lesson by lazy { arguments!!.getSerializable(lessonKey) as Lesson }
    private val viewModel: LessonViewModel by viewModel { parametersOf(lesson) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_lesson, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = LessonAdapter(lesson, this)
        viewPager.isUserInputEnabled = false

        viewModel.currentTask.observe(this) {
            viewPager.currentItem = lesson.tasks.indexOf(it)
        }
    }

    fun onTaskCompleted() {
        viewModel.onTaskCompleted()
    }

    companion object {
        private const val lessonKey = "lessonKey"
        fun create(lesson: Lesson): LessonFragment {
            val fragment = LessonFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(lessonKey, lesson)
            }
            return fragment
        }
    }

}