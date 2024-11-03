package de.ljz.questify.domain.repositories

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import de.ljz.questify.core.receiver.QuestNotificationReceiver
import de.ljz.questify.domain.daos.QuestNotificationDao
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
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

    suspend fun getNotificationsByQuestId(questId: Int) = questNotificationDao.getNotificationsByQuestId(questId)

    suspend fun addQuestNotifications(notifications: List<QuestNotificationEntity>): List<Long> {
        return questNotificationDao.upsertQuestNotifications(notifications)
    }
}