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

    suspend fun deleteQuestCategory(questCategory: QuestCategoryEntity) {
        questCategoryDao.deleteQuestCategory(questCategory.id)
    }

    suspend fun updateQuestCategory(questCategory: QuestCategoryEntity, newText: String) {
        questCategoryDao.updateQuestCategory(questCategory.id, newText)
    }

    fun getAllQuestCategories() = questCategoryDao.getAllQuestCategories()

    fun getQuestCategoryById(id: Int) = questCategoryDao.getQuestCategoryById(id)
}