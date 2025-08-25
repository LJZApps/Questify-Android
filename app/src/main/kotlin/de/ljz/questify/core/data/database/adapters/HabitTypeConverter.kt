package de.ljz.questify.core.data.database.adapters

import androidx.room.TypeConverter
import de.ljz.questify.feature.habits.data.models.HabitType

class HabitTypeConverter {
    @TypeConverter
    fun fromHabitTypeList(types: List<HabitType>): String {
        return types.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toHabitTypeList(data: String): List<HabitType> {
        if (data.isEmpty()) {
            return emptyList()
        }
        return data.split(",").map { HabitType.valueOf(it) }
    }
}