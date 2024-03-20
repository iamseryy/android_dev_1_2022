package ru.search_engine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.search_engine.repository.Repository


private const val DEFAULT_SEARCH_RESULT = "Search results"

class SearchViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.SearchFinish(DEFAULT_SEARCH_RESULT))
    val state = _state.asStateFlow()

    private var searchText = MutableStateFlow("")
    private var searchJob: Job? = null

    @OptIn(FlowPreview::class)
    fun onSearchTextChanged(searchText: String) {
        searchJob?.cancel()
        this.searchText.value = searchText
        if(searchText.length >= 3 ) {
            searchJob = this.searchText.debounce(300).onEach {
                _state.value = State.Searching(state.value.searchResult)
                _state.value = State.SearchFinish(repository.find(searchText))
            }.launchIn(viewModelScope)
        }
    }
}