package ru.dopaminka.entity.story

data class StoryProgress(
    val currentFragment: StoryFragment,
    val isStoryCompleted: Boolean = false,
    private val story: Story
) {
    fun complete(fragment: StoryFragment): StoryProgress {
        if (currentFragment != fragment) return this
        if (currentFragment == story.fragments.last()) return this.copy(isStoryCompleted = true)
        return this.copy(currentFragment = getNextFragment())
    }

    private fun getNextFragment(): StoryFragment {
        val index = story.fragments.indexOf(currentFragment) + 1
        return story.fragments[index]
    }
}