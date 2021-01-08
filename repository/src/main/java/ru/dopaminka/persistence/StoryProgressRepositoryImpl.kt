package ru.dopaminka.persistence

import ru.dopaminka.entity.story.Story
import ru.dopaminka.entity.story.StoryProgress
import ru.dopaminka.usecases.StoryProgressRepository

class StoryProgressRepositoryImpl(story: Story) :
    StoryProgressRepository {

    override var progress = StoryProgress(story.fragments.first(), false, story)
}