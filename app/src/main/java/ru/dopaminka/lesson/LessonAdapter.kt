package ru.dopaminka.lesson

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Task
import ru.dopaminka.entity.readingProgram.*
import ru.dopaminka.tasks.TaskFragment
import ru.dopaminka.tasks.draw.DrawTextTaskFragment
import ru.dopaminka.tasks.listen.ListenTaskFragment

class LessonAdapter(val lesson: Lesson, fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = lesson.tasks.size

    override fun createFragment(position: Int): Fragment {
        val task = lesson.tasks[position]
        return when (task) {
            is ListenTask -> ListenTaskFragment.create(task, lesson)
            is ListenAndSelectTextTask -> StubFragment("ListenAndSelectTextTask")
            is DrawTextTask -> DrawTextTaskFragment.create(task, lesson)
            is InsertMissingLetterTask -> StubFragment("InsertMissingLetterTask")
            is JoinTwoLettersTask -> StubFragment("JoinTwoLettersTask")
            is ReadAndSelectImageTask -> StubFragment("ReadAndSelectImageTask")
            else -> throw IllegalArgumentException("Unknown task type")
        }
    }
}

class StubFragment(val title: String) : TaskFragment<Task>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(context).apply {
            setTextColor(Color.BLUE)
            textSize = 30f
            text = title
            setOnClickListener { onTaskCompleted() }
        }
    }

}