package ru.dopaminka.usecases

import ru.dopaminka.entity.story.Story

interface StoryProvider {
    fun get(): Story
}