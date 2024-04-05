package com.photosofmars.data

import android.util.Log
import com.photosofmars.data.api.ApiService
import com.photosofmars.data.api.RetrofitBuilder
import com.photosofmars.data.dto.PhotoDto
import com.photosofmars.data.dto.PhotosDto
import javax.inject.Inject

class PhotoDataSource @Inject constructor() {

    suspend fun loadPhotos(date: String): PhotosDto {
        return RetrofitBuilder.apiService.getMarsPhotoByDate(date)
    }
}

