package de.ljz.questify.data.repositories

import de.ljz.questify.data.database.daos.QuestDao
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao
) : BaseRepository() {
    suspend fun addMainQuest (quest: MainQuestEntity): Long {
        return questDao.upsertMainQuest(quest)
    }

    suspend fun setQuestDone(id: Int, done: Boolean) {
        questDao.setQuestDone(id, done)
    }

    fun getQuests () = questDao.getMainQuests()

    fun findMainQuestById(id: Int) = questDao.findMainQuestById(id)
}