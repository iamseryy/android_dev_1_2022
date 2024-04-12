package com.attractions.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attractions.domain.PhotoUseCase
import com.attractions.entity.Photo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotographViewModel @Inject constructor(private val useCase: PhotoUseCase): ViewModel() {

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.insert(photo)
            }.fold(
                onSuccess = {},
                onFailure = {
                    Log.d("MarsPhotoViewModel", it.message ?: "")
                    _error.send(it.message ?: "")
                }
            )
        }
    }
}