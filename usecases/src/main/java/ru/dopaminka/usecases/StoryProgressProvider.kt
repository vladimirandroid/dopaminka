package ru.dopaminka.usecases

import ru.dopaminka.entity.story.StoryProgress

interface StoryProgressProvider {
    val progress: StoryProgress
}