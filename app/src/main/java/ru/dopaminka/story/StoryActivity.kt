package ru.dopaminka.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.usecases.CompleteStoryFragment
import ru.dopaminka.usecases.StoryProgressProvider

class StoryActivity : AppCompatActivity() {
    private val storyProgressProvider: StoryProgressProvider by inject()
    private val completeStoryFragment: CompleteStoryFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        if (finishIfStoryCompleted()) return

        val fragment =
            supportFragmentManager.findFragmentByTag(storyFragmentTag) as StoryFragmentFragment?
        if (fragment == null) {
            updateStoryFragmentFragment()
        } else {
            fragment.completeListener = ::onCompleteStoryFragment
        }
    }

    private fun onCompleteStoryFragment() {
        completeStoryFragment.execute(CompleteStoryFragment.Params(storyProgressProvider.progress.currentFragment))
        if (finishIfStoryCompleted()) return
        updateStoryFragmentFragment()
    }

    private fun finishIfStoryCompleted(): Boolean {
        val progress = storyProgressProvider.progress
        if (progress.isStoryCompleted) {
            finish()
            return true
        }
        return false
    }

    private fun updateStoryFragmentFragment() {
        val progress = storyProgressProvider.progress
        val fragment = StoryFragmentFragment.create(progress.currentFragment).apply {
            completeListener = ::onCompleteStoryFragment
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.story_fragment_in, R.anim.story_fragment_out)
            .replace(R.id.container, fragment, "lesson")
            .commit()
    }

    companion object {
        private const val storyFragmentTag = "storyFragmentTag"
    }
}