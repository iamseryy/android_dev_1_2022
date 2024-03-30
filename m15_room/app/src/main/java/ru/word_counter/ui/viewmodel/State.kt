package ru.word_counter.ui.viewmodel

sealed class State {
    data object Ok: State()
    data object Error: State()
}