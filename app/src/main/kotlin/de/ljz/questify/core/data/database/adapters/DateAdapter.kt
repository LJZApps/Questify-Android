package de.ljz.questify.core.data.database.adapters

import androidx.room.TypeConverter
import java.util.Date

class DateAdapter {

    @TypeConverter
    fun fromDate(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return value?.let {
            Date(it)
        }
    }
}