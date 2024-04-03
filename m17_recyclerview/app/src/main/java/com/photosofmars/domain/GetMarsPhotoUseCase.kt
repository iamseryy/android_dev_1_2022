package com.photosofmars.domain

import com.photosofmars.data.MarsPhotosRepository
import com.photosofmars.entity.Photo
import javax.inject.Inject

class GetMarsPhotoUseCase @Inject constructor(
    private val marsPhotosRepository: MarsPhotosRepository
) {
    suspend fun execute(date: String): List<Photo> {
        return marsPhotosRepository.getMarsPhotoByDate(date)
    }
}