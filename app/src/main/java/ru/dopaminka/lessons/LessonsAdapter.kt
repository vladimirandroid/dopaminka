package ru.dopaminka.lessons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.dopaminka.R
import ru.dopaminka.usecases.program.GetLessons

class LessonsAdapter(
    private val lessons: List<GetLessons.LessonView>,
    private val onClick: (GetLessons.LessonView) -> Unit
) :
    RecyclerView.Adapter<LessonsAdapter.LessonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_lessons_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        holder.bind(lessons[position])
    }

    override fun getItemCount() = lessons.size

    inner class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView by lazy { itemView.findViewById(R.id.title) }
        private lateinit var lesson: GetLessons.LessonView

        init {
            title.setOnClickListener { onClick(lesson) }
        }

        fun bind(lesson: GetLessons.LessonView) {
            this.lesson = lesson
            title.text = lesson.title
        }
    }
}