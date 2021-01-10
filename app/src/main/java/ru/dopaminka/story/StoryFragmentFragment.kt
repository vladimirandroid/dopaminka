package ru.dopaminka.story

import android.os.Bundle
import android.view.View
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_story_fragment.*
import org.koin.android.ext.android.inject
import ru.dopaminka.R
import ru.dopaminka.common.AssetAudioPlayer
import ru.dopaminka.entity.story.StoryFragment
import ru.dopaminka.lesson.LessonFragment

class StoryFragmentFragment : Fragment(R.layout.fragment_story_fragment) {
    var completeListener: (() -> Unit)? = null
    private val storyFragment: StoryFragment
        get() = arguments!!.getSerializable(storyFragmentKey) as StoryFragment
    private val audioPlayer: AssetAudioPlayer by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        illustration.setImageResource(
            resources.getIdentifier(
                storyFragment.image,
                "drawable",
                context!!.packageName
            )
        )
        audioPlayer.startListener = ::startImageAnimation
        audioPlayer.completeListener = ::onCompleteVoice
    }

    private fun onCompleteVoice() {
        storyFragment.lesson?.also { showLesson() } ?: completeListener?.invoke()
    }

    private fun showLesson() {
        var f = childFragmentManager.findFragmentByTag("lesson") as LessonFragment?
        if (f == null) {
            f = LessonFragment.create(storyFragment.lesson!!)
            childFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.lesson_fragment_in, R.anim.story_fragment_out)
                .add(R.id.root, f, "lesson")
                .commit()
        }
        f.completeListener = ::onLessonComplete
    }

    private fun onLessonComplete() {
        completeListener?.invoke()
    }

    override fun onResume() {
        super.onResume()
        audioPlayer.play(storyFragment.voice)
    }

    override fun onPause() {
        super.onPause()
        audioPlayer.release()
    }

    private fun startImageAnimation(duration: Int) {
        val width = activity!!.resources.displayMetrics.widthPixels.toFloat()
        val height = activity!!.resources.displayMetrics.heightPixels.toFloat()
        illustration.startAnimation(
            ScaleAnimation(
                1f, 1.5f,
                1f, 1.5f,
                width * storyFragment.pivotX, height * storyFragment.pivotY
            ).apply {
                this.duration = duration.toLong()
                fillAfter = true
            }
        )
    }

    companion object {
        private const val storyFragmentKey = "storyFragmentKey"
        fun create(storyFragment: StoryFragment): StoryFragmentFragment {
            return StoryFragmentFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(storyFragmentKey, storyFragment)
                }
            }
        }
    }
}