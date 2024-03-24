package ru.m12_mvvm.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.m12_mvvm.R
import ru.m12_mvvm.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
        private val viewModel: SearchViewModel by viewModels{SearchViewModalFactory()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setSearchResult(resources.getString(R.string.search_results))

        binding.apply {
            searchButton.setOnClickListener {
                view.hideKeyBoard()
                viewModel.onSearchButtonClick(searchInputEditText.text.toString())
            }

            searchInputEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onTextChanged(text?.length!!)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.state.collect{ state ->
                    when(state){
                        State.SearchPrepare -> {
                            searchButton.isEnabled = false
                            progressBar.isVisible = false
                        }
                        State.Searching -> {
                            progressBar.isVisible = true
                            searchButton.isEnabled = false
                        }
                        is State.SearchFinish -> {
                            progressBar.isVisible = false
                            searchButton.isEnabled = true
                        }
                        State.SearchTextChange -> {
                            searchButton.isEnabled = false
                            progressBar.isVisible = false
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.buttonEnabled.collect { isEnabled ->
                    searchButton.isEnabled = isEnabled
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.searchResultFlow.collect { result ->
                    resultTextView.text = result
                }
            }
        }
    }

    fun View.hideKeyBoard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}