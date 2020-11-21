package ru.dopaminka.listenTask

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_listen_task.*
import org.koin.android.ext.android.get
import ru.dopaminka.R
import ru.dopaminka.entity.common.Identity
import ru.dopaminka.story.TaskCompleteListener
import ru.dopaminka.usecases.task.GetTask

class ListenTaskFragment : Fragment() {
    private val player = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listen_task, container, false);
    }

    override fun onStart() {
        val task = getTask()

        val imageId = resources.getIdentifier(
            task.image,
            "drawable",
            context!!.getPackageName()
        )

        illustration.setImageResource(imageId)

        illustration.setOnClickListener {
            if (player.isPlaying) player.stop()

            val file = context!!.assets.openFd(task.sound)
            player.reset()

            player.setDataSource(file.fileDescriptor, file.startOffset, file.length)
            file.close()
            player.prepare()
            player.start()
        }

        finish.setOnClickListener {
            (activity as TaskCompleteListener).onCompleteTask(task.id)
        }
        super.onStart()
    }

    override fun onDestroy() {
        if (player.isPlaying) player.release();
        super.onDestroy()
    }

    private fun getTask() = get<GetTask>().execute(getTaskId()) as GetTask.ListenTaskView
    private fun getTaskId() = arguments!!.getSerializable(taskIdKey) as Identity

    companion object {
        const val taskIdKey = "taskIdKey"
        fun create(taskId: Identity): Fragment {
            val fragment = ListenTaskFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(taskIdKey, taskId)
            }
            return fragment
        }
    }
}