package ru.user_profile.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.user_profile.exception.ApiServiceException
import ru.user_profile.model.Results
import ru.user_profile.repository.UserRepository

class UserProfileViewModel(private val repository: UserRepository)  : ViewModel() {
    private val _results: MutableStateFlow<Results?> = MutableStateFlow(null)
    var resultsFlow = _results.asStateFlow()

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    fun getRandomUser() {
        viewModelScope.launch {
            try {
                _state.value = State.Loading
                _results.value = repository.getRandomUser()
                _state.value = State.Finish
            } catch (e: ApiServiceException) {
                _state.value = State.Error
                _error.send(e.message!!)
            } catch (e: Exception) {
                _state.value = State.Error
                _error.send(e.message!!)
            }
        }
    }
}