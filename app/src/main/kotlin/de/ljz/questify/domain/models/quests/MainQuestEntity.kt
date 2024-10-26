package de.ljz.questify.domain.models.quests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.ljz.questify.data.shared.Points
import java.util.Date

@Entity(tableName = "main_quests")
data class MainQuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "points")
    val points: Points,

    @ColumnInfo(name = "due_date")
    val dueDate: Date? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date? = null,

    @ColumnInfo(name = "lock_deletion")
    val lockDeletion: Boolean = false,

    @ColumnInfo(name = "done")
    val done: Boolean = false,
)
