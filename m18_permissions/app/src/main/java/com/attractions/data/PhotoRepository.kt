package com.attractions.data

import com.attractions.data.local.dao.PhotoDao
import com.attractions.entity.Photo
import javax.inject.Inject


class PhotoRepository @Inject constructor(
    private val photoDao: PhotoDao
){
    fun findAll() = photoDao.findAll()
    suspend fun insert(photo: Photo) = photoDao.insert(photo)
}
