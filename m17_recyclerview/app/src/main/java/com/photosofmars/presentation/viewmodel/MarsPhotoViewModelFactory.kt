package com.photosofmars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MarsPhotoViewModelFactory @Inject constructor(
    private val marsListPhotoViewModel: MarsListPhotoViewModel
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarsListPhotoViewModel::class.java)) {
            return marsListPhotoViewModel as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}