package com.attractions.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "photo")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "uri")
    val uri: String = ""
)