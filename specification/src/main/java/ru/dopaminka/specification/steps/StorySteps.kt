package ru.dopaminka.specification.steps

import io.cucumber.java8.En
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.dopaminka.entity.story.Story
import ru.dopaminka.entity.story.StoryFragment
import ru.dopaminka.specification.SpecificationStoryProvider
import ru.dopaminka.usecases.CompleteStoryFragment
import ru.dopaminka.usecases.StoryProgressProvider
import ru.dopaminka.usecases.StoryProvider

@KoinApiExtension
class StorySteps : En, KoinComponent {

    private lateinit var fragments: List<StoryFragment>
    private val storyProvider: StoryProvider by inject()
    private val completeStoryFragment: CompleteStoryFragment by inject()
    private val storyProgressProvider: StoryProgressProvider by inject()

    init {
        Given("есть история") {
            val story = Story(mutableListOf())

            val storyProvider = storyProvider as SpecificationStoryProvider
            storyProvider.story = story
        }
        Given("в истории {int} фрагментов") { count: Int ->
            val fragments = storyProvider.get().fragments as MutableList
            for (i in 1..count) fragments.add(StoryFragment("", ""))
        }

        When("ученик завершает фрагмент {int}") { index: Int ->
            val fragment = storyProvider.get().fragments[index]
            completeStoryFragment.execute(CompleteStoryFragment.Params(fragment))
        }
        When("ученик смотрит историю") {
            fragments = storyProvider.get().fragments
        }


        Then("ученик переходит к фрагменту {int}") { index: Int ->
            val fragment = storyProvider.get().fragments[index]
            val currentFragment = storyProgressProvider.progress.currentFragment
            assertEquals(fragment, currentFragment)
        }
        Then("история завершается") {
            assertTrue(storyProgressProvider.progress.isStoryCompleted)
        }
        Then("ученик видит список фрагментов") {
            Assert.assertNotNull(fragments)
            assertTrue(fragments.isNotEmpty())
        }

    }
}