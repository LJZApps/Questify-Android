package de.ljz.questify.feature.habits.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import de.ljz.questify.feature.habits.data.models.HabitsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit_entity")
    fun getAllHabits(): Flow<List<HabitsEntity>>

    @Query("SELECT * FROM habit_entity WHERE id = :id")
    fun getHabitByIdFlow(id: Int): Flow<HabitsEntity>

    @Query("SELECT * FROM habit_entity WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitsEntity

    @Upsert
    suspend fun upsertHabit(habit: HabitsEntity)
}