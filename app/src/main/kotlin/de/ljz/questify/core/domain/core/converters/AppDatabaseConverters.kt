package de.ljz.questify.core.domain.core.converters

import androidx.room.TypeConverter
import java.util.Date

class AppDatabaseConverters {

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