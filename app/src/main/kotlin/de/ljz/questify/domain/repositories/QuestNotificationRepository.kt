package de.ljz.questify.domain.repositories

import de.ljz.questify.domain.daos.QuestNotificationDao
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestNotificationRepository @Inject constructor(
    private val questNotificationDao: QuestNotificationDao
) : BaseRepository() {
    suspend fun addQuestNotification(questNotifications: QuestNotificationEntity): Long {
        return questNotificationDao.upsertQuestNotification(questNotifications)
    }
}