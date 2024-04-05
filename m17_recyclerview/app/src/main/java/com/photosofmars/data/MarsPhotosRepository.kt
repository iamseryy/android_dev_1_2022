package com.photosofmars.data

import com.photosofmars.data.api.RetrofitBuilder
import com.photosofmars.data.dto.PhotoDto
import javax.inject.Inject

class MarsPhotosRepository @Inject constructor() {
    suspend fun getMarsPhotoByDate(date: String): List<PhotoDto> {
        return RetrofitBuilder.apiService.getMarsPhotoByDate(date).photos
    }
}