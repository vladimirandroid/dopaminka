package ru.dopaminka.lesson

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_lesson.*
import ru.dopaminka.R
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.tasks.TaskFragment

class LessonFragment : Fragment(R.layout.fragment_lesson) {

    var completeListener: (() -> Unit)? = null

    private val lesson: Lesson by lazy { arguments!!.getSerializable(lessonKey) as Lesson }
    private val adapter by lazy { LessonAdapter(lesson, this) }
    private var currentTaskFragment: TaskFragment<*>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (positionOffset < 0.000001f) {
                    currentTaskFragment?.stop()
                    currentTaskFragment =
                        childFragmentManager.findFragmentByTag("f${viewPager.currentItem}") as TaskFragment<*>
                    currentTaskFragment?.start()
                }
            }
        })
    }

    fun onTaskCompleted() {
        val task = currentTaskFragment!!.task
        if (task != lesson.tasks.last()) {
            val index = lesson.tasks.indexOf(task) + 1
            viewPager.currentItem = index
        } else {
            completeListener?.invoke()
        }
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