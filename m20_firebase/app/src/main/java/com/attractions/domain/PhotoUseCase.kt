package com.attractions.domain

import com.attractions.data.PhotoRepository
import com.attractions.entity.Photo
import javax.inject.Inject


class PhotoUseCase @Inject constructor(private val repository: PhotoRepository) {
    fun findAll() = repository.findAll()
    suspend fun insert(photo: Photo) = repository.insert(photo)
}