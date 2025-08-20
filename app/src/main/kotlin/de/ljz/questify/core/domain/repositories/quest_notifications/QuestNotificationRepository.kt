package de.ljz.questify.core.domain.repositories.quest_notifications

import de.ljz.questify.core.domain.models.quest_notifications.QuestNotificationEntity
import de.ljz.questify.core.domain.repositories.BaseRepository
import de.ljz.questify.feature.quests.domain.daos.QuestNotificationDao
import de.ljz.questify.feature.quests.domain.repositories.QuestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestNotificationRepository @Inject constructor(
    private val questNotificationDao: QuestNotificationDao,
    private val questRepository: QuestRepository
) : BaseRepository() {

    fun getPendingNotifications(): Flow<List<QuestNotificationEntity>> {
        return questNotificationDao.getPendingNotifications()
    }

    fun getNotificationById(id: Int): QuestNotificationEntity {
        return questNotificationDao.getNotificationById(id)
    }

    suspend fun removeNotifications(questId: Int) {
        questNotificationDao.removeNotificationsByQuestId(questId)
    }

    suspend fun setNotificationAsNotified(id: Int): Int {
        return questNotificationDao.setNotificationAsNotified(id)
    }

    suspend fun isNotified(id: Int): Boolean {
        return questNotificationDao.isNotified(id) > 0
    }

    suspend fun addQuestNotification(questNotifications: QuestNotificationEntity): Long {
        return questNotificationDao.upsertQuestNotification(questNotifications)
    }

    suspend fun getNotificationsByQuestId(questId: Int) =
        questNotificationDao.getNotificationsByQuestId(questId)

    suspend fun addQuestNotifications(notifications: List<QuestNotificationEntity>): List<Long> {
        return questNotificationDao.upsertQuestNotifications(notifications)
    }
}