package de.ljz.questify.feature.quests.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestCategoryDao {
    @Upsert
    suspend fun upsertQuestCategory(questCategory: QuestCategoryEntity)

    @Query("SELECT * FROM quest_category_entity")
    fun getAllQuestCategories(): Flow<List<QuestCategoryEntity>>
}