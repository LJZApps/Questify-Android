package de.ljz.questify.domain.models.quests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.ljz.questify.core.application.Difficulty
import java.util.Date

@Entity(tableName = "quest_entity")
data class QuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "notes")
    val notes: String? = null,

    @ColumnInfo(name = "difficulty")
    val difficulty: Difficulty,

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