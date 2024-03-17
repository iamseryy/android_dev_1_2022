package ru.m12_mvvm.ui

sealed class State {
    object SearchPrepare: State()
    object Searching: State()
    data class SearchFinish(val searchText: String) : State()
    object SearchTextChange: State()


}