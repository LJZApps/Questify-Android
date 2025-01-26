package de.ljz.questify.domain.repositories.quests

import de.ljz.questify.domain.daos.QuestTaskDao
import de.ljz.questify.domain.models.quests.QuestTaskEntity
import de.ljz.questify.domain.repositories.BaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestTaskRepository @Inject constructor(
    private val questTaskDao: QuestTaskDao
) : BaseRepository() {

    fun getQuestTasks(questId: Int) = questTaskDao.getQuestTasksByQuestId(questId = questId)

    suspend fun addQuestTaskEntity(questTaskEntity: QuestTaskEntity) {
        questTaskDao.upsertQuestTask(questTaskEntity)
    }

    suspend fun addMultipleQuestTaskEntity(questTaskEntity: List<QuestTaskEntity>) {
        questTaskDao.upsertMultipleQuestTask(questTaskEntity)
    }
}