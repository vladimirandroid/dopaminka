package ru.dopaminka.specification

import ru.dopaminka.entity.story.Story
import ru.dopaminka.usecases.StoryProvider

class SpecificationStoryProvider : StoryProvider {

    lateinit var story: Story

    override fun get() = story
}