package de.ljz.questify.feature.quests.domain.repositories

import de.ljz.questify.feature.quests.data.models.QuestNotificationEntity
import kotlinx.coroutines.flow.Flow

interface QuestNotificationRepository {

    fun getPendingNotifications(): Flow<List<QuestNotificationEntity>>

    fun getNotificationById(id: Int): QuestNotificationEntity

    suspend fun removeNotifications(questId: Int)

    suspend fun setNotificationAsNotified(id: Int): Int

    suspend fun isNotified(id: Int): Boolean

    suspend fun addQuestNotification(questNotifications: QuestNotificationEntity): Long

    suspend fun getNotificationsByQuestId(questId: Int): List<QuestNotificationEntity>

    suspend fun addQuestNotifications(notifications: List<QuestNotificationEntity>): List<Long>
}