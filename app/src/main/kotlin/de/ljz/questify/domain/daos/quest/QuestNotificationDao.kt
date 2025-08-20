package de.ljz.questify.domain.daos.quest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import de.ljz.questify.domain.models.notifications.QuestNotificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuestNotification(value: QuestNotificationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertQuestNotifications(notifications: List<QuestNotificationEntity>): List<Long>

    @Transaction
    @Query("UPDATE quest_notifications SET notified = 1 WHERE id = :id")
    suspend fun setNotificationAsNotified(id: Int): Int

    @Query("SELECT COUNT(*) FROM quest_notifications WHERE id = :id AND notified = 1")
    suspend fun isNotified(id: Int): Int

    @Query("SELECT * FROM quest_notifications WHERE notify_at > :currentTime AND notified = 0")
    fun getPendingNotifications(currentTime: Long = System.currentTimeMillis()): Flow<List<QuestNotificationEntity>>

    @Query("SELECT * FROM quest_notifications WHERE id = :id")
    fun getNotificationById(id: Int): QuestNotificationEntity

    @Query("SELECT * FROM quest_notifications WHERE quest_id = :questId")
    suspend fun getNotificationsByQuestId(questId: Int): List<QuestNotificationEntity>

    @Transaction
    @Query("DELETE FROM quest_notifications WHERE quest_id = :questId")
    suspend fun removeNotificationsByQuestId(questId: Int)

}