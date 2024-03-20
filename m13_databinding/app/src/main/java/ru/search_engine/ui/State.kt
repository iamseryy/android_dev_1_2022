package ru.search_engine.ui

sealed class State (open val searchResult: String? = null) {
    data class Searching(override val searchResult: String?) : State(searchResult = searchResult)
    data class SearchFinish(override val searchResult: String?) : State(searchResult = searchResult)
}