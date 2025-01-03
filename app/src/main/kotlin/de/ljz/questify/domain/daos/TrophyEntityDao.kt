package de.ljz.questify.domain.daos

import androidx.room.Dao
import androidx.room.Query
import de.ljz.questify.domain.models.trophies.TrophyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrophyEntityDao {

    @Query("SELECT * FROM trophy_entity")
    fun getAllTrophies(): Flow<List<TrophyEntity>>

}