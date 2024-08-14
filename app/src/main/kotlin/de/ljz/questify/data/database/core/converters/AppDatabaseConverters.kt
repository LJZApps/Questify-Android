package de.ljz.questify.data.database.core.converters

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