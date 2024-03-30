package ru.word_counter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.word_counter.data.local.dao.WordDao
import ru.word_counter.data.local.entity.Word


const val PATTERN = "[a-zA-Zа-яА-Я-']+"
class WordCounterViewModel(private val wordDao: WordDao): ViewModel() {
    private val _state = MutableStateFlow<State>(State.Ok)
    val state = _state.asStateFlow()

    val firstFiveWord = wordDao.getLimitListWord(5).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun onTextChanged(text: CharSequence?) = setState(text.isNullOrEmpty() || text.matches(Regex(PATTERN)))

    fun onAdd(word: String) = word.matches(Regex(PATTERN)).apply {
        setState(this)
        saveWordIfValid(word, this)
    }

    private fun saveWordIfValid(word: String, isValid: Boolean) = viewModelScope.launch {
        if(isValid) saveWord(word)
    }

    private suspend fun saveWord(word: String) {
        if (wordDao.isWordExists(word)) {
            wordDao.update(word)
        } else {
            wordDao.insert(Word(word, 1))
        }
    }

    private fun setState(isValid: Boolean) = viewModelScope.launch {
        if(isValid) {
                _state.value = State.Ok
            } else {
                _state.value = State.Error
            }
        }

    fun onClear() {
        viewModelScope.launch {
            wordDao.deleteAll()
        }
    }
}