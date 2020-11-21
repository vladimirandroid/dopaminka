package ru.dopaminka.story

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.dopaminka.listenTask.ListenTaskFragment
import ru.dopaminka.usecases.lesson.GetLessonTasks

class StoryAdapter(fa: FragmentActivity, val tasks: List<GetLessonTasks.TaskView>) :
    FragmentStateAdapter(fa) {

    override fun getItemCount() = tasks.size

    override fun createFragment(position: Int): Fragment {
        return ListenTaskFragment.create(tasks[position].id)
    }
}