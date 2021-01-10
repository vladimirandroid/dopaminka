package ru.dopaminka.entity.story

import ru.dopaminka.entity.program.Lesson
import java.io.Serializable

data class Story(val fragments: List<StoryFragment>)

data class StoryFragment(
    val pivotX: Float,
    val pivotY: Float,
    val image: String,
    val voice: String,
    val lesson: Lesson? = null
) : Serializable