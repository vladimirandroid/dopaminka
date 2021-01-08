package ru.dopaminka.usecases

import ru.dopaminka.entity.story.StoryFragment

/**
 * input = lesson title
 */
class CompleteStoryFragment(private val storyProgressRepository: StoryProgressRepository) :
    UseCase<CompleteStoryFragment.Params, Unit>() {

    override fun execute(params: Params) {
        var progress = storyProgressRepository.progress
        progress = progress.complete(params.fragment)
        storyProgressRepository.progress = progress
    }

    data class Params(val fragment: StoryFragment)
}