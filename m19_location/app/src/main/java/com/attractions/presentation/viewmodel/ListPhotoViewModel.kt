package com.attractions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.attractions.domain.PhotoUseCase
import javax.inject.Inject

class ListPhotoViewModel@Inject constructor(
    private val photoUseCase: PhotoUseCase
): ViewModel() {

    val photos = photoUseCase.findAll()
}