package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface QuestRepository {
    suspend fun addMainQuest(quest: QuestEntity): Long

    suspend fun setQuestDone(id: Int, done: Boolean)

    suspend fun updateQuest(quest: QuestEntity)

    suspend fun updateQuest(
        id: Int,
        title: String,
        description: String? = null,
        difficulty: Difficulty,
        dueDate: Date? = null,
        categoryId: Int? = null
    )

    fun getQuests(): Flow<List<QuestWithSubQuests>>

    fun getQuestById(id: Int): QuestWithSubQuests

    fun getQuestsForCategoryStream(categoryId: Int): Flow<List<QuestWithSubQuests>>

    fun getQuestByIdFlow(id: Int): Flow<QuestWithSubQuests?>

    suspend fun deleteQuest(id: Int)
}