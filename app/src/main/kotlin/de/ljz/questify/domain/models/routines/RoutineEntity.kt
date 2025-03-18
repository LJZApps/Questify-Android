package de.ljz.questify.domain.models.routines

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_entity")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

   @ColumnInfo(name = "title")
    val title: String,
)
