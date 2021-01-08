package ru.dopaminka.usecases

import ru.dopaminka.entity.story.StoryProgress

interface StoryProgressRepository : StoryProgressProvider {
    override var progress: StoryProgress
}