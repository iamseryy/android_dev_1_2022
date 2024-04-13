package com.attractions.data

import androidx.room.TypeConverter
import java.util.Calendar


class Converters {
    @TypeConverter
    fun toCalendar(millis: Long?): Calendar? =
        if (millis == null) null else Calendar.getInstance().apply { timeInMillis = millis }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): Long? = calendar?.time?.time
}