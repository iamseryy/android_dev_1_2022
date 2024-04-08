package com.attractions.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.attractions.data.local.dao.PhotoDao
import com.attractions.entity.Photo

@Database(entities = [Photo::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}