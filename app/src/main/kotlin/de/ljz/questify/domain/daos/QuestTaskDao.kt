package de.ljz.questify.domain.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.ljz.questify.domain.models.quests.QuestTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestTaskDao {

    @Upsert
    suspend fun upsertQuestTask(questTask: QuestTaskEntity)

    @Upsert
    suspend fun upsertMultipleQuestTask(questTask: List<QuestTaskEntity>)

    @Query("SELECT * FROM quest_task_entity WHERE quest_id = :questId")
    fun getQuestTasksByQuestId(questId: Int): Flow<List<QuestTaskEntity>>
}