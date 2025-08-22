package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.daos.QuestCategoryDao
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestCategoryRepository @Inject constructor(
    private val questCategoryDao: QuestCategoryDao
) {
    suspend fun addQuestCategory(questCategory: QuestCategoryEntity) {
        questCategoryDao.upsertQuestCategory(questCategory)
    }

    fun getAllQuestCategories() = questCategoryDao.getAllQuestCategories()
}