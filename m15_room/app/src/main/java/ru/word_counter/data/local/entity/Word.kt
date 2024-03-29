package ru.word_counter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "word")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "counter")
    val counter: Int
)