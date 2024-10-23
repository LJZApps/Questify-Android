package de.ljz.questify.domain.models.notifications

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.ljz.questify.domain.models.quests.MainQuestEntity
import java.util.Date

@Entity(
    tableName = "quest_notifications",
    foreignKeys = [
        ForeignKey(
            entity = MainQuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["quest_id"]
        )
    ]
)
data class QuestNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "quest_id")
    val questId: Int,

    @ColumnInfo(name = "notified")
    val notified: Boolean = false,

    @ColumnInfo(name = "notify_at")
    val notifyAt: Date
)
