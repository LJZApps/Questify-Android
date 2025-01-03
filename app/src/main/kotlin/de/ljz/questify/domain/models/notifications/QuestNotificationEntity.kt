package de.ljz.questify.domain.models.notifications

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.domain.models.trophies.TrophyEntity
import java.util.Date

@Entity(
    tableName = "quest_notifications",
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TrophyEntity::class,
            parentColumns = ["id"],
            childColumns = ["trophy_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["quest_id"]),
        Index(value = ["trophy_id"])
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
    val notifyAt: Date,

    @ColumnInfo(name = "trophy_id")
    val trophyId: Int? = null
)