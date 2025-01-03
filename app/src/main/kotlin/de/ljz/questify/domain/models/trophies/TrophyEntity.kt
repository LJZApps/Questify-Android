package de.ljz.questify.domain.models.trophies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import de.ljz.questify.domain.models.quests.QuestEntity
import java.util.Date

@Entity(
    tableName = "trophy_entity",
    foreignKeys = [
        ForeignKey(
            entity = TrophyEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.Companion.SET_NULL
        ),
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
            onDelete = ForeignKey.Companion.SET_NULL
        )
    ],
    indices = [
        Index(value = ["category_id"]),
        Index(value = ["quest_id"])
    ]
)
data class TrophyEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Date,

    @ColumnInfo(name = "earned_at")
    val earnedAt: Date,

    @ColumnInfo(name = "category_id")
    val categoryId: Int? = null,

    @ColumnInfo(name = "quest_id")
    val questId: Int? = null,

    @ColumnInfo(name = "icon_name")
    val iconName: String
)