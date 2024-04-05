package com.photosofmars.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.photosofmars.domain.GetMarsPhotoUseCase
import com.photosofmars.entity.Photo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val DATE_PHOTO = "2022-01-01"

class MarsListPhotoViewModel @Inject constructor(
    private val getMarsPhotoUseCase: GetMarsPhotoUseCase
): ViewModel() {

    private val _photoFlow: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    var photos = _photoFlow.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    init {
        getPhotos()
    }

    private fun getPhotos() {
        viewModelScope.launch {
            kotlin.runCatching {
                _isLoading.value = true
                getMarsPhotoUseCase.execute(DATE_PHOTO)
            }.fold(
                onSuccess = { _photoFlow.value = it } ,
                onFailure = {
                    Log.d("MarsPhotoViewModel", it.message ?: "")
                    _error.send(it.message ?: "")
                }
            )
            _isLoading.value = false
        }
    }

}