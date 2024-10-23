package de.ljz.questify.domain.models.quests

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "sub_quests",
    foreignKeys = [
        ForeignKey(
            entity = MainQuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["main_quest_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["main_quest_id"]
        )
    ]
)
data class SubQuestEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "main_quest_id")
    val mainQuestId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "points")
    val points: Int,

    @ColumnInfo(name = "due_date")
    val dueDate: Date?,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date,

    @ColumnInfo(name = "lock_deletion")
    val lockDeletion: Boolean,

    @ColumnInfo(name = "done")
    val done: Boolean,

    @ColumnInfo(name = "archived")
    val archived: Boolean
)
