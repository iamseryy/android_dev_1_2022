package ru.word_counter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.word_counter.data.local.dao.WordDao
import ru.word_counter.data.local.entity.Word


@Database(entities = [Word::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordDao(): WordDao
}