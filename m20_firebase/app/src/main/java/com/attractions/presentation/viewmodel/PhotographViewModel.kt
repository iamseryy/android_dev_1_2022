package com.attractions.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attractions.domain.PhotoUseCase
import com.attractions.entity.Photo
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotographViewModel @Inject constructor(private val useCase: PhotoUseCase): ViewModel() {
    fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            kotlin.runCatching {
                useCase.insert(photo)
            }.fold(
                onSuccess = {},
                onFailure = {
                    Log.d("PhotographViewModel", it.message ?: "")
                }
            )
        }
    }
}