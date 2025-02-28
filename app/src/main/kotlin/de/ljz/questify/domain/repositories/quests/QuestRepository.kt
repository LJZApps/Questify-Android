package de.ljz.questify.domain.repositories.quests

import de.ljz.questify.domain.daos.QuestDao
import de.ljz.questify.domain.models.quests.QuestEntity
import de.ljz.questify.domain.repositories.BaseRepository
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

    suspend fun updateQuest(id: Int, title: String, description: String? = null) {
        questDao.updateQuestById(id, title, description)
    }

    fun getQuests() = questDao.getAllQuests()

    fun getQuestById(id: Int) = questDao.getQuestById(id)

    suspend fun suspendGetQuestById(id: Int) = questDao.suspendGetQuestById(id)

    fun getQuestByIdFlow(id: Int) = questDao.getQuestByIdFlow(id)

    suspend fun deleteQuest(id: Int) = questDao.deleteQuest(id)
}