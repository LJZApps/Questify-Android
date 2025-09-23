package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.daos.QuestCategoryDao
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestCategoryRepositoryImpl @Inject constructor(
    private val questCategoryDao: QuestCategoryDao
) : QuestCategoryRepository {

    override suspend fun addQuestCategory(questCategoryEntity: QuestCategoryEntity) {
        questCategoryDao.upsertQuestCategory(
            questCategory = questCategoryEntity
        )
    }

    override suspend fun deleteQuestCategory(id: Int) {
        questCategoryDao.deleteQuestCategory(
            questCategoryId = id
        )
    }

    override suspend fun updateQuestCategory(id: Int, value: String) {
        questCategoryDao.updateQuestCategory(
            questCategoryId = id,
            text =  value
        )
    }

    override fun getAllQuestCategories(): Flow<List<QuestCategoryEntity>> {
        return questCategoryDao.getAllQuestCategories()
    }

    override fun getQuestCategoryById(id: Int): Flow<QuestCategoryEntity?> {
        return questCategoryDao.getQuestCategoryById(
            id = id
        )
    }
}