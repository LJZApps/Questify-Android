package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import kotlinx.coroutines.flow.Flow

interface QuestCategoryRepository {
    suspend fun addQuestCategory(
        questCategoryEntity: QuestCategoryEntity
    )

    suspend fun deleteQuestCategory(
        id: Int
    )

    suspend fun updateQuestCategory(
        id: Int,
        value: String
    )

    fun getAllQuestCategories(): Flow<List<QuestCategoryEntity>>

    fun getQuestCategoryById(
        id: Int
    ): Flow<QuestCategoryEntity?>
}