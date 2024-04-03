package com.photosofmars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MarsPhotoViewModelFactory @Inject constructor(
    private val marsPhotoViewModel: MarsPhotoViewModel
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarsPhotoViewModelFactory::class.java)) {
            return marsPhotoViewModel as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}