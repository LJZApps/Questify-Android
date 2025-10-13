package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.daos.QuestDao
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class QuestRepositoryImpl @Inject constructor(
  private val questDao: QuestDao
): QuestRepository {
    override suspend fun addMainQuest(quest: QuestEntity): Long {
        return questDao.upsert(quest)
    }

    override suspend fun setQuestDone(id: Int, done: Boolean) {
        questDao.setQuestDone(id, done)
    }

    override suspend fun updateQuest(quest: QuestEntity) {
        questDao.upsert(quest)
    }

    /*@Deprecated(
        "Please use updateQuest(quest: QuestEntity)",
        replaceWith = ReplaceWith("updateQuest(quest: QuestEntity)"),
        level = DeprecationLevel.ERROR
    )*/
    override suspend fun updateQuest(
        id: Int,
        title: String,
        description: String?,
        difficulty: Difficulty,
        dueDate: Date?,
        categoryId: Int?
    ) {
        questDao.updateQuestById(
            id = id,
            title = title,
            description = description,
            difficulty = difficulty,
            dueDate = dueDate,
            categoryId = categoryId
        )
    }

    override suspend fun getQuests(): Flow<List<QuestWithSubQuests>> {
        return questDao.getAllQuests()
    }

    override suspend fun getQuestById(id: Int): QuestWithSubQuests {
        return questDao.getQuestById(id)
    }

    override fun getQuestsForCategoryStream(categoryId: Int): Flow<List<QuestWithSubQuests>> {
        return questDao.getQuestsForCategoryStream(categoryId)
    }

    override fun getQuestByIdFlow(id: Int): Flow<QuestWithSubQuests?> {
        return questDao.getQuestByIdFlow(id)
    }

    override suspend fun deleteQuest(id: Int) {
        return questDao.deleteQuest(id)
    }
}