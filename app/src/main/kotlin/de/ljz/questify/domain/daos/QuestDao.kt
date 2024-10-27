package de.ljz.questify.domain.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.ljz.questify.domain.models.quests.MainQuestEntity
import de.ljz.questify.domain.models.quests.SubQuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {

    @Transaction
    @Query("SELECT * FROM main_quests WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY title, description DESC")
    suspend fun searchQuests(query: String): List<MainQuestEntity>

    @Transaction
    @Query("SELECT * FROM sub_quests WHERE main_quest_id = :mainQuestId")
    suspend fun getSubQuests(mainQuestId: Int): List<SubQuestEntity>

    @Query("SELECT * FROM main_quests WHERE done = 0 ORDER BY done")
    fun getMainQuests(): Flow<List<MainQuestEntity>>

    @Query("SELECT * FROM main_quests WHERE id = :id")
    fun findMainQuestById(id: Int): Flow<MainQuestEntity>

    @Transaction
    @Query("UPDATE main_quests SET done = :done WHERE id = :id")
    suspend fun setQuestDone(id: Int, done: Boolean)

    @Upsert
    suspend fun upsertMainQuest(value: MainQuestEntity): Long

    @Upsert
    suspend fun upsertSubQuest(value: SubQuestEntity)

    @Upsert
    suspend fun upsertQuests(value: List<MainQuestEntity>)

    @Upsert
    suspend fun upsertSubQuests(value: List<SubQuestEntity>)

    @Query("DELETE FROM sub_quests WHERE main_quest_id = :mainQuestId")
    suspend fun clearSubQuestsForMainQuest(mainQuestId: Int)
}