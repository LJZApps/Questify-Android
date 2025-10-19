package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.models.SubQuestEntity

interface SubQuestRepository {
    suspend fun addSubQuest(subQuest: SubQuestEntity)

    suspend fun addSubQuests(subQuests: List<SubQuestEntity>)

    suspend fun deleteSubQuest(id: Int)
}