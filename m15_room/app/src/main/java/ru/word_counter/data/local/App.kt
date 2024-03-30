package ru.word_counter.data.local

import android.app.Application
import androidx.room.Room

class App: Application() {
    public lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "word_counter_db"
        ).build()
    }
}