package ru.dopaminka.entity.story

import ru.dopaminka.entity.program.Lesson

data class Story(val fragments: List<StoryFragment>)

data class StoryFragment(val image: String, val voice: String, val lesson: Lesson? = null)