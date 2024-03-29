package ru.word_counter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.word_counter.data.local.dao.WordDao
import ru.word_counter.data.local.entity.Word


const val PATTERN = "[a-zA-Zа-яА-Я-']+"
class WordCounterViewModel(private val wordDao: WordDao): ViewModel() {
    private val _isWordValid = Channel<Boolean>()
    var isWordValid = _isWordValid.receiveAsFlow()
    fun onTextChanged(text: CharSequence?) {

    }

    fun onAdd(word: String) {
        val isValid = word.matches(Regex(PATTERN))

        viewModelScope.launch {
            _isWordValid.send(isValid)
        }

        if(isValid) {
            viewModelScope.launch {
                if (wordDao.isWordExists(word)) {
                    wordDao.update(word)
                } else {
                    wordDao.insert(Word(word, 1))
                }
            }
        }
    }

    fun onClear() {
        viewModelScope.launch {
            wordDao.deleteAll()
        }
    }
}