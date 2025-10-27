package de.ljz.questify.feature.quests.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.ljz.questify.feature.quests.data.models.SubQuestEntity

@Dao
interface SubQuestDao {
    @Upsert
    suspend fun upsertSubQuest(subQuest: SubQuestEntity)

    @Upsert
    suspend fun upsertSubQuests(subQuests: List<SubQuestEntity>)

    @Query("DELETE FROM sub_quest_entity WHERE id = :id")
    suspend fun deleteSubQuest(id: Int)

    @Query("UPDATE sub_quest_entity SET is_done = :checked WHERE id = :id")
    suspend fun checkSubQuest(id: Int, checked: Boolean)
}