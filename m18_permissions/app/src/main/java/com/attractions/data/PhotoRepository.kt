package com.attractions.data

import com.attractions.data.local.dao.PhotoDao
import com.attractions.entity.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val photoDao: PhotoDao){
    fun findAll(): Flow<List<Photo>> {
        return photoDao.findAll()
    }
}