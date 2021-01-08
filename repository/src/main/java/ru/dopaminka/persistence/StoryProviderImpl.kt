package ru.dopaminka.persistence

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import ru.dopaminka.entity.program.Lesson
import ru.dopaminka.entity.program.Program
import ru.dopaminka.entity.story.Story
import ru.dopaminka.usecases.StoryProvider

class StoryProviderImpl(
    val program: Program,
    private val rawFileTextProvider: RawFileTextProvider
) : StoryProvider {
    override fun get(): Story {
        val moshi = Moshi.Builder()
            .add(LessonAdapter(program))
            .build()

        val storyAdapter = moshi.adapter(Story::class.java)
        val story = storyAdapter.fromJson(rawFileTextProvider.get(R.raw.story))!!
        return story
    }

    class LessonAdapter(private val program: Program) {
        @ToJson
        fun toJson(lesson: Lesson) = program.lessons.indexOf(lesson)

        @FromJson
        fun fromJson(lesson: String) = program.lessons[lesson.toInt()]
    }
}