package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.daos.QuestDao
import de.ljz.questify.feature.quests.data.models.QuestEntity
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao
) : BaseRepository() {
    suspend fun addMainQuest(quest: QuestEntity): Long {
        return questDao.upsertMainQuest(quest)
    }

    suspend fun setQuestDone(id: Int, done: Boolean) {
        questDao.setQuestDone(id, done)
    }

    suspend fun upsertQuests(quests: List<QuestEntity>) {
        questDao.upsertQuests(quests)
    }

    suspend fun updateQuest(
        id: Int,
        title: String,
        description: String? = null,
        difficulty: Difficulty,
        dueDate: Date? = null
    ) {
        questDao.updateQuestById(id, title, description, difficulty, dueDate)
    }

    fun getQuests() = questDao.getAllQuests()

    fun getQuestById(id: Int) = questDao.getQuestById(id)

    fun getQuestsForCategoryStream(categoryId: Int) = questDao.getQuestsForCategoryStream(categoryId)

    suspend fun suspendGetQuestById(id: Int) = questDao.suspendGetQuestById(id)

    fun getQuestByIdFlow(id: Int) = questDao.getQuestByIdFlow(id)

    suspend fun deleteQuest(id: Int) = questDao.deleteQuest(id)
}