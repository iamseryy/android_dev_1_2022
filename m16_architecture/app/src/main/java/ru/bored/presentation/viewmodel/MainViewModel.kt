package ru.bored.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bored.data.UsefulActivityDto
import ru.bored.domain.GetUsefulActivityUseCase
import ru.bored.entity.UsefulActivity
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
): ViewModel()  {

    private val _usefulActivityFlow: MutableStateFlow<UsefulActivity?> = MutableStateFlow(null)
    var usefulActivityFlow = _usefulActivityFlow.asStateFlow()

    suspend fun reloadUsefulActivity() {
        _usefulActivityFlow.value = getUsefulActivityUseCase.execute()

    }
}