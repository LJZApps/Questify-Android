package de.ljz.questify.domain.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.ljz.questify.domain.models.quests.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {

    @Transaction
    @Query("SELECT * FROM quest_entity WHERE title LIKE '%' || :query || '%' OR notes LIKE '%' || :query || '%' ORDER BY title, notes DESC")
    suspend fun searchQuests(query: String): List<QuestEntity>

    @Query("SELECT * FROM quest_entity WHERE done = 0 ORDER BY done")
    fun getMainQuests(): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quest_entity WHERE id = :id")
    fun findMainQuestById(id: Int): Flow<QuestEntity>

    @Query("SELECT * FROM quest_entity WHERE id = :id")
    fun getQuestById(id: Int): QuestEntity

    @Query("UPDATE quest_entity SET title = :title, notes = :description WHERE id = :id")
    suspend fun updateQuestById(id: Int, title: String, description: String? = null)

    @Query("SELECT * FROM quest_entity WHERE id = :id")
    suspend fun suspendGetQuestById(id: Int): QuestEntity

    @Transaction
    @Query("UPDATE quest_entity SET done = :done WHERE id = :id")
    suspend fun setQuestDone(id: Int, done: Boolean)

    @Upsert
    suspend fun upsertMainQuest(value: QuestEntity): Long

    @Upsert
    suspend fun upsertQuests(value: List<QuestEntity>)

    @Transaction
    @Query("DELETE FROM quest_entity WHERE id = :questId")
    suspend fun deleteQuest(questId: Int)
}