package ru.search_engine.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.search_engine.repository.Repository

@Suppress("UNCHECKED_CAST")
class SearchViewModalFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(Repository()) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}