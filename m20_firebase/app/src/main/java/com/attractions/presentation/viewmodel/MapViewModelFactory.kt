package com.attractions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MapViewModelFactory @Inject constructor(
    private val mapViewModel: MapViewModel
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return mapViewModel as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}