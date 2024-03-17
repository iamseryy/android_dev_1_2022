package ru.m12_mvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.m12_mvvm.repository.Repository

class SearchViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.SearchPrepare)
    val state = _state.asStateFlow()

    private val _buttonEnabled = Channel<Boolean>()
    var buttonEnabled = _buttonEnabled.receiveAsFlow()

    private val _searchResult = Channel<String>()
    var searchResultFlow = _searchResult.receiveAsFlow()

    private var searchResult = ""

    fun onSearchButtonClick(searchText: String) {
        viewModelScope.launch {
            _state.value = State.Searching
            searchResult = repository.find(searchText)
            _searchResult.send(searchResult)
            _state.value = State.SearchFinish(searchText)
        }
    }

    fun onTextChanged(length: Int) {
        viewModelScope.launch {
            _buttonEnabled.send(length >= 3)
        }
    }

    fun setSearchResult(defaultValue: String) {
        viewModelScope.launch {
            if(searchResult.isBlank()) searchResult = defaultValue
            _searchResult.send(searchResult)
        }
    }
}