package de.ljz.questify.data.repositories

import de.ljz.questify.data.database.daos.QuestDao
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestRepository @Inject constructor(
    private val questDao: QuestDao
) : BaseRepository() {
    suspend fun addMainQuest (quest: MainQuestEntity) {
        questDao.upsertMainQuest(quest)
    }

    fun getQuests () = questDao.getMainQuests()
}