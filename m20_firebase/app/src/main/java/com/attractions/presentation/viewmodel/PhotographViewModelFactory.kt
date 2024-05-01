package com.attractions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject


class PhotographViewModelFactory @Inject constructor(
    private val photographViewModel: PhotographViewModel
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotographViewModel::class.java)) {
            return photographViewModel as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}