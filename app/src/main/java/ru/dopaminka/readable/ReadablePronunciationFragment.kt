package ru.dopaminka.readable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_readable_pronunciation.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.dopaminka.R
import ru.dopaminka.entity.reading.Readable

class ReadablePronunciationFragment : Fragment() {

    private val readable: Readable by lazy { arguments!!.getSerializable(readableKey) as Readable }

    private val viewModel: ReadablePronunciationViewModel by viewModel { parametersOf(readable) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_readable_pronunciation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readableView.setReadable(readable)
        readableView.setOnClickListener {
            viewModel.onPronounce()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    companion object {
        private const val readableKey = "readableKey"
        fun create(readable: Readable): Fragment {
            val fragment = ReadablePronunciationFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(readableKey, readable)
            }
            return fragment
        }
    }

}