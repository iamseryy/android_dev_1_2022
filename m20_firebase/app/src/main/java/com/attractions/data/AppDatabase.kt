package com.attractions.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.attractions.data.local.dao.PhotoDao
import com.attractions.entity.Photo

@Database(entities = [Photo::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}