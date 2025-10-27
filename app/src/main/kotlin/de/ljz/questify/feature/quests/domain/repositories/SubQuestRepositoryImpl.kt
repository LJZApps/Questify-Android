package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.daos.SubQuestDao
import de.ljz.questify.feature.quests.data.models.SubQuestEntity
import javax.inject.Inject

class SubQuestRepositoryImpl @Inject constructor(
    private val subQuestDao: SubQuestDao
) : SubQuestRepository {
    override suspend fun addSubQuest(subQuest: SubQuestEntity) {
        subQuestDao.upsertSubQuest(
            subQuest = subQuest
        )
    }

    override suspend fun addSubQuests(subQuests: List<SubQuestEntity>) {
        subQuestDao.upsertSubQuests(
            subQuests = subQuests
        )
    }

    override suspend fun deleteSubQuest(id: Int) {
        subQuestDao.deleteSubQuest(
            id = id
        )
    }

    override suspend fun checkSubQuest(id: Int, checked: Boolean) {
        subQuestDao.checkSubQuest(
            id = id,
            checked = checked
        )
    }
}