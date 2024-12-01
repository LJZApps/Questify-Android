package de.ljz.questify.domain.models.quests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "quest_checklist_entity",
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["quest_id"]
        )
    ]
)
data class QuestChecklistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "quest_id")
    val questId: Int,

    @ColumnInfo(name = "text")
    val text: String,

    val createdAt: Date,

    val done: Boolean = false
)