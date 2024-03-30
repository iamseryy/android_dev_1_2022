package ru.word_counter.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.word_counter.R
import ru.word_counter.data.local.App
import ru.word_counter.databinding.FragmentWordCounterBinding
import ru.word_counter.ui.viewmodel.State
import ru.word_counter.ui.viewmodel.WordCounterViewModel


class WordCounterFragment : Fragment() {
    private var _binding: FragmentWordCounterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WordCounterViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (requireContext().applicationContext as App).db.wordDao()
                return WordCounterViewModel(wordDao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordCounterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchInputEditText.doOnTextChanged{text, _, _, _ ->
                viewModel.onTextChanged(text)
            }

            addButton.setOnClickListener {
                viewModel.onAdd(searchInputEditText.text.toString())
            }

            clearButton.setOnClickListener {
                viewModel.onClear()
            }

            lifecycleScope.launch {
                viewModel.firstFiveWord.collect {
                    wordListTextview.text = it.joinToString(separator = "\n")
                }
            }

            lifecycleScope.launch {
                viewModel.state.collect {
                    when (it) {
                        State.Ok -> {
                            addButton.isEnabled = true
                            searchInputLayout.isErrorEnabled = false
                        }

                        State.Error -> {
                            addButton.isEnabled = false
                            searchInputLayout.isErrorEnabled = true
                            searchInputLayout.error = resources.getString(R.string.only_letters_hyphens_allowed)
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}