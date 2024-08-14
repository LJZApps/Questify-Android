package de.ljz.questify.data.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import de.ljz.questify.data.database.models.entities.quests.MainQuestEntity
import de.ljz.questify.data.database.models.entities.quests.SubQuestEntity

@Dao
interface QuestDao {

  @Transaction
  @Query("SELECT * FROM main_quests WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY title, description DESC")
  suspend fun searchQuests(query: String): List<MainQuestEntity>

  @Upsert
  suspend fun upsertMainQuest(value: MainQuestEntity)

  @Upsert
  suspend fun upsertSubQuest(value: SubQuestEntity)

  @Upsert
  suspend fun upsertQuests(value: List<MainQuestEntity>)

  @Upsert
  suspend fun upsertSubQuests(value: List<SubQuestEntity>)

  @Query("DELETE FROM sub_quests WHERE main_quest_id = :mainQuestId")
  suspend fun clearSubQuestsForMainQuest(mainQuestId: Int)
}