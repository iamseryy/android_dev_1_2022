package com.attractions.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.attractions.entity.Photo
import kotlinx.coroutines.flow.Flow


@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    fun findAll() : Flow<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: Photo)
}