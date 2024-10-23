package de.ljz.questify.domain.daos

import androidx.room.Dao
import androidx.room.Upsert
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity

@Dao
interface QuestNotificationDao {

    @Upsert
    suspend fun upsertQuestNotification(value: QuestNotificationEntity): Long

}