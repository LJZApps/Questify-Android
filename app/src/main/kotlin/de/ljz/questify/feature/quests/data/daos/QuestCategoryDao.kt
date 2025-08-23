package de.ljz.questify.feature.quests.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestCategoryDao {
    @Upsert
    suspend fun upsertQuestCategory(questCategory: QuestCategoryEntity)

    @Query("DELETE FROM quest_category_entity WHERE id = :questCategoryId")
    suspend fun deleteQuestCategory(questCategoryId: Int)

    @Query("SELECT * FROM quest_category_entity")
    fun getAllQuestCategories(): Flow<List<QuestCategoryEntity>>

    @Query("SELECT * FROM quest_category_entity WHERE id = :id")
    fun getQuestCategoryById(id: Int): Flow<QuestCategoryEntity?>

    @Transaction
    @Update
    suspend fun updateCategories(categories: List<QuestCategoryEntity>)

    @Query("SELECT COUNT(*) FROM quest_category_entity")
    fun getCategoryCount(): Long

    @Query("UPDATE quest_category_entity SET text = :newText WHERE id = :questCategoryId")
    suspend fun updateQuestCategory(questCategoryId: Int, newText: String)
}