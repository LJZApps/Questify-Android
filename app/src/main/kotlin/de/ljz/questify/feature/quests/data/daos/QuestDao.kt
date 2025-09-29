package de.ljz.questify.feature.quests.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import de.ljz.questify.core.utils.Difficulty
import de.ljz.questify.feature.quests.data.models.QuestEntity
import de.ljz.questify.feature.quests.data.relations.QuestWithSubQuests
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface QuestDao {

    @Transaction
    @Query("SELECT * FROM quest_entity WHERE title LIKE '%' || :query || '%' OR notes LIKE '%' || :query || '%' ORDER BY title, notes DESC")
    suspend fun searchQuests(query: String): List<QuestWithSubQuests>

    @Query("SELECT * FROM quest_entity")
    fun getAllQuests(): Flow<List<QuestWithSubQuests>>

    @Update
    suspend fun updateQuest(quest: QuestEntity)

    @Query("SELECT * FROM quest_entity WHERE id = :id")
    suspend fun getQuestById(id: Int): QuestWithSubQuests

    @Query("SELECT * FROM quest_entity WHERE category_id = :categoryId")
    fun getQuestsForCategoryStream(categoryId: Int): Flow<List<QuestWithSubQuests>>

    @Query("UPDATE quest_entity SET title = :title, notes = :description, difficulty = :difficulty, due_date = :dueDate, category_id = :categoryId WHERE id = :id")
    suspend fun updateQuestById(
        id: Int,
        title: String,
        description: String? = null,
        difficulty: Difficulty,
        dueDate: Date? = null,
        categoryId: Int? = null
    )

    @Query("SELECT * FROM quest_entity WHERE id = :id")
    suspend fun suspendGetQuestById(id: Int): QuestWithSubQuests

    @Query("SELECT * FROM quest_entity WHERE id = :id")
    fun getQuestByIdFlow(id: Int): Flow<QuestWithSubQuests?>

    @Transaction
    @Query("UPDATE quest_entity SET done = :done WHERE id = :id")
    suspend fun setQuestDone(id: Int, done: Boolean)

    @Query("SELECT COUNT(*) FROM quest_entity WHERE done = 1")
    fun getCompletedQuestsCount(): Int

    @Upsert
    suspend fun upsertMainQuest(value: QuestEntity): Long

    @Upsert
    suspend fun upsertQuests(value: List<QuestEntity>)

    @Transaction
    @Query("DELETE FROM quest_entity WHERE id = :questId")
    suspend fun deleteQuest(questId: Int)
}