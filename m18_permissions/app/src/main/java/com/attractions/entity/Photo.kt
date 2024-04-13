package com.attractions.entity

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar


@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "uri")
    val uri: String = "",
    @ColumnInfo(name = "date")
    val date: Calendar = Calendar.getInstance()
)
