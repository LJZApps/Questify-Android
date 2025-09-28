package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.daos.QuestNotificationDao
import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestNotificationRepositoryImpl @Inject constructor(
    private val questNotificationDao: QuestNotificationDao
): QuestNotificationRepository {
    override fun getPendingNotifications(): Flow<List<QuestNotificationEntity>> {
        return questNotificationDao.getPendingNotifications()
    }

    override fun getNotificationById(id: Int): QuestNotificationEntity {
        return questNotificationDao.getNotificationById(id)
    }

    override suspend fun removeNotifications(questId: Int) {
        questNotificationDao.removeNotificationsByQuestId(questId)
    }

    override suspend fun setNotificationAsNotified(id: Int): Int {
        return questNotificationDao.setNotificationAsNotified(id)
    }

    override suspend fun isNotified(id: Int): Boolean {
        return questNotificationDao.isNotified(id) > 0
    }

    override suspend fun addQuestNotification(questNotifications: QuestNotificationEntity): Long {
        return questNotificationDao.upsertQuestNotification(questNotifications)
    }

    override suspend fun getNotificationsByQuestId(questId: Int) =
        questNotificationDao.getNotificationsByQuestId(questId)

    override suspend fun addQuestNotifications(notifications: List<QuestNotificationEntity>): List<Long> {
        return questNotificationDao.upsertQuestNotifications(notifications)
    }
}