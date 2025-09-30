package de.ljz.questify.feature.quests.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import de.ljz.questify.feature.quests.data.models.SubQuestEntity

data class QuestWithSubQuests(
    @Embedded val quest: QuestEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "quest_id"
    )
    val subTasks: List<SubQuestEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "quest_id"
    )
    val notifications: List<QuestNotificationEntity>
)
