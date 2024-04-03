package com.photosofmars.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.photosofmars.domain.GetMarsPhotoUseCase
import com.photosofmars.entity.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MarsPhotoViewModel @Inject constructor(
    private val getMarsPhotoUseCase: GetMarsPhotoUseCase
): ViewModel() {

    private val _photoFlow: MutableStateFlow<List<Photo>?> = MutableStateFlow(null)
    var photoFlow = _photoFlow.asStateFlow()


}