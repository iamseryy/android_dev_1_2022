package com.attractions.di

import android.content.Context
import androidx.room.Room
import com.attractions.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "attractions_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.photoDao()
}